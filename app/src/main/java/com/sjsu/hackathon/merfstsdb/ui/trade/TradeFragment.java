package com.sjsu.hackathon.merfstsdb.ui.trade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sjsu.hackathon.merfstsdb.DBHandler;
import com.sjsu.hackathon.merfstsdb.Data;
import com.sjsu.hackathon.merfstsdb.DataListener;
import com.sjsu.hackathon.merfstsdb.FetchData;
import com.sjsu.hackathon.merfstsdb.MainActivity;
import com.sjsu.hackathon.merfstsdb.R;
import com.sjsu.hackathon.merfstsdb.databinding.FragmentTradeBinding;
import com.sjsu.hackathon.merfstsdb.ui.Constants;
import com.sjsu.hackathon.merfstsdb.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Collections;

public class TradeFragment extends Fragment implements DataListener {

    private FragmentTradeBinding binding;
    boolean FIRST = false;
    boolean SECOND = false;
    boolean THIRD = false;
    boolean FOURTH = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TradeViewModel notificationsViewModel =
                new ViewModelProvider(this).get(TradeViewModel.class);

        binding = FragmentTradeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textTrade;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button show = root.findViewById(R.id.trade_show);
        Layer formLayout = root.findViewById(R.id.form_layer);
        Layer chartLayout = root.findViewById(R.id.trade_layer);

        formLayout.setVisibility(View.VISIBLE);
        chartLayout.setVisibility(View.INVISIBLE);

        EditText startY = root.findViewById(R.id.trade_start);
        String startYear = startY.getText().toString();
        EditText endY = root.findViewById(R.id.trade_end_year);
        String endYear = endY.getText().toString();
        String country = MainActivity.country;

        GraphView graph = root.findViewById(R.id.graph);

        //graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        //Build Graph
        //Default show 1980 to 2020
        FetchData fd = new FetchData();

        Button applyYears = root.findViewById(R.id.trade_button);
        applyYears.setOnClickListener(view -> {

            //System.out.println(startYear);

            EditText startY2 = root.findViewById(R.id.trade_start);
            String startYear2 = startY2.getText().toString();
            EditText endY2 = root.findViewById(R.id.trade_end_year);
            String endYear2 = endY2.getText().toString();

            graph.removeAllSeries();

            if(FIRST){
                fd.getData(new DBHandler(this.getContext()),"Reserves", startYear2, endYear2, country, this);
            }

            if(SECOND){
                fd.getData(new DBHandler(this.getContext()),"GNI", startYear2, endYear2, country, this);
            }

            if(THIRD){
                fd.getData(new DBHandler(this.getContext()),"Total Debt", startYear2, endYear2, country, this);
            }

            if(FOURTH){
                fd.getData(new DBHandler(this.getContext()),"GNI Current", startYear2, endYear2, country, this);
            }

        });

        show.setOnClickListener(view -> {

            CheckBox gdpBox = root.findViewById(R.id.reserves);
            FIRST = gdpBox.isChecked();
            CheckBox gniBox = root.findViewById(R.id.gni);
            SECOND = gniBox.isChecked();
            CheckBox totalBox = root.findViewById(R.id.total_debt);
            THIRD = totalBox.isChecked();
            CheckBox gniCurrBox = root.findViewById(R.id.gni_curr);
            FOURTH = gniCurrBox.isChecked();

            if(gdpBox.isChecked()){//first box
                fd.getData(new DBHandler(this.getContext()),"Reserves", startYear, endYear, country, this);
            }

            if(gniBox.isChecked()){//second
                fd.getData(new DBHandler(this.getContext()),"GNI", startYear, endYear, country, this);
            }

            if(totalBox.isChecked()){//third box
                fd.getData(new DBHandler(this.getContext()),"Total Debt", startYear, endYear, country, this);
            }

            if(gniCurrBox.isChecked()){
                fd.getData(new DBHandler(this.getContext()),"GNI Current", startYear, endYear, country, this);
            }

            formLayout.setVisibility(View.INVISIBLE);
            chartLayout.setVisibility(View.VISIBLE);

            Button ann = root.findViewById(R.id.trade_ann);
            if (HomeFragment.actor.equals(Constants.GOVT_OFFICER)) {
                ann.setVisibility(View.VISIBLE);
            } else {
                ann.setVisibility(View.INVISIBLE);
            }

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDataFinish(ArrayList<Data> dataList) {
        View root = binding.getRoot();
        GraphView graph = root.findViewById(R.id.graph);
        System.out.println(dataList);
        ArrayList<DataPoint> x = new ArrayList<DataPoint>();
        Collections.reverse(dataList);
        DataPoint[] myData = new DataPoint[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            myData[i] = new DataPoint(Integer.parseInt(dataList.get(i).getYear()), dataList.get(i).getData());

        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(myData);
        //series.setColor(Color.GREEN);
        graph.addSeries(series);

    }

    @Override
    public void onDataFail(String reason) {

        System.out.println(reason);

    }
}