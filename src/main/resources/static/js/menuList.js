
//(1) 상품리스트 로드하기
function menuLoad(){
    //get은 디폴트
    $.ajax({
        url:`/api/menu`,
        dataType:"json"
    }).done(res=>{
        console.log(res);
    }).fail(error=>{
        console.log("오류",error);
    })
}

menuLoad();

function getMenuItem(){
    let item=`<tr>
            <td>아이스아메리카노</td>
            <td>coffee</td>
            <td>3300원</td>
            <td>판매중</td>
            <td>
                <a href="#" class="btn btn-primary">Edit</a>
                <a href="#" class="btn btn-danger">Delete</a>
            </td>
        </tr>`;
    return item;
}