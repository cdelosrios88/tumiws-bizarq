<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Exportar Certificados SAP"/>
	<tiles:put name="body" value="/pages/certificado/exportarCertificadoBody.jsp" type="page"/>
</tiles:insert>