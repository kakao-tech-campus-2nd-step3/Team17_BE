function productTagDeleteRequestWithToken(productTagId) {
    $.ajax({
        type: 'DELETE',
        url: `/admin/page/tag/product/${productTagId}`,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization',
                "Bearer " + localStorage.getItem('access_token'));
        },
        success: function () {
            alert('상품 태그가 삭제되었습니다.');
            getRequestWithToken('/admin/page/tag/product');
        },
        error: function (xhr) {
            const errorResponse = xhr.responseJSON;
            alert(errorResponse.message);
        }
    });
}
