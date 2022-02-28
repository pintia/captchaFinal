package com.example.verificationdemo.Controller;

import com.example.verificationdemo.Service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@RestController
public class IndexController {
    @Autowired
    private VerificationService verificationService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException, ScriptException, NoSuchMethodException, InterruptedException {
        HttpSession session = request.getSession();
        String sessionId = UUID.randomUUID().toString();
        session.setAttribute("userSession", sessionId);
        return verificationService.getResources(sessionId);
    }

//    @RequestMapping("/static/**")
//    public String staticForbid(){
//        return "error";
//    }
}
