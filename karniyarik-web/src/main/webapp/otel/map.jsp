<DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%
	String lat = request.getParameter("lat");
	String lon = request.getParameter("lon");
	String name= request.getParameter("n");
	String link= request.getParameter("l");
	String address= request.getParameter("a");
	String zip= request.getParameter("z");
	String city= request.getParameter("c");	
	String image= request.getParameter("i");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Otel Yeri</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css" />
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0px; padding: 0px }
  #map_canvas { height: 100% }
  #content {}
  .oimg {width: 100px; height: 100px; float: left;}
  .oname {font-weight:bold; font-size: 14px;}
  .oaddress {}
</style>
<script src="http://maps.google.com/maps/api/js?v=3&sensor=false&language=tr&key=ABQIAAAAThQwFeDe9GKvgUcvSTClrRRfs8w4zhP5mn7r0JS2DSPvupgUyhSAXzfQwQGzfD3Hl-Oikfv67J3bRA" type="text/javascript"></script>
<script type="text/javascript">
  function initialize() {
    var latlng = new google.maps.LatLng(<%=lat%>, <%=lon%>);
    var myOptions = {
      zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    var marker = new google.maps.Marker({
        position: latlng,
        title:"<%=name%>"
    });
    
    marker.setMap(map); 
	//'<div class="oimg"><img src="<%=request.getContextPath()%>/system/imgrsz?&w=100&h=100&v=<%=image%>"/></div>' +
    var contentString = '<div id="content">'+
     
    '<div class="oname"><a href="<%=link%>"><%=name%></a></div>' + 
    '<div class="oaddress"><%=address%>, <%=zip%> <%=city%> </div>'
    '</div>';

	var infowindow = new google.maps.InfoWindow({
	    content: contentString
	});

	infowindow.open(map,marker);
    //google.maps.event.addListener(marker, 'click', function() {});
  }
</script>
</head>
<body onload="initialize()">
<div id="map_canvas" style="width:100%; height:100%"></div>
</body>
</html>