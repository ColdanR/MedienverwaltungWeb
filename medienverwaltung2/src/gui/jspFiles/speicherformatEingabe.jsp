<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<t:template>
<h1>
<p>${context.titel}</p>
</h1>
<form class="form-horizontal" role="form" action="" method="post">
    <div class="form-section">
        <h3>Alle Felder müssen ausgefüllt werden</h3>
        <input type="hidden" name="id" value="${context.dbId}">
        <input type="hidden" name="idMedium" value="${context.idMedium}">
        <input type="hidden" name="idMediumType" value="${context.idMediumType}">
        <div id="abschicken">
	        <div class="form-group">
	            <div class="col-lg-3 col-md-3 col-sm-3 control-label">
	                <label for="genre">Format</label>
	            </div>
	            <div class="col-lg-6 col-md-6 col-sm-3">
	           		<cu:comboBox parameterName="format" selectOptions="${context.selectFormatOptions}" className="form-control" title="Format" selected="${context.selectedFormat}"/>
	            </div>
	            <!--bei auswahl Digital 2 Input Felder mit dazugehörigen Label einfügen!!! -->
	            <div class="col-lg-3 col-md-3 col-sm-3">
	                <button class="btn btn-default">Hinzufügen</button>
	            </div>
	        </div>
        </div>
    </div>
    <div class="form-section">
        <div class="form-group">
            <div class="col-lg-12 col-md-6 col-sm-3" id="button">
                <button class="btn btn-default center-block" name="send" id="send">Eintragen</button>
            </div>
        </div>
    </div>
</form>
</t:template>