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
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">新手指导信息</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${novice.id}">
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>内容</label>
                        <textarea id="content2" name="content" class="form-control auto-size m-b-10" placeholder="..." disabled>${feedback.content}</textarea>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15">
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <!-- Lightbox -->
                            <label>头像</label>
                            <p></p>
                            <p></p>
                            <c:forEach items="${feedback.list}" var="feedbackImage">
                                <a href="${feedbackImage.path}" data-rel="gallery"  class="pirobox_gall img-popup" title="Lovely evening in Noreway">
                                    <img src="${feedbackImage.path}" alt="">
                                </a>
                           </c:forEach>
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
        </form>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    $user = {
        v: {
            list: [],
            chart : null,
            dTable: null
        },
        fn: {
            init: function () {
                $user.fn.initImage();
            },
            save : function() {

            },
            initImage : function() {

//                var $exists = $("#imageFile").find(".fileupload-exists");
//                var $new = $("#imageFile").find(".fileupload-new");
//
//                $exists.each(function() {
//                    $(this).addClass("fileupload-new").removeClass("fileupload-exists");
//                });
//                $new.each(function() {
//                    $(this).addClass("fileupload-exists").removeClass("fileupload-new");
//                });
            },
            save : function () {
                var code =  $('.wysiwye-editor').code();
                $("#fromId").ajaxSubmit({
                    url : "${contextPath}/admin/novice/save",
                    type : "POST",
                    data : {
                        "detail" : code
                    },
                    success : function(result) {
                        if(!result.status) {
                            $common.fn.notify(result.msg);
                            return;
                        }
                        window.location.href = "${contextPath}/admin/novice/index";
                    }
                });
            }
        }
    }
    $(function () {
        $user.fn.init();
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

