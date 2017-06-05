package adapter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class Local_Adapter extends BaseAdapter {

	private final Context activity;
	private final String[] rsstitle;
	private final String[] rsslink;
	private final String[] date;
	private final String[] rssdescription;
	private final String[] imgurl;
	 
	
	// private final String[] total;
	private static LayoutInflater inflater = null;

	public Local_Adapter(Context context, List<String> titles, List<String> descriptions,
			List<String> link,List<String> pubdate,List<String> img) {
		activity = context;
		rsstitle = titles.toArray(new String[titles.size()]);
		rsslink = link.toArray(new String[link.size()]);
		rssdescription = descriptions.toArray(new String[descriptions.size()]);
		date = pubdate.toArray(new String[pubdate.size()]);
		imgurl = img.toArray(new String[img.size()]);
		System.out.println("TITLES SIZE:"+titles.size());
		System.out.println("IMG SIZE:"+img.size());
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return rsstitle.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.listitems, null);
		TextView title = (TextView) vi.findViewById(R.id.title);
		TextView rssdate = (TextView) vi.findViewById(R.id.date);
		//RelativeTimeTextView v = (RelativeTimeTextView) vi.findViewById(R.id.timestamp); //Or just use Butterknife!

		TextView desc = (TextView) vi.findViewById(R.id.description);
		ImageView avatar = (ImageView) vi.findViewById(R.id.avatar);

		String n = rsstitle[position];
		title.setText(n);
	
		
		/*------------------date display--------------------*/
			String dt = ""+ date[position].toString();
			
			SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
			formatter.setLenient(false);
			
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'");
			formatter2.setLenient(false);

        rssdate.setText(dt);

			
		//	rssdate.setText(dt);
			
		
		/*------------------description display--------------------*/
		String ds = ""+rssdescription[position];
		//Spanned sds = Html.fromHtml(ds);
		//String decodedString = StringEscapeUtils.unescapeHtml4(htmlEncodedString);
	    if(!ds.trim().equals(null) ||!ds.trim().equals("null") || !ds.trim().equals("")){
		    SpannableStringBuilder spannedStr = (SpannableStringBuilder) Html
		            .fromHtml(ds.trim());
		    Object[] spannedObjects = spannedStr.getSpans(0, spannedStr.length(),
		            Object.class);
		    for (int i = 0; i < spannedObjects.length; i++) {
		        if (spannedObjects[i] instanceof ImageSpan) {
		            ImageSpan imageSpan = (ImageSpan) spannedObjects[i];
		            spannedStr.replace(spannedStr.getSpanStart(imageSpan),
		                    spannedStr.getSpanEnd(imageSpan), "");
		        }
		    }
		   
		
		desc.setText(spannedStr.toString().trim());
	    }
	    if(ds.trim().equals(null) || ds.trim().equals("null") || ds.trim().equals("")){
	    	//desc.setText("");
	    	desc.setVisibility(View.GONE);
	    }
	
         String url = String.valueOf(imgurl[position]);
         Log.i("URL LIST", url);
		
		if( ! url.isEmpty() || "null".equals(url) || ! " ".equals(url) ){
			System.out.println("Avatar:"+position+"->"+url);
		new DownloadImageTask(avatar)
        .execute(url.replaceAll(" ", "%20"));
		}else{
			avatar.setVisibility(View.GONE);
		}
		return vi;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
		    this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
		    String urldisplay = urls[0];
		    Bitmap mIcon11 = null;
		    try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		    } catch (Exception e) {
		        Log.e("Error", e.getMessage());
		        e.printStackTrace();
		    }
		    return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
		    bmImage.setImageBitmap(result);
		}
		}
	
}