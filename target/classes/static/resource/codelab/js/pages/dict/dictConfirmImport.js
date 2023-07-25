var dictImportData = {
    resultTable: '',
    resultData: []
};
layui.use(['table', 'form', 'DZUtils', 'ClUtils'], function () {
    var table = layui.table,
        form = layui.form,
        DZUtils = layui.DZUtils,
        ClUtils = layui.ClUtils,
        $ = layui.$;

    layer.load(2);

    // 获取sessionStorage
    var editFormData = DZUtils.getStorage('editForm') || null;
    if (editFormData) {
        DZUtils.removeStorage('editForm');
    }

    function init () {
        var submitUrl = window.rootPath + '/dz-ftsp/codelab/dict/confirmImport';

        dictImportData.resultData = editFormData;

        // 设定表格行的渲染
        var resultTableConfig = {
            elem: '#resultTableId',
            limit: 999999,
            data: editFormData,
            cols: [[
                { field: 'fullPath', width: 300, title: '节点' },
                { field: 'nodeValue', width: 200, title: '值' },
                { field: 'operateRemark', minWidth: 100, title: '处理' }
            ]]
        };

        dictImportData.resultTable = table.render(resultTableConfig);

        // 提交事件
        form.on('submit(saveBtn)', function (data) {
            layer.load(2);
            DZUtils.ajaxPost2(submitUrl, { list: dictImportData.resultData }, function (res) {
                if (res.errorNo === 0) {
                    var iframeIndex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(iframeIndex);
                    parent.location.reload();
                } else {
                    layer.alert(res.errorInfo);
                }
                layer.closeAll('loading');
            });
        });

        // 初始化表单
        form.render();
    }

    // 自适应高度
    ClUtils.autoHeight('#resultDiv', function (innerHeight) {
        return innerHeight - 60;
    });

    // 初始化
    init();

    layer.closeAll('loading');

});