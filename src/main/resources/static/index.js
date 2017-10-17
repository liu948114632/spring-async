var add = function () {
    var counter = 1;
    var b = function(){console.log(counter);counter = 10;}
    return {
        ad: b,
        oper:function () {return counter += 1;}
    }
};
function myFunction(){
    var c = add().oper;
    add().ad();
    document.getElementById("demo").innerHTML = c();
}
