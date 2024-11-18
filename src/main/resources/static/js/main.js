function getRequestWithToken(uri) {

  fetch(uri, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('access_token')}`
    }
  })
  .then(response => {
    if (!response.ok) {
      return response.json().then(errorResponse => {
        throw new Error(JSON.stringify(errorResponse));
      });
    }
    return response.text();
  })
  .then(html => {
    document.open();
    document.write(html);
    document.close();
    window.history.pushState({}, '', uri);
  })
  .catch(error => {
    try{
      const errorResponse = JSON.parse(error.message);
      const errorCode = errorResponse['errorCode'];
      
      if (errorResponse['status'] === 401) {
        alert('회원 인증에 실패하였습니다.');
      }

      else if(errorCode === undefined){
        throw new Error('확인되지 않은 에러이고, 서버 응답을 처리할 수 없습니다.')
      }

      else if(errorCode ===  "Member400_001"){
        alert('토큰은 올바르지만 회원이 아닙니다. 회원가입 해주세요');
      }

      else {
        alert('확인되지 않은 에러입니다. 관리자에게 연락해주시기 바랍니다.');
        console.log(error);
        console.log(JSON.stringify(errorResponse));
      }
      localStorage.removeItem('access_token')
      window.location.href = '';
    } catch (e) {
      alert('확인되지 않은 에러이고, 서버 응답을 처리할 수 없습니다.')
      console.error(error);
    }
  });
}
