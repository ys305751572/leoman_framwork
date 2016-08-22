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
        <h1 class="page-title">查看详情</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${adsIndex.id}">
                <input type="hidden" id="address" name="address" value="${adsIndex.position}">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>推荐封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${adsIndex.cover}">
                            </div>
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="imageFile" name="imageFile" type="file"/>
                                </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>大标题：</label>
                        <input type="text" id="title" name="title" value="${adsIndex.title}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>小标题：</label>
                        <input type="text" id="subTitle" name="subTitle" value="${adsIndex.subTitle}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-12 m-b-15" >
                        <label>选择链接位置：</label>
                        <input type="text" id="position" name="position"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
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
    adsIndex = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                var position = $('#address').val();
                if (position == 0) {
                    position = "小说";
                    $('#position').val(position+ " > " + "${category.name} > ${postBase.name}")
                }
                else if (position == 1) {
                    position = "视频";
                    $('#position').val(position+ " > " + "${work.name} > ${workVideo.name}")
                }else if (position == 2) {
                    position = "小说";
                    $('#position').val(position+ " > " + "${work.name} > ${workNovel.name}")
                }else if (position == 3) {
                    position = "漫画";
                    $('#position').val(position+ " > " + "${work.name} > ${workComic.name}")
                }else if (position == 4) {
                    position = "资源";
                    $('#position').val(position+ " > " + "${type} > ${workComic.name}")
                }else if (position == 5) {
                    position = "福利社";
                    $('#position').val(position+ " > " + "${type} > ${welfare.title}")
                }
            },
        }
    }
    $(function () {
        adsIndex.fn.init();
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

