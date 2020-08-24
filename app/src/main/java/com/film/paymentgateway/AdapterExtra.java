package com.film.paymentgateway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterExtra extends RecyclerView.Adapter<AdapterExtra.GroceryViewHolder>{
    ArrayList<HashMap<String,String>> horizontalGrocderyList;
    Context context;

    public AdapterExtra(ArrayList<HashMap<String,String>> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public AdapterExtra.GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_single_row, parent, false);
        AdapterExtra.GroceryViewHolder gvh = new AdapterExtra.GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(AdapterExtra.GroceryViewHolder holder, final int position) {

        if(horizontalGrocderyList.get(position).get("schoolstatus").equals("0")){
            holder.tv_company.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
        else if (horizontalGrocderyList.get(position).get("schoolstatus").equals("")){
            holder.tv_company.setTextColor(ContextCompat.getColor(context, R.color.red));

        } else {
            holder.tv_company.setTextColor(ContextCompat.getColor(context, R.color.green));

        }

        holder.date.setText(horizontalGrocderyList.get(position).get("dt"));
        holder.time.setText(horizontalGrocderyList.get(position).get("tme"));
        holder.tv_company.setText("Ref No:"+horizontalGrocderyList.get(position).get("refno")+"\n"+"\n"+"Cust ID :"+horizontalGrocderyList.get(position).get("cust_id"));
      //  holder.tv_c_status.setText(horizontalGrocderyList.get(position).get(""));
        holder.tv_verify.setText(context.getResources().getString(R.string.Rs)+horizontalGrocderyList.get(position).get("amt")+"\n"+"\n"+horizontalGrocderyList.get(position).get("status"));
    //    holder.tv_verifyy_i.setText(horizontalGrocderyList.get(position).get(""));
    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_main;
        TextView date,status,time,tv_company,tv_c_status,tv_verify,tv_verifyy_i;
        ImageView imageView;
        public GroceryViewHolder(View view) {
            super(view);
            date=view.findViewById(R.id.tv_date);
            time=view.findViewById(R.id.tv_time);

            tv_company=view.findViewById(R.id.tv_company);
          //  tv_c_status=view.findViewById(R.id.tv_c_status);
            tv_verify=view.findViewById(R.id.tv_verify);
         //   tv_verifyy_i=view.findViewById(R.id.tv_verifyy_i);

        }
    }
}