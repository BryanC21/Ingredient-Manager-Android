package com.sjsu.hackathon.ingredient_manager.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sjsu.hackathon.ingredient_manager.Annotation;
import com.sjsu.hackathon.ingredient_manager.AnnotationDBHandler;
import com.sjsu.hackathon.ingredient_manager.DBHandler;
import com.sjsu.hackathon.ingredient_manager.Data;
import com.sjsu.hackathon.ingredient_manager.DataListener;
import com.sjsu.hackathon.ingredient_manager.FetchData;
import com.sjsu.hackathon.ingredient_manager.MainActivity;
import com.sjsu.hackathon.ingredient_manager.R;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentNotificationsBinding;
import com.sjsu.hackathon.ingredient_manager.ui.Constants;
import com.sjsu.hackathon.ingredient_manager.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationsFragment extends Fragment implements DataListener {

    private FragmentNotificationsBinding binding;

    private boolean GDP = false;
    private boolean FDII = false;
    private boolean FDIO = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button show = root.findViewById(R.id.agri_show);
        Layer formLayout = root.findViewById(R.id.agri_form_layer);
        Layer chartLayout = root.findViewById(R.id.agri_chart_layer);
        Layer annLayout = root.findViewById(R.id.agri_ann_layer);

        formLayout.setVisibility(View.VISIBLE);
        chartLayout.setVisibility(View.INVISIBLE);
        annLayout.setVisibility(View.INVISIBLE);

        EditText startY = root.findViewById(R.id.agri_start);
        String startYear = startY.getText().toString();
        EditText endY = root.findViewById(R.id.agri_end_year);
        String endYear = endY.getText().toString();
        String country = MainActivity.country;

        chartLayout.setVisibility(View.INVISIBLE);

        GraphView graph = root.findViewById(R.id.graph);

        //graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        //Build Graph
        //Default show 1980 to 2020
        FetchData fd = new FetchData();

        Button applyYears = root.findViewById(R.id.agri_button);
        applyYears.setOnClickListener(view -> {

            //System.out.println(startYear);

            EditText startY2 = root.findViewById(R.id.agri_start);
            String startYear2 = startY2.getText().toString();
            EditText endY2 = root.findViewById(R.id.agri_end_year);
            String endYear2 = endY2.getText().toString();

            graph.removeAllSeries();

            if (GDP) {
                fd.getData(new DBHandler(this.getContext()), "Contribution To GDP", startYear2, endYear2, country, this);
            }

            if (FDII) {
                fd.getData(new DBHandler(this.getContext()), "Fertilizers", startYear2, endYear2, country, this);
            }

            if (FDIO) {
                fd.getData(new DBHandler(this.getContext()), "Fertilizer Production", startYear2, endYear2, country, this);
            }

        });

        show.setOnClickListener(view -> {

            CheckBox gdpBox = root.findViewById(R.id.reserves);
            GDP = gdpBox.isChecked();
            CheckBox gniBox = root.findViewById(R.id.total_debt);
            FDII = gniBox.isChecked();
            CheckBox totalBox = root.findViewById(R.id.gni_curr);
            FDIO = totalBox.isChecked();

            if (gdpBox.isChecked()) {//first box
                fd.getData(new DBHandler(this.getContext()), "Contribution To GDP", startYear, endYear, country, this);
            }

            if (gniBox.isChecked()) {//second
                fd.getData(new DBHandler(this.getContext()), "Fertilizers", startYear, endYear, country, this);
            }

            if (totalBox.isChecked()) {//third box
                fd.getData(new DBHandler(this.getContext()), "Fertilizer Production", startYear, endYear, country, this);
            }

            formLayout.setVisibility(View.INVISIBLE);
            chartLayout.setVisibility(View.VISIBLE);
            annLayout.setVisibility(View.INVISIBLE);

            Button ann = root.findViewById(R.id.agri_ann);
            if (HomeFragment.actor.equals(Constants.MAC_ECC)) {
                ann.setVisibility(View.VISIBLE);
            } else {
                ann.setVisibility(View.INVISIBLE);
            }
        });

        AnnotationDBHandler ad = new AnnotationDBHandler(this.getContext());
        Button ann = root.findViewById(R.id.agri_ann);
        ann.setOnClickListener(v -> {
            formLayout.setVisibility(View.INVISIBLE);
            chartLayout.setVisibility(View.INVISIBLE);
            annLayout.setVisibility(View.VISIBLE);
            refreshTable(root, ad);
        });

        Button annSub = root.findViewById(R.id.agri_ann_submit);
        annSub.setOnClickListener(v -> {
            // Add code to submit an annotation here
            EditText ann_input = root.findViewById(R.id.agri_ann_text);
            ad.addNewData("Agriculture Annotation", ann_input.getText().toString());
            ann_input.setText("");
            refreshTable(root, ad);
        });

        return root;
    }

    private void refreshTable(View root, AnnotationDBHandler ad) {
        TableLayout table = root.findViewById(R.id.agri_ann_table);
        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }

        ArrayList<Annotation> annList = ad.getDataList();
        TableRow[] tr_head = new TableRow[annList.size()];
        TextView[] textArray = new TextView[annList.size()];
        for (int i = 0; i < annList.size(); i++) {
            Annotation annObj = annList.get(i);
            tr_head[i] = new TableRow(this.getContext());
            tr_head[i].setId(i + 1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            textArray[i] = new TextView(this.getContext());
            textArray[i].setId(i + 111);
            textArray[i].setText(annObj.getBody());
            textArray[i].setTextColor(Color.WHITE);
            textArray[i].setPadding(5, 5, 5, 5);
            tr_head[i].addView(textArray[i]);

            table.addView(tr_head[i], new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }
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