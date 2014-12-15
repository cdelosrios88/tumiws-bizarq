<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<h:form>
	<h:panelGroup layout="block" style="border:1px solid #17356f">
		<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate" columns="2">
	   	 		<rich:columnGroup>
	    			<rich:column >
	    			<h:selectOneRadio id="idRangoFechas">
	    				<f:selectItem itemValue="Rango de Fechas"/>
                     </h:selectOneRadio>
                     </rich:column>
                     <rich:column>
							<h:outputText value="Fecha Ini.:"></h:outputText>
						</rich:column>
						<rich:column>
							<rich:calendar value="#{constanteController.beanConsNatural.dtFecNacimiento}"
								datePattern="dd/MM/yyyy" style="width:76px">
							</rich:calendar>
						</rich:column>
						<rich:column>
							<h:outputText value="Fecha Fin.:"></h:outputText>
						</rich:column>
						<rich:column>
							<rich:calendar 
								datePattern="dd/MM/yyyy" style="width:76px">
							</rich:calendar>
						</rich:column>
	    		</rich:columnGroup>
	    		<rich:columnGroup>
	    			<rich:column>
							<h:outputText value="Mes:"></h:outputText>
					</rich:column>
	    			<rich:column >
	    				<h:selectOneMenu id="cboMesDesde" >
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
						</h:selectOneMenu>
	    			</rich:column>
	    				<rich:column>
							<h:outputText value="Año :"></h:outputText>
						</rich:column>
						<rich:column>
							<h:selectOneMenu id="cboAnioBusq" >
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<f:selectItems id="lstYears" value="#{prestamoController.listYears}"/>
							</h:selectOneMenu>
						</rich:column>
	    		</rich:columnGroup>
	    </h:panelGrid>
	</h:panelGroup>
	
	<h:panelGroup layout="block" style="border:1px solid #17356f">
		<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate" columns="2">
	   	 		<rich:columnGroup>
	    			<rich:column >
	    				<h:outputText value="Sucursal :"></h:outputText>
	    			</rich:column>
	    			<rich:column>
						<h:selectOneMenu  id="cboSucursal" style="width: 140px;" 
							 onchange="getSubSucursal(#{applicationScope.Constante.ONCHANGE_VALUE})">
							<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{prestamoController.listJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}" propertySort="juridica.strRazonSocial"/>
							</h:selectOneMenu>
						</rich:column>
				</rich:columnGroup>
		</h:panelGrid>
		
			<h:panelGrid columns="3">
				<a4j:commandButton value="Buscar" styleClass="btnEstilos"  
 						>
				</a4j:commandButton> 
			 	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"  
			 			>
				</a4j:commandButton>
			 	<a4j:commandButton value="Imprimir" styleClass="btnEstilos"	
			 			>
				</a4j:commandButton> 
		 			
		</h:panelGrid>
		
		<rich:panel id="pMarco1" style="border: 1px solid #17356f;background-color:#DEEBF5;">
                <f:facet name="header">
                    <h:outputText id="lblIngresosCaja" value="INGRESOS A CAJA"></h:outputText>
                </f:facet>
                
                <h:panelGroup id="divTblIngresosCaja" layout="block" style="padding:15px">
					<rich:extendedDataTable id="tblIngresosCaja"  
 						enableContextMenu="false"  
						style="margin:0 auto"
						var="item"
						onRowClick="selecConstante('#{rowKey}')" 
						rowKeyVar="rowKey"
						rows="3" 
						sortMode="single" 
						width="800px" 
						height="190px"
						>					
										
						<rich:column> 
							<f:facet name="header">
								<h:outputText value="Recibi De ."></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column>
						<rich:column>
							<f:facet name="header">
							<h:outputText value="Fecha"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column> 
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Monto"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Fec Anual"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column> 
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Detalle"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column> 
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Nro. Deposito"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
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
						onRowClick="selecConstante('#{rowKey}')" 
						rowKeyVar="rowKey"
						rows="4" 
						sortMode="single" 
						width="800px" 
						height="190px"
						>					
						
						<rich:column width="31px">
							<f:facet name="header">
								<h:outputText value="Nro."></h:outputText>
							</f:facet>
							<h:outputText value="#{rowKey + 1}"></h:outputText>
						</rich:column>				
						<rich:column> 
							<f:facet name="header">
								<h:outputText value=" Nº Ingreso "></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column>
						<rich:column>
							<f:facet name="header">
							<h:outputText value="Recibi de "></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column> 
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Fecha"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Monto "></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column> 
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Fec_Anual"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column> 
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Detalle"></h:outputText>
							</f:facet>
							<h:outputText ></h:outputText>
						</rich:column>
							  <f:facet name="footer">   
					            	<rich:datascroller for="tblDepositosBanco" maxPages="10"/>   
					           </f:facet>
						
					</rich:extendedDataTable>	
		
			</h:panelGroup>
		</rich:panel>
		
		<rich:panel id="pMarco3" style="border: 1px solid #17356f;background-color:#DEEBF5;">
        	<f:facet name="header">
            	<h:outputText id="lblDetalleAsiento" value="DETALLE DEL ASIENTO NRO. :"></h:outputText>
            </f:facet>
            <h:panelGrid  styleClass="tableCellBorder4" style="border:1px solid #17356f; padding:15px">
			    
                <rich:columnGroup>
						<rich:column>
							<h:outputText value="Resumen de Ingresos" style="font-weight:bold,text-align: center"></h:outputText>
						</rich:column>
				</rich:columnGroup>
                <rich:columnGroup>
						<rich:column >
							<h:outputText value="Ingresos a Caja" ></h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="  ???????????"></h:outputText>
						</rich:column>
						
                </rich:columnGroup>
                <rich:columnGroup>
						<rich:column>
							<h:outputText value="Deposito a Banco"></h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="  ???????????"></h:outputText>
						</rich:column>						
                </rich:columnGroup>
                <rich:columnGroup>
						<rich:column>
							<h:outputText value="Total"></h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="  ???????????"></h:outputText>
						</rich:column>						
                </rich:columnGroup>
             </h:panelGrid>
		</rich:panel>

		
	
	</h:panelGroup>
</h:form>