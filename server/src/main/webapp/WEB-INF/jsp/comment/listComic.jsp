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
            <a href="${contextPath}/admin/comment/index" class="btn btn-outline btn-primary btn-lg" role="button">视频评论</a>
            <a href="${contextPath}/admin/comment/indexNovel" class="btn btn-outline btn-primary btn-lg" role="button">小说评论</a>
            <a href="${contextPath}/admin/comment/indexComic" class="btn btn-outline btn-primary btn-lg" role="button">漫画评论</a>
        </div>
        <div class="block-area" id="search">
            <div class="row">
                <div class="col-md-2 form-group">
                    <label>用户账号：</label>
                    <input type="text" class="input-sm form-control" id="mobile" name="mobile" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>漫画名称：</label>
                    <input type="text" class="input-sm form-control" id="name" name="name" placeholder="...">
                </div>
            </div>
        </div>
        <div class="block-area" id="alternative-buttons">
            <button id="c_search" class="btn btn-alt m-r-5">查询</button>
        </div>

        <hr class="whiter m-t-20"/>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>用户账号</th>
                    <th>漫画名称</th>
                    <th>回数</th>
                    <th>回数名称</th>
                    <th>评论时间</th>
                    <th>点赞数</th>
                    <th>评论内容</th>
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
                        <h4 class="modal-title" id="showText">确定删除该评论？</h4>
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

    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    comment = {
        v: {
            list: [],
            dTable: null,
            userId: 0
        },
        fn: {
            init: function () {
                comment.fn.dataTableInit();

                $("#c_search").click(function () {
                    comment.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                comment.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/comment/listComic",
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
                        {"data": "fromUser.mobile"},
                        {"data": "workComic.work.name"},
                        {"data": "workComic.series"},
                        {"data": "workComic.name"},
                        {
                            "data": "createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "praise"},
                        {"data": "content"},
                        {
                            "data": null,
                            "render": function (data) {
                                var detail ="<button title='删除' class='btn btn-primary btn-circle check' onclick='comment.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";

                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.mobile = $("#mobile").val();
                        aoData.name = $("#name").val();
                    }
                });
            },
            rowCallback: function (row, data) {
            },
            "delete": function (id) {

                $("#delete").modal("show");
                $('#confirm').click(function () {

                    $leoman.ajax("${contextPath}/admin/comment/delete", {
                        "commentId": id,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            comment.v.dTable.ajax.reload();
                        } else {
                            $leoman.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        comment.v.dTable.ajax.reload(null, false);
                    } else {
                        comment.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        comment.fn.init();
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

