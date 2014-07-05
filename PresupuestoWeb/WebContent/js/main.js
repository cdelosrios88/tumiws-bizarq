/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function selecciona(fila){
    fila.style.background = "#6699FF";
    fila.style.color = "#FFFFFF";
}

function deselecciona(fila){
    fila.style.background = "#FFFFFF";
    fila.style.color = "#000000";
}

//Retorna solo números
function soloNumeros(evt){
//asignamos el valor de la tecla a keynum
	if(window.event){// IE
		keynum = evt.keyCode;
	}else{
		keynum = evt.which;
	}
	//comprobamos si se encuentra en el rango
	if(keynum>47 && keynum<58){
		return true;
	}else{
		return false;
	}
}

function validarEnteros(event){
	return ( event.ctrlKey || event.altKey 
            || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false) 
            || (95<event.keyCode && event.keyCode<106)
            || (event.keyCode==8) || (event.keyCode==9) 
            || (event.keyCode>34 && event.keyCode<40) 
            || (event.keyCode==46) );
}

function validarNumDocIdentidad(obj,event,ancho){
	return ( event.ctrlKey || event.altKey 
            || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false && obj.value.length<ancho) 
            || (95<event.keyCode && event.keyCode<106 && obj.value.length<ancho)
            || (event.keyCode==8) || (event.keyCode==9) 
            || (event.keyCode>34 && event.keyCode<40) 
            || (event.keyCode==46) );
}

//Para números decimales
function extractNumber(obj, decimalPlaces, allowNegative)
{
	var temp = obj.value;
	
	// avoid changing things if already formatted correctly
	var reg0Str = '[0-9]*';
	if (decimalPlaces > 0) {
		reg0Str += '\\.?[0-9]{0,' + decimalPlaces + '}';
	} else if (decimalPlaces < 0) {
		reg0Str += '\\.?[0-9]*';
	}
	reg0Str = allowNegative ? '^-?' + reg0Str : '^' + reg0Str;
	reg0Str = reg0Str + '$';
	var reg0 = new RegExp(reg0Str);
	if (reg0.test(temp)) return true;

	// first replace all non numbers
	var reg1Str = '[^0-9' + (decimalPlaces != 0 ? '.' : '') + (allowNegative ? '-' : '') + ']';
	var reg1 = new RegExp(reg1Str, 'g');
	temp = temp.replace(reg1, '');

	if (allowNegative) {
		// replace extra negative
		var hasNegative = temp.length > 0 && temp.charAt(0) == '-';
		var reg2 = /-/g;
		temp = temp.replace(reg2, '');
		if (hasNegative) temp = '-' + temp;
	}
	
	if (decimalPlaces != 0) {
		var reg3 = /\./g;
		var reg3Array = reg3.exec(temp);
		if (reg3Array != null) {
			// keep only first occurrence of .
			//  and the number of places specified by decimalPlaces or the entire string if decimalPlaces < 0
			var reg3Right = temp.substring(reg3Array.index + reg3Array[0].length);
			reg3Right = reg3Right.replace(reg3, '');
			reg3Right = decimalPlaces > 0 ? reg3Right.substring(0, decimalPlaces) : reg3Right;
			temp = temp.substring(0,reg3Array.index) + '.' + reg3Right;
		}
	}
	
	obj.value = temp;
}
function blockNonNumbers(obj, e, allowDecimal, allowNegative)
{
	var key;
	var isCtrl = false;
	var keychar;
	var reg;
		
	if(window.event) {
		key = e.keyCode;
		isCtrl = window.event.ctrlKey;
	}
	else if(e.which) {
		key = e.which;
		isCtrl = e.ctrlKey;
	}
	
	if (isNaN(key)) return true;
	
	keychar = String.fromCharCode(key);
	
	// check for backspace or delete, or if Ctrl was pressed
	if (key == 8 || isCtrl)
	{
		return true;
	}

	reg = /\d/;
	var isFirstN = allowNegative ? keychar == '-' && obj.value.indexOf('-') == -1 : false;
	var isFirstD = allowDecimal ? keychar == '.' && obj.value.indexOf('.') == -1 : false;
	
	return isFirstN || isFirstD || reg.test(keychar);
}

/**
 * Handler for onkeypress that clicks {@code targetElement} if the
 * enter key is pressed.
 */
function ifEnterClick(event, targetElement) {
    event = event || window.event;
    if (event.keyCode == 13) {
        // normalize event target, so it looks the same for all browsers
        if (!event.target) {
            event.target = event.srcElement;
        }

        // don't do anything if the element handles the enter key on its own
        if (event.target.nodeName == 'A') {
            return;
        }
        if (event.target.nodeName == 'INPUT') {
            if (event.target.type == 'button' || event.target.type == 'submit') {
                if (strEndsWith(event.target.id, 'focusKeeper')) {
                    // inside some Richfaces component such as rich:listShuttle
                } else {
                    return;
                }
            }
        }
        if (event.target.nodeName =='TEXTAREA') {
            return;
        }

        // swallow event
        if (event.preventDefault) {
            // Firefox
            event.stopPropagation();
            event.preventDefault();
        } else {
            // IE
            event.cancelBubble = true;
            event.returnValue = false;
        }

        targetElement.click();
    }
}

function moverMouse(){
  var lControl = top.frames['alto'];
  if(lControl != null){
	lControl.intSegundoSession=0;
  }
}

function fechaAlertaSession(){
  var intSegundo = 0;
  var lControl = top.frames['alto'];
  var lControlBoton = null;
  if(lControl != null){
	  if(lControl.fechaHoraInicio != null){
		  intSegundo = lControl.fechaHoraInicio.getTime() + lControl.intSegundoSession*1000;
		  var fechaHoraTranscurrido = new Date(intSegundo);
		  intSegundo = lControl.fechaHoraInicio.getTime() + getSegundos(lControl.horaSession);
		  var fechaHoraSession = new Date(intSegundo);
		  intSegundo = fechaHoraSession.getTime() - getSegundos(lControl.horaAlertaSession);
		  var fechaHoraAlertaSession = new Date(intSegundo);
	
		  if(fechaHoraTranscurrido>fechaHoraAlertaSession){
			  //document.getElementById('frmPrincipal:linkCerrar').click();
			  //alert('Su session esta por culminar');
			  lControlBoton = document.getElementById('idAlertaSession');
			  if(lControlBoton!= null){
				  lControlBoton.click();
			  }
			  //window.clearInterval(handleAlerta);
			  //handleAlerta = null;
		  }
	  }
  }else{
	  window.clearInterval(handleAlerta);
	  handleAlerta = null;
  }
}

function iniciar(){
	if(handleAlerta == null){	
	   handleAlerta = window.setInterval('fechaAlertaSession()', 10*1000);
	}
}

var handleAlerta = null;

function getSegundos(hora){
  var arrayHora = hora.split('.');
  var segundo=0;
  segundo = arrayHora[2]*(1000);
  segundo = segundo + arrayHora[1]*(60 * 1000);
  segundo = segundo + arrayHora[0]*(3600 * 1000);
  return segundo;
}

function soloNumerosDecimalesPositivos(elementRef)
{
	 var keyCodeEntered = (event.which) ? event.which : (window.event.keyCode) ? window.event.keyCode : -1;
	 //alert(keyCodeEntered);	
	 if ( (keyCodeEntered >= 48) && (keyCodeEntered <= 57) ){
	 	return true;
	 }
	 // '.' decimal point. Allow only 1 decimal point ('.')
	 else if ( keyCodeEntered == 46 ){
	  	if ( (elementRef.value) && (elementRef.value.indexOf('.') >= 0) )
	  		return false;
	  	else
	  		return true;
	 }	
	 return false;
}