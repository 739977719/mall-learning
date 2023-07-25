var dictAddData = {
    nodesTable: -1
};
layui.use(['form', 'table', 'DZUtils'], function () {
    var form = layui.form,
        table = layui.table,
        DZUtils = layui.DZUtils,
        $ = layui.$;

    layer.load(2);

    // 获取sessionStorage
    var editFormData = DZUtils.getStorage('editForm') || null;
    if (editFormData) {
        DZUtils.removeStorage('editForm');
    }

    function initForm () {

        // 修改时设置初始值
        var submitUrl = window.rootPath + '/dz-ftsp/codelab/dict/addBatch';
        if (editFormData) {
            form.val('commonForm', { nodePath: editFormData.fullPath || '' });
            $('#nodeValue').html(editFormData.nodeValue || '');
            $('#nodePath').html(editFormData.fullPath || '');
        }

        // 加载表格
        dictAddData.nodesTable = table.render({
            elem: '#nodesTableId',
            id: 'nodesTableId',
            data: [{ nodeKey: '', nodeValue: '', orderNum: '999999', remark: '' }],
            toolbar: '#nodesHeaderBar',
            defaultToolbar: [],
            area: '80%',
            cols: [[
                { field: 'nodeKey', width: 200, title: 'key', edit: 'text' },
                { field: 'nodeValue', width: 200, title: '值', edit: 'text' },
                { field: 'orderNum', width: 80, title: '排序值', edit: 'text' },
                { field: 'remark', minWidth: 100, title: '备注', edit: 'text' },
                { title: '操作', width: 70, toolbar: '#nodesOperateBar', align: 'left', fixed:'right' }
            ]]
        });

        // 点击新增事件
        table.on('toolbar(nodesTableFilter)', function (obj) {
            if (obj.event === 'add') {
                var list = table.cache['nodesTableId'] || [];
                list.push({ nodeKey: '', nodeValue: '', orderNum: '999999', remark: '' });
                table.reload('nodesTableId', { data: list });
            }
        });

        // 监听每一行数据的按钮操作
        table.on('tool(nodesTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'delete') {
                var list = table.cache['nodesTableId'] || []
                if (list.length <= 1) {
                    layer.msg('至少填写一行');
                    return false;
                }
                list.shift();
                table.reload('nodesTableId', { data: list });
            }
        });

        // 提交事件
        form.on('submit(saveBtn)', function (data) {
            // 检查节点
            var list = table.cache['nodesTableId'] || []
            var error = '';
            list.every(function (item, i) {
                if (!item.nodeKey || item.nodeKey === '') {
                    error = '第' + (i + 1) + '行缺少key';
                    return false;
                }
                if (!item.nodeValue || item.nodeValue === '') {
                    error = '第' + (i + 1) + '行缺少值';
                    return false;
                }
                if (!item.orderNum || item.orderNum === '') {
                    error = '第' + (i + 1) + '行缺少排序值';
                    return false;
                }
                return true;
            });
            // 检查节点重复
            if ((!error || error === '') && list.length > 1) {
                for (var i = 0; i < list.length - 1; i++) {
                    for (var j = i + 1; j < list.length; j++) {
                        if (list[i].nodeKey === list[j].nodeKey) {
                            error = '第' + (i + 1) + '行与第' + (j + 1) + '行重复';
                            break;
                        }
                    }
                    if (error && error !== '') {
                        break;
                    }
                }
            }
            if (error && error !== '') {
                layer.msg(error);
                return false;
            }
            data.field.list = list;
            layer.load(2);
            DZUtils.ajaxPost2(submitUrl, data.field, function (res) {
                if (res.errorNo === 0) {
                    // 关闭本窗口并刷新
                    layer.msg('操作成功');
                    var iframeIndex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(iframeIndex);
                    parent.location.reload();
                } else {
                    layer.alert(res.errorInfo);
                }
                layer.closeAll('loading');
            });
        });

        // 重新渲染表单
        form.render();

    }

    // 初始化表单
    initForm();

    layer.closeAll('loading');

});
