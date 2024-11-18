function teamTagDeleteRequestWithToken(teamTagId) {
    $.ajax({
        type: 'DELETE',
        url: `/admin/page/tag/team/${teamTagId}`,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization',
                "Bearer " + localStorage.getItem('access_token'));
        },
        success: function () {
            alert('팀 태그가 삭제되었습니다.');
            getRequestWithToken('/admin/page/tag/team');
        },
        error: function (xhr) {
            const errorResponse = xhr.responseJSON;
            alert(errorResponse.message);
        }
    });
}
