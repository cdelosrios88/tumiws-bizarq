<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - GESTIONES -->			
<!-- FECHA     : 04.10.2013               -->

<rich:dataTable value="#{estadoCuentaController.listaGestionCobranzaSocio}" 
				rendered="#{not empty estadoCuentaController.listaGestionCobranzaSocio}"
				styleClass="dataTable1" id="dtGestionCobranzaSoc"
				rowKeyVar="rowKey" rows="5" sortMode="single" width="1250px"
				var="itemGrilla">	
	<f:facet name="header">
		<rich:columnGroup  columnClasses="rich-sdt-header-cell">
	        <rich:column rowspan="2">
				<b><h:outputText value="Tipo de Gestión" /></b>
	        </rich:column>
	        <rich:column rowspan="2">
	            <h:outputText value="Sub Tipo de Gestión" />
	        </rich:column>
	        <rich:column rowspan="2">
	            <h:outputText value="Tipo" />
	        </rich:column>
			<rich:column rowspan="2">
	            <h:outputText value="Fecha" />
	        </rich:column>
	        <rich:column rowspan="2">
	            <h:outputText value="Hora Inicio" />
	        </rich:column>
	        <rich:column rowspan="2">
	            <h:outputText value="Hora Fin" />
	        </rich:column>
	        <rich:column rowspan="2">
	            <h:outputText value="Lugar de Gestión" />
	        </rich:column>
	        <rich:column rowspan="2">
	            <h:outputText value="Nro. Solicitud" />
	        </rich:column>
	        <rich:column rowspan="2" colspan="2">
	            <h:outputText value="Resultado" />
	        </rich:column>
	        <rich:column rowspan="2" colspan="2">
	            <h:outputText value="Observación" />
	        </rich:column>
	        <rich:column rowspan="2" colspan="2">
	            <h:outputText value="Contacto" />
	        </rich:column>
	        <rich:column rowspan="2" colspan="2">
	            <h:outputText value="Gestor" />
	        </rich:column>
	        <rich:column rowspan="2" colspan="2">
	            <h:outputText value="Sucursal" />
	        </rich:column>
	    </rich:columnGroup>
	</f:facet>
    <rich:column style="text-align: center">
		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOGESTION}" 
			itemValue="intIdDetalle" itemLabel="strDescripcion" 
			property="#{itemGrilla.gestionCobranza.intTipoGestionCobCod}"/>
	</rich:column>
	<rich:column style="text-align: center">
		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBTIPOGESTION}" 
			itemValue="intIdDetalle" itemLabel="strDescripcion" 
			property="#{itemGrilla.gestionCobranza.intSubTipoGestionCobCod}"/>
	</rich:column>
	<rich:column style="text-align: center">	
		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_SOCIO}" 
			  itemValue="intIdDetalle" itemLabel="strDescripcion" 
			  property="#{itemGrilla.intTipoPersonaOpe}"/>
	</rich:column>
	<rich:column style="text-align: center">
		<h:outputText value="#{itemGrilla.gestionCobranza.dtFechaGestion}">
			<f:convertDateTime pattern="dd/MM/yyyy"/>
		</h:outputText>
	</rich:column>
	<rich:column style="text-align: center">
		<h:outputText value="#{itemGrilla.gestionCobranza.dtHoraInicio}">
			<f:convertDateTime pattern="HH:mm:ss"/>
		</h:outputText>
	</rich:column>
	<rich:column style="text-align: center">
		<h:outputText value="#{itemGrilla.gestionCobranza.dtHoraFin}">
			<f:convertDateTime pattern="HH:mm:ss"/>
		</h:outputText>
	</rich:column>
	<rich:column style="text-align: center">
		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_LUGARGESTION}" 
			itemValue="intIdDetalle" itemLabel="strDescripcion" 
			property="#{itemGrilla.gestionCobranza.intParaLugarGestion}"/>	
	</rich:column>
	<rich:column style="text-align: center">
		<h:outputText value="#{itemGrilla.intCserItemExp}-#{itemGrilla.intCserdeteItemExp}"/>
	</rich:column>
	<rich:column colspan="2" style="text-align: center">
		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPORESULTADO}" 
			  itemValue="intIdDetalle" itemLabel="strDescripcion" 
			  property="#{itemGrilla.gestionCobranza.intParaTipoResultado}"/>-
		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSUBRESULTADO}" 
			  itemValue="intIdDetalle" itemLabel="strDescripcion" 
			  property="#{itemGrilla.gestionCobranza.intParaTipoSubResultado}"/>													  
	</rich:column>
	<rich:column colspan="2" style="text-align: center">
		<h:outputText value="#{itemGrilla.gestionCobranza.strObservacion}"/>
	</rich:column>
	<rich:column colspan="2" style="text-align: center">
		<h:outputText value="#{itemGrilla.gestionCobranza.strContacto}"/>
	</rich:column>
	<rich:column colspan="2" style="text-align: center">
		<h:outputText id="lblValGestor" value="#{itemGrilla.gestionCobranza.persona.natural.strNombres} #{itemGrilla.gestionCobranza.persona.natural.strApellidoPaterno} #{itemGrilla.gestionCobranza.persona.natural.strApellidoMaterno}" />
	</rich:column>
	<rich:column colspan="2" style="text-align: center">
		<h:outputText id="lblValStrSucursal" value="#{itemGrilla.gestionCobranza.gestorCobranza.sucursal.juridica.strRazonSocial}"></h:outputText>
	</rich:column>
	<f:facet name="footer">     
       	<h:panelGroup layout="block">
	   		 <rich:datascroller for="dtGestionCobranzaSoc" maxPages="10"/>
	   	</h:panelGroup>
   	</f:facet>	
</rich:dataTable>	

