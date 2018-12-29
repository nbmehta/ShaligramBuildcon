package hp.bootmgr.services.impl;

import java.util.List;

import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectPropertyBlock;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.PropertyDetailDAO;
import hp.bootmgr.services.PropertyDetailService;
import hp.bootmgr.vo.PropertyDetail;

@Transactional
@Service("propertyDetailService")
@SuppressWarnings("unchecked")
public class PropertyDetailServiceImpl implements PropertyDetailService{

	@Autowired
	private PropertyDetailDAO propertyDetailDAO;

    private static final Logger logger = Logger.getLogger(PropertyDetailService.class);
	
	@Override
	public List<PropertyDetail> getAll() {
		List<PropertyDetail> ret = null;
		try {
			ret = propertyDetailDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public PropertyDetail getById(Integer key) {
		try {
			return propertyDetailDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			propertyDetailDAO.deleteById(key);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean delete(PropertyDetail object) {
		try {
			propertyDetailDAO.delete(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean update(PropertyDetail object) {
		try {
			propertyDetailDAO.update(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean save(PropertyDetail p) {
		try {
			propertyDetailDAO.save(p);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<PropertyDetail> getPropertiesByBlock(int blockID) {
		try {
			return propertyDetailDAO.getSession().createCriteria(PropertyDetail.class)
				.add(Restrictions.eq("block.blockId", blockID))
				.addOrder(Order.asc("propertyNumber"))
				.list();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PropertyDetail> getPropertiesByBlock(int blockID, boolean onlyNonBooked) {
		if(onlyNonBooked) {
			Query query = propertyDetailDAO.getSession()
					.createQuery("select property FROM PropertyDetail property where property.id not in (select detail.propertyDetail.id from BookingDetail detail) AND property.block.blockId=:blockID");
			query.setInteger("blockID", blockID);
			return query.list();
		} else return getPropertiesByBlock(blockID);
	}

	@Override
	public PropertyDetail getPropertyDetailByQualifiedName(Project project, String name) {
        String[] arr = name.split("-");
        ProjectPropertyBlock block = null;
        if(arr.length != 2) {
            logger.error("Malformed Property Name: " + name);
            return null;
        }
        // Load Project Block
        for(ProjectPropertyBlock propertyBlock : project.getBlocks()) {
            if(propertyBlock.getBlock().equalsIgnoreCase(arr[0])) {
                block = propertyBlock;
                break;
            }
        }
        if(block == null) {
            logger.error("Invalid Block: " + arr[0] + " from: " + name);
            return null;
        }
        List ret = propertyDetailDAO.getSession().createCriteria(PropertyDetail.class)
                .add(Restrictions.eq("project.id", project.getId()))
                .add(Restrictions.eq("block.blockId", block.getBlockId()))
                .add(Restrictions.eq("propertyNumber", Integer.parseInt(arr[1]))).list();
        return ret.size() > 0 ? (PropertyDetail) ret.get(0) : null;
	}

	@Override
	public boolean saveOrderedProperties(PropertyDetail detailModel, int startNum, int endNum) {
		try {
			StatelessSession session = propertyDetailDAO.getStatelessSession();
			Transaction tx = session.beginTransaction();
			for(int i=startNum; i <= endNum; i++) {
				detailModel.setPropertyNumber(i);
				session.insert(detailModel);
			}
			tx.commit();
			session.close();
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
