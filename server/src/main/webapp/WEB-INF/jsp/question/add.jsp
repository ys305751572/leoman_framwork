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
        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" href="#" onclick="$user.fn.add();" title="新增题目" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <hr class="whiter m-t-20"/>
        <h1 class="page-title">题库信息</h1>
        <form id="fromId" name="formName" method="post" action="${contextPath}/admin/question/q/save" class="box tile animated active form-validation-1">
            <div class="block-area">
                <input type="hidden" id="tid" name="tid" value="${tidMap.tid}">
                <div class="row questionRow" id="question1">
                    <div class="col-md-12 m-b-15">
                        <label>题目<input type="hidden" id="questions[1].qno" name="questions[1].qno"><a href="#" onclick="$user.fn.del(1);"><i class="fa fa-times-circle" aria-hidden="true"></i></a></label>
                        <input type="text" id="questions[1].question" name="questions[1].question" value="" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>选项A</label>
                        <input type="text" id="questions[1].optiona" name="questions[1].optiona" value="" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>选项B</label>
                        <input type="text" id="questions[1].optionb" name="questions[1].optionb" value="" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>选项C</label>
                        <input type="text" id="questions[1].optionc" name="questions[1].optionc" value="" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>选项D</label>
                        <input type="text" id="questions[1].optiond" name="questions[1].optiond" value="" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="submit" class="btn btn-info btn-sm m-t-10">提交</button>
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
    $user = {
        v: {
            list: [],
            chart : null,
            dTable: null,
            index : 2
        },
        fn: {
            init: function () {
                $user.fn.initImage();
            },
            save : function() {

            },
            initImage : function() {

//                var $exists = $("#imageFile").find(".fileupload-exists");
//                var $new = $("#imageFile").find(".fileupload-new");
//
//                $exists.each(function() {
//                    $(this).addClass("fileupload-new").removeClass("fileupload-exists");
//                });
//                $new.each(function() {
//                    $(this).addClass("fileupload-exists").removeClass("fileupload-new");
//                });
            },
            add : function() {
                var index = $user.v.index;
                var html = "<div class=\"row questionRow\" id=\"question"+ index +"\">";
                html += "<div class='col-md-12 m-b-15'>";
                html += "<label>题目<a href=\"\"><input type=\"hidden\" id=\"questions["+ index +"].qno\" name=\"questions["+ index +"].qno\"><a href='#' onclick=\"$user.fn.del('" + index + "')\"><i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i></a></label>";
                html += "<input type=\"text\" id=\"questions["+ index +"].question\" name=\"questions["+ index +"].question\" value=\"\" class=\"input-sm form-control validate[required]\" placeholder=\"...\">";
                html += "</div>";
                html += "<div class=\"col-md-6 m-b-15\">";
                html += "<label>选项A</label>";
                html += "<input type=\"text\" id=\"questions["+ index +"].optiona\" name=\"questions["+ index +"].optiona\" value=\"\" class=\"input-sm form-control validate[required]\" placeholder=\"...\">";
                html += "</div>";
                html += "<div class=\"col-md-6 m-b-15\">";
                html += "<label>选项B</label>";
                html += "<input type=\"text\" id=\"questions["+ index +"].optionb\" name=\"questions["+ index +"].optionb\" value=\"\" class=\"input-sm form-control validate[required]\" placeholder=\"...\">";
                html += "</div>";
                html += "<div class=\"col-md-6 m-b-15\">";
                html += "<label>选项C</label>";
                html += "<input type=\"text\" id=\"questions["+ index +"].optionc\" name=\"questions["+ index +"].optionc\" value=\"\" class=\"input-sm form-control validate[required]\" placeholder=\"...\">";
                html += "</div>";
                html += "<div class=\"col-md-6 m-b-15\">";
                html += "<label>选项D</label>";
                html += "<input type=\"text\" id=\"questions["+ index +"].optiond\" name=\"questions["+ index +"].optiond\" value=\"\" class=\"input-sm form-control validate[required]\" placeholder=\"...\">";
                html += "</div>";
                html += "<hr class=\"whiter m-t-20\"/>";
                html += "<div>";
                $(".questionRow:last").after(html);
                $user.v.index = ++index;
            },
            del : function(_index) {
                if($(".questionRow").length == 1) {
                    $common.fn.notify("请至少保留一条数据");
                    return;
                }
                $("#question" + _index).remove();
            }
        }
    }
    $(function () {
        $user.fn.init();
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

