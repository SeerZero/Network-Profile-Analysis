package com.neo.neo;

import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

public class DataValue extends BasicClass{
    @Property(name = "value")
    private String value;

    @Relationship(type = "pair", direction = Relationship.OUTGOING)
    public Set<BasicClass> contains;
    public void contain(BasicClass bc) {
        if (contains == null) {
            contains = new HashSet<>();
        }
        contains.add(bc);
    }
    public DataValue(String name){
        this.name=name;
    }
}
