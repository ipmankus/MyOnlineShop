function TurnError(IdTarget){
    if(document.getElementById(IdTarget).classList.contains("has-success"))
        document.getElementById(IdTarget).classList.remove("has-success");
    if(!document.getElementById(IdTarget).classList.contains("has-error"))
        document.getElementById(IdTarget).classList.add("has-error");
}
function TurnSuccess(IdTarget){
    if(document.getElementById(IdTarget).classList.contains("has-error"))
        document.getElementById(IdTarget).classList.remove("has-error");
    if(!document.getElementById(IdTarget).classList.contains("has-success"))
        document.getElementById(IdTarget).classList.add("has-success");
}
function ValidateName(){
    var currName = document.getElementById("reqName").value;
    var errorMessage = "";
    if(currName.length < 3){
        errorMessage = "Nama terlalu pendek, minimal 3 karakter.";
        TurnError("formName");
    }else{
        TurnSuccess("formName");
    }
    document.getElementById("errorName").innerHTML = errorMessage;
}
function ValidateEmail(){
    var currEmail = document.getElementById("reqEmail").value;
    var errorMessage = "";
    var reValidEmail = /\S+@\S+\.\S+/;
    if(!reValidEmail.test(currEmail)){
        errorMessage = "Alamat Email tidak valid.";
        TurnError("formEmail");
    }else{
        TurnSuccess("formEmail");
    }
    document.getElementById("errorEmail").innerHTML = errorMessage;
}
function ValidatePassword(){
    var currPassword = document.getElementById("reqPassword").value;
    var errorMessage = "";
    if(currPassword.length < 6){
        errorMessage = "Kata Sandi terlalu pendek, minimal 6 karakter.";
        TurnError("formPassword");
    }else{
        TurnSuccess("formPassword");
    }
    document.getElementById("errorPassword").innerHTML = errorMessage;
}