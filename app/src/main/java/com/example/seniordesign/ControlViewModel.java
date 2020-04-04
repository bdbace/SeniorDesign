package com.example.seniordesign;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import no.nordicsemi.android.log.LogSession;
import no.nordicsemi.android.log.Logger;

    public class ControlViewModel extends AndroidViewModel implements DiveManagerCallbacks {
        private final DiveManager mDiveManager;
        private BluetoothDevice mDevice;

        // Connection states Connecting, Connected, Disconnecting, Disconnected etc.
        private final MutableLiveData<String> mConnectionState = new MutableLiveData<>();

        // Flag to determine if the device is connected
        private final MutableLiveData<Boolean> mIsConnected = new MutableLiveData<>();

        // Flag to determine if the device has required services
        private final MutableLiveData<Boolean> mIsSupported = new MutableLiveData<>();

        // Flag to determine if the device is ready
        private final MutableLiveData<Void> mOnDeviceReady = new MutableLiveData<>();

        // Flag that holds the on off state of the LED. On is true, Off is False
        private final MutableLiveData<Boolean> mLEDState = new MutableLiveData<>();

        // Flag that holds the pressed released state of the button on the devkit.
        // Pressed is true, Released is false
        private final MutableLiveData<Boolean> mButtonState = new MutableLiveData<>();

        public LiveData<Void> isDeviceReady() {
            return mOnDeviceReady;
        }

        public LiveData<String> getConnectionState() {
            return mConnectionState;
        }

        public LiveData<Boolean> isConnected() {
            return mIsConnected;
        }

        public LiveData<Boolean> getButtonState() { return mButtonState; }

        public LiveData<Boolean> getLEDState() {
            return mLEDState;
        }

        public LiveData<Boolean> isSupported() {
            return mIsSupported;
        }

        public ControlViewModel(@NonNull final Application application) {
            super(application);

            // Initialize the manager
            mDiveManager = new DiveManager(getApplication());
            mDiveManager.setGattCallbacks(this);
        }

        /**
         * Connect to peripheral.
         */
        public void connect(@NonNull final DiscoveredBluetoothDevice device) {
            // Prevent from calling again when called again (screen orientation changed)
            if (mDevice == null) {
                mDevice = device.getDevice();
                final LogSession logSession
                        = Logger.newSession(getApplication(), null, device.getAddress(), device.getName());
                mDiveManager.setLogger(logSession);
                reconnect();
            }
        }

        /**
         * Reconnects to previously connected device.
         * If this device was not supported, its services were cleared on disconnection, so
         * reconnection may help.
         */
        public void reconnect() {
            if (mDevice != null) {
                mDiveManager.connect(mDevice)
                        .retry(3, 100)
                        .useAutoConnect(false)
                        .enqueue();
            }
        }

        /**
         * Disconnect from peripheral.
         */
        private void disconnect() {
            mDevice = null;
            mDiveManager.disconnect().enqueue();
        }

        @Override
        protected void onCleared() {
            super.onCleared();
            if (mDiveManager.isConnected()) {
                disconnect();
            }
        }


        @Override
        public void onDeviceConnecting(@NonNull final BluetoothDevice device) {
            mConnectionState.postValue(getApplication().getString(R.string.state_connecting));
        }

        @Override
        public void onDeviceConnected(@NonNull final BluetoothDevice device) {
            mIsConnected.postValue(true);
            mConnectionState.postValue(getApplication().getString(R.string.state_discovering_services));
        }

        @Override
        public void onDeviceDisconnecting(@NonNull final BluetoothDevice device) {
            mIsConnected.postValue(false);
        }

        @Override
        public void onDeviceDisconnected(@NonNull final BluetoothDevice device) {
            mIsConnected.postValue(false);
        }

        @Override
        public void onLinkLossOccurred(@NonNull final BluetoothDevice device) {
            mIsConnected.postValue(false);
        }

        @Override
        public void onServicesDiscovered(@NonNull final BluetoothDevice device,
                                         final boolean optionalServicesFound) {
            mConnectionState.postValue(getApplication().getString(R.string.state_initializing));
        }

        @Override
        public void onDeviceReady(@NonNull final BluetoothDevice device) {
            mIsSupported.postValue(true);
            mConnectionState.postValue(null);
            mOnDeviceReady.postValue(null);
        }

        @Override
        public void onBondingRequired(@NonNull final BluetoothDevice device) {
            // Blinky does not require bonding
        }

        @Override
        public void onBonded(@NonNull final BluetoothDevice device) {
            // Blinky does not require bonding
        }

        @Override
        public void onBondingFailed(@NonNull final BluetoothDevice device) {
            // Blinky does not require bonding
        }

        @Override
        public void onError(@NonNull final BluetoothDevice device,
                            @NonNull final String message, final int errorCode) {
            // TODO implement
        }

        @Override
        public void onDeviceNotSupported(@NonNull final BluetoothDevice device) {
            mConnectionState.postValue(null);
            mIsSupported.postValue(false);
        }
    }
