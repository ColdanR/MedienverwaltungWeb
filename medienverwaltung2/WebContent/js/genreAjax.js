$(document).ready(function(){
	document.getElementById("sendGenre").addEventListener("click", function(event){
	    event.preventDefault();
	    var data = {};
	    var bez = document.getElementById("formBezGenre").value;
	    data.data = {
	    		"bez": bez
	    };
	    data.url = "/medienverwaltung2/ajax/addGenre";
	    $.ajax(data).done(function(data) {
	    	var ret = data;
	    	var status = ret.status;
	    	if (status == 0) {
	    		var id = ret.id;
	    		// Option anlegen und selektieren
	    		var option = document.createElement("option");
	    		option.value = id;
	    		option.selected = true;
	    		$(option).text(bez);
	    		$("#genre").append(option);
	    		// ModalDialog schließen
	    		document.getElementById("formBezGenre").value = "";
	    		$("#genreDialog").modal("hide");
	    	} else if (status == 1) {
	    		var errors = ret.errors;
	    		// Fehler Alert
	    		var messages = "";
	    		for (var i = 0; i < errors.length; i++) {
	    			messages = message + errors[i] + "\n";
	    		}
	    		alert(messages);
	    	}
	    });
	});
});

function openDialog() {
	
}