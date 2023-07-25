

function openAddCommoditiesLayer() {
  layui.use(['layer'], function () {
    var layer = layui.layer;

    layer.open({
      type: 2,
      title: '上架商品',
      area: ['500px', '400px'],
      content: ["../../../pages/commodities/addCommoditiesLayer.html", "no"],
      //直接跳转到iframe层页面操作
    })
  });
}




