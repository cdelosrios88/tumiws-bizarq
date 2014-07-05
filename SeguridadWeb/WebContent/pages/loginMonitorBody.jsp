<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<f:subview id="svBody" >
   <a4j:form>
      <div align="center" style="margin-top: 1px ; background-color:#ffffff"  >
	  <rich:spacer height="15px"/><rich:spacer height="15px"/>
	  <rich:spacer height="15px"/><rich:spacer height="15px"/>
	  <rich:panel  style="margin-top:10px;width: 250px;height: 50px; background-repeat:no-repeat;border:1px solid #38610B;border-radius: 10px;box-shadow:rgba(0,0,0,0.4)10px 10px ; height : 68px;background-color:#ffffff" >		
      <h:panelGrid columns="2" border = "0" >
	     <h:outputText value="Usuario :"  style="color:#366092 ;padding-right:25px;" styleClass="spacio" />
		 <h:inputText value="#{loginController.beanBusqueda.codigo}" >
		   <f:validateLength minimum="1" maximum="30" />
		 </h:inputText>
				<rich:spacer height="15px"/><rich:spacer height="15px"/>
				<h:outputText value="Contraseña: "  style="color:#366092"  styleClass="spacio" />
				<h:inputSecret value="#{loginController.beanBusqueda.contrasenha}">
					<f:validateLength minimum="1" maximum="30" />
				</h:inputSecret>	
				<rich:spacer height="15px"/><rich:spacer height="15px"/>
			</h:panelGrid>
			<h:messages />			
		</rich:panel>
		<rich:spacer height="10px"/><rich:spacer height="10px"/>
		
		<h:commandButton value=" Ingresar " action="#{loginController.login}" styleClass="botonesLogin"/>
		<rich:spacer height="30px"/><rich:spacer height="30px"/>
		<!--<a4j:commandLink  action="#{loginController.login}">
			<h:graphicImage value="/images/icons/actualizarEstados.png" style="border:0px" id="imgPdf"/>
		</a4j:commandLink>-->
        <br></br>        
		</div>
	</a4j:form>

</f:subview>