package com.film.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import datamodels.PWEStaticDataModel;

public class MainActivity extends AppCompatActivity {
TextView pay;
String key="2PBP7IABZ2";
        String txnid="123456";
        double amount=200.00;
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


    String serverCalculatedHash = encryptThisString(key+"|"+txnid+"|"+amount+"|"+productinfo+"|"
            +firstname+"|"+email_id+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+udf6+"|"+udf7+"|"+udf8+"|"+udf9+"|"+udf10+"|"+salt+"|"+key);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pay=findViewById(R.id.pay);

        Log.d("TAG", "onCreate: "+serverCalculatedHash);
        Log.d("TAG", "onCreate: "+key+"|"+txnid+"|"+amount+"|"+productinfo+"|"
                +firstname+"|"+email_id+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+udf6+"|"+udf7+"|"+udf8+"|"+udf9+"|"+udf10+"|"+salt+"|"+key);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProceed = new Intent(MainActivity.this, PWECouponsActivity.class);
                intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);     // This is mandatory flag
                intentProceed.putExtra("txnid",txnid);
                intentProceed.putExtra("amount",amount);
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
    } }
