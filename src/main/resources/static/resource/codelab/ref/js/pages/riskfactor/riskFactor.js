var riskFactorData = {
    customTable: '',
    dicts: {}
};
layui.use(['form', 'table', 'DZUtils', 'StUtils'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        DZUtils = layui.DZUtils,
        StUtils = layui.StUtils;

    layer.load(2);

    function initTable() {


        riskFactorData.customTable = DZUtils.customTable({
            elem: '#currentTableId',
            toolbar: '#headerToolbar',
            method: 'post',
            height: 'full-80',
            // url: window.rootPath + '/dz-ftsp/stest/riskfactor/list',
            url: StUtils.rootPath + '/dz-ftsp/stest/riskfactor/list',
            page: false,
            limit: 999999,
            cols: [[
                { field: 'id', title: 'ID', maxWidth: 80, hide: true },
                { field: 'riskId', width: 150, title: '风险因子编号' },
                { field: 'riskName', width: 350, title: '风险因子名称' },
                { field: 'expression', minWidth: 500, title: '回溯公式', hide: true },
                { field: 'expressionCn', minWidth: 500, title: '回溯公式' },
                { field: 'orderNum', width: 80, title: '排序值', align: 'center', edit: 'text' },
                { field: 'delFlag', width: 130, title: '状态', templet: '#switchTpl', unresize: true },
                { title: '操作', width: 100, toolbar: '#currentTableBar', align: 'left', fixed: 'right' }
            ]],
            autoSort: false,
            done: function () {
                layer.closeAll('loading');
            }
        });

        /**
        * toolbar监听事件
        */
        table.on('toolbar(currentTableFilter)', function (obj) {
            if (obj.event === 'add') {
                DZUtils.openModal({
                    title: '风险因子新增',
                    // content: window.rootPath + '/resource/ftsp/stest/pages/riskfactor/riskFactorEdit.html'
                    content: window.rootPath + '/pages/riskfactor/riskFactorEdit.html'
                });
            }
        });

        // 监听每一行数据的按钮操作
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'update') {
                sessionStorage.setItem('editForm', JSON.stringify(data));
                DZUtils.openModal({
                    title: '风险因子修改',
                    // content: window.rootPath + '/resource/ftsp/stest/pages/riskfactor/riskFactorEdit.html'
                    content: window.rootPath + '/pages/riskfactor/riskFactorEdit.html'
                });
            } else if (obj.event === 'delete') {
                layer.confirm('确定删除该风险因子吗？', { title: '提示', icon: 3 }, function (index) {
                    layer.load(2);
                    var delUrl = window.rootPath + '/dz-ftsp/stest/riskfactor/delete';
                    DZUtils.ajaxPost2(delUrl, { id: data.id }, function (res) {
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
        });

        //监听单元格编辑
        table.on('edit(currentTableFilter)', function (obj) {
            // var submitUrl = window.rootPath + '/dz-ftsp/stest/riskfactor/save';
            var submitUrl = StUtils.rootPath + '/dz-ftsp/stest/riskfactor/save';

            var _value = obj.value,//得到修改后的值
                _data = obj.data, //得到所在行所有键值
                _field = obj.field; //得到字段
            _data = eval("({ id: " + _data.id + ", riskId: " + _data.riskId + ", " + _field + ": " + _value + "})");

            DZUtils.ajaxPost2(submitUrl, _data, function (res) {
                if (res.errorNo === 0) {
                    // 关闭本窗口并刷新
                    layer.msg('修改成功');
                } else {
                    layer.msg('修改失败');
                }
                riskFactorData.customTable.reload;
            });
        });
    }

    // 加载表格
    initTable();

});


