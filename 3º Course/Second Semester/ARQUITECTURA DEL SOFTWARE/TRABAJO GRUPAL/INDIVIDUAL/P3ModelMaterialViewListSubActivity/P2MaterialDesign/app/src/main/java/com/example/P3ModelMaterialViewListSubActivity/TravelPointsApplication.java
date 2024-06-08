package com.example.P3ModelMaterialViewListSubActivity;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class
TravelPointsApplication extends Application {

    private List<StatePoint> local_stateList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(StatePoint.class);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }

    public void getServerPointsUpdate(ListView listView, Runnable onDataLoaded) {
        ParseQuery<StatePoint> query = ParseQuery.getQuery("StatePoint");
        query.findInBackground((objects, e) -> {
            if (e == null) {
                local_stateList = objects;
                ArrayAdapter<StatePoint> pointItemsAdapter = new ArrayAdapter<>(listView.getContext(), R.layout.row_layout, R.id.listText, local_stateList);
                listView.setAdapter(pointItemsAdapter);
                pointItemsAdapter.notifyDataSetChanged();
                Log.d("State object query server OK:", "getServerStatePointsUpdate()");
                onDataLoaded.run();  // Ejecutar el callback
            } else {
                Log.d("fail query, reason: " + e.getMessage(), "getServerStatePointsUpdate()");
            }
        });
    }



    public void addObjectUpdate(@NonNull StatePoint aInterestPoint) {
        aInterestPoint.saveInBackground(e -> {
            if (e == null) {
                local_stateList.add(aInterestPoint);
                Log.d("State object saved server OK:", "addStatePointUpdate()");
            } else {
                Log.d("fail save, reason: " + e.getMessage(), "addStatePointUpdate()");
            }
        });
    }

    public List<StatePoint> getLocal_stateList() {
        return local_stateList;
    }

    public void getServerPointsUpdateWithoutListView() { //this can create also the object

        ParseQuery<StatePoint> query = ParseQuery.getQuery("StatePoint");
        query.findInBackground((objects, e) -> {
            if (e == null) {
                local_stateList = objects;
                Log.d("State object query server OK:", "getServerStatePointsUpdate()");
            } else {
                Log.d("fail query, reason: " + e.getMessage(), "getServerStatePointsUpdate()");
            }
        });
    }
}
