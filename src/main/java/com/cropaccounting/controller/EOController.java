package com.cropaccounting.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cropaccounting.models.CropIncomeList;
import com.cropaccounting.models.CropTaskMap;
import com.cropaccounting.models.ExpenceItemValue;
import com.cropaccounting.models.Farmer;
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
import com.cropaccounting.util.PageWrapper;

@Controller
public class EOController {
	private static final String REPLACE_NUMBER = "number:";
	private static int pageSize = 20;

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CropAccountingService cropAccountingService;
	
	@Autowired
	private EOService eoService;
	
	@RequestMapping("/eo/expencelist")
	public void expencelist(Model model) {
		model.addAttribute("crops", cropAccountingService.getCropsList().stream().collect(Collectors.groupingBy(crop -> "" + crop.getId())));
		model.addAttribute("varieties", cropAccountingService.getVarietyList().stream()
				.collect(Collectors.groupingBy(variety -> "" + variety.getId())));
		model.addAttribute("cropTaskMapList", cropAccountingService.getCropTaskMapList());
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("areaExpences", eoService.getAreaCropExpenceList());
		model.addAttribute("cropExpences", cropAccountingService.getCropExpenceLists());
	}
	
	@RequestMapping("/eo/createAreaTaskExpenditure")
	public void createTaskExpenditure(Optional<Long> id, Model model) {
		model.addAttribute("cropTaskMap", cropAccountingService.getExpenceListById(id.isPresent() ? id.get() : 0l));
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
	}
	
	@RequestMapping("/eo/updateAreaTaskExpenditure")
	public void updateAreaTaskExpenditure(Optional<Long> id, Model model) {
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

		List<ExpenceItemValue> expenceItemValueList = new ArrayList<>();
		for (int i = 0; cropActivityItems.isPresent() && i < cropActivityItems.get().length; i++) {
			expenceItemValueList.add(cropAccountingService.getCropExpence(i, cropActivityItems, taskIds, itemExpences, labourExpences));
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
		List<ExpenceItemValue> expenceItemValueList = new ArrayList<>();
		for (int i = 0; cropActivityItems.isPresent() && i < cropActivityItems.get().length; i++) {
			expenceItemValueList.add(cropAccountingService.getCropExpence(i, cropActivityItems, taskIds, itemExpences, labourExpences));
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
	}
	
	@PostMapping("/eo/submitAreaEarnings")
	public String submitAreaEarnings(@ModelAttribute AreaCropIncome cropIncomeList,
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

		cropIncomeList.setCrop(templateIncomeList.get().getCrop())
			.setCropId(templateIncomeList.get().getCropId())
			.setCropName(templateIncomeList.get().getCropName())
			.setVarity(templateIncomeList.get().getVarity())
			.setVarityName(templateIncomeList.get().getVarityName());
		
		Optional<AreaCropIncome> repoCropIncomeList = eoService.getAreaCropIncomeByType(templateIncomeList.get().getCrop(),
				templateIncomeList.get().getVarity(), division.get().getId(), district.get().getId(),
				subDistrict.get().getId());
		
		if (cropIdStr.isPresent()) {
			String idString = cropIdStr.get(); 
			cropIncomeList.setCropId(idString.replace(REPLACE_NUMBER, ""));
			cropIncomeList.setCrop(Long.parseLong(cropIncomeList.getCropId()));
			cropAccountingService.getCropsById(cropIncomeList.getCrop()).ifPresent(portalCrop -> {
				cropIncomeList.setCropName(portalCrop.getName());
				cropIncomeList.setType(portalCrop.getType());
			});
		}
		
		if (varity.isPresent()) {
			String theVariety = varity.get();
			cropIncomeList.setVarity(Long.parseLong(theVariety.replace(REPLACE_NUMBER, "")));
			Optional<Varieties> protalVarity = cropAccountingService.getVarietiesById(cropIncomeList.getVarity());
			if (protalVarity.isPresent())
				cropIncomeList.setVarityName(protalVarity.get().getName());
		}
		List<IncomeItemValue> incomeItemValueList = new ArrayList<>();
		for (int i = 0; cropIncomeItems != null && i < cropIncomeItems.length; i++) {
			if (cropIncomeItems[i] != null && cropIncomeItems[i].length() > 0 && cropIncomeItems[i].indexOf("_") > 0) {
				incomeItemValueList.add(cropAccountingService.getIncomeDetail(i, cropIncomeItems, items, amounts, days, values));
			}
		}
		cropIncomeList.setAreaIncomeItemValueList(incomeItemValueList)
			.setDivision(division.get())
			.setDistrict(district.get())
			.setSubDistrict(subDistrict.get());
		
		if (repoCropIncomeList.isPresent()) {
			repoCropIncomeList.get().getAreaIncomeItemValueList().clear();
			repoCropIncomeList.get().getAreaIncomeItemValueList().addAll(incomeItemValueList);
			eoService.saveAreaCropIncome(repoCropIncomeList.get());
		} else
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
		List<IncomeItemValue> incomeItemValueList = new ArrayList<>();
		for (int i = 0; cropIncomeItems != null && i < cropIncomeItems.length; i++) {
			incomeItemValueList.add(cropAccountingService.getIncomeDetail(i, cropIncomeItems, items, amounts, days, values));
		}
		cropIncomeList.setAreaIncomeItemValueList(incomeItemValueList);
		eoService.saveAreaCropIncome(cropIncomeList);
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		model.addAttribute("areaIncomes", eoService.getAreaCropIncomeList());
		model.addAttribute("cropIncomes", cropAccountingService.getCropIncomeLists());
		return "redirect:/eo/incomelist";
	}
	
	@RequestMapping(value = "/eo/farmerlist", method = RequestMethod.GET)
	public String listBooks(Model model, @RequestParam(defaultValue = "0") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
//		page.ifPresent(p -> currentPage = p);
//		size.ifPresent(s -> pageSize = s);
//		Page<Farmer> bookPage = eoService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
//		model.addAttribute("bookPage", bookPage);
//		int totalPages = bookPage.getTotalPages();
//		if (totalPages > 0) {
//			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//					.boxed()
//					.collect(Collectors.toList());
//			model.addAttribute("pageNumbers", pageNumbers);
//		}
		PageWrapper<Farmer> pagew = new PageWrapper<>(
				eoService.findPaginated(PageRequest.of(page.isPresent() ? page.get().intValue() : 0, 3)), "/eo/farmerlist"); 
		model.addAttribute("page", pagew);
		//model.addAttribute("page", eoService.findPaginated(PageRequest.of(page.isPresent() ? page.get().intValue() : 0, pageSize)));
        model.addAttribute("currentPage", page);
		return "/eo/farmerlist";
	}
}
