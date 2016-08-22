<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
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
        <h1 class="page-title">游戏编辑</h1>
        <form id="fromId" method="post" enctype="multipart/form-data">
            <div class="block-area">
                <input type="hidden" name="gameId" value="${gameResource.id}">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${gameResource.url}">
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
                    <div class="col-md-6 m-b-15">
                        <label>游戏名称：</label>
                        <input type="text" id="name" name="name" value="${gameResource.name}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>版本号：</label>
                        <input type="text" id="version" name="version" value="${gameResource.version}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>大小：</label>
                        <input type="text" id="size" name="size" value="${gameResource.size}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>下载链接：</label>
                        <input type="text" id="linkUrl" name="linkUrl" value="${gameResource.linkUrl}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>游戏简介：</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control" placeholder="...">${gameResource.description}</textarea>
                    </div>
                    <hr class="whiter m-t-20"/>

                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="game.fn.save();" class="btn btn-info btn-sm m-t-10">提交
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
    game = {
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
                var name = $('#name').val();
                var version = $('#version').val();
                var size = $('#size').val();
                var linkUrl = $('#linkUrl').val();
                var description = $('#description').val();

                if (null == name || name == '') {
                    $common.fn.notify("请输入游戏名称", "error");
                    flag = false;
                    return;
                }

                if (null == version || version == '') {
                    $common.fn.notify("请输入版本号", "error");
                    flag = false;
                    return;
                }

                if (null == size || size == '') {
                    $common.fn.notify("请输入游戏大小", "error");
                    flag = false;
                    return;
                }

                if (isNaN(size)) {
                    $common.fn.notify("游戏大小必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == linkUrl || linkUrl == '') {
                    $common.fn.notify("请输入下载链接", "error");
                    flag = false;
                    return;
                }

                if (null == description || description == '') {
                    $common.fn.notify("请输入游戏简介", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
                if (game.fn.checkData()) {
                    console.log(0);
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/game/save",
                        type: "POST",
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/game/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            }
        }
    }
    $(function () {
        game.fn.init();
    })
</script>
</body>
</html>

