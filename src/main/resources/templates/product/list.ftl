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
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>更新时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                                <#list productInfoPage.content as productInfo>
                                <tr>
                                    <td>${productInfo.productId}</td>
                                    <td>${productInfo.productName}</td>
                                    <td><img width="100px" height="100px" src="${productInfo.productIcon}"></td>
                                    <td>${productInfo.productPrice}</td>
                                    <td>${productInfo.productStock}</td>
                                    <td>${productInfo.productDescription}</td>
                                    <#list productCategoryList as productCategory>
                                        <#if productCategory.categoryType==productInfo.categoryItem>
                                            <td>${productCategory.categoryName}</td>
                                        </#if>
                                    </#list>
                                    <td>${productInfo.createTime}</td>
                                    <td>${productInfo.updateTime}</td>
                                    <td><a href="/sell/seller/product/update?productId=${productInfo.productId}&page=${currentPage}&size=${size}">修改</a></td>
                                    <td>
                                        <#if productInfo.productStatusEnum.msg=="下架">
                                            <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}&page=${currentPage}&size=${size}">上架</a>
                                        <#else>
                                            <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}&page=${currentPage}&size=${size}">下架</a>
                                        </#if>
                                    </td>
                                    </tr>
                                </#list>
                        </tbody>
                    </table>
                    <ul class="pagination pull-right">
                                <#if currentPage lte 1>
                                    <li class="disabled"><a href="#">上一页</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                                </#if>

                                <#list 1..productInfoPage.totalPages as index>
                                    <#if currentPage==index>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>

                                <#if currentPage gte productInfoPage.totalPages>
                                    <li class="disabled"><a href="#">下一页</a></li>
                                <#else>
                                    <li><a href="/sell/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                                </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


