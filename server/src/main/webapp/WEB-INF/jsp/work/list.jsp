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
                <div class="col-md-2 form-group">
                    <label>视频名称：</label>
                    <input type="text" class="input-sm form-control" id="name" name="name" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>类别：</label>
                    <select id="typeList" name="typeList" class="select"></select>
                </div>
                <div class="col-md-2 form-group">
                    <label>状态：</label>
                    <select id="status" name="status" class="select">
                        <option value="">全部</option>
                        <option value="0">未完结</option>
                        <option value="1">已完结</option>
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
                        <a data-toggle="modal" onclick="work.fn.add();" title="新增视频" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/work/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="work.fn.delete();" title="删除" class="tooltips">
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
                    <th>名字</th>
                    <th>总集数</th>
                    <th>已更新集数</th>
                    <th>类别</th>
                    <th>状态</th>
                    <th>更新时间</th>
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
                        <h4 class="modal-title">新增剧集</h4>
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
                                           data-rule="required" placeholder="请输入剧集名称"/>
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
                            <div class="form-group">
                                <div class="radio" id="wrap">
                                    <input type="radio" name="type" value="1" id="radio">设为完结
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirm" onclick="work.fn.confirm()" class="btn btn-primary">确定
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
                        <h4 class="modal-title">加载视频</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm1" method="post" enctype="application/x-www-form-urlencoded" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId1" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">视频id:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="num1" name="num1" maxlength="20" data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择类别:</label>
                                <div class="col-sm-5">
                                    <select id="type" style="width: 180px;" class="select"></select>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="work.fn.confirmSub()" class="btn btn-primary">确定
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
    work = {
        v: {
            list: [],
            dTable: null,
            workId: 0
        },
        fn: {
            init: function () {
                work.fn.dataTableInit();

                //自动加载下拉框
                work.fn.getSelectLists();

                $("#c_search").click(function () {
                    work.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                work.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/work/list",
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
                        {"data": "name"},
                        {"data": "seriesCount"},
                        {
                            "data": "updateCount",
                            render: function (data) {
                                if (data == null) {
                                    return 0;
                                } else {
                                    return data;
                                }
                            }},
                        {"data": "category.name",
                        },
                        {
                            "data": "isEnd",
                            render: function (data) {
                                if (data == 0) {
                                    return "未完结";
                                } else {
                                    return "已完结";
                                }
                            }
                        },
                        {
                            "data": "workVideo.createDate",
                            render: function (data) {
                                if (null != data && data != '') {
                                    return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                                } else {
                                    return '暂无';
                                }
                            }
                        },
                        {"data": "playNum"},
                        {"data": "barrageNum"},
                        {
                            "data": null,
                            "render": function (data) {
                                var detail = "<button title='编辑资料' class='btn btn-primary btn-circle edit' onclick='work.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                /*if (data.isEnd == 0) {
                                    detail += "<button title='更新下一集' class='btn btn-primary btn-circle' onclick='work.fn.update(" + data.id + "," + data.workVideo.series + ")'> " +
                                            "<i class='fa fa-bell'></i></button>&nbsp;&nbsp;";
                                }*/
                                detail += "<button title='更新剧集列表' class='btn btn-primary btn-circle' onclick='work.fn.videoList(" + data.id + ")'> " +
                                "<i class='fa fa-table'></i></button>";


                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.name = $("#name").val();
                        aoData.type = $("#typeList").val();
                        aoData.status = $("#status").val();
                    }
                });
            },
            rowCallback: function (row, data) {
                /* $('td', row).last().find(".check").click(function () {
                 var checkbox = $('td', row).first().find("input[type='checkbox']");
                 $user.fn.give(checkbox, [data.id]);
                 });*/
            },
            getSelectLists: function () {
                var workId = $('#id').val();

                $leoman.ajax("${contextPath}/admin/work/typeList", null, function (result) {
                    if (null != result) {
                        $('#typeList').empty();
                        $('#type').empty();
                        // 获取返回的作品类型列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择类型</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                        $('#typeList').selectpicker('refresh');
                        $('#type').append(content);
                        $('#type').selectpicker('refresh');
                    } else {
                        $common.notify("获取类型信息失败", "error");
                    }

                    if (null != workId && workId != '') {
                        $('#typeList').val(workId);
                    }
                });

            },
            "edit": function (workId) {
                window.location.href = "${contextPath}/admin/work/edit?workId=" + workId;
            },
            "add": function () {
                $("#add").modal("show");
                $('#num1').val('');
                //window.location.href = "${contextPath}/admin/work/add";
            },
            "videoList": function (workId) {
                window.location.href = "${contextPath}/admin/work/indexVideo?workId=" + workId;
            },
            "update": function (id, series) {
                if (id != null) {
                    $('#num').val(series + 1);
                    $("#infoModal").modal("show");
                    $('#name2').val('');
                    $('#video').val('');
                    work.v.workId = id;
                }
            },
            "confirm": function () {
                $leoman.ajax("${contextPath}/admin/work/update", {
                    "workId": work.v.workId,
                    num: $('#num').val(),
                    videoName: $('#name2').val(),
                    video: $('#video').val(),
                    status: $('#wrap input[name="type"]:checked ').val()
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        work.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });

            },
            checkData: function () {
                var flag = true;
                var souhuId = $('#num1').val();
                var type = $('#type').val();

                if (null == souhuId || souhuId == '') {
                    $common.fn.notify("请输入视频id", "error");
                    flag = false;
                    return;
                }

                if (null == type || type == '') {
                    $common.fn.notify("请输入视频类别", "error");
                    flag = false;
                    return;
                }
            },
            "confirmSub": function () {
                if (work.fn.checkData()) {
                    $leoman.ajax("${contextPath}/admin/work/add", {
                        id: $('#num1').val(),
                        categoryId : $('#type option:selected').val()
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#add").modal("hide");
                            work.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                }

                $leoman.ajax("${contextPath}/admin/work/add", {
                    id: $('#num1').val(),
                    categoryId : $('#type option:selected').val()
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        $("#add").modal("hide");
                        work.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });
            },
            delete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/work/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            work.v.dTable.ajax.reload();
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
        work.fn.init();
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

