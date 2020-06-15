/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tf.base.project.ahp;

public class AhpLevel {

    float[][] matrix = null;//待求矩阵
    static int dim = 0;//维数
    float[] ap = {0.48272198f, 0.2719554f, 0.15719356f, 0.08812906f};
    float[] w1 = {0.29742387f, 0.33403194f, 0.53929216f, 0.1373987f};
    float[][] am;
    float[] W;
    float[] bm;
    float lamuda = 0.0f;// 入
    float CI = 0.0f;
    float RI = 0.0f;
    float CR = 0.0f;
    float w = 0.0f;

    public AhpLevel() {
    }

    public AhpLevel(float[][] matrix) {
        this.matrix = matrix;
        this.dim = this.matrix.length;
        init();
        this.run();
        this.run2();
        this.run3();
//        this.paint();
    }

    private void init() {
        setRI();
        am = new float[dim][dim];
        W = new float[dim];
        bm = new float[dim];
    }

    public void run() {//第一步 列向量归一化

        float a1 = 0.0f;
        for (int i = 0; i < dim; i++) {
            a1 = 0.0f;
            for (int j = 0; j < dim; j++) { //每一列向量的和

                a1 = matrix[j][i] + a1;
            }
            for (int j = 0; j < dim; j++) { //除以列和,完成第一步列向量归一化

                am[j][i] = matrix[j][i] / a1;
            }
        }
        for (int i = 0; i < dim; i++) {  //按行求和

            for (int j = 0; j < dim; j++) {
                W[i] = W[i] + am[i][j];
            }
        }
    }

    public void run2() {  //第二步  对W 再做一次归一化

        float a1 = 0.0f;
        for (int i = 0; i < dim; i++) { //列向求和

            a1 = W[i] + a1;
        }
        for (int i = 0; i < dim; i++) {//完成归一化

            W[i] = W[i] / a1;
        }
    }

    public void run3() {
        for (int i = 0; i < dim; i++) { //求 A * w

            for (int j = 0; j < dim; j++) {
                bm[i] = bm[i] + W[j] * matrix[i][j];
            }
        }
        for (int i = 0; i < dim; i++) {   //求 入

            lamuda = lamuda + bm[i] / W[i];
        }
        lamuda = lamuda / dim;
        CI = (lamuda - dim) / (dim - 1); //求 CI

        CR = CI / RI;//求 CR

    }

    public void run4() {   //求  组合权重
        for (int i = 0; i < 4; i++) {
            w = w + ap[i] * w1[i];
        }
        System.out.println("w=" + w);
    }

    public void paint() {
        System.out.println("Am=");
        for (int i = 0; i < dim; i++) {
            System.out.print("[ ");
            for (int j = 0; j < dim; j++) {
                System.out.print(am[i][j] + " ");
            }
            System.out.println("]");
        }

        System.out.println("B=");
        System.out.print("[ ");
        for (int i = 0; i < dim; i++) {
            System.out.print(W[i] + " ");
        }
        System.out.println("]");

        System.out.println("入=" + lamuda);
        System.out.println("CI=" + CI);
        System.out.println("CR=" + CR);
    }

    private void setRI() {
        switch(dim){
            case 1:this.RI=0;break;
            case 2:this.RI=0;break;
            case 3:this.RI=0.58f;break;
            case 4:this.RI=0.90f;break;
            case 5:this.RI=1.12f;break;
            case 6:this.RI=1.24f;break;
            case 7:this.RI=1.32f;break;
            case 8:this.RI=1.41f;break;
            case 9:this.RI=1.45f;break;
            case 10:this.RI=1.49f;break;
            case 11:this.RI=1.51f;break;
            default:this.RI=0;
        }
    }
}
