<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label >名称</label>
                            <input type="text" class="form-control" name="productName" value="${(productInfo.getProductName())!""}" />
                        </div>
                        <div class="form-group">
                            <label >单价</label>
                            <input type="text" class="form-control" name="productPrice" value="${(productInfo.getProductPrice())!""}" />
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(productInfo.getProductStock())!""}" />
                        </div>
                        <div class="form-group">
                            <label >介绍</label>
                            <input type="text" class="form-control" name="productDescription" value="${(productInfo.getProductDescription())!""}" />
                        </div>
                        <div class="form-group">
                            <label >图片</label>
                            <img src="${(productInfo.productIcon)!""}"  width="150"   height="130" />
                            <input type="text" class="form-control" name="productIcon" value="${(productInfo.getProductIcon())!""}" />
                        </div>
                        <div class="form-group">
                            <label >类目</label>
                            <select name="categoryType" class="form-control">
                                <#list productCategorieList as productCategory>
                                    <option value="${productCategory.categoryType}"
                                        <#if productInfo?? && productCategory.categoryType == productInfo.categoryType>
                                            selected
                                        </#if>
                                    >${productCategory.getCategoryName()}</option>
                                </#list>

                            </select>
                        </div>


                        <div>
                            <input type="text" name="productId" value="${(productInfo.productId)!""}" hidden="hidden">
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>