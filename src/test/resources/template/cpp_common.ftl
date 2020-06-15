<#macro checkPointer name>NULL != ${name} && !IS_ERROR(${name})</#macro>
<#macro checkStaticPointer name index>${index} < ${name}_length</#macro>
<#macro checkParameter param><#if param.struct || param.array><@checkPointer name=param.name/><#else>NULL != ${param.name}</#if></#macro>
<#macro type param><#if param.struct>struct </#if>${param.cppType}<#if param.struct || param.string>*</#if><#if param.array>*</#if></#macro>
<#macro typeNoArray param><#if param.struct>struct </#if>${param.cppType}<#if param.struct || param.string>*</#if></#macro>
<#macro init param><#if param.struct || param.string>(${param.cppType}*<#if param.array>*</#if>)malloc(sizeof(${param.cppType}<#if param.array>*</#if>))<#else>0</#if></#macro>