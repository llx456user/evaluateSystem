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
	//DLL�̶��ӿں���
	TEMPLATEPROJECT_API char* modelfunc(const char* strIn);
}

//DLLģ��ʵ�ʹ��ܺ���
int dllMain(tIn* pIn, tOut* pOut,char* errorMsg);
//DLL�����ַ�����������
tIn* parseString(const char* strIn);
//DLL����ַ������ɺ���
char* createJSON(tOut* pOut);
//DLL�쳣ʱ����ַ������ɺ���
char* errorJSON(int ret,const char* errorMsg);
//DLL��οռ��ͷź���
void freeInMember(tIn* pIn);
//DLL���οռ��ͷź���
void freeOutMember(tOut* pOut);
//GB2312��UTF-8��ת��
char* G2U(const char* gb2312);
//UTF-8��GB2312��ת��
char* U2G(const char* utf8);
