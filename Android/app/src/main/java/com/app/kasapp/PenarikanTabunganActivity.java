package com.app.kasapp;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PenarikanTabunganActivity extends AppCompatActivity {
    private EditText editTextNamaAnggota, editTextSisaTabungan,
            editTextPenarikan, editTextAkhirSisaTabungan,editTextTanggalPenarikan;
    private ArrayList<String> items=new ArrayList<>();
    private ArrayList<String> itemsId=new ArrayList<>();
    private SpinnerDialog spinnerDialog;
    private String IdAnggota=null;
    private Button buttonSimpan;
    private FloatingActionButton fabEdit, fabHapus;
    private TableRow rowTanggaPenarikan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penarikan_tabungan);
        editTextNamaAnggota = findViewById(R.id.editTextNamaAnggota);
        editTextSisaTabungan = findViewById(R.id.editTextSisaTabungan);
        editTextPenarikan = findViewById(R.id.editTextPenarikan);
        editTextAkhirSisaTabungan = findViewById(R.id.editTextAkhirSisaTabungan);
        editTextTanggalPenarikan = findViewById(R.id.editTextTanggalPenarikan);
        buttonSimpan = findViewById(R.id.buttonSimpan);
        fabEdit = findViewById(R.id.fabEdit);
        fabHapus = findViewById(R.id.fabHapus);
        rowTanggaPenarikan = findViewById(R.id.rowTanggaPenarikan);
        editTextNamaAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tampilkanAnggota();
            }
        });

        editTextPenarikan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {

                    int hasil =  Integer.valueOf(editTextSisaTabungan.getText().toString())- Integer.valueOf(editTextPenarikan.getText().toString());
                    editTextAkhirSisaTabungan.setText(String.valueOf(hasil));
                } catch (NumberFormatException n) {
                    editTextAkhirSisaTabungan.setText(String.valueOf(0));
                    n.getMessage();
                }
            }
        });

        if (getIntent().getStringExtra("untuk").equals("edit")) {
            layoutEdit();
        } else {
            layoutInput();
        }
    }

    private void layoutEdit() {
        IdAnggota = getIntent().getStringExtra("idAnggota");
        getDetailAnggota();
        fabEdit.show();
        fabHapus.show();
        rowTanggaPenarikan.setVisibility(View.VISIBLE);
        buttonSimpan.setVisibility(View.GONE);
        editTextNamaAnggota.setEnabled(false);
        editTextSisaTabungan.setEnabled(false);
        editTextPenarikan.setEnabled(false);
        editTextAkhirSisaTabungan.setEnabled(false);
        editTextNamaAnggota.setText(getIntent().getStringExtra("nama"));
        editTextTanggalPenarikan.setText(getIntent().getStringExtra("tanggalPenarikan"));
        editTextPenarikan.setText(getIntent().getStringExtra("jumlahPenarikan"));
        buttonSimpan.setText("Simpan Perubahan");

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                simpanEditPenarikan();
               // Toast.makeText(PenarikanTabunganActivity.this, "simpan...  "+getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
            }
        });
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSisaTabungan.setEnabled(true);
                editTextPenarikan.setEnabled(true);
                editTextAkhirSisaTabungan.setEnabled(true);
                buttonSimpan.setVisibility(View.VISIBLE);
                fabEdit.hide();
                fabHapus.hide();
            }
        });

        fabHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(PenarikanTabunganActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Benar Akan di Hapus")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                hapusPenarikan(getIntent().getStringExtra("id"));
                                //Toast.makeText(PenarikanTabunganActivity.this, "id.... " +getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
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

    private void hapusPenarikan(final String id) {
        String URL = Server.BASE_URL+"hapus_penarikan.php";
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

                                Toast.makeText(PenarikanTabunganActivity.this, "pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();
                                //dataList.clear();
                                pesanBerhasil("Menhapus");

                            } else {

                                Toast.makeText(PenarikanTabunganActivity.this, "pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();
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
                map.put("id_penarikan",id);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(delReq);
    }

    private void simpanEditPenarikan() {
        String URL = Server.BASE_URL +"edit_penarikan.php";
        final ProgressDialog progressDialog = new ProgressDialog(PenarikanTabunganActivity.this);
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
                                Toast.makeText(PenarikanTabunganActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil("Menyimpan");
                            } else {
                                Toast.makeText(PenarikanTabunganActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PenarikanTabunganActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_penarikan",getIntent().getStringExtra("id"));
                map.put("jumlah_penarikan",editTextPenarikan.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void layoutInput() {
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNamaAnggota.getText().toString().length() ==0 ||
                        editTextSisaTabungan.getText().toString().length() ==0 ||
                        editTextPenarikan.getText().toString().length() ==0 ||
                        editTextAkhirSisaTabungan.getText().toString().length() ==0) {
                    new SweetAlertDialog(PenarikanTabunganActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Terjadi Kesalahan")
                            .setContentText("Semua Kolom Wajib di isi")
                            .show();
                } else {

                    simpanPenarikan();
                }
            }
        });

        getListAnggota();
    }

    private void simpanPenarikan() {
        String URL = Server.BASE_URL +"simpan_penarikan.php";
        final ProgressDialog progressDialog = new ProgressDialog(PenarikanTabunganActivity.this);
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
                                Toast.makeText(PenarikanTabunganActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil("Menyimpan");
                            } else {
                                Toast.makeText(PenarikanTabunganActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PenarikanTabunganActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_anggota",IdAnggota);
                map.put("jumlah_penarikan",editTextPenarikan.getText().toString());

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
    private void tampilkanAnggota() {
        spinnerDialog = new SpinnerDialog(this, items, "Pilih Anggota");
        spinnerDialog.showSpinerDialog();
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                editTextNamaAnggota.setText(item);
                IdAnggota = itemsId.get(position);
                //Toast.makeText(PenarikanTabunganActivity.this, "position "+itemsId.get(position), Toast.LENGTH_SHORT).show();
                getDetailAnggota();
            }
        });
    }

    private void getDetailAnggota() {
        String JSON_URL = Server.BASE_URL+"detail_anggota.php?IdAnggota="+IdAnggota;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray1 = jsonObject.getJSONArray("DataAnggota");
                        Log.i("getDetail", response.toString());
                        for (int a= 0; a < jsonArray1.length(); a++) {
                            String id = jsonArray1.getJSONObject(a).getString("id_anggota");
                            String nama = jsonArray1.getJSONObject(a).getString("nama_anggota");
                            String tabungan = jsonArray1.getJSONObject(a).getString("tabungan");

                            if (getIntent().getStringExtra("untuk").equals("edit")) {
                                int isi = Integer.valueOf(tabungan)+Integer.valueOf(getIntent().getStringExtra("jumlahPenarikan"));
                                editTextSisaTabungan.setText(String.valueOf(isi));


                                int hasil = isi- Integer.valueOf(editTextPenarikan.getText().toString());
                                 editTextAkhirSisaTabungan.setText(String.valueOf(hasil));
                            } else {

                                editTextSisaTabungan.setText(tabungan);
                            }
                        }
                    } catch (JSONException n) {
                        n.getMessage();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VolleyNotif", error.toString());
                //progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void getListAnggota() {
        String JSON_URL = Server.BASE_URL+"daftar_anggota.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("getRespon", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id_anggota");
                        String nama = jsonObject.getString("nama_anggota");

                        itemsId.add(id);
                        items.add(nama);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VolleyNotif", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
