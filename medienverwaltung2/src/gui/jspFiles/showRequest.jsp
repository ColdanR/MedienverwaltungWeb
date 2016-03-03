<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<div class="panel panel-default">
        <div class="panel-body">
            <p>Header</p>
        </div>
    </div>
<div class="table-responsive">
        <table class="table table-stripped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Werte</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${context.header}" var="value">
            <tr>
                <td>${value[0]}</td>
                <td>${value[1]}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <p>Parameter</p>
        </div>
    </div>
    <div class="table-responsive">
        <table class="table table-stripped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Werte</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${context.parameter}" var="value">
            <tr>
                <td>${value[0]}</td>
                <td>${value[1]}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
           <p>Attribute</p>
        </div>
    </div>
    <div class="table-responsive">
        <table class="table table-stripped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Wert</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${context.attributes}" var="value">
            <tr>
                <td>${value[0]}</td>
                <td>${value[1]}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:template>