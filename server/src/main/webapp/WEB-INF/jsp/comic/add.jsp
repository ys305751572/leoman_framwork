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
        <h1 class="page-title">漫画编辑</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${work.id}">
                <input type="hidden" id="typeId" value="${work.category.id}">
                <span style="display: none" id="tempCreatorIds">${tempCreatorIds}</span>
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${work.cover}">
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
                        <label>漫画名称：</label>
                        <input type="text" id="name" name="name" value="${work.name}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>总回数：</label>
                        <input type="text" id="seriesCount" name="seriesCount" value="${work.seriesCount}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>漫画分类：</label>
                        <select id="typeList" style="width: 200px;" class="select"></select>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px">
                        <label>选择主创人员：</label>
                        <select  id="creatorList" onchange="comic.fn.addCreator();" class="select"></select>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <c:if test="${null == workCreatorList || workCreatorList.size() == 0 }">
                            暂无主创人员
                        </c:if>
                        <c:if test="${null != workCreatorList && workCreatorList.size() > 0 }">
                            <c:forEach var="co" items="${workCreatorList}">
                                <div id="ul${co.id }" class="col-md-1 m-b-15">
                                    <input type="text" value="${co.userInfo.nickname}" class="input-sm form-control validate[required]"
                                           name="creators" data-rule="required" placeholder="..." disabled/>
                                    <a id="a" href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 108px;" class="axx" onclick="comic.fn.delete(${co.id}, ${co.userInfo.id})">
                                        <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                    </a>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="col-md-1 m-b-15" style="display: none" id="addCreator">
                        <input type="text" class="input-sm form-control validate[required]"
                               id="creator" name="creator" data-rule="required" placeholder="..."/>
                        <button type="button" onclick="comic.fn.deleteCreator(this)" class="btn btn-info btn-sm m-t-10">删除</button>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>漫画简介：</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control"
                                  placeholder="...">${work.description}</textarea>
                    </div>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>周边购买推荐：</label>
                        <div>&nbsp;&nbsp;</div>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == workSurroundList || workSurroundList.size() == 0 }">
                                暂无周边购买
                            </c:if>
                            <c:if test="${null != workSurroundList && workSurroundList.size() > 0 }">
                                <c:forEach var="co" items="${workSurroundList}">
                                    <div id="ul${co.id }" class="col-md-6 m-b-15">
                                        <label>名称：</label>
                                        <input type="text" value="${co.name}" name="names" class="input-sm form-control validate[required]" placeholder="..."/>&nbsp;&nbsp;
                                        <label>商品描述：</label>
                                        <input type="text" name="descriptions" class="input-sm form-control validate[required]" placeholder="..." value="${co.description}"/>&nbsp;&nbsp;
                                        <label>购买链接：</label>
                                        <input type="text" name="linkUrls" class="input-sm form-control validate[required]" placeholder="..." value="${co.linkUrl}"/>&nbsp;&nbsp;
                                        <label>图片：</label>
                                        <div class="fileupload fileupload-new" data-provides="fileupload">
                                            <div>
                                                <div class="fileupload-preview thumbnail form-control">
                                                    <img src="${co.cover}">
                                                </div>
                                                <span class="btn btn-file btn-alt btn-sm">
                                                    <span class="fileupload-new">选择图片</span>
                                                    <span class="fileupload-exists">更改</span>
                                                    <input id="covers" name="covers${co.id}" type="file"/>
                                                </span>
                                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                                            </div>
                                        </div>
                                        <button type="button" onclick="comic.fn.deletes(${co.id});" class="btn btn-info btn-sm m-t-10">删除</button>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div class="col-md-12 m-b-15">
                            <button type="button" onclick="comic.fn.do();" class="btn btn-info btn-sm m-t-10">增加</button>
                        </div>

                        <div class="col-md-6 m-b-15" style="display: none" id="add">
                            <label>名称：</label>
                            <input type="text" class="form-control input-append date form_datetime"
                                   id="name2" name="name2" maxlength="20"
                                   value="" data-rule="required" placeholder="..."/>
                            <label>商品描述：</label>
                            <input type="text" id="description2" name="description2" value=""
                                   class="input-sm form-control validate[required]" placeholder="...">
                            <label>购买链接：</label>
                            <input type="text" id="linkUrl" name="linkUrl" value=""
                                   class="input-sm form-control validate[required]" placeholder="...">
                            <label>图片：</label>
                            <div class="fileupload fileupload-new" data-provides="fileupload">
                                <div>
                                    <div class="fileupload-preview thumbnail form-control">
                                        <img src="${workSurround.cover}">
                                    </div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="cover" name="cover" type="file"/>
                                </span>
                                    <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>

                    <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                        <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                        <input name="imageIdTemp" type="hidden"/>
                        <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="product.fn.deleteImage(this)">
                            <img id="pic" src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                        </a>
                    </div>

                    <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="showText">删除该周边购买后操作无法撤销，确定删除？</h4>
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
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="comic.fn.save();">提交</button>
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
    comic = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                //自动加载下拉框
                comic.fn.getSelectList();

                //自动加载下拉框
                comic.fn.getSelectLists();

            },
            do: function () {
                var tempDiv = $("#add").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "");
                tempDiv.insertBefore("#add");

            },
            addCreator: function () {

                var selectVal = $('#creatorList option:selected').val();
                var selectText = $('#creatorList option:selected').text();
                var creatorIds = $('#tempCreatorIds').html();

                // 首先判断当前是否已经添加了主创人员信息，如果已经添加，则跳过
                // 否则，将当前选中的主创人员信息添加到缓存span中
                var tempArray = creatorIds.split(',');
                if (tempArray.indexOf(selectVal) == -1) {
                    $('#tempCreatorIds').html(creatorIds + selectVal + ',');

                    var tempDiv = $("#addCreator").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "block");
                    tempDiv.children(":first").prop("value", selectText);
                    tempDiv.insertBefore("#addCreator");
                }
            },
            checkData: function () {
                var flag = true;
                var name = $('#name').val();
                var seriesCount = $('#seriesCount').val();
                var category = $('#typeList').val();
                var description = $('#description').val();
                /*var name2 = $('#name2').val();
                var description2 = $('#description2').val();
                var linkUrl = $('#linkUrl').val();
                var cover = $('#cover').val();*/

                if (null == name || name == '') {
                    $common.fn.notify("请输入漫画名称", "error");
                    flag = false;
                    return;
                }

                if (name.length > 20) {
                    $common.fn.notify("漫画名称字数最多20字", "error");
                    flag = false;
                    return;
                }

                if (null == seriesCount || seriesCount == '') {
                    $common.fn.notify("请输入漫画总回数", "error");
                    flag = false;
                    return;
                }

                if (isNaN(seriesCount)) {
                    $common.fn.notify("漫画总回数必须为数字", "error");
                    flag = false;
                    return;
                }

                if (null == category || category == '') {
                    $common.fn.notify("请输入漫画分类", "error");
                    flag = false;
                    return;
                }

                if (null == description || description == '') {
                    $common.fn.notify("请输入漫画简介", "error");
                    flag = false;
                    return;
                }

                if (description.length > 1000) {
                    $common.fn.notify("漫画简介字数最多1000字", "error");
                    flag = false;
                    return;
                }

               /* if (null == name2 || name2 == '') {
                    $common.fn.notify("请输入周边购买商品名字", "error");
                    flag = false;
                    return;
                }

                if (null == name2 || name2 == '') {
                    $common.fn.notify("请输入周边购买商品描述", "error");
                    flag = false;
                    return;
                }

                if (null == name2 || name2 == '') {
                    $common.fn.notify("请输入周边购买链接", "error");
                    flag = false;
                    return;
                }*/
                return flag;
            },
            save: function () {
                if (comic.fn.checkData()) {

                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/comic/save",
                        type: "POST",
                        data: {
                            "creatorId": $('#creatorList option:selected').val(),
                            "category": $('#typeList option:selected').val(),
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/comic/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            getSelectList: function () {
                var creatorId = $('#creatorId').val();

                $leoman.ajax("${contextPath}/admin/comic/creatorList", null, function (result) {
                    if (null != result) {
                        $('#creatorList').empty();
                        // 获取返回的主创列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择所在主创</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.nickname + "</option>";
                        });
                        $('#creatorList').append(content);
                        $('#creatorList').selectpicker('refresh');
                    } else {
                        $common.notify("获取主创信息失败", "error");
                    }

                    if (null != creatorId && creatorId != '') {
                        $('#creatorList').val(creatorId);
                        $('#creatorList').selectpicker('refresh');
                    }
                });

            },
            getSelectLists: function () {
                var typeId = $('#typeId').val();

                $leoman.ajax("${contextPath}/admin/comic/typeList", null, function (result) {
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

                    if (null != typeId && typeId != '') {
                        $('#typeList').val(typeId);
                        $('#typeList').selectpicker('refresh');
                    }
                });
            },
            deletes: function (id) {
                $("#delete").modal("show");
                $('#confirm').click(function () {
                    $("#ul" + id).remove();
                    $leoman.ajax("${contextPath}/admin/work/deleteSurround", {
                        "id" : id
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
            "delete": function (id, creatorId) {
                $("#ul" + id).remove();
                $leoman.ajax("${contextPath}/admin/work/deleteCreator", {
                    "workId":id
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        work.v.dTable.ajax.reload();
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });
                $('#tempCreatorIds').html().replace(creatorId + ',', "");
            },
            deleteCreator: function (self) {
                $(self).parent().remove();
            },
        }
    }
    $(function () {
        comic.fn.init();
    })
</script>
</body>
</html>

