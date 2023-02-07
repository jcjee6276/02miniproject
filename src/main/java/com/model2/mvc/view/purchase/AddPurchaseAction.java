package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.service.purchase.*;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.framework.Action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddPurchaseAction extends Action{
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		User userVO = new User();
		Purchase pchaseVO = new Purchase();
		Product product = new Product();
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		request.getAttribute("pvo");
		ProductService proservice = new ProductServiceImpl();
		proservice.getProduct(prodNo);
		System.out.println("+++++"+product);
		pchaseVO.setPurchaseProd(product);
		
		HttpSession session = request.getSession();
		userVO = (User)session.getAttribute("user");
		
		
		System.out.println("======ttt"+pchaseVO.getPurchaseProd().getProdNo());
		pchaseVO.setBuyer(userVO);
		pchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		pchaseVO.setReceiverName(userVO.getUserName());
		pchaseVO.setReceiverPhone(userVO.getPhone());
		pchaseVO.setDivyAddr(userVO.getAddr());
		pchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		pchaseVO.setDivyDate(request.getParameter("receiverDate"));
		
		

		
		
		PurchaseService pservice = new PurchaseServiceImpl();
		pservice.addPurchase(pchaseVO);
		
		
		
		
		return "forward:/purchase/addPurchase.jsp";
	}
}
