package com.example.ratemyplate2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


/**
 * JSON Serializer class to read json files.
 */
public class RateMyPlateJSONSerializer {
    private Context mContext;
    private String mFilename;

    /**
     * constructor
     * @param c
     * @param f
     */
    public RateMyPlateJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    /**
     * saves plates
     * @param plates
     * @throws JSONException
     * @throws IOException
     */
    public void savePlates(ArrayList<Plate> plates)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Plate p : plates)
            array.put(p.toJSON());

        //
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * loads plates
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<Plate> loadPlates() throws IOException, JSONException {
        ArrayList<Plate> plates = new ArrayList<Plate>();
        BufferedReader reader = null;
        try {
            //Open and read the file into a Stringbuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                //Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            //Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of plates from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                plates.add(new Plate(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            //Ignore this one, it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return plates;
    }
}
