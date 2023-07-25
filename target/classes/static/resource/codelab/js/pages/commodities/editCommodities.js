$(document).ready(function() {
  var editFormData = JSON.parse(sessionStorage.getItem('editForm'));

  console.log(editFormData);
  $.each(editFormData, function(key, value) {
    $('input[name="' + key + '"]').val(value);
  });


    // // 监听取消按钮点击事件
  // document.getElementById('cancelBtn').addEventListener('click', function() {
  //   layer.closeAll();
  // });
});


layui.use(['form','DZUtils', 'ClUtils'], function() {
  var form = layui.form;
  var DZUtils = layui.DZUtils;
  var ClUtils = layui.ClUtils;

  // 监听提交按钮点击事件
  form.on('submit(saveBtn)', function(data){
    // 处理表单提交逻辑
    var commoditiesData = data.field;
    console.log(commoditiesData);
    // 正则验证商品名称
    if (!/^[A-Za-z0-9\u4e00-\u9fa5]+$/.test(commoditiesData.commoditiesName)) {
      layer.msg('商品名称只能包含汉字、字母和数字', { icon: 2 });
      return false;
    }
    // 正则验证类别
    if (!/^[\u4e00-\u9fa5A-Za-z0-9]+$/.test(commoditiesData.category)) {
      layer.msg('类别只能包含汉字、字母和数字', { icon: 2 });
      return false;
    };
    // 正则验证价格
    if (!/^\d+(\.\d{1,2})?$/.test(commoditiesData.price)) {
      layer.msg('价格必须为数字，且最多保留两位小数', { icon: 2 });
      return false;
    };
    //正则验证库存数量
    if (!/^(0|[1-9]\d*)$/.test(commoditiesData.inventory)) {
      layer.msg('库存数量必须为大于等于0的整数', { icon: 2 });
      return false;
    }
    // 验证通过后提交表单
    // ...
    submitUrl = ClUtils.rootPath + '/dz-ftsp/codelab/commodities/editCommodities'
    DZUtils.ajaxPost2(submitUrl, commoditiesData, function (res){
    console.log(res)
    if (res.errorNo === 0) {
      // 关闭本窗口并刷新
      layer.msg('操作成功');
      var iframeIndex = parent.layer.getFrameIndex(window.name);
      console.log(iframeIndex)
      parent.layer.close(iframeIndex);
      parent.location.reload();
      } else {
          layer.alert(res.errorInfo);
      }
      layer.closeAll('loading');
      
    })
    // 阻止表单默认提交行为
    return false;
  
  });


});