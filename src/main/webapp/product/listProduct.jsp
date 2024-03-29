<%@ page language="java" contentType="text/html; charset=EUC-KR"%>

<%@ page import="java.util.*" %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.*" %>
<%


List<Product> list= (List<Product>)request.getAttribute("list");
Page resultPage=(Page)request.getAttribute("resultPage");

Search search = (Search)request.getAttribute("search");
//==> null 을 ""(nullString)으로 변경
String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%>






<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetProductList(){
	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=manage" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
							상품 관리
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		
		<td align="right">
		<%
		

				if(search.getSearchCondition() != null) {
		%>
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%
			

							if(search.getSearchCondition().equals("0")){
			%>
				<option value="0">상품번호</option>
				<option value="1">상품명</option>
			<%
			

							}else {
			%>
				<option value="0">상품번호</option>
				<option value="1">상품명</option>
			<%
			

							}
			%>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" />
		</td>
	<%
	

			}else{
	%>
		<td align="right">
		<select name="searchCondition" class="ct_input_g" style="width:80px">
			<option value="0">상품번호</option>
				<option value="1">상품명</option>
				</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" />
				
		</td>
		<%
		

				}
		%>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 10 건수, 현재 1 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%
	

			int no1=list.size();
			for(int i=0; i<list.size(); i++) {
			Product vo1 = (Product)list.get(i);
	%>
		
	<tr class="ct_list_pop">
		<td align="center"><%=no1--%></td>
		<td></td>
				
		<td align="left"><a href="/updateProductView.do?prodNo=<%=vo1.getProdNo()%>"><%=vo1.getProdName() %></a></td>
		
		<td></td>
		<td align="left"><%=vo1.getPrice() %></td>
		<td></td>
		<td align="left"><%=vo1.getRegDate() %></td>
		<td></td>
		<td align="left">
		
			판매중
		<% } %>
		</td>	
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		
			<a href="/listProduct.do?page=1&menu=manage">1</a>
		
			<a href="/listProduct.do?page=2&menu=manage">2</a>
		
			<a href="/listProduct.do?page=3&menu=manage">3</a>
		
			<a href="/listProduct.do?page=4&menu=manage">4</a>
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>

</body>

</html>


