package com.example.seniordesign;

import android.os.Parcel;
import android.os.ParcelUuid;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class DevicesLiveData extends LiveData<List<DiscoveredBluetoothDevice>> {
    private static final ParcelUuid FILTER_UUID = new ParcelUuid(DiveManager.LBS_UUID_SERVICE);
    private static final int FILTER_RSSI = -50;

    private final List<DiscoveredBluetoothDevice> mDevices = new ArrayList<>();
    private List<DiscoveredBluetoothDevice> mFilteredDevices = null;
    private boolean mFilterUuidRequired;
    private boolean mFilterNearbyOnly;

    DevicesLiveData(final boolean filterUuidRequired, final boolean filterNearbyOnly)
    {
        mFilterUuidRequired = filterUuidRequired;
        mFilterNearbyOnly = filterNearbyOnly;
    }

    synchronized void bluetoothDisabled()
    {
        mDevices.clear();
        mFilteredDevices = null;
        postValue(null);
    }

    boolean filterByUuid (final boolean uuidRequired)
    {
        mFilterUuidRequired = uuidRequired;
        return applyFilter();
    }

    boolean filterByDistance(final boolean nearbyOnly)
    {
        mFilterNearbyOnly = nearbyOnly;
        return applyFilter();
    }

    synchronized  boolean deviceDiscovered(final ScanResult result) {
        DiscoveredBluetoothDevice device;
        final int index = indexOf(result);
        if(index == -1) {
            device = new DiscoveredBluetoothDevice(result);
            mDevices.add(device);
        } else {
            device = mDevices.get(index);
        }

        device.update(result);
        return (mFilteredDevices != null && mFilteredDevices.contains(device)) || (matchesUuidFilter(result) && matchesNearbyFilter(device.getHighestRssi()));
    }

    public synchronized void clear()
    {
        mDevices.clear();
        mFilteredDevices = null;
        postValue(null);
    }

    synchronized boolean applyFilter()
    {
        final List<DiscoveredBluetoothDevice> devices = new ArrayList<>();
        for(final DiscoveredBluetoothDevice device : mDevices)
        {
            final ScanResult result = device.getScanResult();
            if(matchesUuidFilter(result) && matchesNearbyFilter(device.getHighestRssi())){
                devices.add(device);
            }
        }
        mFilteredDevices = devices;
        postValue(mFilteredDevices);
        return !mFilteredDevices.isEmpty();
    }

    private int indexOf(final ScanResult result) {
        int i = 0;
        for(final DiscoveredBluetoothDevice device : mDevices)
        {
            if (device.matches(result))
                return i;
            i++;
        }
    return -1;
    }

    private boolean matchesUuidFilter (final ScanResult result) {
        if (!mFilterUuidRequired)
            return true;

        final ScanRecord record = result.getScanRecord();
        if (record ==null)
            return false;

        final List<ParcelUuid> uuids = record.getServiceUuids();
        if (uuids == null)
            return false;
        return uuids.contains(FILTER_UUID);
    }

    private boolean matchesNearbyFilter(final int rssi)
    {
        if (!mFilterNearbyOnly)
            return true;
        return rssi >= FILTER_RSSI;
    }



}
