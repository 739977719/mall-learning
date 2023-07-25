var riskEditData = {
    exp: [],
    expCn: [],
    expBak: [],
    expCnBak: [],
    expPanelIdx: 0,
    riskList: [],
    riskBtn: false,
    riskIdx: 0
};
layui.use(['form', 'util', 'DZUtils', 'StUtils'], function () {
    var form = layui.form,
        util = layui.util,
        DZUtils = layui.DZUtils,
        StUtils = layui.StUtils,
        $ = layui.$;

    layer.load(2);

    var pattNum = /^num[0-9]$/i,
        pattSign = /^sign[0-9]$/i,
        signList = ["", "(", ")", "+", "-", "*", "/", "."];

    function initForm() {

        // 修改时设置初始值
        // var submitUrl = window.rootPath + '/dz-ftsp/stest/riskfactor/save';
        var submitUrl = StUtils.rootPath + '/dz-ftsp/stest/riskfactor/save';
        if (editFormData) {
            editFormData.delFlag = editFormData.delFlag ? '1' : '0';
            editFormData.orderNum = editFormData.orderNum || '9999';
            form.val('commonForm', editFormData);
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

        util.event('lay-active', {
            editEx: function (obj) {
                _offset = obj.offset();
                riskEditData.expPanelIdx = layer.open({
                    type: 1,
                    title: '编辑公式',
                    offset: ['10px', _offset.left + 'px'],
                    area: '900px',
                    content: $('#expressionPanel'),
                    cancel: function (layero, index) {
                        riskEditData.exp = [].concat(riskEditData.expBak);
                        riskEditData.expCn = [].concat(riskEditData.expCnBak);
                        typein("");
                    }
                });
            }
        });

        // 重新渲染表单
        form.render();

        // 初始化公式编辑框
        StUtils.getStDicts(['backTrackParam'], "st", function (res) {
            for (r of res['backTrackParam'])
                riskEditData.riskList.push({ "indexName": r.dictKey, "indexId": r.dictValue });
            initExpPanel();
        });
    }

    function initExpPanel() {

        $('#risklist').html(
            function () {
                var _html = [],
                    riskList = riskEditData.riskList;

                for (_i in riskList) {
                    _html.push('<li data-idx="' + _i + '">' + riskList[_i].indexName + '</li>');
                }
                return _html.join("");
            }
        );

        var _risk_lis = $('#risklist').find('li');
        var _riks_btns = $('.st-riskterm-btn');
        _risk_lis.on('click', function (e) {
            _this = $(this);

            _risk_lis.removeClass('layui-transfer-active');
            riskEditData.riskIdx = _this.data('idx');
            _this.addClass('layui-transfer-active');
            if (!riskEditData.riskBtn) {
                _riks_btns.removeClass('layui-btn-disabled');
                riskEditData.riskBtn = true;
            }

        });

        _riks_btns.on('click', function () {
            if (riskEditData.riskBtn) {
                var _rd = $(this).data('risk'),
                    _rdCn = $(this).data('riskcn'),
                    _i = riskEditData.riskIdx,
                    riskList = riskEditData.riskList;
                formula = _rd + "_" + riskList[_i].indexId;
                formulaCn = riskList[_i].indexName + "_" + _rdCn;

                setExpression(formula);
                setExpressionCn(formulaCn);
                _risk_lis.removeClass('layui-transfer-active');
                _riks_btns.addClass('layui-btn-disabled');
                riskEditData.riskBtn = false;
            }
        });

        $('.st-calculator-btn').on('click', function (e) {
            _e = $(this).attr('lay-calc');
            if (pattNum.test(_e)) typein(_e[3]);
            if (pattSign.test(_e)) typein(signList[_e[4]]);
            if (_e === "ok") {
                if (!StUtils.validateExpression4(riskEditData.exp)) {
                    layer.confirm('公式存在错误！！！是否放弃编辑？', { icon: 3, title: '提示' }, function (index) {

                        if (riskEditData.expBak.length > 0) {
                            riskEditData.exp = [].concat(riskEditData.expBak);
                            riskEditData.expCn = [].concat(riskEditData.expCnBak);
                        } else {
                            riskEditData.exp = [];
                            riskEditData.expCn = [];
                        }
                        typein("");
                        layer.close(index);
                        layer.close(riskEditData.expPanelIdx);
                    });

                    return;
                }
                else {
                    riskEditData.expBak = [].concat(riskEditData.exp);
                    riskEditData.expCnBak = [].concat(riskEditData.expCn);

                    layer.close(riskEditData.expPanelIdx);
                    return;
                }
            }
            if (_e === "del") {
                if (riskEditData.exp.length > 0) {
                    riskEditData.exp.pop();
                    riskEditData.expCn.pop();
                    $("#expressionCn").val(riskEditData.expCn.join(""));
                    $("#expression").val(riskEditData.expCn.join(""));
                }
                if (riskEditData.riskBtn) {
                    _risk_lis.removeClass('layui-transfer-active');
                    _riks_btns.addClass('layui-btn-disabled');
                    riskEditData.riskBtn = false;
                }
            }
        });

    }


    // 获取sessionStorage
    var editFormData = DZUtils.getStorage('editForm') || null;
    if (editFormData) {
        DZUtils.removeStorage('editForm');
        if (editFormData.expression) {
            riskEditData.exp = StUtils.splitExpression(editFormData.expression);
            riskEditData.expCn = StUtils.splitExpression(editFormData.expressionCn);
            riskEditData.expBak = [].concat(riskEditData.exp);
            riskEditData.expCnBak = [].concat(riskEditData.expCn);
        }
        initForm();
    }
    else {
        // DZUtils.ajaxGet(window.rootPath + '/dz-ftsp/stest/riskfactor/new', {}, function (res) {
        DZUtils.ajaxPost(StUtils.rootPath + '/dz-ftsp/stest/riskfactor/new', {}, function (res) {
            if (res.errorNo === 0) {
                editFormData = res.result || {};
                // 初始化表单
                initForm();
            }
        });
    }

    layer.closeAll('loading');

    function typein(s) {
        setExpression(s);
        setExpressionCn(s);
    }

    function setExpression(s) {
        riskEditData.exp.push(s);
        $("#expression").val(riskEditData.exp.join(""));
    }

    function setExpressionCn(s) {
        riskEditData.expCn.push(s);
        $("#expressionCn").val(riskEditData.expCn.join(""));
    }
});
