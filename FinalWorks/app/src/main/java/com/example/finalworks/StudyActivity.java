package com.example.finalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalworks.DB.DAO;
import com.example.finalworks.entity.ActivityManager;
import com.example.finalworks.entity.MuiltChoice;

import java.util.List;

public class StudyActivity extends AppCompatActivity {

    private List<MuiltChoice> muiltChoiceList;
    private TextView txvQuestion, txvChoiceList, txvAnswer;
    private MuiltChoice question;
    private int counter = 0; //题目计数器
    private Button btnPre, btnNext;
    private StudyListener sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initiate();
    }

    public void initiate(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        muiltChoiceList = new DAO().getAllMuiltChoices();
        txvQuestion = findViewById(R.id.txvStudyQuestion);
        txvAnswer = findViewById(R.id.txvStudyAnswer);
        txvChoiceList = findViewById(R.id.txvStudyChoiceList);
        btnPre = findViewById(R.id.btnStudyPre);
        btnNext = findViewById(R.id.btnStudyNext);
        sl = new StudyListener(this);

        btnNext.setOnClickListener(sl);
        btnPre.setOnClickListener(sl);


        question = muiltChoiceList.get(counter);
        txvQuestion.setText(question.getStrQuestion());
        txvChoiceList.setText(""+question.getChoiceA()
                + "\n" + question.getChoiceB()
                + "\n" + question.getChoiceC()
                + "\n" + question.getChoiceD());
        txvAnswer.setText(question.getAnswer());
        ActivityManager.getInstance().add(this);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            ActivityManager.getInstance().remove(this);
            finish();
        }else if(item.getItemId() == R.id.exitItem){
            ActivityManager.getInstance().exit();
        }else if(item.getItemId() == R.id.backToHomeItem){
            ActivityManager.getInstance().remove(this);
            finish();
        }else if(item.getItemId() == R.id.wrongTests){
            Intent itt = new Intent(this, WrongQuestionActivity.class);
            startActivity(itt);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.item_to_home_items, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Listener of every elements in this activity
     *
     * */

    private class StudyListener implements View.OnClickListener{
        Context context;


        /**
         * Move to the next question or move to previous one;
         * */
        @Override
        public void onClick(View v) {
            int size = muiltChoiceList.size();
            if(v.getId() == btnPre.getId()){
                if(counter > 0){
                    counter--;
                    question = muiltChoiceList.get(counter);
                    refresh(question);
                }
            }else if(v.getId() == btnNext.getId()){
                if(counter < size - 1){
                    counter++;
                    question = muiltChoiceList.get(counter);
                    refresh(question);
                }
            }
            if(counter >= size - 1){
                Toast.makeText(context, "已经是最后一个啦！", Toast.LENGTH_SHORT).show();
            }if(counter <= 0){
              Toast.makeText(context, "已经是第一个啦！", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        /**
         * Alternate constructor can be provided context of StudyActivity
         *
         * */

        public StudyListener(Context context){
            this.context = context;
        }

    }

    /**
     * Refresh the detail of MuiltChoice when move to the next or previous one
     * */
    public void refresh(MuiltChoice question){
        txvQuestion.setText(question.getStrQuestion());
        txvQuestion.setText(question.getStrQuestion());
        txvChoiceList.setText(""+question.getChoiceA()
                + "\n" + question.getChoiceB()
                + "\n" + question.getChoiceC()
                + "\n" + question.getChoiceD());
        txvAnswer.setText("答案"+question.getAnswer());
    }


}