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
                        <a data-toggle="modal" onclick="dynamic.fn.add();" title="新增数据" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/creatorDynamic/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="dynamic.fn.batchDelete();" title="删除" class="tooltips">
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
                    <th>用户</th>
                    <th>互动</th>
                    <th>数据来源</th>
                    <th>更新时间</th>
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
                        <h4 class="modal-title" id="showText">确定删除该数据？</h4>
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

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">新增数据</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId1" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择主创人员:</label>
                                <div class="col-sm-5">
                                    <select id="creatorList" style="width: 150px;" class="select"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">事件:</label>
                                <div class="col-sm-5">
                                    <select id="detail" style="width: 150px;" class="select"></select>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="dynamic.fn.confirmSub()" class="btn btn-primary">确定
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
    dynamic = {
        v: {
            list: [],
            dTable: null,
            dynamicId: 0
        },
        fn: {
            init: function () {
                dynamic.fn.dataTableInit();

                //自动加载下拉框
                dynamic.fn.getSelectLists();

                //自动加载下拉框
                dynamic.fn.getSelect();

                $("#c_search").click(function () {
                    dynamic.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                dynamic.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/creatorDynamic/list",
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
                        {"data": "userInfo.nickname"},
                        {
                            "data": "status",
                            render: function (data) {
                                if (data == 1) {
                                    return "正在看帖子";
                                } else if (data == 2){
                                    return "正在回帖子";
                                } else if (data == 3){
                                    return "正在发帖子";
                                } else if (data == 4){
                                    return "正在看小说";
                                } else if (data == 5){
                                    return "正在看漫画";
                                } else if (data == 6){
                                    return "正在看网剧";
                                } else if (data == 7){
                                    return "正在发弹幕";
                                }
                            }
                        },
                        {
                            "data": "source",
                            render: function (data) {
                                if (data == 0) {
                                    return "系统获取";
                                } else if (data == 1){
                                    return "手动添加";
                                }
                            }
                        },
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
                                dynamic.v.list.push(data);
                                var detail = "<button title='删除' class='btn btn-primary btn-circle check' onclick='dynamic.fn.delete(" + data.id + ")'> " +
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
            getSelectLists: function () {
                var creatorId = $('#creatorId').val();

                $leoman.ajax("${contextPath}/admin/creatorDynamic/creatorList", null, function (result) {
                    if (null != result) {
                        $('#creatorList').empty();
                        // 获取返回的主创列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择所在主创</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.nickname + "</option>";
                        });
                        $('#creatorList').append(content);
                        $('#creatorList').selectpicker('refresh');
                    } else {
                        $common.notify("获取主创信息失败", "error");
                    }

                    if (null != creatorId && creatorId != '') {
                        $('#creatorList').val(creatorId);
                        $('#creatorList').selectpicker('refresh');
                    }
                });

            },
            getSelect: function () {

                $('#detail').empty();
                // 获取返回的主创列表信息，并循环绑定到select中
                var content = "<option value=''>请选择事件</option>";

                content += "<option value='1'>正在看帖子</option>";
                content += "<option value='2'>正在回帖子</option>";
                content += "<option value='3'>正在发帖子</option>";
                content += "<option value='4'>正在看小说</option>";
                content += "<option value='5'>正在看漫画</option>";
                content += "<option value='6'>正在看网剧</option>";
                content += "<option value='7'>正在发弹幕</option>";

                $('#detail').append(content);
                $('#detail').selectpicker('refresh');

            },
            "add": function () {
                $('#creatorList').val("");
                $('#detail').val("");
                $("#infoModal").modal("show");
            },
            "confirmSub": function () {

                $("#infoForm").ajaxSubmit({
                    url : "${contextPath}/admin/creatorDynamic/save",
                    data : {
                        "creatorId": $('#creatorList option:selected').val(),
                        "status":$('#detail option:selected').val()
                    },
                    success : function() {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        dynamic.v.dTable.ajax.reload();
                    },
                    error : function() {
                        console.log("error");
                    }
                });
            },
            "delete": function (id) {

                if (id != null) {
                    $.each(dynamic.v.list, function (i, item) {
                        if (item.id == id ) {
                            $("#delete").modal("show");
                            dynamic.v.dynamicId = item.id;
                        }
                    });
                }

                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/creatorDynamic/delete", {
                        "dynamicId": dynamic.v.dynamicId
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            dynamic.v.dTable.ajax.reload();
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
                    url : "${contextPath}/admin/creatorDynamic/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            dynamic.v.dTable.ajax.reload();
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
        dynamic.fn.init();
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

