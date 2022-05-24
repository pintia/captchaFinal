package com.example.verificationdemo.Service;

import com.example.verificationdemo.type.CheckLocationRequest;

import java.io.IOException;

public interface VerificationService {

    boolean checkLocation(CheckLocationRequest request, String sessionId);

    boolean requestImg(String sessionId);

    byte[] requestQuestionImg(String sessionId) throws IOException;

    byte[] requestAnswerImg(String sessionId) throws IOException;
}
