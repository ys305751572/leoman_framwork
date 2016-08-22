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
        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" onclick="game.fn.add();"  title="添加游戏" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/game/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="game.fn.batchDelete();" title="删除" class="tooltips">
                            <i class="sa-list-delete"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <%--<div class="block-area" >
            <button type="button" onclick="category.fn.add();"  class="btn btn-alt m-r-5">新增分类</button>
        </div>--%>

        <hr class="whiter m-t-20"/>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>游戏名称</th>
                    <th>版本</th>
                    <th>大小</th>
                    <th>更新时间</th>
                    <th>下载数量</th>
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
                        <h4 class="modal-title" id="showText">确定删除该游戏？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirm" class="btn btn-primary">确定</button>
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
    game = {
        v: {
            list: [],
            dTable: null,
            gameId: 0
        },
        fn: {
            init: function () {
                game.fn.dataTableInit();

            },
            dataTableInit: function () {
                game.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/game/list",
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
                        {"data": "name"},
                        {"data": "version"},
                        {"data": "size"},
                        {
                            "data": "updateDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "downCount"},
                        {
                            "data": null,
                            "render": function (data) {
                                game.v.list.push(data);
                                var detail ="<button title='查看' class='btn btn-primary btn-circle check' onclick='game.fn.detail(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='编辑' class='btn btn-primary btn-circle check' onclick='game.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='删除' class='btn btn-primary btn-circle check' onclick='game.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                    }
                });
            },
            rowCallback: function (row, data) {

            },
            "delete": function (id) {

                if (id != null) {
                    $.each(game.v.list, function (i, item) {
                        if (item.id == id ) {
                            $("#delete").modal("show");
                            game.v.gameId = item.id;
                        }
                    });
                }

                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/game/delete", {
                        "gameId": game.v.gameId
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            game.v.dTable.ajax.reload();
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
                    url : "${contextPath}/admin/game/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            game.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            "edit": function (gameId) {
                window.location.href = "${contextPath}/admin/game/edit?gameId=" + gameId;
            },
            "add": function () {
                window.location.href = "${contextPath}/admin/game/add";
            },
            "detail": function (gameId) {
                window.location.href = "${contextPath}/admin/game/detail?gameId=" + gameId;
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        game.v.dTable.ajax.reload(null, false);
                    } else {
                        game.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        game.fn.init();
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

