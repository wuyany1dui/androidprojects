package com.example.finalworks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalworks.DB.DAO;
import com.example.finalworks.adapter.MainAdapter;
import com.example.finalworks.entity.ActivityManager;
import com.example.finalworks.entity.MuiltChoice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private Button btnAddQuestion, btnStartTest, btnHistoryScore, btnStudyMode;
    private MainListenner m;
    private Intent ittAddQuestion, ittHistoryScore, ittTest , ittStudyMode;
    private ListView listView;
    private List<MuiltChoice> muiltChoiceList = new LinkedList<>();
    private MainAdapter ma;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAO(this);
        initiate();
    }


    /**
     * Listener of every element in this activity;
     *
     * */
    public class MainListenner implements View.OnClickListener, AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

        private String questionStr;
        private Context context; // Context: MainActivity

        @Override
        public void onClick(View v){

            if(v.getId() == R.id.btnAddQuestion){
                startActivityForResult(ittAddQuestion, 100);
            }else if(v.getId() == R.id.btnHistory){
                startActivity(ittHistoryScore);
            }else if(v.getId() == R.id.btnStart){
                startActivity(ittTest);
            }else if(v.getId() == R.id.btnStudyMode){
                startActivity(ittStudyMode);
            }

            return;
        }

        /**
         * show the detail of the MuilChoice
         *
         * */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            MuiltChoice q = muiltChoiceList.get(position);
            builder.setTitle(q.getStrQuestion());
            builder.setMessage("" + q.getChoiceA()
                    + "\n" + q.getChoiceB()
                    + "\n" + q.getChoiceA()
                    + "\n" + q.getChoiceD()
                    + "\n答案：" + q.getAnswer());
            builder.setCancelable(true);
            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        /**
         * Alternate constructor that can be provided a context of MainActivity
         * */
        public MainListenner(Context context){
            this.context = context;
        }



        /**
         * Delete or not the muiltchoice from the database after long click at the item
         *
         * But it doesn't work!!!!!
         * */
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = view.findViewById(R.id.txvStrQuestion);
            questionStr = textView.getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("你确定要删除吗？");
            builder.setMessage("该操作不可逆");
            builder.setPositiveButton("确定", this);
            builder.setNegativeButton("取消", this);


            Dialog dialog = builder.create();
            dialog.show();


            return false;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == Dialog.BUTTON_POSITIVE){
                dao.deleteByQuestion(questionStr);
                return;
            }else if(which == Dialog.BUTTON_POSITIVE){
                 dialog.dismiss();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.about_author_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Jump to the AboutAuthorActivity
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu){
        if(menu.getItemId() == R.id.itemAboutAuthor){
            Intent intent = new Intent(this, AboutAuthorActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menu);
    }

    private void initiate(){
        ittStudyMode = new Intent(this, StudyActivity.class);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnStartTest = findViewById(R.id.btnStart);
        btnHistoryScore = findViewById(R.id.btnHistory);
        listView = findViewById(R.id.listView);
        btnStudyMode = findViewById(R.id.btnStudyMode);

        m = new MainListenner(this);

        btnAddQuestion.setOnClickListener(m);
        btnHistoryScore.setOnClickListener(m);
        btnStartTest.setOnClickListener(m);
        btnStudyMode.setOnClickListener(m);
        muiltChoiceList = dao.getAllMuiltChoices();

        if(muiltChoiceList.isEmpty()){
            dao.generateDatabase();
            muiltChoiceList = dao.getAllMuiltChoices();
        }

        ma = new MainAdapter(muiltChoiceList, this);

        listView.setAdapter(ma);
        listView.setOnItemClickListener(m);
        ittAddQuestion = new Intent(this, AddNewQuestionAcitivity.class);
        ittHistoryScore = new Intent(this, HistoryScoreActivity.class);
        ittTest = new Intent(this, TestActivity.class);


        ActivityManager.getInstance().add(this);

    }


    /**
     * Refresh the database after add a new MuiltChoice to the database
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent i){
        super.onActivityResult(requestCode, resultCode, i);

        muiltChoiceList = dao.getAllMuiltChoices();
        ma = new MainAdapter(muiltChoiceList, this);
        listView.setAdapter(ma);
    }


}