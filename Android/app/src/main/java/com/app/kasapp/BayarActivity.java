package com.app.kasapp;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
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

public class BayarActivity extends AppCompatActivity {
    private EditText editTextMembayar, editTextBayarKas,
            editTextMasukTabungan,editTexnamaListAnggota,editTextTanggalBayar;
    private Button buttonSimpan;
    private ArrayList<String> items=new ArrayList<>();
    private ArrayList<String> itemsId=new ArrayList<>();
    private SpinnerDialog spinnerDialog;
    private MediaPlayer mp;
    private String IdAnggota=null;
    private FloatingActionButton fabEdit,fabHapus;
    private TableRow rowTanggalBayar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);
        buttonSimpan = findViewById(R.id.buttonSimpan);
        editTextMasukTabungan = findViewById(R.id.editTextMasukTabungan);
        editTextMembayar = findViewById(R.id.editTextMembayar);
        editTextBayarKas = findViewById(R.id.editTextBayarKas);
        editTexnamaListAnggota = findViewById(R.id.editTexnamaListAnggota);
        editTextTanggalBayar = findViewById(R.id.editTextTanggalBayar);
        fabEdit = findViewById(R.id.fabEdit);
        fabHapus = findViewById(R.id.fabHapus);
        rowTanggalBayar = findViewById(R.id.rowTanggalBayar);
        editTextBayarKas.addTextChangedListener(textWatcher);
        editTextMembayar.addTextChangedListener(textWatcher);

        if (getIntent().getStringExtra("untuk").equals("edit")) {
            layoutEdit();
        } else if (getIntent().getStringExtra("untuk").equals("input")) {
            layoutIinput();
        }

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            try {
                int hasil =  Integer.valueOf(editTextMembayar.getText().toString())- Integer.valueOf(editTextBayarKas.getText().toString());

                editTextMasukTabungan.setText(String.valueOf(hasil));
            } catch (NumberFormatException n) {
                editTextMasukTabungan.setText(String.valueOf(0));
                n.getMessage();
            }

        }
    };

    private void layoutEdit() {
        rowTanggalBayar.setVisibility(View.VISIBLE);
        buttonSimpan.setVisibility(View.GONE);
        buttonSimpan.setText("Simpan Perubahan");
        fabEdit.show();
        fabHapus.show();
        editTexnamaListAnggota.setText(getIntent().getStringExtra("nama"));
        editTextMembayar.setText(getIntent().getStringExtra("bayar"));
        editTextBayarKas.setText(getIntent().getStringExtra("kas"));
        editTextMasukTabungan.setText(getIntent().getStringExtra("tabungan"));
        editTextTanggalBayar.setText(getIntent().getStringExtra("tanggalBayar"));
        editTexnamaListAnggota.setEnabled(false);
        editTextMembayar.setEnabled(false);
        editTextBayarKas.setEnabled(false);
        editTextMasukTabungan.setEnabled(false);

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTexnamaListAnggota.setEnabled(true);
                editTextMembayar.setEnabled(true);
                editTextBayarKas.setEnabled(true);
                editTextMasukTabungan.setEnabled(true);
                buttonSimpan.setVisibility(View.VISIBLE);
                fabEdit.hide();
                fabHapus.hide();
            }
        });

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                simpanEditPembayaran(getIntent().getStringExtra("id"));
            }
        });

        fabHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(BayarActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Benar Akan di Hapus")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                hapusTransaksi(getIntent().getStringExtra("id"));
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
                //Toast.makeText(BayarActivity.this, "Mengahpus transaksi dengan Id " +getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hapusTransaksi(final String id) {
        String URL = Server.BASE_URL+"hapus_transaksi.php";
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

                                Toast.makeText(BayarActivity.this, "pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();
                                //dataList.clear();
                                pesanBerhasil("Menhapus");

                            } else {

                                Toast.makeText(BayarActivity.this, "pesan : " +res.getString("message"), Toast.LENGTH_SHORT).show();
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
                map.put("id_transaksi",id);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(delReq);
    }

    private void simpanEditPembayaran(final String id) {
        String URL = Server.BASE_URL +"edit_bayar.php";
        final ProgressDialog progressDialog = new ProgressDialog(BayarActivity.this);
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
                                Toast.makeText(BayarActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil("Menyimpan");
                            } else {
                                Toast.makeText(BayarActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BayarActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_transaksi",id);
                map.put("jumlah_kas",editTextBayarKas.getText().toString());
                map.put("jumlah_tabungan",editTextMasukTabungan.getText().toString());
                map.put("jumlah_transaksi",editTextMembayar.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void layoutIinput() {

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextMasukTabungan.getText().toString().startsWith("-")) {
                    Toast.makeText(BayarActivity.this, "Jumlah Membayar Lebih Kecil dari Tagihan Kas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BayarActivity.this, "Menyimpan Data", Toast.LENGTH_SHORT).show();
                    simpanPembayaran();
                }
            }
        });

        editTexnamaListAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tampilkanAnggota();
            }
        });

        getListAnggota();
    }

    private void simpanPembayaran() {
        String URL = Server.BASE_URL +"simpan_bayar.php";
        final ProgressDialog progressDialog = new ProgressDialog(BayarActivity.this);
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
                                Toast.makeText(BayarActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil("Menyimpan");
                            } else {
                                Toast.makeText(BayarActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BayarActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id_anggota",IdAnggota);
                map.put("jumlah_kas",editTextBayarKas.getText().toString());
                map.put("jumlah_tabungan",editTextMasukTabungan.getText().toString());
                map.put("jumlah_transaksi",editTextMembayar.getText().toString());


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

    private void tampilkanAnggota() {
        spinnerDialog = new SpinnerDialog(this, items, "Pilih Anggota");
        spinnerDialog.showSpinerDialog();
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                editTexnamaListAnggota.setText(item);
                IdAnggota = itemsId.get(position);
                //Toast.makeText(BayarActivity.this, "position "+itemsId.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getStringExtra("untuk").equals("edit")) {
            startActivity(new Intent(this, RekapActivity.class));
        } else if (getIntent().getStringExtra("untuk").equals("input")) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
