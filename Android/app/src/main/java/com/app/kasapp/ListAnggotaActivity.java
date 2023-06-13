package com.app.kasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.app.kasapp.DetailAnggota.DettailAnggotaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAnggotaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList <String > namaAnggota;
    private ArrayList <String> idAnggota;
    private LinearLayoutManager linearLayoutManager;
    private ListAdapter listAdapter;
    private SwipeRefreshLayout swipe;
    private FloatingActionButton fabTambahAnggota;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_anggota);
        namaAnggota= new ArrayList<>();
        idAnggota = new ArrayList<>();
        swipe = findViewById(R.id.swipe);
        swipe.setRefreshing(true);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(namaAnggota, idAnggota);
        recyclerView.setAdapter(listAdapter);
        fabTambahAnggota = findViewById(R.id.fabTambahAnggota);
        fabTambahAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet();
                bottomSheetDialog.show();
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                namaAnggota.clear();
                idAnggota.clear();
                listAdapter.notifyDataSetChanged();
                getData();
            }
        });

        getData();
    }

    private void bottomSheet() {
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.input_anggota, null);
        final EditText editTextNamaAnggota = view.findViewById(R.id.editTextNamaAnggota);
        Button buttonSimpanAnggota = view.findViewById(R.id.buttonSimpanAnggota);

        buttonSimpanAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanData(editTextNamaAnggota.getText().toString());
            }
        });

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void simpanData(final String namaAnggota) {
            String URL = Server.BASE_URL+"tambah_anggota.php";
            final ProgressDialog progressDialog = new ProgressDialog(ListAnggotaActivity.this);
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
                                    Toast.makeText(ListAnggotaActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                    pesanBerhasil();
                                } else {
                                    Toast.makeText(ListAnggotaActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ListAnggotaActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("nama_anggota",namaAnggota);
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

    private void pesanBerhasil() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Succes!")
                .setContentText("Penyimpanan Berhasil")
                .show();
        Intent intent = new Intent(this, ListAnggotaActivity.class);
        startActivity(intent);
    }


    private void getData() {
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

                        idAnggota.add(id);
                        namaAnggota.add(nama);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        swipe.setRefreshing(false);
                    }
                }
                listAdapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
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

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList <String> namaAnggota;
        private ArrayList <String> idAnggota;

        public ListAdapter(ArrayList<String> namaAnggota, ArrayList<String> idAnggota) {
            this.namaAnggota = namaAnggota;
            this.idAnggota = idAnggota;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewNamaAnggota;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewNamaAnggota = (TextView) itemView.findViewById(R.id.textViewNamaAnggota);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_anggota, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position)
        {
            holder.textViewNamaAnggota.setText(position+1 +". " +namaAnggota.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListAnggotaActivity.this, DettailAnggotaActivity.class);
                    intent.putExtra("idAnggota", idAnggota.get(position));
                    startActivity(intent);
                  //  Toast.makeText(ListAnggotaActivity.this, "kamu klik id "+idAnggota.get(position) , Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount()
        {
            return namaAnggota.size();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
