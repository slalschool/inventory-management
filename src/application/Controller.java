package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pizzeria.Pizza;
import pizzeria.PizzaDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	
	private PizzaDAO dao;
	
	public void init( ) {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new PizzaDAO(url, username, password);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/add": //intentionally fall through
			case "/edit": showEditForm(request, response); break;
			case "/insert": insertPizza(request, response); break;
			case "/update": updatePizza(request, response); break;
			case "/search": searchPizza(request, response); break; 
			default: viewPizza(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void viewPizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Pizza> pizza = dao.getPizzas();
		request.setAttribute("pizza", pizza);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertPizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String name = request.getParameter("name");
		int pepperoni = Integer.parseInt(request.getParameter("pepperoni"));
		int sausage = Integer.parseInt(request.getParameter("sausage"));
		int bacon = Integer.parseInt(request.getParameter("bacon"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		dao.insertPizza(name,  pepperoni,  sausage,  bacon, quantity);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updatePizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
			? request.getParameter("action")
			: request.getParameter("submit").toLowerCase();
		final int id = Integer.parseInt(request.getParameter("id"));
		
		Pizza pizza = dao.getPizza(id);
		switch (action) {
			case "purchase": pizza.purchaseMe(); break;
			case "save":
				String name =request.getParameter("name");
				int pepperoni = Integer.parseInt(request.getParameter("pepperoni"));
				int sausage = Integer.parseInt(request.getParameter("sausage"));
				int bacon = Integer.parseInt(request.getParameter("bacon"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				pizza.setName(name);
				pizza.setPepperoni(pepperoni);
				pizza.setSausage(sausage);
				pizza.setBacon(bacon);
				pizza.setQuantity(quantity);
				break;
			case "delete": deletePizza(id, request, response); return;
		}
		dao.updatePizza(pizza);
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final int id = Integer.parseInt(request.getParameter("id"));
			
			Pizza pizza = dao.getPizza(id);
			request.setAttribute("pizza", pizza);
		} catch (NumberFormatException e) {
			
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("PizzaForm.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void deletePizza(final int id, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		dao.deletePizza(dao.getPizza(id));
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void searchPizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String category = request.getParameter("search_attribute") != null
				? request.getParameter("search_attribute")
				: null;
				
		final String query = request.getParameter("search_bar") != null
				? request.getParameter("search_bar").toLowerCase()
				: "";
		
		List<Pizza> results = dao.searchPizza(category, query);
		
		request.setAttribute("pizza", results);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request,  response);
	}
}