<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         				-->
	<!-- Autor     : Christian De los Ríos    				-->
	<!-- Modulo    : Créditos - Garantías     				-->
	<!-- Prototipo : PROTOTIPO GARANTÍA PREFERIDA REAL		-->
	<!-- Fecha     : 30/04/2012               				-->

	<script type="text/javascript">
		
	</script>

<h:panelGrid id="pgFormGarantiaPersonal">
	<h:form id="frmGarantia">
    	<rich:panel  style="border: 0px solid #17356f;background-color:#DEEBF5;width:700px;">
            <h:panelGrid columns="4">
            	<rich:column><h:outputText value="Nombre de Crédito :"/></rich:column>
            	<rich:column><h:inputText value="#{creditoController.beanCredito.strDescripcion}" size="50" disabled="true"/></rich:column>
            	<rich:column><h:outputText value="Estado :"/></rich:column>
            	<rich:column>
	            	<h:selectOneMenu value="#{creditoController.beanCredito.intParaEstadoCod}" disabled="true">
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	          	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pgBtnGarantiaPersonal" border="0" columns="2">
	            <rich:column>
	            	<h:panelGroup rendered="#{creditoGarantiaPersonalController.strCreditoGarantiaPersonal == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoGarantiaPersonalController.grabarGarantiaPersonal}" 
			            		oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaPersonal')"
			            		styleClass="btnEstilos" reRender="pgListGarantiaPersonal"/>
		           	</h:panelGroup>
		           	<h:panelGroup rendered="#{creditoGarantiaPersonalController.strCreditoGarantiaPersonal == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
		            	<a4j:commandButton value="Grabar" actionListener="#{creditoGarantiaPersonalController.modificarGarantiaPersonal}" 
		            		oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaPersonal')"
		            		styleClass="btnEstilos" reRender="pgListGarantiaPersonal"/>
	            	</h:panelGroup>
	            </rich:column>
	            <rich:column>
	            	<a4j:commandButton value="Cancelar" styleClass="btnEstilos" oncomplete="Richfaces.hideModalPanel('pAgregarGarantiaPersonal')"/>
	            </rich:column>
            </h:panelGrid>
            
            <h:panelGrid>
	            <rich:panel style="border:1px; solid #17356f;width:670px;background-color:#DEEBF5">
		        	<h:panelGrid id="pgClaseGarantia" columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Clase de Garantía"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu value="#{creditoGarantiaPersonalController.beanGarantiaPersonal.intParaClaseCod}"
						  		valueChangeListener="#{creditoGarantiaPersonalController.reloadCboSubClase}">
	          	 				<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{creditoGarantiaPersonalController.listaClaseGarantia}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								<a4j:support event="onchange" reRender="pgClaseGarantia"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
						  	<h:selectOneMenu value="#{creditoGarantiaPersonalController.beanGarantiaPersonal.intParaSubClaseCod}" style="width:300px;">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" value="#{creditoGarantiaPersonalController.listaSubClaseGarantia}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgNaturalezaGarantia" columns="3">
						<rich:column style="width: 130px;">
							<h:outputText value="Naturaleza de Garantía"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
					  		<h:panelGroup rendered="#{creditoController.beanCredito.intParaTipoPersonaCod != applicationScope.Constante.PARAM_T_TIPOPERSONA_AMBOS}">
					  			<h:selectOneMenu value="#{creditoGarantiaPersonalController.beanGarantiaPersonal.intParaNaturalezaGarantiaCod}">
									<tumih:selectItems var="sel" value="#{creditoGarantiaPersonalController.listaNaturalezaGarantia}"
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
					  		<h:panelGroup rendered="#{creditoController.beanCredito.intParaTipoPersonaCod == applicationScope.Constante.PARAM_T_TIPOPERSONA_AMBOS}">
						  		<h:selectOneMenu value="#{creditoGarantiaPersonalController.beanGarantiaPersonal.intParaNaturalezaGarantiaCod}">
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_NATURALEZA_GARANTIA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</h:panelGroup>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Tipo de Carta Fianza"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<h:selectOneMenu value="#{creditoGarantiaPersonalController.beanGarantiaPersonal.intParaTipoCartaFianzaCod}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_INSTRUMENTO_DEUDA}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
						  	<h:selectBooleanCheckbox value="#{creditoGarantiaPersonalController.beanGarantiaPersonal.boDocAdjunto}"/>Documento Adjunto
						</rich:column>
					</h:panelGrid>
					
					<rich:separator lineType="solid" height="3" styleClass="rich-separator"/>
					
					<h:panelGrid columns="4">
						<rich:column style="width: 130px;">
							<h:outputText value="Tipo de Garantes"/>
					  	</rich:column>
					  	<rich:column>
							<h:outputText value=":"/>
					  	</rich:column>
					  	<rich:column>
						  	<a4j:commandButton value="Agregar" actionListener="#{creditoGarantiaPersonalController.addTipoGarantia}" styleClass="btnEstilos" reRender="pgListTipoGarantia"/>
						</rich:column>
						<rich:column>
						  	<%--<a4j:commandButton value="Eliminar" actionListener="#{creditoGarantiaPersonalController.removeTipoGarantia}" styleClass="btnEstilos" reRender="pgListTipoGarantia"/>--%>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="5">
						<rich:column>
							<h:selectOneMenu value="#{creditoGarantiaPersonalController.intIdTipoOpcion}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_POSICION_GARANTE}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
							<h:selectOneMenu value="#{creditoGarantiaPersonalController.intIdTipoObligatorio}">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_OPCION_GARANTE}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
							<h:outputText value="Condición de Socio :"/>
						</rich:column>
						<rich:column>
							<h:selectManyCheckbox layout="pageDirection" value="#{creditoGarantiaPersonalController.lstCondicionSocio}">
								<f:selectItem itemLabel="Hábil" itemValue="1"/>
								<f:selectItem itemLabel="Excepcional" itemValue="2"/>
								<f:selectItem itemLabel="Cobranza Administrativa" itemValue="3"/>
							</h:selectManyCheckbox>
						</rich:column>
						<rich:column>
							<h:selectManyCheckbox layout="pageDirection" value="#{creditoGarantiaPersonalController.lstTipoHabil}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectManyCheckbox>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid columns="4">
						<rich:column>
							<h:outputText value="Condición Laboral :"/>
						</rich:column>
						<rich:column>
							<h:selectManyCheckbox layout="pageDirection" value="#{creditoGarantiaPersonalController.lstCondicionLaboral}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_CONDICIONLABORAL}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectManyCheckbox>
						</rich:column>
						<rich:column>
							<h:outputText value="Situación Laboral :"/>
						</rich:column>
						<rich:column>
							<h:selectManyCheckbox layout="pageDirection" value="#{creditoGarantiaPersonalController.lstSituacionLaboral}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSOCIO}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectManyCheckbox>
						</rich:column>
					</h:panelGrid>
							
					<h:panelGrid columns="5">
						<rich:column>
							<h:outputText value="Limite de Edad :"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{creditoGarantiaPersonalController.intEdadLimite}" onkeypress="return soloNumeros(event);" size="10"/>años
						</rich:column>
						<rich:column>
							<h:outputText value="Tiempo Mínimo de Contrato :"/>
						</rich:column>
						<rich:column>
							<h:inputText size="8" value="#{creditoGarantiaPersonalController.intTiempoMinContrato}" onkeypress="return soloNumeros(event);"/>
						</rich:column>
						<rich:column>
							<h:selectOneMenu value="#{creditoGarantiaPersonalController.intIdTipoTiempoMinContrato}">
								<f:selectItem itemLabel="Año" itemValue="1"/>
								<f:selectItem itemLabel="Mes" itemValue="2"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
							
					<h:panelGrid columns="5">
						<rich:column>
							<h:selectOneMenu value="#{creditoGarantiaPersonalController.intIdTipoDsctoJudicial}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_DESCUENTO_JUDICIAL_GARANTE}"
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column>
							<h:outputText value="% Aportes Min. :"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{creditoGarantiaPersonalController.bdPorcentajeAporteMin}" onblur="extractNumber(this,2,false);" onkeyup="extractNumber(this,2,false);" size="10"/>%
						</rich:column>
						<rich:column>
							<h:outputText value="Máximo de Garantías:"/>
						</rich:column>
						<rich:column>
							<h:inputText value="#{creditoGarantiaPersonalController.intNumeroMaximoGarantia}" size="5" onkeypress="return soloNumeros(event);"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgListTipoGarantia">
						<rich:dataTable value="#{creditoGarantiaPersonalController.listaTipoGarantiaComp}"
							rendered="#{not empty creditoGarantiaPersonalController.listaTipoGarantiaComp}"
		                    var="item" rowKeyVar="rowKey" sortMode="single" width="750px">
		                    <f:facet name="header">
		                        <rich:columnGroup>
		                        	<rich:column>
		                        		<rich:spacer />
		                        	</rich:column>
		                        	<rich:column width="120px">
		                                <h:outputText value="Condición de Socio"/>
		                            </rich:column>
		                            <rich:column width="180px">
		                                <h:outputText value="Condición Laboral"/>
		                            </rich:column>
		                            <rich:column width="110px">
		                                <h:outputText value="Situación Laboral"/>
		                            </rich:column>
		                            <rich:column width="120px">
		                        		<h:outputText value="Tiempo de Contrato"/>
		                        	</rich:column>
		                        	<rich:column>
		                        		<h:outputText value="Descuento judicial"/>
		                        	</rich:column>
		                        	<rich:column width="90px">
		                        		<h:outputText value="Tipo de Garante"/>
		                        	</rich:column>
		                        	<rich:column>
		                        		<rich:spacer />
		                        	</rich:column>
		                        </rich:columnGroup>
                   			</f:facet>
		                    <rich:columnGroup rendered="#{item.creditoTipoGarantia.intParaEstadoCod!=applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO}">
			                    <rich:column width="15px;">
					                <h:outputText value="#{rowKey+1}"/>
					            </rich:column>
					            <rich:column width="120px">
					            	<h:outputText value="#{item.strCondicionSocio}"/>
					            </rich:column>
					            <rich:column width="180px">
					            	<h:outputText value="#{item.strCondicionLaboral}"/>
					            </rich:column>
					            <rich:column width="110px">
					            	<h:outputText value="#{item.strSituacionLaboral}"/>
					            </rich:column>
					            <rich:column width="120px">
					            	<h:outputText value="#{item.creditoTipoGarantia.intTiempoMinimoContrato}"/> 
					            	<h:selectOneMenu value="#{item.creditoTipoGarantia.intParaTipoTiempoContratoCod}" disabled="true">
										<f:selectItem itemLabel="Año(s)" 	itemValue="1"/>
										<f:selectItem itemLabel="Mes(es)" 	itemValue="2"/>
									</h:selectOneMenu>
					            </rich:column>
					            <rich:column>
					            	<h:outputText value="#{item.creditoTipoGarantia.intParaTipoDsctoJudicialCod==1?'Si':'No'}"/>
					            </rich:column>
					            <rich:column width="90px">
					            	<tumih:outputText cache="#{applicationScope.Constante.PARAM_T_POSICION_GARANTE}" 
                                             itemValue="intIdDetalle" itemLabel="strDescripcion" 
                                             property="#{item.creditoTipoGarantia.intParaTipoOpcionCod}"/>
					            </rich:column>
					            <rich:column>
				                    <a4j:commandButton value="Quitar" actionListener="#{creditoGarantiaPersonalController.removeTipoGarantia}" 
	               						reRender="pgListTipoGarantia" styleClass="btnEstilos1">
	               						<f:param name="rowKeyTipoGarantia" value="#{rowKey}"></f:param>
	               					</a4j:commandButton>
				                </rich:column>
					        </rich:columnGroup>
		                </rich:dataTable>
					</h:panelGrid>
	       		</rich:panel>
           </h:panelGrid>
        </rich:panel>
    </h:form>
</h:panelGrid>