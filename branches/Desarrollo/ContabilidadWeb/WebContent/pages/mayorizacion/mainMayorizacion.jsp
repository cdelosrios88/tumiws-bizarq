<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<!-- Empresa   : Cooperativa Tumi						-->
	<!-- Cod. Req  : REQ14-004: Proceso de Mayorizaci�n		-->
	<!-- Autor     : Christian De los R�os  				-->
	<!-- Modulo    : Contabilidad         					-->
	<!-- Prototipo : Mayorizacion	 						-->
	<!-- Fecha     : 08/09/2014       						-->

<h:form id="frmLoginMayorizacion">
	<div align="center">
		<br/>
		<font size="5"><b>Mayorizaci&oacute;n - Favor ingresar el password de modo que pueda acceder al formulario principal</b></font>
		<br/><br/>
		<rich:panel id="tabla" style="width:960px;border:1px solid #17356f;background-color:#DEEBF5;">
			<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
				<h:panelGrid columns="2" style="border:0px;width:300px;" columnClasses="columna">
					<rich:column width="100px" >
						<h:outputText value ="PASSWORD:" />
					</rich:column>
					<rich:column width="100px">
						<h:inputSecret value="#{mayorizacionController.strPassword}" size="30"/>
					</rich:column>
				</h:panelGrid>
				<br/>
				<h:outputText value="#{mayorizacionController.strMsgError}" styleClass="msgError"/>
				<br/><br/>
				<h:panelGrid>
					<a4j:commandButton value="Aceptar" action="#{mayorizacionController.autenticar}" styleClass="btnEstilos" style="width:65px"
						reRender="tabla"/>
				</h:panelGrid>
			</rich:panel>
		</rich:panel>
	</div>
	<br/><br/>
</h:form>