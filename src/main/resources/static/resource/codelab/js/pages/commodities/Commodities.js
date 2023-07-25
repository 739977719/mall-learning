var commoditiesData = {
  customTable: ''
};
layui.use(['form', 'table', 'laydate', 'DZUtils', 'ClUtils'], function () {
  var $ = layui.jquery,
      form = layui.form,
      table = layui.table,
      laydate = layui.laydate,
      DZUtils = layui.DZUtils,
      ClUtils = layui.ClUtils;

  layer.load(2);
  laydate.render({
      elem: '#createDateBegin',
      format: 'yyyyMMdd',
      type: 'date'
  });
  laydate.render({
      elem: '#createDateEnd',
      format: 'yyyyMMdd',
      type: 'date'
  });


  function initTable () {

      commoditiesData.customTable = DZUtils.customTable({
          elem: '#currentTableId',
          // url: window.rootPath + '/dz-ftsp/codelab/log/list',
          url: ClUtils.rootPath + '/dz-ftsp/codelab/commodities/list',
          cols: [[
              { field: 'id',  width:200, title: '商品编号', sort: true },
              { field: 'commoditiesName', width: 160, title: '商品名称', sort: true },
              { field: 'category', Width: 100, title: '商品类别' },
              { field: 'price', width: 100, title: '单价', sort: true },
              { field: 'inventory', width: 150, title: '库存数量', sort: true },
              { field: 'createdTime', minwidth: 200, title: '操作时间', sort: true },
              { field: 'createdByName', width: 150, title: '操作用户', sort: true },
              { field: 'createdById', width: 150, title: '用户编号', sort: true },
              { field: 'actions', title: '操作', templet: '#actionButtonsTpl' }
          ]],
          autoSort: false,
          initSort: { field: 'createTime', type: 'desc' },
          where: { sort: 'created_time desc, id desc' },
          done: function () {
              layer.closeAll('loading');
          }
      });

      // 监听搜索操作
      form.on('submit(data-search-btn)', function (data) {
          //执行搜索重载
          commoditiesData.customTable = table.reload('currentTableId', {
              page: { curr: 1 },
              where: data.field
          }, 'data');
          return false;
      });

      // 监听排序操作
      table.on('sort(currentTableFilter)', function (data) {
          var orderType = ' ' + (data.type || '');
          commoditiesData.customTable = table.reload('currentTableId', {
              page: { curr: 1 },
              initSort: data,
              where: { sort: ClUtils.transCamelToLine(data.field) + orderType + ', id' + orderType }
          });
      });

      //监听修改或下架操作
      table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data;
        var event = obj.event;
        if (event === 'edit') {
          // Handle the edit button click event
          sessionStorage.setItem('editForm', JSON.stringify(data));
          DZUtils.openModal({
            title: '商品信息修改',
            area: ['600px', '400px'],
            // content: window.rootPath + '/resource/ftsp/stest/pages/riskfactor/riskFactorEdit.html'
            content: window.rootPath + '/pages/commodities/editCommodities.html'
        });
        } else if (event === 'remove') {
          // Handle the remove button click event
          layer.confirm('确定下架该商品吗？', { title: '提示', icon: 3 }, function (index) {
            // layer.load(2);
            submitUrl = ClUtils.rootPath + '/dz-ftsp/codelab/commodities/deleteCommodities'
            DZUtils.ajaxPost2(submitUrl, { id: data.id }, function (res) {
                console.log({ id: data.id })
                
                if (res.errorNo === 0) {
                    layer.close(index);
                    layer.msg('删除成功', { title: '提示', icon: 1 });
                    location.reload();
                } else {
                    layer.alert(res.errorInfo);
                }
                layer.closeAll('loading');
            });
      });

        }
        });}

  // 加载表格
  initTable();


});