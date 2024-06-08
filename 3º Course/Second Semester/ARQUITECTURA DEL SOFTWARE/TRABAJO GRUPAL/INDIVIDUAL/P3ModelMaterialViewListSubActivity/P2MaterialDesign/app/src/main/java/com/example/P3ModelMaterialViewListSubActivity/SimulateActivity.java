package com.example.P3ModelMaterialViewListSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class SimulateActivity extends Activity {
    private String originalId, originalTrainingProgram, originalSong, originalVisualization;
    private int originalVelocity, originalSlope;
    private boolean originalBluetooth;

    private TextView textViewVelocity, textViewSlope;

    private static final int REQUEST_CODE = 22;

    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Simulate Treadmill");

        textViewVelocity = findViewById(R.id.velocityKmText);
        textViewSlope = findViewById(R.id.slopePercentageText);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            originalId = bundle.getString("ID");
            originalTrainingProgram = bundle.getString("Training Program");
            originalSong = bundle.getString("Song");
            originalVisualization = bundle.getString("Visualization");
            originalVelocity = bundle.getInt("Velocity");
            originalSlope = bundle.getInt("Slope");
            originalBluetooth = bundle.getBoolean("Bluetooth");

            if (bundle.containsKey("photo")) {
                photo = bundle.getParcelable("photo");
            }

            ((EditText) findViewById(R.id.edit_id)).setText(originalId);
            ((EditText) findViewById(R.id.edit_music)).setText(originalSong);
            ((EditText) findViewById(R.id.edit_visualization)).setText(originalVisualization);
            ((SeekBar) findViewById(R.id.edit_velocity)).setProgress(originalVelocity);
            ((SeekBar) findViewById(R.id.edit_slope)).setProgress(originalSlope);
            ((Switch) findViewById(R.id.edit_bluetooth)).setChecked(originalBluetooth);

            textViewVelocity.setText(String.valueOf(originalVelocity));
            textViewSlope.setText(String.valueOf(originalSlope));

            Spinner spinner = findViewById(R.id.edit_trainingProgram);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.optionsTrainingProgram, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(originalTrainingProgram)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
        SeekBar seekBarVelocity = findViewById(R.id.edit_velocity);
        SeekBar seekBarSlope = findViewById(R.id.edit_slope);

        seekBarVelocity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewVelocity.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optionally handle touch events
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optionally handle touch events
            }
        });

        seekBarSlope.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSlope.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optionally handle touch events
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optionally handle touch events
            }
        });
    }

    public void setUpCamera(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            photo = (Bitmap) data.getExtras().get("data");
        }
        else{
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void saveState(View view) {
        EditText editId = (EditText) findViewById(R.id.edit_id);
        String id = editId.getText().toString();

        Spinner editTrainingProgram = findViewById(R.id.edit_trainingProgram);
        String trainingProgram = editTrainingProgram.getSelectedItem().toString();

        EditText editMusic = (EditText) findViewById(R.id.edit_music);
        String song = editMusic.getText().toString();

        EditText editVisualization = (EditText) findViewById(R.id.edit_visualization);
        String visualization = editVisualization.getText().toString();

        SeekBar editVelocity = (SeekBar) findViewById(R.id.edit_velocity);
        int velocity = editVelocity.getProgress();

        SeekBar editSlope = (SeekBar) findViewById(R.id.edit_slope);
        int slope = editSlope.getProgress();

        Switch editBluetooth = (Switch) findViewById(R.id.edit_bluetooth);
        boolean bluetooth = editBluetooth.isChecked();

        TravelPointsApplication tpa;
        tpa = (TravelPointsApplication) getApplicationContext();

        StatePoint newStatePoint = new StatePoint(id, trainingProgram, velocity, slope);

        tpa.addObjectUpdate(newStatePoint); //the object has to be created the before the first execution

        Intent intent = new Intent(SimulateActivity.this, StateActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("ID", id);
        bundle.putString("Training Program", trainingProgram);
        bundle.putString("Song", song);
        bundle.putString("Visualization", visualization);
        bundle.putInt("Velocity", velocity);
        bundle.putInt("Slope", slope);
        bundle.putBoolean("Bluetooth", bluetooth);

        if (photo != null){
            bundle.putParcelable("photo", photo);
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void cancelState(View view){
        Intent intent = new Intent(SimulateActivity.this, StateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ID", originalId);
        bundle.putString("Training Program", originalTrainingProgram);
        bundle.putString("Song", originalSong);
        bundle.putString("Visualization", originalVisualization);
        bundle.putInt("Velocity", originalVelocity);
        bundle.putInt("Slope", originalSlope);
        bundle.putBoolean("Bluetooth", originalBluetooth);

        if (photo != null){
            bundle.putParcelable("photo", photo);
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }
}