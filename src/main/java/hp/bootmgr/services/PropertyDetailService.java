package hp.bootmgr.services;

import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.PropertyDetail;

import java.util.List;

public interface PropertyDetailService {
	List<PropertyDetail> getAll();
	PropertyDetail getById(Integer key);
	boolean deleteById(Integer key);
	boolean delete(PropertyDetail object);
	boolean update(PropertyDetail object);
	boolean save(PropertyDetail p);
	boolean saveOrderedProperties(PropertyDetail detailModel, int startNum, int endNum);
	List<PropertyDetail> getPropertiesByBlock(int blockID);
	List<PropertyDetail> getPropertiesByBlock(int blockID, boolean onlyNonBooked);
    PropertyDetail getPropertyDetailByQualifiedName(Project project, String name);
}
