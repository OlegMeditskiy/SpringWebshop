<#import "parts/common.ftlh" as C>
<#import "parts/login.ftlh" as l>
<@C.page>
<div class="containter">
    <div class="row head align-items-center">
        <div class="logo col-md-4">
            <img src="/images/logo.jpg" class="mx-auto d-block" alt="">
        </div>
    </div>
</div>
<div class="container">
    <div class="row login">
        <div class="login-col col-xs-12">
            <div class="login-block">
                <h1>Register</h1>
                <@l.login "/registration"/>

            </div>
        </div>
    </div>
    <div class="row register">
        <div class="col register-col"><a href="/login">Back to log in</a></div>
    </div>
</div>
</@C.page>
