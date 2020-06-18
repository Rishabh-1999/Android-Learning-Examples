package com.rishabhanand.multichoices_quiz_app.Interface;

import com.rishabhanand.multichoices_quiz_app.Model.CurrentQuestion;

public interface IQuestion {

    CurrentQuestion getSelectedQuestion();              //get selected answer from user select
    void showCurrentAnswer();                           // bold correct answer text
    void disableAnswer();                               // disable all check box
    void resetQuestion();                               // reset all set function on question

}
