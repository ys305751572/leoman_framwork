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
                        <a href="${contextPath}/admin/exchange/index" title="刷新" class="tooltips">
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
                    <th>账号</th>
                    <th>昵称</th>
                    <th>兑换物品</th>
                    <th>兑换时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">确认邮寄</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">快递名称：</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="name" name="name" maxlength="20"
                                           data-rule="required" placeholder="请输入快递名称"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">快递单号：</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="num" name="num" maxlength="20"
                                           data-rule="required" placeholder="请输入快递单号"/>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirm" class="btn btn-primary">确定
                            </button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <div class="modal fade" id="check" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">详情</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForms" method="post" enctype="multipart/form-data" class="form-horizontal"  role="form">
                            <input type="hidden" id="hiddenreserveIds" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">物品名字：</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append " style="width: 180px;" id="giftName" name="giftName" maxlength="20" data-rule="required" placeholder="请输入快递名称"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">馒头数：</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append " style="width: 180px;" id="exchangeCoin" name="exchangeCoin" maxlength="20" data-rule="required" placeholder="请输入快递单号"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">收货人：</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append " style="width: 180px;" id="receiverName" name="receiverName" maxlength="20" data-rule="required" placeholder="请输入快递名称"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号：</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append " style="width: 180px;" id="receiverMobile" name="receiverMobile" maxlength="20" data-rule="required" placeholder="请输入快递单号"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">收货地址：</label>
                                <div class="col-sm-5">
                                <input type="text" class="form-control input-append " style="width: 180px;" id="receiverAddress" name="receiverAddress" maxlength="20" data-rule="required" placeholder="请输入快递单号"/>
                                </div>
                            </div>
                        </form>
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
    exchange = {
        v: {
            list: [],
            dTable: null,
            exchangeId: 0
        },
        fn: {
            init: function () {
                exchange.fn.dataTableInit();

            },
            dataTableInit: function () {
                exchange.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/exchange/list",
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
                        {"data": "mobile"},
                        {"data": "nickname"},
                        {"data": "giftName"},
                        {
                            "data": "createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {
                            "data": "status",
                            render: function (data) {
                                if (data == 0) {
                                    return "待邮寄";
                                } else if (data == 1){
                                    return "待签收";
                                }else if (data == 2){
                                    return "已签收";
                                }
                            }
                        },
                        {
                            "data": null,
                            "render": function (data) {
                                exchange.v.list.push(data);
                                var detail = "<button title='查看' class='btn btn-primary btn-circle detail' onclick='exchange.fn.detail(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";
                                if (data.status == 0) {
                                    detail += "<button title='确认邮寄' class='btn btn-primary btn-circle' onclick='exchange.fn.check(" + data.id + ")'> " +
                                            "<i class='fa fa-check'></i></button>&nbsp;&nbsp;";
                                }else {
                                    detail += data.courierName + "：" + data.courierSn;
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
            "detail": function (id) {

                if (id != null) {
                    $.each(exchange.v.list, function (i, item) {
                        if (item.id == id) {

                            $('#giftName').val(item.giftName);
                            $('#exchangeCoin').val(item.exchangeCoin);
                            $('#receiverName').val(item.receiverName);
                            $('#receiverMobile').val(item.receiverMobile);
                            $('#receiverAddress').val(item.receiverAddress);

                            $("#check").modal("show");
                        }
                    });
                }
            },
            "check": function (id) {

                $("#infoModal").modal("show");
                $('#confirm').click(function () {

                    $leoman.ajax("${contextPath}/admin/exchange/check", {
                        "exchangeId": id,
                        "courierName": $('#name').val(),
                        "courierSn": $('#num').val()
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#infoModal").modal("hide");
                            exchange.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        exchange.v.dTable.ajax.reload(null, false);
                    } else {
                        exchange.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        exchange.fn.init();
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

