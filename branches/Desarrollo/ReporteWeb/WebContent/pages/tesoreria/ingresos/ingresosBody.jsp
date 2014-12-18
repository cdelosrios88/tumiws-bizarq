<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<h:form>
	<h:outputText value="#{ingresoCajaController.limpiarIngreso}"/>
	
	<div align="center" style="font-size:medium;"><b>LIBRO CAJA</b></div>
	<br/>
	
	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate" columns="2">
 		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Sucursal :"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu id="cboSucursal" 
					value="#{ingresoCajaController.ingresoCajaFiltro.intIdSucursal}" style="width: 140px;">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{ingresoCajaController.listJuridicaSucursal}"
					itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
				</h:selectOneMenu>
			</rich:column>
		</rich:columnGroup>
	</h:panelGrid>
	<br/>
	
	<h:panelGrid>
		<h:selectOneRadio value="#{ingresoCajaController.intTipoIndFecha}">
			<f:selectItem itemValue="1" itemLabel="Rango Fechas   "/>
   			<f:selectItem itemValue="2" itemLabel="Mensual" />
   			<a4j:support event="onchange" action="#{ingresoCajaController.showDatesByIndicator}" 
   				reRender="pgRanFecha,pgPeriodoMensual"/>
		</h:selectOneRadio>
	</h:panelGrid>
	<br/>
	
	<rich:panel style="border: 1px solid #17356f;background-color:#DEEBF5;">
		<h:panelGrid id="pgRanFecha" columns="2">
			<rich:columnGroup>
				<rich:column>
					<h:outputText value="Fecha Ini.:"/>
				</rich:column>
				<rich:column>
					<rich:calendar value="#{ingresoCajaController.ingresoCajaFiltro.dtFecIni}"
						datePattern="dd/MM/yyyy" style="width:76px" disabled="#{ingresoCajaController.mostrarRanFecha}">
					</rich:calendar>
				</rich:column>
				<rich:column>
					<h:outputText value="Fecha Fin.:"></h:outputText>
				</rich:column>
				<rich:column>
					<rich:calendar value="#{ingresoCajaController.ingresoCajaFiltro.dtFecFin}"
						datePattern="dd/MM/yyyy" style="width:76px" disabled="#{ingresoCajaController.mostrarRanFecha}">
					</rich:calendar>
				</rich:column>
			</rich:columnGroup>
			<br/>
		    
		    <rich:columnGroup>
		    	<rich:column>
						<h:outputText value="Mes:"></h:outputText>
				</rich:column>
		  		<rich:column>
		  			<h:selectOneMenu id="cboMesDesde" value="#{ingresoCajaController.ingresoCajaFiltro.intMesIngreso}"
		  				disabled="#{ingresoCajaController.mostrarPeriodoMensual}">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
		  		</rich:column>
		 		<rich:column>
					<h:outputText value="Año :"/>
				</rich:column>
				<rich:column>
					<h:selectOneMenu id="cboAnioBusq" value="#{ingresoCajaController.ingresoCajaFiltro.intAnioIngreso}"
						disabled="#{ingresoCajaController.mostrarPeriodoMensual}">
						<f:selectItems id="lstYears" value="#{ingresoCajaController.listYears}"/>
					</h:selectOneMenu>
				</rich:column>
		    </rich:columnGroup>
		</h:panelGrid>
	</rich:panel>
    <br/>
		
	<h:panelGrid columns="3">
		<a4j:commandButton value="Buscar" action="#{ingresoCajaController.buscarIngresos}" 
			styleClass="btnEstilos" reRender="divTblIngresosCaja,divTblDepositosBanco,tblResumen,panelMensaje" />
	 	
	 	<h:commandButton id="btnImprimir" value="Imprimir" styleClass="btnEstilos"
	 		action="#{ingresoCajaController.printReport}"/>
	</h:panelGrid>
	
	<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
		styleClass="rich-tabcell-noborder">
		<h:outputText value="#{ingresoCajaController.mensajeOperacion}" 
			styleClass="msgInfo"
			style="font-weight:bold"
			rendered="#{ingresoCajaController.mostrarMensajeExito}"/>
		<h:outputText value="#{ingresoCajaController.mensajeOperacion}" 
			styleClass="msgError"
			style="font-weight:bold;font-size:medium;"
			rendered="#{ingresoCajaController.mostrarMensajeError}"/>
	</h:panelGroup>
	<br/>
		
		<rich:panel id="pMarco1" style="border: 1px solid #17356f;background-color:#DEEBF5;">
               <f:facet name="header">
                   <h:outputText id="lblIngresosCaja" value="INGRESOS A CAJA"></h:outputText>
               </f:facet>
               
               <h:panelGroup id="divTblIngresosCaja" layout="block" style="padding:15px">
				<rich:extendedDataTable id="tblIngresosCaja"  
						enableContextMenu="false"  
					style="margin:0 auto"
					var="item"
					rowKeyVar="rowKey"
					rows="5" 
					sortMode="single" 
					width="850px" 
					height="180px"
					value="#{ingresoCajaController.listaIngresosCaja}">
					
					<rich:column width="31px">
						<f:facet name="header">
							<h:outputText value="Nro."></h:outputText>
						</f:facet>
						<h:outputText value="#{rowKey + 1}"></h:outputText>
					</rich:column>
					<rich:column width="80px"> 
						<f:facet name="header">
							<h:outputText value="Nº Ingreso"/>
						</f:facet>
						<h:outputText value="#{item.strNroIngreso}"/>
					</rich:column>
					<rich:column width="180px"> 
						<f:facet name="header">
							<h:outputText value="Recibi De ."/>
						</f:facet>
						<h:outputText value="#{item.strRecibi}"/>
					</rich:column>
					<rich:column width="70px">
						<f:facet name="header">
						<h:outputText value="Fecha"/>
						</f:facet>
						<h:outputText value="#{item.strFechaIngreso}"/>
					</rich:column> 
					<rich:column width="80px">
						<f:facet name="header">
							<h:outputText value="Monto"/>
						</f:facet>
						<h:outputText value="#{item.bdMontoTotal}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="80px">
						<f:facet name="header">
							<h:outputText value="Fec. Anula"/>
						</f:facet>
						<h:outputText value="#{item.strFechaAnulado}"/>
					</rich:column> 
					<rich:column width="180px">
						<f:facet name="header">
							<h:outputText value="Detalle"/>
						</f:facet>
						<h:outputText value="#{item.strDetalle}"/>
					</rich:column> 
					<rich:column width="100px">
						<f:facet name="header">
							<h:outputText value="Nro. Deposito"/>
						</f:facet>
						<h:outputText value="#{item.strDeposito}"/>
					</rich:column> 
				  	<f:facet name="footer">   
		            	<rich:datascroller for="tblIngresosCaja" maxPages="10"/>   
		           	</f:facet>
				</rich:extendedDataTable>
			</h:panelGroup>
		</rich:panel>
		
		<rich:panel id="pMarco2" style="border: 1px solid #17356f;background-color:#DEEBF5;">
               <f:facet name="header">
                   <h:outputText id="lblDepositosBanco" value="DEPOSITOS A BANCO"></h:outputText>
               </f:facet>
               
               <h:panelGroup id="divTblDepositosBanco" layout="block" style="padding:15px">
				<rich:extendedDataTable id="tblDepositosBanco"  
						enableContextMenu="false"  
					style="margin:0 auto"
					var="item"
					rowKeyVar="rowKey"
					rows="5" 
					sortMode="single" 
					width="900px" 
					height="190px"
					value="#{ingresoCajaController.listaDepositosCaja}">					
					
					<rich:column width="31px">
						<f:facet name="header">
							<h:outputText value="Nro."/>
						</f:facet>
						<h:outputText value="#{rowKey + 1}"/>
					</rich:column>
					<rich:column width="80px"> 
						<f:facet name="header">
							<h:outputText value=" Nº Ingreso "/>
						</f:facet>
						<h:outputText value="#{item.strNroIngreso}"/>
					</rich:column>
					<rich:column width="160px"> 
						<f:facet name="header">
							<h:outputText value="Recibí De"/>
						</f:facet>
						<h:outputText value="#{item.strRecibi}"/>
					</rich:column>
					<rich:column width="160px">
						<f:facet name="header">
						<h:outputText value="Banco"/>
						</f:facet>
						<h:outputText value="#{item.strNombreBanco}"/>
					</rich:column> 
					<rich:column width="120px">
						<f:facet name="header">
							<h:outputText value="Nro. Cuenta"/>
						</f:facet>
						<h:outputText value="#{item.strNumeroCuenta}"/>
					</rich:column>
					<rich:column width="90px">
						<f:facet name="header">
							<h:outputText value="Monto"/>
						</f:facet>
						<h:outputText value="#{item.bdMontoTotal}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
					<rich:column width="90px">
						<f:facet name="header">
							<h:outputText value="Fec. Dep."/>
						</f:facet>
						<h:outputText value="#{item.strFechaIngreso}"/>
					</rich:column> 
					<rich:column width="180px">
						<f:facet name="header">
							<h:outputText value="Detalle"/>
						</f:facet>
						<h:outputText value="#{item.strDetalle}"/>
					</rich:column>
				  	<f:facet name="footer">   
		            	<rich:datascroller for="tblDepositosBanco" maxPages="10"/>   
		           	</f:facet>
				</rich:extendedDataTable>	
	
		</h:panelGroup>
	</rich:panel>
	
	<div align="center">
		<rich:panel id="pMarco3" style="border: 1px solid #17356f;background-color:#DEEBF5;">
			<f:facet name="header">
	           	<h:outputText id="lblDetalleAsiento" value="DETALLE DEL ASIENTO NRO. :"></h:outputText>
	        </f:facet>
           	<h:panelGrid id="tblResumen" styleClass="tableCellBorder4" style="border:1px solid #17356f;padding:15px;">
               	<rich:columnGroup>
					<rich:column>
						<b>Resumen de Ingresos</b>
					</rich:column>
				</rich:columnGroup>
               	<rich:columnGroup>
					<rich:column >
						<h:outputText value="Ingresos a Caja: "/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{ingresoCajaController.bdTotIngresoCaja}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
               	</rich:columnGroup>
               	<rich:columnGroup>
					<rich:column>
						<h:outputText value="Deposito a Banco: "/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{ingresoCajaController.bdTotDepositoBanco}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>						
               	</rich:columnGroup>
               	<rich:columnGroup>
					<rich:column>
						<h:outputText value="Total: "/>
					</rich:column>
					<rich:column>
						<h:outputText value="#{ingresoCajaController.bdTotDifMontoTotal}">
							<f:converter converterId="ConvertidorMontos"/>
						</h:outputText>
					</rich:column>
               	</rich:columnGroup>
            </h:panelGrid>
		</rich:panel>
	</div>
</h:form>