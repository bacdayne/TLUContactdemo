package com.example.tlucontact;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class contactViewHolder_CBNV extends RecyclerView.ViewHolder {
    private TextView tv_name, tv_position, tv_phone, tv_email, tv_department;

    public contactViewHolder_CBNV(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.txt_name);

//        tv_position = itemView.findViewById(R.id.txt_position_label);
//        tv_phone = itemView.findViewById(R.id.txt_phone_label);
//        tv_email = itemView.findViewById(R.id.txt_email_label);
//        tv_department = itemView.findViewById(R.id.txt_department_label);
    }
    public void bind(dbCBNV cbnv) {
        tv_name.setText(cbnv.getTenCBNV());
//        tv_position.setText(cbnv.getChucVu());
//        tv_phone.setText(String.valueOf(cbnv.getSdt()));
//        tv_email.setText(cbnv.getEmail());
//        tv_department.setText(cbnv.getDonVi());
    }

}
