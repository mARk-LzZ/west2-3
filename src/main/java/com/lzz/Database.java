package com.lzz;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class Database {
    static Country country = new Country();
    static Province province = new Province();

    static OkHttpApi okHttpApi=new OkHttpApi();
    static String run;

    static {
        try {
            run=okHttpApi.run("https://covid-api.mmediagroup.fr/v1/cases");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static JSONObject jsonObject=JSONObject.parseObject(run);

    public static void Insert(String countrys) {

        JSONObject jsonObject1=jsonObject.getJSONObject(countrys);
        Set<String> set=jsonObject1.keySet();
        Object[] list=set.toArray();
        JSONObject countryForProv=(JSONObject) jsonObject.getJSONObject(countrys).get(list[0]);
        for (int i=0; i < list.length; i++) {
            String key=(String) list[i];
            if (i == 0) {
                JSONObject jsonObject5=(JSONObject) jsonObject.getJSONObject(countrys).get(key);
                InputStream inputStream=null;
                try {
                    inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession sqlSession=sqlSessionFactory.openSession();
                country.setCountry((String) jsonObject5.get("country"));
                country.setPopulation((Integer) jsonObject5.get("population"));
                country.setSq_km_area((Integer) jsonObject5.get("sq_km_area"));
                country.setLife_expectancy((String) jsonObject5.get("life_expectancy"));
                country.setElevation_in_meters(jsonObject5.get("elevation_in_meters"));
                country.setContinent((String) jsonObject5.get("continent"));
                country.setAbbreviation((String) jsonObject5.get("abbreviation"));
                country.setLocation((String) jsonObject5.get("location"));
                country.setIso((Integer) jsonObject5.get("iso"));
                country.setCapital_city((String) jsonObject5.get("capital_city"));
                country.setConfirmed((Integer) jsonObject5.get("confirmed"));
                country.setRecovered((Integer) jsonObject5.get("recovered"));
                country.setDeaths((Integer) jsonObject5.get("deaths"));

                sqlSession.insert("UserMapper.insertCoun", country);

                sqlSession.commit();
                sqlSession.close();
            } else {
                JSONObject jsonObject5=(JSONObject) jsonObject.getJSONObject(countrys).get(key);
                InputStream inputStream=null;
                try {
                    inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession sqlSession=sqlSessionFactory.openSession();
                province.setCountry((String) countryForProv.get("country"));
                province.setProvince(key);
                province.setLat((String) jsonObject5.get("lat"));
                province.setLon((String) jsonObject5.get("long"));
                province.setConfirmed((Integer) jsonObject5.get("confirmed"));
                province.setRecovered((Integer) jsonObject5.get("recovered"));
                province.setDeaths((Integer) jsonObject5.get("deaths"));
                province.setUpdated((String) jsonObject5.get("updated"));
                sqlSession.insert("UserMapper.insertProv", province);

                sqlSession.commit();
                sqlSession.close();
            }


        }
    }

    public static void selectProv(String province1){

        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        province.setProvince(province1);
        List<Province> list=sqlSession.selectList("UserMapper.selectProv", province);
        for (Province prov: list) {
            System.out.println(prov);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    public static void selectCoun(String country1){
        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        country.setCountry(country1);
        List<Country> list=sqlSession.selectList("UserMapper.selectCoun", country);
        for (Country coun: list) {
            System.out.println(coun);
        }
        sqlSession.commit();
        sqlSession.close();



    }




    public static void updateProv( String prov , String key , Object value){
        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        province.setProvince(prov);
        switch (key){
            case "lat" : province.setLat((String) value); break;
            case "lon" : province.setLon((String) value); break;
            case "confirmed" : province.setConfirmed((Integer) value); break;
            case "recovered" : province.setRecovered((Integer) value); break;
            case "deaths" : province.setDeaths((Integer) value); break;
            case "updated" : province.setUpdated(String.valueOf(value)); break;
        }
        int count=sqlSession.update("UserMapper.updateProv", province);
        System.out.println( count + " data from province changes");
        sqlSession.commit();
        sqlSession.close();
    }
    public static void updateCoun( String coun , String key , Object value){
        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        country.setCountry(coun);
        switch (key){
            case "population" : country.setPopulation((Integer) value); break;
            case "sq_km_area" : country.setSq_km_area((Integer) value); break;
            case "life_expectancy" : country.setLife_expectancy( String.valueOf(value)); break;
            case "elevation_in_meters" : country.setElevation_in_meters(value); break;
            case "iso" : country.setIso((Integer) value); break;
            case "confirmed" : country.setConfirmed((Integer) value); break;
            case "recovered" : country.setRecovered(value); break;
            case "deaths" : country.setDeaths((Integer) value); break;
        }
        int count=sqlSession.update("UserMapper.updateCoun", country);
        System.out.println( count + " data from country changes");
        sqlSession.commit();
        sqlSession.close();
    }
}
