		function sort(objName){
			'use strict';
			var radios = document.getElementsByName(objName);
			var result;
			for(var i=0;i<radios.length;i++){
				if(radios[i].checked){
					result = i;
				}
			}
			alert('test');
			document.location.href = "/?order="+result;
			
		}
		