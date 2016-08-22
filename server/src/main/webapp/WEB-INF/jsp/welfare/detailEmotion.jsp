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
        <h1 class="page-title">表情包详情</h1>
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
                        </div>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>福利标题：</label>
                        <input type="text" id="title" name="title" value="${welfare.title}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>福利小标题：</label>
                        <input type="text" id="subtitle" name="subtitle" value="${welfare.subTitle}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" value="${welfare.coin}" class="form-control validate[required]" id="coin3" name="coin3" placeholder="..." disabled/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>限制：</label>
                        <select id="limit" name="limit" class="select" disabled>
                            <option value="1" <c:if test="${welfare.conditions == 1}">selected</c:if>>一级及以上用户</option>
                            <option value="2" <c:if test="${welfare.conditions == 2}">selected</c:if>>二级及以上用户</option>
                            <option value="3" <c:if test="${welfare.conditions == 3}">selected</c:if>>三级及以上用户</option>
                            <option value="4" <c:if test="${welfare.conditions == 4}">selected</c:if>>四级及以上用户</option>
                            <option value="5" <c:if test="${welfare.conditions == 5}">selected</c:if>>五级及以上用户</option>
                            <option value="6" <c:if test="${welfare.conditions == 6}">selected</c:if>>六级及以上用户</option>
                            <option value="7" <c:if test="${welfare.conditions == 7}">selected</c:if>>七级及以上用户</option>
                            <option value="8" <c:if test="${welfare.conditions == 8}">selected</c:if>>八级及以上用户</option>
                            <option value="9" <c:if test="${welfare.conditions == 9}">selected</c:if>>九级及以上用户</option>
                            <option value="10" <c:if test="${welfare.conditions == 10}">selected</c:if>>十级及以上用户</option>
                            <option value="11" <c:if test="${welfare.conditions == 11}">selected</c:if>>十一级及以上用户</option>
                            <option value="12" <c:if test="${welfare.conditions == 12}">selected</c:if>>十二级及以上用户</option>
                            <option value="13" <c:if test="${welfare.conditions == 13}">selected</c:if>>十三级及以上用户</option>
                            <option value="14" <c:if test="${welfare.conditions == 14}">selected</c:if>>十四级及以上用户</option>
                            <option value="15" <c:if test="${welfare.conditions == 15}">selected</c:if>>十五级及以上用户</option>
                            <option value="16" <c:if test="${welfare.conditions == 16}">selected</c:if>>十六级及以上用户</option>
                            <option value="17" <c:if test="${welfare.conditions == 17}">selected</c:if>>十七级及以上用户</option>
                            <option value="18" <c:if test="${welfare.conditions == 18}">selected</c:if>>十八级及以上用户</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>描述：</label>
                        <textarea cols="40" rows="6" id="description3" name="description3" class="form-control" placeholder="..." disabled>${welfare.description}</textarea>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>上传表情包：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">上传表情包</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="url3" name="url3" type="file"/>
                                </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
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
    welfare = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

            },
        }
    }
    $(function () {
        welfare.fn.init();
    })
</script>
</body>
</html>

