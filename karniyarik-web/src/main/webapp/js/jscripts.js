/** KARNIYARIK **/
var cookie_skipped = $.cookie('skipped');
var cookie_email = $.cookie('email');
var cookie_city = $.cookie('city');

var skipped = true;
var email = null;
var city = null;

var defaultDecimalFormat = { format: '#,0', decimalChar: '.', thousandsChar: ',' };
//JavaScript Document
$(document).ready(function() {
	
    $('.mhide').each(function(i,e){ $(this).attr('title',$(this).val());
      //$(this).bind('focus',function(){ if($(this).val()==$(this).attr('title'))$(this).val('');});
	  $(this).bind('blur',function(){ if($(this).val()=='')$(this).val($(this).attr('title'));});	 
    });
	if(typeof dataEdited == 'undefined' || dataEdited == false)
	{
		$("#words").data('edited', false);
	}
	else {
		$("#words").data('edited', true);
	}
	
	$("#words").data('typed', false);
    
	if($("#stab").length >0) {$("#stab").data('defaultvalue', $("#stab").val()); }
	
	$(".stab .ri").each(function(i,e){
	  $(this).click(function(){
		$(".stab").removeClass("act");
	    $(this).parent().addClass("act");
		$("#stab").val(i+1);
		$(".tabc").children().removeClass("act");
		$(".tabc").children("." + $("#stab").val()).addClass("act");
		
		$("#words").autocomplete({
			source: rootPath + "/rest/autocomplete?c="+$("#stab").val(),
			minLength: 2,
			delay:350,
			select: function(event, ui) {
				submitForm();
			}
		});
		
		if($("#words").data('edited') == false){
			if($("#stab").val()=='3'){
				$("#words").val("Emlak ara...");
			}
			else if($("#stab").val()=='4'){
				$("#words").val("Otel ara...");
			}
			else if($("#stab").val()=='2'){
				$("#words").val("Araç ara...");
			}
			else
			{
				$("#words").val("Ürün ara...");
			}
		}
	  });
	});
	
	$(".tabh").tabs(".tabc",{effect:'slide',fadeOutSpeed:"slow",rotate: true, current:"act"});
	
	$("#words").focus(function(i,e){
		if($("#words").data('edited') == false){
			$("#words").val("")
			}
		}
	);
	$("#words").change(function(i,e){
			if($("#words").val().length > 0){
				$("#words").data('edited', true); 
				$("#words").data('typed', true)
			} else{
				$("#words").data('edited', false); 
			}
		}
	);
	
	$("#words").autocomplete({
		source: rootPath + "/rest/autocomplete?c=" + $("#stab").val(),
		minLength: 2,
		delay:350,
		select: function(event, ui) {
			submitForm();
		}
	});
	
	$(".lhead .sh").each(function(i,e){
	  $(this).click(function(){
		$(this).toggleClass("act");
	    $(this).parent().next().toggleClass("act");
	    $(this).parent().next().next().toggleClass("act");
	  });
	});
	
	  $('.more').click(function(){
		height = $(this).parent().css('max-height')=="150px"?$(this).parent()[0].scrollHeight:"150px";
		text = $(this).parent().css('height')=="150px"?"(-)Daralt":"(+)Genislet";
		$(this).parent().css('height',height);
		$(this).text(text);
	  });


	  $("#words").focus();

	  
	  if($("input.numeric").length > 0) {$("input.numeric").numeric({buttons:false, keyboard:true, format: defaultDecimalFormat})}
	  /*if($("input.price").length > 0) {$("input.price").numeric({buttons:false, keyboard:true,currencySymbol: 'TL', showCurrency: true});}*/
	  
	  if($("a.iframe").length > 0)
	  {
		  $("a.iframe").fancybox({
			  	'padding'		: 0,
			  	'margin'		: 0,
			  	'autoScale'		: true,
			  	'width'			: 450,
			  	'height'		: 500,
				'speedIn'		: 200, 
				'speedOut'		: 200, 
				'overlayColor'	: '#999999',
				'overlayShow'	: false,
				'scrolling'		: 'no',
				'titleShow'		: false,
				'showCloseButton' : true, 
				'hideOnOverlayClick': true
		  });
	  }
	  
	  if($("a.iframe_otel").length > 0)
	  {
		  $("a.iframe_otel").fancybox({
			  	'padding'		: 0,
			  	'margin'		: 0,
			  	'autoScale'		: true,
			  	'width'			: 380,
			  	'height'		: 200,
				'speedIn'		: 200, 
				'speedOut'		: 200, 
				'overlayColor'	: '#999999',
				'overlayShow'	: false,
				'scrolling'		: 'no',
				'titleShow'		: false,
				'showCloseButton' : true, 
				'hideOnOverlayClick': true
		  });
	  }

	  //TOOLTIP
		$(".tt").tooltip({
			position: "bottom center",
			offset: [5, 0],
			effect: "fade",
			opacity: 0.9,
			predelay: 200
		});
	
		$(".tb").tooltip({
			position: "top center",
			effect: "fade",
			opacity: 0.9,
			predelay: 200,
			tipClass: "tooltip tb"
		});
		
	  if($(".lazy").length > 0){
		  $(".lazy").lazyload({ 
			    effect : "fadeIn",
			    placeholder : "http://www.karniyarik.com/images/transparent.gif"
			    /*holder/loading.gif*/
		  });
	  }
	  
	  if (!cookie_skipped)
	  {
	  	prompt_email();
	  }
});

function propContainerToggle(containerName, imgName, bottomName)
{
	$(containerName).parent().toggleClass("cfltr");
	$(containerName).toggle(500);
	$(bottomName).toggleClass("cfltrbc");
	$(imgName).toggleClass("down");
}

function setFormAction(form)
{
	if($('#stab').val()=='1') form.action='urun/search.jsp';
	else if ($('#stab').val()=='2') form.action='araba/search.jsp';	
	else if ($('#stab').val()=='3') form.action='emlak/search.jsp';
	else if ($('#stab').val()=='4') form.action='otel/search.jsp';
	
}

function setFormInSearchPageAction(form, path)
{
	if($('#stab').val()=='1') form.action=path + '/urun/search.jsp';
	else if($('#stab').val()=='2') form.action=path + '/araba/search.jsp';	
	else if($('#stab').val()=='3') form.action=path + '/emlak/search.jsp';
	else if($('#stab').val()=='4') form.action=path + '/otel/search.jsp';	
}

function hookFilter(listname, inputname)
{
	$(document).ready(function() {
		$(listname + " input[type='checkbox']").each(function(index){
			$(this).attr('autocomplete', 'off');
			$(this).bind('change', function(){
				 var valArr = new Array();
				 $(listname + ' :checked').each(function(){
					 valArr.push($(this).next().text()); 
				 });
				$(inputname).val(valArr.join(', '));
			});
		});
	});
}

function setFilterValues(listname, inputname, data)
{
	for(var index=0; index<data.length; index++)
	{
		$(listname + " input[id='"+data[index] + "_chkbox']").attr('checked', 'checked');
	}
}

/*function clearFilter(filterName)
{
	$(filterName).val("");
}*/

var submitted = false;
function submitForm()
{
	if(!submitted)
	{
		var submit = false;
		
		if ($("input[name='query']").val() != "") {
			submit = true;
		} else {
			submit = false;
		} 	
		
		if($("#stab").data('defaultvalue') != $("#stab").val() || $("#words").data('typed') == true)
		{
			clear_form_elements('#frmSearch');
		}
		
		if (submit) {
			submitted = true;
			$('#frmSearch').find(':input[value=""]').attr('disabled', true);
			$('#frmSearch').submit();
		}
	}
}

function clear_form_elements(ele) {
    $(ele).find('.rotc').each(function() {
        switch(this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'hidden':
            case 'textarea':
                $(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });
}

function sortResults(opt)
{
	var value = $('#sort').val();
	value = parseInt(value);
	if(value != opt){
		$('#sort').val(opt) 
	}else if(opt != 1){
		$('#sort').val(opt+1)
	}
	
	submitForm();
}

function cleanAndSubmit(inputname)
{
	var names = inputname.split(",");
	for(var index=0; index<names.length; index++)
	{
		$("input[name='"+names[index]+"']").val("");
		if($("select[name='"+names[index]+"']").length > 0) {$("select[name='"+names[index]+"']").val("");}		
	}
	
	submitForm();
}

function getViewEventName(typeVal)
{
	var typeDesc = "unknown-view";
	
	switch(typeVal)
	{
		case 0: typeDesc = "urun-listing-view"; break;
		case 1: typeDesc = "urun-sponsored-view"; break;
		case 2: typeDesc = "araba-listing-view"; break;
		case 3: typeDesc = "araba-sponsored-view"; break;
		case 4: typeDesc = "otel-listing-view"; break;
		case 5: typeDesc = "otel-sponsored-view"; break;
		case 6: typeDesc = "citydeal-view"; break;
		case 7: typeDesc = "dailydeal-view"; break;
		case 8: typeDesc = "index-view"; break;
		case 9: typeDesc = "urun-not-found"; break;
		case 10: typeDesc = "araba-not-found"; break;
		case 11: typeDesc = "otel-not-found"; break;
		case 12: typeDesc = "emlak-not-found"; break;		
		case 13: typeDesc = "estate-listing-view"; break;
		case 14: typeDesc = "estate-sponsored-view"; break;
	}
	
	return typeDesc
}

function trackClickEvent(urlVal, siteVal, nameVal, typeVal)
{
	var typeDesc = "unknown-view";
	
	switch(typeVal)
	{
		case 0: typeDesc = "urun-listing-click"; break;
		case 1: typeDesc = "urun-sponsored-click"; break;
		case 2: typeDesc = "araba-listing-click"; break;
		case 3: typeDesc = "araba-sponsored-click"; break;
		case 4: typeDesc = "citydeal-click"; break;
		case 5: typeDesc = "dailydeal-click"; break;
		case 6: typeDesc = "otel-listing-click"; break;
		case 7: typeDesc = "otel-sponsored-click"; break;
		case 8: typeDesc = "estate-listing-click"; break;
		case 9: typeDesc = "estate-sponsored-click"; break;
		case 101: typeDesc = "samename-click"; break;
	}
	
	var price = 0; 
	var price = (typeVal == 1) ? 6 : 0;
	_gaq.push(['_setAccount', 'UA-3933507-1'], ['_trackEvent', typeDesc, siteVal, urlVal, price]);
}

function sendClickToAdsense(urlVal, siteVal, nameVal, typeVal)
{
	trackClickEvent(urlVal, siteVal, nameVal, typeVal);
    setTimeout('', 100);
}

function productClicked(urlVal, siteVal, nameVal, typeVal) {
	var queryVal = $('#words').val();
	
	try {
		trackClickEvent(urlVal, siteVal, nameVal, typeVal)

	    $.ajax({ 
			type: "POST",
			url: rootPath+"/rest/event/hit", 
			data: {url:urlVal, site:siteVal, name:nameVal, query:queryVal, type:typeVal}
			});

	    setTimeout('', 100);
	}catch(err){
		//alert(err)
	}  
}

/** ADDTHIS SPECIFIC JS **/
var addthis_localize = {}; 
var addthis_share = {email_template:'single_product'}
var addthis_config = {
		username: "krnyrk",
	    data_track_clickback: true,
	    services_compact: 'email, favorites, facebook, google, live, yahoo, twitter, friendfeed, digg',
	    ui_click: true, 
	    ui_delay: 500,
	    ui_language: "tr",
	    ui_offset_top: -10,
	    ui_offset_left: 0,
	    ui_header_color: "#ffffff",
	    ui_header_background: "#B978A2",
	    ui_cobrand: "Karnıyarık",
	    ui_use_css: true, 
	    ui_use_addressbook: true
};

/*
var addthis_offset_top = 40;
var addthis_offset_left = 60;
*/

function prompt_email() {
	$(document).ready(function() {
		$('#email_dialog').dialog({
			modal: true,
			resizable: false,
			draggable: false,
			height: 'auto',
			width: 480,
			closeText: "Kapat",
			dialogClass: 'maildlg',
			buttons: {
				'Kaydet': function() {
					email = $('#email').val();
					city = $('#city').val();
					cat = $('#cat').val();
					skipped = false;
					
					$.ajax({
						type: "POST",
						url: rootPath + "/system/saveEmailAndCity",
						data: {'email' : email, 'city' : city, 'cat':cat}
					});
					$(this).dialog('close');
					$('#email_check_dialog').dialog({
						modal: true,resizable: false,draggable: false,height: 300,width: 480,dialogClass: 'maildlg',
						buttons: {
							'Kapat': function() {
								$(this).dialog('close');
							}
						}
					});
				}
			},
			close: function(event, ui) {
				write_to_cookie(skipped, email, city);
			}
		})
	});
}

function write_to_cookie(skipped, email, city)
{
	if (skipped)
	{
		$.cookie('skipped', true, {expires:15, path:'/', domain:'karniyarik.com'});
	}
	else
	{
		$.cookie('skipped', false, {expires:730, path:'/', domain:'karniyarik.com'});
		$.cookie('email', email, {expires:730, path:'/', domain:'karniyarik.com'});
		$.cookie('city', city, {expires:730, path:'/', domain:'karniyarik.com'});
	}
}

function resetnumeric(id, value)
{
	var val2 = $.formatNumber(value, defaultDecimalFormat);
	id.val(val2);
}

function searchWithSameName(prodname)
{
	$("#words").val(prodname);
	clear_form_elements('#frmSearch');
	submitForm()
	trackClickEvent(prodname,'','',101);
}