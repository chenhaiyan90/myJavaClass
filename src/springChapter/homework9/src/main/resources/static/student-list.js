/**
 * 
 */
   
 $(document).ready(function () {
    $('#allstudentbtn').click(function () {
    	navi2page("student-list-all.html");
    	return false;
    });
    $('#noldcassbtn').click(function () {
    	navi2page("student-list.html");
    	return false;
    });
 });
 

 function nameFormatter(value, row) {
     var icon = 'glyphicon-star' ;
     return '<i class="glyphicon ' + icon + '"></i> ' + value;
 }
 
 function linkFormatter(value, row) {
     var href="assine-ldclass.html?studentId="+row.id+"&name="+encodeURIComponent(row.name)+"&qq="+row.qq;
     return "<a href='#' onclick=navi2page('"+href+"')>分班</a>";
 }
