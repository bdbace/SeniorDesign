package com.example.seniordesign;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.util.Log;
import java.util.UUID;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("ConstantConditions")
public class ControlActivity extends AppCompatActivity {
    public static final String EXTRA_DEVICE = "no.nordicsemi.android.blinky.EXTRA_DEVICE";

    private ControlViewModel mViewModel;
//
    public final static UUID LBS_UUID_SERVICE = UUID.fromString("00001523-1212-efde-1523-785feabcd123");
    //UART Services
    private final static String TAG = ControlActivity.class.getSimpleName();
    private BluetoothGatt mBluetoothGatt;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ble);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        final DiscoveredBluetoothDevice device = intent.getParcelableExtra(EXTRA_DEVICE);
   //     final String deviceName = device.getName();
   //     final String deviceAddress = device.getAddress();

   //     final Toolbar toolbar = findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);
     //   getSupportActionBar().setTitle(deviceName);
     //   getSupportActionBar().setSubtitle(deviceAddress);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configure the view model
        mViewModel = new ViewModelProvider(this).get(ControlViewModel.class);
        mViewModel.connect(device);

        // Set up views

       // final TextView ledState = findViewById(R.id.led_state);
       // final LinearLayout progressContainer = findViewById(R.id.progress_container);
       // final TextView connectionState = findViewById(R.id.connection_state);
      //  final View content = findViewById(R.id.device_container);
       // final View notSupported = findViewById(R.id.not_supported);

       // mLed.setOnCheckedChangeListener((buttonView, isChecked) -> mViewModel.toggleLED(isChecked));
        mViewModel.isDeviceReady().observe(this, deviceReady -> {
        //    progressContainer.setVisibility(View.GONE);
        //    content.setVisibility(View.VISIBLE);
        });
        mViewModel.getConnectionState().observe(this, text -> {
            if (text != null) {
        //        progressContainer.setVisibility(View.VISIBLE);
        //        notSupported.setVisibility(View.GONE);
        //        connectionState.setText(text);
            }
        });
        mViewModel.isConnected().observe(this, this::onConnectionStateChanged);
        mViewModel.isSupported().observe(this, supported -> {
            if (!supported) {
            //    progressContainer.setVisibility(View.GONE);
             //   notSupported.setVisibility(View.VISIBLE);
            }
        });
       // mViewModel.getLEDState().observe(this, isOn -> {
          //  ledState.setText(isOn ? R.string.turn_on : R.string.turn_off);
          //  mLed.setChecked(isOn);
       // });
       // mViewModel.getButtonState().observe(this,
         //       pressed -> mButtonState.setText(pressed ?
        //                R.string.button_pressed : R.string.button_released));
    }

   // @OnClick(R.id.action_clear_cache)
//    public void onTryAgainClicked() {
  //      mViewModel.reconnect();
  //  }

    private void onConnectionStateChanged(final boolean connected) {
    //    mLed.setEnabled(connected);
         if (!connected) {
            //      mLed.setChecked(false);
            //      mButtonState.setText(R.string.button_unknown);
        }
    }


    // Main Activity UART Code



}
