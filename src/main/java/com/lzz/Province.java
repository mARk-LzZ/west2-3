package com.lzz;


public class Province {
    private String country;
    private String province;
    private String lat;
    private String lon;
    private Integer confirmed;
    private Integer recovered;
    private Integer deaths;
    private String updated;

    @Override
    public String toString() {
        return "Province{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", confirmed=" + confirmed +
                ", recovered=" + recovered +
                ", deaths=" + deaths +
                ", updated=" + updated +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country=country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province=province;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat=lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon=lon;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed=confirmed;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered=recovered;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths=deaths;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated=updated;
    }
}
