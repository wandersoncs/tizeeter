var messages = {
    "obrigatorio": "Campo obrigatório",
    "tamanho": "Este campo deve possuir no mínimo 6 e no máximo 24 caracteres",
    "senha": "Senha e Confirmação da senha devem ser iguais",
    "invalid-email": "Email inválido"
}

function isEmpty(userInput){
    return userInput.length == 0;
}

function hasValidSize(userInput, min, max){
    return userInput.length <= max && userInput.length >= min;
}

function showMessageError(fieldID, message){
    document.getElementById(fieldID + "-error").innerHTML = message;
}

function clearMessages(){
    var error = document.querySelectorAll(".error");
    for(var i in error){
        error[i].innerHTML = "";
    }

}

function validateForm(event){
    
    var isValid = true;
    clearMessages();

    var fields = ["username", "email", "password", "conf-password", "name", "address"];
    for(var id in fields){
        var fieldValue = document.getElementById(fields[id]).value;
        if(isEmpty(fieldValue)){
            showMessageError(fields[id], messages["obrigatorio"]);
            isValid = false;
        }
    }

    var fieldsSize = ["username", "password", "conf-password"];
    for(id in fieldsSize){
        var fieldValue = document.getElementById(fieldsSize[id]).value;
        if(!hasValidSize(fieldValue, 6, 24)){
           showMessageError(fieldsSize[id], messages["tamanho"]);
           isValid = false;
        }
    }

    var password = document.getElementById("password");
    var confPassword = document.getElementById("conf-password");
    if(password.value !== confPassword.value){
        showMessageError("conf-password", messages["senha"]);
        isValid = false;
    }

    if(document.querySelector("input[type=radio]:checked") == null){
        showMessageError("sex", messages["obrigatorio"]);
        isValid = false;
    }

    var email = document.getElementById("email").value;
    if(email.indexOf("@") == -1 || email.indexOf(".") == -1){
        showMessageError("email", messages["invalid-email"]);
        isValid = false;
    }

    if(!isValid){
    	event.preventDefault();
    }
    return isValid;
}

window.onload = function(){
    var form = document.getElementById("cadForm");
    form.addEventListener('submit', validateForm);
}
