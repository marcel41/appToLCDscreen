package com.example.appwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button sendingButton;
    Button pictureTest;
    TextView textView;
    TextView writeText;
    TableLayout tableXD;
    String pictureToSend = "0000000000110111101100000001000111011111";
    // Instantiate the RequestQueue.
    //String url ="http://www.google.com";
    //String url ="http://10.8.12.191/rd";
    String url ="http://10.8.12.191/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendingButton =  (Button)findViewById(R.id.send);

        textView = (TextView) findViewById(R.id.text);
        writeText = (TextView) findViewById(R.id.stringToSend);
        tableXD = (TableLayout) findViewById(R.id.tablePapu);
        pictureTest = (Button)findViewById(R.id.gettingPicture);

        //Log.w("TAG", ""+tableXD.getChildCount());
        for (int i = 0, j = tableXD.getChildCount(); i < j; i++){

            View viewTableRow = tableXD.getChildAt(i);
           // Log.w("TAG", "onCreate: " + i);
            if(viewTableRow instanceof TableRow)
            {
                TableRow row = (TableRow) viewTableRow;
                //Log.w("TAG", ""+row.getChildCount());
                for(int rowi = 0, rowj = row.getChildCount(); rowi < rowj; rowi++)
                {
                    Log.w("TAG", "WORKING");
                    View textViewBlock = row.getChildAt(rowi);
                    if(textViewBlock instanceof TextView){
                        textViewBlock.setOnClickListener(this);
                    }
                }
            }
        }
        sendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatingPicture();
                Log.w("XD", "MORTAL-KOMBAT");
                final RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                Log.w("xd", "help");
//                                textView.setText("Response is: "+ response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        textView.setText("That didn't work!");
//                        error.printStackTrace();
//                        queue.stop();
//                    }
//
//                });

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.w("xd", "help");
                                textView.setText("Response is: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("ERROR COMING", "onErrorResponse: ");
                        textView.setText("That didn't work!");
                        error.printStackTrace();
                        queue.stop();
                    }

                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        //params.put("strToPrint",writeText.getText().toString());
                        params.put("strToPrint",pictureToSend);
                        return params;
                    }
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<String, String>();
//                        params.put("Content-Type","application/x-www-form-urlencoded");
//                        return params;
//                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<>();
                        params.put("Content-Type","application/x-www-form-urlencoded"); //-> this is the key
                        return params;
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(postRequest);
            }
        });
        pictureTest.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Log.w("TAG", ""+tableXD.getChildCount());
                pictureToSend = "";
                for (int i = 0, j = tableXD.getChildCount(); i < j; i++){

                    View viewTableRow = tableXD.getChildAt(i);
                    // Log.w("TAG", "onCreate: " + i);
                    if(viewTableRow instanceof TableRow)
                    {
                        TableRow row = (TableRow) viewTableRow;
                        //Log.w("TAG", ""+row.getChildCount());
                        for(int rowi = 0, rowj = row.getChildCount(); rowi < rowj; rowi++)
                        {
                            Log.w("TAG", "WORKING");
                            View textViewBlock = row.getChildAt(rowi);
                            if(textViewBlock.getBackground().getConstantState() == getResources().getDrawable(R.drawable.border).getConstantState())
                                pictureToSend = pictureToSend.concat("0");
                            else
                                pictureToSend = pictureToSend.concat("1");
                        }
                    }
                    //pictureToSend = pictureToSend.concat("*");
                }
                Log.w("TAG", ">>" + pictureToSend);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Log.w("TAG", "onClick: ");
        if(v.getBackground().getConstantState() == getResources().getDrawable(R.drawable.border).getConstantState())
            v.setBackgroundColor(Color.parseColor("#000000"));
        else
            v.setBackgroundResource(R.drawable.border);
        switch (v.getId())
        {
            //handle multiple view click events
        }
    }
    public void updatingPicture(){
        pictureToSend = "";
        for (int i = 0, j = tableXD.getChildCount(); i < j; i++){

            View viewTableRow = tableXD.getChildAt(i);
            // Log.w("TAG", "onCreate: " + i);
            if(viewTableRow instanceof TableRow)
            {
                TableRow row = (TableRow) viewTableRow;
                //Log.w("TAG", ""+row.getChildCount());
                for(int rowi = 0, rowj = row.getChildCount(); rowi < rowj; rowi++)
                {
                    Log.w("TAG", "WORKING");
                    View textViewBlock = row.getChildAt(rowi);
                    if(textViewBlock.getBackground().getConstantState() == getResources().getDrawable(R.drawable.border).getConstantState())
                        pictureToSend = pictureToSend.concat("0");
                    else
                        pictureToSend = pictureToSend.concat("1");
                }
            }
            //pictureToSend = pictureToSend.concat("*");
        }
        Log.w("TAG", ">>" + pictureToSend);
    }
}
