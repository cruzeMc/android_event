package com.dreamchaser.cruze.android1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dreamchaser.cruze.android1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerButton = (Button) findViewById(R.id.register_user);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText fnameField = (EditText) findViewById(R.id.first_name);
                String fnameText = fnameField.getText().toString();

                EditText lnameField = (EditText) findViewById(R.id.last_name);
                String lnameText = lnameField.getText().toString();

                EditText emailField = (EditText) findViewById(R.id.getEmail);
                String emailText = emailField.getText().toString();

                EditText password1Field = (EditText) findViewById(R.id.password1);
                String password1Text = password1Field.getText().toString();

                EditText password2Field = (EditText) findViewById(R.id.password2);
                String password2Text = password2Field.getText().toString();

                RadioGroup genderField = (RadioGroup) findViewById(R.id.gender);
                int selectId = genderField.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton) findViewById(selectId);
                String genderText = radioButton.getText().toString();

//                Toast.makeText(RegisterActivity.this, radioButton.getText(), Toast.LENGTH_LONG).show();
//                String strJson = "{\"email\""+":"+emailText+","+"\"password\""+":"+password2Text+"}";
                String strJson = "{\"email\""+":"+emailText+","+"\"fname\""+":"+fnameText+","+"\"lname\""+":"+lnameText+","+"\"gender\""+":"+genderText+","+"\"password\""+":"+password2Text+"}";

                if((fnameText == null && fnameText.isEmpty()) || (lnameText == null && fnameText.isEmpty()) || (emailText == null && emailText.isEmpty()) || (genderText == null && genderText.isEmpty())){
                    Toast.makeText(getApplicationContext(), "Missing Field", Toast.LENGTH_LONG).show();
                }

                else if (!((password1Text).equals(password2Text))){
                    Toast.makeText(getApplicationContext(), "Mismatched Password", Toast.LENGTH_LONG).show();
                }

                else{
                    try{
                        JSONObject jsonObject = new JSONObject(strJson);
                        postRequest("http://172.16.140.171:9009/register/"+jsonObject);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void postRequest(final String url) {
        final Context context = getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(context, "Response: " + response, Toast.LENGTH_LONG).show();
                        letUserThrough(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Response: " + error, Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void letUserThrough(String response){
        if (response.toLowerCase().indexOf("true") > 0){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}