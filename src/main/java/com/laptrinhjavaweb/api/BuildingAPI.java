package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.HttpUtil;

@WebServlet(urlPatterns = {"/api-building"})
public class BuildingAPI extends HttpServlet{
	
	private static final long serialVersionUID = -9091477560022412189L;
	private IBuildingService buildingService = new BuildingService();;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper(); 
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		BuildingDTO building = FormUtil.toModel(BuildingDTO.class, request);
		IBuildingService buildingService = new BuildingService();
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder().setName(building.getName())
				.setDistrict(building.getDistrict())
		 .setBuildingArea(building.getBuildingArea()).setNumberOfBasement(building.getNumberOfBasement())
				.build();
		Pageable pageable = new PageRequest(building.getPage() , building.getLimit());
		List<BuildingDTO> buildings = buildingService.findAll(buildingSearchBuilder, pageable);
		
		mapper.writeValue(response.getOutputStream(), buildings);
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	

}
