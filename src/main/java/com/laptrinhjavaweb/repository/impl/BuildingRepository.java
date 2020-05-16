package com.laptrinhjavaweb.repository.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.IBuildingRepository;

public class BuildingRepository extends SimpleJpaRepository<BuildingEntity> implements IBuildingRepository {

//	@Override
//	public List<BuildingEntity> findAll(Map<String, Object> params, Pageable pageable) {
//		String name = (String) params.get("name");
//		String district = (String) params.get("district");
//		StringBuilder where = new StringBuilder(" ");

		/*
		 * StringBuilder where = new StringBuilder(" ");
		 * if(StringUtils.isNotBlank(name)) { where.append("AND A.name LIKE '%" + name +
		 * "%' ");
		 * 
		 * } if(StringUtils.isNotBlank(district)) {
		 * where.append("AND A.district LIKE '%" + district + "%' ");
		 * 
		 * } if(params.get("buildingArea") != null) { Integer buildingArea = (Integer)
		 * params.get("buildingArea"); where.append(" AND A.buildingArea = "+
		 * buildingArea +"  ");
		 * 
		 * } if(params.get("numberOfBasement") != null) { Integer numberOfBasement =
		 * (Integer) params.get("numberOfBasement");
		 * where.append(" AND A.numberOfBasement =  " + numberOfBasement +" ");
		 * 
		 * }
		 */
		
//		return this.findAll( params,pageable);
//	}

}
