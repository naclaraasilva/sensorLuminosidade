package com.example.atividadeluminosidade;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final float LIMIAR_LUZ = 50f;

    private LinearLayout layoutRoot;
    private TextView txtStatus;
    private TextView txtLux;
    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutRoot = findViewById(R.id.layoutRoot);
        txtStatus = findViewById(R.id.txtStatus);
        txtLux = findViewById(R.id.txtLux);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float lux = sensorEvent.values[0];

        txtLux.setText(String.format("Luminosidade: %.1f lux", lux));

        if (lux >= LIMIAR_LUZ) {
            layoutRoot.setBackgroundColor(Color.WHITE);
            txtStatus.setTextColor(Color.BLACK);
            txtLux.setTextColor(Color.BLACK);
            txtStatus.setText("CLARO");
        } else {
            layoutRoot.setBackgroundColor(Color.BLACK);
            txtStatus.setTextColor(Color.WHITE);
            txtLux.setTextColor(Color.WHITE);
            txtStatus.setText("ESCURO");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorLuz != null) {
            sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}