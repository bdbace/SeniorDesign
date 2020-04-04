package com.example.seniordesign;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScannerActivity extends AppCompatActivity implements DevicesAdapter.OnItemClickListener
{
private static final int REQUEST_ACCESS_COARSE_LOCATION  = 1022;
private ScannerViewModel mScannerViewModel;

@BindView(R.id.state_scanning)
View mScanningView;
@BindView(R.id.no_devices)
View mEmptyView;
@BindView(R.id.no_location_permission)
View mNoLocationPermissionView;
@BindView(R.id.action_grant_location_permission)
Button mGrantPermissionButton;
@BindView(R.id.action_permission_settings)
Button mPermissionSettingsButton;
@BindView(R.id.no_location)
View mNoLocationView;
@BindView(R.id.bluetooth_off)
View mNoBluetoothView;

@Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scanner);
    ButterKnife.bind(this);

  //  final Toolbar toolbar = findViewById(R.id.toolbar);
   // setSupportActionBar(toolbar);
   // getSupportActionBar().setTitle("Dive Data Recorder");

    mScannerViewModel = new ViewModelProvider(this).get(ScannerViewModel.class);
    mScannerViewModel.getScannerState().observe(this, this::startScan);

    final RecyclerView recyclerView = findViewById(R.id.recycler_view_ble_devices);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    final DevicesAdapter adapter = new DevicesAdapter(this, mScannerViewModel.getDevices());
    adapter.setOnItemClickListener(this);
    recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        clear();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        stopScan();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
    getMenuInflater().inflate(R.menu.filter, menu);
    menu.findItem(R.id.filter_uuid).setChecked(mScannerViewModel.isUuidFilterEnabled());
    menu.findItem(R.id.filter_nearby).setChecked(mScannerViewModel.isNearbyFilterEnabled());
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
    switch (item.getItemId())
    {
        case R.id.filter_uuid:
            item.setChecked(!item.isChecked());
            mScannerViewModel.filterByUuid(item.isChecked());
            return true;
        case R.id.filter_nearby:
            item.setChecked(!item.isChecked());
            mScannerViewModel.filterByDistance(item.isChecked());
            return true;

    }
    return super.onOptionsItemSelected(item);
    }

@Override
    public void onItemClick(@NonNull final DiscoveredBluetoothDevice device)
{
    final Intent controlIntent = new Intent(this, ControlActivity.class);
    controlIntent.putExtra(ControlActivity.EXTRA_DEVICE, device);
    startActivity(controlIntent);
}

@Override
    public void onRequestPermissionsResult( final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_ACCESS_COARSE_LOCATION){
        mScannerViewModel.refresh();
    }
}

@OnClick(R.id.action_enable_location)
    public void onEnableLocationClicked()
{
    final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    startActivity(intent);
}

@OnClick(R.id.action_enable_bluetooth)
    public void onEnableBluetoothClicked()
    {
    final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivity(enableIntent);
    }

@OnClick(R.id.action_grant_location_permission)
    public void onGrantLocationPermissionClicked()
{
    Utils.markLocationPermissionRequested(this);
    ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);

}

@OnClick(R.id.action_permission_settings)
    public void onPermissionSettingsClicked()
{
    final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.setData(Uri.fromParts("package", getPackageName(), null));
    startActivity(intent);
}


//  Scanning

private void startScan(final ScannerStateLiveData state)
{
    if (Utils.isLocationPermissionsGranted(this))
    {
        mNoLocationPermissionView.setVisibility(View.GONE);
        if(state.isBluetoothEnabled())
        {
            mNoBluetoothView.setVisibility(View.GONE);
            mScannerViewModel.startScan();
            mScanningView.setVisibility(View.VISIBLE);
            if (!state.hasRecords())
            {
                mEmptyView.setVisibility(View.VISIBLE);
                if(!Utils.isLocationRequired(this)||Utils.isLocationEnabled(this))
                {
                    mNoLocationView.setVisibility(View.INVISIBLE);
                }
                else
                {
                    mNoLocationView.setVisibility((View.VISIBLE));
                }
            }
            else
            {
                mEmptyView.setVisibility(View.GONE);
            }
        }
        else
        {
            mNoBluetoothView.setVisibility(View.VISIBLE);
            mScanningView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.GONE);
            clear();
        }
    }
    else
    {
        mNoLocationPermissionView.setVisibility(View.VISIBLE);
        mNoBluetoothView.setVisibility(View.GONE);
        mScanningView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.GONE);
        final boolean deniedForever = Utils.isLocationPermissionDeniedForever(this);
        mGrantPermissionButton.setVisibility(deniedForever ? View.GONE : View.VISIBLE);
        mPermissionSettingsButton.setVisibility(deniedForever ? View.VISIBLE : View.GONE);
    }
}


private void stopScan()
{
    mScannerViewModel.stopScan();
}

private void clear()
{
    mScannerViewModel.getDevices().clear();
    mScannerViewModel.getScannerState().clearRecords();
}


}
