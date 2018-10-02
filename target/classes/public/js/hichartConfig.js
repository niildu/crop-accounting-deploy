$(function () { 
    Highcharts.setOptions({
        lang: {
            decimalPoint: '.',
            thousandsSep: ','
        }
    });
    
   var converters = {
   	    // Latin to Farsi
   	    fa: function (number) {
   	        return number.toString().replace(/\d/g, function (d) {
   	            return String.fromCharCode(d.charCodeAt(0) + 1728);
   	        });
   	    },
   	    // Latin to Arabic
   	    //2432-2559
   	    bn: function (number) {
   	        return number.toString().replace(/\d/g, function (d) {
   	        	//console.log(d.charCodeAt(0));
   	            return String.fromCharCode(d.charCodeAt(0) + (2534 - 48));
   	        });
   	    }
   	};

   	Highcharts.setOptions({
   	    lang: {
   	        decimalPoint: '\u002E'//,thousandsSeparator: '\u09EF'
   	    }
   	});

   	Highcharts.wrap(Highcharts, 'numberFormat', function (proceed) {
   	    var ret = proceed.apply(0, [].slice.call(arguments, 1));
   	    return converters.bn(ret);
   	});
});