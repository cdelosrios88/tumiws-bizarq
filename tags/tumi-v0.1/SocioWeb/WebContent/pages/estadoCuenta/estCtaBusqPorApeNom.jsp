<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - PAGOS BENEFICIOS FDO.SEPELIO - RESUMEN) -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGrid id="formBusquedaPorApellidosYNombres" rendered="#{not empty estadoCuentaController.listaSocioBusquedaPorApeNom}">
	<rich:extendedDataTable id="edtSocioNatural" enableContextMenu="false" sortMode="single" 
		var="item" value="#{estadoCuentaController.listaSocioBusquedaPorApeNom}"
		rowKeyVar="rowKey" rows="5" width="950px" height="186px"
		onRowClick="selectedSocioNatural('#{rowKey}')">                     
		<rich:column width="29px">
			<h:outputText value="#{rowKey + 1}"></h:outputText>
		</rich:column>
		<rich:column width="55">
			<f:facet name="header">
				<h:outputText  value="Código"></h:outputText>
			</f:facet>
			<h:outputText value="#{item.persona.natural.intIdPersona}"></h:outputText>
		</rich:column>
		<rich:column width="130">
			<f:facet name="header">
				<h:outputText value="Nombre Persona"></h:outputText>
			</f:facet>
			<h:outputText value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno}"></h:outputText>
		</rich:column>		
		<rich:column width="115">
			<f:facet name="header">
				<h:outputText value="Nro. Documento"></h:outputText>
			</f:facet>
			<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"></h:outputText>
		</rich:column>
		<rich:column width="110">
			<f:facet name="header">
				<h:outputText value="Tipo Socio"></h:outputText>
			</f:facet>
			<h:outputText value="#{item.strTipoSocio}"/>
		</rich:column>
		<rich:column width="110">
			<f:facet name="header">
				<h:outputText value="Condición"></h:outputText>
			</f:facet>
			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" 
				property="#{item.persona.natural.perLaboral.intCondicionLaboral}"/>
		</rich:column>
		<rich:column>
			<f:facet name="header">
				<h:outputText value="Sucursal"></h:outputText>
			</f:facet>
			<tumih:outputText value="#{socioEstrucController.listSucursal}" 
				itemValue="id.intIdSucursal" itemLabel="juridica.strRazonSocial" 
				property="#{item.socio.socioEstructura.intIdSucursalAdministra}"/>
		</rich:column>
		<rich:column width="100">
			<f:facet name="header">
				<h:outputText value="Modalidad"></h:outputText>
			</f:facet>
			<h:outputText value="#{item.strModalidad}"/>
		</rich:column>
		<rich:column width="100">
			<f:facet name="header">
				<h:outputText value="Tipos Cuenta"></h:outputText>
			</f:facet>
			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTASOCIO}"
				itemValue="intIdDetalle" itemLabel="strDescripcion" 
				property="#{item.cuenta.intParaTipoCuentaCod}"/>		
		</rich:column>
		<rich:column width="100">
			<f:facet name="header">
				<h:outputText value="Fecha Registo"></h:outputText>
			</f:facet>
			<h:outputText value="#{item.socio.socioEstructura.dtFechaRegistro}">
				<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="America/Bogota"/>
			</h:outputText>
		</rich:column>
		<f:facet name="footer">     
			<h:panelGroup layout="block">
				<rich:datascroller for="edtSocioNatural" maxPages="10"/>
				<h:panelGroup layout="block" style="margin:0 auto; width:580px">
					<a4j:commandLink action="#{estadoCuentaController.buscarDatosSocioPorApellidosYNombres}"
						reRender="formCabeceraDatosSocioCuentaYTabs,formServiciosCuentaSocio,formFiltrosBusqueda">
						<h:graphicImage value="/images/icons/mensaje1.jpg" style="border:0px"/>
					</a4j:commandLink>
					<h:outputText value="Para Ver el Estado De Cuenta de un SOCIO/CLIENTE hacer click en el Registro" style="color:#8ca0bd"/>
				</h:panelGroup>
			</h:panelGroup>  
		</f:facet>
	</rich:extendedDataTable>
</h:panelGrid>