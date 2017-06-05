package com.special.ResideMenu;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * User: special
 * Date: 13-12-10
 * Time: ä¸‹å�ˆ11:05
 * Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout{

    /** menu item  icon  */
    private ImageView doc_icon;
    /** menu item  title */
    private TextView doc_title,txt_favorite,txnaira,commnaira,txcounter;
   // hiagent.setText(Html.fromHtml("&#8358;"));

    RelativeLayout linearLayout,lycomm;
  //  private LinearLayout/* linearLayout,*/layoutDoctor,layoutDoctor2;

    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int icon, int title, int iconVisibility) {
        super(context);
        initViews(context);

    }

    public ResideMenuItem(Context context, int icon, String title, int iconVisibility) {
        super(context);
        initViews(context);
      /*  iv_icon.setVisibility(iconVisibility);
        iv_icon.setImageResource(icon);
        tv_title.setText(title);*/
//        tv_title.setTypeface(null, Typeface.BOLD);
    }
    public ResideMenuItem(Context context, boolean gh) {
        super(context);
        initViews(context);
        //doc_icon.setVisibility(iconVisibility);int loader = R.drawable.loader;


        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView

        //  layoutDoctor.setVisibility(iconVisibility);


        //lycomm.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
//        tv_title.setTypeface(null, Typeface.BOLD);
    }

    public ResideMenuItem(Context context, int icon, String title, int iconVisibility,String left) {
        super(context);
        initViews(context);
        //doc_icon.setVisibility(iconVisibility);int loader = R.drawable.loader;


        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView

      //  layoutDoctor.setVisibility(iconVisibility);

        doc_icon.setImageResource(icon);
     //   doc_title.setText(title);
        linearLayout.setVisibility(View.GONE);
        if(title.equals("N")) {
            txcounter.setVisibility(View.GONE);
        }
        if(left.equals("3")){
            lycomm.setVisibility(View.VISIBLE);
        }else {
            lycomm.setVisibility(View.GONE);
        }
//        tv_title.setTypeface(null, Typeface.BOLD);
    }

    public ResideMenuItem(Context context, int icon, String title, int iconVisibility,int bottom) {
        super(context);
        initViews(context);
        //doc_icon.setVisibility(iconVisibility);
        //layoutDoctor.setVisibility(iconVisibility);
        doc_icon.setImageResource(icon);
        doc_title.setText(title);
        linearLayout.setVisibility(View.GONE);
        lycomm.setVisibility(View.GONE);
//        tv_title.setTypeface(null, Typeface.BOLD);
    }


    public ResideMenuItem(Context context,String buttonText) {
        super(context);
        initViews(context);
        //iv_icon.setVisibility(View.GONE);
        //iv_icon.setImageResource(View.GONE);
        linearLayout.setVisibility(View.GONE);
        lycomm.setVisibility(View.GONE);
//        tv_title.setTypeface(null, Typeface.BOLD);
    }
    public ResideMenuItem(Context context, int txtVisibility) {
        super(context);
        initViews(context);

    }


    private void initViews(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu_item, this);

        linearLayout = (RelativeLayout) findViewById(R.id.linearLayout);
        lycomm = (RelativeLayout) findViewById(R.id.lycomm);
        doc_icon = (ImageView) findViewById(R.id.doc_icon);
        doc_title = (TextView) findViewById(R.id.doc_name);
        txcounter = (TextView) findViewById(R.id.counter);
        txnaira = (TextView) findViewById(R.id.vv);
        commnaira = (TextView) findViewById(R.id.commamo);
        txnaira.setText(Html.fromHtml("&#8358;")+" 25,413.00");
        commnaira.setText(Html.fromHtml("&#8358;")+" 12,230.00");
  //      layoutDoctor = (RelativeLayout) findViewById(R.id.layoutDoctors);

    }

    /**
     * set the icon color;
     *
     * @param icon
     */

}
