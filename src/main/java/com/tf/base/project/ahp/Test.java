/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tf.base.project.ahp;

public class Test {
    public static void main(String[] args){
        float[][] s;
        String S="A=[1,2,3,5;0.5,1,2,3;0.33,0.5,1,2;0.2,0.33,0.5,1]";
        float[][] b1;
        String B1="B1=[1,2,0.5;0.5,1,0.33;2,3,1]";
        float[][] b2;
        String B2="B2=[1,0.5,3;2,1,3;0.33,0.33,1]";
        float[][] b3;
        String B3="B3=[1,3,2;0.33,1,0.5;0.5,2,1]";
        float[][] b4;
        String B4="B4=[1,0.5,0.25;2,1,0.33;4,3,1]";
        
        MatixcBean mb=new MatixcBean(S);
        s=mb.getMatixc_float();
        AhpLevel le=new AhpLevel(s);
        
//        mb=new MatixcBean(B1);
//        b1=mb.getMatixc_float();
//        le=new level(b1);
//
//        mb=new MatixcBean(B2);
//        b2=mb.getMatixc_float();
//        le=new level(b2);
//
//        mb=new MatixcBean(B3);
//        b3=mb.getMatixc_float();
//        le=new level(b3);
//
//        mb=new MatixcBean(B4);
//        b4=mb.getMatixc_float();
//        le=new level(b4);
    }
}
