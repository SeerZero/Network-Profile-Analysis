package com.neo.neo;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class DataReadFile {
    Integer c=0,p=0,s=0;
    int z=0;
    BasicRepository basicRepository;
    public DataReadFile() { }

    /**
     * 读取某个文件夹下的所有文件
     */
    class RunableTest implements Runnable {
        File f;
        public RunableTest(File f) {
            this.f=f;
            new Thread(this).start();
        }
        public void run() {
            //public void add(File f, BasicRepository basicRepository){
            Stack<BasicClass> stk = new Stack<BasicClass>();
            DataCFile cf = new DataCFile();                       //文件入栈
            cf.setName(f.getName());
            cf.setFileId(++c);
            BasicClass ccf=basicRepository.findCfile();
            ccf.instance(cf);
            stk.push(cf);
            String sinterface =new String("interfaces");
            try {
                Scanner sc = new Scanner(f);
                while (sc.hasNext()) {
                    String str = sc.nextLine();
                    str = str.trim();
                    for(int i=str.length()-2;i>=0;i--){
                        if(str.charAt(i)=='#'&&str.charAt(i+1)=='#')str=str.substring(0,i);
                        else    if(str.charAt(i)=='/'&&str.charAt(i+1)=='*')str=str.substring(0,i);
                    }
                    str=str.trim();
                    if (str.isEmpty()) continue;         //跳空行
                    else if (str.charAt(str.length() - 1) == '{') { //小节入栈
                        str = str.substring(0, str.length() - 1);
                        str=str.trim();
                        DataPiece pie = new DataPiece(str, ++p);
                        pie.setFile(f.getName());
                        if(stk.peek().getName().equals(sinterface)) {
                            pie.setKey("interfaces");
                        }else{
                            int last = 0;
                            for (int i = 0; i < str.length(); i++) {
                                if (str.charAt(i) == ' ') {
                                    pie.setKey(str.substring(last, i));
                                    last = i + 1;
                                    pie.setValue(str.substring(last, str.length() - 0));
                                    break;
                                }
                            }
                            if (last == 0) pie.setKey(pie.getName());
                        }

                        stk.peek().contain(pie);
                        BasicClass bs=basicRepository.findPiece(pie.getKey());
                        if(bs!=null){
                            bs.instance(pie);
                            basicRepository.save(bs);
                            bs.setNum();
                        }
                        stk.push(pie);
                    } else if (str.charAt(0) == '}') {            //弹出
                        basicRepository.save(stk.peek());
                        stk.pop();
                    } else if (str.charAt(str.length() - 1) == ';') {
                        str = str.substring(0, str.length() - 1);
                        str = str.trim();
                        DataSentence sen = new DataSentence(str, ++s);
                        sen.setFile(f.getName());
                        str.trim();
                        int last=0;
                        for(int i=0;i<str.length();i++){
                            if (str.charAt(i) == ' ') {
                                sen.setKey(str.substring(last, i));
                                last = i + 1;
                                sen.setValue(str.substring(last, str.length() - 0));
                                break;
                            }
                        }
                        if(last==0)sen.setKey(sen.getName());
                        basicRepository.save(sen);
                        stk.peek().contain(sen);
                        BasicClass bs=basicRepository.findSentence(sen.getKey());
                        if(bs!=null){
                            bs.instance(sen);
                            basicRepository.save(bs);
                            bs.setNum();
                        }
                    }
                }
                basicRepository.save(cf);
                stk.pop();
            } catch (Exception e) { }
            System.out.println(++z+" "+f.getName()+" completed");
        }
    }

    public void readfile(String filepath,BasicRepository basicRepository) throws FileNotFoundException, IOException {
        this.basicRepository=basicRepository;
        try {
            File file = new File(filepath);
            String[] filelist = file.list();
            System.out.println("共 "+filelist.length+" 个文件");
            for (int i = 0; i < filelist.length; i++) {
                File rf = new File(filepath + "\\" + filelist[i]);
                new RunableTest(rf);
            }

        } catch (Exception e) {
            System.out.println("文件夹未找到");
        }
    }
}