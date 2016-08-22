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
                        <a data-toggle="modal" onclick="category.fn.add();"  title="新增分类" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/category/index" title="刷新" class="tooltips">
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
                    <th>名称</th>
                    <th>资源数量</th>
                    <th>图标</th>
                    <th>更新时间</th>
                    <th>状态</th>
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
                        <h4 class="modal-title" id="showText">下架后，该分类不会在前端出现，确定下架？</h4>
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
                        <h4 class="modal-title">新增分类</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">分类名称:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="name" name="name" maxlength="20"
                                           data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">封面：</label>
                                <div class="fileupload fileupload-new" data-provides="fileupload">
                                    <div class="fileupload-preview thumbnail form-control">
                                        <img src="" id="image">
                                    </div>
                                    <div class="col-sm-6 control-label">
                                        <span class="btn btn-file btn-alt btn-sm">
                                            <span class="fileupload-new">选择图片</span>
                                            <span class="fileupload-exists">更改</span>
                                            <input id="imageFile" name="imageFile" type="file"/>
                                        </span>
                                        <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="category.fn.confirm()" class="btn btn-primary">确定
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
    category = {
        v: {
            list: [],
            dTable: null,
            categoryId: 0
        },
        fn: {
            init: function () {
                category.fn.dataTableInit();
            },
            dataTableInit: function () {
                category.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/category/list",
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
                        {"data": "count"},
                        {
                            "data": "url",
                            render: function (data) {
                                if (null == data || data == '') {
                                    return "暂无";
                                } else {
                                    return '<img src="' + data + '" width="50px" height="50px" />';
                                }
                            }
                        },
                        {
                            "data": "updateDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {
                            "data": "status",
                            render: function (data) {
                                if (data == 0) {
                                    return "显示";
                                }else if (data == 1) {
                                    return "隐藏";
                                }
                            }},
                        {
                            "data": null,
                            "render": function (data) {
                                category.v.list.push(data);
                                var detail ="<button title='编辑' class='btn btn-primary btn-circle check' onclick='category.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                if (data.status == 0) {
                                    detail += "<button title='隐藏' class='btn btn-primary btn-circle check' onclick='category.fn.delete(" + data.id + "," + data.status +  ")'> " +
                                            "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                }else if (data.status == 1) {
                                    detail += "<button title='取消隐藏' class='btn btn-primary btn-circle check' onclick='category.fn.delete(" + data.id + "," + data.status +  ")'> " +
                                            "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                }
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
            "delete": function (id, status) {

                if (status == 0) {
                    $('#showText').html('隐藏后，该分类不会在前端出现，确定隐藏？');
                } else {
                    $('#showText').html('确定取消隐藏？');
                }

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    var tempStatus = 0;
                    if (status == 0) {
                        tempStatus = 1;
                    }

                    $leoman.ajax("${contextPath}/admin/category/isDown", {
                        "categoryId": id,
                        "status": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            category.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "add": function () {
                $('#name').val("");
                $("#image").prop("src", "");
                $("#infoModal").modal("show");
                category.v.categoryId = null;
            },
            "edit": function (id) {
                if (id != null) {
                    $.each(category.v.list, function (i, item) {
                        if (item.id == id) {
                            $('#name').val(item.name);
                            $("#infoModal").modal("show");
                            $("#image").prop("src", item.url);

                            category.v.categoryId = item.id;
                        }
                    });
                }
            },
            "confirm": function () {
                <%--$leoman.ajax("${contextPath}/admin/category/edit", {--%>
                    <%--"categoryId": category.v.categoryId,--%>
                    <%--name: $('#name').val()--%>
                <%--}, function (result) {--%>
                    <%--if (result == 1) {--%>
                        <%--$common.fn.notify("操作成功", "success");--%>
                        <%--$("#infoModal").modal("hide");--%>
                        <%--category.v.dTable.ajax.reload();--%>
                    <%--} else {--%>
                        <%--$common.fn.notify("操作失败", "error");--%>
                    <%--}--%>
                <%--});--%>
                $("#infoForm").ajaxSubmit({
                    url : "${contextPath}/admin/category/edit",
                    data : {
                        "categoryId": category.v.categoryId,
                        name: $('#name').val()
                    },
                    success : function() {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        category.v.dTable.ajax.reload();
                    },
                    error : function() {
                        console.log("error");
                    }
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        barrage.v.dTable.ajax.reload(null, false);
                    } else {
                        barrage.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        category.fn.init();
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

