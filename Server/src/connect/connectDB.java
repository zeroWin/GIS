package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class connectDB {
	
	// 驱动程序名
	private String driver = "com.mysql.jdbc.Driver";
	
	// URL指向要访问的数据库名gis
	private String url = "jdbc:mysql://localhost:3306/gis?characterEncoding=utf8&useSSL=true";
	
	// Mysql配置时用户名
	private String user = "root";
	
	// Mysql配置时的密码
	private String password = "";
	
	// 声明Connection对象
	private Connection con;
	
	// 存储经度Longitude
	private ArrayList<Double> LongitudeList = new ArrayList<Double>(); 
	
	// 存储纬度Latitude
	private ArrayList<Double> LatitudeList = new ArrayList<Double>(); 
	
	// 存储距离
	private ArrayList<Double> DistanceList = new ArrayList<Double>();
	
	// 存储从数据库读出GPS坐标，0维Lat,经度，1维Long，纬度
	private double[][] DatabaseGPS = null;
	
	
	// 存储经纬度到距离的索引
	Map<RailwayData, Double> map = new HashMap<RailwayData, Double>();
	
	// 存储每个火车最新的位置
	Map<String,Double[]> mapRailwayLocal = new HashMap<String,Double[]>();
	
	public connectDB(){
		try{
			// 加载驱动程序
			Class.forName(driver);
			////1.getConnection()方法，连接MySQL数据库！！
			con = DriverManager.getConnection(url,user,password);
						
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			
		} catch(ClassNotFoundException e) {   
			//数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
		} catch(SQLException e) {
			//数据库连接失败异常处理
			e.printStackTrace();  
		} catch (Exception e) {
			// TODO: handle exception
	            e.printStackTrace();
	    } finally{
	    	System.out.println("数据库连接完毕！！");
	    }
	}
	
	
	public void loadSqlData(){
		try{
			// 创建statement类，用来执行SQL语句
			Statement statement = con.createStatement();
			Statement statement_count = con.createStatement();
			// 获取数据
			String sql =  "SELECT AVG(KmMark) as KmMark,Latitude,Longitude from xingtanlugps GROUP BY Latitude,Longitude";
			//String sql =  "select * from railwaygps";
			ResultSet rs = statement.executeQuery(sql);
			
			sql = "SELECT count(DISTINCT Latitude,Longitude) as count1 FROM xingtanlugps";
			//sql = "SELECT count(*) as count1 FROM railwaygps";
			ResultSet rs_count = statement_count.executeQuery(sql);
			if(rs_count.next()){
				DatabaseGPS = new double[Integer.valueOf(rs_count.getString("count1"))][2];
			}
			int index = 0;
			while(rs.next()){
				LatitudeList.add(Double.valueOf(rs.getString("Latitude")));
				LongitudeList.add(Double.valueOf(rs.getString("Longitude")));
				DistanceList.add(Double.valueOf(rs.getString("KmMark")));
				
				DatabaseGPS[index][0] = Double.valueOf(rs.getString("Latitude"));
				DatabaseGPS[index][1] = Double.valueOf(rs.getString("Longitude"));
				
//				System.out.println(rs.getString("ID") + "\t" + rs.getString("Longitude") + "\t" + rs.getString("Latitude"));
				++index;
				
			}
			
			System.out.println("路径数据读取完成");
			//int a = tree.nearest(DatabaseGPS[0]);
//			System.out.println("KD-tree构建完成");
			
			

			for(int i = 0; i < LatitudeList.size(); ++i){
				RailwayData temp = new RailwayData(new Double[]{LatitudeList.get(i),LongitudeList.get(i)});
//				RailwayData temp1 = new RailwayData(new Double[]{DatabaseGPS[i][0],DatabaseGPS[i][1]});
//				if(temp.hashCode() != temp1.hashCode())
//					System.out.println("error");
				map.put(temp, DistanceList.get(i));
			}
			System.out.println("HashMap构建完成");
			
		} catch(SQLException e) {
			//数据库连接失败异常处理
			e.printStackTrace();  
		} catch (Exception e) {
			// TODO: handle exception
	            e.printStackTrace();
	    } finally{
	    	System.out.println("数据库数据成功获取！！");
	    }	
	}
	
	// 判断距离,单位是米
	public double judgeDistance(double Latitude, double Longitude){
		double result = Integer.MAX_VALUE;
		
		// 获取当前人的位置
		double peopleLocal = findClosedLocal(Latitude,Longitude);
		System.out.println("工人当前位置:"+peopleLocal);
		
		// 遍历hashMap找离的最近的火车
		for(Map.Entry<String, Double[]> entry : mapRailwayLocal.entrySet())
		{
			Double[] temp = entry.getValue();
			if(Math.abs(new Date().getTime() - temp[2]) < 30000){ //火车最新位置和工人最新位置在30s以内，认为有效
				double railwayLocal = findClosedLocal(temp[0], temp[1]);
				System.out.println("火车当前位置:"+railwayLocal);
				double dis = Math.abs(railwayLocal - peopleLocal);
				if(dis < result)
					result = dis;
				
			}
			
		}
		return result*1000;
	}
	
	
	// 返回与输入经纬度离的最近的点的KmMark
	private double findClosedLocal(double Latitude, double Longitude){

		// 在数组DatabaseGPS找离给定点最近的点
		int i = 0;
		int j = DatabaseGPS.length - 1;
		while(i < j)
		{
			double disiToGive = Math.pow(Latitude-DatabaseGPS[i][0], 2) + Math.pow(Longitude-DatabaseGPS[i][1], 2);
			double disjToGive = Math.pow(Latitude-DatabaseGPS[j][0], 2) + Math.pow(Longitude-DatabaseGPS[j][1], 2);
			if(disiToGive < disjToGive)
				j = (i + j)/2; // 向下取整
			else
				i = (int) Math.ceil((i + j) / 2.0);

			
		}
		RailwayData temp = new RailwayData(new Double[]{DatabaseGPS[i][0],DatabaseGPS[i][1]});
		
		return map.get(temp);

	}
	
	// 插入数据
	public void insertLocal(int client_type,String client_number,double Latitude,double Longitude){
		try{	
			String sql = null;
			PreparedStatement preStmt = null;
			// 类型为1是火车
			if(client_type == 1){
				sql = "INSERT INTO xintanlurailwaylocal (railwayNum, Latitude, Longitude) VALUES (?,?,?)";
				
				Double[] insertData = new Double[3];
				insertData[0] = Latitude;
				insertData[1] = Longitude;
				insertData[2] = (double) new Date().getTime();
				// 插入hashmap或更新hashMap的值
				mapRailwayLocal.put(client_number, insertData);
			}
			else { // 类型为0是工人
				sql = "INSERT INTO xintanluipqclocal (peopleNum, Latitude, Longitude) VALUES (?,?,?)";	
			}
			preStmt = con.prepareStatement(sql);	
			preStmt.setString(1, client_number);
			preStmt.setString(2, Double.toString(Latitude));
			preStmt.setString(3, Double.toString(Longitude));
			preStmt.executeUpdate();
			
			System.out.println("插入数据实时位置数据完成");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 判断用户是否存在
	public boolean judgeLogin(int client_type,String client_number){
		boolean result = false;
		try{		
			Statement statement = con.createStatement();
			String sql = null;
			if(client_type == 1){ //类型1位火车
				sql =  "SELECT count(*) as count1 from railwayinfo where railwayNum = " + Integer.valueOf(client_number);
			}
			else { // 类型为0是工人
				sql =  "SELECT count(*) as count1 from userinfo where userNum = " + Integer.valueOf(client_number);
			}
			ResultSet rs = statement.executeQuery(sql);
			if(rs.next()){
				int count = Integer.valueOf(rs.getString("count1"));
				if(count == 1)
					result = true;
			}			
			System.out.println("登陆校验完成");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
