package org.busweb.web.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.busweb.db.base.BaseDaoImpl;
import org.busweb.db.base.IBaseDao;
import org.busweb.db.base.MyDbPools;

/**
 * Servlet implementation class BusOpServlet
 */
public class BusOpServlet extends HttpServlet {
	
	private IBaseDao dao = new BaseDaoImpl();
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BusOpServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("##### init ####");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("##### destroy ####");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodType = request.getParameter("_method_type");
		
		if("modify".equals(methodType)){
			modifyProcess(request,response);
		}else{
			queryProcess(request,response);
		}
	}

	private void modifyProcess(HttpServletRequest request,
			HttpServletResponse response) {

			
			String insertSql =  " INSERT INTO `test_target`               "+
								" (`v_name`,`valid`,`create_time`)        "+
								" (                                       "+
								"   SELECT `v_name`,`valid`,`create_time` "+
								"   FROM `test_bean` s                    "+
								"   WHERE NOT EXISTS (                    "+
								"   	SELECT 1                            "+
								"   	FROM `test_target` t                "+
								"   	WHERE  s.`v_name`=t.`v_name`        "+
								"   )                                     "+
								" );                                      ";
			
			try {
				dao.modifyByPara(insertSql);
				queryProcess(request,response);
			} catch (Exception e) {
				response.setContentType("text/html;charset=UTF-8");
				try {
					response.getWriter().print("<p><font color=red>导入出错:"+e.getMessage()+"</font></p>");
					response.getWriter().flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}
	
	
	private void modifyProcessOld(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String soruceSql =" SELECT `v_name`,`valid`,`create_time` "+
					" FROM `test_bean`       						";
			List<Object[]> soruceList  = null;
			soruceList = soruceList = dao.queryListByPara(soruceSql);
				
			Connection conn = conn = MyDbPools.getConnection();
			
			String insertSql = " insert into `test_target` "+
	                           " (`v_name`,`valid`,`create_time`) "+
	                           " values ( ? , ? , ? ); ";
			if(soruceList!=null && !soruceList.isEmpty()){
				try {
					conn.setAutoCommit(false);
					for (Iterator iterator = soruceList.iterator(); iterator.hasNext();) {
						Object[] objects = (Object[]) iterator.next();
						PreparedStatement ptmt = conn.prepareStatement(insertSql);
						for (int i = 0; i < objects.length; i++) {
							ptmt.setObject(i+1, objects[i]);
						}
						ptmt.executeUpdate();
					}
					//批量提交
					conn.commit();
				} catch (Exception e) {
					conn.rollback();
					throw e;
				}finally{
					conn.setAutoCommit(true);
					MyDbPools.retrieveConnection(conn);
				}
				
			}
			
			queryProcess(request,response);
		} catch (Exception e) {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().print("<p><font color=red>导入出错:"+e.getMessage()+"</font></p>");
				response.getWriter().flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

	private void queryProcess(HttpServletRequest request,
			HttpServletResponse response) {
		String soruceSql =	" SELECT `id`,`v_name`,`valid`,`create_time`    "+
							" FROM `test_bean`       						";
		String targetSql =	" SELECT `id`,`v_name`,`valid`,`create_time`    "+
							" FROM `test_target`       						";
		
		try {
			List<Object[]> soruceList = dao.queryListByPara(soruceSql);
			List<Object[]> targetList = dao.queryListByPara(targetSql);
			request.setAttribute("soruceList", soruceList);
			request.setAttribute("targetList", targetList);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
