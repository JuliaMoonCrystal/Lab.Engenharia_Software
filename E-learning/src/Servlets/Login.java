package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Login")
public class Login extends HttpServlet{
	
	public boolean consulta_Login(String user) {
		final String DRIVER = "org.mariadb.jdbc.Driver";
		final String URL = "jdbc:mysql://localhost:8080/teste_login";
		final String USER = "tempesta";
		final String PASS = "gamamori";
		
		try {
			Class.forName(DRIVER);
			System.out.println("Driver Carregado");
			Connection con = 
					DriverManager.getConnection(URL, USER, PASS);
			System.out.println("Conexao feita");
			String sql = "SELECT * FROM usuarios " + "WHERE usuario = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			boolean r = rs.first();
			con.close();
			return r;
		} catch(Exception e) { 
			System.out.println("Erro ao acessar o banco");
			e.printStackTrace();
		}
		return false;
	}
	
	public void doGet(HttpServletRequest req, 
			HttpServletResponse res) throws IOException {
 		res.sendRedirect("/Login.jsp");
		
	}
	
	@Override
	public void doPost(HttpServletRequest req, 
			HttpServletResponse res) throws IOException { 
		String nome = req.getParameter("TXTUSER");
		String emaIl = req.getParameter("TXTEMAL");
		String senha = req.getParameter("TXTSENHA");
		PrintWriter out = res.getWriter();
		if (this.consulta_Login(nome)) { 
			out.println("<h1>Usuario autenticado</h1>");
		} else {
			out.println("<h1>Usuario inexistente</h1>");
		}
	}
}
