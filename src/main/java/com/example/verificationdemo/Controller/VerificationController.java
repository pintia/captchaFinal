package com.example.verificationdemo.Controller;

import com.example.verificationdemo.Service.VerificationService;
import com.example.verificationdemo.type.CheckLocationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
public class VerificationController {
    @Autowired
    private VerificationService verificationService;

    @RequestMapping("/captcha")
    public String captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String sessionId = UUID.randomUUID().toString();
        session.setAttribute("userSession", sessionId);

        return verificationService.getResources(sessionId);
    }

    @RequestMapping("/checkLocation")
    public boolean checkLocation(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CheckLocationRequest checkLocationRequest = objectMapper.readValue(requestBody, CheckLocationRequest.class);
        String sessionId = (String) request.getSession().getAttribute("userSession");
        return verificationService.checkLocation(checkLocationRequest, sessionId);
    }
}
