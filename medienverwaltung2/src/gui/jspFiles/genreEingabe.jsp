<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<form class="form-horizontal" role="form" action="" method="post">
                <div class="form-section">
                    <h3>Alle Felder müssen ausgefüllt werden</h3>
                    <input type="hidden" name="id" value="${context.dbId}">
                    <div class="form-group">
                        <div class="col-sm-3 col-lg-3 control-label">
                            <label for="bez"> Bezeichnung</label>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-4">
                            <input  type="text" class="form-control" name="bez" id="bez" required="required" value="${context.bezeichnung}">
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