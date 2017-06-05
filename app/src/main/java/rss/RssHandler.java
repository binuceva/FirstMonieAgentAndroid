/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rss;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;

public class RssHandler extends DefaultHandler {
	
	private RssFeed rssFeed;
	private RssItem rssItem;
	private StringBuilder stringBuilder;
    
	String val;
	
	
	@Override
	public void startDocument() {
		rssFeed = new RssFeed();
	}
	
	/**
	 * Return the parsed RssFeed with it's RssItems
	 * @return
	 */
	public RssFeed getResult() {
		return rssFeed;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		stringBuilder = new StringBuilder();
		
		
		RootElement root = new RootElement("rss");
		 
		Element channel = root.getChild("channel");
		//Element item = channel.getChild(ITEM);
		Element item = channel.getChild("item");
		item.setEndElementListener(new EndElementListener(){
			public void end() {
				val="";
			}
		});
		item.getChild("title").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				val = (body);
				System.out.println("Rss Handler TITLE:"+ body);
			}
		});
		
		if (localName.trim().equals("thumbnail")) {          
	        String thumbnail = attributes.getValue("url");  
	     //   Log.i("IMAGEURL", thumbnail);
	        rssItem.setImage(thumbnail);
	    }
		if (localName.trim().equals("date")) {          
	        String dcdate = qName;
	        String dcdate2 = localName.trim();
	        String dcdate3 = attributes.getValue(qName);
	      System.out.println("Rss Handler DATE:"+ dcdate);
	      System.out.println("Rss Handler DATE2:"+ dcdate2);
	      System.out.println("Rss Handler DATE3:"+ dcdate3);
	     //   rssItem.setPubDate4(dcdate3);
	    }
		
		if (qName.equals("dc:date")) {          
	        String newsimage =localName ;  
	     System.out.println("qName1 dc:date: "+newsimage);
	   //     rssItem.setNewsImage(newsimage);
	    }
		
		
		if(qName.equals("item") && rssFeed != null) {
			rssItem = new RssItem();
			
			if (qName.equals("image")) {          
		        String newsimage = attributes.getValue("url");  
		     System.out.println("NEWSIMAGEURL: "+newsimage);
		        rssItem.setNewsImage(newsimage);
		    }
			
			if (qName.equals("dc:date")) {          
		        String newsimage = attributes.getValue("date");  
		     System.out.println("qName dc:date: "+newsimage);
		   //     rssItem.setNewsImage(newsimage);
		    }
			
			
			rssItem.setFeed(rssFeed);
			
		
			rssFeed.addRssItem(rssItem);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		stringBuilder.append(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
		
		if(rssFeed != null && rssItem == null) {
			// Parse feed properties
			
			try {
				if (qName != null && qName.length() > 0) {
				    String methodName = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);
				    Method method = rssFeed.getClass().getMethod(methodName, String.class);
				    method.invoke(rssFeed, stringBuilder.toString());
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			
		} else if (rssItem != null) {
			// Parse item properties
			
			try {
				if(qName.equals("content:encoded")) 
					qName = "content";
				String methodName = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);
				Method method = rssItem.getClass().getMethod(methodName, String.class);
				method.invoke(rssItem, stringBuilder.toString());
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		
	}

}
