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
import com.app.kasapp.Models.RekapTransaksi;
import com.app.kasapp.PenarikanTabunganActivity;
import com.app.kasapp.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RekapBlank3Fragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView textViewTotalPenarikan;
    public static ListAdapter listAdapter;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public RekapBlank3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_frag1 = inflater.inflate(R.layout.fragment_blank_rekap3, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        recyclerView = view_frag1.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(RekapActivity.rekapPenarikans);
        recyclerView.setAdapter(listAdapter);
        textViewTotalPenarikan = view_frag1.findViewById(R.id.textViewTotalPenarikan);
        return view_frag1;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {

        private ArrayList <RekapPenarikan> rekapPenarikans;
        public ListAdapter(ArrayList<RekapPenarikan> rekapPenarikans) {
            this.rekapPenarikans = rekapPenarikans;
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
                holder.bayar.setText("Penarikan");
                holder.tanggalTransaksi.setText("Tanggal Penarikan");

                holder.noList.setBackgroundColor(Color.parseColor("#4CAF50"));
                holder.namaAnggota.setBackgroundColor(Color.parseColor("#4CAF50"));
                holder.bayar.setBackgroundColor(Color.parseColor("#4CAF50"));
                holder.tanggalTransaksi.setBackgroundColor(Color.parseColor("#4CAF50"));

            } else {

                int ke = position-1;

                holder.noList.setText(String.valueOf(ke+1));
                holder.namaAnggota.setText(rekapPenarikans.get(ke).getNama_anggota());
                holder.bayar.setText(rekapPenarikans.get(ke).getJumlah_penarikan());
                holder.tanggalTransaksi.setText(rekapPenarikans.get(ke).getTanggal_penarikan());
            }

            textViewTotalPenarikan.setText("Total Penarikan Rp. "+String.valueOf(RekapActivity.finalTotalPenarikan));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sharedpreferences.getString("level_anggota", null).equals("1")) {
                        try {
                            RekapActivity.finalTotalKas = 0;
                            RekapActivity.finalTotalTabungan=0;
                            RekapActivity.finalTotalPenarikan = 0;
                            RekapActivity.finalTotalPengeluaran=0;
                            Intent intent = new Intent(getActivity(), PenarikanTabunganActivity.class);
                            intent.putExtra("untuk","edit");
                            intent.putExtra("id", rekapPenarikans.get(position-1).getId_penarikan());
                            intent.putExtra("idAnggota", rekapPenarikans.get(position-1).getId_anggota());
                            intent.putExtra("nama", rekapPenarikans.get(position-1).getNama_anggota());
                            intent.putExtra("jumlahPenarikan", rekapPenarikans.get(position-1).getJumlah_penarikan());
                            intent.putExtra("tanggalPenarikan", rekapPenarikans.get(position-1).getTanggal_penarikan());
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
            return rekapPenarikans.size()+1;
        }
    }
}
