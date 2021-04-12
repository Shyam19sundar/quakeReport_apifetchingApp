package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquackLoader extends AsyncTaskLoader<List<Earthquack>> {
    private static final String LOG_CAT = EarthquackLoader.class.getName();

    private String mURL;

    public EarthquackLoader(Context context,String url){
        super(context);
        mURL=url;
    }


    protected void onstartloading(){
        forceLoad();
    }

    @Override

    public List<Earthquack> loadInBackground(){
        if(mURL==null){
            return null;
        }
        List<Earthquack> earthquacks=Queryutils.fetchEarthquakeData(mURL);
        return earthquacks;
    }
}
