package com.allybros.taskcase.controller;

import org.springframework.stereotype.Controller;

@Controller
public class RegisterController {

    public String registerForm(){
        return "register-form";
    }

}
