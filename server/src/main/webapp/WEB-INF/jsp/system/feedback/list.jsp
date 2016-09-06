<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="ThemeBucket">
  <link rel="shortcut icon" href="#" type="image/png">
  <title>Dynamic Table</title>
  <%@ include file="../../inc/new2/css.jsp"%>
</head>
<body class="sticky-header">
<section>
    <%@ include file="../../inc/new2/menu.jsp"%>
    <!-- main content start-->
    <div class="main-content" >
        <%@ include file="../../inc/new2/header.jsp"%>
        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group col-sm-1">
                                <select class="form-control input-sm" id="status">
                                    <option value="">全部</option>
                                    <option value="0">未处理</option>
                                    <option value="1">已处理</option>
                                </select>
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="startDate" name="startDate" placeholder="开始时间" class="form-control form_datetime" readonly/>
                            </div>
                            <div class="form-group col-sm-2">
                                <input type="text" id="endDate" name="endDate" placeholder="结束时间" class="form-control form_datetime" readonly/>
                            </div>
                            <button id="c_search" class="btn btn-info">搜索</button>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            日志列表
                            <span class="tools pull-right">
                               <button class="btn btn-default " type="button"><i class="fa fa-refresh"></i>刷新</button>
                               <%--<button class="btn btn-info" type="button" onclick="$admin.fn.add();">新增角色</button>--%>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="adv-table">
                                <table  class="display table table-bordered table-striped" id="dataTables" width="100%">
                                    <thead>
                                        <tr>
                                            <th><input type="checkbox" class="list-parent-check" onclick="$leoman.checkAll(this);"/></th>
                                            <th>用户</th>
                                            <th>是否处理</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>

                        <!-- Modal -->
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-hidden="true">&times;</button>
                                        <h4 class="modal-title">日志信息</h4>
                                    </div>
                                    <div class="modal-body row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <div class="col-sm-12">
                                                    <textarea rows="6" id="messageDetail" class="form-control" disabled></textarea>
                                                </div>
                                            </div>
                                            <hr>
                                            <%--<div class="form-group">--%>
                                                <%--<div class="col-sm-6">--%>
                                                    <%--<button type="button" id="getOptionBtn" class="btn btn-primary">关闭--%>
                                                    <%--</button>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</section>
<%@ include file="../../inc/new2/foot.jsp"%>
<script>
    $('.form_datetime').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView : 2,
        showMeridian: true,
        format: 'yyyy-mm-dd'
    });


    $admin = {
        v: {
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $admin.fn.dataTableInit();
                $("#c_search").click(function () {
                    $admin.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                $admin.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "bSort":false,
                    "ajax": {
                        "url": "${contextPath}/admin/feedback/list",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "id",
                            "render": function (data) {
                                var checkbox = "<input type='checkbox' class='list-check' onclick='$leoman.subSelect(this);' value=" + data + ">";
                                return checkbox;
                            }
                        },
                        {
                            "data": "userId"
                        },
                        {
                            "data": "status",
                            "render" : function(data) {
                                if (data == 0) {
                                    return "未处理";
                                }
                                else {
                                    return "已处理";
                                }
                            }
                        },
                        {
                            "data": "createDate",
                            "render" : function(data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            "data": "content",
                            "render": function (data) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$admin.fn.openModel(\'" + data + "\')\">" +
                                        "<i class='fa fa-eye'></i> 查看日志信息</button>";

//                                var edit = "<button title='编辑' class='btn btn-primary btn-circle edit' onclick=\"$admin.fn.add(\'" + data + "\')\">" +
//                                        "<i class='fa fa-pencil-square-o'></i> 编辑</button>";

                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.logType = $("#logType").val();
                        aoData.startDate = $("#startDate").val();
                        aoData.endDate = $("#endDate").val();
                    }
                });
            },
            openModel: function(message) {
                $("#messageDetail").text(message);
                $("#myModal").modal("show");
                <%--$.ajax({--%>
                    <%--url : "${contextPath}/admin/log/getMessage",--%>
                    <%--type : "POST",--%>
                    <%--data : {--%>
                        <%--"id" : id--%>
                    <%--},--%>
                    <%--success : function(result) {--%>
                        <%--if(result.status == 0) {--%>
                            <%--var _message = result.data.logEntity.message;--%>
                            <%--$("#messageDetail").text(_message);--%>
                            <%--$("#myModal").modal("show");--%>
                        <%--}--%>
                        <%--else {--%>
                            <%--alert("错误信息查询错误");--%>
                        <%--}--%>
                    <%--}--%>
                <%--});--%>
            },
            delete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/cq/deleteBatch",
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
                        $admin.v.dTable.ajax.reload();
                    }
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        $admin.v.dTable.ajax.reload(null, false);
                    } else {
                        $admin.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        $admin.fn.init();
    })
</script>
</body>
</html>
