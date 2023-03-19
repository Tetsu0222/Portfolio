var elmSubmit = document.getElementById("ID_SUBMIT");
elmSubmit.onclick = function(){
  var elmUserName   = document.getElementById("NAME");
  var elmUserEmail = document.getElementById("EMAIL");
  var elmUserMessege = document.getElementById("MESSAGE");
  var canSubmit = true;
  if(elmUserName.value == "" || elmUserEmail.value == "" || elmUserMessege ==""){
    alert("入力漏れの項目があります。");
    canSubmit = false;
  }
  return canSubmit;
}
