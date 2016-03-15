<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<div class="form-horizontal">
    <div class="form-section" id="detail">
		<h3 class="panel-title">Detailsseite</h3>
            <div class="form-group">
                <div class="col-sm-3 col-lg-3 control-label">
                    <label> Titel</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4 control-label">
                    <p>${context.bezeichnung}</p>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-3 clo-lg-3 control-label">
                    <label> Erscheinungsjahr</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <p> ${context.erscheinungsjahr}</p>
                </div>
            </div>
            <c:if test="${context.medium.id == 0}">
            <div class="form-group">
                        <div class="col-sm-3 clo-lg-3 control-label">
                            <label for="live"> Live</label>
                        </div>    
                        <div class="col-lg-6 col-md-6 col-sm-4">
                            <input  type="checkbox" class="form-control" name="live" disabled="disabled" id="live"<c:if test="${context.live}"> checked</c:if>>
                        </div>
                    </div>
            </c:if>
            <div class="form-group">
                <div class="col-sm-3 clo-lg-3 control-label">
                    <label> Bemerkung</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4 control-label">
                    <p>${context.bemerkung}</p>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="genre">Genre</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-3 control-label">
                	<p>${context.genre}</p>
                </div>
            </div>
            <c:if test="${context.medium.id != 0 || context.medium.id != 5}">
            <div class="form-group">
                <div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="art">Art</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-3 control-label">
                	<p>${context.art}</p>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-3">
                    <button class="btn btn-default">Hinzufügen</button>
                </div>
            </div>
            </c:if>
            <c:if test="${context.medium.id != 0 || context.medium.id != 5}">
            <div>
            	<div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label>Sprache</label>
                </div>
            	<div class="col-lg-6 col-md-6 col-sm-4 control-label">
                    <p>${context.sprache}</p>
                </div>
            </div>    
            </c:if>
            <c:if test="${context.medium.id == 2}">
            <div>
            	<div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="auflage">Auflage</label>
                </div>
            	<div class="col-lg-6 col-md-6 col-sm-4">
                    <p>${context.auflage}</p>
                </div>
            </div>    
            </c:if>
            <c:if test="${context.medium.id == 5}">
            <div class="form-group">
                <div class="col-sm-3 col-lg-3 control-label">
                    <label>Betriebssystem</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4 control-label">
                    <p>${context.betriebssystem}</p>
                </div>
            </div>
         	</c:if>
         	<!--Akkordion Anfang  -->
         	 <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a id="test" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                <div class="col-lg-8">
                                    <p> Format</p>
                                </div>
                                <div class="col-lg-4">
                                    <button id="collapse" class="btn btn-default pull-right">Bearbeiten</button>
                                    <button id="collapse" class="btn btn-default pull-right">Löschen</button>
                                </div>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                        <c:forEach items="${context}" var="data">
                        ${data.format}<br>
                        </c:forEach>
                        </div>
                        <div class="panel-footer">
                            <button class="btn btn-default center-block">Neuanlage</button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                                <h4 class="panel-title">
                                    <a id="test" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        <div class="col-lg-8">
                                            <p>Speicherort</p>
                                        </div>
                                        <div class="col-lg-4">
                                            <button id="collapse" class="btn btn-default pull-right">Bearbeiten</button>
                                            <button id="collapse" class="btn btn-default pull-right">Löschen</button>
                                        </div>
                                    </a>
                                </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <c:forEach items="${context}" var="data">
                        		${data.speicherort}<br>
                        	</c:forEach>
                        </div>
                    </div>
                </div>
            </div><!--Akkordion Ende  -->
	 </div>
   	 <div class="form-section">
        <div class="form-group">
            <div class="col-lg-12 col-md-6 col-sm-3" id="button">
                <button class="btn btn-default center-block" name="edit" id="edit">Bearbeiten</button>
            </div>
        </div>
     </div>
</div>
</t:template>