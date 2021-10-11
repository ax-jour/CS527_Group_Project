

function updateTable(){
    var btn_mysql = document.getElementById('mysql');
    // var instacart = document.getElementById('instacart');
    var selectList=document.getElementById("selectList");
    var selectValue=selectList.options[selectList.selectedIndex].value;
    var query=document.getElementById("inputContent").value;
    var url="http://127.0.0.1:5000";
    if (btn_mysql.checked){
        url+='/mysql/'+selectValue.toString();
    }
    else{
        url+='/redshift/'+selectValue.toString();
    }
    if (query!==""){
        $.ajax({
            type:'POST',
            url:url,
            data:{
                "query":query
            },
            dataType:"json",
            success:function(data){
                if (data!=null && data!==""){
                    var str="";
                    str+="<tr>";
                    for(var i =0; i<data.colName.length;i++){
                        str+="<td><b>"+data.colName[i]+"</b></td>";
                    }
                    str+="</tr>";
                    for(var i=0; i<data.rawData.length; i++){
                        str+="<tr>";
                        for(var j=0; j<data.colName.length; j++){
                            str+="<td>"+data.rawData[i][j]+"</td>";
                        }
                        str+="</tr>";
                    }
                    $("#databaseTable").html(str);
                    $("#runningTime").html(data.time);
                }
            }});
    }
}

