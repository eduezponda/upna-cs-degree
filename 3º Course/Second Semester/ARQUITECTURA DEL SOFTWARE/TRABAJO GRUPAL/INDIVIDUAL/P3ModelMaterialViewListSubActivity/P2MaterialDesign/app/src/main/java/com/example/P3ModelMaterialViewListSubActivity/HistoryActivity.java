package com.example.P3ModelMaterialViewListSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends Activity {
    private String originalId, originalTrainingProgram, originalSong, originalVisualization;
    private int originalVelocity, originalSlope;
    private boolean originalBluetooth;

    private Bitmap photo;

    private XYPlot plot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setupToolbar();
        extractDataFromBundle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ListView listView = findViewById(R.id.listViewHistory);
        TravelPointsApplication tpa = (TravelPointsApplication) getApplication();
        tpa.getServerPointsUpdate(listView, () -> setupPlot(tpa.getLocal_stateList(), tpa));
    }



    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("History");
    }

    private void setupPlot(List<StatePoint> statePointList, TravelPointsApplication tpa) {
        plot = findViewById(R.id.mySimpleXYPlot);

        List<Double> velocityList = new ArrayList<>();
        List<Double> slopeList = new ArrayList<>();

        for (StatePoint sp : statePointList) {
            velocityList.add(sp.getVelocity());
            slopeList.add(sp.getSlope());
        }

        List<Pair<Double, Double>> dataPairs = new ArrayList<>();
        for (int i = 0; i < velocityList.size(); i++) {
            dataPairs.add(new Pair<>(velocityList.get(i), slopeList.get(i)));
        }

        Collections.sort(velocityList);

        List<Pair<Double, Double>> sortedDataPairs = new ArrayList<>();
        for (Double velocity : velocityList) {
            for (Pair<Double, Double> pair : dataPairs) {
                if (pair.first.equals(velocity)) {
                    sortedDataPairs.add(pair);
                    break;
                }
            }
        }

        velocityList.clear();
        slopeList.clear();
        for (Pair<Double, Double> pair : sortedDataPairs) {
            velocityList.add(pair.first);
            slopeList.add(pair.second);
        }

        XYSeries series1 = new SimpleXYSeries(
                velocityList,
                slopeList,
                "");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, null, null);
        series1Format.setPointLabelFormatter(new PointLabelFormatter());

        plot.addSeries(series1, series1Format);

        plot.setTitle("Velocity vs Slope Analysis");
        plot.setDomainLabel("Velocity");
        plot.setRangeLabel("Slope");

        plot.redraw();
    }

    private void extractDataFromBundle() {
        ImageView imageView = findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();

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
                imageView.setImageBitmap(photo);
            }
        }
    }

    private Bundle createBundleWithOriginalData() {
        Bundle bundle = new Bundle();
        bundle.putString("ID", originalId);
        bundle.putString("Training Program", originalTrainingProgram);
        bundle.putString("Song", originalSong);
        bundle.putString("Visualization", originalVisualization);
        bundle.putInt("Velocity", originalVelocity);
        bundle.putInt("Slope", originalSlope);
        bundle.putBoolean("Bluetooth", originalBluetooth);
        return bundle;
    }

    public void simulate(View view) {
        Intent intent = new Intent(this, SimulateActivity.class);
        intent.putExtras(createBundleWithOriginalData());
        startActivity(intent);
    }

    public void state(View view) {
        Intent intent = new Intent(this, StateActivity.class);
        intent.putExtras(createBundleWithOriginalData());
        startActivity(intent);
    }


}