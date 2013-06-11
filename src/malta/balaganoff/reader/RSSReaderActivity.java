package malta.balaganoff.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RSSReaderActivity extends Activity {
	
	public static RSSItem selectedRssItem = null;

	  String feedUrl = "http://pois.donntu.edu.ua/ru/feed/rss.html?type=rss";
	  ListView rssListView = null;
	  ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
	  ArrayAdapter<RSSItem> aa = null;
	  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // get the listview from layout.xml

        rssListView = (ListView) findViewById(R.id.rssListView);

        // here we specify what to execute when individual list items clicked

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          //@Override

          public void onItemClick(AdapterView<?> av, View view, int index,

              long arg3) {

            selectedRssItem = rssItems.get(index);

            // we call the other activity that shows a single rss item in
            // one page

            Intent intent = new Intent(

                "malta.balaganoff.reader.displayRssItem");

            startActivity(intent);

          }

        });
        
        //adapters are used to populate list. they take a collection,

        //a view (in our example R.layout.list_item

    aa = new ArrayAdapter<RSSItem>(this, R.layout.list_item, rssItems);

        //here we bind array adapter to the list

    rssListView.setAdapter(aa);

     refreshRssList();

  }
    private void refreshRssList() {



        ArrayList<RSSItem> newItems = RSSItem.getRssItems(feedUrl);

        rssItems.clear();
        rssItems.addAll(newItems);

        aa.notifyDataSetChanged();

      }

    }