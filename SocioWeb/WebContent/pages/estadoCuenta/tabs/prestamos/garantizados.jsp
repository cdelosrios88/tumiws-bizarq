<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - GARANTIZADOS -->			
<!-- FECHA     : 30.09.2013               -->

<h:panelGrid id="formDetalleGarantiaCredito" rendered="#{estadoCuentaController.blnShowPanelTabPrestamosGarantizados}">
	<rich:columnGroup>
		<rich:column width="1150px" style="text-align: left">
			<b><h:outputText value="GARANTIZADOS" /></b>
		</rich:column>
	</rich:columnGroup>
	<!-- DATOS DE LA GRILLA -->	
	<rich:dataTable value="#{estadoCuentaController.listaGarantiaCredito}" 
					styleClass="dataTable1" id="dtGarantizados"
					rowKeyVar="rowKey" rows="5" sortMode="single" width="1150px" 
					var="itemGrilla">		
		<f:facet name="header">
			<rich:columnGroup columnClasses="rich-sdt-header-cell">
				<rich:column width="60px">
					<h:outputText value="Item" />
				</rich:column>
				<rich:column width="300px">
					<h:outputText value="Socio/Cliente" />
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="Aporte" />
				</rich:column>
				<rich:column width="100px">
					<h:outputText value="Fecha" />
				</rich:column>
				<rich:column width="250px">
					<h:outputText value="Conceptos" />
				</rich:column>
				<rich:column width="100px">
					<h:outputText value="Solicitud" />
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="Monto" />
				</rich:column>
				<rich:column width="100px">
					<h:outputText value="CT/CP/CA" />
				</rich:column>
				<rich:column width="80px">
					<h:outputText value="Saldo" />
				</rich:column>
			</rich:columnGroup>
		</f:facet>
		<rich:column width="60px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{rowKey + 1}" />
		</rich:column>
		<rich:column width="300px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.socioComp.persona.natural.intIdPersona} - #{itemGrilla.socioComp.persona.natural.strNombres} #{itemGrilla.socioComp.persona.natural.strApellidoPaterno} #{itemGrilla.socioComp.persona.natural.strApellidoMaterno}" />
		</rich:column>
		<rich:column width="80px" style="text-align: center">
				<h:outputText style="text-align: center" value="#{itemGrilla.bdSaldoAporte}">
				<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="100px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.expedienteComp.strFechaSolicitud}" />
		</rich:column>
		<rich:column width="250px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.expedienteComp.expediente.strDescripcion}" />
		</rich:column>
		<rich:column width="100px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.expedienteComp.expediente.id.intItemExpediente}#{itemGrilla.expedienteComp.expediente.id.intItemExpedienteDetalle}" />
		</rich:column>
		<rich:column width="80px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.expedienteComp.expediente.bdMontoTotal}" >
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<rich:column width="100px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.expedienteComp.strCuotas}" />
		</rich:column>
		<rich:column width="80px" style="text-align: center">
				<h:outputText style="text-align: center"
					value="#{itemGrilla.expedienteComp.expediente.bdSaldoCredito}">
					<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
		<f:facet name="footer">     
        	<h:panelGroup layout="block">
		   		 <rich:datascroller for="dtGarantizados" maxPages="10"/>
		   	</h:panelGroup>
	   	</f:facet>	
	</rich:dataTable>
</h:panelGrid>
