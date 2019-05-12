import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.corba.se.pept.transport.Connection;


public class loginServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        
		Connection con=null;
		PrintWriter out =response.getWriter();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("加载成功");
			con=(Connection)DriverManager.getConnection(
					"jdbc:mysql://127.0.01:3306/userss","root","lml123"
					);
			System.out.println("sql连接成功");
			
			Statement sta=(Statement)con.createStatement();
			String user=request.getParameter("user");
			String pwd=request.getParameter("pwd");
			ResultSet rs=(ResultSet)sta.executeQuery("select*from user where user ='"+user+"'and pwd='"+pwd+"'");
			if(rs.next()){
				String yh=rs.getString("user");
				String mm=rs.getString("pwd");
				if(yh.equals(user) && mm.equals(pwd)){
					HttpSession session=requset.getSession();
					session.setAttribute("username",user);
					session.setAttribute("ped",pwd);
					session.setMaxInactiveInterval(10);
					System.out.println("你的信息已存入");
					request.getRequestDispatcher("home").forward(request,response);
				}
			}else{
				response.sendRedirect("/limeiling/register.html");
			}
		}
	
	}

}
