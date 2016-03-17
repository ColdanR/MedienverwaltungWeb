<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<h1>
<p>${context.titel}</p>
</h1>
<form class="form-horizontal" role="form" action="" method="post">
        <div class="form-section">
            <h3>Alle Felder müssen ausgefüllt werden</h3>
            <input type="hidden" value="${context.dbId}">
            <div class="form-group">
                <div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="speichermedium">Speicherortart</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-3">
                     <cu:comboBox multiple="false" parameterName="speicherortArt" selectOptions="${context.selectOptions}" className="form-control" title="speichermedium" selected="${context.selected}"/>                    
                </div>
                <div class="form-group">
                    <div class="col-sm-3 col-lg-3 control-label">
                        <label for="lagerort">Lagerort</label>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-4">
                        <input class="form-control" name="lagerort" id="lagerort" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3 col-lg-3 control-label">
                        <label for="bemerkung">Bemerkung</label>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-4">
                        <input class="form-control" name="bemerkung" id="bemerkung" required="required">
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