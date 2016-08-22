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
        <h1 class="page-title">编辑经验</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data">
            <input type="hidden" id="welfareId" name="welfareId" value="${welfare.id}">
            <div class="block-area">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${welfare.cover}">
                            </div>
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="cover" name="cover" type="file"/>
                                </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>福利标题：</label>
                        <input type="text" id="title" name="title" value="${welfare.title}" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>福利小标题：</label>
                        <input type="text" id="subtitle" name="subtitle" value="${welfare.subTitle}" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>类型：</label>
                        <select id="type" name="type" class="select" disabled>
                            <option value="">全部</option>
                            <option value="0" <c:if test="${welfare.type == 0}">selected</c:if>>铃声</option>
                            <option value="1" <c:if test="${welfare.type == 1}">selected</c:if>>经验值</option>
                            <option value="2" <c:if test="${welfare.type == 2}">selected</c:if>>实物</option>
                            <option value="3" <c:if test="${welfare.type == 3}">selected</c:if>>表情包</option>
                            <option value="4" <c:if test="${welfare.type == 4}">selected</c:if>>商城购买</option>
                            <option value="5" <c:if test="${welfare.type == 5}">selected</c:if>>游戏兑换码</option>
                        </select>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" value="${welfare.coin}" class="form-control validate[required]" id="coin1" name="coin1" maxlength="20" placeholder="..."/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>经验数：</label>
                        <input type="text" value="${welfare.exper}" vid="experience" name="experience" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-12 m-b-15"  style="margin-top: 10px;">
                        <label>描述：</label>
                        <textarea cols="40" rows="6" id="description1" name="description1" class="form-control" placeholder="...">${welfare.description}</textarea>
                    </div>
                    <hr class="whiter m-t-20"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="welfare.fn.save();" class="btn btn-info btn-sm m-t-10">提交
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
    welfare = {
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
                var title = $('#title').val();
                var subtitle = $('#subtitle').val();
                var type = $('#type').val();

                if (null == title || title == '') {
                    $common.fn.notify("请输入福利标题", "error");
                    flag = false;
                    return;
                }

                if (null == subtitle || subtitle == '') {
                    $common.fn.notify("请输入福利小标题", "error");
                    flag = false;
                    return;
                }

                if (null == type || type == '') {
                    $common.fn.notify("请选择类型", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
                if (welfare.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/welfare/save",
                        type: "POST",
                        data: {
                            type: $('#type').val()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/welfare/index";
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
        welfare.fn.init();
    })
</script>
</body>
</html>

