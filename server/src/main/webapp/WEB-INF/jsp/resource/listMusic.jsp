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
                        <a data-toggle="modal" onclick="music.fn.add();"  title="上传音乐" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/music/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="music.fn.batchDelete();" title="删除" class="tooltips">
                            <i class="sa-list-delete"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <%--<div class="block-area" >
            <button type="button" onclick="category.fn.add();"  class="btn btn-alt m-r-5">新增分类</button>
        </div>--%>

        <hr class="whiter m-t-20"/>
        <!-- form表格 -->
        <div class="block-area" id="tableHover">
            <table class="table table-bordered table-hover tile" id="dataTables" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><input type="checkbox" class="pull-left list-parent-check"/></th>
                    <th>音乐名称</th>
                    <th>类别</th>
                    <th>试听数量</th>
                    <th>上传时间</th>
                    <th>演唱者</th>
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
                        <h4 class="modal-title" id="showText">确定删除该音乐？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirm" class="btn btn-primary">确定</button>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">上传音乐</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">音乐分类:</label>
                                <div class="col-sm-5">
                                    <select id="categoryList" style="width: 150px;" class="select"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">音乐名称:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="name" name="name" maxlength="20"
                                           data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">演唱者:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="singer" name="singer" maxlength="20"
                                           data-rule="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">音乐：</label>
                                <div class="fileupload fileupload-new" data-provides="fileupload">
                                    <div class="fileupload-preview thumbnail form-control"></div>
                                    <div class="col-sm-6 control-label">
                                        <span class="btn btn-file btn-alt btn-sm">
                                            <span class="fileupload-new">选择音乐</span>
                                            <span class="fileupload-exists">更改</span>
                                            <input id="imageFile" name="imageFile" type="file"/>
                                        </span>
                                        <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="music.fn.confirmSub()" class="btn btn-primary">确定
                            </button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <object name="Player" id="Player" width=300 height=200 classid="CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6" style="display: none">
            <param name="URL" value=""/>
            <param name="autoStart" value="0"/>
            <param name="balance" value="0"/>
            <param name="baseURL" value/>
            <param name="captioningID" value/>
            <param name="currentPosition" value="0"/>
            <param name="currentMarker" value="0"/>
            <param name="defaultFrame" value="0"/>
        </object>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    music = {
        v: {
            list: [],
            dTable: null,
            musicId: 0
        },
        fn: {
            init: function () {
                music.fn.dataTableInit();

                //自动加载下拉框
                music.fn.getSelectList();
            },
            dataTableInit: function () {
                music.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/music/list",
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
                        {"data": "name"},
                        {"data": "category.name"},
                        {"data": "num"},
                        {
                            "data": "createDate",
                            render: function (data) {
                                return new Date(data).format("yyyy-MM-dd hh:mm:ss")
                            }
                        },
                        {"data": "singer"},
                        {
                            "data": null,
                            "render": function (data) {
                                music.v.list.push(data);

                                var detail = "<button title='删除' class='btn btn-primary btn-circle check' onclick='music.fn.delete(" + data.id + ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                /*detail +="<button title='试听' class='btn btn-primary btn-circle check' onclick='music.fn.listen(" + data.id +   ")'> " +
                                        "<i class='fa fa-music'></i></button>&nbsp;&nbsp;";*/

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
            "delete": function (id) {

                if (id != null) {
                    $.each(music.v.list, function (i, item) {
                        if (item.id == id ) {
                            $("#delete").modal("show");
                            music.v.musicId = item.id;
                        }
                    });
                }

                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/music/delete", {
                        "musicId": music.v.musicId
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            music.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            batchDelete: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/music/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            music.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            "add": function () {
                $("#image").prop("src", "");
                $('#categoryList').val("");
                $('#name').val("");
                $('#singer').val("");
                $("#infoModal").modal("show");
            },
            "listen": function (musicId) {
                /*document.Player.URL=url;
                document.Player.controls.play(); //让播放器开始播放*/
                /*document.write(' <OBJECT id="Player"');
                document.write(' classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6"');
                document.write(' width=0 height=0 > <param name="URL" value=url /> <param name="AutoStart" value="false" /> </OBJECT>');*/
                //player.controls.play();
                $leoman.ajax("${contextPath}/admin/music/listen", {
                    "musicId": musicId
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        music.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });
            },
            "confirmSub": function () {

                $("#infoForm").ajaxSubmit({
                    url : "${contextPath}/admin/music/save",
                    data : {
                        "categoryId": $('#categoryList option:selected').val(),
                    },
                    success : function() {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        music.v.dTable.ajax.reload();
                    },
                    error : function() {
                        console.log("error");
                    }
                });

                /*$leoman.ajax("${contextPath}/admin/still/save", {
                    "categoryId": $('#categoryList option:selected').val(),
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        still.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });*/

            },
            getSelectList: function () {
                //var creatorId = $('#creatorId').val();

                $leoman.ajax("${contextPath}/admin/music/categoryList", null, function (result) {
                    if (null != result) {
                        $('#categoryList').empty();
                        // 获取返回的主创列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择分类</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#categoryList').append(content);
                        $('#categoryList').selectpicker('refresh');
                    } else {
                        $common.notify("获取主创信息失败", "error");
                    }

                    /*if (null != creatorId && creatorId != '') {
                        $('#creatorList').val(creatorId);
                    }*/
                });

            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        music.v.dTable.ajax.reload(null, false);
                    } else {
                        music.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        music.fn.init();
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

