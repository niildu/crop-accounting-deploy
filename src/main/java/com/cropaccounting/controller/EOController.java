package com.cropaccounting.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cropaccounting.models.CropActivityItem;
import com.cropaccounting.models.CropCalenderTask;
import com.cropaccounting.models.CropIncome;
import com.cropaccounting.models.CropIncomeList;
import com.cropaccounting.models.CropTaskMap;
import com.cropaccounting.models.Crops;
import com.cropaccounting.models.ExpenceItemValue;
import com.cropaccounting.models.IncomeItemValue;
import com.cropaccounting.models.Varieties;
import com.cropaccounting.models.area.AreaCropExpence;
import com.cropaccounting.models.area.AreaCropIncome;
import com.cropaccounting.models.area.District;
import com.cropaccounting.models.area.Division;
import com.cropaccounting.models.area.SubDistrict;
import com.cropaccounting.service.AreaService;
import com.cropaccounting.service.CropAccountingService;
import com.cropaccounting.service.EOService;

@Controller
public class EOController {
	private static final String REPLACE_NUMBER = "number:";
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CropAccountingService cropAccountingService;
	
	@Autowired
	private EOService eoService;
	
	@RequestMapping("/eo/expencelist")
	public void expencelist(Model model) {
		model.addAttribute("cropTaskMapList", cropAccountingService.getCropTaskMapList());
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("areaExpences", eoService.getAreaCropExpenceList());
		model.addAttribute("cropExpences", cropAccountingService.getCropExpenceLists());
	}
	
	@RequestMapping("/eo/createAreaTaskExpenditure")
	public void createTaskExpenditure(Optional<Long> id, Model model) {
		model.addAttribute("cropTaskMap", cropAccountingService.getExpenceListById(id.isPresent() ? id.get() : 0l));
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		//model.addAttribute("cropTaskMap", eoService.getAreaCropExpenceById(id.isPresent() ? id.get() : 0l));
	}
	
	@RequestMapping("/eo/updateAreaTaskExpenditure")
	public void updateAreaTaskExpenditure(Optional<Long> id, Model model) {
		//model.addAttribute("cropTaskMap", cropAccountingService.getExpenceListById(id.isPresent() ? id.get() : 0l));
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("cropTaskMap", eoService.getAreaCropExpenceById(id.isPresent() ? id.get() : 0l));
	}
	
	@PostMapping("/eo/submitAreaExpenditure")
	public String submitExpenditure(@ModelAttribute AreaCropExpence cropExpenceList,
			@RequestParam("cropTaskMapId") Optional<Long> cropTaskMapId,
			@RequestParam("cropExpenceList.expenceItemValue.cropActivityItem") Optional<String[]> cropActivityItems,
			@RequestParam("cropExpenceList.expenceItemValue.itemExpence") Optional<String[]> itemExpences,
			@RequestParam("cropExpenceList.expenceItemValue.labourExpence") Optional<String[]> labourExpences,
			@RequestParam("taskId") Optional<String[]> taskIds, Model model) {

		Optional<Division> division = areaService.getDivisionByPortalId(1l);
		Optional<District> district = areaService.getDistrictByPortalId(1l);
		Optional<SubDistrict> subDistrict = areaService.getSubDistrictByPortalId(1l);
		Optional<CropTaskMap> cropTaskMap = cropAccountingService
				.getCropTaskMap(cropTaskMapId.isPresent() ? cropTaskMapId.get() : 0l);
		Optional<AreaCropExpence> repoCropExpenceList = eoService.getAreaCropExpenceByType(cropTaskMap.get().getCrop(),
				cropTaskMap.get().getVarity(), division.get().getId(), district.get().getId(),
				subDistrict.get().getId());

		ExpenceItemValue expenceItemValue = null;
		Optional<CropActivityItem> cropActivityItem = null;
		Optional<CropCalenderTask> cropCalenderTask = null;
		List<ExpenceItemValue> expenceItemValueList = new ArrayList<>();
		BigDecimal itemExp = BigDecimal.ZERO;
		BigDecimal labourExp = BigDecimal.ZERO;
		for (int i = 0; cropActivityItems.isPresent() && i < cropActivityItems.get().length; i++) {
			expenceItemValue = new ExpenceItemValue();
			if (cropActivityItems.isPresent() && cropActivityItems.get()[i].length() > 0)
				cropActivityItem = cropAccountingService.getCropActivityItem(Long.parseLong(cropActivityItems.get()[i]));
			if (cropActivityItem.isPresent())
				expenceItemValue.setCropActivityItem(cropActivityItem.get());

			if (taskIds.isPresent() && taskIds.get()[i].length() > 0)
				cropCalenderTask = cropAccountingService.getCropCalenderTaskById(Long.parseLong(taskIds.get()[i]));

			if (cropCalenderTask.isPresent())
				expenceItemValue.setCropCalenderTask(cropCalenderTask.get());

			if (itemExpences.isPresent() && itemExpences.get()[i].length() > 0)
				itemExp = new BigDecimal(itemExpences.get()[i]);
			expenceItemValue.setItemExpence(itemExp);
			if (labourExpences.isPresent() && labourExpences.get()[i].length() > 0)
				labourExp = new BigDecimal(labourExpences.get()[i]);
			expenceItemValue.setLabourExpence(labourExp);

			expenceItemValueList.add(expenceItemValue);
		}
		if (repoCropExpenceList.isPresent())
			cropExpenceList = repoCropExpenceList.get();
		cropExpenceList.setAreaExpenceItemValueList(expenceItemValueList);
		cropExpenceList.setDivision(division.get());
		cropExpenceList.setDistrict(district.get());
		cropExpenceList.setSubDistrict(subDistrict.get());
		
		if (cropTaskMap.isPresent()) {
			cropExpenceList.setType(cropTaskMap.get().getType());
			cropExpenceList.setCrop(cropTaskMap.get().getCrop());
			cropExpenceList.setVarity(cropTaskMap.get().getVarity());
			eoService.saveAreaCropExpence(cropExpenceList);
		}
		model.addAttribute("cropExpences", cropAccountingService.getCropExpenceLists());
		model.addAttribute("areaExpences", cropAccountingService.getCropExpenceLists());
		return "redirect:/eo/expencelist";
	}
	
	@PostMapping("/eo/updateAreaExpenditure")
	public String updateExpenditure(@ModelAttribute AreaCropExpence cropExpenceList,
			@RequestParam("areaExpenseId") Optional<Long> areaExpenseId,
			@RequestParam("cropExpenceList.expenceItemValue.cropActivityItem") Optional<String[]> cropActivityItems,
			@RequestParam("cropExpenceList.expenceItemValue.itemExpence") Optional<String[]> itemExpences,
			@RequestParam("cropExpenceList.expenceItemValue.labourExpence") Optional<String[]> labourExpences,
			@RequestParam("taskId") Optional<String[]> taskIds, Model model) {
		Optional<AreaCropExpence> areaCropExpence = eoService
				.getAreaCropExpenceById(areaExpenseId.isPresent() ? areaExpenseId.get() : 0l);
		if (areaCropExpence.isPresent())
			cropExpenceList = areaCropExpence.get();
		Optional<AreaCropExpence> repoCropExpenceList = areaCropExpence;
		ExpenceItemValue expenceItemValue = null;
		Optional<CropActivityItem> cropActivityItem = null;
		Optional<CropCalenderTask> cropCalenderTask = null;
		List<ExpenceItemValue> expenceItemValueList = new ArrayList<>();
		BigDecimal itemExp = BigDecimal.ZERO;
		BigDecimal labourExp = BigDecimal.ZERO;
		for (int i = 0; cropActivityItems.isPresent() && i < cropActivityItems.get().length; i++) {
			expenceItemValue = new ExpenceItemValue();
			if (cropActivityItems.isPresent() && cropActivityItems.get()[i].length() > 0)
				cropActivityItem = cropAccountingService
						.getCropActivityItem(Long.parseLong(cropActivityItems.get()[i]));
			if (cropActivityItem.isPresent())
				expenceItemValue.setCropActivityItem(cropActivityItem.get());

			if (taskIds.isPresent() && taskIds.get()[i].length() > 0)
				cropCalenderTask = cropAccountingService.getCropCalenderTaskById(Long.parseLong(taskIds.get()[i]));

			if (cropCalenderTask.isPresent())
				expenceItemValue.setCropCalenderTask(cropCalenderTask.get());

			if (itemExpences.isPresent() && itemExpences.get()[i].length() > 0)
				itemExp = new BigDecimal(itemExpences.get()[i]);
			expenceItemValue.setItemExpence(itemExp);
			if (labourExpences.isPresent() && labourExpences.get()[i].length() > 0)
				labourExp = new BigDecimal(labourExpences.get()[i]);
			expenceItemValue.setLabourExpence(labourExp);

			expenceItemValueList.add(expenceItemValue);
		}
		if (repoCropExpenceList.isPresent())
			cropExpenceList = repoCropExpenceList.get();
		cropExpenceList.setAreaExpenceItemValueList(expenceItemValueList);

		eoService.saveAreaCropExpence(cropExpenceList);
		model.addAttribute("cropExpences", cropAccountingService.getCropExpenceLists());
		model.addAttribute("areaExpences", cropAccountingService.getCropExpenceLists());
		return "redirect:/eo/expencelist";
	}
	
	@RequestMapping("/eo/incomelist")
	public void incomelist(Model model) {
		model.addAttribute("cropTaskMapList", cropAccountingService.getCropTaskMapList());
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("areaIncomes", eoService.getAreaCropIncomeList());
		model.addAttribute("cropIncomes", cropAccountingService.getCropIncomeLists());
	}
	
	@RequestMapping("/eo/areacropearning")
	public void areacropearning(Optional<Long> id, Model model) {
		model.addAttribute("cropTaskMap", cropAccountingService.getCropIncomeListById(id.isPresent() ? id.get() : 0l));
		model.addAttribute("incomeItemList", cropAccountingService.getExpenceItemList());
		//model.addAttribute("cropTaskMap", eoService.getAreaCropExpenceById(id.isPresent() ? id.get() : 0l));
	}
	
	@PostMapping("/eo/submitAreaEarnings")
	public String submitAreaEarnings(@ModelAttribute AreaCropIncome cropIncomeList,
			/*@RequestParam("areaIncomeId") Optional<Long> areaIncomeId,*/
			@RequestParam("templateIncomeId") Optional<Long> templateIncomeId,
			@RequestParam("type") Optional<String> type,
			@RequestParam("cropId") Optional<String> cropIdStr, @RequestParam("variety") Optional<String> varity,
			@RequestParam("income") String[] cropIncomeItems, @RequestParam("incomeItems") String[] items,
			@RequestParam("days") String[] days, @RequestParam("amounts") String[] amounts,
			@RequestParam("values") String[] values, Model model) {
		
		Optional<Division> division = areaService.getDivisionByPortalId(1l);
		Optional<District> district = areaService.getDistrictByPortalId(1l);
		Optional<SubDistrict> subDistrict = areaService.getSubDistrictByPortalId(1l);
		Optional<CropIncomeList> templateIncomeList = cropAccountingService
				.getCropIncomeListById(templateIncomeId.isPresent() ? templateIncomeId.get() : 0l);
		
		if (!templateIncomeList.isPresent())
			return "redirect:/eo/incomelist";

		cropIncomeList.setCrop(templateIncomeList.get().getCrop());
		cropIncomeList.setCropId(templateIncomeList.get().getCropId());
		cropIncomeList.setCropName(templateIncomeList.get().getCropName());
		cropIncomeList.setVarity(templateIncomeList.get().getVarity());
		cropIncomeList.setVarityName(templateIncomeList.get().getVarityName());
//		Optional<AreaCropIncome> areaCropIncome = eoService.getAreaCropIncomeById(areaIncomeId.isPresent() ? areaIncomeId.get() : 0l);
//		if (areaCropIncome.isPresent())
//			cropIncomeList = areaCropIncome.get();
		
		Optional<AreaCropIncome> repoCropIncomeList = eoService.getAreaCropIncomeByType(templateIncomeList.get().getCrop(),
				templateIncomeList.get().getVarity(), division.get().getId(), district.get().getId(),
				subDistrict.get().getId());
		
		if (repoCropIncomeList.isPresent())
			cropIncomeList = repoCropIncomeList.get();
		
		//type.ifPresent(theType -> cropIncomeList.setType(theType.split(":")[1]));
		if (cropIdStr.isPresent()) {
			String idString = cropIdStr.get(); 
			cropIncomeList.setCropId(idString.replace(REPLACE_NUMBER, ""));
			cropIncomeList.setCrop(Long.parseLong(cropIncomeList.getCropId()));
			Optional<Crops> protalCrops = cropAccountingService.getCropsById(cropIncomeList.getCrop());
			if (protalCrops.isPresent()) {
				cropIncomeList.setCropName(protalCrops.get().getName());
				cropIncomeList.setType(protalCrops.get().getType());
			}
			
			/*cropIncomeList.setVarity(Long.parseLong(idString.split("-")[1]));
			Optional<Varieties> protalVarity = cropAccountingService.getVarietiesById(cropIncomeList.getVarity());
			if (protalVarity.isPresent())
				cropIncomeList.setVarityName(protalVarity.get().getName());*/
		}
		
		if (varity.isPresent()) {
			String theVariety = varity.get();
			cropIncomeList.setVarity(Long.parseLong(theVariety.replace(REPLACE_NUMBER, "")));
			Optional<Varieties> protalVarity = cropAccountingService.getVarietiesById(cropIncomeList.getVarity());
			if (protalVarity.isPresent())
				cropIncomeList.setVarityName(protalVarity.get().getName());
		}
		
		IncomeItemValue incomeItemValue = null;
		Optional<CropIncome> cropIncome = null;
		List<IncomeItemValue> incomeItemValueList = new ArrayList<>();

		for (int i = 0; cropIncomeItems != null && i < cropIncomeItems.length; i++) {
			incomeItemValue = new IncomeItemValue();
			if (cropIncomeItems[i] != null && cropIncomeItems[i].length() > 0 && cropIncomeItems[i].indexOf("_") > 0) {
				String id = cropIncomeItems[i].split("_")[1];
				cropIncome = cropAccountingService.getCropIncomeById(Long.parseLong(id));
				if (cropIncome.isPresent())
					incomeItemValue.setCropIncome(cropIncome.get());
				//incomeItemValue.setType(items[i]);
				if (items[i] != null && items[i].length() > 0)
					incomeItemValue.setType(items[i]);
				if (amounts[i] != null && amounts[i].length() > 0)
					incomeItemValue.setAmount(new BigDecimal(amounts[i]));
				if (days[i] != null && days[i].length() > 0)
					incomeItemValue.setDay(Integer.parseInt(days[i]));
				if (values[i] != null && values[i].length() > 0)
					incomeItemValue.setTotValue(new BigDecimal(values[i]));
				incomeItemValueList.add(incomeItemValue);
			}
		}
		cropIncomeList.setAreaIncomeItemValueList(incomeItemValueList);
		cropIncomeList.setDivision(division.get());
		cropIncomeList.setDistrict(district.get());
		cropIncomeList.setSubDistrict(subDistrict.get());
		eoService.saveAreaCropIncome(cropIncomeList);
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("areaIncomes", eoService.getAreaCropIncomeList());
		model.addAttribute("cropIncomes", cropAccountingService.getCropIncomeLists());
		return "redirect:/eo/incomelist";
	}
	
	@RequestMapping("/eo/updateareacropearning")
	public void updateareacropearning(Optional<Long> id, Model model) {
		model.addAttribute("cropTaskMap", eoService.getAreaCropIncomeById(id.isPresent() ? id.get() : 0l));
		model.addAttribute("incomeItemList", cropAccountingService.getExpenceItemList());
		//model.addAttribute("cropTaskMap", eoService.getAreaCropExpenceById(id.isPresent() ? id.get() : 0l));
	}
	
	@PostMapping("/eo/updateAreaEarnings")
	public String updateAreaEarnings(@ModelAttribute AreaCropIncome cropIncomeList,
			@RequestParam("areaIncomeId") Optional<Long> areaIncomeId,
			@RequestParam("type") Optional<String> type,
			@RequestParam("cropId") Optional<String> cropIdStr, @RequestParam("variety") Optional<String> varity,
			@RequestParam("income") String[] cropIncomeItems, @RequestParam("incomeItems") String[] items,
			@RequestParam("days") String[] days, @RequestParam("amounts") String[] amounts,
			@RequestParam("values") String[] values, Model model) {
		
		Optional<AreaCropIncome> repoCropIncomeList = eoService
				.getAreaCropIncomeById(areaIncomeId.isPresent() ? areaIncomeId.get() : 0l);
		
		if (!repoCropIncomeList.isPresent())
			return "redirect:/eo/incomelist";

		cropIncomeList = repoCropIncomeList.get();
		IncomeItemValue incomeItemValue = null;
		Optional<CropIncome> cropIncome = null;
		List<IncomeItemValue> incomeItemValueList = new ArrayList<>();

		for (int i = 0; cropIncomeItems != null && i < cropIncomeItems.length; i++) {
			incomeItemValue = new IncomeItemValue();
			if (cropIncomeItems[i] != null && cropIncomeItems[i].length() > 0 && cropIncomeItems[i].indexOf("_") > 0) {
				String id = cropIncomeItems[i].split("_")[1];
				cropIncome = cropAccountingService.getCropIncomeById(Long.parseLong(id));
				if (cropIncome.isPresent())
					incomeItemValue.setCropIncome(cropIncome.get());
				//incomeItemValue.setType(items[i]);
				if (items[i] != null && items[i].length() > 0)
					incomeItemValue.setType(items[i]);
				if (amounts[i] != null && amounts[i].length() > 0)
					incomeItemValue.setAmount(new BigDecimal(amounts[i]));
				if (days[i] != null && days[i].length() > 0)
					incomeItemValue.setDay(Integer.parseInt(days[i]));
				if (values[i] != null && values[i].length() > 0)
					incomeItemValue.setTotValue(new BigDecimal(values[i]));
				incomeItemValueList.add(incomeItemValue);
			}
		}
		cropIncomeList.setAreaIncomeItemValueList(incomeItemValueList);
		eoService.saveAreaCropIncome(cropIncomeList);
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("areaIncomes", eoService.getAreaCropIncomeList());
		model.addAttribute("cropIncomes", cropAccountingService.getCropIncomeLists());
		return "redirect:/eo/incomelist";
	}
}
