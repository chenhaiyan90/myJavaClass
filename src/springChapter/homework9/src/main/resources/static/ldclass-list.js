/**
 * 
 */
 function linkFormatter(value, row) {
     var href="ldclass-student.html?classId="+row.classId+"&className="+encodeURIComponent(row.className);
     return "<a href='#' onclick=navi2page('"+href+"')>花名册</a>";
 }