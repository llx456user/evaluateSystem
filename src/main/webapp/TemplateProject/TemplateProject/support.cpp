//****************************************************************************//
//	Copyright
///	@file		support.cpp
///	@brief
///	@author
///	@date
///	@note		���ļ�����Ϊģ�͵Ĺ̶�֧�ź����������޸�
//****************************************************************************//

#include "TemplateProject.h"


//DLL�̶��ӿں���
TEMPLATEPROJECT_API char* modelfunc(const char* inputFilePath,const char* outFilePath)
{
	int ret = DLL_ERROR;
	ifstream fInParamer(inputFilePath);
    	string temp;
    	while (getline(fInParamer, temp)) //���ж�ȡ�ַ���
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
		//�����ַ�������Ϊ��νṹ��
		pIn = parseString(strIn);
		if (NULL != pIn)
		{
			//���νṹ��ռ�����
			pOut = (tOut*)malloc(sizeof(tOut));
			if (NULL != pOut)
			{
				//DLL���ܺ�������
                ret = dllMain(pIn, pOut,errorMsg);
				if (DLL_OK == ret)
				{
					//���νṹ��ת��Ϊ����ַ���
					strOut = createJSON(pOut);
					//����������ļ�����
                    std::ofstream fOutParamer(outFilePath);
                    fOutParamer << strOut;
                    fOutParamer.close();
                    //����ڴ�
                    if (strOut != NULL) {
                    	 free(strOut);
                    }

				}

				//���νṹ��ռ��ͷ�
				freeOutMember(pOut);
				free(pOut);
				pOut = NULL;
			}

			//��νṹ��ռ��ͷ�
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
		cJSON* okStringJson = cJSON_CreateString("��ȷ");
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
			errorMsg = "ģ�ͺ���û�и���������Ϣ�����ģ�ͺ�����д����ϵ��";
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

//GB2312��UTF-8��ת��
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

//UTF-8��GB2312��ת��
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
