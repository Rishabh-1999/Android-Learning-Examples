package com.rishabhanand.multichoices_quiz_app.Model;

public class Question {
    private  int id;
    private String questionText,questionImage,answerA,answerB,answerC,answerD,correctAnswer;
    private boolean isImageQuestion;
    private int categoryid;

    public Question()
    {}

    public Question(int id, String questionText, String questionImage, String answerA, String answerB, String answerC, String answerD, String correctAnswer, boolean isImageQuestion, int categoryid) {
        this.id = id;
        this.questionText = questionText;
        this.questionImage = questionImage;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.isImageQuestion = isImageQuestion;
        this.categoryid = categoryid;
    }

    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean getIsImageQuestion() {
        return isImageQuestion;
    }

    public int getCategoryid() {
        return categoryid;
    }
}
