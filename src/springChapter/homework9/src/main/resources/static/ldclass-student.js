
var classId=getQueryString(curPageUrl, 'classId');
var dataUrl="students/list?classId="+classId;
$('#student-table').bootstrapTable({
 url: dataUrl,
    columns: [{
        field: 'id',
        title: 'ID'
        
    }, {
        field: 'qq',
        title: 'QQ'
    }, {
        field: 'name',
        title: '姓名'
         },
         {
        field: 'namespell',
        title: '拼音'
         },
         {
        field: 'phone',
        title: '电话',
        
         },
           {
        field: 'email',
        title: '邮箱'
         },
         { field: 'sex',
        title: '性别'
         },
               
         { field: 'workYear',
          title: '经验'
         },
         { field: 'clstate',
        title: '状态'
         },
         { field: 'clstateReason',
        title: '标识'
         },
         { field: 'clseason',
          title: '插班时间'
         },
         {  
        title: '转学',
        formatter:"linkFormatter"
         }
         
    ]
})

function linkFormatter(value, row) {
     var href="assine-ldclass.html?studentId="+row.id+"&name="+encodeURIComponent(row.name)+"&qq="+row.qq;
     return "<a href='#' onclick=navi2page('"+href+"')>转学</a>";
 }
