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
<style type="text/css">
      .active-row {background-color: #DAE8FB !important;
	               cursor: pointer;
	              }
      .tabla {border: 1px;
	          }

   /* Definicion de los estilos para la tabla*/
      .encabezado {text-align: center;
   				   font: 11px Arial, sans-serif;
   				   font-weight: bold;
                   color: Snow;
                   background:  #87CEFA;
                   }
   /* Definicion de estilos para la columnas */
      .primero {text-align: center;
   				font: 11px Arial, sans-serif;
   				background: #A2CD5A;
				}
      .ultimo {font: 11px Arial, sans-serif;
   			   text-align: center;
   			   background: #BCEE68;
			  }

</style>

<script type="text/javascript">

	function jsSetColumnName(columnName){
		//alert(columnName);
		document.getElementById("formAudiSis:hiddenNombreColumna").value=columnName;
	}
	
	function jsSetOperadorLogico(cboOperLogicos){
		//alert(cboOperLogicos[cboOperLogicos.value].innerHTML);
		document.getElementById("formAudiSis:hiddenOperLogico").value=cboOperLogicos[cboOperLogicos.value].innerHTML;
	}
</script>

	<rich:modalPanel id="mpTablas" width="380" height="410" resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Seleccionar Tablas" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkTablas"/>
               <rich:componentControl for="mpTablas" attachTo="hidelinkTablas" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmTablas">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px; " >                 
                <h:panelGrid id="pTablasMenu">
                	<rich:dataTable id="tbTablasMenu" value="#{auditoriaController.beanListDataObjects}" rows="10" 
	                            	var="item" rowKeyVar="rowKey" sortMode="single"  rendered="#{not empty auditoriaController.beanListDataObjects}"> 
	                	<rich:column width="120">
		                	<f:facet name="header">
		                    	<h:outputText value="Nombre Tabla"/>
		                    </f:facet>
		                    <h:outputText value="#{item.strNombreObjeto}"/>
		                </rich:column>
		                <rich:column width="120">
		                    <h:selectBooleanCheckbox value="#{item.blnSelectedObjecto}"/>
		                </rich:column>
		                <f:facet name="footer">   
		                    <rich:datascroller for="tbTablasMenu" maxPages="5"/>   
		                </f:facet> 
	                </rich:dataTable>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="3" border="0"  style="width: 200px;">
                    <h:commandButton value="Seleccionar" styleClass="btnEstilos" actionListener="#{auditoriaController.adjuntarDataObjects}"/>
                    <a4j:commandButton value="Todos" styleClass="btnEstilos" actionListener="#{auditoriaController.selectAll}" reRender="tbTablasMenu">
                    	<f:param id="selectAll" name="selectAll" value="selectAll"/>
                    </a4j:commandButton>
                    <a4j:commandButton value="Limpiar" styleClass="btnEstilos" actionListener="#{auditoriaController.selectAll}" reRender="tbTablasMenu"/>
                </h:panelGrid>
                <h:panelGrid>
					<h:inputHidden id="hiddenIdTablas"/>
				</h:panelGrid>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
    
    <h:form id="formAudiSis">
      <rich:tabPanel>
         <rich:tab label="Sesión"  style="background-color: #E8F1FF">
         	
            <rich:panel style="border:1px solid #17356f ;width: 755px; background-color:#DEEBF5">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >
             		
                    <f:facet name="header">
                        <h:outputText id="lblBusqueda" value="Busqueda" ></h:outputText>
                    </f:facet>
                    <h:panelGrid columns="6">
                        <h:outputText value="Empresa : "/>
                        <rich:column style="width: 230px; border:none">
			            		<h:selectOneMenu id="cboEmpBusqSesion" 
			                       	valueChangeListener="#{controllerFiller.reloadCboUsuario}"
			                       	required="true" style="width: 220px;"
			                       	value="#{auditoriaSistController.intCboEmpSesion}">
							        <f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
										itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
							        <a4j:support event="onchange" reRender="cboUsuBusqSesion" ajaxSingle="true" />
							  	</h:selectOneMenu>
		            	</rich:column>
                        
                        <h:outputText value="Estado de Sesión : " ></h:outputText>
                        <rich:column style="width: 110px; border:none">
                        	<h:selectOneMenu required="true" style="width: 100px;"
			                	value="#{auditoriaSistController.intCboEstadosSesion}">
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_ESTADOUNIVERSAL}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				            </h:selectOneMenu>
                        </rich:column>
                        
                        <h:outputText value="Tipo de Sucursal : "  style="padding-left: 15px;"></h:outputText>
						<h:selectOneMenu required="true" style="width: 100px;"
		                	value="#{auditoriaSistController.intCboTiposSucursal}">
							<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
			            </h:selectOneMenu>
                     </h:panelGrid>
                     <rich:spacer height="4px"/><rich:spacer height="4px"/>
                     <h:panelGrid columns="7">
                        <rich:column width="74" style="border: none">
		            		<h:outputText value="Usuario :" ></h:outputText>
		            	</rich:column>
		            	<rich:column width="160" style="border: none">
		            		<h:selectOneMenu id="cboUsuBusqSesion" value="#{auditoriaSistController.intCboUsuSesion}"
			               			style="width:150px;">
					               	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
									<tumih:selectItems var="sel" value="#{controllerFiller.listUsuario}"
										itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
					        </h:selectOneMenu>
		            	</rich:column>
		            	<rich:column width="21">
		            		<h:selectBooleanCheckbox value="#{auditoriaSistController.blnRangoFechas}"/>
		            	</rich:column>
		            	<rich:column>
		            		<h:outputText value="Rango de fecha :" />
		            	</rich:column>
		            	<rich:column>
		               		<rich:calendar value="#{auditoriaSistController.dtInicioBusqSesion}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 70px" style="width: 170px"/>
		                </rich:column>
		                <rich:column>
		               		<rich:calendar value="#{auditoriaSistController.dtFinBusqSesion}" datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 70px" style="width: 170px"/>
		                </rich:column>
                        <a4j:commandButton value="Buscar" actionListener= "#{auditoriaSistController.listarSesiones}" reRender="pgbusqSesiones,tblSesiones" styleClass="btnEstilos"/>
                     </h:panelGrid>
                    
                 <br/>
                 <div style="width:100%; overflow-x:auto; overflow-y:hidden; padding-left: 30px">
                     <h:panelGrid id="pgbusqSesiones">
                            <rich:extendedDataTable id="tblSesiones" enableContextMenu="false" 
                             value="#{auditoriaSistController.beanListSesiones}" var="item" sortMode="single" rows="5"
					  		 rowKeyVar="rowKey" width="645px" height="160px" rendered="#{not empty auditoriaSistController.beanListSesiones}">
                                
					  		 	<rich:column width="18px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
					  		 
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Código" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.intIdUsuario}"></h:outputText>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Usuario" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strUsuario}"></h:outputText>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strFecha}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Hora Inicio" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strHoraIni}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Hora Fin" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strHoraFin}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estado" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strEstado}"></h:outputText>
                                </rich:column>
								<f:facet name="footer">   
						        	<rich:datascroller for="tblSesiones" maxPages="10"/>   
						        </f:facet>
                            </rich:extendedDataTable>
                        </h:panelGrid>
					</div>
                </rich:panel>
     	    <h:panelGrid columns="3">
            	<a4j:commandButton id="btnDelAllSessions" value="Eliminar sesiones" actionListener="#{auditoriaSistController.eliminarSesionesActivas}" reRender="pgListSessions,tblSesionesActivas" ajaxSingle="true" styleClass="btnEstilos"/>
                <a4j:commandButton id="btnRefrescarSesiones" value="Refrescar sesiones activas" actionListener="#{auditoriaSistController.listarSesionesActivas}" reRender="pgListSessions,tblSesionesActivas" ajaxSingle="true" styleClass="btnEstilos"/>
            </h:panelGrid>
            
           	<rich:panel id="pnSesionesActivas" style="width: 720px;border:1px solid #17356f;background-color:#DEEBF5;">
  			    <h:panelGrid id="pgListSessions" columns="2">
                	<rich:column width="20px" style="border: none"/>
                	<rich:column id="colSesionesActivas" style="border: none">
                            <rich:dataTable id="tblSesionesActivas" sortMode="single" rows="5" width="645px"
                             value="#{auditoriaSistController.beanListSesionesActivas}" var="item" rowKeyVar="rowKey">
                                
					  		 	<rich:column width="18px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
                                
                                <rich:column width="18px" id="colSeleccion">
                                	<f:facet name="header">
                                        <h:selectBooleanCheckbox value="#{auditoriaSistController.blnSelectAllSessions}" valueChangeListener="#{auditoriaSistController.selectAllSessions}">
                                        	<a4j:support event="onclick" reRender="pgListSessions,tblSesionesActivas" ajaxSingle="true"/>
                                        </h:selectBooleanCheckbox>
                                    </f:facet>
                                    
                                    <h:selectBooleanCheckbox value="#{item.blnSeleccionado}"></h:selectBooleanCheckbox>
                                </rich:column>
					  		 
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Código" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.intIdUsuario}"></h:outputText>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Usuario" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strUsuario}"></h:outputText>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Sucursal" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strSucursal}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strFecha}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Hora Inicio" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strHoraIni}"></h:outputText>
                                </rich:column>
								<f:facet name="footer">   
						        	<rich:datascroller for="tblSesionesActivas" maxPages="10"/>   
						        </f:facet>
                            </rich:dataTable>
                	</rich:column>
            	</h:panelGrid>
     		</rich:panel>
  
              <!--xcvcxvcx-->
                 <br></br>
               
         </rich:panel>
     </rich:tab>
     
	<rich:tab id="tbAplicacion" label="Aplicación"  >
	 	<rich:panel style="width: 800px ;background-color: #DEEBF5">
           	<h:panelGrid columns="4">
            	<rich:column style="width: 70px; border: none">
            		<h:outputText  value="Empresa :" />
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboEmpresa"
						style="width: 300px;" valueChangeListener="#{controllerFiller.reloadCboUsuarioMenu}"
						value="#{auditoriaController.intCboEmpresa}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						<a4j:support event="onchange" reRender="pMenu01,pMenu02,pMenu03,pMenu04" ajaxSingle="true" />
					</h:selectOneMenu>
                </rich:column>
                
                <rich:column style="width: 150px; border: none">
            		<h:outputText  value="Tipo de Aplicación :"  style="padding-left:10px;"/>
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboTipoAplicacion"
						value="#{auditoriaController.intCboTipoAplicacion}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOAPLICACION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
           	</h:panelGrid>
           	
           	<h:panelGrid id="pMenu01" columns="3" border="0" >
            	<rich:column style="width: 150px; border: none">
                    <h:outputText id="lblMenu1" value="Menú 01" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenu1"
                       	valueChangeListener="#{controllerFiller.reloadCboMenu2}" value="#{auditoriaController.strMenu01}"
                       	style="width: 250px;">
				        <f:selectItems value="#{controllerFiller.cboMenu1}"></f:selectItems>
				        <a4j:support event="onchange" reRender="cboMenu2,cboMenu3,cboMenu4" ajaxSingle="true"/>
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pMenu02" columns="3" border="0">
            	<rich:column style="width: 150px; border: none">
                    <h:outputText id="lblMenu2" value="Menú 02" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenu2"
                       	valueChangeListener="#{controllerFiller.reloadCboMenu3}" value="#{auditoriaController.strMenu02}"
                       	style="width: 250px;">      
				        <f:selectItems value="#{controllerFiller.cboMenu2}"></f:selectItems>
				        <a4j:support event="onchange" reRender="cboMenu3,cboMenu4" ajaxSingle="true" />
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pMenu03" columns="3" border="0" >
            	<rich:column style="width: 150px; border: none">
                    <h:outputText id="lblMenu3" value="Menú 03" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenu3"
                       	valueChangeListener="#{controllerFiller.reloadCboMenu4}" value="#{auditoriaController.strMenu03}"
                       	style="width: 250px;">
				        <f:selectItems value="#{controllerFiller.cboMenu3}"></f:selectItems>
				        <a4j:support event="onchange" reRender="cboMenu4" ajaxSingle="true" />
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pMenu04" columns="3" border="0" >
            	<rich:column style="width: 150px; border: none">
                    <h:outputText id="lblMenu4" value="Menú 04" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenu4"
                       	style="width: 250px;" value="#{auditoriaController.strMenu04}">
				        <f:selectItems value="#{controllerFiller.cboMenu4}"/>
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid columns="8" width="780">
            	<rich:column style="width:110px;border:none">
            		<h:outputText  value="Tipo de Sucursal" />
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboTipoSucursal" value="#{auditoriaController.intCboTipoSucursal}">
                		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
                
                <rich:column style="width:105px;border: none">
            		<h:outputText  value="Tipo de Reporte" />
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboTipoReporte" value="#{auditoriaController.intCboTipoReporte}">
                		<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOFRECCONSULTA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
                
                <rich:column style="border: none">
            		<h:outputText  value="Ordernar por" />
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboTipoUsuario" value="#{auditoriaController.intCboTipoUsuario}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOORDENAPLICACION}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
                
                <rich:column style="border: none">
            		<h:outputText  value="Tipo de ingreso" />
            	</rich:column>
                <rich:column style="border:none">
                	<h:selectOneMenu id="idCboTipoIngreso" value="#{auditoriaController.intCboTipoIngreso}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
                		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOOPCCONSULTA}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
                </rich:column>
           	</h:panelGrid>
           	
           	<h:panelGrid id="pRanFec" columns="8">
            	<rich:column style="border:none">
            		<h:selectBooleanCheckbox id="chkRanFec" value="#{auditoriaController.chkRanFec}"> 
            			<a4j:support event="onclick" reRender="pRanFec" actionListener="#{auditoriaController.enableDisable}" />
            		</h:selectBooleanCheckbox>
            	</rich:column>
            	<rich:column style="width:140px;border:none;padding-left:5px;">
            		<h:outputText  value="Rango de Fechas" />
            	</rich:column>
                <rich:column style="border:none;">
	         		<rich:calendar popup="true" value="#{auditoriaController.daFechaIni}"
	         			disabled="#{auditoriaController.formRanFecEnabled}"
                        datePattern="dd/MM/yyyy" inputStyle="width:80px"
                        cellWidth="10px" cellHeight="20px"/>
	         	</rich:column>
	         	<rich:column style="border:none;">
	         		<rich:calendar popup="true" value="#{auditoriaController.daFechaFin}"
	         			disabled="#{auditoriaController.formRanFecEnabled}"
                        datePattern="dd/MM/yyyy" inputStyle="width:80px"
                        cellWidth="10px" cellHeight="20px"/>
	         	</rich:column>
                <rich:column style="border:none;padding-left:10px;">
            		<h:selectBooleanCheckbox id="chkUsuario" value="#{auditoriaController.chkUsuario}">
            			<a4j:support event="onclick" reRender="pRanFec" actionListener="#{auditoriaController.enableDisable}" />
            		</h:selectBooleanCheckbox>
            	</rich:column>
            	<rich:column style="border:none;padding-left:5px;">
            		<h:outputText value="Usuario:" />
            	</rich:column>
            	<rich:column style="border:none;padding-left:10px;">
            		<h:selectOneMenu id="idCboUsuario" value="#{auditoriaController.intCboUsuario}" style="width:180px;" disabled="#{auditoriaController.formUsuarioEnabled}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" value="#{controllerFiller.listUsuario}"
							itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strNombreUsuario}"/>
					</h:selectOneMenu>
            	</rich:column>
           	</h:panelGrid>
           	
           	<h:panelGrid columns="1">
           		<rich:column style="border:none;">
           			<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{auditoriaController.listarAplicacion}" reRender="pgAuditoriaSist"/>
           		</rich:column>
           	</h:panelGrid>
           	<rich:spacer height="10px"/>
           	<h:panelGrid>
           		<h:outputText value="#{auditoriaController.msgTxtError}" styleClass="msgError"/>
           		<h:outputText value="#{auditoriaController.msgTxtTipoReporte}" styleClass="msgError"/>
           	</h:panelGrid>
            <rich:spacer height="10px"/>
            
            <h:panelGrid id="pgAuditoriaSist">
	            <rich:scrollableDataTable id="sdtAuditoria" value="#{auditoriaController.beanListAplicacion}"
	              	var="item" rowKeyVar="key" width="750px" height="180px"
	              	rendered="#{not empty auditoriaController.beanListAplicacion}">
	           		<rich:column width="10px">
						<f:facet name="header">
							<h:outputText value="" />
						</f:facet>
						<h:outputText value="#{key+1}"/>
					</rich:column>
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Opción" />
						</f:facet>
						<h:outputText value="#{item.strTransaccion}"></h:outputText>
					</rich:column>
					
					<rich:columns value="#{auditoriaController.beanListColumns}" var="columns" 
						width="40px" index="ind" style="text-align:center;">
	                	<f:facet name="header">
		                    <h:outputText value="#{columns.strCabecera}"/>
		                </f:facet>
	                	<h:outputText value="#{item.lstFechas[ind]}"/>
		            </rich:columns>
		            
					<rich:column width="50px">
						<f:facet name="header">
							<h:outputText value="APP"/>
						</f:facet>
						<div align="center"><h:outputText value="#{item.intSumCntAcceso}"/></div>
					</rich:column>
				</rich:scrollableDataTable>
			</h:panelGrid>
 		 </rich:panel>
    </rich:tab>
     
	<rich:tab id="tbFormReport" label="Formulario y Reporte"  >
    	<rich:panel style="width: 780px ;background-color: #DEEBF5">
    		<h:panelGrid columns="2">
            	<rich:column style="border: none">
            		<h:selectBooleanCheckbox value="#{auditoriaController.chkMenuFormApp}">
            			<a4j:support event="onclick" reRender="pFormRepMenu01,pFormRepMenu02,pFormRepMenu03,pFormRepMenu04" actionListener="#{auditoriaController.enableDisableFormRep}"/>
            		</h:selectBooleanCheckbox>
            	</rich:column>
                <rich:column style="border:none;padding-left:10px;">
                	<h:outputText value="Menú" />
                </rich:column>
           	</h:panelGrid>
           	
           	<h:panelGrid id="pFormRepMenu01" columns="3" border="0" >
            	<rich:column style="width: 150px; border: none">
                    <h:outputText value="Menú 01" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenuFormRep1" disabled="#{auditoriaController.formRepMenuEnabled}"
                       	valueChangeListener="#{controllerFiller.reloadCboMenu2}" value="#{auditoriaController.strMenuFormApp01}"
                       	style="width: 250px;">
				        <f:selectItems value="#{controllerFiller.cboMenu1}"></f:selectItems>
				        <a4j:support event="onchange" reRender="cboMenuFormRep2,cboMenuFormRep3,cboMenuFormRep4" ajaxSingle="true"/>
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pFormRepMenu02" columns="3" border="0">
            	<rich:column style="width: 150px; border: none">
                    <h:outputText value="Menú 02" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenuFormRep2" disabled="#{auditoriaController.formRepMenuEnabled}"
                       	valueChangeListener="#{controllerFiller.reloadCboMenu3}" value="#{auditoriaController.strMenuFormApp02}"
                       	style="width: 250px;">      
				        <f:selectItems value="#{controllerFiller.cboMenu2}"></f:selectItems>
				        <a4j:support event="onchange" reRender="cboMenuFormRep3,cboMenuFormRep4" ajaxSingle="true" />
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pFormRepMenu03" columns="3" border="0" >
            	<rich:column style="width: 150px; border: none">
                    <h:outputText value="Menú 03" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenuFormRep3" disabled="#{auditoriaController.formRepMenuEnabled}"
                       	valueChangeListener="#{controllerFiller.reloadCboMenu4}" value="#{auditoriaController.strMenuFormApp03}"
                       	style="width: 250px;">
				        <f:selectItems value="#{controllerFiller.cboMenu3}"></f:selectItems>
				        <a4j:support event="onchange" reRender="cboMenuFormRep4" ajaxSingle="true" />
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pFormRepMenu04" columns="3" border="0" >
            	<rich:column style="width: 150px; border: none">
                    <h:outputText value="Menú 04" style="padding-left:10px" ></h:outputText>
                </rich:column>
                <rich:column style="border:none;">
                    <h:outputText value=":" ></h:outputText>
                </rich:column>
                <rich:column style="border: none">
                    <h:selectOneMenu id="cboMenuFormRep4" disabled="#{auditoriaController.formRepMenuEnabled}"
                       	style="width: 250px;" value="#{auditoriaController.strMenuFormApp04}">
				        <f:selectItems value="#{controllerFiller.cboMenu4}"/>
				  	</h:selectOneMenu>
                </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pAplicaciones" columns="4">
            	<rich:column style="border:none;">
	            	<h:selectBooleanCheckbox value="#{auditoriaController.chkAplicaciones}">
	            		<a4j:support event="onclick" reRender="pAplicaciones" actionListener="#{auditoriaController.enableDisableFormRep}"/>
	            	</h:selectBooleanCheckbox>
	            </rich:column>
            	<rich:column style="border:none;width:100px;padding-left:5px;">
	            	<h:outputText value="Aplicaciones"/>
	            </rich:column>
	            <rich:column style="border:none;">
	            	<h:inputText size="90" disabled="#{auditoriaController.formRepAppEnabled}"></h:inputText>
	            </rich:column>
	            <rich:column style="border:none;">
		            <a4j:commandLink disabled="#{auditoriaController.formRepAppEnabled}">
		              	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
		            </a4j:commandLink>
		        </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pTablas" columns="4">
            	<rich:column style="border:none;">
	            	<h:selectBooleanCheckbox value="#{auditoriaController.chkTablas}">
	            		<a4j:support event="onclick" reRender="pTablas" actionListener="#{auditoriaController.enableDisableFormRep}"/>
	            	</h:selectBooleanCheckbox>
	            </rich:column>
            	<rich:column style="border:none;width:100px;padding-left:5px;">
	            	<h:outputText value="Tablas"/>
	            </rich:column>
	            <rich:column style="border:none;">
	            	<h:inputText value="#{auditoriaController.strTablasMenu}" size="90" disabled="#{auditoriaController.formRepTablasEnabled}"></h:inputText>
	            </rich:column>
	            <rich:column style="border:none;">
		            <a4j:commandLink actionListener="#{auditoriaController.listarTablas}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" 
		            		reRender="frmTablas,tbTablasMenu" immediate="true" disabled="#{auditoriaController.formRepTablasEnabled}">
                       	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                    </a4j:commandLink>
		        </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pVistas" columns="4">
            	<rich:column style="border:none;">
	            	<h:selectBooleanCheckbox value="#{auditoriaController.chkVistas}">
	            		<a4j:support event="onclick" reRender="pVistas" actionListener="#{auditoriaController.enableDisableFormRep}"/>
	            	</h:selectBooleanCheckbox>
	            </rich:column>
            	<rich:column style="border:none;width:100px;padding-left:5px;">
	            	<h:outputText value="Vistas"/>
	            </rich:column>
	            <rich:column style="border:none;">
	            	<h:inputText value="#{auditoriaController.strVistasMenu}" size="90" disabled="#{auditoriaController.formRepVistasEnabled}"></h:inputText>
	            </rich:column>
	            <rich:column style="border:none;">
		            <a4j:commandLink actionListener="#{auditoriaController.listarVistas}" oncomplete="Richfaces.showModalPanel('mpTablas').show()" 
		            	reRender="pTablasMenu, tbTablasMenu" immediate="true" disabled="#{auditoriaController.formRepVistasEnabled}">
                       	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                    </a4j:commandLink>
		        </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pTriggers" columns="4">
            	<rich:column style="border:none;">
	            	<h:selectBooleanCheckbox value="#{auditoriaController.chkTriggers}">
	            		<a4j:support event="onclick" reRender="pTriggers" actionListener="#{auditoriaController.enableDisableFormRep}"/>
	            	</h:selectBooleanCheckbox>
	            </rich:column>
            	<rich:column style="border:none;width:100px;padding-left:5px;">
	            	<h:outputText value="Triggers"/>
	            </rich:column>
	            <rich:column style="border:none;">
	            	<h:inputText value="#{auditoriaController.strTriggersMenu}" size="90" disabled="#{auditoriaController.formRepTriggersEnabled}"></h:inputText>
	            </rich:column>
	            <rich:column style="border:none;">
	            	<a4j:commandLink actionListener="#{auditoriaController.listarTriggers}" oncomplete="Richfaces.showModalPanel('mpTablas').show()"
	            		reRender="pTablasMenu, tbTablasMenu" immediate="true" disabled="#{auditoriaController.formRepTriggersEnabled}">
                    	<h:graphicImage value="/images/icons/buscar6.jpg" style="border:0px"/>
                    </a4j:commandLink>
		        </rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pRanFecFormRep" columns="8">
            	<rich:column style="border:none;">
	            	<h:selectBooleanCheckbox value="#{auditoriaController.chkRanFecFormRep}">
	            		<a4j:support event="onclick" reRender="pRanFecFormRep" actionListener="#{auditoriaController.enableDisableFormRep}"/>
	            	</h:selectBooleanCheckbox>
	            </rich:column>
            	<rich:column style="border:none;width:120px;padding-left:5px;">
	            	<h:outputText value="Rango de Fecha"/>
	            </rich:column>
	            <rich:column style="border:none;">
	            	<h:selectOneMenu disabled="#{auditoriaController.formRepRanFecEnabled}">
	            	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_RANGOFECHA}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	            </rich:column>
	            <rich:column style="border:none;padding-left:10px;">
		            <h:outputText value="Inicio"/>
		        </rich:column>
		        <rich:column style="border:none;padding-left:10px;">
		            <rich:calendar popup="true" value="#{auditoriaController.daFechaIni}"
		            	disabled="#{auditoriaController.formRepRanFecEnabled}"
	         			datePattern="dd/MM/yyyy" inputStyle="width:80px"
                        cellWidth="10px" cellHeight="20px"/>
		        </rich:column>
		        <rich:column style="border:none;padding-left:10px;">
		            <h:outputText value="Fin"/>
		        </rich:column>
		        <rich:column style="border:none;padding-left:10px;">
		            <rich:calendar popup="true" value="#{auditoriaController.daFechaFin}"
		            	disabled="#{auditoriaController.formRepRanFecEnabled}"
	         			datePattern="dd/MM/yyyy" inputStyle="width:80px"
                        cellWidth="10px" cellHeight="20px"/>
		        </rich:column>
		        <rich:column style="border:none;padding-left:30px;">
		            <h:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{auditoriaController.listarFormReporte}"/>
		        </rich:column>
            </h:panelGrid>
            
            <h:panelGrid>
            	<rich:extendedDataTable id="dtFormReport"
                       	value="#{auditoriaController.beanListFormReport}" 
                       	var="item" sortMode="single" rows="5"
                       	rendered="#{not empty auditoriaController.beanListFormReport}"
                       	rowKeyVar="rowkey" width="700px" height="170px">
	             	<rich:column width="15px">
	              		<f:facet name="header">
	                  		<h:outputText />
	                  	</f:facet>
	                  	<div align="center"><h:outputText value="#{rowkey+1}"/></div>
	              	</rich:column>
	             	<rich:column width="120px">
	              		<f:facet name="header">
	                  		<h:outputText value="Solicitud de Cambio"/>
	                  	</f:facet>
	                  	<div align="center"><h:outputText value="#{item.intIdItem}"/></div>
	              	</rich:column>
	              	<rich:column width="150px">
	              	<f:facet name="header">
	                  	<h:outputText value="Tipo de Cambio"/>
	                  </f:facet>
	                  <h:outputText value="#{item.strTipoCambio}"/>
	              	</rich:column>
	              	<rich:column>
	              		<f:facet name="header">
	                  		<h:outputText value="Estado"/>
	                  	</f:facet>
	                  	<h:outputText value="#{item.strEstado}"/>
	              	</rich:column>
	              	<rich:column>
	              		<f:facet name="header">
	                  		<h:outputText value="Fecha"/>
	                  	</f:facet>
	                  	<h:outputText value="#{item.strFecSolicitud}"/>
	              	</rich:column>
	              	<rich:column>
	              		<f:facet name="header">
	                  		<h:outputText value="Clase"/>
	                  	</f:facet>
	                  	<h:outputText value="#{item.strClase}"/>
	              	</rich:column>
	              	<rich:column>
	              		<f:facet name="header">
	                  		<h:outputText value="Detalle/Anexos"/>
	                  	</f:facet>
	                  	<h:outputText value="#{item.intIdAnexos}"/>
	              	</rich:column>
	              	
	              	<f:facet name="footer">
	              		<rich:datascroller for="dtFormReport" maxPages="10"/>
	              	</f:facet>
	              	
	              	<a4j:support event="onRowClick" actionListener="#{auditoriaController.editFormReport}" reRender="pDescripcion,pDesarrollador,pSolicitante">
			        	<f:param id="strDescripcion" 	name="strDescripcion" 	value="#{item.strDescripcion}"/>
			        	<f:param id="strDesarrollador" 	name="strDesarrollador" value="#{item.strDesarrollador}"/>
			        	<f:param id="strSolicitante" 	name="strSolicitante" 	value="#{item.strSolicitante}"/>
			        </a4j:support>
             	</rich:extendedDataTable>
            </h:panelGrid>
            
            <h:panelGrid id="pDescripcion" columns="2">
            	<rich:column style="width:110px;border:none;padding-left:30px;">
            		<h:outputText value="Descripción"  rendered="#{not empty auditoriaController.beanListFormReport}"/>
            	</rich:column>
            	<rich:column style="width:150px;border:none;">
            		<h:inputText size="100" value="#{auditoriaController.beanFormReport.strDescripcion}" readonly="true" rendered="#{not empty auditoriaController.beanListFormReport}"/>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pDesarrollador" columns="2">
            	<rich:column style="width:110px;border:none;padding-left:30px;">
            		<h:outputText value="Desarrollado por"  rendered="#{not empty auditoriaController.beanListFormReport}"/>
            	</rich:column>
            	<rich:column style="width:150px;border:none;">
            		<h:inputText size="100" value="#{auditoriaController.beanFormReport.strDesarrollador}" readonly="true" rendered="#{not empty auditoriaController.beanListFormReport}"/>
            	</rich:column>
            </h:panelGrid>
            
            <h:panelGrid id="pSolicitante" columns="2">
            	<rich:column style="width:110px;border:none;padding-left:30px;">
            		<h:outputText value="Solicitantes"  rendered="#{not empty auditoriaController.beanListFormReport}"/>
            	</rich:column>
            	<rich:column style="width:150px;border:none;">
            		<h:inputText size="100" value="#{auditoriaController.beanFormReport.strSolicitante}" readonly="true" rendered="#{not empty auditoriaController.beanListFormReport}"/>
            	</rich:column>
            </h:panelGrid>
    	</rich:panel>
     </rich:tab>
    
     <rich:tab label="Data"  style="background-color: #E8F1FF">
         	
            <rich:panel style="border:1px solid #17356f ;width: 755px; background-color:#DEEBF5">                       
             <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;" >

                    <h:panelGrid columns="2">
                    	<rich:column width="140" style="border:none">
                    		<h:outputText value="Histórico de Datos : " ></h:outputText>
                    	</rich:column>
                    	<rich:column style="border:none">
                    		<h:graphicImage value="/images/icons/formDataImg.jpg"/>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="3" id="pgTablas">
                    	<rich:column width="260" style="border:none">
                    		<rich:extendedDataTable id="tblTablas" enableContextMenu="false"
                             value="#{auditoriaSistController.beanListTablas}" var="item" sortMode="single" rows="5" 
					  		 rowKeyVar="rowKey" width="240px" height="194px">
					  		 	
                                <a4j:support event="onRowClick" actionListener="#{auditoriaSistController.listarColumnas}" reRender="pgTablas,tblColumnas">
                                	<f:param id="pTableName" name="pTableName" value="#{item.strNombreObjeto}"></f:param>
                                </a4j:support>
                                
                                <f:facet name="header">
                                	<h:outputText value="Tablas" ></h:outputText>
                                </f:facet>
                                
					  		 	<rich:column width="30px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
					  		 
                                <rich:column width="210px">
                                    <f:facet name="header">
                                        <h:commandButton value="Nombre de Tabla" actionListener="#{auditoriaSistController.listarTablas}" styleClass="btnEstilos1"></h:commandButton>
                                    </f:facet>
                                    <h:outputText value="#{item.strNombreObjeto}"></h:outputText>
                                </rich:column>
                                
								<f:facet name="footer">   
						        	<rich:datascroller for="tblTablas" maxPages="10"/>   
						        </f:facet>
                            </rich:extendedDataTable>
                    	</rich:column>
                    	
                    	<rich:column width="260" style="border:none">
                    		<rich:extendedDataTable id="tblColumnas" enableContextMenu="false"
                             value="#{auditoriaSistController.beanListColumnas}" var="item" sortMode="single" rows="5" 
					  		 onRowClick="jsSetColumnName('#{item.strNombreObjeto}');" rowKeyVar="rowKey" width="240px" height="190px">
                                
                                <f:facet name="header">
                                	<h:outputText  value="Columnas" ></h:outputText>
                                </f:facet>
                                
					  		 	<rich:column width="30px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
					  		 
                                <rich:column width="210px">
                                    <f:facet name="header">
                                        <h:outputText  value="Tabla: #{auditoriaSistController.strNombreTabla}" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strNombreObjeto}"></h:outputText>
                                </rich:column>
                                
								<f:facet name="footer">   
						        	<rich:datascroller for="tblColumnas" maxPages="10"/>   
						        </f:facet>
                            </rich:extendedDataTable>
                    	</rich:column>
                    	
                    	<rich:column style="border:none">
                   			<h:inputHidden id="hiddenNombreColumna"></h:inputHidden>
                    	</rich:column>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="6">
                    	<rich:column style="border:none">
                    		<h:outputText value="Oper Lógico : "/>
                    	</rich:column>
		            	<rich:column style="width: 160px; border:none">
                        	<h:selectOneMenu required="true" style="width: 150px;"
			                	value="#{auditoriaSistController.intCboOperadorLogico}" onchange="jsSetOperadorLogico(this);">
			                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
								<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_OPERADORLOGICO}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				            </h:selectOneMenu>
				            <h:inputHidden id="hiddenOperLogico"></h:inputHidden>
                        </rich:column>
		            	<rich:column style="border:none">
                    		<h:outputText value="Expresión Lógica : " ></h:outputText>
                    	</rich:column>
                    	<rich:column style="border:none">
                    		<h:inputText value="#{auditoriaSistController.strValorColumna}" ></h:inputText>
                    	</rich:column>
                     </h:panelGrid>
                     <rich:spacer height="4px"/><rich:spacer height="4px"/>
                     <h:panelGrid columns="8">
		            	<rich:column width="21" style="border: none">
		            		<h:selectBooleanCheckbox value="#{auditoriaSistController.blnChkFechas}">
		            			<a4j:support event="onclick" actionListener="#{auditoriaSistController.habilitarFiltroFechas}" reRender="colFechaIni,colFechaFin" ajaxSingle="true"/>
		            		</h:selectBooleanCheckbox>
		            	</rich:column>
		            	<rich:column id="colFechaIni" style="border: none">
		               		<rich:calendar value="#{auditoriaSistController.dtAudiDesde}" disabled="#{auditoriaSistController.blnFiltrarFechas}" 
		               		datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 70px" style="width: 170px"/>
		                </rich:column>
		                <rich:column id="colFechaFin" style="border: none">
		               		<rich:calendar value="#{auditoriaSistController.dtAudiHasta}" disabled="#{auditoriaSistController.blnFiltrarFechas}" 
		               		datePattern="dd/MM/yyyy HH:mm" inputStyle="width: 70px" style="width: 170px"/>
		                </rich:column>
		                <rich:column width="21" style="border: none">
		            		<h:selectBooleanCheckbox value="#{auditoriaSistController.blnValoresNull}"/>
		            	</rich:column>
		            	<rich:column style="border:none">
                    		<h:outputText value="Incluir Valores Null : " ></h:outputText>
                    	</rich:column>
                    	<rich:column width="21" style="border: none">
		            		<h:selectBooleanCheckbox value="#{auditoriaSistController.blnValoresCero}"/>
		            	</rich:column>
		            	<rich:column style="border:none">
                    		<h:outputText value="Incluir Valores Cero : " ></h:outputText>
                    	</rich:column>
                        <a4j:commandButton value="Buscar" actionListener="#{auditoriaSistController.buscarHistoricoData}" reRender="pgTblAuditoria" styleClass="btnEstilos"></a4j:commandButton>
                     </h:panelGrid>
                     <rich:spacer height="6px"/><rich:spacer height="6px"/>
                     <h:panelGrid id="pgTblAuditoria">
                     	<rich:dataTable id="tblAudiTablas" rowKeyVar="rowKey" width="705px" 
                             value="#{auditoriaSistController.beanListAudiTablas}" var="item" sortMode="single" rows="5"  
					  		 onRowMouseOver="this.style.backgroundColor='#dfecab'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.tableBackgroundColor}'">
                                
					  		 	<rich:column width="30px">
                                    <h:outputText value="#{rowKey + 1}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Tabla" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strTabla}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Columna" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strColumna}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Valor Antiguo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strValorAnterior}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Valor Nuevo" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strValorNuevo}"></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText  value="Fecha Modificacion" ></h:outputText>
                                    </f:facet>
                                    <h:outputText value="#{item.strFechaRegistro}"></h:outputText>
                                </rich:column>
                                
								<f:facet name="footer">   
						        	<rich:datascroller for="tblAudiTablas" maxPages="10"/>   
						        </f:facet>
                            </rich:dataTable>
                     </h:panelGrid>
                     <rich:spacer height="4px"/><rich:spacer height="4px"/>
                     <h:panelGrid columns="5">
		            	<rich:column width="21" style="border: none">
		            		<h:selectBooleanCheckbox value="#{auditoriaSistController.blnRangoFechas}"></h:selectBooleanCheckbox>
		            	</rich:column>
		            	<rich:column style="border:none">
                    		<h:outputText value="Exportar Resultado a : " ></h:outputText>
                    	</rich:column>
		            	<rich:column style="width: 160px; border:none">
                        	<h:selectOneMenu required="true" style="width: 150px;"
			                	value="#{auditoriaSistController.intCboTipoArchivo}">
			                	<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
			                	<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOARCHIVOS}" 
									itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
				            </h:selectOneMenu>
                        </rich:column>
                        <h:commandButton value="Exportar" actionListener="#{auditoriaSistController.exportarHistoricoData}" styleClass="btnEstilos"></h:commandButton>
                     </h:panelGrid>
                    	
                </rich:panel>
               
         </rich:panel>
     </rich:tab>
         
        </rich:tabPanel>           
   </h:form>