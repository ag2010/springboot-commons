layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    
    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : '/system/findAllSysUsers?pageIndex=1',
        cellMinWidth : 95,
        method:'post',
        page : true,
        height : "full-125",
        limits : [20,30,40,50,60,80,100],
        limit : 20,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'real_name', title: '姓名', minWidth:100, align:"center"},
            {field: 'user_name', title: '用户名', minWidth:100, align:"center"},
            {field: 'mobile', title: '手机号', minWidth:130, align:"center"},
            {field: 'dept_name', title: '部门', minWidth:100, align:"center"},
            {field: 'role_name', title: '角色', minWidth:100, align:"center"},
            {field: 'status', title: '状态',  align:'center',templet:function(d){
                return d.status == "0" ? "禁用" : "正常";
            }},
            {field: 'last_visit', title: '最后登录时间', align:'center',minWidth:200,templet:function(t){
        		
	            	if(t.last_visit!=null && t.last_visit!=''){
	            		return t.last_visit;
	            	}else{
	            		return '';
	            	}
            }},
            {field: 'last_ip', title: '最后登录IP', minWidth:100, align:"center"},
            {field: 'email', title: '邮箱', minWidth:200, align:'center',templet:function(d){
            		if(d.email!=null && d.email!=''){
            			return '<a class="layui-blue" href="mailto:'+d.email+'">'+d.email+'</a>';
            		}else{
            			return '';
            		}
            }},
            
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
//        if($(".searchVal").val() != ''){
            table.reload("userListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                		user_name: $(".searchVal").val()  //搜索的关键字
                }
            })
//        }else{
//            layer.msg("请输入搜索的内容");
//        }
    });

    //添加用户
    function addUser(edit){
    
    	var qUrl="";
    	if(edit==null){//添加
    		qUrl="/system/toAddUser";
    	}else{//修改
    		qUrl="/system/toAddUser?uid="+edit.id;
    	}
        var index = layui.layer.open({
            title : edit==null?"添加员工":"修改员工",
            type : 2,
            content : qUrl,
            success : function(layero, index){
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addNews_btn").click(function(){
        addUser(null);
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addUser(data);
        }else if(layEvent === 'usable'){ //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用";
            if(_this.text()=="已禁用"){
                usableText = "是否确定启用此用户？",
                btnText = "已启用";
            }
            layer.confirm(usableText,{
                icon: 3,
                title:'系统提示',
                cancel : function(index){
                    layer.close(index);
                }
            },function(index){
                _this.text(btnText);
                layer.close(index);
            },function(index){
                layer.close(index);
            });
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                $.post("/system/deleteUser",{
                		uid : data.id  //将需要删除的newsId作为参数传入
                },function(data){
                    if(data.code==0){
                        tableIns.reload();
                        layer.close(index);
                    }else{
	                    layer.alert(data.msg);
	                    layer.close(index);
                    }

                })
            });
        }
    });

})
