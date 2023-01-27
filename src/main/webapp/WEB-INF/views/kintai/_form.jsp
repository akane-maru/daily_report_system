<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            <c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>


<fmt:parseDate value="${kintai.kintai_date}" pattern="yyyy-MM-dd" var="kintaiDay" type="date" />
<label for="${AttributeConst.KINTAI_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.KINTAI_DATE.getValue()}" id="${AttributeConst.KINTAI_DATE.getValue()}" value="<fmt:formatDate value='${kintaiDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label>氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />


<label for="${AttributeConst.KINTAI_BEGIN.getValue()}">出勤</label><br />
<input type="time" name="${AttributeConst.KINTAI_BEGIN.getValue()}" id="${AttributeConst.KINTAI_BEGIN.getValue()}" value="${kintai.begin}" />
<br /><br />

<label for="${AttributeConst.KINTAI_FINISH.getValue()}">退勤</label><br />
<input type="time"  name="${AttributeConst.KINTAI_FINISH.getValue()}" id="${AttributeConst.KINTAI_FINISH.getValue()}" value="${kintai.finish}" />
<br /><br />


<input type="hidden" name="${AttributeConst.KINTAI_ID.getValue()}" value="${kintai.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">登録</button>
