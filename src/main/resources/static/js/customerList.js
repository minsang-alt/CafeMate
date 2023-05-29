//(1)회원 리스트 로드하기
function customerLoad(){
    $.ajax({
        url:`/api/customers`,
        dataType:"json"
    }).done(res=>{
       // console.log("성공",res);

        res.data.forEach((customer)=>{
           let customerItem = getCustomerItem(customer);
           $("#customer-list").append(customerItem);
        });
    }).fail(error=>{
        console.log("오류",error);

    })
}
customerLoad();

function getCustomerItem(customer){
    let item=`
       <tr id="customerList-${customer.id}">
      <td>${customer.name}</td>
      <td>${customer.customerId}</td>
      <td>${customer.phoneNumber}</td>
      <td>${customer.email}</td>
      <td>
      <button onclick="location.href='/admin/customers/${customer.id}';" class="btn btn-primary">Edit</button>
      <button onclick="deleteCustomer(${customer.id})" class="btn btn-danger">Delete</button>
      </td>
      </tr>
    `
    return item;
}

//customer 삭제
function deleteCustomer(customerId){
    let result = confirm(`정말로 삭제하시겠습니까?`);
    if(result){
        $.ajax({
            type: "delete",
            url: `/api/customers/${customerId}`,
            dataType:"json"
        }).done(res=>{
            $(`#customerList-${customerId}`).remove();
        }).fail(error=>{
            console.log("오류",error);
        });
    }
}