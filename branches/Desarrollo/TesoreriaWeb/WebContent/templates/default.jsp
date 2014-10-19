<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta name="keywords" content=""/>
	<meta name="description" content=""/>
	<title>ERP Tumi <tiles:getAsString name="pageTitle" ignore="true" /></title>
	
	
	<!-- <link  rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset-fonts-grids.css"/> -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/> 
	<script type="text/javascript">
		 A4J.AJAX.onError = function(req, status, message){
			window.alert("Custom onError handler "+message);
		}
	</script>
	
	<script language="JavaScript" src="${pageContext.request.contextPath}/js/main.js"  type="text/javascript"></script>
	
</head>

<body id="mainBody" onload="iniciar();" onmousemove="moverMouse();" oncontextmenu="return false">
<f:view>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">

	<tr><td>
		<table border="0" cellpadding="0" cellspacing="0" width="1000px" style="margin:0 auto">
		<%--
		<tr style="width:100%;">
			<td><tiles:insert definition="page.header" flush="false" /></td>
		</tr>
		--%>
		<tr style="background-color:#DEEBF5;width:100%;">
			<%--
			<td style="color:#88d888;background-color:#0070a6 ;width:200px;"> 
				<tiles:insert definition="page.left" flush="false" />
			</td>
			--%>
			<td style="width:100%;">






				<div id="pageTitle">
					<tiles:getAsString name="pageTitle" ignore="true" />
						<a4j:status id="staEstadoGeneral">
			                <f:facet name="start">
			                    <h:graphicImage id="giEstado"  value="/images/icons/status.gif"/>
			                </f:facet>
			            </a4j:status>
			        
				</div>
				<tiles:insert attribute="body" flush="false" />
			</td>
		</tr>
		<%--
		<tr>
				<tiles:insert definition="page.footer" flush="false" />
		</tr>
		--%>
		</table>
	</td>
	</tr>	
	</table>
<input type="button" id="idAlertaSession" style="display:none;" onclick="Richfaces.showModalPanel('pAlertaSession')"/>
<rich:modalPanel id="pAlertaSession" minWidth="200" minHeight="100" autosized="true" resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGroup><h:outputText value="Alerta de Fin de Sessión"/></h:panelGroup>
    </f:facet>
    <f:facet name="controls">
           <h:graphicImage value="/images/icons/remove_20.png" onclick="Richfaces.hideModalPanel('pAlertaSession')" styleClass="hidelink"/>
    </f:facet>
    <h:form>
    	<rich:panel style="border-width:1px;border-style:solid;border-color:#17356f;background-color:#DEEBF5;width:250px;">                 
            <h:panelGrid columns="2"  border="0" cellspacing="4" >
                <h:outputText id="lblCodigo" value="Su sessión esta por culminar"  style="padding-right:35px;"/>
            </h:panelGrid>
            <h:panelGrid columns="2" border="0"  style="width: 200px;">
                <input type="button" value="Aceptar" onclick="Richfaces.hideModalPanel('pAlertaSession')" class="btnEstilos"/>
            </h:panelGrid>
         </rich:panel>
         <rich:spacer height="4px"/>
    </h:form>
</rich:modalPanel>		
</f:view>
</body>
</html>