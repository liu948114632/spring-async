/**
 * Created by sunpeng on 2017/9/25.
 */
var stompClient = null;
var socket = null;
var socket2=null;
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    $("#mess").html("");

}
function connect() {
    // var socket = new SockJS('/gs-guide-webSocket');
    // stompClient = Stomp.over(socket);
    // stompClient.connect({}, function (frame) {
    //     setConnected(true);
    //     console.log('Connected: ' + frame);
    //     stompClient.subscribe('/topic/hello', function (greeting) {
    //         // showGreeting(JSON.parse(greeting.body).content);
    //         showGreeting(greeting.body);
    //     });
    // });
    var name = $("#open").val();
    if(name == null || name == ""){
        alert("名字不能为空")
        return;
    }
    $.ajax({
        url: "/login",
        data: {name: name},
        success: function (res) {
            if (res == "ok") {
                socket = new SockJS('/gs-guide-webSocket');
                setConnected(true);
                socket.onopen=function (e) {
                };
                socket.onmessage = function (e) {
                    console.log(e);
                    showGreeting(e.data);
                };
                console.log("ok");
            } else {
                console.log("连接失败!!");
            }
        }
    });
}
function connect2() {
    var name = $("#open").val();
    if(name == null || name == ""){
        alert("名字不能为空")
        return;
    }
    $.ajax({
        url: "/login",
        data: {name: name},
        success: function (res) {
            if (res == "ok") {
                socket2 = new SockJS('/hello');
                setConnected(true);
                socket2.onopen=function (e) {
                };
                socket2.onmessage = function (e) {
                    console.log(e);
                    mess(e.data);
                };
                console.log("ok");
            } else {
                console.log("连接失败!!");
            }
        }
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    socket.close();
    setConnected(false);
    console.log("Disconnected");
}
function sendName() {
    // stompClient.send("/app/getMessage", {}, JSON.stringify({'name': $("#name").val()}));
    // stompClient.send("/app/getMessage", {}, $("#name").val());
    // $.ajax("/sendMessage",{message:$("#name").val()},function (res) {
    //     console.log("send success")
    // })
    $.ajax({
        url: "/sendMessage",
        data: {message: $("#name").val()},
        success: function (res) {
            console.log("send success")
        }
    });
    // socket.send($("#name").val());  //使用socket的send方法
}

function send() {
    $.ajax({
        url: "/sendMessage_hello",
        data: {message: $("#name2").val()},
        success: function (res) {
            console.log("send success")
        }
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function mess(message) {
    $("#mess").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#connect2" ).click(function() { connect2(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#disconnect2" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#send2" ).click(function() { send(); });
});