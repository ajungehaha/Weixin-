<html>
    <head>
       <#include "../common/header.ftl">
        <link href="/sell/css/detail.css" rel="stylesheet" type="text/css">

    <title>订单详情</title>
    </head>
    <body>
        <div id="wrapper" class="toggled">
            <!--边栏-->
            <#include "../common/nav.ftl">

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-4 column">
                            <table class="table table-condensed table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>
                                        订单编号
                                    </th>
                                    <th>
                                        金额
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                    <td>
                                    ${orderMasterDTO.getOrderId()}
                                    </td>
                                    <td>
                                    ${orderMasterDTO.getOrderAmount()}
                                    </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-condensed table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>
                                        商品编号
                                    </th>

                                    <th>
                                        商品图片
                                    </th>
                                    <th>
                                        商品名称
                                    </th>
                                    <th>
                                        单价
                                    </th>
                                    <th>
                                        数量
                                    </th>
                                    <th>
                                        总额
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderMasterDTO.getOrderDetailList() as orderDetailDTO>
                                    <tr>
                                        <td>
                                            ${orderDetailDTO.getProductId()}
                                        </td>
                                        <td>
                                            <img src="${orderDetailDTO.getProductIcon()} ">
                                        </td>
                                        <td>
                                            ${orderDetailDTO.getProductName()}
                                        </td>
                                        <td>
                                            ${orderDetailDTO.getProductPrice()}
                                        </td>
                                        <td>
                                            ${orderDetailDTO.getProductQuantity()}
                                        </td>
                                        <td>
                                            ${orderDetailDTO.getProductPrice()*orderDetailDTO.getProductQuantity()}
                                        </td>
                                    </tr>

                                </#list>

                                </tbody>
                            </table>
                        </div>
                        <#if orderMasterDTO.getOrderStatus()==0>
                            <a href="/sell/seller/order/finsh?orderId=${orderMasterDTO.getOrderId()}"><button type="button" class="btn btn-default btn-primary">完结订单</button></a>
                            <a href="/sell/seller/order/cancel?orderId=${orderMasterDTO.getOrderId()}"><button type="button" class="btn btn-default btn-danger">取消订单</button></a>
                        </#if>
                </div>
                </div>
            </div>
        </div>
    </body>
</html>