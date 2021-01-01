package com.example.finalworks.entity;

import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MuiltChoice extends DataSupport implements Comparable{

    private String strQuestion, choiceA, choiceB, choiceC, choiceD, answer;
    private boolean isWrong;

    @Override
    public boolean equals(Object o){
        MuiltChoice q = (MuiltChoice) o;
        if(strQuestion.equals(q.strQuestion)&&choiceD.equals(q.getChoiceD())&&choiceC.equals(q.getChoiceC())&&choiceB.equals(q.getChoiceB())&&choiceA.equals(q.getChoiceA())&&answer == q.getAnswer())
            return true;
        else
            return false;

    }

    @Override
    public int hashCode(){
        return strQuestion.hashCode();
    }

    public String getAnswer(){
        return this.answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getStrQuestion() {
        return strQuestion;
    }

    public void setStrQuestion(String strQuestion) {
        this.strQuestion = strQuestion;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public boolean isWrong(){
        return this.isWrong;
    }

    public void setWrong(boolean isWrong)
    {
        this.isWrong = isWrong;
    }



    private MuiltChoice(){
        this.isWrong = false;
    };

    public MuiltChoice(String strQuestion, String choiceA, String choiceB, String choiceC, String choiceD, String answer){
        this.strQuestion = strQuestion;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.answer = answer;
        this.isWrong = false;
    }

    public static MuiltChoice newQuestions(String strQuestion, String choiceA, String choiceB, String choiceC, String choiceD, String answer){
        return new MuiltChoice(strQuestion, choiceA, choiceB, choiceC, choiceD, answer);
    }

    @Override
    public int compareTo(Object o) {
        MuiltChoice q = (MuiltChoice) o;
        return strQuestion.compareTo(q.getStrQuestion());
    }


}
