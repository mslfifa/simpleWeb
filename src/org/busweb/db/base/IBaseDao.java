package org.busweb.db.base;

import java.util.List;

public interface IBaseDao {
	List<Object[]> queryListByPara(String sql,Object ... paras) throws Exception;
	
	int modifyByPara(String sql,Object... paras) throws Exception;
}
