package com.cropaccounting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cropaccounting.models.Farmer;
import com.cropaccounting.models.area.AreaCropExpence;
import com.cropaccounting.models.area.AreaCropIncome;
import com.cropaccounting.repository.AreaCropExpenceRepository;
import com.cropaccounting.repository.AreaCropIncomeRepository;
import com.cropaccounting.repository.FarmerRepository;

@Service
public class EOService {

	@Autowired
	private AreaCropExpenceRepository areaCropExpenceRepository;
	
	@Autowired
	private AreaCropIncomeRepository areaCropIncomeRepository;
	
	@Autowired
	private FarmerRepository farmerRepository;

	public Optional<AreaCropExpence> getAreaCropExpenceByType(long crop, long varity, long divisionId,
			long districtId, long subDistrictId) {
		System.out.println(" crop::" + crop + " " + " varity::" + varity);
		return areaCropExpenceRepository.findAreaExpence(crop, varity, divisionId, districtId, subDistrictId);
	}
	
	public AreaCropExpence saveAreaCropExpence(AreaCropExpence areaCropExpence) {
		return areaCropExpenceRepository.save(areaCropExpence);
	}
	
	public Optional<AreaCropExpence> getAreaCropExpenceById(Long id) {
		return areaCropExpenceRepository.findById(id);
	}
	
	public List<AreaCropExpence> getAreaCropExpenceList() {
		return areaCropExpenceRepository.findAll();
	}
	
	public Optional<AreaCropIncome> getAreaCropIncomeByType(long crop, long varity, long divisionId,
			long districtId, long subDistrictId) {
		return areaCropIncomeRepository.findAreaCropIncome(crop, varity, divisionId, districtId, subDistrictId);
	}
	
	public AreaCropIncome saveAreaCropIncome(AreaCropIncome areaCropIncome) {
		return areaCropIncomeRepository.save(areaCropIncome);
	}
	
	public Optional<AreaCropIncome> getAreaCropIncomeById(Long id) {
		return areaCropIncomeRepository.findById(id);
	}
	
	public List<AreaCropIncome> getAreaCropIncomeList() {
		return areaCropIncomeRepository.findAll();
	}

	public Page<AreaCropIncome> getAreaCropIncomeList(Pageable pageable) {
		return areaCropIncomeRepository.findAll(pageable);
	}

	public Page<Farmer> findPaginated(Pageable pageable) {
//		int pageSize = pageable.getPageSize();
//		int currentPage = pageable.getPageNumber();
//		int startItem = currentPage * pageSize;
//		List<Farmer> list;
//		if (books.size() < startItem) {
//			list = Collections.emptyList();
//		} else {
//			int toIndex = Math.min(startItem + pageSize, books.size());
//			list = books.subList(startItem, toIndex);
//		}
//		Page<Farmer> bookPage
//			= new PageImpl<Farmer>(list, PageRequest.of(currentPage, pageSize), books.size());
//			return bookPage;
		return farmerRepository.findAll(pageable);
	}

	public Page<Farmer> findPaginated(Optional<String> nid, Pageable pageable) {
		return farmerRepository.searchByNID(nid.isPresent() ? nid.get() : "", pageable);
	}
}
