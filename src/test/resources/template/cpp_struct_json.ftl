<#import "cpp_common.ftl" as c/>
<#macro parameterToJson param inName outName>
    <#if param.struct>
        <#if param.array>
        cJSON* ${outName} = cJSON_CreateArray();
        for(int i=0;<@c.checkPointer name=inName+"[i]"/>;i++){
            cJSON* ${outName}Item = ${param.cppType?uncap_first}ToJson(${inName}[i]);
            cJSON_AddItemToArray(${outName},${param.name}JsonItem);
        }
        <#else>
        cJSON* ${outName} = ${param.cppType?uncap_first}ToJson(${inName});
        </#if>
    <#else>
        <#if param.array>
        cJSON* ${outName} = cJSON_CreateArray();
        for(int i=0;<@c.checkStaticPointer name=inName index="i"/>;i++){
            <#if param.string>
            cJSON* ${outName}Item = cJSON_CreateString(${inName}[i]));
            <#elseif param.number>
            cJSON* ${outName}Item = cJSON_CreateNumber(${inName}[i]);
            </#if>
            cJSON_AddItemToArray(${outName},${outName}Item);
        }
        <#else>
            <#if param.string>
            cJSON* ${outName} = cJSON_CreateString(${inName});
            <#elseif param.number>
            cJSON* ${outName} = cJSON_CreateNumber(${inName});
            </#if>
        </#if>
    </#if>
</#macro>

<#macro structToJson param>
cJSON*  ${param.cppType?uncap_first}ToJson(struct ${param.cppType}* ${param.name}){
    if(NULL != ${param.name}){
        cJSON* json = cJSON_CreateObject();
        <#if param.parameters?? && param.parameters?size gt 0>
        <#list param.parameters as each>
        <@c.type param=each/> ${each.name} = ${param.name}->${each.name};
        <#if each.array>int ${each.name}_length = ${param.name}->${each.name}_length;</#if>
        if(<@c.checkParameter param=each/>){
            <@parameterToJson param=each inName=each.name outName=each.name+"Json"/>
            cJSON_AddItemToObject(json,"${each.name}",${each.name}Json);
        }
        </#list>
        </#if>
        return json;
    }
    return NULL;
}
</#macro>

<#macro StructToJsonString param>
char* ${param.cppType?uncap_first}ToJsonString(struct ${param.cppType}* ${param.name}){
    if(NULL != ${param.name}){
        cJSON* json = ${param.cppType?uncap_first}ToJson(${param.name});
        return cJSON_Print(json);
    }
    return NULL;
}
</#macro>

<#macro structToJsonList list>
<#if list??>
    <#list list as each>
        <@structToJson param=each/>
    </#list>
</#if>
</#macro>