<#import "parts/common.ftlh" as c>

<@c.page>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/admin">Admin panel</a></li>
            <li class="breadcrumb-item"><a href="/admin/user">Users</a></li>
            <li class="breadcrumb-item active" aria-current="page">Products</li>

        </ol>
    </nav>
    <div style="margin-left: 50px; margin-right: 50px;">
        <div>
            <h1>Products</h1>
        </div>
        <div> <button class="btn cart" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                Add new Product
            </button>
        </div>
        <div class="collapse" id="collapseExample" style="margin-top: 20px; margin-bottom: 20px;">
            <div class="card card-body">
                <form method="post">
                    <div class="form-group">
                        <label for="category">Category</label>
                        <select name="category" class="form-control" id="category">
                            <option value="chocolate">Chocolate</option>
                            <option value="dairy_free">Dairy-free</option>
                            <option value="ice_cream">Ice Cream</option>
                            <option value="traning">Traning</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" />
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea type="text" class="form-control" id="description" name="description" placeholder="Enter Description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="price">Price</label>
                        <input type="number" class="form-control" pattern="^[ 0-9]+$" id="price" title="Numbers" name="price" placeholder="Enter price" />
                    </div>


                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button class="btn cart" type="submit" >Add</button>
                </form>
            </div>
        </div>



        <div>
            <table id="example" class="table table-bordered" style="width:100%">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Image</th>

                    <th>Title</th>
                    <th>Description</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <#list products as product>
                    <tr>
                        <td style="width: 20px;">${product.id}</td>
                        <td style="width: 100px;"><img src="/images/products/${product.id}.jpg" style="width: 100px; height: auto;" alt="..."></td>
                        <td>${product.title}</td>
                        <td>${product.description}</td>
                        <td style="width: 50px;">${product.price}</td>
                    </tr>

                </#list>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            $('#example').DataTable();
        } );
    </script>
</@c.page>