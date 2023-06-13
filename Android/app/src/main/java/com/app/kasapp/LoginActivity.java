package com.app.kasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.kasapp.DetailAnggota.DettailAnggotaActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextId;
    private Button buttonLogin;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        editTextId = findViewById(R.id.editTextId);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkuser();
            }
        });


        checkSudahLoginAtauBelum();
    }

    private void checkSudahLoginAtauBelum() {
        if (sharedpreferences.getString("level_anggota", null)==null) {

            Toast.makeText(this, "Masukan Id User", Toast.LENGTH_SHORT).show();

        } else if (sharedpreferences.getString("level_anggota", null).equals("1")){

            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        } else if (sharedpreferences.getString("level_anggota", null).equals("2")) {

            Intent intent = new Intent(LoginActivity.this, DettailAnggotaActivity.class);
            intent.putExtra("idAnggota", sharedpreferences.getString("id_anggota", null));
            startActivity(intent);
        }

    }

    private void checkuser() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#E91E63"));
        pDialog.setTitleText("Mohon Tunggu.....");
        pDialog.setCancelable(false);
        pDialog.show();

        String JSON_URL = Server.BASE_URL+"login.php?id="+editTextId.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                JSON_URL,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);

                    if (res.getString("succes").equals("1")) {
                        Toast.makeText(LoginActivity.this, "pesan : " + res.getString("message"), Toast.LENGTH_SHORT).show();

                        simpanData(res.getString("level_anggota"), res.getString("id_anggota"));
                        
                    } else {
                        Toast.makeText(LoginActivity.this, "pesan : " + res.getString("message"), Toast.LENGTH_SHORT).show();
                        //  errorHarusIsi();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Anda Memasukan ID Yang Salah", Toast.LENGTH_SHORT).show();
                }
                Log.i("getJson", response.toString());
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                Log.e("Volley", error.toString());
                //  progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);


    }

    private void simpanData(String level_anggota, String id_anggota) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("level_anggota", level_anggota);
        editor.putString("id_anggota", id_anggota);
        editor.apply();
        editor.commit();


        if (level_anggota.equals("1")) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Intent intent = new Intent(LoginActivity.this, DettailAnggotaActivity.class);
            intent.putExtra("idAnggota", id_anggota);
            startActivity(intent);
        }
    }
}
