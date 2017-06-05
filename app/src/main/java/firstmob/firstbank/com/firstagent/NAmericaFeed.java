package firstmob.firstbank.com.firstagent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.Local_Adapter;
import rss.RssFeed;
import rss.RssItem;
import rss.RssReader;


public class NAmericaFeed extends Fragment {
ImageView imageView1;
    ListView l;
      String [][] sortedrss;
    String nImg;
    List<String> titl = new ArrayList<String>();
    public ListAdapter Adapt;
    public List<String> t_list  ;
    public List<String> desc_list;
    public List<String> l_list;
    public List<String> dt_list;
    public List<String> rsslink;
    public List<String> src_list;
    public List<String> ml_list;
    private ProgressBar progress;
    int r =0;

    public NAmericaFeed() {
        // Required empty public constructor
    }
  /*  private static Fragment newInstance(Context context) {
        LayoutOne f = new LayoutOne();

        return f;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.localnews, null);
        progress = (ProgressBar) root.findViewById(R.id.progressBar);
       t_list =   new ArrayList<String>();
        desc_list =   new ArrayList<String>();
       l_list =   new ArrayList<String>();
       rsslink =   new ArrayList<String>();
        dt_list =   new ArrayList<String>();
     l = (ListView) root.findViewById(R.id.lv);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               /* Intent viewMessage = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(rsslink.get(position).toString()));
                startActivity(viewMessage);*/

                Bundle b  = new Bundle();
                String urll = rsslink.get(position).toString();

                b.putString("url",urll);

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

        return root;
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            //	new SendTask().execute();
           new fetchnews(getActivity()).execute();
            //	RegTest();
            return true;
        } else {

            Toast.makeText(
                    getActivity(),
                    "No Internet Connection. Please check your internet setttings",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void StartChartAct(int i){


    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
    private class fetchnews extends AsyncTask<Void, Void, Void> {

        private volatile boolean running = true;
        private Context context;


        @SuppressWarnings("unused")
        Date today = new Date();

        String stImg ="http://www.standardmedia.co.ke/common/i/standard-digital-world-inner-page.png";


        public fetchnews(Context cxt) {
            context = cxt;

        }

        @Override
        protected void onPreExecute() {

            if(r==0){

                progress.setVisibility(View.VISIBLE);
                l.setVisibility(View.VISIBLE);
            }

        }




        protected Void doInBackground(Void... arg0) {
            // if (running) {
            System.out.println("BG for Standard");


                String surl = "http://allafrica.com/tools/headlines/rdf/usafrica/headlines.rdf";


                Date curDate = new Date();
                //	long oldMillis;
                long curMillis = curDate.getTime();


                URL sturl = null;
                try {
                    sturl = new URL(surl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                RssFeed sfeed = null;
                try {
                    sfeed = RssReader.read(sturl);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(!(sfeed == null)) {
                ArrayList<RssItem> srssItems = sfeed.getRssItems();


                int nsize = t_list.size();

                //  System.out.println("NSIZE: "+nsize);


                String[] tarr = new String[nsize];
                String[] descrarr = new String[nsize];
                String[] larr = new String[nsize];
                String[] dtarr = new String[nsize];

                tarr = t_list.toArray(new String[t_list.size()]);
                descrarr = desc_list.toArray(new String[desc_list.size()]);
                larr = l_list.toArray(new String[l_list.size()]);
                dtarr = dt_list.toArray(new String[dt_list.size()]);


                List<String> titles = new ArrayList<String>(srssItems.size());
                List<String> pubdate = new ArrayList<String>(srssItems.size());
                List<String> img = new ArrayList<String>(srssItems.size());
                List<String> mil = new ArrayList<String>(srssItems.size());

                rsslink = new ArrayList<String>(srssItems.size());
                List<String> desc = new ArrayList<String>(srssItems.size());


                for (int s = 0;s < srssItems.size();s++) {
                    RssItem rssItem = new RssItem();
                    rssItem.setTitle(srssItems.get(s).getTitle());
                    rssItem.setDescription(srssItems.get(s).getDescription());
                    rssItem.setLink(srssItems.get(s).getLink());
                    rssItem.setPubDate(srssItems.get(s).getPubDate());

                    if(!(titl.contains(rssItem.getTitle()))) {
                        titles.add(rssItem.getTitle());
                        titl.add(rssItem.getTitle());
                        desc.add(rssItem.getDescription());
                        rsslink.add(rssItem.getLink());
                        pubdate.add(String.valueOf(rssItem.getPubDate()));
                        img.add(stImg);
                        mil.add("" + 10000);
                    }
                    //  }
                }

                // progress.setVisibility(View.GONE);
                // l.setVisibility(View.GONE);

                Adapt = new Local_Adapter(getActivity(), titles, desc, rsslink, pubdate, img);
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            l.setAdapter((ListAdapter) Adapt);
                        }
                    });
                } catch (Throwable t) {
                    Log.v("STANDARD ERROR", "Error");
                }


            }

            return null;
        }


            protected void onPostExecute (Void result){
              /*  if (r == 1) {
                    pullToRefreshView.onRefreshComplete();
                    r = 0;
                }*/

                progress.setVisibility(View.GONE);
                l.setVisibility(View.VISIBLE);

            }
        }

        public Long dateconvert(String date) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
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
            }
            oldMillis = oldDate.getTime();


            return oldMillis;
        }

}
