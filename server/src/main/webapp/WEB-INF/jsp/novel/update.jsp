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
                <input type="hidden" id="workNovelId" name="workNovelId" value="${workNovel.id}">
                <input type="hidden" id="series" name="series" value="${series}">
                <div class="row">
                    <div class="col-md-6 m-b-15">
                        <label>小说名称：</label>
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
                        <input type="text" id="novel" name="novel" value="${workNovel.name}"
                               class="input-sm form-control validate[required]" placeholder="..." >
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>章节正文：</label>
                        <div class="wysiwye-editor">${workNovel.detail}</div>
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <div class="radio" id="wrap">
                            <input type="radio" name="type" value="1" id="radio" <c:if test="${work.isEnd == 1}">checked</c:if>>设为完结
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>

                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="$novel.fn.save();">提交</button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

        <form id="tempImageForm" method="post" action="${contextPath}/admin/creator/addTempImage" enctype="multipart/form-data">
            <input type="file" name="tempImage" id="tempImage" data-rule="required" style="display:none;" onchange="creator.fn.saveTempImage()"/>
            <input type="hidden" id="tempImageType" name="type"/>
        </form>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    $novel = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {


            },
            checkData: function () {
                var flag = true;
                var novel = $('#novel').val();
                var code = $('.wysiwye-editor').code();

                if (null == novel || novel == '') {
                    $common.fn.notify("请输入章节名字", "error");
                    flag = false;
                    return;
                }

                if (null == code || code == '') {
                    $common.fn.notify("请输入章节正文", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
                if ($novel.fn.checkData()) {
                    var code = $('.wysiwye-editor').code();
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/novel/updateSave",
                        type: "POST",
                        data: {
                            "content": code,
                            status: $('#wrap input[name="type"]:checked ').val(),
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/novel/indexNovel?workId=" + $("#workId").val();
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
        $novel.fn.init();
    })
</script>
</body>
</html>
