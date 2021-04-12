package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;
import java.util.List;

public class EarthquackAdapter extends ArrayAdapter<Earthquack> {
    public EarthquackAdapter(Context context, List<Earthquack>earthquacks){
        super(context,0,earthquacks);
    }

    private static final String LOCATION_SEPERATOR=" of ";
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemview = convertView;
        if (listItemview == null) {
            listItemview = LayoutInflater.from(getContext()).inflate(R.layout.eathquake_list_item, parent, false);
        }

        Earthquack currentearthquack=(Earthquack)getItem(position);


        TextView magnitudetextview=(TextView)listItemview.findViewById(R.id.magnitude);

        String formattedmagnitude = formatMagnitude(currentearthquack.getMmagnitude());

        magnitudetextview.setText(formattedmagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudetextview.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentearthquack.getMmagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        String originallocation=currentearthquack.getMplace();

        String primarylocation;
        String locationoffset;

        if(originallocation.contains(LOCATION_SEPERATOR)){
            String[] parts=originallocation.split(LOCATION_SEPERATOR);
            locationoffset=parts[0] + LOCATION_SEPERATOR;
            primarylocation=parts[1];
        }
        else {
            locationoffset=getContext().getString(R.string.near_the);
            primarylocation=originallocation;
        }

        TextView primarylocationtextview = (TextView)listItemview.findViewById(R.id.primary_location);

        primarylocationtextview.setText(primarylocation);

        TextView locationoffsettextview =(TextView)listItemview.findViewById(R.id.location_offset);

        locationoffsettextview.setText(locationoffset);

        Date dateobject = new Date(currentearthquack.getMdate());

        TextView datetextview =(TextView)listItemview.findViewById(R.id.date);

        String formatteddate = formatDate(dateobject);

        datetextview.setText(formatteddate);

        TextView timetextView = (TextView)listItemview.findViewById(R.id.time);

        String formattedtime= formatTime(dateobject);

        timetextView.setText(formattedtime);

        return listItemview;
    }

    private String formatDate(Date dateobject){
        SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateobject);
    }

    private  String formatTime(Date dateobject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateobject);
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat decimalFormat= new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}

