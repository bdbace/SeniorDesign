package com.example.seniordesign.ui.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seniordesign.R
import com.example.seniordesign.room_database_swimdata.DiveListAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {textView.text = it })

        //
        val recyclerView =  root.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = DiveListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.allDives.observe(viewLifecycleOwner, Observer { dives ->
            // Update the cached copy of the words in the adapter.
            dives?.let { adapter.setDives(it) }
        })

         //
        return root
    }
}
