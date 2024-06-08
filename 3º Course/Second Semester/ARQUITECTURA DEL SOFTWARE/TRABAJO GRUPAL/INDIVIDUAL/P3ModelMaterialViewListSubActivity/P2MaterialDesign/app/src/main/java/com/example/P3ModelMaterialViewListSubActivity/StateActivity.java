package com.example.P3ModelMaterialViewListSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class StateActivity extends Activity {
    private TextView textViewId, textViewTrainingProgram, textViewSong, textViewVisualization,
            textViewVelocity, textViewSlope, textViewBluetooth;

    private String idText, trainingProgramText, songText, visualizationText, bluetoothText;
    private int velocityText, slopeText;

    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        setupToolbar();
        initializeViews();
        loadInitialData(getIntent().getExtras());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("State");
    }

    private void initializeViews() {
        textViewId = findViewById(R.id.idStateText);
        textViewTrainingProgram = findViewById(R.id.trainingProgramStateText);
        textViewSong = findViewById(R.id.musicStateText);
        textViewVisualization = findViewById(R.id.visualizationStateText);
        textViewVelocity = findViewById(R.id.velocityStateText);
        textViewSlope = findViewById(R.id.slopeStateText);
        textViewBluetooth = findViewById(R.id.connectionBluetoothStateText);
    }

    private void loadInitialData(Bundle bundle) {
        if (bundle != null) {
            idText = bundle.getString("ID");
            trainingProgramText = bundle.getString("Training Program");
            songText = bundle.getString("Song");
            visualizationText = bundle.getString("Visualization");
            velocityText = bundle.getInt("Velocity");
            slopeText = bundle.getInt("Slope");
            bluetoothText = bundle.getBoolean("Bluetooth") ? "Yes" : "No";

            double averageCaloriesPerHour = 0;
            String bluetoothText = "No";
            int heartRate;
            int pesoUsuario = 70;

            if (velocityText != 0 || slopeText != 0) {
                averageCaloriesPerHour = calcularCaloriasQuemadasPorHora(velocityText, slopeText);
                heartRate = calcularRitmoCardiaco(velocityText, slopeText);
            }
            else{
                heartRate = calcularRitmoCardiacoReposo(pesoUsuario);
            }

            ((TextView) findViewById(R.id.caloriesStateText)).setText(String.format("%.2f", averageCaloriesPerHour));
            ((TextView) findViewById(R.id.heartRateStateText)).setText(String.valueOf(heartRate));



            if (bundle.containsKey("photo")) {
                photo = bundle.getParcelable("photo");
            }

            textViewId.setText(idText);
            textViewTrainingProgram.setText(trainingProgramText);
            textViewSong.setText(songText);
            textViewVisualization.setText(visualizationText);
            textViewVelocity.setText(String.valueOf(velocityText));
            textViewSlope.setText(String.valueOf(slopeText));
            textViewBluetooth.setText(bluetoothText);
        }
    }

    public void simulate(View view) {
        Intent intent = createIntent(SimulateActivity.class);
        startActivity(intent);
    }

    public void history(View view) {
        Intent intent = createIntent(HistoryActivity.class);
        startActivity(intent);
    }

    private Intent createIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();

        bundle.putString("ID", idText);
        bundle.putString("Training Program", trainingProgramText);
        bundle.putString("Song", songText);
        bundle.putString("Visualization", visualizationText);
        bundle.putInt("Velocity", safelyParseInt(textViewVelocity.getText().toString(), 0));
        bundle.putInt("Slope", safelyParseInt(textViewSlope.getText().toString(), 0));
        bundle.putBoolean("Bluetooth", "Yes".equals(textViewBluetooth.getText().toString()));

        if (photo != null) {
            bundle.putParcelable("photo", photo);
        }

        intent.putExtras(bundle);
        return intent;
    }

    private int safelyParseInt(String text, int defaultValue) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private double calcularCaloriasQuemadasPorHora(double velocidad, double pendiente) {
        double factorMET = obtenerFactorMET(velocidad, pendiente);
        double pesoUsuario = 70.0;
        double duracionEjercicioHoras = 1.0;

        return factorMET * pesoUsuario * duracionEjercicioHoras;
    }

    private double obtenerFactorMET(double velocidad, double pendiente) {
        double factorMET;
        if (velocidad <= 5.0) {
            factorMET = 2.0 + 0.1 * pendiente;
        } else if (velocidad <= 8.0) {
            factorMET = 4.5 + 0.15 * pendiente;
        } else if (velocidad <= 12.0) {
            factorMET = 8.0 + 0.2 * pendiente;
        } else {
            factorMET = 10.0 + 0.3 * pendiente;
        }
        return factorMET;
    }


    private int calcularRitmoCardiaco(double velocidad, double pendiente) {
        double factorMET = obtenerFactorMET(velocidad, pendiente);

        int edadUsuario = 30; // suposicion 30 años
        int ritmoCardiacoMaximo = 220 - edadUsuario; // fórmula estándar ritmo cardíaco máximo

        // Supongamos que un MET de 1 corresponde al 50% del ritmo cardíaco máximo y cada punto MET incrementa esto en un 10%
        double porcentajeRitmoCardiaco = 0.50 + (factorMET - 1) * 0.10;
        porcentajeRitmoCardiaco = Math.min(porcentajeRitmoCardiaco, 0.85); // Limitamos porcentaje a un máximo del 85%

        return (int) (ritmoCardiacoMaximo * porcentajeRitmoCardiaco);
    }

    private static int calcularRitmoCardiacoReposo(double pesoUsuarioKg) { //formula Astrand
        int ritmoCardiacoBasalEnReposo = 72;
        double coeficiente = 0.6;
        int pesoBaseKg = 70;

        return (int) (ritmoCardiacoBasalEnReposo + coeficiente * (pesoUsuarioKg - pesoBaseKg));
    }
}