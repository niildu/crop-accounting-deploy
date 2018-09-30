function drawLandOwnerShipChart(data, config) {
    var salesByRegionChart = Highcharts.chart('landownership', {
        chart: {
            type: 'pie',
            margin: 40,
            options3d: {
        		enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: config.heading
        },
        tooltip: {
          	//pointFormat: "${point.y:,.0f}"
        },
        plotOptions: {
        	pie: {
        		allowPointSelect: true,
        		depth: 35
        	}
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'সংখ্যা',
            colorByPoint:true,
            data: data
    	}]
    });
}