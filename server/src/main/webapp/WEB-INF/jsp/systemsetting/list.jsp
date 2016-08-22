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
        <h1 class="page-title">相关设置</h1>
        <form id="fromId" method="post" enctype="multipart/form-data">

            <div class="block-area">
                <div class="col-md-12 m-b-15">
                <label class="col-md-12 m-b-15">设置前端用户最多可输入字符数：</label>
                <div class="col-md-6 m-b-15">
                    <input type="text" id="wordNum" name="wordNum" value="${limitWords.num}"
                           class="input-sm form-control validate[required]" placeholder="...">
                    <button type="button" onclick="prize.fn.saveLimit();" class="btn btn-info btn-sm m-t-10">提交</button>
                </div>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-12 m-b-15" style="margin-top: 10px">
                    <label>抽奖概率设置：</label>
                    <div class="col-md-12 m-b-15">
                        <div class="col-md-3 m-b-15">
                            <label>加油：</label>
                            <input type="text" id="name1" name="name1" value="${prize1.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>10馒头：</label>
                            <input type="text" id="name2" name="name2" value="${prize2.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>19馒头：</label>
                            <input type="text" id="name3" name="name3" value="${prize3.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>29馒头：</label>
                            <input type="text" id="name4" name="name4" value="${prize4.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>99馒头：</label>
                            <input type="text" id="name5" name="name5" value="${prize5.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>199馒头：</label>
                            <input type="text" id="name6" name="name6" value="${prize6.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>499馒头：</label>
                            <input type="text" id="name7" name="name7" value="${prize7.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <label>999馒头：</label>
                            <input type="text" id="name8" name="name8" value="${prize8.pro}"
                                   class="input-sm form-control validate[required]" placeholder="...">
                        </div>
                        <div class="col-md-3 m-b-15">
                            <button type="button" onclick="prize.fn.save();" class="btn btn-info btn-sm m-t-10">提交</button>
                        </div>
                    </div>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                    <label>日常任务设置：</label>
                    <div>&nbsp;&nbsp;</div>
                    <div class="col-md-12 m-b-15">
                    <c:if test="${null == taskList || taskList.size() == 0 }">
                        暂无日常任务
                    </c:if>
                    <c:if test="${null != taskList && taskList.size() > 0 }">
                        <c:forEach var="co" items="${taskList}">
                            <div class="col-md-6 m-b-15"  id="ul" + ${co.id}>
                                <label>任务类型：</label>
                                <select id="type" name="type" class="select">
                                    <option value="">选择任务类型</option>
                                    <option value="0" <c:if test="${co.type == 0}">selected</c:if>>发布帖子</option>
                                    <option value="1" <c:if test="${co.type == 1}">selected</c:if>>主创点赞</option>
                                    <option value="2" <c:if test="${co.type == 2}">selected</c:if>>邀请用户</option>
                                    <option value="3" <c:if test="${co.type == 3}">selected</c:if>>发布评价</option>
                                    <option value="4" <c:if test="${co.type == 4}">selected</c:if>>发布弹幕</option>
                                    <option value="5" <c:if test="${co.type == 5}">selected</c:if>>回复帖子</option>
                                </select>&nbsp;&nbsp;
                                <label>任务数量：</label>
                                <input type="text" id="num" name="num"  value="${co.taskCount}" class="input-sm form-control validate[required]" placeholder="请输入任务数量">&nbsp;&nbsp;
                                <label>馒头奖励数量：</label>
                                <input type="text" id="coin" name="coin" value="${co.coin}" class="input-sm form-control validate[required]" placeholder="请填写馒头奖励数量">&nbsp;&nbsp;
                                <button type="button" onclick="prize.fn.delete(${co.id});" class="btn btn-info btn-sm m-t-10">删除</button>
                            </div>
                        </c:forEach>
                    </c:if>
                    <div class="col-md-12 m-b-15">
                        <div>&nbsp;&nbsp;</div>
                        <button type="button" onclick="prize.fn.do();" class="btn btn-info btn-sm m-t-10">增加</button>
                    </div>

                    <div class="form-group col-md-6 m-b-15" style="display: none" id="add">
                        <label>任务类型：</label>
                        <select id="type1" name="type1" class="form-control">
                            <option value="">选择任务类型</option>
                            <option value="0">发布帖子</option>
                            <option value="1">主创点赞</option>
                            <option value="2">邀请用户</option>
                            <option value="3">发布评价</option>
                            <option value="4">发布弹幕</option>
                            <option value="5">回复帖子</option>
                        </select>

                        <label>任务数量：</label>
                        <input type="text" id="num1" name="num1" class="input-sm form-control validate[required]" placeholder="请输入任务数量">

                        <label>馒头奖励数量：</label>
                        <input type="text" id="coin1" name="coin1" class="input-sm form-control validate[required]" placeholder="请填写馒头奖励数量">

                    </div>

                    <div class="col-md-12 m-b-15">
                        <button type="button" onclick="prize.fn.saveTask();" class="btn btn-info btn-sm m-t-10">提交</button>
                    </div>
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
    prize = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

            },
            do: function () {

                var tempDiv = $("#add").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "");
                tempDiv.insertBefore("#add");


            },
            checkData: function () {
                var flag = true;
                var name1 = $('#name1').val();
                var name2 = $('#name2').val();
                var name3 = $('#name3').val();
                var name4 = $('#name4').val();
                var name5 = $('#name5').val();
                var name6 = $('#name6').val();
                var name7 = $('#name7').val();
                var name8 = $('#name8').val();


                if (null == name1 || name1 == '') {
                    $common.fn.notify("请输入加油概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name1)) {
                    $common.fn.notify("加油概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name2 || name2 == '') {
                    $common.fn.notify("请输入10馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name2)) {
                    $common.fn.notify("10馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name3 || name3 == '') {
                    $common.fn.notify("请输入19馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name3)) {
                    $common.fn.notify("19馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name4 || name4 == '') {
                    $common.fn.notify("请输入29馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name4)) {
                    $common.fn.notify("29馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name5 || name5 == '') {
                    $common.fn.notify("请输入99馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name5)) {
                    $common.fn.notify("99馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name6 || name6 == '') {
                    $common.fn.notify("请输入199馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name6)) {
                    $common.fn.notify("199馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name7 || name7 == '') {
                    $common.fn.notify("请输入499馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name7)) {
                    $common.fn.notify("499馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == name8 || name8 == '') {
                    $common.fn.notify("请输入999馒头概率", "error");
                    flag = false;
                    return;
                }
                if (isNaN(name8)) {
                    $common.fn.notify("999馒头概率必须为数字", "error");
                    flag = false;
                    return;
                }

                /*if ((name1+name2+name3+name4+name5+name6+name7+name8) != 100.0) {
                    $common.fn.notify("概率和必须为100%", "error");
                    flag = false;
                    return;
                }*/

                return flag;
            },
            saveLimit: function () {
                if (prize.fn.checkData()) {
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/prize/saveLimit",
                        type: "POST",
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/prize/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            save: function () {
                if (prize.fn.checkData()) {
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/prize/save",
                        type: "POST",
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/prize/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            saveTask: function () {

                $("#fromId").ajaxSubmit({
                    url: "${contextPath}/admin/prize/saveTask",
                    type: "POST",
                    date: {

                    },
                    success: function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            window.location.href = "${contextPath}/admin/prize/index";
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    }
                });
            },
            delete: function (id) {

                $leoman.ajax("${contextPath}/admin/prize/delete", {
                    "id" : id
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        window.location.href = "${contextPath}/admin/prize/index";
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });
            },
        }
    }
    $(function () {
        prize.fn.init();
    })
</script>
</body>
</html>

