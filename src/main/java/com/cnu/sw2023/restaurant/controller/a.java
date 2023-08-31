package com.cnu.sw2023.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class a {

    @GetMapping("/")
    public String go (){
        return "index";
    }
}
