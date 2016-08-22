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

        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a href="${contextPath}/admin/feedback/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="feedback.fn.delete();" title="删除" class="tooltips">
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
                    <th>用户昵称</th>
                    <th>用户账号</th>
                    <th>反馈时间</th>
                    <th>意见详情</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">详情</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <textarea cols="120" rows="6" id="description" name="description" class="form-control" placeholder="..." disabled></textarea>
                                </div>
                            </div>
                        </form>
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
    feedback = {
        v: {
            list: [],
            dTable: null,
            feedbackId: 0
        },
        fn: {
            init: function () {

                feedback.fn.dataTableInit();

            },
            dataTableInit: function () {
                feedback.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/feedback/list",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "id",
                            "render": function (data) {
                                var checkbox = "<input type='checkbox' class='pull-left list-check' value=" + data + ">";
                                return checkbox;
                            }
                        },
                        {"data": "userInfo.nickname"},
                        {"data": "userInfo.mobile"},
                        {
                            "data": "createDate",
                            "render" : function(data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            "data": "content",
                            "render" : function(data) {
                                var content;
                                if(data.length > 30) {
                                    content = data.substring(0,30);
                                    content = content + "...";
                                    return content;
                                }
                                return data;
                            }
                        },
                        {
                            "data": null,
                            "render": function (data) {
                                feedback.v.list.push(data);
                                /*var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"feedback.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i></button>";*/
                                var detail ="<button title='查看' class='btn btn-primary btn-circle check' onclick='feedback.fn.detail(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";
                                return detail ;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {

                    }
                });
            },
            "detail": function (id) {
                if (id != null) {
                     $.each(feedback.v.list, function (i, item) {
                         if (item.id == id) {
                             $('#description').val(item.content);
                             $("#infoModal").modal("show");
                         }
                     });
                 }
            },
            delete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/feedback/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            feedback.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            /*delete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/feedback/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(!result.status) {
                            $common.fn.notify(result.msg);
                            return;
                        }
                        feedback.v.dTable.ajax.reload();
                    }
                });
            },*/
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        feedback.v.dTable.ajax.reload(null, false);
                    } else {
                        feedback.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        feedback.fn.init();
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

