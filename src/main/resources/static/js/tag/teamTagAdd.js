function teamTagAddRequestWithToken() {
    const requestJson = {
        "teamTagName": document.getElementById("teamTagName").value,
        "teamTagAttribute": document.getElementById("teamTagAttribute").value
    };

    $.ajax({
        type: 'POST',
        url: '/admin/page/tag/team',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(requestJson),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization',
                "Bearer " + localStorage.getItem('access_token'));
        },
        success: function () {
            alert('팀 태그가 추가되었습니다.');
            getRequestWithToken('/admin/page/tag/team');
        },
        error: function (xhr) {
            const errorResponse = xhr.responseJSON;
            alert(errorResponse.message);
        }
    });
}
