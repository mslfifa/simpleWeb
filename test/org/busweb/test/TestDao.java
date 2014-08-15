package org.busweb.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.busweb.db.base.BaseDaoImpl;
import org.busweb.db.base.IBaseDao;

public class TestDao {
	
	private static class DbReadTask implements Runnable{
		
		public void run() {
			IBaseDao dao = new BaseDaoImpl();
			try {
				
				List<Object[]> list = dao.queryListByPara(" SELECT                   "+
												"   `id`,                 "+
												"   `v_name`,                "+
												"   `valid`,              "+
												"   `create_time`        "+
												" FROM `test_bean`       "+
												" WHERE `valid` = ?    ", false);
				int rowIndex=0;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					System.out.println("@@@@ thread["+Thread.currentThread().getId()+"]rowData["+(rowIndex++)+"]:");
					Object[] rowArr = (Object[]) iterator.next();
					for (int i = 0; i < rowArr.length; i++) {
						System.out.print("index["+i+"]="+rowArr[i]+"|");
					}
					System.out.println();
				}
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	private static class DbWriteTask implements Runnable{
		
		public void run() {
			IBaseDao dao = new BaseDaoImpl();
			try {
				String insertSql =  " INSERT INTO `test_bean`          "+
									" (`v_name`,`valid`,`create_time`) "+
									" VALUES (?,?,?);                  ";
				UUID uuid = UUID.randomUUID();
				int num = dao.modifyByPara(insertSql, true,uuid.toString(),false, new Date());
				System.out.println("####### ����"+num+"������¼");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//
		for (int i = 0; i < 10; i++) {
			if(i%3==0){
				//new Thread(new DbWriteTask()).start();
			}else{
				new Thread(new DbReadTask()).start();
			}
			
		}
		
		
	}

}
