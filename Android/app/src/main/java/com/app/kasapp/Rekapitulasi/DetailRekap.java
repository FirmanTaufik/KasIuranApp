package com.app.kasapp.Rekapitulasi;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.app.kasapp.R;

public class DetailRekap {
    Context context;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
    public DetailRekap(Context context) {
        this.context = context;

    }

    public void showBottomSheet() {
        bottomSheet();
        bottomSheetDialog.show();
    }

    public void bottomSheet() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.input_anggota, null);


        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ((View) view.getParent()).setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
    }
}
