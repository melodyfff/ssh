<%--
  Created by IntelliJ IDEA.
  User: xinchen
  Date: 2017/10/3
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false"%>
<html>
<body>
<h1>标题: ${title}</h1>
<h1>消息 : ${message}</h1>
<h2>国际化：<fmt:message key="hello.test"/> </h2>
<h2>国际化数据库：<fmt:message key="my.test"/> </h2>
<a href="<c:url value="/logout" />">Logout</a>

</body>
</html>
