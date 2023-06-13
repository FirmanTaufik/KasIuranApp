package com.app.kasapp.Rekapitulasi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.kasapp.Models.RekapPenarikan;
import com.app.kasapp.Models.RekapPengeluaran;
import com.app.kasapp.PengeluaranKasActivity;
import com.app.kasapp.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RekapBlank4Fragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public static  ListAdapter listAdapter;
    private TextView textViewTotalPengeluaran;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public RekapBlank4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_frag1 = inflater.inflate(R.layout.fragment_blank_rekap4, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        recyclerView = view_frag1.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(RekapActivity.rekapPengeluarans);
        recyclerView.setAdapter(listAdapter);
        textViewTotalPengeluaran = view_frag1.findViewById(R.id.textViewTotalPengeluaran);

        return view_frag1;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {

        private ArrayList <RekapPengeluaran> rekapPengeluarans;

        public ListAdapter(ArrayList<RekapPengeluaran> rekapPengeluarans) {
            this.rekapPengeluarans = rekapPengeluarans;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView noList, namaAnggota, bayar, tanggalTransaksi,textPengeluaran;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.noList = (TextView) itemView.findViewById(R.id.noList);
                this.bayar = (TextView) itemView.findViewById(R.id.bayar);
                this.namaAnggota = (TextView) itemView.findViewById(R.id.namaAnggota);
                this.tanggalTransaksi = (TextView) itemView.findViewById(R.id.tanggalTransaksi);
                this.textPengeluaran = (TextView) itemView.findViewById(R.id.textPengeluaran);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rekap, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position)
        {
            holder.textPengeluaran.setVisibility(View.VISIBLE);
            holder.namaAnggota.setVisibility(View.GONE);
            if (position==0) {

                holder.noList.setText("No");

                holder.bayar.setText("Keluar");
                holder.textPengeluaran.setText("Keperluan");
                holder.tanggalTransaksi.setText("Tanggal Pengeluaran");

                holder.noList.setBackgroundColor(Color.parseColor("#9C27B0"));
                holder.namaAnggota.setBackgroundColor(Color.parseColor("#9C27B0"));
                holder.bayar.setBackgroundColor(Color.parseColor("#9C27B0"));
                holder.tanggalTransaksi.setBackgroundColor(Color.parseColor("#9C27B0"));
                holder.textPengeluaran.setBackgroundColor(Color.parseColor("#9C27B0"));

            } else {

                int ke = position-1;
                holder.noList.setText(String.valueOf(ke+1));

                holder.bayar.setText(rekapPengeluarans.get(ke).getJumlah_pengeluaran());
                holder.textPengeluaran.setText(rekapPengeluarans.get(ke).getKeperluan());

                holder.tanggalTransaksi.setText(rekapPengeluarans.get(ke).getTanggal_pengeluaran());
            }
            textViewTotalPengeluaran.setText("Total Pengeluaran Rp. "+String.valueOf(RekapActivity.finalTotalPengeluaran));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sharedpreferences.getString("level_anggota", null).equals("1")) {
                        try {
                            RekapActivity.finalTotalKas = 0;
                            RekapActivity.finalTotalTabungan=0;
                            RekapActivity.finalTotalPenarikan = 0;
                            RekapActivity.finalTotalPengeluaran=0;
                            Intent intent = new Intent(getActivity(), PengeluaranKasActivity.class);
                            intent.putExtra("untuk", "edit");
                            intent.putExtra("id", rekapPengeluarans.get(position-1).getId_pengeluaran());
                            intent.putExtra("jumlahPengeluaran", rekapPengeluarans.get(position-1).getJumlah_pengeluaran());
                            intent.putExtra("keperluan", rekapPengeluarans.get(position-1).getKeperluan());
                            intent.putExtra("tanggalPengeluaran", rekapPengeluarans.get(position-1).getTanggal_pengeluaran());
                            startActivity(intent);
                        } catch (IndexOutOfBoundsException i ){
                            i.getMessage();
                        }

                    }

                }
            });

        }

        @Override
        public int getItemCount()
        {
            return rekapPengeluarans.size()+1;
        }
    }

}
