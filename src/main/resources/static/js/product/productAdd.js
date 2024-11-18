function productAddRequestWithToken() {
  const requestJson = {
    "imageUrl": document.getElementById("imageUrl").value,
    "productUrl": document.getElementById("productUrl").value,
    "name": document.getElementById("name").value,
    "price": parseInt(document.getElementById("price").value, 10),
    "storeName": document.getElementById("storeName").value,
    "tagId": parseInt(document.getElementById("tagId").value, 10)
  };

  $.ajax({
    type: 'POST',
    url: '/admin/page/product',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(requestJson),
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization',
          "Bearer " + localStorage.getItem('access_token'));
    },
    success: function () {
      alert('상품이 추가되었습니다.');
      getRequestWithToken('/admin/page/product');
    },
    error: function (xhr) {
      const errorResponse = JSON.stringify(xhr.responseJSON.message);
      alert(errorResponse);  // 에러 메시지 alert
    }
  });
}