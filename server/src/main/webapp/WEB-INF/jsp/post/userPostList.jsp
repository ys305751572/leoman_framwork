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
                    <label>帖子关键字：</label>
                    <input type="text" class="input-sm form-control" id="description" name="description" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>发布者账号：</label>
                    <input type="text" class="input-sm form-control" id="mobile" name="mobile" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>帖子分类：</label>
                    <select id="typeList" name="typeList" style="width: 200px;" class="select"></select>
                </div>
                <div class="col-md-2 form-group">
                    <label>状态：</label>
                    <select id="status" name="status" class="select">
                        <option value="">全部</option>
                        <option value="0">正常</option>
                        <option value="1">锁帖</option>
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
                        <a data-toggle="modal" onclick="userPost.fn.add();"  title="发布帖子" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/userPost/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="userPost.fn.deleteBatch();" title="删除" class="tooltips">
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
                    <th>帖子内容</th>
                    <th>是否精华</th>
                    <th>是否置顶</th>
                    <th>点赞数</th>
                    <th>评论数</th>
                    <th>提交时间</th>
                    <th>发布者账号</th>
                    <th>发布者昵称</th>
                    <th>分类</th>
                    <th>状态</th>
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
                        <h4 class="modal-title" id="showText">删除该帖子后操作无法撤销，确定删除？</h4>
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
    userPost = {
        v: {
            list: [],
            dTable: null,
            userPostId: 0
        },
        fn: {
            init: function () {
                userPost.fn.dataTableInit();

                userPost.fn.getSelectList();

                $("#c_search").click(function () {
                    userPost.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                userPost.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/userPost/list",
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
                            "data": "content",
                            render: function (data) {
                                if (null != data && data != '') {
                                    return data.length > 30 ? (data.substring(0, 30) + '...') : data;
                                } else {
                                    return "";
                                }
                            }},
                        {
                            "data": "essence",
                            render: function(data) {
                                if (data == 0) {
                                    return "否";
                                }else {
                                    return "是";
                                }
                            }
                        },
                        {
                            "data": "stick",
                            render: function(data) {
                                if (data == 0) {
                                    return "否";
                                }else {
                                    return "是";
                                }
                            }
                        },
                        {"data": "praise"},
                        {"data": "comment"},
                        {
                            "data": "createDate",
                            render: function (data) {
                                if (data == null) {
                                    return "暂无";
                                }else {
                                    return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                                }
                            }
                        },
                        {"data": "userInfo.mobile"},
                        {"data": "userInfo.nickname"},
                        {"data": "category.name"},
                        {
                            "data": "status",
                            render: function (data) {
                                if (data == 0) {
                                    return "正常";
                                }else if (data == 1) {
                                    return "锁帖";
                                }
                            }},
                        {
                            "data": null,
                            "render": function (data) {
                                userPost.v.list.push(data);
                                var detail ="<button title='查看详情' class='btn btn-primary btn-circle check' onclick='userPost.fn.detail(" + data.id + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";

                                if (data.essence == 0 && data.status == 0) {
                                    detail += "<button title='精华' class='btn btn-primary btn-circle check' onclick='userPost.fn.essence(" + data.id + "," + data.essence +  ")'> " +
                                            "<i class='fa fa-heart'></i></button>&nbsp;&nbsp;";
                                }else if (data.essence == 1 && data.status == 0) {
                                    detail += "<button title='取消精华' class='btn btn-primary btn-circle check' onclick='userPost.fn.essence(" + data.id + "," + data.essence +  ")'> " +
                                            "<i class='fa fa-heart'></i></button>&nbsp;&nbsp;";
                                }

                                if (data.stick == 0 && data.status == 0) {
                                    detail += "<button title='置顶' class='btn btn-primary btn-circle check' onclick='userPost.fn.stick(" + data.id + "," + data.stick +  ")'> " +
                                            "<i class='fa fa-level-up'></i></button>&nbsp;&nbsp;";
                                }else if (data.stick == 1 && data.status == 0) {
                                    detail += "<button title='取消置顶' class='btn btn-primary btn-circle check' onclick='userPost.fn.stick(" + data.id + "," + data.stick +  ")'> " +
                                            "<i class='fa fa-level-up'></i></button>&nbsp;&nbsp;";
                                }

                                detail += "<button title='删除' class='btn btn-primary btn-circle check' onclick='userPost.fn.delete(" + data.id + "," + data.status +  ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";
                                return detail;
                            }

                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.content = $("#description").val();
                        aoData.mobile = $("#mobile").val();
                        aoData.category = $("#typeList").val();
                        aoData.status = $("#status").val();
                    }
                });
            },
            rowCallback: function (row, data) {
            },
            "add": function () {
                window.location.href = "${contextPath}/admin/userPost/add";
            },
            "detail": function (postId) {
                window.location.href = "${contextPath}/admin/userPost/detail?postId=" + postId;
            },
            "stick": function (id, stick) {

                if (stick == 0) {
                    $('#showText').html('确定置顶该帖子？');
                } else {
                    $('#showText').html('确定取消该贴的置顶？');
                }

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    var tempStatus = 0;
                    if (stick == 0) {
                        tempStatus = 1;
                    }

                    $leoman.ajax("${contextPath}/admin/userPost/isStick", {
                        "postId": id,
                        "stick": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            userPost.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "essence": function (id, essence) {

                if (essence == 0) {
                    $('#showText').html('确定将该帖子设为精华？');
                } else {
                    $('#showText').html('确定取消该贴的精华？');
                }

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    var tempStatus = 0;
                    if (essence == 0) {
                        tempStatus = 1;
                    }

                    $leoman.ajax("${contextPath}/admin/userPost/isEssence", {
                        "postId": id,
                        "essence": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            userPost.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "delete": function (id) {

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/userPost/delete", {
                        "postId": id,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            userPost.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            deleteBatch: function () {
                var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                var ids = checkBox.getInputId();
                $.ajax({
                    url : "${contextPath}/admin/userPost/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            userPost.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            getSelectList: function () {
                var workId = $('#id').val();

                $leoman.ajax("${contextPath}/admin/userPost/categoryList", null, function (result) {
                    if (null != result) {
                        $("#typeList").empty();
                        // 获取返回的作品类型列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择类型</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                        $("#typeList").selectpicker('refresh');
                    } else {
                        $common.notify("获取类型信息失败", "error");
                    }

                    if (null != workId && workId != '') {
                        $('#typeList').val(workId);
                    }
                });

            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        userPost.v.dTable.ajax.reload(null, false);
                    } else {
                        userPost.v.dTable.ajax.reload();
                    }
                    $leoman.notify(result.msg, "success");
                } else {
                    $leoman.notify(result.msg, "error");
                }
            }
        }
    }
    $(function () {
        userPost.fn.init();
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

