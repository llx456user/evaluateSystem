package com.tf.base.common.constants;

public class CommonConstants {
	
	public static String ROOT_DIR = "";

	/**
	 * 价格信息“|”分隔符
	 */
	public static final String PRICE_INFO_DIVISION_VER = "\\|";
	
	/**
	 * 价格信息“#”分隔符
	 */
	public static final String PRICE_INFO_DIVISION_JH = "#";
	
	/**
	 * 价格信息“^”分隔符
	 */
	public static final String PRICE_INFO_DIVISION_SJH = "\\^";
	
	/**
	 * amaduce接口数据来源标识
	 */
	public static final String INTERFACE_DATA_SOURCE_AMAD_F = "AMAD_F";
	
	/**
	 * 政策类型——私有
	 */
	public static final String POLICY_TYPE_PRIVATE = "0";
	
	/**
	 * 政策类型——公有
	 */
	public static final String POLICY_TYPE_PUBLIC = "1";
	
	
	/**
	 * 字符编码
	 */
	public static final String CHARACTER_ENCODE = "UTF-8";

	/**
	 * HTML格式内容字符编码
	 */
	public static final String CONTENT_TYPE = "text/html; charset=utf-8";
	
	/**
	 * 财务部的部门ID
	 */
	public static final String FINANCE_DEPT_ID  = "300016";

	public static final String DEL_FLAG_YES = "1";//删除
	public static final String DEL_FLAG_NO = "0";//正常
	
	public static final String STATUS_FLAG_VALID = "1";//有效
	public static final String STATUS_FLAG_INVALID = "2";//无效
	
	public static final String GENDER = "GENDER";
	public static final String POST_LEVEL = "POST_LEVEL";
	public static final String LEADER_LEVEL = "LEADER_LEVEL";
	public static final String STATUS = "DEPT_STATUS";
	
	public static final String MODULE_DATABASE_BACKUP = "DATABASE_BACKUP";
	public static final String SWITCH_ON = "ON";
	public static final String SWITCH_OFF = "OFF";
	
	public static final String YES_NO = "YES_NO";
	
	public static final String HAS_NOT = "HAS_NOT";
	
	public static final String NATIONALITY = "NATIONALITY";
	
	public static final String ADDRESS_LEVEL = "ADDRESS_LEVEL";
	/**
	 * 模块：社会组织-组织信息  1
	 */
	public static final String SYSTEM_MODEL_SOCIAL_ORG = "1";
	
	/* 非公 */
	public static final String ENTERPRISE_TYPE = "ENTERPRISE_TYPE";
	public static final String ENTERPRISE_TYPE_NATIONALITY = "ENTERPRISE_TYPE_NATIONALITY";
	public static final String BUSINESS_TYPE = "BUSINESS_TYPE";
	public static final String ENTERPRISE_BELOCATED_ADDRESS = "ENTERPRISE_BELOCATED_ADDRESS";
	public static final String ZONE_LEVEL = "ZONE_LEVEL";
	public static final String UNPUBLIC_ORG_STATUS = "UNPUBLIC_ORG_STATUS";
	public static final String UNPUBLIC_ORG_OTHER_CONDITION = "UNPUBLIC_ORG_OTHER_CONDITION";
	/* 社会 */
	public static final String ORG_NATURE = "ORG_NATURE";
	public static final String ORG_CATEGORY = "ORG_CATEGORY";
	public static final String PARTY_DEPUTY_TYPE = "PARTY_DEPUTY_TYPE";
	public static final String SOCIAL_ORG_STATUS = "SOCIAL_ORG_STATUS";
	public static final String SOCIAL_ORG_OTHER_CONDITION = "SOCIAL_ORG_OTHER_CONDITION";
	/* 党员 */
	public static final String PARTY_MBR_CHANGE_TYPE = "PARTY_MBR_CHANGE_TYPE";
	public static final String FINAL_EDUCATION = "FINAL_EDUCATION";
	public static final String UNPUBLIC_PM_OTHER_CONDITION = "UNPUBLIC_PM_OTHER_CONDITION";
	public static final String SOCIAL_PM_OTHER_CONDITION = "SOCIAL_PM_OTHER_CONDITION";
	/* 非公-党组织  社会 -党组织*/
	public static final String PARTY_ORG_CLASS = "PARTY_ORG_CLASS";
	public static final String PARTY_ORG_FORM = "PARTY_ORG_FORM";
	public static final String NO_ESTABLISH_PARTY_ORG_REASON = "NO_ESTABLISH_PARTY_ORG_REASON";
	public static final String SOURCE = "SOURCE";
	public static final String PARTY_SECRETARIES_AND_COMMISSION_TYPE = "PARTY_SECRETARIES_AND_COMMISSION_TYPE";
	public static final String ANNUAL_SURVEY = "ANNUAL_SURVEY";
	public static final String PARTY_ORG_STATUS = "PARTY_ORG_STATUS";
	public static final String HEIGHT_PARTY_ORG = "HEIGHT_PARTY_ORG";
	public static final String OTHER_INFO_MANAGER = "OTHER_INFO_MANAGER";
	public static final String PARTY_ORG_OPERATOR = "PARTY_ORG_OPERATOR";
	/* 智能预警*/
	public static final String PERSONNEL_CATEGORY = "PERSONNEL_CATEGORY";
	public static final String REMINDER_CYCLE = "REMINDER_CYCLE";
	public static final String WARN_SWITCH = "WARN_SWITCH";
	public static final String TIME_UNIT = "TIME_UNIT";
	public static final String WARN_TYPE = "WARN_TYPE";
	/* 相关工作*/
	public static final String HONOR_PARTY_ORG_TYPE = "HONOR_PARTY_ORG_TYPE";
	public static final String HONOR_LEVER  = "HONOR_LEVER";
	public static final String HONOR_ORG_TYPE = "HONOR_ORG_TYPE";
	public static final String FORM_DESIGN = "FORM_DESIGN";
	public static final String NOTICE_STATUS = "NOTICE_STATUS";
	public static final String NOTICE_EVALUAT = "NOTICE_EVALUAT";
	public static final String MEET_STATUS = "MEET_STATUS";
	
	public enum LOG_OPERATION_TYPE {
		
		CREATE("1"),
		VIEW("2"),
		MODIFY("3"),
		DELETE("4"),
		LOGIN("0"),
		LOGOUT("-1");
		
		private String value;  
		
		LOG_OPERATION_TYPE(String value) {  
            this.value = value;  
        }  
          
        public String toString() {  
            return value;  
        }  
	}
	
	public  enum LOG_MODULE {
		
		BASEINFO("0"),
		PRICEINFO("1"),
		DOWNGRADEINFO("3"),
		TGINFO("4"),
		OTHERINFO("5");
		private String value;  
		
		LOG_MODULE(String value) {  
            this.value = value;  
        }  
          
        public String toString() {  
            return value;  
        }  
	}
	
	public enum WARNING_SWITCH {
		ON("1"), OFF("0");

		private String value;

		WARNING_SWITCH(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
	}
	
}
