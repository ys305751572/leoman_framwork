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
        <h1 class="page-title">浮窗广告</h1>
        <div class="block-area">
            <button onclick="floatWin.fn.banner(1,0);" class="btn btn-outline btn-primary btn-lg" role="button">显示</button>
            <button onclick="floatWin.fn.banner(1,1);" class="btn btn-outline btn-primary btn-lg" role="button">隐藏</button>
        </div>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${floatWin.id}">
                <input type="hidden" id="categoryId" value="${floatWin.categoryId}">
                <input type="hidden" id="seriesId" value="${floatWin.detailId}">
                <input type="hidden" id="positions" value="${floatWin.position}">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${floatWin.cover}">
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
                    <div class="col-md-3 m-b-15" style="margin-top: 10px;">
                        <label>标题：</label>
                        <input type="text" id="title" name="title" value="${floatWin.title}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-3 m-b-15" style="margin-top: 10px;">
                        <label>日期：</label>
                        <input type="text" class="form-control input-append date form_datetime"
                               style="width: 180px;" id="time" name="time" maxlength="20"
                               value="<date:date value="${floatWin.time}" format="yyyy-MM-dd HH:mm:ss"></date:date>"
                               data-rule="required" placeholder="..."/>
                    </div>
                    <div class="col-md-12 m-b-15" >
                        <div class="col-md-2 form-group">
                            <label>选择链接位置：</label>
                            <select id="position" name="position" style="width: 150px;" class="select">
                                <option value="">全部</option>
                                <option value="0" <c:if test="${floatWin.position == 0}">selected</c:if>>帖子</option>
                                <option value="1" <c:if test="${floatWin.position == 1}">selected</c:if>>视频</option>
                                <option value="2" <c:if test="${floatWin.position == 2}">selected</c:if>>小说</option>
                                <option value="3" <c:if test="${floatWin.position == 3}">selected</c:if>>漫画</option>
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
                        <button type="button" onclick="floatWin.fn.save();" class="btn btn-info btn-sm m-t-10">提交
                        </button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

        <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="showText">确定禁用该账号？</h4>
                    </div>
                    <div class="modal-body">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" id="confirm" class="btn btn-primary">确定
                        </button>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </div>
    </section>
    <br/><br/>
</section>
<!-- JS -->
<%@ include file="../inc/new/foot.jsp" %>

<script>
    floatWin = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

                // 渲染时间控件
                floatWin.fn.loadTime();

                if ($('#positions').val() == 0) {
                    //自动加载下拉框
                    floatWin.fn.loadCategorys();
                }else {
                    //自动加载下拉框
                    floatWin.fn.loadCategory($('#positions').val());
                }


                $('#position').change(function () {

                    var type = $(this).val();

                    if (null != type && type != '' && type != 0) {
                        floatWin.fn.loadCategory(type);
                    }
                    if (null != type && type != '' && type == 0) {
                        floatWin.fn.loadCategorys();
                    }


                });

                $('#categoryList').change(function () {
                    //$('#seriesId').val('');

                    var categoryId = $(this).val();
                    var type = $('#position').val();
                    if (type != 0) {
                        if (null != categoryId && categoryId != '' && null != type && type != '') {
                            floatWin.fn.loadSeries(categoryId, type);
                        }
                    }
                    if (type == 0) {
                        if (null != categoryId && categoryId != '') {
                            floatWin.fn.loadContent(categoryId);
                        }
                    }
                });
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
            loadCategory: function (type) {

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

                    var categoryId = $('#categoryId').val();
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

                floatWin.fn.loadSeries(sourceId, type);
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

                floatWin.fn.loadContent(sourceId);
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
                var cover = $('#imageFile').val();

                if (null == cover || cover == '') {
                    $common.fn.notify("请上传广告图片", "error");
                    flag = false;
                    return;
                }

                return flag;
            },
            save: function () {
                if (floatWin.fn.checkData()) {
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/floatWin/save",
                        type: "POST",
                        data: {
                            "categoryId": $('#categoryList option:selected').val(),
                            "seriesId": $('#seriesList option:selected').val()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/floatWin/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            banner: function (id, status) {
                if (status == 0) {
                    $('#showText').html('确定隐藏该浮窗广告？');
                } else {
                    $('#showText').html('确定显示该浮窗广告？');
                }

                $("#delete").modal("show");
                $('#confirm').click(function () {
                    var tempStatus = 0;
                    if (status == 0) {
                        tempStatus = 1;
                    }

                    $leoman.ajax("${contextPath}/admin/floatWin/banned", {
                        "floatWinId": id,
                        "status": tempStatus
                    }, function (result) {
                        if (result == 1) {
                            $common.fn.notify("操作成功", "success");
                            $("#delete").modal("hide");
                        } else {
                            $common.fn.notify("操作失败", "error");
                        }
                    });
                });
            },
        }
    }
    $(function () {
        floatWin.fn.init();
    })
</script>
</body>
</html>

