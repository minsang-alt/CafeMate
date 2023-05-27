//(1)주문리스트 로드하기
function orderLoad(){
    $.ajax({
        url:`/api/orders`,
        dataType:"json"
    }).done(res=>{
        console.log("성공",res);
    }).fail(error=>{
        console.log("오류",error);
    });
}

orderLoad();