package com.example.verificationdemo.Service;

import com.example.verificationdemo.type.CheckLocationRequest;

import java.io.IOException;

public interface VerificationService {
    String getResources(String sessionId) throws IOException;

    boolean checkLocation(CheckLocationRequest request, String sessionId);
}
