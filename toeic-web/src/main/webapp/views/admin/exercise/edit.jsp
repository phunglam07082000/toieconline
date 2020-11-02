<%--
  Created by IntelliJ IDEA.
  User: Lam Phung
  Date: 10/20/2020
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp"%>
<c:url var="editExerciseUrl" value="/ajax-admin-exercise-edit.html">
    <c:param name="urlType" value="url_edit"/>
</c:url>
<c:choose>
    <c:when test="${not empty messageResponse}">
        ${messageResponse}
    </c:when>
    <c:otherwise>
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <c:if test="${not empty item.pojo.exerciseId}">
                        <h4 class="modal-title"><fmt:message key="label.exercise.edit" bundle="${lang}"/></h4>
                    </c:if>
                    <c:if test="${empty item.pojo.exerciseId}">
                        <h4 class="modal-title"><fmt:message key="label.exercise.add" bundle="${lang}"/></h4>
                    </c:if>
                </div>

                <form action="${editExerciseUrl}" method="POST" id="editUserForm" >

                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="md-form">
                                    <input type="text" placeholder="<fmt:message key='label.exercise.name' bundle='${lang}'/>" class="form-control" value="${item.pojo.name}" id="username" name="pojo.name" required/>
                                </div>
                            </div>
                            <br/>
                            <br/>

                            <div class="col-md-12">
                                <div class="md-form">
                                    <c:choose>
                                        <c:when test="${not empty item.pojo.exerciseId}">
                                            <select id="type" name="typeId">
                                                <option value="${item.pojo.typeDTO.typeId}">${item.pojo.typeDTO.name}</option>
                                                <c:forEach items="${item.types}" var="itemType">
                                                    <c:if test="${itemType.typeId ne item.pojo.typeDTO.typeId}">
                                                        <option value="${itemType.typeId}">${itemType.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select id="type" name="typeId">
                                                <option value="-1"><fmt:message key="label.option.role" bundle="${lang}"/></option>
                                                <c:forEach items="${item.types}" var="itemType">
                                                    <option value="${itemType.typeId}">${itemType.name}</option>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${not empty item.pojo.exerciseId}">
                        <input type="hidden" name="pojo.exerciseId" value="${item.pojo.exerciseId}"/>
                    </c:if>
                    <input type="hidden" name="crudaction" id="crudactionEdit"/>
                </form>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close" bundle="${lang}"/></button>
                    <button type="button" id="btnSave" class="btn btn-primary"><fmt:message key="label.save" bundle="${lang}"/></button>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
