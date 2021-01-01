package com.example.finalworks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalworks.DB.DAO;
import com.example.finalworks.adapter.MainAdapter;
import com.example.finalworks.entity.MuiltChoice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



/**
 * Activity to show the MuiltChoice has been wrong chosen while testing;
 *
 * */
public class WrongQuestionActivity extends AppCompatActivity {

    private List<MuiltChoice> allMuiltChoice, WrongMuiltChoice;
    private DAO dao = new DAO(this);
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question);
        initiate();
    }

    public void initiate(){
        allMuiltChoice = dao.getAllMuiltChoices();
        getWrongMuiltChoice();
        listView = findViewById(R.id.listViewWrongs);
        listView.setAdapter(new MainAdapter(WrongMuiltChoice, this));
        listView.setOnItemClickListener(new Listener());
    }


    public void getWrongMuiltChoice(){
        WrongMuiltChoice = new LinkedList<>();
        Iterator<MuiltChoice> iter = allMuiltChoice.iterator();
        while(iter.hasNext()){
            MuiltChoice m = iter.next();
            if(m.isWrong()){
                WrongMuiltChoice.add(m);
            }
        }
    }

    void showQuestion(MuiltChoice m){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(m.getStrQuestion());
        builder.setMessage("" + m.getChoiceA()
                + "\n" + m.getChoiceB()
                + "\n" + m.getChoiceA()
                + "\n" + m.getChoiceD()
                + "\n答案：" + m.getAnswer());
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
    private class Listener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MuiltChoice m = WrongMuiltChoice.get(position);
            showQuestion(m);
        }
    }
}