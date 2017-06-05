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
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class AccountSetDefAdapter extends RecyclerView.Adapter<AccountSetDefAdapter.MyViewHolder>  implements View.OnClickListener {

	private List<AccountList> planetList;
    AccountList p;
	private Context context;
    String defaul;
    int pos;
    private boolean onBind;
    private List<AccountList> origPlanetList;
    private LayoutInflater inflater;


    Integer selected_position = -1;
String acc;






    public AccountSetDefAdapter(Context context, List<AccountList> planetList,String def) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.planetList = planetList;
        this.defaul = def;


    }

    public void delete(int position) {
        planetList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public AccountList getItem(int position) {
        return planetList.get(position);
    }

    @Override
    public AccountSetDefAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.def_acclist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(AccountSetDefAdapter.MyViewHolder holder, int position) {
         p = planetList.get(position);
        acc = p.getAccId();
        holder.accid.setText(p.getAccId());
        holder.accamo.setText(p.getAmo());
        holder.mRadio.setChecked(position == selected_position);

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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.checkBox){

        }
    }

   /* public long getItemId(int position) {
        return planetList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        PlanetHolder holder = new PlanetHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.def_acclist, null);
            // Now we can fill the layout with the right values
        TextView    accid = (TextView) v.findViewById(R.id.bname);
            // curr = (TextView) v.findViewById(R.id.curr);
        TextView    accamo = (TextView) v.findViewById(R.id.vv);
           CheckBox   checkBox = (CheckBox) v.findViewById(R.id.checkBox);




            holder.accid = accid;
            holder.chkbox = checkBox;

            holder.accamo = accamo;


            v.setTag(holder);
        }
        else
            holder = (PlanetHolder) v.getTag();

        AccountList p = planetList.get(position);
        acc = p.getAccId();
        holder.accid.setText(p.getAccId());
        holder.accamo.setText(p.getAmo());

      *//*  if(position==selected_position)
        {
            holder.chkbox.setChecked(true);
        }
        else
        {
            holder.chkbox.setChecked(false);
        }

        holder.chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    selected_position =  position;

                }
                else{
                    selected_position = -1;
                }
                notifyDataSetChanged();
            }
        });*//*



        return v;
    }

    public void resetData() {
        planetList = origPlanetList;
    }*/


	/* *********************************
	 * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView accid;
        //  public TextView curr;
        public TextView accamo;
        //public CheckBox chkbox;
        public RadioButton mRadio;

        public MyViewHolder(View v) {
            super(v);
            accid = (TextView) v.findViewById(R.id.bname);
            //chkbox = (CheckBox) v.findViewById(R.id.checkBox);
            accamo = (TextView) v.findViewById(R.id.vv);
            mRadio = (RadioButton) v.findViewById(R.id.radio);
            mRadio.setOnClickListener(this);
            //chkbox.setOnCheckedChangeListener(this);
        }

      /*  @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!onBind) {
                if (b) {
                    selected_position = pos;

                } else {
                    selected_position = -1;
                }
                notifyDataSetChanged();
            }
        }*/

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.radio){
                selected_position = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }


}
