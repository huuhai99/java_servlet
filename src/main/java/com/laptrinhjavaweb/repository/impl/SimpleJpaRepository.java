package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.anotation.Entity;
import com.laptrinhjavaweb.anotation.Table;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.repository.JpaRepository;

public class SimpleJpaRepository<T> implements JpaRepository<T> {

	private Class<T> zClass;

	/*
	 * phương thức dùng để get đối tượng trong T. và SimpleJpaRepostory<T> là 1 cái
	 * mảng phương thức thứ 3 dùng để add cái parameterzedType vào 1 cái class là
	 * zclass
	 */

	@SuppressWarnings("unchecked")
	public SimpleJpaRepository() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];

	}

	public List<T> findAll(Map<String, Object> properties,Pageable pageable,Object...where) {
		String tableName = "";
		// zClass.isAnnotationPresent(Table.class) nếu zclass có cái table.class thì
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder sql = new StringBuilder("select * from " + tableName+ " A where 1=1 ");
		sql = createSQLfindAll(sql , properties); // buil chung cho tat ca
		if (where != null && where.length > 0) {
			sql.append(where[0]);
		}
		sql.append(" limit "+pageable.getOffset()+", "+pageable.getLimit()+"");
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
		Connection connection = null;
//		PreparedStatement statement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			//Doing để kết nối với csdl
			connection = EntityManagerFactory.getConnection();
			// thực thi csdl
			statement = connection.createStatement();
			// truyền csdl và resultSet vf resultSet là 1 cái bảng tạm
			resultSet = statement.executeQuery(sql.toString());
			return resultSetMapper.mapRow(resultSet, this.zClass);
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}

	}

	private StringBuilder createSQLfindAll(StringBuilder where,Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			String[] keys = new String[params.size()];// danh sach key (name, district , buildingArea...)
			Object[] values = new Object[params.size()];// du lieu
			int i = 0;
			for (Map.Entry<String, Object> item : params.entrySet()) {
				keys[i] = item.getKey();
				values[i] = item.getValue();
				i++;
			}
			for (int i1 = 0; i1 < keys.length; i1++) {
				if ((values[i1] instanceof String) && (StringUtils.isNotBlank(values[i1].toString()))) {				
					where.append("AND LOWER(A." + keys[i1] + ")  LIKE '%" + values[i1].toString() + "%' ");
				
				} else if ((values[i1] instanceof Integer)&&(values[i1]!= null)){
					where.append("AND LOWER(A." + keys[i1] + ") = " + values[i1] + " ");

				} else if ((values[i1] instanceof Long) && (values[i1] != null)) {
					where.append("AND LOWER(A." + keys[i1] + ") = " + values[i1] + " ");

				}
			}
		}
		return where;
	}

	@Override
	public List<T> findAll(Map<String, Object> properties, Object... where) {
		String tableName = "";
		// zClass.isAnnotationPresent(Table.class) nếu zclass có cái table.class thì
		if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
			Table tableClass = zClass.getAnnotation(Table.class);
			tableName = tableClass.name();
		}
		StringBuilder sql = new StringBuilder("select * from " + tableName+ " A where 1=1 ");
		sql = createSQLfindAll(sql , properties); // buil chung cho tat ca
		if (where != null && where.length > 0) {
			sql.append(where[0]);
		}
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
		Connection connection = null;
//		PreparedStatement statement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			//Doing để kết nối với csdl
			connection = EntityManagerFactory.getConnection();
			// thực thi csdl
			statement = connection.createStatement();
			// truyền csdl và resultSet vf resultSet là 1 cái bảng tạm
			resultSet = statement.executeQuery(sql.toString());
			return resultSetMapper.mapRow(resultSet, this.zClass);
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}

	}

	
}