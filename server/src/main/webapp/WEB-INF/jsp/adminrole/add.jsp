<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="format-detection" content="telephone=no">
    <meta charset="UTF-8">
    <meta name="description" content="Violate Responsive Admin Template">
    <meta name="keywords" content="Super Admin, Admin, Template, Bootstrap">
    <title>Super Admin Responsive Template</title>
    <!-- CSS -->
    <%@ include file="../inc/new/css.jsp" %>
</head>
<body id="skin-cloth">
<%@ include file="../inc/new/header.jsp" %>
<div class="clearfix"></div>
<section id="main" class="p-relative" role="main">
    <%@ include file="../inc/new/menu.jsp" %>
    <section id="content" class="container">
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">主创编辑</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${adminRole.id}">
                <input type="hidden" id="roleId" name="roleId" value="${adminRole.role.id}">
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>账号：</label>
                        <input type="text" id="account" name="account" value="${adminRole.admin.username}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px">
                        <label>手机号：</label>
                        <input type="text" id="mobile" name="mobile" value="${adminRole.admin.mobile}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px">
                        <label>密码：</label>
                        <input type="password" id="password" name="password" value="${adminRole.admin.password}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-4 m-b-15" style="margin-top: 10px;">
                        <label>权限：</label>
                        <select id="roleList" name="roleList" style="width: 200px;" class="select"></select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="adminRole.fn.save();" class="btn btn-info btn-sm m-t-10">提交</button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    adminRole = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                //自动加载下拉框
                adminRole.fn.getSelectList();
            },
            checkData: function () {
                var flag = true;
                var account = $('#account').val();
                var mobile = $('#mobile').val();
                var password = $('#password').val();
                var roleList = $('#roleList').val();

                if (null == account || account == '') {
                    $common.fn.notify("请输入账号", "error");
                    flag = false;
                    return;
                }

                if (null == mobile || mobile == '') {
                    $common.fn.notify("请输入手机号", "error");
                    flag = false;
                    return;
                }

                if (null == password || password == '') {
                    $common.fn.notify("请输入密码", "error");
                    flag = false;
                    return;
                }

                if (null == roleList || roleList == '') {
                    $common.fn.notify("请选择权限", "error");
                    flag = false;
                    return;
                }
                return flag;
            },
            save: function () {
                if (adminRole.fn.checkData()) {

                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/adminRole/save",
                        type: "POST",
                        data: {
                            "roleId": $('#roleList option:selected').val()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/adminRole/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            getSelectList: function () {
                var roleId = $('#roleId').val();

                $leoman.ajax("${contextPath}/admin/adminRole/roleList", null, function (result) {
                    if (null != result) {
                        $('#roleList').empty();
                        // 获取返回的列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择权限</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#roleList').append(content);
                        $('#roleList').selectpicker('refresh');
                    } else {
                        $common.notify("获取省份信息失败", "error");
                    }

                    if (null != roleId && roleId != '') {
                        $('#roleList').val(roleId);
                        $('#roleList').selectpicker('refresh');
                    }
                });
            },
        }
    }
    $(function () {
        adminRole.fn.init();
    })
</script>
</body>
</html>

