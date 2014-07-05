<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

	<!-- Company:ASIS TP Autor:ev -->
	
	<script type="text/javascript">	
	
	function marcarSap(chkTodoSap,form){
		var valor=chkTodoSap.checked
		document.getElementById(form.name + ':id' ).checked=valor;
		document.getElementById(form.name + ':sociedad' ).checked=valor;
		document.getElementById(form.name + ':numeroPedido' ).checked=valor;
		document.getElementById(form.name + ':posicionPedido' ).checked=valor;
		document.getElementById(form.name + ':cantidad' ).checked=valor;
		document.getElementById(form.name + ':moneda' ).checked=valor;
		document.getElementById(form.name + ':importe' ).checked=valor;
		document.getElementById(form.name + ':condicionPago' ).checked=valor;
		document.getElementById(form.name + ':posicionPedidoBloqueado' ).checked=valor;
		document.getElementById(form.name + ':codigoAcreedor' ).checked=valor;
		document.getElementById(form.name + ':ruc' ).checked=valor;
		document.getElementById(form.name + ':nombreAcreedor' ).checked=valor;
		document.getElementById(form.name + ':statusProveedor' ).checked=valor;
		document.getElementById(form.name + ':numeroCertificacion' ).checked=valor;
		document.getElementById(form.name + ':cantidadCertificada' ).checked=valor;
		document.getElementById(form.name + ':importeCertificado' ).checked=valor;
		document.getElementById(form.name + ':fechaCreacion' ).checked=valor;
		document.getElementById(form.name + ':numeroDiasCertificacion' ).checked=valor;
		document.getElementById(form.name + ':numeroDocumentoAnulacion' ).checked=valor;
		document.getElementById(form.name + ':fechaAnulacion' ).checked=valor;
		document.getElementById(form.name + ':numeroCompensado' ).checked=valor;
		document.getElementById(form.name + ':fechaCompensacion' ).checked=valor;
		document.getElementById(form.name + ':fechaVencimientoPago1' ).checked=valor;
		document.getElementById(form.name + ':fechaVencimientoPago2' ).checked=valor;
		document.getElementById(form.name + ':usuarioSap' ).checked=valor;
		document.getElementById(form.name + ':nombreSap' ).checked=valor;
		document.getElementById(form.name + ':centroCostos' ).checked=valor;		  				
	}
		function marcarGestor(chkTodoGestor,form){
		var valor=chkTodoGestor.checked
		document.getElementById(form.name + ':tieneDescuento' ).checked=valor;
		document.getElementById(form.name + ':importeDescuento1' ).checked=valor;
		document.getElementById(form.name + ':importeDescuento2' ).checked=valor;
		document.getElementById(form.name + ':importeDescuento3' ).checked=valor;
		document.getElementById(form.name + ':importeNetoCertificacion' ).checked=valor;
		document.getElementById(form.name + ':contratoFirmado' ).checked=valor;
		document.getElementById(form.name + ':desctTipoGarantia' ).checked=valor;
		document.getElementById(form.name + ':fechaRecepcion' ).checked=valor;
		document.getElementById(form.name + ':observacionGestor' ).checked=valor;				  				
	}			
	</script>

<!-- Declaro El Formulario Para  Certificado SAP -->
<h:form id="frmCertificado">
	<br />
			<rich:panel >
			<f:facet name="header">
				<h:outputText value = "Exportación de Certificado - SAP" style="text-align:left"> </h:outputText>
				
			</f:facet>
								
			<h:panelGrid columns="6" border = "0" >
			
			   <h:panelGrid columns="2">
				<h:outputText value="Seleccionar Todo:" styleClass="spacio" />
		       </h:panelGrid>
		        <h:panelGrid columns="2">					
				<h:selectBooleanCheckbox id="chkTodoSap" onclick="marcarSap(this, this.form)"></h:selectBooleanCheckbox>														
		       </h:panelGrid>
			   
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			   
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			   	  
				<rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				
				<h:panelGrid columns="2">
						<h:outputText value="Nro Correlativo:" styleClass="spacio" />
		       </h:panelGrid>
		        <h:panelGrid columns="2">					
				<h:selectBooleanCheckbox id="id" value="#{CertificadoController.mapaColumnas.id.visible}"></h:selectBooleanCheckbox>											
		       </h:panelGrid>
		        <h:panelGrid columns="2">
						<h:outputText value="Sociedad: " styleClass="spacio" />															
		       </h:panelGrid>
		        <h:panelGrid columns="2">						
						<h:selectBooleanCheckbox id="sociedad" value="#{CertificadoController.mapaColumnas.sociedad.visible}"></h:selectBooleanCheckbox>											
		       </h:panelGrid>
		       
		        <h:panelGrid columns="2">
						<h:outputText value="Nro Pedido: " styleClass="spacio" />																
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="numeroPedido" value="#{CertificadoController.mapaColumnas.numeroPedido.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				
		       <h:panelGrid columns="2">
						<h:outputText value="Posición Pedido: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="posicionPedido" value="#{CertificadoController.mapaColumnas.posicionPedido.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		      
		       	
				<h:panelGrid columns="2">
						<h:outputText value="Cantidad: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="cantidad" value="#{CertificadoController.mapaColumnas.cantidad.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
				<h:panelGrid columns="2">
						<h:outputText value="Moneda Origen SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="moneda" value="#{CertificadoController.mapaColumnas.moneda.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		        <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
		      
		       <h:panelGrid columns="2">
						<h:outputText value="Importe Pedido: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="importe" value="#{CertificadoController.mapaColumnas.importe.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		       <h:panelGrid columns="2">
						<h:outputText value="Condición de Pago " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="condicionPago" value="#{CertificadoController.mapaColumnas.condicionPago.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>		   
				
			  <h:panelGrid columns="2">
						<h:outputText value="Pos. Pedido Bloqueado?: " styleClass="spacio" />													
		       </h:panelGrid>
		        <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="posicionPedidoBloqueado" value="#{CertificadoController.mapaColumnas.posicionPedidoBloqueado.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		        <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
					
				<h:panelGrid columns="2">
						<h:outputText value="Código Acreedor SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="codigoAcreedor" value="#{CertificadoController.mapaColumnas.codigoAcreedor.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       <h:panelGrid columns="2">
						<h:outputText value="Identificador Fiscal SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="ruc" value="#{CertificadoController.mapaColumnas.ruc.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
				 <h:panelGrid columns="2">
						<h:outputText value="Nombre Acreedor SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="nombreAcreedor" value="#{CertificadoController.mapaColumnas.nombreAcreedor.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
																																
				<rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
																																											
				<h:panelGrid columns="2">
						<h:outputText  value="Status de Proveedor en SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="statusProveedor" value="#{CertificadoController.mapaColumnas.statusProveedor.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
				
				 <h:panelGrid columns="2">
						<h:outputText value="Nro de Certificación: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="numeroCertificacion" value="#{CertificadoController.mapaColumnas.numeroCertificacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
				 <h:panelGrid columns="2">
						<h:outputText value="Cantidad Certificada: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="cantidadCertificada" value="#{CertificadoController.mapaColumnas.cantidadCertificada.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				
				 <h:panelGrid columns="2">
						<h:outputText value="Importe Certificado: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="importeCertificado" value="#{CertificadoController.mapaColumnas.importeCertificado.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>																																								
			  <h:panelGrid columns="2">
						<h:outputText value="Fecha de Creación de Certificación: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="fechaCreacion" value="#{CertificadoController.mapaColumnas.fechaCreacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
				 <h:panelGrid columns="2">
						<h:outputText value="Nro de Días de Certificación: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="numeroDiasCertificacion" value="#{CertificadoController.mapaColumnas.numeroDiasCertificacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		         <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				  
				  <h:panelGrid columns="2">
						<h:outputText value="Nro de Doc Anulación de Certificación: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="numeroDocumentoAnulacion" value="#{CertificadoController.mapaColumnas.numeroDocumentoAnulacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
				 <h:panelGrid columns="2">
						<h:outputText value="Fecha de Anulación de Certificación: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="fechaAnulacion" value="#{CertificadoController.mapaColumnas.fechaAnulacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
 					<h:panelGrid columns="2">
						<h:outputText value="Nro Doc Compensado: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="numeroCompensado" value="#{CertificadoController.mapaColumnas.numeroCompensado.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				
				 <h:panelGrid columns="2">
						<h:outputText value="Fecha de Conpensación: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="fechaCompensacion" value="#{CertificadoController.mapaColumnas.fechaCompensacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		        <h:panelGrid columns="2">
						<h:outputText value="Fecha de Vencimiento de Pago 1: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="fechaVencimientoPago1" value="#{CertificadoController.mapaColumnas.fechaVencimientoPago1.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       <h:panelGrid columns="2">
						<h:outputText value="Fecha de Vencimiento de Pago 2: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="fechaVencimientoPago2" value="#{CertificadoController.mapaColumnas.fechaVencimientoPago2.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
						       		      
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>																																								
				<rich:spacer height="4px"/><rich:spacer height="4px"/>      
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
		       		       
		        <h:panelGrid columns="2">
						<h:outputText value="Código Usuario SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="usuarioSap" value="#{CertificadoController.mapaColumnas.usuarioSap.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
				 <h:panelGrid columns="2">
						<h:outputText value="Nombre Usuario SAP: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="nombreSap" value="#{CertificadoController.mapaColumnas.nombreSap.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>																																																						      
		        <h:panelGrid columns="2">
						<h:outputText value="Centro de Costo: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="centroCostos" value="#{CertificadoController.mapaColumnas.centroCostos.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       		         		       	     
	      </h:panelGrid>
	      	      	      	      	   
      </rich:panel>
        
        <rich:panel >
      <f:facet name="header">
				<h:outputText value = "Datos Gestor" > </h:outputText>
			</f:facet>
			<h:panelGrid columns="6">		       
		       <h:panelGrid columns="2">
				 <h:outputText value="Seleccionar Todo:" styleClass="spacio" style="padding-right:100px;" />
		       </h:panelGrid>
		        <h:panelGrid columns="2">					
				<h:selectBooleanCheckbox id="chkTodoGestor" onclick="marcarGestor(this, this.form)"></h:selectBooleanCheckbox>											
		       </h:panelGrid>
			   
			   <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			   
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			    <h:panelGrid columns="2">
				<h:outputText value="" styleClass="spacio" />
		       </h:panelGrid>
			   <rich:spacer height="4px"/><rich:spacer height="4px"/>
			   <rich:spacer height="4px"/><rich:spacer height="4px"/>
			   <rich:spacer height="4px"/><rich:spacer height="4px"/>			   
			   <h:panelGrid columns="2">
						<h:outputText value="Tiene Descuento: " styleClass="spacio" />													
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="tieneDescuento" value="#{CertificadoController.mapaColumnas.tieneDescuento.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		        <h:panelGrid columns="2">
						<h:outputText value="Importe Desc Penalidad: " styleClass="spacio" style="padding-right:58px;"/>												
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="importeDescuento1" value="#{CertificadoController.mapaColumnas.importeDescuento1.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		       <h:panelGrid columns="2">
						<h:outputText value=" Importe Desc Fondo Garantía: " styleClass="spacio" style="padding-right:16px;"/>												
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="importeDescuento2" value="#{CertificadoController.mapaColumnas.importeDescuento2.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>
		      
		      <h:panelGrid columns="2">
						<h:outputText value="Importe Desc Nota de Crédito: " styleClass="spacio" />											
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="importeDescuento3" value="#{CertificadoController.mapaColumnas.importeDescuento3.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
			   <h:panelGrid columns="2">
						<h:outputText value="Importe Neto de Certificación: " styleClass="spacio" />											
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="importeNetoCertificacion" value="#{CertificadoController.mapaColumnas.importeNetoCertificacion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
		       <h:panelGrid columns="2">
						<h:outputText value="Contrato Firmado: " styleClass="spacio" />											
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="contratoFirmado" value="#{CertificadoController.mapaColumnas.contratoFirmado.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/>
		       
		       <h:panelGrid columns="2">
						<h:outputText value="Garantías Económicas: " styleClass="spacio" />											
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="desctTipoGarantia" value="#{CertificadoController.mapaColumnas.desctTipoGarantia.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		        <h:panelGrid columns="2">
						<h:outputText value="Fecha de Aceptación del Servicio: " styleClass="spacio" />											
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="fechaRecepcion" value="#{CertificadoController.mapaColumnas.fechaRecepcion.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       
			   <h:panelGrid columns="2">
						<h:outputText value="Observación de Área Gestora: " styleClass="spacio" />											
		       </h:panelGrid>
		       <h:panelGrid columns="2">				
						<h:selectBooleanCheckbox id="observacionGestor" value="#{CertificadoController.mapaColumnas.observacionGestor.visible}"></h:selectBooleanCheckbox>				
		       </h:panelGrid>
		       </h:panelGrid>
	   </rich:panel>
	    
      <rich:panel >
      <f:facet name="header">
				<h:outputText value = "Interventor" style="align:left"> </h:outputText>
			</f:facet>
			<h:panelGrid columns="3">		
			  <h:panelGrid columns="2">
						<h:outputText value="Permitir Modificación: " styleClass="spacio" style="padding-right:82px;"/>
						<h:selectBooleanCheckbox value="#{CertificadoController.mapaColumnas.permitirModificacion.visible}"></h:selectBooleanCheckbox>											
		       </h:panelGrid>
				<h:panelGrid columns="2">
						<h:outputText value="Tiene Observaciones Intervención " styleClass="spacio" style="padding-right:12px;"/>				
						<h:selectBooleanCheckbox value="#{CertificadoController.mapaColumnas.observacionIntervencion.visible}"></h:selectBooleanCheckbox>		
		       </h:panelGrid>
		      
		       <h:panelGrid columns="2">
						<h:outputText value="Observación Interventor " styleClass="spacio" style="padding-right:38px;"/>
						<h:selectBooleanCheckbox value="#{CertificadoController.mapaColumnas.descripcionObservacionIntervencion.visible}"></h:selectBooleanCheckbox>											
		       </h:panelGrid>
	      </h:panelGrid>
	   </rich:panel>
	   
	   <rich:panel >
      <f:facet name="header">
				<h:outputText value = "Cuentas Por Pagar" style="align:left" > </h:outputText>
			</f:facet>
		<h:panelGrid columns="4" border="0">		
				<h:panelGrid columns="2">
						<h:outputText value="Factura Registrada " styleClass="spacio" style="padding-right:92px;"/>												
		       </h:panelGrid>		       
		       <h:panelGrid columns="2">
		       <h:selectBooleanCheckbox value="#{CertificadoController.mapaColumnas.facturaRegistrada.visible}"></h:selectBooleanCheckbox>	
		       </h:panelGrid>
		       <h:outputText value="Factura Recibida: " styleClass="spacio" style="padding-right:93px;" />
		        <h:panelGrid columns="2">
		       <h:selectBooleanCheckbox value="#{CertificadoController.mapaColumnas.facturaRecibida.visible}"></h:selectBooleanCheckbox>	
		       </h:panelGrid>
		       <rich:spacer height="4px"/><rich:spacer height="4px"/> 
		       		       		     
	      </h:panelGrid>
	   </rich:panel>       
	    <rich:spacer height="4px"/><rich:spacer height="4px"/>
	    <rich:spacer height="4px"/><rich:spacer height="4px"/>
	    <h:panelGrid columns="2">	    		
	            <h:commandButton action="listaUsuarioPerfil" actionListener="#{CertificadoController.generateReporteCertificado}" value ="Generar Reporte"></h:commandButton>
	            <a4j:commandButton action="buscarCertificado" value=" Volver "			
			reRender="pgDatatable2" styleClass="spacio"/>	
		</h:panelGrid>
	 	
</h:form>
