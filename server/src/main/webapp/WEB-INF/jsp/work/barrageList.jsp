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
                <input type="hidden" id="id" name="id" value="${workVideo.id}">
                <input type="hidden" id="workId" name="workId" value="${workVideo.work.id}">
                <input type="hidden" id="creatorId" name="creatorId" value="${workCreator.userInfo.id}">
                <input type="hidden" id="length" name="length" value="${workVideo.length}">
            </div>
        </div>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>主创昵称</th>
                    <th>弹幕内容</th>
                    <th>时长（秒）</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>

        <div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">编辑主创弹幕</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm1" method="post" enctype="application/x-www-form-urlencoded" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId1" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择主创人员:</label>
                                <div class="col-sm-5">
                                    <select id="creatorList" style="width: 180px;" class="select"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">弹幕时间:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" style="width: 180px;" id="barrageTime" maxlength="20" data-rule="required" placeholder="请输入弹幕时间"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">弹幕内容:</label>
                                <div class="col-sm-5">
                                    <textarea cols="40" rows="6" id="detail" name="detail" class="form-control"
                                              data-rule="required" placeholder="请输入弹幕内容"></textarea>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="barrage.fn.confirmSub()" class="btn btn-primary">确定
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
    barrage = {
        v: {
            list: [],
            dTable: null,
            workId: 0
        },
        fn: {
            init: function () {
                barrage.fn.dataTableInit();

                //自动加载下拉框
                barrage.fn.getSelectList();

            },
            dataTableInit: function () {
                barrage.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/work/barrageList",
                        "type": "POST",
                        "data": {
                            workId: $("#id").val(),
                        },
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
                        {"data": "content"},
                        {"data": "time"},
                        {
                            "data": null,
                            "render": function (data) {
                                barrage.v.list.push(data);
                                var detail = "<button title='编辑' class='btn btn-primary btn-circle' onclick='barrage.fn.edit(" + data.id + ")'>" +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                return detail;
                            }
                        }
                    ],
                });
            },
            rowCallback: function (row, data) {

            },
            "barrageList": function (workId) {
                window.location.href = "${contextPath}/admin/work/indexBarrage?workId=" + workId;
            },
            "edit": function (id) {
                if (id != null) {
                    $.each(barrage.v.list, function (i, item) {
                        if (item.id == id) {
                            console.log(item);
                            $('#creatorList').val(item.userInfo.id);
                            $('#creatorList').selectpicker('refresh');
                            $('#barrageTime').val(item.time);
                            $('#detail').val(item.content);
                            $("#add").modal("show");
                            barrage.v.workId = item.id;
                        }
                    });
                }
            },
            "confirmSub": function () {
                var flag = true;
                var creator = $('#creatorList').val();
                var detail = $('#detail').val();
                var time = $('#barrageTime').val();
                if (creator == null || creator == '') {
                    $common.fn.notify("请选择主创", "error");
                    flag = false;
                    return;
                }

                if (null == time || time == '') {
                    $common.fn.notify("请输入弹幕时间", "error");
                    flag = false;
                    return;
                }

                if (isNaN(time)) {
                    $common.fn.notify("弹幕时间必须为数字", "error");
                    flag = false;
                    return;
                }

                if (time > $('#length').val()) {
                    $common.fn.notify("弹幕时间应小于视频总时长", "error");
                    flag = false;
                    return;
                }

                if (flag) {
                    $leoman.ajax("${contextPath}/admin/work/addBarrage", {
                        "workVideoId": $('#id').val(),
                        "barrageId": barrage.v.workId,
                        "creatorId": $('#creatorList option:selected').val(),
                        time: $('#barrageTime').val(),
                        detail: $('#detail').val()
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#add").modal("hide");
                            barrage.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                }
            },
            getSelectList: function () {
                //var creatorId = $('#creatorId').val();

                $leoman.ajax("${contextPath}/admin/work/videoCreatorList",{
                    "workId": $('#workId').val(),
                } , function (result) {
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

                    /*if (null != creatorId && creatorId != '') {
                        $('#creatorList').val(creatorId);
                        $('#creatorList').selectpicker('refresh');
                    }*/
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
        barrage.fn.init();
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

