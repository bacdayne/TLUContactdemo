package com.example.tlucontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChiTietCbnvActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtPhone;
    private TextView txtPosition;
    private TextView txtEmail;
    private TextView txtDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_cbnv); // Sử dụng lại layout đã sửa đổi

        // Ánh xạ các TextView
        txtName = findViewById(R.id.txt_name);
        txtPhone = findViewById(R.id.txt_phone_label);
        txtPosition = findViewById(R.id.txt_position_label);
        txtEmail = findViewById(R.id.txt_email_label);
        txtDepartment = findViewById(R.id.txt_department_label);

        // Lấy Intent
        Intent intent = getIntent();

        // Lấy dữ liệu từ Intent
        String tenCbnv = intent.getStringExtra("tenCbnv");
        String soDienThoai = intent.getStringExtra("soDienThoai");
        String diaChi = intent.getStringExtra("diaChi");
        String chucVu = intent.getStringExtra("chucVu");
        String email = intent.getStringExtra("email");
        String donViCongTac = intent.getStringExtra("donViCongTac");

        // Hiển thị dữ liệu lên TextViews
        txtName.setText("Tên CBNV: " + tenCbnv);
        txtPhone.setText("SĐT: " + soDienThoai);
        txtPosition.setText("Chức vụ: " + chucVu);
        txtEmail.setText("Email: " + email);
        txtDepartment.setText("Đơn vị: " + donViCongTac);
    }
}