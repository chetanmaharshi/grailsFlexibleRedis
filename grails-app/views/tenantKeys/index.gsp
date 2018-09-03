
<%@ page import="com.qwerty.TenantKeys" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tenantKeys.label', default: 'TenantKeys')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tenantKeys" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-tenantKeys" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="accessKey1" title="${message(code: 'tenantKeys.accessKey1.label', default: 'Access Key1')}" />
					
						<g:sortableColumn property="accessKey2" title="${message(code: 'tenantKeys.accessKey2.label', default: 'Access Key2')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${tenantKeysInstanceList}" status="i" var="tenantKeysInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tenantKeysInstance.id}">${fieldValue(bean: tenantKeysInstance, field: "accessKey1")}</g:link></td>
					
						<td>${fieldValue(bean: tenantKeysInstance, field: "accessKey2")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tenantKeysInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
