package com.app.kasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.app.kasapp.Rekapitulasi.RekapActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView textViewDaftarAnggota;
    private SwipeRefreshLayout swipe;
    private TextView textViewTotalKas,textViewTotalTabungan,
            textViewTotalPengeluaran,textViewRekap,
            textViewPengeluaranKas,textViewPenarikanTabungan;
    private FloatingActionButton fabBayar,fabLogOut;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        textViewTotalKas = findViewById(R.id.textViewTotalKas);
        textViewTotalTabungan = findViewById(R.id.textViewTotalTabungan);
        textViewDaftarAnggota = findViewById(R.id.textViewDaftarAnggota);
        textViewTotalPengeluaran = findViewById(R.id.textViewTotalPengeluaran);
        textViewPengeluaranKas = findViewById(R.id.textViewPengeluaranKas);
        textViewPenarikanTabungan = findViewById(R.id.textViewPenarikanTabungan);
        fabBayar = findViewById(R.id.fabBayar);
        fabLogOut = findViewById(R.id.fabLogOut);
        swipe = findViewById(R.id.swipe);
        swipe.setRefreshing(true);
        textViewDaftarAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListAnggotaActivity.class));
            }
        });

        textViewRekap = findViewById(R.id.textViewRekap);
        textViewRekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RekapActivity.class);
                intent.putExtra("dari", "main");
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        fabBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BayarActivity.class);
                intent.putExtra("untuk", "input");
                startActivity(intent);
            }
        });

        textViewPengeluaranKas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PengeluaranKasActivity.class);
                intent.putExtra("untuk","input");
                startActivity(intent);
            }
        });

        textViewPenarikanTabungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PenarikanTabunganActivity.class);
                intent.putExtra("untuk","input");
                startActivity(intent);
            }
        });

        fabLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Ingin Logout Aplikasi")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                hapusSharePref();

                            }
                        })
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        getData();
    }

    private void hapusSharePref() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("level_anggota", null);
        editor.putString("id_anggota", null);
        editor.apply();
        editor.commit();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void getData() {
        String JSON_URL = Server.BASE_URL+"beranda.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("getRespon", s.toString());
                try {
                    JSONObject object = new JSONObject(s);
                    textViewTotalKas.setText("Rp. "+object.getString("totalKas"));
                    textViewTotalTabungan.setText("Rp. "+object.getString("totalTabungan"));
                    textViewTotalPengeluaran.setText("Rp. "+object.getString("totalPengeluaran"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipe.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Terjadi Kesalahan Silahkan Reload...Usap ke Bawah Untuk Reload..", Toast.LENGTH_LONG).show();
                }
                swipe.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Terjadi Kesalahan Silahkan Reload...Usap ke Bawah Untuk Reload..", Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Apakah Ingin Tutup Aplikasi")
                .setConfirmText("Ya")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);

                    }
                })
                .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
