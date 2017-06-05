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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import firstmob.firstbank.com.firstagent.R;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.MyViewHolder> {

	private List<Products> planetList;
	private Context context;
	private Filter planetFilter;

    private LayoutInflater inflater;

	/*public ProductsAdapter(List<Products> planetList, Context ctx) {
		super(ctx, R.layout.list_single, planetList);
		this.planetList = planetList;
		this.context = ctx;
		this.origPlanetList = planetList;
	}

	public int getCount() {
		return planetList.size();
	}

	public Products getItem(int position) {
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
			v = inflater.inflate(R.layout.prod_list, null);
			// Now we can fill the layout with the right values




			holder.name = txtTitle;

			holder.desc = txtDesc;


			v.setTag(holder);
		}
		else
			holder = (PlanetHolder) v.getTag();

		Products p = planetList.get(position);
		holder.name.setText(p.getName());

        holder.desc.setText(p.getHead());



		return v;
	}

	public void resetData() {
		planetList = origPlanetList;
	}*/


	/* *********************************
	 * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/
public NewProductsAdapter(Context context, List<Products> planetList) {
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

public Products getItem(int position) {
        return planetList.get(position);
        }

@Override
public NewProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.prod_newlist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

        }
public void onBindViewHolder(NewProductsAdapter.MyViewHolder holder, int position){


        Products p = planetList.get(position);
        holder.name.setText(p.getName());

        //holder.desc.setText(p.getHead());
    Log.v("Image Value", Integer.toString(p.getImg()));
    //holder.iv.setBackgroundResource(p.getImg());
   // holder.iv.setImageBitmap(BitmapFactory.decodeResource(Resources.getSystem(), p.getImg()));
        }
class MyViewHolder extends RecyclerView.ViewHolder  {
    public TextView name;
    //public TextView desc;
    public CircleImageView iv;

    public MyViewHolder(View v) {
        super(v);

       name = (TextView) v.findViewById(R.id.txt);
        //desc = (TextView) v.findViewById(R.id.txt2);
        iv = (CircleImageView) v.findViewById(R.id.item2);
    }


}

	


}
