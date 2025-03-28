package com.example.tlucontact;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class contactViewHolder_donvi extends RecyclerView.ViewHolder {
    private TextView tv_name, tv_phone, tv_address;

    public contactViewHolder_donvi(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.txt_name);
//        tv_phone = itemView.findViewById(R.id.txt_phone);
//        tv_address = itemView.findViewById(R.id.txt_address);

    }
    public void bind(dbdonvi donvi) {
        tv_name.setText(donvi.getTenDonVi());
//        tv_phone.setText(String.valueOf(donvi.getSdt()));
//        tv_address.setText(donvi.getDiaChi());
    }

}
