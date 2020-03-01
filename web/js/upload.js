$(document).ready(function() {
    $('#upload').click(function() {
        upload($('#uploadfile').val());
    });
});

function upload(fileName) {
    $.ajaxFileUpload({
        url : '/user/do_tx',   //提交的路径
        secureuri : false, // 是否启用安全提交，默认为false
        fileElementId : 'uploadfile', // file控件id
        dataType : 'json',
        data : {
            fileName : fileName   //传递参数，用于解析出文件名
        }, // 键:值，传递文件名
        success : function(data, status) {
        },
        error : function(data, status) {
        }
    });
}
// 得到图片源
function getFileUrl(sourceId) {
    var url;
    // 前端预览
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
        url = document.getElementById(sourceId).value;
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}

// 文件的id  要显示pic的img的id
function preImg(sourceId, targetId) {
    var url = getFileUrl(sourceId);
    var imgPre = document.getElementById(targetId);
    imgPre.src = url;
}