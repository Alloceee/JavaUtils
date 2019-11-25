package com.yws.email.controller;

import com.yws.email.entity.Email;
import com.yws.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    @ResponseBody
    public String send(Email email){
        return emailService.send(email.getToEmail(),email.getObject(),email.getText(),true);
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
