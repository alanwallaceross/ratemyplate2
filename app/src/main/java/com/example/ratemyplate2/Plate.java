package com.example.ratemyplate2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


/**
 * Plate class
 */
public class Plate {
    private static final String JSON_NAME = "mName";
    private static final String JSON_CAPTION = "mCaption";
    private static final String JSON_IMAGE = "image";



    private String mName;
    private String mCaption;
    private Image mImage;

    public Plate(String mName, String mCaption, Image mImage){
        this.mName = mName;
        this.mCaption = mCaption;
        this.mImage = mImage;
    }


    /**
     * loads json strings and objects from json file
     * @param json
     * @throws JSONException
     */
    public Plate(JSONObject json) throws JSONException {
        if (json.has(JSON_NAME)){
            mName = json.getString(JSON_NAME);
        }

        if (json.has(JSON_CAPTION)){
            mCaption = json.getString(JSON_CAPTION);
        }

        if (json.has(JSON_IMAGE)){
            mImage = new Image(json.getJSONObject(JSON_IMAGE));
        }

    }

    /**
     * puts names, captions and images into json file
     * @return
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, mName);
        json.put(JSON_CAPTION, mCaption);
        json.put(JSON_IMAGE, mImage);
        return json;
    }


    /**
     * returns name of plate
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * sets name of plate
     * @param mName
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * returns caption of plate
     * @return
     */
    public String getCaption() {
        return mCaption;
    }

    /**
     * sets caption of plate
     * @param mCaption
     */
    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    /**
     * returns image of plate
     * @return
     */
    public Image getImage() {
        return mImage;
    }

    /**
     * sets image of plate
     * @param mImage
     */
    public void setImage(Image mImage) {
        this.mImage = mImage;
    }
}
