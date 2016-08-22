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
                    <label>姓名</label>
                    <input type="text" class="input-sm form-control" id="username" name="username" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>手机</label>
                    <input type="text" class="input-sm form-control" id="mobile" name="mobile" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>生日</label>
                    <input type="text" class="input-sm form-control form_datetime" id="birthday" name="birthday"
                           placeholder="..." readonly="readonly">
                </div>
                <div class="col-md-2 form-group">
                    <label>注册时间</label>
                    <input type="text" class="input-sm form-control form_datetime" id="" name="" placeholder="..."
                           readonly="readonly">
                </div>
                <div class="col-md-2 form-group">
                    <label>性别</label>
                    <select id="sex" name="sex" class="select">
                        <option value="">全部</option>
                        <option value="男">男</option>
                        <option value="女">女</option>
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
                    <th>用户姓名</th>
                    <th>设备类型</th>
                    <th>测量状态</th>
                    <th>测量血糖</th>
                    <th>血糖状态</th>
                    <th>预警血糖</th>
                    <th>测量时间</th>
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
                        "url": "${contextPath}/admin/measure/getXtDataList",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "measure_id",
                            "render": function (data) {
//                                var checkbox = "<div class=\"icheckbox_minimal\" aria-checked=\"false\" aria-disabled=\"false\" style=\"position: relative;\"><input type=\"checkbox\" value="+ data +" class='pull-left list-check' style=\"position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);\"></div>";
                                var checkbox = "<input type='checkbox' class='pull-left list-check' value=" + data + ">";
                                return checkbox;
                            }
                        },
                        {"data": "measureName"},
                        {
                            "data": "measureType",
                            "render" : function(data) {
                                var mstype = "";
                                if(data == 1){
                                    mstype = "血压";
                                } else if(data == 2){
                                    mstype = "血糖";
                                } else if(data == 3){
                                    mstype = "心率";
                                } else if(data == 4){
                                    mstype = "血脂";
                                } else {
                                }
                                return mstype;
                            }
                        },
                        {
                            "data": "measureState",
                            "render" : function(data) {
                                var state = "";
                                if(data == 1){
                                    state = "设备记录";
                                } else if(data == 2){
                                    state = "手动记录";
                                } else {
                                }
                                return state;
                            }
                        },
                        {"data": "result4"},
                        {
                            "data": "result4",
                            "render" : function(data) {
                                var jt = "";
                                var xt = parseFloat(data);
                                if(xt < 3.9){
                                    jt = "<span style='color: red; font-weight:bold;'>↓</span>";
                                } else if(xt > 6.1){
                                    jt = "<span style='color: red; font-weight:bold;'>↑</span>";
                                } else {
                                    jt = "<span style='font-weight:bold;'>--</span>";
                                }
                                return '<div align="center">' + jt + '</div>';
                            }
                        },
                        {
                            "data": "",
                            "render" : function() {
                                return '<div align="center">3.9~6.1</div>';
                            }
                        },
                        {
                            "data": "bak6",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {
                            "data": "userId",
                            "render": function (data) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle add' onclick=\"$user.fn.detail(\'"+ data +"\')\">" +
                                        "<i class='fa fa-eye'></i></button>";
                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.measureName = $("#measureName").val();
                        aoData.sendTimeQ = $("#sendTimeQ").val();
                        aoData.sendTimeZ = $("#sendTimeZ").val();
                        aoData.xtstate = $("#xtstate").val();
                    }
                });
            },
            detail : function(userId) {
                $.ajax({
                    "url" : "${contextPath}/admin/aoluser/sfUserInfo",
                    "data" : {
                        "userId":userId
                    },
                    "dataType": "json",
                    "type" : "POST",
                    "success" : function(result) {
                        if(!result.status) {
                            console.log("!result.status")
                            $common.fn.notify(result.msg);
                            return;
                        }
                        window.location.href = "${contextPath}/admin/aoluser/detail?userId=" + userId;
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

