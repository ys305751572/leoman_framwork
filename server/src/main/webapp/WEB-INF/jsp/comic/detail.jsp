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
        <h1 class="page-title">预览</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <div class="row">

                    <div class="col-md-6 m-b-15">
                        <label>漫画名称：</label>
                        <input type="text" id="name" name="name" value="${workComic.work.name}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>章节：</label>
                        <input type="text" id="seriesCount" name="seriesCount" value="${workComic.series}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>章节名字：</label>
                        <input type="text" id="comic" name="comic" value="${workComic.name}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>图片数：</label>
                        <input type="text" id="" name="seriesCount" value="${workComic.imageList}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>最后更新时间：</label>
                        <input type="text" id="updateDate" name="updateDate" class="input-sm form-control validate[required]" placeholder="..." disabled value="<date:date value="${workComic.updateDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>" />

                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>上传时间：</label>
                        <input type="text" id="createDate" name="createDate" class="input-sm form-control validate[required]" placeholder="..." disabled value="<date:date value="${workComic.createDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>" />

                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>章节正文：</label>
                        <div>&nbsp;&nbsp;</div>
                        <c:if test="${null == workComicImageList || workComicImageList.size() == 0 }">
                            暂无
                        </c:if>
                        <c:if test="${null != workComicImageList && workComicImageList.size() > 0 }">
                            <c:forEach var="co" items="${workComicImageList}">
                                <img class="imgs" alt="" src="${co.url}" style="height: 200px;width: 300px; z-index: 1;"/>
                            </c:forEach>
                        </c:if>
                    </div>
                    <%--<div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>章节正文：</label>
                        <div id="image" value="" name="image"/>
                    </div>
                    <div id="tempDiv" style="display:none;float: left; height: 200px;width: 300px;margin-right:6px; z-index: 0;margin-bottom: 50px;">
                        <img class="imgs" alt="" src="" style="height: 200px;width: 300px; z-index: 1;"/>
                        <input name="imageIdTemp" type="hidden"/>
                    </div>--%>
                    <hr class="whiter m-t-20"/>

                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

        <%--<form id="tempImageForm" method="post" action="${contextPath}/admin/creator/addTempImage" enctype="multipart/form-data">
            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="creator.fn.saveTempImage()"/>
            <input type="hidden" id="tempImageType" name="type"/>
        </form>--%>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    $comic = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                // 加载图片数组
                $comic.fn.getSerImages();
            },
            /*getSerImages: function () {
                var imgList = ${creatorImageList };

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        $comic.fn.insertImage(item.path, item.id);
                    } else {
                        $('#image').html('暂无');
                    }
                });

            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#image");
            },*/
        }
    }
    $(function () {
        $comic.fn.init();
    })
</script>
</body>
</html>

