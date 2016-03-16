<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<!--Wenn nicht Festplatte,Kassette,DIa zusätzlich!!! -->
    <div class="form-group">
        <div class="col-sm-3 col-lg-3 control-label">
            <label for="art">Art</label>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-3">
            <cu:comboBox multiple="false" parameterName="art" selectOptions="${context.artOptions}" className="form-control" title="art" selected="${context.artSelected}"/>
        </div>
    </div>
</t:template>