package com.example.healthtrackingapp.data.models;

public class TinhTrangSucKhoe {
    private String tinhTrang;
    private boolean isOn;

    public TinhTrangSucKhoe(String tinhTrang, boolean isOn) {
        this.tinhTrang = tinhTrang;
        this.isOn = isOn;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
