<%-- 
    Document   : createTour
    Created on : Jun 20, 2020, 5:30:10 PM
    Author     : NhatBPM;
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Tour Page</title>
        <meta name="robots" content="noindex, nofollow">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="homeStyle.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <nav class="navbar navbar-expand-sm navbar-dark ">
                <a class="navbar-brand display-4" href="createTourPage">DREAM TRAVEL</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item dropdown active ">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">
                                ${sessionScope.user.name}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li><a class="dropdown-item" href="logout">Log Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h1 class="text text-center">Create Tour</h1>
                    <c:set var="errorTour" value="${requestScope.errorTour}"/>
                    <form action="createTour" method="POST" enctype="multipart/form-data">
                        <div class="row">
                            <div class="form-group col-12">
                                <label class="">Name Tour</label>
                                <input class="form-control" placeholder="Name Tour" type="text" name="txtName" value="${errorTour.name}">
                                <c:if test="${not empty errorTour.nameError}">
                                    <p class="text-danger">${errorTour.nameError}</p>
                                </c:if>
                            </div>
                            <div class="form-group col-12">
                                <label class="">Place Tour</label>
                                <input class="form-control" placeholder="Place Tour" type="text" name="txtPlace" value="${errorTour.place}">
                                <c:if test="${not empty errorTour.placeError}">
                                    <p class="text-danger">${errorTour.placeError}</p>
                                </c:if>
                            </div>
                            <div class="form-group col-6">
                                <label class="">Date From</label>
                                <input class="form-control" placeholder="Date From" type="date" name="txtDateFrom" value="${errorTour.dateFrom}">
                                <c:if test="${not empty errorTour.dateFromError}">
                                    <p class="text-danger">${errorTour.dateFromError}</p>
                                </c:if>
                            </div>
                            <div class="form-group col-6">
                                <label class="">Date To</label>
                                <input class="form-control" placeholder="Date To" type="date" name="txtDateTo" value="${errorTour.dateTo}">
                                <c:if test="${not empty errorTour.dateToError}">
                                    <p class="text-danger">${errorTour.dateToError}</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-6">
                                <label class="">Price From(USD)</label>
                                <input class="form-control" placeholder="Price From (USD)" type="text" name="txtPrice" value="${errorTour.price}">
                                <c:if test="${not empty errorTour.priceError}">
                                    <div class="text-danger">${errorTour.priceError}</div>
                                </c:if>
                            </div>
                            <div class="form-group col-6">
                                <label class="">Quota</label>
                                <input class="form-control" placeholder="Quota" type="text" name="txtQuota" value="${errorTour.qouta}">
                                <c:if test="${not empty errorTour.qoutaError}">
                                    <div class="text-danger">${errorTour.qoutaError}</div>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="file" name="image" value="" accept="image/*" />
                            <c:if test="${not empty errorTour.imageError}">
                                <div class="text-danger">${errorTour.imageError}</div>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-dark btn-block display-3">Create Tour</button>
                        </div>
                        <c:if test="${not empty requestScope.createMsg}">
                            <div class="alert alert-success">
                                <strong>Success!</strong> ${requestScope.createMsg}
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
