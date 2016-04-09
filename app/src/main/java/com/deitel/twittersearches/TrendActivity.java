// MainActivity.java
// Manages your favorite Twitter searches for easy
// access and display in the device's web browser
package com.deitel.twittersearches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrendActivity extends AppCompatActivity {
   private List<String> tags; // list of tags for saved searches
   private SearchesAdapter adapter; // for binding data to RecyclerView

   // configures the GUI and registers event listeners
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_trend);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      tags = new ArrayList<>();
      Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);

      RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      // create RecyclerView.Adapter to bind tags to the RecyclerView
      adapter = new SearchesAdapter(tags, itemClickListener, itemLongClickListener);
      recyclerView.setAdapter(adapter);

      // specify a custom ItemDecorator to draw lines between list items
      recyclerView.addItemDecoration(new ItemDivider(this));
   }

   // itemClickListener launches web browser to display search results
   private final OnClickListener itemClickListener =
      new OnClickListener() {
         @Override
         public void onClick(View view) {
            // get query string and create a URL representing the search
            String tag = ((TextView) view).getText().toString();
            //String urlString = getString(R.string.search_URL) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

            // create an Intent to launch a web browser
            //Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));

            //startActivity(webIntent); // show results in web browser
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

