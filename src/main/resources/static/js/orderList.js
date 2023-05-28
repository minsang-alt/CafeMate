//(1)주문리스트 로드하기
function orderLoad(){
    $.ajax({
        url:`/api/orders`,
        dataType:"json"
    }).done(res=>{
        console.log("성공",res);
        res.data.forEach((order)=>{
           let orderItem = getOrderItem(order);
            $("#order-list").append(orderItem);
        });
    }).fail(error=>{
        console.log("오류",error);
    });
}

orderLoad();

function getOrderItem(order){
    let item = ` <tr id="orderList-${order.orderId}">
          <td>${order.alias}</td>
          <td>${order.customerId}</td>
          <td>${order.menuName.join(', ')}</td>
          <td>${order.orderId}</td>
          <td>${new Date(order.ordersDate).toLocaleString()}</td>
          <td>${order.quantity}</td>
          <td><button onclick="deleteRow(${order.orderId})">Delete</button></td>
        </tr>
`;
    return item;
}

//메뉴 삭제 , 실제로는 db에서 삭제가 아니라 판매완료로 바뀜
function deleteRow(orderId) {
    //메뉴 삭제버튼을 누르면 판매 완료가 되도록 요청
    $.ajax({
        type:"delete",
        url:`/api/order/${orderId}`,
        dataType:"json"
    }).done(res=>{
        $(`#orderList-${orderId}`).remove();
    }).fail(error=>{
        console.log("오류",error);
    })
}