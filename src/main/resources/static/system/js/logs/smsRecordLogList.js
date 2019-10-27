layui.use(['form','layer','laydate','table','laytpl'],function(){
    	var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;
    	
    	
        var endDate= laydate.render({
        	elem: '#beginTime',// 选择器结束时间
            type: 'date',
            min:"1970-1-1",// 设置min默认最小值
            });
        // 日期范围
        var startDate=laydate.render({
            elem: '#endTime',
            type: 'date',
            max:"2099-12-31",// 设置一个默认最大值
           
        });
    	
    	
    	form.on('select(selectSearch)', function(data){
        	clearSearch();
        	hiddenSearch()
        	if(data.value=='0'){
    	      	  $("#title").removeClass("layui-hide");
    	      	  $("#title").addClass("layui-show");
    	    }else if(data.value=='1'){
            	  $("#searchStatus").removeClass("layui-hide");
    	      	  $("#searchStatus").addClass("layui-show");
    	    }else if(data.value=='2'){
          	  $("#searchMessageType").removeClass("layui-hide");
    	      	 $("#searchMessageType").addClass("layui-show");
    	  }else if(data.value=='3'){
        	  	$("#searchTime").removeClass("layui-hide");
        	  	$("#searchTime").addClass("layui-show");
          }else if(data.value=='4'){
	      	  	$("#send_to").removeClass("layui-hide");
	    	  	$("#send_to").addClass("layui-show");
          }else if(data.value=='5'){
        	  	$("#receive_account_id").removeClass("layui-hide");
        	  	$("#receive_account_id").addClass("layui-show");
          }
         });
        
        function clearSearch(){
      	  $("#title").val("");
      	  $("#send_to").val("");
      	  $("#receive_account_id").val("");
    	  $("#beginTime").val("");
      	  $("#endTime").val("");
      	  $("#selectStatus").val("");
      	  $("#selectMessageType").val("");
      	}
        
       function hiddenSearch(){
    	  $("#title").addClass("layui-hide");
       	  $("#searchTime").addClass("layui-hide");
       	  $("#searchStatus").addClass("layui-hide");
       	  $("#searchMessageType").addClass("layui-hide");
       }

    // 新闻列表
    var tableIns = table.render({
        elem: '#smsRecordLogList',
        url : '/sms/findAllSmsRecordLogs',
        cellMinWidth : 95,
        page : true,
        method:'post',
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "smsRecordLogListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
           {field: 'title', title: '标题', width:200},
            {field: 'content', title: '内容',width:300, align:'center'},
            {field: 'send_to', title: '接受人账号',width:150 , align:'center'},
            {field: 'status', title: '状态', minWidth:100, align:"center",templet:function(item){
            	switch(item.status){
            	case 0:
            		return "失败";
            		break;
            	case 1:
            		return "成功";
            		break;
            	}
            }},
            {field: 'note', title: '备注',width:250, align:'center'},
            {field: 'template_id', title: '接受账号ID',width:100, align:'center'},
            {field: 'send_time', title: '发送时间',width:200, align:'center'},
            {field: 'message_type', title: '状态', minWidth:100, align:"center",templet:function(item){
            	switch(item.message_type){
            	case 0:
            		return "短信";
            		break;
            	case 1:
            		return "邮箱";
            		break;
            		
            	case 2:
            		return "站内信";
            		break;
            	}
            }},
           
            
        ]]
    });

    // 是否置顶
    form.on('switch(newsTop)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            if(data.elem.checked){
                layer.msg("置顶成功！");
            }else{
                layer.msg("取消置顶成功！");
            }
        },500);
    })

    // 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	var tempSelectSearch=$("#selectSearch").val();
    	if(tempSelectSearch==3){// 根据用户名搜索
    		if(($("#beginTime").val()==null||$("#beginTime").val()=='')&&($("#endTime").val()==null||$("#endTime").val()=='')){
    			layer.open({
    				  title: '提示'
    				  ,content: '开始时间和结束时间不能同时为空！'
    				}); 
    			return;
    		}else{
    			if(CompareDate($("#beginTime").val(),$("#endTime").val())){
    				layer.open({
      				  title: '提示'
      				  ,content: '开始时间不能大于结束时间！'
      				}); 
    			}
    		}
    		
    	}
    	
    	function CompareDate(d1,d2){
    	  return ((new Date(d1.replace(/-/g,"\/"))) >= (new Date(d2.replace(/-/g,"\/"))));
    	}
    	table.reload("smsRecordLogListTable",{
                page: {
                    curr: 1 // 重新从第 1 页开始
                },
                where: {
                	title: $("#title").val(),
                	send_to: $("#send_to").val(),
                	receiveAccountId: $("#receive_account_id").val(),
                	statusStr:  $("#selectStatus").val(),
                	beginTime : $("#beginTime").val(),
                	endTime : $("#endTime").val(),
                	messageTypeStr : $("#selectMessageType").val()
                	
                }
                	
            })
       
    });

    // 添加文章
    function addNews(edit){
        var index = layui.layer.open({
            title : "添加文章",
            type : 2,
            content : "newsAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".newsName").val(edit.newsName);
                    body.find(".abstract").val(edit.abstract);
                    body.find(".thumbImg").attr("src",edit.newsImg);
                    body.find("#news_content").val(edit.content);
                    body.find(".newsStatus select").val(edit.newsStatus);
                    body.find(".openness input[name='openness'][title='"+edit.newsLook+"']").prop("checked","checked");
                    body.find(".newsTop input[name='newsTop']").prop("checked",edit.newsTop);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        // 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addNews();
    })

    // 批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                tableIns.reload();
                layer.close(index);
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    // 列表操作
    table.on('tool(newsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ // 编辑
            addNews(data);
        } else if(layEvent === 'del'){ // 删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                
                    tableIns.reload();
                    layer.close(index);
            });
        } else if(layEvent === 'look'){ // 预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})