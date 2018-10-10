<html>
    <#include "../common/header.ftl" >
<body>
    <#--边栏-->
    <div id="wrapper" class="toggled">
        <#include "../common/nav.ftl">
        <div class="page-content-wrapper">
            <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-condensed table-bordered">
                                    <thead>
                                    <tr>
                                        <th>订单id</th>
                                        <th>姓名</th>
                                        <th>手机号</th>
                                        <th>地址</th>
                                        <th>金额</th>
                                        <th>订单状态</th>
                                        <th>支付状态</th>
                                        <th>创建时间</th>
                                        <th colspan="2">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.orderStatusEnum.msg}</td>
                                    <td>${orderDTO.payStatusEnum.msg}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                    <#if orderDTO.orderStatusEnum.msg!="新订单">
                                        <td>取消</td>
                                    <#else>
                                        <td><a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a></td>
                                    </#if>
                                </tr>
                                </#list>
                                    </tbody>
                                </table>
                            <ul class="pagination pull-right">
                                <#if currentPage lte 1>
                                    <li class="disabled"><a href="#">上一页</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                                </#if>

                                <#list 1..orderDTOPage.totalPages as index>
                                    <#if currentPage==index>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>

                                <#if currentPage gte orderDTOPage.totalPages>
                                    <li class="disabled"><a href="#">下一页</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                                </#if>
                                </ul>
                        </div>
                    </div>
                </div>
        </div>
    </div>

    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                   您有新订单！
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" onclick="javascript:document.getElementById('notice').pause()" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="location.reload()">查看</button>
                </div>
            </div>
        </div>
    </div>

    <audio id="notice" loop="loop" >
        <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
    </audio>

    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        var webSocket = null;
        if('WebSocket' in window){
            webSocket = new WebSocket("ws://172.16.11.153/sell/webSocket");
        }else{
            alert('该浏览器不支持websocket');
        }
        webSocket.onopen = function (ev) {
            console.log("建立连接");
        }
        webSocket.onclose = function (ev) {
            console.log("关闭连接");
        }

        webSocket.onmessage = function (ev) {
            console.log(ev.data);
            $('#myModal').modal('show');
            document.getElementById('notice').play();

        }
        webSocket.onerror = function (ev) {
            alert("WebSocket通信错误");
        }
        window.onbeforeunload = function () {
            webSocket.close();
        }
    </script>

</body>
</html>


