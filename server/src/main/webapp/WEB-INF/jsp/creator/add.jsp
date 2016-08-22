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
        <h1 class="page-title">主创编辑</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="id" name="id" value="${userInfo.id}">
                <input type="hidden" id="provinceId" value="${userInfo.creator.city.province.id}">
                <input type="hidden" id="cityId" value="${userInfo.creator.city.id}">
                <span id="tempAddImageIds1" style="display: none"></span>
                <span id="tempAddImageIds2" style="display: none"></span>
                <span id="tempDelImageIds" style="display: none"></span>
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>头像：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${userInfo.avater}">
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
                    <%--<div class="col-md-6 m-b-15">
                        <label>账号：</label>
                        <input type="text" id="mobile" name="mobile" value="${userInfo.mobile}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>--%>
                    <div class="col-md-6 m-b-15">
                        <label>昵称：</label>
                        <input type="text" id="nickname" name="nickname" value="${userInfo.nickname}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>密码：</label>
                        <input type="password" id="password" name="password" value="${userInfo.password}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>性别：</label>
                        <select id="gender" name="gender" style="width: 200px;" class="select">
                            <option value="">请选择性别</option>
                            <option value="0" <c:if test="${userInfo.gender == 0}">selected</c:if>>男</option>
                            <option value="1" <c:if test="${userInfo.gender == 1}">selected</c:if>>女</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>城市：</label>
                        <div>&nbsp;&nbsp;</div>
                        <div class="col-sm-2">
                            <select id="provinceList" name="provinceList" style="width: 300px;" class="select">
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <select id="cityList" name="cityList" style="width: 300px;" class="select"></select>
                        </div>
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>个人简介：</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control"
                                  placeholder="...">${userInfo.creator.description}</textarea>
                        <%--<input type="text" id="description" name="description" value="${userInfo.creator.description}"
                               class="input-sm form-control validate[required]" placeholder="...">--%>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>微博：</label>
                        <input type="text" id="weibo" name="weibo" value="${userInfo.creator.weibo}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>后援会：</label>
                        <input type="text" id="dipan" name="experience" value="${userInfo.creator.experience}"
                               class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>动态：</label>
                        <div>&nbsp;&nbsp;</div>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == dynamicList || dynamicList.size() == 0 }">
                                暂无动态
                            </c:if>
                            <c:if test="${null != dynamicList && dynamicList.size() > 0 }">
                                <c:forEach var="co" items="${dynamicList}">
                                    <div class="col-md-6 m-b-15" id="ul${co.id }" >
                                        <label>时间：</label>
                                        <input value="<date:date value="${co.time}" format="yyyy-MM-dd HH:mm:ss"></date:date>" name="times" class="form-control input-append date form_datetime" />&nbsp;&nbsp;
                                        <label>事件：</label>
                                        <select id="things" name="things" style="width: 150px;" class="select">
                                            <option value=''>请选择事件</option>
                                            <option value='1' <c:if test="${co.status eq 1}">selected</c:if>>正在看帖子</option>
                                            <option value='2' <c:if test="${co.status eq 2}">selected</c:if>>正在回帖子</option>
                                            <option value='3' <c:if test="${co.status eq 3}">selected</c:if>>正在发帖子</option>
                                            <option value='4' <c:if test="${co.status eq 4}">selected</c:if>>正在看小说</option>
                                            <option value='5' <c:if test="${co.status eq 5}">selected</c:if>>正在看漫画</option>
                                            <option value='6' <c:if test="${co.status eq 6}">selected</c:if>>正在看网剧</option>
                                            <option value='7' <c:if test="${co.status eq 7}">selected</c:if>>正在发弹幕</option>
                                        </select>
                                        <%--<input  value="<c:if test="${co.status eq 1}">正在看帖子</c:if><c:if test="${co.status eq 2}">正在回帖子</c:if><c:if test="${co.status eq 3}">正在发帖子</c:if><c:if test="${co.status eq 4}">正在看小说</c:if><c:if test="${co.status eq 5}">正在看漫画</c:if><c:if test="${co.status eq 6}">正在看网剧</c:if><c:if test="${co.status eq 7}">正在发弹幕</c:if>" name="things" class="input-sm form-control validate[required]" />&nbsp;&nbsp;--%>
                                        <button type="button" onclick="creator.fn.delete(${co.id});" class="btn btn-info btn-sm m-t-10">删除</button>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div class="col-md-12 m-b-15">
                            <button type="button" onclick="creator.fn.do();" class="btn btn-info btn-sm m-t-10">增加</button>
                        </div>

                        <div class="col-md-6 m-b-15" style="display: none" id="add">
                            <label>时间：</label>
                            <input type="text" class="form-control input-append date form_datetime"
                                   style="width: 300px;" id="time" name="time" maxlength="20"
                                   value="" data-rule="required" placeholder="..."/>
                            <label>事件：</label>
                            <select id="thing" name="thing" style="width: 300px;" class="form-control">
                                <option value=''>请选择事件</option>
                                <option value='1'>正在看帖子</option>
                                <option value='2'>正在回帖子</option>
                                <option value='3'>正在发帖子</option>
                                <option value='4'>正在看小说</option>
                                <option value='5'>正在看漫画</option>
                                <option value='6'>正在看网剧</option>
                                <option value='7'>正在发弹幕</option>
                            </select>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>主创获赞感谢音：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <embed src="${userInfo.creator.audioUrl}" menu="true" loop="true" play="true">
                            </div>
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">上传音乐</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="url" name="url" type="file"/>
                                </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
                        </div>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="${userInfo.creator.coverUrl}">
                            </div>
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="imageFile2" name="imageFile2" type="file"/>
                                </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-12 control-label">剧照:</label>
                        <div>&nbsp;&nbsp;</div>
                        <div class="col-sm-10">
                            <div style="float: left;margin-bottom: 30px;" id="mainImage">
                                <a href="javascript:void(0);" onclick="creator.fn.AddTempImg(0)">
                                    <img src="${contextPath}/static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-12 control-label">生活照:</label>
                        <div>&nbsp;&nbsp;</div>
                        <div class="col-sm-10">
                            <div style="float: left;margin-bottom: 30px;" id="mainImage2">
                                <a href="javascript:void(0);" onclick="creator.fn.AddTempImg(1)">
                                    <img src="${contextPath}/static/images/add.jpg" style="height: 200px; width: 200px; display: inherit; margin-bottom: 6px;" border="1"/>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div id="tempDiv" style="display:none;float: left; height: 210px;width: 200px;margin-right:6px; z-index: 0;margin-bottom: 15px;">
                        <img class="imgs" alt="" src="" style="height: 200px;width: 200px; z-index: 1;"/>
                        <input name="imageIdTemp" type="hidden"/>
                        <a href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 203px; left: 184px; display: none;" class="axx" onclick="creator.fn.deleteImage(this)">
                            <img id="pic" src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                        </a>
                    </div>
                    <hr class="whiter m-t-20"/>

                    <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="showText">删除该动态后操作无法撤销，确定删除？</h4>
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
                        <button type="button" onclick="creator.fn.save();" class="btn btn-info btn-sm m-t-10">提交</button>
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
    creator = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
                creator.fn.getSerImages();

                //自动加载下拉框
                creator.fn.getSelectList();

                //套图主图预览
                $("#mainImage").uploadPreview({
                    Img: "mainImage",
                    Width: 200,
                    Height: 170
                });

                $("#mainImage2").uploadPreview({
                    Img: "mainImage2",
                    Width: 200,
                    Height: 170
                });

                $('#provinceList').change(function () {
                    $('#cityId').val('');

                    var provinceId = $(this).val();
                    creator.fn.loadCity(provinceId);
                });

                // 渲染时间控件
                creator.fn.loadTime();
            },
            do: function () {

                /*var html = "";
                html += "<label>时间：</label>"
                html += "<input type='text' class='form-control input-append date form_datetime'id='time' name='time' maxlength='20'value='' data-rule='required' placeholder='...'/>"
                html += "<label>事件：</label>"
                html += "<select id='thing' style='width: 150px;' class='select'>"
                html += "<option value=''>请选择事件</option>"
                html += "<option value='1'>正在看帖子</option>"
                html += "<option value='2'>正在回帖子</option>"
                html += "<option value='3'>正在发帖子</option>"
                html += "<option value='4'>正在看小说</option>"
                html += "<option value='5'>正在看漫画</option>"
                html += "<option value='6'>正在看网剧</option>"
                html += "<option value='7'>正在发弹幕</option>"
                html += "</select>"

                $('#baseDiv').append(html);*/

                var tempDiv = $("#add").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "");
                tempDiv.insertBefore("#add");

                // 渲染时间控件
                creator.fn.loadTime();
                //creator.fn.getSelect();
            },
            checkData: function () {
                var flag = true;
                //var mobile1 = $('#mobile').val();
                var password1 = $('#password').val();
                var nickName1 = $('#nickName').val();
                var description1 = $('#description').val();
                var thing1 = $('#thing').val();

                /*if (null == mobile1 || mobile1 == '') {
                    $common.fn.notify("请输入账号", "error");
                    flag = false;
                    return;
                }*/

                if (null == password1 || password1 == '') {
                    $common.fn.notify("请输入密码", "error");
                    flag = false;
                    return;
                }

                /*if (null == nickName1 || nickName1 == '') {
                    $common.fn.notify("请输入昵称", "error");
                    flag = false;
                    return;
                }*/

                if (description1.length > 1000) {
                    $common.fn.notify("个人简介字数最多1000字", "error");
                    flag = false;
                    return;
                }

                if (thing1.length > 50) {
                    $common.fn.notify("事件字数最多50字", "error");
                    flag = false;
                    return;
                }
                return flag;
            },
            save: function () {
                if (creator.fn.checkData()) {

                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/creator/save",
                        type: "POST",
                        data: {
                            "provinceId": $('#provinceList option:selected').val(),
                            "cityId": $('#cityList option:selected').val(),
                            "tempAddImageIds1": $("#tempAddImageIds1").html(),
                            "tempAddImageIds2": $("#tempAddImageIds2").html(),
                            "tempDelImageIds": $("#tempDelImageIds").html()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/creator/index";
                            } else if (result == 2){
                                $common.fn.notify("昵称已存在，请重新输入", "error");
                            }else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            getSelectList: function () {
                var provinceId = $('#provinceId').val();

                $leoman.ajax("${contextPath}/admin/creator/provinceList", null, function (result) {
                    if (null != result) {
                        $('#provinceList').empty();
                        // 获取返回的省份列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择所在省份</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#provinceList').append(content);
                        $('#provinceList').selectpicker('refresh');
                    } else {
                        $common.notify("获取省份信息失败", "error");
                    }

                    if (null != provinceId && provinceId != '') {
                        $('#provinceList').val(provinceId);
                        $('#provinceList').selectpicker('refresh');
                    }
                });

                var sourceId = 0;
                if (null == provinceId || provinceId == '') {
                    sourceId = $('#provinceList option:selected').val();
                } else {
                    sourceId = provinceId;
                }

                creator.fn.loadCity(sourceId);
            },
            /*getSelect: function () {

                $('#thing').empty();
                // 获取返回的主创列表信息，并循环绑定到select中
                var content = "<option value=''>请选择事件</option>";

                content += "<option value='1'>正在看帖子</option>";
                content += "<option value='2'>正在回帖子</option>";
                content += "<option value='3'>正在发帖子</option>";
                content += "<option value='4'>正在看小说</option>";
                content += "<option value='5'>正在看漫画</option>";
                content += "<option value='6'>正在看网剧</option>";
                content += "<option value='7'>正在发弹幕</option>";

                $('#thing').append(content);
                $('#thing').selectpicker('refresh');

            },*/
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
            loadCity: function (sourceId) {
                $('#cityList').html('');

                $leoman.ajax("${contextPath}/admin/creator/cityList", {
                    provinceId: sourceId
                }, function (result) {
                    if (null != result) {
                        $("#cityList").empty();
                        // 获取返回的城市列表信息，并循环绑定到select中
                        var content = "<option value=''>请选择所在城市</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#cityList').append(content);
                        $("#cityList").selectpicker('refresh');
                    } else {
                        $sixmac.notify("获取城市信息失败", "error");
                    }

                    var cityId = $('#cityId').val();
                    if (null != cityId && cityId != '') {
                        $('#cityList').val(cityId);
                        $("#cityList").selectpicker('refresh');
                    }
                });
            },
            getSerImages: function () {
                var imgList = ${creatorImageList };

                $.each(imgList, function (i, item) {
                    if (null != item) {
                        switch (Number(item.type)) {
                            case 0:
                                creator.fn.insertImage(item.path, item.id);
                                break;
                            case 1:
                                creator.fn.insertImage2(item.path, item.id);
                                break;
                        }
                    } else {
                        $('#mainImage').html('暂无');
                        $('#mainImage2').html('暂无');
                    }
                });

            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#mainImage");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    creator.fn.mouseover($(this));
                }, function () {
                    creator.fn.mouseOut($(this));
                });
            },
            insertImage2: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#mainImage2");

                // 让所有的克隆出来的
                tempDiv.hover(function () {
                    creator.fn.mouseover($(this));
                }, function () {
                    creator.fn.mouseOut($(this));
                });
            },
            mouseover: function (mouse) {
                $(mouse).children("a").fadeIn(300);
            },
            mouseOut: function (mouse) {
                $(mouse).children("a").fadeOut(300);
            },
            AddTempImg: function (type) {
                // a标签绑定onclick事件
                $('#tempImage').click();
                $('#tempImageType').val(type);
            },
            saveTempImage: function () {
                $("#tempImageForm").ajaxSubmit({
                    dataType: "json",
                    success: function (data) {
                        if (null != data.image && data.image != '') {
                            if (data.type == 0) {
                                $('#tempAddImageIds1').html($('#tempAddImageIds1').html() + data.id + ',');
                                creator.fn.insertImage(data.image, data.id);
                            } else {
                                $('#tempAddImageIds2').html($('#tempAddImageIds2').html() + data.id + ',');
                                creator.fn.insertImage2(data.image, data.id);
                            }
                        } else {
                            $common.notify("图片格式不正确", "error");
                        }
                    }
                });
            },
            deleteImage: function (self) {
                //creator.v.imageSize = creator.v.imageSize - 1;
                var imageId = $(self).prev().val();
                $('#tempDelImageIds').html($('#tempDelImageIds').html() + imageId + ',');
                $(self).parent().remove();
            },
            delete: function (id) {
                $("#delete").modal("show");
                $('#confirm').click(function () {
                    $("#ul" + id).remove();
                    $leoman.ajax("${contextPath}/admin/creator/delete", {
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
        }
    }
    $(function () {
        creator.fn.init();
    })
</script>
</body>
</html>

