package com.example.tlucontact;

import android.os.Parcel;
import android.os.Parcelable;

public class dbCBNV implements Parcelable {
    private String ten, chucVu, email, donVi, sdt;

    public dbCBNV(String ten, String chucVu, String email, String donVi, String sdt) {
        this.ten = ten;
        this.chucVu = chucVu;
        this.email = email;
        this.donVi = donVi;
        this.sdt = sdt;
    }

    protected dbCBNV(Parcel in) {
        ten = in.readString();
        chucVu = in.readString();
        email = in.readString();
        donVi = in.readString();
        sdt = in.readString();
    }

    public static final Creator<dbCBNV> CREATOR = new Creator<dbCBNV>() {
        @Override
        public dbCBNV createFromParcel(Parcel in) {
            return new dbCBNV(in);
        }

        @Override
        public dbCBNV[] newArray(int size) {
            return new dbCBNV[size];
        }
    };

    public dbCBNV(String masv, String ten, String lop, String diem) {
        this.ten = ten;
        this.chucVu = chucVu;
        this.email = email;
        this.donVi = donVi;
        this.sdt = sdt;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ten);
        dest.writeString(chucVu);
        dest.writeString(email);
        dest.writeString(donVi);
        dest.writeString(sdt);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public String getChucVu() {
        return chucVu;
    }
    public String getEmail() {
        return email;
    }
    public String getDonVi() {
        return donVi;
    }
    public String getSdt() {
        return sdt;
    }
    public String getTenCBNV() {
        return ten;
    }

    public String getTen() {
        return ten;
    }
}
