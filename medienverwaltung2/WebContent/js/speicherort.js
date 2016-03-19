$(document).ready(function() {
	$("#speicherortArt").on("change", function() {
		var url = "/medienverwaltung2/ajax/speicherort";
		var data = {};
		data.art = document.getElementById("speicherortArt").value;
		$("#addFields").load(url ,data);
	});
});