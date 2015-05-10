package com.example.punith.swipereddit;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class RedditResult extends ActionBarActivity {

    /**
     * This variable is the container that will host our cards
     */
    private CardContainer mCardContainer;
    private SimpleCardStackAdapter globalAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit_result);
        try {
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.JSON_STRING);
            JSONArray obj = new JSONObject(message).getJSONObject("data").getJSONArray("children");

            mCardContainer = (CardContainer) findViewById(R.id.layoutview);

            Resources r = getResources();

            globalAdapter = new SimpleCardStackAdapter(this);

            ArrayList<String> imageList = new ArrayList<String>();

            for (int i = 0; i < 10; i++) {
                String thumbnail = "";
                thumbnail = obj.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                imageList.add(thumbnail);
            }

           new DownloadImageTask(globalAdapter).execute(imageList);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {

        finish();
    }


    private class DownloadImageTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap>> {
        SimpleCardStackAdapter bmImage;

        public DownloadImageTask(SimpleCardStackAdapter bmImage) {
            this.bmImage = bmImage;
        }

        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... urls) {
            ArrayList<Bitmap> main = new ArrayList<Bitmap>();
            Iterator<String> itr = urls[0].iterator();
            while (itr.hasNext()) {
                String urldisplay = itr.next();
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                    main.add(mIcon11);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }


            return main;
        }


        protected void onPostExecute(ArrayList<Bitmap> result) {
            Iterator<Bitmap> itr = result.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Bitmap result1 = itr.next();
                i++;

                globalAdapter.add(new CardModel("", "", result1));
                mCardContainer.setAdapter(globalAdapter);
            }

        }
    }
}