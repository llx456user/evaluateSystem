var tabsPlugs = {
		
		init:function(tabsId){
			var html = 
				    '<ul class="nav nav-tabs">'	
					//+'<li class="active"><a href="###" data-target="#defaultContent" data-toggle="tab" title="欢迎您">欢迎页</a></li>'
					+'</ul>'
					+'<div class="tab-content">'
					+'<div class="tab-pane fade active in" id="defaultContent">'
					//+'   <p>欢迎使用TRIP系统。</p>'
					+' </div>'
					+'</div>';
			$("#" + tabsId).html(html);
			
			$(".tab-content").height($(document).height() - 45 - 45);
			
			this.addTab("欢迎您", "hello",null,true);
			
			//this.initShortcutKey();
			
		},
		
		addTab:	function (subtitle,url,icon,isFirst){
			var hasTitle = false;
			$('ul.nav-tabs> li>a').each(function(){
				
				if($(this).attr("title") == subtitle){
					hasTitle = true ; 
				}
			});
			
			if(!hasTitle){
				$('.nav-tabs > li').removeClass('active');
				//var iconContent = '<span style="position:absolute;top:2px;right:5px;cursor:pointer;" onclick="tabsPlugs.closeTab(&quot;'+subtitle+'&quot;)"><img src="resources/static/image/closeTabBtn.png" style="width:13px;height:13px;" /></span>';
				var iconContent = '<span style="top: 0px;top: -4px\0; cursor: pointer; position: absolute; left: 80%; right: 0px; bottom: 80%;" onclick="tabsPlugs.closeTab(&quot;'+subtitle+'&quot;)"><img src="resources/static/image/closeTabBtn.png" style="width:13px;height:13px;" /></span>';
				if(isFirst){
					iconContent = '';
				}
				
				var tab = '<li class="active"><a class="tabTitle" href="###" data-target="#'+subtitle+'" data-toggle="tab" title="'+subtitle+'">'+subtitle+'</a>'+iconContent+'</li>'; 
				var frame = this.createFrame(url);
				$('.tab-content > div.tab-pane').removeClass("active").removeClass("in");
				var tabContent = '<div class="tab-pane fade active in" id="'+subtitle+'">'+frame+'</div>';
				$('.nav-tabs').append(tab);
				$('.tab-content').append(tabContent);
				
			}else{
				$('.nav-tabs > li').removeClass('active');
				$('.tab-content > div.tab-pane').removeClass("active").removeClass("in");
				
				$('.nav-tabs > li > a[title="'+subtitle+'"]').parent().addClass("active");
				$(".tab-content > #" + subtitle).addClass("active").addClass('in');
			}
		},
	createFrame:function (url){
			// alert(url)
			/*var s = '<iframe class="embed-responsive-item" id="'+url+'" src="'+ctx + '/' + url+'" scrolling="yes" frameborder="0" style="padding-bottom: 3.5%;width: 100%;height: 95%;"></iframe>';*/
		var h = $(document).height() - 45 - 45  - 50;
		var s = '<iframe class="embed-responsive-item" id="'+url+'" src="'+ctx + '/' + url+'" scrolling="yes" frameborder="0" style="width: 100%;height: '+h+'px;"></iframe>';
			return s;
		},
		
		closeTab:function (subtitle){
			var curTitle = $('.nav-tabs > li.active').find("a").text();
			
			var $li = $('.nav-tabs > li > a[title="'+subtitle+'"]').parent();
			var $content = $(".tab-content > #" + subtitle);
			if(curTitle == subtitle){
				// 清楚class
				$('.nav-tabs > li').removeClass('active');
				$('.tab-content > div.tab-pane').removeClass("active").removeClass("in");
				
				$li.prev().addClass("active");
				$content.prev().addClass("active").addClass('in');
			}
			
			// 移除
			$li.remove();
			$content.remove();
		},
		initShortcutKey:function(){
			var _self = this;
			var $curelement;
			$('.nav-tabs').on('dblclick','li > .tabTitle',function(){
				var subtitle = $(this).prop("title");
				if($('.nav-tabs li').index($(this).parent()) != 0) _self.closeTab(subtitle);
			});
			
			$('.nav-tabs').on("contextmenu", "li > .tabTitle",function(e){
				var cMenu = '<ul class="dropdown-menu" id="contextmenu">'
				   +'<li><a href="###">关闭当前</a></li>'
				   +'<li><a href="###">全部关闭</a></li>'
				   +'<li><a href="###">关闭其他</a></li>'
				   +'<li><a href="###">关闭左侧</a></li>'
				   +'<li><a href="###">关闭右侧</a></li>'
				   +'</ul>';
				var x = getX(e);
				var y = getY(e);
				//console.log(x,y);
				$("#contextmenu").remove();
				$(cMenu).appendTo("body").css("position", "absolute").css("top", y -10 ).css("left", x- 10).show();
				
				$curelement = $(this);
			    return false;
			});
			$(document).on('mouseleave',"#contextmenu",function(){
				//console.log('leave');
				$(this).remove();
			});
			$(document).on('click',"#contextmenu li > a",function(){
				var index = $("#contextmenu li").index($(this).parent());
				$("#contextmenu").remove();
				if(0 == index){
					
					if($('.nav-tabs li').index($curelement.parent()) != 0) _self.closeTab($curelement.prop('title'));
				}
				else if(1 == index){
					$(".nav-tabs").children().each(function(){
						if($('.nav-tabs li').index($(this))!= 0){
							_self.closeTab($(this).find(".tabTitle").prop("title"));
						}
					});
				}
					
				else if(2 == index){
					
					$curelement.parent().siblings().each(function(){
						if($('.nav-tabs li').index($(this))!= 0){
							_self.closeTab($(this).find(".tabTitle").prop("title"));
						}
					});
				}
				else if(3 == index){
					
					$curelement.parent().prevAll().each(function(){
						if($('.nav-tabs li').index($(this))!= 0){
							_self.closeTab($(this).find(".tabTitle").prop("title"));
						}
					});
				}
				else if(4 == index){
					
					$curelement.parent().nextAll().each(function(){
						if($('.nav-tabs li').index($(this))!= 0){
							_self.closeTab($(this).find(".tabTitle").prop("title"));
						}
					});
				}
				
			})
			
			function getX(e) {
			  e = e || window.event;
			  return e.pageX || e.clientX + document.body.scrollLeft;
			}
			
			function getY(e) {
			  e = e|| window.event;
			  return e.pageY || e.clientY + document.body.scrollTop;
			}
		}
};