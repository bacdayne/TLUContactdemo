package com.example.tlucontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CBNV_Adapter extends RecyclerView.Adapter<contactViewHolder_CBNV> {
    private ArrayList<dbCBNV> cbnvList;
    private ArrayList<dbCBNV> originalList;
    private Context context;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position); // Thay đổi thành boolean
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public CBNV_Adapter(Context context, ArrayList<dbCBNV> cbnvList) {
        this.context = context;
        this.cbnvList = cbnvList;
        this.originalList = new ArrayList<>(cbnvList);
    }

    @NonNull
    @Override
    public contactViewHolder_CBNV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_db_cbnv, parent, false);
        return new contactViewHolder_CBNV(v);
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder_CBNV holder, int position) {
        dbCBNV selectedCbnv = cbnvList.get(position);
        holder.bind(selectedCbnv);

        // Thiết lập OnClickListener cho mỗi item để xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietCbnvActivity.class);
            intent.putExtra("tenCbnv", selectedCbnv.getTenCBNV());
            intent.putExtra("chucVu", selectedCbnv.getChucVu());
            intent.putExtra("soDienThoai", selectedCbnv.getSdt());
            intent.putExtra("email", selectedCbnv.getEmail());
            intent.putExtra("donViCongTac", selectedCbnv.getDonVi());
            context.startActivity(intent);
        });

        // Thiết lập OnLongClickListener cho mỗi item để xóa/sửa
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    return longClickListener.onItemLongClick(adapterPosition); // Trả về giá trị boolean từ listener
                }
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return (cbnvList != null) ? cbnvList.size() : 0;
    }

    public void updateList(ArrayList<dbCBNV> cbnvList) {
        this.cbnvList = cbnvList;
        notifyDataSetChanged();
    }

    public void filter(String string) {
        ArrayList<dbCBNV> filteredList = new ArrayList<>();
        String lowerCaseFilter = string.toLowerCase();
        for (dbCBNV item : originalList) { // Lọc trên danh sách gốc
            if (item.getTenCBNV().toLowerCase().contains(lowerCaseFilter)) {
                filteredList.add(item);
            }
        }
        updateList(filteredList);
    }

    public void resetData() {
        updateList(originalList);
    }
}