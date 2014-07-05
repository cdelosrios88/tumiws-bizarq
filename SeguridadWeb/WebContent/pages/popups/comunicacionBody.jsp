<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Christian De los Ríos    -->
	<!-- Modulo    : Créditos                 -->
	<!-- Prototipo : PROTOTIPO COMUNICACION   -->
	<!-- Fecha     : 25/01/2012               -->

	<script language="JavaScript" src="/js/main.js"  type="text/javascript">
	      
	</script>
    <h:form id="frmComunicacion">
     	<rich:panel style="border: 2px solid #17356f;background-color:#DEEBF5;width:750px;">
     		<h:message id="LABEL_ID_MSG" styleClass="msgError" for="Numero" />
             <h:panelGrid border="0" columns="2">
	             <rich:column style="border:none">
	             	<a4j:commandButton value="Grabar" actionListener="#{comunicacionController.grabarComunicacion}" styleClass="btnEstilos"
	             		disabled="#{comunicacionController.enableDisableFormComunicacion}"
	             		reRender="#{comunicacionController.strIdModalPanel},#{comunicacionController.pgListComunicacion}"/>
	             </rich:column>
	             <rich:column style="border:none"><h:commandButton value="Cancelar" disabled="#{comunicacionController.enableDisableFormComunicacion}" styleClass="btnEstilos"/></rich:column>
             </h:panelGrid>
             <rich:spacer height="4px"/><rich:spacer height="4px"/>
             <rich:separator height="5px"/>
             
             <h:panelGrid id="pgFormComunic" columns="2" border="0">
	             <rich:panel style="border:1px; solid #17356f;width:700px; background-color:#DEEBF5">
		         	<h:panelGrid columns="2" style="border:0px">
						<rich:column style="border:none">
							<h:outputText value="Tipo de Comunicación:"/>
					  	</rich:column>
					  	<rich:column style="border:none;padding-left:10px;">
						  	<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intTipoComunicacionCod}" styleClass="SelectOption2">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOMUNICACION}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgFormComunic1" columns="7">
						<rich:column style="border:none">
						  	<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intSubTipoComunicacionCod}" styleClass="SelectOption2" style="width:140px;">
								<f:selectItem itemValue="1" itemLabel="Casa"/>
								<f:selectItem itemValue="2" itemLabel="Trabajo"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="150px" style="border:none;">
							<h:outputText value="Tipo de Línea:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intTipoLineaCod}" styleClass="SelectOption2">
								<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOLINEATELEF}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="border:none;">
							<h:outputText value="Número:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:inputText id="Numero" label="Número de Teléfono" value="#{comunicacionController.beanComunicacion.intNumero}" onkeypress="return soloNumeros(event);" size="15">
								<f:validateLength minimum="7"/>
							</h:inputText>
						</rich:column>
						<rich:column style="border:none;padding-left:10px;">
							<h:outputText value="Anexo:"/>
						</rich:column>
						<rich:column style="border:none;padding-left:10px;">
							<h:inputText value="#{comunicacionController.beanComunicacion.intAnexo}" onkeypress="return soloNumeros(event);" size="15" maxlength="5"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgDesc" columns="5" style="border:0px">
						<rich:column style="border:none">
							<h:selectBooleanCheckbox value="#{comunicacionController.chkDescripcion}">
								<a4j:support event="onclick" actionListener="#{comunicacionController.enableDisableControls}" reRender="pgDesc"/>
							</h:selectBooleanCheckbox>
					  	</rich:column>
					  	<rich:column>
					  		<h:outputText value="Descripción"/>
					  	</rich:column>
					  	<rich:column>
							<h:inputText value="#{comunicacionController.beanComunicacion.strDescripcion}" disabled="#{comunicacionController.enableDisableDescComunicacion}" size="63"/>
						</rich:column>
					  	<rich:column>
							<rich:column>
								<h:selectBooleanCheckbox value="#{comunicacionController.chkCasoEmerg}">
									<a4j:support event="onclick" actionListener="#{comunicacionController.enableDisableControls}" reRender="pCasoEmerg,pgEmail,pgPagWeb"/>
								</h:selectBooleanCheckbox>
							</rich:column>
					  	</rich:column>
					  	<rich:column style="border:none;">
							<h:outputText value="Caso de Emergencia"/>
						</rich:column>
					</h:panelGrid>
					<rich:panel style="border-bottom:0px;border-left:0px;border-right:0px;border-top: 1px solid #17356f;background-color:#DEEBF5;width:720px;">
						<h:panelGrid id="pgEmail" columns="3">
							<rich:column width="80px" style="border:none;">
								<h:outputText value="E-mail:"/>
							</rich:column>
							<rich:column style="border:none;">
								<h:selectOneMenu style="width:140px;" disabled="#{comunicacionController.formCasoEmerg}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</rich:column>
							<rich:column style="border:none;">
								<h:inputText id="idemail" size="80" label="E-mail" disabled="#{comunicacionController.formCasoEmerg}"
									validatorMessage="El e-mail ingresado no es válido">
									<f:validator validatorId="checkvalidemail" />
								</h:inputText>
							</rich:column>
						</h:panelGrid>
						<h:message for="idemail" styleClass="msgError"/>
						
						<h:panelGrid id="pgPagWeb" columns="3">
							<rich:column width="80px" style="border:none;">
								<h:outputText value="Pág. Web:"/>
							</rich:column>
							<rich:column style="border:none;">
								<h:selectOneMenu style="width:140px;" disabled="#{comunicacionController.formCasoEmerg}">
									<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
										itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</rich:column>
							<rich:column style="border:none;">
								<h:inputText size="80" disabled="#{comunicacionController.formCasoEmerg}"/>
							</rich:column>
						</h:panelGrid>
					</rich:panel>
		        </rich:panel>
            </h:panelGrid>
         </rich:panel>
     </h:form>