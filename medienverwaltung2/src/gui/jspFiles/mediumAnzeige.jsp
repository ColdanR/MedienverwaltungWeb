<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template>
<h1>
<p>${context.titel}</p>
</h1>
	<div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
            <th></th>
            <th>Titel</th>
            <th>Genre</th>
            <th>Erscheinungsdatum</th>
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
                        <li><a href="${context.baseURI}details.html?id=${data.dbId}">Details</a></li>
                        <li><a href="${context.baseURI}editieren.html?id=${data.dbId}">Bearbeiten</a></li>
                        <li><a href="${context.baseURI}anzeigen.html?act=delete&id=${data.dbId}">L�schen</a></li>
                    </ul>
                </div></td>
                <td>${data.bezeichnung}</td>
                <td>${data.genre}</td>
                <td>${data.datum}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:template>