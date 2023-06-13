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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.app.kasapp.BayarActivity;
import com.app.kasapp.DetailAnggota.DettailAnggotaActivity;
import com.app.kasapp.Models.ItemTransaksi;
import com.app.kasapp.Models.RekapTransaksi;
import com.app.kasapp.R;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RekapBlankFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public static  ListAdapter listAdapter;
    private TextView textViewTotalKas;
    private BottomSheetDialog bottomSheetDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public RekapBlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_frag1 = inflater.inflate(R.layout.fragment_blank_rekap, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        recyclerView = view_frag1.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(RekapActivity.rekapTransaksis);
        recyclerView.setAdapter(listAdapter);
        textViewTotalKas = view_frag1.findViewById(R.id.textViewTotalKas);
        return view_frag1;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {

        private ArrayList <RekapTransaksi> rekapTransaksis;

        public ListAdapter(ArrayList<RekapTransaksi> rekapTransaksis) {
            this.rekapTransaksis = rekapTransaksis;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView noList, namaAnggota, bayar, tanggalTransaksi;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.noList = (TextView) itemView.findViewById(R.id.noList);
                this.bayar = (TextView) itemView.findViewById(R.id.bayar);
                this.namaAnggota = (TextView) itemView.findViewById(R.id.namaAnggota);
                this.tanggalTransaksi = (TextView) itemView.findViewById(R.id.tanggalTransaksi);
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

            if (position==0) {

                holder.noList.setText("No");
                holder.namaAnggota.setText("Nama Anggota");
                holder.bayar.setText("Bayar");
                holder.tanggalTransaksi.setText("Tanggal Bayar");

                holder.noList.setBackgroundColor(Color.parseColor("#FFC107"));
                holder.namaAnggota.setBackgroundColor(Color.parseColor("#FFC107"));
                holder.bayar.setBackgroundColor(Color.parseColor("#FFC107"));
                holder.tanggalTransaksi.setBackgroundColor(Color.parseColor("#FFC107"));

            } else {

                int ke = position-1;

                holder.noList.setText(String.valueOf(ke+1));
                holder.namaAnggota.setText(rekapTransaksis.get(ke).getNama_anggota());
                holder.bayar.setText(rekapTransaksis.get(ke).getJumlah_kas());
                holder.tanggalTransaksi.setText(rekapTransaksis.get(ke).getTanggal_transaksi());
            }
            textViewTotalKas.setText("Total Kas Rp. "+String.valueOf(RekapActivity.finalTotalKas));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sharedpreferences.getString("level_anggota", null).equals("1")) {
                        try {

                            RekapActivity.finalTotalKas = 0;
                            RekapActivity.finalTotalTabungan=0;
                            RekapActivity.finalTotalPenarikan = 0;
                            RekapActivity.finalTotalPengeluaran=0;
                            Intent intent = new Intent(getActivity(), BayarActivity.class);
                            intent.putExtra("untuk", "edit");
                            intent.putExtra("id", rekapTransaksis.get(position-1).getId_transaksi());
                            intent.putExtra("nama", rekapTransaksis.get(position-1).getNama_anggota());
                            intent.putExtra("bayar", rekapTransaksis.get(position-1).getJumlah_transaksi());
                            intent.putExtra("kas", rekapTransaksis.get(position-1).getJumlah_kas());
                            intent.putExtra("tabungan", rekapTransaksis.get(position-1).getJumlah_tabungan());
                            intent.putExtra("tanggalBayar", rekapTransaksis.get(position-1).getTanggal_transaksi());
                            startActivity(intent);

                        } catch (IndexOutOfBoundsException i) {
                            i.getMessage();
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount()
        {
            return rekapTransaksis.size()+1;
        }
    }

}
