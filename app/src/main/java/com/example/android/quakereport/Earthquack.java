package com.example.android.quakereport;

public class Earthquack {

    private Double mmagnitude;

    private String mplace;

    private long mdate;

    private String murl;

    public Earthquack(Double magnitude,String place,long date,String url){
        mmagnitude=magnitude;
        mplace=place;
        mdate=date;
        murl=url;
    }

    public Double getMmagnitude(){
        return mmagnitude;
    }

    public String getMplace(){
        return mplace;
    }

    public long getMdate(){
        return mdate;
    }

    public String getMurl(){return murl;}
}
