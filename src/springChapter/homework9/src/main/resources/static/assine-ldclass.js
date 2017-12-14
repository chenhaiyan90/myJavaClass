/**
 * 
 */
$(document).ready(
		function() {
			$('#assinInf').text(
					"当前学员:" + getQueryString(curPageUrl, 'name') + " QQ:"
							+ getQueryString(curPageUrl, 'qq') + " Id:"
							+ getQueryString(curPageUrl, 'studentId'));
			$.getJSON("ldclassmap", function(data) {
				var selectObj = $("#classIdSelect");
				selectObj.find('option').remove();
				selectObj.append('<option value="">请选择</option>')
				$.each(data, function() {
					selectObj.append('<option value="' + this.key + '">'
							+ this.value + '</option>')

				})
			})
			$('#assginform').submit(function() {

				var data = $(this).serializeArray();
				data.push({
					name : "studentId",
					value : getQueryString(curPageUrl, 'studentId')
				});

				$('#response').html("<b>提交请求...</b>");
				$.ajax({
					type : 'POST',
					url : 'assignclass',
					data : data
				}).done(function(data) {
					if (data == 'Success') {
						// var url = "student-list.html";
						bootbox.alert(data, function() {
							back2page();
						});
					} else {
						bootbox.alert(data);
					}

				}).fail(function(e) {
					alert("Posting failed." + e);

				});
				return false;

			});

			$("#cancelbt").click(function() {
				//var url = "student-list.html";
				back2page();

			})
		});
