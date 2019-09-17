package com.neo.neo;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

public abstract class BasicClass {
    @Id @GeneratedValue
    private Long nodeId;

    @Property(name = "name")
    protected String name;

    @Property(name = "file")
    protected String ffile;

    @Property(name = "key")
    private String key;
    @Property(name="value")
    private String value;           //值，建立文件链接
    @Property(name = "num")
    private int num;

    public void setKey(String key){this.key=key;}

    public void setValue(String value){this.value=value;}

    public void setFile(String ffile){this.ffile=ffile;}

    public void setNum(){num++;}

    public int getNum(){return num;}

    public String getFile(){return ffile;}

    public String getKey(){return key;}

    public String getValue(){return value;}

    @Relationship(type = "CONTAIN", direction = Relationship.OUTGOING)
    public Set<BasicClass> contains;
    public void contain(BasicClass bc) {
        if (contains == null) {
            contains = new HashSet<>();
        }
        contains.add(bc);
    }
    @Relationship(type = "pair", direction = Relationship.UNDIRECTED)
    public Set<BasicClass> pair;
    public void pair(BasicClass bc) {
        if (pair == null) {
            pair = new HashSet<>();
        }
        pair.add(bc);
    }

    @Relationship(type = "Instance_Of", direction = Relationship.OUTGOING)
    public Set<BasicClass> instances;
    public void instance(BasicClass bc) {
        if (instances == null) {
            instances = new HashSet<>();
        }
        instances.add(bc);
        num++;
    }

    public Long getId() {
        return nodeId;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }


    public int getSize(){return instances.size();}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (this.nodeId == null) {
            // For newly created entity, id will be null
            return false;
        }

        BasicClass entity = (BasicClass) obj;
        return this.nodeId.equals(entity.nodeId);
    }
    @Override
    public int hashCode() {
        return nodeId == null ? super.hashCode() : nodeId.hashCode();
    }
}