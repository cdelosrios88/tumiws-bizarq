<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<f:subview id = "svBody" >
   <a4j:form id = "frmLoginUsuarios">
      <div id="dvMarco">
	      <rich:spacer height="15px"/><rich:spacer height="15px"/>
		  <rich:spacer height="15px"/><rich:spacer height="15px"/>
          <rich:panel id="pMarco" style="margin-top:10px;width: 270px;height: 100px; background-repeat:no-repeat;border:1px solid #38610B;border-radius: 10px;box-shadow:rgba(0,0,0,0.4)10px 10px ; height : 110px;background-color:#dbe5f1" >			
		     <h:panelGrid columns="2" border = "0" >
			    <h:outputText id = "lblUsuario" value="Usuario:"  style = "color:#2e5b90 ;padding-right:40px;"  styleClass="spacio" />
				<h:inputText  id = "txtUsuario" value="#{loginController.beanBusqueda.codigo}">
				<f:validateLength minimum="1" maximum="30" />
				</h:inputText>
				<rich:spacer height="15px"/><rich:spacer height="15px"/>
				<h:outputText id="lblContraseña" value="Contraseña: "  style="color:#2e5b90 ;padding-right:10px;"  styleClass="spacio" />
				<h:inputSecret id="txtsContraseña" value="#{loginController.beanBusqueda.contrasenha}">
				   <f:validateLength minimum="1" maximum="30" />
				</h:inputSecret>	
				<rich:spacer height="15px"/><rich:spacer height="15px"/>					
				<h:outputText id = "lblEmpresa" value="Empresas:"  style="color:#2e5b90 ;padding-right:25px;"  styleClass="spacio"/>
				<h:selectOneMenu  id="cboEmpresas" value="#" >
				   <f:selectItem itemValue = "0" itemLabel = "Elija Empresa" />
				   <f:selectItem itemValue = "1"  itemLabel = "Coop Tumi" />
				   <f:selectItem itemValue = "2"  itemLabel = "Coop Santa Ana" />
				   <f:selectItem itemValue = "3"  itemLabel = "Asociación" />
				</h:selectOneMenu>							
				</h:panelGrid>					
				<h:messages />			
          </rich:panel>
		  <rich:spacer height = "15px"/><rich:spacer height = "15px"/>
		  <rich:spacer height = "30px"/><rich:spacer height = "30px"/>
		  <h:panelGrid columns="2">
		     <h:commandButton value=" Ingresar " action="#{loginController.login}" styleClass="botonesLogin"/>
		     <h:commandButton value=" Limpiar" action="#" styleClass="botonesLogin"/>   
		  </h:panelGrid>		  		
		<!--<a4j:commandLink  action="#{loginController.login}">
			<h:graphicImage value="/images/icons/actualizarEstados.png" style="border:0px" id="imgPdf"/>
		</a4j:commandLink>-->						    		 
      </div>
   </a4j:form>
</f:subview>