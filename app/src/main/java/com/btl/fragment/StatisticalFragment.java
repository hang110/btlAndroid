package com.btl.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.btl.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class StatisticalFragment extends Fragment {

    private TextView max1, max2;
    private PieChart pieChart;
    private SeekBar seekBar1, seekBar2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistical_fragment, container, false);
    }

   @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);


        int male = StatisticalFragmentArgs.fromBundle(getArguments()).getMale();
        int female = StatisticalFragmentArgs.fromBundle(getArguments()).getFemale();

        int sum = male + female;
        setMaxStudent(sum);

        float it1 = male * 100f / sum;
        float it2 = female * 100f / sum;

        seekBar1.setProgress((int) it1);
        seekBar2.setProgress((int) it2);

        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);

        painPieChart(it1, it2);
    }

    void painPieChart(float it1, float it2) {
        ArrayList listGender = new ArrayList<PieEntry>();
        if (it1 != 0) {
            listGender.add(new PieEntry(it1, 0));
        }
        if (it2 != 0) {
            listGender.add(new PieEntry(it2, 1));
        }
        PieDataSet dataSet = new PieDataSet(listGender, "Student Gender");

        int[] colors = {getResources().getColor(R.color.teal_200), getResources().getColor(R.color.red)};

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        dataSet.setColors(colors);
        dataSet.setSliceSpace(2f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);
        pieChart.animateXY(1000, 1000);
    }

    void setMaxStudent(int sum) {
        max1.setText(sum + "");
        max2.setText(sum + "");
    }

    void init(View v) {
        max1 = v.findViewById(R.id.tvt_max_student1);
        max2 = v.findViewById(R.id.tvt_max_student2);
        seekBar1 = v.findViewById(R.id.seek_1);
        seekBar2 = v.findViewById(R.id.seek_2);

        pieChart = v.findViewById(R.id.ctv_pie_chart);
    }
}
