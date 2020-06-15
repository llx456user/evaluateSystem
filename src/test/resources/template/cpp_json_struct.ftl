<#import "cpp_common.ftl" as c/>
<#macro jsonToParameter param jsonName>
    <#if param.struct>
        <#if param.array>
        cJSON* ${param.name}JsonArray = cJSON_GetObjectItem(${jsonName},"${param.name}");
        int ${param.name}Length = cJSON_GetArraySize(${param.name}JsonArray);
        ${param.cppType}** ${param.name} = (${param.cppType}**)malloc(sizeof(${param.cppType}*)*${param.name}Length);
        cJSON* ${param.name}Json = ${param.name}JsonArray->child;
        for (int i = 0; i < ${param.name}Length; i++) {
            ${param.name}[i] = parse${param.cppType}(${param.name}Json);
            ${param.name}Json = ${param.name}Json->next;//last time return null
            //${param.name}++;
        }
        <#else>
        cJSON* ${param.name}Json = cJSON_GetObjectItem(${jsonName},"${param.name}");
        struct ${param.cppType}* ${param.name} = parse${param.cppType}(${param.name}Json);
        </#if>
    <#else>
        <#if param.array>
        cJSON* ${param.name}JsonArray = cJSON_GetObjectItem(${jsonName},"${param.name}");
        int ${param.name}Length = cJSON_GetArraySize(${param.name}JsonArray);
            <#if param.string>
            ${param.cppType}** ${param.name} = (${param.cppType}**)malloc(sizeof(${param.cppType}*)*${param.name}Length);
            <#elseif param.number>
            ${param.cppType}* ${param.name} = (${param.cppType}*)malloc(sizeof(${param.cppType})*${param.name}Length);
            </#if>
            cJSON* ${param.name}JsonItem = ${param.name}JsonArray->child;
        for (int i = 0; i < ${param.name}Length; i++) {
            <#if param.string>
            ${param.name}[i] = ${param.name}JsonItem->valuestring;
            <#elseif param.double>
            ${param.name}[i] = ${param.name}JsonItem->valuedouble;
            <#elseif param.int>
            ${param.name}[i] = ${param.name}JsonItem->valueint;
            </#if>
            ${param.name}JsonItem = ${param.name}JsonItem->next;//last time return null
            //${param.name}++;
        }
        <#else>
            cJSON* ${param.name}JsonItem = cJSON_GetObjectItem(${jsonName},"${param.name}");
            <#if param.string>
            ${param.cppType} *${param.name} = ${param.name}JsonItem->valuestring;
            <#elseif param.double>
            ${param.cppType} ${param.name} = ${param.name}JsonItem->valuedouble;
            <#elseif param.int>
            ${param.cppType} ${param.name} = ${param.name}JsonItem->valueint;
            </#if>
        </#if>
    </#if>
</#macro>

<#macro jsonStringToStruct param>
</#macro>

<#macro jsonToStruct param>
struct ${param.cppType}* parse${param.cppType}(cJSON *json){
if(<@c.checkPointer name="json"/>) {
struct ${param.cppType}* ${param.name} = (${param.cppType}*)malloc(sizeof(${param.cppType}));
    <#if param.parameters?? && param.parameters?size gt 0>
        <#list param.parameters as each>
        if (contains(json,"${each.name}")) {
            <@jsonToParameter param=each jsonName="json"/>
            ${param.name}->${each.name} = ${each.name};
            <#if each.array>
            ${param.name}->${each.name}_length = ${each.name}Length;
            </#if>
        }
        </#list>
    </#if>
return ${param.name};
}
return NULL;
}
</#macro>

<#macro jsonToStructList list>
<#if list ??>
    <#list list as each>
        <@jsonToStruct param=each/>
    </#list>
</#if>
</#macro>