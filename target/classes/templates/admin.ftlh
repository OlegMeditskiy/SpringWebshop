<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">Admin panel</li>
            <li class="breadcrumb-item"><a href="/admin/user">Users</a></li>
            <li class="breadcrumb-item"><a href="/admin/product">Products</a></li>
        </ol>
    </nav>
    <div>
        <div class="btn-group" role="group" aria-label="Basic example">

            <a href="/"><button class="btn cart"><i class="fas fa-home"></i></button></a>
            <@l.logout/>
        </div>
        <div>
            <h1>Admin panel</h1>
            <p>Here is your admin panel, use upper menu to navigate</p>
        </div>


    </div>
</@c.page>