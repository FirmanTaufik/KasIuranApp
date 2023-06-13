package com.app.kasapp.DetailAnggota;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.kasapp.Models.ItemPenarikan;
import com.app.kasapp.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Blank2Fragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public static ListAdapter2 listAdapter2;
    public Blank2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view_frag1 = inflater.inflate(R.layout.fragment_blank2, container, false);
        recyclerView = view_frag1.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listAdapter2 = new ListAdapter2(DettailAnggotaActivity.itemPenarikans);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter2);
        return view_frag1;
    }

    public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ViewHolder>
    {

        private ArrayList <ItemPenarikan> itemPenarikans;
        public ListAdapter2(ArrayList<ItemPenarikan> itemPenarikans) {
            this.itemPenarikans = itemPenarikans;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewJumlahPenarikan, textViewTanggal;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewJumlahPenarikan = (TextView) itemView.findViewById(R.id.textViewJumlahPenarikan);
                this.textViewTanggal = (TextView) itemView.findViewById(R.id.textViewTanggal);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penarikan, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position)
        {
           holder.textViewJumlahPenarikan.setText("Mengambil Tabungan Rp. "+itemPenarikans.get(position).getJumlah_penarikan());
           holder.textViewTanggal.setText(itemPenarikans.get(position).getTanggal_penarikan());
        }

        @Override
        public int getItemCount()
        {
            return itemPenarikans.size();
        }
    }

}
