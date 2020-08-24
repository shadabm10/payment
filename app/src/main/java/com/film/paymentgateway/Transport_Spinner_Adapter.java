package com.film.paymentgateway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Developer on 5/11/17.
 */

public class Transport_Spinner_Adapter extends BaseAdapter{

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> list_array;
    private String cust_id;
    private GlobalClass globalClass;


    public Transport_Spinner_Adapter(Context context, ArrayList<HashMap<String, String>> array
                                     ) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list_array = array;
        this.cust_id = cust_id;

        globalClass = (GlobalClass) context.getApplicationContext();


    }


    public int getCount() {
        return list_array.size();
    }

    public Object getItem(int position) {
        return list_array.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.single, null, true);

        TextView tv_name = rowView.findViewById(R.id.tv_singel);
        String mnthname = list_array.get(position).get("mnthname");
        tv_name.setText(mnthname);





        return rowView;
    }



    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }






}
