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

<script type="text/javascript">	  
	  
// 	function jsSeleccionArea(itemIdEmp,itemIdSuc,itemIdArea){
// 	    document.getElementById("frmAreaModalPanel:hiddenIdEmpArea").value=itemIdEmp;
// 	    document.getElementById("frmAreaModalPanel:hiddenIdSucArea").value=itemIdSuc;
// 	    document.getElementById("frmAreaModalPanel:hiddenIdArea").value=itemIdArea;
// 	}
</script>
	     <rich:panel id="pMarcoArea" style="border:1px solid #17356f ;width: 707px; background-color:#DEEBF5">
         <h:panelGrid columns="6" style="width: 700px" border="0" >
         	<rich:column style="width: 70px; border: none">
         	    <h:outputText id="lblAreaEmpresa" value="Empresa :" style="padding-left: 10px" > </h:outputText>
         	</rich:column>
         	<rich:column style="width: 302px; border: none">
         		<h:selectOneMenu value="#{empresaController.intCboEmpresaSuc}" style="width: 300px"
         			valueChangeListener="#{empresaController.reloadCboSucursales}">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
				    <a4j:support event="onchange" reRender="cboFilSucursal" ajaxSingle="true"/>
				</h:selectOneMenu>
         	</rich:column>
         	<rich:column >
         		<h:outputText id="lblTipoEmpresaArea" value="Tipo :" > </h:outputText>
         	</rich:column>
         	<rich:column >
	         	<h:selectOneMenu id="cboFilTipo" value="#{empresaController.cboTipoArea}">
	         		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		        	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAREA}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
         	</rich:column>
         	<rich:column >
         		<h:outputText id="lblEstadoEmpresa" value="Estado :" > </h:outputText>
         	</rich:column>
         	<rich:column >
         		<h:selectOneMenu id="cboFilEstado" value="#{empresaController.cboEstadoArea}">
					<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"
						tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
				</h:selectOneMenu>
         	</rich:column>
         </h:panelGrid>
		
         <h:panelGrid id="pgFiltros" columns="7" border="0" style="width: 700px; padding-left: 0px">
         	<rich:column style="width:75px; border: none">
         		<h:outputText id="lblFilSucursal" value ="Sucursal:" style="padding-left: 10px;" ></h:outputText>
         	</rich:column>
         	<rich:column style="width:100px; border: none">
         		<h:selectOneMenu id="cboFilSucursal" style="width: 200px;" value="#{empresaController.cboSucursal}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaSucursal}"
								itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>
				</h:selectOneMenu>
         	</rich:column>
         	<rich:column style="width:40px; border: none">
         		<h:outputText id="lblFilArea" value ="Area:" ></h:outputText>
         	</rich:column>
         	<rich:column style="width: 135px; border: none">
         		<h:inputText id="txtFilArea" size="20" value="#{empresaController.txtArea}"></h:inputText>
         	</rich:column>
         	<rich:column style="width: 15px; border: none">
         	       <h:selectBooleanCheckbox value="#{empresaController.chkAreaCodigo}"/>
         	</rich:column>
         	<rich:column style="width: 110px; border: none">
         		<h:outputText id="lblCodigos" value ="Códigos" ></h:outputText>
         	</rich:column>
         	<rich:column >
         		<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{empresaController.listarAreas}" reRender="pgFilArea"/>
         	</rich:column>
	     </h:panelGrid>
	     
		 <div align="center">                    
                <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >                                                               
                        <h:panelGrid id="pgFilArea" columns="1">
                            <rich:extendedDataTable id="tblFilArea" value="#{empresaController.beanListAreas}" var="item" sortMode="single" 
                            	rowKeyVar="rowkey" rendered="#{not empty empresaController.beanListAreas}" rows="5"
                            	onRowClick="jsSeleccionArea(#{item.area.id.intPersEmpresaPk}, #{item.area.id.intIdSucursalPk}, #{item.area.id.intIdArea},#{item.area.intIdEstado});"
                            	width="645px" height="220px" rows="5">
                                <rich:column width="29px">
                                    <h:outputText value="#{rowkey+1}"></h:outputText>
                                </rich:column>
                                <rich:column width="170px" sortBy="#{item.area.strDescripcion}">
                                    <f:facet name="header">
                                        <h:outputText  id="lblEncaNombArea" value="Nombre de Área" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValNombArea" value="#{item.area.strDescripcion}"></h:outputText>
                                    <rich:toolTip for="lblValNombArea" value="#{item.area.strDescripcion}"></rich:toolTip>
                                </rich:column>

                                <rich:column sortBy="#{item.area.intIdTipoArea}">
                                    <f:facet name="header">
                                        <h:outputText id="lblTipo" value="Tipo" ></h:outputText>
                                    </f:facet>
                                    <h:selectOneMenu value="#{item.area.intIdTipoArea}" disabled="true">
		                    			<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
		                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAREA}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							    	</h:selectOneMenu>
                                </rich:column>

                                <rich:column width="120" sortBy="#{item.empresa.juridica.strRazonSocial}">
                                    <f:facet name="header">
                                        <h:outputText value="Empresa" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValEmpresa" value="#{item.empresa.juridica.strRazonSocial}"></h:outputText>
                                    <rich:toolTip for="lblValEmpresa" value="#{item.empresa.juridica.strRazonSocial}"></rich:toolTip>
                                </rich:column>
                                
                                <rich:column width="120" sortBy="#{item.sucursal.juridica.strRazonSocial}">
                                    <f:facet name="header">
                                        <h:outputText id="lblSucursal1" value="Sucursal" ></h:outputText>
                                    </f:facet>
                                    <h:outputText id="lblValSucursal1" value="#{item.sucursal.juridica.strRazonSocial}"></h:outputText>
                                    
                                    <rich:toolTip for="lblValSucursal1" value="#{item.sucursal.juridica.strRazonSocial}"></rich:toolTip>
                                </rich:column>
                                
                                <rich:column width="100px" sortBy="#{item.area.intIdEstado}">
                                    <f:facet name="header">
                                        <h:outputText id="lblActivo" value="Estado" ></h:outputText>
                                    </f:facet>
                                     
                                    <h:selectOneMenu value="#{item.area.intIdEstado}" disabled="true">
		                    			<f:selectItem itemLabel="Anulado" itemValue="0"/>
		                    			<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
											itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
							    	</h:selectOneMenu>
                                </rich:column>
                                
                                <f:facet name="footer">
                                	<rich:datascroller for="tblFilArea" maxPages="10"></rich:datascroller>
                                </f:facet>
                                
                             

                            </rich:extendedDataTable>                            
                                                                                     
                            <h:panelGrid columns="2" border="">
								<h:outputLink value="#" id="linkPanelArea">
							        <h:graphicImage value="/images/icons/mensaje1.jpg"
										style="border:0px"/>
										
										
							        <rich:componentControl for="panelUpdateDeleteArea" attachTo="linkPanelArea" operation="show" event="onclick"/>
							        
							    </h:outputLink>
	                            <h:outputText value="Para Eliminar o Modificar un Área hacer click en el Registro" style="color: #4449a5"></h:outputText>
                            </h:panelGrid>
                            
                            </h:panelGrid>
                </rich:panel>
       </div>
	
	<h:panelGrid columns="3">
       <h:commandButton value="Nuevo" styleClass="btnEstilos" actionListener="#{empresaController.habilitarGrabarArea}">
       </h:commandButton>  
       <h:commandButton value="Guadar" styleClass="btnEstilos" 
       					actionListener="#{empresaController.grabarArea}"
       					rendered="#{empresaController.strTipoMantArea == applicationScope.Constante.MANTENIMIENTO_GRABAR}">
       </h:commandButton>
       <h:commandButton value="Guadar" styleClass="btnEstilos" 
       					actionListener="#{empresaController.modificarArea}"
       					rendered="#{empresaController.strTipoMantArea == applicationScope.Constante.MANTENIMIENTO_MODIFICAR}">
       </h:commandButton>
       <h:commandButton value="Cancelar" styleClass="btnEstilos" actionListener="#{empresaController.cancelarGrabarArea}">
       </h:commandButton>
	</h:panelGrid>
	
	         
    <rich:panel style="width: 608px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{empresaController.areaRendered}">
    
         	<h:panelGrid id="pgArea" columns="5" style="width:410px;">
             <h:outputText id="lblTipoArea" value="Tipo de Área :" style="padding-left: 15px;padding-right: 34px;" ></h:outputText>
              	<h:selectOneMenu id="cboTipoArea" value="#{empresaController.beanArea.intIdTipoArea}" disabled="#{empresaController.formAreaDisabled}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAREA}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>
                <h:outputLabel  value="*" style="color:#f20526; font-size: 22px;"/>
                <h:outputText id="lblEstado" value="Estado :"/>
                
                <h:selectOneMenu id="cboEstado1" value="#{empresaController.beanArea.intIdEstado}" disabled="#{empresaController.formAreaDisabled}">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				</h:selectOneMenu>				
            </h:panelGrid>
            <h:panelGrid>
  		     	<h:outputText value="#{empresaController.msgTipoAreaError}" styleClass="msgError"></h:outputText>
  		     	<h:outputText value="#{empresaController.msgEstadoAreaError}" styleClass="msgError"></h:outputText>
          	</h:panelGrid>
            
            <h:panelGrid columns="3" border="0" >
               <rich:column style="width: 150px; border: none">
                  <h:outputText value="Empresa" style="padding-left: 10px" ></h:outputText>
               </rich:column>
               <rich:column style="border:none;">
                  <h:outputText value=":" ></h:outputText>
               </rich:column>
               <rich:column >
                  <h:selectOneMenu id="cboEmpresasArea"
                  	label="EmpresasArea" valueChangeListener="#{empresaController.reloadCboSucursales}"
                    style="width: 300px;" value="#{empresaController.beanArea.id.intPersEmpresaPk}"
                    disabled="#{empresaController.formAreaDisabled}"
                    >
				    <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{empresaController.listaJuridicaEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonSocial}"/>
				    <a4j:support event="onchange" reRender="cboSucursalesArea" ajaxSingle="true"/>
				  </h:selectOneMenu>
               </rich:column>
               <rich:column >
		       		<h:outputText value="#{empresaController.msgTxtEmpresaArea}" styleClass="msgError"></h:outputText>
		       </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="3" border="0" >
               <rich:column style="width: 150px; border: none">
                  <h:outputText value="Sucursal" style="padding-left: 10px" ></h:outputText>
               </rich:column>
               <rich:column style="border:none;">
                  <h:outputText value=":" ></h:outputText>
               </rich:column>
               
               <rich:column >
                  <h:selectOneMenu id="cboSucursalesArea" label="SucursalesArea" style="width: 300px;"
                   				   value="#{empresaController.beanArea.id.intIdSucursalPk}"
                   				   disabled="#{empresaController.formAreaDisabled}">
				       <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					   <tumih:selectItems var="sel" value="#{empresaController.listaJuridicaSucursal}"
							itemValue="#{sel.id.intIdSucursal}" itemLabel="#{sel.juridica.strRazonSocial}"/>   
			      </h:selectOneMenu>
               </rich:column>
               
               <rich:column>
		       		<h:outputText value="#{empresaController.msgTxtSucursalArea}" styleClass="msgError"></h:outputText>
		       </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="3" border="0" >
               <rich:column style="width: 150px; border: none">
                  <h:outputText id="lblNomArea" value="Nombre de Área" style="padding-left: 10px" ></h:outputText>
               </rich:column>
               <rich:column style="border:none;">
                  <h:outputText value=":" ></h:outputText>
               </rich:column>
               <rich:column >
                  <h:inputText id="txtNomArea" size="30" style="padding-right: 25px;" value="#{empresaController.beanArea.strDescripcion}" disabled="#{empresaController.formAreaDisabled}"></h:inputText>
               </rich:column>
               <rich:column >
		       		<h:outputText value="#{empresaController.msgTxtArea}" styleClass="msgError"></h:outputText>
		       </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="3" border="0" >
               <rich:column style="width: 150px; border: none">
                  <h:outputText id="lblAbrevArea" value="Abreviatura" style="padding-left:10px" ></h:outputText>
               </rich:column>
               <rich:column style="border:none;">
                  <h:outputText value=":" ></h:outputText>
               </rich:column>
               <rich:column >
                  <h:inputText id="txtAbrevArea" size="10" style="padding-right: 30px;" value="#{empresaController.beanArea.strAbreviatura}" disabled="#{empresaController.formAreaDisabled}"></h:inputText>
               </rich:column>
               <rich:column >
		       		<h:outputText value="#{empresaController.msgTxtAbrevArea}" styleClass="msgError"></h:outputText>
		       </rich:column>
            </h:panelGrid>
         
          <h:panelGrid id="pgAgregar"  columns="2" border="0">
          	<rich:panel id="pCodigo" style="border: 0px solid #17356f;background-color:#DEEBF5; " >
	          	<h:panelGrid id="pgChkAgregar" columns="4" border="0">
	          		 <rich:column width="6px" style="border: none;"></rich:column>
	          		 <h:selectBooleanCheckbox id="chkAgregar" value="#{empresaController.chkCodigo}" disabled="#{empresaController.formAreaDisabled}">
			          	<a4j:support event="onclick" reRender="pCodigo" actionListener="#{empresaController.enableDisable2}" />
			         </h:selectBooleanCheckbox>			         
			         <h:outputText  id="lblCodigo" value="Código" style="padding-right: 25px" ></h:outputText>
			         <a4j:commandButton id="btnAgregar" value="Agregar" styleClass="btnEstilos" actionListener="#{empresaController.addAreaCodigo}" 
			         disabled="#{empresaController.formAreaEnabled}" reRender="pgAreaCodigo"/>
		         </h:panelGrid>
          	</rich:panel>
          	<h:panelGrid id="pgAreaCodigo">
          		<rich:dataTable id="idtblAreaCodigos" rendered="#{not empty empresaController.beanArea.listaAreaCodigo}"
								  value="#{empresaController.beanArea.listaAreaCodigo}"
								  var="item">
							 <rich:column style="width:150px">
							 	<f:facet name="header">
		                           <h:outputText  id="lblNombreTer" value="Nombre" ></h:outputText>
		                        </f:facet>
		                        <h:inputText value="#{item.strDescripcion}" size="20" disabled="#{empresaController.formAreaDisabled}"/>
							 </rich:column>
							 <rich:column style="width:80px">
							 	<f:facet name="header">
		                           <h:outputText  id="lblActivoTer" value="Activo" ></h:outputText>
		                        </f:facet>
		                        <h:selectOneMenu id="cboTipoActivoAreacod" value="#{item.intIdEstado}" disabled="#{empresaController.formAreaDisabled}">
					               	<f:selectItem itemValue="1" itemLabel="Activo"/>
					               	<f:selectItem itemValue="0" itemLabel="Inactivo"/>
					            </h:selectOneMenu>
							 </rich:column>
							 <rich:column style="width:120px">
							 	<f:facet name="header">
		                           <h:outputText  id="lblCodigoTer" value="Código" ></h:outputText>
		                        </f:facet>
		                        <h:inputText value="#{item.strCodigo}" size="20" disabled="#{empresaController.formAreaDisabled}"/>
							 </rich:column>
			    </rich:dataTable>
                 			 <h:outputText value="#{empresaController.msgAreaCodigoError}" styleClass="msgError"/>
          	</h:panelGrid>
         </h:panelGrid>
      </rich:panel>

      </rich:panel>