package com.example.finalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.finalworks.DB.DAO;
import com.example.finalworks.adapter.HistoryScoreAdapter;
import com.example.finalworks.adapter.MainAdapter;
import com.example.finalworks.entity.ActivityManager;
import com.example.finalworks.entity.HistoryScore;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HistoryScoreActivity extends AppCompatActivity {
    private List<HistoryScore> historyScoreList = new LinkedList<>();
    private ListView scoreList;
    private HistoryScoreAdapter hsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_score);
        initiate();
    }

    @Override
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



    public void initiate(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        historyScoreList = new DAO().getHistoryScore();
        scoreList = findViewById(R.id.scoreList);
        hsa = new HistoryScoreAdapter(this,historyScoreList);

        ActivityManager.getInstance().add(this);
        scoreList.setAdapter(hsa);
    }

}