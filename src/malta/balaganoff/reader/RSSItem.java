package malta.balaganoff.reader;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RSSItem {
      private String title;
	  private String description;
	  private Date pubDate;
	  private String link;

	  public RSSItem(String title, String description, Date pubDate, String link) {

	   this.title = title;
	   this.description = description;
	   this.pubDate = pubDate;
	   this.link = link;

	  }
	
	  public String getTitle(){
		  return this.title;
		  }

	  public String getLink() {
			return this.link;
		  }

	  public String getDescription()  {
			return this.description;
		  }

	  public Date getPubDate()  {
		   return this.pubDate;
		  }

	  public String toString() {

		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - hh:mm:ss");
		    String result = getTitle() + " \n ( " + sdf.format(this.getPubDate()) + " )";
		    return result;

		  }	  
	
	  public static ArrayList<RSSItem> getRssItems(String feedUrl) {
		
		  ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
		    //   RSSItem rssItemT = new RSSItem("Yandex news", "",
		    //   new Date(), "http://news.yandex.ru/export.html");
//		       rssItems.add(rssItemT);

		       try {
	      URL url = new URL(feedUrl);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	      if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	        InputStream is = conn.getInputStream();

	        //DocumentBuilderFactory, DocumentBuilder are used for
	        //xml parsing

	        DocumentBuilderFactory dbf = DocumentBuilderFactory
	        .newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();

	        //using db (Document Builder) parse xml data and assign

	        //it to Element
	        Document document = db.parse(is);
	        Element element = document.getDocumentElement();

	        //take rss nodes to NodeList

	        NodeList nodeList = element.getElementsByTagName("item");

	        if (nodeList.getLength() > 0) {
	          for (int i = 0; i < nodeList.getLength(); i++) {

	            //take each entry (corresponds to <item></item> tags in

	            //xml data

	            Element entry = (Element) nodeList.item(i);
	            Element _titleE = (Element) entry.getElementsByTagName(
	                "title").item(0);

	            Element _descriptionE = (Element) entry
	                .getElementsByTagName("description").item(0);
	            Element _pubDateE = (Element) entry
	                .getElementsByTagName("pubDate").item(0);
	            Element _linkE = (Element) entry.getElementsByTagName(
	               "link").item(0);
	            String _title = _titleE.getFirstChild().getNodeValue();
	            String _description = _descriptionE.getFirstChild().getNodeValue();
	            Date _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
	            String _link = _linkE.getFirstChild().getNodeValue();

	            //create RssItemObject and add it to the ArrayList
	            RSSItem rssItem = new RSSItem(_title, _description,
	               _pubDate, _link);
	            rssItems.add(rssItem);
	          }
	        }
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	   return rssItems;
		    }
	
	  
}
