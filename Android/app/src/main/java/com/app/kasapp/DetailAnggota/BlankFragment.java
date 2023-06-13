package com.app.kasapp.DetailAnggota;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.kasapp.Models.ItemTransaksi;
import com.app.kasapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public static ListAdapter listAdapter;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_frag1 = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = view_frag1.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter(DettailAnggotaActivity.itemTransaksi);
        recyclerView.setAdapter(listAdapter);
        return view_frag1;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList <ItemTransaksi> itemTransaksis;
        public ListAdapter(ArrayList<ItemTransaksi> itemTransaksi) {
            this.itemTransaksis = itemTransaksi;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewJumlahUang, textViewTanggal,
            textViewJumlahUangKas,textViewTanggalKas,
                    textViewJumlahUangTabungan, textViewTanggalTabungan;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewJumlahUang = (TextView) itemView.findViewById(R.id.textViewJumlahUang);
                this.textViewTanggal = (TextView) itemView.findViewById(R.id.textViewTanggal);
                this.textViewJumlahUangKas = (TextView) itemView.findViewById(R.id.textViewJumlahUangKas);
                this.textViewTanggalKas = (TextView) itemView.findViewById(R.id.textViewTanggalKas);
                this.textViewJumlahUangTabungan = (TextView) itemView.findViewById(R.id.textViewJumlahUangTabungan);
                this.textViewTanggalTabungan = (TextView) itemView.findViewById(R.id.textViewTanggalTabungan);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position)
        {
            holder.textViewTanggal.setText(itemTransaksis.get(position).getTanggal_transaksi());
            holder.textViewJumlahUang.setText("Membayar Rp. "+itemTransaksis.get(position).getJumlah_transaksi());

            holder.textViewJumlahUangKas.setText("Masuk Ke Kas Rp. "+itemTransaksis.get(position).getJumlah_kas());
            holder.textViewTanggalKas.setText(itemTransaksis.get(position).getTanggal_transaksi());

            holder.textViewJumlahUangTabungan.setText("Masuk ke Tabungan Rp. "+itemTransaksis.get(position).getJumla_tabungan());
            holder.textViewTanggalTabungan.setText(itemTransaksis.get(position).getTanggal_transaksi());
        }

        @Override
        public int getItemCount()
        {
            return itemTransaksis.size();
        }
    }
}
