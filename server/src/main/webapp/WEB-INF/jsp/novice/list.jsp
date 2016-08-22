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
                    <label>内容</label>
                    <input type="text" class="input-sm form-control" id="content1" name="content" placeholder="...">
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
                        <a data-toggle="modal" href="${contextPath}/admin/novice/add" title="新增" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/novice/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="$user.fn.delete();" title="删除" class="tooltips">
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
                    <th>内容</th>
                    <th>排序</th>
                    <th>时间</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    $user = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $user.fn.dataTableInit();
                $("#c_search").click(function () {
                    $user.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                $user.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/novice/list",
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
                        {"data": "content"},
                        {"data": "index2"},
                        {
                            "data": "createDate",
                            "render" : function(data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            "data": "id",
                            "render": function (data) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$user.fn.detail(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i></button>";

                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$user.fn.edit(\'" + data + "\')\">" +
                                        "<i class='fa fa-pencil-square-o'></i></button>";
                                return detail + "&nbsp;&nbsp;" + edit;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.content = $("#content1").val();
                    }
                });
            },
            edit: function (id) {
                window.location.href = "${contextPath}/admin/novice/add?id=" + id;
            },
            detail: function (id) {
                window.location.href = "${contextPath}/admin/novice/detail?id=" + id;
            },
            delete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/novice/deleteBatch",
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
                        $user.v.dTable.ajax.reload();
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
        $user.fn.init();
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

