package com.neo.neo;
import ch.qos.logback.core.subst.Node;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Id;


import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Piece extends BasicClass  {
    @Property(name = "pieceId")
    private Integer pieceId;          //pieceId

    @Property(name = "type")
    private String type;            //juniper||cisco||huaWei
    /*
        @Relationship(type = "IN", direction = Relationship.OUTGOING)
        public Set<NodeObject> ins;
    */

    public Piece(){}

    public Piece(String s,Integer i){
        name=s;
        pieceId=i;
    }

    public void setPieceId(Integer pieceId){
        this.pieceId=pieceId;
    }

    public Integer getPieceId(){
        return pieceId;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

}
