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
                    <label>设备序列号</label>
                    <input type="text" class="input-sm form-control" id="deviceSerial" name="deviceSerial" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>使用状态</label>
                    <select id="usedState" name="usedState" class="select">
                        <option value="">全部</option>
                        <option value="1">已被使用</option>
                        <option value="0">未被使用</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>设备类型</label>
                    <select id="deviceType" name="deviceType" class="select">
                        <option value="">全部</option>
                        <option value="1">血压</option>
                        <option value="2">血糖</option>
                    </select>
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
                    <th>设备序列号</th>
                    <th>功能类型</th>
                    <th>设备类型</th>
                    <th>生产年份</th>
                    <th>生产月份</th>
                    <th>使用状态</th>
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
                        "url": "${contextPath}/admin/devices/getDevicesDataList",
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
                        {"data": "deviceSerial"},
                        {
                            "data": "deviceType",
                            "render" : function(data) {
                                var devtype = "";
                                if(data == "1"){
                                    devtype = "血压";
                                } else if(data == "2"){
                                    devtype = "血糖";
                                } else if(data == "3"){
                                    devtype = "体重";
                                } else if(data == "4"){
                                    devtype = "运动";
                                } else {
                                }
                                return devtype;
                            }
                        },
                        {
                            "data": "bak1",
                            "render" : function(data) {
                                var devtype = "";
                                if(data == "0"){
                                    devtype = "普通设备";
                                } else if(data == "1"){
                                    devtype = "GSM设备";
                                } else {
                                }
                                return devtype;
                            }
                        },
                        {
                            "data": "deviceProYear",
                            "render": function (data) {
                                if (data == 1) {
                                    return '男';
                                }
                                else {
                                    return '女';
                                }
                            }
                        },
                        {"data": "deviceProMonth"},
                        {
                            "data": "usedState",
                            "render": function (data) {
                                var usedstate = "";
                                if(data == "0"){
                                    usedstate = "未被使用";
                                } else if(data == "1"){
                                    usedstate = "已被使用";
                                } else {
                                }
                                return usedstate;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.deviceSerial = $("#deviceSerial").val();
                        aoData.usedState = $("#usedState").val();
                        aoData.usedState = $("#deviceType").val();
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

