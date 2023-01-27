<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actKintai" value="${ForwardConst.ACT_KINTAI.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>勤怠 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${kintai.employee.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${kintai.kintai_date}" pattern="yyyy-MM-dd" var="kintaiDay" type="date" />
                    <td><fmt:formatDate value='${kintaiDay}' pattern='yyyy-MM-dd' /></td>
                </tr>

                <tr>
                    <th>出勤</th>
                    <td><pre><c:out value="${kintai.begin}" /></pre></td>
                </tr>

                <tr>
                    <th>退勤</th>
                    <td><pre><c:out value="${kintai.finish}" /></pre></td>
                </tr>

                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${kintai.created_at}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${kintai.updated_at}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_employee.id == kintai.employee.id}">
            <p>
                <a href="<c:url value='?action=${actKintai}&command=${commEdt}&id=${kintai.id}' />">この勤怠を編集する</a>
            </p>
        </c:if>

        <p>
            <a href="<c:url value='?action=${actKintai}&command=${commIdx}' />">勤怠一覧に戻る</a>
        </p>
    </c:param>
</c:import>