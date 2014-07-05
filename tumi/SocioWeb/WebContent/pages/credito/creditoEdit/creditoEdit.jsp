<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

					<rich:panel rendered="#{creditoController.formCreditoRendered}" style="width: 970px;border:1px solid #17356f;background-color:#DEEBF5;">
		          	 	<h:panelGrid columns="5">
		          	 		<rich:column width="140"><h:outputText value="Nombre del Crédito"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
			          	 		<h:inputText value="#{creditoController.beanCredito.strDescripcion}" size="80"/>
		                    </rich:column>
		                    <rich:column width="120"><h:outputText value="Estado de la solicitud:" /></rich:column>
		                    <rich:column>
			          	 		<h:selectOneMenu value="#{creditoController.beanCredito.intParaEstadoSolicitudCod}" disabled="true">
			          	 			<f:selectItem itemLabel="Pendiente" itemValue="0"/>
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOCONFIGPRODUCTOS}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
		                    </rich:column>
		          	 	</h:panelGrid>
		          	 	<h:outputText value="#{creditoController.msgTxtDescripcion}" styleClass="msgError"/>
		          	 	
		          	 	<h:panelGrid id="pgEstCredito" columns="8">
		          	 		<rich:column width="140"><h:outputText value="Estado del Crédito"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
			          	 		<h:selectOneMenu value="#{creditoController.beanCredito.intParaEstadoCod}">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
		                    </rich:column>
		                    <rich:column width="120"><h:outputText value="Tipo de Crédito:" /></rich:column>
		                    <rich:column>
		                    	<h:selectOneMenu value="#{creditoController.beanCredito.id.intParaTipoCreditoCod}"
		                    		valueChangeListener="#{creditoController.reloadCboTipoCreditoEmpresa}">
		                    		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			          	 			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CREDITO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
									<a4j:support event="onchange" reRender="pgEstCredito"/>
								</h:selectOneMenu>
		                    </rich:column>
		                    <rich:column>
		                    	<h:outputText value="Según Empresa: "/>
		                    </rich:column>
		                    <rich:column>
		                    	<h:selectOneMenu value="#{creditoController.beanCredito.intParaTipoCreditoEmpresa}">
				                       <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
				                       <tumih:selectItems var="sel" value="#{creditoController.listaTipoCreditoEmpresa}" 
				                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
				                       propertySort="intOrden"/>
				                </h:selectOneMenu>
		                    </rich:column>
		                    <rich:column>
		          	 			<h:outputText value="#{creditoController.msgTxtTipoCredito}" styleClass="msgError"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
			          	
		          	 	<h:panelGrid id="pgRanFec" columns="5">
		          	 		<rich:column width="140"><h:outputText value="Fecha de Inicio"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<rich:calendar enableManualInput="true"
			                   	value="#{creditoController.daFechaIni}"
			                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
			                    cellWidth="10px" cellHeight="20px"/>
		          	 		</rich:column>
		          	 		<rich:column>
			          	 		<h:selectOneRadio value="#{creditoController.rbFecIndeterm}">
			          	 			<f:selectItem itemLabel="Fecha de Fin" itemValue="1"/>
			          	 			<f:selectItem itemLabel="Indeterminado" itemValue="2"/>
			          	 			<a4j:support event="onclick" actionListener="#{creditoController.enableDisableControls}" reRender="pgRanFec"/>
			          	 		</h:selectOneRadio>
		                    </rich:column>
		                    <rich:column>
		          	 			<rich:calendar enableManualInput="true"
		          	 			rendered="#{creditoController.fecFinCreditoRendered}"
			                   	value="#{creditoController.daFechaFin}"
			                    datePattern="dd/MM/yyyy" inputStyle="width:70px;"
			                    cellWidth="10px" cellHeight="20px"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	<h:outputText value="#{creditoController.msgTxtFechaIni}" styleClass="msgError"/>
		          	 	
		          	 	<h:panelGrid columns="6">
		          	 		<rich:column width="140"><h:outputText value="Tipo de Persona"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{creditoController.beanCredito.intParaTipoPersonaCod}" 
		          	 				valueChangeListener="#{creditoController.reloadCboTipoCreditoSbs}">
		                        	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden"/>
									<a4j:support event="onchange" reRender="pgTipoCreditoSBS,pgNaturalezaGarantia"/>
		          	 			</h:selectOneMenu>
		          	 		</rich:column>
		          	 		<rich:column><h:outputText value="Tipo de relación: "/></rich:column>
		          	 		<rich:column>
		          	 		<h:selectOneMenu value="#{creditoController.beanCredito.intParaRolPk}">
			                       <tumih:selectItems var="sel" value="#{creditoController.listaTipoRol}" 
				                       itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
				                       propertySort="intOrden"/>
			                </h:selectOneMenu>
		          	 		<%--
		          	 			<h:selectOneMenu value="#{creditoController.beanCredito.intParaRolPk}">
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOROLAFECTO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu>--%>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="#{creditoController.msgTxtTipoPersona}" styleClass="msgError"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
			          	
		          	 	<h:panelGrid columns="5">
		          	 		<rich:column width="140"><h:outputText value="Condición de Socio"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneRadio value="#{creditoController.rbCondicion}" disabled="#{creditoController.formCondSocio}">
		          	 				<f:selectItem itemLabel="Todos"  itemValue="1"/>
		          	 				<f:selectItem itemLabel="Elegir" itemValue="2"/>
		          	 				<a4j:support event="onclick" actionListener="#{creditoController.listarCondicion}" reRender="dtCondSocio"/>
		          	 			</h:selectOneRadio>
		          	 		</rich:column>
		          	 		<rich:column style="border:1px solid #17356f;background-color:#DEEBF5;">
		          	 			<h:selectManyCheckbox value="#{creditoController.lstTipoCondSocio}">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectManyCheckbox>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="#{creditoConroller.msgTxtTipoCondSocio}" styleClass="msgError"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="dtCondSocio" columns="1">
		          	 		<rich:column>
		          	 			<h:selectManyCheckbox value="#{creditoController.lstCondicionCredito}" layout="pageDirection">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
										propertySort="intOrden" />
		          	 			</h:selectManyCheckbox>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	<%--
	          	 		<h:panelGrid id="dtCondSocio">
	         	 			<rich:dataTable value="#{creditoController.listaCondicionComp}"
			         			rendered="#{not empty creditoController.listaCondicionComp}"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="180px">
			                	<rich:column width="15px;">
					                <h:selectBooleanCheckbox value="#{item.chkSocio}"/>
					            </rich:column>
					            <rich:column width="500">
					            	<h:outputText value="#{item.tabla.strDescripcion}"/>
					            </rich:column>
			                </rich:dataTable>
	         	 		</h:panelGrid>--%>
			          	
		          	 	<h:panelGrid columns="3">
		          	 		<rich:column width="140"><h:outputText value="Tipo de Condición Laboral"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{creditoController.beanCredito.intParaCondicionLaboralCod}">
		                        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" 
										tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
		          	 			</h:selectOneMenu>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgTipoCreditoSBS" columns="4">
		          	 		<rich:column width="140"><h:outputText value="Tipo de crédito(SBS)"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{creditoController.beanCredito.intParaTipoSbsCod}">
									<tumih:selectItems var="sel" value="#{creditoController.listaTipoCreditoSbs}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="#{creditoController.msgTxtTipoSbs}" styleClass="msgError"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="140"><h:outputText value="Tipo de Moneda"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{creditoController.beanCredito.intParaMonedaCod}">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOMONEDA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="#{creditoController.msgTxtMoneda}" styleClass="msgError"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="9">
		          	 		<rich:column width="140"><h:outputText value="Rango de Montos"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column><h:outputText value="Mínimo"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			Monto :
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{creditoController.beanCredito.bdMontoMinimo}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			Porcentaje :
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{creditoController.beanCredito.bdPorcMinimo}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%
		          	 		</rich:column>
		          	 		<rich:column style="border:1px solid #17356f;background-color:#DEEBF5;">
			          	 		<h:selectManyCheckbox value="#{creditoController.lstRanMontoMin}">
			          	 			<tumih:selectItems var="sel" value="#{creditoController.listaTipoCaptacion}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			          	 			<%-- <tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_APLICACION_CAPTACION_CREDITOS}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>--%>
			          	 		</h:selectManyCheckbox>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="9">
		          	 		<rich:column width="141"></rich:column>
		          	 		<rich:column></rich:column>
		          	 		<rich:column><h:outputText value="Máximo"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			Monto :
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{creditoController.beanCredito.bdMontoMaximo}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			Porcentaje :
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{creditoController.beanCredito.bdPorcMaximo}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%
		          	 		</rich:column>
		          	 		<rich:column style="border:1px solid #17356f;background-color:#DEEBF5;border-top:none;">
			          	 		<h:selectManyCheckbox value="#{creditoController.lstRanMontoMax}">
			          	 			<tumih:selectItems var="sel" value="#{creditoController.listaTipoCaptacion2}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			          	 			<%--<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_APLICACION_CAPTACION_CREDITOS}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>--%>
			          	 		</h:selectManyCheckbox>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="140"><h:outputText value="Cálculo de Interés"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<h:selectOneMenu value="#{creditoController.beanCredito.intParaTipoCalculoInteresCod}">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CALCINTERES}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
		          	 			</h:selectOneMenu>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="#{creditoController.msgTxtTipoCalcInteres}" styleClass="msgError"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="10">
		          	 		<rich:column width="140"><h:outputText value="Rango de Interés"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column><h:outputText value="Tasa"/></rich:column>
		          	 		<rich:column><h:inputText value="#{creditoController.bdPorcTasa}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="Meses máximo"/> 
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{creditoController.intMesesMax}" size="8" onkeypress="return soloNumeros(event);"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:outputText value="Monto máximo"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<h:inputText value="#{creditoController.bdMontoMax}" size="8" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>
		          	 		</rich:column>
		          	 		
		          	 		<rich:column>
								<h:selectOneMenu value="#{creditoController.intCondicionSocioInteres}">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"/>
		          	 			</h:selectOneMenu>
		          	 			
		          	 			
		          	 		</rich:column>
		          	 		
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Agregar" actionListener="#{creditoController.addRangoInteres}" styleClass="btnEstilos" reRender="pgListRangoInteres"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<%--<a4j:commandButton value="Eliminar" actionListener="#{creditoController.removeRangoInteres}" styleClass="btnEstilos" reRender="pgListRangoInteres"/>--%>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid>
		          	 		<h:outputText value="#{creditoController.msgTxtPorcTasa}" styleClass="msgError"/>
		          	 		<h:outputText value="#{creditoController.msgTxtMesMaximo}" styleClass="msgError"/>
		          	 		<h:outputText value="#{creditoController.msgTxtMontoMaximo}" styleClass="msgError"/>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgListRangoInteres" columns="2">
		          	 		<rich:column width="140"></rich:column>
		          	 		<rich:column>
		          	 		<div align="center">
		          	 		<rich:dataTable value="#{creditoController.listaCreditoInteres}"
			         			rendered="#{not empty creditoController.listaCreditoInteres}"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="310px">
			                    <f:facet name="header">
			                        <rich:columnGroup>
			                        	<rich:column>
			                                <h:outputText value="Tasa (%)"/>
			                            </rich:column>
			                            <rich:column>
			                                <h:outputText value="Meses Máximo"/>
			                            </rich:column>
			                            <rich:column>
			                                <h:outputText value="Monto Máximo"/>
			                            </rich:column>
			                            <rich:column>
			                                <h:outputText value="Condición Socio"/>
			                            </rich:column>
			                            <rich:column>
			                        		<rich:spacer/>
			                        	</rich:column>
			                        </rich:columnGroup>
                    			</f:facet>
			                	<rich:columnGroup rendered="#{item.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
				                	<rich:column width="100px">
						                <div align="center"><h:outputText value="#{item.bdTasaInteres}"/></div>
						            </rich:column>
						            <rich:column width="120px">
						                <div align="center"><h:outputText value="#{item.intMesMaximo}"/></div>
						            </rich:column>
						            <rich:column width="120px">
						                <div align="center"><h:outputText value="#{item.bdMontoMaximo}"/></div>
						            </rich:column>
						        	<rich:column width="120px">
						                <div align="center">
											<tumih:outputText itemValue="intIdDetalle" itemLabel="strDescripcion" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
											property="#{item.intParaTipoSocio}" />
										</div>
						            </rich:column>
				                	<rich:column>
					                    <a4j:commandButton value="Quitar" actionListener="#{creditoController.removeRangoInteres}" 
		               						reRender="pgListRangoInteres" styleClass="btnEstilos1">
		               						<f:param name="rowKeyRangoInteres" value="#{rowKey}"></f:param>
		               					</a4j:commandButton>
					                </rich:column>
				                </rich:columnGroup>
			                </rich:dataTable>
			                </div>
			                </rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="5">
		          	 		<rich:column width="140px"><h:outputText value="Tasa Interés Anual"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column><h:inputText value="#{creditoController.beanCredito.bdTasaAnual}" size="10" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%</rich:column>
		          	 		<rich:column><h:outputText value="Factor"/></rich:column>
		          	 		<rich:column><h:inputText value="#{creditoController.beanCredito.bdFactor}" size="10" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="6">
		          	 		<rich:column width="140px"><h:outputText value="Tasa Moratoria"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column><h:outputText value="Mensual"/></rich:column>
		          	 		<rich:column><h:inputText value="#{creditoController.beanCredito.bdTasaMoratoriaMensual}" size="10" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%</rich:column>
		          	 		<rich:column><h:outputText value="Anual"/></rich:column>
		          	 		<rich:column><h:inputText value="#{creditoController.beanCredito.bdTasaMoratoriaAnual}" size="10" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="140px"><h:outputText value="Tasa Seguro Desgravamen"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column><h:inputText value="#{creditoController.beanCredito.bdTasaSeguroDesgravamen}" size="10" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);"/>%</rich:column>
		          	 		<rich:column>
								<h:selectOneMenu value="#{creditoController.beanCredito.intParaAplicacionSeguroDesgrav}">
		          	 				<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_MONTO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="3">
		          	 		<rich:column width="140px"><h:outputText value="Finalidad del Crédito"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
								<h:selectManyCheckbox value="#{creditoController.lstFinalidadCredito}" layout="pageDirection">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FINALIDAD_CREDITO}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectManyCheckbox>
							</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="6">
		          	 		<rich:column width="140px"><h:outputText value="Garantía"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Preferida Personal" oncomplete="#{rich:component('pAgregarGarantiaPersonal')}.show()"
		          	 				actionListener="#{creditoGarantiaPersonalController.habilitarGrabarGarantiaPersonal}" reRender="pgFormGarantiaPersonal"
		          	 				styleClass="btnEstilos1" disabled="#{creditoController.beanCredito.id.intItemCredito==null}"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Preferida Real" oncomplete="#{rich:component('pAgregarGarantiaReal')}.show()"
		          	 				actionListener="#{creditoGarantiaRealController.habilitarGrabarGarantiaReal}" reRender="pgFormGarantiaReal"
		          	 				styleClass="btnEstilos1" disabled="#{creditoController.beanCredito.id.intItemCredito==null}"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Preferida Autoliquidable" oncomplete="#{rich:component('pAgregarGarantiaAutoliquidable')}.show()"
		          	 				actionListener="#{creditoGarantiaAutoliquidableController.habilitarGrabarGarantiaAutoliquidable}" reRender="pgFormGarantiaAutoliquidable"
		          	 				styleClass="btnEstilos1" disabled="#{creditoController.beanCredito.id.intItemCredito==null}"/>
		          	 		</rich:column>
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Preferida Rápida Realización" oncomplete="#{rich:component('pAgregarGarantiaRapidaRealizacion')}.show()"
		          	 				actionListener="#{creditoGarantiaRapidaRealizacionController.habilitarGrabarGarantiaRapidaRealizacion}" reRender="pgFormGarantiaRapidaRealizacion"
		          	 				styleClass="btnEstilos1" disabled="#{creditoController.beanCredito.id.intItemCredito==null}"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgListGarantiaPersonal">
		          	 		<h:outputText value="Garantía Preferida Personal" rendered="#{not empty creditoGarantiaPersonalController.listaGarantiaPersonal}"/>
		          	 		<rich:dataTable value="#{creditoGarantiaPersonalController.listaGarantiaPersonal}"
			         			rendered="#{not empty creditoGarantiaPersonalController.listaGarantiaPersonal}"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="800px" title="Garantía Preferida Personal">
			                	<rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Clase"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CLASE_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaClaseCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Sub Clase"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBCLASE_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaSubClaseCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Naturaleza"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaNaturalezaGarantiaCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Tipo Carta Fianza"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_INSTRUMENTO_DEUDA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaTipoCartaFianzaCod}"/>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkEditCreditoGarantiaPersonal" styleClass="no-decor" reRender="pgFormGarantiaPersonal" 
					            		actionListener="#{creditoGarantiaPersonalController.irModificarGarantia}"
					                    oncomplete="#{rich:component('pAgregarGarantiaPersonal')}.show()">
					                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					                    <rich:toolTip for="lnkEditCreditoGarantiaPersonal" value="Editar" followMouse="true"/>
					                </a4j:commandLink>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkDeleteCreditoGarantiaPersonal" styleClass="no-decor" reRender="pgListGarantiaPersonal"
					            		actionListener="#{creditoGarantiaPersonalController.eliminarGarantiaPersonal}"
					            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
					                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					            		<rich:toolTip for="lnkDeleteCreditoGarantiaPersonal" value="Eliminar" followMouse="true"/>
					            	</a4j:commandLink>
					            </rich:column>
			                </rich:dataTable>
		          	 	</h:panelGrid>
		          	 	
		          	 	<br/>
		          	 	<h:panelGrid id="pgListGarantiaReal">
		          	 		<h:outputText value="Garantía Preferida Real" rendered="#{not empty creditoGarantiaRealController.listaGarantiaReal}"/>
		          	 		<rich:dataTable value="#{creditoGarantiaRealController.listaGarantiaReal}"
			         			rendered="#{not empty creditoGarantiaRealController.listaGarantiaReal}"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="800px" title="Garantía Preferida Real">
			                	<rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Clase"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CLASE_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaClaseCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Sub Clase"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBCLASE_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaSubClaseCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Naturaleza"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaNaturalezaGarantiaCod}"/>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkEditCreditoGarantiaReal" styleClass="no-decor" reRender="pgFormGarantiaReal" 
					            		actionListener="#{creditoGarantiaRealController.irModificarGarantia}"
					                    oncomplete="#{rich:component('pAgregarGarantiaReal')}.show()">
					                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					                    <rich:toolTip for="lnkEditCreditoGarantiaReal" value="Editar" followMouse="true"/>
					                </a4j:commandLink>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkDeleteCreditoGarantiaReal" styleClass="no-decor" reRender="pgListGarantiaReal"
					            		actionListener="#{creditoGarantiaRealController.eliminarGarantiaReal}"
					            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
					                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					            		<rich:toolTip for="lnkDeleteCreditoGarantiaReal" value="Eliminar" followMouse="true"/>
					            	</a4j:commandLink>
					            </rich:column>
			                </rich:dataTable>
		          	 	</h:panelGrid>
		          	 	
		          	 	<br/>
		          	 	<h:panelGrid id="pgListGarantiaAutoliquidable">
		          	 		<h:outputText value="Garantía Preferida Autoliquidable" rendered="#{not empty creditoGarantiaAutoliquidableController.listaGarantiaAutoliquidable}"/>
		          	 		<rich:dataTable value="#{creditoGarantiaAutoliquidableController.listaGarantiaAutoliquidable}"
			         			rendered="#{not empty creditoGarantiaAutoliquidableController.listaGarantiaAutoliquidable}"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="500px" title="Garantía Preferida Autoliquidable">
			                	<rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Naturaleza"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaNaturalezaGarantiaCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Tipo de Cta."/>
			                		</f:facet>
			                		<h:outputText value="#{item.intParaDerechoCarta==0?'Cuenta Depósitos':'Derechos de Carta'}"/>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkEditCreditoGarantiaAutoliquidable" styleClass="no-decor" reRender="pgFormGarantiaAutoliquidable" 
					            		actionListener="#{creditoGarantiaAutoliquidableController.irModificarGarantia}"
					                    oncomplete="#{rich:component('pAgregarGarantiaAutoliquidable')}.show()">
					                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					                    <rich:toolTip for="lnkEditCreditoGarantiaAutoliquidable" value="Editar" followMouse="true"/>
					                </a4j:commandLink>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkDeleteCreditoGarantiaAutoliquidable" styleClass="no-decor" reRender="pgListGarantiaAutoliquidable"
					            		actionListener="#{creditoGarantiaAutoliquidableController.eliminarGarantiaAutoliquidable}"
					            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
					                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					            		<rich:toolTip for="lnkDeleteCreditoGarantiaAutoliquidable" value="Eliminar" followMouse="true"/>
					            	</a4j:commandLink>
					            </rich:column>
			                </rich:dataTable>
		          	 	</h:panelGrid>
		          	 	
		          	 	<br/>
		          	 	<h:panelGrid id="pgListGarantiaRapidaRealizacion">
		          	 		<h:outputText value="Garantía Rápida Realización" rendered="#{not empty creditoGarantiaRapidaRealizacionController.listaGarantiaRapidaRealizacion}"/>
		          	 		<rich:dataTable value="#{creditoGarantiaRapidaRealizacionController.listaGarantiaRapidaRealizacion}"
			         			rendered="#{not empty creditoGarantiaRapidaRealizacionController.listaGarantiaRapidaRealizacion}"
			                    var="item" rowKeyVar="rowKey" sortMode="single" width="600px" title="Garantía Rápida Realización">
			                	<rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Clase"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CLASE_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaClaseCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Sub Clase"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_SUBCLASE_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaSubClaseCod}"/>
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Naturaleza"/>
			                		</f:facet>
			                		<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.intParaNaturalezaGarantiaCod}"/>
                                             
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkEditCreditoGarantiaRapidaRealiz" styleClass="no-decor" reRender="pgFormGarantiaRapidaRealizacion" 
					            		actionListener="#{creditoGarantiaRapidaRealizacionController.irModificarGarantia}"
					                    oncomplete="#{rich:component('pAgregarGarantiaRapidaRealizacion')}.show()">
					                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					                    <rich:toolTip for="lnkEditCreditoGarantiaRapidaRealiz" value="Editar" followMouse="true"/>
					                </a4j:commandLink>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkDeleteCreditoGarantiaRapidaRealiz" styleClass="no-decor" reRender="pgListGarantiaRapidaRealizacion"
					            		actionListener="#{creditoGarantiaRapidaRealizacionController.eliminarGarantiaRapidaRealizacion}"
					            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
					                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}" 		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}" 	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}" 			name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intParaTipoGarantiaCod}" 	name="pIntIdTipoGarantia"/>
					                    <f:param value="#{item.id.intItemCreditoGarantia}" 	name="pIntIdItemCreditoGarantia"/>
					            		<rich:toolTip for="lnkDeleteCreditoGarantiaRapidaRealiz" value="Eliminar" followMouse="true"/>
					            	</a4j:commandLink>
					            </rich:column>
			                </rich:dataTable>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="140px"><h:outputText value="Descuentos"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Agregar" oncomplete="#{rich:component('pAgregarDescuentos')}.show()" 
		          	 				actionListener="#{creditoDescuentoController.habilitarGrabarCreditoDscto}" reRender="pgFormDsctoCredito"
		          	 				styleClass="btnEstilos" disabled="#{creditoController.beanCredito.id.intItemCredito==null}"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgListDscto">
		          	 		<rich:dataTable value="#{creditoDescuentoController.listaDescuentoCredito}"
			         			rendered="#{not empty creditoDescuentoController.listaDescuentoCredito}"
			                    var="item" rowKeyVar="rowKey" sortMode="single">
			                	<rich:column width="200px">
			                		<f:facet name="header">
			                			<h:outputText value="Concepto"/>
			                		</f:facet>
					            	<h:outputText value="#{item.strConcepto}"/>
					            </rich:column>
					            <rich:column width="90px">
			                		<f:facet name="header">
			                			<h:outputText value="Monto"/>
			                		</f:facet>
					            	<h:outputText value="#{item.bdMonto}"/>
					            </rich:column>
					            <rich:column width="90px">
			                		<f:facet name="header">
			                			<h:outputText value="Porcentaje"/>
			                		</f:facet>
					            	<h:outputText value="#{item.bdPorcentaje}"/>
					            </rich:column>
					            <rich:column width="90px">
			                		<f:facet name="header">
			                			<h:outputText value="Fec. Inicio"/>
			                		</f:facet>
					            	<h:outputText value="#{item.strDtFechaIni}"/>
					            </rich:column>
					            <rich:column width="90px">
			                		<f:facet name="header">
			                			<h:outputText value="Fec. Fin"/>
			                		</f:facet>
					            	<h:outputText value="#{item.strDtFechaFin}"/>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkEditCreditoDscto" styleClass="no-decor" reRender="pgFormDsctoCredito" 
					            		actionListener="#{creditoDescuentoController.irModificarDescuento}"
					                    oncomplete="#{rich:component('pAgregarDescuentos')}.show()">
					                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}"  		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}"  	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}"  		name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intItemCreditoDescuento}" name="pIntIdItemCreditoDescuento"/>
					                    <rich:toolTip for="lnkEditCreditoDscto" value="Editar" followMouse="true"/>
					                </a4j:commandLink>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkDeleteCreditoDscto" styleClass="no-decor" reRender="pgListDscto"
					            		actionListener="#{creditoDescuentoController.eliminarDescuento}"
					            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
					                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}"  		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}"  	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}"  		name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intItemCreditoDescuento}" name="pIntIdItemCreditoDescuento"/>
					            		<rich:toolTip for="lnkDeleteCreditoDscto" value="Eliminar" followMouse="true"/>
					            	</a4j:commandLink>
					            </rich:column>
			                </rich:dataTable>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="140px"><h:outputText value="Excepciones"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column>
		          	 			<a4j:commandButton value="Agregar" oncomplete="#{rich:component('pAgregarExcepciones')}.show()" 
		          	 				actionListener="#{creditoExcepcionController.habilitarGrabarCreditoExcepcion}" reRender="pgFormExcepcionCredito"
		          	 				styleClass="btnEstilos" disabled="#{creditoController.beanCredito.id.intItemCredito==null}"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgListExcepcion">
		          	 		<rich:dataTable value="#{creditoExcepcionController.listaExcepcionCredito}"
			         			rendered="#{not empty creditoExcepcionController.listaExcepcionCredito}"
			                    var="item" rowKeyVar="rowKey" sortMode="single">
			                	<rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Doble Cuota"/>
			                		</f:facet>
					            	<h:selectOneMenu value="#{item.intIndicadorDobleCuota}" disabled="true">
							  			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_INDICADOR_DOBLE_CUOTA}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							  		</h:selectOneMenu>
					            </rich:column>
					            <rich:column width="90px">
			                		<f:facet name="header">
			                			<h:outputText value="Primer Crédito"/>
			                		</f:facet>
					            	<h:outputText value="#{item.intPrimerCredito}"/> días
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Límite de Cuota Fija"/>
			                		</f:facet>
					            	<h:outputText value="#{item.intPorcentajeLimiteCuota}"/>%
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Límite de Edad"/>
			                		</f:facet>
					            	<h:outputText value="#{item.intEdadLimite}"/> años
					            </rich:column>
					            <rich:column>
			                		<f:facet name="header">
			                			<h:outputText value="Días de Tolerancia"/>
			                		</f:facet>
					            	<h:outputText value="#{item.intDiasTolerancia}"/> días
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkEditCreditoExcepcion" styleClass="no-decor" reRender="pgFormExcepcionCredito" 
					            		actionListener="#{creditoExcepcionController.irModificarExcepcion}"
					                    oncomplete="#{rich:component('pAgregarExcepciones')}.show()">
					                    <h:graphicImage value="/images/icons/edit.png" alt="edit"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}"  		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}"  	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}"  		name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intItemCreditoExcepcion}" name="pIntIdItemCreditoExcepcion"/>
					                    <rich:toolTip for="lnkEditCreditoExcepcion" value="Editar" followMouse="true"/>
					                </a4j:commandLink>
					            </rich:column>
					            <rich:column>
					            	<a4j:commandLink id="lnkDeleteCreditoExcepcion" styleClass="no-decor" reRender="pgListExcepcion"
					            		actionListener="#{creditoExcepcionController.eliminarExcepcion}"
					            		onclick="if(!confirm('Está Ud. Seguro de Eliminar este Registro?'))return false;">
					                    <h:graphicImage value="/images/icons/delete.png" alt="delete"/>
					                    <f:param value="#{item.id.intPersEmpresaPk}"  		name="pIntIdEmpresa"/>
					                    <f:param value="#{item.id.intParaTipoCreditoCod}"  	name="pIntIdTipoCredito"/>
					                    <f:param value="#{item.id.intItemCredito}"  		name="pIntIdItemCredito"/>
					                    <f:param value="#{item.id.intItemCreditoExcepcion}" name="pIntIdItemCreditoExcepcion"/>
					            		<rich:toolTip for="lnkDeleteCreditoExcepcion" value="Eliminar" followMouse="true"/>
					            	</a4j:commandLink>
					            </rich:column>
			                </rich:dataTable>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid columns="4">
		          	 		<rich:column width="140px"><h:outputText value="Modelos Contables"/></rich:column>
		          	 		<rich:column><h:outputText value=":"/></rich:column>
		          	 		<rich:column><h:commandButton value="Solicitar" styleClass="btnEstilos"/></rich:column>
		          	 		<rich:column><h:outputText value="Estado: "/></rich:column>
		          	 	</h:panelGrid>
			          	
		          	 	<h:panelGrid id="pgProvision" columns="4">
		          	 		<rich:column width="150"></rich:column>
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkProvision}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgProvision"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Provisión de Crédito"/>
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
		          	 		<rich:column width="180">
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Provisión de Interés de Crédito"/>
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Extorno de Provisión de Interés"/>
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Provisión de Interés Moratorio"/>
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Extorno de Interés Moratorio"/>
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Provisión de Garantías"/>
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Aprobación del Crédito"/>
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
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Anulación del Crédito"/>
		          	 		</rich:column>
		          	 		<rich:column width="120">
		          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCancelacion}" styleClass="btnEstilos"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgGiroCredito" columns="4">
		          	 		<rich:column width="150"></rich:column>
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCancelacion}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgGiroCredito"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Giro del Crédito"/>
		          	 		</rich:column>
		          	 		<rich:column width="120">
		          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCancelacion}" styleClass="btnEstilos"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgCancelacionPlla" columns="4">
		          	 		<rich:column width="150"></rich:column>
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCancelacion}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgCancelacionPlla"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Cancelación por Planilla"/>
		          	 		</rich:column>
		          	 		<rich:column width="120">
		          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCancelacion}" styleClass="btnEstilos"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgCancelacionCaja" columns="4">
		          	 		<rich:column width="150"></rich:column>
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCancelacion}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgCancelacionCaja"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Cancelación por Caja"/>
		          	 		</rich:column>
		          	 		<rich:column width="120">
		          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCancelacion}" styleClass="btnEstilos"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
		          	 	
		          	 	<h:panelGrid id="pgCastigoCred" columns="4">
		          	 		<rich:column width="150"></rich:column>
		          	 		<rich:column>
		          	 			<h:selectBooleanCheckbox value="#{fondoSepelioController.chkCancelacion}">
		          	 				<a4j:support event="onclick" actionListener="#{fondoSepelioController.enableDisableControls}" reRender="pgCastigoCred"/>
		          	 			</h:selectBooleanCheckbox>
		          	 		</rich:column>
		          	 		<rich:column width="180">
		          	 			<h:outputText value="Castigo del Crédito"/>
		          	 		</rich:column>
		          	 		<rich:column width="120">
		          	 			<h:commandButton value="Detalle" disabled="#{fondoSepelioController.enabDisabCancelacion}" styleClass="btnEstilos"/>
		          	 		</rich:column>
		          	 	</h:panelGrid>
			       </rich:panel>