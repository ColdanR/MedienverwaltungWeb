<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
	<div id="genreDialog" class="modal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Genre anlegen</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="col-sm-3 col-lg-3 control-label">
						<label for"formBezGenre">Bezeichnung</label>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-4">
						<input type="text" class="form-control" name="formBezGenre" id="formBezGenre">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="sendGenre">Abschicken</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Abbrechen</button>
			</div>
    </div>

  </div>
	</div>
	<form class="form-horizontal" role="form" action="" method="post">
        <div class="form-section">
            <h3>Alle Felder müssen ausgefüllt werden</h3>
            <input type="hidden" value="${context.dbId}">
            <div class="form-group">
                <div class="col-sm-3 col-lg-3 control-label">
                    <label for="bezeichnung"> Titel</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <input  type="text" class="form-control" name="bezeichnung" id="bezeichnung" required="required" value="${context.bezeichnung}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-3 clo-lg-3 control-label">
                    <label for="erscheinungsjahr"> Erscheinungsjahr</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <input  type="text" class="form-control" name="erscheinungsjahr" id="erscheinungsjahr" required="required" value="${context.erscheinungsjahr}">
                </div>
            </div>
            <c:if test="${context.medium.id == 0}">
            <div class="form-group">
                        <div class="col-sm-3 clo-lg-3 control-label">
                            <label for="live"> Live</label>
                        </div>    
                        <div class="col-lg-6 col-md-6 col-sm-4">
                            <input  type="checkbox" class="form-control" name="live" id="live"<c:if test="${context.live}"> checked</c:if>>
                        </div>
                    </div>
            </c:if>
            <div class="form-group">
                <div class="col-sm-3 clo-lg-3 control-label">
                    <label for="bemerkung"> Bemerkung</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <input  type="text" class="form-control" name="bemerkung" id="bemerkung" value="${context.bemerkung}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="genre">Genre</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-3">
                <cu:comboBox multiple="true" parameterName="genre" selectOptions="${context.genreOptions}" className="form-control" title="genre" selectedList="${context.genreSelected}"/>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-3">
                    <button data-toggle="modal" data-target="#genreDialog" class="btn btn-default" type="button">Hinzufügen</button>
                </div>
            </div>
            <c:if test="${context.medium.id != 0 && context.medium.id != 4 && context.medium.id != 5}">
            <div class="form-group">
                <div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="art">Art</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-3">
                <cu:comboBox multiple="false" parameterName="art" selectOptions="${context.artOptions}" className="form-control" title="art" selected="${context.artSelected}"/>
                </div>
            </div>
            </c:if>
            <c:if test="${context.medium.id != 0 && context.medium.id != 5}">
            <div>
            	<div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="sprache">Sprache</label>
                </div>
            	<div class="col-lg-6 col-md-6 col-sm-4">
                    <input  type="text" class="form-control" name="sprache" id="sprache" value="${context.sprache}">
                </div>
            </div>    
            </c:if>
            <c:if test="${context.medium.id == 2}">
            <div>
            	<div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="auflage">Auflage</label>
                </div>
            	<div class="col-lg-6 col-md-6 col-sm-4">
                    <input  type="text" class="form-control" name="auflage" id="auflage" value="${context.auflage}">
                </div>
            </div>    
            </c:if>
            <c:if test="${context.medium.id == 4}">
            <div class="form-group">
                <div class="col-sm-3 col-lg-3 control-label">
                    <label for="betrieb">Betriebssystem</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <input class="form-control" name="betrieb" id="betrieb" required="required" value="${context.betriebssystem}">
                </div>
            </div>
         	</c:if>
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