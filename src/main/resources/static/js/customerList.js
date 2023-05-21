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
      </tr>
    `
    return item;
}