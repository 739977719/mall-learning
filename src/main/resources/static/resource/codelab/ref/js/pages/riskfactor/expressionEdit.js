var expressionData = {
    exp: [],
    expCn: [],
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

    // 获取sessionStorage
    // var editFormData = DZUtils.getStorage('editForm') || null;
    // if (editFormData) {
    //     DZUtils.removeStorage('editForm');
    // }
    var explist = [];

    layer.load(2);

    function initForm() {


        $('.st-calculator-btn').on('click', function (e) {
            _e = $(this).attr('lay-calc');
            if (_e === "ok") {
                explist = splitExpression(exp);

                $('#exp1').val(explist.join(""));
                if (!validateExpression(explist)) {
                    layer.msg('公式错误');
                }
                else {
                    layer.msg('公式正确');
                }
            }
            if (_e === "ex") {
                splitExpression(exp);
            }
        });

        // 重新渲染表单
        form.render();
    }


    initForm();


    layer.closeAll('loading');

    function validateExpression(obj) {
        var exp = obj || [],
            signSet = new Set(["+", "-", "*", "/"]),
            expStack = [],
            brackets = 0,
            items = 0,
            signs = 0;
        console.log("<<<<<<<<<<<<<<<<<<<<<");
        console.log(exp);
        for (var i = 0; i < exp.length; i++) {
            e = exp[i];
            console.log(e);

            if (e === "(") {
                brackets++;
                do {
                    i++, e = exp[i];
                    if (e == ")") {
                        brackets--;
                        if (brackets < 1) break;
                    }
                    else if (e == "("){
                        brackets++;
                    }
                    expStack.push(e);
                } while (i < exp.length);
                if (i >= exp.length) {
                    console.log("表达式错误==>括号未闭合配对");
                    return false;
                }
                
                if (!validateExpression(expStack)) return false;
                else {
                    expStack = [];
                    items++;
                }
            }
            else if (e === ")") {
                console.log("表达式错误==>括号未闭合配对,位置:" + i);
                return false;
            }
            else if (signSet.has(e)) {
                if (items > 0) signs++;
                else {
                    console.log("表达式错误==>" + e + "附近缺少运算项目,位置:" + i);
                    return false;
                }
                if (signs > 1) {
                    console.log("表达式错误==>" + e + "附近缺少运算项目,位置:" + i);
                    return false;
                }
            }
            else {
                items++;
                if (items == 2 && signs == 1) {
                    items = 1, signs = 0;
                }
                if (items > 1 && signs == 0) {
                    console.log("表达式错误==>" + e + "附近，缺少运算符");
                    return false;
                }
            }
        }
        if ((items == 1 && signs == 0) || (items == 2 && signs == 1)) {
            items = 0, signs = 0;
        }
        if (brackets != 0 ||
            items != 0 ||
            signs != 0) {
            console.log("表达式错误");
            return false;
        }
        console.log(">>>>>>>>>>>>>>>>>>>>>>>")
        return true;
    }
    function splitExpression(exp) {
        var exps = [],
            item = [],
            signs = new Set(["(", ")", "+", "-", "*", "/"]);
        var formula = exp.replace(/ /g, '');
        for (e of formula) {
            if (signs.has(e)) {
                if (item.length > 0) {
                    exps.push(item.join(""));
                    item = [];
                }
                exps.push(e);
            }
            else {
                item.push(e);
            }
        }
        if (item.length > 0) {
            exps.push(item.join(""));
        }
        return exps;
    }
});
