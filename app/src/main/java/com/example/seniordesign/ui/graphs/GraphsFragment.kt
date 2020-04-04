package com.example.seniordesign.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.seniordesign.R

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
        return root
    }
}