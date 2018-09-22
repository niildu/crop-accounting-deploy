package com.cropaccounting.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cropaccounting.models.Crop;
import com.cropaccounting.models.CropExpenceList;
import com.cropaccounting.models.CropIncomeList;
import com.cropaccounting.models.Crops;
import com.cropaccounting.models.ExpenceItem;
import com.cropaccounting.models.ExpenceItemValue;
import com.cropaccounting.models.Farmer;
import com.cropaccounting.models.FarmerCropTask;
import com.cropaccounting.models.FarmerTask;
import com.cropaccounting.models.UserModel;
import com.cropaccounting.models.Varieties;
import com.cropaccounting.models.area.AreaCropExpence;
import com.cropaccounting.models.area.District;
import com.cropaccounting.models.area.Division;
import com.cropaccounting.models.area.SubDistrict;
import com.cropaccounting.service.AreaService;
import com.cropaccounting.service.CropAccountingService;
import com.cropaccounting.service.EOService;
import com.cropaccounting.util.UnitEnum;

@Controller
public class UdcController {
	private static final String REPLACE_NUMBER = "number:";
	private static final String REPLACE_STRING = "string:";
	
	@Autowired
	private CropAccountingService cropAccountingService;
	
	@Autowired
	private EOService eoService;
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/app/farmercropearning")
	public void farmercropearning(@RequestParam("nid") Optional<String> nid, Model model) {

		LocalDate startDate = LocalDate.now();
		startDate = startDate.minusMonths(6);
		List<Crop> crops = cropAccountingService.getCropByNID(nid.isPresent() ? nid.get() : "", startDate);

		Map<String, List<CropIncomeList>> incomeMap = new HashMap<>();
		for (Crop crop : crops) {
			String cropType = crop.getType().replace(REPLACE_STRING, "");
			incomeMap.put("" + crop.getCrop(),
					cropAccountingService.getCropIncomeLists(crop.getCrop(), crop.getVarity()));
		}

		model.addAttribute("nid", nid);
		model.addAttribute("incomeMap", incomeMap);
		model.addAttribute("crops", crops);
	}

	@RequestMapping("/app/cropcalender1")
	public void cropcalender1() {

	}

	@RequestMapping("/app/cropcalender2")
	public void cropcalender2() {

	}
	
	@RequestMapping("/app/cropcalender")
	public void cropcalender(@RequestParam("nid") Optional<String> nid, Model model) {
		List<UserModel> users = cropAccountingService.getUsersExceptAdmin();
		List<FarmerCropTask> farmerCropTaskList = Arrays.asList();
		if (nid.isPresent())
			farmerCropTaskList = cropAccountingService.getFarmerCropTaskById(nid.get());

		model.addAttribute("nid", nid);
		model.addAttribute("farmerCropTaskList", farmerCropTaskList);
		model.addAttribute("users", users);
		model.addAttribute("url", "/app/cropcalender");
	}
	
	@RequestMapping("/app/farmerRegistration")
	public void farmerRegistration(Model model) {
		cropAccountingService.setCropSelection(model);
		model.addAttribute("farmer", new Farmer());
	}
	
	@RequestMapping("/app/croplist")
	public void croplist(Model model) {
		cropAccountingService.setCropSelection(model);
		model.addAttribute("crop", new Crop());
	}
	@PostMapping("/app/submitFarmerRegistration")
	public String submitFarmerRegistration(@ModelAttribute Farmer farmer, Model model) {
		Optional<Division> division = areaService.getDivisionByPortalId(1l);
		Optional<District> district = areaService.getDistrictByPortalId(1l);
		Optional<SubDistrict> subDistrict = areaService.getSubDistrictByPortalId(1l);
		if (!division.isPresent() || !district.isPresent() || !subDistrict.isPresent())
			return "/app/croplist";
		
		farmer.setDivision(division.get());
		farmer.setDistrict(district.get());
		farmer.setSubDistrict(subDistrict.get());
		farmer = cropAccountingService.saveFarmer(farmer);
		model.addAttribute("farmer", farmer);
		model.addAttribute("crop", new Crop());
		return "/app/croplist";
	}
	
	@PostMapping("/app/submit")
	public String submit(@ModelAttribute Crop crop, @RequestParam("farmer.id") Optional<Long> farmerId,
			@RequestParam("cropId") Optional<String> cropIdStr, @RequestParam("variety") Optional<String> varity,
			@RequestParam("crop.startDateString") Optional<String> date, Model model) {
		
		Optional<Farmer> farmer = null;
		if (farmerId.isPresent())
			farmer = cropAccountingService.getFarmerById(farmerId.get());
		if (farmer.isPresent())
			crop.setFarmer(farmer.get());
		
		FarmerCropTask farmerCropTask = new FarmerCropTask();
		
		cropIdStr.ifPresent(idString -> {
			crop.setCropId(idString.replace(REPLACE_NUMBER, ""));
			crop.setCrop(Long.parseLong(crop.getCropId()));
			Optional<Crops> protalCrops = cropAccountingService.getCropsById(crop.getCrop());
			if (protalCrops.isPresent()) {
				crop.setName(protalCrops.get().getName());
				crop.setType(protalCrops.get().getType());
				farmerCropTask.setCrop(crop.getCrop());
				model.addAttribute("portalCropId", crop.getCrop());
			}
			
			varity.ifPresent(theVariety -> {
				crop.setVarity(Long.parseLong(theVariety.replace(REPLACE_NUMBER, "")));
				Optional<Varieties> protalVarity = cropAccountingService.getVarietiesById(crop.getVarity());
				if (protalVarity.isPresent()) {
					crop.setVariety(protalVarity.get().getName());
					farmerCropTask.setVarity(crop.getVarity());
					model.addAttribute("varietyId", crop.getVarity());
				}
			});
		});
		
		farmerCropTask.setStartDate(crop.getStartDate());
		
		Optional<AreaCropExpence> areaCropExpence = eoService.getAreaCropExpenceByType(crop.getCrop(), crop.getVarity(),
				crop.getFarmer().getDivision().getId(), crop.getFarmer().getDistrict().getId(),
				crop.getFarmer().getSubDistrict().getId());
		Optional<CropExpenceList> cropExpenceListActual = Optional.empty();
		List<ExpenceItem> expenceItemList = cropAccountingService.getExpenceItemList();
		if (areaCropExpence.isPresent()) {
			model.addAttribute("expenceItemList", expenceItemList);
			farmerCropTask.setFarmerTaskList(areaCropExpence.get().getAreaExpenceItemValueList().stream()
					.map(ex -> getFarmerTask(expenceItemList, ex, crop.getStartDate())).collect(Collectors.toList()));
		}
		
		if (!areaCropExpence.isPresent()) {
			cropExpenceListActual = cropAccountingService.getCropExpenceListByType(crop.getCrop(),
					crop.getVarity());
		}
		// only if no area wise expense not present
		if (cropExpenceListActual.isPresent()) {
			model.addAttribute("expenceItemList", expenceItemList);
			farmerCropTask.setFarmerTaskList(cropExpenceListActual.get().getExpenceItemValueList().stream()
					.map(ex -> getFarmerTask(expenceItemList, ex, crop.getStartDate())).collect(Collectors.toList()));
		}
		
		farmerCropTask.setFarmer(crop.getFarmer());
		cropAccountingService.saveFarmerCropTask(farmerCropTask);
		crop.setLandTotalInDefault(BigDecimal.valueOf(crop.getCropLandQuantity())
				.multiply(BigDecimal.valueOf(UnitEnum.getById(crop.getCropUnit()).convertionRate())));
		cropAccountingService.saveCrop(crop);
		model.addAttribute("cropId", crop.getId());
		model.addAttribute("cropExpenceList", cropExpenceListActual);
		return "redirect:/app/cropprint";
	}
	
	private FarmerTask getFarmerTask(List<ExpenceItem> expenceItemList, ExpenceItemValue ex, LocalDate stDate) {
		return new FarmerTask().withCropActivity(ex.getCropActivity(expenceItemList))
				.withCropActivityType(ex.getCropActivityType(expenceItemList))
				.withCropActivityItem(ex.getCropActivityItem())
				.withItemExpence(ex.getItemExpence())
				.withLabourExpence(ex.getLabourExpence())
				.withTaskName(ex.getCropCalenderTask().getTaskName())
				.withTaskDateStr(ex.getCropCalenderTask().getTaskDateStr())
				.withDateOfUpload(ex.getCropCalenderTask().getDateOfUpload())
				.withStartDate(java.time.LocalDateTime.of(
						stDate.plusDays(Long.parseLong(ex.getCropCalenderTask().getTaskDateStr())), java.time.LocalTime.NOON))
				.withEndDate(java.time.LocalDateTime.of(
						stDate.plusDays(Long.parseLong(ex.getCropCalenderTask().getTaskDateStr())),
						java.time.LocalTime.MAX));
	}
	
	@RequestMapping("/app/print")
	public void print() {
	}

	@RequestMapping("/app/testprint")
	public String testprint(Model model, @RequestParam("portalCropId") Optional<Long> portalCropId,
			@RequestParam("portalVarietyId") Optional<Long> portalVarietyId) {
		List<ExpenceItem> expenceItemList = cropAccountingService.getExpenceItemList();
		Optional<CropExpenceList> cropExpenceList = cropAccountingService.getCropExpenceListByType(portalCropId.isPresent() ? portalCropId.get() : 0l,
				portalVarietyId.isPresent() ? portalVarietyId.get() : 0l);
		model.addAttribute("expenceItemList", expenceItemList);
		model.addAttribute("cropExpenceList", cropExpenceList);
		model.addAttribute("cropId", 0l);
		model.addAttribute("portalCropId", portalCropId.isPresent() ? portalCropId.get() : 0l);
		model.addAttribute("varietyId", portalVarietyId.isPresent() ? portalVarietyId.get() : 0l);
		return "redirect:/app/cropprint";
	}
	
	@RequestMapping("/app/cropprint")
	public void cropprint(@Valid Long cropId, @Valid Long portalCropId,	@Valid Long varietyId, Model model) {
		Optional<Crop> crop = cropAccountingService.getCropById(cropId);
		if(!crop.isPresent())
			crop = cropAccountingService.getTestCrop(Optional.ofNullable(portalCropId), Optional.ofNullable(varietyId));
		
		List<ExpenceItem> expenceItemList = cropAccountingService.getExpenceItemList();
		Optional<CropExpenceList> cropExpenceList = cropAccountingService.getCropExpenceListByType(portalCropId,
				varietyId);
		System.out.println(crop);
		System.out.println(cropExpenceList);
		model.addAttribute("crop", crop.isPresent() ? crop.get() : null);
		model.addAttribute("expenceItemList", expenceItemList);
		model.addAttribute("cropExpenceList", cropExpenceList);
	}

	@RequestMapping("/app/tbprint")
	public void tbprint() {
	}
}
