package com.cropaccounting.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cropaccounting.beans.CropVarietyBean;
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
import com.cropaccounting.models.Farmer;
import com.cropaccounting.models.FarmerCropTask;
import com.cropaccounting.models.FarmerTask;
import com.cropaccounting.models.IncomeItem;
import com.cropaccounting.models.IncomeItemValue;
import com.cropaccounting.models.UserModel;
import com.cropaccounting.models.Varieties;
import com.cropaccounting.repository.CropActivityItemRepository;
import com.cropaccounting.repository.CropActivityRepository;
import com.cropaccounting.repository.CropActivityTypeRepository;
import com.cropaccounting.repository.CropCalenderTaskRepository;
import com.cropaccounting.repository.CropExpenceListRepository;
import com.cropaccounting.repository.CropIncomeItemRepository;
import com.cropaccounting.repository.CropIncomeListRepository;
import com.cropaccounting.repository.CropIncomeRepository;
import com.cropaccounting.repository.CropRepository;
import com.cropaccounting.repository.CropTaskMapRepository;
import com.cropaccounting.repository.CropsRepository;
import com.cropaccounting.repository.ExpenceItemRepository;
import com.cropaccounting.repository.FarmerCropTaskRepository;
import com.cropaccounting.repository.FarmerRepository;
import com.cropaccounting.repository.FarmerTaskRepository;
import com.cropaccounting.repository.IncomeItemRepository;
import com.cropaccounting.repository.UserModelRepository;
import com.cropaccounting.repository.VarietiesRepository;

@Service
public class CropAccountingService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserModelRepository userModelRepo;
	
	@Autowired
	private FarmerTaskRepository farmerTaskRepository;
	
	@Autowired
	private CropRepository cropRepository;

	@Autowired
	private CropsRepository cropsRepository;
	
	@Autowired
	private VarietiesRepository varietiesRepository;
	
	@Autowired
	private CropIncomeListRepository cropIncomeListRepository;
	
	@Autowired
	private CropIncomeRepository cropIncomeRepository;
	
	@Autowired
	private CropIncomeItemRepository cropIncomeItemRepository;

	@Autowired
	private CropActivityTypeRepository cropActivityTypeRepository;
	
	@Autowired
	private CropActivityItemRepository cropActivityItemRepository;
	
	@Autowired
	private CropActivityRepository cropActivityRepository;
	
	@Autowired
	private ExpenceItemRepository expenceItemRepository;
	
	@Autowired
	private CropTaskMapRepository cropTaskMapRepository;
	
	@Autowired
	private CropExpenceListRepository cropExpenceListRepository;
	
	@Autowired
	private IncomeItemRepository incomeItemRepository;
	
	@Autowired
	private CropCalenderTaskRepository cropCalenderTaskRepository;
	
	@Autowired
	private FarmerCropTaskRepository farmerCropTaskRepository;
	
	@Autowired
	private FarmerRepository farmerRepository;
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTypes() {
		return em.createNativeQuery("SELECT distinct a.type FROM crops a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getCrops() {
		return em.createNativeQuery("SELECT  a.type, a.name, a.id FROM crops a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getVarieties() {
		return em.createNativeQuery("SELECT a.crop_id, a.name, a.id FROM varieties a where crop_id is not null").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Varieties> getVarietyList() {
		return em.createNativeQuery("SELECT a.crop_id, a.name, a.id FROM varieties a where crop_id is not null", Varieties.class).getResultList();
	}
	
	public FarmerTask saveFarmerTask(FarmerTask farmerTask) {
		return farmerTaskRepository.save(farmerTask);
	}
	
	public Crops saveCrops(Crops crops) {
		return cropsRepository.save(crops);
	}
	
	public Varieties saveVarieties(Varieties varieties) {
		return varietiesRepository.save(varieties);
	}
	
	public CropIncomeList saveCropIncomeList(CropIncomeList cropIncomeList) {
		return cropIncomeListRepository.save(cropIncomeList);
	}
	
	public CropIncome saveCropIncome(CropIncome cropIncome) {
		return cropIncomeRepository.save(cropIncome);
	}
	
	public CropIncomeItem saveCropIncomeItem(CropIncomeItem cropIncomeItem) {
		return cropIncomeItemRepository.save(cropIncomeItem);
	}
	
	public CropActivity saveCropActivity(CropActivity cropActivity) {
		return cropActivityRepository.save(cropActivity);
	}
	
	public CropActivityType saveCropActivityType(CropActivityType cropActivityType) {
		return cropActivityTypeRepository.save(cropActivityType);
	}
	
	public CropActivityItem saveCropActivityItem(CropActivityItem cropActivityItem) {
		return cropActivityItemRepository.save(cropActivityItem);
	}
	
	public CropTaskMap saveCropTaskMap(CropTaskMap cropTaskMap) {
		return cropTaskMapRepository.save(cropTaskMap);
	}
	
	public ExpenceItem saveExpenceItem(ExpenceItem expenceItem) {
		return expenceItemRepository.save(expenceItem);
	}
	
	public Crop saveCrop(Crop crop) {
		return cropRepository.save(crop);
	}
	
	public Farmer saveFarmer(Farmer farmer) {
		return farmerRepository.save(farmer);
	}
	
	public FarmerCropTask saveFarmerCropTask(FarmerCropTask farmerCropTask) {
		return farmerCropTaskRepository.save(farmerCropTask);
	}
	
	public CropExpenceList saveCropExpenceList(CropExpenceList cropExpenceList) {
		return cropExpenceListRepository.save(cropExpenceList);
	}
	
	public Optional<UserModel> getUsers(Long userId) {
		return userModelRepo.findById(userId);
	}
	
	public List<UserModel> getUsersExceptAdmin() {
		return userModelRepo.getUserExceptAdmin();
	}
	
	public Optional<FarmerTask> getFarmerTaskById(Long id) {
		return farmerTaskRepository.findById(id);
	}
	
	public List<FarmerCropTask> getFarmerCropTaskById(String nid) {
		return farmerCropTaskRepository.findByNID(nid);
	}
	
	public List<Farmer> getFarmerByNID(String nid) {
		return farmerRepository.searchByNID(nid);
	}
	
	public List<Farmer> getFarmerByMobile(String mobileNo) {
		return farmerRepository.searchByMobile(mobileNo);
	}

	public Optional<Farmer> getFarmerById(Long id) {
		return farmerRepository.findById(id);
	}
	
	public List<Crop> getCropList() {
		List<Crop> elementList = new ArrayList<>(); 
		cropRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<Crop> getCropById(Long id) {
		return cropRepository.findById(id);
	}
	
	public List<Crop> getCropByNID(String nid, LocalDate startDate) {
		return cropRepository.find(nid, startDate);
	}
	
	public List<Crops> getCropsList() {
		List<Crops> elementList = new ArrayList<>(); 
		cropsRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<Crops> getCropsById(Long id) {
		return cropsRepository.findById(id);
	}
	
	public Optional<Varieties> getVarietiesById(Long id) {
		return varietiesRepository.findById(id);
	}
	
	public Optional<CropIncomeList> getCropIncomeList(long crop, long varity) {
		return cropIncomeListRepository.findOne(crop, varity);
	}
	
	public Optional<CropIncomeList> getCropIncomeListById(long id) {
		return cropIncomeListRepository.findById(id);
	}
	
	public List<CropIncomeList> getCropIncomeLists(long crop, long varity) {
		return cropIncomeListRepository.find(crop, varity);
	}
	
	public List<CropIncomeList> getCropIncomeLists() {
		List<CropIncomeList> elementList = new ArrayList<>(); 
		cropIncomeListRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public List<CropIncome> getCropIncomeList() {
		List<CropIncome> elementList = new ArrayList<>(); 
		cropIncomeRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<CropIncome> getCropIncomeById(Long id) {
		return cropIncomeRepository.findById(id);
	}
	
	public List<CropIncomeItem> getCropIncomeItemList() {
		List<CropIncomeItem> elementList = new ArrayList<>(); 
		cropIncomeItemRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public List<CropActivityType> getCropActivityTypeList() {
		List<CropActivityType> elementList = new ArrayList<>(); 
		cropActivityTypeRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public List<CropActivityItem> getCropActivityItemList() {
		List<CropActivityItem> elementList = new ArrayList<>(); 
		cropActivityItemRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public List<CropActivity> getCropActivityList() {
		List<CropActivity> elementList = new ArrayList<>(); 
		cropActivityRepository.findAll().forEach(elementList::add);
		return elementList;
	}

	public List<CropActivityType> getCropActivityTypelist() {
		List<CropActivityType> elementList = new ArrayList<>(); 
		cropActivityTypeRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<CropActivity> getCropActivity(Long id) {
		return cropActivityRepository.findById(id);
	}
	
	public Optional<CropActivityType> getCropActivityType(Long id) {
		return cropActivityTypeRepository.findById(id);
	}
	
	public Optional<CropActivityItem> getCropActivityItem(Long id) {
		return cropActivityItemRepository.findById(id);
	}
	
	public List<ExpenceItem> getExpenceItemList() {
		List<ExpenceItem> elementList = new ArrayList<>(); 
		expenceItemRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public List<ExpenceItem> findOrderedList() {
		List<ExpenceItem> elementList = new ArrayList<>(); 
		expenceItemRepository.findOrderedList().forEach(elementList::add);
		return elementList;
	}

	public List<ExpenceItem> getExpenceItemOrderedList() {
		List<ExpenceItem> elementList = new ArrayList<>(); 
		expenceItemRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<ExpenceItem> getExpenceItem(Long id) {
		return expenceItemRepository.findById(id);
	}
	
	public List<CropTaskMap> getCropTaskMapList() {
		List<CropTaskMap> elementList = new ArrayList<>(); 
		cropTaskMapRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<CropTaskMap> getCropTaskMap(Long id) {
		return cropTaskMapRepository.findById(id);
	}
	
	public List<CropExpenceList> getCropExpenceLists() {
		List<CropExpenceList> elementList = new ArrayList<>(); 
		cropExpenceListRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public List<IncomeItem> getIncomeItemList() {
		List<IncomeItem> elementList = new ArrayList<>(); 
		incomeItemRepository.findAll().forEach(elementList::add);
		return elementList;
	}
	
	public Optional<CropExpenceList> getCropExpenceListByType(String type, long crop, long varity) {
		System.out.println("type::" + type + " " + " crop::" + crop + " " + " varity::" + varity);
		return cropExpenceListRepository.find(type, crop, varity);
	}
	
	public Optional<CropExpenceList> getCropExpenceListByType(long crop, long varity) {
		System.out.println( "crop::" + crop + " " + " varity::" + varity);
		return cropExpenceListRepository.find(crop, varity);
	}
	
	public Optional<CropExpenceList> getExpenceListById(Long id) {
		return cropExpenceListRepository.findById(id);
	}
	
	public Optional<CropCalenderTask> getCropCalenderTaskById(Long id) {
		return cropCalenderTaskRepository.findById(id);
	}
	
	public void setCropSelection(Model model) {
		model.addAttribute("types", getTypes());
		model.addAttribute("crops", getCrops());
		List<Varieties> varieties = getVarietyList();
		model.addAttribute("varietiesMap",
				varieties.stream().collect(Collectors.groupingBy(variety -> variety.getId())));
		model.addAttribute("cropsMap", getCropsList().stream().collect(Collectors.groupingBy(crop -> crop.getId())));
		model.addAttribute("varieties", varieties);
		List<CropVarietyBean> cropsList = getCropsList().stream()
				.flatMap(crop -> getCropVarietyMap(crop, varieties).stream()).collect(Collectors.toList());
		model.addAttribute("cropList", cropsList);
	}
	
	private List<CropVarietyBean> getCropVarietyMap(Crops crop, List<Varieties> varietyList) {
		return varietyList.stream().filter(variety -> variety.getCrop_id() == crop.getId())
				.map(variety -> new CropVarietyBean()
						.setCropId(crop.getId()).setCropName(crop.getName())
						.setVarietyId(variety.getId()).setVarietyName(variety.getName()))
				.collect(Collectors.toList());
	}
	
	public Optional<Crop> getTestCrop(Optional<Long> portalCropId, Optional<Long> portalVarietyId) {
		Farmer farmer = new Farmer();
		farmer.setName("Abdur Rob");
		farmer.setNid("1234567891234");
		Crop crop = new Crop().setCrop(portalCropId.isPresent() ? portalCropId.get() : 0l)
				.setCropCast("")
				.setCropLandQuantity(1)
				.setCropUnit(1)
				.setFarmer(farmer)
				.setStartDate(LocalDate.now())
				.setVarity(portalVarietyId.isPresent() ? portalCropId.get() : 0l);
		return Optional.of(crop);
	}

	private Long getLongValue(String val) {
		try {
			return Long.parseLong(val);
		} catch (ArithmeticException are) {
			return 0l;
		}
	}
	
	public IncomeItemValue getIncomeDetail(int i, String[] cropIncomeItems, String[] items, String[] amounts,
			String[] days, String[] values) {
		IncomeItemValue incomeItemValue = new IncomeItemValue();
		if (cropIncomeItems[i] != null && cropIncomeItems[i].length() > 0 && cropIncomeItems[i].indexOf("_") > 0) {
			String id = cropIncomeItems[i].split("_")[1];
			Optional<CropIncome> cropIncome = getCropIncomeById(getLongValue(id));
			if (cropIncome.isPresent())
				incomeItemValue.setCropIncome(cropIncome.get());
			// incomeItemValue.setType(items[i]);
			if (items[i] != null && items[i].length() > 0)
				incomeItemValue.setType(items[i]);
			if (amounts[i] != null && amounts[i].length() > 0)
				incomeItemValue.setAmount(new BigDecimal(amounts[i]));
			if (days[i] != null && days[i].length() > 0)
				incomeItemValue.setDay(Integer.parseInt(days[i]));
			if (values[i] != null && values[i].length() > 0)
				incomeItemValue.setTotValue(new BigDecimal(values[i]));
		}
		return incomeItemValue;
	}
	
	public IncomeItemValue getCropIncome(int i, String[] cropIncomeItems, String[] items, String[] amounts,
			String[] days, String[] values) {
		if (cropIncomeItems[i] == null || cropIncomeItems[i].length() == 0 || cropIncomeItems[i].indexOf("_") <= 0)
			return null;
		IncomeItemValue incomeItemValue = new IncomeItemValue();
		String id = cropIncomeItems[i].split("_")[1];
		getCropIncomeById(getLongValue(id)).ifPresent(cropIncome -> incomeItemValue.setCropIncome(cropIncome));
		if (items[i] != null && items[i].length() > 0)
			incomeItemValue.setType(items[i]);
		if (amounts[i] != null && amounts[i].length() > 0)
			incomeItemValue.setAmount(new BigDecimal(amounts[i]));
		if (days[i] != null && days[i].length() > 0)
			incomeItemValue.setDay(Integer.parseInt(days[i]));
		if (values[i] != null && values[i].length() > 0)
			incomeItemValue.setTotValue(new BigDecimal(values[i]));
		return incomeItemValue;
	}
	
	public ExpenceItemValue getCropExpence(int i, Optional<String[]> cropActivityItems, Optional<String[]> taskIds,
			Optional<String[]> itemExpences, Optional<String[]> labourExpences) {
		ExpenceItemValue expenceItemValue = new ExpenceItemValue();
		if (cropActivityItems.isPresent() && cropActivityItems.get()[i].length() > 0)
			getCropActivityItem(getLongValue(cropActivityItems.get()[i]))
					.ifPresent(actItem -> expenceItemValue.setCropActivityItem(actItem));
		if (taskIds.isPresent() && taskIds.get()[i].length() > 0)
			getCropCalenderTaskById(getLongValue(taskIds.get()[i]))
					.ifPresent(calTask -> expenceItemValue.setCropCalenderTask(calTask));
		expenceItemValue.setItemExpence(
				itemExpences.isPresent() && itemExpences.get()[i].length() > 0 ? new BigDecimal(itemExpences.get()[i])
						: BigDecimal.ZERO);
		expenceItemValue.setLabourExpence(labourExpences.isPresent() && labourExpences.get()[i].length() > 0
				? new BigDecimal(labourExpences.get()[i])
				: BigDecimal.ZERO);
		return expenceItemValue;
	}
	
	public CropCalenderTask getCalenderTask(int i, Optional<String[]> replaceDates, Optional<String[]> replaceNames,
			Optional<String[]> replaceAct, Optional<String[]> replaceTask, Optional<String[]> replaceComm) {
		CropCalenderTask cropCalenderTask = new CropCalenderTask();
		if (replaceDates.get()[i] != null && replaceDates.get()[i].length() > 0)
			cropCalenderTask.setTaskDateStr(replaceDates.get()[i]);
		if (replaceNames.isPresent() && replaceNames.get()[i] != null && replaceNames.get()[i].length() > 0)
			cropCalenderTask.setTaskName(replaceNames.get()[i]);
		if (replaceAct.isPresent() && replaceAct.get()[i].length() > 0) {
			Optional<CropActivity> cropActivity = getCropActivity(getLongValue(replaceAct.get()[i]));
			if (cropActivity.isPresent())
				cropCalenderTask.setCropActivity(cropActivity.get());
		}

		if (replaceTask.get()[i] != null && replaceTask.get()[i].length() > 0) {
			Optional<CropActivityType> cropActivityType = getCropActivityType(getLongValue(replaceTask.get()[i]));
			if (cropActivityType.isPresent())
				cropCalenderTask.setCropActivityType(cropActivityType.get());
		}
		if (replaceComm.get()[i] != null && replaceComm.get()[i].length() > 0)
			cropCalenderTask.setComments(replaceComm.get()[i]);
		return cropCalenderTask;
	}
}
