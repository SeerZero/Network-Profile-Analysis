package com.neo.neo;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class DataPiece extends BasicClass  {
    @Property(name = "pieceId")
    private Integer pieceId;          //pieceId

    @Property(name = "type")
    private String type;            //juniper||cisco||huaWei



    @Property(name="fileBelong")
    private String cfile;               //所属的文件，便于建立文件内连接
    /*
        @Relationship(type = "IN", direction = Relationship.OUTGOING)
        public Set<NodeObject> ins;
    */

    public DataPiece(){}

    public DataPiece(String s,Integer i){
        name=s;
        pieceId=i;
    }


    public void setPieceId(Integer pieceId){
        this.pieceId=pieceId;
    }

    public Integer getPieceId(){
        return pieceId;
    }

    public void setType(String type){ this.type=type; }

    public String getType(){
        return type;
    }



}
