package com.app.kasapp;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.app.kasapp.Rekapitulasi.RekapActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PengeluaranKasActivity extends AppCompatActivity {
    private Button buttonSimpan;
    private EditText textViewBiayaKeluar, textViewKeterangan,textViewTanggalPengeluaran;
    private LinearLayout layoutTanggalPengeluaran;
    private FloatingActionButton fabEdit, fabHapus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_kas);
        buttonSimpan = findViewById(R.id.buttonSimpan);
        textViewBiayaKeluar = findViewById(R.id.textViewBiayaKeluar);
        textViewKeterangan = findViewById(R.id.textViewKeterangan);
        textViewTanggalPengeluaran = findViewById(R.id.textViewTanggalPengeluaran);
        layoutTanggalPengeluaran = findViewById(R.id.layoutTanggalPengeluaran);
        fabHapus = findViewById(R.id.fabHapus);
        fabEdit = findViewById(R.id.fabEdit);
        if (getIntent().getStringExtra("untuk").equals("edit")) {
            layoutEdit();
        } else {
            layoutInput();
        }

    }

    private void layoutEdit() {
        fabHapus.show();
        fabEdit.show();
        textViewBiayaKeluar.setEnabled(false);
        textViewKeterangan.setEnabled(false);
        layoutTanggalPengeluaran.setVisibility(View.VISIBLE);
        textViewBiayaKeluar.setText(getIntent().getStringExtra("jumlahPengeluaran"));
        textViewKeterangan.setText(getIntent().getStringExtra("keperluan"));
        textViewTanggalPengeluaran.setText(getIntent().getStringExtra("tanggalPengeluaran"));
        buttonSimpan.setVisibility(View.GONE);
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanPerubahan();
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabHapus.hide();
                fabEdit.hide();
                textViewBiayaKeluar.setEnabled(true);
                textViewKeterangan.setEnabled(true);
                buttonSimpan.setVisibility(View.VISIBLE);

            }
        });

        fabHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(PengeluaranKasActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Benar Akan di Hapus")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                hapusPengeluarasKas(getIntent().getStringExtra("id"));
                                //Toast.makeText(PengeluaranKasActivity.this, "Id... "+getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
                                sDialog.dismissWithAnimation();
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
    }

    private void hapusPengeluarasKas(final String id) {
        String URL = Server.BASE_URL+"hapus_pengeluaran.php";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Sedang Menghapus Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest delReq = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("volley","response : " + response.toString());
                        try {
                            JSONObject res = new JSONObject(response);

                            if (res.getString("succes").equals("1")) {

                                Toast.makeText(PengeluaranKasActivity.this, "pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();
                                //dataList.clear();
                                pesanBerhasil("Menghapus");

                            } else {

                                Toast.makeText(PengeluaranKasActivity.this, "pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();
                                pesanGagal();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id_pengeluaran",id);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(delReq);
    }

    private void simpanPerubahan() {
        String URL = Server.BASE_URL +"edit_penegeluaran.php";
        final ProgressDialog progressDialog = new ProgressDialog(PengeluaranKasActivity.this);
        progressDialog.setMessage("Loading Sedang Menyimpan Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);

                            if (res.getString("succes").equals("1")) {
                                Toast.makeText(PengeluaranKasActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil("Menyimpan");
                            } else {
                                Toast.makeText(PengeluaranKasActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanGagal();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pesanGagal();
                        }

                        Log.i("getResponInput", response);
                        progressDialog.dismiss();

                        //  startActivity( new Intent(InsertData.this,MainActivity.class));
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(PengeluaranKasActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_pengeluaran",getIntent().getStringExtra("id"));
                map.put("jumlah_pengeluaran",textViewBiayaKeluar.getText().toString());
                map.put("keperluan",textViewKeterangan.getText().toString());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void layoutInput() {
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewBiayaKeluar.getText().toString().trim().length() ==0
                        || textViewKeterangan.getText().toString().trim().length() ==0) {
                    new SweetAlertDialog(PengeluaranKasActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Terjadi Kesalahan")
                            .setContentText("Semua Kolom Wajib di isi")
                            .show();
                } else {
                    simpanData();
                }
            }
        });
    }

    private void simpanData() {
        String URL = Server.BASE_URL+"simpan_pengeluaran.php";
        final ProgressDialog progressDialog = new ProgressDialog(PengeluaranKasActivity.this);
        progressDialog.setMessage("Loading Sedang Menyimpan Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);

                            if (res.getString("succes").equals("1")) {
                                Toast.makeText(PengeluaranKasActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil("Menyimpan");
                            } else {
                                Toast.makeText(PengeluaranKasActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanGagal();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pesanGagal();
                        }

                        Log.i("getResponInput", response);
                        progressDialog.dismiss();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(PengeluaranKasActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("jumlah_pengeluaran",textViewBiayaKeluar.getText().toString());
                map.put("keperluan",textViewKeterangan.getText().toString());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void pesanGagal() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Waduhh...")
                .setContentText("Terjadi Kesalahan Silahkan Ulangi Lagi")
                .show();
    }

    private void pesanBerhasil(String jenis) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Succes!")
                .setContentText(jenis+" berhasil")
                .show();

        if (getIntent().getStringExtra("untuk").equals("edit")) {
            startActivity(new Intent(this, RekapActivity.class));
        } else if (getIntent().getStringExtra("untuk").equals("input")) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        super.onBackPressed();
    }
}
