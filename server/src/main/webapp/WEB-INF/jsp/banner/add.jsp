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
        <h1 class="page-title">广告banner编辑</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${banner.id}">
                <input type="hidden" id="infoType" value="${banner.id}">
                <input type="hidden" id="categoryId" value="${banner.categoryId}">
                <input type="hidden" id="seriesId" value="${banner.workId}">
                <input type="hidden" id="positions" value="${banner.position}">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>广告封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${banner.cover}">
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
                    <div class="col-md-6 m-b-15">
                        <div class="radio">
                            <label onclick="banner.fn.show(0)">
                                <input type="radio" name="type" value="0"
                                       <c:if test="${banner.linkUrl != null || banner.id == null}">checked</c:if>>添加外链
                            </label>
                        </div>
                        <div class="radio">
                            <label onclick="banner.fn.show(1)">
                                <input type="radio" name="type" value="1"
                                       <c:if test="${(banner.linkUrl == null || banner.linkUrl == '') && banner.id != null}">checked</c:if>>选择链接地址
                            </label>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="display: none;margin-top: 10px;" id="type1">
                        <label>链接地址：</label>
                        <input type="text" id="linkUrl" value="${banner.linkUrl}" name="linkUrl" class="input-sm form-control validate[required]" placeholder="请输入链接url">
                    </div>
                    <div class="col-md-12 m-b-15" style="display: none;margin-top: 10px;" id="type2">
                        <div class="col-md-2 form-group">
                            <label>分类：</label>
                            <select id="position" name="position" style="width: 150px;" class="select">
                                <option value="">全部</option>
                                <option value="0" <c:if test="${banner.position == 0}">selected</c:if>>帖子</option>
                                <option value="1" <c:if test="${banner.position == 1}">selected</c:if>>视频</option>
                                <option value="2" <c:if test="${banner.position == 2}">selected</c:if>>小说</option>
                                <option value="3" <c:if test="${banner.position == 3}">selected</c:if>>漫画</option>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <label>选择分类：</label>
                            <select id="categoryList" style="width: 150px;" class="select"></select>
                        </div>
                        <div class="col-sm-2">
                            <label>选择详情：</label>
                            <select id="seriesList" style="width: 150px;" class="select"></select>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="banner.fn.save();" class="btn btn-info btn-sm m-t-10">提交
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
    banner = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                var infoType = $('#infoType').val();
                var type = 0;
                if (null != infoType && infoType != '') {
                    type = infoType;

                    if (type == 0) {
                        $("#type1").css('display', '');
                        $("#type2").css('display', 'none');
                    } else {
                        $("#type1").css('display', 'none');
                        $("#type2").css('display', '');
                    }
                }
                banner.fn.show(type);

                if ($('#positions').val() == 0) {
                    //自动加载下拉框
                    banner.fn.loadCategorys();
                }else {
                    //自动加载下拉框
                    banner.fn.loadCategory($('#positions').val());
                }


                $('#position').change(function () {

                    var type = $(this).val();

                    if (null != type && type != '' && type != 0) {
                        banner.fn.loadCategory(type);
                    }
                    if (null != type && type != '' && type == 0) {
                        banner.fn.loadCategorys();
                    }


                });

                $('#categoryList').change(function () {
                    //$('#seriesId').val('');

                    var categoryId = $(this).val();
                    var type = $('#position').val();
                    if (type != 0) {
                        if (null != categoryId && categoryId != '' && null != type && type != '') {
                            banner.fn.loadSeries(categoryId, type);
                        }
                    }
                    if (type == 0) {
                        if (null != categoryId && categoryId != '') {
                            banner.fn.loadContent(categoryId);
                        }
                    }
                });
            },
            show: function (type) {
                if (type == 0) {
                    //$("#type2").val("");
                    $('#position').val("");
                    $('#categoryList').val("");
                    $('#seriesList').val("");

                    $("#type1").css('display', '');
                    $("#type2").css('display', 'none');
                } else if (type == 1) {
                    //$("#type1").val("");
                    $('#linkUrl').val('');

                    $("#type1").css('display', 'none');
                    $("#type2").css('display', '');
                }
            },
            loadCategory: function (type) {

                var categoryId = $('#categoryId').val();

                $leoman.ajax("${contextPath}/admin/banner/categoryList", {
                    typeId: type
                }, function (result) {
                    if (null != result && result.length > 0) {
                        $('#categoryList').empty();
                        // 获取返回的城市列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择分类</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#categoryList').append(content);
                        $('#categoryList').selectpicker('refresh');
                    } else {
                        $leoman.notify("获取分类信息失败", "error");
                    }

                    if (null != categoryId && categoryId != '') {
                        $('#categoryList').val(categoryId);
                        $('#categoryList').selectpicker('refresh');
                    }
                });

                var sourceId = 0;
                if (null == categoryId || categoryId == '') {
                    sourceId = $('#categoryList option:selected').val();
                } else {
                    sourceId = categoryId;
                }

                banner.fn.loadSeries(sourceId, type);
            },
            loadSeries: function (categoryId, type) {
                $('#seriesList').html('');

                $leoman.ajax("${contextPath}/admin/banner/seriesList", {
                    categoryId: categoryId,
                    typeId: type
                }, function (result) {
                    if (null != result) {
                        $('#seriesList').empty();
                        // 获取返回的城市列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择详情</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#seriesList').append(content);
                        $('#seriesList').selectpicker('refresh');
                    } else {
                        $leoman.notify("获取分类信息失败", "error");
                    }

                    var seriesId = $('#seriesId').val();
                    if (null != seriesId && seriesId != '') {
                        $('#seriesList').val(seriesId);
                        $('#seriesList').selectpicker('refresh');
                    }
                });
            },
            loadCategorys: function () {

                var categoryId = $('#categoryId').val();

                $leoman.ajax("${contextPath}/admin/banner/categorys", null, function (result) {
                    if (null != result && result.length > 0) {
                        $('#categoryList').empty();
                        // 获取返回的城市列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择分类</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#categoryList').append(content);
                        $('#categoryList').selectpicker('refresh');
                    } else {
                        $leoman.notify("获取分类信息失败", "error");
                    }

                    if (null != categoryId && categoryId != '') {
                        $('#categoryList').val(categoryId);
                        $('#categoryList').selectpicker('refresh');
                    }
                });

                var sourceId = 0;
                if (null == categoryId || categoryId == '') {
                    sourceId = $('#categoryList option:selected').val();
                } else {
                    sourceId = categoryId;
                }

                banner.fn.loadContent(sourceId);
            },
            loadContent: function (categoryId) {
                $('#seriesList').html('');

                $leoman.ajax("${contextPath}/admin/banner/loadContent", {
                    categoryId: categoryId
                }, function (result) {
                    if (null != result) {
                        $('#seriesList').empty();
                        // 获取返回的城市列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择详情</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#seriesList').append(content);
                        $('#seriesList').selectpicker('refresh');
                    } else {
                        $leoman.notify("获取分类信息失败", "error");
                    }

                    var seriesId = $('#seriesId').val();
                    if (null != seriesId && seriesId != '') {
                        $('#seriesList').val(seriesId);
                        $('#seriesList').selectpicker('refresh');
                    }
                });
            },
            checkData: function () {
                var flag = true;
                /*var cover = $('#imageFile').val();

                 if (null == cover || cover == '') {
                 $common.fn.notify("请上传广告图片", "error");
                 flag = false;
                 return;
                 }*/

                return flag;
            },
            save: function () {
                if (banner.fn.checkData()) {
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/banner/save",
                        type: "POST",
                        data: {
                            "categoryId": $('#categoryList option:selected').val(),
                            "seriesId": $('#seriesList option:selected').val()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/banner/index";
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
        banner.fn.init();
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

