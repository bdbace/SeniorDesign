package com.example.seniordesign.ui.maps

import android.content.Context
import android.content.Intent
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
import com.example.seniordesign.MapActivity2
import com.example.seniordesign.R
import com.example.seniordesign.room_database_swimdata.DiveMapsAdapter
import com.google.android.gms.maps.*


public abstract class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapsViewModel: MapsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsViewModel =
            ViewModelProviders.of(this).get(MapsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_maps, container, false)
        val textView: TextView = root.findViewById(R.id.text_maps)
        mapsViewModel.text.observe(this, Observer {
            textView.text = it
        })


        //
        val recyclerView =  root.findViewById<RecyclerView>(R.id.recyclerView_maps)
        val adapter = DiveMapsAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        mapsViewModel.allDives.observe(viewLifecycleOwner, Observer { dives ->
            // Update the cached copy of the words in the adapter.
            dives?.let { adapter.setDives(it) }
        })

        val mapsView = root.findViewById<MapView>(R.id.mapView)
        if (mapsView != null) {
            mapsView.getMapAsync(this)
        }

        var mapIntent = Intent(getActivity(), MapActivity2::class.java)
        startActivity(mapIntent)

//


        //
        return root
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Do your stuff here
    }
}

/*
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if(getActivity()!=null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Do your stuff here
    }
}

 */