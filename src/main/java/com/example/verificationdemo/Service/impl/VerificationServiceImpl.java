package com.example.verificationdemo.Service.impl;

import com.example.imageUtils.AnswerCreateUtil;
import com.example.verificationdemo.FileTemplates.FileTemplate;
import com.example.verificationdemo.Service.VerificationService;
import com.example.verificationdemo.type.AnswerSessionMap;
import com.example.verificationdemo.type.CheckLocationRequest;
import com.example.verificationdemo.utils.IGlobalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import sun.misc.BASE64Encoder;

import javax.script.*;
import java.io.*;
import java.nio.charset.Charset;

@Service("VerificationService")
public class VerificationServiceImpl implements VerificationService {
    //TODO: session存不同上报请求的答案数据，五分钟失效
    double imgWidth = AnswerCreateUtil.answerWidth * 0.9;

    @Autowired
    private IGlobalCache<String, AnswerSessionMap> globalCache;

    private File getRandomFile(String src){
        File folder = new File(src);
        File[] tempList = folder.listFiles();

        int randomIndex = (int)(Math.random() * tempList.length);
        File randomFile = tempList[randomIndex];
        return randomFile;
    }

    private String getBase64File(File file) throws IOException {
        InputStream randomFileIS = new FileInputStream(file);
        byte[] data = new byte[randomFileIS.available()];
        randomFileIS.read(data);
        randomFileIS.close();
        return new BASE64Encoder().encode(data);
    }

    private double getAnswerLocation(File img, int answer){
        String fileName = getFileName(img);
        double location = imgWidth * fileName.indexOf(String.valueOf(answer)) / (fileName.length() - 1);
        return location;
    }

    private double answerLocationReCalculate(double answer, double scaleX){
        if (answer / imgWidth <= 1 / (1 + scaleX)){
            return answer * scaleX;
        }else {
            return imgWidth * scaleX / (1 + scaleX) + (answer - imgWidth / (1 + scaleX)) / scaleX;
        }
    }

    private String getFileName(File file){
        String name = file.getName();
        return name.substring(0, name.lastIndexOf('.'));
    }

    private int getQuestionAnswer(String question){
        String[] operands = question.split("=|\\+");
        return Integer.parseInt(operands[0]) + Integer.parseInt(operands[1]);
    }

    private String obfuscator(String sourceString) throws IOException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String result="";
        String cmd = "/Users/bytedance/.nvm/versions/node/v14.18.1/bin/node " + path + "static/js/obfuscator.js " + path + "static/js/captcha.js";

        Process ps = Runtime.getRuntime().exec(cmd);

        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("GBK")));
        String line = null;
        while ((line = br.readLine()) != null) {
            result+=line+"\n";
        }

        br.close();
        ps.waitFor();

        return result;
    }

    @Override
    public String getResources(String sessionId) throws IOException, ScriptException, NoSuchMethodException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        File question = getRandomFile(path + "static/image/Question");
        File answer1 = getRandomFile(path + "static/image/Answer");
        File answer2 = getRandomFile(path + "static/image/Answer");

        String questionString = getFileName(question);
        int answerInteger = getQuestionAnswer(questionString);


        String jsTemplate = FileTemplate.jsFile;
        String htmlTemplate = FileTemplate.htmlFile;

        double randomScale = Math.random() * 1.5 + 0.5;
        double answer1Location = getAnswerLocation(answer1, answerInteger/10);
        double answer2Location = getAnswerLocation(answer2, answerInteger%10);
        AnswerSessionMap answerSessionMap = new AnswerSessionMap(answer1Location, answer2Location, randomScale);

        globalCache.set(sessionId, answerSessionMap, 300);
        String url = "http://localhost:8080";
        String jsFile = String.format(jsTemplate, randomScale, url);

        String tempJsFile = obfuscator(jsFile);
        String htmlFile = String.format(htmlTemplate, getBase64File(question), getBase64File(answer1), getBase64File(answer2), tempJsFile);
        return htmlFile;
    }

    @Override
    public boolean checkLocation(CheckLocationRequest request, String sessionId) {
        int index = request.getAnswerIndex();
        AnswerSessionMap answerSessionMap = globalCache.get(sessionId);
        double answer = index == 1 ? answerSessionMap.getAnswer2() : answerSessionMap.getAnswer1();
        double userAnswer = answerLocationReCalculate(request.getMoveX(), answerSessionMap.getScale());

        return Math.abs(userAnswer - answer) < 10;
    }
}
