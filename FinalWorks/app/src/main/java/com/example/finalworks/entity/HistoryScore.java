package com.example.finalworks.entity;

import org.litepal.crud.DataSupport;

import java.util.Objects;

public class HistoryScore extends DataSupport implements Comparable{
    private int score, no;
    private static int counter = 0;
    private String time;


    private HistoryScore(){};

    public HistoryScore(int score, String time){
        this.score = score;
        this.no = counter;
        counter++;
        this.time = time;
    }

    @Override
    public boolean equals(Object o){
        HistoryScore h = (HistoryScore) o;
        if(time.equals(h.getTime())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, time);
    }

    public int getScore(){
        return this.score;
    }

    public int getNumber(){
        return this.counter;
    }

    public String getTime(){
        return time;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setTime(String time){
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        HistoryScore h = (HistoryScore) o;
        return time.compareTo(h.getTime());
    }
}
