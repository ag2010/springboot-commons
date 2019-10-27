layui.use(['form','table','element','laytpl','layer','tree'],function(){
    var layer = layui.layer;
	var form = layui.form;
	var table = layui.table;
    var laytpl = layui.laytpl;
    var $=layui.jquery;
    
    
    
    form.verify({
    	
    	  
    	  //我们既支持上述函数式的方式，也支持下述数组的形式
    	  //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
    	  pass: [
    	    /^[\S]{6,12}$/
    	    ,'密码必须6到12位，且不能出现空格'
    	  ] 
    	});      
    
    
    
	//部门树
	function showDeptTreeDialog(){
		layer.open({
			type: 1,
			title: '选择所属部门',
			area: ['400px', '300px'],
			offset: 'auto',
			content: '<ul id="treeDepts" class="ztree" style="padding: 25px 80px;"></ul>',
			btn: ['确定','关闭'],
			btnAlign: 'c', 
			yes: function(index){
				var treeObj = $.fn.zTree.getZTreeObj("treeDepts");
				var sNodes = treeObj.getSelectedNodes();
				var choseNodeName;
				var choseNodeId;
				var choseNodePath;
				var choseNodeLevel="0";
		    	if (sNodes.length > 0) {
		    		choseNodeName=sNodes[0].name;
		    		choseNodeId=sNodes[0].id;
		    		
		    		var pathNodes=sNodes[0].getPath();
		    		
		    		
			    		for(var i=0;i<pathNodes.length;i++){
			    			if(i==0){
			    				choseNodePath=pathNodes[i].name;
			    			}else{
			    				choseNodePath=choseNodePath+"/"+pathNodes[i].name;
			    			}
			    			choseNodeLevel=i;
			    		}   			
		    		
				
		    	}
				
		    	$("#pDept").val(choseNodePath);
				
				$("#dept_id").val(choseNodeId);
//				$("#dept_path").val(choseNodePath);
//				$("#level").val(choseNodeLevel);
				layer.close(index);
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
		    }

		};
		$.post("/system/allDeptTreeNoRoot",function(data) {
			$.fn.zTree.init($("#treeDepts"), setting, data);  
			layer.closeAll('loading');
		},"json");
	}
    
    $('#pDept').on('click', function() {
		
    	showDeptTreeDialog();
    	
	});
    
    

    form.on("submit(addUser)",function(data){
    	
    	var qUrl="";
    	
    	if($("#userId").val()!=null && $("#userId").val()!='' && $("#userId").val()>0){
    		qUrl="/system/editUser"; //修改
    	}else{
    		qUrl="/system/addUser"; //增加
    	}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 提交信息
        $.post(qUrl,{
        	 id:$("#userId").val(),
        	 user_name : $("#user_name").val(),
        	 real_name : $("#real_name").val(),
        	 mobile : $("#mobile").val(),
        	 email : $("#email").val(),
        	 dept_id : $("#dept_id").val(),
        	 role_id : $("#role_id").val(),
        	 gender	: $("#gender").val()
         },function(res){
             if(res.code==0){
                 top.layer.close(index);
                 top.layer.msg(res.msg);
                 layer.closeAll("iframe");
                 //刷新父页面
                 parent.location.reload();
             }else{
            	 top.layer.close(index);
                 layer.alert(res.msg);
             }
         });
         return false;
    });
    
 form.on("submit(changePwd)",function(data){
    	
    	var qUrl="";
    	
    	if($("#userId").val()!=null && $("#userId").val()!='' && $("#userId").val()>0){
    		qUrl="/system/editPwd"; //修改
    	}else{
    		qUrl="/system/addUser"; //增加
    	}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 提交信息
        $.post(qUrl,{
        	 id:$("#userId").val(),
        	 passwd: $("#oldPwd").val(),
        	 new_pwd : $("#newPwd").val(),
        	 comfirmPwd : $("#comfirmPwd").val()
        	 
         },function(res){
             if(res.code==0){
                 top.layer.close(index);
                 top.layer.msg(res.msg);
                 layer.closeAll("iframe");
                 //刷新父页面
                 parent.location.reload();
             }else{
            	 top.layer.close(index);
                 layer.alert(res.msg);
             }
         });
         return false;
    });
    
    

})