package com.example.ratemyplate2;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


/**
 * Class for plate collection singleton
 */
public class PlateCollection {
        private static final String TAG = "PlateCollection";
        private static final String FILENAME = "plates.json";

        private ArrayList<Plate> mPlates;
        private RateMyPlateJSONSerializer mSerializer;

        private static PlateCollection sPlateCollection;
        private Context mAppContext;

    /**
     *
     * @param appContext
     */
    private PlateCollection(Context appContext){
            mAppContext = appContext;
            mSerializer = new RateMyPlateJSONSerializer(mAppContext, FILENAME);

            try {
                mPlates = mSerializer.loadPlates();
            } catch (Exception e) {
                mPlates = new ArrayList<>();
                Log.e(TAG, "Error loading plates: ", e);
            }
        }

    /**
     *
     * @param c
     * @return
     */
    public static PlateCollection get(Context c){
            if (sPlateCollection == null) {
                sPlateCollection = new PlateCollection(c.getApplicationContext());
            }
            return sPlateCollection;
        }

    /**
     * adds plate to collection
     * @param p
     */
    public void addPlate(Plate p){
            mPlates.add(p);
        }

    /**
     * removes plate from collection by position
     * @param i
     */
    public void removePlateByPosition(int i){
            mPlates.remove(i);
        }


    /**
     * saves plates
     * @return
     */
    public boolean savePlates() {
            try {
                mSerializer.savePlates(mPlates);
                Log.d(TAG, "plates saved to file");
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Error saving plates: ", e);
                return false;
            }
        }


    /**
     * gets plates
     * @return
     */
    public ArrayList<Plate> getPlates() {
            return mPlates;
        }

    /**
     * sets plates
     * @param mPlates
     */
    public void setPlates(ArrayList<Plate> mPlates) {
            this.mPlates = mPlates;
        }

    /**
     * gets app context
     * @return
     */
    public Context getAppContext() {
            return mAppContext;
        }

    /**
     * sets app context
     * @param mAppContext
     */
    public void setAppContext(Context mAppContext) {
            this.mAppContext = mAppContext;
        }
}
