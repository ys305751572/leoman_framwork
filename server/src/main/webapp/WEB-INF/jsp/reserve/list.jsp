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
                    <label>城市</label>
                    <input type="text" class="input-sm form-control" id="cityId" name="cityId" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>球场</label>
                    <input type="text" class="input-sm form-control" id="stadiumId" name="stadiumId" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>赛制</label>
                    <select id="matchType" name="matchType" class="select">
                        <option value="">全部</option>
                        <option value="0">三人制</option>
                        <option value="1">五人制</option>
                        <option value="2">七人制</option>
                        <option value="3">十一人制</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>付款方式</label>
                    <select id="payment" name="payment" class="select">
                        <option value="">全部</option>
                        <option value="0">AA制</option>
                        <option value="1">全额</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>保险</label>
                    <select id="name" name="name" class="select">
                        <option value="">全部</option>
                        <option value="1">5万元经济型意外保险</option>
                        <option value="2">10万元基础型意外保险</option>
                        <option value="3">100万元高端型意外保险</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>组队状态</label>
                    <select id="status" name="status" class="select">
                        <option value="">全部</option>
                        <option value="0">正在组队</option>
                        <option value="1">组队成功</option>
                        <option value="2">组队失败</option>
                        <option value="3">比赛结束</option>
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
                    <th>比赛时间</th>
                    <th>城市</th>
                    <th>球场</th>
                    <th>创建者</th>
                    <th>赛制</th>
                    <th>付款方式</th>
                    <th>保险</th>
                    <th>已报人数</th>
                    <th>预定人数</th>
                    <th>状态</th>
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
                        "url": "${contextPath}/admin/reserve/list",
                        "type": "POST"
                    },
                    "columns": [
                        {
                            "data": "userId",
                            "render": function (data) {
//                                var checkbox = "<div class=\"icheckbox_minimal\" aria-checked=\"false\" aria-disabled=\"false\" style=\"position: relative;\"><input type=\"checkbox\" value="+ data +" class='pull-left list-check' style=\"position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);\"></div>";
                                var checkbox = "<input type='checkbox' class='pull-left list-check' value=" + data + ">";
                                return checkbox;
                            }
                        },
                        {"data": "startDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "cityId"},
                        {"data": "stadiumId"},
                        {"data": "user.nickName"},
                        {"data": "matchType",
                            render:function(data){
                                if(data==0){
                                    return "三人制";
                                }
                                if(data==1){
                                    return "五人制";
                                }
                                if(data==2){
                                    return"七人制";
                                }
                                if(data==3){
                                    return"十一人制";
                                }
                            }
                        },
                        {"data": "payment",
                            render:function(data) {
                                if (data == 0) {
                                    return "AA制";
                                }
                                if (data == 1) {
                                    return "全额";
                                }
                            }
                        },
                        {"data": "systemInsurance.name"},
                        {"data":"userId"},
                        {"data": "createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "status",
                            render: function (data) {
                                if (data==0){
                                    return"正在组队";
                                }
                                if (data==1){
                                    return"组队成功";
                                }
                                if (data==2){
                                    return"组队失败";
                                }
                                if (data==3){
                                    return"比赛结束";
                                }
                            }
                        },
                        {
                            "data": "userId",
                            "render": function (data) {
                                var detail = "<button title='查看' class='btn btn-primary btn-circle detail' ONCLICK='$user.fn.detail("+ data +")'> " +
                                        "<i class='fa fa-eye'></i></button>";
                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.cityId = $("#cityId").val();
                        aoData.stadiumId = $("#stadiumId").val();
                        aoData.matchType = $("#matchType").val();
                        aoData.payment = $("#payment").val();
                        aoData.name = $("#name").val();
                        aoData.status = $("#status").val();
                    }
                });
            },
            rowCallback: function (row, data) {
                var items = $user.v.list;
                $('td', row).last().find(".add").attr("href", 'admin/reserve/detail?id=' + data.id);
            },
            "detail" : function(userId) {
                window.location.href = "${contextPath}/admin/reserve/detail?userId=" + userId;
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

