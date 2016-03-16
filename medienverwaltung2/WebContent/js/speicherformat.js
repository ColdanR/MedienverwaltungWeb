/**
 * Created by Jasmin Burger on 16.03.2016.
 */
$(document).ready(function(){
    var sel = document.getElementById("genre");
    $("#genre").on("change", function() {
        if(sel.value == 2)
        {
            //var divform = new Element('div', {class: 'form-group'});
            var divform = document.createElement("div");
            divform.className = "form-group";
            //var contLabel = new Element('div', {class: 'col-lg-3 col-md-3 col-sm-3 control-label'});
            var contLable = document.createElement("div");
            contLable.className ="col-lg-3 col-md-3 col-sm-3 control-label";
            //var labelDat = new Element('label', {for: 'dateiformat'});
            var lableDat = document.createElement("label");
            lableDat.htmlFor = "dateiformat";
            $(lableDat).append(document.createTextNode("Dateiformat"));
            var contInp = document.createElement("div");
            contInp.className = "col-lg-6 col-md-6 col-sm-4";
            //var divInpDat = new Element('input', {id: 'dateiformat', name: 'dateiformat', class: 'form-control'});
            var divInpDat = document.createElement("input");
            divInpDat.id = "dateiformat";
            divInpDat.name = "dateiformat";
            divInpDat.className = "form-control";
            divInpDat.required = 'required';
            var jqDivForm = $(divform);
            jqDivForm.append(contLable);
            $(contLable).append(lableDat);
            jqDivForm.append(contInp);
            $(contInp).append(divInpDat);

            var divform2 = document.createElement("div");
            divform2.className = "form-group";
            var contLable2 = document.createElement("div");
            contLable2.className ="col-lg-3 col-md-3 col-sm-3 control-label";
            var lableDat2 = document.createElement("label");
            lableDat2.htmlFor = "quali";
            $(lableDat2).append(document.createTextNode("Qualität"));
            var contInp2 = document.createElement("div");
            contInp2.className = "col-lg-6 col-md-6 col-sm-4";
            var divInpDat2 = document.createElement("input");
            divInpDat2.id = "quali";
            divInpDat2.name = "quali";
            divInpDat2.className = "form-control";
            divInpDat2.required = 'required';
            var jqDivForm2 = $(divform2);
            jqDivForm2.append(contLable2);
            $(contLable2).append(lableDat2);
            jqDivForm2.append(contInp2);
            $(contInp2).append(divInpDat2);

            $("#abschicken").append(jqDivForm);
            $("#abschicken").append(jqDivForm2);

        }
    });

});
