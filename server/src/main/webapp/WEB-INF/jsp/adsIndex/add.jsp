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
        <h1 class="page-title">编辑推荐</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${adsIndex.id}">
                <input type="hidden" id="categoryId" value="${adsIndex.categoryId}">
                <input type="hidden" id="seriesId" value="${adsIndex.workId}">
                <input type="hidden" id="positions" value="${adsIndex.position}">
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
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>小标题：</label>
                        <input type="text" id="subTitle" name="subTitle" value="${adsIndex.subTitle}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-12 m-b-15" >
                        <div class="col-md-2 form-group">
                            <label>选择链接位置：</label>
                            <select id="position" name="position" style="width: 150px;" class="select">
                                <option value="0" <c:if test="${adsIndex.position == 0}">selected</c:if>>帖子</option>
                                <option value="1" <c:if test="${adsIndex.position == 1}">selected</c:if>>视频</option>
                                <option value="2" <c:if test="${adsIndex.position == 2}">selected</c:if>>小说</option>
                                <option value="3" <c:if test="${adsIndex.position == 3}">selected</c:if>>漫画</option>
                                <option value="4" <c:if test="${adsIndex.position == 4}">selected</c:if>>资源</option>
                                <option value="5" <c:if test="${adsIndex.position == 5}">selected</c:if>>福利社</option>
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
                        <button type="button" onclick="adsIndex.fn.save();" class="btn btn-info btn-sm m-t-10">提交
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
    adsIndex = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                if ($('#positions').val() == 0) {
                    //自动加载下拉框
                    adsIndex.fn.loadCategorys();
                } else if ($('#positions').val() == 4) {
                    //自动加载下拉框
                    adsIndex.fn.loadCategoryResource();
                }else if ($('#positions').val() == 5) {
                    //自动加载下拉框
                    adsIndex.fn.loadCategoryWelfare();
                }else {
                    //自动加载下拉框
                    adsIndex.fn.loadCategory($('#positions').val());
                }


                $('#position').change(function () {

                    var type = $(this).val();

                    if (null != type && type != '' && type != 0 && type != 4  && type != 5) {
                        adsIndex.fn.loadCategory(type);
                    } else if (type == 0) {
                        adsIndex.fn.loadCategorys();
                    }else if (type == 4) {
                        adsIndex.fn.loadCategoryResource();
                    }else if (type == 5) {
                        adsIndex.fn.loadCategoryWelfare();
                    }
                });

                $('#categoryList').change(function () {
                    //$('#seriesId').val('');

                    var categoryId = $(this).val();
                    var type = $('#position').val();
                    if (type != 0 && type != 4  && type != 5) {
                        if (null != categoryId && categoryId != '' && null != type && type != '') {
                            adsIndex.fn.loadSeries(categoryId, type);
                        }
                    }else if (type == 0) {
                        if (null != categoryId && categoryId != '') {
                            adsIndex.fn.loadContent(categoryId);
                        }
                    }else if (type == 4) {
                        if (null != categoryId && categoryId != '') {
                            adsIndex.fn.loadResource(categoryId);
                        }
                    }else if (type == 5) {
                        if (null != categoryId && categoryId != '') {
                            adsIndex.fn.loadWelfare(categoryId);
                        }
                    }
                });
            },
            loadCategory: function (type) {

                var categoryId = $('#categoryId').val();

                $leoman.ajax("${contextPath}/admin/banner/categoryList", {
                    typeId: type
                }, function (result) {
                    if (null != result) {
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

                adsIndex.fn.loadSeries(sourceId, type);
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

                adsIndex.fn.loadContent(sourceId);
            },
            loadCategoryResource: function () {

                $('#categoryList').empty();

                var content = "<option value=''>请选择分类</option>";
                content += "<option value='1'>剧照</option>";
                content += "<option value='2'>音乐</option>";
                content += "<option value='3'>游戏</option>";

                $('#categoryList').append(content);
                $('#categoryList').selectpicker('refresh');

                var categoryId = $('#categoryId').val();
                if (null != categoryId && categoryId != '') {
                    $('#categoryList').val(categoryId);
                    $('#categoryList').selectpicker('refresh');
                }

                var sourceId = 0;
                if (null == categoryId || categoryId == '') {
                    sourceId = $('#categoryList option:selected').val();
                } else {
                    sourceId = categoryId;
                }

                adsIndex.fn.loadResource(sourceId);
            },
            loadCategoryWelfare: function () {

                $('#categoryList').empty();

                var content = "<option value=''>请选择分类</option>";
                content += "<option value='1'>铃声</option>";
                content += "<option value='2'>经验值</option>";
                content += "<option value='3'>实物</option>";
                content += "<option value='4'>表情包</option>";
                content += "<option value='5'>商城购买</option>";
                content += "<option value='6'>游戏兑换码</option>";

                $('#categoryList').append(content);
                $('#categoryList').selectpicker('refresh');

                var categoryId = $('#categoryId').val();
                if (null != categoryId && categoryId != '') {
                    $('#categoryList').val(categoryId);
                    $('#categoryList').selectpicker('refresh');
                }

                var sourceId = 0;
                if (null == categoryId || categoryId == '') {
                    sourceId = $('#categoryList option:selected').val();
                } else {
                    sourceId = categoryId;
                }

                adsIndex.fn.loadWelfare(sourceId);
            },
            loadSeries: function (categoryId, type) {

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
            loadResource: function (categoryId) {
                $('#seriesList').html('');

                $leoman.ajax("${contextPath}/admin/adsIndex/loadResource", {
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
            loadWelfare: function (categoryId) {
                $('#seriesList').html('');

                $leoman.ajax("${contextPath}/admin/adsIndex/loadWelfare", {
                    categoryId: categoryId
                }, function (result) {
                    if (null != result) {
                        $('#seriesList').empty();
                        var content = "<option value=''>请选择详情</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.title + "</option>";
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
                var cover = $('#imageFile').val();
                var title = $('#title').val();
                var subTilte = $('#subTitle').val();
                var position = $('#position').val();
                var category = $('#categoryList').val();
                var series = $('#seriesList').val();

                if (null == cover || cover == '') {
                    $common.fn.notify("请上传广告图片", "error");
                    flag = false;
                    return;
                }

                if (null == title || title == '') {
                    $common.fn.notify("请输入大标题", "error");
                    flag = false;
                    return;
                }

                if (null == subTilte || subTilte == '') {
                    $common.fn.notify("请输入小标题", "error");
                    flag = false;
                    return;
                }

                if (null == position || position == '') {
                    $common.fn.notify("请选择位置", "error");
                    flag = false;
                    return;
                }

                if (null == category || category == '') {
                    $common.fn.notify("请选择分类", "error");
                    flag = false;
                    return;
                }

                if (null == series || series == '') {
                    $common.fn.notify("请选择详情", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
                console.log($('#seriesList option:selected').val());
                if (adsIndex.fn.checkData()) {
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/adsIndex/save",
                        type: "POST",
                        data: {
                            "categoryId": $('#categoryList option:selected').val(),
                            "seriesId": $('#seriesList option:selected').val()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/adsIndex/index";
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

