package com.rishabhanand.multichoices_quiz_app.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.rishabhanand.multichoices_quiz_app.Model.Category;
import com.rishabhanand.multichoices_quiz_app.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper {

    public static final String DB_NAME =  "EDMTQuiz2019.db";
    public static final int DB_VER =  1;

    private static DBHelper instance;

    public  static synchronized DBHelper getInstance(Context context)
    {
        if(instance == null)
            instance = new DBHelper(context);
        return  instance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /*
    GET ALL CATEEGORIES FROM DB
     */
    public List<Category> getAllCategory()
    {
        SQLiteDatabase db=instance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Category;",null);
        List<Category> categories = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Category category= new Category(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getString(cursor.getColumnIndex("Image")));
                categories.add(category);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return categories;
    }

    /*
        GET 30 QUESTIONS FROM DB BY CATEGORY
     */
    public  List<Question> getQuestionsByCategory(int category)
    {
        SQLiteDatabase db=instance.getWritableDatabase();

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM Question WHERE CategoryID = %d ORDER BY RANDOM() LIMIT 30",category),null);
        List<Question> questions = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Question question= new Question(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("QuestionText")),
                        cursor.getString(cursor.getColumnIndex("QuestionImage")),
                        cursor.getString(cursor.getColumnIndex("AnswerA")),
                        cursor.getString(cursor.getColumnIndex("AnswerB")),
                        cursor.getString(cursor.getColumnIndex("AnswerC")),
                        cursor.getString(cursor.getColumnIndex("AnswerD")),
                        cursor.getString(cursor.getColumnIndex("CorrectAnswer")),
                        cursor.getInt(cursor.getColumnIndex("IsImageQuestion"))==0?Boolean.FALSE:Boolean.TRUE,
                        cursor.getInt(cursor.getColumnIndex("CategoryID"))
                        );
                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return questions;
    }
}
