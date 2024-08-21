package com.mrymw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    @GetMapping("/chat")
    public String chat() {
        return "chat.html";
    }


}
