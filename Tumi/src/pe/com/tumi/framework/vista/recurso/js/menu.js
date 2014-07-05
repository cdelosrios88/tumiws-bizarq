
j$.setControlMenu=function(){var lControl;try{lControl=document.getElementById('idControlMenu');if(lControl!=null&&lControl!=undefined){styleControlador=lControl.style;styleControlador.top=j$.getAlturaMediaDePantalla()+'px';}}catch(e){alert('Script situarControlMenu msg: '+e.message)}
return false;}
j$.controlarMenu=function(){j$.invocarAjax('modificarMenu');}
j$.setEstadoVisualMenu=function(psEstadoMenu){try{if(psEstadoMenu!=''){if(psEstadoMenu=='estadoVisible'){j$.mostrarMenu();}else if(psEstadoMenu=='estadoOculto'){j$.ocultarMenu();}
j$.setControlMenu();}}catch(e){alert('Script setEstadoVisualMenu: '+e.message);}}
j$.ocultarMenu=function(){try{var btn=document.getElementById('idBtnControlMenu');btn.value=">";if(jBrowser.isIE){parent.masterModulo.cols="0%,1%,*";}else{top.document.getElementById('masterModulo').cols="0%,1%,*";}}catch(e){alert('Script ocultarMenu msg: '+e.message)}}
j$.mostrarMenu=function(){try{var btn=document.getElementById('idBtnControlMenu');btn.value="<";if(jBrowser.isIE){parent.masterModulo.cols="20%,1%,79%";}else{top.document.getElementById('masterModulo').cols="20%,1%,79%";}}catch(e){alert('Script mostrarMenu msg: '+e.message)}}
var XMLHttpRequestObject=false;if(window.XMLHttpRequest){XMLHttpRequestObject=new XMLHttpRequest();}else if(window.ActiveXObject){XMLHttpRequestObject=new ActiveXObject("Microsoft.XMLHTTP");}
j$.invocarAjax=function(pMetodo){var lURL=strContextPath+"/remoting";var lsRespuesta;if(pMetodo!=null){lURL=lURL+"?parametro="+pMetodo;if(XMLHttpRequestObject){XMLHttpRequestObject.open("GET",lURL);XMLHttpRequestObject.onreadystatechange=function(){var lReadyState=XMLHttpRequestObject.readyState;if(lReadyState==4){var lStatus=XMLHttpRequestObject.status;if(lStatus==200){lsRespuesta=XMLHttpRequestObject.responseText;j$.setEstadoVisualMenu(lsRespuesta);}}}
XMLHttpRequestObject.send(null);}}}