layui.use(['table', 'treetable', 'DZUtils', 'ClUtils'], function () {
    var $ = layui.jquery,
        table = layui.table,
        treetable = layui.treetable,
        DZUtils = layui.DZUtils,
        ClUtils = layui.ClUtils;

    layer.load(2);

    function initTree () {

        // 获取字典数据
        // DZUtils.ajaxPost(window.rootPath + '/dz-ftsp/codelab/dict/getList', {}, function (res) {
        DZUtils.ajaxPost(ClUtils.rootPath + '/dz-ftsp/codelab/dict/getList', {}, function (res) {
            if (res.errorNo === 0) {
                // 预处理数据
                var data = res.result || [];
                data = $.map(data, function (item) {
                    return {
                        dictId: item.id,
                        nodeKey: item.nodeKey,
                        nodeValue: item.nodeValue,
                        orderNum: item.orderNum,
                        remark: item.remark || '',
                        fullPath: (item.nodePath ? item.nodePath + '.' : '') + item.nodeKey,
                        treeFullPath: (item.nodePath ? item.nodePath + '.' : '') + item.nodeKey,
                        nodePath: item.nodePath,
                        treeNodePath: item.nodePath ? item.nodePath : '@ROOT@'
                    };
                });
                data.unshift({
                    fullPath: '',
                    treeFullPath: '@ROOT@',
                    nodePath: '',
                    treeNodePath: '@@ROOT@@',
                    nodeKey: '(根节点)',
                    nodeValue: '(根节点)'
                });

                treetable.render({
                    elem: '#dict-table',
                    treeColIndex: 0,
                    treeSpid: '@@ROOT@@',
                    treeIdName: 'treeFullPath',
                    treePidName: 'treeNodePath',
                    dataSet: data,
                    toolbar: '#headerToolbar',
                    defaultToolbar: false,
                    page: false,
                    treeDefaultClose: true,
                    cols: [[
                        { field: 'nodeKey', width: 200, title: '节点' },
                        { field: 'fullPath', width: 200, title: '路径' },
                        { field: 'nodeValue', width: 200, title: '字典值' },
                        { field: 'orderNum', width: 80, title: '排序值' },
                        { field: 'remark', minWidth: 80, title: '备注' },
                        { title: '操作', width: 130, toolbar: '#currentTableBar', align: 'left', fixed:'right' }
                    ]],
                    done: function () {
                        DZUtils.setPageBtn();
                        layer.closeAll('loading');
                    }
                });
            }
        });

        /**
         * toolbar监听事件
         */
        table.on('toolbar(dict-table)', function (obj) {
            if (obj.event === 'import') {
                DZUtils.openModal({
                    title: '字典导入',
                    area: ['500px', '500px'],
                    content: window.rootPath + '/resource/ftsp/codelab/pages/dict/dictPrepareImport.html',
                })
            } else if (obj.event === 'export') {
                window.open('/dz-ftsp/codelab/dict/export', '_blank');
            }
        });

        // 监听每一行数据的按钮操作
        table.on('tool(dict-table)', function (obj) {
            var data = obj.data;
            if (obj.event === 'addBatch') {
                sessionStorage.setItem('editForm', JSON.stringify(data));
                DZUtils.openModal({
                    title: '字典批量新增',
                    content: window.rootPath + '/resource/ftsp/codelab/pages/dict/dictAddBatch.html'
                })
            } else if (obj.event === 'update') {
                data.id = data.dictId;
                sessionStorage.setItem('editForm', JSON.stringify(data));
                DZUtils.openModal({
                    title: '字典修改',
                    content: window.rootPath + '/resource/ftsp/codelab/pages/dict/dictUpdate.html'
                })
            } else if (obj.event === 'delete') {
                layer.confirm('确定删除该节点吗？', {title: '提示', icon: 3}, function (index) {
                    layer.load(2);
                    var delUrl = window.rootPath + '/dz-ftsp/codelab/dict/delete';
                    DZUtils.ajaxPost2(delUrl, { id: data.dictId }, function (res) {
                        if (res.errorNo === 0) {
                            layer.close(index);
                            layer.msg('删除成功', {title: '提示', icon: 1});
                            location.reload();
                        } else {
                            layer.alert(res.errorInfo);
                        }
                        layer.closeAll('loading');
                    });
                });
            }
        });

    }

    initTree();

});


