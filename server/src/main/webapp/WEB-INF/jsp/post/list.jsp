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
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <span id="tempAddImageIds1" style="display: none"></span>
            <span id="tempDelImageIds" style="display: none"></span>
        </form>
        <!-- 查询条件 -->
        <div class="block-area" id="search">
            <div class="row">
                <div class="col-md-2 form-group">
                    <label>帖子名字：</label>
                    <input type="text" class="input-sm form-control" id="name" name="name" placeholder="...">
                </div>
                <div class="col-md-2 form-group">
                    <label>帖子类型：</label>
                    <select id="type" name="type" class="select">
                        <option value="">全部</option>
                        <option value="0">普通贴</option>
                        <option value="1">直播贴</option>
                        <option value="2">投票贴</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>状态：</label>
                    <select id="status" name="status" class="select">
                        <option value="">全部</option>
                        <option value="0">未开始</option>
                        <option value="1">正在进行</option>
                        <option value="2">已结束</option>
                        <option value="3">——</option>
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
                        <a data-toggle="modal" onclick="post.fn.add();"  title="发布帖子" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/post/index" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                    <li class="show-on" style="display: none;">
                        <a onclick="post.fn.deleteBatch();" title="删除" class="tooltips">
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
                    <th>帖子名字</th>
                    <th>封面</th>
                    <th>是否精华</th>
                    <th>是否置顶</th>
                    <th>点赞数</th>
                    <th>评论数</th>
                    <th>提交时间</th>
                    <th>帖子类型</th>
                    <th>状态</th>
                    <th>分类</th>
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

        <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">添加直播</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="multipart/form-data" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">直播内容:</label>
                                <div class="col-sm-8">
                                    <textarea cols="40" rows="6" id="description" name="description" class="form-control" placeholder="..."></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">图片:</label>
                                <div class="col-sm-6">
                                    <div style="float: left;margin-bottom: 30px;" id="mainImage">
                                        <a href="javascript:void(0);" onclick="post.fn.AddTempImg()">
                                            <img src="${contextPath}/static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="radio" id="wrap">
                                        <input type="radio" name="type" value="2" id="radio">设为完结
                                    </div>
                                </div>
                            </div>

                            <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                                <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                                <input name="imageIdTemp" type="hidden"/>
                                <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="post.fn.deleteImage(this)">
                                    <img id="pic" src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                </a>
                            </div>

                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" id="confirmSub" onclick="post.fn.confirm()" class="btn btn-primary">确定
                            </button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>

        <form id="tempImageForm" method="post" action="${contextPath}/admin/post/addTempImage" enctype="multipart/form-data">
            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="post.fn.saveTempImage()"/>
            <input type="hidden" id="tempImageType" name="type"/>
        </form>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    post = {
        v: {
            list: [],
            dTable: null,
            postId: 0
        },
        fn: {
            init: function () {
                post.fn.dataTableInit();

                $("#c_search").click(function () {
                    post.v.dTable.ajax.reload();
                });
            },
            dataTableInit: function () {
                post.v.dTable = $leoman.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ajax": {
                        "url": "${contextPath}/admin/post/list",
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
                        {
                            "data": "avater",
                            render: function (data) {
                                if (null == data || data == '') {
                                    return "暂无";
                                } else {
                                    return '<img src="' + data + '" width="50px" height="50px" />';
                                }
                            }
                        },
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
                        {
                            "data": "type",
                            render: function (data) {
                                if (data == 0) {
                                    return "普通贴";
                                }else if (data == 1) {
                                    return "直播贴";
                                }else if (data == 2) {
                                    return "投票贴";
                                }
                            }
                        },
                        {
                            "data": "status",
                            render: function (data) {
                                if (data == 0) {
                                    return "未开始";
                                }else if (data == 1) {
                                    return "正在进行";
                                }else if (data == 2) {
                                    return "已结束";
                                }else {
                                    return "——";
                                }
                            }
                        },
                        {"data": "category.name"},

                        {
                            "data": null,
                            "render": function (data) {
                                post.v.list.push(data);
                                var detail ="<button title='查看详情' class='btn btn-primary btn-circle check' onclick='post.fn.detail(" + data.id + "," + data.type + ")'> " +
                                        "<i class='fa fa-eye'></i></button>&nbsp;&nbsp;";

                                if (data.essence == 0) {
                                    detail += "<button title='精华' class='btn btn-primary btn-circle check' onclick='post.fn.essence(" + data.id + "," + data.essence +  ")'> " +
                                            "<i class='fa fa-heart'></i></button>&nbsp;&nbsp;";
                                }else if (data.essence == 1) {
                                    detail += "<button title='取消精华' class='btn btn-primary btn-circle check' onclick='post.fn.essence(" + data.id + "," + data.essence +  ")'> " +
                                            "<i class='fa fa-heart'></i></button>&nbsp;&nbsp;";
                                }

                                if (data.stick == 0) {
                                    detail += "<button title='置顶' class='btn btn-primary btn-circle check' onclick='post.fn.stick(" + data.id + "," + data.stick +  ")'> " +
                                            "<i class='fa fa-level-up'></i></button>&nbsp;&nbsp;";
                                }else if (data.stick == 1) {
                                    detail += "<button title='取消置顶' class='btn btn-primary btn-circle check' onclick='post.fn.stick(" + data.id + "," + data.stick +  ")'> " +
                                            "<i class='fa fa-level-up'></i></button>&nbsp;&nbsp;";
                                }

                                if (data.type == 1 && data.status == 1) {
                                    detail += "<button title='添加直播' class='btn btn-primary btn-circle check' onclick='post.fn.addZB(" + data.id + ")'> " +
                                            "<i class='fa fa-bell'></i></button>&nbsp;&nbsp;";
                                }

                                detail += "<button title='编辑' class='btn btn-primary btn-circle check' onclick='post.fn.edit(" + data.id + ")'> " +
                                        "<i class='fa fa-edit'></i></button>&nbsp;&nbsp;";

                                detail += "<button title='删除' class='btn btn-primary btn-circle check' onclick='post.fn.delete(" + data.id + "," + data.status +  ")'> " +
                                        "<i class='fa fa-times'></i></button>&nbsp;&nbsp;";

                                return detail;
                            }

                        }
                    ],
                    "fnServerParams": function (aoData) {
                        aoData.name = $("#name").val();
                        aoData.category = $("#type").val();
                        aoData.status = $("#status").val();
                    }
                });
            },
            rowCallback: function (row, data) {
            },
            "add": function () {
                window.location.href = "${contextPath}/admin/post/add";
            },
            "addZB": function (id) {
                if (id != null) {
                    $('#description').val('');
                    $('#mainImage').val('');
                    $("#infoModal").modal("show");
                    post.v.postId = id;
                }
            },
            "edit": function (postId) {
                window.location.href = "${contextPath}/admin/post/edit?postId=" + postId;
            },
            "detail": function (postId) {
                window.location.href = "${contextPath}/admin/post/detail?postId=" + postId;
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

                    $leoman.ajax("${contextPath}/admin/post/isStick", {
                        "postId": id,
                        "stick": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            post.v.dTable.ajax.reload();
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

                    $leoman.ajax("${contextPath}/admin/post/isEssence", {
                        "postId": id,
                        "essence": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            post.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            "delete": function (id) {

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/post/delete", {
                        "postId": id,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            post.v.dTable.ajax.reload();
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
                    url : "${contextPath}/admin/post/deleteBatch",
                    data : {
                        "ids" : JSON.stringify(ids)
                    },
                    type : "post",
                    dataType : "json",
                    success : function(result) {
                        if(result == 1) {
                            $common.fn.notify("操作成功", "success");
                            post.v.dTable.ajax.reload();
                            return;
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            AddTempImg: function () {
                // a标签绑定onclick事件
                $('#tempImage').click();
                $('#tempImageType').val();
            },
            saveTempImage: function () {
                $("#tempImageForm").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.url && data.url != '') {

                            $('#tempAddImageIds1').html($('#tempAddImageIds1').html() + data.id + ',');
                            post.fn.insertImage(data.url, data.id);

                        } else {
                            $common.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#mainImage");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    post.fn.mouseover($(this));
                }, function () {
                    post.fn.mouseOut($(this));
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            deleteImage: function (self) {
                //creator.v.imageSize = creator.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            "confirm": function () {
                $leoman.ajax("${contextPath}/admin/post/addZB", {
                    "postId": post.v.postId,
                    content: $('#description').val(),
                    status: $('#wrap input[name="type"]:checked ').val(),
                    "tempAddImageIds1": $("#tempAddImageIds1").html(),
                    "tempDelImageIds": $("#tempDelImageIds").html()
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        $("#infoModal").modal("hide");
                        post.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
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
        post.fn.init();
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

