<html>
<#include "../common/header.ftl">
<body>
<div class="container-fluid row">
<#--边栏-->
        <#include "../common/leftMenu.ftl">
    <div class="col-xs-12 col-md-8">
    <#--主要内容-->
        <div class="container pull-right">
    <div class="row clearfix">
        <div class="col-md-4 column">
            <table class="table">
                <thead>
                <tr>
                    <th>订单编号</th>
                    <th>订单总金额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.buyerAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <#--订单详情-->
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>商品ID</th>
                    <th>商品名称</th>
                    <th>商品价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                <tr>
                    <td>${orderDetail.detailId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <#if orderDTO.orderStatus == 0>
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" class="btn btn-default btn-info">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" class="btn btn-default btn-danger">取消订单</a>
                    </#if>
                    <a href="/sell/seller/order/list" class="btn btn-success btn-info">返回上层</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>