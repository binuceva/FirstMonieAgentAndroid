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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class ForexAdapter extends ArrayAdapter<Forex> implements Filterable {

	private List<Forex> planetList;
	private Context context;
	private Filter planetFilter;
	private List<Forex> origPlanetList;

	public ForexAdapter(List<Forex> planetList, Context ctx) {
		super(ctx, R.layout.list_single, planetList);
		this.planetList = planetList;
		this.context = ctx;
		this.origPlanetList = planetList;
	}
	
	public int getCount() {
		return planetList.size();
	}

	public Forex getItem(int position) {
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
			v = inflater.inflate(R.layout.fx_list, null);
			// Now we can fill the layout with the right values
			TextView txtcurr = (TextView) v.findViewById(R.id.txt);
            TextView txtby = (TextView) v.findViewById(R.id.txt4);
            TextView txtsell = (TextView) v.findViewById(R.id.txt5);

			
			holder.curr = txtcurr;
            holder.sell = txtsell;
			holder.buy = txtby;
		
			
			v.setTag(holder);
		}
		else 
			holder = (PlanetHolder) v.getTag();
		
		Forex p = planetList.get(position);
		holder.curr.setText(p.getCurr());
        holder.sell.setText(p.getSell());
        holder.buy.setText(p.getBuy());

		
		
		return v;
	}

	public void resetData() {
		planetList = origPlanetList;
	}
	
	
	/* *********************************
	 * We use the holder pattern        
	 * It makes the view faster and avoid finding the component
	 * **********************************/
	
	private static class PlanetHolder {
		public TextView curr;
		public TextView buy;
        public TextView sell;

	}
	

	


}
