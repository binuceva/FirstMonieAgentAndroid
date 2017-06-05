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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class RvTopThreeAdapt extends RecyclerView.Adapter<RvTopThreeAdapt.MyViewHolder>  {

    private List<Dashboard> planetList;
    private Context context;
    private Filter planetFilter;
    private List<Dashboard> origPlanetList;

    private LayoutInflater inflater;



    public RvTopThreeAdapt(Context context, List<Dashboard> planetList) {
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

    public Dashboard getItem(int position) {
        return planetList.get(position);
    }

    public long getItemId(int position) {
        return planetList.get(position).hashCode();
    }

    @Override
    public RvTopThreeAdapt.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.topthreelist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RvTopThreeAdapt.MyViewHolder holder, int position) {
        Dashboard p = planetList.get(position);
        holder.descr.setText(p.getName());

        holder.iv.setBackgroundResource(p.getIdImg());

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
        public ImageView iv;

        public MyViewHolder(View v) {
            super(v);
            TextView txtTitle = (TextView) v.findViewById(R.id.txt);

            ImageView imgView = (ImageView)v.findViewById(R.id.img);




           descr = txtTitle;
iv = imgView;
        }

    }


}
