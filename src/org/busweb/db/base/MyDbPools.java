package org.busweb.db.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyDbPools {
	
	private static final int MAX_POOL_SIZE = 10;
	
	private static ArrayBlockingQueue<Connection> connQueue = new ArrayBlockingQueue(MAX_POOL_SIZE,true);
	

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?userUnicode=true&characterEncoding=utf-8", "root", "123456");
			for (int i = 0; i < MAX_POOL_SIZE; i++) {
				connQueue.offer(conn);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}


	public static synchronized Connection getConnection() throws InterruptedException{
		//从队列取出Connection
		return connQueue.poll(40,TimeUnit.SECONDS);
	}
	
	public static synchronized void retrieveConnection(Connection conn){
		//Connection对象回收到队列
		connQueue.offer(conn);
	}
}
