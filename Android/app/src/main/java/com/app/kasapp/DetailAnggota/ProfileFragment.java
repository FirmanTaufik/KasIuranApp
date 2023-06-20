package com.app.kasapp.DetailAnggota;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.kasapp.MainActivity;
import com.app.kasapp.R;
import com.app.kasapp.Server;
import com.app.kasapp.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;


public class ProfileFragment extends Fragment {

    public interface OnListener{
        void onChange(String result);
    }

    private OnListener onListener  ;

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }
    private String TAG = "ProfileFragmentTAG";
    private FragmentProfileBinding binding;
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: "+ DettailAnggotaActivity.dataAnggota.toString());

        JSONArray jsonArray1 = null;
        try {
            jsonArray1 = DettailAnggotaActivity.dataAnggota.getJSONArray("DataAnggota");
            for (int a= 0; a < jsonArray1.length(); a++) {
                String npm = jsonArray1.getJSONObject(a).getString("npm");
                binding.textNpm.setText("NPM : "+npm);
                String nama = jsonArray1.getJSONObject(a).getString("nama_anggota");
                binding.textViewNamaPengguna.setText( nama);
                String jurusan = jsonArray1.getJSONObject(a).getString("jurusan");
                binding.textJurusan.setText(": "+jurusan);
                String semester = jsonArray1.getJSONObject(a).getString("semester");
                binding.textSemester.setText(": "+semester);
                String alamat = jsonArray1.getJSONObject(a).getString("alamat");
                binding.textAlamat.setText(": "+alamat);
                String no_hp = jsonArray1.getJSONObject(a).getString("no_hp");
                binding.textNoHp.setText(": "+no_hp);
                String tempat_lahir = jsonArray1.getJSONObject(a).getString("tempat_lahir");
                String tanggal_lahir = jsonArray1.getJSONObject(a).getString("tanggal_lahir");
                binding.textTTL.setText(": "+tempat_lahir +", "+tanggal_lahir );
                String foto = jsonArray1.getJSONObject(a).getString("foto");
                Glide.with(getActivity()).load(Server.BASE_URL_IMAGE  + foto).into(binding.image);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        super.onViewCreated(view, savedInstanceState);
    }
}