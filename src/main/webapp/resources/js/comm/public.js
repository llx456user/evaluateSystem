Date.prototype.format = function(format) {
        var o = {
            "M+": this.getMonth() + 1, // month
            "d+": this.getDate(), // day
            "h+": this.getHours(), // hour
            "m+": this.getMinutes(), // minute
            "s+": this.getSeconds(), // second
            "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
            "S": this.getMilliseconds()
            // millisecond
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        return format;
};

var Common = {

    //EasyUI用DataGrid用日期格式化
    TimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        if(jQuery.isNumeric(value)){
        	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }
        var val = dateValue.format("yyyy-MM-dd hh:mm:ss");
        return val.substr(11, 5);
    },
    DateTimeFormatter: function (value, rec, index) {
    	
        if (value == undefined || value == "" || value =="null") {
            return "";
        }
        if(jQuery.isNumeric(value)){
        	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-MM-dd hh:mm:ss");
    },

    //EasyUI用DataGrid用日期格式化
    DateFormatter: function (value, rec, index) {
    	if (value == undefined || value == "" || value =="null") {
            return "";
        }
        if(jQuery.isNumeric(value)){
        	return new Date(value).format("yyyy-MM-dd");
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-MM-dd");
    }
};
/**
 * 设置页面列长度
 */
//1.订单信息列表——各列设置
var orderInfo=
{
	orderNoAndTime:{field :"orderNoAndTime",title :"渠道订单号<br/>预订时间",width :"12%", align:"center"},//渠道订单号/预订时间
	flightOption:{field :"flightOption",title :"航程类型",width :"6%", align:"center"},//航程类型
	pnr:{field :"pnr",title :"PNR",width :"6%", align:"center"},//pnr
	passenger:{field :"passenger",title :"乘机人",width :"15%", align:"center"},//乘机人
	sailInfo:{field :"sailInfo",title :"航程信息",width :"30%", align:"center"},//航程信息
	priceAmount:{field :"priceAmount",title :"订单价格",width :"8%", align:"center"},//订单价格
	orderNo:{field : "orderNo",title : "内部订单号",align:"center",hidden:true },//内部订单号
	orderFrom:{field : "orderFrom",title : "渠道来源",align:"center",hidden:true },//渠道来源
	orderFrom_show:{field : "orderFrom",title : "供应商",width : "6%",align:"center" },
	id:{field : "id",title : "ID", hidden:true},//id
	status:{field : "status",title : "status", hidden:true}//订单状态
} ; 
   
//2.设置乘客信息列表——各列设置
var passengerInfo=
{
	    id: {field : "id",title : "ID", hidden:true},
		orderid:{field : "orderid",title : "orderid", hidden:true},
		name:{field : "name",title : "拼音姓名",width : "200", align:"center"} ,//拼音名字
		gender:{field : "gender",title : "性别",width : "50",align:"center" },//性别
		birthday:{field : "birthday",title : "出生日期",width : "100",align:"center",formatter:formatterdate } ,//出生日期
		pastype:{field : "pastype",title : "顾客类型",width : "100",align:"center" } ,//顾客类型
		cardtype:{field : "cardtype",title : "证件类型",width : "100",align:"center" } ,//证件类型
		cardissueplace:{field : "cardissueplace",title : "签发国家",width : "100",align:"center" } ,//签发国家
		cardnum:{field : "cardnum",title : '证件号码',width : "150",align:"center"},//证件号
		cardexpired:{field : "cardexpired",title : "有效期限",width : "100",align:"center" },//有效期
		ticketno_editor:{field : "ticketno",title : "票号",width : "250",align:"center",
			editor: { type: "validatebox", options: { required: true} }
        }//票号

} ; 

function formatterdate(val, row) {
	if (val != null) {
	var date = new Date(val);
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
	+ date.getDate();
	}
}
// 按照yyyy-MM-dd格式化日期
function dateformatter(date){
	return date.format("yyyy-MM-dd");
}

// 按照yyyy-MM-dd hh:mm:ss格式化日期
function datetimeformatter(date){
	return date.format("yyyy-MM-dd hh:mm:ss");
}

//禁用form表单里所有元素
function disableForm(formId,isDisabled) {  
    
    var attr="disable";  
    if(!isDisabled){  
       attr="enable";  
    }  
    $("form[id='"+formId+"'] :text").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] textarea").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] select").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] :radio").attr("disabled",isDisabled);  
    $("form[id='"+formId+"'] :checkbox").attr("disabled",isDisabled);  
      
    //禁用jquery easyui中的下拉选（使用input生成的combox）  
  
    $("#" + formId + " input[class='combobox-f combo-f']").each(function () {  
        if (this.id) {alert("input"+this.id);  
            $("#" + this.id).combobox(attr);  
        }  
    });  
      
    //禁用jquery easyui中的下拉选（使用select生成的combox）  
    $("#" + formId + " select[class='combobox-f combo-f']").each(function () {  
        if (this.id) {  
        alert(this.id);  
            $("#" + this.id).combobox(attr);  
        }  
    });  
      
    //禁用jquery easyui中的日期组件dataBox  
    $("#" + formId + " input[class='datebox-f combo-f']").each(function () {  
        if (this.id) {  
        alert(this.id)  
            $("#" + this.id).datebox(attr);  
        }  
    });  
} 

/**
 * 对比data，overlistobj disabled checkbox
 * @param data datagrid的数据
 * @param overlistobj  json数组
 */
function checkboxDisabled(data,overlistobj,isChange,range){
	
//	var k=0;
	if (data.rows.length > 0 && overlistobj.length>0) {
        //循环判断操作为新增的不能选择
        for (var i = 0; i < data.rows.length; i++) {
      	  for(var j=0;j<overlistobj.length;j++){
      		//根据name让某些行不可选
            if (data.rows[i].name == overlistobj[j].name && data.rows[i].cardNum == overlistobj[j].cardNum) {
            	if( overlistobj[j].finalOrderStatus=='227' && !isChange){//如果是改期完成 && 在退票申请界面
            		continue;
            	}
                $("#"+range+" input[type='checkbox']")[i].disabled = true;
//                k++;
            }
      	  }
        }
//        if(k>0){
//  		  $("#"+range+" input[type='checkbox']")[0].disabled = true;
//  	  }
    }
}
function setCheckboxDisabled(data,range){
	if (data.rows.length > 0){
		for (var i = 0; i < data.rows.length; i++) {
			$("#"+range+" input[type='checkbox']")[i].disabled = true;
		}
	}
}
/**
 * 屏蔽datagrid行选中checkbox  disable 
 * @param datagrid datagrid对象
 */
function allCheckboxDisabled(datagrid,range){
	//加载完毕后获取所有的checkbox遍历
    $("#"+range+" input[type='checkbox']").each(function(index, el){
        //如果当前的复选框不可选，则不让其选中
        if (el.disabled == true) {
      	  $(datagrid).datagrid('unselectRow', index - 1);
        }
    })
}

/**
 * 如果无数据值展示横线【-】
 * @param value
 * @param row
 * @param index
 * @returns
 */
function ifNullShowHeng(value,row,index){
	
	if(value==null || value==undefined){
		return "-";
	}
	if(!isNaN(value)){
		return value;
	}
	if(value==""){
		return "-";
	}
	return value;
}

//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
	  var r1,r2,m;
	  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	  m=Math.pow(10,Math.max(r1,r2))
	  return (arg1*m+arg2*m)/m
}


//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2) {
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){}
	try{m+=s2.split(".")[1].length}catch(e){}
	return  Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

//格式化金额  12345格式化为12,345.00 
function fmoney(s, n) { 
	var head="";
	if(s<0){
		head="-";
		s=(s+"").replace("-", "");
	}
	n = n > 0 && n <= 20 ? n : 2; 
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
	t = ""; 
	for (i = 0; i < l.length; i++) { 
	t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
	} 
	return head+t.split("").reverse().join("") + "." + r; 
	}
