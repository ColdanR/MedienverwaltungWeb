<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<form class="form-horizontal" role="form" action="" method="post">
                <div class="form-section">
                <input type="hidden" name="id" value="${context.dbId}">
                    <h3>Alle Felder müssen ausgefüllt werden</h3>
                    <div class="form-group">
                        <div class="col-sm-3 col-lg-3 control-label">
                            <label for="vname"> Vorname</label>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-4">
                            <input  type="text" class="form-control" name="vname" id="vname" required="required" value="${context.vname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-3 clo-lg-3 control-label">
                            <label for="nname"> Nachname</label>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-4">
                            <input  type="text" class="form-control" name="nname" id="nname" required="required" value="${context.vname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-3 clo-lg-3 control-label">
                            <label for="kname"> Künstlername</label>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-4">
                            <input  type="text" class="form-control" name="kname" id="kname" required="required" value="${context.vname}">
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