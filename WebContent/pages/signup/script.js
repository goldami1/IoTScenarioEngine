
$('input[type="radio"]').on('change', function() {
	if ($("#"+$(this).val()).hasClass('is-hidden')) {
		$("#vendor").toggleClass('is-hidden');
		$("#enduser").toggleClass('is-hidden');
	}
});


$("input[type=file]").on('change',function(){
	$("#file-label").text(this.files[0].name);
});
