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
        <h1 class="page-title">小说编辑</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="messageId" name="messageId" value="${message.id}"/>
                <input type="hidden" id="tempType" value="${message.toObject}"/>
                <%--<form id="productForm" method="post" action="backend/message/save"
                      class="form-horizontal nice-validator n-default" role="form" novalidate="novalidate">--%>
                <div class="col-md-6 m-b-15">
                    <label>消息名称:</label>
                    <input type="text"id="title" name="title" value="${message.title}"
                           class="input-sm form-control validate[required]" placeholder="..."/>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                    <label class="col-md-1 ">发送对象:</label>
                    <%--<div class="col-sm-4">--%>
                        <div class="col-md-1">
                            <input type="checkbox" name="sendto"
                                   id="sendto_alls" value="0"/> <label for="sendto_alls">全部</label>
                        </div>
                        <div class="col-md-1">
                            <input type="checkbox" name="sendto"
                                   onclick="message.fn.checkChkBox(this)" id="sendto_users"
                                   value="1"/> <label for="sendto_users">普通会员</label>
                        </div>
                        <div class="col-md-1">
                            <input type="checkbox" name="sendto"
                                   <%--style="vertical-align: middle; margin-top: 0px;"--%>
                                   onclick="message.fn.checkChkBox(this)" id="sendto_creator"
                                   value="2"/> <label for="sendto_creator">主创会员</label>
                        </div>
                    <%--</div>--%>
                </div>

                <hr class="whiter m-t-20"/>
                <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                    <label>消息详情：</label>
                    <div class="wysiwye-editor">${message.content}</div>
                </div>
                <hr class="whiter m-t-20"/>
                <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                    <label>发送时间：</label>
                    <input type="text" class="form-control input-append date form_datetime"
                           style="width: 180px;" id="subTime" name="subTime" maxlength="20"
                           value="<date:date value="${message.sendDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>" data-rule="required" placeholder="..."/>
                </div>
                <hr class="whiter m-t-20"/>
            </div>
        </form>

        <div class="form-group">
            <div  class="col-md-offset-5">
                <button type="button" class="btn btn-info btn-sm m-t-10" onclick="message.fn.subInfo()">提交</button>
                <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
            </div>
        </div>
    </section>
    <!-- /#page-wrapper -->
</section>

<%@ include file="../inc/new/foot.jsp" %>
<script type="text/javascript">
    var message = {
        v: {
            id: "message",
            list: [],
            dTable: null,
            mainImageStatus: 0,
            imageSize: 0,
            types: ""
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                if ($("#productId").val() != "") {
                    $("#showH").text("——编辑消息");
                }

                $('#sendto_alls').click(function () {
                    if (this.checked) {
                        $(":checkbox").each(function () {
                            this.checked = true;
                        });
                    } else {
                        $(":checkbox").each(function () {
                            this.checked = false;
                        });
                    }
                });

                message.fn.loadData();

                // 渲染时间控件
                message.fn.loadTime();
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
            loadData: function () {
                // 界面加载时，选中复选框
                var messageId = $('#messageId').val();
                if (null != messageId && messageId != '') {
                    // 回选复选框
                    var tempType = $('#tempType').val();
                    var array = tempType.split(',');
                    for (var i = 0; i < array.length; i++) {
                        $('input:checkbox[name="sendto"]').each(function () {
                            if ($(this).val() == array[i]) {
                                $(this).parent().addClass("checked");
                                //$(this).prop("check", true);
                            }
                        });
                    }
                }
            },
            checkData: function () {
                var flag = true;
                var all = $('#sendto_alls').val();
                var user = $('#sendto_users').val();
                var creator = $('#sendto_creator').val();
                var title = $('#title').val();
                var content = $('.wysiwye-editor').code();
                var time = $('#subTime').val();

                if (null == title || title == '') {
                    $common.notify('消息名称不能为空', "error");
                    flag = false;
                    return;
                }

                /*$('input:checkbox[name="sendto"]').each(function () {
                    if ($(this).parent.classes("checked")) {
                        result = true;
                        return;
                    }
                });*/

                if (all == null || all == '' && user == null || user == '' && creator == null || creator == '') {
                    $common.notify('发送对象不能为空', "error");
                    return;
                }

                if (null == content || content == '') {
                    $common.notify('消息详情不能为空', "error");
                    flag = false;
                    return;
                }

                if (null == title || title == '') {
                    $common.notify('消息名称不能为空', "error");
                    flag = false;
                    return;
                }

                if (null == time || time == '') {
                    $common.notify('消息发送时间不能为空', "error");
                    flag = false;
                    return;
                }

                // 到此处的时候，说明前面的校验都通过，此时保存需要提交的复选框的值
                /*$('input:checkbox[name="sendto"]:checked').each(function () {
                    console.log($(this).val());
                    message.v.types += $(this).val() + ',';
                });*/
                $('.checked').each(function () {
                    console.log($(this).val());
                    message.v.types += $(this).children().val() + ',';
                });

                return flag;
            },
            subInfo: function () {
                // 所有的验证通过后，执行新增操作
                if (message.fn.checkData()) {
                    $("#fromId").ajaxSubmit({
                        url: "${contextPath}/admin/message/save",
                        type: "POST",
                        data: {
                            "id": $('#messageId').val(),
                            "types": message.v.types,
                            "content": $('.wysiwye-editor').code(),
                            "time": $('#subTime').val()
                        },
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/message/index";
                            } else {
                                $common.fn.notify("操作失败", "error");
                            }
                        }
                    });
                }
            },
            checkChkBox: function (data) {
                if (data.checked == false) {
                    document.getElementsByName('sendto')[0].checked = false;
                }
                var arrChk = document.getElementsByName('sendto');
                var flag = true;
                for (var i = 0; i < arrChk.length; i++) {
                    if (i == 0) {
                        continue;
                    } else {
                        if (arrChk[i].checked == false) {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    document.getElementsByName('sendto')[0].checked = flag;
                }
            },
        }
    }

    $(document).ready(function () {
        message.fn.init();
    });

</script>

</html>