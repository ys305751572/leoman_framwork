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
                    <label>小说名称：</label>
                    <input type="text" class="input-sm form-control" id="name" name="name" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>类别：</label>
                    <select id="typeList" name="typeList" style="width: 200px;" class="select"></select>
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
        <hr class="whiter m-t-20"/>

        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" onclick="novel.fn.add();" title="新增小说" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/novel/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="novel.fn.delete();" title="删除" class="tooltips">
                            <i class="sa-list-delete"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <hr class="whiter m-t-20"/>

        <%--<div class="block-area">
            <button type="button" onclick="novel.fn.add();" class="btn btn-alt m-r-5">新增小说</button>
        </div>
        <hr class="whiter m-t-20"/>--%>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>封面</th>
                    <th>名字</th>
                    <th>总章数</th>
                    <th>已更新章数</th>
                    <th>类别</th>
                    <th>状态</th>
                    <th>更新时间</th>
                    <th>阅读数</th>
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
    novel = {
        v: {
            list: [],
            dTable: null,
            novelId: 0
        },
        fn: {
            init: function () {
                novel.fn.dataTableInit();

                //自动加载下拉框
                novel.fn.getSelectLists();

                $("#c_search").click(function () {
                    novel.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                novel.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/novel/list",
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
                            "data": "workNovel.series",
                            render: function (data) {
                                if (data == null) {
                                    return "暂无";
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
                            "data": "workNovel.updateDate",
                            render: function (data) {
                                if (null != data && data != '') {
                                    return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                                } else {
                                    return '暂无';
                                }
                            }
                        },
                        {"data": "playNum"},
                        {
                            "data": null,
                            "render": function (data) {
                                var detail = "<button title='编辑资料' class='btn btn-primary btn-circle edit' onclick='novel.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                if (data.isEnd == 0) {
                                    detail += "<button title='更新下一章' class='btn btn-primary btn-circle' onclick='novel.fn.update(" + data.id + "," + data.workNovel.series + ")'> " +
                                            "<i class='fa fa-bell'></i></button>&nbsp;&nbsp;";
                                }
                                detail += "<button title='章节列表' class='btn btn-primary btn-circle' onclick='novel.fn.novelList(" + data.id + ")'> " +
                                "<i class='fa fa-table'></i></button>";

                                return detail;
                            }
                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.name = $("#name").val();
                        aoData.type = $("#type").val();
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

                $leoman.ajax("${contextPath}/admin/novel/typeList", null, function (result) {
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

                    if (null != workId && workId != '') {
                        $('#typeList').val(workId);
                        $('#typeList').selectpicker('refresh');
                    }
                });

            },
            "edit": function (workId) {
                window.location.href = "${contextPath}/admin/novel/edit?workId=" + workId;
            },
            "add": function () {
                window.location.href = "${contextPath}/admin/novel/add";
            },
            "novelList": function (workId) {
                window.location.href = "${contextPath}/admin/novel/indexNovel?workId=" + workId;
            },
            "update": function (workId, series) {
                window.location.href = "${contextPath}/admin/novel/update?workId=" + workId + "&series=" + (series == null ? 0 : series);
            },
            delete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/novel/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            novel.v.dTable.ajax.reload();
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
        novel.fn.init();
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

