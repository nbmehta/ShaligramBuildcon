/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import java.util.List;

import hp.bootmgr.dao.PropertyTypeDAO;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.vo.PropertyType;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("propertyTypeProviderService")
public class PropertyTypeProviderServiceImpl implements PropertyTypeProviderService {
	
	@Autowired
	private PropertyTypeDAO propertyTypeDAO;

	@Override
	public PropertyType getPropertyTypeById(int id) {
		try {
			return propertyTypeDAO.getById(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PropertyType> getPropertyTypes() {
		List<PropertyType> ret = null;
		try {
			ret = propertyTypeDAO.getAll();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public boolean addPropertyType(PropertyType propertyType) {
		try {
			propertyTypeDAO.save(propertyType);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;			
		}
	}

	@Override
	public boolean updatePropertyType(PropertyType propertyType) {
		try {
			propertyTypeDAO.update(propertyType);
			return true;
		} catch(Exception ex) {
			return false;			
		}
	}

	@Override
	public boolean deletePropertyType(int id) {
		try {
			propertyTypeDAO.deleteById(id);
			return true;
		} catch(Exception ex) {
			return false;			
		}
	}

	@Override
	public boolean deletePropertyType(PropertyType propertyType) {
		try {
			propertyTypeDAO.delete(propertyType);
			return true;
		} catch(Exception ex) {
			return false;			
		}
	}

	@Override
	public boolean isAlreadyAvailable(String name) {
		try {
			return propertyTypeDAO.getSession().createCriteria(PropertyType.class).add(Restrictions.eq("name",name)).list().size() > 0;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;			
		}
	}

}
