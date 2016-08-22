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
        <h1 class="page-title">发布帖子</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <span id="tempAddImageIds1" style="display: none"></span>
                <span id="tempDelImageIds" style="display: none"></span>
                <%--<input type="hidden" id="workId" name="workId" value="${work.id}">
                <input type="hidden" id="typeId"  value="${work.category.id}">
                <input type="hidden" id="creatorId"  value="${workCreator.userLogin.id}">--%>
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>选择主创人员：</label>
                        <div>&nbsp;&nbsp;</div>
                        <select  id="creatorList" style="width: 150px;" class="select"></select>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>帖子分类：</label>
                        <div>&nbsp;&nbsp;</div>
                        <select id="typeList" style="width: 200px;" class="select"></select>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>帖子详情：</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control" placeholder="..."></textarea>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-sm-12 control-label" style="margin-top: 10px">
                        <label>上传图片:</label>
                        <div class="col-sm-12">
                            <div style="float: left;margin-bottom: 30px;" id="mainImage">
                                <a href="javascript:void(0);" onclick="userPost.fn.AddTempImg()">
                                    <img src="${contextPath}/static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                        <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                        <input name="imageIdTemp" type="hidden"/>
                        <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="userPost.fn.deleteImage(this)">
                            <img id="pic" src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                        </a>
                    </div>
                    <hr class="whiter m-t-20"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="userPost.fn.save();" class="btn btn-info btn-sm m-t-10">提交
                        </button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

        <form id="tempImageForm" method="post" action="${contextPath}/admin/userPost/addTempImage" enctype="multipart/form-data">
            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="userPost.fn.saveTempImage()"/>
            <input type="hidden" id="tempImageType" name="type"/>
        </form>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    userPost = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                //自动加载下拉框
                userPost.fn.getSelectList();

                //自动加载下拉框
                userPost.fn.getSelectLists();

            },
            checkData: function () {
                var flag = true;
                var creator = $('#creatorList').val();
                var category = $('#creatorList').val();
                var description = $('#description').val();

                if (null == creator || creator == '') {
                    $common.fn.notify("请选择主创人员", "error");
                    flag = false;
                    return;
                }

                if (null == category || category == '') {
                    $common.fn.notify("请选择帖子分类", "error");
                    flag = false;
                    return;
                }

                if (null == description || description == '') {
                    $common.fn.notify("请输入帖子详情", "error");
                    flag = false;
                    return;
                }

                if (description.length > 1000) {
                    $common.fn.notify("帖子详情字数最多200字", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {

                if (userPost.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/userPost/save",
                        type: "POST",
                        data: {
                            "creatorId": $('#creatorList option:selected').val(),
                            "category": $('#typeList option:selected').val(),
                            "tempAddImageIds1": $("#tempAddImageIds1").html(),
                            "tempDelImageIds": $("#tempDelImageIds").html()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/userPost/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            getSelectList: function () {
                //var creatorId = $('#creatorId').val();

                $leoman.ajax("${contextPath}/admin/userPost/creatorList", null, function (result) {
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
                    }*/
                });

            },
            getSelectLists: function () {
                //var typeId = $('#typeId').val();

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

                   /* if (null != typeId && typeId != '') {
                        $('#typeList').val(typeId);
                    }*/
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
                            userPost.fn.insertImage(data.url, data.id);
                            console.log(data);
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
                    userPost.fn.mouseover($(this));
                }, function () {
                    userPost.fn.mouseOut($(this));
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
        }
    }
    $(function () {
        userPost.fn.init();
    })
</script>
</body>
</html>

