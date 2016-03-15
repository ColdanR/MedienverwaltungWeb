<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<form class="form-horizontal" role="form" action="" method="post">
        <div class="form-section">
            <h3>Alle Felder müssen ausgefüllt werden</h3>
            <div class="form-group">
                <div class="col-lg-3 col-md-3 col-sm-3 control-label">
                    <label for="speichermedium">Medium</label>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-3">
                     <cu:comboBox multiple="false" parameterName="speichermedium" selectOptions="${context.speichermediumOptions}" className="form-control" title="speichermedium" selected="${context.speichermediumSelected}"/>                    
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
                <!--Wenn nicht Festplatte zusätzlich!!! -->
                <div class="form-group">
                    <div class="col-sm-3 col-lg-3 control-label">
                        <label for="zustand">Zustand</label>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-4">
                        <input class="form-control" name="zustand" id="zustand" required="required">
                    </div>
                </div>
                <!--Wenn nicht Festplatte,Kassette,DIa zusätzlich!!! -->
                <div class="form-group">
                    <div class="col-sm-3 col-lg-3 control-label">
                        <label for="art">art</label>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-3">
                        <cu:comboBox multiple="false" parameterName="art" selectOptions="${context.artOptions}" className="form-control" title="art" selected="${context.artSelected}"/>
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