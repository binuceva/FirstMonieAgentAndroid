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
package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class ImeiRvAdapter extends RecyclerView.Adapter<ImeiRvAdapter.MyViewHolder>   {

	private List<ImeiList> planetList;
	private Context context;
	private Filter planetFilter;
    private LayoutInflater inflater;
	private List<ImeiList> origPlanetList;

/*	public ImeiRvAdapter(List<ImeiList> planetList, Context ctx) {
		super(ctx, R.layout.list_single, planetList);
		this.planetList = planetList;
		this.context = ctx;
		this.origPlanetList = planetList;
	}*/
	
	/*public int getCount() {
		return planetList.size();
	}

	public ImeiList getItem(int position) {
		return planetList.get(position);
	}

	public long getItemId(int position) {
		return planetList.get(position).hashCode();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		PlanetHolder holder = new PlanetHolder();
		
		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.imei_list, null);
			// Now we can fill the layout with the right values
			TextView txtcurr = (TextView) v.findViewById(R.id.txt);
            TextView txtimei = (TextView) v.findViewById(R.id.txt11);

			
			holder.curr = txtcurr;
            holder.dtype = txtimei;

		
			
			v.setTag(holder);
		}
		else 
			holder = (PlanetHolder) v.getTag();
		
		ImeiList p = planetList.get(position);
        String dtype = p.getDtype();
        if(dtype == null){
            dtype = "N/A";
        }

		holder.curr.setText(dtype);
        holder.dtype.setText(p.getImei() + " (Imei No)");


		
		return v;
	}

	public void resetData() {
		planetList = origPlanetList;
	}*/
	
	
	/* *********************************
	 * We use the holder pattern        
	 * It makes the view faster and avoid finding the component
	 * **********************************/

    public ImeiRvAdapter(Context context, List<ImeiList> planetList) {
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

    public ImeiList getItem(int position) {
        return planetList.get(position);
    }

    @Override
    public ImeiRvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.imei_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ImeiRvAdapter.MyViewHolder holder, int position) {
        ImeiList p = planetList.get(position);
        String dtype = p.getDtype();
        if(dtype == null){
            dtype = "N/A";
        }

      holder.curr.setText(dtype);
        holder.dtype.setText(p.getImei() + " (Imei No)");

    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView curr;
        public TextView dtype;

        public MyViewHolder(View v) {
            super(v);
            TextView txtcurr = (TextView) v.findViewById(R.id.txt);
            TextView txtimei = (TextView) v.findViewById(R.id.txt11);


            curr = txtcurr;
            dtype = txtimei;
        }


    }
	

	


}
