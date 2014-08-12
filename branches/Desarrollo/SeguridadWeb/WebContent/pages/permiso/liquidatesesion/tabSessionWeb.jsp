<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
<!-- Empresa   : Cooperativa Tumi         	-->
<!-- Autor     : Joao Rivera - BIZARQ		-->
<!-- Modulo    : Seguridad         			-->
<!-- Prototipo : Liquidación de Sesiones	-->
<!-- Fecha     : 11/08/2014       			-->

<rich:panel style="border: 0px solid #17356f;"
	styleClass="rich-tabcell-noborder">

	<h:panelGrid columns="6">
		<rich:column style="width: 110px">
			<h:outputText value="Empresa : " />
		</rich:column>
		<rich:column width="200px">
			<h:selectOneMenu id="cboEmpresa" style="width: 150px;"
				value="#{liquidateSessionController.objLiqSess.intPersEmpresa}">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{liquidateSessionController.listaEmpresas}"
					itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strSiglas}" />
				<a4j:support event="onchange" reRender="idCboSucursalEmp" actionListener= "#{liquidateSessionController.reloadCboSucursales}"
					ajaxSingle="true" />
			</h:selectOneMenu>
		</rich:column>
		<rich:column style="width: 110px">
			<h:outputText value="Usuario : " />
		</rich:column>
		<rich:column width="200px">
			<h:inputText style="width: 150px;"
				value="#{liquidateSessionController.objLiqSess.strUsuario}" />
		</rich:column>

		<rich:column style="width: 110px">
			<h:outputText value="Estado : " />
		</rich:column>
		<rich:column width="200px">
			<h:selectOneMenu id="cboEstado" style="width: 150px;"
				value="#{liquidateSessionController.objLiqSess.intEstado}">
				<tumih:selectItems var="sel"
					value="#{liquidateSessionController.listaEstados}"
					itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" />
			</h:selectOneMenu>
		</rich:column>
	</h:panelGrid>
	<rich:spacer height="10px" />
	<h:panelGrid columns="6">
		<rich:column style="width: 97px">
			<h:outputText value="Sucursal : "  />
		</rich:column>
		<rich:column width="190px">
			<h:selectOneMenu id="idCboSucursalEmp" style="width: 150px;"
				value="#{liquidateSessionController.objLiqSess.intCboSucursalEmp}">
				<f:selectItem itemLabel="Seleccionar.." itemValue="0" />
				<tumih:selectItems var="sel"
					value="#{liquidateSessionController.listaJuridicaSucursal}"
					itemValue="#{sel.id.intIdSucursal}"
					itemLabel="#{sel.juridica.strRazonSocial}" />
			</h:selectOneMenu>
		</rich:column>
		<rich:column  style="width: 110px">
       			<h:outputText value="Rango de Fecha : "/>
       		</rich:column>
       		<rich:column>
			<rich:calendar datePattern="dd/MM/yyyy"  value="#{liquidateSessionController.objLiqSess.fechaInicioFiltro}"						
				jointPoint="down-right" direction="right" inputSize="17" showApplyButton="true"/> 
              </rich:column>
          	<rich:column style="width: 205px">
       			<rich:calendar datePattern="dd/MM/yyyy"  value="#{liquidateSessionController.objLiqSess.fechaFinFiltro}"
				jointPoint="down-right" direction="right" inputSize="17" showApplyButton="true"/> 
       		</rich:column>
       		<rich:column>
         		<a4j:commandButton styleClass="btnEstilos" value="Buscar"
                   	action="#{liquidateSessionController.buscarSesionWeb}" reRender="panelSesionWeb"/>
         	</rich:column>  
	</h:panelGrid>

	<rich:spacer height="12px" />

	<h:panelGrid id="panelSesionWeb">
		<rich:dataTable id="tblAccesosSW" sortMode="single" var="item" 
                    value="#{liquidateSessionController.listaSesionWeb}"  
					rowKeyVar="rowKey"  rows="5" width="860px"  align="center">
                    
                    <f:facet name="header">
	                   	<rich:columnGroup>
	                   		<rich:column width="15"/>
                 			<rich:column width="100px">
                 				<h:outputText value="Codigo"/>
                 			</rich:column>
                 			<rich:column width="200px">
                 				<h:outputText value="Nombre de Usuario"/>
                 			</rich:column>
                 			<rich:column>
                 				<h:outputText value="Estado Sesion"/>
                 			</rich:column>
                 			<rich:column width="150px">
                 				<h:outputText value="Empresa"/>
                 			</rich:column>
                 			<rich:column width="120px">
                 				<h:outputText value="Sucursal"/>
                 			</rich:column>
                 			<rich:column width="120px">
                 				<h:outputText value="Fecha Inicio"/>
                 			</rich:column>
                 			<rich:column width="120px">
                 				<h:outputText value="Fecha Fin"/>
                 			</rich:column>
                 			<rich:column width="150px">
                 				<h:outputText value="MAC Address"/>
                 			</rich:column>
	                 	</rich:columnGroup>
	                </f:facet>
					<rich:column width="15px" style="text-align:center;">
                    	<h:outputText value="#{rowKey + 1}"></h:outputText>                        	
                    </rich:column>
                    <rich:column >
                      	<h:outputText value="#{item.intPersonaPk}"/>
                	</rich:column>
                    <rich:column>
                      	<h:outputText value="#{item.strFullName}"/>
                	</rich:column>
                  	<rich:column style="text-align:center;">
                      	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="intIdDetalle"
							itemLabel="strDescripcion"
							property="#{item.session.intIdEstado}"/>
                    </rich:column>
                    <rich:column >
                      	<h:outputText value="#{item.strEmpresa}"/>
                  	</rich:column>
                    <rich:column>
                        <h:outputText value="#{item.strSucursal}"/>
                  	</rich:column>
                  	<rich:column>
                        <h:outputText value="#{item.session.tsFechaRegistro}">
                        	<f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                  	</rich:column>
                    <rich:column>
                    	<h:outputText value="#{item.session.tsFechaTermino}">
                    		<f:convertDateTime pattern="dd/MM/yyyy" />
                    	</h:outputText>
                  	</rich:column>
                  	<rich:column>
                     	<h:outputText value="#{item.session.strMacAddress}"/>
                  	</rich:column>
                    <f:facet name="footer">   
							<rich:datascroller for="tblAccesosSW" maxPages="20"/>   
					</f:facet>
    	</rich:dataTable>
	</h:panelGrid>


	<h:panelGrid columns="2">
						   	<h:outputLink value="#" id="linkPanelEmpr">
						        <h:graphicImage value="/images/icons/mensaje1.jpg"
									style="border:0px"/>
						        <rich:componentControl for="panelInactiveSess" attachTo="linkPanelEmpr" operation="show" event="onclick"/>
						    </h:outputLink>
							<h:outputText value="Para desactivar sesión. Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>                                     
						</h:panelGrid>

</rich:panel>
