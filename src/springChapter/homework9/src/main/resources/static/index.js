$(document).ready(function() {
	$("ul.navbar-nav li").each(function() {
		$(this).on("click", function(e) {
			var newPage = $(this).attr("data-page");
			navi2page(newPage);
		})
	})
})