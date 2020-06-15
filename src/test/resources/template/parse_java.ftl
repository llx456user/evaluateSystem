<#macro relative current jsonName>${jsonName}_${current.name}</#macro>
<#macro parseParameter current jsonName parent>
    <#if !current.struct>
    if (${jsonName}.containsKey("${current.name}")) {
        ${current.type} ${current.name} = ${jsonName}.get${current.type}("${current.name}");
        ${parent}.${current.name} = ${current.name};
    }
    </#if>
</#macro>
<#macro parseParameterArray current jsonName parent>
    <#if !current.struct && current.array>
    if (${jsonName}.containsKey("${current.name}")) {
    JSONArray ${current.name}JsonArray = ${jsonName}.getJSONArray("${current.name}");
        ${current.type}[] ${current.name}Array = new ${current.type}[${current.name}JsonArray.size()];
    for (int i = 0; i < ${current.name}JsonArray.size(); i++) {
        ${current.name}Array[i] = ${current.name}JsonArray.get${current.type}(i);
    }
        ${parent}.${current.name} = ${current.name}Array;
    }
    </#if>
</#macro>
<#macro parseObject current jsonName parent>
    <#if current.struct && !current.array>
    if (${jsonName}.containsKey("${current.name}")) {
    JSONObject ${current.name}Json = ${jsonName}.getJSONObject("${current.name}");
        ${current.type} ${current.name} = ${current.type}.parse(${current.name}Json);
        ${parent}.${current.name} = ${current.name};
    }
    </#if>
</#macro>
<#macro parseObjectArray current jsonName parent>
    <#if current.struct && current.array>
    if (${jsonName}.containsKey("${current.name}")) {
    JSONArray ${current.name}JsonArray = ${jsonName}.getJSONArray("${current.name}");
        ${current.type}[] ${current.name}Array = new ${current.type}[${current.name}JsonArray.size()];
    for (int i = 0; i < ${current.name}JsonArray.size(); i++) {
    JSONObject ${current.name}Json = ${current.name}JsonArray.getJSONObject(i);
        ${current.name}Array[i] = ${current.type}.parse(${current.name}Json);
    }
        ${parent}.${current.name} = ${current.name}Array;
    }
    </#if>
</#macro>
package com.tf.base.jni.test.output;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

<#if list??>
    <#list list as each>
        <#if each.struct>
        class ${each.type}{
            <#if each.parameters?? && each.parameters?size gt 0>
                <#list each.parameters as param>
                    <#if param.struct>
                        ${param.type}<#if param.array>[]</#if> ${param.name} = <#if param.array>new ${param.type}[]{</#if>new ${param.type}()<#if param.array>}</#if>;
                    <#else>
                        ${param.type}<#if param.array>[]</#if> ${param.name} = <#if param.array>new ${param.type}[]{</#if><#if param.string>"${param.name}(${param.type})"<#else>0</#if><#if param.array>}</#if>;
                    </#if>
                </#list>
            </#if>
        public static ${each.type} parse(JSONObject json){
        if(null != json) {
            ${each.type} ${each.name} = new ${each.type}();
            <#if each.parameters?? && each.parameters?size gt 0>
                <#list each.parameters as param>
                    <#if param.struct>
                        <#if param.array>
                            <@parseObjectArray current=param jsonName="json" parent=each.name/>
                        <#else>
                            <@parseObject current=param jsonName="json" parent=each.name/>
                        </#if>
                    <#else>
                        <#if param.array>
                            <@parseParameterArray current=param jsonName="json" parent=each.name/>
                        <#else>
                            <@parseParameter current=param jsonName="json" parent=each.name/>
                        </#if>
                    </#if>
                </#list>
            </#if>
        return ${each.name};
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
            <#if each.parameters?? && each.parameters?size gt 0>
                <#list each.parameters as param>
                sb.append("\"${param.name}\":");
                    <#if param.array>
                    sb.append("[");
                    for(int i=0;i<${param.name}.length;i++){
                        <#if param.quotation>
                        sb.append("\"");
                        </#if>
                    sb.append(${param.name}[i]);
                        <#if param.quotation>
                        sb.append("\"");
                        </#if>
                    if(i < ${param.name}.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    <#else>
                        <#if param.quotation>
                        sb.append("\"");
                        </#if>
                    sb.append(${param.name});
                        <#if param.quotation>
                        sb.append("\"");
                        </#if>
                    </#if>
                    <#if param_has_next>
                    sb.append(",");
                    </#if>
                </#list>
            </#if>
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        </#if>
    </#list>
</#if>

public class JsonParser{

<#macro paramJsonString param><#if param.struct && param.parameters??><#if param.array>\"${param.name}\":[{<#list param.parameters as each><@paramJsonString param=each/><#if each_has_next>,</#if></#list>}]<#else>\"${param.name}\":{<#list param.parameters as each><@paramJsonString param=each/><#if each_has_next>,</#if></#list>}</#if><#elseif param.type?lower_case == 'string'><#if param.array>\"${param.name}\":[\"${param.name}(${param.type})\"]<#else>\"${param.name}\":\"${param.name}(${param.type})\"</#if></#if></#macro>
public String testJson(){
return "{<@paramJsonString param=inModel/>}";
}

//todo need to product step one
public void execute(${inModel.type} ${inModel.name}) {
//todo
}

//todo need to product step two
public ${inModel.type} parseJson(JSONObject sourceJson) {
<#if inModel.struct>
return ${inModel.type}.parse(sourceJson);
<#else>
return sourceJson.get${inModel.type}("${inModel.name}");
</#if>
}

<#if inModel.array>
public ${inModel.type}[] parseJsonArray(JSONArray sourceJsonArray) {
    ${inModel.type}[] ${inModel.name}s = new ${inModel.type}[sourceJsonArray.size()];
for(int i=0;i
<sourceJsonArray.size();i++){
JSONObject ${inModel.name}Json = sourceJsonArray.getJSONObject(i);
    ${inModel.name}s[i] = ${inModel.type}.parse(${inModel.name}Json);
//todo
}
return ${inModel.name}s;
}
</#if>

public String getArrayString(Object[] array) {
StringBuffer sb = new StringBuffer("[");
for (int i = 0; i < array.length; i++) {
sb.append(array[i].toString());
if (i < array.length - 1) {
sb.append(",");
}
}
sb.append("]");
return sb.toString();
}

@Test
public void parseJsonTest(){
System.out.println("source is : ");
<#if inModel.struct>
    <#if inModel.array>
        ${inModel.type}[] ${inModel.name} = new ${inModel.type}[]{new ${inModel.type}()};
String jsonString = getArrayString(${inModel.name});
    <#else>
        ${inModel.type} ${inModel.name} = new ${inModel.type}();
String jsonString = ${inModel.name}.toString();
    </#if>
//String jsonString = testJson();
System.out.println(jsonString);
    <#if inModel.array>
JSONArray json = JSONArray.parseArray(jsonString);
    <#else>
JSONObject json = JSONObject.parseObject(jsonString);
    </#if>
System.out.println("source json is : ");
System.out.println(json);
System.out.println("parse result is : ");
    <#if inModel.array>
        ${inModel.type}[] ${inModel.name}Result = parseJsonArray(json);
String resultString = getArrayString(${inModel.name}Result);
    <#else>
        ${inModel.type} ${inModel.name}Result = parseJson(json.getJSONObject("${inModel.name}"));
String resultString = ${inModel.name}Result.toJsonString();
    </#if>
System.out.println(resultString);
Assert.assertEquals(jsonString,resultString);
<#else>
    <#if inModel.array>
        ${inModel.type}[] ${inModel.name} = new ${inModel.type}[]{<#if inModel.string>"${inModel.name}(${inModel.type}"<#else>0</#if>);
    <#else>
        ${inModel.type} ${inModel.name} = <#if inModel.string>"${inModel.name}(${inModel.type}"<#else>0</#if>;
        System.out.println("{${inModel.name}:"+ ${inModel.name}+"}");
    </#if>
</#if>
}

}