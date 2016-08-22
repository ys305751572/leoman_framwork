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
        <h1 class="page-title">散客详情</h1>
        <div class="block-area">
            <div class="col-md-2">
                <div class="tile">
                    <h2 class="tile-title">基本信息</h2>
                    <div class="p-10" style="height:520px">
                        <div class="form-group">
                            <label>比赛时间:</label>
                            ${reserve.startDate}
                        </div>
                        <div class="form-group">
                            <label>城 市:</label>
                            ${reserve.startDate}
                        </div>
                        <div class="form-group">
                            <label>球 场:</label>
                            ${reserve.nickName}
                        </div>
                        <div class="form-group">
                            <label>年 龄:</label>
                            ${user.age}
                        </div>
                        <div class="form-group">
                            <label>身 高:</label>
                            ${user.height}
                        </div>
                        <div class="form-group">
                            <label>体 重:</label>
                            ${user.weight}
                        </div>
                        <div class="form-group">
                            <label>位 置:</label>
                            ${user.position}
                        </div>
                        <div class="form-group">
                            <label>用户状态:</label>
                            <div class="col-sm-5">
                                <h4><c:if test="${user.status eq 0}">正常</c:if>
                                    <c:if test="${user.status eq 1}">冻结</c:if>
                                </h4>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>注册时间:</label>
                            <date:date format="yyyy-MM-dd HH:mm:ss" value="${user.createDate}"></date:date>
                        </div>
                        <div class="form-group">
                            <label>信誉积分:</label>
                            ${user.credibility}
                        </div>
                        <div class="form-group">
                            <label>会员等级:</label>
                            <div class="col-sm-5">
                                <h4><c:if test="${user.vipLevel eq 0}">非会员</c:if>
                                    <c:if test="${user.vipLevel ne 0}">Lv + "${user.vipLevel}"</c:if>
                                </h4>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>会员积分:</label>
                            ${user.integral}
                        </div>
                        <div class="form-group">
                            <label>会员结束时间:</label>
                            <date:date format="yyyy-MM-dd HH:mm:ss" value="${user.vipEndDate}"></date:date>
                        </div>
                        <div class="form-group">
                            <label>地理位置:</label>
                            ${user.integral}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>
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

