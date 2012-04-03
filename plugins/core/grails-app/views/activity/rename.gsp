<%@ page contentType="text/html;charset=UTF-8" %>
<div>
	<g:form action="update" method="post" >
		<g:hiddenField name="id" value="${params.ownerId}"></g:hiddenField>
		<div class="dialog">
			<table>
				<tbody>
					<tr class="prop">
						<td valign="top" class="name">
							<label class="bold inline" for="title"><g:message code="activity.name" /></label>
						</td>
						<td valign="top" class="value">
							<g:textField name="name" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</g:form>
</div>