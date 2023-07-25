/**
 * description:工具方法
 */
 layui.define(["jquery", "table", "layer","form"], function (exports) {
    var $ = layui.$,
        form = layui.form,
        table = layui.table,
        layer = layui.layer;

    var DZUtils = {
        // 解析url querystring
        parseQueryString : function (url) {
            url = url || window.location.href;
            var quotIndex = url.indexOf('?');
            if(quotIndex === -1){
                return {};
            }
            var querystring = url.substring(quotIndex + 1);
            var obj = {};
            querystring.replace(/([^&#=]+)=([^&#=]+)/g, function(){
                obj[arguments[1]] = arguments[2];
            });
            return obj;
        },
        // 获取页面的按钮权限
        getPageBtnCodes : function (pageId) {
            var loginBtns = sessionStorage.getItem('loginBtns');
            var btnCodes = [];
            if(!loginBtns){
                return btnCodes;
            }
            try{
                var btnsData = JSON.parse(loginBtns);
                var pageBtns = btnsData[pageId] || [];
                btnCodes = pageBtns.map(function(item){
                    return item.btn_code;
                })

                return btnCodes;
            }catch(e){
                return btnCodes;
            }
        },
        // 设置页面按钮权限
        setPageBtn : function (){
            var pageId = this.parseQueryString().pageId || 0;
            var pageBtnCodes = this.getPageBtnCodes(pageId);
            var btnList = $('*[data-btn-code]');
            if(btnList.length && btnList.length >0){
                layui.each($('*[data-btn-code]'), function(index, item){
                    var code = $(item).attr('data-btn-code');
                    if(pageBtnCodes.indexOf(code) != -1){
                        $(item).removeClass('layui-hide');
                    }
                })
            }
        },
        // 校验特殊字符
        checkInputChar: function(value, fieldName) {
            var reg = /[<>\'\"]/;
            if(reg.test(value)){
                return fieldName + '不能包含特殊字符';
            }
        },
        // 校验数字
        checkNumber: function(value, fieldName) {
            var reg = /^\d+$/;
            if(!reg.test(value)) {
                return fieldName + '必须是数字';
            }
        },
        checkPhone: function(phone) {
            var reg = /^(13[0-9]|14[01456789]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
            return reg.test(phone);
        },
        // 校验密码
        checkPassword: function(password) {
           var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?])[A-Za-z\d@$!%*#?]{8,20}$/;
           return reg.test(password);
        },
        // 校验是否为空
        isNull: function(str) {
            return str == 'null' || str == 'undefined' || str == null
        },
        // 校验是否为非空或空字符串
        isNullOrEmpty: function(str) {
            return this.isNull(str) || (str + '').trim().length == 0
        },
        /**
         * 存储信息到sessionStorage
         * @param name  对象名
         * @param content  存入内容
         */
        setStorage :function (name, content) {
            if (!name) return
            if (typeof content !== 'string') {
                content = JSON.stringify(content)
            }
            window.sessionStorage.setItem(name, content)
        },
        /**
         * 获取sessionStorage信息
         * @param name  对象名
         */
        getStorage : function (name) {
            if (!name) return
            try {
                var content = JSON.parse(window.sessionStorage.getItem(name));
                if (typeof content === 'object'){
                    return content;
                }else{
                    return window.sessionStorage.getItem(name);
                }
            } catch (e) {
                return window.sessionStorage.getItem(name);
            }
        },
        /**
         * 删除sessionStorage信息
         * @param name  对象名
         */
        removeStorage :function(name) {
            if (!name) return
            window.sessionStorage.removeItem(name)
        },
        /**
         * 表单对象自动填入
         * @param obj  填入对象
         * @param form  填入页面id
         */
        loadFormData : function (fillData,form){
            var obj = null;
            if(typeof fillData === 'string'){
                obj = this.getStorage(fillData);
                this.removeStorage(fillData);
            }else if(typeof fillData === 'object'){
                obj = fillData;
            }
            if(!obj || !form){
                console.error("传入参数错误！");
                return;
            }
            var key,value,tagName,type,arr;
            for(x in obj){
                key = x;
                value = obj[x];
                $(form).find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
                    tagName = $(this)[0].tagName;
                    type = $(this).attr('type');
                    if(tagName=='INPUT'){
                        if(type=='radio'){
                            $(this).attr('checked',$(this).val()==value);
                        }else if(type=='checkbox'){
                            arr = value.split(',');
                            for(var i =0;i<arr.length;i++){
                                if($(this).val()==arr[i]){
                                    $(this).attr('checked',true);
                                    break;
                                }
                            }
                        }else{
                            $(this).val(value);
                        }
                    }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                        $(this).val(value);
                    }
                    
                });
            }
        },
        /**
         * ajax封装
         * @param url  请求url
         * @param params  请求参数
         * @param callback  回调函数
         */
        ajaxPost : function (url,params,callback,async){
            var paramData = null;
            var asyncFlag = true;
            try {
                paramData = JSON.stringify(params);
            } catch (error) {
                console.error("ajaxPost传入参数错误！");
                return;
            }
            if(typeof async === 'boolean'){
                asyncFlag = async
            }
            $.ajax({
                url:url,
                data:paramData,
                async:asyncFlag,
                type: 'post',
                contentType: 'application/json;charset=utf-8',
                dataType:'json',
                success:function(result){
                    //请求成功时
                    if(result.errorNo=='0'){
                        callback && callback(result);
                    }else if(result.errorNo=='-999'){
                        layer.msg('登陆状态失效，请退出重新登陆');
                    }else{
                        layer.alert(result.errorInfo);
                    }
                },
                error:function(){
                    layer.alert("接口请求出错！")
                }
            })
        },
        /**
         * ajax封装 (请求成功即处理回调)
         * @param url  请求url
         * @param params  请求参数
         * @param callback  回调函数
         */
        ajaxPost2 : function (url,params,callback,async){
            var paramData = null;
            var asyncFlag = true;
            try {
                paramData = JSON.stringify(params);
            } catch (error) {
                console.error("ajaxPost传入参数错误！");
                return;
            }
            if(typeof async === 'boolean'){
                asyncFlag = async
            }
            $.ajax({
                url:url,
                data:paramData,
                async:asyncFlag,
                type: 'post',
                contentType: 'application/json;charset=utf-8',
                dataType:'json',
                success:function(result){
                    callback && callback(result);
                },
                error:function(){
                    layer.alert("接口请求出错！")
                }
            })
        },
        promiseAjax: function(url, params, type) {
            var defer = $.Deferred();
            type = type || 'post';
            var ajaxParams = {
                url: url,
                type: type || 'post',
                dataType:'json',
            };

            if(type === 'post') { // post
                var paramData = null;
                try {
                    paramData = JSON.stringify(params);
                } catch (error) {
                    console.error("ajaxPost传入参数错误！");
                    return defer.reject();
                }
                ajaxParams.data = paramData;
                ajaxParams.contentType = 'application/json;charset=utf-8';
            }
            $.ajax(ajaxParams).done(function(res) {
                defer.resolve(res);
            })
            .fail(function(err) {
                defer.reject(err);
            });
            return defer;
        },
        /**
         * 打开模态框
         * @param opt  配置文件
         */
        openModal : function (opt){
            if(!opt.content){
                console.error("未传入弹窗地址！");
                return;
            }
            // 默认配置
            var customOpt = {
                title: '弹窗',
                type: 2,
                shade: 0.2,
                maxmin:true,
                shadeClose: false,
                area: ['80%', '80%'],
                content: '',
                cancel:function(){
                    sessionStorage.removeItem("addBtns");
                }
            }
            var options = $.extend({}, customOpt, opt);
            layer.open(options);
            return false;
        },
        /**
         * 表格渲染
         * @param opt  配置文件
         */
        customTable : function (opt){
            var _this = this;
            var doneFunction = opt.done || null;
            if(doneFunction){
                delete opt['done'];
            }
            var customOpt = {
                toolbar: '#headerToolbar',
                filterName: 'currentTableFilter',
                method: 'post',
                contentType: 'application/json;charset=utf-8',
                defaultToolbar:['filter'],
                request: {
                  pageName: 'pageNum',
                  limitName: 'pageSize'
                },
                parseData: function(res){
                    return {
                      "code": res.errorNo,
                      "data": res.result,
                      "count": res.total,
                      "msg": res.errorInfo
                    }
                },
                height:'full-180',
                limits: [10, 15, 20, 25, 50, 100],
                limit: 10,
                page: true,
                done: function(res, curr, count){
                    _this.setPageBtn();
                    doneFunction && doneFunction(res, curr, count);
                }
            }
            var options = $.extend({}, customOpt, opt);
            var cutomTalbeRender = table.render(options);
            table.on('sort('+options.filterName+')',function(){
                _this.setPageBtn();
            })
            return cutomTalbeRender;
        },
        /**
         * 加载下拉框
         * @param dictListOpts 下拉框配置文件 
         */
        loadListSelect: function(dictListOpts) {
            var defaultOpt = {
                el: '',                     // 绑定的元素id，eg:#demo
                dictList : [],              // 字典元素列表
                dictValue : 'dictValue',    // 下拉框选项value对应值
                dictName : 'dictName',      // 下拉框name对应值
                defaultVal : '',            // 下拉框默认值
                firstOption: -1             // 下拉框第一个默认值，-1代表直接取列表第一个值
            }
            var options = $.extend({}, defaultOpt, dictListOpts);
            var selectOpt = '';
            if(options.firstOption !==-1){
                selectOpt = options.firstOption  || '<option value="">全部</option>';
            }
            var dictList = options.dictList;
            for(var i in dictList) {
                selectOpt += "<option value='"+ String(dictList[i][options.dictValue]) +"'>" + dictList[i][options.dictName] + "</option>"
            }
            if(!options.el){
                console.error('select需传入绑定元素id');
            }
            $(options.el).append(selectOpt);
            if(!this.isNullOrEmpty(options.defaultVal)) {
                $(options.el).val(String(options.defaultVal));
            }
            form.render("select");
        },
        /**
         * 加载下拉框字典项
         * @param dictOpts 加载下拉框配置文件
         */
        loadSelectDict: function(dictOpts) {
            var _this = this;
            var defaultOpt = {
                el: '',                     // 绑定的元素id，eg:#demo
                dictUrl : window.rootPath + '/dz-ftsp/frame/sys/dict/get',  // 下拉框加载url
                dictParams: {},             // 下拉框请求参数
                dictType : '',              // 请求字典type
                dictList : [],              // 字典元素列表
                dictValue : 'dictValue',    // 下拉框选项value对应值
                dictName : 'dictKey',      // 下拉框name对应值
                defaultVal : '',            // 下拉框默认值
                firstOption: -1             // 下拉框第一个默认值，-1代表直接取列表第一个值
            }
            var options = $.extend({}, defaultOpt, dictOpts);
            if(options.dictList.length){
                this.loadListSelect(options);
            }else{
                var params = {}
                if(!this.isNullOrEmpty(options.dictType)){
                    params = {
                        dictType:options.dictType
                    }
                }
                params = $.extend({},params,options.dictParams)
                this.ajaxPost(options.dictUrl,params,function(res){
                    var dictList = res.result[0][options.dictType];
                    options.dictList = dictList;
                    _this.loadListSelect(options);
                });
            }
        },
        /** 加载表格字典项
        * @param routeStrList  字典项，多个类型以逗号分隔
        * @param sysCode       系统Code
        * @param callback  回调函数
        */
       loadTableDictList: function(routeStr,sysCode,callback) {
           var params = {
               routeStrList:[],
               sysCode:sysCode
           };
           routeStr = routeStr.split(",");
           routeStr.filter(function(v){
               params.routeStrList.push({routeStr:v})
           });
           this.ajaxPost(window.rootPath + '/dz-ftsp/frame/sys/dict/get',params,function(res){
               var dictList = res.result;
               callback && callback(dictList);
           });
       },
        /**
        * 匹配字典项
        * @param dictList  字典列表
        * @param dictVal  需要显示的name对应的value
        * @param dictMap  name和value的对应字段名
        */
       loadTableDict: function(dictList,dictVal,dictMap) {
           var dictMapDefault = {
               value:'dictValue',
               name:'dictKey'
           }
           var dictMap = $.extend({},dictMapDefault,dictMap)
           if(dictList && dictList.length){
                var dictName = '';
                for(var i=0;i<dictList.length;i++){
                    if(dictList[i][dictMap.value] == dictVal){
                        dictName = dictList[i][dictMap.name];
                        break;
                    }
                }
                return dictName;
           }
        },
        getFormValueByKey: function (obj, keyCode){
            var i = 0;
            for(key in obj){
                  if(keyCode == key){
                       return  Object.values(obj)[i];
                  }
                  i++;
            }
            return "";

       },
        /**
         * 初始化文件导入上传文件框
         * @param opt  配置文件
         */
        initUploadArea : function (opt,callback){
            var url = opt.url;
            // 默认配置
            var customOpt = {
                width: "420px",                   // 宽度
                height: "150px",                   // 高度
                itemWidth: "80px",                // 文件项的宽度
                itemHeight: "60px",               // 文件项的高度
                url: url,                         // 上传文件的路径
                fileType: ["pdf"],                // 上传文件的类型
                fileSize: 2097152,               // 上传文件的大小50M
                multiple: false,                  // 是否可以多个文件上传
                dragDrop: false,                  // 是否可以拖动上传文件
                tailor: false,                    // 是否可以裁剪图片
                del: true,                       // 是否可以删除文件
                finishDel: false,  				        // 是否在上传文件完成后删除预览
                /* 外部获得的回调接口 */
                onSelect: function (selectFiles, allFiles) {
                    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
                    if(selectFiles[0] && selectFiles[0].name){
                        $('.upload_file_text').text(selectFiles[0].name);
                    }
                },
                onDelete: function (file, files) {
                    // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
                    $('#fileImage').val('');
                },
                onSuccess: function (file, response) {
                    var res = JSON.parse(response);
                    // 文件上传成功的回调方法
                    if(res.errorNo !== 0){
                        layer.alert(res.errorInfo);
                        return;
                    }
                    var uploadRes = res.result;
                    $('.upload_file_text').text('');
                    callback && callback(uploadRes);
                },
                onFailure: function (file, response) {          // 文件上传失败的回调方法
                    console.info("此文件上传失败：");
                    console.info(file.name);
                }
            }
            var options = $.extend({}, customOpt, opt);
            $("#zyupload").zyUpload(options);
        },
        /**
         * 加载下拉框字典项
         * @param dictOpts 加载下拉框配置文件
         */
        loadSelectDictByRoutePath: function(dictOpts) {
            var _this = this;
            var defaultOpt = {
                el: '',                     // 绑定的元素id，eg:#demo
                dictUrl : window.rootPath + '/dz-ftsp/frame/sys/dict/getDict',  // 下拉框加载url
                dictParams: {},             // 下拉框请求参数
                dictType : '',              // 请求字典type
                dictList : [],              // 字典元素列表
                dictValue : 'dictValue',    // 下拉框选项value对应值
                dictName : 'dictKey',      // 下拉框name对应值
                defaultVal : '',            // 下拉框默认值
                firstOption: -1             // 下拉框第一个默认值，-1代表直接取列表第一个值
            }
            var options = $.extend({}, defaultOpt, dictOpts);
            if(options.dictList.length){
                this.loadListSelect(options);
            }else{
                var params = {}
                if(!this.isNullOrEmpty(options.dictType)){
                    params = {
                        dictType:options.dictType
                    }
                }
                params = $.extend({},params,options.dictParams)
                this.ajaxPost(options.dictUrl,params,function(res){
                    var dictList = res.result[0][options.dictType];
                    options.dictList = dictList;
                    _this.loadListSelect(options);
                });
            }
        },
        /** 加载表格字典项
         * @param routePathList  字典项，多个类型以逗号分隔
         * @param sysCode       系统Code
         * @param callback  回调函数
         */
        loadTableDictListByRoutePath: function(routePath,sysCode,callback) {
            var params = {
                routePathList:[],
                sysCode:sysCode
            };
            routePath = routePath.split(",");
            routePath.filter(function(v){
                params.routePathList.push({routePath:v})
            });
            this.ajaxPost(window.rootPath + '/dz-ftsp/frame/sys/dict/getDict',params,function(res){
                var dictList = res.result;
                callback && callback(dictList);
            });
        }
    };

    exports("DZUtils", DZUtils);
});
