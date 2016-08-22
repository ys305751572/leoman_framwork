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
                    <label>举报人昵称：</label>
                    <input type="text" class="input-sm form-control" id="nickname1" name="nickname1" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>举报人账号：</label>
                    <input type="text" class="input-sm form-control" id="mobile" name="mobile" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>举报对象昵称：</label>
                    <input type="text" class="input-sm form-control" id="nickname2" name="nickname2" placeholder="...">
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
                        <a href="${contextPath}/admin/report/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
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
                    <th>举报人账号</th>
                    <th>举报人昵称</th>
                    <th>举报时间</th>
                    <th>举报类型</th>
                    <th>举报对象</th>
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
                        <h4 class="modal-title" id="showText" >确定删帖？</h4>
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

        <div class="modal fade" id="lock" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">确定锁帖？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirmSub" class="btn btn-primary">确定
                        </button>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <div class="modal fade" id="ignore" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">确定忽略？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirmSubs" class="btn btn-primary">确定
                        </button>
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
    report = {
        v: {
            list: [],
            dTable: null,
            reportId: 0
        },
        fn: {
            init: function () {
                report.fn.dataTableInit();

                $("#c_search").click(function () {
                    report.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                report.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/report/list",
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
                        {"data": "userInfo.mobile"},
                        {"data": "userInfo.nickname"},
                        {
                            "data": "createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "reportType"},
                        {"data": "postBase.userInfo.nickname"},
                        {
                            "data": null,
                            "render": function (data) {
                                var detail = "";
                                if (data.status == 1) {

                                    detail = "已删帖";
                                } else if (data.status == 2) {

                                    detail = "已锁帖";
                                } else if (data.status == 3) {

                                    detail = "已忽略";
                                }else {
                                    detail = "<button title='删帖' class='btn btn-primary btn-circle detail' onclick='report.fn.delete(" + data.id + ")'> " +
                                            "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                    detail += "<button title='锁帖' class='btn btn-primary btn-circle check' onclick='report.fn.lock(" + data.id + ")'> " +
                                            "<i class='fa fa-lock'></i></button>&nbsp;&nbsp;";
                                    detail += "<button title='忽略' class='btn btn-primary btn-circle check' onclick='report.fn.ignore(" + data.id + ")'> " +
                                            "<i class='fa fa-check'></i></button>&nbsp;&nbsp;";
                                }

                                    return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $("#mobile").val();
                        aoData.nickname1 = $("#nickname1").val();
                        aoData.nickname2 = $("#nickname2").val();
                    }
                });
            },
            rowCallback: function (row, data) {
                $('td', row).last().find(".check").click(function () {
                    var checkbox = $('td', row).first().find("input[type='checkbox']");
                    $user.fn.give(checkbox, [data.id]);
                });
            },
            "delete": function (id) {

                $("#delete").modal("show");
                $('#confirm').click(function () {

                    $leoman.ajax("${contextPath}/admin/report/delete", {
                        "reportId": id
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            report.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "lock": function (id) {

                $("#lock").modal("show");
                $('#confirmSub').click(function () {

                    $leoman.ajax("${contextPath}/admin/report/lock", {
                        "reportId": id,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#lock").modal("hide");
                            report.v.dTable.ajax.reload(null, false);
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "ignore": function (id) {

                $("#ignore").modal("show");
                $('#confirmSubs').click(function () {

                    $leoman.ajax("${contextPath}/admin/report/ignore", {
                        "reportId": id,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#ignore").modal("hide");
                            report.v.dTable.ajax.reload(null, false);
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $user.v.dTable.ajax.reload(null, false);
                    } else {
                        $user.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        report.fn.init();
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

