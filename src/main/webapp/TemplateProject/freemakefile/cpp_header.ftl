<#import "cpp_common.ftl" as c/>
<#import "cpp_struct.ftl" as cs/>
#include <windows.h>
#include <stdlib.h>
#include "cJSON.h"
 #include <iostream>
#include <fstream>
#include <string>
using namespace std;

#define ${api_name} __declspec(dllexport)

extern "C" {
${api_name} char* modelfunc(const char* inputFilePath, const char* outFilePath);
}

/**************************************************/
/*                      define                    */
/**************************************************/
#define DLL_ERROR        -1
#define DLL_OK            0


/**************************************************/
/*                     struct                     */
/**************************************************/
<#if arrayFunction ?? && arrayFunction>
    <#if inModel?? && inModel.parameters?? && inModel.parameters?size gt 0>
struct InStruct{
        <#list inModel.parameters as each>
    <@c.typeNoArray param=each/> ${each.name};
        </#list>
};
    </#if>
    <#if outModel?? && outModel.parameters?? && outModel.parameters?size gt 0>
struct OutStruct{
        <#list outModel.parameters as each>
    <@c.typeNoArray param=each/> ${each.name};
        </#list>
};
    </#if>
</#if>
<#if list??>
<@cs.structList list=list />
</#if>

/**************************************************/
/*                     function                   */
/**************************************************/<#macro structToJsonString param>
char* ${param.cppType?uncap_first}ToJsonString(<@c.typeNoArray param=param/> ${param.name})
</#macro>
<#macro structToJson param>
cJSON* ${param.cppType?uncap_first}ToJson(<@c.typeNoArray param=param/> ${param.name})
</#macro>
<#macro jsonToStruct param>
<@c.typeNoArray param=param/> parse${param.cppType}(cJSON *json)
</#macro>
bool contains(cJSON* json,const char* key);
<#if list??>
<#list list as each>
<#if each.struct>
<@structToJsonString param=each/>;
<@structToJson param=each/>;
<@jsonToStruct param=each/>;
</#if>
</#list>
</#if>

<#if arrayFunction ?? && arrayFunction>
int dllMain(struct InStruct *in,struct OutStruct *out,char* errorMsg);
<#else>
int dllMain(<@c.type param=inModel/> input_${inModel.name}, <@c.type param=outModel/> output_${outModel.name},char* errorMsg);
</#if>
<@c.type param=inModel/> parseInput(const char* strIn);
char* parseOutput(<@c.type param=outModel/> output_${outModel.name});
char* errorJSON(int ret,const char* errorMsg);
void freeInMember(<@c.type param=inModel/> ${inModel.name});
void freeOutMember(<@c.type param=outModel/> ${outModel.name});
//GB2312到UTF-8的转换
char* G2U(const char* gb2312);
//UTF-8到GB2312的转换
char* U2G(const char* utf8);
