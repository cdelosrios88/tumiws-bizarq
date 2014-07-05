<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<div class="messages">
	<a4j:outputPanel id="opMessages" ajaxRendered="true" layout="block" 
					style="text-align:left; padding-bottom:15px">
		<h:messages id="messages" errorClass="errorMessage" infoClass="infoMessage">
		</h:messages>
	</a4j:outputPanel>
</div>