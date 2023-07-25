/**
 * 工具方法
 */
layui.define(['jquery', 'form', 'layer', 'upload', 'util'], function (exports) {
    var $ = layui.$,
        form = layui.form,
        layer = layui.layer,
        upload = layui.upload,
        device = layui.device(),
        util = layui.util,
        DZUtils = layui.DZUtils;

    var ClUtils = {
        CONST: {},
        /**
         * 驼峰命名转化为下划线命名，testName -> test_name
         */
        transCamelToLine: function (str) {
            return str.replace(/([A-Z])/g, '_$1').toLowerCase();
        },
        /**
         * 初始化上传组件
         *
         * @param configUrl  获取配置地址
         * @param formFilter 表单的layui-filter
         * @param uploadEl   上传组件的位置，如#upload
         * @param infoEl     消息显示的位置，如#uploadInfo
         * @param pathName   表单对应path的字段名
         * @param groupName  表单对应group的字段名
         * @param fileName   表单对应文件名的字段名
         * @param renderOpts 上传组件初始化配置
         */
        uploadConfig: {},
        initUpload: function (configUrl, formFilter, uploadEl, infoEl, pathName, groupName, fileName, renderOpts) {

            // 初始化
            function startInit(uploadConfig) {
                var defaultRenderOpts = {
                    elem: uploadEl,
                    url: window.rootPath + '/dz-ftsp/fastdfs/upload/file',
                    data: { groupName: uploadConfig.groupName },
                    auto: true,
                    size: uploadConfig.fileMaxSize / 1024,
                    drag: true,
                    choose: function (obj) {
                        obj.pushFile();
                        obj.preview(function (index, file) {
                            $(infoEl).html('已选择文件：' + file.name);
                        });
                    },
                    before: function (obj) {
                        obj.preview(function (index, file) {
                            $(infoEl).html('正在上传文件：' + file.name);
                        });
                    },
                    done: function (res, index, upload) {
                        var formData = {},
                            success = false
                        uploadFailInfo = '';
                        if (res.errorNo === 0 && res.result && res.result.length > 0) {
                            if (res.result[0].uploadSuccess === '0') {
                                success = true;
                            }
                            uploadFailInfo = res.result[0].uploadFailInfo || '';
                        }
                        if (success) {
                            $(infoEl).html('上传成功，文件名：' + res.result[0].originalFilename);
                            formData[pathName] = res.result[0].fileName;
                            formData[groupName] = res.result[0].groupName;
                            formData[fileName] = res.result[0].originalFilename;
                        } else {
                            layer.alert('上传失败' + (uploadFailInfo ? ('，原因：' + uploadFailInfo) : ''));
                            $(infoEl).html('上传失败' + (uploadFailInfo ? ('，原因：' + uploadFailInfo) : ''));
                            formData[pathName] = '';
                            formData[groupName] = '';
                        }
                        form.val(formFilter, formData);
                    },
                    error: function () {
                        layer.alert('上传失败，原因：其他');
                        $(infoEl).html('上传失败，原因：其他');
                        var formData = {};
                        formData[pathName] = '';
                        formData[groupName] = '';
                        form.val(formFilter, formData);
                    }
                };
                var options = $.extend({}, defaultRenderOpts, renderOpts);
                upload.render(options);
            }

            // 获取配置
            if (!configUrl && this.uploadConfig.groupName) {
                startInit(this.uploadConfig);
            } else {
                var url = configUrl || (window.rootPath + '/dz-ftsp/stest/upload/getConfig');
                DZUtils.ajaxPost(url, {}, function (res) {
                    if (res.errorNo === 0) {
                        if (!configUrl) {
                            this.uploadConfig = res.result;
                        }
                        startInit(res.result)
                    }
                });
            }

        },
        /**
         * 自适应高度
         *
         * @param autoEl  需要自适应高度的部分
         * @param heightGetter 高度计算方法，传入窗口高度，返回实际高度
         */
        autoHeight: function (autoEl, heightGetter) {
            var autoElement = $(autoEl);
            if (autoElement && autoElement.length > 0) {
                // 定义计算高度的方法
                function countHeight() {
                    var windowInnerHeight = window.innerHeight;
                    var height = windowInnerHeight;
                    if (heightGetter) {
                        height = heightGetter(windowInnerHeight);
                    }
                    autoElement.height(height);
                }
                // 初始运行一次
                countHeight();
                // 窗口大小改变时触发运行
                window.onresize = countHeight;
            }
        },
        loadTableDict: function (dictList, dictVal, dictMap) {
            dictVal = dictVal == null && dictVal !== 0 ? '' : dictVal + '';
            return DZUtils.loadTableDict(dictList, dictVal, dictMap);
        },
        /**
         * 获取工作平台字典
         *
         * @param dictPaths  字典路径列表
         * @param callback  回调函数
         */
        getStDicts: function (dictPaths, parent, callback) {
            if (dictPaths && dictPaths.length && callback) {
                // DZUtils.ajaxPost(window.rootPath + '/dz-ftsp/stest/dict/getDicts', { dictPaths, parent }, function (res) {
                DZUtils.ajaxPost(ClUtils.rootPath + '/dz-ftsp/stest/dict/getDicts', { dictPaths, parent }, function (res) {
                    if (res.errorNo === 0) {
                        callback(res.result);
                    }
                });
            }
        },
        /**
         * 获取工作平台字典节点
         *
         * @param dictPaths  字典路径列表
         * @param callback  回调函数
         */
        getStDictNodes: function (dictPaths, parent, callback) {
            if (dictPaths && dictPaths.length && callback) {
                DZUtils.ajaxPost(window.rootPath + '/dz-ftsp/stest/dict/getDictNodes', { dictPaths, parent }, function (res) {
                    if (res.errorNo === 0) {
                        callback(res.result);
                    }
                });
            }
        },
        regexPatterns: {
            idCard: /^(1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|82|[7-9]1)[0-9]{4}(((19|20)[0-9]{2}(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((19|20)(0[48]|[2468][048]|[13579][26])0229)|(20000229))[0-9]{3}[0-9Xx]$/,
            decimalWy: /^[0-9]+(\.[0-9]{1,6})?$/
        },
        limitChars: function (sourceStr, blankChar, limitLength) {
            if (sourceStr.length > limitLength) {
                return sourceStr;
            }
            var targetStr = sourceStr;
            while (targetStr.length < limitLength) {
                targetStr = blankChar + targetStr;
            }
            return targetStr;
        },
        downloadAttach: function (path, group, name) {
            name = encodeURI(encodeURI(name || path));
            var url = '/dz-ftsp/fastdfs/download/file/link?groupName=' + group + '&fileName=' + path + '&contentDisposition=attachment;filename=' + name;
            window.open(url, '_blank');
        },
        getLastDate: function () {
            var now = new Date().getTime(),
                d = new Date(now - 86400 * 1000);
            return util.toDateString(d, "yyyyMMdd");
        },
        moneyFormart: function (s, n) {
            if (s == "null" || s == null || s == "")
                return "-";
            _s = s.toString();    
            
            n = n > 0 && n <= 20 ? n : 2;
            var _sign = false;
            if (_s[0] == "-") { _sign = true; _s = _s.substring(1); }
            _s = parseFloat((_s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = _s.split(".")[0].split("").reverse(),
                r = _s.split(".")[1];
            t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            }
            t = t.split("").reverse().join("") + "." + r;
            if (_sign) return "-" + t;
            else return t;
        },
        amountFormart: function (s, n) {
            n = n > 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            return s;
        },
        dateFormart: function (s) {
            _s = new String(s);
            n = _s.length;
            return (n >= 4 ? _s.substr(0, 4) : '') + (n >= 6 ? '-' + _s.substr(4, 2) : '') + (n >= 8 ? '-' + _s.substr(6, 2) : '');
        },
        percentageFormart: function (s, n) {
            n = n > 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "") * 100).toFixed(n) + "%";
            return s;
        },
        ajaxGet: function (url, params, callback, async) {
            var paramData = null;
            var asyncFlag = true;
            try {
                paramData = JSON.stringify(params);
            } catch (error) {
                console.error("ajaxPost传入参数错误！");
                return;
            }
            if (typeof async === 'boolean') {
                asyncFlag = async
            }
            $.ajax({
                url: url,
                data: paramData,
                async: asyncFlag,
                type: 'get',
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                success: function (result) {
                    //请求成功时
                    if (result.errorNo == '0') {
                        callback && callback(result);
                    }
                },
                error: function () {
                    layer.alert("接口请求出错！")
                }
            })
        },
        rootPath: "http://127.0.0.1:15055"
    };

    exports('ClUtils', ClUtils);
});
