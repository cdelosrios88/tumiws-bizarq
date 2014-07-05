
function jBrowser(){var ua,s,i;this.isIE=false;this.isNS=false;this.version=null;ua=navigator.userAgent;s="MSIE";if((i=ua.indexOf(s))>=0){this.isIE=true;this.version=parseFloat(ua.substr(i+s.length));return;}
s="Netscape6/";if((i=ua.indexOf(s))>=0){this.isNS=true;this.version=parseFloat(ua.substr(i+s.length));return;}
s="Gecko";if((i=ua.indexOf(s))>=0){this.isNS=true;this.version=6.1;return;}}
var jBrowser=new jBrowser();if(!window.j$){window.j$={};}
j$.getAlturaDePantalla=function(){var altura=0;try{if(jBrowser.isIE){if(jBrowser.version>7){altura=document.body.clientHeight;}else{altura=document.documentElement.clientHeight;}}else if(jBrowser.isNS){if(jBrowser.version>3){altura=document.body.clientHeight;}else{altura=document.documentElement.clientHeight;}}}catch(e){alert('Script getAlturaDePantalla msg: '+e.message)}
return altura;}
j$.getAlturaMediaDePantalla=function(){var posicion=0;var heightPantalla=0;var top=0;try{heightPantalla=j$.getAlturaDePantalla();if(jBrowser.isIE){top=document.body.scrollTop;}else{top=document.documentElement.scrollTop;}
if(heightPantalla>0){posicion=top+heightPantalla/2;}else{posicion=0;}
return posicion;}catch(e){alert('Script getAlturaMediaDePantalla msg: '+e.message)}}
j$.setAlturaDePantallaPorId=function(pIdControl){var lControl;try{lControl=document.getElementById(pIdControl);if(lControl!=null&&lControl!=undefined){styleControlador=lControl.style;styleControlador.height=j$.getAlturaDePantalla()+'px';}}catch(e){alert('Script setAlturaDePantalla msg: '+e.message)}
return false;}