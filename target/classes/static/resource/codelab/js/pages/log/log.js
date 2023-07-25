var logData = {
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

        logData.customTable = DZUtils.customTable({
            elem: '#currentTableId',
            // url: window.rootPath + '/dz-ftsp/codelab/log/list',
            url: ClUtils.rootPath + '/dz-ftsp/codelab/log/list',
            cols: [[
                { field: 'operateCode', width: 200, title: '操作编号', sort: true },
                { field: 'operateName', width: 150, title: '操作名称', sort: true },
                { field: 'content', minWidth: 300, title: '内容' },
                { field: 'createTime', width: 160, title: '操作时间', sort: true },
                { field: 'createNickName', width: 100, title: '操作人', sort: true },
                { field: 'relateId', width: 100, title: '关联ID', sort: true }
            ]],
            autoSort: false,
            initSort: { field: 'createTime', type: 'desc' },
            where: { sort: 'create_time desc, id desc' },
            done: function () {
                layer.closeAll('loading');
            }
        });

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            //执行搜索重载
            logData.customTable = table.reload('currentTableId', {
                page: { curr: 1 },
                where: data.field
            }, 'data');
            return false;
        });

        // 监听排序操作
        table.on('sort(currentTableFilter)', function (data) {
            var orderType = ' ' + (data.type || '');
            logData.customTable = table.reload('currentTableId', {
                page: { curr: 1 },
                initSort: data,
                where: { sort: ClUtils.transCamelToLine(data.field) + orderType + ', id' + orderType }
            });
        });

    }

    // 加载表格
    initTable();

});


