package com.tf.base.jni;

import com.alibaba.fastjson.JSONObject;
import com.sun.jna.Native;
import com.tf.base.go.util.InparamterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JnaUtil {
    private static Logger log = LoggerFactory.getLogger(JnaUtil.class);
    //加载dll成功
    public static   final  int OK=1;
    //加载dll错误
    public static   final  int ERROR=0;
    // dll路径
    private  String dllpath ;
    /**
     *
     * @param dllpath  dll路径
     */
    public  JnaUtil(String dllpath){
        this.dllpath=dllpath;
    }


    /**
     * 调用调用模型dll函数方法
     * @param input
     * @return
     */
    public static JnaResult callDllFun(String dllpath,String input){
        String[] filePath= JnaFileUtil.getDllInputFilaPath();
        JnaFileUtil.writeDllInputParamter(input,filePath[0]);
        JnaUtil jnaUtil = new JnaUtil(dllpath);
        JnaResult result = new JnaResult();
        //取得dll的instance
        JnaCallFunction jnaInstance = null;
        try {
            jnaInstance = (JnaCallFunction) Native.loadLibrary(dllpath, JnaCallFunction.class);

        } catch (Throwable e) {
            e.printStackTrace();
            result.setResultcode(JnaResult.JNA_ERROR);
            result.setMsg("dll文件加载错误，请检查dll的编译问题！");
            return  result ;
        }

        try {
//            log.info(input);
//            log.info("输出input开始......");
//            log.info("输出input结束......");
            ChangeCharset changeCharset = new ChangeCharset();
//           String  reFun= jnaInstance.modelfunc(changeCharset.toGBK(filePath[0]),changeCharset.toGBK(filePath[1]));

//           log.info("输出reFun开始......");
//           log.info(reFun);
//           log.info("输出reFun结束......");

            //做个测试
            String reFun = InparamterUtil.readString("D:\\upload\\file\\dllfile\\20200308\\1583651944309_out.txt");
            result.setResultcode(JnaResult.JNA_SUCESS);
            result.setMsg(reFun);

//           JSONObject resultJson = JSONObject.parseObject(reFun);
//            if (resultJson.containsKey("DLL_ERROR")){
//                result.setResultcode(JnaResult.JNA_ERROR);
//                result.setMsg(resultJson.getString("DLL_ERROR"));
//            }else{
//             //  改变得到的方式，改成文件的格式
//                // reFun = changeCharset.toGBK(reFun) ;
//                reFun = InparamterUtil.readString(filePath[1]);
//                result.setResultcode(JnaResult.JNA_SUCESS);
//                result.setMsg(reFun);
//            }
        } catch (Throwable e) {
            e.printStackTrace();
            //输出错误堆栈
            log.info("函数调用错误，调用参数错误，错误堆栈如下：");
            log.error(e.getMessage(),e);
            result.setResultcode(JnaResult.JNA_ERROR);
            result.setMsg("函数调用错误，调用参数："+input);
            return  result ;
        }
        return  result;
    }

    public static void main(String[] args) {
        String dllPath="C:\\Users\\peach\\source\\repos\\ConsoleApplication1\\x64\\Debug\\testbigdata10.dll";
        String inAndOutPath="d:\\1.txt";
        JnaUtil jnaUtil = new JnaUtil(dllPath);
        JnaResult result = new JnaResult();
        //取得dll的instance
        JnaCallFunction jnaInstance = null;
        for (int i=0;i<100;i++){
            try {
                jnaInstance = (JnaCallFunction) Native.loadLibrary(dllPath, JnaCallFunction.class);
                String  reFun= jnaInstance.modelfunc("d:\\1.txt","d:\\1_out.txt");
                System.out.println(reFun);
                Thread.sleep(10000);
            } catch (Throwable e) {
                e.printStackTrace();
                result.setResultcode(JnaResult.JNA_ERROR);
                result.setMsg("dll文件加载错误，请检查dll的编译问题！");
            }
            if(i%5==0){
                System.out.println(i);
            }
        }


    }

}
