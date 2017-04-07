package com.proj4.project4;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by traceys5 on 4/6/17.
 *
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView bikeImage;

    public DownloadImage(ImageView bikeImageView) {
        this.bikeImage = bikeImageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bikeImage.setImageBitmap(result);
    }
}