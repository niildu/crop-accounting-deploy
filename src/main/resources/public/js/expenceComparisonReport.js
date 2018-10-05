function drawComparisonChart(expenceComparison, config) {
	var taskCategories = [];
	var countryTaskExpence = [];
	var areaTaskExpence = [];
	for (var key in expenceComparison) {
		console.log(key);
		var comparisonrow =  expenceComparison[key];
		for (var i = 0; i < comparisonrow.length; i++) {
			var areatask = comparisonrow[i].areaDetails;
        	var croptask = comparisonrow[i].cropDetails;
        	var taskKey = comparisonrow[i].cropName + '(' + comparisonrow[i].varietyName + ')'
        		+(croptask != null && croptask.activityName != null ? croptask.activityName : '')
        		+ '-' + 
        		(croptask != null && croptask.taskName != null ? croptask.taskName : '');
        	taskCategories.push(taskKey);
        	countryTaskExpence.push(croptask != null ? (-1) * croptask.labourExpence : 0);
        	areaTaskExpence.push(areatask != null ? areatask.labourExpence : 0);
		}
	}
     
    //console.log(taskCategories);
     

    Highcharts.chart('comparison', {
        chart: {
            type: 'bar'
        },
        //var year = moment().format('YYYY'); var year = moment().year();Aug 4, 2014
        title: {
            text: config.heading
        },
        subtitle: {
            text: 'Source: <a href="http://populationpyramid.net/germany/2018/">' 
            +	config.heading 
            +	'</a>'
        },
        xAxis: [{
            categories: taskCategories,// categories,
            reversed: false,
            labels: {
                step: 1
            }
        }/* , 
        { // mirror axis on right side
            opposite: false,
            reversed: false,
            categories: taskCategories, //categories,
            linkedTo: 0,
            labels: {
                step: 1
            }
        } */],
        yAxis: {
            title: {
                text: null
            }/* ,
            labels: {
                formatter: function () {
                    return this.value;//Math.abs(this.value) + '%';
                }
            } */
         },
         credits: {
             enabled: false
         },
         plotOptions: {
             series: {
                 stacking: 'normal'
             }
         },

         tooltip: {
             formatter: function () {
                 return '<b>' + this.series.name + ', ' + config.task + '-' + this.point.category + '</b><br/>' +
                     config.expence + ': ' + Highcharts.numberFormat(Math.abs(this.point.y), 0);
             }
         },

         series: [{
             name: config.expencecountry,
             data: countryTaskExpence
         }, {
             name: config.expencearea,
             data: areaTaskExpence
         }]
     });
}