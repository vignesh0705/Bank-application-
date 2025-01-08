package Database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
	static Connection con=null;
	String url="jdbc:mysql://localhost:3306/bankproject";
	String userName="root";
	String passWord="vicky@07";
	
	public Connection getConnection() {
		if(con==null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection(url,userName,passWord);
				System.out.println("Connection established successfully");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return con;
	}
	public static void main(String[] args) {
		DBconnection connect=new DBconnection();
		Connection con=connect.getConnection();
	}
}
