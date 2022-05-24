package com.example.verificationdemo.Service.impl;

import com.example.verificationdemo.Service.VerificationService;
import com.example.verificationdemo.type.AnswerSessionMap;
import com.example.verificationdemo.type.CheckLocationRequest;
import com.example.verificationdemo.utils.IGlobalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import java.io.*;

@Service("VerificationService")
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private IGlobalCache<String, AnswerSessionMap> globalCache;

    private File getRandomFile(String src){
        File folder = new File(src);
        File[] tempList = folder.listFiles();

        int randomIndex = (int)(Math.random() * tempList.length);
        File randomFile = tempList[randomIndex];
        return randomFile;
    }

    private double getAnswerLocation(File img, int answer){
        String fileName = getFileName(img);
        double location = fileName.indexOf(String.valueOf(answer))  * 1.0 / (fileName.length() - 1);
        return location;
    }

    private double getAnswerLocation(File question, File answer){
        String questionString = getFileName(question);
        int answerInteger = getQuestionAnswer(questionString);

        return getAnswerLocation(answer, answerInteger/10);
    }

    private String getFileName(File file){
        String name = file.getName();
        return name.substring(0, name.lastIndexOf('.'));
    }

    private int getQuestionAnswer(String question){
        String[] operands = question.split("=|\\+");
        return Integer.parseInt(operands[0]) + Integer.parseInt(operands[1]);
    }

    @Override
    public boolean checkLocation(CheckLocationRequest request, String sessionId) {
        AnswerSessionMap answerSessionMap = globalCache.get(sessionId);
        double answer = answerSessionMap.getAnswer();
        double userAnswer = request.getMoveX();

        return Math.abs(userAnswer - answer) < 0.03;
    }

    @Override
    public boolean requestImg(String sessionId) {
        try{
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            File question = getRandomFile(path + "static/image/Question");
            File answer = getRandomFile(path + "static/image/Answer");

            String questionPath = question.getName();
            String answerPath = answer.getName();

            double answerLocation = getAnswerLocation(question, answer);

            AnswerSessionMap answerSessionMap = new AnswerSessionMap(answerLocation, questionPath, answerPath);

            globalCache.set(sessionId, answerSessionMap, 300);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private byte[] getImgContent(String path) throws IOException {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    @Override
    public byte[] requestQuestionImg(String sessionId) throws IOException {
        AnswerSessionMap answerSessionMap = globalCache.get(sessionId);
        String fileName = answerSessionMap.getQuestionPath();
        answerSessionMap.setQuestionPath(null);
        globalCache.set(sessionId, answerSessionMap);

        if (fileName != null){
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            return getImgContent(path + "static/image/Question/" + fileName);
        }else{
            return new byte[0];
        }

    }

    @Override
    public byte[] requestAnswerImg(String sessionId) throws IOException {
        AnswerSessionMap answerSessionMap = globalCache.get(sessionId);
        String fileName = answerSessionMap.getAnswerPath();
        answerSessionMap.setAnswerPath(null);
        globalCache.set(sessionId, answerSessionMap);

        if (fileName != null){
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            return getImgContent(path + "static/image/Answer/" + fileName);
        }else{
            return new byte[0];
        }

    }
}
