<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-指标管理添加</title>
	<script type="text/javascript" src="<c:url value='/resources/js/platform/indexAdd.js?version=${jsversion}'/>"></script>
	<style>
	.active{
		border:1px solid red;
	}
	.indexBtnImg{
		/*border:1px solid #D2F2EA;*/
		border:1px solid #359F74;
		border-radius: 5px;
	    background-repeat: no-repeat;
	    width:90px;
	    height:90px;
	    margin:5px 0 0 5px;
	    /*background-color: #D2F2EA !important;*/
		background-color: #359F74 !important;
	}
	.indexBtnImg:hover{
		border-color:gray;
		cursor: pointer;
		background-color: #A9A9A9 !important;
	}
	.indexBtnImg strong{
	    display: block;
	    font-weight: bold;
	    color: #ffffff;
	    padding-top: 55px;
	}
	#datasetBtn{
		background: url('${ctx}/resources/images/btn-database2.png') no-repeat center top 10px;
	}
	#modelBtn{
		background: url('${ctx}/resources/images/btn-module2.png') no-repeat center top 10px;
	}
	#chartBtn{
		background: url('${ctx}/resources/images/btn-chart2.png') no-repeat center top 10px;
	}
	#gridBtn{
		background: url('${ctx}/resources/images/grid2.png') no-repeat center top 10px;
	}
	#indexBtn{
		 background: url('${ctx}/resources/images/indexTemp2.png') no-repeat center top 10px;
	 }
	#childBtn{
		background: url('${ctx}/resources/images/btn-child2.png') no-repeat center top 10px;
	}

	.boxF, .boxS, .boxT, .overlay
        {
            width: 50px;
            height: 75px;
            overflow: hidden;
        }
        .boxF, .boxS
        {
            visibility: hidden;
        }
        .boxF
        {
            transform: rotate(120deg);
            float: left;
            margin-left: 10px;
            -ms-transform: rotate(120deg);
            -moz-transform: rotate(120deg);
            -webkit-transform: rotate(120deg);
        }
        .boxS
        {
            transform: rotate(-60deg);
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
        }
        .boxT
        {
            transform: rotate(-60deg);
            background: no-repeat 50% center;
            background-size: 125% auto;
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
            visibility: visible;
            background-color: #73797D;
            padding-top:30px;
            color:#fff;
        }
        #contants{
        	padding: 3px 0px 8px 8px;
        }
        .boxY{
        	 transform: rotate(-45deg);
            width:45px;
            height:45px;
            -ms-transform: rotate(-45deg);
            -moz-transform: rotate(-45deg);
            -webkit-transform: rotate(-45deg);
            visibility: visible;
            background-color: #73797D;
            padding-top:10px;
            color:#fff;
        }
        .boxZ{
        	 transform: rotate(45deg);
        	  -ms-transform: rotate(45deg);
            -moz-transform: rotate(45deg);
            -webkit-transform: rotate(45deg);
        }
        #if{
        	padding: 20px 0px 0px 20px;
        }

        .dropdown-menu{
        	text-align: left;
        }

        .showGrid{
        	width:90px;
        	height:90px;
        	background:#ddd;
        	border-radius:5px;
        }
	</style>
</head>
<body>
	<div class="container">



	<!-- 多模型关联设计 -->
	<div class="panel">
		<div class="panel-heading">
			多模型关联设计
		</div>
		<div class="panel-body">
			<div style="width: 100%;position: relative;border:1px solid #ddd;">
				<div style="float:left;width:103px;height:920px;border:1px solid #ddd;text-align:center;background:#ddd;">
					<div class="btn-group">
						<div class="indexBtnImg dropdown-toggle" data-toggle="dropdown" id="datasetBtn" style="margin-right:6px;">
							<strong>数据源</strong>
						</div>
						<ul class="dropdown-menu" role="menu">
							<li class="dropdown-submenu">
								<a href="###">文件类型</a>
								<ul class="dropdown-menu" id="datasetDropdown_file">
							        <li class="dropdown-submenu"><a href="###" data-id="-1">评估结果</a>
							        <ul class="dropdown-menu">
                                        <c:choose>
                                            <c:when test="${empty accessFiles}">
                                                <li><a href="###"   class="dragClass">无数据</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${accessFiles}" var="acf">
                                                    <li><a href="###" id="file${acf.id}"  fileid="${acf.id}" filename="${acf.fileName}"  draggable="true" ondragstart="drag(event)" type = "file" class="dragClass">${acf.fileName}</a></li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
							        </ul>
							        </li>
							        <li class="dropdown-submenu"><a href="###" data-id="-2">数据筛选结果</a>
							        <ul class="dropdown-menu">
                                        <c:choose>
                                            <c:when test="${empty dataFiles}">
                                                <li><a href="###"   class="dragClass">无数据</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${dataFiles}" var="dataf">
                                                    <li><a href="###" id="file${dataf.id}"  fileid="${dataf.id}" filename="${dataf.fileName}" draggable="true" ondragstart="drag(event)" type = "file" class="dragClass">${dataf.fileName}</a></li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
							        </ul>
							        </li>
							        <li class="dropdown-submenu"><a href="###" data-id="3">自定义分类</a>
							        <ul class="dropdown-menu">
										<c:forEach items="${indexFileList}" var="fc">
											<li class="dropdown-submenu" cateid="${fc.datasourceCategory.id }" >
												<a href="#" style="width:200px;" cateid="${fc.datasourceCategory.id }">${fc.datasourceCategory.categoryName }</a>
                                                <ul class="dropdown-menu">
                                                    <c:choose>
                                                        <c:when test="${empty fc.datasourceFileList}">
                                                            <li><a href="###"   class="dragClass">无数据</a></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach items="${fc.datasourceFileList}" var="datafile">
                                                                <li><a href="###" id="${datafile.id}" draggable="true" fileid="${datafile.id}" filename="${datafile.fileName}"  type = "file" ondragstart="drag(event)" class="dragClass">${datafile.fileName}</a></li>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
											</li>
										</c:forEach>
							        	<!--<li><a href="###" id="custom_1" draggable="true" ondragstart="drag(event)" class="dragClass">测试1</a></li> -->
							        </ul>
							        </li>
							    </ul>
							</li>
							<li class="dropdown-submenu">
								<a href="###">数据库类型</a>
								<ul class="dropdown-menu" id="datasetDropdown_database">
									<c:forEach items="${dbTypeList}" var="db">
									<li class="dropdown-submenu" cateid="${db.datasourceDb.id }" >
										<a href="#" id="dbType${db.datasourceDb.id}" style="width:200px;" cateid="${db.datasourceDb.id }">${db.datasourceDb.sourceName } </a>
										<ul class="dropdown-menu">
											<c:choose>
												<c:when test="${empty db.datasourceSqlList}">
													<li><a href="###"   class="dragClass">无数据</a></li>
												</c:when>
												<c:otherwise>
													<c:forEach items="${db.datasourceSqlList}" var="dbfile">
														<li><a href="###" id="db${dbfile.id}" draggable="true" fileid="${dbfile.id}" filename="${dbfile.sqlName}"  type = "sql" ondragstart="drag(event)" class="dragClass">${dbfile.sqlName}</a></li>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</ul>
									</li>
									</c:forEach>
								</ul>
							</li>
					  	</ul>
					</div>
					<div class="btn-group">
						<div class="indexBtnImg dropdown-toggle" data-toggle="dropdown" id="modelBtn" style="margin-right:6px;">
							<strong>模型</strong>
						</div>
						<%--<ul class="dropdown-menu" role="menu" id="modelDropdown">--%>
							<%--<li class="dropdown-submenu"><a href="###" data-id="1">模型系列</a>--%>
						        <ul class="dropdown-menu">
									<c:forEach items="${mcList }" var="mc">
										<li class="dropdown-submenu" cateid="${mc.modelCategory.id }" >
											<a href="#" id="model_${mc.modelCategory.id }" style="width:200px;" cateid="${mc.modelCategory.id }" >${mc.modelCategory.categoryName } </a>
											<ul class="dropdown-menu">
												<c:choose>
													<c:when test="${empty mc.modelInfoList}">
														<li><a href="###"   class="dragClass">无数据</a></li>
													</c:when>
													<c:otherwise>
														<c:forEach items="${mc.modelInfoList}" var="mcmodel">
															<li><a href="###" id="model${mcmodel.id}" draggable="true" modelid="${mcmodel.id}"  type = "selectModel" ondragstart="drag(event)" class="dragClass">${mcmodel.modelName}</a></li>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</ul>
										</li>
									</c:forEach>
						        	<%--<li><a href="###" id="model_test1" type="selectModel" draggable="true" ondragstart="drag(event)" class="dragClass">测试1</a></li>--%>
						        </ul>
						    <%--</li>--%>
					  	<%--</ul>--%>
					</div>
					<div class="btn-group">
						<div class="indexBtnImg dropdown-toggle" data-toggle="dropdown" id="chartBtn" style="margin-right:6px;">
							<strong>图形</strong>
						</div>
						<ul class="dropdown-menu" role="menu" id="chartDropdown">
							<li><a href="###" id="piePic" type="pie"  draggable="true" ondragstart="drag(event)" class="dragClass">饼图</a></li>
							<li  ><a href="###" id="colPic" type="bar" draggable="true" ondragstart="drag(event)" class="dragClass">柱状图</a></li>
							<li><a href="###" id="linePic" type="line" draggable="true" ondragstart="drag(event)" class="dragClass">折线图</a></li>
							<li><a href="###" id="hlinePic" type="Hline" draggable="true" ondragstart="drag(event)" class="dragClass">曲线图</a></li>
							<li><a href="###" id="dotPic"  type="scatter"  draggable="true" ondragstart="drag(event)" class="dragClass">散点图</a></li>
						<!--	<li><a href="###" id="mapPic" type="map" draggable="true" ondragstart="drag(event)" class="dragClass">地图</a></li>-->
							<li><a href="###" id="3dPic" type="hscatter" draggable="true" ondragstart="drag(event)" class="dragClass">三维图</a></li>
					  	</ul>
					</div>
					<div class="indexBtnImg" id="gridBtn" draggable="true" type="grid" ondragstart="drag(event)" >
						<strong type="grid">表格</strong>
					</div>
					<div class="indexBtnImg" id="indexBtn" draggable="true" type="tpl" ondragstart="drag(event)" onclick="showTplPage()">
						<strong>模板</strong>
					</div>
					<div class="indexBtnImg" id="childBtn" draggable="true" type="childNode" ondragstart="drag(event)">
						<strong>子节点</strong>
					</div>
					<div id="myPaletteDiv" style="height: 350px; margin-top:10px;border: solid 0px gray "></div>
					<!--去掉常数和if
					<div id="contants" class="indexBtnImg" draggable="true" ondragstart="drag(event)">
						<div class="boxF">
							<div class="boxS"><div class="boxT">常数</div></div>
						</div>
					</div>
					<div id="if" class="indexBtnImg" draggable="true" ondragstart="drag(event)">
						<div class="boxY">
							<div class="boxZ">if...</div>
						</div>
					</div>
					-->
				</div>
				<div id="show" style="height:920px; vertical-align: top; padding: 5px; width:90%; margin-left: 110px;	" ondrop="drop(event)" ondragover="allowDrop(event)">
				</div>

			</div>
		</div>
	</div>
    <!-- 新建指标 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
				<strong>指标信息</strong>
			</div>
		    <div class="panel-body">
				<form>
					<input type="hidden" name="id" id="id" value="${bean.id }"/>
					<input type="hidden" name="indexCategoryid" id="indexCategoryid" value="${bean.indexCategoryid }"/>
				  <div class="form-group">
				    <label for="indexName">指标名称</label>
				    <input type="text" class="form-control" id="indexName" value="${bean.indexName }" placeholder="请输入指标名称">
				  </div>
				  <div class="form-group">
				    <label for="indexContent">指标内容</label>
				    <input  type="text"  class="form-control" id="indexContent"  value="${bean.indexContent }"  placeholder="请输入指标内容">
				  </div>
					<div class="form-group">
						<div class="checkbox">
							<label>

								<c:choose>
									<c:when test="${bean.visibleChild==0}">
										<input type="checkbox" id ="visibleChild"  > 显示子指标内容
									</c:when>
									<c:otherwise>
										<input type="checkbox" id ="visibleChild" checked=true> 显示子指标内容
									</c:otherwise>
								</c:choose>
							</label>
						</div>
					</div>
				<button class="btn btn-primary" type="button" onclick="save();">保存</button>
				<button type="button" class="btn btn-primary" onclick="otherSave()">另存为</button>
				<button class="btn btn-primary" type="button" onclick="back();">关闭</button>
				 </form>
			</div>
		</div>
		<!-- setting -->
		<div class="modal fade" id="settingPage">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">数据筛选设定</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
							<tr>
								<td>判断符号</td>
								<td>输入参数</td>
							</tr>
							<tr>
								<td>
									<select class="form-control" name="symbol">
										<option value="">-请选择-</option>
										<option value="<"><</option>
										<option value=">">></option>
										<option value="=">=</option>
										<option value=">=">>=</option>
										<option value="<="><=</option>
									</select>

								</td>
								<td>
									<input type="text" class="form-control" name="iptParam">
								</td>
							</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<!-- settingnameanddefault -->
		<div class="modal fade" id="settingNameAndDault">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">设置名字</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
							<tr>
								<td>名称</td>
								<td>默认值</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="paramName">
								</td>
								<td>
									<input type="text" class="form-control" name="defaultValue">
								</td>
							</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<!-- settingname -->
		<div class="modal fade" id="settingName">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">设置项目数据源名称</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
							<tr>
								<td>项目数据源名称</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="paramName">
								</td>
							</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>


		<!-- 设置柱状图，折线图，散点图，曲线图的标题，x轴，y轴的标题 -->
		<div class="modal fade" id="settingPictureTitle">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">设置标题</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
						<tr>
							<td>标题</td>
						</tr>
						<tr>
							<td>
								<input type="text" class="form-control" name="pictureTitle">
							</td>
						</tr>
							<tr>
								<td>x标题</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="xTitle">
								</td>
							</tr>
						<tr>
							<td>y标题</td>
						</tr>
						<tr>
							<td>
								<input type="text" class="form-control" name="yTitle">
							</td>
						</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 设置柱状图，折线图，散点图，曲线图的标题，x轴，y轴的标题 -->
		<div class="modal fade" id="settingPictureThreeTitle">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">设置标题</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
							<tr>
								<td>标题</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="pictureTitle">
								</td>
							</tr>
							<tr>
								<td>x标题</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="xTitle">
								</td>
							</tr>
							<tr>
								<td>y标题</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="yTitle">
								</td>
							</tr>
						<tr>
							<td>z标题</td>
						</tr>
						<tr>
							<td>
								<input type="text" class="form-control" name="zTitle">
							</td>
						</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 设置柱状图和表格的标题 -->
		<div class="modal fade" id="settingTableTitle">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">设置标题</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
							<tr>
								<td>标题</td>
							</tr>
							<tr>
								<td>
									<input type="text" class="form-control" name="pictureTitle">
								</td>
							</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 另存为模板 -->
		<div class="modal fade" id="otherSavePage">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
						<h4 class="modal-title">另存为模板</h4>
					</div>
					<div class="modal-body">
						<p>
						<table>
							<tr>
								<td>模板名称</td>
								<td>
									<input type="text" class="form-control" name="tplName">
								</td>
							</tr>
						</table>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary OK">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
            function callBackSelectTp(indexid){
                window.location.href = ctx + '/platform/indexEdit?id=' + indexid;
            }
		</script>
</body>

</html>
