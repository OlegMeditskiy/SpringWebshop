<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as l>
<@C.page>
<div class="container-fluid">
    <div class="row align-items-center">
        <div class="logo col-lg-3">
            <a href="main.html"><img id="logo" src="/images/logo.jpg" alt=""></a>
        </div>
        <div class="col-lg-7 col-md-9 col-sm-8 col-6">
            <div class="input-group">
                <input type="text" class="form-control" aria-label="Search" aria-describedby="button-search">
                <div class="input-group-append">
                    <button  id="button-search"class="searchButton btn btn-outline-secondary" type="button">Search</button>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="btn-group float-right" role="group" aria-label="Basic example">
                <button class="btn cart"><i class="far fa-user-circle"></i></button>

                    <@l.logout/>

                <button class="btn cart"><i class="fas fa-shopping-cart"></i></button>

            </div>
        </div>

    </div>
</div>

<nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light">

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav  mr-auto">
            <li class="nav-item">
                <a class="menu nav-link" href="#">All products</a>
            </li>
            <li class="nav-item">
                <a class="menu nav-link" href="#">Chocolate</a>
            </li>
            <li class="nav-item">
                <a class="menu nav-link" href="#">Dairy-free</a>
            </li>
            <li class="nav-item">
                <a class="menu nav-link" href="#">Ice cream</a>
            </li>
            <li class="nav-item">
                <a class="menu nav-link" href="#">Traning</a>
            </li>
        </ul>

    </div>
</nav>
<div class="last"> Last products</div>
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalCenterTitle">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
<#list products as product>
        <div class="project col-md-6 col-lg-4">
            <div class="card" style="width: 18rem;">
                <img src="/images/${product.id}.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${product.title}</h5>
                    <p class="card-text">${product.description}</p>
                    <button class="btn cart" data-toggle="modal" data-target="#exampleModalCenter">Add to basket</button>
                </div>
            </div>
        </div>
</#list>
    </div>

</div>
</@C.page>