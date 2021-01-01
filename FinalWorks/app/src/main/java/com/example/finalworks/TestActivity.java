package com.example.finalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.finalworks.DB.DAO;
import com.example.finalworks.entity.ActivityManager;
import com.example.finalworks.entity.HistoryScore;
import com.example.finalworks.entity.MuiltChoice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class TestActivity extends AppCompatActivity  {
    private List<MuiltChoice> allQuestions, targetQuestionList;
    private int counter = 0; //cuonters of the correct
    private static final int NUMBER = 10; //sum of MuiltChoices
    private Counter myCounter = new Counter(10 * 1000, 1 * 1000); //CountdownTimer
    private Button btnNext;
    private TestListener tl = new TestListener();
    private RadioGroup choices;
    private String answer = "A"; //Answer of MuiltChoice
    private int number = 0; // Counter of MuiltChoice
    private TextView txvTestQuestion, txvTimer;
    private RadioButton A, B, C, D;
    private MuiltChoice muiltChoice;
    private DAO dao = new DAO();
    private CountDownTimer c = new CountDownTimer(4 * 1000, 1* 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            btnNext = findViewById(R.id.btnNext);
            choices = findViewById(R.id.choices);
            txvTestQuestion = findViewById(R.id.txvTestQuestion);
            A = findViewById(R.id.rbtnA);
            B = findViewById(R.id.rbtnB);
            C = findViewById(R.id.rbtnC);
            D = findViewById(R.id.rbtnD);
            txvTimer = findViewById(R.id.txvTimer);

            choices.setOnCheckedChangeListener(tl);
            btnNext.setOnClickListener(tl);

            txvTimer.setTextColor(Color.RED);

            A.setVisibility(INVISIBLE);
            B.setVisibility(INVISIBLE);
            C.setVisibility(INVISIBLE);
            D.setVisibility(INVISIBLE);
            btnNext.setVisibility(INVISIBLE);

            txvTestQuestion.setText("准备好了吗？");

            txvTimer.setText(millisUntilFinished / 1000 +"");
        }

        public void restart(){
            this.cancel();
            this.start();
        }

        @Override
        public void onFinish() {
            initiate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        c.start();
    }


    public boolean judge(){
        if(answer.equals(muiltChoice.getAnswer())){
            counter++;
            muiltChoice.setWrong(false);
            muiltChoice.save();
            return true;
        }
        else {
            muiltChoice.setWrong(true);
            muiltChoice.save();
            return false;
        }
    }

    public int finalScore() {
        return (counter * 10);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            ActivityManager.getInstance().remove(this);
            finish();
        }else if(item.getItemId() == R.id.exitItem){
            ActivityManager.getInstance().exit();
        }else if(item.getItemId() == R.id.retestItem){
            c.cancel();
            c.start();
            myCounter.cancel();
        }
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.retest_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class Counter extends CountDownTimer{

        public Counter(long millsInFuture, long countInterval){
            super(millsInFuture, countInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txvTimer.setText(millisUntilFinished / 1000 + "");
        }


        @Override
        public void onFinish() {
            moveToNext();
            return;
        }

        /**
         * my method to restart the count down timer
         * */
        public void restart(){
            this.cancel();
            this.start();
        }
    }

    private class TestListener implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnNext){
                moveToNext();
            }
            return;
        }

        /**
         * set the member variable answer;
         * */

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = findViewById(choices.getCheckedRadioButtonId());
            if(rb.getId() == A.getId()){
                answer = "A";
            }else if(rb.getId() == B.getId()){
                answer = "B";
            }else if(rb.getId() == C.getId()){
                answer = "C";
            }else if(rb.getId() == D.getId()){
                answer = "D";
            }
        }
    }


    /**
     * Initiate the Activity at create time;
     * the caller is onCreate.
     *
     * */
    private void initiate(){
        A.setVisibility(VISIBLE);
        B.setVisibility(VISIBLE);
        C.setVisibility(VISIBLE);
        D.setVisibility(VISIBLE);
        btnNext.setVisibility(VISIBLE);

        allQuestions = getAllQuestions();
        targetQuestionList = generateQuestionList(getRandomSet(allQuestions.size(), NUMBER));
        muiltChoice = targetQuestionList.get(0);
        txvTestQuestion.setText(muiltChoice.getStrQuestion());
        A.setText(muiltChoice.getChoiceA());
        B.setText(muiltChoice.getChoiceB());
        C.setText(muiltChoice.getChoiceC());
        D.setText(muiltChoice.getChoiceD());
        answer = muiltChoice.getAnswer();
        myCounter.start();
        ActivityManager.getInstance().add(this);
        return;
    }
    /**
     * This method is used to get the exact MuiltChoiceList for the test
     *
     * */

    private List<MuiltChoice> generateQuestionList(Set<Integer> s){
        if(allQuestions.size() < s.size()){
            return allQuestions;
        }else{
            Iterator<Integer> iter = s.iterator();
            List<MuiltChoice> q = new LinkedList<>();
            while(iter.hasNext()){
                q.add(allQuestions.get(iter.next()));
                iter.remove();
            }
            allQuestions.clear();
            return q;
        }
    }

    private List<MuiltChoice> getAllQuestions(){
        return dao.getAllMuiltChoices();
    }


    /**
     * Using set to get a mathematics set of Integer
     *
     * @param range range of integer;
     * @param size size of set;
     *
     * @return set of integer
     * */
    private Set<Integer> getRandomSet(int range, int size){
        Set<Integer> set = new HashSet<>();

        do{
            set.add((int) (Math.random() * range));
        }while(set.size() < size);

        return  set;
    }


    /**
     * refresh the textview and radiobutton when the time is up,
     * or when the user press the next button
     * */
    private void refreshQuestion(MuiltChoice q){
        q = targetQuestionList.get(number);
        txvTestQuestion.setText(q.getStrQuestion());
        A.setText(q.getChoiceA());
        B.setText(q.getChoiceB());
        C.setText(q.getChoiceC());
        D.setText(q.getChoiceD());
        myCounter.restart();
    }


    /**
     *  when the test is finished, make choices and textview of count down time to a state that not visible
     * */
    public void disvisible(){
        A.setVisibility(INVISIBLE);
        B.setVisibility(INVISIBLE);
        C.setVisibility(INVISIBLE);
        D.setVisibility(INVISIBLE);
        txvTimer.setVisibility(INVISIBLE);


        return;
    }

    public void moveToNext(){
        int i = targetQuestionList.size();
        if(number >= i-1){
            if(number == i-1){
                number++;
                judge();
            }else if(number==100){
                return;
            }else{
                number=100;
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:SS");
                String time = sdf.format(date);
                HistoryScore historyScore = new HistoryScore(finalScore(), time);
                txvTestQuestion.setText("测试结束\n" +
                        "你的最终成绩是"+
                        finalScore());
                historyScore.save();
                disvisible();
            }
        }else if(number < i){
            number++;
            judge();
            muiltChoice = targetQuestionList.get(number);
            refreshQuestion(muiltChoice);
        }
    }
}