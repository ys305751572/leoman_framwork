<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">
    <title>Login</title>
    <%@ include file="../inc/new2/css.jsp"%>
    <![endif]-->
</head>
<body class="login-body">
<div class="container">

    <form class="form-signin" action="${contextPath}/admin/login/check">
        <div class="form-signin-heading text-center">
            <img src="${contextPath}/static/html/images/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <input type="text" id="username" name="username" class="form-control" placeholder="登录名" autofocus>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码">

            <button class="btn btn-lg btn-login btn-block" type="submit">
                登录
            </button>
            <%--<div class="registration">--%>
                <%--Not a member yet?--%>
                <%--<a class="" href="registration.html">--%>
                    <%--Signup--%>
                <%--</a>--%>
            <%--</div>--%>
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> Remember me
                <span class="pull-right">
                    <%--<a data-toggle="modal" href="#myModal"> Forgot Password?</a>--%>
                </span>
            </label>
        </div>

        <!-- Modal -->
        <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal"
             class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Forgot Password ?</h4>
                    </div>
                    <div class="modal-body">
                        <p>Enter your e-mail address below to reset your password.</p>
                        <input type="text" name="email" placeholder="Email" autocomplete="off"
                               class="form-control placeholder-no-fix">
                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        <button class="btn btn-primary" type="button">Submit</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- modal -->
    </form>
</div>
<!-- Placed js at the end of the document so the pages load faster -->
<!-- Placed js at the end of the document so the pages load faster -->
<%@ include file="../inc/new2/foot.jsp"%>
</body>
</html>
