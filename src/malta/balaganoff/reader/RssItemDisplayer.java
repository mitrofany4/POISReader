package malta.balaganoff.reader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class RssItemDisplayer extends Activity {
	
	
	@Override

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.rss_item_displayer);
	    RSSItem selectedRssItem = RSSReaderActivity.selectedRssItem;

	    //Bundle extras = getIntent().getExtras();

	    TextView titleTv = (TextView)findViewById(R.id.itemtitleTextView);
	//    TextView contentTv = (TextView)findViewById(R.id.contentTextView);
	    WebView webTv = (WebView)findViewById(R.id.contentWebView);
	    String title = "";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - hh:mm");
   title = "\n" + selectedRssItem.getTitle() + " \n ( "
	        + sdf.format(selectedRssItem.getPubDate()) + " ) \n";
	   
	/*    content += selectedRssItem.getDescription() + "\n"
	        + selectedRssItem.getLink();*/
	    titleTv.setText(title);
	    Document doc = null;
	    try {
//			doc = Jsoup.connect(selectedRssItem.getLink()).get();
            URL url = new URL(selectedRssItem.getLink());
            doc = Jsoup.parse(url,5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Elements intro = doc.select(".itemIntroText");
        Elements fulltext = doc.select(".itemFullText");

	    Elements media = fulltext.select("[src]");
	    for (Element img : media){
	    	if (img.tagName().equals("img")){
	    		img.attr("width","300 dp");
	    	}
	    }

	    Elements span = fulltext.select("span");
	    for (Element e : span){
	    	String s = e.attr("style").toString();
	    	boolean b=e.attr("style").equals("display: none");
	    	if (b) e.empty();
	    }
        
	    String html = intro.html()+fulltext.html();


	    String clean = Jsoup.clean(html, "http://pois.donntu.edu.ua/", Whitelist.basicWithImages());


        String aditional ="";
        Document descr = Jsoup.parse(selectedRssItem.getDescription());

        Elements links = descr.select("a");
        if (links.size()>0){
        aditional = "<p><strong class = \"download\"><ins>Вложения:</ins></strong></p>";

        for(Element link:links){
            String href = link.attr("href");
            String name = link.html();

            aditional=aditional+"<div class = \"download\"><a href = "+href+">"+name+"</a></div>";
        }
        aditional=aditional+"</div>";
        }

        String style = "<head><style type=\"text/css\">"+
                " p, div, span { text-align: justify; }  " +
                ".download{font-size:18px}" +
                "</style></head>";

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+style+"<body>"+clean+aditional+"</body>";
        webTv.loadDataWithBaseURL(null, header , "text/html", null, null);
	}
}
