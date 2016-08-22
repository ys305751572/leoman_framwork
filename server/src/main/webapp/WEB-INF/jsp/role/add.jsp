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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="clearfix"></div>
<section id="main" class="p-relative" role="main">
    <%@ include file="../inc/new/menu.jsp" %>
    <section id="content" class="container">
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">权限编辑</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${roles.id}"/>
                <input type="hidden" id="tempType" value="${roleModuleIds}"/>
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>角色名称：</label>
                        <input type="text" class="input-sm form-control validate[required]"
                               id="name" name="name" value="${roles.name}" placeholder="..."/>
                    </div>
                    <%--<hr class="whiter m-t-20"/>--%>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>权限名称:</label>

                        <table border="1" class="col-md-12 m-b-15" style="line-height: 30px;">
                            <tr width="100px">
                                <th width="6%;" style="text-align: center;">序号</th>
                                <th width="12%;" style="text-align: center;">功能名称</th>
                                <th width="80%">

                                </th>
                            </tr>
                            <c:forEach var="n" items="${moduleList}" varStatus="index">
                                <tr>
                                    <td style="text-align: center;">${index.index + 1}</td>
                                    <td style="text-align: center;">${n.name}</td>
                                    <td>
                                        <div style="padding-left: 10px; padding-right: 10px;">
                                            <c:forEach var="m" items="${n.modulesList}">
                                            <div class="col-md-2" style="padding-top: 10px;">
                                                <input type="checkbox" name="roles" id="roles_${m.id}" value="${m.id}" style="vertical-align: middle;margin-top: 20px"/>
                                                <label style="vertical-align: middle;margin-top: -10px;" for="roles_${m.id}">${m.name}</label>
                                            </div>
                                            <%--<ul>
                                                <input type="checkbox" name="roles" value="${m.id}"/>${m.name}
                                            </ul>--%>
                                            </c:forEach>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="role.fn.save();" class="btn btn-info btn-sm m-t-10">提交
                        </button>
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
    role = {
        v: {
            list: [],
            chart: null,
            dTable: null,
            types: []
        },
        fn: {
            init: function () {
//                $("#fromId").validationEngine();

                $('#sendto_alls').click(function () {
                    if (this.checked) {
                        $(":checkbox").each(function () {
                            this.checked = true;
                        });
                    } else {
                        $(":checkbox").each(function () {
                            this.checked = false;
                        });
                    }
                });

                role.fn.loadData();
            },
            loadData: function () {
                // 界面加载时，选中复选框
                var id = $('#id').val();
                if (null != id && id != '') {
                    // 回选复选框
                    var tempType = $('#tempType').val();
                    var array = tempType.split(',');
                    for (var i = 0; i < array.length; i++) {
                        $('input:checkbox[name="roles"]').each(function () {
                            if ($(this).val() == array[i]) {
                                $(this).parent().addClass("checked");
                                // $(this).prop("checked", true);
                            }
                        });
                    }
                }
            },
            checkData: function () {
                var flag = true;
                var result = false;
                var name = $('#name').val();

                if (null == name || name == '') {
                    $sixmac.notify('角色名称不能为空', "error");
                    flag = false;
                    return;
                }

                $('input:checkbox[name="roles"]').each(function () {
                    if ($(this).parent().hasClass('checked')) {
                        result = true;
                        return;
                    }
                });

                if (!result) {
                    $sixmac.notify('权限不能为空', "error");
                    return;
                }

                // 到此处的时候，说明前面的校验都通过，此时保存需要提交的复选框的值
                $('input:checkbox[name="roles"]').each(function () {
                    if ($(this).parent().hasClass('checked')) {
                        role.v.types.push($(this).val());
                    }
                });

                return flag;
            },
            save: function () {
//                if(!$("#fromId").validationEngine("validate")) return;

                if (role.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/role/save",
                        type: "POST",
                        data: {
                            "types": JSON.stringify(role.v.types)
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/role/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
        }
    }
    $(function () {
        role.fn.init();
    })
</script>
</body>
</html>

