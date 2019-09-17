package com.neo.neo;

import org.neo4j.ogm.annotation.Property;

public class DataKey extends BasicClass{
    @Property(name="time")
    private Integer time;
    public DataKey(String n){
        name=n;
        time=0;
    }
    public void addTime(){
        time++;
    }
    public Integer getTime(){
        return time;
    }
}
