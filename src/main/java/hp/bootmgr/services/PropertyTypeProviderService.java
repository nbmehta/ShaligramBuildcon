/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import java.util.List;

import hp.bootmgr.vo.PropertyType;

public interface PropertyTypeProviderService {
	public PropertyType getPropertyTypeById(int id);
	public List<PropertyType> getPropertyTypes();
	public boolean addPropertyType(PropertyType propertyType);
	public boolean updatePropertyType(PropertyType propertyType);
	public boolean deletePropertyType(int id);
	public boolean deletePropertyType(PropertyType propertyType);
	public boolean isAlreadyAvailable(String name);
}