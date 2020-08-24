package com.film.paymentgateway;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Report extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    private List<News> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager1;
    private AdapterExtra groceryAdapter;
    private AdapterExtra groceryAdapter1;
    ArrayList<HashMap<String,String>> canType;
    ArrayList<HashMap<String,String>> array_trans;
    ArrayList<HashMap<String,String>> array_trans1;
    String selectedItemText,amt_trans,can_type,mnthname,admission,annual,fine,fine_days,tptfee;
    ArrayAdapter<String> dataAdapter1,dataAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        recyclerView=findViewById(R.id.recycler);
        groceryRecyclerView=findViewById(R.id.recycler1);
        spinner1=findViewById(R.id.spinner1);
        List<String> type = new ArrayList<String>();

        type.add("Success");
        type.add("Failed");
      //  groceryAdapter = new AdapterExtra(groceryList1, getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        groceryRecyclerView.setLayoutManager(layoutManager1);
      //  recyclerView.setAdapter(groceryAdapter);
        canType = new ArrayList<>();
        array_trans = new ArrayList<>();
        array_trans1 = new ArrayList<>();
         dataAdapter1 = new ArrayAdapter(Report.this, android.R.layout.simple_spinner_item, type);
          spinner1.setAdapter(dataAdapter1);
      //  CanType("Success");
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                 selectedItemText = (String) parent.getItemAtPosition(position);
                Log.d("TAG", "onItemSelected: "+selectedItemText);
               CanType(selectedItemText);



            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        //   populategroceryList1();
    }
    private void CanType(final String selectedItemText) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        canType.clear();
        array_trans.clear();
        array_trans1.clear();


        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.CAN_TYPE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "JOB RESPONSE: " + response.toString());




                Gson gson = new Gson();

                try {


                    JsonArray array = gson.fromJson(response, JsonArray.class);
                    for (int i = 0; i < array.size(); i++){
                        JsonObject jobj1 = array.get(i).getAsJsonObject();
                        String regdno = jobj1.get("regdno").getAsString().replaceAll("\"", "");
                        admission = jobj1.get("admission").getAsString().replaceAll("\"", "");
                        annual = jobj1.get("annual").getAsString().replaceAll("\"", "");
                        fine = jobj1.get("fine").getAsString().replaceAll("\"", "");
                        fine_days = jobj1.get("fine_days").getAsString().replaceAll("\"", "");
                        tptfee = jobj1.get("tptfee").getAsString().replaceAll("\"", "");

                      //  tv_admission_fee.setText(admission);
                      //  tv_annual_fee.setText(annual);
                      //  tv_fine.setText("Late Fee Fine :"+fine+".00"+"("+fine_days+ " "+"days"+")");
                      //  tv_transport.setText(tptfee);

                      //  str1 = Double.parseDouble(admission);
                       // str2 = Double.parseDouble(annual);
                      //  str3 = Double.parseDouble(fine);
                       // str4 = Double.parseDouble(tptfee);
                        //  str5 = Double.parseDouble(can_type);
                       // total=str1+str2+str3+str4+rs;
/*
                        serverCalculatedHash  = encryptThisString(key+"|"+txnid+"|"+total+"|"+productinfo+"|"
                                +firstname+"|"+email_id+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+udf6+"|"+udf7+"|"+udf8+"|"+udf9+"|"+udf10+"|"+salt+"|"+key);
*/
                      //  Log.d("TAG", "onResponse: "+total);

                     //   String stringdouble= Double.toString(total);
                     //   tv_total.setText(stringdouble);
                        JsonArray jarray = jobj1.getAsJsonArray("feemnth");
                     //   array1 = new ArrayList<>();
                        for (int j = 0; j < jarray.size(); j++) {
                            JsonObject jobjmonth = jarray.get(j).getAsJsonObject();
                            //get the object

                            //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                            String item_name = jobjmonth.get("mnthname").toString().replaceAll("\"", "");
                            String amt = jobjmonth.get("amt").toString().replaceAll("\"", "");
                            if(item_name.equals("AllPaid")){
                              //  tv_tuition.setVisibility(View.GONE);
                             //   tv_paid.setVisibility(View.VISIBLE);
                              //  tv_fine.setVisibility(View.GONE);
                            }
                            HashMap<String, String> map_ser = new HashMap<>();


                            map_ser.put("mnthname", item_name);
                            map_ser.put("amt", amt);


                            canType.add(map_ser);

                          //  array1.add(item_name);


                        }
                       // dataAdapter2 = new ArrayAdapter(HomePage.this, android.R.layout.simple_spinner_item, array1);

                      //  spinner2.setAdapter(dataAdapter2);


                        JsonArray transport = jobj1.getAsJsonArray("tptmnth");
                       // array2 = new ArrayList<>();
                        for (int j = 0; j < transport.size(); j++) {
                            JsonObject jobjmonth = transport.get(j).getAsJsonObject();
                            //get the object

                            //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                            String item_name = jobjmonth.get("mnthname").toString().replaceAll("\"", "");
                            String amt = jobjmonth.get("amt").toString().replaceAll("\"", "");

                            HashMap<String, String> map_ser = new HashMap<>();


                            map_ser.put("mnthname", item_name);
                            map_ser.put("amt", amt);


                         //   array_trans.add(map_ser);

                          //  array2.add(item_name);


                        }
                      //  dataAdapter1 = new ArrayAdapter(HomePage.this, android.R.layout.simple_spinner_item, array2);
                      //  spinner1.setAdapter(dataAdapter1);

                      JsonArray trsansaction = jobj1.getAsJsonArray("thistory");
                       // array2 = new ArrayList<>();
                        for (int j = 0; j < trsansaction.size(); j++) {
                            JsonObject jobjmonth = trsansaction.get(0).getAsJsonObject();
                            //get the object

                            if(selectedItemText.equals("Success")){
                                recyclerView.setVisibility(View.VISIBLE);
                                groceryRecyclerView.setVisibility(View.GONE);

                                JsonArray succ = jobjmonth.getAsJsonArray("success");
                                Log.d("TAG", "onResponse: "+succ.size());
                                for (int k = 0; k< succ.size(); k++) {
                                    JsonObject succ1 = succ.get(k).getAsJsonObject();
                                    String status = succ1.get("status").toString().replaceAll("\"", "");
                                    String srno = succ1.get("srno").toString().replaceAll("\"", "");
                                    String dt = succ1.get("dt").toString().replaceAll("\"", "");
                                    String cust_id = succ1.get("cust_id").toString().replaceAll("\"", "");
                                    String refno = succ1.get("refno").toString().replaceAll("\"", "");
                                    String tme = succ1.get("tme").toString().replaceAll("\"", "");
                                    String Errstatus = succ1.get("Errstatus").toString().replaceAll("\"", "");
                                    String ErrDescription = succ1.get("ErrDescription").toString().replaceAll("\"", "");
                                    String schoolstatus = succ1.get("schoolstatus").toString().replaceAll("\"", "");
                                    String gateway = succ1.get("gateway").toString().replaceAll("\"", "");
                                    String amt = succ1.get("amt").toString().replaceAll("\"", "");
                                    HashMap<String, String> map_ser = new HashMap<>();


                                    map_ser.put("status", status);
                                    map_ser.put("srno", srno);
                                    map_ser.put("dt", dt);
                                    map_ser.put("cust_id", cust_id);
                                    map_ser.put("refno", refno);
                                    map_ser.put("tme", tme);
                                    map_ser.put("Errstatus", Errstatus);
                                    map_ser.put("ErrDescription", ErrDescription);
                                    map_ser.put("schoolstatus", schoolstatus);
                                    map_ser.put("gateway", gateway);
                                    map_ser.put("amt", amt);
                                    array_trans.add(map_ser);
                                }
                                //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                                groceryAdapter = new AdapterExtra(array_trans, getApplicationContext());
                                recyclerView.setAdapter(groceryAdapter);
                            }

                            else if(selectedItemText.equals("Failed")){
                                groceryRecyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                JsonArray fail = jobjmonth.getAsJsonArray("failed");
                                for (int l = 0; l < fail.size(); l++) {
                                    JsonObject succ1 = fail.get(l).getAsJsonObject();
                                    String status = succ1.get("status").toString().replaceAll("\"", "");
                                    String srno = succ1.get("srno").toString().replaceAll("\"", "");
                                    String dt = succ1.get("dt").toString().replaceAll("\"", "");
                                    String cust_id = succ1.get("cust_id").toString().replaceAll("\"", "");
                                    String refno = succ1.get("refno").toString().replaceAll("\"", "");
                                    String tme = succ1.get("tme").toString().replaceAll("\"", "");
                                    String Errstatus = succ1.get("Errstatus").toString().replaceAll("\"", "");
                                    String ErrDescription = succ1.get("ErrDescription").toString().replaceAll("\"", "");
                                    String schoolstatus = succ1.get("schoolstatus").toString().replaceAll("\"", "");
                                    String gateway = succ1.get("gateway").toString().replaceAll("\"", "");
                                    String amt = succ1.get("amt").toString().replaceAll("\"", "");
                                    HashMap<String, String> map_ser = new HashMap<>();


                                    map_ser.put("status", status);
                                    map_ser.put("srno", srno);
                                    map_ser.put("dt", dt);
                                    map_ser.put("cust_id", cust_id);
                                    map_ser.put("refno", refno);
                                    map_ser.put("tme", tme);
                                    map_ser.put("Errstatus", Errstatus);
                                    map_ser.put("ErrDescription", ErrDescription);
                                    map_ser.put("schoolstatus", schoolstatus);
                                    map_ser.put("gateway", gateway);
                                    map_ser.put("amt", amt);
                                    array_trans1.add(map_ser);
                                }
                                //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                                groceryAdapter1 = new AdapterExtra(array_trans1, getApplicationContext());
                                groceryRecyclerView.setAdapter(groceryAdapter1);
                            }



                          //  array2.add(item_name);


                        }

                    }





                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "DATA NOT FOUND: " + error.getMessage());

            }
        }) ;

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));


    }

}
