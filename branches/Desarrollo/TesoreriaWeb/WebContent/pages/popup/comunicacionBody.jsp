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
	
<a4j:region>
	<a4j:jsFunction name="selecTipoComunicacion" reRender="divTiposComunicacion"
		actionListener="#{comunicacionController.disableTipoComunicacion}">
		<f:param name="pIntTipoComunicacion"></f:param>
	</a4j:jsFunction>
</a4j:region>
	
    <h:form id="frmComunicacion">
     	<rich:panel style="border: 2px solid #17356f;background-color:#DEEBF5;width:750px;height:158px">
     		<h:message id="LABEL_ID_MSG" styleClass="msgError" for="Numero" />
             <h:panelGrid border="0" columns="2">
	             <rich:column style="border:none">
	             	<a4j:commandButton value="Grabar" 
	             		actionListener="#{comunicacionController.grabarComunicacion}" 
	             		disabled="#{!comunicacionController.habilitarEditar}"
	             		styleClass="btnEstilos"
	             		reRender="#{comunicacionController.pgListComunicacion}" 
	             		oncomplete="Richfaces.hideModalPanel('#{comunicacionController.strIdModalPanel}')"/>
	             </rich:column>
	             <rich:column style="border:none">
	             	<a4j:commandButton value="Cancelar" 
	             		disabled="#{!comunicacionController.habilitarEditar}"
	             		styleClass="btnEstilos">
			    		<rich:componentControl for="pAgregarComunicacion" operation="hide" event="onclick"/>
			    	</a4j:commandButton>
	             </rich:column>
             </h:panelGrid>
             <rich:spacer height="4px"/><rich:spacer height="4px"/>
             <rich:separator height="5px"/>
             
         	<h:panelGrid columns="2" style="border:0px">
				<rich:column style="border:none">
					<h:outputText value="Tipo de Comunicación:"/>
			  	</rich:column>
			  	<rich:column style="border:none;padding-left:10px;">
				  	<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intTipoComunicacionCod}" 
				  		disabled="#{!comunicacionController.habilitarEditar}"
				  		styleClass="SelectOption2"
				  	 	onchange="selecTipoComunicacion(#{applicationScope.Constante.ONCHANGE_VALUE})">
						<f:selectItem itemLabel="Seleccione" itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOCOMUNICACION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
				</rich:column>
			</h:panelGrid>
				
			<h:panelGroup id="divTiposComunicacion" style="border:1px; solid #17356f;width:700px; background-color:#DEEBF5">
				<h:panelGroup layout="block" rendered="#{comunicacionController.renderTelefono}">
					<h:panelGrid id="pgFormComunic1" columns="7">
						<rich:column style="border:none">
						  	<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intSubTipoComunicacionCod}" 
						  		disabled="#{!comunicacionController.habilitarEditar}"
						  		styleClass="SelectOption2" 
						  		style="width:140px">
						  		<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPO_TELEFONO}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column width="150px" style="border:none">
							<h:outputText value="Tipo de Línea:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intTipoLineaCod}" 
								disabled="#{!comunicacionController.habilitarEditar}"
								styleClass="SelectOption2">
								<f:selectItem itemLabel="Seleccione" itemValue="0"/>
								<tumih:selectItems var="sel" 
									cache="#{applicationScope.Constante.PARAM_T_TIPOLINEATELEF}" 
									itemValue="#{sel.intIdDetalle}" 
									itemLabel="#{sel.strDescripcion}"/>
							</h:selectOneMenu>
						</rich:column>
						<rich:column style="border:none;">
							<h:outputText value="Número:"/>
						</rich:column>
						<rich:column style="border:none;">
							<h:inputText id="Numero" value="#{comunicacionController.beanComunicacion.intNumero}" 
								disabled="#{!comunicacionController.habilitarEditar}"
								onkeydown="return validarEnteros(event);" 
								size="15">
								<f:validateLength minimum="7"/>
							</h:inputText>
						</rich:column>
						<rich:column style="border:none;padding-left:10px;">
							<h:outputText value="Anexo:"/>
						</rich:column>
						<rich:column style="border:none;padding-left:10px;">
							<h:inputText value="#{comunicacionController.beanComunicacion.intAnexo}" 
								disabled="#{!comunicacionController.habilitarEditar}"
								onkeydown="return validarEnteros(event);" 
								size="15" 
								maxlength="5"/>
						</rich:column>
					</h:panelGrid>
					
					<h:panelGrid id="pgDesc" columns="5" style="border:0px">
						<rich:column style="border:none">
							<h:selectBooleanCheckbox value="#{comunicacionController.chkDescripcion}" disabled="#{!comunicacionController.habilitarEditar}">
								<a4j:support event="onclick" actionListener="#{comunicacionController.disableControls}" reRender="pgDesc"/>
							</h:selectBooleanCheckbox>
					  	</rich:column>
					  	<rich:column>
					  		<h:outputText value="Descripción"/>
					  	</rich:column>
					  	<rich:column>
							<h:inputText value="#{comunicacionController.beanComunicacion.strDescripcion}" 
								disabled="#{!comunicacionController.habilitarEditar}" 
								size="63"/>
						</rich:column>
					  	<rich:column>
							<rich:column>
								<h:selectBooleanCheckbox value="#{comunicacionController.chkCasoEmerg}"/>
							</rich:column>
					  	</rich:column>
					  	<rich:column style="border:none;">
							<h:outputText value="Caso de Emergencia"/>
						</rich:column>
					</h:panelGrid>
				</h:panelGroup>
				
				<%-- 
				<rich:separator lineType="solid" height="2px" style="background-image: none !important; border: none; background-color:#17356f; width:720px"/>
				--%>
				<h:panelGrid id="pgEmail" columns="3" rendered="#{comunicacionController.renderEmail}">
					<rich:column width="80px" style="border:none">
						<h:outputText value="E-mail:"/>
					</rich:column>
					<rich:column style="border:none">
						<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intSubTipoComunicacionCod}" 
							disabled="#{!comunicacionController.habilitarEditar}"
							style="width:140px">
							<f:selectItem itemLabel="Seleccione" itemValue="0"/>
							<tumih:selectItems var="sel" 
								cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column style="border:none">
						<h:inputText id="txtEmail" 
							disabled="#{!comunicacionController.habilitarEditar}"
							size="80" 
							value="#{comunicacionController.strEmail}"/>
					</rich:column>
				</h:panelGrid>
				
				<h:panelGrid id="pgPagWeb" columns="3" rendered="#{comunicacionController.renderWebSite}">
					<rich:column width="80px" style="border:none">
						<h:outputText value="Pág. Web:"/>
					</rich:column>
					<rich:column style="border:none;">
						<h:selectOneMenu value="#{comunicacionController.beanComunicacion.intSubTipoComunicacionCod}" 
							disabled="#{!comunicacionController.habilitarEditar}"
							style="width:140px">
							<f:selectItem itemLabel="Seleccione" itemValue="0"/>
							<tumih:selectItems var="sel" 
								cache="#{applicationScope.Constante.PARAM_T_TIPODIRECELECTRONICA}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
						</h:selectOneMenu>
					</rich:column>
					<rich:column style="border:none;">
						<h:inputText id="txtWeb" 
							disabled="#{!comunicacionController.habilitarEditar}"
							value="#{comunicacionController.strWeb}" 
							size="80"/>
					</rich:column>
				</h:panelGrid>
	        </h:panelGroup>
            
         </rich:panel>
     </h:form>