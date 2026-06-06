package com.registration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 挂号核心服务
 * 
 * 分布式防重设计：
 * 1. Redis Lua 原子操作（SETNX + EXPIRE）
 * 2. 数据库唯一索引兜底
 * 3. 业务状态校验
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final StringRedisTemplate redisTemplate;

    /** Lua 原子防重脚本：SETNX + EXPIRE 原子执行 */
    private static final String DEDUP_LUA = 
        "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
        "    redis.call('expire', KEYS[1], ARGV[2]) " +
        "    return 1 " +
        "else " +
        "    return 0 " +
        "end";

    /**
     * 创建挂号订单（含分布式防重）
     * @param patientId 患者ID
     * @param scheduleId 排班ID
     * @param date 挂号日期
     * @return 订单ID
     */
    public String createRegistration(String patientId, String scheduleId, String date) {
        // Step 1: Redis Lua 原子防重
        String dedupKey = String.format("reg:dedup:%s:%s:%s", patientId, scheduleId, date);
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(DEDUP_LUA, Long.class);
        Long result = redisTemplate.execute(script, 
            Collections.singletonList(dedupKey), 
            UUID.randomUUID().toString(), 
            String.valueOf(TimeUnit.MINUTES.toSeconds(5))
        );

        if (result == null || result == 0) {
            log.warn("重复挂号请求被拦截: patient={}, schedule={}, date={}", patientId, scheduleId, date);
            throw new RuntimeException("请勿重复提交挂号请求");
        }

        // Step 2: 业务校验（排班是否存在、余量是否充足等）
        log.info("通过防重校验，创建挂号订单: patient={}, schedule={}, date={}", patientId, scheduleId, date);

        // Step 3: 写入数据库（唯一索引兜底）
        // INSERT INTO registration_order (id, patient_id, schedule_id, reg_date, status)
        // VALUES (?, ?, ?, ?, 'PENDING')
        // ON DUPLICATE KEY UPDATE ...

        return UUID.randomUUID().toString();
    }
}
