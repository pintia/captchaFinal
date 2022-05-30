package cn.patest.captchafinal.Controller;

import cn.patest.captchafinal.Service.VerificationService;
import cn.patest.captchafinal.type.CheckLocationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    private VerificationService verificationService;

    @CrossOrigin
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @CrossOrigin
    @RequestMapping("/captcha")
    public String captcha(){
        return UUID.randomUUID().toString();
    }

    @CrossOrigin
    @RequestMapping(value = "/questionImg",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] staticQuestionImg(@RequestParam(name="session") String sessionId) throws IOException {
        return verificationService.requestQuestionImg(sessionId);
    }

    @CrossOrigin
    @RequestMapping(value = "/answerImg",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] staticAnswerImg(@RequestParam(name="session") String sessionId) throws IOException {
        return verificationService.requestAnswerImg(sessionId);
    }

    @CrossOrigin
    @RequestMapping("/checkLocation")
    @ResponseBody
    public boolean checkLocation(@RequestParam(name="session") String sessionId, @RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CheckLocationRequest checkLocationRequest = objectMapper.readValue(requestBody, CheckLocationRequest.class);
        return verificationService.checkLocation(checkLocationRequest, sessionId);
    }

    @CrossOrigin
    @RequestMapping("/requestImg")
    @ResponseBody
    public boolean staticImg(@RequestParam(name="session") String sessionId){
        return verificationService.requestImg(sessionId);
    }
}
