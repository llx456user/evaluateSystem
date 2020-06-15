//****************************************************************************//
//	Copyright
///	@file		support.cpp
///	@brief
///	@author
///	@date
///	@note		该文件内容为模型的固定支撑函数，请勿修改
//****************************************************************************//

#include "TemplateProject.h"


//DLL固定接口函数
TEMPLATEPROJECT_API char* modelfunc(const char* inputFilePath,const char* outFilePath)
{
	int ret = DLL_ERROR;
	ifstream fInParamer(inputFilePath);
    	string temp;
    	while (getline(fInParamer, temp)) //按行读取字符串
    	{
    	}
    fInParamer.close();

	tIn* pIn = NULL;
	tOut* pOut = NULL;
	char* strOut = NULL;
    char* errorMsg = NULL;
    const char* strIn = temp.c_str() ;
    temp = "";
	if (NULL != strIn)
	{
		//输入字符串解析为入参结构体
		pIn = parseString(strIn);
		if (NULL != pIn)
		{
			//出参结构体空间申请
			pOut = (tOut*)malloc(sizeof(tOut));
			if (NULL != pOut)
			{
				//DLL功能函数调用
                ret = dllMain(pIn, pOut,errorMsg);
				if (DLL_OK == ret)
				{
					//出参结构体转换为输出字符串
					strOut = createJSON(pOut);
					//进行输出到文件处理
                    std::ofstream fOutParamer(outFilePath);
                    fOutParamer << strOut;
                    fOutParamer.close();
                    //清空内存
                    if (strOut != NULL) {
                    	 free(strOut);
                    }

				}

				//出参结构体空间释放
				freeOutMember(pOut);
				free(pOut);
				pOut = NULL;
			}

			//入参结构体空间释放
			freeInMember(pIn);
			free(pIn);
			pIn = NULL;
		}
	}

	if (DLL_OK != ret)
	{
		strOut = errorJSON(ret,errorMsg);
	}else{
      cJSON* okCodeJson = cJSON_CreateNumber(result);
		cJSON* okStringJson = cJSON_CreateString("正确");
		cJSON* okJson = cJSON_CreateObject();
		cJSON_AddItemToObject(okJson, "code", okCodeJson);
		cJSON_AddItemToObject(okJson, "message", okStringJson);
		strOut = cJSON_Print(okJson);
	}
	return strOut;
}

tIn* parseString(const char* strIn)
{
	cJSON *root = NULL;
	char *para = NULL;
	tIn* pIn = NULL;

	if (NULL == strIn)
	{
		return NULL;
	}

	root = cJSON_Parse(strIn);
	if (NULL != root)
	{
		pIn = (tIn*)malloc(sizeof(tIn));
		if (NULL != pIn)
		{
			PARSE_FUNC_AREA
		}

		cJSON_Delete(root);
		root = NULL;
	}

	return pIn;
}

char* createJSON(tOut* pOut)
{
	cJSON *root = NULL;
	char *strOut = NULL;

	if (NULL != pOut)
	{
		root = cJSON_CreateObject();
		if (NULL != root)
		{
			CREATE_FUNC_AREA
			strOut = cJSON_Print(root);

			cJSON_Delete(root);
			root = NULL;
		}
	}

	return strOut;
}

char* errorJSON(int ret,const  char* errorMsg)
{
	cJSON *root = NULL;
	char *strOut = NULL;

	root = cJSON_CreateObject();
	if (NULL != root)
	{

		if (errorMsg == NULL) {
			errorMsg = "模型函数没有给出错误消息，请和模型函数编写者联系！";
		}

	   cJSON_AddStringToObject(root, "DLL_ERROR", errorMsg);

		strOut = cJSON_Print(root);

		cJSON_Delete(root);
		root = NULL;
	}

	return strOut;
}

void freeInMember(tIn* pIn)
{
	if (NULL != pIn)
	{
		FREE_IN_MEMBER
	}
}

void freeOutMember(tOut* pOut)
{
	if (NULL != pOut)
	{
		FREE_OUT_MEMBER
	}
}

//GB2312到UTF-8的转换
char* G2U(const char* gb2312)
{
	int len = MultiByteToWideChar(CP_ACP, 0, gb2312, -1, NULL, 0);
	wchar_t* wstr = new wchar_t[len + 1];
	memset(wstr, 0, len + 1);
	MultiByteToWideChar(CP_ACP, 0, gb2312, -1, wstr, len);
	len = WideCharToMultiByte(CP_UTF8, 0, wstr, -1, NULL, 0, NULL, NULL);
	char* str = new char[len + 1];
	memset(str, 0, len + 1);
	WideCharToMultiByte(CP_UTF8, 0, wstr, -1, str, len, NULL, NULL);
	if (wstr) delete[] wstr;
	return str;
}

//UTF-8到GB2312的转换
char* U2G(const char* utf8)
{
	int len = MultiByteToWideChar(CP_UTF8, 0, utf8, -1, NULL, 0);
	wchar_t* wstr = new wchar_t[len + 1];
	memset(wstr, 0, len + 1);
	MultiByteToWideChar(CP_UTF8, 0, utf8, -1, wstr, len);
	len = WideCharToMultiByte(CP_ACP, 0, wstr, -1, NULL, 0, NULL, NULL);
	char* str = new char[len + 1];
	memset(str, 0, len + 1);
	WideCharToMultiByte(CP_ACP, 0, wstr, -1, str, len, NULL, NULL);
	if (wstr) delete[] wstr;
	return str;
}
