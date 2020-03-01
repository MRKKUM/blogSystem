
var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    var name = document.getElementById("name").value;
    websocket = new WebSocket("ws://192.168.18.188:8081/webSocket/"+name);
}
else {
    alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function () {
    setMessageInnerHTML("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    setMessageInnerHTML("欢迎您加入聊天室!");
}

//接收到消息的回调方法
websocket.onmessage = function (event) {
    setMessageInnerHTML(event.data);
}
// 接收到人数
websocket
//连接关闭的回调方法
websocket.onclose = function () {
    setMessageInnerHTML("已退出聊天室,欢迎下次再来！");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    var div = document.getElementById('message');
    // 信息加入
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
    div.scrollTop = div.scrollHeight;
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var name = document.getElementById("name").value;
    var message = document.getElementById('text').value;
    websocket.send(name+": "+message);
    document.getElementById("text").value="";
    document.getElementById("text").focus()
}