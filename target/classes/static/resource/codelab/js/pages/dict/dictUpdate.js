layui.use(['form', 'DZUtils'], function () {
    var form = layui.form,
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
        var submitUrl = window.rootPath + '/dz-ftsp/codelab/dict/update';
        if (editFormData) {
            form.val('commonForm', editFormData);
            $('#nodePath').html(editFormData.nodePath || '');
            $('#nodeKey').html(editFormData.nodeKey || '');
        }

        // 提交事件
        form.on('submit(saveBtn)', function (data) {
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
