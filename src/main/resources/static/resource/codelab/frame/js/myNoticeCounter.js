// 暂存数据
var noticeCounterData = {
    sysCode: '',
    interval: 15000 // 间隔，15秒
};
layui.use(['DZUtils'], function () {
    var $ = layui.jquery,
        DZUtils = layui.DZUtils;

    // 获取子系统代码
    noticeCounterData.sysCode = DZUtils.parseQueryString()['sys_code'] || 'frame';

    // 计时任务
    function countMyUnread() {
        var param = {sysCode: noticeCounterData.sysCode};
        noticeAjaxPost('/dz-ftsp/frame/notice/countMyUnread', param, function (res) {
            if (res.errorNo == '0' && res.result) {
                var notice = res.result.notice || 0;
                var todo = res.result.todo || 0;
                var userId = res.result.loginUser.id || 0;
                var noticeCount = sessionStorage.getItem("noticeCount" + userId) || 0;
                var count = notice + todo;

                if (count > noticeCount) {
                    playSound();
                }
                if (count > 0) {
                    $('#notice-badge-dot').html('<span class="layui-badge-dot"></span>');
                    $('#notice-badge').removeClass('layui-bg-gray');
                    $('#notice-badge').html(count > 99 ? '99+' : (count + ''));
                } else {
                    $('#notice-badge-dot').html('');
                    $('#notice-badge').addClass('layui-bg-gray');
                    $('#notice-badge').html('0');
                }

                sessionStorage.setItem("noticeCount" + userId, count);

            } else if (res.errorNo == '-999') {
                clearInterval(loop);
                layer.alert('登陆状态失效，重新登陆', {icon: 3, title: '提示'}, function () {
                    $.post('/dz-ftsp/logout', function () {
                        var pcLocation = document.location;
                        var pcHost = pcLocation.protocol + '//' + pcLocation.host;
                        var loginPath = DZUtils.getStorage("loginPath") || null;
                        if (!loginPath) {
                            loginPath = '/dz-ftsp/login_page';
                        }
                        sessionStorage.clear();
                        window.location = pcHost + loginPath;
                    })
                });
            } else {
                clearInterval(loop);
                console.log("通知接口异常");
                // layer.alert(res.errorInfo);
            }
        });
    }

    // 先运行一次
    // countMyUnread();

    // 启动计时器
    // var loop = setInterval(countMyUnread, noticeCounterData.interval);


    var playSound = function () {
        var borswer = window.navigator.userAgent.toLowerCase();
        if (borswer.indexOf('ie') >= 0) {
            var strEmbed = "<embed name='embedPlay' src='/resource/ftsp/frame/images/notice/noticeAudio.wav' autosrart='true' hidden='true' loop='false'></embed>";
            if ($("body").find("embed").length <= 0) {
                $("body").append(strEmbed);
                var embed = document.embedPlay;
                embed.volume = 100;
            }
        } else {
            var strAudio = "<audio id='audioPlay' src='/resource/ftsp/frame/images/notice/noticeAudio.wav' autoplay hidden='true'></audio>"
            if ($("body").find("audio").length <= 0) {
                $("body").append(strAudio);
            }
            var audio = document.getElementById("audioPlay");
            audio.play();
        }
    }

    function noticeAjaxPost(url, params, callback, async) {
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
            type: 'post',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: function (result) {
                callback && callback(result);
            },
            error: function () {
                console.log("获取通知列表接口异常");
            }
        })
    }

});


