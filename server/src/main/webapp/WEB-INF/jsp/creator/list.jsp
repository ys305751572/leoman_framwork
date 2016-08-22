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
                    <label>手机号：</label>
                    <input type="text" class="input-sm form-control" id="mobile" name="mobile" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>昵称：</label>
                    <input type="text" class="input-sm form-control" id="nickName" name="nickName" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>状态：</label>
                    <select id="status" name="status" class="select">
                        <option value="">全部</option>
                        <option value="0">正常</option>
                        <option value="1">封禁</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>性别：</label>
                    <select id="gender" name="gender" class="select">
                        <option value="">全部</option>
                        <option value="0">男</option>
                        <option value="1">女</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="block-area" id="alternative-buttons">
            <button id="c_search" class="btn btn-alt m-r-5">查询</button>
        </div>
        <hr class="whiter m-t-20"/>
        <%--<div class="block-area">
            <button type="button" onclick="creator.fn.add();"  class="btn btn-alt m-r-5">新增主创</button>
        </div>--%>
        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" onclick="creator.fn.add();"  title="新增主创" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/creator/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="creator.fn.batchDelete();" title="一键禁用" class="tooltips">
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
                    <th>昵称</th>
                    <th>注册时间</th>
                    <th>性别</th>
                    <th>状态</th>
                    <th>城市</th>
                    <th>作品数</th>
                    <th>馒头数</th>
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
                        <h4 class="modal-title" id="showText">确定禁用该账号？</h4>
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

        <div class="modal fade" id="creator" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="showTexts">推荐后将在热门推荐区展示，确定推荐？</h4>
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

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">绑定手机号</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="num" name="num" maxlength="20"
                                           data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">验证码:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="code" name="code" maxlength="20"
                                           data-rule="required"/>
                                </div>
                                <button type="button" id="sendCode" class="btn btn-primary">发送验证码
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" class="btn btn-primary">确定
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
    creator = {
        v: {
            list: [],
            dTable: null,
            userId: 0
        },
        fn: {
            init: function () {
                creator.fn.dataTableInit();

                $("#c_search").click(function () {
                    creator.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                creator.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/creator/list",
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
                        {"data": "mobile"},
                        {"data": "nickname"},
                        {
                            "data": "creator.createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {
                            "data": "gender",
                            render: function (data) {
                                if (data == 0) {
                                    return "男";
                                } else {
                                    return "女";
                                }
                            }
                        }, {
                            "data": "status",
                            render: function (data) {
                                if (data == 0) {
                                    return "正常";
                                } else {
                                    return "封禁";
                                }
                            }
                        },
                        {"data": "creator.city.name"},
                        {"data": "num"},
                        {"data": "coin"},
                        {
                            "data": null,
                            "render": function (data) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle detail' onclick='creator.fn.detail(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";
                                if (data.status == 0) {
                                    detail += "<button title='禁用' class='btn btn-primary btn-circle check' onclick='creator.fn.changeStatus(" + data.id + "," + data.status + ")'> " +
                                            "<i class='fa fa-check'></i></button>&nbsp;&nbsp;";
                                }else if (data.status == 1) {
                                    detail += "<button title='解禁' class='btn btn-primary btn-circle check' onclick='creator.fn.changeStatus(" + data.id + "," + data.status + ")'> " +
                                            "<i class='fa fa-check'></i></button>&nbsp;&nbsp;";
                                }

                                detail += "<button title='编辑主创信息' class='btn btn-primary btn-circle' onclick='creator.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";

                                if (data.creator.status == 0) {
                                    detail += "<button title='推荐' class='btn btn-primary btn-circle' onclick='creator.fn.setCreator(" + data.id + "," + data.creator.status + ")'> " +
                                            "<i class='fa fa-heart'></i></button>&nbsp;&nbsp;";
                                }else if (data.creator.status == 1) {
                                    detail += "<button title='取消推荐' class='btn btn-primary btn-circle' onclick='creator.fn.setCreator(" + data.id + "," + data.creator.status + ")'> " +
                                            "<i class='fa fa-heart'></i></button>&nbsp;&nbsp;";
                                }

                                if (data.mobile == null || data.mobile == "") {
                                    detail += "<button title='绑定手机' class='btn btn-primary btn-circle' onclick='creator.fn.mobile(" + data.id + ")'> " +
                                            "<i class='fa fa-bell'></i></button>";
                                }
                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $("#mobile").val();
                        aoData.nickname = $("#nickName").val();
                        aoData.status = $("#status").val();
                        aoData.gender = $("#gender").val();
                    }
                });
            },
            rowCallback: function (row, data) {
                /*$('td', row).last().find(".check").click(function () {
                 var checkbox = $('td', row).first().find("input[type='checkbox']");
                 creator.fn.give(checkbox, [data.id]);
                 });*/
            },
            "detail": function (userId) {
                window.location.href = "${contextPath}/admin/creator/detail?userId=" + userId;
            },
            "add": function () {
                window.location.href = "${contextPath}/admin/creator/add?";
            },
            "edit": function (userId) {
                window.location.href = "${contextPath}/admin/creator/edit?userId=" + userId;
            },
            "changeStatus": function (id, status) {
                if (status == 0) {
                    $('#showText').html('确定禁用该账号？');
                } else {
                    $('#showText').html('确定解封该账号？');
                }

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    var tempStatus = 0;
                    if (status == 0) {
                        tempStatus = 1;
                    }

                    $leoman.ajax("${contextPath}/admin/creator/banned", {
                        "userId": id,
                        "status": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            creator.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "setCreator": function (id, status) {
                if (status == 0) {
                    $('#showTexts').html('推荐后将在热门推荐区展示，确定推荐？');
                } else {
                    $('#showTexts').html('确定取消该主创的推荐？');
                }

                $("#creator").modal("show");
                $('#confirmSubs').click(function () {
                    var tempStatus = 0;
                    if (status == 0) {
                        tempStatus = 1;
                    }

                    $leoman.ajax("${contextPath}/admin/creator/setCreator", {
                        "userId": id,
                        "status": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#creator").modal("hide");
                            creator.v.dTable.ajax.reload(null, false);
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            mobile: function (id) {
                $("#infoModal").modal("show");
                $('#sendCode').click(function () {

                    $leoman.ajax("${contextPath}/admin/creator/sendCode", {
                        "num": $('#num').val()
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            //$("#infoModal").modal("hide");
                            creator.v.dTable.ajax.reload(null, false);
                        } else if (result == 0) {
                            //$("#infoModal").modal("hide");
                            $common.fn.notify("请输入手机号", "error");
                        } else if (result == 2) {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });

                $('#confirmSub').click(function () {
                    $leoman.ajax("${contextPath}/admin/creator/mobile", {
                        "userId": id,
                        "num": $('#num').val(),
                        "code": $('#code').val()
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#infoModal").modal("hide");
                            creator.v.dTable.ajax.reload(null, false);
                        } else if (result == 2){
                            $common.fn.notify("请输入验证码", "error");
                        }else if (result == 3){
                            $common.fn.notify("验证码错误", "error");
                        }
                    });
                });
            },
            batchDelete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/creator/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            creator.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        creator.v.dTable.ajax.reload(null, false);
                    } else {
                        creator.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        creator.fn.init();
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

