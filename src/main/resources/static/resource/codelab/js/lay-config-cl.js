/**
 * layui扩展
 */
//  window.rootPath = (function (src) {
//    src = document.scripts[document.scripts.length - 1].src;
//    console.log(src.substring(0, src.lastIndexOf("/js")));
//    return src.substring(0, src.lastIndexOf("/js"));
// })();

layui.extend({
   formSelects: '{/}' + window.rootPath + '/js/lay-module/formSelects/formSelects-v4',
   ClUtils: '{/}' + window.rootPath + '/js/lay-module/utils/ClUtils'
});
