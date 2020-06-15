$(document).ready(function() {
	$('table.datatable').datatable({
	     data: {
	        cols: [
{width:110, text: '文件目录', type: 'string'},
{width: 150, text: '意义', type: 'string'}
	        ],
	        rows: [
	            {checked: false, data: ["src/main/webapp/WEB-INF/views", "存放显示用JSP（按照业务分文件夹）"]},
	                          {checked: false, data: ["src/main/webapp/resources", "存放前台页面使用资源（包含JS,CSS,IMAGES等）"]},
	                          {checked: false, data: ["src/main/java/com/tf/base", "存放后台逻辑"]},
	                          {checked: false, data: ["src/main/java/com/tf/base/index/contorller", "业务路由信息"]},
	                          {checked: false, data: ["src/main/java/com/tf/base/index/blogic", "业务逻辑信息"]},
	                          {checked: false, data: ["src/main/java/com/tf/base/index/domain", "业务BEAN"]},
	                          {checked: false, data: ["src/main/java/com/tf/base/index/persistence", "业务持久化接口"]},
	                          {checked: false, data: ["src/main/java/com/tf/base/index/service", "业务内共通服务"]},
	                          {checked: false, data: ["src/main/java/com/tf/base/common", "系统级共通服务"]},
	                          {checked: false, data: ["src/main/resources", "后台逻辑对应资源（包括系统properties、数据库处理文件）"]}
	        ]
	    }
	});
	
	
})
