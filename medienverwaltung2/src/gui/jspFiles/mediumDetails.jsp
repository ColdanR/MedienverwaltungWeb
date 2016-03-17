<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<h1>
<p>${context.titel}</p>
</h1>
<div class="form-horizontal">
    <div class="form-section" id="detail">
		<h3 class="panel-title">Detailsseite ${context.medium.bezeichnung}</h3>
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
                    <label> Datum</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <p> ${context.datum}</p>
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
            <c:if test="${context.medium.id != 0 && context.medium.id != 4 && context.medium.id != 5}">
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
            <c:if test="${context.medium.id != 0 && context.medium.id != 4}">
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
            <c:if test="${context.medium.id == 4}">
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
         	 	<c:forEach items="${context.formate}" var="data">
         	 		<div class="panel panel-default">
	                    <div class="panel-heading" role="tab" id="headingOne">
	                        <h4 class="panel-title">
	                            <a id="test" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	                                <div class="col-lg-8">
	                                    <p> ${data.bezeichnung }</p>
	                                </div>
	                                <div class="col-lg-4">
	                                    <a id="collapse" class="btn btn-default pull-right" href="${pageContext.request.contextPath}/formate/editieren.html?id=${data.id}&idMedium=${context.dbId}&idMediumType=${context.medium.id}&idFormatType=${data.idFormatType}">Bearbeiten</a>
	                                    <a id="collapse" class="btn btn-default pull-right" href="${pageContext.request.contextPath}/formate/loeschen.html?id=${data.id}&idMedium=${context.dbId}&idMediumType=${context.medium.id}">Löschen</a>
	                                </div>
	                            </a>
	                        </h4>
	                     </div>
	                     <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
	                        <div class="panel-body">
		                        <div class="table-responsive">
		        					<table class="table table-striped">
		                        			<thead>
				                       			<tr>
				                       				<th>Type</th>	
				                       				<th>Art</th>	
				                       				<th>Bezeichnung</th>	
				                       				<th>Bemerkung</th>	
				                       				<th>Zustand}</th>	
				                       				<th></th>
				                       			</tr>
		                        			</thead>
		                        		<c:forEach items="${data.speicherorte}" var="dat">
		                        			<tr>
		                        				<td>${dat.type}</td>	
		                        				<td>${dat.art}</td>	
		                        				<td>${dat.bez}</td>	
		                        				<td>${dat.bemerkung}</td>	
		                        				<td>${dat.zustand}</td>	
		                        				<td>
			                        				<div class="dropdown">
	                    								<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
	                        								Action
	                       									<span class="caret"></span>
	                    								</button>
	                    								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									                        <li><a href="${pageContext.request.contextPath}/speicherorte/editieren.html?id=${dat.id}&idSpeicherortArt=${dat.type.id}&idFormat=${data.id}&idMedium=${context.dbId}&idMediumType=${context.medium.id}&idFormatType=${data.idFormatType}">Bearbeiten</a></li>
									                        <li><a href="${pageContext.request.contextPath}/speicherorte/loeschen.html?id=${dat.id}&idSpeicherortArt=${dat.type.id}&idFormat=${data.id}&idMedium=${context.dbId}&idMediumType=${context.medium.id}&idFormatType=${data.idFormatType}">Löschen</a></li>
	                    								</ul>idFormatType
	                								</div>
                								</td>
		                        			</tr>
		                        			</c:forEach>			
		                        	</table>
		                        	<div class="col-lg-12 col-md-6 col-sm-3" id="button">
                                    	<div class="col-lg-4 col-md-2 col-sm-1">
                                    	</div>
                                    	<div class="col-lg-4 col-md-2 col-sm-1">
		                        			<a class="btn btn-default center-block" href="${pageContext.request.contextPath}/speicherorte/anlage.html?idFormat=${data.id}&idMedium=${context.dbId}&idMediumType=${context.medium.id}&idFormatType=${data.idFormatType}">Neuanlage Speicherort</a>
		                        	 	</div>
                                    	<div class="col-lg-4 col-md-2 col-sm-1">
                                    	</div>
                                	</div>
		                        </div>
	                        </div>
						  </div>
               		</div>
         	 	</c:forEach>
            </div><!--Akkordion Ende  -->
   			<div class="col-lg-12 col-md-6 col-sm-3" id="button">
         	 	<div class="col-lg-4 col-md-2 col-sm-1">
             	</div>
             	<div class="col-lg-4 col-md-2 col-sm-1">
             		<a class="btn btn-default center-block" href="${pageContext.request.contextPath}/formate/anlage.html?idMedium=${context.dbId}&idMediumType=${context.medium.id}">Neuanlage</a>
     			</div>
              <div class="col-lg-4 col-md-2 col-sm-1">
              </div>
          </div>
	 </div>
</div>
</t:template>