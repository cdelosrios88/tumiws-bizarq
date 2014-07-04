<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico    		  -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : EFECTUADO   			  -->
	<!-- Fecha     : 11/10/2013               -->

	
	<h:panelGrid columns="2" id="panelSocio">
			<rich:column width="75">
				<h:outputText value="Socio : " />
			</rich:column>
			<rich:column width="500">
			<h:inputText  						 
					value="#{efectuadoController.seleccionado.socio.id.intIdPersona} 
						   #{efectuadoController.seleccionado.socio.strApePatSoc} 
						   #{efectuadoController.seleccionado.socio.strApeMatSoc} 
						   #{efectuadoController.seleccionado.socio.strNombreSoc}- 
						   #{efectuadoController.seleccionado.documento.strNumeroIdentidad}
					" size="65"  readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />
			</rich:column>
	</h:panelGrid>
	
	
	<h:panelGrid  id="panelMesPeriodo">
		<rich:columnGroup>
		    <rich:column width="600">
				<h:inputText value="Periodo Evaluado " disabled="true" style="width:90px;"/>
		
				<tumih:inputText  cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" style="width:65px;"
				property="#{efectuadoController.dtoDeEfectuado.intPeriodoMes}" disabled="true" />	
		
				<h:inputText value=" #{efectuadoController.dtoDeEfectuado.intPeriodoAnio}"
							 disabled="true" style="width:40px;"/>
			</rich:column>
	   </rich:columnGroup>
	</h:panelGrid>
	
	
	
	<h:panelGrid columns="2" id="panelDependencia1" >
		<rich:column width="75">
				<h:outputText value="Dependencia: " />
		</rich:column>
		<rich:column width="500">
				<h:inputText  value="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial}
									 #{efectuadoController.dtoDeEfectuado.estructura.juridica.strRazonSocial}
									-#{efectuadoController.dtoDeEfectuado.strModalidad2}"  
						  size="65" readonly="true" style="background-color: #BFBFBF;font-weight:bold;" />
		</rich:column>		
	</h:panelGrid>
	
	<h:panelGrid columns="2" id="panelEnviado1">
		<rich:column width="75">
				<h:outputText value="Enviado: " />
		</rich:column>
		<rich:column width="250" >
		
				<h:inputText value="#{efectuadoController.seleccionado.yaEfectuado.bdMontoEnviadoDeEnviomonto}"
							size="5"	readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align: right" />				
		</rich:column>
	</h:panelGrid>
	<h:panelGrid columns="2" id="panelEfectuado1">
		<rich:column width="75">
				<h:outputText value="Efectuado: " />
		</rich:column>
		<rich:column width="250">
				<h:outputText value="No procesado"  styleClass="msgError"
							  rendered="#{efectuadoController.seleccionado.yaEfectuado.bdMontoEfectuado == null}"/>
				<h:inputText value="#{efectuadoController.seleccionado.yaEfectuado.bdMontoEfectuado}" 
							 rendered="#{efectuadoController.seleccionado.yaEfectuado.bdMontoEfectuado != null}"
							 size="5" readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align: right"/>
		</rich:column>
	</h:panelGrid>
	
	<h:panelGrid columns="2" id="panelDependencia2" rendered="#{!efectuadoController.blnTieneEnEDAE}">
		<rich:column width="75">
				<h:outputText value="Dependencia: " />
		</rich:column>
		<rich:column width="500">
			<h:inputText  value="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.strRazonSocial} 
			#{efectuadoController.dtoDeEfectuado.estructura.juridica.strRazonSocial}-
			#{efectuadoController.dtoDeEfectuado.strModalidad}"   
						 size="65"   readonly="true" style="background-color: #BFBFBF;font-weight:bold;" rendered="#{!efectuadoController.blnActivarSegundaModalidad}"/>
			
		</rich:column>
	</h:panelGrid>
	
	<h:panelGrid columns="2" id="panelDependNoTieneEfectuado" rendered="#{efectuadoController.blnTieneEnEDAE}">
		<rich:column width="75">
				<h:outputText value="Dependencia: " />
		</rich:column>
		<rich:column width="500">
			<h:selectOneMenu style="width:400px;" disabled="#{true}" 
							value="#{efectuadoController.dtoDeEfectuado.juridicaSucursal.intIdPersona}">
				<tumih:selectItems var="sel" value="#{efectuadoController.listaJuridicaSucursalDePPEjecutoraEnvio}"
									itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}"/>
			</h:selectOneMenu>			
		</rich:column>
	</h:panelGrid>
	
	<h:panelGrid columns="2" id="panelUnidadEjecutora" rendered="#{efectuadoController.blnTieneEnEDAE}">
			<rich:column width="75">
			</rich:column>
			<rich:column width="500">     
				<h:selectOneMenu value="#{efectuadoController.dtoDeEfectuado.estructura.juridica.intIdPersona}"	
					disabled="#{true}"			
					style="width:400px;">
					<tumih:selectItems var="sel"
						value="#{efectuadoController.listaBusquedaDePPEjecutoraEnvio}"
						itemValue="#{sel.id.intCodigo}" itemLabel="#{sel.juridica.strRazonSocial}" />
				</h:selectOneMenu>
			</rich:column>
		</h:panelGrid>
		
		 <h:panelGrid columns="2" id="panelComboBienHaberOincentivo" rendered="#{efectuadoController.blnTieneEnEDAE}">
			<rich:column width="75">
			</rich:column>
			<rich:column width="500">						
				<h:selectOneMenu style="width:400px;" disabled="#{true}">								 
					<tumih:selectItems var="sel" value="#{efectuadoController.listaModalidadTipoPlanilla}" 
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
					tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
	 		 	</h:selectOneMenu>
			</rich:column>			
		</h:panelGrid>
		
	<h:panelGrid columns="3" id="panelEnviado2">
		<rich:column width="75">
				<h:outputText value="Enviado: " />
		</rich:column>
		
		<rich:column width="500">
				<h:inputText value="#{efectuadoController.seleccionado.efectuado.bdMontoEnviadoDeEnviomonto}"
							size="5"   readonly="true" style="background-color: #BFBFBF;font-weight:bold; text-align:right"/>					
		</rich:column>
		
		<rich:column width="350">
				<a4j:commandButton value="Recalcular" action="#{efectuadoController.recalcularAE}" styleClass="btnEstilos"
				reRender="panelEnviado1,panelEnviado2,panelEfectuado2,panelMensaje,tablaAgregarSocio" >				
				</a4j:commandButton>
		</rich:column>
		
	</h:panelGrid>
	<h:panelGrid columns="3" id="panelEfectuado2">
		<rich:column width="75">
				<h:outputText value="Efectuado: " />				
		</rich:column>
		
		<rich:column width="500" >
				<h:inputText value="#{efectuadoController.seleccionado.efectuado.bdMontoEfectuado}" 
							 onblur="extractNumber(this,2,false);"  size="5"
							 onkeyup="return extractNumber(this,2,false)" style="text-align: right"
							 onkeypress="return soloNumerosDecimalesPositivos(this)" />
		</rich:column>
		<rich:column width="350">
				<a4j:commandButton value="Grabar" action="#{efectuadoController.grabarAE}" 
				reRender="panelEnviado1,panelEnviado2,panelEfectuado2,idGrillaDePlanillaEfectuado,idGrillaNoTieneEfectuada,idGrillaTieneEfectuada" 
				styleClass="btnEstilos" oncomplete="Richfaces.hideModalPanel('pAgregarEfectuado'), Richfaces.hideModalPanel('pAgregarSocioEnEfectuado')">								
				</a4j:commandButton>				
		</rich:column>
		
	</h:panelGrid>
	<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: left"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{efectuadoController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{efectuadoController.mostrarMensajeExito}"/>
			<h:outputText value="#{efectuadoController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{efectuadoController.mostrarMensajeError}"/>
		</h:panelGroup>

