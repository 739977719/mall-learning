layui.use(['form', 'table', 'DZUtils'], function () {
    var $ = layui.jquery,
        form = layui.form,
        DZUtils = layui.DZUtils;
			
			function setExportModalData(){
				if( $('#frame-export-checked').length > 0 ){
					var exportModalData = DZUtils.getStorage('exportModalData');
					//动态添加  表头信息
					var formTitles = exportModalData.formTitles;
					console.log(formTitles);
					var html=''
					for(var j=0,len=formTitles.length; j<len; j++){
					        if(formTitles[j].title == '操作' || formTitles[j].exportShow == false){
					            continue;
					        }
							html +='<input type="checkbox" name="exportColumns" value="'+ formTitles[j].title +'" title="'+ formTitles[j].title +'" checked="checked" >';
					}

					$('#frame-export-checked').html(html);
					form.render("checkbox");
					//默认文件名
					$('#fileName').val(getCurrentDate(new Date()));
					//页码设置
					$('#toPageNum').val(exportModalData.pages).attr("oninput", "if (value > " + exportModalData.pages +") value = " + exportModalData.pages + "; if (value < 1) value = ''");
					$('#pageSize').val(exportModalData.limit);
				}
			}

            function getCurrentDate(date){
                var y = date.getFullYear();
                var m = date.getMonth()+1;
                var d = date.getDate();
                var h = date.getHours();
                var min = date.getMinutes();
                var s = date.getSeconds();
                var str=y+''+(m<10?('0'+m):m)+''+(d<10?('0'+d):d)+''+(h<10?('0'+h):h)+''+(min<10?('0'+min):min)+''+(s<10?('0'+s):s);
                console.log(str);
                return str;
            }

            function getSelectColumn(exportModalData){
                var formTitles = exportModalData.formTitles;
                columns = [];
                $("input[name='exportColumns']:checked").each(function(){
                    for(var j=0,len=formTitles.length; j<len; j++){
                          if(formTitles[j].title == $(this).val()){
                               var col = {
                                    "title": formTitles[j].title,
                                    "field": formTitles[j].field,
                                    "expWidth": formTitles[j].expWidth || 3700,
                                    "type": formTitles[j].expType || "string",
                                    "dictType": formTitles[j].expDictType || "",
                                    "pattern": formTitles[j].pattern || "#",
                                    "customDict": formTitles[j].customDict || "", // 自定义导出字典信息

                               }
                               columns.push(col);
                          }
                    }
                });
                return columns;
            }

			function submitData(){
				form.on('submit(saveBtn)', function (data) {
					console.log(data.field);
					var field = data.field;
					
					if (field.fromPageNum > field.toPageNum) {
					    layer.msg("起始页数值不能大于末尾页", {icon:5, anim: 6});
					    return;
                    }

                    var exportModalData = DZUtils.getStorage('exportModalData');
                    console.log(exportModalData);

                    //获取选中的columns
                    var select_columns = getSelectColumn(exportModalData);

                    //拼接exp_json
					var exp_json = {
						"fileName": field.fileName,
						"pageSize": field.pageSize,
						"fromPageNum": field.fromPageNum,
						"toPageNum": field.toPageNum,
						"listControllerClassName": exportModalData.exportParam.listControllerClassName,
						"listControllerMethodName": exportModalData.exportParam.listControllerMethodName,
						"url": exportModalData.exportParam.url,
						"queryParam": exportModalData.searchParam,
						"columns": select_columns
				    }
                    console.log(exp_json);
                    layui.layer.load();
                    DZUtils.ajaxPost(exp_json.url,exp_json,function(data){
                            DZUtils.removeStorage('exportModalData');

                           // parent.layer.close(parent.layer.getFrameIndex(window.name));
                           // window.open(window.rootPath +'/dz-ftsp/fastdfs/download/file/link?fileName='+data.result.fileName+"&groupName="+data.result.groupName
                           //     +"&contentType=application/msexcel;charset=UTF-8&contentDisposition=attachment;filename="+ exp_json.fileName +".xls")
                           open(window.rootPath +'/dz-ftsp/fastdfs/download/file/link?fileName='+data.result.fileName+"&groupName="+data.result.groupName
                                                                +"&contentType=application/msexcel;charset=UTF-8&contentDisposition=attachment;filename="+ exp_json.fileName +".xls");
                           layui.layer.closeAll();
                        layer.alert('导出成功', {
                            closeBtn: 0,
                            icon: 1,
                            btn: ['确定'],
                            yes: function () {
                                var iframeIndex = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(iframeIndex);
                            }

                        });
                    });
				});
			}

            function open(url){
                try{
                    var elemIf = document.createElement("iframe");
                    elemIf.src=url;
                    elemIf.style.display="none";
                    document.body.appendChild(elemIf);
                }catch(e){
                    alert("下载异常！");
                }
            }

			$(function(){
				setExportModalData();
				submitData();
    	    })
  
  });
  