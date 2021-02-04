package pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PizzaDAO {
	private final String url;
	private final String username;
	private final String password;
	
	public PizzaDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Pizza getPizza(int id) throws SQLException {
		final String sql = "SELECT * FROM pizza WHERE pizza_id = ?";
		
		Pizza pizza = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String name = rs.getString("name");
			int pepperoni = rs.getInt("pepperoni");
			int sausage = rs.getInt("sausage");
			int bacon = rs.getInt("bacon");
			int quantity = rs.getInt("quantity");
			
			pizza = new Pizza(id, name, pepperoni, sausage, bacon, quantity);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return pizza;
	}
	
	public List<Pizza> getPizzas() throws SQLException {
		final String sql = "SELECT * FROM pizza ORDER BY pizza_id ASC";
		
		List<Pizza> pizza = new ArrayList<Pizza>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("pizza_id");
			String name = rs.getString("name");
			int pepperoni = rs.getInt("pepperoni");
			int sausage = rs.getInt("sausage");
			int bacon = rs.getInt("bacon");
			int quantity = rs.getInt("quantity");
			
			pizza.add(new Pizza(id, name, pepperoni, sausage, bacon, quantity));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return pizza;
	}
	
	public boolean insertPizza(String name, int pepperoni, int sausage, int bacon, int quantity) throws SQLException {       
		final String sql = "INSERT INTO pizza (name, pepperoni, sausage, bacon, quantity) " +
			"VALUES (?, ?, ?, ?, ?)";
		
        Connection conn = getConnection();        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, name);
        pstmt.setInt(2, pepperoni);
        pstmt.setInt(3, sausage);
        pstmt.setInt(4, bacon);
        pstmt.setInt(5, quantity);
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
	
	public boolean updatePizza(Pizza pizza) throws SQLException {
        final String sql = "UPDATE pizza SET name = ?, pepperoni = ?, sausage = ?, bacon = ?, quantity = ? " +
            "WHERE pizza_id = ?";
                
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
                
        pstmt.setString(1, pizza.getName());
        pstmt.setInt(2, pizza.getPepperoni());
        pstmt.setInt(3, pizza.getSausage());
        pstmt.setInt(4, pizza.getBacon());
        pstmt.setInt(5, pizza.getQuantity());
        pstmt.setInt(6, pizza.getId());
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
	
    public boolean deletePizza(Pizza pizza) throws SQLException {
    	final String sql = "DELETE FROM pizza WHERE pizza_id = ?";
    	
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setInt(1, pizza.getId());
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
    
    public List<Pizza> searchPizza(String category, String query) throws SQLException {
    	String sql = "";
    	Connection conn = getConnection();
    	PreparedStatement pstmt = null;
    	
    	if (category.equals("ID")) {
    		int number = Integer.parseInt(query);
    		sql = "SELECT * FROM pizza WHERE pizza_id = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, number);
    		
    	} else if (category.equals("name")) {
    		sql = "SELECT * FROM pizza WHERE name = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, query);
    	}
    	
    	List<Pizza> pizza = new ArrayList<Pizza>();
    	ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("pizza_id");
			String name = rs.getString("name");
			int pepperoni = rs.getInt("pepperoni"); 
			int sausage = rs.getInt("sausage"); 
			int bacon = rs.getInt("bacon"); 
			int quantity = rs.getInt("quantity"); 
			
			pizza.add(new Pizza(id, name, pepperoni, sausage, bacon, quantity));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return pizza;
    	
    }
    
	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}
}