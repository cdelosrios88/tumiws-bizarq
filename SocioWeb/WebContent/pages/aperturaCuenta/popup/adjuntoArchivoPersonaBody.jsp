<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Christian De los Ríos 			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 02/07/2012               		-->

	<h:form id="frmAdjuntoPersona">
    	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 570px;">
    		<h:panelGroup id="divFirmaBeneficiario" layout="block" style="float:left; width:300px; margin-left:88px">
           		<h:panelGrid columns="1" styleClass="tableCellBorder4">
             	<rich:column width="202" style="border: solid 1px silver; height:102px; padding:0px">
             		<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty beneficiarioController.fileFirmaSocio}"
	                    createContent="#{beneficiarioController.paintImage}" value="#{beneficiarioController.fileFirmaSocio}"
	                    style="width:200px; height:100px;" cacheable="false">  
                    </a4j:mediaOutput>
             	</rich:column>
             	<rich:column style="padding:0px">
             		<a4j:commandButton value="Firma" oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
					actionListener="#{beneficiarioController.adjuntarFirma}" reRender="mpFileUpload"/>
             	</rich:column>
            	</h:panelGrid>
           	</h:panelGroup>
          	
           	<h:panelGroup id="divFotoBeneficiario" layout="block" style="float:left">
           		<h:panelGrid columns="1" styleClass="tableCellBorder4">
             	<rich:column width="102" style="border: solid 1px silver; height:102px; padding:0px">
             		<a4j:mediaOutput element="img" mimeType="image/jpeg" rendered="#{not empty beneficiarioController.fileFotoSocio}"
                        createContent="#{beneficiarioController.paintImage}" value="#{beneficiarioController.fileFotoSocio}"
                        style="width:100px; height:100px;" cacheable="false">  
                    </a4j:mediaOutput>
             	</rich:column>
             	<rich:column style="padding:0px">
             		<a4j:commandButton value="Foto" oncomplete="Richfaces.showModalPanel('mpFileUpload')" styleClass="btnEstilos1"
					actionListener="#{beneficiarioController.adjuntarFoto}" reRender="mpFileUpload"/>
             	</rich:column>
           		</h:panelGrid>
           	</h:panelGroup>
           	
           	<h:panelGrid columns="2" style="text-align:center">
           		<rich:column>
           			<a4j:commandButton value="Grabar" action="#{beneficiarioController.grabarArchivoBeneficiario}"
           				oncomplete="Richfaces.hideModalPanel('mpAdjuntoPersona')" styleClass="btnEstilos"/>
           		</rich:column>
           	</h:panelGrid>
         </rich:panel>
    </h:form>