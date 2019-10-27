layui.use(['form','table','element','laytpl','layer'], function() {
    var layer = layui.layer;
	var form = layui.form;
	var table = layui.table;
    var laytpl = layui.laytpl;
    var $=layui.jquery;

	//渲染表格
	layui.table.render({
		elem : '#roleTable',
		url : '/system/queryRoleList',
		page: false,
		method:'post',
		id:'roleTable',
		cols: [[
			{type:'numbers'},
			{field:'role_name', sort: true, title: '角色名称'},
			{field:'note', sort: true, title: '备注'},
			{field:'status', sort: true, templet: '#statusTpl',width: 80, title: '状态'},
			{align:'center', toolbar: '#barTpl', minWidth: 180, title: '操作'}
    	]]
	});
	
	//添加按钮点击事件
	$("#addBtn").click(function(){
		showEditModel(null);
		return false;
	});
	
	//表单提交事件
	layui.form.on('submit(btnSubmit)', function(data){
		layer.load(1);
		var qUrl="";
		if($("#roleId").val()!=null && $("#roleId").val()!='' && $("#roleId").val()>0){
			qUrl="/system/monfiyAddRole";//修改
		}else{
			qUrl="/system/saveAddRole";//新增
		}
        $.post(qUrl,{
        	id : $("#roleId").val(),
        	role_name : $("#role_name").val(),
        	status : $("status").val(),
        	note:$("#note").val(),
        	sort:$("#sort").val(),
     },function(res){
         if(res.code==0){
        	 layer.closeAll();
        	 layer.msg(res.msg);
             //刷新父页面
        	 layui.table.reload('roleTable', {});
         }else{
        	 layer.msg(res.msg);
         }
     });
		return false;
		
		
	
	});
	
	//工具条点击事件
	layui.table.on('tool(table)', function(obj){
		var data = obj.data;
		var layEvent = obj.event;
 
		if(layEvent === 'edit'){ //修改
			showEditModel(data);
		} else if(layEvent === 'del'){ //删除
			doDelete(data);
		} else if(layEvent == 'detail'){
			showPermDialog(data.id);
		}else if(layEvent == 'operateAuth'){
			perAuthWin(data.id,data.role_name);
		}
	});
	
	//监听状态开关操作
	layui.form.on('switch(statusCB)', function(obj){
		updateStatus(obj);
	});
	
	
    //权限授权
    function perAuthWin(rid,rname){
        var index = layer.open({
            title : "权限授权-"+rname,
            area: ['100%', '100%'],
            type : 2,
            content : "/system/toPerAuthPage?roleId="+rid,
            success : function(layero, index){
            }
        })
        layer.full(index);
        window.sessionStorage.setItem("indexRole",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layer.full(window.sessionStorage.getItem("indexRole"));
        })
    }
	
	
	
	
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
	});


	//显示表单弹窗
	function showEditModel(data){
		layer.open({
			type: 1,
			title: data==null?"添加角色":"修改角色",
			area: '450px',
			offset: 'auto',
			content: $("#addModel").html()
		});
		if(data!=null){
			$("#editForm input[id=roleId]").val(data.id);
			$("#editForm input[id=status]").val(data.status);
			$("#editForm input[id=sort]").val(data.sort);
			$("#editForm input[id=role_name]").val(data.role_name);
			$("#editForm textarea[id=note]").val(data.note);
		}
		$("#btnCancel").click(function(){
			layer.closeAll('page');
		});
	}

	//删除
	function doDelete(data){
        layer.confirm('确定删除该角色吗？',{icon:3, title:'提示信息'},function(index){
            $.post("/system/deleteRole",{
            		delId : data.id  //将需要删除的newsId作为参数传入
            },function(data){
                if(data.code==0){
                	layer.close(index);
                	layui.table.reload('roleTable', {});
                    
                }else{
                	layer.close(index);
                    layer.alert(data.msg);
                    
                }

            })
        });
		
		
	}

	//更改状态
	function updateStatus(obj){
		layer.load(1);
		var newStatus = obj.elem.checked?0:1;
		$.post("/system/editRoleStatus", {
			id: obj.elem.value,
			oper_status: newStatus,
		}, function(data){
			layer.closeAll('loading');
			if(data.code==0){
				layui.table.reload('table', {});
			}else{
				layer.msg(data.msg,{icon: 2});
			}
		});
	}

	//搜索
	function doSearch(){
		var key = $("#searchKey").val();
		var value = $("#searchValue").val();
		if (value=='') {
			key = '';
		}
		layui.table.reload('table', {where: {searchKey: key,searchValue: value}});
	}

	//管理权限
	function showPermDialog(roleId){
		layer.open({
			type: 1,
			title: '菜单授权',
			area: ['350px', '400px'],
			offset: 'auto',
			content: '<ul id="treeAuth" class="ztree" style="padding: 25px 80px;"></ul>',
			btn: ['保存','关闭'],
			btnAlign: 'c', 
			yes: function(index){
				saveRolePerm(roleId,index);
			}
		});
		layer.load(1);
		var setting = {
			check: {enable: true},
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
		    }
		};
		$.post("/system/permissionsTreeList?roleId="+roleId,function(data) {
			$.fn.zTree.init($("#treeAuth"), setting, data);  
			layer.closeAll('loading');
		},"json");
	}
	
	
	//业务管理权限
	function showOperateDialog(roleId){
		layer.open({
			type: 1,
			title: '业务授权',
			area: ['350px', '400px'],
			offset: 'auto',
			content: '<ul id="treeOperateAuth" class="ztree" style="padding: 25px 80px;"></ul>',
			btn: ['保存','关闭'],
			btnAlign: 'c', 
			yes: function(index){
				//saveRolePerm(roleId,index);
			}
		});
		layer.load(1);
		var setting = {
			check: {enable: true},
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
		    }
		};
		$.post("/system/operatePermissionsTreeList?roleId="+roleId,function(data) {
			$.fn.zTree.init($("#treeOperateAuth"), setting, data);  
			layer.closeAll('loading');
		},"json");
	}

	//保存权限
	function saveRolePerm(roleId,index){
		layer.load(1);
		var treeObj = $.fn.zTree.getZTreeObj("treeAuth");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = ""
		for(var i=0;i<nodes.length;i++){
			if(i==0){
				ids=nodes[i].code;
			}else{
				ids=ids+","+nodes[i].code;
			}
		}
		$.post("/system/authorizeMenuAction",{
			roid: roleId,
			menuPers: ids
		},function(data){
			layer.closeAll();
			layer.closeAll('loading');
			layer.alert(data.msg);
            if(data.code==0){
            		layui.table.reload('roleTable', {});
            }
		},"json");
	}

});
