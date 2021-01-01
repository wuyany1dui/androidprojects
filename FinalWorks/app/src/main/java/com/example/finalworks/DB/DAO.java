package com.example.finalworks.DB;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.finalworks.exception.IllegalInputException;
import com.example.finalworks.entity.HistoryScore;
import com.example.finalworks.entity.MuiltChoice;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DAO {

    private Context context;

    public boolean addQuestion(@NonNull MuiltChoice question) throws IllegalInputException {
        if (question == null) {
            throw new IllegalInputException("对象不能为空");
        } else if (question.getStrQuestion().equals("") || question.getStrQuestion() == null) {
            throw new IllegalInputException("题干为空");
        } else if (question.getChoiceA() == null || question.getChoiceA().equals("") || question.getChoiceB() == null
                || question.getChoiceB().equals("") && question.getChoiceC() == null ||
                question.getChoiceC().equals("") || question.getChoiceD() == null || question.getChoiceD().equals("")) {
            throw new IllegalInputException("选项不能为空");
        } else {
            question.save();
            return true;
        }

    }

    public List<MuiltChoice> getAllMuiltChoices() {
        return DataSupport.findAll(MuiltChoice.class);
    }

    public List<HistoryScore> getHistoryScore() {
        List<HistoryScore> h = DataSupport.findAll(HistoryScore.class);
        return h;
    }

    public int deleteByQuestion(String question){

        return DataSupport.deleteAll(MuiltChoice.class, "strQuestion = ", question);

    }

    public void generateDatabase() {
        try {
            readFileToGenerate();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readFileToGenerate() throws IOException {
        InputStream is = context.getResources().getAssets().open("MuiltChoice.txt");
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader bfr = new BufferedReader(isr);

        String str = "";
        int i = 0;
        while((str = bfr.readLine()) != null){
            System.out.println(i++);
            String[] muiltChoiceInfo = str.split("/");
            new MuiltChoice(muiltChoiceInfo[0], muiltChoiceInfo[1], muiltChoiceInfo[2],muiltChoiceInfo[3], muiltChoiceInfo[4], muiltChoiceInfo[5]).save();
        }


    }

    public DAO(){}

    public DAO(Context context){
        this.context = context;
    }

}