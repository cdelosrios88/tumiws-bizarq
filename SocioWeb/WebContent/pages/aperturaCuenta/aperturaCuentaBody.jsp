<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi      -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : Apertura Cuenta	      -->			
	<!-- Fecha     : 21/06/2012               -->

<rich:panel style="border:1px solid #17356f; width: 950px; background-color:#DEEBF5"
		rendered="#{aperturaCuentaController.blnShowDivFormAperturaCuenta}">
    	<h:panelGrid columns="6">
    		<rich:column width="120px">
    			<h:outputText value="Socio / Cliente"/>
    		</rich:column>
    		<rich:column>
    			<h:outputText value=":"/>
    		</rich:column>
    		<rich:column>
    			<h:inputText value="#{aperturaCuentaController.beanSocioComp.persona.intIdPersona} - #{aperturaCuentaController.beanSocioComp.persona.natural.strApellidoPaterno} #{aperturaCuentaController.beanSocioComp.persona.natural.strApellidoMaterno} #{aperturaCuentaController.beanSocioComp.persona.natural.strNombres}" size="70" disabled="true"/>
    		</rich:column>
    		<rich:column>
    			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPODOCUMENTO}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{aperturaCuentaController.beanSocioComp.persona.documento.intTipoIdentidadCod}"/>: 
    			<h:inputText value="#{aperturaCuentaController.beanSocioComp.persona.documento.strNumeroIdentidad}" size="15" disabled="true"/>
    		</rich:column>
    		<rich:column>
    			<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{aperturaCuentaController.beanSocioComp.cuenta.intParaCondicionCuentaCod}"/> - 
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}"
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{aperturaCuentaController.beanSocioComp.cuenta.intParaSubCondicionCuentaCod}"/>
    		</rich:column>
    		<rich:column>
    			<a4j:commandButton value="Ver Ficha" actionListener="#{socioController.obtenerSocioEstructura}"
  					styleClass="btnEstilos1" reRender="frmFichaSocio" oncomplete="Richfaces.showModalPanel('mpFichaSocio'),disableFichaSocio()">
       	 		</a4j:commandButton>
    		</rich:column>
    	</h:panelGrid>
    	
    	<h:panelGrid columns="3">
    		<rich:column width="120px">
    			<h:outputText value="Unidad Ejecutora"/>
    		</rich:column>
    		<rich:column>
    			<h:outputText value=":"/>
    		</rich:column>
    		<rich:column>
    			<tumih:outputText value="#{aperturaCuentaController.listSucursalSocio}" 
							itemValue="id.intIdSucursal" itemLabel="juridica.strSiglas" 
							property="#{aperturaCuentaController.intSucursalSocio}"/> - 
    			<tumih:outputText value="#{aperturaCuentaController.listEstructura}" 
							itemValue="id.intCodigo" itemLabel="juridica.strRazonSocial" 
							property="#{aperturaCuentaController.intCodigo}"/> - 
				<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_MODALIDADPLANILLA}" 
							itemValue="intIdDetalle" itemLabel="strDescripcion" 
							property="#{aperturaCuentaController.beanSocioEstructura.intModalidad}"/>
    		</rich:column>
    	</h:panelGrid>
    	
    	<h:panelGrid id="pgTipoCuenta" columns="6">
    		<rich:column width="120px"> 
    			<h:outputText value="Tipo de Cuenta"/>
    		</rich:column>
    		<rich:column> 
    			<h:outputText value=":"/>
    		</rich:column>
    		<rich:column>
    			<h:selectOneMenu value="#{aperturaCuentaController.beanCuenta.intParaTipoCuentaCod}"
    				valueChangeListener="#{aperturaCuentaController.reloadCboTipoConformacionCta}">
		            <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
    				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTASOCIO}" 
                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
                       propertySort="intOrden"/>
                    <a4j:support event="onchange" reRender="pgTipoCuenta"/>
    			</h:selectOneMenu>
    		</rich:column>
    		<rich:column visible="#{aperturaCuentaController.blnTipoCuentaNoSocio}">
    			<h:selectOneMenu value="#{aperturaCuentaController.beanCuenta.intParaTipoConformacionCod}"
    				valueChangeListener="#{aperturaCuentaController.reloadCboTipoSubCtaSocio}" >
                    <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                    <tumih:selectItems var="sel" value="#{aperturaCuentaController.listaTipoConformacionSocio}" 
		                itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
		                propertySort="intOrden"/>
		            <a4j:support event="onchange" reRender="pgTipoCuenta"/>
               	</h:selectOneMenu>
    		</rich:column>
    		<rich:column visible="#{aperturaCuentaController.blnTipoCuentaNoSocio}">
    			<h:selectOneMenu value="#{aperturaCuentaController.beanCuenta.intParaSubTipoCuentaCod}">
                    <f:selectItem itemLabel="------------------" itemValue="0"/>
                    <tumih:selectItems var="sel" value="#{aperturaCuentaController.listaTipoSubCuentaSocio}" 
		                itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
		                propertySort="intOrden"/>
               	</h:selectOneMenu>
    		</rich:column>
    		<rich:column visible="#{aperturaCuentaController.blnTipoCuentaNoSocio}">
    			<a4j:commandButton value="Aperturar Cuentas" actionListener="#{aperturaCuentaController.aperturarCuenta}" styleClass="btnEstilos1" disabled="#{aperturaCuentaController.blnDisabledBtnAperCta}" reRender="pTiposConcepto"/>
    		</rich:column>
    	</h:panelGrid>
    	
    	<h:panelGrid id="msgErrorsApertura">
    		<h:outputText value="#{aperturaCuentaController.msgTipoCuenta}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgTipoConformacion}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgSubTipoCuenta}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgTxtValorAportacion}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgTxtValorFondoSepelio}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgTxtValorFondoRetiro}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgTxtValorMantCuenta}" styleClass="msgError"/>
    		<h:outputText value="#{aperturaCuentaController.msgTxtIntegrante}" styleClass="msgError"/>
    	</h:panelGrid>
    	
    	<h:panelGrid id="pTiposConcepto">
	    	<rich:panel rendered="#{aperturaCuentaController.formTiposConceptoRendered && aperturaCuentaController.blnTipoCuentaNoSocio}" style="border-top:1px solid #17356f; border-bottom:none; border-left:none; border-right:none; width: 939px; background-color:#DEEBF5">
	    		<h:panelGrid columns="3">
	    			<rich:column width="130px">
	    				<h:outputText value="Aportes"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value=":"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneRadio value="#{aperturaCuentaController.intValorAportacion}">
	    					<tumih:selectItems var="sel" value="#{aperturaCuentaController.beanAdenda.listaAdendaCaptacionAportacion}" 
		                       itemValue="#{sel.captacion.id.intItem}" itemLabel="#{sel.captacion.bdValorConfiguracion==null? '%': 'S/.'} #{sel.captacion.bdValorConfiguracion==null? sel.captacion.bdPorcConfiguracion: sel.captacion.bdValorConfiguracion}"/>
	    				</h:selectOneRadio>
	    			</rich:column>
	    		</h:panelGrid>
	    		<h:panelGrid columns="3">
	    			<rich:column width="130px">
	    				<h:outputText value="Mnto. de Cuenta"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value=":"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneRadio value="#{aperturaCuentaController.intValorMantenimientoCta}">
	    					<tumih:selectItems var="sel" value="#{aperturaCuentaController.beanAdenda.listaAdendaCaptacionMantCuenta}" 
		                       itemValue="#{sel.captacion.id.intItem}" itemLabel="#{sel.captacion.bdValorConfiguracion==null? '%': 'S/.'} #{sel.captacion.bdValorConfiguracion==null? sel.captacion.bdPorcConfiguracion: sel.captacion.bdValorConfiguracion}"/>
	    				</h:selectOneRadio>
	    			</rich:column>
	    		</h:panelGrid>
	    		<h:panelGrid columns="3">
	    			<rich:column width="130px">
	    				<h:outputText value="Fondo de Sepelio"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value=":"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneRadio value="#{aperturaCuentaController.intValorFondoSepelio}">
	    					<tumih:selectItems var="sel" value="#{aperturaCuentaController.beanAdenda.listaAdendaCaptacionFondoSepelio}" 
		                       itemValue="#{sel.captacion.id.intItem}" itemLabel="#{sel.captacion.bdValorConfiguracion==null? '%': 'S/.'} #{sel.captacion.bdValorConfiguracion==null? sel.captacion.bdPorcConfiguracion: sel.captacion.bdValorConfiguracion}"/>
	    				</h:selectOneRadio>
	    			</rich:column>
	    		</h:panelGrid>
	    		<h:panelGrid columns="3">
	    			<rich:column width="130px">
	    				<h:outputText value="Fondo de Retiro"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:outputText value=":"/>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneRadio value="#{aperturaCuentaController.intValorFondoRetiro}">
	    					<tumih:selectItems var="sel" value="#{aperturaCuentaController.beanAdenda.listaAdendaCaptacionFondoRetiro}" 
		                       itemValue="#{sel.captacion.id.intItem}" itemLabel="#{sel.captacion.bdValorConfiguracion==null? '%': 'S/.'} #{sel.captacion.bdValorConfiguracion==null? sel.captacion.bdPorcConfiguracion: sel.captacion.bdValorConfiguracion}"/>
	    				</h:selectOneRadio>
	    			</rich:column>
	    		</h:panelGrid>
	    		
	    		<h:panelGrid columns="2">
	    			<rich:column width="140px"/>
	    			<rich:column>
	    				<rich:panel style="border:1px solid #17356f; width:850px; background-color:#DEEBF5">
	    					<h:panelGrid columns="3">
	    						<rich:column width="120px">
	    							<h:outputText value="Beneficiarios"/>
	    						</rich:column>
	    						<rich:column>
	    							<h:outputText value=":"/>
	    						</rich:column>
	    						<rich:column>
	    							<a4j:commandButton value="Agregar" oncomplete="#{rich:component('mpBeneficiario')}.show()"
					  					styleClass="btnEstilos" reRender="frmBeneficiario" actionListener="#{beneficiarioController.cancelarBeneficiario}">
					       	 		</a4j:commandButton>
	    						</rich:column>
	    					</h:panelGrid>
	    					
	    					<h:panelGrid id="pgListBeneficiario">
	    						<rich:dataTable id="dtBeneficiarios" var="item" rowKeyVar="rowKey" sortMode="single" width="830px" 
												value="#{beneficiarioController.listaBeneficiario}"
												rendered="#{not empty beneficiarioController.listaBeneficiario}">
									<f:facet name="header">
										<rich:columnGroup>
											<rich:column/>
											<rich:column width="250px">
												<h:outputText value="Nombre Completo"/>
											</rich:column>
											<rich:column width="150px">
												<h:outputText value="Número Documento"/>
											</rich:column>
											<rich:column width="140px">
												<h:outputText value="Tipo de Vínculo"/>
											</rich:column>
											<rich:column width="140px">
												<h:outputText value="Tipo de Concepto"/>
											</rich:column>
											<rich:column width="140px">
												<h:outputText value="Valor de Concepto"/>
											</rich:column>
											<rich:column/>
											<rich:column/>
										</rich:columnGroup>
									</f:facet>
									<rich:columnGroup rendered="#{item.paraEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
										<rich:column width="15">
											<div align="center"><h:outputText value="#{rowKey+1}"/></div>
										</rich:column>
										<rich:column>
											<h:outputText value="#{item.persona.natural.strNombres} #{item.persona.natural.strApellidoPaterno} #{item.persona.natural.strApellidoMaterno}"/>
										</rich:column>
										<rich:column>
											<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"/>
										</rich:column>
										<rich:column>
											<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
	                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                             property="#{item.persona.personaEmpresa.vinculo.intTipoVinculoCod}"/>
										</rich:column>
										<rich:column>
											<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOCUENTA}" 
	                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                             property="#{item.intParaTipoConceptoCod}"/>
										</rich:column>
										<rich:column>
											<div align="right"><h:outputText value="#{item.bdPorcentaje}"/></div>
										</rich:column>
										<rich:column>
											<a4j:commandLink id="lnkAdjuntaArchivoSocio" styleClass="no-decor" reRender="frmAdjuntoPersona"
												action="#{beneficiarioController.getIdPersona}" oncomplete="#{rich:component('mpAdjuntoPersona')}.show()">
							                    <h:graphicImage value="/images/icons/page_detail_20.png" alt="view"/>
							                    <rich:toolTip for="lnkAdjuntaArchivoSocio" value="Adjuntar Firma y Foto del Socio" followMouse="true" styleClass="tooltip" showDelay="500"/>
							                    <f:param name="intIdPersona" value="#{item.persona.intIdPersona}" />
							                </a4j:commandLink>
										</rich:column>
										<%--<rich:column>
							            	<a4j:commandLink id="lnkEditBeneficiario" styleClass="no-decor" reRender="frmBeneficiario" 
							            		actionListener="#{beneficiarioController.irModificarBeneficiario}"
							            		rendered="#{item.id.intItemBeneficio!=null}"
							                    oncomplete="#{rich:component('mpBeneficiario')}.show(),disableFormBeneficiario()">
							                    <h:graphicImage value="/images/icons/page_detail_20.png" alt="view"/>
							                    	<f:param value="#{item.id.intPersEmpresaPk}"		name="pIntPersEmpresaPk"/>
							                    	<f:param value="#{item.id.intCuentaPk}"				name="pIntCuenta"/>
							                    	<f:param value="#{item.id.intItemCuentaConcepto}"	name="pIntItemCuentaConcepto"/>
							                    	<f:param value="#{item.id.intItemBeneficio}"		name="pIntItemBeneficio"/>
							                    	<f:param value="#{rowKey}" name="rowKeyBeneficiario"/>
							                    <rich:toolTip for="lnkEditBeneficiario" value="Ver" followMouse="true"/>
							                </a4j:commandLink>
							            </rich:column>--%>
							            <rich:column>
							            	<a4j:commandLink id="lnkDeleteBeneficiario" styleClass="no-decor" reRender="pgListBeneficiario"
							            		actionListener="#{beneficiarioController.removeBeneficiario}"
							            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
							                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
							                    <f:param value="#{rowKey}"  		name="rowKeyBeneficiario"/>
							            		<rich:toolTip for="lnkDeleteBeneficiario" value="Eliminar" followMouse="true"/>
							            	</a4j:commandLink>
							            </rich:column>
									</rich:columnGroup>
					            </rich:dataTable>
	    					</h:panelGrid>
	    				</rich:panel>
	    			</rich:column>
	    		</h:panelGrid>
	    	</rich:panel>
    	</h:panelGrid>
</rich:panel>