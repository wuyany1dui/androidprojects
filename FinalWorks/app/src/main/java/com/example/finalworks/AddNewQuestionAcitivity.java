package com.example.finalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalworks.DB.DAO;
import com.example.finalworks.entity.ActivityManager;
import com.example.finalworks.entity.MuiltChoice;
import com.example.finalworks.exception.IllegalInputException;

public class AddNewQuestionAcitivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText edtQuestion, edtA, edtB, edtC, edtD;
    private Button btnAdd;
    private Spinner spinner;
    private Character[] answers;
    private String answer;
    private ArrayAdapter<Character> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_question_acitivity);
        ActivityManager.getInstance().add(this);
        initiate();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnAdd.getId()) {
            MuiltChoice q = new MuiltChoice(edtQuestion.getText().toString(), edtA.getText().toString(),
                    edtB.getText().toString(), edtC.getText().toString(), edtD.getText().toString(), answer);
            DAO qd = new DAO();
            try {
                if (qd.addQuestion(q)) {
                    setResult(100);
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT);
                    finish();
                }
            } catch (IllegalInputException e) {
                e.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(e.getMessage());
                if (e.getMessage().equals("题干为空")) {
                    builder.setMessage("题干不能为空，请检查后重新输入\n点击对话框外或者点击确定按钮可以回到上一层");
                } else if (e.getMessage().equals("选项不能为空")) {
                    builder.setMessage("选项中有空选项，请检查后重新输入\n点击对话框外或者点击确定按钮可以回到上一层");
                }

                builder.setCancelable(true);
                builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
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
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.item_to_home_items, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void initiate() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtQuestion = findViewById(R.id.strQuestion);
        edtA = findViewById(R.id.choiceA);
        edtB = findViewById(R.id.choiceB);
        edtC = findViewById(R.id.choiceC);
        edtD = findViewById(R.id.choiceD);
        btnAdd = findViewById(R.id.btnAdd);
        spinner = findViewById(R.id.answer);


        btnAdd.setOnClickListener(this);
        answers = new Character[]{'A', 'B', 'C', 'D'};
        aa = new ArrayAdapter<Character>(this, android.R.layout.simple_spinner_dropdown_item, answers);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        answer = answers[position].toString();
        Log.wtf("tag", answer);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}