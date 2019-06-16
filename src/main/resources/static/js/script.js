function sort(objName) {
	'use strict';
	var radios = document.getElementsByName(objName);
	var result;
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked) {
			result = i;
		}
	}
	document.location.href = "/?order=" + result;
		}
function getSelectedRadioNum(radioName){
	var radios = document.getElementsByName(radioName);
	var result;
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked) {
			result = i;
		}
	}
	return result;
}
function restoreRadio(objName){
	'use strict';
	var num = document.getElementById('order');
	var radios = document.getElementsByName(objName);
	var result;
	for (var i = 0; i < radios.length; i++) {
		if (i==num.value) {
			radios[i].checked=true;
		}
	}
}

function getIdText(){
	var text=document.js.idText.value;
	document.location.href = "edit_form/"+text;
}

function findRecordByTextValue(id,radioName){
	'use strict';
	var target = document.getElementById(id).value;
	var radioNum = getSelectedRadioNum(radioName);
	alert(radioNum);
}