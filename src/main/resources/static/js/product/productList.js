function productDeleteRequestWithToken(url) {
  const requestJson = { };

  console.log("url", url);

  $.ajax({
    type: 'DELETE',
    url: url,
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(requestJson),
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', "Bearer " + localStorage.getItem('access_token'));
    },
    success: function() {
      alert('상품이 삭제되었습니다.');
      getRequestWithToken('/admin/page/product')
    },
    error: function(xhr) {
      const errorResponse = xhr.responseJSON;
      alert(errorResponse.message);  // 에러 메시지 alert
    }
  });
}