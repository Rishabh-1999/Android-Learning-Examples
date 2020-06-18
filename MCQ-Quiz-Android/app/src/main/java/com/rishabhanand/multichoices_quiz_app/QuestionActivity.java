package com.rishabhanand.multichoices_quiz_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.rishabhanand.multichoices_quiz_app.Adapter.AnswerSheetAdapter;
import com.rishabhanand.multichoices_quiz_app.Adapter.QuestionFragmentAdapter;
import com.rishabhanand.multichoices_quiz_app.Common.Common;
import com.rishabhanand.multichoices_quiz_app.DBHelper.DBHelper;
import com.rishabhanand.multichoices_quiz_app.DBHelper.OnlineDBHelper;
import com.rishabhanand.multichoices_quiz_app.Interface.MyCallback;
import com.rishabhanand.multichoices_quiz_app.Model.CurrentQuestion;
import com.rishabhanand.multichoices_quiz_app.Model.Question;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int CODE_GET_RESULT = 9999;
    int time_play = Common.TOTAL_TIME;
    boolean isAnswerModeView = false;

    TextView txt_right_answer,txt_timer,txt_wrong_answer;

    RecyclerView answer_sheet_view;
    AnswerSheetAdapter answerSheetAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onDestroy() {
        if(Common.countDownTimer!=null)
            Common.countDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selectedCategory.getName());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //first, we need take question from DB
        takeQuestion();


    }

    private void finishGame() {

        int position=viewPager.getCurrentItem();
        QuestionFragment questionFragment = Common.fragementsList.get(position);
        CurrentQuestion question_state = questionFragment.getSelectedQuestion();
        Common.answerSheetList.set(position,question_state);            // set question answer for answer sheet
        answerSheetAdapter.notifyDataSetChanged();                      // change color in answer sheet


        countCorrectAnswer();

        txt_right_answer.setText(new StringBuilder(String.format("%d",Common.right_answer_count))
                .append("/")
                .append(String.format("%d",Common.questionList.size())).toString());



        if(question_state.getType() == Common.ANSWER_TYPE.NO_ANSWER)
        {
            questionFragment.showCurrentAnswer();
            questionFragment.disableAnswer();
        }

        //we will navigate to new result activity here
        Intent intent =new Intent(QuestionActivity.this,ResultActivity.class);
        Common.timer = Common.TOTAL_TIME - time_play;
        Common.no_answer_count = Common.questionList.size() - (Common.wrong_answer_count+Common.right_answer_count);
        Common.data_question = new StringBuilder(new Gson().toJson(Common.answerSheetList));

        startActivityForResult(intent,CODE_GET_RESULT);

    }

    private void countCorrectAnswer() {

        //result variable
        Common.right_answer_count = Common.wrong_answer_count = 0;
        for(CurrentQuestion item:Common.answerSheetList)
            if(item.getType() == Common.ANSWER_TYPE.RIGHT_ANSWER)
                Common.right_answer_count++;
            else if(item.getType() == Common.ANSWER_TYPE.WRONG_ANSWER)
                Common.wrong_answer_count++;

    }

    private void genFragmentList() {
        for(int i=0;i<Common.questionList.size();i++)
        {
            Bundle bundle=new Bundle();
            bundle.putInt("index",1);
            QuestionFragment fragment =new QuestionFragment();
            fragment.setArguments(bundle);

            Common.fragementsList.add(fragment);
        }
    }

    private void countTimer() {
        if(Common.countDownTimer == null)
        {
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME,1000) {
                @Override
                public void onTick(long l) {
                    txt_timer.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(l),
                            TimeUnit.MILLISECONDS.toSeconds(l) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
                    time_play-=1000;
                }

                @Override
                public void onFinish() {
                    //finish game
                }
            }.start();
        }
        else
        {
            Common.countDownTimer.cancel();
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME,1000) {
                @Override
                public void onTick(long l) {
                    txt_timer.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(l),
                            TimeUnit.MILLISECONDS.toSeconds(l) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
                    time_play-=1000;
                }

                @Override
                public void onFinish() {
                    //finish game
                }
            }.start();
        }
    }

    private void takeQuestion() {

        if(!Common.isOnlineMode) {

            Common.questionList = DBHelper.getInstance(this).getQuestionsByCategory(Common.selectedCategory.getId());
            if (Common.questionList.size() == 0) {
                // if no question
                new MaterialStyledDialog.Builder(this)
                        .setTitle("Opps !")
                        .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                        .setDescription("We  don't have any question in this" + Common.selectedCategory.getName() + " category")
                        .setPositiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).show();
            } else {
                if (Common.answerSheetList.size() > 0)
                    Common.answerSheetList.clear();
                //generate answer item from question
                //30  question = 30 answer sheet item
                //1 question = 1=answer answer sheet item
                for (int i = 0; i < Common.questionList.size(); i++) {
                    //because we need take index of question in list , so we will use for i
                    Common.answerSheetList.add(new CurrentQuestion(i, Common.ANSWER_TYPE.NO_ANSWER));// default all answer is no answer

                }
            }
            setupQuestion();
        }
        else
        {
            OnlineDBHelper.getInstance(this, FirebaseDatabase.getInstance())
                    .readData(new MyCallback() {
                        @Override
                        public void setQuestionList(List<Question> questionList) {

                            Common.questionList.clear();
                            Common.questionList = questionList;

                            if (Common.questionList.size() == 0) {
                                // if no question
                                new MaterialStyledDialog.Builder(QuestionActivity.this)
                                        .setTitle("Opps !")
                                        .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                                        .setDescription("We  don't have any question in this" + Common.selectedCategory.getName() + " category")
                                        .setPositiveText("OK")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        }).show();
                            } else {
                                if (Common.answerSheetList.size() > 0)
                                    Common.answerSheetList.clear();
                                //generate answer item from question
                                //30  question = 30 answer sheet item
                                //1 question = 1=answer answer sheet item
                                for (int i = 0; i < Common.questionList.size(); i++) {
                                    //because we need take index of question in list , so we will use for i
                                    Common.answerSheetList.add(new CurrentQuestion(i, Common.ANSWER_TYPE.NO_ANSWER));// default all answer is no answer

                                }
                            }
                            setupQuestion();
                        }
                    },Common.selectedCategory.getName().replace(" ","").replace("/","_"));
        }
    }

    private void setupQuestion() {
        if(Common.questionList.size()>0) {

            //show textview right answer and textview timer
            txt_right_answer =(TextView)findViewById(R.id.txt_questions_right);
            txt_timer=(TextView)findViewById(R.id.txt_timer);

            txt_timer.setVisibility(View.VISIBLE);
            txt_right_answer.setVisibility(View.VISIBLE);

            txt_right_answer.setText(new StringBuilder(String.format("%d/%d",Common.right_answer_count,Common.questionList.size())));

            countTimer();

            //view
            answer_sheet_view = (RecyclerView) findViewById(R.id.grid_answer);
            answer_sheet_view.setHasFixedSize(true);
            if (Common.questionList.size() >= 5)  // if question list have size > 5 , we will seperate, we will seperate 2 rows
                answer_sheet_view.setLayoutManager(new GridLayoutManager(this, Common.questionList.size() / 2));
            answerSheetAdapter = new AnswerSheetAdapter(this, Common.answerSheetList);
            answer_sheet_view.setAdapter(answerSheetAdapter);

            viewPager=(ViewPager)findViewById(R.id.viewpager);
            tabLayout=(TabLayout)findViewById(R.id.sliding_tabs);

            genFragmentList();

            QuestionFragmentAdapter questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager(),
                    this,
                    Common.fragementsList);
            viewPager.setAdapter(questionFragmentAdapter);

            tabLayout.setupWithViewPager(viewPager);

            //event
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                int SCROLLING_RIGHT = 0;
                int SCROLLING_LEFT  = 1;
                int SCROLLING_UNDETERMINED = 2;

                int currentScrollingDirection = 2;

                private void setScollingDirection(float positionOffset)
                {
                    if(1 - positionOffset >= 0.5)
                        this.currentScrollingDirection=SCROLLING_RIGHT;
                    else if(1 - positionOffset <= 0.5)
                        this.currentScrollingDirection=SCROLLING_LEFT;
                }

                private boolean isScollDirectionUndetermine()
                {
                    return currentScrollingDirection == SCROLLING_UNDETERMINED;
                }

                private boolean isScrollingRight()
                {
                    return currentScrollingDirection == SCROLLING_RIGHT;
                }

                private boolean isScrollingLeft()
                {
                    return  currentScrollingDirection == SCROLLING_LEFT;
                }

                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    if(isScollDirectionUndetermine())
                        setScollingDirection(v);
                }

                @Override
                public void onPageSelected(int i) {

                    QuestionFragment questionFragment;
                    int position = 0;
                    if(i>0)
                    {
                        if(isScrollingRight())
                        {
                            //if user scroll to right , get previous fragment to calculate result
                            questionFragment = Common.fragementsList.get(i - 1);
                            position = i - 1;
                        }
                        else if(isScrollingRight())
                        {
                            //if user scroll to left , get next fragment to calculate result
                            questionFragment = Common.fragementsList.get(i + 1);
                            position = i + 1;
                        }
                        else
                        {
                            questionFragment = Common.fragementsList.get(position);
                        }
                    }
                    else
                    {
                        questionFragment = Common.fragementsList.get(0);
                        position = 0;
                    }

                    //if you want to show correct answer , just call function here
                    CurrentQuestion question_state = questionFragment.getSelectedQuestion();
                    Common.answerSheetList.set(position,question_state);            // set question answer for answer sheet
                    answerSheetAdapter.notifyDataSetChanged();                      // change color in answer sheet

                    countCorrectAnswer();

                    txt_right_answer.setText(new StringBuilder(String.format("%d",Common.right_answer_count))
                            .append("/")
                            .append(String.format("%d",Common.questionList.size())).toString());
                    txt_wrong_answer.setText(String.valueOf(Common.wrong_answer_count));

                    if(question_state.getType() == Common.ANSWER_TYPE.NO_ANSWER)
                    {
                        questionFragment.showCurrentAnswer();
                        questionFragment.disableAnswer();
                    }

                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if(i == ViewPager.SCROLL_STATE_IDLE)
                        this.currentScrollingDirection = SCROLLING_UNDETERMINED;
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_wrong_answer);
        ConstraintLayout constraintLayout = (ConstraintLayout)item.getActionView();
        txt_wrong_answer=(TextView)constraintLayout.findViewById(R.id.txt_wrong_answer);
        txt_wrong_answer.setText(String.valueOf(0));
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_finish_game) {
            if(!isAnswerModeView)
            {
                new MaterialStyledDialog.Builder(this)
                        .setTitle("Finish ?")
                        .setIcon(R.drawable.ic_mood_black_24dp)
                        .setDescription("Do you really want to finish ?")
                        .setNegativeText("No")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                finishGame();
                            }
                        }).show();
            }
            else
                finishGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_GET_RESULT)
        {
            if(requestCode == Activity.RESULT_OK)
            {
                String action = data.getStringExtra("action");
                if(action == null || TextUtils.isEmpty(action))
                {
                    int questionNum = data.getIntExtra(Common.KEY_BACK_FROM_RESULT,-1);
                    viewPager.setCurrentItem(questionNum);

                    isAnswerModeView = true;
                    Common.countDownTimer.cancel();

                    txt_wrong_answer.setVisibility(View.GONE);
                    txt_right_answer.setVisibility(View.GONE);
                    txt_timer.setVisibility(View.GONE);
                }
                else
                {
                    if(action.equals("viewquizanswer"))
                    {
                        viewPager.setCurrentItem(0);

                        isAnswerModeView=true;
                        Common.countDownTimer.cancel();

                        txt_wrong_answer.setVisibility(View.GONE);
                        txt_right_answer.setVisibility(View.GONE);
                        txt_timer.setVisibility(View.GONE);

                        for(int i=0;i<Common.fragementsList.size();i++)
                        {
                            Common.fragementsList.get(i).showCurrentAnswer();
                            Common.fragementsList.get(i).disableAnswer();
                        }
                    }
                    else if(action.equals("doitagain"))
                    {
                        viewPager.setCurrentItem(0);

                        isAnswerModeView=false;
                        countTimer();

                        txt_wrong_answer.setVisibility(View.VISIBLE);
                        txt_right_answer.setVisibility(View.VISIBLE);
                        txt_timer.setVisibility(View.VISIBLE);

                        for (CurrentQuestion item:Common.answerSheetList)
                            item.setType(Common.ANSWER_TYPE.NO_ANSWER); //reset all question
                        answerSheetAdapter.notifyDataSetChanged();
                        //answer

                        for(int i=0;i<Common.fragementsList.size();i++)
                            Common.fragementsList.get(i).resetQuestion();
                    }
                }
            }
        }
    }
}
