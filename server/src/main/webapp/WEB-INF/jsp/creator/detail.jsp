<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags" prefix="date" %>
<%@ taglib prefix="margin-top" uri="http://www.springframework.org/tags/form" %>
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

    <link rel="stylesheet" href="${contextPath}/html/audio/css/not.the.skin.css">
    <link rel="stylesheet" href="${contextPath}/html/audio/circle.skin/circle.player.css">



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
        <h1 class="page-title">主创详情</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <h2 class="tile-title">基本信息</h2>
                <div class="col-lg-12" style="float: left">
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>头像:</label>
                        <img class="imgs" alt="" src="${userInfo.avater}" style="height: 200px;width: 300px; z-index: 1;"/>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>账号:</label>
                        <input type="text" id="mobile" value="<c:if test="${userInfo.mobile == '' || userInfo.mobile == null}">暂无</c:if><c:if test="${userInfo.mobile != null}">${userInfo.mobile}</c:if>" name="mobile" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px;">
                        <label>昵称:</label>
                        <input type="text" id="nickname" value=" ${userInfo.nickname}" name="nickname" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>性别:</label>
                        <input type="text" id="gender" name="gender" value="<c:if test="${userInfo.gender eq 0}">男</c:if><c:if test="${userInfo.gender eq 1}">女</c:if>" style="text-align: left" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>城市:</label>
                        <input type="text" id="city" value=" ${userInfo.creator.city.name}" name="city" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>状态:</label>
                        <input type="text" id="status" name="status" style="text-align: left" value="<c:if test="${userInfo.status eq 0}">正常</c:if><c:if test="${userInfo.status eq 1}">封禁</c:if>" class="input-sm form-control validate[required]" placeholder="..." disabled/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>主册时间:</label>
                        <input type="text" id="createDate" name="createDate" class="input-sm form-control validate[required]" placeholder="..." disabled value="<date:date value="${userInfo.createDate}" format="yyyy-MM-dd HH:mm:ss"></date:date>"/>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>会员类型:</label>
                        <input type="text" id="isCreator" value="<c:if test="${userInfo.isCreator eq 0}">普通会员</c:if><c:if test="${userInfo.isCreator eq 1}">主创</c:if>" name="isCreator" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>会员等级:</label>
                        <input type="text" id="level" value="<c:if test="${userInfo.level eq 0}">非会员</c:if><c:if test="${userInfo.level ne 0}">Lv${userInfo.level}</c:if>" name="level" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>粉丝数:</label>
                        <input type="text" id="funs" value="${userInfo.funs}" name="funs" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>关注数:</label>
                        <input type="text" id="focus" value="${userInfo.focus}" name="focus" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>发帖数:</label>
                        <input type="text" id="posts" value="${userInfo.posts}" name="posts" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>馒头数:</label>
                        <input type="text" id="praises" value="${userInfo.coin}" name="praises" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>作品数:</label>
                        <c:if test="${null == workCreatorList || workCreatorList.size() == 0 }">
                            暂无作品
                        </c:if>
                        <c:if test="${null != workCreatorList && workCreatorList.size() > 0 }">
                            <span style="color:white;">${workCreatorList.size()}</span>&nbsp;&nbsp;
                            (<c:forEach var="co" items="${workCreatorList}">
                            <span style="color:white;"><c:if test="${co.work.category.type eq 4}">视频</c:if><c:if test="${co.work.category.type eq 5}">小说</c:if><c:if test="${co.work.category.type eq 6}">漫画</c:if></span>：
                            <span style="color:white;">${co.work.name}</span>;&nbsp;&nbsp;
                        </c:forEach>)
                        </c:if>
                        <%--<input type="text" id="works" name="works" class="input-sm form-control validate[required]" placeholder="..." disabled>--%>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>收货地址：</label>
                        <c:if test="${null == addressList || addressList.size() == 0 }">
                            暂无收货地址
                        </c:if>
                        <c:if test="${null != addressList && addressList.size() > 0 }">
                            <c:forEach var="co" items="${addressList}">
                                <ul id="ul" + ${co.id }>
                                    <span style="color:white;">${co.name}</span>&nbsp;&nbsp;
                                    <span style="color:white;">${co.mobile}</span>&nbsp;&nbsp;
                                    <span style="color:white;">${co.address}</span>&nbsp;&nbsp;
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>个人简介:</label>
                        <textarea cols="40" rows="6" id="description" name="description" class="form-control" placeholder="...">${userInfo.creator.description}</textarea>
                    </div>
                    <div class="col-md-6 m-b-15" style="margin-top: 10px">
                        <label>微博:</label>
                        <input type="text" id="weibo" value=" ${userInfo.creator.weibo}" name="weibo" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <div class="col-md-6 m-b-15">
                        <label>后援会:</label>
                        <input type="text" id="experience" name="gender" value="${userInfo.creator.experience}" class="input-sm form-control validate[required]" placeholder="..." disabled>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>今日动态：</label>
                        <c:if test="${null == dynamicList || dynamicList.size() == 0 }">
                            暂无动态
                        </c:if>
                        <c:if test="${null != dynamicList && dynamicList.size() > 0 }">
                            <c:forEach var="co" items="${dynamicList}">
                                <ul id="ul" + ${co.id }>
                                    <span style="color:white;"><date:date value="${co.time}" format="yyyy-MM-dd HH:mm:ss"></date:date></span>&nbsp;&nbsp;
                                    <span style="color:white;"><c:if test="${co.status eq 1}">正在看帖子</c:if><c:if test="${co.status eq 2}">正在回帖子</c:if><c:if test="${co.status eq 3}">正在发帖子</c:if><c:if test="${co.status eq 4}">正在看小说</c:if><c:if test="${co.status eq 5}">正在看漫画</c:if><c:if test="${co.status eq 6}">正在看网剧</c:if><c:if test="${co.status eq 7}">正在发弹幕</c:if></span>&nbsp;&nbsp;
                                    <%--<span style="color:white;">${co.thing}</span>&nbsp;&nbsp;--%>
                                </ul>
                            </c:forEach>
                        </c:if>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>主创获赞感谢音：</label>
                        <!-- The jPlayer div must not be hidden. Keep it at the root of the body element to avoid any such problems. -->
                        <div id="jquery_jplayer_1" class="cp-jplayer"></div>
                        <!-- This is the 2nd instance's jPlayer div -->
                        <%--<div class="prototype-wrapper">--%>
                        <!-- A wrapper to emulate use in a webpage and center align -->
                            <!-- The container for the interface can go where you want to display it. Show and hide it as you need. -->
                            <div id="cp_container_1" >
                                <%--<div class="cp-buffer-holder"> <!-- .cp-gt50 only needed when buffer is > than 50% -->
                                    <div class="cp-buffer-1"></div>
                                    <div class="cp-buffer-2"></div>
                                </div>
                                <div class="cp-progress-holder"> <!-- .cp-gt50 only needed when progress is > than 50% -->
                                    <div class="cp-progress-1"></div>
                                    <div class="cp-progress-2"></div>
                                </div>--%>
                                <ul class="cp-controls">
                                    <li><a href="#" class="cp-play" tabindex="1">play</a></li>
                                    <li><a href="#" class="cp-pause" style="display:none;" tabindex="1">pause</a></li> <!-- Needs the inline style here, or jQuery.show() uses display:inline instead of display:block -->
                                </ul>
                            </div>
                            <!-- This is the 2nd instance HTML -->
                        </div>
                    </div>

                    <%--<hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>主创获赞感谢音：</label>
                        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="150" height="20"
                                codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
                            <param name="movie" value="singlemp3player.swf?showDownload=false"/>
                            <param name="wmode" value="transparent"/>
                            <embed wmode="transparent" width="150" height="20" src="singlemp3player.swf?showDownload=false"
                                   type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" hidden="no"/>
                        </object>
                    </div>--%>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>封面:</label>
                        <div>&nbsp;&nbsp;</div>
                        <img class="imgs" alt="" src="${userInfo.creator.coverUrl}" style="height: 200px;width: 300px; z-index: 1;"/>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>剧照:</label>
                        <div>&nbsp;&nbsp;</div>
                        <div id="still" value="" name="still"></div>
                    </div>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px;">
                        <label>生活照:</label>
                        <div>&nbsp;&nbsp;</div>
                        <div id="lifePhotos" value="" name="lifePhotos"></div>
                    </div>
                    <div id="tempDiv" style="display:none;float: left; height: 200px;width: 300px;margin-right:6px; z-index: 0;margin-bottom: 50px;">
                        <img class="imgs" alt="" src="" style="height: 200px;width: 300px; z-index: 1;"/>
                        <input name="imageIdTemp" type="hidden"/>
                    </div>
                    <hr class="whiter m-t-20"/>
                </div>
                <div class="col-md-12 m-b-15">
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
<script type="text/javascript" src="${contextPath}/html/audio/js/jquery.transform.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/jquery.grab.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/jquery.jplayer.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/mod.csstransforms.min.js"></script>
<script type="text/javascript" src="${contextPath}/html/audio/js/circle.player.js"></script>
<script>
    var creator = {
        v: {
            id: "post",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                $.ajaxSetup({
                    async: false
                });

                // 加载图片数组
                creator.fn.getSerImages();

                /*
                 * Instance CirclePlayer inside jQuery doc ready
                 *
                 * CirclePlayer(jPlayerSelector, media, options)
                 *   jPlayerSelector: String - The css selector of the jPlayer div.
                 *   media: Object - The media object used in jPlayer("setMedia",media).
                 *   options: Object - The jPlayer options.
                 *
                 * Multiple instances must set the cssSelectorAncestor in the jPlayer options. Defaults to "#cp_container_1" in CirclePlayer.
                 */

                var myCirclePlayer = new CirclePlayer("#jquery_jplayer_1",
                        {
                            m4a: "${contextPath}/html/audio/1.mp3"
                            //m4a: "${contextPath}/admin/creator/${userInfo.creator.audioUrl}"
                        }, {
                            cssSelectorAncestor: "#cp_container_1"
                        });

                // This code creates a 2nd instance. Delete if not required.

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
                        $('#still').html('暂无');
                        $('#lifePhotos').html('暂无');
                    }
                });

            },
            insertImage: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#still");
            },
            insertImage2: function (path, id) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.prop('id', '');
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", path);
                tempDiv.children(":first").next().prop("value", id);
                tempDiv.insertBefore("#lifePhotos");
            },
            /*play: function() {
             var audio = document.getElementById('audio'); //重要
             if(audio.paused) {
             audio.play;
             } else {
             audio.pause();
             }
             },*/
            play: function () {
                // document.Player.URL = 'G.E.M. 邓紫棋 - 泡沫.mp3';
                $('#tempMusic').controls.play(); // 让播放器开始播放
            },
            /*play: function (url) {
             var flag = false;
             document.getElementById("userLogin").onsubmit = function () {
             if (flag) {
             return true;
             }
             else {
             document.getElementById("loginSound").play();
             return false;
             }
             }
             document.getElementById("loginSound").onended = function () {
             flag = true;
             document.getElementById("userLogin").submit();
             }
             },*/
        }
    }
    $(function () {
        creator.fn.init();
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
<%--<script type="text/javascript" src="../static/js/jquery.jmp3.js"></script>--%>
</body>
</html>

