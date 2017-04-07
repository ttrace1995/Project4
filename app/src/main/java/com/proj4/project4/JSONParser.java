package com.proj4.project4;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by traceys5 on 4/5/17.
 *
 */
public class JSONParser {

    static InputStream inputStream = null;
    static JSONObject JSONobject = null;
    static String JSONstring = "";

    HttpURLConnection urlConnection;

    public JSONParser() {

    }

    public JSONObject getJSON(String in) {

        try {
            URL url = new URL(in);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
        }
        catch (IOException io) {}

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();
            JSONstring = sb.toString();
            JSONobject = new JSONObject(JSONstring);
            Log.v("taaaaaaaaag", JSONobject.toString());
        }
        catch (Exception e) {}

        return JSONobject;
    }
}
