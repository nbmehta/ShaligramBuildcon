package hp.bootmgr.dao;

import org.hibernate.Session;
import org.hibernate.StatelessSession;

import hp.bootmgr.vo.PropertyDetail;

public interface PropertyDetailDAO extends GenericDAO<PropertyDetail, Integer>{
	public Session getSession();
	public StatelessSession getStatelessSession();
}
