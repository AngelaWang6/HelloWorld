package jdbctest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HelloJDBC {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DAVID";
	private static final String PASSWORD = "123456";

//	private static final String SQL = "INSERT INTO DEPARTMENT(DEPTNO, DNAME, LOC)VALUES(50 , '會計部' , '台灣台北')";
	private static final String SQL = "INSERT INTO DEPARTMENT(DEPTNO, DNAME, LOC)VALUES(?, ?, ?)";
//	private static final String SQL = "SELECT dname , deptno , loc FROM DEPARTMENT ORDER BY DEPTNO DESC";
	
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入要新增的部門編號");
		int deptno = sc.nextInt();
		System.out.println("請輸入要新增的部門名稱");
		String dname = sc.next();
		System.out.println("請輸入要新增的部門所在地!!");
		String loc = sc.next();
		
		sc.close();
		
		
		try {
			// step 1:載入驅動
			Class.forName(DRIVER);
			System.out.println("載入成功");

			// step 2:建立連線
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("連線成功");

			// step 3:準備SQL指令與執行
//			stmt = con.createStatement();
//			int count = stmt.executeUpdate(SQL);
//			System.out.println("更新" + count + "筆資料");
			
//			stmt = con.createStatement();
//			rs = stmt.executeQuery(SQL);
			
//			while(rs.next()) {
//				int deptno = rs.getInt("DEPTNO");  // 欄位名稱
//				String dname = rs.getString("DNAME");  // 欄位名稱
//				String loc = rs.getString("LOC");  // 欄位名稱
//---------------------------------------------------------------------			
//				int deptno = rs.getInt(1);  // 欄位索引值，從1開始
//				String dname = rs.getString(2);   // 欄位索 引值
//				String loc = rs.getString(3);   // 欄位索引值
//				
//				System.out.println("DEPTNO = " + deptno);
//				System.out.println("DNAME = " + dname);
//				System.out.println("LOC = " + loc);
//				System.out.println("======================");
//			}
			
			// step 3:準備SQL指令與執行(PreparedStatement版)
			pstmt = con.prepareStatement(SQL);  // 此時先把部分SQL指令交給資料庫編譯了，接下來就等參數拿到即可執行
			// 拿到參數後，要一個蘿蔔一個坑設定給該句子的各個問號後才能執行
			pstmt.setInt(1, deptno);
			pstmt.setString(2, dname);
			pstmt.setString(3, loc);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) { // 關閉連線確定不為空值，才有關閉連線的必要
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) { // 關閉連線確定不為空值，才有關閉連線的必要
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) { // 關閉連線確定不為空值，才有關閉連線的必要
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
}