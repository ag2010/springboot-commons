layui.use([ 'form', 'table', 'element', 'laytpl', 'layer', 'treeGrid' ], function() {
	var layer = layui.layer;
	var form = layui.form;
	var table = layui.table;
	var laytpl = layui.laytpl;
	var $ = layui.jquery;
	var treeGrid = layui.treeGrid;
	
    var treeTable =treeGrid.render({
    	id:"treePerTable",
        elem: "#treePerTable",
        url:"/system/queryPreList",
        cellMinWidth: 100,
        treeId:"permissions_code",//树形id字段名称
        treeUpId:"permissions_group",//树形父id字段名称
        treeShowName:"permissions_name",//以树形式显示的字段
        cols : [[
         	 {field:'permissions_name',width:'200', title: '权限名称'},
             {field:'permissions_code',width:'200', title: '权限标识'},
             {field:'note',width:'200', title: '备注'},
             {field:'sort',width:'60',align:"center", title: '排序'},
//             {title:'操作',fixed:"right",align:"center", templet:'#perOpertListBar'}
             {title:'操作',fixed:"right",align:"center", templet:function(item){
            	 // 如果等于空，则表示添加的虚拟父类数据
            	if(item.permissions_group == ""){
            		return "";
            	}
            	var edit = '<a class="layui-btn layui-btn layui-btn-xs" lay-event="edit" shiro:hasPermission="per_edit">编辑</a>';
            	var del = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" shiro:hasPermission="per_del">删除</a>';
            	return edit + del;
             }}
        ]],
        page:false
    });
    
	//工具条点击事件
    treeGrid.on('tool(treePerTable)', function(obj){
		var data = obj.data;
		var layEvent = obj.event;
 
		if(layEvent === 'edit'){ //修改
			showPerMgrModel(data);
		} else if(layEvent === 'del'){ //删除
			doDelete(data);
		}
	});
    
	//添加按钮点击事件
	$("#addPerBtn").click(function(){
		showPerMgrModel(null);
		return false;
	});

    // 显示新增、编辑窗口
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
//             		$("#groupSelect").find('input').removeAttr("readonly");
             		form.render('select');
            	});
            }
		});
		
		if(data != null){
			$("#perMgrForm input[id=perid]").val(data.id);
			$("#perMgrForm input[id=permissions_name]").val(data.permissions_name);
			$("#perMgrForm input[id=permissions_code]").val(data.permissions_code);
			$("#perMgrForm input[id=sort]").val(data.sort);
			$("#perMgrForm select[id=groupSelect]").val(data.permissions_group);
			$("#perMgrForm textarea[id=note]").val(data.note);
		}

		$("#btnCancel").click(function(){
			layer.closeAll('page');
		});
	};

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
             	treeTable.reload();
         }else{
        	 layer.msg(res.msg);
         }
     });
		return false;
	});

	function doDelete(item){
        layer.confirm('确定删除该权限吗？',{icon:3, title:'提示信息'},function(index){
    		layer.load(1);
            $.post("/system/deletePer",{
        		delId : item.id  //将需要删除的newsId作为参数传入
            },function(data){
            	layer.closeAll();
                if(data.code==0){
   	        	 	layer.msg(data.msg);
   	             	treeTable.reload();
                    //刷新父页面
                }else{
                    layer.alert(data.msg);
                }
            })
        });
	};
});
