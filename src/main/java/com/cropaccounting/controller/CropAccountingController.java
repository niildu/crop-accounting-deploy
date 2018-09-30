package com.cropaccounting.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cropaccounting.beans.ExpenditureRequest;
import com.cropaccounting.beans.ExpenditureResponse;
import com.cropaccounting.models.Crop;
import com.cropaccounting.models.CropActivity;
import com.cropaccounting.models.CropActivityItem;
import com.cropaccounting.models.CropActivityType;
import com.cropaccounting.models.CropCalenderTask;
import com.cropaccounting.models.CropExpenceList;
import com.cropaccounting.models.CropIncome;
import com.cropaccounting.models.CropIncomeItem;
import com.cropaccounting.models.CropIncomeList;
import com.cropaccounting.models.CropTaskMap;
import com.cropaccounting.models.Crops;
import com.cropaccounting.models.ExpenceItem;
import com.cropaccounting.models.ExpenceItemValue;
import com.cropaccounting.models.FarmerTask;
import com.cropaccounting.models.IncomeItemValue;
import com.cropaccounting.models.UserModel;
import com.cropaccounting.models.Varieties;
import com.cropaccounting.service.CropAccountingService;

@Controller
public class CropAccountingController {
	private final Logger log = LoggerFactory.getLogger(CropAccountingController.class);
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String REPLACE_NUMBER = "number:";
	private static final String REPLACE_STRING = "string:";

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CropAccountingService cropAccountingService;

	@PostMapping(value = "/api/expenditure")
	@ResponseBody
	public ExpenditureResponse extractManifest(@RequestBody ExpenditureRequest req, Locale lc) {
		return ExpenditureResponse.newInstance();
	}

	@RequestMapping("/cropmanagement/createcropprice")
	public List<UserModel> createcropprice() {
		return cropAccountingService.getUsersExceptAdmin();
	}

	@RequestMapping("/cropmanagement/createitemprice")
	public List<UserModel> createitemprice() {
		return cropAccountingService.getUsersExceptAdmin();
	}

	@PostMapping("/cropmanagement/updatetaskdate")
	@ResponseBody
	public String updatetaskdate(@RequestParam("start") String start, @RequestParam("end") String end,
			@RequestParam("taskId") String taskId) {

		Optional<FarmerTask> taskOptional = cropAccountingService.getFarmerTaskById(Long.parseLong(taskId));
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
				.ofPattern(DATE_TIME_FORMAT);

		FarmerTask task = null;
		if (!taskOptional.isPresent())
			throw new NullPointerException();

		task = taskOptional.get();
		task.setStartDate(java.time.LocalDateTime.parse(start, formatter));
		task.setEndDate(java.time.LocalDateTime.parse(end, formatter));

		cropAccountingService.saveFarmerTask(task);
		return "success";
	}

	@RequestMapping("/cropmanagement/createcrop")
	public void createcrop(@RequestParam("cropId") Optional<String> cropId,
			@RequestParam("cropType") Optional<String> cropType, Model model) {
		List<Object[]> crops = cropAccountingService.getCrops();
		Object[] crop = null;
		if (cropId.isPresent())
			crop = crops.stream().filter(cropObject -> cropObject[0].equals(cropId.get())).findFirst().orElse(null);

		log.debug("crop::"+crop);
		model.addAttribute("crop", crop);
		model.addAttribute("crops", crops);
		model.addAttribute("cropType", cropType);
	}

	@PostMapping("/cropmanagement/submitCrop")
	public String submitCrop(@RequestParam("cropId") Optional<String> cropId,
			@RequestParam("cropType") Optional<String> cropType, @RequestParam("cropName") String cropName,
			@RequestParam("varityName") Optional<String> varityName, Model model) {

		if (!cropId.isPresent()) {
			System.out.println("IN IS PRESENT");
			/*String query = "insert into varieties(name,crop_id) values('" + varityName + "'," + cropId
					+ ");SELECT LAST_INSERT_ID();";
			em.createNativeQuery(query).executeUpdate();*/
		} else {
			log.info("Adding crop!!");
			Crops portalcrops = new Crops();
			portalcrops.setName(cropName);
			if (cropType.isPresent())
				portalcrops.setType(cropType.get().replace(",", ""));
			cropAccountingService.saveCrops(portalcrops);

			Varieties protalvirieties = new Varieties();
			varityName.ifPresent(variety -> protalvirieties.setName(variety));
			protalvirieties.setCrop_id(portalcrops.getId());

			cropAccountingService.saveVarieties(protalvirieties);
		}

		cropAccountingService.setCropSelection(model);
		return "redirect:/app/croplist";
	}

	@RequestMapping("/cropmanagement/list")
	public void list(Model model) {
		List<Crop> cropList = cropAccountingService.getCropList();
		model.addAttribute("cropList", cropList);
	}

	@RequestMapping("/cropmanagement/createActivity")
	public void createActivity() {
	}

	@RequestMapping("/cropmanagement/createActivityType")
	public void createActivityType() {
	}

	@RequestMapping("/cropmanagement/createIncome")
	public void createIncome(Model model ) {
		model.addAttribute("cropIncome", new CropIncome());
	}

	@PostMapping("/cropmanagement/submitIncomeByDate")
	public String submitIncomeByDate(@ModelAttribute CropIncomeList cropIncomeList, @RequestParam("type") Optional<String> type,
			@RequestParam("cropId") Optional<String> cropIdStr, @RequestParam("variety") Optional<String> varity,
			@RequestParam("income") String[] cropIncomeItems, @RequestParam("incomeItems") String[] items,
			@RequestParam("days") String[] days, @RequestParam("amounts") String[] amounts,
			@RequestParam("values") String[] values, Model model) {

		//type.ifPresent(theType -> cropIncomeList.setType(theType.split(":")[1]));
		cropIdStr.ifPresent(idString -> {
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
		});
		varity.ifPresent(theVariety -> {
			cropIncomeList.setVarity(Long.parseLong(theVariety.replace(REPLACE_NUMBER, "")));
			Optional<Varieties> protalVarity = cropAccountingService.getVarietiesById(cropIncomeList.getVarity());
			if (protalVarity.isPresent())
				cropIncomeList.setVarityName(protalVarity.get().getName());
		});

		Optional<CropIncomeList> repoCropIncomeList = cropAccountingService.getCropIncomeList(cropIncomeList.getCrop(),
				cropIncomeList.getVarity());

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
		if (repoCropIncomeList.isPresent()) {
			repoCropIncomeList.get().getIncomeItemValueList().clear();
			repoCropIncomeList.get().getIncomeItemValueList().addAll(incomeItemValueList);
			cropAccountingService.saveCropIncomeList(repoCropIncomeList.get());
		} else {
			cropAccountingService.saveCropIncomeList(cropIncomeList);
		}

		List<CropIncomeList> cropIncomesList = cropAccountingService.getCropIncomeLists();
		model.addAttribute("cropIncomesList", cropIncomesList);
		return "redirect:/cropmanagement/createearnings";
	}

	@RequestMapping("/cropmanagement/createearnings")
	public void createearnings(Model model) {
		model.addAttribute("cropIncomes", cropAccountingService.getCropIncomeList());
		model.addAttribute("cropIncomesList", cropAccountingService.getCropIncomeLists());
		model.addAttribute("incomeItemList", cropAccountingService.getIncomeItemList());
		cropAccountingService.setCropSelection(model);
		model.addAttribute("cropIncomeList", new CropIncomeList());
	}

	@RequestMapping("/cropmanagement/createIncomeItem")
	public void createIncomeItem() {

	}

	@PostMapping("/cropmanagement/submitIncome")
	public String submitIncome(@ModelAttribute CropIncome cropIncome, Model model) {
		cropAccountingService.saveCropIncome(cropIncome);
		model.addAttribute("incomeList", cropAccountingService.getCropIncomeList());
		cropAccountingService.setCropSelection(model);
		return "redirect:/cropmanagement/createearnings";
	}

	@PostMapping("/cropmanagement/submitIncomeItem")
	public String submitIncomeItem(@ModelAttribute CropIncomeItem cropIncomeItem, Model model) {
		cropAccountingService.saveCropIncomeItem(cropIncomeItem);
		model.addAttribute("incomeItemList", cropAccountingService.getCropIncomeItemList());
		return "redirect:/cropmanagement/incomeitemlist";
	}

	@RequestMapping("/cropmanagement/createActivityItem")
	public void createActivityItem() {

	}

	@PostMapping("/cropmanagement/submitActivity")
	public String submitActivity(@ModelAttribute CropActivity cropActivity, Model model) {
		System.out.println("cropActivity::" + cropActivity);
		cropAccountingService.saveCropActivity(cropActivity);
		model.addAttribute("activityList", cropAccountingService.getCropActivityList());
		return "redirect:/cropmanagement/activitylist";
	}

	@PostMapping("/cropmanagement/submitActivityType")
	public String submitActivityType(@ModelAttribute CropActivityType cropActivityType, Model model) {
		cropAccountingService.saveCropActivityType(cropActivityType);
		model.addAttribute("activityList", cropAccountingService.getCropActivityTypeList());
		return "redirect:/cropmanagement/activityTypeList";
	}

	@PostMapping("/cropmanagement/submitActivityItem")
	public String submitActivityItem(@ModelAttribute CropActivityItem cropActivityItem, Model model) {
		cropAccountingService.saveCropActivityItem(cropActivityItem);
		model.addAttribute("cropActivityItemList", cropAccountingService.getCropActivityItemList());
		return "redirect:/cropmanagement/activityItemList";
	}

	@RequestMapping("/cropmanagement/activitylist")
	public void activitylist(Model model) {
		model.addAttribute("activityList", cropAccountingService.getCropActivityList());
		model.addAttribute("cropActivity", new CropActivity());
	}

	@RequestMapping("/cropmanagement/editActivity")
	public String editActivity(Long id, Model model) {
		model.addAttribute("cropActivity", cropAccountingService.getCropActivity(id));
		return "redirect:/cropmanagement/createActivity";
	}

	@RequestMapping("/cropmanagement/activityTypeList")
	public void activityTypeList(Model model) {
		model.addAttribute("activityTypeList", cropAccountingService.getCropActivityTypeList());
		model.addAttribute("cropActivityType", new CropActivityType());
	}

	@RequestMapping("/cropmanagement/editActivityType")
	public String editActivityType(Long id, Model model) {
		model.addAttribute("cropActivityType", cropAccountingService.getCropActivityType(id));
		return "redirect:/cropmanagement/createActivity";
	}

	@RequestMapping("/cropmanagement/activityItemList")
	public void activityItemList(Model model) {
		model.addAttribute("activityItemList", cropAccountingService.getCropActivityItemList());
		model.addAttribute("cropActivityItem", new CropActivityItem());
	}

	@RequestMapping("/cropmanagement/editActivityItem")
	public String editActivityItem(Long id, Model model) {
		model.addAttribute("cropActivityItem", cropAccountingService.getCropActivityItem(id));
		return "redirect:/cropmanagement/createActivity";
	}

	@RequestMapping("/cropmanagement/createmaincrop")
	public void createmaincrop() {
	}

	@RequestMapping("/cropmanagement/cropmatrix")
	public void cropmatrix() {
	}

	@RequestMapping("/cropmanagement/updatecropprice")
	public void updatecropprice() {
	}

	@RequestMapping("/cropmanagement/createcropcalendar")
	public void createcropcalendar(Model model) {
		model.addAttribute("cropActivityList", cropAccountingService.getCropActivityList());
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
		cropAccountingService.setCropSelection(model);
	}

	@PostMapping("/cropmanagement/submitcropcaltask")
	public String submitCropCalTask(@ModelAttribute CropTaskMap cropTaskMap, @RequestParam("cropId") Optional<String> cropIdStr,
			@RequestParam("variety") Optional<String> varietyIdStr,	@RequestParam("taskname") Optional<String[]> replaceNames,
			@RequestParam("taskdate") Optional<String[]> replaceDates, @RequestParam("activity") Optional<String[]> replaceAct,
			@RequestParam("task") Optional<String[]> replaceTask, @RequestParam("comments") Optional<String[]> replaceComm, Model model) {

		
		cropIdStr.ifPresent(idString -> {
			long cropIdToSet = Long.parseLong(idString.replace(REPLACE_NUMBER, ""));
			cropTaskMap.setCrop(cropIdToSet);
			Optional<Crops> protalCrops = cropAccountingService.getCropsById(cropIdToSet);
			if (protalCrops.isPresent()) {
				cropTaskMap.setType(protalCrops.get().getType());
				cropTaskMap.setCropName(protalCrops.get().getName());
			}
		});
		
		if (varietyIdStr.isPresent()) {
			cropTaskMap.setVarity(Long.parseLong(varietyIdStr.get().replace(REPLACE_NUMBER, "")));
			Optional<Varieties> portalVariety = cropAccountingService.getVarietiesById(cropTaskMap.getVarity());
			portalVariety.ifPresent(variety -> cropTaskMap.setVarityName(variety.getName()));
		}
		
		List<CropCalenderTask> taskList = new ArrayList<CropCalenderTask>();

		Optional<CropActivity> cropActivity = null;
		Optional<CropActivityType> cropActivityType = null;
		System.out.println(replaceDates.get().length);
		for (int i = 0; replaceAct.isPresent() && i < replaceAct.get().length; i++) {
			CropCalenderTask cropCalenderTask = new CropCalenderTask();
			if (replaceDates.get()[i] != null && replaceDates.get()[i].length() > 0)
				cropCalenderTask.setTaskDateStr(replaceDates.get()[i]);
			if (replaceNames.isPresent() && replaceNames.get()[i] != null && replaceNames.get()[i].length() > 0)
				cropCalenderTask.setTaskName(replaceNames.get()[i]);
			if (replaceAct.isPresent() && replaceAct.get()[i].length() > 0) {
				cropActivity = cropAccountingService.getCropActivity(Long.parseLong(replaceAct.get()[i]));
				if (cropActivity.isPresent())
					cropCalenderTask.setCropActivity(cropActivity.get());
			}

			if (replaceTask.get()[i] != null && replaceTask.get()[i].length() > 0) {
				cropActivityType = cropAccountingService.getCropActivityType(Long.parseLong(replaceTask.get()[i]));
				if (cropActivityType.isPresent())
					cropCalenderTask.setCropActivityType(cropActivityType.get());
			}
			if (replaceComm.get()[i] != null && replaceComm.get()[i].length() > 0)
				cropCalenderTask.setComments(replaceComm.get()[i]);

			taskList.add(cropCalenderTask);
		}
		cropTaskMap.setTaskList(taskList);
		cropAccountingService.saveCropTaskMap(cropTaskMap);
		model.addAttribute("id", Optional.ofNullable(cropTaskMap.getId()));
		System.out.println("cropTaskMap.getId()::" + cropTaskMap.getId());
		//createTaskExpenditure(cropTaskMap.getId(), model);
		return "redirect:/cropmanagement/createTaskExpenditure?id=" + cropTaskMap.getId();
	}

	@RequestMapping("/cropmanagement/cropTasklist")
	public void cropTasklist(Model model) {
		model.addAttribute("cropTaskMapList", cropAccountingService.getCropTaskMapList());
	}

	@RequestMapping("/cropmanagement/createTaskExpenditure")
	public void createTaskExpenditure(Optional<Long> id, Model model) {
		model.addAttribute("cropTaskMap", cropAccountingService.getCropTaskMap(id.isPresent() ? id.get() : 0l));
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
	}

	@RequestMapping("/cropmanagement/cropexpencelist")
	public void cropexpencelist(Model model) {
		model.addAttribute("cropExpences", cropAccountingService.getCropExpenceLists());
		cropAccountingService.setCropSelection(model);
	}

	@RequestMapping("/cropmanagement/createExpenceItem")
	public void createExpenceItem(Model model) {
		model.addAttribute("cropActivityList", cropAccountingService.getCropActivityList());
		model.addAttribute("cropActivityTypeList", cropAccountingService.getCropActivityTypelist());
	}

	@PostMapping("/cropmanagement/submitExpenceItem")
	public String submitExpenceItem(@ModelAttribute ExpenceItem expenceItem,
			@RequestParam("cropActivity") String cropActivityId,
			@RequestParam("cropActivityType") String cropActivityTypeId,
			@RequestParam("cropActivityItem") String cropActivityItemId) {
		expenceItem = new ExpenceItem();
		System.out.println("cropActivityId::" + cropActivityId);
		Optional<CropActivity> cropActivity = null;
		Optional<CropActivityType> cropActivityType = null;
		Optional<CropActivityItem> cropActivityItem = null;

		if (!cropActivityId.isEmpty())
			cropActivity = cropAccountingService.getCropActivity(Long.parseLong(cropActivityId));
		if (!cropActivityTypeId.isEmpty())
			cropActivityType = cropAccountingService.getCropActivityType(Long.parseLong(cropActivityTypeId));
		if (!cropActivityItemId.isEmpty())
			cropActivityItem = cropAccountingService.getCropActivityItem(Long.parseLong(cropActivityItemId));

		if (cropActivity.isPresent())
			expenceItem.setCropActivity(cropActivity.get());
		if (cropActivityType.isPresent())
			expenceItem.setCropActivityType(cropActivityType.get());
		if (cropActivityItem.isPresent())
			expenceItem.setCropActivityItem(cropActivityItem.get());

		if (expenceItem != null)
			cropAccountingService.saveExpenceItem(expenceItem);

		List<ExpenceItem> expenceItemList = cropAccountingService.getExpenceItemList();
		String json = expenceItemList.toString();
		json = json.substring(1, json.length() - 1);
		System.out.println("\n\n" + expenceItem.toStringJson());
		return "redirect:/cropmanagement/expenceItemList";
	}

	@RequestMapping("/cropmanagement/editExpenceItem")
	public String editExpenceItem(Long id, Model model) {
		model.addAttribute("expenceItem", cropAccountingService.getExpenceItem(id));
		model.addAttribute("cropActivityList", cropAccountingService.getCropActivityList());
		model.addAttribute("cropActivityTypeList", cropAccountingService.getCropActivityTypeList());
		return "redirect:/cropmanagement/createExpenceItem";
	}

	@RequestMapping("/cropmanagement/expenceItemList")
	public void expenceItemList(Model model) {
		model.addAttribute("expenceItemList", cropAccountingService.findOrderedList());
		model.addAttribute("cropActivityList", cropAccountingService.getCropActivityList());
		model.addAttribute("cropActivityTypeList", cropAccountingService.getCropActivityTypeList());
		model.addAttribute("cropActivityItemList", cropAccountingService.getCropActivityItemList());
	}

	@RequestMapping("/cropmanagement/incomeItemList")
	public void incomeItemList(Model model) {
		model.addAttribute("incomeItemList", cropAccountingService.getIncomeItemList());
		model.addAttribute("cropIncomeList", cropAccountingService.getCropIncomeList());
		model.addAttribute("cropIncomeItemList", cropAccountingService.getCropIncomeItemList());
	}

	

	@PostMapping("/cropmanagement/submitExpenditure")
	public String submitExpenditure(@ModelAttribute CropExpenceList cropExpenceList,
			@RequestParam("cropTaskMapId") Optional<Long> cropTaskMapId,
			@RequestParam("cropExpenceList.expenceItemValue.cropActivityItem") Optional<String[]> cropActivityItems,
			@RequestParam("cropExpenceList.expenceItemValue.itemExpence") Optional<String[]> itemExpences,
			@RequestParam("cropExpenceList.expenceItemValue.labourExpence") Optional<String[]> labourExpences,
			@RequestParam("taskId") Optional<String[]> taskIds, Model model) {

		
		Optional<CropTaskMap> cropTaskMap = cropAccountingService
				.getCropTaskMap(cropTaskMapId.isPresent() ? cropTaskMapId.get() : 0l);
		Optional<CropExpenceList> repoCropExpenceList = cropAccountingService.getCropExpenceListByType(
				cropTaskMap.get().getType(), cropTaskMap.get().getCrop(), cropTaskMap.get().getVarity());

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
		cropExpenceList.setExpenceItemValueList(expenceItemValueList);

		if (cropTaskMap.isPresent()) {
			cropExpenceList.setType(cropTaskMap.get().getType());
			cropExpenceList.setCrop(cropTaskMap.get().getCrop());
			cropExpenceList.setVarity(cropTaskMap.get().getVarity());
			cropExpenceList.setCropTaskMap(cropTaskMap.get());
			cropAccountingService.saveCropExpenceList(cropExpenceList);
		}
		model.addAttribute("cropExpences", cropAccountingService.getCropExpenceLists());
		return "redirect:/cropmanagement/cropexpencelist";
	}

	@RequestMapping("/cropmanagement/farmerTaskExpenditure")
	public void farmerTaskExpenditure(Long id, Model model) {
		model.addAttribute("cropExpenceList", cropAccountingService.getExpenceListById(id));
		model.addAttribute("expenceItemList", cropAccountingService.getExpenceItemList());
	}

	@RequestMapping("/cropmanagement/otherdb")
	public void otherdb(Model model) {
		cropAccountingService.setCropSelection(model);
	}
}