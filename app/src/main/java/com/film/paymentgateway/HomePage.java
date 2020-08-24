package com.film.paymentgateway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easebuzz.payment.kit.PWECouponsActivity;
import com.google.android.gms.common.internal.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodels.PWEStaticDataModel;

public class HomePage extends AppCompatActivity {
    CheckBox check_bo1,checkbox12,checkbox_ad;
    private Spinner spinner1, spinner2;
    ArrayList<HashMap<String,String>> canType;
    ArrayList<HashMap<String,String>> array_trans;
    ArrayList<String> array1;
    ArrayList<String> array2;
    boolean userSelect = false;
    String amt_trans,can_type,mnthname;
      int  admission,annual,fine,fine_days,tptfee,MonthlyFeeAmount,TransportFeeAmount;
    GlobalClass globalClass;
    String  previous, current;
    ArrayAdapter<String> dataAdapter1,dataAdapter2;
    Customers_Spinner_Adapter customers_spinner_adapter;
    Transport_Spinner_Adapter transport_spinner_adapter;
    TextView tv_admission_fee_zero,tv_annual_zero,tv_transport_zero,tv_total,tv_paid,tv_fine,trsancation,pay,tv_admission_fee,tv_annual_fee,tv_tuition,tv_transport;
    String key="2PBP7IABZ2";
    String txnid="123456";
    double amount=200.00;
    double total=0.0;
    double input=0.0;
    double _new_input=0.0;
    double _new_transport=0.0;
    double str1,str2,str3,str4,str5,rs;
    String productinfo="dfgf";
    String firstname="Shadab";
    String email_id="shadabm@gmail.com";
    String udf1="";
    String udf2="";
    String udf3="";
    String udf4="";
    String udf5="";
    String udf6="";
    String udf7="";
    String udf8="";
    String udf9="";
    String udf10="";
    String salt="DAH88E3UWQ";
    String serverCalculatedHash;
    Shared_Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        trsancation=findViewById(R.id.trsancation);
        tv_annual_fee=findViewById(R.id.tv_annual_fee);
        tv_admission_fee=findViewById(R.id.tv_admission_fee);
        tv_transport=findViewById(R.id.tv_transport);
        tv_tuition=findViewById(R.id.tv_tuition);
        tv_fine=findViewById(R.id.fine);
        tv_paid=findViewById(R.id.tv_paid);
        tv_total=findViewById(R.id.tv_total);
        check_bo1=findViewById(R.id.check_bo1);
        checkbox12=findViewById(R.id.checkbox12);
        checkbox_ad=findViewById(R.id.checkbox_ad);
        tv_admission_fee_zero=findViewById(R.id.tv_admission_fee_zero);
        tv_annual_zero=findViewById(R.id.tv_annual_zero);
        tv_transport_zero=findViewById(R.id.tv_transport_zero);
        pay=findViewById(R.id.pay);
        spinner1 = (Spinner) findViewById(R.id.spinner2);
        spinner2 = (Spinner) findViewById(R.id.spinner1);
        canType = new ArrayList<>();
        array_trans = new ArrayList<>();

        CanType();



        Log.d("TAG", "onCreateT: "+_new_input);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverCalculatedHash  = encryptThisString(key+"|"+txnid+"|"+total+"|"+productinfo+"|"
                        +firstname+"|"+email_id+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+udf6+"|"+udf7+"|"+udf8+"|"+udf9+"|"+udf10+"|"+salt+"|"+key);

                Intent intentProceed = new Intent(HomePage.this, PWECouponsActivity.class);
                intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);     // This is mandatory flag
                intentProceed.putExtra("txnid",txnid);
                intentProceed.putExtra("amount",total);
                intentProceed.putExtra("productinfo",productinfo);
                intentProceed.putExtra("firstname",firstname);
                intentProceed.putExtra("email",email_id);
                intentProceed.putExtra("phone","9876543210");
                intentProceed.putExtra("key",key);
                intentProceed.putExtra("udf1",udf1);
                intentProceed.putExtra("udf2",udf2);
                intentProceed.putExtra("udf3",udf3);
                intentProceed.putExtra("udf4",udf4);
                intentProceed.putExtra("udf5",udf5);
                intentProceed.putExtra("udf6",udf6);
                intentProceed.putExtra("udf7",udf7);
                intentProceed.putExtra("udf8",udf8);
                intentProceed.putExtra("udf9",udf9);
                intentProceed.putExtra("udf10",udf10);
                intentProceed.putExtra("address1","customer_address1");
                intentProceed.putExtra("address2","customer_address2");
                intentProceed.putExtra("city","kolkata");
                intentProceed.putExtra("state","wb");
                intentProceed.putExtra("country","India");
                intentProceed.putExtra("zipcode","700104");
                intentProceed.putExtra("hash",serverCalculatedHash);
                intentProceed.putExtra("unique_id","jdhfjsd");
                intentProceed.putExtra("pay_mode","test");
                intentProceed.putExtra("split_payments","");
                intentProceed.putExtra("sub_merchant_id","");
                startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);


            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                can_type = canType.get(position).get("amt");
                input = Double.parseDouble(can_type);
                Log.d("TAG", "onItemSelected: " + input);
                tv_tuition.setText(String.valueOf(input));
                if (position > 0) {
                    HashMap<String, String> map_ser = (HashMap<String, String>) parent.getItemAtPosition(position);
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    mnthname = map_ser.get("mnthname");
                    can_type = map_ser.get("amt");
                    input = Double.parseDouble(can_type);
                    Log.d("TAG", "onItemSelected: " + input);

                    tv_tuition.setText(String.valueOf(input));
                    Log.d("Input", "Input: " + input);
/*
                input = Double.parseDouble(can_type);
                String stringdouble= Double.toString(input+total);
                tv_total.setText(stringdouble);
                Log.d("TAG", "onItemSelected: "+can_type);
                Log.d("TAG", "onItemSelected: "+position);*/

                    total = total + annual;
                    tv_total.setText(String.valueOf(total));
                    pay.setText("Pay " + getApplicationContext().getResources().getString(R.string.Rs) + " " + String.valueOf(total));
                    Log.d("TAG", "Total: " + total);
                    Log.d("TAG", "Total: " + total);
                    Log.d("TAG", "Totalannual: " + annual);
                    Log.d("TAG", "Totalfine: " + fine);
                    Log.d("TAG", "Totalstr5: " + str5);
                    Log.d("TAG", "Totaladmission: " + admission);
                    Log.d("TAG", "Totalinput: " + input);
                    // CanType();
                }
                //
                //   Add(can_type);
            }





            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                //   HashMap<String, String> map_ser = (HashMap<String, String>) parent.getItemAtPosition(position);
                //     amt_trans = array_trans.get(position).get("amt");



              /*  str5 = Double.parseDouble(amt_trans);
                total = fine + str5 + admission + input + annual;
                tv_total.setText(String.valueOf(total));
                tv_transport.setText(String.valueOf(amt_trans));
                pay.setText("Pay " + getApplicationContext().getResources().getString(R.string.Rs) + " " + String.valueOf(total));*/
               if (position > 0) {

                    HashMap<String, String> map_ser2 = (HashMap<String, String>) parent.getItemAtPosition(position);

                    mnthname = map_ser2.get("mnthname");
                    //  Toast.makeText(getApplicationContext(),"Tuition Fees of "+mnthname+" "+"is added",Toast.LENGTH_LONG).show();
                    amt_trans = map_ser2.get("amt");


                    str5 = Double.parseDouble(amt_trans);
                    globalClass.setName(String.valueOf(str5));
                    preference.savePrefrence();
                    //  _new_transport=_new_transport+str5;
                    tv_transport.setText(String.valueOf(amt_trans));
                    String stringdouble = Double.toString(total + str5);
                    // tv_total.setText(stringdouble);
                    //   globalClass.setPhone_number(stringdouble);

                    total =total + str5 ;
                    tv_total.setText(String.valueOf(total));
                    pay.setText("Pay " + getApplicationContext().getResources().getString(R.string.Rs) + " " + String.valueOf(total));
                    Log.d("TAG", "Total: " + total);
                    Log.d("TAG", "Totalannual: " + annual);
                    Log.d("TAG", "Totalfine: " + fine);
                    Log.d("TAG", "Totalstr5: " + str5);
                    Log.d("TAG", "Totaladmission: " + admission);
                    Log.d("TAG", "Totalinput: " + input);
                    //CanType();
                    //   Add(can_type);
                }


            }




            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        check_bo1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                  if (isChecked){
                      // perform logic

                      double annual1=annual;
                      total= total + annual1;
                      String stringdouble= Double.toString(total);
                      Log.d("TAG", "TOtalC: "+total);
                      Log.d("TAG", "TOtalC: "+annual);
                     // Log.d("TAG", "TOtalC: "+annual1);
                      tv_total.setText(stringdouble);
                      pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(total));
                      //CanType();
                      tv_annual_fee.setVisibility(View.VISIBLE);
                      tv_annual_zero.setVisibility(View.GONE);


                  }
                  else {

                    //  CanType();



                      double annual1=annual;
                      total= total - annual1;
                      Log.d("TAG", "TOtalU: "+total);
                      Log.d("TAG", "TOtalU: "+annual);
                    //  Log.d("TAG", "TOtalU: "+annual1);
                      String stringdouble= Double.toString(total);
                      Log.d("TAG", "TOtalU: "+stringdouble);
                      tv_total.setText(stringdouble);
                      pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(total));
                      tv_annual_fee.setVisibility(View.GONE);
                      tv_annual_zero.setVisibility(View.VISIBLE);
                  }
              }

                      }
        );
        checkbox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                  if (isChecked){
                      Log.d("Mytotal", "Mytotal "+total);
                      Log.d("Mytotal", "Mytotal "+(total+str5));

                      // perform logic
                      _new_input= total+str5;
                      String stringdouble= Double.toString(_new_input);
                      Log.d("TAG", "str5+: "+globalClass.getName());
                      Log.d("TAG", "onCheckedChangedStr: "+str5);

                      tv_total.setText(String.valueOf(_new_input));
                     pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(_new_input));
                       tv_transport.setText(globalClass.getName());
                      tv_transport.setVisibility(View.VISIBLE);
                      tv_transport_zero.setVisibility(View.GONE);
                      spinner1.setEnabled(true);
                      spinner1.setClickable(true);
                      spinner1.setAdapter(transport_spinner_adapter);


                  }
                  else {
                      Log.d("Mytotal", "Mytotal "+total);
                      Log.d("Mytotal", "Mytotal "+(total-str5));
                      _new_input= total-str5;
                      String stringdouble= Double.toString(total-str5);
                      Log.d("TAG", "onCheckedChanged+: "+total);
                      Log.d("TAG", "onCheckedChangedStr: "+str5);
                      pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(_new_input));

                      tv_total.setText(String.valueOf(_new_input));
                      tv_transport.setVisibility(View.GONE);
                      tv_transport_zero.setVisibility(View.VISIBLE);
                      spinner1.setEnabled(false);
                      spinner1.setClickable(false);
                      spinner1.setAdapter(transport_spinner_adapter);
                     // CanType();
                  }
              }

                      }
        );
        checkbox_ad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                  if (isChecked){
                      // perform logic
                     total= total+admission;
                      String stringdouble= Double.toString(total);
                      Log.d("TAG", "onCheckedChanged+: "+total);
                     tv_total.setText(stringdouble);
                      pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(total));
                      tv_admission_fee.setVisibility(View.VISIBLE);
                      tv_admission_fee_zero.setVisibility(View.GONE);
                      //CanType();

                  }
                  else {
                      total= total-admission;
                      String stringdouble= Double.toString(total);
                      Log.d("TAG", "onCheckedChanged+: "+total);
                      Log.d("TAG", "onCheckedChangedS+: "+str1);
                      pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(total));

                      tv_total.setText(stringdouble);
                      tv_admission_fee.setVisibility(View.GONE);
                      tv_admission_fee_zero.setVisibility(View.VISIBLE);
                     // CanType();
                  }
              }

                      }
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: "+requestCode);
        Log.d("TAG", "onActivityResult: "+resultCode);
        Log.d("TAG", "onActivityResult: "+data);
        if (data != null) {
            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                String result = data.getStringExtra("result");
                String payment_response = data.getStringExtra("payment_response");
                Log.d("TAG", "onActivityResult: "+payment_response.toString());
                try {
                    JSONObject jobj = new JSONObject(payment_response);
                    String status = jobj.getString("status");
                    Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();

                    // Handle response here
                } catch (Exception e) {
                    // Handle exception here
                }
            }
        }
    }

    public void trsancation(View view) {
        startActivity(new Intent(getApplicationContext(),Report.class));
    }
    public static String encryptThisString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private void CanType() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        canType.clear();


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
                        int regdno = jobj1.get("regdno").getAsInt();
                         admission = jobj1.get("admission").getAsInt();
                         annual = jobj1.get("annual").getAsInt();
                         fine = jobj1.get("fine").getAsInt();
                         fine_days = jobj1.get("fine_days").getAsInt();
                         tptfee = jobj1.get("tptfee").getAsInt();
                        MonthlyFeeAmount = jobj1.get("MonthlyFeeAmount").getAsInt();
                        TransportFeeAmount = jobj1.get("TransportFeeAmount").getAsInt();

                        tv_admission_fee.setText(String.valueOf(admission));
                        tv_annual_fee.setText(String.valueOf(annual));
                        tv_fine.setText("Late Fine :"+String.valueOf(fine)+"("+String.valueOf(fine_days)+ " "+"days"+")");
                        tv_transport.setText(String.valueOf(tptfee));


                        Log.d("TAG", "Input: "+input);
                      //  str5 = Double.parseDouble(can_type);
                        total=admission+annual+fine+MonthlyFeeAmount+TransportFeeAmount;
                        Log.d("TAG", "onResponse: "+total);


                        tv_total.setText(String.valueOf(total));
                        pay.setText("Pay "+getApplicationContext().getResources().getString(R.string.Rs)+" "+String.valueOf(total));
                        if(check_bo1.isChecked()){

                            tv_annual_fee.setVisibility(View.VISIBLE);
                            tv_annual_zero.setVisibility(View.GONE);
                        }
                        else {

                            tv_annual_fee.setVisibility(View.GONE);
                            tv_annual_zero.setVisibility(View.VISIBLE);

                        }


                        if(checkbox_ad.isChecked()){

                            tv_admission_fee.setVisibility(View.VISIBLE);
                            tv_admission_fee_zero.setVisibility(View.GONE);

                        }
                        else {
                            tv_admission_fee.setVisibility(View.GONE);
                            tv_admission_fee_zero.setVisibility(View.VISIBLE);
                        }
                       if(checkbox12.isChecked()){

                            tv_transport.setVisibility(View.VISIBLE);
                            tv_transport_zero.setVisibility(View.GONE);

                        }
                       else {
                           tv_transport.setVisibility(View.GONE);
                           tv_transport_zero.setVisibility(View.VISIBLE);
                       }

                        JsonArray jarray = jobj1.getAsJsonArray("feemnth");
                        array1 = new ArrayList<>();
                        for (int j = 0; j < jarray.size(); j++) {
                            JsonObject jobjmonth = jarray.get(j).getAsJsonObject();
                            //get the object

                            //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                            String item_name = jobjmonth.get("mnthname").toString().replaceAll("\"", "");
                            String amt = jobjmonth.get("amt").toString().replaceAll("\"", "");
                            if(item_name.equals("AllPaid")){
                                tv_tuition.setVisibility(View.GONE);
                              tv_paid.setVisibility(View.VISIBLE);
                              tv_fine.setVisibility(View.GONE);
                            }
                            HashMap<String, String> map_ser = new HashMap<>();


                            map_ser.put("mnthname", item_name);
                            map_ser.put("amt", amt);


                            canType.add(map_ser);

                            array1.add(item_name);


                        }
                        customers_spinner_adapter = new Customers_Spinner_Adapter(HomePage.this,canType);

                        spinner2.setAdapter(customers_spinner_adapter);



                        JsonArray transport = jobj1.getAsJsonArray("tptmnth");
                        array2 = new ArrayList<>();

                        for (int j = 0; j < transport.size(); j++) {
                            JsonObject jobjmonth = transport.get(j).getAsJsonObject();
                            //get the object

                            //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                            String item_name = jobjmonth.get("mnthname").toString().replaceAll("\"", "");
                            String amt = jobjmonth.get("amt").toString().replaceAll("\"", "");

                            HashMap<String, String> map_ser = new HashMap<>();


                            map_ser.put("mnthname", item_name);
                            map_ser.put("amt", amt);


                            array_trans.add(map_ser);

                            array2.add(item_name);


                        }
                        transport_spinner_adapter = new Transport_Spinner_Adapter(HomePage.this,array_trans);

                        spinner1.setAdapter(transport_spinner_adapter);

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
