<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actKintai" value="${ForwardConst.ACT_KINTAI.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>勤怠管理</h2>
        <h3>【自分の勤怠　一覧】</h3>
        <table id="kintai_list">
            <tbody>
                <tr>
                    <th class="kintai_name">氏名</th>
                    <th class="kintai_date">日付</th>
                    <th class="kintai_begin">出勤</th>
                    <th class="kintai_finish">退勤</th>
                    <th class="kintai_action">操作</th>
                </tr>
                <c:forEach var="kintai" items="${kintai}" varStatus="status">
                    <fmt:parseDate value="${kintai.kintai_date}" pattern="yyyy-MM-dd" var="kintaiDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="kintai_name"><c:out value="${kintai.employee.name}" /></td>
                        <td class="kintai_date"><fmt:formatDate value='${kintaiDay}' pattern='yyyy-MM-dd' /></td>
                         <td class="kintai_begin">${kintai.begin}</td>
                         <td class="kintai_finish">${kintai.finish}</td>
                        <td class="kintai_action"><a href="<c:url value='?action=${actKintai}&command=${commShow}&id=${kintai.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${kintai_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((kintai_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actTop}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actKintai}&command=${commNew}' />">勤怠の登録</a></p>

    </c:param>
</c:import>
