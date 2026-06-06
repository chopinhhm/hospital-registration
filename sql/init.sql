-- 互联网医院挂号系统 数据库初始化脚本
CREATE DATABASE IF NOT EXISTS hospital_registration DEFAULT CHARACTER SET utf8mb4;
USE hospital_registration;

-- 排班表
CREATE TABLE doctor_schedule (
    id VARCHAR(36) PRIMARY KEY,
    doctor_id VARCHAR(36) NOT NULL,
    department VARCHAR(50) NOT NULL,
    schedule_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    total_quota INT NOT NULL,
    remaining_quota INT NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    UNIQUE KEY uk_doctor_date_slot (doctor_id, schedule_date, time_slot),
    INDEX idx_date (schedule_date),
    INDEX idx_department (department)
) ENGINE=InnoDB COMMENT='排班表';

-- 挂号订单表（联合唯一索引防重兜底）
CREATE TABLE registration_order (
    id VARCHAR(36) PRIMARY KEY,
    patient_id VARCHAR(36) NOT NULL,
    schedule_id VARCHAR(36) NOT NULL,
    reg_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_patient_schedule (patient_id, schedule_id, reg_date),
    INDEX idx_patient (patient_id),
    INDEX idx_schedule (schedule_id)
) ENGINE=InnoDB COMMENT='挂号订单表';
