function drawMonthWiseProductionChart(productionData, config) {
	var monthWiseProductionData = {'jan' : 0, 'feb' : 0, 'mar' : 0, 'apr' : 0, 'may' : 0, 'jun' : 0,
			'jul' : 0, 'aug' : 0, 'sep' : 0, 'oct' : 0, 'nov' : 0, 'dec' : 0};
	var monthlyProductionData = new Array();

	function getProdIteminProdArray(needle, haystack) {
		var length = haystack.length;
		for (var i = 0; i < length; i++) {
			if (haystack[i].name == needle)
				return haystack[i];
		}
	}

	function inProdArray(needle, haystack) {
		var length = haystack.length;
		for (var i = 0; i < length; i++) {
			if (haystack[i].name == needle)
				return true;
		}
		return false;
	}

	function populateMonthData(monthWiseProductionData, row, keyindex, dataindex) {
		switch (row[keyindex]) {
		case 'January':
			monthWiseProductionData[0] = row[dataindex];
			break;
		case 'February':
			monthWiseProductionData[1] = row[dataindex];
			break;
		case 'March':
			monthWiseProductionData[2] = row[dataindex];
			break;
		case 'April':
			monthWiseProductionData[3] = row[dataindex];
			break;
		case 'May':
			monthWiseProductionData[4] = row[dataindex];
			break;
		case 'June':
			monthWiseProductionData[5] = row[dataindex];
			break;
		case 'July':
			monthWiseProductionData[6] = row[dataindex];
			break;
		case 'August':
			monthWiseProductionData[7] = row[dataindex];
			break;
		case 'September':
			monthWiseProductionData[8] = row[dataindex];
			break;
		case 'October':
			monthWiseProductionData[9] = row[dataindex];
			break;
		case 'November':
			monthWiseProductionData[10] = row[dataindex];
			break;
		case 'December':
			monthWiseProductionData[11] = row[dataindex];
			break;
		default:
			break;
		}
	}
	
	for (var i = 0; i < productionData.length; i++) {
		var row = productionData[i];
		var key = row[1] + '-' + row[3];
		
		if (!inProdArray(key, monthlyProductionData)) {
			var newData = [0,0,0,0,0,0,0,0,0,0,0,0];
			populateMonthData(newData, row, 0, 2);
			var cropDetail = {'name' : key, 'data': newData};
			monthlyProductionData.push(cropDetail);
		} else {
			cropDetail = getProdIteminProdArray(key, monthlyProductionData);
			populateMonthData(cropDetail.data, row, 0, 2);
		}
	}
	console.log(monthlyProductionData);
	/*
	[{
        name: 'Tokyo',
        data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

    }, {
        name: 'New York',
        data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

    }, {
        name: 'London',
        data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

    }, {
        name: 'Berlin',
        data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

    }]
	
	*/
	
	var productionChart = Highcharts.chart('production', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Monthly Average Rainfall'
	    },
	    subtitle: {
	        text: 'Source: WorldClimate.com'
	    },
	    xAxis: {
	    	categories: [config.month.jan, config.month.feb, config.month.mar, config.month.apr, 
            	config.month.may, config.month.jun, config.month.jul, config.month.aug, config.month.sep, 
            	config.month.oct, config.month.nov, config.month.dec],
	        crosshair: true
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Rainfall (mm)'
	        }
	    },
	    tooltip: {
	        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
	        footerFormat: '</table>',
	        shared: true,
	        useHTML: true
	    },
	    plotOptions: {
	        column: {
	            pointPadding: 0.2,
	            borderWidth: 0
	        }
	    },
	    series: monthlyProductionData
	    /*[{
	        name: 'Tokyo',
	        data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
	
	    }, {
	        name: 'New York',
	        data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
	
	    }, {
	        name: 'London',
	        data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
	
	    }, {
	        name: 'Berlin',
	        data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
	
	    }]*/
	});
}