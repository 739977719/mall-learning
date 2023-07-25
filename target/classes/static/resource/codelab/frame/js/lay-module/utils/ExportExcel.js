/**
 * description:工具方法
 */
layui.define(["jquery", "DZUtils", "table"], function (exports) {
  var $ = layui.$,
      table = layui.table,
      DZUtils = layui.DZUtils;

  var ExportExcel = {
       /**
       * 初始化导出模态框
       */
      initModal : function (cutomTalbe,tableObj,exportParam){
        if(tableObj.event === 'export'){
            if(!cutomTalbe){
                console.error("未传入table config！");
                return;
            }
            var cutomTalbeData = cutomTalbe.config;
            var exportModalData = {
                exportParam:exportParam,
                formTitles:cutomTalbeData.cols[0],
                pages:cutomTalbeData.page.pages,
                limit:cutomTalbeData.page.limit,
                searchParam:cutomTalbeData.where
            }
            DZUtils.setStorage('exportModalData',exportModalData);
            DZUtils.openModal({
                title: '表格导出',
                content: window.rootPath + '/resource/ftsp/frame/pages/pagehelper/exportmodal.html'
            })
        }
      }
  };

  exports("ExportExcel", ExportExcel);
});
