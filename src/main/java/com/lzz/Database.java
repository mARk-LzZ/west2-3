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

//数据库操作
public class Database {
    static Country country=new Country();
    static Province province=new Province();
    static OkHttpApi okHttpApi=new OkHttpApi();
    static String run;

    static {
        try {
            run=okHttpApi.run("https://covid-api.mmediagroup.fr/v1/cases"); //获取前端数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static JSONObject jsonObject=JSONObject.parseObject(run); //将前端数据储存在一个JSONObject对象里

    public static void Insert(String countrys) {
        JSONObject jsonObject1=jsonObject.getJSONObject(countrys); //获取键为country的value 其中的value均为子键
        Set<String> set=jsonObject1.keySet();//将获取到的子键存入一个set集合中
        Object[] keys=set.toArray(); //因set集合无法指定位置 将该集合转为一个数组
        JSONObject countryForProv=(JSONObject) jsonObject.getJSONObject(countrys).get(keys[0]); // 此JSONObject对象为国家的键 即All
        for (int i=0; i < keys.length; i++) { // 循环遍历取出数组中的键
            String key=(String) keys[i];
            JSONObject jsonObject5=(JSONObject) jsonObject.getJSONObject(countrys).get(key);
            InputStream inputStream=null; //mybatis数据库操作
            if (i == 0) { //获取“All” 对应的所有value
                try {
                    inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession sqlSession=sqlSessionFactory.openSession();
                //将获取到的数据封装在一个Country对象中
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

                sqlSession.insert("UserMapper.insertCoun", country); //执行插入操作

                sqlSession.commit();//提交数据
                sqlSession.close(); //回收资源
            } else { //获取除了 “All” 以外所有地区省市的value
                try {
                    inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession sqlSession=sqlSessionFactory.openSession();
                //将获取到的所有数据封装在一个province对象里
                province.setCountry((String) countryForProv.get("country"));
                province.setProvince(key);
                province.setLat((String) jsonObject5.get("lat"));
                province.setLon((String) jsonObject5.get("long"));
                province.setConfirmed((Integer) jsonObject5.get("confirmed"));
                province.setRecovered((Integer) jsonObject5.get("recovered"));
                province.setDeaths((Integer) jsonObject5.get("deaths"));
                province.setUpdated((String) jsonObject5.get("updated"));
                sqlSession.insert("UserMapper.insertProv", province);//执行插入操作

                sqlSession.commit();
                sqlSession.close();
            }


        }
    }

    public static void selectProv(String province1) { //查询省市 的数据

        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        province.setProvince(province1); //确定是哪个省市的数据
        List<Province> list=sqlSession.selectList("UserMapper.selectProv", province);
        for (Province prov : list) { //循环遍历键为province中的value
            System.out.println(prov);
        }

        sqlSession.commit();
        sqlSession.close();
    }

    public static void selectCoun(String country1) { //同selectProv
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
        for (Country coun : list) {
            System.out.println(coun);
        }
        sqlSession.commit();
        sqlSession.close();


    }


    public static void updateProv(String prov, String key, Object value) { //更新省市数据
        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        province.setProvince(prov); //确定要更新的数据条
        switch (key) { //确定要更新的字段
            case "lat":
                province.setLat((String) value);
                break;
            case "lon":
                province.setLon((String) value);
                break;
            case "confirmed":
                province.setConfirmed((Integer) value);
                break;
            case "recovered":
                province.setRecovered((Integer) value);
                break;
            case "deaths":
                province.setDeaths((Integer) value);
                break;
            case "updated":
                province.setUpdated(String.valueOf(value));
                break;
        }
        int count=sqlSession.update("UserMapper.updateProv", province); //执行更新操作 成功则返回更新成功的数据条数
        System.out.println(count + " data from province changes");
        sqlSession.commit();
        sqlSession.close();
    }

    public static void updateCoun(String coun, String key, Object value) { // 同ypdateProv
        InputStream inputStream=null;
        try {
            inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        country.setCountry(coun);
        switch (key) {
            case "population":
                country.setPopulation((Integer) value);
                break;
            case "sq_km_area":
                country.setSq_km_area((Integer) value);
                break;
            case "life_expectancy":
                country.setLife_expectancy(String.valueOf(value));
                break;
            case "elevation_in_meters":
                country.setElevation_in_meters(value);
                break;
            case "iso":
                country.setIso((Integer) value);
                break;
            case "confirmed":
                country.setConfirmed((Integer) value);
                break;
            case "recovered":
                country.setRecovered(value);
                break;
            case "deaths":
                country.setDeaths((Integer) value);
                break;
        }
        int count=sqlSession.update("UserMapper.updateCoun", country);
        System.out.println(count + " data from country changes");
        sqlSession.commit();
        sqlSession.close();
    }
}
