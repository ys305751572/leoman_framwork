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
                    <label>福利名称：</label>
                    <input type="text" class="input-sm form-control" id="title" name="title" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>类型：</label>
                    <select id="type" name="type" class="select">
                        <option value="">全部</option>
                        <option value="0">铃声</option>
                        <option value="1">经验值</option>
                        <option value="2">实物</option>
                        <option value="3">表情包</option>
                        <option value="4">商城购买</option>
                        <option value="5">游戏兑换码</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="block-area" id="alternative-buttons">
            <button id="c_search" class="btn btn-alt m-r-5">查询</button>
        </div>
        <%--<div class="block-area">
            <button type="button" onclick="work.fn.add();" class="btn btn-alt m-r-5">新增视频</button>
        </div>
        <hr class="whiter m-t-20"/>--%>

        <hr class="whiter m-t-20"/>

        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" onclick="welfare.fn.add();" title="添加福利" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/work/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="welfare.fn.delete();" title="删除" class="tooltips">
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
                    <th>封面</th>
                    <th>标题</th>
                    <th>副标题</th>
                    <th>类型</th>
                    <th>所需馒头</th>
                    <th>添加时间</th>
                    <th>兑换次数</th>
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
                        <h4 class="modal-title">确定删除该福利？</h4>
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
    welfare = {
        v: {
            list: [],
            dTable: null,
            welfareId: 0
        },
        fn: {
            init: function () {
                welfare.fn.dataTableInit();

                $("#c_search").click(function () {
                    welfare.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                welfare.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/welfare/list",
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
                        {"data": "title"},
                        {"data": "subTitle"},
                        {
                            "data": "type",
                            render: function (data) {
                                if (data == 0) {
                                    return "铃声";
                                }else if (data == 1) {
                                    return "经验值";
                                }else if (data == 2) {
                                    return "实物";
                                }else if (data == 3) {
                                    return "表情包";
                                }else if (data == 4) {
                                    return "商城购买";
                                }else if (data == 5) {
                                    return "游戏兑换码";
                                }
                            }},
                        {"data": "coin"},
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
                        {"data": "num"},
                        {
                            "data": null,
                            "render": function (data) {
                                welfare.v.list.push(data);
                                var detail ="<button title='查看' class='btn btn-primary btn-circle check' onclick='welfare.fn.detail(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='编辑' class='btn btn-primary btn-circle check' onclick='welfare.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='删除' class='btn btn-primary btn-circle check' onclick='welfare.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.title = $("#title").val();
                        aoData.type = $("#type").val();
                    }
                });
            },
            rowCallback: function (row, data) {
            },
            "delete": function (id) {

                if (id != null) {
                    $.each(welfare.v.list, function (i, item) {
                        if (item.id == id ) {
                            $("#delete").modal("show");
                            welfare.v.welfareId = item.id;
                        }
                    });
                }

                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/welfare/delete", {
                        "welfareId": welfare.v.welfareId
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            welfare.v.dTable.ajax.reload();
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
                    url : "${contextPath}/admin/welfare/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            welfare.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            "edit": function (welfareId) {
                window.location.href = "${contextPath}/admin/welfare/edit?welfareId=" + welfareId;
            },
            "add": function () {
                window.location.href = "${contextPath}/admin/welfare/add";
            },
            "detail": function (welfareId) {
                window.location.href = "${contextPath}/admin/welfare/detail?welfareId=" + welfareId;
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        welfare.v.dTable.ajax.reload(null, false);
                    } else {
                        welfare.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        welfare.fn.init();
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

