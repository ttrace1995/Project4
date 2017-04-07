package com.proj4.project4;

import android.graphics.Bitmap;

/**
 * Created by traceys5 on 4/5/17.
 *
 */
public class BikeData {

    private String Company;
    private String Location;
    private String Model;
    private String Date;
    private String Description;
    private String PictureJPEG;
    private String Link;
    private double Price;
    private Bitmap imageBit;

    public BikeData() {}

    public void setCompany(String in) {
        Company = in;
    }

    public void setImageBit(Bitmap in) {
        imageBit = in;
    }

    public void setLocation(String in) {
        Location = in;
    }

    public void setModel(String in) {
        Model = in;
    }

    public void setDate(String in) {
        Date = in;
    }

    public void setDescription(String in) {
        Description = in;
    }

    public void setPictureJPEG(String in) {
        PictureJPEG = in;
    }

    public void setLink(String in) {
        Link = in;
    }

    public void setPrice(double in) {
        Price = in;
    }
    public String getModel() {
        return Model;
    }
    public String getDescription() {
        return Description;
    }
    public String getCompany() {
        return Company;
    }
    public String getLocation() {
        return Location;
    }
    public String getDate() {
        return Date;
    }
    public String getLink() {
        return Link;
    }
    public String getPictureJPEG() {
        return PictureJPEG;
    }
    public double getPrice() {
        return Price;
    }
    public Bitmap getImageBit() {
        return imageBit;
    }

}
