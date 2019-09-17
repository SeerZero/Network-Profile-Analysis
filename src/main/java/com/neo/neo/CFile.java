package com.neo.neo;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Id;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity
public class CFile extends BasicClass  {

    @Property(name = "fileId")
    private Integer fileId;          //fileId

    @Property(name = "type")
    private String type;            //juniper||cisco||huaWei

    public CFile(){}
    public CFile(String s,Integer i){
        name=s;
        fileId=i;
    }

    public Integer getFileId(){
        return fileId;
    }

    public void setFileId(Integer fileId){
        this.fileId=fileId;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }
}
