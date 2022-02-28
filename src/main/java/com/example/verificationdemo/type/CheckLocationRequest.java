package com.example.verificationdemo.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckLocationRequest {
    @JsonProperty
    private int moveX;

    @JsonProperty
    private int answerIndex;

    public int getMoveX(){
        return this.moveX;
    }

    public int getAnswerIndex(){
        return this.answerIndex;
    }
}
