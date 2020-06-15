package com.tf.base.jni.jnidll;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class TestDll1Service {
    public interface TestDll1 extends Library {

        /**

         * 当前路径是在项目下，而不是bin输出目录下。

         */
        TestDll1 INSTANCE = (TestDll1) Native.loadLibrary("C:\\Users\\Administrator\\source\\repos\\firstdll\\x64\\Debug\\firstdll", TestDll1.class);
         int testshow(int j);
    }

    /**

     *

     */

    public TestDll1Service() {
        // TODO Auto-generated constructor stub
    }



    /**

     * @param args

     */

    public static void main(String[] args) {

        // TODO Auto-generated method stub



        int i =TestDll1.INSTANCE.testshow(5);

        System.out.println("HHEEH我我们无法万恶"+i);

    }
}
