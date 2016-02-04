function init() {
	var tags = document.getElementsByTagName("a");
	for (var i = 0; i < tags.length; i++) {
		tags[i].addEventListener("click", function(event) {
			event.preventDefault();
		});
	}
}
document.addEventListener("load", function() {
	init();
});