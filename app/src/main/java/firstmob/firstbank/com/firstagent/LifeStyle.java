package firstmob.firstbank.com.firstagent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import adapter.ShopItem;
import adapter.adapter.MovieGridAdapter;


public class LifeStyle extends ActionBarActivity {
    GridView listView;

    List<ShopItem> items;
    MovieGridAdapter aAdpt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle);
      //  setToolbarTitle("Lifestyle");
        listView = (GridView) findViewById(R.id.listView);

        // Choose your own preferred column width

        items = new ArrayList<ShopItem>();

        // initialize your items array

        /*AsymmetricGridViewAdapter asymmetricAdapter = new AsymmetricGridViewAdapter<>(this, listView, adapter);
        listView.setAdapter(asymmetricAdapter);*/

        dummyList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p =    items.get(i).getDescription();
String title = items.get(i).getTitle();
                String url =    items.get(i).getImageUrl();

                Intent ii = new Intent(getApplicationContext(), MovieDetails.class);
                ii.putExtra("url", url);
                ii.putExtra("title", title);
                ii.putExtra("desc", title);
                startActivity(ii);
            }
        });
    }

    void dummyList() {
        ShopItem item = new ShopItem("The Grey: Fox Theatre, VI", "http://graphicdesignjunction.com/wp-content/uploads/2011/12/grey-movie-poster.jpg",
               ApplicationConstants.KEY_NAIRA + "3,000.00", "Grey is the color");
        items.add(item);
        item = new ShopItem("Finding Nemo: Fox Theatre, VI", "https://s-media-cache-ak0.pinimg.com/236x/41/2f/96/412f96d1d0a926436fc4cc2c755fac18.jpg",
                ApplicationConstants.KEY_NAIRA + "3,000.00", " A tale about a shark");
        items.add(item);
        aAdpt = new MovieGridAdapter(items, getApplicationContext());

        listView.setAdapter(aAdpt);


    }
}
