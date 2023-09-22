package br.utfpr.edu.jogogeneral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shutdown")
public class ApplicationController {

    private final ApplicationContext applicationContext;

    @Autowired
    public ApplicationController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/")
    public String viewIndex() {
        return "./index";
    }

    @PostMapping("/exit")
    @ResponseBody
    public String exitApplication() {
        System.exit(0);

        return "Ok";
    }
}