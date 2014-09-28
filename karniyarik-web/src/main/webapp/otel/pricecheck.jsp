<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css" />
<style type="text/css">
	input[type="text"].fin {
		-moz-border-radius-bottomleft:4px;
		-moz-border-radius-bottomright:4px;
		-moz-border-radius-topleft:4px;
		-moz-border-radius-topright:4px;
		border:1px solid #CCCCCC;
		line-height:1.0em;
		padding:4px;
		width: 100px;
	}
	
	select.fin {
		width:40px;
		-moz-border-radius-bottomleft:4px;
		-moz-border-radius-bottomright:4px;
		-moz-border-radius-topleft:4px;
		-moz-border-radius-topright:4px;
		border:1px solid #CCCCCC;
	}
	
	.pin{
		float:left;
		margin-right:10px;
	}
	.ftrdcont {height:140px;}
	.otelpri{width:380px; height: 200px;}
</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.8.7.custom.min.js"></script>
<script type="text/javascript">
 	function getPrice(){
 	 	
 	}
</script>
</head>
<body class="ftrd">
<div class="ftrdmain otelpri">
	<div class="ftrdheader">
		<div style="display:inline">Otel Fiyatı Sorgula</div>
		<div class="ftrd_close"><a href="" onclick="parent.$.fancybox.close();">Kapat</a></div>
	</div>
	<div class="ftrdwindow">
		<div class="ftrdcont">
			<div class="ftrd_sec">
				<div class="ftrd_sec_head"></div>
				<div class="pin">
					Giriş Tarihi
					<input type="text" class="fin"></input>
				</div>
				<div class="pin">
					Çıkış Tarihi
					<input type="text" class="fin"></input>
				</div>
				<div style="display:block; clear:both;margin-bottom:20px;"></div>
				<div class="pin">
					Oda Sayısı
					<select class="fin">
						<option>1</option>
						<option>2</option>
					</select>
				</div>
				<div class="pin">
					Yetişkin
					<select class="fin">
						<option>1</option>
						<option>2</option>
					</select>
				</div>
				<div class="pin">
					Çocuk
					<select class="fin">
						<option>1</option>
						<option>2</option>
					</select>
				</div>
			</div>
			<div style="display:block; clear:both;"></div>
			<div class="ftrd_sec">
				<div class="pin">
					<input type="button" class="fin" value="Sorgula" onclick=""/>
				</div>
				<div class="pin">
					<span id="price"></span>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>