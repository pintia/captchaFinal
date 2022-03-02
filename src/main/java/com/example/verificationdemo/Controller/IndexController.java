package com.example.verificationdemo.Controller;

import com.example.verificationdemo.Service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/static/js/**")
    public String staticJsForbid(){
        return "error";
    }
}
