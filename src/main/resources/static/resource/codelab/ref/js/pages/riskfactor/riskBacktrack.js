var riskBacktrackData = {
    customTable: '',
    dicts: {}
};
layui.use(['form', 'table', 'laydate', 'DZUtils', 'StUtils'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        DZUtils = layui.DZUtils,
        StUtils = layui.StUtils;

    layer.load(2);

    laydate.render({
        elem: '#ocDate',
        format: 'yyyyMMdd',
        type: 'date'
    });
    $('#ocDate').val(StUtils.getLastDate());

    var evenPatt = /^clickB[123]$/i;

    function initTable() {

        riskBacktrackData.customTable = DZUtils.customTable({
            elem: '#currentTableId',
            toolbar: '#headerToolbar',
            method: 'post',
            // url: window.rootPath + '/dz-ftsp/stest/riskfactor/getBack',
            url: StUtils.rootPath + '/dz-ftsp/stest/riskfactor/getBack',
            page: false,
            limit: 999999,
            cols: [
                [
                    { field: 'riskId', width: 150, title: '风险因子编号', hide: true, rowspan: 2 },
                    { field: 'riskName', minWidth: 350, title: '压力情景参数', fixed: 'left', rowspan: 2 },
                    { title: '未来1个月', align: 'center', colspan: 2 },
                    { title: '未来2个月', align: 'center', colspan: 2 },
                    { title: '未来3个月', align: 'center', colspan: 2 },
                ],
                [
                    {
                        field: 'backValue1', width: 130, event: 'clickB1', style: 'cursor: pointer;', title: '回溯值', align: 'center',
                        templet: function (d) { return (isWarn(d.backValue1, d.threshold1)) ? '<span style="color:red;">' + floatFormart(d.backValue1, 4) + '</span>' : floatFormart(d.backValue1, 4); }
                    },
                    {
                        field: 'threshold1', width: 130, title: '阈值', align: 'center',
                        templet: function (d) { return (isWarn(d.backValue1, d.threshold1)) ? '<span style="color:red;">' + floatFormart(d.threshold1, 4) + '</span>' : floatFormart(d.threshold1, 4); }
                    },
                    {
                        field: 'backValue2', width: 130, event: 'clickB2', style: 'cursor: pointer;', title: '回溯值', align: 'center',
                        templet: function (d) { return (isWarn(d.backValue2, d.threshold2)) ? '<span style="color:red;">' + floatFormart(d.backValue2, 4) + '</span>' : floatFormart(d.backValue2, 4); }
                    },
                    {
                        field: 'threshold2', width: 130, title: '阈值', align: 'center',
                        templet: function (d) { return (isWarn(d.backValue2, d.threshold2)) ? '<span style="color:red;">' + floatFormart(d.threshold2, 4) + '</span>' : floatFormart(d.threshold2, 4); }
                    },
                    {
                        field: 'backValue3', width: 130, event: 'clickB3', style: 'cursor: pointer;', title: '回溯值', align: 'center',
                        templet: function (d) { return (isWarn(d.backValue3, d.threshold3)) ? '<span style="color:red;">' + floatFormart(d.backValue3, 4) + '</span>' : floatFormart(d.backValue3, 4); }
                    },
                    {
                        field: 'threshold3', width: 130, title: '阈值', align: 'center',
                        templet: function (d) { return (isWarn(d.backValue3, d.threshold3)) ? '<span style="color:red;">' + floatFormart(d.threshold3, 4) + '</span>' : floatFormart(d.threshold3, 4); }
                    },
                    { field: 'expression', title: '公式', hide: true }
                ]
            ],
            autoSort: false,
            done: function (res) {
                layer.closeAll('loading');
            }
        });
        _s = "[lay-filter=LAY-table-" + riskBacktrackData.customTable.config.index + "]";
        _t = $(_s).find(".layui-table-tool-self");
        _t.html("");

        /**
        * toolbar监听事件
        */
        table.on('toolbar(currentTableFilter)', function (obj) {
            if (obj.event === 'export') {
                //var _url = window.rootPath + '/dz-ftsp/stest/riskfactor/export';
                var _url = StUtils.rootPath + '/dz-ftsp/stest/riskfactor/export';
                _param = obj.config.where;
                i = 0;
                for (_p in _param) {
                    if (i < 1) _url = _url + "?" + _p + "=" + _param[_p];
                    else _url = _url + "&" + _p + "=" + _param[_p];
                    i++;
                }

                var alink = document.createElement("a");
                alink.href = _url;
                document.body.appendChild(alink);
                alink.click();
                document.body.removeChild(alink);
            }
        });

        // 监听每一行数据的按钮操作
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (evenPatt.test(obj.event)) {
                var expDict = $.parseJSON(data.expression);
                layer.open({
                    type: 1,
                    title: "风险因子回溯明细",
                    area: ['500px', '300px'],
                    offset: 'l',
                    content: function () {
                        var _html = [
                            '<table class="layui-table">',
                            '<tr><td colspan="2"><span style="font-weight:bold">' + data.riskName + function () {
                                if (obj.event == "clickB1") return "</span> - 未来1个月";
                                if (obj.event == "clickB2") return "</span> - 未来2个月";
                                if (obj.event == "clickB3") return "</span> - 未来3个月";
                            }() + '</td></tr>',
                            '<tr><td>回溯公式</td><td>' + expDict.expCn + '</td></tr>',
                            '<tr><td>参数公式</td><td>' + function () {
                                if (obj.event == "clickB1")
                                    return expDict.exp1;
                                if (obj.event == "clickB2")
                                    return expDict.exp2;
                                if (obj.event == "clickB3")
                                    return expDict.exp;
                            }() + '</td></tr>',
                            '</table>'];
                        return _html.join("");
                    }()
                });
            }
        });

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            //执行搜索重载
            riskBacktrackData.customTable = table.reload('currentTableId', {
                where: data.field,
                done: function (res) {
                    _s = "[lay-filter=LAY-table-" + riskBacktrackData.customTable.config.index + "]";
                    _t = $(_s).find(".layui-table-tool-self");
                    _t.html("");

                    _colspan = $("[data-key='1-0-2']").attr("colspan");
                    if (_colspan == "1")
                        $("[data-key='1-0-2']").attr("colspan", "2");
                }
            }, 'data');
            return false;
        });
    }

    // 加载表格
    initTable();

    // 格式化数值
    function floatFormart(s, n) {
        n = n > 0 && n <= 20 ? n : 2;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "%";
        return s;
    }

    function isWarn(v, t) {
        _v = Math.abs((v) ? parseFloat(v + "") : 0);
        _t = Math.abs((t) ? parseFloat(t + "") : 0);
        return _v > _t;
    }
});


