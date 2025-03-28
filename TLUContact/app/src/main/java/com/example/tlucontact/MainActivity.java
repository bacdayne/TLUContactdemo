package com.example.tlucontact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn_dsach_donvi, btn_dsach_CBNV;
    private RecyclerView recyclerView_donvi, recyclerView_cbnv;
    private DonviAdapter donviAdapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_dsach_donvi = findViewById(R.id.btn_dsach_donvi);
        btn_dsach_CBNV = findViewById(R.id.btn_dsach_CBNV);
        btn_dsach_donvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<dbdonvi> donviList = new ArrayList<>();
                donviList.add(new dbdonvi("Hành chính - Tổng hợp", "123456789", "116 Nguyễn Huệ, Quận 1, TP.HCM"));
                donviList.add(new dbdonvi("Phòng Đào tạo", "987654321", "12 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội"));
                donviList.add(new dbdonvi("Công tác Sinh viên", "112233445", "45 Hai Bà Trưng, Quận 1, TP.HCM"));
                donviList.add(new dbdonvi("Công nghệ Thông tin", "223344556", "78 Trần Hưng Đạo, Quận 5, TP.HCM"));
                donviList.add(new dbdonvi("Quản trị Kinh doanh", "334455667", "23 Nguyễn Văn Linh, Hải Châu, Đà Nẵng"));
                donviList.add(new dbdonvi("Kế toán - Kiểm toán", "445566778", "56 Phạm Văn Đồng, Cầu Giấy, Hà Nội"));
                donviList.add(new dbdonvi("Thư viện", "556677889", "89 Lê Duẩn, Quận 1, TP.HCM"));
                donviList.add(new dbdonvi("Phòng Thanh tra - Pháp chế", "667788990", "101 Lê Lai, Quận 1, TP.HCM"));
                Log.d("MainActivity", "Danh sách đơn vị: " + donviList);

                Intent intent = new Intent(MainActivity.this, DanhBaDonVi.class);
                intent.putParcelableArrayListExtra("donvi_list", donviList);
                startActivity(intent);
            }
        });
        btn_dsach_CBNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khởi tạo danh sách CBNV
                ArrayList<dbCBNV> cbnvList = new ArrayList<>();
                cbnvList.add(new dbCBNV("Nguyễn Văn A", "Giảng viên", "a@gmail.com", "Phòng Đào tạo", "234566742"));
                cbnvList.add(new dbCBNV("Trần Thị B", "Trợ giảng", "b@gmail.com", "Phòng Công tác Sinh viên", "345678123"));
                cbnvList.add(new dbCBNV("Lê Văn C", "Hiệu trưởng", "c@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "456789234"));
                cbnvList.add(new dbCBNV("Phạm Thị D", "Phó hiệu trưởng", "d@gmail.com", "Phòng Đào tạo", "567890345"));
                cbnvList.add(new dbCBNV("Hoàng Văn E", "Trưởng phòng đào tạo", "e@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "678901456"));
                cbnvList.add(new dbCBNV("Đỗ Thị F", "Nhân viên thư viện", "f@gmail.com", "Thư viện", "789012567"));
                cbnvList.add(new dbCBNV("Vũ Văn G", "Cố vấn học tập", "g@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "890123678"));
                cbnvList.add(new dbCBNV("Ngô Thị H", "Giáo vụ", "h@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "901234789"));
                cbnvList.add(new dbCBNV("Bùi Văn I", "Nhân viên văn phòng", "i@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "912345890"));
                cbnvList.add(new dbCBNV("Dương Thị K", "Thanh tra giáo dục", "k@gmail.com", "Phòng Thanh tra - Pháp chế", "923456901"));
                Log.d("MainActivity", "Danh sách CBNV: " + cbnvList);
                // Tạo Intent và truyền danh sách CBNV
                Intent intent = new Intent(MainActivity.this, DanhBaCBNV.class);
                intent.putParcelableArrayListExtra("cbnv_list", cbnvList);
                startActivity(intent);
            }
        });

    }
}