<#import "parts/common.ftlh" as c>

<@c.page>
    User editor

    <form action="/product" method="post">
        <input type="text" name="title" value="${product.title}">
        <input type="text" name="title" value="${product.description}">
        <input type="hidden" value="${product.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@c.page>