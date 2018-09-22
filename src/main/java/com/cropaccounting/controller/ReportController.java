package com.cropaccounting.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cropaccounting.repository.ReportDataRepository;

@RestController
public class ReportController {
	@Autowired
	private ReportDataRepository repo;
	
	@RequestMapping("/report/cropVsLand")
	@ResponseBody
	public ResponseEntity<?> cropVsLand(Model model) {
		return ResponseEntity.ok(repo.getLandCropSum());
	}
	
	@RequestMapping("/report/cropRegistration")
	@ResponseBody
	public ResponseEntity<?> cropRegistration(Model model) {
		return ResponseEntity.ok(repo.getRegistrationData());
	}
	
	@RequestMapping("/report/labourExpenceComparison")
	@ResponseBody
	public ResponseEntity<?> labourExpenceComparison(Model model) {
		return ResponseEntity.ok(repo.getComparisonOfLabour());
	}
	
	@RequestMapping("/report/landOwnerShip")
	@ResponseBody
	public ResponseEntity<?> landOwnerShip(Model model) {
		return ResponseEntity.ok(repo.getLandOwnershipData());
	}
	
	@RequestMapping("/report/production")
	@ResponseBody
	public ResponseEntity<?> production(Model model) {
		return ResponseEntity.ok(repo.getCropProduction());
	}
	
    @RequestMapping(value = "/report/template3", method=RequestMethod.GET)
    public String chart(Model model) {
         
        //first, add the regional sales
        Integer northeastSales = 17089;
        Integer westSales = 10603;
        Integer midwestSales = 5223;
        Integer southSales = 10111;
         
        model.addAttribute("northeastSales", northeastSales);
        model.addAttribute("southSales", southSales);
        model.addAttribute("midwestSales", midwestSales);
        model.addAttribute("westSales", westSales);
         
        //now add sales by lure type
        List<Integer> inshoreSales = Arrays.asList(4074, 3455, 4112);
        List<Integer> nearshoreSales = Arrays.asList(3222, 3011, 3788);
        List<Integer> offshoreSales = Arrays.asList(7811, 7098, 6455);
         
        model.addAttribute("inshoreSales", inshoreSales);
        model.addAttribute("nearshoreSales", nearshoreSales);
        model.addAttribute("offshoreSales", offshoreSales);
         
        return "redirect:/home";
    }
}