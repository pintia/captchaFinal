package cn.patest.captchafinal.type;

import java.io.Serializable;

public class AnswerSessionMap implements Serializable {
    private double answer;
    private String questionPath;
    private String answerPath;

    public AnswerSessionMap(double answer, String questionPath, String answerPath){
        this.answer = answer;
        this.questionPath = questionPath;
        this.answerPath = answerPath;
    }

    public double getAnswer() {
        return answer;
    }

    public String getQuestionPath(){
        return questionPath;
    }

    public String getAnswerPath(){
        return answerPath;
    }

    public void setQuestionPath(String questionPath){
        this.questionPath = questionPath;
    }

    public void setAnswerPath(String answerPath){
        this.answerPath = answerPath;
    }
}
