package com.example.tlucontact;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DanhBaCBNV extends AppCompatActivity { // Loại bỏ <com.example.tlucontact.DatabaseHelper>

    private CBNV_Adapter cbnvAdapter;
    private Toolbar toolbar_dbCBNV;
    private RecyclerView recyclerView_cbnv;
    private ArrayList<dbCBNV> cbnvList;
    private EditText editTextSearchCBNV;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_ba_cbnv);
        databaseHelper = new DatabaseHelper(this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        databaseHelper.openDataBase();


        initViews();
        setupToolbar();
        loadCBNVData();
        setupRecyclerView();
        setupSearchFunctionality();
    }

    private void initViews() {
        recyclerView_cbnv = findViewById(R.id.rcv_CBNV);
        toolbar_dbCBNV = findViewById(R.id.toolbar_dbCBNV);
        editTextSearchCBNV = findViewById(R.id.editTextSearchCBNV);

        // Apply window insets to RecyclerView
        ViewCompat.setOnApplyWindowInsetsListener(recyclerView_cbnv, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Phương thức để load dữ liệu từ database
    @SuppressLint("Range")
    private void loadCBNVDataFromDB() {
        cbnvList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TB_SINHVIEN", null);
        while (cursor.moveToNext()) {
            String masv = cursor.getString(cursor.getColumnIndex("MaSV"));
            String ten = cursor.getString(cursor.getColumnIndex("TEN"));
            String lop = cursor.getString(cursor.getColumnIndex("LOP"));
            String diem = cursor.getString(cursor.getColumnIndex("DIEM"));
            dbCBNV sinhvien = new dbCBNV(masv, ten, lop, diem);
            cbnvList.add(sinhvien);
        }


        // Thực hiện truy vấn để lấy dữ liệu từ database
        // Ví dụ:
        // Cursor cursor = db.query(...);
        // while (cursor.moveToNext()) {
        //     dbCBNV cbnv = new dbCBNV(...);
        //     cbnvList.add(cbnv);
        // }
        // cursor.close();
        // db.close();

        sortCBNVList(); // Sắp xếp danh sách sau khi lấy dữ liệu
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar_dbCBNV);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar_dbCBNV.setNavigationOnClickListener(v -> finish());
    }

    private void loadCBNVData() {
        cbnvList = getIntent().getParcelableArrayListExtra("cbnv_list");
        if (cbnvList == null) {
            cbnvList = new ArrayList<>(Arrays.asList(getDefaultCBNVData()));
        }
        sortCBNVList(); // Sắp xếp danh sách sau khi lấy dữ liệu
    }

    private void setupRecyclerView() {
        recyclerView_cbnv.setLayoutManager(new LinearLayoutManager(this));
        cbnvAdapter = new CBNV_Adapter(this, cbnvList);
        recyclerView_cbnv.setAdapter(cbnvAdapter);

        // Setup long click listener for edit/delete options
        cbnvAdapter.setOnItemLongClickListener(position -> {
            showOptionsDialog(position);
            return true;
        });
    }

    private void setupSearchFunctionality() {
        editTextSearchCBNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cbnvAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextSearchCBNV.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                editTextSearchCBNV.setText("");
                cbnvAdapter.resetData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cbnv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_cbnv) {
            showAddCBNVDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddCBNVDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm CBNV");

        LinearLayout layout = createCBNVInputLayout();
        builder.setView(layout);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            ArrayList<EditText> editTexts = getEditTextsFromLayout(layout);
            if (validateInput(editTexts)) {
                dbCBNV newCBNV = createCBNVFromInput(editTexts);
                cbnvList.add(newCBNV);
                sortCBNVList(); // Sắp xếp sau khi thêm
                cbnvAdapter.notifyDataSetChanged(); // Cập nhật adapter
                Toast.makeText(this, "Đã thêm CBNV!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private LinearLayout createCBNVInputLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        String[] hints = {"Tên CBNV", "Chức vụ", "Email", "Đơn vị", "Số điện thoại"};
        for (String hint : hints) {
            EditText editText = new EditText(this);
            editText.setHint(hint);
            layout.addView(editText);
        }
        return layout;
    }

    private ArrayList<EditText> getEditTextsFromLayout(LinearLayout layout) {
        ArrayList<EditText> editTexts = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            if (layout.getChildAt(i) instanceof EditText) {
                editTexts.add((EditText) layout.getChildAt(i));
            }
        }
        return editTexts;
    }

    private boolean validateInput(ArrayList<EditText> editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private dbCBNV createCBNVFromInput(ArrayList<EditText> editTexts) {
        String ten = editTexts.get(0).getText().toString().trim();
        String chucVu = editTexts.get(1).getText().toString().trim();
        String email = editTexts.get(2).getText().toString().trim();
        String donVi = editTexts.get(3).getText().toString().trim();
        String sdt = editTexts.get(4).getText().toString().trim();
        return new dbCBNV(ten, chucVu, email, donVi, sdt);
    }

    private dbCBNV[] getDefaultCBNVData() {
        return new dbCBNV[]{
                new dbCBNV("Nguyễn Văn A", "Giảng viên", "a@gmail.com", "Phòng Đào tạo", "234566742"),
                new dbCBNV("Trần Thị B", "Trợ giảng", "b@gmail.com", "Phòng Công tác Sinh viên", "345678123"),
                new dbCBNV("Lê Văn C", "Hiệu trưởng", "c@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "456789234"),
                new dbCBNV("Phó hiệu trưởng", "Phó hiệu trưởng", "d@gmail.com", "Phòng Đào tạo", "567890345"),
                new dbCBNV("Hoàng Văn E", "Trưởng phòng đào tạo", "e@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "678901456"),
                new dbCBNV("Đỗ Thị F", "Nhân viên thư viện", "f@gmail.com", "Thư viện", "789012567"),
                new dbCBNV("Vũ Văn G", "Cố vấn học tập", "g@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "890123678"),
                new dbCBNV("Ngô Thị H", "Giáo vụ", "h@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "901234789"),
                new dbCBNV("Bùi Văn I", "Nhân viên văn phòng", "i@gmail.com", "Trung tâm Ngoại ngữ - Tin học", "912345890"),
                new dbCBNV("Dương Thị K", "Thanh tra giáo dục", "k@gmail.com", "Phòng Thanh tra - Pháp chế", "923456901")
        };
    }

    private void showOptionsDialog(int position) {
        final CharSequence[] options = {"Sửa", "Xóa", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn tùy chọn");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Sửa")) {
                showEditCBNVDialog(position);
            } else if (options[item].equals("Xóa")) {
                deleteCBNV(position);
            } else if (options[item].equals("Hủy")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showEditCBNVDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa CBNV");

        LinearLayout layout = createCBNVInputLayout();
        ArrayList<EditText> editTexts = getEditTextsFromLayout(layout);

        // Populate EditTexts with current data
        dbCBNV currentCBNV = cbnvList.get(position);
        editTexts.get(0).setText(currentCBNV.getTen());
        editTexts.get(1).setText(currentCBNV.getChucVu());
        editTexts.get(2).setText(currentCBNV.getEmail());
        editTexts.get(3).setText(currentCBNV.getDonVi());
        editTexts.get(4).setText(currentCBNV.getSdt());

        builder.setView(layout);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            if (validateInput(editTexts)) {
                dbCBNV updatedCBNV = createCBNVFromInput(editTexts);
                cbnvList.set(position, updatedCBNV);
                sortCBNVList(); // Sắp xếp sau khi sửa
                cbnvAdapter.notifyDataSetChanged(); // Cập nhật adapter
                Toast.makeText(this, "Đã sửa CBNV!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void deleteCBNV(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa CBNV này?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            cbnvList.remove(position);
            cbnvAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "Đã xóa CBNV!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void sortCBNVList() {
        cbnvList.sort((cbnv1, cbnv2) -> cbnv1.getTen().compareToIgnoreCase(cbnv2.getTen()));
    }
}