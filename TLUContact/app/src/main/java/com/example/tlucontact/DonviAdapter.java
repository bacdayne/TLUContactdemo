package com.example.tlucontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonviAdapter extends RecyclerView.Adapter<contactViewHolder_donvi> {
    private ArrayList<dbdonvi> donviList; // Đổi từ mảng [] sang ArrayList<>
    private Context context;

    public DonviAdapter(Context context, ArrayList<dbdonvi> donviList) {
        this.context = context;
        this.donviList = donviList;
    }

    @NonNull
    @Override
    public contactViewHolder_donvi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_db_donvi, parent, false);
        return new contactViewHolder_donvi(v);
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder_donvi holder, int position) {
        dbdonvi selectedDonvi = donviList.get(position);
        holder.bind(selectedDonvi);

        // Thiết lập OnClickListener cho mỗi item
        holder.itemView.setOnClickListener(v -> {

            // Tạo Intent để chuyển sang Activity chi tiết
            Intent intent = new Intent(context, Chitietlienhe_Donvi.class);
            // Truyền dữ liệu của đơn vị được chọn qua Intent
            intent.putExtra("tenDonVi", selectedDonvi.getTenDonVi());
            intent.putExtra("soDienThoai", selectedDonvi.getSdt());
            intent.putExtra("diaChi", selectedDonvi.getDiaChi());
            // Bạn có thể truyền thêm dữ liệu khác nếu cần

            // Khởi chạy Activity chi tiết
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (donviList != null) ? donviList.size() : 0;
    }
}