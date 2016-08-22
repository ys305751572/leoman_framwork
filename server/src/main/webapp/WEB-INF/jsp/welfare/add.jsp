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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="clearfix"></div>
<section id="main" class="p-relative" role="main">
    <%@ include file="../inc/new/menu.jsp" %>
    <section id="content" class="container">
        <!-- Breadcrumb -->
        <ol class="breadcrumb hidden-xs">
            <li><a href="javascript:history.go(-1);" title="返回"><span class="icon">&#61771;</span></a></li>
        </ol>
        <h1 class="page-title">添加福利</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data">
            <div class="block-area">
                <div class="row">
                    <div class="col-md-12 m-b-15">
                        <label>封面：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail form-control">
                                <img src="">
                            </div>
                            <div>
                                <span class="btn btn-file btn-alt btn-sm">
                                    <span class="fileupload-new">选择图片</span>
                                    <span class="fileupload-exists">更改</span>
                                    <input id="cover" name="cover" type="file"/>
                                </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>福利标题：</label>
                        <input type="text" id="title" name="title" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>福利小标题：</label>
                        <input type="text" id="subtitle" name="subtitle" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-3 m-b-15">
                        <label>类型：</label>
                        <select id="type" name="type" class="select" onchange="welfare.fn.change();">
                            <option value="">全部</option>
                            <option value="0">铃声</option>
                            <option value="1">经验值</option>
                            <option value="2">实物</option>
                            <option value="3">表情包</option>
                            <option value="4">商城购买</option>
                            <option value="5">游戏兑换码</option>
                        </select>
                    </div>

                    <div class="col-md-12" id="baseDiv"></div>
                </div>
                <hr class="whiter m-t-20"/>

                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" onclick="welfare.fn.save();" class="btn btn-info btn-sm m-t-10">提交
                        </button>
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>

                <div class="col-md-12 m-b-15" style="display: none;margin-top: 10px;" id="music">
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" class="form-control  validate[required]" id="coin" name="coin" maxlength="20" placeholder="..."/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>铃声时长：</label>
                        <input type="text" id="length" name="length" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px">
                        <label>上传音乐：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
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
                </div>

                <div class="col-md-12 m-b-15" style="display: none" id="exper">
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" class="form-control validate[required]" id="coin1" name="coin1" maxlength="20" placeholder="..."/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>经验数：</label>
                        <input type="text" id="experience" name="experience" value="" class="input-sm form-control validate[required]" placeholder="...">
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>描述：</label>
                        <textarea cols="40" rows="6" id="description1" name="description1" class="form-control" placeholder="..."></textarea>
                    </div>
                </div>

                <div class="col-md-12 m-b-15" style="display: none" id="entity">
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" class="form-control validate[required]" id="coin2" name="coin2" maxlength="20" placeholder="..."/>
                    </div>
                    <div class="col-md-6 m-b-15" id="a">
                        <label>限制：</label>
                        <select id="limit2" name="limit2" class="form-control">
                            <option value="0">不限次数</option>
                            <option value="1">每天仅一次</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>描述：</label>
                        <textarea cols="40" rows="6" id="description2" name="description2" class="form-control" placeholder="..."></textarea>
                    </div>
                </div>

                <div class="col-md-12 m-b-15" style="display: none" id="emotion">
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" class="form-control validate[required]" id="coin3" name="coin3" maxlength="20" placeholder="..."/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>限制：</label>
                        <select id="limit3" name="limit3" class="form-control">
                            <option value="1">一级及以上用户</option>
                            <option value="2">二级及以上用户</option>
                            <option value="3">三级及以上用户</option>
                            <option value="4">四级及以上用户</option>
                            <option value="5">五级及以上用户</option>
                            <option value="6">六级及以上用户</option>
                            <option value="7">七级及以上用户</option>
                            <option value="8">八级及以上用户</option>
                            <option value="9">九级及以上用户</option>
                            <option value="10">十级及以上用户</option>
                            <option value="11">十一级及以上用户</option>
                            <option value="12">十二级及以上用户</option>
                            <option value="13">十三级及以上用户</option>
                            <option value="14">十四级及以上用户</option>
                            <option value="15">十五级及以上用户</option>
                            <option value="16">十六级及以上用户</option>
                            <option value="17">十七级及以上用户</option>
                            <option value="18">十八级及以上用户</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>描述：</label>
                        <textarea cols="40" rows="6" id="description3" name="description3" class="form-control" placeholder="..."></textarea>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>上传表情包：</label>
                        <div class="fileupload fileupload-new" data-provides="fileupload">
                            <div>
                                        <span class="btn btn-file btn-alt btn-sm">
                                            <span class="fileupload-new">上传表情包</span>
                                            <span class="fileupload-exists">更改</span>
                                            <input id="url3" name="url3" type="file"/>
                                        </span>
                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">移除</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12 m-b-15" style="display: none" id="store">
                    <div class="col-md-6 m-b-15">
                        <label>购买链接：</label>
                        <input type="text" class="form-control validate[required]" id="url4" name="url4" maxlength="20" placeholder="..."/>
                    </div>
                </div>

                <div class="col-md-12 m-b-15" style="display: none" id="game">
                    <div class="col-md-6 m-b-15">
                        <label>馒头数：</label>
                        <input type="text" class="form-control validate[required]" id="coin5" name="coin5" maxlength="20" placeholder="..."/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>限制：</label>
                        <select id="limit5" name="limit5" class="form-control">
                            <option value="0">不限次数</option>
                            <option value="1">每天仅一次</option>
                        </select>
                    </div>
                    <div class="col-md-12 m-b-15">
                        <label>请输入游戏码：</label>
                        <input type="text" class="form-control validate[required]" id="url5" name="url5" maxlength="20" placeholder="..."/>
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
    welfare = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {

            },
            change: function () {
                var val = $("#type").val();
                console.log(val);
                if (val == 0) {

                    $('#baseDiv').html('');
                    var tempDiv = $("#music").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "");
                    // tempDiv.insertBefore("#music");
                    $('#baseDiv').append(tempDiv);
                } else if (val == 1) {

                    $('#baseDiv').html('');
                    var tempDiv = $("#exper").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "");
                    // tempDiv.insertBefore("#exper");
                    $('#baseDiv').append(tempDiv);
                } else if (val == 2) {

                    $('#baseDiv').html('');
                    var tempDiv = $("#entity").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "");
                    // tempDiv.insertBefore("#entity");
                    $('#baseDiv').append(tempDiv);

                } else if (val == 3) {

                    $('#baseDiv').html('');
                    var tempDiv = $("#emotion").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "");
                    // tempDiv.insertBefore("#emotion");
                    $('#baseDiv').append(tempDiv);
                } else if (val == 4) {

                    $('#baseDiv').html('');
                    var tempDiv = $("#store").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "");
                    // tempDiv.insertBefore("#store");
                    $('#baseDiv').append(tempDiv);
                } else if (val == 5) {

                    $('#baseDiv').html('');
                    var tempDiv = $("#game").clone();
                    tempDiv.prop('id', '');
                    tempDiv.css("display", "");
                    // tempDiv.insertBefore("#game");
                    $('#baseDiv').append(tempDiv);
                }
            },
            checkData: function () {
                var flag = true;
                var title = $('#title').val();
                var subtitle = $('#subtitle').val();
                var type = $('#type').val();

                if (null == title || title == '') {
                    $common.fn.notify("请输入福利标题", "error");
                    flag = false;
                    return;
                }

                if (null == subtitle || subtitle == '') {
                    $common.fn.notify("请输入福利小标题", "error");
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
                if (welfare.fn.checkData()) {
                    $("#fromId").ajaxSubmit({

                        url: "${contextPath}/admin/welfare/save",
                        type: "POST",
                        data: {},
                        success: function (result) {
                            if (result == 1) {
                                $common.fn.notify("操作成功", "success");
                                window.location.href = "${contextPath}/admin/welfare/index";
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
        welfare.fn.init();
    })
</script>
</body>
</html>

