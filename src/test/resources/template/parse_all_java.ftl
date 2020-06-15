<#macro relative current parentName>${parentName}_${current.name}</#macro>
<#macro parseStatic current parentName>
    <#if !current.struct>
    ${current.type} ${current.name} = ${parentName}Json.get${current.type}("${current.name}");
    </#if>
</#macro>
<#macro parseStaticArray current parentName>
    <#if !current.struct>
    JSONArray ${current.name}Array = ${parentName}Json.getJSONArray("${current.name}");
    ${current.type}[] ${current.name}s = new ${current.type}[${current.name}Array.size()];
    for (int <@relative current=current parentName=parentName />_index = 0; <@relative current=current parentName=parentName />_index < ${current.name}Array.size(); <@relative current=current parentName=parentName />_index++) {
    ${current.name}[<@relative current=current parentName=parentName />_index] = ${current.name}Array.get${current.type?cap_first}(<@relative current=current parentName=parentName />_index);
    }
    </#if>
</#macro>
<#macro parseObject current parentName>
${current.type} ${current.name} = new ${current.type}();
    <#if current.parameters??>
        <#list current.parameters as each>
            <@parse current=each parentName=current.name/>
        </#list>
    </#if>
</#macro>
<#macro parseArray current parentName>
JSONArray ${current.name}Array = ${parentName}Json.getJSONArray("${current.name}");
${current.type}[] ${current.name} = new ${current.type}[${current.name}Array.size()];
for (int <@relative current=current parentName=parentName />_index = 0; <@relative current=current parentName=parentName />_index < ${current.name}Array.size(); <@relative current=current parentName=parentName />_index++) {
JSONObject <@relative current=current parentName=parentName />Json = ${current.name}Array.getJSONObject(<@relative current=current parentName=parentName />_index);

${current.type} <@relative current=current parentName=parentName />_obj = new ${current.type}();
    <#if current.parameters??>
        <#list current.parameters as each>
            <@parse current=each parentName=parentName+"_"+current.name+"_obj"/>
        </#list>
    </#if>
${current.name}[<@relative current=current parentName=parentName />_index] = <@relative current=current parentName=parentName />_obj;
}
</#macro>
<#macro parse current parentName>
    <#if current.struct>
        <#if current.array>
            <@parseArray current=current parentName=parentName/>
        <#else>
        JSONObject ${current.name}Json = ${parentName}Json.getJSONObject("${current.name}");
            <@parseObject current=current parentName=parentName/>
        </#if>
    <#else>
        <#if current.array>
            <@parseStatic current=current parentName=parentName/>
        <#else>
            <@parseStatic current=current parentName=parentName/>
        </#if>
    </#if>
${parentName}.set${current.name?cap_first}(${current.name});
</#macro>
package com.tf.base.jni.test.output;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.jni.test.Model.Book;
import com.tf.base.jni.test.Model.Box;
import com.tf.base.jni.test.Model.Pen;
import com.tf.base.jni.test.Model.Student;

public class JsonParser{


//todo need to product step one
public void execute(${model.type} ${model.name}) {
//todo
}

//todo need to product step two
public Object parse(JSONObject sourceJson) {
<@parse current=model parentName="source"/>
return ${model.name};
}

}