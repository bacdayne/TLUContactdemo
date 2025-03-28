package com.example.tlucontact;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class DanhBaDonVi extends AppCompatActivity {
    private DonviAdapter donviAdapter;
    private Toolbar toolbar_dbdonvi;
    private RecyclerView recyclerView_donvi;

    private ArrayList<dbdonvi> donviList; // Chuyển từ mảng sang ArrayList để dễ cập nhật




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_ba_don_vi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rcv_dbdonvi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar_dbdonvi = findViewById(R.id.toolbar_dbdv);
        setSupportActionBar(toolbar_dbdonvi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_dbdonvi.setNavigationOnClickListener(v -> finish());
        // Nhận danh sách từ Intent hoặc dùng danh sách mặc định
        donviList = new ArrayList<>();
        donviList = getIntent().getParcelableArrayListExtra("donvi_list");
        if (donviList == null) {
            donviList = new ArrayList<>(Arrays.asList(donvi)); // Nếu không có dữ liệu từ Intent, dùng danh sách mặc định
        }

        recyclerView_donvi = findViewById(R.id.rcv_dbdonvi);
        recyclerView_donvi.setLayoutManager(new LinearLayoutManager(this));
        donviAdapter = new DonviAdapter(this, donviList);
        recyclerView_donvi.setAdapter(donviAdapter);
    }
    // Thêm menu "Thêm đơn vị" vào Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_donvi, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_donvi) {
            showAddDonViDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showAddDonViDialog() {
        // Hiển thị AlertDialog để thêm đơn vị mới
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm đơn vị");

        // Layout chứa các EditText
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        final EditText editTextTenDonVi = new EditText(this);
        editTextTenDonVi.setHint("Tên đơn vị");
        layout.addView(editTextTenDonVi);

        final EditText editTextSoDienThoai = new EditText(this);
        editTextSoDienThoai.setHint("Số điện thoại");
        layout.addView(editTextSoDienThoai);

        final EditText editTextDiaChi = new EditText(this);
        editTextDiaChi.setHint("Địa chỉ");
        layout.addView(editTextDiaChi);

        builder.setView(layout);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String tenDonVi = editTextTenDonVi.getText().toString();
            String soDienThoai = editTextSoDienThoai.getText().toString();
            String diaChi = editTextDiaChi.getText().toString();
            if (tenDonVi.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty()) {
                Toast.makeText(DanhBaDonVi.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                dbdonvi newDonVi = new dbdonvi(tenDonVi, soDienThoai, diaChi);
                donviList.add(newDonVi);
                donviAdapter.notifyDataSetChanged();
                Toast.makeText(DanhBaDonVi.this, "Thêm đơn vị thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();


    }
    dbdonvi[] donvi = {
            new dbdonvi("Hành chính - Tổng hợp", "123456789", "116 Nguyễn Huệ, Quận 1, TP.HCM"),
            new dbdonvi("Phòng Đào tạo", "987654321", "12 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội"),
            new dbdonvi("Công tác Sinh viên", "112233445", "45 Hai Bà Trưng, Quận 1, TP.HCM"),
            new dbdonvi("Công nghệ Thông tin", "223344556", "78 Trần Hưng Đạo, Quận 5, TP.HCM"),
            new dbdonvi("Quản trị Kinh doanh", "334455667", "23 Nguyễn Văn Linh, Hải Châu, Đà Nẵng"),
            new dbdonvi("Kế toán - Kiểm toán", "445566778", "56 Phạm Văn Đồng, Cầu Giấy, Hà Nội"),
            new dbdonvi("Trung tâm Ngoại ngữ - Tin học", "556677889", "89 Lê Lợi, Ngô Quyền, Hải Phòng"),
            new dbdonvi("Thư viện", "667788990", "34 Võ Thị Sáu, Ninh Kiều, Cần Thơ"),
            new dbdonvi("Phòng Tài chính - Kế hoạch", "778899001", "67 Điện Biên Phủ, Bình Thạnh, TP.HCM"),
            new dbdonvi("Phòng Thanh tra - Pháp chế", "889900112", "90 Nguyễn Đình Chiểu, Quận 3, TP.HCM")
    };

}