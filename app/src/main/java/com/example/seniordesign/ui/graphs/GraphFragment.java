package com.example.seniordesign.ui.graphs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.seniordesign.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {
    BarChart bar;


    public static GraphFragment newInstance() {
        GraphFragment fragment = new GraphFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public GraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_graph, container, false);

    //    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ProductSans.ttf");
        BarChart bar = view.findViewById(R.id.bar);
        LineChart line = view.findViewById(R.id.line);
        PieChart pie = view.findViewById(R.id.pie);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 8f,"Number of Dives"));
        entries.add(new BarEntry(1f, 23f,"Average Length of Dive"));
        entries.add(new BarEntry(2f, 52f,"Longest Dive"));
        entries.add(new BarEntry(3f, 11f,"Shortest Dive"));


        List<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(0f, 18f));
        lineEntries.add(new Entry(1f, 23f));
        lineEntries.add(new Entry(2f, 27f));
        lineEntries.add(new Entry(3f, 13f));
        lineEntries.add(new Entry(4f, 11f));
        lineEntries.add(new Entry(5f, 5f));
        lineEntries.add(new Entry(6f, 6f));
        lineEntries.add(new Entry(7f, 8f));
        lineEntries.add(new Entry(8f, 11f));
        lineEntries.add(new Entry(9f, 13f));
        lineEntries.add(new Entry(10f, 7f));
        lineEntries.add(new Entry(11f, 2f));

        LineDataSet lSet = new LineDataSet(lineEntries, "Dive Speed");
        LineData lineData = new LineData(lSet);
        line.setData(lineData);
        line.invalidate(); // refresh


        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(18.5f, "Dive"));
        pieEntries.add(new PieEntry(26.7f, "Depth"));
        pieEntries.add(new PieEntry(24.0f,"Time"));
        pieEntries.add(new PieEntry(30.8f,"Average"));
        PieDataSet pSet = new PieDataSet(pieEntries, "Marks");
        PieData pieData = new PieData(pSet);
        pie.setData(pieData);
        pSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pie.invalidate(); // refresh

        BarDataSet bSet = new BarDataSet(entries, "Marks");
        bSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        ArrayList<String> barFactors = new ArrayList<>();
        barFactors.add("Total");
        barFactors.add("Average");
        barFactors.add("Longest");
        barFactors.add("Shortest");


        XAxis xAxis = bar.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        BarData data = new BarData(bSet);
        data.setBarWidth(0.9f); // set custom bar width
        data.setValueTextSize(12);
        Description description = new Description();
        description.setTextColor(R.color.colorPrimary);
        description.setText("All values in minutes");
        bar.setDescription(description);
        bar.setData(data);
        bar.setFitBars(true); // make the x-axis fit exactly all bars
        bar.invalidate(); // refresh
        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));

        Legend l = bar.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
     //   l.setTypeface(font);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        List<LegendEntry> lentries = new ArrayList<>();
        for (int i = 0; i < barFactors.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = ColorTemplate.VORDIPLOM_COLORS[i];
            entry.label = barFactors.get(i);
            lentries.add(entry);
        }
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f);
        l.setCustom(lentries);

      //  return inflater.inflate(R.layout.fragment_graphs, container, false);
    return view;
    }

}