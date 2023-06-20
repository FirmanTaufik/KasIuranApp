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
    private String TAG ="LoginActivityTAG";
    private EditText editTextId,editDate;
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
        editDate = findViewById(R.id.editDate);
        editTextId = findViewById(R.id.editTextId);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextId.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "masukan dulu npm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editDate.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "masukan dulu tanggal lahir", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkuser();
            }
        });


        checkSudahLoginAtauBelum();
    }

    private void checkSudahLoginAtauBelum() {
        if (sharedpreferences.getString("id_anggota", null)!=null) {
            if (sharedpreferences.getString("level_anggota", null).equals("1"))
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            else {
                Intent intent = new Intent(LoginActivity.this, DettailAnggotaActivity.class);
                intent.putExtra("idAnggota", sharedpreferences.getString("id_anggota", null));
                startActivity(intent);
            }
        }

    }

    private void checkuser() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#E91E63"));
        pDialog.setTitleText("Mohon Tunggu.....");
        pDialog.setCancelable(false);
        pDialog.show();

        String JSON_URL = Server.BASE_URL+"login.php?npm="+editTextId.getText().toString()+"&tanggal_lahir="+editDate.getText().toString();
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
                    Toast.makeText(LoginActivity.this, "Anda Memasukan Akun Yang Salah", Toast.LENGTH_SHORT).show();
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

        Log.d(TAG, "simpanData: "+sharedpreferences.getString("id_anggota",null));

        if (level_anggota.equals("1")) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Intent intent = new Intent(LoginActivity.this, DettailAnggotaActivity.class);
            intent.putExtra("idAnggota", id_anggota);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
