layui.use(['form','table','element','laytpl','layer'], function() {
    var layer = layui.layer;
	var form = layui.form;
	var table = layui.table;
    var laytpl = layui.laytpl;
    var $=layui.jquery;


	function doSearch(){
		var key = $("#searchKey").val();
		var value = $("#searchValue").val();
		if (value=='') {
			key = '';
		}
		layui.table.reload('table', {where: {searchKey: key,searchValue: value}});
	};
	
    //用户列表
    var tableIns = table.render({
        elem: '#perList',
        url : '/system/queryPreList',
        cellMinWidth : 95,
        method:'post',
        page : true,
        height : "full-125",
        limits : [20,30,40,50,60,80,100],
        limit : 20,
        id : "perList",
        cols : [[
            {field: 'permissions_name', title: '权限名称', minWidth:150, align:"center"},
            {field: 'permissions_code', title: '权限标识', minWidth:200, align:"center"},
            {field: 'permissions_group', title: '权限分组', minWidth:130, align:"center"},
            {field: 'note', title: '备注', minWidth:100, align:"center"},
            {field: 'sort', title: '排序', minWidth:50, align:"center"},
            {title: '操作', minWidth:175, templet:'#barPerList',fixed:"right",align:"center"}
        ]]
    });
    
    
	//工具条点击事件
	layui.table.on('tool(perList)', function(obj){
		var data = obj.data;
		var layEvent = obj.event;
 
		if(layEvent === 'edit'){ //修改
			showPerMgrModel(data);
		} else if(layEvent === 'del'){ //删除
			doDelete(data);
		}
	});
	
	layer.load(1);
	var setting = {
	    data: {
	    		key:{
	    			name:"name"
	    		},
	    		simpleData:{
	    			enable: true,
	    			idKey:"id",
	    			pidKey:"pId",
	    			rootId:"null"
	    		}
	    },
	    callback: {
			onClick: zTreeOnClick
		}
	};
	
	$.post("/system/perGroupList",function(data) {
		$.fn.zTree.init($("#perGroupAuthTree"), setting, data);  
		layer.closeAll('loading');
	});
	function zTreeOnClick(event, treeId, treeNode) {
		table.reload("perList",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
            		menu_group: treeNode.id  //搜索的关键字
            }
        });
        
	}

	
	//添加按钮点击事件
	$("#addPerBtn").click(function(){
		showPerMgrModel(null);
		return false;
	});
	
	//显示表单弹窗
	function showPerMgrModel(data){
		layer.open({
			type: 1,
			title: data==null?"添加权限":"修改权限",
			area: ['600px', '460px'],
			offset: 'auto',
			content: $("#perAddModel").html(),
			success: function() {
                 
             	$.post("/system/getAllPerGroups",function(rsdata) {
             		for(var i =0;i<rsdata.length;i++){  
                        var opt = rsdata[i];
                        if(data!=null && data.permissions_group==opt.groupCode){
                        	$("#groupSelect").append("<option value='"+opt.groupCode+"' selected>"+opt.groupName+"</option>");
                        }else{
                        	$("#groupSelect").append("<option value='"+opt.groupCode+"'>"+opt.groupName+"</option>");
                        }
                          
                    }
             		form.render('select');
            	});
             	

            }
			
		});
		
		if(data!=null){
			$("#perMgrForm input[id=perid]").val(data.id);
			$("#perMgrForm input[id=permissions_name]").val(data.permissions_name);
			$("#perMgrForm input[id=permissions_code]").val(data.permissions_code);
			$("#perMgrForm input[id=sort]").val(data.sort);
			$("#perMgrForm select[id=groupSelect]").val(data.permissions_group);
			$("#perMgrForm textarea[id=note]").val(data.note);
		}
		
//		$("#editForm")[0].reset();
//		$("#editForm").attr("method","POST");

		$("#btnCancel").click(function(){
			layer.closeAll('page');
		});
	}
	
	
	//表单提交事件
	layui.form.on('submit(btnSubmit)', function(data){
		layer.load(1);
		
		var qUrl="";
		if($("#perid").val()!=null && $("#perid").val()!='' && $("#perid").val()>0){
			qUrl="/system/monfiyAddPer";//修改
		}else{
			qUrl="/system/saveAddPer";//新增
		}
        $.post(qUrl,{
        	id : $("#perid").val(),
        	permissions_name : $("#permissions_name").val(),
        	permissions_group : $("#groupSelect").val(),
        	permissions_code : $("#permissions_code").val(),
        	note:$("#note").val(),
        	sort:$("#sort").val(),
     },function(res){
         if(res.code==0){
        	 layer.closeAll();
        	 layer.msg(res.msg);
             //刷新父页面
             table.reload("perList",{
                 page: {
                     curr: 1 //重新从第 1 页开始
                 }
             });
         }else{
        	 layer.msg(res.msg);
         }
     });
		return false;
		
	});
	
	//删除
	function doDelete(data){
        layer.confirm('确定删除该权限吗？',{icon:3, title:'提示信息'},function(index){
            $.post("/system/deletePer",{
            		delId : data.id  //将需要删除的newsId作为参数传入
            },function(data){
                if(data.code==0){
                	layer.close(index);
                    //刷新父页面
                    table.reload("perList",{
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    
                }else{
                	layer.close(index);
                    layer.alert(data.msg);
                    
                }

            })
        });
		
		
	}

});
