package com.example.seniordesign.ui.BLE

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.seniordesign.R
import com.example.seniordesign.ScannerActivity

class BLEFragment : Fragment() {
    private lateinit var bleViewModel: BLEViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bleViewModel =
            ViewModelProviders.of(this).get(BLEViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ble, container, false)
        val textView: TextView = root.findViewById(R.id.text_ble)
        bleViewModel.text.observe(this, Observer {
            textView.text = it

        })
        val button = root.findViewById<Button>(R.id.btn_scan)
        button?.setOnClickListener()
        {
        //    Toast.makeText(getActivity(), "Scanning", Toast.LENGTH_LONG).show()
                var clickintent = Intent(getActivity(), ScannerActivity::class.java)
                startActivity(clickintent)
        }


        return root
    }
}
