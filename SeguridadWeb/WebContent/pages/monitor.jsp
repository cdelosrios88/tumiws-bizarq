<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Bienvenido"/>
	<tiles:put name="body" value="/pages/monitorBody.jsp" type="page"/>
</tiles:insert>