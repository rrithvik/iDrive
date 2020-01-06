package com.rrithvik.idrive;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button b;
    public String buttonText, speedValue;
    public Double speedDouble;
    public TextView textView;
    public ToggleButton mainButton;
    private Boolean firstTime = null;

//    Intent mServiceIntent;
//    private PersistService mSensorService;
//    Context ctx;
//    public Context getCtx() {
//        return ctx;
//    }

    LocationService myService;
    static boolean status;
    LocationManager locationManager;
    static TextView dist, time, speed;
    static long startTime, endTime;
    ImageView image;
    static ProgressDialog locate;
    static int p = 0;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            myService = binder.getService();
            status = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            status = false;
        }
    };

    void bindService() {
        if (status)
            return;
        Intent i = new Intent(getApplicationContext(), LocationService.class);
        bindService(i, sc, BIND_AUTO_CREATE);
        status = true;
        startTime = System.currentTimeMillis();
    }

    void unbindService() {
        if (status == false)
            return;
        Intent i = new Intent(getApplicationContext(), LocationService.class);
        unbindService(sc);
        status = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onDestroy() {
//        stopService(mServiceIntent);
//        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
        if (status == true)
            unbindService();
    }

    @Override
    public void onBackPressed() {
        if (status == false)
            super.onBackPressed();
        else
            moveTaskToBack(true);
    }

//    private boolean isMyServiceRunning(Class<?> serviceClass) {
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(service.service.getClassName())) {
//                Log.i ("isMyServiceRunning?", true+"");
//                return true;
//            }
//        }
//        Log.i ("isMyServiceRunning?", false+"");
//        return false;
//    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean checkAndRequestPermissions() {
        int phone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int sms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int wake = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
        int task = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_TASKS);
        int ringer = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        int notification = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int receiver = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (receiver != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (wake != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WAKE_LOCK);
        }
        if (task != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.GET_TASKS);
        }
        if (ringer != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (notification != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        ctx = this;
        setContentView(R.layout.activity_main);
//        mSensorService = new PersistService(getCtx());
//        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
//        if (!isMyServiceRunning(mSensorService.getClass())) {
//            startService(mServiceIntent);
//        }

//        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
//                .getBoolean("isfirstrun", true);
//
//        if (isFirstRun) {
//            CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
//            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            cdd.show();
//
//            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
//                    .putBoolean("isfirstrun", false).apply();
//        }

        startService(new Intent(this, PersistService.class));

//        speedValue = LocationService.speedValue;
//        speedDouble = Double.parseDouble(speedValue);
//        if (speedDouble > 0){
//            setContentView(R.layout.activity_main);
//        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        checkAndRequestPermissions();

        checkGps();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return;
        }

//        final AudioManager am;
//        am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
//
//        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//        PhoneStateListener phoneStateListener = new PhoneStateListener() {
//            @Override
//            public void onCallStateChanged(int state, String incomingNumber) {
//                super.onCallStateChanged(state, incomingNumber);
//
//                if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
//                    Toast.makeText(getApplicationContext(), "Phone is in a Call", Toast.LENGTH_SHORT).show();
//                }
//                if (state == TelephonyManager.CALL_STATE_RINGING) {
//
//                }
//
//                if (state == TelephonyManager.CALL_STATE_IDLE) {
//                    Toast.makeText(getApplicationContext(), "Phone is Idle", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        if (!status)
            //Here, the Location Service gets bound and the GPS Speedometer gets Active.
            bindService();
        locate = new ProgressDialog(MainActivity.this);
        locate.setIndeterminate(true);
        locate.setCancelable(false);
        locate.setMessage("Getting Location...");
        locate.show();

        mainButton = (ToggleButton) findViewById(R.id.button);
        buttonText = mainButton.getText().toString();


        dist = (TextView) findViewById(R.id.distancetext);
        time = (TextView) findViewById(R.id.timetext);
        speed = (TextView) findViewById(R.id.speedtext);

    }

    //This method leads you to the alert dialog box.
    void checkGps() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            showGPSDisabledAlertToUser();
        }
    }

    //This method configures the Alert Dialog box.
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Enable GPS to use application")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void onCheckedChanged(View view) {

        boolean checked = ((ToggleButton) view).isChecked();
        if (checked) {
            // The toggle is enabled
            mainButton.setBackground(getDrawable(R.drawable.oval));
            mainButton.setText(getString(R.string.running));
            checkGps();
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                return;
            }


            if (status == false)
                //Here, the Location Service gets bound and the GPS Speedometer gets Active.
                bindService();
            locate = new ProgressDialog(MainActivity.this);
            locate.setIndeterminate(true);
            locate.setCancelable(false);
            locate.setMessage("Getting Location...");
            locate.show();
        } else {
            // The toggle is disabled
            mainButton.setBackground(getDrawable(R.drawable.oval_stopped));
            mainButton.setText(getString(R.string.stopped));
            if (status)
                unbindService();
            p = 0;
        }
    }
}
