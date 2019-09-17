package com.neo.neo;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class ReadFile {
    Integer c=0,p=0,s=0;
    int z=0;
    BasicRepository basicRepository;
    public ReadFile() { }
    void testt(File f){
        Stack<BasicClass> stk = new Stack<BasicClass>();
        CFile cf = new CFile();                       //文件入栈
        cf.setName("test");
        cf.setFileId(++c);
        stk.push(cf);
        String sinterface =new String("interfaces");
        int ccc=0;
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {
                String str = sc.nextLine();
                str = str.trim();
                //if (str.length() >= 2 && str.charAt(0) == '#' && str.charAt(1) == '#') continue;//跳注释
                for(int i=str.length()-2;i>=0;i--){
                    if(str.charAt(i)=='#'&&str.charAt(i+1)=='#')str=str.substring(0,i);
                    else    if(str.charAt(i)=='/'&&str.charAt(i+1)=='*')str=str.substring(0,i);
                }
                str=str.trim();
                if (str.isEmpty()) continue;         //跳空行
                else if (str.charAt(str.length() - 1) == '{') { //小节入栈
                    if(ccc>=1)ccc++;
                    if(ccc==2)continue;
                    str = str.substring(0, str.length() - 1);
                    str=str.trim();
                    for(int i=0;i<str.length();i++) {
                        if (str.charAt(i) == ' ') {
                            str = str.substring(0, i);
                        }
                    }
                    Piece pie = new Piece(str, ++p);
                    stk.peek().contain(pie);
                    stk.push(pie);
                    if(stk.peek().name.equals(sinterface))ccc=1;
                } else if (str.charAt(0) == '}') {            //弹出
                    if(ccc==2){
                        ccc--;
                        continue;
                    }
                    if(ccc>=1)ccc--;
                    basicRepository.save(stk.peek());
                    stk.pop();
                } else if (str.charAt(str.length() - 1) == ';') {                                  //单句去参
                    str = str.substring(0, str.length() - 1);//去掉;
                    for(int i=0;i<str.length();i++){
                        if(str.charAt(i)==' '){
                            str=str.substring(0,i);
                        }
                    }
                    int x = str.length();
                    boolean b = true;
                    while (x >= 1 && b) {
                        --x;
                        char c = str.charAt(x);
                        if (c == ' ') str = str.substring(0, x);
                        else if ((c >= 'G' && c <= 'Z') || (c >= 'g' && c <= 'z')) b = false;
                    }
                    if (b) continue;//去参之后变空行
                    str = str.trim();
                    Sentence sen = new Sentence(str, ++s);
                    basicRepository.save(sen);
                    stk.peek().contain(sen);
                }
            }
            basicRepository.save(cf);
        } catch (Exception e) { }
        System.out.println(++z+" "+f.getName()+" completed");
    }

    public void readfile(String filepath,BasicRepository basicRepository) throws FileNotFoundException, IOException {
        this.basicRepository=basicRepository;
        try {
            File file = new File(filepath);
            String[] filelist = file.list();
            System.out.println("共 "+filelist.length+" 个文件");
            for (int i = 0; i < filelist.length; i++) {
                File rf = new File(filepath + "\\" + filelist[i]);
                testt(rf);
            }

        } catch (Exception e) {
            System.out.println("文件夹未找到");
        }
    }
}