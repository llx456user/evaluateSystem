<#import "cpp_common.ftl" as c/>
<#import "cpp_struct_json.ftl" as jsc/>
<#import "cpp_json_struct.ftl" as sjc/>
#include <windows.h>
#include <stdlib.h>
#include "cJSON.h"
#include "${header}.h"

/**************************************************/
/*                     function                     */
/**************************************************/
<#if list ??>
<#list list as each>
    <@sjc.jsonToStruct param=each/>
    <@jsc.structToJson param=each/>
    <@jsc.StructToJsonString param=each/>
</#list>
</#if>
bool contains(cJSON* json,const char* key){
    if(NULL != json && NULL != key){
        cJSON* value = cJSON_GetObjectItem(json,key);
        if(NULL != value){
            return true;
        }
    }
    return false;
}

<@c.type param=inModel/> parseInput(const char* jsonString){
    if(NULL != jsonString){
        cJSON* json = cJSON_Parse(jsonString);
<#if inModel.struct>
       ${inModel.cppType?cap_first}* p = parse${inModel.cppType?cap_first}(json);
        //将申请的内存释放掉
		cJSON_Delete(json);
        return p;
<#else>
        if(contains(json,"${inModel.name}")){
            <@sjc.jsonToParameter param=inModel jsonName="json"/>
            return ${inModel.name};
        }
</#if>
    }
    return NULL;
}

char* parseOutput(<@c.type param=outModel/> ${outModel.name}){
    if(<@c.checkPointer name=outModel.name/>){
<#if outModel.struct>
        cJSON* json = ${outModel.cppType?uncap_first}ToJson(${outModel.name});
<#else>
        cJSON* json = cJSON_CreateObject();
\     \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    <@jsc.parameterToJson param=outModel inName=outModel.name outName=outModel.name+"Json"/>
        cJSON_AddItemToObject(json,"${outModel.name}",${outModel.name}Json);
</#if>
    char* reJson = cJSON_Print(json);
    //注意释放内存
    cJSON_Delete(json);
    return reJson;
    }
    return NULL;
}

${api_name} char* modelfunc(const char* inputFilePath,const char* outFilePath){
    int result = DLL_ERROR;
    ifstream fInParamer(inputFilePath);
    string temp;
    while (getline(fInParamer, temp)) //按行读取字符串
    {
    }
    fInParamer.close();

    <@c.type param=inModel/> input_${inModel.name} = parseInput(temp.c_str());
    temp = "";
    char* outputJsonString = NULL;
    if (NULL != input_${inModel.name}){
        <@c.type param=outModel/> output_${outModel.name} = <@c.init param=outModel/>;
        if (NULL != output_${outModel.name}){
        <#if arrayFunction ?? && arrayFunction>
            struct InStruct *in = (struct InStruct*)malloc(sizeof(struct InStruct));
            struct OutStruct *out = (struct OutStruct*)malloc(sizeof(struct OutStruct));
            char *error = NULL;
            <#if inModel?? && inModel.parameters?? && inModel.parameters?size gt 0>
                <#if outModel?? && outModel.parameters?? && outModel.parameters?size gt 0>
                    <#list outModel.parameters as each>
            output_${outModel.name}->${each.name}_length = input_${inModel.name}->${inModel.parameters[0].name}_length;
            output_${outModel.name}->${each.name} = (<@c.type param=each />)malloc(sizeof(<@c.typeNoArray param=each />)*output_${outModel.name}->${each.name}_length);
                    </#list>
                </#if>
            for(int i=0;i<input_${inModel.name}->${inModel.parameters[0].name}_length;i++){
                <#list inModel.parameters as each>
                in->${each.name} = input_${inModel.name}->${each.name}[i];
                </#list>
                result = dllMain(in,out,error);
            <#if outModel?? && outModel.parameters?? && outModel.parameters?size gt 0>
                <#list outModel.parameters as each>
                output_${outModel.name}->${each.name}[i] = out->${each.name};
                </#list>
            </#if>
            </#if>
                if (DLL_OK != result){
                    outputJsonString = error;
                    break;
                }
            }
        <#else>
            result = dllMain(input_${inModel.name}, output_${outModel.name},outputJsonString);
        </#if>
            if (DLL_OK == result){
                outputJsonString = parseOutput(output_${outModel.name});
                //进行输出到文件处理
                std::ofstream fOutParamer(outFilePath);
                fOutParamer << outputJsonString;
                fOutParamer.close();
                //清空内存
                if (outputJsonString != NULL) {
                 free(outputJsonString);
                }
            }
            <#if outModel.struct || outModel.string || outModel.array>
            freeOutMember(output_${outModel.name});
            free(output_${outModel.name});
            </#if>
            output_${outModel.name} = NULL;
        }

        <#if inModel.struct || inModel.string || inModel.array>
        freeInMember(input_${inModel.name});
        free(input_${inModel.name});
        </#if>
        input_${inModel.name} = NULL;
    }

    if (DLL_OK != result){
        cJSON* errorCodeJson = cJSON_CreateNumber(result);
        cJSON* errorStringJson = cJSON_CreateString(outputJsonString);
        cJSON* errorJson = cJSON_CreateObject();
        cJSON_AddItemToObject(errorJson,"code",errorCodeJson);
        cJSON_AddItemToObject(errorJson,"message",errorStringJson);
        outputJsonString = cJSON_Print(errorJson);
    }else {
        cJSON* okCodeJson = cJSON_CreateNumber(result);
        cJSON* okStringJson = cJSON_CreateString("正确");
        cJSON* okJson = cJSON_CreateObject();
        cJSON_AddItemToObject(okJson, "code", okCodeJson);
        cJSON_AddItemToObject(okJson, "message", okStringJson);
        outputJsonString = cJSON_Print(okJson);
    }
    return outputJsonString;
}
<#if inModel.struct || inModel.string || inModel.array>
void freeInMember(struct ${inModel.cppType}*<#if inModel.array>*</#if> ${inModel.name}) {
        <#list inModel.parameters as each>
            if (${inModel.name}->${each.name}) {
                free(${inModel.name}->${each.name});
                ${inModel.name}->${each.name} = NULL;
		   }
        </#list>
    }
</#if>
<#if outModel.struct || outModel.string || outModel.array>
void freeOutMember(struct ${outModel.cppType}*<#if outModel.array>*</#if> ${outModel.name}) {}
</#if>


//GB2312到UTF-8的转换
char* G2U(const char* gb2312)
    {
    int len = MultiByteToWideChar(CP_ACP, 0, gb2312, -1, NULL, 0);
    wchar_t* wstr = new wchar_t[len + 1];
    memset(wstr, 0, len + 1);
    MultiByteToWideChar(CP_ACP, 0, gb2312, -1, wstr, len);
    len = WideCharToMultiByte(CP_UTF8, 0, wstr, -1, NULL, 0, NULL, NULL);
    char* str = new char[len + 1];
    memset(str, 0, len + 1);
    WideCharToMultiByte(CP_UTF8, 0, wstr, -1, str, len, NULL, NULL);
    if (wstr) delete[] wstr;
    return str;
}


//UTF-8到GB2312的转换
char* U2G(const char* utf8)
{
    int len = MultiByteToWideChar(CP_UTF8, 0, utf8, -1, NULL, 0);
    wchar_t* wstr = new wchar_t[len + 1];
    memset(wstr, 0, len + 1);
    MultiByteToWideChar(CP_UTF8, 0, utf8, -1, wstr, len);
    len = WideCharToMultiByte(CP_ACP, 0, wstr, -1, NULL, 0, NULL, NULL);
    char* str = new char[len + 1];
    memset(str, 0, len + 1);
    WideCharToMultiByte(CP_ACP, 0, wstr, -1, str, len, NULL, NULL);
    if (wstr) delete[] wstr;
    return str;
}