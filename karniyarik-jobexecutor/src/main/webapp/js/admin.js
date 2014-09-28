$(document).ready(function() {
	 
	 $("ul.sf-menu").supersubs({ 
         minWidth:    12,   // minimum width of sub-menus in em units 
         maxWidth:    27,   // maximum width of sub-menus in em units 
         extraWidth:  1     // extra width can ensure lines don't sometimes turn over 
                            // due to slight rounding differences and font-family 
     }).superfish();  
	 
	  if($("a.iframe").length > 0)
	  {
		  $("a.iframe").fancybox({
			  	'padding'		: 0,
			  	'margin'		: 0,
			  	'autoScale'		: true,
			  	'width'			: 720,
			  	'height'		: 700,
				'speedIn'		: 200, 
				'speedOut'		: 200, 
				'overlayColor'	: '#999999',
				'overlayShow'	: false,
				'scrolling'		: 'false',
				'titleShow'		: true,
				'showCloseButton' : true, 
				'hideOnOverlayClick': true
		  });
	  }
	  
	  if($("span.progress").length > 0)
	  {
		  $("span.progress").each(function(index){
			  $(this).progressBar();
		  });
	  }
});