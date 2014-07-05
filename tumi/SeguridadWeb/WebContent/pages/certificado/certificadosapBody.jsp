	<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
	<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
	<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
	<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
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
      function validarNum(e){
               tecla = (document.all) ? e.keyCode : e.which;
    		   if (tecla == 8) 
    		       return true;
   			   patron = /\d/;
    		   te = String.fromCharCode(tecla);
	  return patron.test(te);
     }
      function disableCheck(field, causer) {
		      if (causer.checked) {
			     field.checked = true;
	          }else {
		           field.checked = false;
		      }
	  }
      function DesactivarPeriodica(field) {
	           disableCheck(frmMonitoreo.checkHeader, field);
	  }
	  function DesactivarSuspencion(field) {
	          disableCheck(frmMonitoreo.checkOrden, field);
	  }
      function validarCheck(form){		
	          if(form.checkHeader.checked==true){			
			     c=parseInt("0");
			     z=parseInt("100");			
				 while(z>c){
				       form.ordenCheck[c].checked=true;			
				       form.ordenCheck[c].value=form.txtCodigo.value;			
				       c++;
				  }			 
		      }else{c=parseInt("0");			 
			        z=parseInt("100");			 
					while(z>c){
						  form.ordenCheck[c].checked=false;
						  c++;
			        }
		       }		
      }	
      /*Funcion para seleccionar todos los check*/	
      function checkUncheckAll(checkBoxGeneral) {	
	          var checkBoxGeneralState = checkBoxGeneral.checked;
			  if(checkBoxGeneralState) {
				 for(i=0; i<document.frmMonitoreo.elements.length; i++) {
					if(document.frmMonitoreo.elements[i].type=="checkbox") {
						document.frmMonitoreo.elements[i].checked = true;
					}
				 }
			  }else {
		             for(i=0; i<document.frmMonitoreo.elements.length; i++) {
						if(document.frmMonitoreo.elements[i].type=="checkbox") {
						   document.frmMonitoreo.elements[i].checked = false;
			              }
		             }
	           }
      }
      
   </script>	
		<a4j:form id="frmMonitoreo">
		<rich:panel id="panelEdit">
		<f:facet name="header">
		      <h:outputText value="Consulta Certificados" />
		    </f:facet>		
			<h:panelGrid columns="2" id="pgParametros">
				<h:outputText value="Usuario SAP: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.usuario}"  id="somListaSistema1"   disabled="#{CertificadoController.blouqearUsuario}"   maxlength="30" size="30" />
				
				<h:outputText value="Fecha Inicio: "  styleClass="spacio" />
				<rich:calendar datePattern="dd/MM/yyyy"  value="#{CertificadoController.fechaInicio}"  /> 
				
				<h:outputText value="Fecha Fin: "  styleClass="spacio" />
				<rich:calendar datePattern="dd/MM/yyyy"  value="#{CertificadoController.fechaFin}"  />			
				<h:outputText value="Lista de Certificados: " styleClass="spacio" />
				<h:inputTextarea value ="#{CertificadoController.certificado}"    rows="10" >				
				</h:inputTextarea>
				
				<h:outputText value="Lista moneda: " styleClass="spacio"/>
				<h:selectOneMenu value="#{CertificadoController.monedaElegida}" id="somSistema" >
					<f:selectItems value="#{CertificadoController.listaMoneda}" />
				</h:selectOneMenu>																
			</h:panelGrid>
			
			<h:panelGrid columns="2" id="pgBotones">
				<h:commandButton value="Ejecutar" actionListener= "#{CertificadoController.execute}"  id="btnEjecutar" >
				<rich:toolTip for="btnEjecutar" value="Ejecutar" />				
				</h:commandButton>
				<h:commandButton value="Limpiar" actionListener= "#{CertificadoController.limpiar}"  id="btnEjecutar12" >
				<rich:toolTip for="btnEjecutar12" value="Limpiar" />
				</h:commandButton>
			</h:panelGrid>
				
		<rich:datascroller for="rdt1" maxPages="20" rendered="#{not empty CertificadoController.beanList}"/>
		<div style="height:440px ; width:100%; overflow-x:auto; overflow-y:hidden">
		
			<h:panelGrid columns="1" id="pgDatatable3">	 							
					<rich:dataTable id="rdt1" rows="#{CertificadoController.rows}" value="#{CertificadoController.beanList}" var="item"  width="100%" 
					  dir="LTR" frame="hsides" rules="all" rowKeyVar="rowKey"  cellspacing="5" rendered="#{not empty CertificadoController.beanList}" >
						<f:facet name="header">
							<h:outputText value="Listado de Certificados" />
						</f:facet>											
											
						<rich:column>				
						<f:facet name="header">							
							<h:selectBooleanCheckbox id="checkAll"  value="#{CertificadoController.chekAll}">
		       					<a4j:support event="onclick" action="#"  onsubmit="checkUncheckAll(this)" actionListener="#{CertificadoController.seleccionarCheck}" reRender="total,pgTotales" />	
							</h:selectBooleanCheckbox>						  					
						</f:facet>					  						
						<h:selectBooleanCheckbox  value="#{item.seleccionado}"  >
							<a4j:support event="onclick" reRender="total,pgTotales" actionListener="#{CertificadoController.seleccionarCheck}"/>
						</h:selectBooleanCheckbox>
						</rich:column>										
																																
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Nro Correlativo" />
							</f:facet>
							<h:outputText value="#{item.id}"></h:outputText> 
						</rich:column>
															
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Sociedad" />
							</f:facet>
							<h:outputText value="#{item.sociedad}"></h:outputText> 
						</rich:column>
						
						<rich:column sortable="true">
							<f:facet name="header">
								<h:outputText value="Número Pedido" />
							</f:facet>
								<h:outputText value="#{item.numeroPedido}"></h:outputText>
						</rich:column>		
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Posición Pedido" />
							</f:facet>
							<h:outputText value="#{item.posicionPedido}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Cantidad" />
							</f:facet>
							<h:outputText value="#{item.cantidad}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Moneda Origen SAP" />
							</f:facet>
							<h:outputText value="#{item.moneda}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Importe de Pedido" />
							</f:facet>
							<h:outputText value="#{item.importe}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Condición de Pago" />
							</f:facet>
							<h:outputText value="#{item.condicionPago}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Pos. Pedido Bloqueado?" />
							</f:facet>
							<h:outputText value="#{item.posicionPedidoBloqueado}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value=" Identificador Fiscal Acreedor" />
							</f:facet>
							<h:outputText value="#{item.ruc}"></h:outputText>
						</rich:column>
												
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Código Acreedor SAP" />
							</f:facet>
							<h:outputText value="#{item.codigoAcreedor}"></h:outputText>
						</rich:column>

						<rich:column >
							<f:facet name="header">
								<h:outputText value="Nombre Acreedor SAP" />
							</f:facet>
							<h:outputText value="#{item.nombreAcreedor}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Factura Recibida" />
							</f:facet>
							<h:outputText value="#{item.facturaRecibida}"></h:outputText>
						</rich:column>
						
							<rich:column >
							<f:facet name="header">
								<h:outputText value="Status de Proveedor" />
							</f:facet>
							<h:outputText value="#{item.statusProveedor}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Número de Certificación" />
						</f:facet>
							<h:outputText value="#{item.numeroCertificacion}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Cantidad Certificada" />
							</f:facet>
							<h:outputText value="#{item.cantidadCertificada}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Importe Certificado" />
							</f:facet>
							<h:outputText id="lblImporteCertificado" value="#{item.importeCertificado}">							
							</h:outputText>
						</rich:column>											
									
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Fecha de Creación Certificación" />
							</f:facet>
							<h:outputText value="#{item.fechaCreacion}"></h:outputText>
					   </rich:column>			
						<rich:column>
							<f:facet name="header">
								<h:outputText value="Nro de Días de Certificación" />
							</f:facet>
							<h:outputText value="#{item.numeroDiasCertificacion}"></h:outputText>
					   </rich:column>											
										
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Nro de Doc Anulación de Certificación" />
							</f:facet>
							<h:outputText value="#{item.numeroDocumentoAnulacion}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Fecha de Anulación de Certificación" />
							</f:facet>
							<h:outputText value="#{item.fechaAnulacion}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Nro Doc Compensado" />
							</f:facet>
							<h:outputText value="#{item.numeroCompensado}"></h:outputText>
						</rich:column>
						
						<rich:column >
							<f:facet name="header">
								<h:outputText value="Fecha de Conpensación" />
							</f:facet>
							<h:outputText value="#{item.fechaCompensacion}"></h:outputText>
					    </rich:column>
					
					   <rich:column >
							<f:facet name="header">
								<h:outputText value="Fecha de Vencimiento de Pago1" />
							</f:facet>
							<h:outputText value="#{item.fechaVencimientoPago1}"></h:outputText>
					    </rich:column>
					    
					    <rich:column >
							<f:facet name="header">
								<h:outputText value="Fecha de Vencimiento de Pago2" />
							</f:facet>
							<h:outputText value="#{item.fechaVencimientoPago2}"></h:outputText>
					    </rich:column>
					
					    <rich:column >
							<f:facet name="header">
								<h:outputText value="Código Usuario SAP" />
							</f:facet>
							<h:outputText value="#{item.usuarioSap}"></h:outputText>
					    </rich:column>
					    
					     <rich:column >
							<f:facet name="header">
								<h:outputText value="Nombre Usuario SAP" />
							</f:facet>
							<h:outputText value="#{item.nombreSap}"></h:outputText>
					    </rich:column>
					    
					     <rich:column >
							<f:facet name="header">
								<h:outputText value="Centro de Costo" />
							</f:facet>
							<h:outputText value="#{item.centroCostos}"></h:outputText>
					    </rich:column>
					    
							<rich:column>
							<f:facet name="header">
								<h:outputText value="Ver Detalle" />
							</f:facet>
							<h:commandLink action="editarCertificado" id="verDetalle"
								actionListener="#{CertificadoController.verDetalle}" immediate="true">
								<h:graphicImage value="/images/icons/edit2_20.png"
									style="border:0px" title="Descargar Archivos"/>
								<f:param name="sid" id="sid1" value="#{item.id}" />
								<rich:toolTip for="verDetalle" value="Ver Detalle"/>
							</h:commandLink>
							
						</rich:column>
						<rich:column>
						<f:facet name="header">
							<h:outputText value="Ver Adjuntos" />
						</f:facet>
						<h:commandLink action="carga" id="RolLink"
							actionListener="#{usuarioController.loadRol}" immediate="true">
							<h:graphicImage value="/images/icons/add_20.png" 
									style="border:0px;align:center"  />
							<f:param name="sid3" id="sid3" value="#{item.id}" />
							<rich:toolTip for="RolLink" value="Ver Adjuntos"/>
						</h:commandLink>
					</rich:column>									  
																					
					</rich:dataTable>
					<a4j:outputPanel id="outputPanelZebra" ajaxRendered="true">
                                      <rich:jQuery selector="#rdt1 tr:odd" query="addClass('odd-row')" />
            <rich:jQuery selector="#rdt1 tr:even" query="addClass('even-row')" />
            <rich:jQuery selector="#rdt1 tr" 
                query="mouseover(function(){jQuery(this).addClass('active-row')})"/>
            <rich:jQuery selector="#rdt1 tr" 
                query="mouseout(function(){jQuery(this).removeClass('active-row')})"/>
                                </a4j:outputPanel>
			</h:panelGrid>			
					
		</div>		
		</rich:panel>
	<rich:panel>
	      <f:facet name="header">
	        <h:outputText value="Totales de  Certificados" />
	      </f:facet>	
		<!-- panelGrid de  Totales de Certificados -->
	<h:panelGrid columns="4" border ="0" id= "total" >        
     <h:panelGrid columns="2">
				<h:outputText value="Importe Total: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.totalCertificados}" readonly="true" size="10" 
						style="background-color: #D0D0D0" id="id1">
						<f:validateLength minimum="1" maximum="8" />
					</h:inputText>
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				<h:outputText value="Descuentos: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.totalDescuentos}" readonly="true" size="10"  
						style="background-color: #D0D0D0" id="id3">
						<f:validateLength minimum="1" maximum="8" />
					</h:inputText>
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				<h:outputText value="Importe Neto a Pagar: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.totalImporteNeto}" readonly="true" size="10" 
						style="background-color: #D0D0D0" id="id4">
						<f:validateLength minimum="1" maximum="8" />
					</h:inputText>
       </h:panelGrid>
       <!-- panelGrid de  Descuentos de Certificados -->
       <h:panelGrid columns="2">
				<h:outputText value="Cuentas Por Pagar: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.totalImporteDescuentos1}"  readonly="true" size="10" 
						style="background-color: #D0D0D0" id="id88">
						<f:validateLength minimum="1" maximum="8" />
					</h:inputText>			
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
						
       </h:panelGrid>
        <h:panelGrid columns="2">
				<h:outputText value="Penalidad: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.totalImporteDescuentos2}"  readonly="true" size="10" 
						style="background-color: #D0D0D0" id="a11">
						<f:validateLength minimum="1" maximum="6" />
					</h:inputText>										
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				
								
       </h:panelGrid>
        <h:panelGrid columns="2">
				<h:outputText value="Nota de Crédito: " styleClass="spacio" />
				<h:inputText value="#{CertificadoController.totalImporteDescuentos3}" readonly="true" size="10" 
						style="background-color: #D0D0D0" id="b11">
						<f:validateLength minimum="1" maximum="6" />
					</h:inputText>							
				
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				
       </h:panelGrid>
     </h:panelGrid> 
      
		<h:panelGrid columns="2" id ="pgTotales">
			<h:commandButton actionListener="#{CertificadoController.imprimir}"  disabled="#{CertificadoController.habilitarImpresion}"   value ="Imprimir"></h:commandButton>
			<h:commandButton action="exportarCertificado" actionListener="#{CertificadoController.exportar}" value ="Exportar"></h:commandButton>					
		</h:panelGrid>	
	</rich:panel>	
				
		</a4j:form>