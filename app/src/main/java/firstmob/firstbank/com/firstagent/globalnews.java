package firstmob.firstbank.com.firstagent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.Local_Adapter;

//import com.sun.syndication.feed.synd.SyndContent;


@SuppressLint("NewApi")


public class globalnews extends Fragment
{
    /** Called when the activity is first created. */
    private final ArrayList<String> list = new ArrayList<String>();
    private ListView listv;
    private ArrayAdapter<String> adapter = null;
   // private SyndContent desc;
   public ListAdapter Adapt;
    private ArrayList<String> d = new ArrayList<String>() ;
    private List<String> rsslink;
    
    String [][] sortedrss;
    
    private List<Message> messages;



    int r =0;
    
	private ProgressBar progress;
	LinearLayout l;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    
    	 View rootView = inflater.inflate(R.layout.localnews, container, false);
    	 if (android.os.Build.VERSION.SDK_INT > 9) {
    		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		    StrictMode.setThreadPolicy(policy);
    		}
    	 
    	 
    	 progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
 		progress.setVisibility(View.GONE);
 		l = (LinearLayout) rootView.findViewById(R.id.progressBarLayout);
 		l.setVisibility(View.GONE);
 		
 		progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"),
 	            android.graphics.PorterDuff.Mode.MULTIPLY);

        listv = (ListView) rootView.findViewById(R.id.lv);
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				Bundle b  = new Bundle();
				String urll = rsslink.get(position).toString();

				b.putString("url", urll);

				Fragment  fragment = new NewsWebv();

				Activity activity123 = getActivity();

				if(activity123 instanceof MainActivity) {
					String title = "News";
					fragment.setArguments(b);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					//  String tag = Integer.toString(title);
					fragmentTransaction.add(R.id.container_body, fragment, title);
					fragmentTransaction.addToBackStack(title);
					((MainActivity)getActivity())
							.setActionBarTitle(title);
					fragmentTransaction.commit();
				}
				if(activity123 instanceof SignInActivity) {
					String title = "News";
					fragment.setArguments(b);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					//  String tag = Integer.toString(title);
					fragmentTransaction.add(R.id.container_body, fragment,title);
					fragmentTransaction.addToBackStack(title);

					fragmentTransaction.commit();
				}
				if(activity123 instanceof News) {
					Intent intent = new Intent(getActivity(), NewsWebvActiv.class);
					intent.putExtras(b);
					startActivity(intent);
				}



			}
        });
        progress.setVisibility(View.GONE);
        checkInternetConnection();
    		
    		return rootView;

        }
   

/*
   @Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(rsslink.get(position-1).toString()));
		this.startActivity(viewMessage);
	}*/


	
	
	//--------------Connecting to Internet-----------------------//
		private boolean checkInternetConnection() {
			ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo() != null
					&& cm.getActiveNetworkInfo().isAvailable()
					&& cm.getActiveNetworkInfo().isConnected()) {
			
				new fetchglobalnews(getActivity()).execute();
				
				
				return true;
			} else {
				r=0;
				Toast.makeText(getActivity().getBaseContext(),
						"No Internet Connection. Please check your internet setttings",
						Toast.LENGTH_LONG).show();
				return false;
			}
		}

	
	
		
		
		private class fetchglobalnews extends AsyncTask<Void, Void, Void> {
			//public ProgressDialog progressDialog;
			private Context context;
		  //  ProgressDialog dialog;
		    
		    String ajImg ="http://www.aljazeera.com/assets/images/AJLOGOGOOGLE.png";
		    String cnnImg = "http://i.cdn.turner.com/cnn/.e/img/1.0/logo/cnn.logo.rss.gif";
		   String bbcImg ="http://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif";
		        public fetchglobalnews(Context cxt) {
		            context = cxt;
		          //  dialog = new ProgressDialog(context);
		        }

			@Override
			protected void onPreExecute() {
			//	progressDialog = ProgressDialog();
			//  progressDialog.setCancelable(true);
				// dialog.setTitle("Fetching global news");
				 if(r==0){
			        	//dialog.show();
					 progress.setVisibility(View.VISIBLE);
					 l.setVisibility(View.VISIBLE);
			        	}
			}


			protected Void doInBackground(Void... arg0) {
				System.out.println("Doing in BG");
				 try{
				 String cnn ="http://rss.cnn.com/rss/cnn_topstories.rss";
						//		"http://rss.cnn.com/rss/cnn_crime.rss";
				String aj="http://www.aljazeera.com/xml/rss/all.xml";
				String bbc ="http://feeds.bbci.co.uk/news/rss.xml";
				
							  URL cnnurl = new URL(cnn);
							  URL ajurl = new URL(aj);
							  URL bbcurl = new URL(bbc);
					  rss.RssFeed cnnfeed = rss.RssReader.read(cnnurl);
					  rss.RssFeed ajfeed = rss.RssReader.read(ajurl);
					  rss.RssFeed bbcfeed = rss.RssReader.read(bbcurl);

					  ArrayList<rss.RssItem> cnnrssItems = cnnfeed.getRssItems();
					  ArrayList<rss.RssItem> ajrssItems = ajfeed.getRssItems();
					  ArrayList<rss.RssItem> bbcrssItems = bbcfeed.getRssItems();

					  List<String> titles = new ArrayList<String>(cnnrssItems.size()+ajrssItems.size()+bbcrssItems.size());
					  List<String> pubdate = new ArrayList<String>(cnnrssItems.size()+ajrssItems.size()+bbcrssItems.size());
					  List<String> img = new ArrayList<String>(cnnrssItems.size()+ajrssItems.size()+bbcrssItems.size());
					  List<String> mil = new ArrayList<String>(cnnrssItems.size()+ajrssItems.size()+bbcrssItems.size());
					  
					  rsslink = new ArrayList<String>(cnnrssItems.size()+ajrssItems.size()+bbcrssItems.size());
				    	List<String> desc = new ArrayList<String>(cnnrssItems.size()+ajrssItems.size()+bbcrssItems.size());
				    	
				    	
				    	
				    	
				    	Date curDate = new Date();
						long oldMillis;
						long curMillis = curDate.getTime();
				    	
						
				    	//sortedrss = ;
				    	
				    	
				    	
					  for(rss.RssItem rssItem : cnnrssItems) {
					     // Log.i("CNN RSS Reader", rssItem.getTitle());
					     // Log.i("CNN DESC", rssItem.getDescription());
					    //  Log.i("CNN DATE",String.valueOf(rssItem.getPubDate()));
						//  Log.e("curMillis", String.valueOf(curMillis));
					      long pubmillis = dateconvert(rssItem.getPubDate());
					      
					     long timedif = curMillis - pubmillis;
					       if(timedif < 8640000){
					    	//   Log.i("CNN TIME DIFF",String.valueOf(timedif));
					      titles.add(rssItem.getTitle());
					      desc.add(rssItem.getDescription());
					     rsslink.add(rssItem.getLink());
					      pubdate.add(String.valueOf(rssItem.getPubDate()));
					     // img.add(rssItem.getImage());
					      
					      mil.add(""+pubmillis) ; 
					      img.add(cnnImg);
					      
					      
					       }
					  }
					  
					  for(rss.RssItem rssItem : ajrssItems) {
						  
						  long pubmillis = dateconvert(rssItem.getPubDate());
					      
					      long timedif =  curMillis - pubmillis;
					       if(timedif < 8640000){
						  
					     // Log.i("AJ RSS Reader", rssItem.getTitle());
					     // Log.i("AJ DESC", rssItem.getDescription());
					     // Log.i("AJ DATE",String.valueOf(rssItem.getPubDate()));
					    //	   Log.i("AJ",String.valueOf(timedif));
					      titles.add(rssItem.getTitle());
					      desc.add(rssItem.getDescription());
					      rsslink.add(rssItem.getLink());
					      pubdate.add(String.valueOf(rssItem.getPubDate()));
					      mil.add(""+pubmillis) ; 
					      img.add(ajImg);
					     
					      
					       }
					       
					       
					       
					  }
					  for(rss.RssItem rssItem : bbcrssItems) {
						  long pubmillis = dateconvert(rssItem.getPubDate());
					      
					      long timedif = curMillis - pubmillis;
					       if(timedif < 8640000){
					     // Log.i("BBC RSS Reader", rssItem.getTitle());
					     // Log.i("BBC DESC", rssItem.getDescription());
					     // Log.i("BBC DATE",String.valueOf(rssItem.getPubDate()));
					    	//   Log.i("BBC TIME DIFF",String.valueOf(timedif));
					      titles.add(rssItem.getTitle());
					      desc.add(rssItem.getDescription());
					      rsslink.add(rssItem.getLink());
					      pubdate.add(String.valueOf(rssItem.getPubDate()));
					      mil.add(""+pubmillis) ; 
					      img.add(bbcImg);
					    
					      
					       }
					  }
					  
					     
					  	sortedrss = new String[titles.size()][6];
					  	 int i=0;
					    for(int rssi = 0;rssi<titles.size();rssi++){
					   
						    	  
						  sortedrss[ i][0] = mil.get(rssi);
					      sortedrss[ i][1] = titles.get(rssi);
					      sortedrss[ i][2] = desc.get(rssi) ;
					      sortedrss[ i][3] = rsslink.get(rssi);
					      sortedrss[ i][4] = img.get(rssi);
					      sortedrss[ i][5] = pubdate.get(rssi);
					      
						    	 i++; 
						    	
					    
					  }
					    Log.i("COUNT", ""+i); 
					  
					    Log.i("RSS SIZE", ""+titles.size());
					    Log.i("SORTED SIZE", ""+sortedrss.length);
					
			 List<String> titles2 = new ArrayList<String>(i);
			 List<String> pubdate2 = new ArrayList<String>(i);
		     List<String> img2 = new ArrayList<String>(i);			  
		      rsslink = new ArrayList<String>(i);
		   	List<String> desc2 = new ArrayList<String>(i);
			List<String> mil2 = new ArrayList<String>(i);
					    	
					    	
	
					    	
				int n = sortedrss.length;
	            int k;
	            for (int m = n; m >= 0; m--) {
		           for (int i1 = 0; i1 < m - 1; i1++) {
                     k = i1 + 1;
                     System.out.println("Is"+Long.parseLong(sortedrss[i1][0])+">"+
                    		 Long.parseLong(sortedrss[k][0]));
                     
	                    if (Long.parseLong(sortedrss[i1][0]) < Long.parseLong(sortedrss[k][0])) {
					                       // swapNumbers(i, k, array);
					           	String tempmillis,temptitle,tempdesc,templink,tempimg,tempdate;
					           	tempmillis = sortedrss[i1][0];
					           	temptitle = sortedrss[i1][1];
					           	tempdesc = sortedrss[i1][2];
					           	templink = sortedrss[i1][3];
					           	tempimg = sortedrss[i1][4];
					           	tempdate = sortedrss[i1][5];
					           	
		                        sortedrss[i1][0] = sortedrss[k][0];
		                        sortedrss[i1][1] = sortedrss[k][1];
		                        sortedrss[i1][2] = sortedrss[k][2];
		                        sortedrss[i1][3] = sortedrss[k][3];
		                        sortedrss[i1][4] = sortedrss[k][4];
		                        sortedrss[i1][5] = sortedrss[k][5];
		                        
			                    sortedrss[k][0] = tempmillis;
			                    sortedrss[k][1] = temptitle;
			                    sortedrss[k][2] = tempdesc;
			                    sortedrss[k][3] = templink;
			                    sortedrss[k][4] = tempimg;
			                    sortedrss[k][5] = tempdate;
			                    
					                    	
					            }
					          }
			          }			    	
					    						    	
				    	
					    	
	  for(int iz=0; iz<sortedrss.length; iz++){
						 				   	  
		if(!sortedrss[ iz][1].equals("") || !sortedrss[ iz][1].equals(null)){
				titles2.add(""+String.valueOf(sortedrss[iz][1]));
			     desc2.add(""+String.valueOf(sortedrss[iz][2]));
		      rsslink.add(""+ String.valueOf(sortedrss[iz][3]));
			     pubdate2.add(""+String.valueOf(sortedrss[iz][5]));
			     img2.add( ""+String.valueOf(sortedrss[iz][4]));
			     mil2.add( ""+String.valueOf(sortedrss[iz][0]));
			     
						 
			  }
		//Log.i("NEW TITLES", titles2.get(iz)); 
		Log.i("NEW DATES", pubdate2.get(iz)); 
	//	 Log.i("NEW RSS COUNT", "rss:"+titles2.size()); 
		  } 
					  
		System.out.println("Final RSS COUNT-"+ "rss:"+String.valueOf(rsslink.size())); 
					  
		  //dialog.dismiss();
                 //    Adapt = new (getActivity().getBaseContext(), titles, desc, rsslink, pubdate, img);

                     Adapt = new Local_Adapter(getActivity().getBaseContext(), titles2,desc2, rsslink,pubdate2,img2) {
        };
				    	
	  	getActivity().runOnUiThread(new Runnable() {
		    public void run() {
                listv.setAdapter((android.widget.ListAdapter) Adapt);
		    	    }
			   	});
	  	     } catch (Throwable t){
 	     }
		
				return null;
			}

			protected void onPostExecute(Void result) {
			/*	 if(r==1){
		            	pullToRefreshView.onRefreshComplete();
		            	r=0;
		            }*/
				//dialog.dismiss();
                progress.setVisibility(View.GONE);
                l.setVisibility(View.VISIBLE);
				
			}
			
			
			
		}
	
	
		
		
		public Long dateconvert(String date){
			SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
			formatter.setLenient(false);

			Date curDate = new Date();
			long oldMillis;
			long curMillis = curDate.getTime();
			String curTime = formatter.format(curDate);

			String oldTime = date;
			Date oldDate = null;
			try {
				oldDate = formatter.parse(oldTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'EDT'");
				try {
					oldDate = formatter2.parse(oldTime);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			 oldMillis = oldDate.getTime();
			//Log.e("FORMAT DATE", String.valueOf(oldDate));
			Log.e("OLD MILLIS", String.valueOf(oldMillis));
			return oldMillis;
		}
		

		public Long dateconvert2(Date date){
			SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
			formatter.setLenient(false);

			Date curDate = new Date();
			long oldMillis;
			long curMillis = curDate.getTime();
			String curTime = String.valueOf(formatter.format(curDate));

			String oldTime = String.valueOf(date);
			Date oldDate = null;
			try {
				oldDate = formatter.parse(oldTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 oldMillis = oldDate.getTime();
			//Log.e("FORMAT DATE", String.valueOf(oldDate));
			Log.e("OLD MILLIS", String.valueOf(oldMillis));
			return oldMillis;
		}
		

}