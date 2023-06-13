package com.app.kasapp.Rekapitulasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.app.kasapp.DetailAnggota.Blank2Fragment;
import com.app.kasapp.DetailAnggota.DettailAnggotaActivity;
import com.app.kasapp.MainActivity;
import com.app.kasapp.Models.RekapPenarikan;
import com.app.kasapp.Models.RekapPengeluaran;
import com.app.kasapp.Models.RekapTransaksi;
import com.app.kasapp.R;
import com.app.kasapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RekapActivity extends AppCompatActivity {
    public static ArrayList <RekapTransaksi> rekapTransaksis;
    public static ArrayList <RekapPenarikan> rekapPenarikans;
    public static ArrayList <RekapPengeluaran> rekapPengeluarans;
    public static int finalTotalKas =0;
    public static int finalTotalTabungan =0;
    public static int finalTotalPenarikan =0;
    public static int finalTotalPengeluaran =0;
    private FloatingActionButton fabDate;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);
        rekapTransaksis = new ArrayList<>();
        rekapPenarikans = new ArrayList<>();
        rekapPengeluarans = new ArrayList<>();
        fabDate = findViewById(R.id.fabDate);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("KAS"));
        tabLayout.addTab(tabLayout.newTab().setText("TABUNGAN"));
        tabLayout.addTab(tabLayout.newTab().setText("PENARIKAN"));
        tabLayout.addTab(tabLayout.newTab().setText("PENGELUARAN"));
        final PagerAdapter adapter = new tabsPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
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

        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet();
                bottomSheetDialog.show();
            }
        });



    }



    private void getDataTransaksi(String tanggal1, String tanggal2){

        final ProgressDialog progressDialog = new ProgressDialog(RekapActivity.this);
        progressDialog.setMessage("Loading Sedang Menyimpan Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String JSON_URL = null;

        try {
            if (getIntent().getStringExtra("dari").equals("detail")) {
                JSON_URL = Server.BASE_URL+"rekap_dana.php?Tanggal1="+tanggal1+"&Tanggal2="+tanggal2 +
                        "&id="+getIntent().getStringExtra("id");

            } else {
                JSON_URL = Server.BASE_URL+"rekap_dana.php?Tanggal1="+tanggal1+"&Tanggal2="+tanggal2;

            }
        } catch (NullPointerException n ) {
            n.getMessage();
            JSON_URL = Server.BASE_URL+"rekap_dana.php?Tanggal1="+tanggal1+"&Tanggal2="+tanggal2;
        }


         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray = jsonObject.getJSONArray("Transaksi");
                        for (int a= 0; a < jsonArray.length(); a++) {
                            String id_transaksi = jsonArray.getJSONObject(a).getString("id_transaksi");
                            String id_anggota = jsonArray.getJSONObject(a).getString("id_anggota");
                            String nama_anggota = jsonArray.getJSONObject(a).getString("nama_anggota");
                            String jumlah_kas =jsonArray.getJSONObject(a).getString("jumlah_kas");
                            String jumlah_tabungan =jsonArray.getJSONObject(a).getString("jumlah_tabungan");
                            String jumlah_transaksi = jsonArray.getJSONObject(a).getString("jumlah_transaksi");
                            String tanggal_transaksi = jsonArray.getJSONObject(a).getString("tanggal_transaksi");

                            rekapTransaksis.add(new RekapTransaksi(
                                    id_transaksi, id_anggota, nama_anggota,
                                    jumlah_kas, jumlah_tabungan, jumlah_transaksi,
                                    tanggal_transaksi
                            ));

                            finalTotalKas += Integer.valueOf(jumlah_kas);
                            finalTotalTabungan += Integer.valueOf(jumlah_tabungan);
                        }
                        RekapBlankFragment.listAdapter.notifyDataSetChanged();
                        RekapBlank2Fragment.listAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        e.printStackTrace();

                        progressDialog.dismiss();
                    }


                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray = jsonObject.getJSONArray("Penarikan");
                        for (int a= 0; a < jsonArray.length(); a++) {
                            String id_penarikan = jsonArray.getJSONObject(a).getString("id_penarikan");
                            String id_anggota = jsonArray.getJSONObject(a).getString("id_anggota");
                            String nama_anggota = jsonArray.getJSONObject(a).getString("nama_anggota");
                            String jumlah_penarikan =jsonArray.getJSONObject(a).getString("jumlah_penarikan");
                            String tanggal_penarikan =jsonArray.getJSONObject(a).getString("tanggal_penarikan");

                           rekapPenarikans.add(new RekapPenarikan(
                                   id_penarikan, id_anggota, nama_anggota, jumlah_penarikan, tanggal_penarikan
                           ));
                            finalTotalPenarikan += Integer.valueOf(jumlah_penarikan);
                        }

                        RekapBlank3Fragment.listAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();

                    }


                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray jsonArray = jsonObject.getJSONArray("Pengeluaran");
                        for (int a= 0; a < jsonArray.length(); a++) {
                            String id_pengeluaran = jsonArray.getJSONObject(a).getString("id_pengeluaran");
                            String jumlah_pengeluaran = jsonArray.getJSONObject(a).getString("jumlah_pengeluaran");
                            String keperluan = jsonArray.getJSONObject(a).getString("keperluan");
                            String tanggal_pengeluaran =jsonArray.getJSONObject(a).getString("tanggal_pengeluaran");

                         rekapPengeluarans.add(new RekapPengeluaran(
                                 id_pengeluaran, jumlah_pengeluaran, keperluan, tanggal_pengeluaran
                         ));

                         finalTotalPengeluaran += Integer.valueOf(jumlah_pengeluaran);
                        }
                        RekapBlank4Fragment.listAdapter.notifyDataSetChanged();


                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();

                    }
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VolleyNotif", error.toString());
                progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    private void bottomSheet() {
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.filter_tanggal, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        final EditText dariTanggal = view.findViewById(R.id.dariTanggal);
        final EditText sampaiTanggal = view.findViewById(R.id.sampaiTanggal);
        Button buttonFilter = view.findViewById(R.id.buttonFilter);
        dariTanggal.setInputType(InputType.TYPE_NULL);
        sampaiTanggal.setInputType(InputType.TYPE_NULL);
        dariTanggal.requestFocus();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        dariTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(RekapActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //todo
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                dariTanggal.setText(dateFormatter.format(newDate.getTime()));

                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        sampaiTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(RekapActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //todo
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                sampaiTanggal.setText(dateFormatter.format(newDate.getTime()));

                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dariTanggal.getText().toString().trim().length() ==0 ||
                        sampaiTanggal.getText().toString().trim().length() ==0) {
                    new SweetAlertDialog(RekapActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Terjadi Kesalahan")
                            .setContentText("Semua Kolom Wajib di isi")
                            .show();
                } else {
                    rekapTransaksis.clear();
                    rekapPenarikans.clear();
                    rekapPengeluarans.clear();
                    RekapBlankFragment.listAdapter.notifyDataSetChanged();
                    RekapBlank2Fragment.listAdapter.notifyDataSetChanged();
                    RekapBlank3Fragment.listAdapter.notifyDataSetChanged();
                    RekapBlank4Fragment.listAdapter.notifyDataSetChanged();
                    finalTotalKas = 0;
                    finalTotalTabungan=0;
                    finalTotalPenarikan = 0;
                    finalTotalPengeluaran=0;
                    getDataTransaksi(dariTanggal.getText().toString(), sampaiTanggal.getText().toString());
                    bottomSheetDialog.hide();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onBackPressed() {
        finalTotalKas = 0;
        finalTotalTabungan=0;
        finalTotalPenarikan = 0;
        finalTotalPengeluaran=0;
        if (!getIntent().hasExtra("dari"))
            startActivity(new Intent(this, MainActivity.class));
        else finish();
        super.onBackPressed();

//        if (getIntent().getStringExtra("dari").equals("detail")) {
//            Intent intent = new Intent(this, DettailAnggotaActivity.class);
//            intent.putExtra("idAnggota", getIntent().getStringArrayExtra("id"));
//            startActivity(intent);
//        } else {
//
//            startActivity(new Intent(this, MainActivity.class));
//        }
    }

    public class tabsPager extends FragmentStatePagerAdapter {

        int tabCount;

        public tabsPager(FragmentManager fm, int numberOfTabs) {
            super(fm);
            this.tabCount = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    RekapBlankFragment tabs1 = new RekapBlankFragment();
                    return tabs1;
                case 1:
                    RekapBlank2Fragment tabs2 = new RekapBlank2Fragment();
                    return tabs2;
                case 2:
                    RekapBlank3Fragment tabs3 = new RekapBlank3Fragment();
                    return tabs3;
                case 3:
                    RekapBlank4Fragment tabs4 = new RekapBlank4Fragment();
                    return tabs4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }


}

