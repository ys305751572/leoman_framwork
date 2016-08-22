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
                <input type="hidden" id="creatorId" name="creatorId" value="${workCreator.userLogin.id}">
                <div class="col-md-2 form-group">
                    <label>漫画名称：</label>
                    <input type="text" value="${work.name}" class="input-sm form-control" name="name" placeholder="..." disabled>
                </div>
                <div class="col-md-2 form-group">
                    <label>总回数：</label>
                    <input type="text" value="${work.seriesCount}" class="input-sm form-control" name="name" placeholder="..." disabled>
                </div>
                <div class="col-md-2 form-group">
                    <label>已更新回数：</label>
                    <input type="text" value="${work.workComic.series}" class="input-sm form-control" name="name" placeholder="..." disabled>
                </div>
                <div class="col-md-2 form-group">
                    <label>漫画分类：</label>
                    <select id="typeList" style="width: 200px;" class="select" disabled></select>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-12 form-group" style="margin-top: 10px;">
                    <label>漫画简介：</label>
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
                    <th>章数</th>
                    <th>章数名称</th>
                    <th>更新时间</th>
                    <th>图片数</th>
                    <th>阅读数</th>
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
                        <h4 class="modal-title" id="showText" >确定删除该章节？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirm" class="btn btn-primary">确定
                        </button>
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
    comic = {
        v: {
            list: [],
            dTable: null,
            comicId: 0
        },
        fn: {
            init: function () {
                comic.fn.dataTableInit();

                //自动加载下拉框
                comic.fn.getSelectLists();

            },
            dataTableInit: function () {
                comic.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/comic/comicList",
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
                            "data": "updateDate",
                            render: function (data) {
                                if (null != data && data != '') {
                                    return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                                } else {
                                    return "暂无";
                                }
                            }
                        },
                        {"data": "imageList"},
                        {"data": "work.playNum"},
                        {
                            "data": null,
                            "render": function (data) {
                                comic.v.list.push(data);
                                var detail = "<button title='编辑' class='btn btn-primary btn-circle' onclick='comic.fn.edit(" + data.id + ")'>" +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";
                                detail += "<a title='预览' class='btn btn-primary btn-circle edit' onclick='comic.fn.list(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></a>&nbsp;&nbsp;";
                                detail += "<button title='删除' class='btn btn-primary btn-circle' onclick='comic.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>";

                                return detail;
                            }
                        }
                    ],
                });
            },
            rowCallback: function (row, data) {
            },
            "edit": function (comicId) {
                window.location.href = "${contextPath}/admin/comic/updateEdit?comicId=" + comicId ;
            },
            "list": function (comicId) {
                window.location.href = "${contextPath}/admin/comic/detail?comicId=" + comicId ;
            },
            getSelectLists: function () {
                var typeId = $('#typeId').val();

                $leoman.ajax("${contextPath}/admin/comic/typeList", null, function (result) {
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
            "delete": function (id) {

                $("#delete").modal("show");
                $('#confirm').click(function () {

                    $leoman.ajax("${contextPath}/admin/comic/delete", {
                        "comicId": id,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            comic.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
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
        comic.fn.init();
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

