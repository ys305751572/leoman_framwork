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
        <div class="block-area">
            <div class="row">
                <ul class="list-inline list-mass-actions">
                    <li>
                        <a data-toggle="modal" onclick="sensitiveWords.fn.add();" title="新增敏感词" class="tooltips">
                            <i class="sa-list-add"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${contextPath}/admin/sensitiveWords/detail" title="刷新" class="tooltips">
                            <i class="sa-list-refresh"></i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <h1 class="page-title">敏感词库</h1>
        <form id="fromId" name="formName" method="post" enctype="multipart/form-data"
              class="box tile animated active form-validation-1">
            <div class="block-area">
                <div class="row">
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>A：${a.size()}个</label>
                        <div class="col-md-12 m-b-15" style="margin-top: 10px">
                            <c:if test="${null == a || a.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != a && a.size() > 0 }">
                                <c:forEach var="a" items="${a}">
                                    <div id="ul${a.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${a.words}" class="input-sm form-control validate[required]" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx" onclick="sensitiveWords.fn.delete(${a.id})">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <%--<div class="col-md-1 m-b-15" style="display: none" id="addCreator">
                        <input type="text" class="input-sm form-control validate[required]"
                               id="creator" name="creator" data-rule="required" placeholder="..."/>
                        <button type="button" onclick="sensitiveWords.fn.deleteCreator(this)" class="btn btn-info btn-sm m-t-10">删除</button>
                    </div>--%>
                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>B：${b.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == b || b.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != b && a.size() > 0 }">
                                <c:forEach var="b" items="${b}">
                                    <div id="ul${b.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${b.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx" onclick="sensitiveWords.fn.delete(${b.id})">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>C：${c.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == c || c.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != c && c.size() > 0 }">
                                <c:forEach var="c" items="${c}">
                                    <div id="ul${c.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${c.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx" onclick="sensitiveWords.fn.delete(${c.id})">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>D：${d.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == d || d.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != d && d.size() > 0 }">
                                <c:forEach var="d" items="${d}">
                                    <div id="ul${d.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${d.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx" onclick="sensitiveWords.fn.delete(${d.id})">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>E：${e.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == e || e.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != e && e.size() > 0 }">
                                <c:forEach var="e" items="${e}">
                                    <div id="ul${e.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${e.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" onclick="sensitiveWords.fn.delete(${e.id})"
                                           style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>F：${f.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == f || f.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != f && f.size() > 0 }">
                                <c:forEach var="f" items="${f}">
                                    <div id="ul${f.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${f.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" onclick="sensitiveWords.fn.delete(${f.id})"
                                           style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx" >
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>G：${g.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == g || g.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != g && g.size() > 0 }">
                                <c:forEach var="g" items="${g}">
                                    <div id="ul${g.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${g.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);" onclick="sensitiveWords.fn.delete(${g.id})"
                                           style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx" >
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>H：${h.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == h || h.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != h && h.size() > 0 }">
                                <c:forEach var="h" items="${h}">
                                    <div id="ul${h.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${h.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${h.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>I：${i.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == i || i.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != i && i.size() > 0 }">
                                <c:forEach var="i" items="${i}">
                                    <div id="ul${i.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${i.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${i.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>J：${j.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == j || j.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != j && j.size() > 0 }">
                                <c:forEach var="j" items="${j}">
                                    <div id="ul${j.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${j.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${j.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>K：${k.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == k || k.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != k && k.size() > 0 }">
                                <c:forEach var="k" items="${k}">
                                    <div id="ul${k.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${k.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${k.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>L：${l.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == l || l.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != l && l.size() > 0 }">
                                <c:forEach var="l" items="${l}">
                                    <div id="ul${l.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${l.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${l.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>M：${m.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == m || m.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != m && m.size() > 0 }">
                                <c:forEach var="m" items="${m}">
                                    <div id="ul${m.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${m.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${m.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>N：${n.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == n || n.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != n && n.size() > 0 }">
                                <c:forEach var="n" items="${n}">
                                    <div id="ul${n.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${n.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${n.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>O：${o.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == o || o.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != o && o.size() > 0 }">
                                <c:forEach var="o" items="${o}">
                                    <div id="ul${o.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${o.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${o.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>P：${p.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == p || p.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != p && p.size() > 0 }">
                                <c:forEach var="p" items="${p}">
                                    <div id="ul${p.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${p.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${p.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>Q：${q.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == q || q.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != q && q.size() > 0 }">
                                <c:forEach var="q" items="${q}">
                                    <div id="ul${q.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${q.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${q.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>R：${r.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == r || r.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != r && r.size() > 0 }">
                                <c:forEach var="r" items="${r}">
                                    <div id="ul${r.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${r.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${r.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>S：${s.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == s || s.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != s && s.size() > 0 }">
                                <c:forEach var="s" items="${s}">
                                    <div id="ul${s.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${s.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${s.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>T：${t.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == t || t.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != t && t.size() > 0 }">
                                <c:forEach var="t" items="${t}">
                                    <div id="ul${t.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${t.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${t.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>U：${u.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == u || u.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != u && u.size() > 0 }">
                                <c:forEach var="u" items="${u}">
                                    <div id="ul${u.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${u.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${u.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>V：${v.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == v || v.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != v && v.size() > 0 }">
                                <c:forEach var="v" items="${v}">
                                    <div id="ul${v.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${v.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${v.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>W：${w.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == w || w.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != w && w.size() > 0 }">
                                <c:forEach var="w" items="${w}">
                                    <div id="ul${w.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${w.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${w.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>X：${x.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == x || x.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != x && x.size() > 0 }">
                                <c:forEach var="x" items="${x}">
                                    <div id="ul${x.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${x.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${x.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>Y：${y.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == y || y.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != y && y.size() > 0 }">
                                <c:forEach var="y" items="${y}">
                                    <div id="ul${y.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${y.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${y.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                    <hr class="whiter m-t-20"/>
                    <div class="col-md-12 m-b-15" style="margin-top: 10px">
                        <label>Z：${z.size()}个</label>
                        <div class="col-md-12 m-b-15">
                            <c:if test="${null == z || z.size() == 0 }">
                                暂无
                            </c:if>
                            <c:if test="${null != z && z.size() > 0 }">
                                <c:forEach var="z" items="${z}">
                                    <div id="ul${z.id }" class="col-md-1 m-b-15">
                                        <input type="text" value="${z.words}" class="input-sm form-control validate[required]"
                                               name="creators" data-rule="required" placeholder="..." disabled/>
                                        <a id="a" href="javascript:void(0);"  onclick="sensitiveWords.fn.delete(${z.id})" style="float: none; z-index: 10; position: relative; bottom: 30px; left: 105px;" class="axx">
                                            <img src="${contextPath}/static/images/xx.png" style="height: 16px; width: 16px; display: inline;" border="1"/>
                                        </a>
                                            <%--<button type="button" onclick="work.fn.delete(${co.id})" class="btn btn-info btn-sm m-t-10">删除</button>--%>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">删除该敏感词后操作无法撤销，确定删除？</h4>
                                </div>
                                <div class="modal-body">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="button" id="confirmSub" class="btn btn-primary">确定
                                    </button>
                                </div>
                                <!-- /.modal-content -->
                            </div>
                            <!-- /.modal-dialog -->
                        </div>
                    </div>

                <div class="form-group">
                    <div class="col-md-offset-5">
                        <button type="button" class="btn btn-info btn-sm m-t-10" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </form>

        <div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">新增敏感词</h4>
                    </div>
                    <div class="modal-body">
                        <form id="infoForm" method="post" enctype="application/x-www-form-urlencoded" class="form-horizontal" role="form">
                            <input type="hidden" id="hiddenreserveId" name="reserveId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">敏感词:</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-append "
                                           style="width: 180px;" id="words" name="words" maxlength="20"
                                           data-rule="required"/>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" onclick="sensitiveWords.fn.save()" class="btn btn-primary">确定
                            </button>
                        </div>
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
    sensitiveWords = {
        v: {
            list: [],
            chart: null,
            dTable: null
        },
        fn: {
            init: function () {
//                $("#fromId").validationEngine();
            },
            deleteCreator: function (self) {
                $(self).parent().remove();
            },
            add: function () {

                $('#words').val("");
                $("#add").modal("show");
//                if(!$("#fromId").validationEngine("validate")) return;
            },
            save: function () {

                $leoman.ajax("${contextPath}/admin/sensitiveWords/save", {
                    "words": $('#words').val()
                }, function (result) {
                    if (result == 1) {
                        $common.fn.notify("操作成功", "success");
                        $("#add").modal("hide");
                        window.location.href = "${contextPath}/admin/sensitiveWords/detail";
                    } else if (result == 2){
                        $common.fn.notify("该词汇已经存在", "error");
                        $("#add").modal("hide");
                    } else {
                        $common.fn.notify("操作失败", "error");
                    }
                });

            },
            delete: function (id) {
                $("#delete").modal("show");
                $('#confirmSub').click(function () {
                    $("#ul" + id).remove();
                    $leoman.ajax("${contextPath}/admin/sensitiveWords/delete", {
                        "id": id
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
        sensitiveWords.fn.init();
    })
</script>
</body>
</html>

