package com.example.ratemyplate2;

import org.json.JSONException;
import org.json.JSONObject;

public class Image {
    private static final String JSON_FILENAME = "filename";


    private String mFileName;

    /** Create a image representing an existing file on disk */
    public Image(String filename){
        mFileName = filename;
    }

    public Image(JSONObject json) throws JSONException {
        mFileName = json.getString(JSON_FILENAME);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, mFileName);
        return json;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }


}
