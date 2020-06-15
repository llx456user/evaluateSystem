<#macro definationParameter param>
    <#if param.struct>
        <#if param.array>
        struct ${param.cppType} **${param.name};
        int ${param.name}_length;
        <#else>
        struct ${param.cppType} *${param.name};
        </#if>
    <#else>
        <#if param.array>
            <#if param.string>
            ${param.cppType} **${param.name};
            <#--const ${param.cppType} **const_${param.name} = ${param.name};-->
            <#else>
              ${param.cppType} *${param.name};
            </#if>
        int ${param.name}_length;
        <#else>
            <#if param.string>
           ${param.cppType} *${param.name};
           <#--const  ${param.cppType} *const_${param.name} = ${param.name};-->
            <#else>
            ${param.cppType} ${param.name};
            </#if>
        </#if>
    </#if>
</#macro>

<#macro struct param>
struct ${param.cppType}{
<#if param.parameters??>
    <#list param.parameters as each>
        <@definationParameter param=each/>
    </#list>
</#if>
};
</#macro>

<#macro structList list>
<#if list ??>
    <#list list as each>
        <@struct param=each/>
    </#list>
</#if>
</#macro>