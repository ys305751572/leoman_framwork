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

    <link rel="stylesheet" href="${contextPath}/html/audio/css/not.the.skin.css">
    <link rel="stylesheet" href="${contextPath}/html/audio/circle.skin/circle.player.css">

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
        <h1 class="page-title">帖子详情</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="gameId" name="gameId" value="${gameResource.id}">
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>用户账号：</label>
                        <input type="text" id="mobile" name="mibile" value="${postBase.userInfo.mobile}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>昵称：</label>
                        <input type="text" id="nickname" name="nickname" value="${postBase.userInfo.nickname}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <%--<div class="col-md-6 m-b-15">
                        <label>转发数：</label>
                        <input type="text" id="size" name="size" value="${gameResource.size}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>--%>
                    <div class="col-md-6 m-b-15">
                        <label>点赞数：</label>
                        <input type="text" id="praise" name="praise" value="${postBase.praise}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>评论数：</label>
                        <input type="text" id="comment" name="comment" value="${postBase.comment}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>发帖时间：</label>
                        <input type="text" id="createDate" name="createDate" value="<date:date value="${postBase.createDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>帖子内容：</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control" placeholder="..." disabled>${postBase.content}</textarea>
                    </div>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>帖子图片:</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == postImageList || postImageList.size() == 0 }">
                                暂无图片
                            </c:if>
                            <div>&nbsp;&nbsp;</div>
                            <c:if test="${null != postImageList && postImageList.size() > 0 }">
                                <c:forEach var="co" items="${postImageList}">
                                    <img class="imgs" alt="" src="${co.url}" style="height: 200px;width: 300px; z-index: 1;"/>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>音频：</label>
                        <!-- The jPlayer div must not be hidden. Keep it at the root of the body element to avoid any such problems. -->
                        <div id="jquery_jplayer_1" class="cp-jplayer"></div>
                        <div id="cp_container_1" >
                            <div class="cp-circle-control"></div>
                            <ul class="cp-controls">
                                <li><a href="#" class="cp-play" tabindex="1">play</a></li>
                                <li><a href="#" class="cp-pause" style="display:none;" tabindex="1">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->
                            </ul>
                        </div>
                        <!-- This is the 2nd instance HTML -->
                    </div>
                </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
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
<script type="text/javascript" src="${contextPath}/html/audio/js/jquery.transform.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/jquery.grab.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/jquery.jplayer.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/mod.csstransforms.min.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/circle.player.js"></script>
<script>
    userPost = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                var myCirclePlayer = new CirclePlayer("#jquery_jplayer_1",
                        {
                            m4a: "${contextPath}/html/audio/1.mp3"
                            //m4a: "${contextPath}/admin/creator/${userInfo.creator.audioUrl}"
                        }, {
                            cssSelectorAncestor: "#cp_container_1"
                        });

                /*// 加载图片数组
                userPost.fn.getSerImages();*/
            },
            /*getSerImages: function () {
                var imgList = ${postImageList};
                console.log(1);
                $.each(imgList, function (i, item) {
                    if (null != item) {

                        userPost.fn.insertImage(item.path, item.id);
                    } else {
                        $('#still').html('暂无');
                    }
                });

            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#still");
            },*/
        }
    }
    $(function () {
        userPost.fn.init();
    })
</script>
</body>
</html>

