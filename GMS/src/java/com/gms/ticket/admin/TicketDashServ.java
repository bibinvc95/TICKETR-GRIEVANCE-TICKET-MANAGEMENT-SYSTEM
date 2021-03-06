package com.gms.ticket.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gms.connection.ConnectionFactory;
import com.gms.utill.DBUtil;

/**
 * Servlet implementation class TicketingServ
 */
@WebServlet("/auth/admticketdash")
public class TicketDashServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketDashServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	 
		HttpSession localsession = request.getSession(false);
		
		String name = (String) localsession.getAttribute("username");
		
		//================iterating User Details  =============	
		Connection con = null;
		JSONObject usesfactors = new JSONObject();  
		
		 //JSONArray jsonArr = new JSONArray();
		 
			HashMap<String, String> jobj = null;

			ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String,String>>();
			
		  
		try
		{
			
			//String query = "select * from ticket_tbl where assigndto='"+name+"' and status='Processing' || status='Hold' order by id desc";
			String query = "select * from ticket_tbl where assigndto='"+name+"' and status='Processing' || assigndto='"+name+"' and status='Hold' ||follower='"+name+"' and status='Processing' || follower='"+name+"' and status='Hold' ||requstuser='"+name+"' and status='Processing' || requstuser='"+name+"' order by id desc";																			
			
			System.out.println("Query :userloginservelt:"+ query);
			con = ConnectionFactory.getConnection();
			DBUtil dbUtil = new DBUtil(con);
			ResultSet rst = dbUtil.executeQuery(query);
			int check = 0;
			while(rst.next()){
				jobj = new HashMap<String, String>();
				jobj.put("id", rst.getString("id"));
				
				check = Integer.parseInt(rst.getString("id"));
				
				if(check< 10) {
					jobj.put("ticket_id", '0'+rst.getString("id"));
				}else {
					jobj.put("ticket_id", rst.getString("id"));	
				}
				//jobj.put("ticket_id", rst.getString("ticket_id"));
				jobj.put("title", rst.getString("title"));
				jobj.put("natureofwork", rst.getString("natureofwork"));
				jobj.put("status", rst.getString("status"));
				jobj.put("reqdate", rst.getString("reqdate"));
				jobj.put("reqtime", rst.getString("reqtime"));
				jobj.put("remarks", rst.getString("remarks"));
				jobj.put("requstuser", rst.getString("requstuser"));
				jobj.put("assigndto", rst.getString("assigndto"));
				jobj.put("taskcomplete_date", rst.getString("taskcomplete_date"));
				jobj.put("taskcomplete_time", rst.getString("taskcomplete_time"));
				jobj.put("typeofsupport", rst.getString("typeofsupport"));
				jobj.put("priority", rst.getString("priority"));
				jobj.put("follower", rst.getString("follower"));
				jobj.put("duedate", rst.getString("duedate"));
				jobj.put("duetime", rst.getString("duetime"));
				jobj.put("files", rst.getString("files"));
				userList.add(jobj);
			}
			 //  jsonArr.put(jobj);
               System.out.println("-----SMS Send Status---- : "+userList.toString());  
              
             //  response.setContentType("application/json");
   		     //response.getWriter().print(jsonArr.toString());
   		    
   		  request.setAttribute("userList", userList);
			request.getRequestDispatcher("/auth/admdashticket").forward(request, response);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			if(con !=null){
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	
}
