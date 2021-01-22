package com.lzz;

import java.io.Serializable;

public class Country implements Serializable {
    private String country;
    private Integer population;
    private Integer sq_km_area;
    private  String  life_expectancy;
    private Object elevation_in_meters;
    private String continent;
    private String abbreviation;
    private String location;
    private Integer iso;
    private String capital_city;
    private Integer confirmed;
    private Integer recovered;
    private Integer deaths;

    public Integer getIso() {
        return iso;
    }

    public void setIso(Integer iso) {
        this.iso=iso;
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



    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", population='" + population + '\'' +
                ", sq_km_area='" + sq_km_area + '\'' +
                ", life_expectancy='" + life_expectancy + '\'' +
                ", elevation_in_meters='" + elevation_in_meters + '\'' +
                ", continent='" + continent + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", location='" + location + '\'' +
                ", iso='" + iso + '\'' +
                ", capital_city='" + capital_city + '\'' +
                ", confirmed='" + confirmed + '\'' +
                ", recovered='" + recovered + '\'' +
                ", deaths='" + deaths + '\'' +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country=country;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population=population;
    }

    public Integer getSq_km_area() {
        return sq_km_area;
    }

    public void setSq_km_area(Integer sq_km_area) {
        this.sq_km_area=sq_km_area;
    }

    public String getLife_expectancy() {
        return life_expectancy;
    }

    public void setLife_expectancy(String life_expectancy) {
        this.life_expectancy=life_expectancy;
    }

    public Object getElevation_in_meters() {
        return elevation_in_meters;
    }

    public void setElevation_in_meters(Object elevation_in_meters) {
        this.elevation_in_meters=elevation_in_meters;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent=continent;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation=abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location=location;
    }



    public String getCapital_city() {
        return capital_city;
    }

    public void setCapital_city(String capital_city) {
        this.capital_city=capital_city;
    }


    public void setRecovered(Object value) {
    }
}
