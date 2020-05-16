package com.laptrinhjavaweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhjavaweb.anotation.Column;
import com.laptrinhjavaweb.anotation.Entity;

public class ResultSetMapper<T> {
	public List<T> mapRow(ResultSet rs, Class<T> zClass) {
		List<T> results = new ArrayList<>();

		try {
			if (zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				Field[] fields = zClass.getDeclaredFields();
				while (rs.next()) {
					T object = zClass.newInstance();
					for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
						String columnName = resultSetMetaData.getColumnName(i + 1);
						Object columnValue = rs.getObject(i + 1);
						ColumnModel columnModel = new ColumnModel();
						columnModel.setColumnName(columnName);
						columnModel.setColumValue(columnValue);
//						for (Field field : fields) {
//							if (field.isAnnotationPresent(Column.class)) {
//								Column column = field.getAnnotation(Column.class);
//								if (column.name().equals(columnName) && columnValue != null) {
//									BeanUtils.setProperty(object, field.getName(), columnValue);
//									break;
//
//								}
//							}
//						}
						convertResultSetToEntity(fields, columnModel, object);
						Class<?> parentClass = zClass.getSuperclass();// kiem tr xem lop zClass con co lop con nao k
						while (parentClass != null) {
							Field[] fieldParent = parentClass.getDeclaredFields();
//							for (Field field : fieldParent) {
//								if (field.isAnnotationPresent(Column.class)) {
//									Column column = field.getAnnotation(Column.class);
//									if (column.name().equals(columnName) && columnValue != null) {
//										BeanUtils.setProperty(object, field.getName(), columnValue);
//										break;
//									}
//								}
//							}
							convertResultSetToEntity(fieldParent, columnModel, object);
							parentClass = parentClass.getSuperclass();
						}
					}
					results.add(object);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return results;

	}

	@SuppressWarnings("unchecked")
	private void convertResultSetToEntity(Field[] fields, ColumnModel columnModel, Object... objects) {
		T object = (T) objects[0];
		// ham chung cho field anh fieldParent khi co double key word
		
		try {
			for (Field field : fields) {
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					if (column.name().equals(columnModel.getColumnName()) && columnModel.getColumValue() != null) {
						BeanUtils.setProperty(object, field.getName(), columnModel.getColumValue());
						break;
					}
				}
			}
		} catch (InvocationTargetException | IllegalAccessException e) {
			System.out.println(e.getMessage());
		}
	}

	static class ColumnModel {
		private String columnName;
		private Object columValue;

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public Object getColumValue() {
			return columValue;
		}

		public void setColumValue(Object columValue) {
			this.columValue = columValue;
		}

	}

}
