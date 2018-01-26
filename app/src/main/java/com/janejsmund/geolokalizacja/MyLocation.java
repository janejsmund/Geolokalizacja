package com.janejsmund.geolokalizacja;


class MyLocation {

    private String nazwa;
    private String opis;
    private String promien;
    private String latitude;
    private String longitude;

    MyLocation() {}

    private MyLocation(String nazwa, String opis, String promien, String latitude, String longitude) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.promien = promien;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    void setOpis(String opis) {
        this.opis = opis;
    }

    void setPromien(String promien) {
        this.promien = promien;
    }

    void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    String getNazwa() {
        return nazwa;
    }

    String getOpis() {
        return opis;
    }

    String getPromien() {
        return promien;
    }

    String getLatitude() {
        return latitude;
    }

    String getLongitude() {
        return longitude;
    }
}
