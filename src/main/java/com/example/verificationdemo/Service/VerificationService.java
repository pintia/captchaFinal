package com.example.verificationdemo.Service;

import com.example.verificationdemo.type.CheckLocationRequest;

import javax.script.ScriptException;
import java.io.IOException;

public interface VerificationService {
    String getResources(String sessionId) throws IOException, ScriptException, NoSuchMethodException, InterruptedException;

    boolean checkLocation(CheckLocationRequest request, String sessionId);
}
