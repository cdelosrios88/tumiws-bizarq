<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : AES 		  Edit        -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 02/04/2012               -->

		<rich:panel rendered="#{fondoSepelioController.formFondoSepelioRendered}" style="width: 960px;border:1px solid #17356f;background-color:#DEEBF5;">
          	 	<h:panelGrid columns="5">
          	 		<rich:column width="140"><h:outputText value="Nombre del Sepelio" /></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
	          	 		<h:inputText value="#{fondoSepelioController.beanCaptacion.strDescripcion}" size="80"/>
                    </rich:column>
                    <rich:column width="120"><h:outputText value="Estado de la solicitud:" /></rich:column>
                    <rich:column>
	          	 		<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaEstadoSolicitudCod}" disabled="true">
	          	 			<f:selectItem itemLabel="Pendiente" itemValue="0"/>
          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
                    </rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{fondoSepelioController.msgTxtDescripcion}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid id="pgRanFec" columns="5">
          	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<rich:calendar enableManualInput="true"
	                   	value="#{fondoSepelioController.daFechaIni}"
	                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                    cellWidth="10px" cellHeight="20px"/>
          	 		</rich:column>
          	 		<rich:column>
	          	 		<h:selectOneRadio value="#{fondoSepelioController.rbFecIndeterm}">
	          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
	          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
	          	 			<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgRanFec"/>
	          	 		</h:selectOneRadio>
                    </rich:column>
                    <rich:column>
          	 			<rich:calendar enableManualInput="true"
          	 			rendered="#{fondoSepelioController.fecFinAportacionRendered}"
	                   	value="#{fondoSepelioController.daFechaFin}"
	                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
	                    cellWidth="10px" cellHeight="20px"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	<h:outputText value="#{fondoSepelioController.msgTxtFechaIni}" styleClass="msgError"/>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Estado del Sepelio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:panelGroup rendered="#{fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
	          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaEstadoCod}" disabled="true">
	          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>
						<h:panelGroup rendered="#{fondoSepelioController.strAportacion == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
	          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaEstadoCod}" disabled="false">
	          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</h:panelGroup>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoSepelioController.msgTxtEstadoAporte}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="6">
          	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaTipopersonaCod}">
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								tipoVista="#{applicationScope.Constante.CACHE_TOTAL}" 
								propertySort="intOrden"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column><h:outputText value="Tipo de relación: "/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaRolPk}">
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOROLAFECTO}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
								propertySort="intOrden"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoSepelioController.msgTxtTipoPersona}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{fondoSepelioController.rbCondicion}">
          	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
          	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.listarCondicion}" reRender="dtCondSocio"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoSepelioController.msgTxtCondicion}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	<h:panelGrid id="dtCondSocio">
         	 			<rich:dataTable value="#{fondoSepelioController.listaCondicionComp}"
	         			rendered="#{not empty fondoSepelioController.listaCondicionComp}"
	                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
	                	<rich:column width="15px;">
			                <h:selectBooleanCheckbox value="#{item.chkSocio}"/>
			            </rich:column>
			            <rich:column width="500">
			            	<h:outputText value="#{item.tabla.strDescripcion}"/>
			            </rich:column>
	                </rich:dataTable>
         	 		</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Tipo de Descuento"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaTipoDescuentoCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODSCTOAPORT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoSepelioController.msgTxtTipoDscto}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid columns="4">
          	 		<rich:column width="140"><h:outputText value="Tipo de Configuración"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaTipoConfiguracionCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOBROAPORT}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoSepelioController.msgTxtTipoConfig}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgValAportacion" columns="8">
          	 		<rich:column width="140"><h:outputText value="Valor de Aportación"/></rich:column>
          	 		<rich:column><h:outputText value=":"/></rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaMonedaCod}">
          	 				<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
								propertySort="intOrden"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneRadio value="#{fondoSepelioController.strValAporte}">
          	 				<f:selectItem itemLabel="Importe" itemValue="1"/>
          	 				<f:selectItem itemLabel="%" itemValue="2"/>
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgValAportacion"/>
          	 			</h:selectOneRadio>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoSepelioController.beanCaptacion.bdValorConfiguracion}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" disabled="#{fondoSepelioController.enabDisabValImporte}" size="10"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoSepelioController.beanCaptacion.bdPorcConfiguracion}" onblur="extractNumber(this,4,false);" onkeyup="extractNumber(this,4,false);" disabled="#{fondoSepelioController.enabDisabValPorcentaje}" size="10"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaAplicacionCod}">
          	 				<f:selectItem itemLabel="RMV" itemValue="1"/>
          	 			</h:selectOneMenu>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="#{fondoSepelioController.msgTxtMoneda}" styleClass="msgError"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgLimiteEdad" columns="5">
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkLimiteEdad}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgLimiteEdad"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="115px">
          	 			<h:outputText value="Limite de Edad"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoSepelioController.beanCaptacion.intEdadLimite}" onkeypress="return soloNumeros(event);" size="5" maxlength="2" disabled="#{fondoSepelioController.enabDisabLimiteEdad}"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="años"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgRequisitosBenef" columns="3">
          	 		<rich:column width="140">
          	 			<h:outputText value="Requisitos de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:panelGrid id="pgTiempoPresent" columns="4">
          	 				<rich:column>
          	 					<h:selectBooleanCheckbox value="#{fondoSepelioController.chkTiempoPresentSustento}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgTiempoPresent"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="220">
		          	 			<h:outputText value="Tiempo de presentación de sustento"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intTiempoSustento}" disabled="#{fondoSepelioController.enabDisabTiempoPresentSust}">
		                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIEMPODOCUMENTOS}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu> año(s)
          	 				</rich:column>
          	 				<rich:column>
		          	 			<h:selectOneMenu value="#{fondoSepelioController.beanCaptacion.intParaTipoMaxMinSustCod}" disabled="#{fondoSepelioController.enabDisabTiempoPresentSust}">
		                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESCALATIEMPO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu>
          	 				</rich:column>
          	 			</h:panelGrid>
          	 			
          	 			<h:panelGrid columns="2">
          	 				<rich:column>
          	 					<h:selectBooleanCheckbox value="#{fondoSepelioController.chkEscala}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgTipoBeneficiario,pgCuotasCanc,pgCuotasCancDet"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column>
		       	 				<h:outputText value="Escala"/>
		       	 			</rich:column>
          	 			</h:panelGrid>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgTipoBeneficiario" columns="2">
          	 		<rich:column width="140"/>
          	 		<rich:panel style="border:2px solid #17356f;border-bottom:none;background-color:#DEEBF5;width:725px;">
	          	 		<h:panelGrid columns="3">
		          	 		<rich:column>
		          	 			<h:outputText value="Tipo de Beneficiario"/>
		          	 		</rich:column>
		          	 		<!--<rich:column>
		          	 			<h:selectOneMenu value="#{fondoSepelioController.intTipoBeneficiario}" disabled="#{fondoSepelioController.enabDisabEscala}">
		          	 				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu>
		          	 		</rich:column>-->
				          	<rich:column>
								<h:selectOneMenu style="width:120px" value="#{fondoSepelioController.intTipoBeneficiario}" disabled="#{fondoSepelioController.enabDisabEscala}"> 
									<f:selectItem itemValue="0" itemLabel="Seleccione..."/>
									<tumih:selectItems var="sel" value="#{fondoSepelioController.listaTipoBeneficiario}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="#{fondoSepelioController.msgTxtTipoBeneficiario}" styleClass="msgError"/>
		          	 		</rich:column>
	          	 		</h:panelGrid>
          	 		</rich:panel>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCuotasCanc" columns="2">
          	 		<rich:column width="140"/>
          	 		<rich:panel style="border:2px solid #17356f;background-color:#DEEBF5;width:725px;">
	          	 		<h:panelGrid id="pgCuotasCancDet" columns="8">
	          	 			<rich:column>
	          	 				<h:outputText value="Cuotas canceladas"/>
	          	 			</rich:column>
		          	 		<rich:column>
		                        <h:selectOneMenu value="#{fondoSepelioController.intRangoCuota}" disabled="#{fondoSepelioController.enabDisabEscala}">
		                            <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
		                            <tumih:selectItems var="condi" cache="#{applicationScope.Constante.PARAM_T_TIPO_RANGOCUOTAS}"
		                                               itemValue="#{condi.intIdDetalle}" itemLabel="#{condi.strDescripcion}"/>
		                        </h:selectOneMenu>
	                        </rich:column>
	                        <rich:column>
	                            <h:inputText value="#{fondoSepelioController.intCuotaCancelada}" disabled="#{fondoSepelioController.enabDisabEscala}" onkeypress="return soloNumeros(event);" size="5"/>
	                        </rich:column>
	                        <rich:column>
	                            <h:outputText value="Beneficio" />
	                        </rich:column>
	                        <rich:column>
	                            <h:inputText value="#{fondoSepelioController.bdBeneficio}" disabled="#{fondoSepelioController.enabDisabEscala}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="5" maxlength="12"/>
	                        </rich:column>
	                        <rich:column>
	                            <h:outputText value="Gasto Adm."/>
	                        </rich:column>
	                        <rich:column>
	                            <h:inputText value="#{fondoSepelioController.bdGastoAdm}" disabled="#{fondoSepelioController.enabDisabEscala}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="5" maxlength="12"/>%
	                        </rich:column>
		                    <rich:column>
		                        <a4j:commandButton value="Agregar" disabled="#{fondoSepelioController.enabDisabEscala}" actionListener="#{fondoSepelioController.addReqVinculo}" styleClass="btnEstilos" reRender="pgTipoBeneficiario,pgCuotasCanc,pgReqVinculo"/>
		                    </rich:column>
	                     </h:panelGrid>
                     </rich:panel>
          	 	</h:panelGrid>
          	 	<br/>
          	 	
          	 	<h:panelGrid id="pgReqVinculo" columns="2">
          	 		<rich:column width="140"/>
          	 		<rich:dataTable value="#{fondoSepelioController.listaRequisitos}"
	         			 rendered="#{not empty fondoSepelioController.listaRequisitos}"
	                     var="item" rowKeyVar="rowKey" sortMode="single" width="500px">
	                	 <f:facet name="header">
		                	 <rich:columnGroup>
		                	 	<rich:column><rich:spacer /></rich:column>
		                	 	<rich:column>
		                	 		<h:outputText value="Tipo de Beneficiario"/>
		                	 	</rich:column>
		                	 	<rich:column>
		                	 		<h:outputText value="Tipo de cuota"/>
		                	 	</rich:column>
		                	 	<rich:column>
		                	 		<h:outputText value="Cuotas canceladas"/>
		                	 	</rich:column>
		                	 	<rich:column>
		                	 		<h:outputText value="Beneficio"/>
		                	 	</rich:column>
		                	 	<rich:column>
		                	 		<h:outputText value="Gasto Administrativo"/>
		                	 	</rich:column>
		                	 	<rich:column><rich:spacer /></rich:column>
		                	 </rich:columnGroup>
	                	 </f:facet>
	                	 <rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
		                	 <rich:column width="25px">
		                	 	<h:outputText value="#{rowKey+1}"/>
		                	 </rich:column>
				             <rich:column width="120px;">
				             	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPOVINCULO}" 
	                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                property="#{item.id.intParaTipoRequisitoBenef}"/>
				             </rich:column>
				             <rich:column width="110px;">
				             	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_RANGOCUOTAS}" 
	                                itemValue="intIdDetalle" itemLabel="strDescripcion" 
	                                property="#{item.intParaTipoMaxMinCod}"/>
				             </rich:column>
				             <rich:column>
				            	 <div align="center"><h:outputText value="#{item.intNumeroCuota}"/></div>
				             </rich:column>
				             <rich:column width="80px;">
				            	 <div align="center"><h:outputText value="#{item.bdBeneficio}"/></div>
				             </rich:column>
				             <rich:column width="120px;">
				            	 <div align="center"><h:outputText value="#{item.bdGastoAdministrativo}"/></div>
				             </rich:column>
				             <rich:column>
	                       		<a4j:commandLink id="lnkQuitarRequisito" actionListener="#{fondoSepelioController.removeReqVinculo}" reRender="pgReqVinculo">
	         						<f:param name="rowKeyRequisito" value="#{rowKey}"></f:param>
	         						<h:graphicImage value="/images/icons/delete.png" alt="delete"/>
	         						<rich:toolTip for="lnkQuitarRequisito" value="Quitar" followMouse="true"/>
	         					</a4j:commandLink>
		                  	 </rich:column>
	                  	</rich:columnGroup>
	                </rich:dataTable>
          	 	</h:panelGrid>
          	 	<br/>
          	 	
          	 	<h:panelGrid id="pgCeseBenef" columns="5">
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCeseBeneficioMeses}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgCeseBenef"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="115px">
          	 			<h:outputText value="Cese del Beneficio"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value="Meses de no descuento consecutivo"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:inputText value="#{fondoSepelioController.beanCaptacion.intCeseBeneficio}" disabled="#{fondoSepelioController.enabDisabCeseBenefMeses}" onkeypress="return soloNumeros(event);" size="5" maxlength="2"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgModelosCont" columns="3">
          	 		<rich:column width="140">
          	 			<h:outputText value="Modelos Contables"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:outputText value=":"/>
          	 		</rich:column>
          	 		<rich:column>
          	 			<h:commandButton value="Solicitar" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgProvision" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkProvision}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgProvision"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Provisión"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabProvision}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgExtProvision" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkExtProvision}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgExtProvision"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Extorno de Provisión"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabExtProvision}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgRegularicCuotas" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkRegularicCuota}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgRegularicCuotas"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Regularización de cuotas"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabRegularicCuota}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCeseBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCeseBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgCeseBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Cese del Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCeseBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgSolicBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkSolicitudBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgSolicBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Solicitud de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabSolicitudBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgAprobBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkAprobacBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgAprobBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Aprobación de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabAprobacBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgAnulacRecBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkAnulRechazoBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgAnulacRecBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Anulación o Rechazo de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabAnulRechazoBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgGiroBeneficio" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkGiroBeneficio}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgGiroBeneficio"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Giro de Beneficio"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabGiroBenef}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
          	 	
          	 	<h:panelGrid id="pgCancelacion" columns="4">
          	 		<rich:column width="150"></rich:column>
          	 		<rich:column>
          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCancelacion}">
          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgCancelacion"/>
          	 			</h:selectBooleanCheckbox>
          	 		</rich:column>
          	 		<rich:column width="150">
          	 			<h:outputText value="Cancelación"/>
          	 		</rich:column>
          	 		<rich:column width="120">
          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCancelacion}" styleClass="btnEstilos"/>
          	 		</rich:column>
          	 	</h:panelGrid>
       </rich:panel>