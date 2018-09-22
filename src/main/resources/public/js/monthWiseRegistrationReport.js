var monthWiseData = {'jan' : 0, 'feb' : 0, 'mar' : 0, 'apr' : 0, 'may' : 0, 'jun' : 0,
        			'jul' : 0, 'aug' : 0, 'sep' : 0, 'oct' : 0, 'nov' : 0, 'dec' : 0};
var cropRegistrationData = new Array();

function getItemInArray(needle, haystack) {
    var length = haystack.length;
    for(var i = 0; i < length; i++) {
        if(haystack[i].name == needle)
            return haystack[i];
    }
}

function inArray(needle, haystack) {
    var length = haystack.length;
    for(var i = 0; i < length; i++) {
        if(haystack[i].name == needle)
            return true;
    }
    return false;
}

function populateMonthData(monthWiseData, row) {
	switch (row[2]) {
	case 'January' :
		monthWiseData[0] = row[0];
		break;
	case 'February' :
		monthWiseData[1] = row[0];
		break;
	case 'March' :
		monthWiseData[2] = row[0];
		break;
	case 'April' :
		monthWiseData[3] = row[0];
		break;
	case 'May' :
		monthWiseData[4] = row[0];
		break;
	case 'June' :
		monthWiseData[5] = row[0];
		break;
	case 'July' :
		monthWiseData[6] = row[0];
		break;
	case 'August' :
		monthWiseData[7] = row[0];
		break;
	case 'September' :
		monthWiseData[8] = row[0];
		break;
	case 'October' :
		monthWiseData[9] = row[0];
		break;
	case 'November' :
		monthWiseData[10] = row[0];
		break;
	case 'December' :
		monthWiseData[11] = row[0];
		break;
	default:
		break;
	}
}

function drawMonthWiseRegistrationChart(registrationData, config) {
	for (var i = 0; i < registrationData.length; i++) {
		var row = registrationData[i];
		if (!inArray(row[1], cropRegistrationData)) {
			var newData = [0,0,0,0,0,0,0,0,0,0,0,0];
			populateMonthData(newData, row);
			var cropDetail = {'name' : row[1], 'data': newData};
			cropRegistrationData.push(cropDetail);
		} else {
			cropDetail = getItemInArray(row[1], cropRegistrationData);
			populateMonthData(cropDetail.data, row);
		}
	}

	var salesByTypeChart = Highcharts.chart('salesByType', {
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
					enabled: true,
            	alpha: 2,
            	beta: 2,
            	depth: 50
            }
        },
        title: {
            text: config.heading
        },
        xAxis: {
            categories: [config.month.jan, config.month.feb, config.month.mar, config.month.apr, 
            	config.month.may, config.month.jun, config.month.jul, config.month.aug, config.month.sep, 
            	config.month.oct, config.month.nov, config.month.dec]
        },
        yAxis: {
            title: {
                text: config.yaxis
            }
        },
        tooltip: {
          	//pointFormat: "${point.y:,.0f}"
        },
        plotOptions: {
        	column: {
        		depth: 60,
          		stacking: true,
           		grouping: false,
          		groupZPadding: 10
            }
        },
        credits: {
            enabled: false
        },
        series: cropRegistrationData
        	/*
        	[{
        	data:cropRegistrationData,
        	dataLabels: {
                enabled: true,
                format: '{y:.1f}',
                style: {
                    fontSize: '13px'
                }
            }
        	}]*/
    });
}