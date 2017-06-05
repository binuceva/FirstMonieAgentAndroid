/*
 * Copyright (C) 2012 jfrankie (http://www.survivingwithandroid.com)
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
package adapter.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import firstmob.firstbank.com.firstagent.R;

public class TopFavAdapter extends RecyclerView.Adapter<TopFavAdapter.MyViewHolder>  {

    private List<FavList> planetList;
    private Context context;
    private Filter planetFilter;
    private List<Dashboard> origPlanetList;

    private LayoutInflater inflater;



    public TopFavAdapter(Context context, List<FavList> planetList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.planetList = planetList;



    }

    public void delete(int position) {
        planetList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public FavList getItem(int position) {
        return planetList.get(position);
    }

    public long getItemId(int position) {
        return planetList.get(position).hashCode();
    }

    @Override
    public TopFavAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.topfavlist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(TopFavAdapter.MyViewHolder holder, int position) {
        FavList p = planetList.get(position);
        holder.descr.setText(p.getName());

        holder.iv.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), p.getIdImg()));

/*pos = position;
        onBind = true;
 if(position==selected_position)
        {
            holder.chkbox.setChecked(true);
        }
        else
        {
            holder.chkbox.setChecked(false);
        }
        onBind = false;*/
        //  holder.chkbox.setOnClickListener(this);


    }





    class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView descr;
        public TextView price;
        public CircleImageView iv;

        public MyViewHolder(View v) {
            super(v);
            TextView txtTitle = (TextView) v.findViewById(R.id.txt);

            CircleImageView imgView = (CircleImageView)v.findViewById(R.id.img);




           descr = txtTitle;
iv = imgView;
        }

    }


}
