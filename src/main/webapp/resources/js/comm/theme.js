$(function(){
	  var themeKey = "evaluate.theme";
	  var themeIndex = "evaluate.theme.index";
	  
	  $(".theme-box .theme").click(function(){
		  
		  $(this).siblings().removeClass("theme-active");
		  var theme = $(this).attr("theme");
		  
		  setThemeCss(theme,$(".theme-box .theme").index($(this)));
		  
		 
	  });
	  var themeValue = getCookie(themeKey);
	  var themeIndexValue = getCookie(themeIndex);
	  if(themeValue != null && themeIndexValue != null){
		  $(".theme-box .theme").siblings().removeClass("theme-active");
		  setThemeCss(themeValue,themeIndexValue);
	  }
	  
	  function setThemeCss(theme,index){
		  $(".theme-box .theme").eq(index).addClass("theme-active");
		  var oldThemeUrl = $("#zuiTheme").attr("href");
		  $("#zuiTheme").attr("href",oldThemeUrl.split("theme")[0] + "theme/"+theme+"?"+new Date());
		  
		  var iframes = document.getElementsByTagName("iframe");
		  for(var i = 0; i<iframes.length;i++){
			  var iframe = iframes[i];
			  var doc = iframe.contentWindow.document;
			  var zuiTheme = doc.getElementById("zuiTheme");
			  if(zuiTheme){
				  zuiTheme.href=oldThemeUrl.split("theme")[0] + "theme/"+theme+"?"+new Date();
			  }
		  }
		  
		  delCookie(themeKey);
		  delCookie(themeIndex);
		  setCookie(themeKey,theme == null || theme == '' ? '_blue.css' : theme);
		  setCookie(themeIndex,index == null || index == '' ? 0:index);
	  }
  })