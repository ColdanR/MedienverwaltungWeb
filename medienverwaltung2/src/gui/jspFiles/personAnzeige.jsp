<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template>
	<div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
            <th></th>
            <th>Vorname</th>
            <th>Nachname</th>
            <th>Künstlername</th>
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
                        <li><a href="${context.baseURI}details.html?id=${data.id}">Details</a></li>
                        <li><a href="${context.baseURI}?action=editMedium&id=${data.id}">Bearbeiten</a></li>
                        <li><a href="${context.baseURI}?action=delete&id=${data.id}">Löschen</a></li>
                    </ul>
                </div></td>
                <td>${data.vname}</td>
                <td>${data.nname}</td>
                <td>${data.kname}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:template>