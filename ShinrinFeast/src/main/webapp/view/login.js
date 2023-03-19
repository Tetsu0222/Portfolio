var elmSubmit = document.getElementById("ID_SUBMIT");
elmSubmit.onclick = function(){
  var elmUserId   = document.getElementById("ID_USER_ID");
  var elmPassword = document.getElementById("ID_PASSWORD");
  var canSubmit = true;
  if(elmUserId.value == "" || elmPassword.value == ""){
    alert("入力漏れの項目があります。");
    canSubmit = false;
  }
  return canSubmit;
}
