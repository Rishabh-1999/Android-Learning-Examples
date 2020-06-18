package com.rishabhanand.multichoices_quiz_app.Common;

import android.content.Intent;
import android.os.CountDownTimer;

import com.rishabhanand.multichoices_quiz_app.Model.Category;
import com.rishabhanand.multichoices_quiz_app.Model.CurrentQuestion;
import com.rishabhanand.multichoices_quiz_app.Model.Question;
import com.rishabhanand.multichoices_quiz_app.QuestionFragment;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Common {

    public static final int TOTAL_TIME = 20*60*1000 ;  //20 min
    public static final String KEY_GO_TO_QUESTION = "GO_TO_QUESTION";
    public static final String KEY_BACK_FROM_RESULT = "BACK_FROM_RESULT";
    public static final String KEY_SAVE_ONLINE_MODE = "ONLINE_MODE";
    public  static List<Question> questionList = new ArrayList<>();
    public static Category selectedCategory = new Category();
    public static List<CurrentQuestion> answerSheetList = new ArrayList<>();
    public static List<CurrentQuestion> answerSheetListFiltered = new ArrayList<>();
    public static CountDownTimer countDownTimer;
    public static int right_answer_count = 0;
    public static int wrong_answer_count = 0;
    public static ArrayList<QuestionFragment> fragementsList = new ArrayList<>();
    public static TreeSet<String> selected_values = new TreeSet<>();
    public static int timer = 0;
    public static int no_answer_count = 0;
    public static StringBuilder data_question=new StringBuilder();
    public static boolean isOnlineMode  =false;

    public enum ANSWER_TYPE {
        NO_ANSWER,
        WRONG_ANSWER,
        RIGHT_ANSWER
    }
}
