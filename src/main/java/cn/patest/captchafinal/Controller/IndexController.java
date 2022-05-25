package cn.patest.captchafinal.Controller;

import cn.patest.captchafinal.Service.VerificationService;
import cn.patest.captchafinal.type.CheckLocationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    private VerificationService verificationService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/captcha")
    public String captcha(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = UUID.randomUUID().toString();
        session.setAttribute("userSession", sessionId);
        return "captcha";
    }

    @RequestMapping(value = "/questionImg",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] staticQuestionImg(HttpServletRequest request) throws IOException {
        String sessionId = (String) request.getSession().getAttribute("userSession");
        return verificationService.requestQuestionImg(sessionId);
    }

    @RequestMapping(value = "/answerImg",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] staticAnswerImg(HttpServletRequest request) throws IOException {
        String sessionId = (String) request.getSession().getAttribute("userSession");
        return verificationService.requestAnswerImg(sessionId);
    }

    @RequestMapping("/checkLocation")
    @ResponseBody
    public boolean checkLocation(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CheckLocationRequest checkLocationRequest = objectMapper.readValue(requestBody, CheckLocationRequest.class);
        String sessionId = (String) request.getSession().getAttribute("userSession");
        return verificationService.checkLocation(checkLocationRequest, sessionId);
    }

    @RequestMapping("/requestImg")
    @ResponseBody
    public boolean staticImg(HttpServletRequest request, HttpServletResponse response){
        String sessionId = (String) request.getSession().getAttribute("userSession");
        return verificationService.requestImg(sessionId);
    }
}
