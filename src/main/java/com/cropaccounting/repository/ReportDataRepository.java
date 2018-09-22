package com.cropaccounting.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cropaccounting.beans.ReportExpenceCompareDetails;
import com.cropaccounting.beans.ReportExpenceCompareDetails.ReportExpenceDetails;
import com.cropaccounting.models.CropExpenceList;
import com.cropaccounting.models.Crops;
import com.cropaccounting.models.ExpenceItemValue;
import com.cropaccounting.models.Varieties;
import com.cropaccounting.models.area.AreaCropExpence;
import com.cropaccounting.service.CropAccountingService;
import com.cropaccounting.service.EOService;

@Component
public class ReportDataRepository {

	@Autowired
	private EntityManager em;

	@Autowired
	private CropAccountingService cropAccountingService;

	@Autowired
	private EOService eoService;

	@SuppressWarnings("unchecked")
	public List<Object[]> getLandOwnershipData() /* throws JSONException */ {
		String sql = "	SELECT (CASE WHEN land_owner = 'Yes' THEN 'স্বয়ং' WHEN land_owner = 'No' THEN 'ইজারা' END) as name, COUNT(cr.id) y"
				+ "		FROM crop cr WHERE land_owner is not null GROUP BY land_owner";
		return em.createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getRegistrationData() {
		String sql = "	SELECT SUM(total) total, cropname, monthname FROM (SELECT COUNT(cr.id) total, cr.name as cropname,"
				+ "		(CASE " + "		WHEN to_char(reg.start_date,'Mon') = 'Jan' THEN 'January'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Feb' THEN 'February'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Mar' THEN 'March'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Apr' THEN 'April'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'May' THEN 'May'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Jun' THEN 'June'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Jul' THEN 'July'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Aug' THEN 'August'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Sep' THEN 'September'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Oct' THEN 'October'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Nov' THEN 'November'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Dec' THEN 'December'" + "		END) AS monthname"
				+ "		FROM crop reg, crops cr" + "		WHERE cr.id = reg.crop"
				+ " 	GROUP BY to_char(reg.start_date,'Mon'),cr.name) AS tmp"
				+ "		GROUP BY tmp.monthname, tmp.cropname";
		return em.createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getLandCropSum() {
		String sql = "	SELECT cropname as name, SUM(totalLand) y "
				+ "		from (SELECT cr.name as cropname, sum(reg.land_total_in_default) as totalLand"
				+ "		FROM crop reg, crops cr"
				+ "		WHERE cr.id = reg.crop 	GROUP BY reg.crop_unit, cr.name) AS tmp"
				+ "		GROUP BY tmp.cropname";
		return em.createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getCropProduction() {
		String sql = "	SELECT monthname, cropname as name, SUM(production) y, type "
				+ "		from (SELECT cr.name as cropname, sum(reg.land_total_in_default * val.amount) as production, val.type,"
				+ "		(CASE " + "		WHEN to_char(reg.start_date,'Mon') = 'Jan' THEN 'January'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Feb' THEN 'February'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Mar' THEN 'March'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Apr' THEN 'April'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'May' THEN 'May'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Jun' THEN 'June'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Jul' THEN 'July'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Aug' THEN 'August'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Sep' THEN 'September'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Oct' THEN 'October'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Nov' THEN 'November'"
				+ "		WHEN to_char(reg.start_date,'Mon') = 'Dec' THEN 'December'" + "		END) AS monthname"
				+ "		FROM crop reg, crops cr, area_crop_income ar, "
				+ "		area_crop_income_area_income_item_value_list armap, income_item_value val"
				+ "		WHERE cr.id = reg.crop AND cr.id = ar.crop AND armap.area_crop_income_id = ar.id"
				+ "		AND armap.area_income_item_value_list_id = val.id "
				+ "		GROUP BY to_char(reg.start_date,'Mon'), val.type, cr.name) AS tmp"
				+ "		GROUP BY tmp.type, tmp.monthname, tmp.cropname "
				+ "		ORDER BY tmp.monthname, tmp.cropname, tmp.type";
		return em.createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getExpenceComparison() {
		String sql = " SELECT arex.crop maincrop, arex.varity mainvariety, cratv.name	 mainactivity, crtp.name cractype, crac.labour_expence mainexpence,"
				+ " arex.crop areacrop, arex.varity areavariety, aratv.name areaactivity,	 artp.name aractype, arac.labour_expence areaexpence"
				+ " FROM crop_expence_list crex, crop_expence_list_expence_item_value_list	 crmap,"
				+ " expence_item_value crac, crop_activity_item cract, crop_calender_task	 crtsk, crop_activity cratv,"
				+ " crop_activity_item crit, crop_activity_type crtp,"
				+ " area_crop_expence arex, area_crop_expence_area_expence_item_value_list	 armap,"
				+ " expence_item_value arac, crop_activity_item aract, crop_calender_task	 artsk, crop_activity aratv,"
				+ " crop_activity_item arit, crop_activity_type artp"
				+ " WHERE crex.id = crmap.crop_expence_list_id and	 crmap.expence_item_value_list_id = crac.id"
				+ " AND crac.crop_calender_task_id = crtsk.id and crtsk.crop_activity_id =	 cratv.id and crtsk.crop_activity_type_id = crtp.id"
				+ " AND arex.id = armap.area_crop_expence_id and	 armap.area_expence_item_value_list_id = arac.id"
				+ " AND arac.crop_calender_task_id = artsk.id and artsk.crop_activity_id =	 aratv.id and artsk.crop_activity_type_id = artp.id"
				+ " AND arex.crop = crex.crop and arex.varity = crex.varity AND cratv.name =	 aratv.name"
				+ " AND crit.name = arit.name and crtp.name = artp.name";
		return em.createNativeQuery(sql).getResultList();
	}

	public Map<String, List<ReportExpenceCompareDetails>> getComparisonOfLabour() {
		List<AreaCropExpence> areaExpence = eoService.getAreaCropExpenceList();
		List<CropExpenceList> cropExpences = cropAccountingService.getCropExpenceLists();
		List<Crops> cropsList = cropAccountingService.getCropsList();
		List<Varieties> varietyList = cropAccountingService.getVarietyList();
		Map<String, List<ReportExpenceCompareDetails>> areaExp = new HashMap<>();
		areaExpence.stream().forEach(expence -> {
			List<ReportExpenceCompareDetails> compareDetails = getAreaExpence(expence).stream().map(theexpence -> {
				return new ReportExpenceCompareDetails().setAreaDetails(theexpence);
			}).collect(Collectors.toList());
			areaExp.put("" + expence.getCrop(), compareDetails);
		});
		cropExpences.stream().forEach(expence -> {
			List<ReportExpenceCompareDetails> compareDetails = getCropExpence(expence).stream().map(theexpence -> {
				Crops crop = cropsList.stream().filter(crops -> crops.getId() == expence.getCrop()).findFirst()
						.orElse(null);
				Varieties variety = varietyList.stream().filter(thevariety -> thevariety.getId() == expence.getVarity())
						.findFirst().orElse(null);
				return new ReportExpenceCompareDetails().setCropName(crop != null ? crop.getName() : "")
						.setVarietyName(variety != null ? variety.getName() : "").setCropDetails(theexpence);
			}).collect(Collectors.toList());
			List<ReportExpenceCompareDetails> areaExpences = areaExp.get("" + expence.getCrop());
			compareDetails.forEach(cropExpence -> setCropExpence(cropExpence, areaExpences));
			areaExp.put("" + expence.getCrop(), compareDetails);
		});
		return areaExp;
	}

	private void setCropExpence(ReportExpenceCompareDetails cropExpence,
			List<ReportExpenceCompareDetails> areaExpences) {
		if (areaExpences != null) {
			ReportExpenceCompareDetails areaExpence = areaExpences.stream().filter(exp -> {
				return exp.getAreaDetails().getCropId() == cropExpence.getCropDetails().getCropId()
						&& exp.getAreaDetails().getActivityName().equals(cropExpence.getCropDetails().getActivityName())
						&& exp.getAreaDetails().getTaskName().equals(cropExpence.getCropDetails().getTaskName());
			}).findFirst().orElse(null);
			cropExpence.setAreaDetails(areaExpence.getAreaDetails());
		}
	}

	private List<ReportExpenceDetails> getAreaExpence(AreaCropExpence areaCropExpence) {
		return areaCropExpence.getAreaExpenceItemValueList().stream()
				.map(expenceItem -> populateReportExpence(areaCropExpence.getCrop(), expenceItem))
				.collect(Collectors.toList());
	}

	private ReportExpenceDetails populateReportExpence(long cropId, ExpenceItemValue expenceItem) {
		ReportExpenceDetails row = new ReportExpenceDetails();
		row.setActivityName(expenceItem.getCropCalenderTask().getCropActivity() != null
				? expenceItem.getCropCalenderTask().getCropActivity().getName()
				: "");
		row.setTaskName(expenceItem.getCropCalenderTask().getCropActivityType() != null
				? expenceItem.getCropCalenderTask().getCropActivityType().getName()
				: "");
		row.setLabourExpence(expenceItem.getLabourExpence() != null ? expenceItem.getLabourExpence() : BigDecimal.ZERO);
		row.setCropId(cropId);
		return row;
	}

	private List<ReportExpenceDetails> getCropExpence(CropExpenceList cropExpenceList) {
		return cropExpenceList.getExpenceItemValueList().stream()
				.map(expenceItem -> populateReportExpence(cropExpenceList.getCrop(), expenceItem))
				.collect(Collectors.toList());
	}
}
