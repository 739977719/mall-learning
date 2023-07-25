$(function () {
  layui.use(['form', 'ClUtils', 'DZUtils'], function () {
    var form = layui.form;
    var ClUtils = layui.ClUtils;
    var DZUtils = layui.DZUtils;


    submitUrl = ClUtils.rootPath + '/dz-ftsp/codelab/commodities/getLatestId'

    DZUtils.ajaxPost2(submitUrl, null, function (res) {
      if (res.errorNo === 0){
        var latestId = res.result;
        var newId = latestId + 1;
        console.log(newId);
        $('#idInput').val(newId);
      }else{
        console.log(res.errorInfo);
      }
    })


    //监听提交
    form.on('submit(addCommoddities)', function (data) {
      var commoditiesData = data.field; // 获取表单数据
      // 正则验证商品名称
      if (!/^[A-Za-z0-9\u4e00-\u9fa5]+$/.test(commoditiesData.commoditiesName)) {
        layer.msg('商品名称只能包含汉字、字母和数字', { icon: 2 });
        return false;
      }

      // 正则验证类别
      if (!/^[\u4e00-\u9fa5A-Za-z0-9]+$/.test(commoditiesData.category)) {
        layer.msg('类别只能包含汉字、字母和数字', { icon: 2 });
        return false;
      }

      // 正则验证价格
      if (!/^\d+(\.\d{1,2})?$/.test(commoditiesData.price)) {
        layer.msg('价格必须为数字，且最多保留两位小数', { icon: 2 });
        return false;
      }
      //正则验证库存数量
      if (!/^(0|[1-9]\d*)$/.test(commoditiesData.inventory)) {
        layer.msg('库存数量必须为大于等于0的整数', { icon: 2 });
        return false;
      }

      console.log(commoditiesData);

      //通过验证后
      //发送表单数据到服务端
      submitUrl = ClUtils.rootPath + '/dz-ftsp/codelab/commodities/addCommodities'
      //ajax
      DZUtils.ajaxPost2(submitUrl, commoditiesData, function (res) {
        if (res.result === "上架成功") {
          // 关闭本窗口并刷新
          // layer.msg(res.result);
          var iframeIndex = parent.layer.getFrameIndex(window.name);
          parent.layer.close(iframeIndex);
          parent.location.reload();
        } else if(res.result === "商品已存在"){
          layer.confirm(res.result);
          
        }
        // layer.closeAll('loading');
      });
      return false; // 阻止表单默认提交行为

    })
  });
});



function validateProductCode(productCode) {
  var regex = /^[A-Z0-9]{16}$/;
  return regex.test(productCode);
}