package com.neo.neo;
import ch.qos.logback.core.subst.Node;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Id;


import java.util.Set;

@NodeEntity
public class Sentence extends BasicClass  {
    @Property(name = "sentenceId")
    private Integer sentenceId;      //sentenceId

    @Property(name = "type")
    private String type;            //juniper||cisco||huaWei

    public Sentence(){}
    public Sentence(String s,Integer i){
        name=s;
        sentenceId=i;
    }

    public void setSentenceId(Integer sentenceId){
        this.sentenceId=sentenceId;
    }

    public Integer getSentenceId(){
        return sentenceId;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

}
