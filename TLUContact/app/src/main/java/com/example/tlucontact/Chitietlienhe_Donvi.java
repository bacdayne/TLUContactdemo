package com.example.tlucontact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Chitietlienhe_Donvi extends AppCompatActivity {

    private TextView txtName, txtPhone, txtAddress;


    private TextView edit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_lienhe);

        // Ánh xạ các TextView từ layout chi_tiet_lienhe.xml
        txtName = findViewById(R.id.txt_name);
        txtPhone = findViewById(R.id.txt_phone);
        txtAddress = findViewById(R.id.txt_address);
        edit = findViewById(R.id.edt_edit);

        // Lấy Intent đã gửi dữ liệu
        Intent intent = getIntent();

        // Lấy dữ liệu từ Intent, sử dụng các key bạn đã truyền trong DonviAdapter
        String tenDonVi = intent.getStringExtra("tenDonVi");
        String soDienThoai = intent.getStringExtra("soDienThoai");
        String diaChi = intent.getStringExtra("diaChi");

        // Hiển thị dữ liệu lên các TextView
        txtName.setText("Tên đơn vị: " + tenDonVi);
        txtPhone.setText("SĐT: " + soDienThoai);
        txtAddress.setText("Địa chỉ: " + diaChi);

    }
}