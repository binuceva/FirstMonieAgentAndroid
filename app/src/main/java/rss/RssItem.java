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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class RssItem implements Comparable<RssItem>, Parcelable {

	private RssFeed feed;
	private String title;
	private String link;
	private Date pubDate;
	//private Date pubDate2;
	private String dt;
	private String description;
	private String content;
	private String imageUrl;
	private String newsimage;
	//private String dcdate;
	
	private String pubDate4;
	public RssItem() {
		
	}
	
	@SuppressLint("NewApi")
	public RssItem(Parcel source) {
		
		Bundle data = source.readBundle();
		title = data.getString("title");
		link = data.getString("link");
		//dt = data.getString("pubdate");
		dt = "date type2";
		Log.i("DT RSSITEM", dt);
		/*if(!data.getString("pubdate").equals(null)){
		dt = data.getString("pubdate");
		}else if(!data.getString("date").equals(null)){
				dt = "no date";//+data.getString("date");
						//data.getString("date");
				
				
				}else{
					dt = "cant recognize";
				}
			*/
		
		//dcdate = data.getString("dc:date");
	//	pubDate = (Date) data.getSerializable("\"dc:date\"");
	//	pubDate = (Date) data.getSerializable("\"dc:date\"");
	//	System.out.println("pubDate NATION DATE: "+pubDate);
		
		//pubDate2= (Date) data.getSerializable("pubdate");
		
		//dcdate = String.valueOf(data.getSerializable("dc&#58;date"));
		//dcdate = String.valueOf(data.getSerializable("dc:date"));
		
	//	System.out.println("dcdate NATION DATE: "+dcdate);
		
		
		description = data.getString("description");
		content = data.getString("content");
		feed = data.getParcelable("feed");
		
		
		imageUrl = getImage().toString().trim();
		newsimage = getNewsImage().toString().trim();
		//pubDate4 = getPubDate4().toString().trim();
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		Bundle data = new Bundle();
	
		data.putString("title", title);
		data.putString("link", link);
	//	data.putString("pubdate", dt);
		//data.putString("pubdate2", String.valueOf(pubDate2));
		//data.putString("dc:date", dcdate);
		//data.putSerializable("dcdate2", pubDate);
		data.putString("description", description);
		data.putString("image", imageUrl);
		data.putString("newsimage", imageUrl);
		data.putString("content", content);
		data.putParcelable("feed", feed);
		dest.writeBundle(data);
	}
	
	public static final Creator<RssItem> CREATOR = new Creator<RssItem>() {
		public RssItem createFromParcel(Parcel data) {
			return new RssItem(data);
		}
		public RssItem[] newArray(int size) {
			return new RssItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
	
	
	public String getImage(){
		return imageUrl;
	}
	
	public String getNewsImage(){
		return newsimage;
	}
	
	public void setImage( String imageUrl){
		this.imageUrl = imageUrl;
	}
	
	public void setNewsImage( String newsimage){
		this.newsimage = newsimage;
	}
	
	public RssFeed getFeed() {
		return feed;
	}

	public void setFeed(RssFeed feed) {
		this.feed = feed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	
/*	public String getPubDate2() {
		return dcdate;
	}

	public void setPubDate2(String dcdate) {
		//System.out.println("setPubDate2 NATION DATE: "+dcdate);
		this.dcdate = dcdate;
	}
	*/
	public String getPubDate() {
		return dt;
	}
	
	

	public void setPubDate(String dt) {
		this.dt = dt;
	}

	
	/*public Date getPubDate3() {
		return pubDate;
	}
	
	public void setPubDate3(Date dcdate2) {
		/*try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			this.pubDate = dateFormat.parse(dcdate2);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
//		this.pubDate = dcdate2;
//	}
	
	
/*	public String getPubDate4() {
		return dcdate;
	}

	public void setPubDate4(String dcdate) {
		//System.out.println("setPubDate2 NATION DATE: "+dcdate);
		this.dcdate = dcdate;
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/*public String getDate() {
		return dt;
	}

	public void setDate(String dt) {
		this.dt = dt;
	}
	*/

	@Override
	public int compareTo(RssItem another) {
		if(getPubDate() != null && another.getPubDate() != null) {
			return getPubDate().compareTo(another.getPubDate());
		}
		/*else if(getPubDate2() != null && another.getPubDate2() != null) {
			return getPubDate2().compareTo(another.getPubDate2());
		}*/
		else { 
			return 0;
		}
	}
	
}
