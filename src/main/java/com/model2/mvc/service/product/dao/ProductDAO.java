package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.*;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDAO {
	public ProductDAO() {
	}
	
	public void insertProduct(Product product) throws Exception {
		Connection con = DBUtil.getConnection();
		String sql = "insert into product values (SEQ_PRODUCT_PROD_NO.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setDate(3, product.getRegDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	public Product findProduct(int prodNo) throws Exception {
		Connection con = DBUtil.getConnection();
		String sql = "select * from product where prod_no=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		ResultSet rs = stmt.executeQuery();
		
		Product product = null;
		while(rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			
		}
		con.close();
		
		System.out.println(product);
		return product;
		
	}
	
	public void updateProduct(Product product) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql ="update product set prod_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,price=?,IMAGE_FILE=? where prod_No=?"; //update product set PROD_NAME=?, PROD_DETAIL=?, REG_DATE=?, PRICE=?, IMAGE_FILE=? where PROD_NO=?
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setDate(3, product.getRegDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
	
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from Product ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " where PROD_NO='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME='" + search.getSearchKeyword()
						+ "'";
			}
		}
		sql += " order by PROD_NO";

		System.out.println("UserDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("Price"));
			product.setRegDate(rs.getDate("reg_date"));
			
			list.add(product);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("UserDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
	
}
