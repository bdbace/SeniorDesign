package com.example.seniordesign.ui.graphs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniordesign.R
import com.example.seniordesign.room_database_swimdata.DiveGraphsAdapter
import com.example.seniordesign.room_database_swimdata.DiveMapsAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GraphsFragment : Fragment() {

    private lateinit var graphsViewModel: GraphsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        graphsViewModel =
            ViewModelProviders.of(this).get(GraphsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_graphs, container, false)
        val textView: TextView = root.findViewById(R.id.text_graphs)
        graphsViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val recyclerView =  root.findViewById<RecyclerView>(R.id.recyclerView_graphs)
        val adapter = DiveGraphsAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        graphsViewModel = ViewModelProvider(this).get(GraphsViewModel::class.java)
        graphsViewModel.allDives.observe(viewLifecycleOwner, Observer { dives ->
            // Update the cached copy of the words in the adapter.
            dives?.let { adapter.setDives(it) }
        })
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // create line chart
/*
        @Nullable
        val lineChartView = view.findViewById<LineChart>(R.id.line_graph)

        val entries1 = ArrayList<Entry>()
        entries1.add(Entry(0f, 50f))
        entries1.add(Entry(6f, 5f))
        entries1.add(Entry(14f, 32f))
        val v1 = LineDataSet(entries1, "Test")
        v1.color = Color.RED
        v1.setDrawValues(false)
        lineChartView.data = LineData(v1)
        lineChartView.invalidate()
    */
    }

}