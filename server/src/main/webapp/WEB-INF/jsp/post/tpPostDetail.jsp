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
        <h1 class="page-title">投票贴详情</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data">
            <div class="block-area">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <input type="hidden" id="postId" name="postId" value="${postBase.id}">
                        <input type="hidden" id="categoryId" name="categoryId" value="${postBase.category.id}">
                        <label>封面：</label>
                        <img class="imgs" alt="" src="${postBase.avater}" style="height: 200px;width: 300px; z-index: 1;"/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>帖子名称：</label>
                        <input type="text" id="name" name="name" value="${postBase.name}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>截止时间：</label>
                        <input type="text" class="form-control input-append date form_datetime"
                               value="<date:date value="${postBase.endDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>"
                               style="width: 180px;" id="endDate" name="endDate" maxlength="20"
                               data-rule="required" placeholder="..." disabled/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>分类：</label>
                        <select id="typeList" name="typeList" class="select" disabled></select>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>类型：</label>
                        <select id="type" name="type" class="select" disabled>
                            <option value="">全部</option>
                            <option value="0" <c:if test="${postBase.type == 0}">selected</c:if>>普通贴</option>
                            <option value="1" <c:if test="${postBase.type == 1}">selected</c:if>>直播贴</option>
                            <option value="2" <c:if test="${postBase.type == 2}">selected</c:if>>投票贴</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15" id="tp">
                        <label>投票说明：</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control" placeholder="..." disabled>${postBase.content}</textarea>
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>投票对象：</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == tpPostSubList || tpPostSubList.size() == 0 }">
                                投票对象
                            </c:if>
                            <c:if test="${null != tpPostSubList && tpPostSubList.size() > 0 }">
                                <c:forEach var="co" items="${tpPostSubList}">
                                    <div class="col-md-3 m-b-15">
                                        <label>名字：</label>
                                        <input value="${co.name}" class="input-sm form-control validate[required]" disabled/>&nbsp;&nbsp;
                                        <label>票数：</label>
                                        <input value="${co.count}" class="input-sm form-control validate[required]" disabled/>&nbsp;&nbsp;
                                        <label>头像：</label>
                                        <div>
                                            <img class="imgs" alt="" src="${co.cover}" style="height: 200px;width: 300px; z-index: 1;"/>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <div class="col-md-4 m-b-15" style="display: none" id="delete">
                        <input type="text" id="tpSub" name="tpSub" class="input-sm form-control validate[required]" placeholder="...">
                        <label>头像：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="">
                            </div>
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="avater" name="avater" type="file"/>
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
    post = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                post.fn.getSelectList();
                post.fn.loadTime();
            },
            loadTime: function () {
                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1,
                    format: 'yyyy-mm-dd hh:ii:ss'
                });
            },
            getSelectList: function () {
                var workId = $('#categoryId').val();

                //console.log(hdsjjhhj);
                $leoman.ajax("${contextPath}/admin/userPost/categoryList", null, function (result) {
                    if (null != result) {
                        $('#typeList').empty();
                        // 获取返回的作品类型列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择类型</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#typeList').append(content);
                        $('#typeList').selectpicker('refresh');
                    } else {
                        $common.notify("获取类型信息失败", "error");
                    }

                    if (null != workId && workId != '') {
                        $('#typeList').val(workId);
                        $('#typeList').selectpicker('refresh');
                    }
                });

            },
            do: function () {
                var tempDiv = $("#delete").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "");
                tempDiv.insertBefore("#delete");
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var typeList = $('#typeList').val();
                var type = $('#type').val();

                if (null == name || name == '') {
                    $common.fn.notify("请输入帖子名字", "error");
                    flag = false;
                    return;
                }

                if (null == typeList || typeList == '') {
                    $common.fn.notify("请选择帖子分类", "error");
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
                if (post.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/post/save",
                        type: "POST",
                        data: {
                            content: $('.wysiwye-editor').code()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/post/index";
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
        post.fn.init();
    })
</script>
</body>
</html>

