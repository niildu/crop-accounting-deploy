package com.cropaccounting.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cropaccounting.beans.FarmerDetails;
import com.cropaccounting.service.CropAccountingService;

@Controller
public class FarmerController {
	
	@Autowired
	private CropAccountingService cropAccountingService;
	
	@RequestMapping(value = "/app/searchFarmerNID", method = RequestMethod.GET)
	@ResponseBody
	public List<FarmerDetails> searchFarmerNID(@RequestParam(value = "query") Optional<String> nid) {
		if (nid.isPresent()) {
			return cropAccountingService.getFarmerByNID(nid.get()).stream().map(farmer -> new FarmerDetails()
					.setId(farmer.getId()).setMobileNo(farmer.getMobileNo()).setNid(farmer.getNid()).setName(farmer.getName()))
					.collect(Collectors.toList());
		}
		return Arrays.asList();
	}

	@RequestMapping(value = "/app/searchFarmerMobile", method = RequestMethod.GET)
	@ResponseBody
	public List<FarmerDetails> searchFarmerMobile(@RequestParam(value = "query") Optional<String> mobile) {
		if (mobile.isPresent()) {
			return cropAccountingService.getFarmerByMobile(mobile.get()).stream().map(farmer -> new FarmerDetails()
					.setId(farmer.getId()).setMobileNo(farmer.getMobileNo()).setNid(farmer.getNid()).setName(farmer.getName()))
					.collect(Collectors.toList());
		}
		return Arrays.asList();
	}
}
