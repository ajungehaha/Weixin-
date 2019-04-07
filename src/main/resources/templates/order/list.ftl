
<html>
<head>
    <#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <!--边栏-->
    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>
                            订单编号
                        </th>
                        <th>
                            姓名
                        </th>
                        <th>
                            手机号
                        </th>
                        <th>
                            地址
                        </th>
                        <th>
                            金额
                        </th>
                        <th>
                            订单状态
                        </th>
                        <th>
                            支付状态
                        </th>
                        <th>
                            创建时间
                        </th>
                        <th>
                            操作
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orderMasterDTOPage.content as orderMasterDTO>
                        <tr>
                        <td>
                        ${orderMasterDTO.orderId}
                        </td>
                        <td>
                        ${orderMasterDTO.buyName}
                        </td>
                        <td>
                        ${orderMasterDTO.buyPhone}
                        </td>
                        <td>
                        ${orderMasterDTO.buyAdress}
                        </td>
                        <td>
                        ${orderMasterDTO.orderAmount}
                        </td>
                        <td>
                        ${orderMasterDTO.getOrderStatusCode().getMessage()}
                        </td>
                        <td>
                        ${orderMasterDTO.getPayStatusCode().getMessage()}
                        </td>
                        <td>
                        ${orderMasterDTO.createTime}
                        </td>
                        <td style="float: left; ">
                    <a href="/sell/seller/order/detail?orderId=${orderMasterDTO.getOrderId()}"><button type="button" class="btn btn-default">详情</button></a>
                        <#if orderMasterDTO.getOrderStatus()==0>
                            <a href="/sell/seller/order/cancel?orderId=${orderMasterDTO.getOrderId()}"><button type="button" class="btn btn-default">取消</button></a>

                        </#if>
                        </td>

                        </tr>
                    </#list >
                    </tbody>
                </table>
            </div>
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled">
                            <a href="#">上一页</a>
                        </li>

                    <#else>
                        <li>
                        <a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a>
                        </li>
                    </#if>
                        <!--从1到一个数就是下面的写法-->
                    <#list 1..orderMasterDTOPage.getTotalPages() as i>
                        <#if currentPage == i>
                            <li class="disabled">
                            <a href="/sell/seller/order/list?page=${i}&size=${size}">${i}</a>
                            </li>

                        <#else>
                            <li>
                            <a href="/sell/seller/order/list?page=${i}&size=${size}">${i}</a>
                            </li>
                        </#if>
                    </#list>

                    <#if currentPage+1 gte orderMasterDTOPage.getTotalPages()>
                        <li class="disabled">
                            <a href="#">下一页</a>
                        </li>

                    <#else>
                        <li>
                        <a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a>
                        </li>
                    </#if>
                </ul>
            </div>
            <div class="col-md-12 column">
                <#--播放音乐-->
                <div>
                    <audio id="notice" loop="loop">
                        <source src="/sell/mp3/song.mp3" type="audio/mpeg"/>
                    </audio>
                </div>
                <#--弹窗-->
                <div class="modal fade" id="mymodel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 class="modal-title" id="myModalLabel">
                                   提醒
                                </h4>
                            </div>
                            <div class="modal-body">
                               你有新的订单
                            </div>
                            <div class="modal-footer">
                                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>


        <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>

        <script>

            var websocket = null;
            //判断浏览器是否支持webscoket
            if ('WebSocket' in window)
            {

                websocket = new WebSocket("ws://ajungesell.natapp1.cc/sell/websocket");
            }
            else
            {
                alert('当前浏览器不支持websocket');
            }
            <#--//设置返回的消息的方法-->
            <#--function setMessageInnerHtml(innerHTML)-->
            <#--{-->
                <#--${'#message'}.innerHTML = innerHTML;-->
            <#--}-->

            //连接发生错误的回调方法
            websocket.onerror = function () {
                console.log('Websockt连接发生错误');
            }

            //连接成功建立的回调方法
            websocket.onopen = function () {
                console.log('websocket连接成功');
            }
            //接收消息的回调方法
            websocket.onmessage = function (event) {
                console.log("收到消息"+event.data);

                //调用窗口
                ${'#mymodel'}.modal('show');
                //播放音乐
                document.getElementById('notice').play();


            }
            //连接关闭的回调方法
            websocket.onclose = function () {
                console.log('连接关闭');
            }

            //监听事件的窗口，当窗口关闭，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会报异常
            window.onbeforeunload = function () {
                closeWebsocket();
            }
            //关闭连接
            function closeWebsocket() {
                websocket.close();

            }

        </script>
</div>
</div>
</body>
</html>