<%-- 
    Document   : viewCard
    Created on : Jun 20, 2020, 9:29:38 PM
    Author     : NhatBPM;
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Check Out Page</title>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <link href="homeStyle.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <nav class="navbar navbar-expand-sm navbar-dark ">
                <a class="navbar-brand display-4" href="search">DREAM TRAVEL</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav ml-auto">
                        <c:set var="user" value="${sessionScope.user}"/>
                        <c:if test="${empty user}">
                            <li class="nav-item">
                                <a class="nav-link active" href="loginPage">Login</a>
                            </li>
                        </c:if>
                        <c:if test="${not empty user}">
                            <li class="nav-item">
                                <a class="nav-link active" href="viewCart">Your Cart</a>
                            </li>
                            <li class="nav-item dropdown active ">
                                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">
                                    ${sessionScope.user.name}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li><a class="dropdown-item" href="viewCard">View Your Card</a></li>
                                    <li><a class="dropdown-item" href="logout">Log Out</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </nav>
        </div>
        <c:set var="listTourCart" value="${sessionScope.listTourCart}"/>
        <c:if test="${not empty listTourCart}">
            cart.
            <div class="container">
                <table class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th style="width: 52%;">Tour</th>
                            <th style="width: 10%;">Price</th>
                            <th style="width: 8%;">Quantity</th>
                            <th style="width: 20%;" class="text-center">Subtotal</th>
                            <th style="width: 10%;">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tourMap" items="${listTourCart}">
                            <c:set var="tour" value="${tourMap.value}"/>
                            <tr>
                                <form action="updateAmountTour">
                                    <td>
                                        <div class="row">
                                            <div class="col-sm-5 hidden-xs">
                                                <img src="${tour.imageLink}" style="width:200px;margin:0; height:180px;" />
                                            </div>
                                            <div class="col-sm-7">
                                                <h4 class="nomargin">${tour.name}</h4>
                                                <p>Departure date: ${tour.dateFrom} </p>
                                                <p>Finish date: ${tour.dateTo} </p>
                                                <p>Place: ${tour.place}</p>
                                                <p>Quota: ${tour.quota} tickets</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td data-th="Price">$${tour.price}</td>
                                    <td data-th="Quantity">
                                        <input type="number" class="form-control text-center" value="${tour.amount}" min="1" name="txtAmount" />
                                        <c:if test="${requestScope.tourIdUpdate == tourMap.key}">
                                            <c:if test="${not empty requestScope.errorAmountCart}">
                                                <p class="text-danger">Only ${requestScope.errorAmountCart} tickets left.</p>
                                            </c:if>
                                        </c:if>
                                    </td>
                                    <td data-th="Subtotal" class="text-center">
                                        $${tour.price * tour.amount}
                                        <c:if test="${requestScope.tourIdUpdate == tourMap.key}">
                                            <c:if test="${not empty requestScope.errorAmountCart}">
                                                <p class="text-danger">Please update again amount or remove the tour from the cart.</p>
                                            </c:if>
                                            <c:if test="${not empty requestScope.updateSuccess}">
                                                <p class="text-success">${requestScope.updateSuccess}</p>
                                            </c:if>
                                        </c:if>
                                    </td>
                                    <td class="actions" data-th="">
                                        <input type="hidden" name="tourId" value="${tourMap.key}" />
                                        <button class="btn btn-info" type="submit">
                                            <i class="fa fa-refresh"> Refresh</i>
                                        </button>
                                        <c:url var="urlRewriting" value="removeTourBooking">
                                            <c:param name="tourId" value="${tourMap.key}"/>
                                        </c:url>
                                        <a href="${urlRewriting}" class="btn btn-danger"><i class="fa fa-trash-o"> Remove</i></a>
                                    </td>   
                                </form>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td>
                                <a href="search" class="btn btn-warning"><i class="fa fa-angle-left"></i><i class="fa fa-angle-left"></i> Continue Shopping</a>
                            </td>
                            <td colspan="2" class="hidden-xs">

                            </td>
                            <td class="hidden-xs text-center">
                                <strong>Total $${requestScope.total}</strong>
                            </td>
                            <td>
                                <!--  <a href="" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a>-->
                            </td>
                        </tr>
                    </tfoot>
                </table>
                <div class="row">
                    <div class="col-8">
                        <h4>Payment</h4>
                        <form action="checkout" method="POST">
                            <input type="hidden" name="txtTotal" value="${requestScope.total - (requestScope.total * sessionScope.discountPercent / 100)}" />
                            <button class="btn btn-primary btn-lg btn-block" type="submit">Continue to checkout</button>
                        </form>
                    </div>
                    <div class="col-4">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Your cart</span>
                            <span class="badge badge-secondary badge-pill">${listTourCart.size()} </span>
                        </h4>
                        <ul class="list-group mb-3">
                            <li class="list-group-item d-flex justify-content-between lh-condensed">
                              <div>
                                <h6 class="my-0">Total temporary</h6>
                              </div>
                              <span class="text-muted">$${requestScope.total}</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between bg-light">
                                <div class="text-success">
                                    <h6 class="my-0">Discount Code</h6>
                                    <small>${sessionScope.discountCode}</small>
                                </div>
                                <span class="text-success">-$${requestScope.total * sessionScope.discountPercent / 100}</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between lh-condensed">
                                <span>Total (USD)</span>
                                <strong>$${requestScope.total - (requestScope.total * sessionScope.discountPercent / 100)}</strong>
                            </li>
                        </ul>
                        <form class="card p-2" action="checkDiscountCode">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Discount code" name="txtDiscountCode" value="${sessionScope.discountCode}">
                                <div class="input-group-append">
                                    <input type="hidden" name="txtTotal" value="${requestScope.total}" />
                                    <button type="submit" class="btn btn-secondary">Check Code</button>
                                </div>
                            </div>
                            <c:if test="${not empty requestScope.errorDiscout}">
                                <h6 class="alert alert-danger container">${requestScope.errorDiscout}</h6> 
                            </c:if>
                        </form>
                </div>
                </div>
            </div>
        </c:if>
        <c:if test="${empty listTourCart}">
            <div class="container">
                <h4 class="alert alert-danger">No Items</h4> 
                <a href="search" class="btn btn-warning"><i class="fa fa-angle-left"></i><i class="fa fa-angle-left"></i> Continue Shopping</a>
            </div>
        </c:if>

    </body>
</html>
