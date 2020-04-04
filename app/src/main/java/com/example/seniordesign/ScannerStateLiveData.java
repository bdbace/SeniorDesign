package com.example.seniordesign;

import androidx.lifecycle.LiveData;

public class ScannerStateLiveData extends LiveData<ScannerStateLiveData> {
    private boolean mScanningStarted;
    private boolean mHasRecords;
    private boolean mBluetoothEnabled;
    private boolean mLocationEnabled;
    ScannerStateLiveData(final boolean bluetoothEnabled, final boolean locationEnabled)
    {
        mScanningStarted = false;
        mBluetoothEnabled = bluetoothEnabled;
        mLocationEnabled = locationEnabled;
        postValue(this);
    }

    void refresh() {postValue(this);}
    void scanningStarted()
    {
        mScanningStarted = true;
        postValue(this);
    }
    void scanningStopped()
    {
        mScanningStarted = false;
        postValue(this);
    }

    void bluetoothEnabled()
    {
        mBluetoothEnabled = true;
        postValue(this);
    }
    synchronized void bluetoothDisabled()
    {
        mBluetoothEnabled = false;
        mHasRecords = false;
        postValue(this);
    }
    void setLocationEnabled(final boolean enabled)
    {
        mLocationEnabled = enabled;
        postValue(this);
    }
    void recordFound()
    {
        mHasRecords = true;
        postValue(this);
    }
    boolean isScanning(){return mScanningStarted;}
    public boolean hasRecords(){return mHasRecords;}
    public boolean isBluetoothEnabled(){return mBluetoothEnabled;}
    public boolean isLocationEnabled(){return mLocationEnabled;}
    public void clearRecords(){
        mHasRecords = false;
        postValue(this);
    }

}
