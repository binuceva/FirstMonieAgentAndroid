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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import adapter.ShopItem;
import firstmob.firstbank.com.firstagent.R;

public class MovieGridAdapter extends ArrayAdapter<ShopItem> implements Filterable {

	private List<ShopItem> planetList;
	private Context context;
	private Filter planetFilter;
	private List<ShopItem> origPlanetList;

	public MovieGridAdapter(List<ShopItem> planetList, Context ctx) {
		super(ctx, R.layout.list_single, planetList);
		this.planetList = planetList;
		this.context = ctx;
		this.origPlanetList = planetList;
	}

	public int getCount() {
		return planetList.size();
	}

	public ShopItem getItem(int position) {
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
			v = inflater.inflate(R.layout.shop_row, null);
			// Now we can fill the layout with the right values


			holder.price = (TextView) v.findViewById(R.id.shop_price);
			holder.title = (TextView) v.findViewById(R.id.shop_title);
			holder.pic = (ImageView) v.findViewById(R.id.shop_image);
			holder.progressBar = (ProgressBar) v.findViewById(R.id.shop_progress);


			v.setTag(holder);
		}
		else
			holder = (PlanetHolder) v.getTag();

		ShopItem p = planetList.get(position);
		holder.price.setText(p.getPrice());
		holder.title.setText(p.getTitle());
		final PlanetHolder finalHolder = holder;
		Picasso.with(context)
				.load(p.getImageUrl())
				.into(holder.pic, new com.squareup.picasso.Callback() {
					@Override
					public void onSuccess() {
						//do smth when picture is loaded successfully
						finalHolder.progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onError() {
						//do smth when there is picture loading error
					}
				});


		
		
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
		TextView price;
		TextView title;
		ImageView pic;
	 ProgressBar progressBar;
	}
	

	


}
