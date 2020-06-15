package com.tf.base.platform.service;

import com.tf.base.common.domain.ModelInfo;
import com.tf.base.common.domain.ModelParmeter;
import com.tf.base.common.domain.StructParmeter;
import com.tf.base.jni.model.MakeFileUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wanquan on 2018/3/29.
 *
 */
public class SolutionUtil {

    public SolutionUtil() {

    }

    public static void main(String[] args) {
    //1
        SolutionUtil solutionUtil= new SolutionUtil();
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setId(3018);
        modelInfo.setModelName("wangmw_test1");
        modelInfo.setModelVersion("1.0");
        modelInfo.setModelContent("1");
        modelInfo.setModelCategoryid(109);

        List<ModelParmeter> list = new ArrayList<>();
        ModelParmeter modelParmeter1= new ModelParmeter();
        modelParmeter1.setId(3034);
        modelParmeter1.setModelId(3018);
        modelParmeter1.setParmeterName("p1");
        modelParmeter1.setParmeterUnit("not_array");
        modelParmeter1.setParmeterType("int");
        modelParmeter1.setInoutType(0);
        modelParmeter1.setParentId(0);
        list.add(modelParmeter1);

        ModelParmeter modelParmeter2= new ModelParmeter();
        modelParmeter2.setId(3035);
        modelParmeter2.setModelId(3018);
        modelParmeter2.setParmeterName("p2");
        modelParmeter2.setParmeterUnit("not_array");
        modelParmeter2.setParmeterType("long");
        modelParmeter2.setInoutType(0);
        modelParmeter2.setParentId(0);
        list.add(modelParmeter2);

        ModelParmeter modelParmeter3= new ModelParmeter();
        modelParmeter3.setId(3036);
        modelParmeter3.setModelId(3018);
        modelParmeter3.setParmeterName("o1");
        modelParmeter3.setParmeterUnit("not_array");
        modelParmeter3.setParmeterType("long");
        modelParmeter3.setInoutType(1);
        modelParmeter3.setParentId(0);
        list.add(modelParmeter3);
//        ModelParmeter modelParmeter4= new ModelParmeter();
//        modelParmeter4.setId(3037);
//        modelParmeter4.setModelId(3018);
//        modelParmeter4.setParmeterName("struct_int");
//        modelParmeter4.setParmeterUnit("not_array");
//        modelParmeter4.setParmeterType("struct");
//        modelParmeter4.setInoutType(2);
//        modelParmeter4.setParentId(0);
//        list.add(modelParmeter4);
//        ModelParmeter modelParmeter5= new ModelParmeter();
//        modelParmeter5.setId(3038);
//        modelParmeter5.setModelId(3018);
//        modelParmeter5.setParmeterName("*cc2");
//        modelParmeter5.setParmeterUnit("not_array");
//        modelParmeter5.setParmeterType("struct_int");
//        modelParmeter5.setInoutType(0);
//        modelParmeter5.setParentId(0);
//        list.add(modelParmeter5);
//        ModelParmeter modelParmeter6= new ModelParmeter();
//        modelParmeter6.setId(3039);
//        modelParmeter6.setModelId(3018);
//        modelParmeter6.setParmeterName("c1");
//        modelParmeter6.setParmeterUnit("not_array");
//        modelParmeter6.setParmeterType("int");
//        modelParmeter6.setParentId(3037);
//        modelParmeter6.setInoutType(2);
//        list.add(modelParmeter6);
//        ModelParmeter modelParmeter7= new ModelParmeter();
//        modelParmeter7.setId(3040);
//        modelParmeter7.setModelId(3018);
//        modelParmeter7.setParmeterName("c2");
//        modelParmeter7.setParmeterUnit("not_array");
//        modelParmeter7.setParmeterType("int");
//        modelParmeter7.setParentId(3037);
//        modelParmeter7.setInoutType(2);
//        list.add(modelParmeter7);
//        String templatePath = "D:\\workspace\\work\\server\\eval\\evaluateSystem\\evaluateSystem\\src\\main\\webapp\\TemplateProject\\";
//        String projectName = "project" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        solutionUtil.makeSolution(list,templatePath,PROJECT_PATH,projectName);
//
//        try {
//            solutionUtil.zip(PROJECT_PATH+projectName,PROJECT_PATH,projectName + ".zip");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static String PROJECT_PATH = "C:\\upload5\\project\\";
    private static final String PARAM_TYPE = "paramType";
    private static final String PARAM_NAME = "paramName";
    private static final String PROJECT_LOWER = "TemplateProject";
    private static final String PROJECT_UPPER = "TEMPLATEPROJECT";
    private static final String STRUCT_AREA = "STRUCT_AREA";
    private static final String PARSE_FUNC_AREA = "PARSE_FUNC_AREA";
    private static final String CREATE_FUNC_AREA = "CREATE_FUNC_AREA";
    private static final String STRUCT_MEMBER_AREA = "STRUCT_MEMBER_AREA";
    private static final String FREE_IN_MEMBER = "FREE_IN_MEMBER";
    private static final String FREE_OUT_MEMBER = "FREE_OUT_MEMBER";
    private static final String POINTER_LEN = "Len";
    private static final int PARAM_FLAG_IN = 0;
    private static final int PARAM_FLAG_OUT = 1;
    private static final int PARAM_FLAG_STRUCT = 2;
    private static final String OBJECT_NAME = "OBJECT_NAME";
    private static final String FULL_NAME = "FULL_NAME";
    private static final String STR_NUM_FUNC = "STR_NUM_FUNC";
    private static final String STR_TAB = "STR_TAB";
    private static  final  String STR_ARRAY_FULL_NAME_LEN="STR_ARRAY_FULL_NAME_LEN";
    private static final String STR_BASE_PARSE =
            "STR_TABcJSON *paramNameData = cJSON_GetObjectItem(OBJECT_NAME, \"paramName\");\r\n" +
                    "STR_TABif (NULL != paramNameData)\r\n" +
                    "STR_TAB{\r\n" +
                    "STR_TAB\tpara = cJSON_Print(paramNameData);\r\n" +
                    "STR_TAB\tif (NULL != para)\r\n" +
                    "STR_TAB\t{\r\n" +
                    "STR_TAB\t\tFULL_NAME = STR_NUM_FUNC(para);\r\n" +
                    "STR_TAB\t\tfree(para);\r\n" +
                    "STR_TAB\t\tpara = NULL;\r\n" +
                    "STR_TAB\t}\r\n" +
                    "STR_TAB}\r\n";
    private static final String STR_ARRAY_PARSE =
            "STR_TABcJSON *paramNameArr = cJSON_GetObjectItem(OBJECT_NAME, \"paramName\");\r\n" +
                    "STR_TABif ((NULL != paramNameArr) && (cJSON_Array == paramNameArr->type))\r\n" +
                    "STR_TAB{\r\n" +
                    "STR_TAB\tint STR_ARRAY_FULL_NAME_LEN = cJSON_GetArraySize(paramNameArr);\r\n" +
                    "STR_TAB\tFULL_NAME = (paramType*)malloc(sizeof(paramType) * STR_ARRAY_FULL_NAME_LEN);\r\n" +
                    "STR_TAB\tif (NULL != FULL_NAME)\r\n" +
                    "STR_TAB\t{\r\n" +
                    "STR_TAB\t\tfor (int paramNameIdx = 0; paramNameIdx < STR_ARRAY_FULL_NAME_LEN; paramNameIdx++)\r\n" +
                    "STR_TAB\t\t{\r\n" +
                    "STR_TAB\t\t\tcJSON *paramNameData = cJSON_GetArrayItem(paramNameArr, paramNameIdx);\r\n" +
                    "STR_TAB\t\t\tpara = cJSON_Print(paramNameData);\r\n" +
                    "STR_TAB\t\t\tif (NULL != para)\r\n" +
                    "STR_TAB\t\t\t{\r\n" +
                    "STR_TAB\t\t\t\tFULL_NAME[paramNameIdx] = STR_NUM_FUNC(para);\r\n" +
                    "STR_TAB\t\t\t\tfree(para);\r\n" +
                    "STR_TAB\t\t\t\tpara = NULL;\r\n" +
                    "STR_TAB\t\t\t}\r\n" +
                    "STR_TAB\t\t}\r\n" +
                    "STR_TAB\t}\r\n" +
                    "STR_TAB}\r\n";
    private static final String STR_STRUCT_PARSE =
            "STR_TABcJSON *paramNameArr = cJSON_GetObjectItem(OBJECT_NAME, \"paramName\");\r\n" +
                    "STR_TABif ((NULL != paramNameArr) && (cJSON_Array == paramNameArr->type))\r\n" +
                    "STR_TAB{\r\n" +
                    "STR_TAB\tint STR_ARRAY_FULL_NAME_LEN = cJSON_GetArraySize(paramNameArr);\r\n" +
                    "STR_TAB\tFULL_NAME = (paramType*)malloc(sizeof(paramType) * STR_ARRAY_FULL_NAME_LEN);\r\n" +
                    "STR_TAB\tif (NULL != FULL_NAME)\r\n" +
                    "STR_TAB\t{\r\n" +
                    "STR_TAB\t\tfor (int paramNameIdx = 0; paramNameIdx < STR_ARRAY_FULL_NAME_LEN; paramNameIdx++)\r\n" +
                    "STR_TAB\t\t{\r\n" +
                    "STR_TAB\t\t\tcJSON *paramNameData = cJSON_GetArrayItem(paramNameArr, paramNameIdx);\r\n" +
                    "STRUCT_MEMBER_AREA" +
                    "STR_TAB\t\t}\r\n" +
                    "STR_TAB\t}\r\n" +
                    "STR_TAB}\r\n";
    private static final String STR_BASE_CREATE = "STR_TABcJSON_AddNumberToObject(OBJECT_NAME, \"paramName\", FULL_NAME);\r\n";
    private static final String STR_ARRAY_CREATE =
            "STR_TABcJSON *paramNameJSON = cJSON_CreateparamTypeArray(FULL_NAME, STR_ARRAY_FULL_NAME_LEN);\r\n" +
                    "STR_TABcJSON_AddItemToObject(OBJECT_NAME, \"paramName\", paramNameJSON);\r\n";
    private static final String STR_STRUCT_CREATE =
            "STR_TABcJSON *paramNameArr = cJSON_CreateArray();\r\n" +
                    "STR_TABfor (int paramNameIdx = 0; paramNameIdx < STR_ARRAY_FULL_NAME_LEN; paramNameIdx++)\r\n" +
                    "STR_TAB{\r\n" +
                    "STR_TAB\tcJSON *paramNameData = cJSON_CreateObject();\r\n" +
                    "STRUCT_MEMBER_AREA" +
                    "STR_TAB\tcJSON_AddItemToArray(paramNameArr, paramNameData);\r\n" +
                    "STR_TAB}\r\n" +
                    "STR_TABcJSON_AddItemToObject(OBJECT_NAME, \"paramName\", paramNameArr);\r\n";
    private static final String STR_BASE_FREE =
            "STR_TABif (NULL != FULL_NAME)\r\n" +
                    "STR_TAB{\r\n" +
                    "STR_TAB\tfree(FULL_NAME);\r\n" +
                    "STR_TAB\tFULL_NAME = NULL;\r\n" +
                    "STR_TAB}\r\n";
    private static final String STR_STRUCT_FREE =
            "STR_TABif (NULL != FULL_NAME)\r\n" +
                    "STR_TAB{\r\n" +
                    "STR_TAB\tfor (int paramNameIdx = 0; paramNameIdx < STR_ARRAY_FULL_NAME_LEN; paramNameIdx++)\r\n" +
                    "STR_TAB\t{\r\n" +
                    "STRUCT_MEMBER_AREA" +
                    "STR_TAB\t}\r\n" +
                    "STR_TAB\tfree(FULL_NAME);\r\n" +
                    "STR_TAB\tFULL_NAME = NULL;\r\n" +
                    "STR_TAB}\r\n";


    /**
     * 生成模型解决方案
     *
     * @param params
     * @param templatePath
     * @param projectPath
     * @param projectName
     */
    public void makeSolution(List<ModelParmeter> params, Map<String, List<StructParmeter>> sMap, String templatePath, String projectPath, String projectName, ModelInfo modelInfo) {
        StringBuffer headerBuffer = null;
        StringBuffer sourceBuffer = null;
        makeSolutionFile(templatePath, projectPath, projectName);
//        try {
//            headerBuffer = makeHeader(templatePath, projectName, params);
//            sourceBuffer = makeSource(templatePath, projectName, params);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        String headerName = projectPath + projectName + "\\" + projectName + "\\" + projectName + ".h";
//        String sourceName = projectPath + projectName + "\\" + projectName + "\\" + "support.cpp";
//
//        makeFile(headerName, headerBuffer);
//        makeFile(sourceName, sourceBuffer);
        String outPath=projectPath + projectName + "\\" + projectName + "\\";
        String freemakeFilePath=templatePath+ "\\"+"freemakefile"+"\\";
        ModelParmeterTool modelParmeterTool = new ModelParmeterTool(params, sMap);
        MakeFileUtil makeFileUtil = new MakeFileUtil(modelParmeterTool);
        makeFileUtil.makeCppFile(projectName,freemakeFilePath,outPath,modelInfo.getIsSingleFun());
    }





    /**
     * 生成zip包
     * @param srcPath
     * @param zipPath
     * @param zipFileName
     * @throws Exception
     */
    public void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
        if (srcPath.isEmpty() || zipPath.isEmpty() || zipFileName.isEmpty()) {
            //throw new ParameterException(ICommonResultCode.PARAMETER_IS_NULL);
            throw new Exception("parameter is null");
        }
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);
            //判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
            if (srcFile.isDirectory() && zipPath.indexOf(srcPath) != -1) {
                //throw new ParameterException(ICommonResultCode.INVALID_PARAMETER, "zipPath must not be the child dir");
                throw new Exception("zipPath must not be the child dir");
            }
            //判断压缩文件保存的路径是否存在，如果不存在，则创建目录
            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory()) {
                zipDir.mkdirs();
            }
            //创建压缩文件保存的文件对象
            String zipFilePath = zipPath + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                //SecurityManager securityManager = new SecurityManager();
                //securityManager.checkDelete(zipFilePath);
                //删除已存在的目标文件
                zipFile.delete();
            }
//            zipFile.deleteOnExit();
            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);
            //如果只是压缩一个文件，则需要截取该文件的父目录
            String srcRootDir = srcPath;
            if (srcFile.isFile()) {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1) {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            //调用递归压缩方法进行目录或文件压缩
            zip(srcRootDir, srcFile, zos);
            zos.flush();
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception
    {
        if (file == null)
        {
            return;
        }

        //如果是文件，则直接压缩该文件
        if (file.isFile())
        {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];

            //获取文件相对于压缩文件夹根目录的子路径
            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1)
            {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1)
            {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        }
        //如果是目录，则压缩整个目录
        else
        {
            //压缩目录中的文件或子目录
            File[] childFileList = file.listFiles();
            for (int n=0; n<childFileList.length; n++)
            {
                childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
                zip(srcRootDir, childFileList[n], zos);
            }
        }
    }


    /**
     * 制作解决方案头文件
     *
     * @param templatePath
     * @param projectName
     * @param params
     * @return
     * @throws IOException
     */
    public StringBuffer makeHeader(String templatePath, String projectName, List<ModelParmeter> params) throws IOException {
        StringBuffer headerBuffer = new StringBuffer();
        StringBuffer structBuffer = null;
        String headerTemplate = templatePath + "\\TemplateProject\\TemplateProject.h";

        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        structBuffer = makeStruct(params);

        try {
            File file = new File(headerTemplate);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            for (int i = 0; (line = br.readLine()) != null; i++) {
                if (line.contains(PROJECT_LOWER)) {
                    line = line.replaceAll(PROJECT_LOWER, projectName);
                } else if (line.contains(PROJECT_UPPER)) {
                    line = line.replaceAll(PROJECT_UPPER, projectName.toUpperCase());
                } else if (line.contains(STRUCT_AREA)) {
                    headerBuffer.append(structBuffer);
                    headerBuffer.append(System.getProperty("line.separator"));
                    continue;
                }
                headerBuffer.append(line);
                headerBuffer.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        return headerBuffer;
    }


    public StringBuffer makeStruct(List<ModelParmeter> params) {
        StringBuffer structBuffer = new StringBuffer();

        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (param.getParmeterType().equals("struct")) {
                structBuffer.append("struct " + param.getParmeterName());
                if (null != param.getParmeterSource() && !param.getParmeterSource().isEmpty()) {
                    structBuffer.append("				//" + param.getParmeterSource());
                }
                structBuffer.append(System.getProperty("line.separator"));
                structBuffer.append(("{"));
                structBuffer.append(System.getProperty("line.separator"));

                for (int j = 0; j < params.size(); j++) {
                    ModelParmeter member = params.get(j);
                    if (0 != member.getParentId() && param.getId().intValue() == member.getParentId().intValue()) {
                        structBuffer.append("	" + member.getParmeterType() + "				" + member.getParmeterName() + ";");
                        if (null != member.getParmeterSource() && !member.getParmeterSource().isEmpty()) {
                            structBuffer.append("				//" + member.getParmeterSource());
                        }
                        structBuffer.append(System.getProperty("line.separator"));
                    }
                }

                structBuffer.append(("};"));
                structBuffer.append(System.getProperty("line.separator"));
                structBuffer.append(System.getProperty("line.separator"));
            }
        }

        structBuffer.append("struct " + "tIn");
        structBuffer.append(System.getProperty("line.separator"));
        structBuffer.append(("{"));
        structBuffer.append(System.getProperty("line.separator"));
        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (param.getInoutType() == PARAM_FLAG_IN && !param.getParmeterType().equals("struct") && 0 == param.getParentId()) {
                structBuffer.append("	" + param.getParmeterType() + "				" + param.getParmeterName() + ";");
                if (null != param.getParmeterSource() && !param.getParmeterSource().isEmpty()) {
                    structBuffer.append("				//" + param.getParmeterSource());
                }
                structBuffer.append(System.getProperty("line.separator"));
            }
        }

        structBuffer.append(("};"));
        structBuffer.append(System.getProperty("line.separator"));
        structBuffer.append(System.getProperty("line.separator"));

        structBuffer.append("struct " + "tOut");
        structBuffer.append(System.getProperty("line.separator"));
        structBuffer.append(("{"));
        structBuffer.append(System.getProperty("line.separator"));
        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (param.getInoutType() == PARAM_FLAG_OUT && !param.getParmeterType().equals("struct") && 0 == param.getParentId()) {
                structBuffer.append("	" + param.getParmeterType() + "				" + param.getParmeterName() + ";");
                if (null != param.getParmeterSource() && !param.getParmeterSource().isEmpty()) {
                    structBuffer.append("				//" + param.getParmeterSource());
                }
                structBuffer.append(System.getProperty("line.separator"));
            }
        }

        structBuffer.append(("};"));

        return structBuffer;
    }


    public StringBuffer makeSource(String templatePath, String projectName, List<ModelParmeter> params) throws IOException {
        StringBuffer sourceBuffer = new StringBuffer();
        String sourceTemplate = templatePath + "\\TemplateProject\\support.cpp";

        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer parseBuffer = makeParseJSON(params, PARAM_FLAG_IN, "", "root", "pIn->", "\t\t\t");
        StringBuffer createBuffer = makeCreateJSON(params, PARAM_FLAG_OUT, "", "root", "pOut->", "\t\t\t");
        StringBuffer inFreeBuffer = makeFree(params, PARAM_FLAG_IN, "", "pIn->", "\t\t");
        StringBuffer outFreeBuffer = makeFree(params, PARAM_FLAG_OUT, "", "pOut->", "\t\t");

        try {
            File file = new File(sourceTemplate);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            for (int i = 0; (line = br.readLine()) != null; i++) {
                if (line.contains(PROJECT_LOWER)) {
                    line = line.replaceAll(PROJECT_LOWER, projectName);
                } else if (line.contains(PROJECT_UPPER)) {
                    line = line.replaceAll(PROJECT_UPPER, projectName.toUpperCase());
                } else if (line.contains(PARSE_FUNC_AREA)) {
                    sourceBuffer.append(parseBuffer);
                    continue;
                } else if (line.contains(CREATE_FUNC_AREA)) {
                    sourceBuffer.append(createBuffer);
                    continue;
                } else if (line.contains(FREE_IN_MEMBER)) {
                    sourceBuffer.append(inFreeBuffer);
                    continue;
                } else if (line.contains(FREE_OUT_MEMBER)) {
                    sourceBuffer.append(outFreeBuffer);
                    continue;
                }
                sourceBuffer.append(line);
                sourceBuffer.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        return sourceBuffer;
    }


    public boolean makeSolutionFile(String templatePath, String path, String projectName) {
        boolean bool = true;

        String fileDir = path + projectName;
        File fileDirTest = new File(fileDir);
        if (!fileDirTest.exists()) {
            fileDirTest.mkdirs();
        }
        fileDirTest.deleteOnExit();
        fileDirTest = new File(fileDir + "\\" + projectName);
        if (!fileDirTest.exists()) {
            fileDirTest.mkdirs();
        }
        fileDirTest.deleteOnExit();

        String solutionFileName = fileDir + "\\" + projectName + ".sln";
        String vcxprojFileName = fileDir + "\\" + projectName + "\\" + projectName + ".vcxproj";
        String filtersFileName = fileDir + "\\" + projectName + "\\" + projectName + ".vcxproj.filters";
        String jsonHeaderName = fileDir + "\\" + projectName + "\\" + "cJSON.h";
        String jsonSourceName = fileDir + "\\" + projectName + "\\" + "cJSON.c";
//        String sourceName = fileDir + "\\" + projectName + "\\" + projectName + ".cpp";

        String solutionTemplate = templatePath + "\\TemplateProject.sln";
        String vcxprojTemplate = templatePath + "\\TemplateProject\\TemplateProject.vcxproj";
        String filtersTemplate = templatePath + "\\TemplateProject\\TemplateProject.vcxproj.filters";
        String jsonHeaderTemplate = templatePath + "\\TemplateProject\\cJSON.h";
        String jsonSourceTemplate = templatePath + "\\TemplateProject\\cJSON.c";
//        String sourceTemplate = templatePath + "\\TemplateProject\\TemplateProject.cpp";
        StringBuffer fileBuffer = null;
        String fileContent = null;

        try {
            fileBuffer = readFile(solutionTemplate);
            fileContent = fileBuffer.toString();
            fileContent = fileContent.replaceAll(PROJECT_LOWER, projectName);
            makeFile(solutionFileName, fileContent);

            fileBuffer = readFile(vcxprojTemplate);
            fileContent = fileBuffer.toString();
            fileContent = fileContent.replaceAll(PROJECT_LOWER, projectName);
            fileContent = fileContent.replaceAll(PROJECT_UPPER, projectName.toUpperCase());
            makeFile(vcxprojFileName, fileContent);

            fileBuffer = readFile(filtersTemplate);
            fileContent = fileBuffer.toString();
            fileContent = fileContent.replaceAll(PROJECT_LOWER, projectName);
            makeFile(filtersFileName, fileContent);

            fileBuffer = readFile(jsonHeaderTemplate);
            makeFile(jsonHeaderName, fileBuffer);

            fileBuffer = readFile(jsonSourceTemplate);
            makeFile(jsonSourceName, fileBuffer);

//            fileBuffer = readFile(sourceTemplate);
//            fileContent = fileBuffer.toString();
//            fileContent = fileContent.replaceAll(PROJECT_LOWER, projectName);
//            makeFile(sourceName, fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bool;
    }


    /**
     * 解决方案
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public StringBuffer readFile(String fileName) throws IOException {
        StringBuffer fileBuffer = new StringBuffer();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;

        try {
            File file = new File(fileName);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            for (int i = 0; (line = br.readLine()) != null; i++) {
                fileBuffer.append(line);
                fileBuffer.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        return fileBuffer;
    }

    /**
     * ???????
     *
     * @param fileName
     * @param fileContent
     * @return
     */
    public boolean makeFile(String fileName, String fileContent) {
        boolean bool = false;

        try {
            File file = new File(fileName);

            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                file.deleteOnExit();
            }

            writeFile(file, fileContent);

            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bool;
    }

    /**
     * 制作文件
     *
     * @param fileName
     * @param fileBuffer
     * @return
     */
    public boolean makeFile(String fileName, StringBuffer fileBuffer) {
        boolean bool = false;
        try {
            File file = new File(fileName);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            file.deleteOnExit();
            writeFile(file, fileBuffer.toString());
            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bool;
    }


    /**
     * 写文件
     *
     * @param file       文件名
     * @param fileContent 文件内容
     * @return
     * @throws IOException
     */
    public boolean writeFile(File file, String fileContent) throws IOException {
        boolean bool = false;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(fileContent.toCharArray());
            pw.flush();
            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return bool;
    }

    public StringBuffer makeParseJSON(List<ModelParmeter> params, int typeFlag, String parentType, String objectName, String parentName, String strTab) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String paramName = "";
        String line = "";
        int parentId = ModelInfoUtil.getParentId(params, parentType);

        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (typeFlag == param.getInoutType() && !param.getParmeterName().endsWith(POINTER_LEN)
                    && ((0 != param.getParentId() && param.getParentId() == parentId)
                    || (0 == parentId && 0 == param.getParentId()))) {
                if (ModelInfoUtil.confirmType(ModelInfoUtil.INT_TYPE_SET, param.getParmeterType()) || ModelInfoUtil.confirmType(ModelInfoUtil.FLOAT_TYPE_SET, param.getParmeterType())) {
                    if (param.getParmeterName().startsWith("*")) {
                        paramName = param.getParmeterName().substring(1);
                        line = STR_ARRAY_PARSE.replaceAll(PARAM_TYPE, param.getParmeterType());
                    } else {
                        paramName = param.getParmeterName();
                        line = STR_BASE_PARSE;
                    }
                    line = line.replaceAll(OBJECT_NAME, objectName);
                    line = line.replaceAll(PARAM_NAME, paramName);
                    line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");

                    line = line.replaceAll(FULL_NAME, parentName + paramName);
                    line = line.replaceAll(STR_NUM_FUNC, getNumFunc(param.getParmeterType()));
                    line = line.replaceAll(STR_TAB, strTab);
                } else {
                    paramName = param.getParmeterName().substring(1);
                    String strIndex = paramName + "Idx";
                    StringBuffer member = makeParseJSON(params, PARAM_FLAG_STRUCT, param.getParmeterType(), paramName + "Data", parentName + paramName + "[" + strIndex + "].", strTab + "\t\t\t");
                    line = STR_STRUCT_PARSE.replaceAll(OBJECT_NAME, objectName);
                    line = line.replaceAll(PARAM_TYPE, param.getParmeterType());
                    line = line.replaceAll(PARAM_NAME, paramName);
                    line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");
                    line = line.replaceAll(FULL_NAME, parentName + paramName);
                    line = line.replaceAll(STRUCT_MEMBER_AREA, member.toString());
                    line = line.replaceAll(STR_TAB, strTab);
                }

                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));
            }
        }

        return buffer;
    }

    public StringBuffer makeCreateJSON(List<ModelParmeter> params, int typeFlag, String parentType, String objectName, String parentName, String strTab) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String paramName = "";
        String line = "";
        int parentId = ModelInfoUtil.getParentId(params, parentType);

        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (typeFlag == param.getInoutType() && !param.getParmeterName().endsWith(POINTER_LEN)
                    && ((0 != param.getParentId() && param.getParentId() == parentId)
                    || (0 == parentId && 0 == param.getParentId()))) {
                if (ModelInfoUtil.confirmType(ModelInfoUtil.INT_TYPE_SET, param.getParmeterType()) || ModelInfoUtil.confirmType(ModelInfoUtil.FLOAT_TYPE_SET, param.getParmeterType())) {
                    if (param.getParmeterName().startsWith("*")) {
                        paramName = param.getParmeterName().substring(1);
                        line = STR_ARRAY_CREATE.replaceAll(PARAM_TYPE, convertType(param.getParmeterType()));
                    } else {
                        paramName = param.getParmeterName();
                        line = STR_BASE_CREATE;
                    }
                    line = line.replaceAll(OBJECT_NAME, objectName);
                    line = line.replaceAll(PARAM_NAME, paramName);
                    line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");
                    line = line.replaceAll(FULL_NAME, parentName + paramName);
                    line = line.replaceAll(STR_TAB, strTab);
                } else {
                    paramName = param.getParmeterName().substring(1);
                    String strIndex = paramName + "Idx";
                    StringBuffer member = makeCreateJSON(params, PARAM_FLAG_STRUCT, param.getParmeterType(), paramName + "Data", parentName + paramName + "[" + strIndex + "].", strTab + "\t");
                    line = STR_STRUCT_CREATE.replaceAll(OBJECT_NAME, objectName);
                    line = line.replaceAll(PARAM_NAME, paramName);
                    line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");
                    line = line.replaceAll(FULL_NAME, parentName + paramName);
                    line = line.replaceAll(STRUCT_MEMBER_AREA, member.toString());
                    line = line.replaceAll(STR_TAB, strTab);
                }

                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));
            }
        }

        return buffer;
    }

    public StringBuffer makeFree(List<ModelParmeter> params, int typeFlag, String parentType, String parentName, String strTab) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String paramName = "";
        String line = "";
        int parentId = ModelInfoUtil.getParentId(params, parentType);

        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (typeFlag == param.getInoutType() && param.getParmeterName().startsWith("*")
                    && ((0 != param.getParentId() && param.getParentId() == parentId)
                    || (0 == parentId && 0 == param.getParentId()))) {
                paramName = param.getParmeterName().substring(1);
                if (ModelInfoUtil.confirmType(ModelInfoUtil.INT_TYPE_SET, param.getParmeterType()) || ModelInfoUtil.confirmType(ModelInfoUtil.FLOAT_TYPE_SET, param.getParmeterType())) {
                    line = STR_BASE_FREE.replaceAll(FULL_NAME, parentName + paramName);
                    line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");
                    line = line.replaceAll(STR_TAB, strTab);
                } else {
                    String strIndex = param.getParmeterName().substring(1) + "Idx";
                    StringBuffer member = makeFree(params, PARAM_FLAG_STRUCT, param.getParmeterType(), parentName + paramName + "[" + strIndex + "].", strTab + "\t\t");
                    if (member.toString().isEmpty()) {
                        line = STR_BASE_FREE.replaceAll(FULL_NAME, parentName + paramName);
                        line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");
                        line = line.replaceAll(STR_TAB, strTab);
                    } else {
                        line = STR_STRUCT_FREE.replaceAll(PARAM_NAME, paramName);
                        line = line.replaceAll(STR_ARRAY_FULL_NAME_LEN, paramName+"Len");
                        line = line.replaceAll(FULL_NAME, parentName + paramName);
                        line = line.replaceAll(STRUCT_MEMBER_AREA, member.toString());
                        line = line.replaceAll(STR_TAB, strTab);
                    }
                }

                buffer.append(line);
            }
        }

        return buffer;
    }


    /**
     * 基础类型转换
     * @param baseType
     * @return
     */
    public String convertType(String baseType) {
        String type = "";
        if (ModelInfoUtil.confirmType(ModelInfoUtil.INT_TYPE_SET, baseType)) {
            type = "Int";
        } else if (baseType.equals("float")) {
            type = "Float";
        } else if (baseType.equals("double")) {
            type = "Double";
        }
        return type;
    }

    public String getNumFunc(String baseType) {
        String func = "";

        if (ModelInfoUtil.confirmType(ModelInfoUtil.INT_TYPE_SET, baseType)) {
            func = "atoi";
        }
        else if (ModelInfoUtil.confirmType(ModelInfoUtil.FLOAT_TYPE_SET, baseType)) {
            func = "atof";
        }

        return func;
    }
}
