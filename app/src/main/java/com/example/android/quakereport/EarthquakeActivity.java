/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity  {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthquackAdapter mAdapter;

    private TextView emptytextView;
 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Create a fake list of earthquake locations.
        ArrayList<Earthquack> earthquakes = Queryutils.extractEarthquakes();
     /*   earthquakes.add(new Earthquack("7.2","San Francisco","Feb 2 2016"));
        earthquakes.add(new Earthquack("6.1","London","July 20 2015"));
        earthquakes.add(new Earthquack("3.9","Tokyo","Nov 10 2014"));
        earthquakes.add(new Earthquack("5.4","Mexico City","May 3 2014"));
        earthquakes.add(new Earthquack("2.8","Moscow","Jan 31 2013"));
        earthquakes.add(new Earthquack("4.9","Rio de Janeiro","Aug 19 2012"));
        earthquakes.add(new Earthquack("1.6","Paris","Ovt 30 2011"));  */

        // Find a reference to the {@link ListView} in the layout
   /*     ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes

        final EarthquackAdapter adapter = new EarthquackAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquack currentEarthquake = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getMurl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });*/
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.earthquake_activity);

            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new adapter that takes an empty list of earthquakes as input
            mAdapter = new EarthquackAdapter(this, new ArrayList<Earthquack>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mAdapter);

            emptytextView=(TextView)findViewById(R.id.empty_text);
            earthquakeListView.setEmptyView(emptytextView);

            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =connectivityManager.getNetworkInfo();


            // Set an item click listener on the ListView, which sends an intent to a web browser
            // to open a website with more information about the selected earthquake.
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    Earthquack currentEarthquake = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getMurl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }

            });
            EarthquakeAsyncTask task = new EarthquakeAsyncTask();
            task.execute(USGS_REQUEST_URL);
        }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquack>> {
            LoaderManager loaderManager=getLoaderManager();

        @Override
        protected List<Earthquack> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquack> result = Queryutils.fetchEarthquakeData(urls[0]);
            return result;

        }


        @Override
        protected void onPostExecute(List<Earthquack> data) {
            // Clear the adapter of previous earthquake data

            View loadingindicator= (View)findViewById(R.id.progress_circula);
            loadingindicator.setVisibility(View.GONE);

            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.

            emptytextView.setText(R.string.no_earthquakes);

            mAdapter.clear();
            if(data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }

         /*   @Override
            public Loader<List<Earthquack>> onCreateLoader ( int i, Bundle bundle){
                // TODO: Create a new loader for the given URL
            }

            @Override
            public void onLoadFinished
            (Loader < List < Earthquack >> loader, List < Earthquack > earthquakes){
                // TODO: Update the UI with the result
            }

            @Override
            public void onLoaderReset (Loader < List < Earthquack >> loader) {
                // TODO: Loader reset, so we can clear out our existing data.
            }*/
            }

}
