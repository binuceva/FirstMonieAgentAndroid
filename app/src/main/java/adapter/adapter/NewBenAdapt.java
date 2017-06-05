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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class NewBenAdapt extends ArrayAdapter<NewBenList> implements Filterable {

	private List<NewBenList> planetList;
	private Context context;
	private Filter planetFilter;
	private List<NewBenList> origPlanetList;

	public NewBenAdapt(List<NewBenList> planetList, Context ctx) {
		super(ctx, R.layout.ben_list, planetList);
		this.planetList = planetList;
		this.context = ctx;
		this.origPlanetList = planetList;
	}
	
	public int getCount() {
		return planetList.size();
	}

	public NewBenList getItem(int position) {
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
			v = inflater.inflate(R.layout.ben_list, null);
			// Now we can fill the layout with the right values
			TextView accid = (TextView) v.findViewById(R.id.txt);
            TextView curr = (TextView) v.findViewById(R.id.txt2);
            TextView bank = (TextView) v.findViewById(R.id.txt9);
            TextView bran = (TextView) v.findViewById(R.id.txt8);
            TextView account = (TextView) v.findViewById(R.id.txt4);
            TextView btyp = (TextView) v.findViewById(R.id.txt11);

            RelativeLayout rmob = (RelativeLayout) v.findViewById(R.id.mobn);
            RelativeLayout racc = (RelativeLayout) v.findViewById(R.id.relacc);
            RelativeLayout rban = (RelativeLayout) v.findViewById(R.id.relbank);

			

			
			holder.txtname = accid;
            holder.txtmobno = curr;
            holder.txtacc = account;
            holder.txtbank = bank;
            holder.txtbranch = bran;
            holder.txtbtype = btyp;

            holder.relmob = rmob;
            holder.relacc = racc;
            holder.relbank = rban;

		
			
			v.setTag(holder);
		}
		else 
			holder = (PlanetHolder) v.getTag();

		NewBenList p = planetList.get(position);
        if(p.getBenType().equals("Mobile Money")){
            holder.relbank.setVisibility(View.GONE);
            holder.relacc.setVisibility(View.GONE);
            holder.relmob.setVisibility(View.VISIBLE);
        }
        if(p.getBenType().equals("Other Bank")){
            holder.relbank.setVisibility(View.VISIBLE);
            holder.relacc.setVisibility(View.VISIBLE);
            holder.relmob.setVisibility(View.GONE);
        }
        if(p.getBenType().equals("Within Bank")){
            holder.relbank.setVisibility(View.GONE);
            holder.relacc.setVisibility(View.VISIBLE);
            holder.relmob.setVisibility(View.GONE);
        }
		holder.txtname.setText(p.getBenName());
        holder.txtmobno.setText(p.getBenmob());
        holder.txtacc.setText(p.getAcc());
        holder.txtbank.setText(p.getBank());
        holder.txtbranch.setText(p.getBranch());
        holder.txtbtype.setText(p.getBenType());



		
		
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
		public TextView txtname;
		public TextView txtmobno;
        public TextView txtacc;
        public TextView txtbtype;
        public TextView txtbank;
        public TextView txtbranch;

        public RelativeLayout relmob;
        public RelativeLayout relacc;
        public RelativeLayout relbank;


	}
	

	


}
