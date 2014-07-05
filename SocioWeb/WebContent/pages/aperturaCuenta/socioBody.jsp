<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->
	
    <h:panelGroup layout="block" style="padding:15px">
   <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;">
   
		<div align="center">
			<b>REGISTRO DE SOCIO / CLIENTE</b>
		</div>
	</rich:panel>
    <h:panelGrid id="pgBusq1" columns="6" border="0">
			<rich:column width="120px">
				<h:outputText value="Datos de Socio" styleClass="estiloLetra1" />
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{socioController.intBusquedaTipo}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_BUSQSOLICPTMO}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
			</rich:column>
			<rich:column>
				<h:inputText size="67" value="#{socioController.strBusqCadena}"/>
			</rich:column>
			<rich:column>
				<h:outputText value="Situación:" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:selectOneMenu value="#{socioController.intBusqSituacionCta}" style="width:150px"> 
					<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
					<tumih:selectItems var="sel"
						cache="#{applicationScope.Constante.PARAM_T_SITUACIONCUENTA}"
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						propertySort="intOrden"/>
				</h:selectOneMenu>
				
			</rich:column>
		</h:panelGrid>
    	
    	<h:panelGrid id="pgBusq2" columns="7">
    		<rich:columnGroup>
    		<rich:column  width="120px">
				<h:outputText value="Fecha Inscripción" styleClass="estiloLetra1"/>
			</rich:column>
			<rich:column>
				<h:outputText value=":" styleClass="estiloLetra1"/>
			</rich:column>
    		<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{socioController.dtBusqFechaRegDesde}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			<rich:column>
				<rich:calendar popup="true" enableManualInput="true" value="#{socioController.dtBusqFechaRegHasta}"
					datePattern="dd/MM/yyyy" inputStyle="width:70px;" />
			</rich:column>
			
			<rich:column width="120px">
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Buscar" actionListener="#{socioController.buscarSocioFiltros}" styleClass="btnEstilos" 
					reRender="divTablaSocioBusq"/>
			</rich:column>
			<rich:column>
				<a4j:commandButton value="Limpiar" actionListener="#{socioController.limpiarFiltros}" styleClass="btnEstilos" 
					reRender="pgBusq1,pgBusq2,divTablaSocioBusq"/>
			</rich:column>
			
    		</rich:columnGroup>
    	</h:panelGrid>

    	<rich:spacer height="10px"/>
     	
    	<h:panelGrid id="divTablaSocioBusq">
			<rich:extendedDataTable id="tblSocioNatu" sortMode="single"
				enableContextMenu="false" var="item" 
				value="#{socioController.listaSocioBusqueda}"
				rowKeyVar="rowKey" rows="5" width="970px" height="165px"
				align="center" noDataLabel="No existen registros">
				
	  		 	<rich:column width="15px">
                    <h:outputText value="#{rowKey + 1}" />
                </rich:column>

                <rich:column width="50">
                    <f:facet name="header">
                        <h:outputText  value="Código" />
                    </f:facet>
                    <h:outputText value="#{item.socio.id.intIdPersona}" title="#{item.socio.id.intIdPersona}"></h:outputText>
                </rich:column>

                <rich:column width="135">
                    <f:facet name="header">
                        <h:outputText value="Nombre Persona" />
                    </f:facet>
                    <h:outputText value="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"
                    				title="#{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}, #{item.socio.strNombreSoc}"></h:outputText>
                </rich:column>

                <rich:column width="70">
                   	<f:facet name="header">
                       <h:outputText value="DNI" />
                   	</f:facet>
                   	<h:outputText value="#{item.socio.strNroDocumento}" title="#{item.socio.strNroDocumento}"></h:outputText>
				</rich:column>
				
				<rich:column width="75">
                   <f:facet name="header">
                       <h:outputText value="Socio" />
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.socio.intTipoSocio}"/>
                </rich:column>
                
                <rich:column width="85">
                   <f:facet name="header">
                       <h:outputText value="Condición" />
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.socio.intCondicionLab}"/>
                </rich:column>
                
                <rich:column width="85">
                   <f:facet name="header">
                       <h:outputText value="Modalidad" />
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.socio.intParaModalidad}"/>
                </rich:column>
                
                <rich:column width="90">
                   <f:facet name="header">
                       <h:outputText value="Sucursal" />
                   </f:facet>
                   <tumih:outputText value="#{socioEstrucController.listSucursal}" 
							itemValue="id.intIdSucursal" itemLabel="juridica.strSiglas" 
							property="#{item.socio.intSucuAdministra}"/>
                </rich:column>
                
                <rich:column width="90">
                   <f:facet name="header">
                       <h:outputText value="Tipo Cuenta" />
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTASOCIO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.socio.intParaTipoCta}"/>
                </rich:column>
                
                <rich:column width="90">
                   <f:facet name="header">
                       <h:outputText value="Nro. Cuenta" />
                   </f:facet>
                   <h:outputText value="#{item.socio.strNumeroCta}" title="#{item.socio.strNumeroCta}" />
				</rich:column>
				
				<rich:column width="85">
                   <f:facet name="header">
                       <h:outputText value="Situación" />
                   </f:facet>
                   <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SITUACIONCUENTA}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{item.socio.intParaSituacionCta}"/>
				</rich:column>
				
				<rich:column width="90">
                   <f:facet name="header">
                       <h:outputText value="F. Registo" />
                   </f:facet>
                   <h:outputText value="#{item.socio.strFechaRegistroCta}" title="#{item.socio.strFechaRegistroCta}">
					</h:outputText>
					</rich:column>
				
				<f:facet name="footer">
					<rich:datascroller for="tblSocioNatu" maxPages="10" />
				</f:facet>
				<a4j:support event="onRowClick"
					actionListener="#{socioController.selectSocioBusqueda}"
					oncomplete="Richfaces.showModalPanel('mpUpDelSocioNatu')"
					reRender = "formUpDelEstrucOrg">
					<f:attribute name="itemSocioBusq" value="#{item}" />
				</a4j:support>                 
        	</rich:extendedDataTable>
	    </h:panelGrid>

			<h:panelGrid columns="1"
				style="margin-left: auto; margin-right: auto">
				<h:outputText
					value="Para Modificar o Eliminar hacer clic en el registro"
					style="color:#8ca0bd" />
			</h:panelGrid>
    	
    	<a4j:include viewId="/pages/mensajes/errorInfoMessages.jsp"/>
    	
       	<h:panelGrid id="divPanelControlSocio" rendered="#{socioController.blnShowDivPanelControlsSocio}" columns="3"> 
	       	<a4j:commandButton value="Nuevo" actionListener="#{socioController.showNewSocioNatu}" styleClass="btnEstilos"  
	        				   reRender="divFormSocio,divFormAperturaCuenta"/>
	        <a4j:commandButton value="Guardar" actionListener="#{socioController.grabarSocioNatural}" styleClass="btnEstilos"  
	        				   oncomplete="if(#{socioController.isValidSocioNatural==false}){Richfaces.showModalPanel('mpMessage')}"
	        				   reRender="divFormSocio,divFormAperturaCuenta"/>
	    	<a4j:commandButton value="Cancelar" actionListener="#{socioController.hideFormSocioNatu}" styleClass="btnEstilos"
	    					   reRender="divFormSocio,divFormAperturaCuenta">
	    		<rich:componentControl for="mpRepLegal" operation="hide" event="onclick"/>
	    	</a4j:commandButton>
	    </h:panelGrid>
	    
       	<h:panelGrid id="divPanelControlAperturaCuenta" rendered="#{aperturaCuentaController.blnShowDivPanelControlsAperturaCuenta}" columns="3">
       		<a4j:commandButton value="Nuevo" actionListener="#{aperturaCuentaController.showNewAperturaCuenta}" styleClass="btnEstilos"  
	        				   reRender="divFormSocio,divFormAperturaCuenta"/> 
	       	<h:panelGroup rendered="#{aperturaCuentaController.strAperturaCuenta == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
                <a4j:commandButton value="Guardar" actionListener="#{aperturaCuentaController.grabarCuenta}" styleClass="btnEstilos"  
	        				   	reRender="divFormAperturaCuenta,msgErrorsApertura"/>
            </h:panelGroup>
            <h:panelGroup rendered="#{aperturaCuentaController.strAperturaCuenta == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
            	<a4j:commandButton value="Guardar" actionListener="#{aperturaCuentaController.modificarCuenta}" styleClass="btnEstilos" 
            					reRender="divFormAperturaCuenta,msgErrorsApertura"/>
            </h:panelGroup>
	        
	    	<a4j:commandButton value="Cancelar" actionListener="#{aperturaCuentaController.hideFormAperturaCuenta}" styleClass="btnEstilos"
	    					   reRender="divFormAperturaCuenta,divFormSocio">
	    		<rich:componentControl for="mpRepLegal" operation="hide" event="onclick"/>
	    	</a4j:commandButton>
	    </h:panelGrid>
		<br/>
		
		  <h:panelGroup layout="block" id="divFormSocio"> 
            <rich:panel style="border:1px solid #17356f; width: 966px; background-color:#DEEBF5"
            			rendered="#{socioController.blnShowDivFormSocio}">
             		
             	<h:panelGroup layout="block" rendered="#{socioController.blnShowFiltroPersonaNatu}">
             		<h:panelGrid columns="7">
                    	<rich:column width="100">
                    		<h:outputText value="Tipo de Persona: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="120">
                     		<h:selectOneMenu value="#{socioController.personaBusqueda.intTipoPersonaCod}"
                     						onchange="selecTipoPersona(#{applicationScope.Constante.ONCHANGE_VALUE})">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
							  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
							  		propertySort="intOrden"/>
                    		</h:selectOneMenu>
                    	</rich:column>
                    	<rich:column width="144">
                    		<h:outputText value="Número de Documento: "></h:outputText>
                    	</rich:column>
                    	<rich:column width="120">
                     		<h:selectOneMenu id="cboTipoIdentidad" style="width:110px" value="#{socioController.personaBusqueda.documento.intTipoIdentidadCod}">
                    			<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
							  	<tumih:selectItems var="sel" value="#{socioController.listDocumentoBusq}" 
								  		itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								  		propertySort="intOrden"/>
                    		</h:selectOneMenu>  
                    	</rich:column>
                    	<rich:column>
                    		<h:inputText size="15" value="#{socioController.personaBusqueda.documento.strNumeroIdentidad}"></h:inputText>
                    	</rich:column>
                     </h:panelGrid>
                     <rich:spacer height="10"></rich:spacer>
                     <h:panelGrid style="width: 100%">
                     	<a4j:commandButton value="Validar Datos" styleClass="btnEstilos1" style="width: 100%" 
                     					   actionListener="#{socioController.validarSocioNatural}" reRender="divFormSocio,mpConfirmMessage,mpMessage"
                     					   oncomplete="if(#{socioController.beanSocioComp==null || socioController.beanSocioComp.padron!=null}){Richfaces.showModalPanel('mpConfirmMessage')}
                     					   				else if(#{socioController.beanSocioComp.persona.intIdPersona==null}){Richfaces.showModalPanel('mpMessage')}"/>
                     </h:panelGrid>
                     <rich:spacer height="10"></rich:spacer>
             	</h:panelGroup>
             	
                <a4j:include id="divFormSocioNatu" viewId="/pages/aperturaCuenta/socioNaturalBody.jsp" layout="block"
                 				rendered="#{socioController.blnShowDivFormSocioNatu}"/>
                 				
                <h:panelGrid>
					<h:outputText value="#{socioController.msgTxtDsctoJudicialYPerfil}" styleClass="msgError"/>
					<h:outputText value="#{socioController.msgTxtRolPersona}" styleClass="msgError"/>
					<h:outputText value="#{socioController.msgTxtPermisoSucursal}" styleClass="msgError"/>
				</h:panelGrid>
         </rich:panel>
      </h:panelGroup>
      
	  <h:panelGrid id="divFormAperturaCuenta">
	    	<a4j:include id="inclApertCta" viewId="/pages/aperturaCuenta/aperturaCuentaBody.jsp" />
	  </h:panelGrid>
      
	</h:panelGroup>