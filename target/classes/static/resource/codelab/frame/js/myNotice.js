// 通知窗口内容
var noticeDialogTemplate = '' +
    '<div style="padding: 30px">' +
    '   <div id="notice-tab" class="layui-tab">' +
    '       <ul id="notice-tab-title" class="layui-tab-title">' +
    '           <li class="layui-this" notice-type="1">通知<span id="notice-tab-type1-badge" class="layui-badge layui-bg-gray">0</span></li>' +
    '           <li notice-type="2">待办<span id="notice-tab-type2-badge" class="layui-badge layui-bg-gray">0</span></li>' +
    '           <div class="layui-inline" style="margin-left: 40px;">' +
    '               <button id="notice-refresh-btn" class="layui-btn layui-btn-sm"><i class="layui-icon">&#xe669;</i></button>' +
    '               <button id="notice-set-read-btn" class="layui-btn layui-btn-sm" style="margin-left: 10px;">全部设为已读</button>' +
    '           </div>' +
    '           <div class="layui-form" style="float: right">' +
    '               <div class="layui-inline">' +
    '                   <input id="notice-only-unread" type="checkbox" checked lay-skin="switch" lay-filter="onlyUnread" lay-text="只看未读未办|全部">' +
    '               </div>' +
    '           </div>' +
    '       </ul>' +
    '       <div class="layui-tab-content">' +
    '           <div id="notice-list1-content" class="layui-tab-item layui-show">' +
    '               <ul id="notice-list1"></ul>' +
    '           </div>' +
    '           <div id="notice-list2-content" class="layui-tab-item">' +
    '               <ul id="notice-list2"></ul>' +
    '           </div>' +
    '       </div>' +
    '   </div>' +
    '</div>';
// 通知列表
var noticeItemTemplate = '' +
    '<li id="notice-{{ d.id }}" related-mark="{{ d.relatedMark }}" class="notice-item">' +
    '    <div>' +
    '        <div class="notice-item-title layui-inline">' +
    '            <span class="notice-item-title-font">{{ d.noticeTitle }}</span>' +
    '        </div>' +
    '        <div class="layui-inline">' +
    '            {{# if (d.readStatus + \'\' === \'0\') { }}' +
    '               <span class="notice-item-title-read-badge layui-badge">未读</span>' +
    '            {{# } else { }}' +
    '               <span class="notice-item-title-read-badge layui-badge layui-bg-gray">已读</span>' +
    '            {{# } }}' +
    '            {{# if (d.noticeType + \'\' === \'2\') { }}' +
    '               {{# if (d.handleStatus + \'\' === \'0\') { }}' +
    '                   <span class="notice-item-title-handle-badge layui-badge">未完成</span>' +
    '               {{# } else { }}' +
    '                   <span class="notice-item-title-handle-badge layui-badge layui-bg-gray">已完成</span>' +
    '               {{# } }}' +
    '            {{# } }}' +
    '        </div>' +
    '       <div class="notice-item-time">{{ d.createTime }}</div>' +
    '    </div>' +
    '    <div class="notice-item-content">{{ d.noticeContentLimit50 }}</div>' +
    '</li>';
// 通知详情窗口
var noticeDetailTemplate = '' +
    '<div id="notice-detail">' +
    '   <form class="layui-form" action="">' +
    '       <div class="layui-form-item">' +
    '           <label class="layui-form-label">标题</label>' +
    '           <div class="layui-input-block"><p>{{ d.noticeTitle }}</p></div>' +
    '       </div>' +
    '       <div class="layui-form-item">' +
    '           <label class="layui-form-label">内容</label>' +
    '           <div class="layui-input-block"><p>{{ d.noticeContent }}</p></div>' +
    '       </div>' +
    '       {{# if (d.pageLink) { }}' +
    '       <div class="layui-form-item">' +
    '           <label class="layui-form-label">业务页</label>' +
    '           <div class="layui-input-block"><p><a href="javascript:;" id="notice-detail-page-link">点击前往</a></p></div>' +
    '       </div>' +
    '       {{# } }}' +
    '       {{# if (d.remark) { }}' +
    '       <div class="layui-form-item">' +
    '           <label class="layui-form-label">备注</label>' +
    '           <div class="layui-input-block"><p>{{ d.remark }}</p></div>' +
    '       </div>' +
    '       {{# } else if (d.handleWay + \'\' === \'2\' && d.handleStatus + \'\' === \'0\') { }}' +
    '       <div class="layui-form-item">' +
    '           <label class="layui-form-label">备注</label>' +
    '           <div class="layui-input-block">' +
    '               <textarea name="remark" class="layui-textarea"></textarea>' +
    '           </div>' +
    '       </div>' +
    '       <div class="layui-form-item">' +
    '           <div class="layui-input-block">' +
    '               <button class="layui-btn" lay-submit lay-filter="noticeDetail">完成待办</button>' +
    '           </div>' +
    '       </div>' +
    '       {{# } }}' +
    '   </form>' +
    '</div>';
// 暂存数据
var noticeData = {
    sysCode: '',
    onlyUnread: true,
    type1HasNew: false,
    type2HasNew: false,
    type1Count: 0,
    type2Count: 0,
    type1MaxId: 0,
    type2MaxId: 0,
    currentDetailIndex: -1
};
layui.use(['form', 'flow', 'laytpl', 'miniTab', 'DZUtils'], function () {
    var $ = layui.jquery,
        form = layui.form,
        flow = layui.flow,
        laytpl = layui.laytpl,
        miniTab = layui.miniTab,
        DZUtils = layui.DZUtils;

    // 获取子系统代码
    noticeData.sysCode = DZUtils.parseQueryString()['sys_code'] || 'frame';
    var userInfo = DZUtils.getStorage('userInfo') || null;

    // 定义点击详情事件
    function clickDetail(item) {
        var itemEl = $('#notice-' + item.id);
        item.handleStatus = itemEl.attr('handle-status') || (item.handleStatus + '');
        item.readStatus = itemEl.attr('read-status') || (item.readStatus + '');
        item.remark = itemEl.attr('remark') || item.remark || '';
        // 定义设为已读后的处理
        function finishSetRead() {
            var noticeDetailHtml = laytpl(noticeDetailTemplate).render(item);
            noticeData.currentDetailIndex = layer.open({
                type: 1,
                title: '通知详情',
                shade: 0.2,
                shadeClose: true,
                id: 'notice-detail-layer',
                area: '600px',
                content: noticeDetailHtml,
                success: function () {
                    // 配置业务页点击事件
                    if (item.pageLink) {
                        $('#notice-detail-page-link').click(function () {
                            // 设为已完成
                            if (item.handleWay + '' === '1' && item.handleStatus + '' === '0') {
                                DZUtils.ajaxPost('/dz-ftsp/frame/notice/finish', {sysCode: noticeData.sysCode, id: item.id}, function () {
                                    var relatedMark = itemEl.attr('related-mark');
                                    if (relatedMark) {
                                        // 有关联通知，一并修改
                                        $('li[related-mark=\'' + relatedMark + '\']').each(function() {
                                            $(this).attr('handle-status', '1');
                                            $(this).find('.notice-item-title-handle-badge').addClass('layui-bg-gray');
                                            $(this).find('.notice-item-title-handle-badge').html('已完成');
                                        })
                                    } else {
                                        // 无关联通知
                                        itemEl.attr('handle-status', '1');
                                        itemEl.find('.notice-item-title-handle-badge').addClass('layui-bg-gray');
                                        itemEl.find('.notice-item-title-handle-badge').html('已完成');
                                    }
                                });
                            }
                            // 关闭窗口
                            layer.closeAll();
                            noticeData.currentDetailIndex = -1;
                            // 获取菜单，打开页面
                            var menuEl;
                            $('.layuimini-menu-left a').each(function () {
                                var href = $(this).attr('layuimini-href');
                                if (href && href.startsWith(item.pageLink)) {
                                    menuEl = $(this);
                                    return false;
                                }
                            });
                            var url = menuEl ? menuEl.attr('layuimini-href') : (window.rootPath + item.pageLink);
                            url = url + (url.indexOf('?') >= 0 ? '&' : '?') + 'noticeId=' + item.id;
                            if (item.pageParam) {
                                url = url + '&' + item.pageParam;
                            }
                            var title = menuEl ? menuEl.attr('title') : item.remark || '任务详情';
                            miniTab.openNewTabByIframe({ href: url, title: title });
                        });
                    }

                    // 配置表单提交事件
                    if (item.handleWay + '' === '2' && item.handleStatus + '' === '0') {
                        form.on('submit(noticeDetail)', function (data) {
                            layer.confirm('确定完成该待办吗？', {icon: 3, title: '提示'}, function (index) {
                                var remark = data.field.remark;
                                DZUtils.ajaxPost('/dz-ftsp/frame/notice/finish', {sysCode: noticeData.sysCode, id: item.id, remark}, function () {
                                    layer.msg('操作成功');
                                    // 设置列表的已完成状态
                                    var relatedMark = itemEl.attr('related-mark');
                                    if (relatedMark) {
                                        // 有关联通知，一并修改
                                        $('li[related-mark=\'' + relatedMark + '\']').each(function() {
                                            $(this).attr('handle-status', '1');
                                            $(this).attr('remark', remark || '');
                                            $(this).find('.notice-item-title-handle-badge').addClass('layui-bg-gray');
                                            $(this).find('.notice-item-title-handle-badge').html('已完成');
                                        })
                                    } else {
                                        // 无关联通知
                                        $('#notice-' + item.id).attr('handle-status', '1');
                                        $('#notice-' + item.id).attr('remark', remark || '');
                                        var titleHandleBadge = $('#notice-' + item.id + ' .notice-item-title-handle-badge');
                                        titleHandleBadge.addClass('layui-bg-gray');
                                        titleHandleBadge.html('已完成');
                                    }
                                    // 关闭窗口
                                    if (noticeData.currentDetailIndex >= 0) {
                                        layer.close(noticeData.currentDetailIndex);
                                        noticeData.currentDetailIndex = -1;
                                    }
                                });
                            });
                            return false;
                        });
                    }

                    // 重新渲染一次，不然表单显示有问题
                    form.render();
                }
            });
        }

        if (item.readStatus + '' === '0') {
            DZUtils.ajaxPost('/dz-ftsp/frame/notice/setRead', {sysCode: noticeData.sysCode, id: item.id}, function (res) {
                // 设置列表的已读状态
                var badge = $('#notice-' + item.id + ' .notice-item-title-read-badge');
                badge.addClass('layui-bg-gray');
                badge.html('已读');
                itemEl.attr('read-status', '1');

                // 设置未读数量角标
                var unreadCount = noticeData['type' + item.noticeType + 'Count'] - 1;
                noticeData['type' + item.noticeType + 'Count'] = unreadCount;
                $('#notice-tab-type' + item.noticeType + '-badge').html(unreadCount + '');
                if (unreadCount > 0) {
                    $('#notice-tab-type' + item.noticeType + '-badge').removeClass('layui-bg-gray');
                } else {
                    $('#notice-tab-type' + item.noticeType + '-badge').addClass('layui-bg-gray');
                }

                var noticeCount = sessionStorage.getItem("noticeCount" + userInfo.id) || 0;
                sessionStorage.setItem("noticeCount" + userInfo.id, noticeCount - 1);

                // 打开详情窗口
                finishSetRead();
            });
        } else {
            // 打开详情窗口
            finishSetRead();
        }
    }

    // 配置点击事件，打开窗口
    $('#explore-my-notices').on('click', function () {
        var width = (document.documentElement.clientWidth - 200) * 0.8;
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shade: 0.2,
            shadeClose: true,
            id: 'explore-my-notices-layer',
            area: width + 'px',
            content: noticeDialogTemplate,
            success: function () {
                // 加载流容器
                function reloadFlow(noticeType) {
                    // 先删除原有的
                    $('#notice-list' + noticeType).remove();
                    var content = $('#notice-list' + noticeType + '-content');
                    content.unbind();
                    content.append('<ul id="notice-list' + noticeType + '"></ul>');
                    // 加载
                    noticeData['flow' + noticeType] = flow.load({
                        elem: '#notice-list' + noticeType,
                        scrollElem: '#notice-list' + noticeType,
                        end: '没有更多了',
                        done(page, next) {
                            var param = {sysCode: noticeData.sysCode, noticeType};
                            if (page > 1) {
                                param.maxId = noticeData['type' + noticeType + 'MaxId'];
                            }
                            if (noticeData.onlyUnread) {
                                param.type = '1';
                            }
                            DZUtils.ajaxPost('/dz-ftsp/frame/notice/listMy', param, function (res) {
                                if (res.result && res.result.list) {
                                    var pageHtml = '';
                                    if (res.result.list.length > 0) {
                                        noticeData['type' + noticeType + 'MaxId'] = res.result.list[res.result.list.length - 1].id;
                                    } else {
                                        noticeData['type' + noticeType + 'MaxId'] = 0;
                                    }
                                    if (page === 1) {
                                        // 第一页带上刷新提示
                                        noticeData['type' + noticeType + 'Count'] = res.result['type' + noticeType + 'Count'];
                                        noticeData['type' + noticeType + 'HasNew'] = false;
                                        pageHtml = pageHtml + '<li class="notice-list-tips layui-bg-orange" style="line-height: 30px;text-align: center;display: none;">你有最新未读的消息，请刷新查看</li>';
                                    }

                                    // 渲染模板，拼接html
                                    pageHtml = pageHtml + res.result.list.map(item => {
                                        // 列表上的内容限长50
                                        var noticeContentLimit50 = item.noticeContent || '';
                                        if (noticeContentLimit50.length > 50) {
                                            noticeContentLimit50 = noticeContentLimit50.substring(0, 50) + '...';
                                        }
                                        item.noticeContentLimit50 = noticeContentLimit50;
                                        item.relatedMark = item.relatedMark || '';
                                        return laytpl(noticeItemTemplate).render(item);
                                    }).join('\n');

                                    // 渲染html
                                    next(pageHtml, res.result.list.length >= 10);

                                    // 绑定点击详情事件
                                    res.result.list.forEach(item => {
                                        $('#notice-' + item.id).click(() => {
                                            clickDetail(item);
                                        });
                                    });

                                    // 更新刷新提示的状态
                                    if (!noticeData.type1HasNew && noticeData.type1Count !== res.result.type1Count) {
                                        noticeData.type1HasNew = true
                                        $('#notice-list1 .notice-list-tips').css('display', 'block');
                                    }
                                    if (!noticeData.type2HasNew && noticeData.type2Count !== res.result.type2Count) {
                                        noticeData.type2HasNew = true
                                        $('#notice-list2 .notice-list-tips').css('display', 'block');
                                    }

                                    // 更新未读的数量角标
                                    noticeData.type1Count = res.result.type1Count;
                                    noticeData.type2Count = res.result.type2Count;
                                    $('#notice-tab-type1-badge').html(res.result.type1Count + '');
                                    $('#notice-tab-type2-badge').html(res.result.type2Count + '');
                                    if (res.result.type1Count > 0) {
                                        $('#notice-tab-type1-badge').removeClass('layui-bg-gray');
                                    } else {
                                        $('#notice-tab-type1-badge').addClass('layui-bg-gray');
                                    }
                                    if (res.result.type2Count > 0) {
                                        $('#notice-tab-type2-badge').removeClass('layui-bg-gray');
                                    } else {
                                        $('#notice-tab-type2-badge').addClass('layui-bg-gray');
                                    }
                                }
                            });
                        }
                    });
                }

                // 开始加载
                reloadFlow(1);
                reloadFlow(2);

                // 绑定刷新事件
                $('#notice-refresh-btn').click(function () {
                    // 获取当前的活动tab
                    var noticeType = $('#notice-tab-title .layui-this').attr('notice-type');
                    reloadFlow(noticeType);
                });

                // 绑定一键已读事件
                $('#notice-set-read-btn').click(function () {
                    // 获取当前的活动tab
                    var noticeType = $('#notice-tab-title .layui-this').attr('notice-type');
                    var count = Number(noticeData['type' + noticeType + 'Count']);
                    if (count <= 0) {
                        layer.msg('没有未读的记录');
                        return;
                    }
                    var noticeTypeName = noticeType === '1' ? '通知' : '待办';
                    layer.confirm('确定将所有' + noticeTypeName + '都设为已读吗？', {icon: 3, title: '提示'}, function (index) {
                        DZUtils.ajaxPost('/dz-ftsp/frame/notice/setAllRead', {sysCode: noticeData.sysCode, noticeType}, function () {
                            layer.msg('操作成功');

                            sessionStorage.removeItem("noticeCount" + userInfo.id);

                            // 未读总数设为0，列表所有未读标签设为已读
                            noticeData['type' + noticeType + 'Count'] = 0;
                            $('#notice-tab-type' + noticeType + '-badge').html('0');
                            $('#notice-list' + noticeType + ' li').each(function () {
                                $(this).attr('read-status', '1');
                                var titleReadBadge = $(this).find('.notice-item-title-read-badge');
                                if (!titleReadBadge.hasClass('layui-bg-gray')) {
                                    titleReadBadge.addClass('layui-bg-gray');
                                    titleReadBadge.html('已读');
                                }
                            });
                        });
                    });
                });

                // 绑定开关事件
                form.on('switch(onlyUnread)', function () {
                    noticeData.onlyUnread = this.checked;
                    reloadFlow(1);
                    reloadFlow(2);
                });

                // 重新渲染一次，不然开关显示有问题
                form.render();
            }
        });
    });



});


