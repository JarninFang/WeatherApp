package com.jarninfang.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    TextView forecastTextview;
    private ShareActionProvider shareActionProvider;

    private String mForecastStr;
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if(shareActionProvider != null) {
            shareActionProvider.setShareIntent(createShareIntent());
        } else {
            Log.d("DetailActivityFragment", "Share Action Provider is null");
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + FORECAST_SHARE_HASHTAG);

        return shareIntent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        mForecastStr = getForecastDataFromIntent();
        forecastTextview = (TextView) rootView.findViewById(R.id.detail_forecast_textview);
        forecastTextview.setText(mForecastStr);
        return rootView;
    }

    private String getForecastDataFromIntent() {
        String forecastData;
        Intent i = getActivity().getIntent();

        if((i != null) && i.hasExtra(Intent.EXTRA_TEXT)) {
            forecastData = i.getStringExtra(Intent.EXTRA_TEXT);
        }

        else {
            forecastData = "Error retrieving forecast";
        }


        return forecastData;
    }
}
