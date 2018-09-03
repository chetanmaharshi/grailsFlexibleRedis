<%@ page import="com.qwerty.TenantKeys" %>



<div class="fieldcontain ${hasErrors(bean: tenantKeysInstance, field: 'accessKey1', 'error')} required">
	<label for="accessKey1">
		<g:message code="tenantKeys.accessKey1.label" default="Access Key1" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="accessKey1" required="" value="${tenantKeysInstance?.accessKey1}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tenantKeysInstance, field: 'accessKey2', 'error')} required">
	<label for="accessKey2">
		<g:message code="tenantKeys.accessKey2.label" default="Access Key2" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="accessKey2" required="" value="${tenantKeysInstance?.accessKey2}"/>

</div>

