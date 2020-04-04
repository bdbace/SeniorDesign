package com.example.seniordesign;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utils {
    private static final String PREFS_LOCATION_NOT_REQUIRED = "location not required";
    private static final String PREFS_PERMISSION_REQUESTED = "permission_requested";

    public static boolean isBleEnabled()
    {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter != null && adapter.isEnabled();
    }

    public static boolean isLocationPermissionsGranted(@NonNull final Context context)
    {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean isLocationPermissionDeniedForever (@NonNull final Activity activity)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return !isLocationPermissionsGranted(activity)
                    && preferences.getBoolean(PREFS_PERMISSION_REQUESTED, false)
                        && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean isLocationEnabled(@NonNull final Context context)
    {
        if (isMarshmallowOrAbove())
        {
            int locationmode = Settings.Secure.LOCATION_MODE_OFF;
            try {
                locationmode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                }
            catch(final Settings.SettingNotFoundException e)
            {

            }
            return locationmode != Settings.Secure.LOCATION_MODE_OFF;
        }
        return true;
    }

    public static boolean isLocationRequired(@NonNull final Context context)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREFS_LOCATION_NOT_REQUIRED, isMarshmallowOrAbove());
    }

    public static void markLocationNotRequired (@NonNull final Context context)
    {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            preferences.edit().putBoolean(PREFS_LOCATION_NOT_REQUIRED, false).apply();
    }


    public static void markLocationPermissionRequested(@NonNull final Context context)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(PREFS_PERMISSION_REQUESTED, true).apply();
    }

    public static boolean isMarshmallowOrAbove()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
