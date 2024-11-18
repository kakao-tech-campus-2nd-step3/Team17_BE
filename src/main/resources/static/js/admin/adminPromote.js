function putAdminCode() {
  let requestJson = {
    "adminCode": document.getElementById('adminCode').value
  };

  $.ajax({
    type: 'PUT',
    url: '/admin/promote',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(requestJson),
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', "Bearer " + localStorage.getItem('access_token'));
    },
    success: function() {
      alert('관리자 등록이 완료되었습니다.');
      getRequestWithToken('/admin/page');
    },
    error: function(xhr) {
      const errorResponse = xhr.responseJSON;
      alert(errorResponse.message);  // 에러 메시지 alert
    }
  });
}