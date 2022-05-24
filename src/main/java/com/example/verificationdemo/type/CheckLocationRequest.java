package com.example.verificationdemo.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckLocationRequest {
    @JsonProperty
    private double moveX;

    public double getMoveX(){
        return this.moveX;
    }
}
