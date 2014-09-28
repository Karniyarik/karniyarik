KRNYRK = window.KRNYRK || {};

var krnyrk_query = "";
var krnyrk_maxResult = 4;
var krnyrk_header_text = '#E5E5E5';
var krnyrk_background = '#636363';
var krnyrk_list_background = 'white';
var krnyrk_name_color = '#5F7A06';
var krnyrk_price_color = '#883171';
var krnyrk_store_color = '#636363';
var krnyrk_div_width = '300px';

function krnyrkcallbackfunc(jsonData) {
	krnyrkscriptobj.removeScriptTag();
	krnyrkListDiv.removeChild(krnyrkloadingImg);
	if(jsonData.error != null && jsonData.error != ""){
		krnyrkListDiv.innerHTML = "Sonuç bulunamadı...";
	}
	else{
		var list = "";	
		for(var index =0; index<jsonData.products.product.length && index < krnyrk_maxResult; index++)
    	{
    		var product = jsonData.products.product[index];
    		var proStr =
    		'<div class="krnyrk_prod">'+
			'<!--div class="krnyrk_prod_img"><img src="'+product.imageurl+'"/></div-->'+
			'<div class="krnyrk_prod_name">'+
			'	<a href="'+ product.url +'" style="color:'+krnyrk_name_color+';">'+modifyName(product.name)+'</a><br/>'+
			'	<span class="krnyrk_prod_store" style="color:'+krnyrk_store_color+';">'+product.site+'</span>'+
			'</div>'+
			'<div class="krnyrk_prod_pri">'+
				'<span style="color:'+krnyrk_price_color+';">' + product.price + ' ' + product.currency + '</span>'+
			'</div>'+
			'</div>';
    		list += proStr;
    	}
		krnyrkListDiv.innerHTML = list; 
	}
}

function modifyName(name){
	if(name.length>25)
	{
		name = name.substring(0,22) + "...";
	}
	
	return name;
}

(function() {
  var global = this;
  
  if (KRNYRK && KRNYRK.Widget) {
	return
  }

  KRNYRK.Widget = function(params){
	  if(params.maxresult != null){krnyrk_maxResult = params.maxresult;}  
	  if(params.header.text != null){krnyrk_header_text = params.header.text;}
	  if(params.header.background != null){krnyrk_background = params.header.background;}
	  if(params.list.background != null){krnyrk_list_background = params.list.background;}
	  if(params.list.name != null){krnyrk_name_color = params.list.name;}
	  if(params.list.price != null){krnyrk_price_color = params.list.price;}
	  if(params.list.store != null){krnyrk_store_color = params.list.store;}
	  if(params.width != null){krnyrk_div_width = params.width;}
	  krnyrk_query = escape(params.query);
	  
	  function JSONscriptRequest(fullUrl) {
		    this.fullUrl = fullUrl; 
		    this.noCacheIE = '&noCacheIE=' + (new Date()).getTime();
		    this.headLoc = document.getElementsByTagName("head").item(0);
		    this.scriptId = 'JscriptId' + JSONscriptRequest.scriptCounter++;
	}
	
	JSONscriptRequest.scriptCounter = 1;
	JSONscriptRequest.prototype.buildScriptTag = function () {
		  this.scriptObj = document.createElement("script");
		  this.scriptObj.setAttribute("type", "text/javascript");
		  this.scriptObj.setAttribute("charset", "utf-8");
		  this.scriptObj.setAttribute("src", this.fullUrl + this.noCacheIE);
		  this.scriptObj.setAttribute("id", this.scriptId);
	}
		 
	JSONscriptRequest.prototype.removeScriptTag = function () {
	  this.headLoc.removeChild(this.scriptObj);  
	}
	
	JSONscriptRequest.prototype.addScriptTag = function () {
	  this.headLoc.appendChild(this.scriptObj);
	}

	  var construct = function(){
		  var rootContextPath = "http://www.karniyarik.com/"; 
		  var css=document.createElement("link");
		  css.setAttribute("rel", "stylesheet");
		  css.setAttribute("type", "text/css");
		  css.setAttribute("href", rootContextPath + "/widget/widget.css");
		  document.body.appendChild(css);
		  
		  document.write('<div id="KRNYRK.Widget.DIV" class="krnyrkwidget" style="width:'+krnyrk_div_width+'">'+
				  '<div class="krnyrk_head" style="background-color:'+krnyrk_background+';"><h2 style="color:'+krnyrk_header_text+';">Nerede Ne kadar?</h2></div>'+
				  '<div id="krnyrk_list" class="krnyrk_list" style="background-color:'+krnyrk_list_background+';"></div>'+
				  '<div class="krnyrk_bottom" style="background-color:'+krnyrk_background+';"><span><a href="' + rootContextPath + '"/ne-kadar/'+krnyrk_query+'">Daha fazlasını gör</a></span><a href="http://www.karniyarik.com"><img src="images/logo_small.png"></img></a></div>' +
				  '</div>');
		  krnyrkListDiv = document.getElementById('krnyrk_list');
		  krnyrkloadingImg = document.createElement("img");
		  krnyrkloadingImg.src=rootContextPath + "widget/images/loading.gif";		  
		  krnyrkListDiv.appendChild(krnyrkloadingImg);
		  var serviceURL = rootContextPath + "rest/js/product?q="+krnyrk_query+"&key=4BD4761A33A89F4842B49649D18173A7&callback=krnyrkcallbackfunc";
		  krnyrkscriptobj = new JSONscriptRequest(serviceURL);
		  krnyrkscriptobj.buildScriptTag();
		  krnyrkscriptobj.addScriptTag();
	  };

	  return {
	    start : function() {construct();}
	  };
  };
})();