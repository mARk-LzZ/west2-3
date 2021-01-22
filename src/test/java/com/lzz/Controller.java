package com.lzz;



public class Controller {

//choose which command you want to do
    public static void main(String[] args) {

//        insert of data
        Database.Insert("China");
        Database.Insert("US");
        Database.Insert("United Kingdom");
        Database.Insert("Japan");


//        select data
        Database.selectCoun("China");
        Database.selectProv("Gansu");


//        update data
        Database.updateProv("Gansu","confirmed",239);
        Database.updateCoun("China","population" ,1);

    }


}
