package com.example.tlucontact;

import android.os.Parcel;
import android.os.Parcelable;

public class dbdonvi implements Parcelable {
    private String tenDonVi;
    private String sdt;
    private String diaChi;

    public dbdonvi(String tenDonVi, String sdt, String diaChi) {
        this.tenDonVi = tenDonVi;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    protected dbdonvi(Parcel in) {
        tenDonVi = in.readString();
        sdt = in.readString();
        diaChi = in.readString();
    }

    public static final Creator<dbdonvi> CREATOR = new Creator<dbdonvi>() {
        @Override
        public dbdonvi createFromParcel(Parcel in) {
            return new dbdonvi(in);
        }

        @Override
        public dbdonvi[] newArray(int size) {
            return new dbdonvi[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tenDonVi);
        dest.writeString(sdt);
        dest.writeString(diaChi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        this.tenDonVi = tenDonVi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
