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
        <h1 class="page-title">医生信息</h1>
        <form action="${contextPath}/admin/doctor/save" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="">
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>医师名称</label>
                        <input type="text" id="name" name="name" value="${doctor.name}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>

                    <div class="col-md-6 m-b-15">
                        <label>职业</label>
                        <input type="text" id="level" value="${doctor.level}" name="level" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>

                    <div class="col-md-6 m-b-15">
                        <label>科室</label>
                        <input type="text" id="depart" name="depart" value="${doctor.depart}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>

                    <div class="col-md-6 m-b-15" >
                        <label>所在医院</label>
                        <input type="text" id="hospital" name="hospital" value="${doctor.hospital}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>

                    <div class="col-md-6 m-b-15" >
                        <label>所获奖励</label>
                        <input type="text" id="reward" name="reward" value="${doctor.reward}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>

                    <div class="col-md-6 m-b-15" >
                        <label>擅长领域</label>
                        <input type="text" id="domain" name="domian" value="${doctor.domain}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>性别</label>
                        <select class="select" id="gender" name="gender" disabled>
                            <option <c:if test="${doctor.gender=='1'}">selected="selected" </c:if> value="1">男</option>
                            <option <c:if test="${doctor.gender=='2'}">selected="selected" </c:if> value="2">女</option>
                        </select>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15">
                        <!-- Lightbox -->
                        <label>头像</label>
                        <p></p>
                        <p></p>
                        <a href="${contextPath}/html/img/gallery/1.jpg" data-rel="gallery"  class="pirobox_gall img-popup" title="Lovely evening in Noreway">
                            <img src="${contextPath}/html/img/gallery/thumbs/1.jpg" alt="">
                        </a>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15">
                        <label>详细描述</label>
                        <div class="wysiwye-editor" id="detail" name="detail" disabled>${doctor.detail}</div>
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

