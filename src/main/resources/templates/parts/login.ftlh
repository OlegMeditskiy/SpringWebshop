<#macro login path>

<form class="login-form" action="${path}" method="post">
    <div class="form-group">
        <label for="login">Username</label>
        <input type="text" name="username" class="form-control" id="login" placeholder="Enter username">
    </div>

    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Enter password">
    </div>
    <div class="text-right">
        <button type="submit" class="signinButton btn" value="Sign in">Sign in</button>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <button type="submit" class="btn cart"><i class="fas fa-sign-out-alt"></i></button>
    </form>
</#macro>