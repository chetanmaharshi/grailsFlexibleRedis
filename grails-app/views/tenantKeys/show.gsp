
<%@ page import="com.qwerty.TenantKeys" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tenantKeys.label', default: 'TenantKeys')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tenantKeys" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tenantKeys" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tenantKeys">
			
				<g:if test="${tenantKeysInstance?.accessKey1}">
				<li class="fieldcontain">
					<span id="accessKey1-label" class="property-label"><g:message code="tenantKeys.accessKey1.label" default="Access Key1" /></span>
					
						<span class="property-value" aria-labelledby="accessKey1-label"><g:fieldValue bean="${tenantKeysInstance}" field="accessKey1"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tenantKeysInstance?.accessKey2}">
				<li class="fieldcontain">
					<span id="accessKey2-label" class="property-label"><g:message code="tenantKeys.accessKey2.label" default="Access Key2" /></span>
					
						<span class="property-value" aria-labelledby="accessKey2-label"><g:fieldValue bean="${tenantKeysInstance}" field="accessKey2"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:tenantKeysInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${tenantKeysInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
