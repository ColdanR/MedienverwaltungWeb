<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tags/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:template>
<!--Wenn nicht Festplatte zusätzlich!!! -->
   <div class="form-group">
       <div class="col-sm-3 col-lg-3 control-label">
           <label for="zustand">Zustand</label>
       </div>
       <div class="col-lg-6 col-md-6 col-sm-4">
           <input class="form-control" name="zustand" id="zustand" required="required">
       </div>
   </div>
</t:template>