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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="clearfix"></div>
<section id="main" class="p-relative" role="main">
    <%@ include file="../inc/new/menu.jsp" %>
    <section id="content" class="container">
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">直播贴详情</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data">
            <div class="block-area">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <input type="hidden" name="postId" value="${postBase.id}">
                        <input type="hidden" id="categoryId" name="categoryId" value="${postBase.category.id}">
                        <label>封面：</label>
                        <img class="imgs" alt="" src="${postBase.avater}" style="height: 200px;width: 300px; z-index: 1;"/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>帖子名称：</label>
                        <input type="text" id="name" name="name" value="${postBase.name}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15" id="zb">
                        <label>开始时间：</label>
                        <input type="text" style="width: 180px;" id="startDate" name="startDate" disabled class="form-control input-append date form_datetime" placeholder="..." value="<date:date value="${postBase.startDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>"/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>分类：</label>
                        <select id="typeList" name="typeList" class="select" disabled></select>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>类型：</label>
                        <select id="type" name="type" class="select" disabled>
                            <option value="">全部</option>
                            <option value="0" <c:if test="${postBase.type == 0}">selected</c:if>>普通贴</option>
                            <option value="1" <c:if test="${postBase.type == 1}">selected</c:if>>直播贴</option>
                            <option value="2" <c:if test="${postBase.type == 2}">selected</c:if>>投票贴</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>直播详情：</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == zbPostInfoList || zbPostInfoList.size() == 0 }">
                                暂无直播
                            </c:if>
                            <c:if test="${null != zbPostInfoList && zbPostInfoList.size() > 0 }">
                                <c:forEach var="co" items="${zbPostInfoList}">
                                    <div id="ul" + ${co.id }>
                                        <label>内容：</label>
                                        <textarea  class="form-control" cols="40" rows="6">${co.content}</textarea>
                                        <label>图片：</label>
                                        <div>
                                            <c:if test="${null == co.imageList || co.imageList.size() == 0 }">
                                                暂无图片
                                            </c:if>
                                            <c:if test="${null != co.imageList && co.imageList.size() > 0 }">
                                                <c:forEach var="eo" items="${co.imageList}">
                                                    <img class="imgs" alt="" src="${eo.url}" style="height: 200px;width: 300px; z-index: 1;"/>
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </d>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="showText">删除该图片后操作无法撤销，确定删除？</h4>
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
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                post.fn.getSelectList();
                post.fn.loadTime();
            },
            loadTime: function () {
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1,
                    format: 'yyyy-mm-dd hh:ii:ss'
                });
            },
            delete: function (imageId) {

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    $leoman.ajax("${contextPath}/admin/post/deleteImage", {
                        "imageId": imageId,
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                            post.fn.init();
                            //post.v.dTable.ajax.reload();
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
            getSelectList: function () {
                var workId = $('#categoryId').val();

                //console.log(hdsjjhhj);
                $leoman.ajax("${contextPath}/admin/userPost/categoryList", null, function (result) {
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
            checkData: function () {
                var flag = true;
                var name = $('#name').val();

                if (null == name || name == '') {
                    $common.fn.notify("请输入帖子名字", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
                if (post.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/post/save",
                        type: "POST",
                        data: {
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/post/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            AddTempImg: function () {
                // a标签绑定onclick事件
                $('#tempImage').click();
            },
            saveTempImage: function () {
                $("#tempImageForm").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.url && data.url != '') {

                            $('#tempAddImageIds1').html($('#tempAddImageIds1').html() + data.id + ',');
                            post.fn.insertImage(data.image, data.id);

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
            },
        }
    }
    $(function () {
        post.fn.init();
    })
</script>
</body>
</html>

