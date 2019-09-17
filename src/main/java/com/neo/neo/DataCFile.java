package com.neo.neo;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;


@NodeEntity
public class DataCFile extends BasicClass  {

    @Property(name = "fileId")
    private Integer fileId;          //fileId

    @Property(name = "type")
    private String type;            //juniper||cisco||huaWei

    @Property(name="checked")
    private String checked;

    public String getChecked(){
        return checked;
    }

    public DataCFile(){}
    public DataCFile(String s,Integer i){
        name=s;
        fileId=i;
        checked="0";
    }
    public void setZero(){
        checked="0";
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
