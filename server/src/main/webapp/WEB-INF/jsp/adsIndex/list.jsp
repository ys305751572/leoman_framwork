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
                    <%--<li>
                        <a data-toggle="modal" onclick="adsIndex.fn.add();" title="新增推荐" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>--%>
                    <li>
                        <a href="${contextPath}/admin/adsIndex/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <%--<li class="show-on" style="display: none;">
                        <a onclick="adsIndex.fn.batchDelete();" title="删除" class="tooltips">
                            <i class="sa-list-delete"></i>
                        </a>
                    </li>--%>
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
                    <th>标题</th>
                    <th>小标题</th>
                    <th>封面</th>
                    <th>链接页</th>
                    <th>添加日期</th>
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
                        <h4 class="modal-title" id="showText">确定删除该推荐？</h4>
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
    adsIndex = {
        v: {
            list: [],
            dTable: null,
            adsIndexId: 0
        },
        fn: {
            init: function () {
                adsIndex.fn.dataTableInit();
            },
            dataTableInit: function () {
                adsIndex.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/adsIndex/list",
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
                        {"data": "title"},
                        {"data": "subTitle"},
                        {
                            "data": "cover",
                            render: function (data) {
                                if (null == data || data == '') {
                                    return "暂无";
                                } else {
                                    return '<img src="' + data + '" width="50px" height="50px" />';
                                }
                            }
                        },
                        {
                            "data": "position",
                            render: function (data) {
                                if (data == 0) {
                                    return "帖子";
                                }else if (data == 1) {
                                    return "视频";
                                }else if (data == 2) {
                                    return "小说";
                                }else if (data == 3) {
                                    return "漫画";
                                }else if (data == 4) {
                                    return "资源";
                                }else if (data == 5) {
                                    return "福利社";
                                }
                            }},
                        {
                            "data": "createDate",
                            render: function (data) {
                                if (null != data && data != '') {
                                    return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                                } else {
                                    return '暂无';
                                }
                            }
                        },
                        {
                            "data": null,
                            "render": function (data) {
                                adsIndex.v.list.push(data);
                                var detail = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick='adsIndex.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='查看' class='btn btn-primary btn-circle' onclick='adsIndex.fn.detail(" + data.id + ")'> " +
                                 "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";
                                /*detail += "<button title='删除' class='btn btn-primary btn-circle' onclick='adsIndex.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";*/

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
            "edit": function (adsIndexId) {
                window.location.href = "${contextPath}/admin/adsIndex/edit?adsIndexId=" + adsIndexId;
            },
            /*"add": function () {
                window.location.href = "${contextPath}/admin/adsIndex/add";
            },*/
            "detail": function (adsIndexId) {
                window.location.href = "${contextPath}/admin/adsIndex/detail?adsIndexId=" + adsIndexId;
            },
            "delete": function (id) {

                if (id != null) {
                    $.each(adsIndex.v.list, function (i, item) {
                        if (item.id == id ) {
                            $("#delete").modal("show");
                            adsIndex.v.adsIndexId = item.id;
                        }
                    });
                }

                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/adsIndex/delete", {
                        "adsIndexId": adsIndex.v.adsIndexId
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            adsIndex.v.dTable.ajax.reload();
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
                    url : "${contextPath}/admin/adsIndex/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            adsIndex.v.dTable.ajax.reload();
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
                        adsIndex.v.dTable.ajax.reload(null, false);
                    } else {
                        adsIndex.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        adsIndex.fn.init();
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
        format: 'yyyy-mm-dd hh:ii:ss'
    });
</script>
</body>
</html>

