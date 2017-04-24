function addNumbers() {
    var number1 = document.addNumbersForm.number1.value;
    var number2 = document.addNumbersForm.number2.value;
    document.getElementById("result").innerHTML = (parseInt(number1) + parseInt(number2)).toString();
}

var person = {name:"John", age:50, getDes:function(info) {
    return this.name + " is " + this.age + " years old and " + info;
}};