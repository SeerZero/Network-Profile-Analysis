package com.neo.neo;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.System.exit;

@SpringBootApplication
@EnableNeo4jRepositories
public class NeoApplication {
    private final static Logger log = LoggerFactory.getLogger(NeoApplication.class);
    public static void main(String[] args) { SpringApplication.run(NeoApplication.class, args); }

    //************模板清空***************
    @Bean
    CommandLineRunner clearCFile(CFileRepository cf) { return args ->cf.deleteAll(); }

    @Bean
    CommandLineRunner clearPiece(PieceRepository pi) { return args -> pi.deleteAll();}

    @Bean
    CommandLineRunner clearSentence(SentenceRepository se) {return args -> se.deleteAll();}

    //************数据层清空*************
    @Bean
    CommandLineRunner demo221(DataCFileRepository cf) { return args ->cf.deleteAll(); }

    @Bean
    CommandLineRunner demo222(DataPieceRepository pi) { return args -> pi.deleteAll(); }

    @Bean
    CommandLineRunner demo223(DataSentenceRepository se) { return args -> se.deleteAll(); }
    //全都换成basic其实可以减少操作


    //**************建立模板***********
    @Bean
    CommandLineRunner filesInitTemplate(BasicRepository basicRepository) {
         return args ->{
            ReadFile rf=new ReadFile();
            rf.readfile("data",basicRepository);
            System.out.println("successful");
         };
    }

    @Bean
    CommandLineRunner unique(BasicRepository basicRepository){
        return args ->{
            basicRepository.uniqueCFile();
            basicRepository.uniquePiece();
            basicRepository.uniqueSentence();
            basicRepository.uniqueContain();
            System.out.println("unique successful");
        };
    }

//step 2，instance_of
    @Bean
    CommandLineRunner instances(BasicRepository basicRepository) {
        return args ->{
            DataReadFile rf=new DataReadFile();
            rf.readfile("data",basicRepository);
            System.out.println("successful");
        };
    }

    @Bean
    CommandLineRunner addinstance(BasicRepository basicRepository) {
        return args ->{
            DataReadFile rf=new DataReadFile();
            rf.readfile("add",basicRepository);
            System.out.println("successful");
        };
    }


//step 6, pair
    @Bean
    CommandLineRunner Pair(BasicRepository dv){
        return args->{
            List<DataCFile> ll=dv.findDataCfile();      //置0
            for(int i=0;i<ll.size();i++){
                ll.get(i).setZero();
                dv.save(ll.get(i));
            }
            List<DataCFile> list=dv.findFileChecked("0");       //查找没找过的
            Vector<BasicClass> v=new Vector<>();
            for(int i=0;i<list.size();i++){                           //遍历所有子节点建立v
                System.out.println(ll.get(i).getName());
                System.out.println(i);
                dv.deleteAllVPK();
                v.clear();
                DataCFile dc=list.get(i);
                v.addElement(dc);
                int start=0;
                while(start<v.size()){
                    List<BasicClass> lt2=dv.findSon(v.elementAt(start).getId());
                    for(int j=0;j<lt2.size();j++){
                        v.addElement(lt2.get(j));
                    }
                    ++start;
                }
                int cnt=0;
                for(int j=0;j<v.size();j++){                //对所有key-value节点建立key-value库
                    String value=v.elementAt(j).getValue();
                    String key=v.elementAt(j).getKey();
                    if(value==null||value.isEmpty())continue;
                    if(key==null||value.isEmpty())continue;
                    DataValue d=dv.findValue(value);
                    if(d==null){
                        d=new DataValue(value);
                    }
                    DataKey k=dv.findKey(value,key);
                    if(k==null){
                        k=new DataKey(key);
                        d.pair(k);
                    }
                    k.addTime();
                    dv.save(d);
                    dv.save(k);
                }
                List<DataValue> lt3=dv.findAllValue();
                String tt="v6filter";
                for(int j=0;j<lt3.size();j++){              //查找所有的value节点，对每个节点保存其中的key vector
                    DataValue r=lt3.get(j);
                    List<DataKey> lt4=dv.findAllKey(r.getName());
                    Vector<BasicClass> v2=new Vector<>();
                    for(int k=0;k<lt4.size();k++){
                        if(lt4.get(k).getTime()>5){
                            BasicClass s=dv.findSentence(lt4.get(k).getName());
                            if(s==null)continue;
                            v2.addElement(s);
                        }
                    }
                    if(v2.size()<2)continue;
                    for(int k=0;k<v2.size();k++){
                        for(int l=k+1;l<v2.size();l++){
                            if(dv.findPair(v2.elementAt(k).getName(),v2.elementAt(l).getName())!=null)continue;
                            v2.elementAt(k).pair(v2.elementAt(l));
                            dv.save(v2.elementAt(k));
                            dv.save(v2.elementAt(l));
                        }
                    }
                }
                dv.deleteAllVPK();
            }
            System.out.println("complete");
        };
    }

}
