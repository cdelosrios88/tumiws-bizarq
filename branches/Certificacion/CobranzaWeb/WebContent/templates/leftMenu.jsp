<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

	<div id="leftMenu" style="width:200px"  >
		<a4j:form > 
		<!--  	<rich:tree value="#{menuController.rootNode}"  var="nodeData" style="color:#88d888" ajaxSubmitSelection="true" styleClass="richTree" -->
		<!--		switchType="ajax" reRender="main" componentState="#{menuController.treeState}"> -->
			<!--	<rich:treeNode nodeSelectListener="#{menuController.selectNode}"  > -->
		<!--		</rich:treeNode> -->
	<!--		</rich:tree> -->
						

	

<!-- Menu principal -->
     <rich:panelMenu style="width:193px"               id="tumi"  mode = "ajax"
			          iconExpandedGroup="disc"         iconCollapsedGroup="disc"
			          iconExpandedTopGroup="chevronUp" iconGroupTopPosition="right"
				      iconCollapsedTopGroup="chevronDown"
				      expandSingle="true"
				      expandMode="ajax"
				      >  
				      
    <rich:panelMenuGroup label="Administración  de Cuentas" style="background-color:red"  >        
		<rich:panelMenuItem label="Usuarios" action="rol" >
        </rich:panelMenuItem>
	</rich:panelMenuGroup >
	
	<rich:panelMenuGroup label="Parametros">   
		<rich:panelMenuItem label="Empresas" action="empresa" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Usuarios-Perfiles" action="usuarioPerfil" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Horario de Ingreso al Sistema" action="horarioIngreso" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Formulario - Documentación" action="admFormDoc" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Relación Perfil / MOF" action="perfilMof" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Registro de PC" action="registroPc">
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Accesos Fuera de Hora" action="accesosEspeciales" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Reglamentos y Políticas" action="reglamentosPoliticas" >
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Auditoria de Sistemas" action="auditoria" >
		</rich:panelMenuItem>
	</rich:panelMenuGroup > 
	
	<rich:panelMenuGroup label="Sistemas">        
		<rich:panelMenuItem label="Administración Usuario CPanel" action="administracionUsuarioPanel" >
	     </rich:panelMenuItem>     
	     <rich:panelMenuItem label="Auditoria de Sistemas" action="auditoriaSist" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Administración de Formularios y Reportes" action="adminMenu" >
	     </rich:panelMenuItem>     	  	          
	</rich:panelMenuGroup >
	
	<rich:panelMenuGroup label="Créditos">        
		<rich:panelMenuItem label="Hoja de Planeamiento" action="hojaPlaneamiento" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Estructura Organica" action="estructuraOrganica" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Persona Juridica" action="perJuriConve" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Representante Legal" action="representanteLegal" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Cuenta Bancaria" action="cuentaBancaria" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Aportaciones" action="aportaciones" >
	     </rich:panelMenuItem>
	     <rich:panelMenuItem label="Mantenimiento Cuenta" action="mantCuentas" >
	     </rich:panelMenuItem>
	</rich:panelMenuGroup >
	
	<rich:panelMenuGroup label="Popups">        
		<rich:panelMenuItem label="Beneficiario" action="beneficiario" >
	    </rich:panelMenuItem>
	    <rich:panelMenuItem label="Domicilio" action="domicilio" >
	    </rich:panelMenuItem>
	</rich:panelMenuGroup >
	
	<rich:panelMenuGroup label="Gestión de Proyectos">        
		<rich:panelMenuItem label="Empresas" action="solicitudCambio" >
	     </rich:panelMenuItem>     	  	      
	</rich:panelMenuGroup >
	
	  <rich:panelMenuGroup label="Servicios" id="group1" ajaxSingle="true"  actionListener="#{CertificadoController.excluir}"     expanded="#{CertificadoController.habilitarGrupo}"  >
	 	<rich:panelMenuItem   label="Direcciones" id = "pmiDir" action="sistema"   mode= "ajax"   > 
		<!--<a4j:commandLink value="direcciones11"   action="sistema" reRender="tumi" ></a4j:commandLink>-->
		</rich:panelMenuItem>              
		<rich:panelMenuItem label="Ubigeo" action="sistema"    mode = "ajax"  >  
		       
		</rich:panelMenuItem>       
	 </rich:panelMenuGroup> 
	 
	     
	<rich:panelMenuGroup label="Cobranzas y Gestiones fredy " id="group2" >   
		<rich:panelMenuItem label="Pago de Prestamos" action="#{planColMB.atualizaConteudo}">      
		<f:param name="current" value="Item 1.1"/>      
		</rich:panelMenuItem>                
		<rich:panelMenuItem label="Refinanciación" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">    
		<f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudFundoAgricola.xhtml"/>                 
		</rich:panelMenuItem>    
		<rich:panelMenuItem label="Reprogramación" action="#{planColMB.atualizaConteudo}">    
		<f:param name="current" value="Item 1.3"/>    
		</rich:panelMenuItem>        
		<rich:panelMenuItem label="Provisiones" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">      
		<f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudFrenteColheita.xhtml"/>    
		</rich:panelMenuItem>     
		<rich:panelMenuItem label="Riesgos" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">
		<f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudCenario.xhtml"/> 
		 </rich:panelMenuItem>   
		 <rich:panelMenuItem label="RCC" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">   
		 <f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudVariedade.xhtml"/>                  </rich:panelMenuItem>     
		 <rich:panelMenuItem label="Opcion1" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">    
		 <f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudMaturador.xhtml"/>              
		 </rich:panelMenuItem>         
		 <rich:panelMenuItem label="Opcion2" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">              
		 <f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudProgramacao.xhtml"/>                  </rich:panelMenuItem>      
	 </rich:panelMenuGroup> 
	   
	   
	   <rich:panelMenuGroup label="Cartera y Central de Riesgos">
		<rich:panelMenuItem label="Simulador" action="#{planColMB.atualizaConteudo}">  
		<f:param name="current" value="Item 2.1"/>               
		</rich:panelMenuItem>               
		<rich:panelMenuItem   label="Estado de Cuentas" action="#{planColMB.atualizaConteudo}"> 
		<f:param name="current" value="Item 2.2"/>              
		</rich:panelMenuItem>              
		<rich:panelMenuItem label="Extornar" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">  
		<f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudFrenteProgramacao.xhtml"/>        
		</rich:panelMenuItem>       
	 </rich:panelMenuGroup> 
	 
	  
	  <rich:panelMenuGroup  label="Previsión Social" id="g2" ajaxSingle="true"  actionListener="#{CertificadoController.excluir1}"  expanded="#{CertificadoController.habilitarGrupo1}"  >
		<rich:panelMenuItem label="Realizar Pagos" action="#{planColMB.atualizaConteudo}">  
		<f:param name="current" value="Item 2.1"/>               
		</rich:panelMenuItem>               
		<rich:panelMenuItem   label="Desembolso de  Prestamos" action="#{planColMB.atualizaConteudo}"> 
		<f:param name="current" value="Item 2.2"/>              
		</rich:panelMenuItem>              
		<rich:panelMenuItem label="Extornar" action="#{planColMB.atualizaConteudo}" reRender="panelLeftContent,a4jUrlIncludeLeft,panelRightContent,a4jUrlIncludeRight">  
		<f:setPropertyActionListener target="#{planColMB.urlIncludeRight}" value="/pages/protected/crudFrenteProgramacao.xhtml"/>        
		</rich:panelMenuItem>
		
		<rich:panelMenuGroup label="Tercera Opción" expanded="#{CertificadoController.habilitarGrupo1}"  >
			<rich:panelMenuItem   label="ver Detalle de Cronograma" mode="ajax" action="opcionMenu" >		 	
			</rich:panelMenuItem>			
			<rich:panelMenuItem   label="Imprimir comprobante" action="listaUsuarioInactivo">		 	
			</rich:panelMenuItem>
		</rich:panelMenuGroup>
       
	 </rich:panelMenuGroup> 
	        
	  
	
	<rich:panelMenuGroup label="Tesoreria"> 
		<rich:panelMenuItem label="Asientos Contables" action="#{planColMB.atualizaConteudo}">          
		<f:param name="current" value="Item 3.1"/>           
		</rich:panelMenuItem> 
		<rich:panelMenuItem label="Reportes" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Opcion1" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion2" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion3" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion4" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion5" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion6" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion7" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion8" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
	</rich:panelMenuItem>            
		
	  </rich:panelMenuGroup>  
	  	  
	  <rich:panelMenuGroup label="Contabilidad"> 
		<rich:panelMenuItem label="Asientos Contables" action="#{planColMB.atualizaConteudo}">          
		<f:param name="current" value="Item 3.1"/>           
		</rich:panelMenuItem> 
		<rich:panelMenuItem label="Reportes" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Opcion1" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion2" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion3" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion4" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion5" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion6" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion7" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion8" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
	</rich:panelMenuItem>            
		
	  </rich:panelMenuGroup>
	            
	            
	  <rich:panelMenuGroup label="Presupuestos"> 
		<rich:panelMenuItem label="Asientos Contables" action="#{planColMB.atualizaConteudo}">          
		<f:param name="current" value="Item 3.1"/>           
		</rich:panelMenuItem> 
		<rich:panelMenuItem label="Reportes" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Opcion1" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion2" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion3" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion4" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion5" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion6" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion7" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion8" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
	</rich:panelMenuItem>            
		
	  </rich:panelMenuGroup>
	  	  	 
	   <rich:panelMenuGroup label="Logística"> 
		<rich:panelMenuItem label="Asientos Contables" action="#{planColMB.atualizaConteudo}">          
		<f:param name="current" value="Item 3.1"/>           
		</rich:panelMenuItem> 
		<rich:panelMenuItem label="Reportes" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Opcion1" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion2" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion3" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion4" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion5" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion6" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion7" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
		</rich:panelMenuItem>             
		<rich:panelMenuItem label="Opcion8" action="#{planColMB.atualizaConteudo}">        
		<f:param name="current" value="Item 3.2"/>        
	</rich:panelMenuItem>            
		
	  </rich:panelMenuGroup>
	  	  	
 </rich:panelMenu>
  
  
		</a4j:form>

	</div>