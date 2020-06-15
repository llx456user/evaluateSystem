<#import "cpp_common.ftl" as c/>
#include "${header}.h"
#include <stdlib.h>
#include <stdio.h>


//****************************************************************************//
//	function		dllMain
///	@note			DLL模型实际功能函数
///	@attention
///	@attention		模型入参空间已经全部申请
///	@attention		模型出参pOut的空间已经在接口函数中申请
///	@attention		模型出参pOut的成员需要申请空间时，请在功能实现时申请
///	@attention
///	@param[in]		pIn			模型入参结构体
///	@param[out]		pOut		模型出参结构体
/// @param[errorMsg] errorMsg   模型计算错误返回消息
///	@retval			DLL_OK		模型正常返回
///	@retval			DLL_ERROR   模型异常返回
///	@author
///	@date
//****************************************************************************//
<#if arrayFunction?? && arrayFunction>
int dllMain(InStruct *in, OutStruct *out, char* errorMsg){
    int ret = DLL_OK;
    if (<@c.checkPointer name="in"/> && <@c.checkPointer name="out"/>) {
    }
    return ret;
}
<#else>
int dllMain(<@c.type param=inModel/> input_${inModel.name},<@c.type param=outModel/> output_${outModel.name}, char* errorMsg)
    {
    int ret = DLL_OK;
    if (<@c.checkPointer name="input_"+inModel.name/> && <@c.checkPointer name="output_"+outModel.name/>) {
    }
    return ret;
}
</#if>
