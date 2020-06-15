#include "TemplateProject.h"


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
int dllMain(tIn* pIn, tOut* pOut,char* errorMsg)
{
	int ret = DLL_OK;
	return ret;
}