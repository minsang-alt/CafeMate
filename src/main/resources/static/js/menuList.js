/*<!CDATA[*/


let principal = $(".principal").val();


//(1) 상품리스트 로드하기
function menuLoad(){
    //get은 디폴트
    $.ajax({
        url:`/api/menu`,
        dataType:"json"
    }).done(res=>{
        //console.log("성공" , res);

        res.data.forEach((menu)=>{
            console.log(menu);
            let menuItem = getMenuItem(menu);
            $("#menu-list").append(menuItem);
        });

    }).fail(error=>{
        console.log("오류",error);
    })
}

menuLoad();

function getMenuItem(menu){
    let onSaleText = menu.on_sale ? "판매중" : "판매중단";
    let auth = `<td>
                <button onclick="updateMenu(${menu.id})" class="btn btn-primary">Edit</button>
                <button onclick="deleteMenu(${menu.id})" class="btn btn-danger">Delete</button>
        </td>`;
    let item=`<tr id="menuList-${menu.id}">
            <td>${menu.product_name}</td>
            <td>${menu.category}</td>
            <td>${menu.price}</td>
            <td>${onSaleText}</td>
           `

        if(principal=='admin') item+= `${auth}`;

        item+= `</tr>`;
    return item;

}

//메뉴 삭제
function deleteMenu(menuId){
    let result = confirm('정말로 삭제하시겠습니까?');
    if(result) {
        $.ajax({
            type: "delete",
            url: `/api/menu/${menuId}`,
            dataType:"json"
        }).done(res=>{
            $(`#menuList-${menuId}`).remove();
        }).fail(error=>{
            console.log("오류",error);
        })
    }
}

//메뉴수정
function updateMenu(menuId){
   //관리자가 여러 메뉴중 하나를 택하여

}

/*]]>*/