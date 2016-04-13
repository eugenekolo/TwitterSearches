package com.deitel.twittersearches;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
//import com.google.api.translate.Language;
//import com.google.api.translate.Translate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TranslationActivity extends AppCompatActivity {
   private ArrayList<String> statuses; // List of trend names
   private HashMap<String, String> trendsUrls; // Hash map of trend names to their urls
   private SearchesAdapter adapter; // for binding data to RecyclerView
   private JSONObject statusesJson = null;
   private Integer DISPLAY_AMNT = 5; // Can make this go up to 10, but spec says 5

   // configures the GUI and registers event listeners
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_trend);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      statuses = new ArrayList<String>();
      trendsUrls = new HashMap<String, String>();

      RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      // create RecyclerView.Adapter to bind tags to the RecyclerView
      adapter = new SearchesAdapter(statuses, itemClickListener, itemLongClickListener);
      recyclerView.setAdapter(adapter);

      // specify a custom ItemDecorator to draw lines between list items
      recyclerView.addItemDecoration(new ItemDivider(this));

      ApiRequestTask apiRequest = new ApiRequestTask(new ApiRequestTask.ApiResponse() {
         public void onResponse(String response) {
            try {
               statusesJson = new JSONObject(response);
               JSONArray tmp = statusesJson.getJSONArray("statuses");
               for (int i = 0; i < 5; i++) {
                  String name = tmp.getJSONObject(i).getString("text");
                  statuses.add(name);
               }
               adapter.notifyDataSetChanged(); // update trends in RecyclerView
            } catch (Exception e){
               Util.print(Log.getStackTraceString(e));
            }
         }
      });

      if (MainActivity.APP_TOKEN != null) {
         Bundle extras = getIntent().getExtras();
         apiRequest.execute(" https://api.twitter.com/1.1/search/tweets.json?q=" + extras.getString("query", "UTF-8"),
            "GET", MainActivity.APP_TOKEN);
      }
   }


   // itemClickListener launches web browser to display search results
   private final OnClickListener itemClickListener =
      new OnClickListener() {
         @Override
         public void onClick(View view) {
            // get query string and create a URL representing the search
            String trend = ((TextView) view).getText().toString();
            String url = trendsUrls.get(trend);

            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(webIntent);
         }
      };

   // Does nothing ATM.
   private final OnLongClickListener itemLongClickListener =
      new OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
            return true;
         }
      };
}

