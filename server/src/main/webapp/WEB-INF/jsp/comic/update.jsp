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
        <h1 class="page-title">更新下一章</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="workId" name="workId" value="${work.id}">
                <input type="hidden" id="workComicId" name="workComicId" value="${workComic.id}">
                <input type="hidden" id="series" name="series" value="${series}">
                <span id="tempAddImageIds1" style="display: none"></span>
                <span id="tempDelImageIds" style="display: none"></span>
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>漫画名称：</label>
                        <input type="text" id="name" name="name" value="${work.name}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>章节：</label>
                        <input type="text" id="seriesCount" name="seriesCount" value="${series}"
                               class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>章节名字：</label>
                        <input type="text" id="comic" name="comic" value="${workComic.name}"
                               class="input-sm form-control validate[required]" placeholder="..." >
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>添加正文：</label>
                        <div>&nbsp;&nbsp;</div>
                        <div class="col-sm-10">
                            <div style="float: left;margin-bottom: 30px;" id="mainImage">
                                <a href="javascript:void(0);" onclick="$comic.fn.AddTempImg()">
                                    <img src="${contextPath}/static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <div class="radio" id="wrap" <c:if test="${work.isEnd == 1}">checked</c:if>>
                            <input type="radio" name="type" value="1" id="radio" <c:if test="${work.isEnd == 1}">checked</c:if>>设为完结
                        </div>
                    </div>

                    <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                        <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                        <input name="imageIdTemp" type="hidden"/>
                        <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="$comic.fn.deleteImage(this)">
                            <img id="pic" src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                        </a>
                    </div>
                    <hr class="whiter m-t-20"/>

                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="$comic.fn.save();">提交</button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

        <form id="tempImageForm" method="post" action="${contextPath}/admin/comic/addTempImage" enctype="multipart/form-data">
            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="$comic.fn.saveTempImage()"/>
        </form>
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

                $comic.fn.getSerImages();

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainImage",
                    Width: 200,
                    Height: 170
                });

            },
            getSerImages: function () {
                if (${workComicImageList} != null && ${workComicImageList} != "") {
                    var imgList = ${workComicImageList};
                    $.each(imgList, function (i, item) {
                        if (null != item) {
                            $comic.fn.insertImage(item.path, item.id);

                        } else {
                            $('#mainImage').html('暂无');
                        }
                    });
                }
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
                    $comic.fn.mouseover($(this));
                }, function () {
                    $comic.fn.mouseOut($(this));
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
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
                            $comic.fn.insertImage(data.url, data.id);
                        } else {
                            $leoman.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            deleteImage: function (self) {
                //$comic.v.imageSize = creator.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            checkData: function () {
                var flag = true;
                var comic = $('#comic').val();
                //var tempAddImageIds1 =  $("#tempAddImageIds1").html();

                if (null == comic || comic == '') {
                    $common.fn.notify("请输入章节名字", "error");
                    flag = false;
                    return;
                }

               /* if (null == tempAddImageIds1 || tempAddImageIds1 == '') {
                    $common.fn.notify("请输入章节正文", "error");
                    flag = false;
                    return;
                }*/

                return flag;
            },
            save: function () {
                if ($comic.fn.checkData()) {
                    var code = $('.wysiwye-editor').code();
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/comic/updateSave",
                        type: "POST",
                        data: {
                            "content": code,
                            "status": $('#wrap input[name="type"]:checked ').val(),
                            "tempAddImageIds1": $("#tempAddImageIds1").html(),
                            "tempDelImageIds": $("#tempDelImageIds").html(),
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/comic/indexComic?workId=" + $("#workId").val();
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
        }
    }
    $(function () {
        $comic.fn.init();
    })
</script>
</body>
</html>

