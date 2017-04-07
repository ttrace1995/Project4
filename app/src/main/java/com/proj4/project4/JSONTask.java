package com.proj4.project4;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by traceys5 on 4/5/17.
 *
 */

public class JSONTask extends AsyncTask<String, String, JSONObject> {

    public AsyncResponse response = null;
    private JSONArray jsonArray;
    private ArrayList<BikeData> bikeArray;
    private HttpURLConnection connection;
    private InputStream inputStream;
    private Bitmap bitmap;

    public JSONTask(AsyncResponse delegate){
        this.response = delegate;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONParser parse = new JSONParser();
        return parse.getJSON(params[0]);
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            // Getting JSON Array
            jsonArray = json.getJSONArray("Bikes");
            bikeArray = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject current = jsonArray.getJSONObject(i);
                BikeData hold = new BikeData();

                //hold.setImageBit(getImage(current.getString("Picture")));
                hold.setCompany(current.getString("Company"));
                hold.setModel(current.getString("Model"));
                hold.setLocation(current.getString("Location"));
                hold.setDate(current.getString("Date"));
                hold.setDescription(current.getString("Description"));
                hold.setLink(current.getString("Link"));
                hold.setPictureJPEG(current.getString("Picture"));
                hold.setPrice(current.getDouble("Price"));
                bikeArray.add(hold);
            }
            response.processFinish(bikeArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}