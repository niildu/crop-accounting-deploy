package com.cropaccounting.krishiapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cropaccounting.ex.BadRequestException;
import com.cropaccounting.krishi.api.clientapi.ApiException;
import com.cropaccounting.krishi.api.clientapi.KrishiAPI;
import com.cropaccounting.krishi.api.clientapi.model.Crop;
import com.cropaccounting.krishi.api.clientapi.model.Variety;

@Service
public class KrishiAPIService {
	private static final String CROPS_CACHE = "krishi_crops";
	private static final String VARIETIES_CACHE = "krishi_varieties";
	private static final String A2I_KRISHI = "a2i";
	@Autowired
	private KrishiAPI service;
	
	@Cacheable(value = CROPS_CACHE, unless = "#result == null")
	public List<Crop> getCrops() {
		try {
			return service.getCrops().getData();
		} catch (ApiException e) {
			throw new BadRequestException(A2I_KRISHI, "Crops not found");
		}
	}
	
	@Cacheable(value = VARIETIES_CACHE, unless = "#result == null")
	public List<Variety> getVarities() {
		try {
			return service.getVarieities().getData();
		} catch (ApiException e) {
			throw new BadRequestException(A2I_KRISHI, "Varieties not found");
		}
	}
}
