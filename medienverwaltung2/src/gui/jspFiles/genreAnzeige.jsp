<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template>
	<div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
            <th></th>
            <th>Bezeichnung</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${context.list}" var="data">
            <tr>
                <td><div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        Action
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        <li><a href="${pageContext.request.contextPath}/genre/editieren.html?id=${data.id}">Bearbeiten</a></li>
                        <li><a href="${pageContext.request.contextPath}/genre/loeschen.html?id=${data.id}">Löschen</a></li>
                    </ul>
                </div></td>
                <td>${data.bezeichnung}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:template>