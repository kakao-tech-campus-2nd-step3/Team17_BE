function productTagAddRequestWithToken() {
    const requestJson = {
        "productTagName": document.getElementById("productTagName").value
    };

    $.ajax({
        type: 'POST',
        url: '/admin/page/tag/product',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(requestJson),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization',
                "Bearer " + localStorage.getItem('access_token'));
        },
        success: function () {
            alert('상품 태그가 추가되었습니다.');
            getRequestWithToken('/admin/page/tag/product');
        },
        error: function (xhr) {
            const errorResponse = xhr.responseJSON;
            alert(errorResponse.message);
        }
    });
}
