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
                        <a href="${contextPath}/admin/category/indexStill" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="category.fn.batchDelete();" title="删除" class="tooltips">
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
                    <th>名称</th>
                    <th>剧照数量</th>
                    <th>更新时间</th>
                    <th>封面</th>
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
                        <h4 class="modal-title" id="showText">确定删除该分类？</h4>
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

        <div class="modal fade" id="deletes" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">该分类下有剧照，无法删除？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
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
                        "url": "${contextPath}/admin/category/listStill",
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
                            "data": "updateDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
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
                            "data": null,
                            "render": function (data) {
                                category.v.list.push(data);
                                var detail ="<button title='编辑' class='btn btn-primary btn-circle check' onclick='category.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='删除' class='btn btn-primary btn-circle check' onclick='category.fn.delete(" + data.id + "," + data.status +  ")'> " +
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
                /*$('td', row).last().find(".check").click(function () {
                 var checkbox = $('td', row).first().find("input[type='checkbox']");
                 creator.fn.give(checkbox, [data.id]);
                 });*/
            },
            "delete": function (id, status) {

                if (id != null) {
                    $.each(category.v.list, function (i, item) {
                        if (item.id == id && item.count == 0) {
                            $("#delete").modal("show");
                            console.log(0);
                            category.v.categoryId = item.id;
                        }else if (item.id == id && item.count != 0) {
                            console.log(1);
                            $("#deletes").modal("show");
                            category.v.categoryId = item.id;
                        }
                    });
                }

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
            batchDelete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/category/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            category.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
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
                            $("#image").prop("src", item.url);
                            $("#infoModal").modal("show");
                            category.v.categoryId = item.id;
                        }
                    });
                }
            },
            "confirm": function () {

                /*$("#infoForm").ajaxSubmit({
                    url : "${contextPath}/admin/category/editStill",
                    type : "POST",
                    data : {
                        "categoryId": category.v.categoryId,
                        "name": $('#name').val()
                    },
                    success : function(result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#infoModal").modal("hide");
                            category.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });*/

                $("#infoForm").ajaxSubmit({
                    url : "${contextPath}/admin/category/editStill",
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

