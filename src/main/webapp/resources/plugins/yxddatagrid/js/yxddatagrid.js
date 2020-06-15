(function($) {
	
	var _settings ;
	
	var methods = {
		init: function(options) {
			var _self = this;
			return this.each(function() {
				var $this = $(this);

				var settings = $this.data('Datagrid.options');
				if(typeof(settings) == 'undefined') {

					var defaults = {
					}
					settings = $.extend({}, defaults, options);
					$this.data('Datagrid.options', settings);
				} else {
					settings = $.extend({}, settings, options);
				}
				
				_settings = settings;
				//console.log(_settings);
			});
		},
		load: function(options) {
			
			return this.each(function() {
				var $this = $(this);
				
				///console.log(options);
				var params = options.queryParams || {};
				if(!options.queryParams)
					options.queryParams = params;
				
				if(!options.other.currentPage)
					options.other.currentPage = 1;
				if(!options.other.pageSize)
					options.other.pageSize = 10;
				params.page = options.other.currentPage || 1;
				params.rows = options.other.pageSize || 10;
				
				var defaultsTables = {
						checkable: false,
					    sortable: false,
					    //checkedClass: "checked",
					    colHover:true,
					    hoverClass:'hover',
					    minFixedLeftWidth: 300,
					    storage:false
				};
				var tables = $.extend({}, defaultsTables, options.tables);
				options.tables = tables;
				
				$this.Datagrid('init',options);
				var openProgress = true;
				if(options.other.openProgress != undefined){
					if(!options.other.openProgress)
						openProgress = false;
				}
				//依赖comm/public.js
				var opIdx;
				if(openProgress) opIdx = openProgressExt("加载中...");
				$.ajax({
					url:options.url,
				    data:params,
				    type:'post',
				    cache:false,
				    success:function(data){
				    	//依赖comm/public.js
				    	if(openProgress)closeProgressExt(opIdx);
				    	
				    	options.data = data;
			    		$this.Datagrid('renderTable',options);
				    }
				}) 
			});
		},
		renderTable:function(options){
			var data = options.data;
			var $this = $(this);
			$this.datatable(options.tables).off("render.zui.datatable").on("render.zui.datatable",function(event){
				//console.log(event);
				var rowDatas = $this.data('Datagrid.rowDatas');
				if(rowDatas){
					$.each(rowDatas,function(k,rowData){
						if (options.rowStyler && options.rowStyler != "null") {
							if(options.rowStyler && typeof(options.rowStyler) === "function"){
								$.each(data.rows,function(i,row){
									var r = options.rowStyler(i,row);
									$($this.context).next().find("table tbody tr").filter('[data-id="' + rowData.id + '"]').attr("style",r);
								});
								
							}
						}
					});
				}
				
				if(options.onLoadSuccess && typeof(options.onLoadSuccess) === "function")
					options.onLoadSuccess(data);
				
				
			});
			$this.datatable(options.tables).on("sort.zui.datatable", function(event) {
				//console.log(event);
				// console.log("表格已重新排序！");
				var i = 0 ;
				$($this.context).next().find("table tbody tr").each(function(){
					i++;
					$(this).find("td.myColW-break:eq(0)").text(i);
					
				});
			});;
			var cols = options.other.data.cols;
			var rowDatas = [];
			var k = 0;
			
			/*//没有被用到的数据依然留存
			$.each(data.rows,function(i,o){
				
				var fieldNames = [];
				$.each(cols,function(j,field){
					fieldNames.push(field.field);
				});
				for(var key in o){
					var isContain = $.inArray(key,fieldNames) == -1 ? false : true;
					if(!isContain){
						cols.push({field :key,hidden:true});
					}
					//console.log(key,isContain);
				}
				//console.log('------------------------------');
			});*/
			
			$.each(cols,function(i,col){
				if(col.hidden != undefined){
					cols[i].colClass = col.hidden || col.hidden == "true" ?  "myColW-hidden" : "myColW-break";
				}
				if(col.title)
					cols[i].text = col.title;
				if(!cols[i].colClass)
					cols[i].colClass = "myColW-break";
			});
			//console.log(data.rows)
			$.each(data.rows,function(i,o){
				var tmpdata = [];
				var hasIndex = false;
				$.each(cols,function(j,field){
					
					if (field.formatter && field.formatter != "null") {
						if(typeof field.formatter === "function") {
							
							var r = field.formatter(o[field.field] == null ? '' : o[field.field],o,i);
							tmpdata.push(r);
						}
					}else{
						if(field.field == '#'){
							hasIndex = true;
						}else{
							tmpdata.push(o[field.field] == null ? '' : o[field.field]);
						}
					}
					
				});
				
				if(hasIndex){
					tmpdata.unshift(i+1);
				}
				
				var row = {id: options.other.currentPage +"_" + i,data: tmpdata};
				rowDatas.push(row);
				
				$this.removeData('Datagrid.rowDatas');
				$this.data('Datagrid.rowDatas',rowDatas);
			})
			$this.datatable('load', {
		    	cols: cols,rows: rowDatas
		    });
			var currentPage = options.other.currentPage;
			
			//依赖jquery.page.js
			$(".yxdPageCode").createPage({
				pageCount:parseInt(data.total < options.other.pageSize ? 
						1: (parseInt(data.total) % parseInt(options.other.pageSize) == 0 ?
								(parseInt(data.total) / parseInt(options.other.pageSize)) :
									(parseInt(data.total) / parseInt(options.other.pageSize) + 1))),
				current:parseInt(currentPage),
				backFn:function(p){
					//console.log('当前第' + p + '页');
					options.other.currentPage = p;
					$this.Datagrid('load',options);
				}
			});
		},
		options:function(){
			//console.log(_settings);
			return $(this).data('Datagrid.options') || _settings;
		},
		reload:function(){
			var settings = $(this).data('Datagrid.options') || _settings ;
			if(settings == null)
				throw new Error('settings is null');
			else{
				settings.other.currentPage = 1;
				$(this).Datagrid('load',settings);
			}
		},
		getChecked:function(){
			
			var $this = $(this);
			var settings = $(this).data('Datagrid.options') || _settings ;
			var cols = settings.other.data.cols;
			var rowDatas = $this.data('Datagrid.rowDatas');
			var tmpRowDatas = [];
			var rows = [];
			if(!$this.data('zui.datatable').checks){
				//console.log('selected is null');
				return rows;
			}
			$.each(rowDatas,function(i,rowData){
				$.each($this.data('zui.datatable').checks.checks,function(j,id){
					if(id == rowData.id){
						tmpRowDatas.push(rowData);
					}
				});
			});
			
			$.each(tmpRowDatas,function(i,rowData){
				
				var row = new Object(); 
				$.each(cols,function(j,col){
					if(rowData.data[j] && typeof(rowData.data[j]) === "object")
						row[col.field] = rowData.data[j].text;
					else if(typeof (rowData.data[j]) == "string")
						row[col.field] = rowData.data[j];
					else
						row[col.field] = String(rowData.data[j]);
				});
				rows.push(row);
			});
			
			return rows;
		},
		getRows:function(){
			var $this = $(this);
			var settings = $(this).data('Datagrid.options') || _settings ;
			var cols = settings.other.data.cols;
			var rowDatas = $this.data('Datagrid.rowDatas');
			var rows = [];
			$.each(rowDatas,function(i,rowData){
				
				var row = new Object(); 
				$.each(cols,function(j,col){
					if(rowData.data[j] && typeof(rowData.data[j]) === "object")
						row[col.field] = rowData.data[j].text;
					else if(typeof (rowData.data[j]) == "string")
						row[col.field] = rowData.data[j];
					else
						row[col.field] = String(rowData.data[j]);
				});
				
				rows.push(row);
			});
			return rows;
		},
		selectRow:function(index){
			var $this = $(this);
			return this.each(function() {
				 $($(this).context).next().find("table tbody tr").eq(index).find(".check-btn").click();
			});
			
		},
		beginEdit:function(index){
			var $this = $(this);
			return this.each(function() {
				 var $tr = $($(this).context).next().find(".table tbody tr").filter('[data-index="' + index + '"]');
				 var settings = $this.data('Datagrid.options') || _settings ;
				 var cols = settings.other.data.cols;
				 var colEditorDatetimeClass = [];
				 $.each(cols,function(i,col){
					 if(col.editor && typeof(col.editor) === "object"){
						 
						 var fune = col.editor;
						 var $td = $tr.find("td").filter('[data-index="' + i + '"]');
						 var oc = $td.html();
						 var required = col.editor.options && col.editor.options.required ?col.editor.options.required : false;
						 var requiredClass = required ? "dataRequired":""; 
						 var contentType = '';
						 if(fune.type == "datetimebox"){
							 contentType = '<input type="text" class="'+requiredClass+' form-control form-datetime-empty_'+i+'" id="'+col.field+'_'+index+"_"+i+'" value="'+oc+'"/>';
						 }else if(fune.type == "validatebox"){
							 contentType = '<input type="text" class="'+requiredClass+' form-control" id="'+col.field+'_'+index+"_"+i+'" value="'+oc+'"/>';
						 }else{
							 contentType = '<input type="text" class="'+requiredClass+' form-control" id="'+col.field+'_'+index+"_"+i+'" value="'+oc+'"/>';
						 }
						 $td.html(contentType);
						 colEditorDatetimeClass.push('form-datetime-empty_'+i);
					 }
				 });
				 $.each(colEditorDatetimeClass,function(i,dtclass){
					 $("." + dtclass).datetimepicker({
				  			language:  "zh-CN",
				  			todayBtn:  1,
				  			autoclose: 1,
				  			todayHighlight: 1,
				  			showMeridian: true,
				  			format: "yyyy-mm-dd hh:ii:ss"
				  		});
				 });
				 $this.data("Datagrid.valid",false);
			});
		},
		endEdit:function(index){
			var $this = $(this);
			return this.each(function() {
					 var rowDatas = $this.data('Datagrid.rowDatas');
					 var $tr = $($(this).context).next().find(".table tbody tr").filter('[data-index="' + index + '"]');
					 var settings = $this.data('Datagrid.options') || _settings ;
					 var cols = settings.other.data.cols;
					 var colEditorDatetimeClass = [];
					 //验证
					 var valid_ids = [];
					 $.each(cols,function(i,col){
						 if(col.editor && typeof(col.editor) === "object"){
							 if(col.editor.options && typeof(col.editor.options) === "object"){
								 var required = col.editor.options.required;
								 if(required != undefined && required == true){
									 if($("#" + col.field+"_"+index+"_"+i).val() == ""){
										 valid_ids .push(  col.field+"_"+index+"_"+i);
										 //return false;
									 }
								 }
							 } 
						 }
					 });
					 
					 if(valid_ids.length > 0){
						 $.each(valid_ids,function(i,id){
							$("#" +id).attr("data-toggle","tooltip")
							/*.attr("data-placement","top").attr("title","该项为必填项!").
							attr("data-tip-class","tooltip-danger");*/
							
						 });
						 $('[data-toggle="tooltip"]').tooltip({
							    tipClass: 'tooltip-danger',
							    dataPlacement:'auto',
							    title:"该项为必填项!"
							});
							$('[data-toggle="tooltip"]').tooltip('show') 
							return;
					 }
					 
					 
					 $.each(cols,function(i,col){
						 if(col.editor && typeof(col.editor) === "object"){
							 
							 var fune = col.editor;
							 var $td = $tr.find("td").filter('[data-index="' + i + '"]');
							 var c;
							 if(fune.type == "datetimebox" || fune.type == "validatebox"){
								 c = $td.find("input").val();
							 }else{
								 c = $td.find("input").val();
							 }
							 $td.html(c);
							 
							 //遗留问题 修改后影响原数据
							 rowDatas[index].data[i] = c;
							 //console.log(rowDatas);
							 
						 }
					 });
					 $this.data('Datagrid.rowDatas',rowDatas);
				});
			
		},
		validate:function(){
			var $this = $(this);
			var editorRequired_arr = this.next().find(".table tbody tr td .dataRequired");
			var len = editorRequired_arr.length;
			var t ;
			if(len > 0){
				var valid = false;
				$.each(editorRequired_arr,function(){
					
					var val = $(this).val();
					if(val == null || val == ""){
						t = $(this);
						valid = true;
						return false;
					}
				});
				if(valid){
					t.attr("data-toggle","tooltip");
					t.focus();
					t.tooltip({
					    tipClass: 'tooltip-danger',
					    dataPlacement:'auto',
					    title:"该项为必填项!"
					});
					t.tooltip('show') ;
					return false;
				}
				
			}
		
			return true;
		}
		
	};
 
	$.fn.Datagrid = function() {
		var method = arguments[0];
 
		if(methods[method]) {
 
			method = methods[method];
			arguments = Array.prototype.slice.call(arguments, 1);
		} else if( typeof(method) == 'object' || !method ) {
			method = methods.init;
		} else {
			$.error( 'Method ' +  method + ' does not exist on jQuery.Datagrid' );
			return this;
		}
 
		return method.call(this,arguments[0]);
 
	}
 
})(jQuery);

if(window.sessionStorage && !window.sessionStorage.myDog){
	if(window.console){
		
		console.log('          .----.  \n'
				+ '       _.’ __    `.   \n'
				+'    .--(#)(##)---/#\  \n'
				+'  .’ @          /###\  \n'
				+'  :         ,   #####  \n'
				+ '  `-..__.-’ _.-\###/    \n'
				+'         `;_:    `"’  \n'
				+'       .’"""""`.   \n'
				+'      /,  yxd   ,\  \n'
				+'     //    make!  \\ \n' 
				+'     `-._______.-’  \n'
				+'     ___`. | .’___   \n'
				+'    (______|______) \n');
	}
}
if(window.sessionStorage){
	if(!window.sessionStorage.myDog)window.sessionStorage.myDog = "Tom";
}
/**
 * 
 * 
 * 例子：
 * var options = {
			url:'success/query',
			queryParams : getQueryParams(),
			other:{
				pageSize:10,
			    currentPage:1,
			    openProgress:false,//默认true
			    data: {
			        cols: [
			            {width: 80, text: '#', type: 'string',  colClass: 'myColW-break',field:'index'},
			            {width: 160, text: 'OTA同步批次号', type: 'string',colClass: 'myColW-break', field:'clearNum'},
			            {width: 80, text: '平台订单号', type: 'string',  colClass: 'myColW-break',field:'platformOrderNo'}
			        ]
			    }
			},
			tables:{
				checkable: false,
			    sortable: false,
			    minFixedLeftWidth: 300
			   
			}
		    
		};
	$("#statementTbl").Datagrid('load',options);
	
	function getQueryParams() {
	
		var clearNum = $("#clearNum").val();
		var otaSyncBatchNum = $("#otaSyncBatchNum").val();
		var platformOrderNo = $("#platformOrderNo").val();
		
		return {
			clearNum: clearNum,
			otaSyncBatchNum: otaSyncBatchNum,
			platformOrderNo:platformOrderNo
		};
	}
*/