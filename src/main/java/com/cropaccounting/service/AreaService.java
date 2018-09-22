package com.cropaccounting.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropaccounting.models.area.District;
import com.cropaccounting.models.area.Division;
import com.cropaccounting.models.area.SubDistrict;
import com.cropaccounting.repository.DistrictRepository;
import com.cropaccounting.repository.DivisionRepository;
import com.cropaccounting.repository.SubDistrictRepository;

@Service
public class AreaService {
	@Autowired
	private DivisionRepository divisionRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private SubDistrictRepository subDistrictRepository;
	
	public Optional<Division> getDivisionById(Long divisionId) {
		return divisionRepository.findById(divisionId);
	}

	public Optional<District> getDistrictById(Long districtId) {
		return districtRepository.findById(districtId);
	}

	public Optional<SubDistrict> getSubDistrictById(Long subDistrictId) {
		return subDistrictRepository.findById(subDistrictId);
	}
	
	public Optional<Division> getDivisionByPortalId(Long divisionPortalId) {
		return divisionRepository.getDivisionByPortalId(divisionPortalId);
	}

	public Optional<District> getDistrictByPortalId(Long districtPortalId) {
		return districtRepository.getDistrictByPortalId(districtPortalId);
	}

	public Optional<SubDistrict> getSubDistrictByPortalId(Long subDistrictPortalId) {
		return subDistrictRepository.getSubDistrictByPortalId(subDistrictPortalId);
	}
}
