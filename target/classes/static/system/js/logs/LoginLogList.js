layui.use(['form','layer','laydate','table','laytpl'],function(){
    	var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;
    	
    	
        var endDate= laydate.render({
        	elem: '#end',//选择器结束时间
            type: 'date',
            min:"1970-1-1",//设置min默认最小值
        });
        //日期范围
        var startDate=laydate.render({
            elem: '#start',
            type: 'date',
            max:"2099-12-31",//设置一个默认最大值
        });
    	
    	
    	form.on('select(selectSearch)', function(data){
        	clearSearch();
        	hiddenSearch()
        	if(data.value=='0'){
    	      	  $("#user_name").removeClass("layui-hide");
    	      	  $("#user_name").addClass("layui-show");
    	      	
            }else if(data.value=='1'){
            	  
            	  $("#searchTime").removeClass("layui-hide");
    	      	  $("#searchTime").addClass("layui-show");
    	      	
            }else if(data.value=='2'){
          	  
          	 	 $("#searchStutas").removeClass("layui-hide");
    	      	 $("#searchStutas").addClass("layui-show");
    	      	
          }else if(data.value=='3'){
          	  
       	 	 $("#user_id").removeClass("layui-hide");
 	      	 $("#user_id").addClass("layui-show");
 	      	
       }
         });
        
        function clearSearch(){
      	  $("#user_name").val("");
      	  $("#user_id").val("");
    	  $("#start").val("");
      	  $("#end").val("");
      	  $("#selectStutas").val("");
      	}
        
       function hiddenSearch(){
    	  $("#user_name").addClass("layui-hide");
    	  $("#user_id").addClass("layui-hide");
       	  $("#searchTime").addClass("layui-hide");
       	  $("#searchStutas").addClass("layui-hide");
       }
       
       


    //新闻列表
    var tableIns = table.render({
        elem: '#sysLoginLogList',
        url : '/sys/log/findAllLoginSysLogs',
        cellMinWidth : 95,
        page : true,
        method:'post',
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "sysLoginLoginLogListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'user_id', title: '用户编号', width:100},
            {field: 'user_name', title: '用户姓名', align:'center'},
            {field: 'log_memo', title: '备忘录', align:'center'},
            {field: 'log_ip', title: '日子IP地址', align:'center'},
            {field: 'os', title: '操作系统',width:100, align:'center'},
            {field: 'browser', title: '浏览器',width:200, align:'center'},
            {field: 'status', title: '状态', Width:80, align:"center",templet:function(item){
            	switch(item.status){
            	case 0:
            		return "失败";
            		break;
            	case 1:
            		return "成功";
            		break;
            	
            	}
            }},
            
        ]]
    });

    //是否置顶
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

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	

    	var tempSelectSearch=$("#selectSearch").val();
    	
    	var temStatus=$("#selectStutas").val();
    	
    	var user_name=$("#user_name").val();
    	
    	
    	
    	
    	if(tempSelectSearch==1){//根据用户名搜索
    		if(($("#start").val()==null||$("#start").val()=='')&&($("#end").val()==null||$("#start").val()=='')){
    			layer.open({
    				  title: '提示'
    				  ,content: '开始时间和结束时间均不能为空！'
    				}); 
    			
    			return;
    		}else{
    			if(CompareDate($("#start").val(),$("#end").val())){
    				layer.open({
      				  title: '提示'
      				  ,content: '开始时间不能大于结束时间！'
      				}); 
    				return;
    			}
    		}
    		
    	}
    	
    	
    	
    	function CompareDate(d1,d2){
    	  return ((new Date(d1.replace(/-/g,"\/"))) >= (new Date(d2.replace(/-/g,"\/"))));
    	}
        	
        	/*if(temStatus==null){
        		temStatus=-1;
        	}
    		
    		
    		if(tempSelectSearch==0){//根据用户名搜索
    			
    			temStatus=-1;
    		}else if(tempSelectSearch==1){//根据时间搜索
    			
    			temStatus=-1;
    		}else if(tempSelectSearch==2){//根据状态搜索
    			
    		}*/
    		
    		
    		//alert(user_name);
    		
    		

        	
        	
        	
            table.reload("sysLoginLoginLogListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	user_name: user_name, 
                	statusStr: temStatus,                	
                	start : $("#start").val(),
                	end : $("#end").val(),
                	userIdStr :  $("#user_id").val()
                	
                }
                	
            })
       
    });

    //添加文章
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
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addNews();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(newsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addNews(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                    tableIns.reload();
                    layer.close(index);
                // })
            });
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})