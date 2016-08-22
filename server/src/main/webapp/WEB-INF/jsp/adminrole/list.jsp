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
        <!-- 查询条件 -->
        <div class="block-area" id="search">
            <div class="row">
                <div class="col-md-2 form-group">
                    <label>账号：</label>
                    <input type="text" class="form-control" id="account" maxlength="20" placeholder="..."/>
                </div>
                <div class="col-md-2 form-group">
                    <label>手机号：</label>
                    <input type="text" class="form-control" id="mobile" maxlength="20" placeholder="..."/>
                </div>
                <div class="col-md-2 form-group">
                    <label>权限：</label>
                    <select id="roleList" name="roleList" class="select"></select>
                </div>
            </div>
        </div>
        <div class="block-area" id="alternative-buttons">
            <button id="c_search" class="btn btn-alt m-r-5">查询</button>
        </div>
        <hr class="whiter m-t-20"/>
        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" onclick="adminRole.fn.add();"  title="新增管理人员" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/adminRole/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="adminRole.fn.batchDelete();" title="一键删除" class="tooltips">
                            <i class="sa-list-delete"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <hr class="whiter m-t-20"/>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>账号</th>
                    <th>添加时间</th>
                    <th>手机号</th>
                    <th>最近登录时间</th>
                    <th>权限</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>

        <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="showText">确定删除该管理员？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirm" class="btn btn-primary">确定
                        </button>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">重置密码</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新密码:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="password" name="password" maxlength="20"
                                           data-rule="required" placeholder="请输入新密码"/>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="adminRole.fn.confirm()"
                                    class="btn btn-primary">确定
                            </button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    adminRole = {
        v: {
            list: [],
            dTable: null,
            adminRoleId: 0
        },
        fn: {
            init: function () {
                adminRole.fn.dataTableInit();

                $("#c_search").click(function () {
                    adminRole.v.dTable.ajax.reload();
                });

                //自动加载下拉框
                adminRole.fn.getSelectList();
            },
            dataTableInit: function () {
                adminRole.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/adminRole/list",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "id",
                            "render": function (data) {
//                                var checkbox = "<div class=\"icheckbox_minimal\" aria-checked=\"false\" aria-disabled=\"false\" style=\"position: relative;\"><input type=\"checkbox\" value="+ data +" class='pull-left list-check' style=\"position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);\"></div>";
                                var checkbox = "<input type='checkbox' class='pull-left list-check' value=" + data + ">";
                                return checkbox;
                            }
                        },
                        {"data": "admin.username"},
                        {
                            "data": "createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "admin.mobile"},
                        {
                            "data": "admin.lastLoginDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "role.name"},
                        {
                            "data": null,
                            "render": function (data) {
                                var detail = "<button title='编辑' class='btn btn-primary btn-circle' onclick='adminRole.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='重置密码' class='btn btn-primary btn-circle check' onclick='adminRole.fn.changePassword(" + data.id + ")'> " +
                                        "<i class='fa fa-bell'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='删除' class='btn btn-primary btn-circle' onclick='adminRole.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";

                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.account = $("#account").val();
                        aoData.roleId = $("#roleList").val();
                        aoData.mobile = $("#mobile").val();
                    }
                });
            },
            rowCallback: function (row, data) {

            },
            "add": function () {
                window.location.href = "${contextPath}/admin/adminRole/add?";
            },
            "edit": function (userId) {
                window.location.href = "${contextPath}/admin/adminRole/add?id=" + userId;
            },
            "delete": function (id) {

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/adminRole/delete", {
                        "userId": id
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            adminRole.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            batchDelete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/adminRole/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            adminRole.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            getSelectList: function () {

                $leoman.ajax("${contextPath}/admin/adminRole/roleList", null, function (result) {
                    if (null != result) {
                        $('#roleList').empty();
                        // 获取返回的主创列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择权限</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#roleList').append(content);
                        $('#roleList').selectpicker('refresh');
                    } else {
                        $common.notify("获取主创信息失败", "error");
                    }
                });

            },
            changePassword: function () {

                $("#infoModal").modal("show");
                $('#confirmSub').click(function () {
                    $leoman.ajax("${contextPath}/admin/adminRole/changePassword", {
                        userId: $('#id'),
                        password: $('#password').val(),
                    }, function (result) {
                        if (result > 0) {
                            $common.notify("操作成功", "success");
                            $("#infoModal").modal("hide");
                            adminRole.v.dTable.ajax.reload();
                        } else {
                            $common.notify("操作失败", "error");
                        }
                    });
                });

            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        adminRole.v.dTable.ajax.reload(null, false);
                    } else {
                        adminRole.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        adminRole.fn.init();
    })
</script>
<script>
    $('.form_datetime').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: "2",
        forceParse: 0,
        showMeridian: 1,
        format: 'yyyy-mm-dd'
    });
</script>
</body>
</html>

