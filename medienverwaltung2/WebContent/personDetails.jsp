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
                    <label> Vorname</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4 control-label">
                    <p>${context.vname}</p>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-3 clo-lg-3 control-label">
                    <label> Nachname</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4">
                    <p> ${context.nname}</p>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-3 clo-lg-3 control-label">
                    <label> Künstlername</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-4 control-label">
                    <p>${context.kname}</p>
                </div>
            </div>
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