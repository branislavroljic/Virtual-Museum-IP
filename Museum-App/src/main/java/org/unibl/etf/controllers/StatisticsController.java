package org.unibl.etf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.models.dto.Report;
import org.unibl.etf.services.LogService;
import org.unibl.etf.services.UserService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final LogService logService;
    private final UserService userService;

    public StatisticsController(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @GetMapping("/logged_in_users")
    public ResponseEntity<Integer> countLoggedInUsers(Authentication principal){
        logService.log(principal.getName(),"COUNT_LOGGED_IN", "User requested for number of logged in users.",
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(userService.countLoggedInUsers());
    }

    @GetMapping("/registered_users")
    public ResponseEntity<Integer> countRegisterUsers(Authentication principal){
        logService.log(principal.getName(),"COUNT_REGISTERED", "User  requested for number of registered users.",
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(userService.countRegisteredUsers());
    }

    @GetMapping("/24h_login")
    public ResponseEntity<List<Report>> getLoginsByHourFromLast24Hour(Authentication principal){
        logService.log(principal.getName(),"GET_24_LOGINS", "User  requested for 24h login statistics.",
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(logService.selectLoginsByHourFromLast24Hour());
    }
}
