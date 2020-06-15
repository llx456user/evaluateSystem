#include <windows.h>
#include <stdlib.h>
#include "cJSON.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;


#define TEMPLATEPROJECT_API __declspec(dllexport)


/**************************************************/
/*                      define                    */
/**************************************************/
#define DLL_ERROR		-1
#define DLL_OK			0
#define PIN               0
#define CIRCLE            1
#define RACT              2
#define ROUNDRACT         3
#define ARROW             4

/**************************************************/
/*                     struct                     */
/**************************************************/
STRUCT_AREA


/**************************************************/
/*                     function                   */
/**************************************************/
extern "C"
{
	//DLL固定接口函数
	TEMPLATEPROJECT_API char* modelfunc(const char* strIn);
}

//DLL模型实际功能函数
int dllMain(tIn* pIn, tOut* pOut,char* errorMsg);
//DLL输入字符串解析函数
tIn* parseString(const char* strIn);
//DLL输出字符串生成函数
char* createJSON(tOut* pOut);
//DLL异常时输出字符串生成函数
char* errorJSON(int ret,const char* errorMsg);
//DLL入参空间释放函数
void freeInMember(tIn* pIn);
//DLL出参空间释放函数
void freeOutMember(tOut* pOut);
//GB2312到UTF-8的转换
char* G2U(const char* gb2312);
//UTF-8到GB2312的转换
char* U2G(const char* utf8);
