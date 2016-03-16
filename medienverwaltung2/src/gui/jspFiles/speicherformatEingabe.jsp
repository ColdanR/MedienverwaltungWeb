<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<form class="form-horizontal" role="form" action="" method="post">
    <div class="form-section">
        <h3>Alle Felder müssen ausgefüllt werden</h3>
        <div id="abschicken">
	        <div class="form-group">
	            <div class="col-lg-3 col-md-3 col-sm-3 control-label">
	                <label for="genre">Format</label>
	            </div>
	            <div class="col-lg-6 col-md-6 col-sm-3">
	                <select class="form-control" name="genre" id="genre">
	                    <option value="1">Analog</option>
	                    <option value="2">Digital</option>
	                </select>
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