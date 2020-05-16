package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

public class BuildingConverter {

	public BuildingDTO converterToDTO(BuildingEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class); 
		if (entity.getBuildingArea() != null) {
			dto.setBuildingArea(String.valueOf(entity.getBuildingArea()));
		}
		if (entity.getNumberOfBasement() != null) {
			dto.setBuildingArea(String.valueOf(entity.getNumberOfBasement()));
		}

		return dto; 
	}

}
