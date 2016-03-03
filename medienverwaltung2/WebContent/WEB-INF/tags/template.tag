<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="false" body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Multimediaverwaltung - ${context.titel}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
        <c:forEach items="${context.cssFiles}" var="cssFile">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${cssFile}" />
        </c:forEach>
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" defer></script>
        <c:forEach items="${context.jsFiles}" var="jsFile">
        <script src="${pageContext.request.contextPath}/js/${jsFile}" defer></script>
        </c:forEach>
    </head>
    <body>
        <nav class="navbar navbar-default">
        <div class="collapse navbar-collapse" id="navbar-collapse-2">
          <ul class="nav navbar-nav navbar-left">
              <li><a href="index.html">Multimediaverwaltung</a></li>
              <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Musik
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="musik-anlegen.html">Anlegen</a></li>
                    <li><a href="musik-anzeigen.html">Anzeigen</a></li>
                </ul>   
            </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
              <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Person
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="person-anlegen.html">Anlegen</a></li>
                    <li><a href="person-anzeigen.html">Anzeigen</a></li>
                </ul>   
            </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Buch
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="buch-anlegen.html">Anlegen</a></li>
                        <li><a href="buch-anzeigen.html">Anzeigen</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Film
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="film-anlegen.html">Anlegen</a></li>
                        <li><a href="film-anzeigen.html">Anzeigen</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Hörbuch
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="hobuch-anlegen.html">Anlegen</a></li>
                        <li><a href="hobuch-anzeigen.html">Anzeigen</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Spiele
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="spiel-anlegen.html">Anlegen</a></li>
                        <li><a href="spiel-anzeigen.html">Anzeigen</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Bilder
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="bild-anlegen.html">Anlegen</a></li>
                        <li><a href="bild-anzeigen.html">Anzeigen</a></li>
                    </ul>
                </li>
            </ul>
        </div> 
        </nav>
        <div class="container">
        	<c:if test="${fn:length(context.errors) > 0}">
        	<div class="form-horizontal">
            	<div class="form-section">
            		<h3>Fehlermeldungen</h3>
	        		<ul class="form-group errorList">
	        			<c:forEach items="${context.errors}" var="error">
	        			<li>${error}</li>
	        			</c:forEach>
	        		</ul>
        		</div>
        	</div>
        	</c:if>
        	<jsp:doBody/>
         </div>
    </body>
</html>