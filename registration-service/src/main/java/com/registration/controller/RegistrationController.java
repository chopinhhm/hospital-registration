package com.registration.controller;

import com.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 挂号接口
 */
@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/create")
    public String create(@RequestParam String patientId, 
                         @RequestParam String scheduleId,
                         @RequestParam String date) {
        return registrationService.createRegistration(patientId, scheduleId, date);
    }

    @GetMapping("/query/{patientId}")
    public Object queryMyRegistrations(@PathVariable String patientId) {
        return "查询挂号记录";
    }
}
