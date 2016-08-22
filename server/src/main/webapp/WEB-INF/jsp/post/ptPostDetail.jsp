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
        <h1 class="page-title">普通贴详情</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data">
            <div class="block-area">
                <div class="row">
                    <input type="hidden" name="postId" value="${postBase.id}">
                    <input type="hidden" id="categoryId" name="categoryId" value="${postBase.category.id}">
                    <div class="col-md-12 m-b-15">
                        <label>封面：</label>
                        <img class="imgs" alt="" src="${postBase.avater}" style="height: 200px;width: 300px; z-index: 1;"/>
                    </div>
                    <div class="col-md-4 m-b-15">
                        <label>帖子名称：</label>
                        <input type="text" id="name" name="name" value="${postBase.name}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-4 m-b-15">
                        <label>分类：</label>
                        <select id="typeList" name="typeList" class="select" disabled></select>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-4 m-b-15" style="margin-top: 10px">
                        <label>类型：</label>
                        <select id="type" name="type" class="select" disabled>
                            <option value="">全部</option>
                            <option value="0" <c:if test="${postBase.type == 0}">selected</c:if>>普通贴</option>
                            <option value="1" <c:if test="${postBase.type == 1}">selected</c:if>>直播贴</option>
                            <option value="2" <c:if test="${postBase.type == 2}">selected</c:if>>投票贴</option>
                        </select>
                    </div>

                    <div class="col-md-12 m-b-15" style="margin-top: 10px;" id="pt">
                        <label>帖子详情：</label>
                        <div class="wysiwye-editor">${postBase.content}</div>
                    </div>

                    <hr class="whiter m-t-20"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="post.fn.save();" class="btn btn-info btn-sm m-t-10">提交
                        </button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
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
            getSelectList: function () {
                var workId = $('#categoryId').val();

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
        }
    }
    $(function () {
        post.fn.init();
    })
</script>
</body>
</html>

