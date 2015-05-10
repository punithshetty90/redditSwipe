
package com.example.punith.swipereddit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends Activity {

    public final static String JSON_STRING = "reddit_json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void redditPage(View view) {
        try {
            Log.e("infinite", "infinite");
            new JSONParse().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            Button imageButton = (Button) findViewById(R.id.imageButton);
            imageButton.setVisibility(View.INVISIBLE);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setVisibility(View.INVISIBLE);


            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            StringBuilder sb = new StringBuilder();
            JSONObject json = null;
            String url = new String();
            try {
                url = "http://www.reddit.com/r/pics/top.json";
                json = jParser.getJSONFromUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Intent intent = new Intent(MainActivity.this, RedditResult.class);
            try {
                intent.putExtra(MainActivity.JSON_STRING, json.toString());
                startActivity(intent);
                Button imageButton = (Button) findViewById(R.id.imageButton);
                imageButton.setVisibility(View.VISIBLE);
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setVisibility(View.VISIBLE);

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Internet Issue: Try again", Toast.LENGTH_LONG).show();

                Button imageButton = (Button) findViewById(R.id.imageButton);
                imageButton.setVisibility(View.VISIBLE);
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setVisibility(View.VISIBLE);

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
                e.printStackTrace();
            }
        }

    }
}
