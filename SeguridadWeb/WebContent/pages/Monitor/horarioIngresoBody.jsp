<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Seguridad : Cooperativa El Tumi      -->
	<!-- Autor     : Christian De los Ríos	  -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Horario de Ingreso al Sistema -->
	<!-- Fecha     : 07/11/2011               -->
	<style type="text/css">
	      .active-row {background-color: #DAE8FB !important;
		               cursor: pointer;
		              }
	      .tabla {border: 1px;}
		
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
		  function jsSeleccionHorario(itemIdEmpresa, itemIdTipoSuc, itemStrFecIni){
               document.getElementById("frmHorModalPanel:hiddenIdEmpresa").value=itemIdEmpresa;
               document.getElementById("frmHorModalPanel:hiddenIdTipoSucursal").value=itemIdTipoSuc;
               document.getElementById("frmHorModalPanel:hiddenStrFecIni").value=itemStrFecIni;
	      }
	      
	      function verificaUpload(){
	      	if(document.getElementById('frmPrincipal:upload').component.idCounter == 0){
	      		document.getElementById('frmPrincipal:hiddenStrFileName').value='';
	      	
	      	}else{
		      	if(document.getElementById('frmPrincipal:upload').component.entries[0].state==FileUploadEntry.UPLOAD_SUCCESS){
		      		document.getElementById('frmPrincipal:hiddenStrFileName').value;
		      	} else{
		      		document.getElementById('frmPrincipal:hiddenStrFileName').value='';
		      	}
	      	}
	      }
	      /*
	      function enable_HorarioL(status){ //Lunes
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraLSesion").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosLSesion").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosLSesion").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinL").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinL").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinL").disabled = status;
		  }
		  
		  function enable_HorarioM(status){ //Habilitar - Desabilitar para Martes
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraIniM").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosIniM").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosIniM").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinM").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinM").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinM").disabled = status;
		  }
		  
		  function enable_HorarioMi(status){ //Habilitar - Desabilitar para Miércoles
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraIniMi").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosIniMi").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosIniMi").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinMi").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinMi").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinMi").disabled = status;
		  }
		  
		  function enable_HorarioJ(status){ //Habilitar - Desabilitar para Jueves
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraIniJ").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosIniJ").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosIniJ").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinJ").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinJ").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinJ").disabled = status;
		  }
		  
		  function enable_HorarioV(status){ //Habilitar - Desabilitar para Viernes
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraIniV").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosIniV").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosIniV").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinV").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinV").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinV").disabled = status;
		  }
		  
		  function enable_HorarioS(status){ //Habilitar - Desabilitar para Sábado
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraIniS").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosIniS").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosIniS").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinS").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinS").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinS").disabled = status;
		  }
		  
		  function enable_HorarioD(status){ //Habilitar - Desabilitar para Domingo
		  	status =! status;
	      	document.getElementById("frmPrincipal:cboHoraIniD").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosIniD").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosIniD").disabled = status;
	      	document.getElementById("frmPrincipal:cboHoraFinD").disabled = status;
	      	document.getElementById("frmPrincipal:cboMinutosFinD").disabled = status;
	      	document.getElementById("frmPrincipal:cboSegundosFinD").disabled = status;
		  }
	      */
	</script>

	<rich:modalPanel id="panelUpdateDeleteHorario" width="380" height="120"
	 resizeable="false" style="background-color:#DEEBF5;">
        <f:facet name="header">
            <h:panelGrid>
              <rich:column style="border: none;">
                <h:outputText value="Actualizar/Eliminar Usuario" ></h:outputText>
              </rich:column>
            </h:panelGrid>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
               <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkUsr"/>
               <rich:componentControl for="panelUpdateDeleteHorario" attachTo="hidelinkUsr" operation="hide" event="onclick"/>
           </h:panelGroup>
        </f:facet>
        <h:form id="frmHorModalPanel">
        	<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 680px;">                 
                <h:panelGrid columns="2"  border="0" cellspacing="4" >
                    <h:outputText value="¿Desea Ud. Actualizar o Eliminar un Horario?"  style="padding-right: 35px;"></h:outputText>
                </h:panelGrid>
                <rich:spacer height="16px"/>
                <h:panelGrid columns="2" border="0"  style="width: 200px;">
                    <h:commandButton value="Actualizar" actionListener="#{horarioIngresoController.modificarHorario}" styleClass="btnEstilos"/>
                    <h:commandButton value="Eliminar" actionListener="#{horarioIngresoController.eliminarHorario}" onclick="if (!confirm('Está Ud. Seguro de Eliminar este Registro?')) return false;" styleClass="btnEstilos"/>
                </h:panelGrid>
				<h:inputHidden id="hiddenIdEmpresa"></h:inputHidden>
				<h:inputHidden id="hiddenIdTipoSucursal"></h:inputHidden>
				<h:inputHidden id="hiddenStrFecIni"></h:inputHidden>
             </rich:panel>
             <rich:spacer height="4px"/><rich:spacer height="8px"/>
        </h:form>
    </rich:modalPanel>
	
    <h:form id="frmPrincipal">
    	<rich:panel style="border:1px solid #17356f ;width:760px; background-color:#DEEBF5">
    		<h:panelGrid columns="6" style="width: 740px" border="0" >
	         	<rich:column style="border:none">
	         	    <h:outputText id="lblEmpresa" value="Empresa:" > </h:outputText>
	         	</rich:column>
	         	<h:selectOneMenu value="#{horarioIngresoController.cboEmpresa}" style="width:300px;">
					<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
						itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
				</h:selectOneMenu>
	         	<rich:column style="width:110px;">
	         		<h:outputText id="lblTipoSucursal" value="Tipo de Sucursal:"/>
	         	</rich:column>
	         	<rich:column>
		         	<h:selectOneMenu id="idCboTipoSucursal" value="#{horarioIngresoController.cboTipoSucursal}">
		         		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	         	</rich:column>
	         	<rich:column>
	         		<h:outputText id="lblEstadoUsuario" value="Estado:"/>
	         	</rich:column>
	         	<rich:column>
	         		<h:selectOneMenu value="#{horarioIngresoController.cboEstado}">
						<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
						itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					</h:selectOneMenu>
	         	</rich:column>
	         </h:panelGrid>
	         
	         <rich:spacer height="8px"></rich:spacer>
	         
	         <h:panelGrid>
		     	<a4j:commandButton value="Buscar" styleClass="btnEstilos" actionListener="#{horarioIngresoController.listarHorarios}" reRender="pgFilHorario"/>
		     </h:panelGrid>
		     
		     <rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width:700px;">                                                               
                   <h:panelGrid id="pgFilHorario" columns="1">
                       <rich:extendedDataTable id="tblFilHorarios"
                       	value="#{horarioIngresoController.beanListHorarios}" 
                       	var="item" sortMode="multi" rows="#{horarioIngresoController.rows}"
                       	onRowClick="jsSeleccionHorario(#{item.intIdEmpresa}, #{item.intIdTipoSucursal}, '#{item.daFecIni}');"
                       	rendered="#{not empty horarioIngresoController.beanListHorarios}"
                       	rowKeyVar="rowkey" width="720px" height="170px">
                           <rich:column width="15px">
                               <f:facet name="header">
                                   <h:outputText />
                               </f:facet>
                               <h:outputText value="#{rowkey+1}"/>
                           </rich:column>
                           <rich:column width="140px">
                               <f:facet name="header">
                                   <h:outputText value="Nombre de Empresa"/>
                               </f:facet>
                               <h:outputText value="#{item.strEmpresa}"/>
                           </rich:column>
                           <rich:column>
                               <f:facet name="header">
                                   <h:outputText value="Tipo Sucursal"/>
                               </f:facet>
                               <h:outputText value="#{item.strTipoSucursal}"/>
                           </rich:column>
                           
                           <rich:column width="120px">
                               <f:facet name="header">
                                   <h:outputText value="Fecha de Inicio"/>
                               </f:facet>
                               <h:outputText value="#{item.daFecIni}"/>
                           </rich:column>
                           <rich:column width="110px">
                               <f:facet name="header">
                                   <h:outputText value="Fecha de Fin"/>
                               </f:facet>
                               <h:outputText value="#{item.daFecFin}"/>
                           </rich:column>
                           
                           <rich:column width="130px">
                               <f:facet name="header">
                                   <h:outputText value="Archivo"/>
                               </f:facet>
                               <h:commandLink value="#{item.strAdjunto}" actionListener="#{horarioIngresoController.downloadOks}" style="color:blue">
                               		<f:param id="paramAdj" name="paramAdj" value="#{item.strAdjunto}" />
                               </h:commandLink>
                           </rich:column>
                           
                           <rich:column>
                               <f:facet name="header">
                                   <h:outputText value="Estado"/>
                               </f:facet>
                               <h:outputText value="#{item.strEstado}"/>
                           </rich:column>
                           
                           <f:facet name="footer">
                           		<rich:datascroller for="tblFilHorarios" maxPages="10"/>
                           </f:facet>
                       </rich:extendedDataTable>
                       
                       <h:panelGrid columns="2">
					   	<h:outputLink value="#" id="linkPanelHorario">
					        <h:graphicImage value="/images/icons/mensaje1.jpg"
								style="border:0px"/>
					        <rich:componentControl for="panelUpdateDeleteHorario" attachTo="linkPanelHorario" operation="show" event="onclick"/>
					    </h:outputLink>
						<h:outputText value="Para Eliminar o Actualizar Hacer click en Registro" style="color:#8ca0bd" ></h:outputText>                                     
						</h:panelGrid>
                </h:panelGrid>
	        </rich:panel>
	        
	        <h:panelGrid columns="3">
	        	<h:commandButton value="Nuevo" actionListener="#{horarioIngresoController.habilitarGrabarHorario}" styleClass="btnEstilos"/>                     
	            <h:commandButton id="btnGuardarHorario" value="Guardar" actionListener="#{horarioIngresoController.grabarHorario}" onclick="verificaUpload();" styleClass="btnEstilos"/>												                 
	            <h:commandButton value="Cancelar" actionListener="#{horarioIngresoController.cancelarGrabarHorario}" styleClass="btnEstilos"/>
           	</h:panelGrid>
           	
    		<rich:spacer height="4px"/><rich:spacer height="4px"/>
    		
    		<rich:panel id="pMarco3" style="width: 730px;border:1px solid #17356f;background-color:#DEEBF5;" rendered="#{horarioIngresoController.horarioRendered}">
           		<h:panelGrid columns="4" border="0" >
	               <rich:column style="width: 120px; border: none">
	                  <h:outputText value="Empresa" style="padding-left:10px" ></h:outputText>
	               </rich:column>
	               <rich:column style="border:none;">
	                  <h:outputText value=":" ></h:outputText>
	               </rich:column>
	               <rich:column style="border: none">
	                  	<h:selectOneMenu id="cboEmpresasPermiso" 
                       	style="width: 300px;" disabled="#{horarioIngresoController.formHorarioEnabled}"
                       	value="#{horarioIngresoController.strCboEmpresa}">
					        <f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
							<tumih:selectItems var="sel" value="#{controllerFiller.listEmpresa}"
								itemValue="#{sel.intIdPersona}" itemLabel="#{sel.strRazonsocial}"/>
						</h:selectOneMenu>
	               </rich:column>
	               <rich:column style="width: 200px; border: none">
	                    <h:outputText value="#{horarioIngresoController.msgEmpresa}" styleClass="msgError"></h:outputText>
	               </rich:column>
	            </h:panelGrid>
	            <rich:spacer height="8px"></rich:spacer>
	            
	            <h:panelGrid columns="4" border="0" >
	               <rich:column style="width: 120px; border: none">
	                  <h:outputText value="Tipo Sucursal" style="padding-left:10px" ></h:outputText>
	               </rich:column>
	               <rich:column style="border:none;">
	                  <h:outputText value=":" ></h:outputText>
	               </rich:column>
	               <rich:column style="border: none">
	               		<h:selectOneMenu value="#{horarioIngresoController.beanHorario.intIdTipoSucursal}"  disabled="#{horarioIngresoController.formHorarioEnabled}">
					   		<f:selectItem itemLabel="Seleccionar.." itemValue="0"/>
					   		<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOSUCURSAL}" 
								itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
					    </h:selectOneMenu>
	               </rich:column>
	               <rich:column style="border: none;">
                       	<h:outputText value="#{horarioIngresoController.msgTipoSucursal}" styleClass="msgError"></h:outputText>
	               </rich:column>
	            </h:panelGrid>
	            <rich:spacer height="8px"></rich:spacer>
	            
	            <h:panelGrid columns="2" border="0">
	            	<rich:column style="border:none;padding-left:10px;">
	                    <h:selectBooleanCheckbox value="#{horarioIngresoController.beanHorario.chkFeriado}" disabled="#{horarioIngresoController.formHorarioEnabled}"/>
	                </rich:column>
	                <rich:column style="border:0px;padding-left:5px;">
	               		<h:outputText value="Considerar Feriados de Calendario" ></h:outputText>
	                </rich:column>
	            </h:panelGrid>
	            <rich:spacer height="8px"></rich:spacer>
	            
	            <h:panelGrid id="pFechas" columns="7" border="0" width="700px">
	            	<rich:spacer width="10px"></rich:spacer>
	            	<rich:column style="border:0px;width:110px;">
	            		<h:outputText value="Fecha de Inicio" ></h:outputText>
	            	</rich:column>
	            	<rich:column style="border:0px;">
	            		<h:outputText value=":"></h:outputText>
	            	</rich:column>
	            	<rich:column style="border:0px;width:145px;">
	                    <rich:calendar popup="true"
	                    	value="#{horarioIngresoController.daFechaHorarioIni}"
                    		disabled="#{horarioIngresoController.formHorarioEnabled}"
	                        datePattern="dd/MM/yyyy"
	                        cellWidth="10px" cellHeight="20px" style="width:200px"/>
	                </rich:column>
	                <rich:column style="border:0px;padding-left:30px;">
	                	<h:selectOneRadio id="radPerfUsu" value="#{horarioIngresoController.rbPerfUsu}" valueChangeListener="#{horarioIngresoController.showFecFin}">
	                  		<f:selectItem itemValue="1" itemLabel="Fechas"/>
	                  		<f:selectItem itemValue="2" itemLabel="Indeterminado"/>
				            <a4j:support event="onclick" reRender="pFechas" ajaxSingle="true"/>
	                   	</h:selectOneRadio>
	                </rich:column>
	                <rich:column style="border:0px;">
	            		<h:outputText value="Fecha de Fin :" />
	            	</rich:column>
	                <rich:column style="border:0px;">
	                    <rich:calendar popup="true"
	                    	value="#{horarioIngresoController.daFechaHorarioFin}"
                    		disabled="#{horarioIngresoController.formHorarioEnabled2}"
                    		rendered="#{horarioIngresoController.fecFinHorarioRendered}"
	                        datePattern="dd/MM/yyyy"
	                        cellWidth="10px" cellHeight="20px" style="width:200px"/>
	                </rich:column>
	            </h:panelGrid>
            	<rich:column style="border: none;">
                   	<h:outputText value="#{horarioIngresoController.msgFechaInicio}" styleClass="msgError"></h:outputText>
               	</rich:column>
	            <rich:spacer height="10px;"></rich:spacer>
	            
	            <rich:panel id="pLunes" style="width:700px;border:1px solid #6699FF;border-bottom-style:none;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox id="chkLunes" value="#{horarioIngresoController.chkLunes}">
			                    	<a4j:support event="onclick" reRender="pLunes" actionListener="#{horarioIngresoController.enableDisable}" />
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Lunes" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraLSesion"    
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioLEnabled}"
		                         	value="#{horarioIngresoController.strHoraIniL}">       
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>   
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosLSesion"    
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioLEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniL}">       
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>   
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosLSesion"    
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioLEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniL}">       
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>   
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinL"    
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioLEnabled}"
		                         	value="#{horarioIngresoController.strHoraFinL}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinL"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioLEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinL}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosFinL"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioLEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinL}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtLunes}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:panel id="pMartes" style="width:700px;border:1px solid #6699FF;border-bottom-style:none;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox value="#{horarioIngresoController.chkMartes}">
			                    	<a4j:support event="onclick" reRender="pMartes" actionListener="#{horarioIngresoController.enableDisable}" />
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Martes" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraIniM"    
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioMEnabled}"
		                         	value="#{horarioIngresoController.strHoraIniM}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosIniM"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioMEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniM}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosIniM"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioMEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniM}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinM"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioMEnabled}"
		                         	value="#{horarioIngresoController.strHoraFinM}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinM"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioMEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinM}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu> 
		                      	<h:selectOneMenu id="cboSegundosFinM"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioMEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinM}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtMartes}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:panel id="pMiercoles" style="width:700px;border:1px solid #6699FF;border-bottom-style:none;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox value="#{horarioIngresoController.chkMiercoles}">
			                    	<a4j:support event="onclick" reRender="pMiercoles" actionListener="#{horarioIngresoController.enableDisable}"/>
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Miércoles" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraIniMi"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioMiEnabled}"
		                         	value="#{horarioIngresoController.strHoraIniMi}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosIniMi"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioMiEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniMi}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosIniMi"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioMiEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniMi}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinMi" 
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioMiEnabled}"
		                         	value="#{horarioIngresoController.strHoraFinMi}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinMi"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioMiEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinMi}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosFinMi"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioMiEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinMi}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtMiercoles}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:panel id="pJueves" style="width:700px;border:1px solid #6699FF;border-bottom-style:none;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox value="#{horarioIngresoController.chkJueves}">
			                    	<a4j:support event="onclick" reRender="pJueves" actionListener="#{horarioIngresoController.enableDisable}"/>
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Jueves" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraIniJ"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioJEnabled}" 
		                         	value="#{horarioIngresoController.strHoraIniJ}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosIniJ"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioJEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniJ}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosIniJ"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioJEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniJ}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinJ"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioJEnabled}"
		                         	value="#{horarioIngresoController.strHoraFinJ}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinJ"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioJEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinJ}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosFinJ"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioJEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinJ}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtJueves}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:panel id="pViernes" style="width:700px;border:1px solid #6699FF;border-bottom-style:none;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox value="#{horarioIngresoController.chkViernes}">
			                    	<a4j:support event="onclick" reRender="pViernes" actionListener="#{horarioIngresoController.enableDisable}"/>
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Viernes" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraIniV"    
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioVEnabled}"
		                         	value="#{horarioIngresoController.strHoraIniV}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosIniV"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioVEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniV}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosIniV"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioVEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniV}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinV"    
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioVEnabled}"
		                         	value="#{horarioIngresoController.strHoraFinV}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinV"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioVEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinV}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosFinV"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioVEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinV}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtViernes}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:panel id="pSabado" style="width:700px;border:1px solid #6699FF;border-bottom-style:none;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox value="#{horarioIngresoController.chkSabado}">
			                    	<a4j:support event="onclick" reRender="pSabado" actionListener="#{horarioIngresoController.enableDisable}"/>
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Sábado" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraIniS"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioSEnabled}"
		                         	value="#{horarioIngresoController.strHoraIniS}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosIniS"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioSEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniS}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosIniS"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioSEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniS}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinS"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioSEnabled}" 
		                         	value="#{horarioIngresoController.strHoraFinS}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinS"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioSEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinS}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosFinS"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioSEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinS}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtSabado}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:panel id="pDomingo" style="width:700px;border:1px solid #6699FF;background-color:#DEEBF5;" >
	            	<div align="center">
		            	<h:panelGrid columns="7" border="0">
			            	<rich:column style="border:none;">
			                    <h:selectBooleanCheckbox value="#{horarioIngresoController.chkDomingo}">
			                    	<a4j:support event="onclick" reRender="pDomingo" actionListener="#{horarioIngresoController.enableDisable}"/>
			                    </h:selectBooleanCheckbox>
			                </rich:column>
			                <rich:column style="width:90px; border:0px; padding-left:5px;">
			               		<h:outputText value="Domingo" />
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Inicio :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraIniD"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioDEnabled}"
		                         	value="#{horarioIngresoController.strHoraIniD}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosIniD"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioDEnabled}"
		                         	value="#{horarioIngresoController.strMinutosIniD}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosIniD"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioDEnabled}"
		                         	value="#{horarioIngresoController.strSegundosIniD}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:80px; padding-left:10px;">
			               		<h:outputText value="H. Fin :" />
			                </rich:column>
			                <rich:column style="border:0px;">
			                    <h:selectOneMenu id="cboHoraFinD"
		                         	label="Hora" disabled="#{horarioIngresoController.formHorarioDEnabled}"
		                         	value="#{horarioIngresoController.strHoraFinD}">
						            <f:selectItems value="#{mbTimePicker.hora}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboMinutosFinD"
		                         	label="Minutos" disabled="#{horarioIngresoController.formHorarioDEnabled}"
		                         	value="#{horarioIngresoController.strMinutosFinD}">
						            <f:selectItems value="#{mbTimePicker.minutos}"></f:selectItems>
						        </h:selectOneMenu>
		                      	<h:selectOneMenu id="cboSegundosFinD"
		                         	label="Segundos" disabled="#{horarioIngresoController.formHorarioDEnabled}"
		                         	value="#{horarioIngresoController.strSegundosFinD}">
						            <f:selectItems value="#{mbTimePicker.segundos}"></f:selectItems>
						        </h:selectOneMenu>
			                </rich:column>
			                <rich:column style="border:0px; width:100px;">
			               		<h:outputText value="#{horarioIngresoController.msgTxtDomingo}" styleClass="msgError"/>
			                </rich:column>
			            </h:panelGrid>
		            </div>
	            </rich:panel>
	            
	            <rich:spacer height="5px"></rich:spacer>
	            
	            <h:panelGrid border="0">
		            <h:outputText value="Motivo:" />
		            <rich:fileUpload id="upload" addControlLabel="Agregar Archivo"
		             disabled="#{horarioIngresoController.formHorarioEnabled}"
		             clearAllControlLabel="Limpiar Todos" clearControlLabel="Limpiar"
		             uploadControlLabel="Subir Archivo" listHeight="65"
		             fileUploadListener="#{fileUploadController.listener}"
					 maxFilesQuantity="1" 
					 onupload="document.getElementById('frmPrincipal:hiddenStrFileName').value=event.memo.entry.fileName;"
					 immediateUpload="#{fileUploadController.autoUpload}"
					 acceptedTypes="#{fileUploadController.acceptedTypes}"
					 allowFlash="#{fileUploadController.useFlash}">
					 <f:facet name="label">
					 	<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
					 </f:facet>
					</rich:fileUpload>
					<h:inputHidden id="hiddenStrFileName"></h:inputHidden>
					<h:inputHidden id="hiddenStrAdjunto"></h:inputHidden>
	            </h:panelGrid>
	            
	            <rich:spacer height="10px"></rich:spacer>
	            
	            <h:panelGrid columns="2" border="0" rendered="#{horarioIngresoController.strAdjuntoRendered}">
	            	<rich:column style="width:80px;border:none;">
		            	<h:outputText value="Archivo:" />
		            </rich:column>
		            <rich:column style="border:none;">
		            	<h:inputText value="#{horarioIngresoController.beanHorario.strAdjunto}" size="80"  readonly="true"/>
		            </rich:column>
	            </h:panelGrid>
	            
           	</rich:panel>
    		
    	</rich:panel>
   	</h:form>