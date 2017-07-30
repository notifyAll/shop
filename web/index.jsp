<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: vivid
  Date: 2017/3/15
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="session"></c:set>

<%--<script type="text/javascript" >--%>
  <%--window.onload=function () {--%>
      <%--window.open("${path}/index.action",null,"");--%>
  <%--}--%>
<%--</script>--%>

<%--//使用这种跳转方式需要在web.xml中配置
 <filter-mapping>
        <filter-name>struts2</filter-name>
        <url-pattern>/*</url-pattern>
        <dispatcher>REQUEST</dispatcher>  在struts模块中加这两句
        <dispatcher>FORWARD</dispatcher>
    </filter-mapping>
--%>
<jsp:forward page="index.action"></jsp:forward>

