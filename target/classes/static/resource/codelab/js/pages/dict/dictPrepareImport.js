layui.use(['form', 'upload', 'DZUtils', 'ClUtils'], function () {
    var form = layui.form,
        upload = layui.upload,
        DZUtils = layui.DZUtils,
        ClUtils = layui.ClUtils,
        $ = layui.$;

    layer.load(2);

    function initForm () {

        var submitUrl = window.rootPath + '/dz-ftsp/codelab/dict/prepareImport';

        // 提交事件
        form.on('submit(saveBtn)', function (data) {
            if (!data.field.filePath || data.field.filePath === '' || !data.field.fileGroup || data.field.fileGroup === '') {
                layer.msg('请上传文件');
                return false;
            }
            layer.load(2);
            DZUtils.ajaxPost2(submitUrl, data.field, function (res) {
                if (res.errorNo === 0) {
                    sessionStorage.setItem('editForm', JSON.stringify(res.result));
                    var iframeIndex = parent.layer.getFrameIndex(window.name);
                    var parentLayer = parent.layer;
                    parentLayer.close(iframeIndex);
                    parentLayer.open({
                        title: '字典导入确认',
                        type: 2,
                        area: [ '700px', '500px' ],
                        content: window.rootPath + '/resource/ftsp/codelab/pages/dict/dictConfirmImport.html'
                    });
                } else {
                    layer.alert(res.errorInfo);
                }
                layer.closeAll('loading');
            });
        });

        // 重新渲染表单
        form.render();

    }

    // 初始化上传组件
    ClUtils.initUpload('', 'commonForm', '#filePathUpload', '#filePathInfo', 'filePath', 'fileGroup', 'fileName', { exts: 'json' });

    initForm();

    layer.closeAll('loading');
});
