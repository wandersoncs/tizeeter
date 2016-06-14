function updateTizeetSize(tizeet){
    var size = document.getElementById("tizeet_size");
    size.innerHTML = 140 - tizeet.length;
    if(size.innerHTML >= 0){
        size.className = "";
    } else {
        size.className = "invalidTizeet";
    }
}

function updateSubmitButton(tizeetLength){
    var button = document.getElementById("submit");
    if(tizeetLength <= 0 || tizeetLength > 140){
        button.disabled = true;
    }else {
        button.disabled = false;
    }
}

window.onload = function(){

    var tizeet = document.getElementById("tizeet");
    tizeet.addEventListener("keyup", function(){
        updateTizeetSize(tizeet.value);
        updateSubmitButton(tizeet.value.length);
        }
    );
};
