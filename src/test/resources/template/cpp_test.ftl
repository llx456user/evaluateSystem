<#import "cpp_struct.ftl" as cs/>
#include "stdafx.h"
#include <windows.h>
#include <stdlib.h>
#include <stdio.h>
#include "../project20180427142603/${header}.h"
#include "testdll.h"
#pragma comment(lib, "..\\Debug\\project20180427142603.lib") //添加lib文件引用


<#macro parameterTestData param parentName>
    <#if param.struct>
        <#if param.array>
        ${parentName}->${param.name}[0] = test${param.cppType}Data();
        ${parentName}->${param.name}_length = 1;
        <#else>
        ${parentName}->${param.name} = test${param.cppType}Data();
        </#if>
    <#else>
        <#if param.array>
            <#if param.string>
            ${parentName}->${param.name}[0] = (char*)"${param.name}(${param.cppType})";
            ${parentName}->${param.name}_length = 1;
            <#else>
            ${parentName}->${param.name}[0] = ${param.name?length};
            </#if>
        ${parentName}->${param.name}_length = 1;
        <#else>
            <#if param.string>
            ${parentName}->${param.name} = (char*)"${param.name}(${param.cppType})";
            <#else>
            ${parentName}->${param.name} = ${param.name?length};
            </#if>
        </#if>
    </#if>
</#macro>

<#macro structTestData param>
struct ${param.cppType}* test${param.cppType}Data(){
    struct ${param.cppType}* ${param.name} = (${param.cppType}*)malloc(sizeof(${param.cppType}));
<#if param.parameters??>
    <#list param.parameters as each>
        <@parameterTestData param=each parentName=param.name/>
    </#list>
</#if>
    return  ${param.name};
    }
</#macro>
<#if list ??>
<#list list as each>
     <@structTestData param=each/>
</#list>
</#if>

int main(){
    ${inModel.cppType}* ${inModel.name} = test${inModel.cppType}Data();
    ${outModel.cppType}* ${outModel.name} = test${outModel.cppType}Data();
    char* in = ${inModel.cppType?uncap_first}ToJsonString(${inModel.name});
    char* out = ${outModel.cppType?uncap_first}ToJsonString(${outModel.name});
    printf("in : %s",in);
    char* result = execute(in);
    printf("out : %s",out);
    printf("result : %s",result);
    return 0;
}
