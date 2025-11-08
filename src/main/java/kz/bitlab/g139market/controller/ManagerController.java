package kz.bitlab.g139market.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {

    @GetMapping
    public String welcome() {
        return "Welcome, manager!";
    }
}
