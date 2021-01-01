package com.example.finalworks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalworks.R;
import com.example.finalworks.entity.HistoryScore;

import java.util.List;

public class HistoryScoreAdapter extends BaseAdapter {

    private List<HistoryScore> list;
    private Context context;
    private LayoutInflater mInflter;

    public HistoryScoreAdapter(Context context, List<HistoryScore> list){
        this.list = list;
        this.context = context;
        mInflter = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = mInflter.inflate(R.layout.history_score_listview, null);
        TextView txvNumber = layout.findViewById(R.id.txvNumber);
        TextView txvScore = layout.findViewById(R.id.txvScore);
        TextView txvTime = layout.findViewById(R.id.txvTime);
        HistoryScore h = list.get(position);
        txvNumber.setText(position+"");
        txvScore.setText(h.getScore()+ "");
        txvTime.setText(h.getTime());

        return layout;
    }
}
