<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: vivid
  Date: 2017/3/18
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajax测试</title>
    <script type="text/javascript">
        function isExist() {
// 获取输入框值
            var name=document.getElementById("username").value;
//            alert(name+"iiiiiiiiii");
            //1创建异步交互对象
            var request =createXmlHttp();
            //2设置监听
            request.onreadystatechange=function () {
                if (request.readyState==4 && request.status==200){
                    //当请求被正确响应时做一些事情
                    var yz=document.getElementById("yz");
//                  用el不行方法直接就挂了 目测js中用不了el的表达式
                    <%--yz.innerHTML=${msg};--%>
                    yz.innerHTML=request.responseText;
                }
            };
             //3打开连接
            request.open("get","${pageContext.request.contextPath}/shop/user_isExist.action?time="+new Date().getTime()+"&username="+name,true);
             //4发送
            request.send();
        }
        //获得XMLHttpRequest 标准写法为了适应不同的浏览器 不同的浏览器获取XMLHttpRequest是不同的
        function createXmlHttp() {
            var xmlHttp;
            try { // Firefox, Opera 8.0+, Safari
                xmlHttp = new XMLHttpRequest();
            }
            catch (e) {
                try {// Internet Explorer
                    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                }
                catch (e) {
                    try {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    catch (e) {
                    }
                }
            }
            return xmlHttp;
        }
    </script>
</head>
<body>
<h1>ajax 用户名验证的测试</h1>
<form action="" method="post" >

    <input type="text"  id="username" name="username" onblur="isExist()"><span id="yz"></span><span id="yz1"> <s:actionmessage ></s:actionmessage></span>
    <input type="text">
    <input type="submit" value="提交">
</form>
</body>
</html>
