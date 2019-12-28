<#import "parts/common.ftlh" as c>

<@c.page>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/admin">Admin panel</a></li>
            <li class="breadcrumb-item active" aria-current="page">Users</li>
            <li class="breadcrumb-item"><a href="/admin/product">Products</a></li>
        </ol>
    </nav>
    <div>
        <h1>Users</h1>

    </div>

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/admin/user/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>