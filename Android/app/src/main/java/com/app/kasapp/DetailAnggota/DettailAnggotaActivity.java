package com.app.kasapp.DetailAnggota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.app.kasapp.AppController;
import com.app.kasapp.ListAnggotaActivity;
import com.app.kasapp.LoginActivity;
import com.app.kasapp.MainActivity;
import com.app.kasapp.Models.ItemPenarikan;
import com.app.kasapp.Models.ItemTransaksi;
import com.app.kasapp.R;
import com.app.kasapp.Rekapitulasi.RekapActivity;
import com.app.kasapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DettailAnggotaActivity extends AppCompatActivity {


    private SwipeRefreshLayout swipe;
    public static ArrayList <ItemTransaksi> itemTransaksi;
    public static ArrayList <ItemPenarikan> itemPenarikans;
    private TextView textViewTotalTabungan, textViewNamaPengguna,textViewId,textViewTotalKas;
    private FloatingActionButton fabEdit, fabHapus,fabMore, fabLogOut;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    ProfileFragment profileFragment = new ProfileFragment();
    public static JSONObject dataAnggota = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettail_anggota);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        textViewTotalTabungan = findViewById(R.id.textViewTotalTabungan);
        textViewNamaPengguna = findViewById(R.id.textViewNamaPengguna);
        textViewId = findViewById(R.id.textViewId);
        textViewTotalKas = findViewById(R.id.textViewTotalKas);
        fabEdit = findViewById(R.id.fabEdit);
        fabHapus = findViewById(R.id.fabHapus);
        fabMore = findViewById(R.id.fabMore);
        fabLogOut = findViewById(R.id.fabLogOut);
        itemTransaksi = new ArrayList<>();
        itemPenarikans = new ArrayList<>();
        swipe = findViewById(R.id.swipe);
        swipe.setRefreshing(true);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itemTransaksi.clear();
                itemPenarikans.clear();
                BlankFragment.listAdapter.notifyDataSetChanged();
                Blank2Fragment.listAdapter2.notifyDataSetChanged();
                getData(Server.BASE_URL+"detail_anggota.php?IdAnggota="+getIntent().getStringExtra("idAnggota"));
            }
        });

        fabHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(DettailAnggotaActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Benar Akan di Hapus")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                hapusAnggota(textViewId.getText().toString());
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
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet();
                bottomSheetDialog.show();
            }
        });

        fabMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DettailAnggotaActivity.this, RekapActivity.class);
                intent.putExtra("dari", "detail");
                intent.putExtra("id", textViewId.getText().toString());
                startActivity(intent);
            }
        });

        Log.i("getIdAnggota",sharedpreferences.getString("id_anggota", null));

        getData(Server.BASE_URL+"detail_anggota.php?IdAnggota="+getIntent().getStringExtra("idAnggota"));

        fabLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(DettailAnggotaActivity.this, SweetAlertDialog.WARNING_TYPE)
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

        seleksiFab();
        getData2();
    }
    private void getData2() {
        String JSON_URL = Server.BASE_URL+"beranda.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("getRespon", s.toString());
                try {
                    JSONObject object = new JSONObject(s);
                    textViewTotalKas.setText("Rp. "+object.getString("totalKas"));
                } catch (JSONException e) {
                    e.printStackTrace();
                 }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void hapusSharePref() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("level_anggota", null);
        editor.putString("id_anggota", null);
        editor.apply();
        editor.commit();

        startActivity(new Intent(DettailAnggotaActivity.this, LoginActivity.class));
    }

    private void seleksiFab() {
        switch (sharedpreferences.getString("level_anggota", null)){
            case "1":
                fabEdit.show();
                fabHapus.show();
                fabLogOut.hide();
                findViewById(R.id.liner).setVisibility(View.GONE);
                break;
            case "2":
                fabEdit.hide();
                fabHapus.hide();
                fabLogOut.show();
                break;
        }
    }

    private void bottomSheet() {
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.input_anggota, null);
        final EditText editTextNamaAnggota = view.findViewById(R.id.editTextNamaAnggota);
        editTextNamaAnggota.setText(textViewNamaPengguna.getText().toString());
        Button buttonSimpanAnggota = view.findViewById(R.id.buttonSimpanAnggota);
        buttonSimpanAnggota.setText("Simpan Perubahan");
        buttonSimpanAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAnggota(textViewId.getText().toString(),editTextNamaAnggota.getText().toString());
            }
        });

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void editAnggota(final  String idAnggota, final String namaAnggota){
        String URL = Server.BASE_URL+"edit_anggota.php";
        final ProgressDialog progressDialog = new ProgressDialog(DettailAnggotaActivity.this);
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
                                Toast.makeText(DettailAnggotaActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil();
                            } else {
                                Toast.makeText(DettailAnggotaActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DettailAnggotaActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("nama_anggota",namaAnggota);
                map.put("id_anggota",idAnggota);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);

    };
    private void hapusAnggota(final String IdAnggota) {
        String URL = Server.BASE_URL+"hapus_anggota.php";
        final ProgressDialog progressDialog = new ProgressDialog(DettailAnggotaActivity.this);
        progressDialog.setMessage("Loading Sedang Melakukan Proses...");
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
                                Toast.makeText(DettailAnggotaActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                pesanBerhasil();
                            } else {
                                Toast.makeText(DettailAnggotaActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DettailAnggotaActivity.this, "pesan : Menghapus Anggota", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("IdAnggota",IdAnggota);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void getData(String JSON_URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("getRespon", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray = jsonObject.getJSONArray("DataTransaksi");
                        int max = 4;
                        if (jsonArray.length()<max) {
                            max = jsonArray.length();
                        }
                        for (int a= 0; a < max; a++) {
                            String kas =jsonArray.getJSONObject(a).getString("jumlah_kas");
                            String tabungan =jsonArray.getJSONObject(a).getString("jumlah_tabungan");
                            String totalBayar = jsonArray.getJSONObject(a).getString("jumlah_transaksi");
                            String tanggalBayar = jsonArray.getJSONObject(a).getString("tanggal_transaksi");

                            itemTransaksi.add(new ItemTransaksi(kas,tabungan ,totalBayar,tanggalBayar));
                        }

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                        swipe.setRefreshing(false);
                    }

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        dataAnggota  = response.getJSONObject(i);
                        JSONArray jsonArray1 = jsonObject.getJSONArray("DataAnggota");
                        for (int a= 0; a < jsonArray1.length(); a++) {
                            String id = jsonArray1.getJSONObject(a).getString("npm");
                            String nama = jsonArray1.getJSONObject(a).getString("nama_anggota");
                            String tabungan = jsonArray1.getJSONObject(a).getString("tabungan");
                            textViewNamaPengguna.setText(" "+nama);
                            textViewId.setText(" "+id);

                            textViewTotalTabungan.setText("Rp. "+tabungan);
                        }
                    } catch (JSONException n) {
                        n.getMessage();
                    }

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray1 = jsonObject.getJSONArray("DataPenarikan");
                        for (int a= 0; a < jsonArray1.length(); a++) {
                            String id_penarikan = jsonArray1.getJSONObject(a).getString("id_penarikan");
                            String jumlah_penarikan = jsonArray1.getJSONObject(a).getString("jumlah_penarikan");
                            String tanggal_penarikan = jsonArray1.getJSONObject(a).getString("tanggal_penarikan");
                            itemPenarikans.add(new ItemPenarikan(id_penarikan, jumlah_penarikan, tanggal_penarikan));

                        }
                    } catch (JSONException n) {
                        n.getMessage();
                    }
                }

                swipe.setRefreshing(false);
                initTab();
                Blank2Fragment.listAdapter2.notifyDataSetChanged();
                BlankFragment.listAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
                Log.e("VolleyNotif", error.toString());
                //progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        }

    private void initTab() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (tabLayout.getTabCount()==0) {
            tabLayout.addTab(tabLayout.newTab().setText("TRANSAKSI"));
            tabLayout.addTab(tabLayout.newTab().setText("PENARIKAN"));
            tabLayout.addTab(tabLayout.newTab().setText("Profile"));
            final tabsPager adapter = new tabsPager(getSupportFragmentManager(), tabLayout.getTabCount());
            adapter.addFragment(new BlankFragment());
            adapter.addFragment(new Blank2Fragment());
            adapter.addFragment(profileFragment);
            viewPager.setAdapter(adapter);
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tabs) {
                                                           viewPager.setCurrentItem(tabs.getPosition());
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tabs) {

                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tabs) {

                                                       }

                                                   });
    }

    private void pesanGagal() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Waduhh...")
                .setContentText("Terjadi Kesalahan Silahkan Ulangi Lagi")
                .show();
    }

    private void pesanBerhasil() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Succes!")
                .setContentText("Penghapusan Berhasil")
                .show();
        Intent intent = new Intent(this, ListAnggotaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (sharedpreferences.getString("level_anggota", null).equals("1")) {

            startActivity(new Intent(this, MainActivity.class));
        } else {
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
}
