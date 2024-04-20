//Filename:		BackgroundTask.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549, 8896386, 8825169
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the Cat API to work

package com.example.habittracker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchAPI extends AsyncTask<Void, Void, String> {

    private static WeakReference<Context> contextRef;

    public FetchAPI(Context context) {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (MainWindow.catto == null) {
            String imageUrl = null;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            // Fetch a random cat picture from the internet
            try {
                URL url = new URL("https://api.thecatapi.com/v1/images/search");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse JSON response and get image URL
                    JSONArray jsonArray = new JSONArray(response.toString());
                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        imageUrl = jsonObject.optString("url");
                    }
                } else {
                    Log.e("FetchAPI", "HTTP error code: " + connection.getResponseCode());
                }
            } catch (IOException | JSONException e) {
                Log.e("FetchAPI", "Error fetching cat picture", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("FetchAPI", "Error closing reader", e);
                    }
                }
            }
            return imageUrl;
        }

        return null;
    }

    @Override
    protected void onPostExecute(String imageUrl) {
        super.onPostExecute(imageUrl);

        // Display the cat picture on the main activity
        if (contextRef.get() != null && imageUrl != null) {
            MainWindow activity = (MainWindow) contextRef.get();
            new LoadImageTask(activity).execute(imageUrl);

            return;
        }

        setupCattos(MainWindow.catto);
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Drawable> {
        private WeakReference<MainWindow> activityRef;

        LoadImageTask(MainWindow activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        protected Drawable doInBackground(String... strings) {
            String imageUrl = strings[0];
            Drawable drawable = null;

            try {
                InputStream inputStream = (InputStream) new URL(imageUrl).getContent();
                drawable = Drawable.createFromStream(inputStream, "image");
            } catch (IOException e) {
                Log.e("FetchAPI", "Error loading image", e);
            }

            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);

            // Store catto image :)
            if (drawable != null) {
                MainWindow.catto = drawable;
            }

            setupCattos(drawable);
        }
    }

    private static void setupCattos(Drawable drawable) {
        Context context = contextRef.get();
        if (context != null) {
            MainWindow activity = (MainWindow) context;
            ImageView imageView = activity.findViewById(R.id.mainwindow_image);
            imageView.setImageDrawable(drawable);
        }
    }
}
