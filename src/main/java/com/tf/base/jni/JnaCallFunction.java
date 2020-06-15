package com.tf.base.jni;

import com.sun.jna.win32.StdCallLibrary;

public interface JnaCallFunction extends StdCallLibrary {
    /**
     * 模型函数调用接口
     * @param inputFilePath ,outputFilePath
     * @return
     */
    public abstract String modelfunc(String inputFilePath,String outputFilePath);
}
