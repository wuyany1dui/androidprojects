package com.example.finalworks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalworks.HistoryScoreActivity;
import com.example.finalworks.MainActivity;
import com.example.finalworks.R;
import com.example.finalworks.entity.HistoryScore;
import com.example.finalworks.entity.MuiltChoice;

import java.util.List;

public class MainAdapter extends BaseAdapter {

    private List<MuiltChoice> muiltChoiceList;
    private Context context;
    private LayoutInflater inflater;

    public MainAdapter(List<MuiltChoice> list, Context context){
        this.muiltChoiceList = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return muiltChoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return muiltChoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View layout = inflater.inflate(R.layout.main_listview, null);
        TextView txvCounter = layout.findViewById(R.id.txvCounter);
        TextView txvStrQuestion = layout.findViewById(R.id.txvStrQuestion);
        txvCounter.setText(position+1+".");
        txvStrQuestion.setText(muiltChoiceList.get(position).getStrQuestion());
        return layout;



    }
}
