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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import firstmob.firstbank.com.firstagent.R;

public class ChqListAdapt extends RecyclerView.Adapter<ChqListAdapt.MyViewHolder> {

	private List<ChequeAdapter> planetList;
	private Context context;

    private LayoutInflater inflater;

	/*public AccountRecListAdapter(List<AccountList> planetList, Context ctx) {
		super(ctx, R.layout.list_single, planetList);
		this.planetList = planetList;
		this.context = ctx;
		this.origPlanetList = planetList;
	}

	public int getCount() {
		return planetList.size();
	}

	public AccountList getItem(int position) {
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
			v = inflater.inflate(R.layout.acc_list, null);
			// Now we can fill the layout with the right values
			TextView accid = (TextView) v.findViewById(R.id.bname);
            TextView curr = (TextView) v.findViewById(R.id.sbpid);
            TextView accamo = (TextView) v.findViewById(R.id.vv);





			holder.accid = accid;
            holder.curr = curr;

			holder.accamo = accamo;


			v.setTag(holder);
		}
		else
			holder = (PlanetHolder) v.getTag();

		AccountList p = planetList.get(position);
		holder.accid.setText(p.getAccId());
        holder.accamo.setText(p.getAmo());
        holder.curr.setText(p.getCurr());




		return v;
	}

	public void resetData() {
		planetList = origPlanetList;
	}


	*//* *********************************
	 * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************//*

	private static class PlanetHolder {
		public TextView accid;
		public TextView curr;
        public TextView accamo;
	}*/


    public ChqListAdapt(Context context, List<ChequeAdapter> planetList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.planetList = planetList;


    }



    public void delete(int position) {
        planetList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cheq_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       ChequeAdapter p = planetList.get(position);
       // holder.img.setBackgroundResource(p.getCDImg());
String bytedata = p.getImgData();

        Log.v("Byte Data",bytedata);
        //byte[] byteArray =bytedata.getBytes();
        byte[] byteArray = Base64.decode(bytedata.getBytes(), Base64.DEFAULT);
        if(byteArray != null) {

            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            holder.img.setImageBitmap(bmp);
        }
       /* try {
            byte[] imgbytes = Base64.decode(bytedata, Base64.DEFAULT | Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,
                    imgbytes.length);
            holder.img.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }*/

     /* String  path = Environment.getExternalStorageDirectory() + File.separator + "cache" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        path += "gh.jpg";
        byte[] bytes = bytedata.getBytes();
        // File imgFile = new  File(root + "/req_images/CamProfile.jpg");
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(path));
            out.write(bytes);
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        //holder.curr.setText(p.getCurr());

    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;


        public MyViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.cdimage);

        }
    }


}
