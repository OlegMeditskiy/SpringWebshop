<#import "parts/common.ftlh" as c>

<@c.page>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/admin">Admin panel</a></li>
            <li class="breadcrumb-item"><a href="/admin/user">Users</a></li>
            <li class="breadcrumb-item active" aria-current="page">Products</li>

        </ol>
    </nav>
    <div>
        <h1>Products</h1>
    </div>
    <div> <button class="btn cart" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Add new Product
        </button>
    </div>
    <div class="collapse" id="collapseExample">
    <div class="card card-body">
        <form method="post">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" />
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea type="text" class="form-control" id="description" name="description" placeholder="Enter Description"></textarea>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button class="btn cart" type="submit" >Add</button>
        </form>
    </div>
    </div>



    <div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Image</th>
                <th>id</th>
                <th>Title</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <#list products as product>
                <tr>
                    <td style="width: 100px;"><img src="/images/${product.id}.jpg" style="width: 100px; height: auto;" alt="..."></td>
                    <td style="width: 20px;">${product.id}</td>
                    <td>${product.title}</td>
                    <td>${product.description}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>