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
                <input type="hidden" id="id" name="id" value="${work.id}">
                <input type="hidden" id="typeId" name="id" value="${work.category.id}">
                <%--<input type="hidden" id="creatorId" name="creatorId" value="${workCreator.userLogin.id}">--%>
                <div class="col-md-2 form-group">
                    <label>视频名称：</label>
                    <input type="text" value="${work.name}" class="input-sm form-control" name="name" placeholder="..." disabled>
                </div>
                <div class="col-md-2 form-group">
                    <label>总集数：</label>
                    <input type="text" value="${work.seriesCount}" class="input-sm form-control" name="name" placeholder="..." disabled>
                </div>
                <div class="col-md-2 form-group">
                    <label>已更新集数：</label>
                    <input type="text" value="${work.updateCount}" class="input-sm form-control" name="name" placeholder="..." disabled>
                </div>
                <div class="col-md-2 form-group">
                    <label>视频分类：</label>
                    <select id="typeList" style="width: 200px;" class="select" disabled></select>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-12 form-group" style="margin-top: 10px;">
                    <label>视频简介：</label>
                    <textarea cols="40" rows="4" id="description" name="description" class="form-control"
                              placeholder="..." disabled>${work.description}</textarea>
                </div>
            </div>
        </div>
        <hr class="whiter m-t-20"/>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>集数</th>
                    <th>剧集名称</th>
                    <th>更新时间</th>
                    <th>时长（秒）</th>
                    <th>播放量</th>
                    <th>弹幕数</th>
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
                        <h4 class="modal-title">编辑剧集</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="application/x-www-form-urlencoded" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">剧集数:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="num" name="num" maxlength="20"
                                           data-rule="required" disabled/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">剧集名称:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="name2" name="name2" maxlength="20"
                                           data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">视频链接:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="video" name="video" maxlength="20"
                                           data-rule="required" placeholder="请输入视频链接"/>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirm" onclick="video.fn.confirm()" class="btn btn-primary">确定
                            </button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">添加主创弹幕</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm1" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
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
                                    <input type="text" class="form-control input-append "
                                           style="width: 150px; length: 240px" id="subTime" name="subTime" maxlength="20"
                                           data-rule="required" placeholder="请输入弹幕时间"/>
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
                            <button type="button" id="confirmSub" onclick="video.fn.confirmSub()" class="btn btn-primary">确定
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
    video = {
        v: {
            list: [],
            dTable: null,
            workId: 0,
            length: 0
        },
        fn: {
            init: function () {
                video.fn.dataTableInit();

                //自动加载下拉框
                video.fn.getSelectList();

                //自动加载下拉框
                video.fn.getSelectLists();

            },
            dataTableInit: function () {
                video.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/work/videoList",
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
                        {"data": "series"},
                        {"data": "name"},
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
                        {"data": "length"},
                        {"data": "work.playNum"},
                        {"data": "work.barrageNum"},
                        {
                            "data": null,
                            "render": function (data) {
                                video.v.list.push(data);
                                var detail = "<button title='编辑' class='btn btn-primary btn-circle' onclick='video.fn.edit(" + data.id + ")'>" +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='主创弹幕列表' class='btn btn-primary btn-circle' onclick='video.fn.barrageList(" + data.id + ")'> " +
                                        "<i class='fa fa-table'></i></button>&nbsp;&nbsp;";
                                detail += "<button title='添加主创弹幕' class='btn btn-primary btn-circle' onclick='video.fn.add(" + data.id + "," + data.length + ")'> " +
                                        "<i class='fa fa-bell'></i></button>";

                                return detail;
                            }
                        }
                    ],
                });
            },
            rowCallback: function (row, data) {
                /* $('td', row).last().find(".check").click(function () {
                 var checkbox = $('td', row).first().find("input[type='checkbox']");
                 $user.fn.give(checkbox, [data.id]);
                 });*/
            },
            "barrageList": function (workId) {
                window.location.href = "${contextPath}/admin/work/indexBarrage?workId=" + workId;
            },
            "edit": function (id) {
                if (id != null) {
                    $.each(video.v.list, function (i, item) {
                        if (item.id == id) {
                            $('#num').val(item.series);
                            $('#name2').val(item.name);
                            $('#video').val(item.linkUrl);
                            $("#infoModal").modal("show");
                            video.v.workId = item.id;
                        }
                    });
                }
            },
            "confirm": function () {
                $leoman.ajax("${contextPath}/admin/work/update", {
                    workId: $('#id').val(),
                    "workVideoId": video.v.workId,
                    num: $('#num').val(),
                    videoName: $('#name2').val(),
                    video: $('#video').val(),
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        video.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });

            },
            "add": function (id, length) {

                if (id != null) {
                    $('#subTime').val('');
                    $('#detail').val('');
                    $("#add").modal("show");
                    video.v.workId = id;
                    video.v.length = length;
                }
            },
            "confirmSub": function () {
                var flag = true;
                var creator = $('#creatorList').val();
                var detail = $('#detail').val();
                var time = $('#subTime').val();
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

                if (time > video.v.length) {
                    $common.fn.notify("弹幕时间应小于视频总时长", "error");
                    flag = false;
                    return;
                }

                if (flag) {
                    $leoman.ajax("${contextPath}/admin/work/addBarrage", {
                        "workVideoId": video.v.workId,
                        "creatorId": $('#creatorList option:selected').val(),
                        time: $('#subTime').val(),
                        detail: $('#detail').val()
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#add").modal("hide");
                            video.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                }
            },
            getSelectList: function () {
                //var creatorId = $('#creatorId').val();

                $leoman.ajax("${contextPath}/admin/work/videoCreatorList", {
                    "workId": $('#id').val(),
                }, function (result) {
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
            getSelectLists: function () {
                var typeId = $('#typeId').val();

                $leoman.ajax("${contextPath}/admin/work/typeList", null, function (result) {
                    if (null != result) {
                        $('#typeList').empty();
                        // 获取返回的作品类型列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择类型</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                        $('#typeList').selectpicker('refresh');
                    } else {
                        $common.notify("获取类型信息失败", "error");
                    }

                    if (null != typeId && typeId != '') {
                        $('#typeList').val(typeId);
                        $('#typeList').selectpicker('refresh');
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
        video.fn.init();
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

