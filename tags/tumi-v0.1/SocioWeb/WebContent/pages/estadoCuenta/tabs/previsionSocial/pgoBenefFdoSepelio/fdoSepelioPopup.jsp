<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- EMPRESA   : COOPERATIVA TUMI         -->
<!-- AUTOR     : JUNIOR CHAVEZ VALVERDE   -->
<!-- MODULO    : SOCIO                    -->
<!-- PROTOTIPO : ESTADO DE CUENTA - PAGOS BENEFICIOS FDO.SEPELIO - DETALLE -->			
<!-- FECHA     : 30.09.2013               -->

<rich:modalPanel id="mpPgoCtasDetalleFdoSepelio" width="880" height="300" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="PAGO DE CUOTAS FDO. SEPELIO - DETALLE"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="mpPgoCtasDetalleFdoSepelio" operation="hide" event="onclick"/>
			</h:graphicImage>
		</h:panelGroup>
	</f:facet> 
	<rich:dataTable value="#{estadoCuentaController.listaPrevSocialFdoSepelioDetalle}" 
					styleClass="dataTable1" id="dtPagoCtasDetalleFdoSepelio"
					rowKeyVar="rowKey" rows="10" sortMode="single" style="margin:0 auto text-align: left"
					var="itemGrilla">
		<f:facet name="header">
			<rich:columnGroup columnClasses="rich-sdt-header-cell">
				<rich:column width="150px" rowspan="2" style="text-align: center">
					<h:outputText value="Fecha de Pago" />
				</rich:column>
				<rich:column width="70px" rowspan="2" style="text-align: center">
					<h:outputText value="Periodo" />
				</rich:column>
				<rich:column width="150px" rowspan="2" style="text-align: center">
					<h:outputText value="Tipo Movimiento" />
				</rich:column>
				<rich:column width="150px" rowspan="2" style="text-align: center">
					<h:outputText value="Nro. Documento" />
				</rich:column>
				<rich:column width="100px" rowspan="2" style="text-align: center">
					<h:outputText value="Cargo / Abono" />
				</rich:column>
				<rich:column width="100px" rowspan="2" style="text-align: center">
					<h:outputText value="Monto" />
				</rich:column>
			</rich:columnGroup>
		</f:facet>

		<rich:column width="100px" style="text-align: center">
			<h:outputText value="#{itemGrilla.strFechaMovimiento}"/>
		</rich:column>
		<rich:column width="70px" style="text-align: center">
			<h:outputText value="#{itemGrilla.intPeriodoFechaCptoPago}"/>
		</rich:column>
		<rich:column width="150px" style="text-align: center">
			<h:outputText value="#{itemGrilla.strDescTipoMovimiento}"/>
		</rich:column>
		<rich:column width="150px" style="text-align: center">
			<h:outputText value="#{itemGrilla.movimiento.strNumeroDocumento}"/>
		</rich:column>
		<rich:column width="100px" style="text-align: center">
			<h:outputText value="#{itemGrilla.strDescTipoCargoAbono}"/>
		</rich:column>
		<rich:column width="100px" style="text-align: center">
			<h:outputText value="#{itemGrilla.movimiento.bdMontoMovimiento}">
			<f:converter converterId="ConvertidorMontos"  /></h:outputText>
		</rich:column>
			
		<f:facet name="footer">     
        	<h:panelGroup layout="block">
		   		 <rich:datascroller for="dtPagoCtasDetalleFdoSepelio" maxPages="10"/>
		   	</h:panelGroup>
	   	</f:facet>
	</rich:dataTable>
</rich:modalPanel>
