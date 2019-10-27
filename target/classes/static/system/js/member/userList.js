layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    

    // 用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : '/member/findAllMemUser?pageIndex=1',
        cellMinWidth : 95,
        method:'post',
        page : true,
        height : "full-125",
        limits : [20,30,40,50,60,80,100],
        limit : 20,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'username', title: '用户名', minWidth:100, align:"center"},
            {field: 'nickname', title: '昵称', minWidth:100, align:"center"},
            {field: 'realname', title: '真实姓名', minWidth:100, align:"center"},
            {field: 'gender', title: '性别', minWidth:60, align:"center", templet: function (item){
            	switch (item.gender) {
            		case 0:
            			return "女";
            			break;
            		case 1:
            			return "男";
            			break;
            		case 2:
            			return "保密";
            			break;
            		default: return "";	// 出错
            	}
            }},
            {field: 'mobile', title: '手机号码', minWidth:120, align:"center"},
            {field: 'email', title: '电子邮箱', minWidth:200, align:"center", templet: function (item){
	            return item.email != "" ? '<a class="layui-blue" href="mailto:'+item.email+'">'+item.email+'</a>' : "";
	        }},
            {field: 'certificate_type', title: '证件类型', minWidth:130, align:"center", templet: function (item){
        		switch (item.certificate_type){
	        		case 0:
	        			return "身份证";
	        			break;
	        		default: return "";	// 出错
	        	}
	        }},
            {field: 'certificate_no', title: '证件号码', minWidth:100, align:"center"},
            {field: 'credit', title: '信用权重', minWidth:100, align:"center"},
            {field: 'status', title: '状态', minWidth:100, align:"center", templet: function(item){
            	return item.status == 0 ? "正常" : "禁用";
            }},
            {field: 'lastId', title: '最后登录IP', minWidth:100, align:"center"},
            {field: 'lastTime', title: '最后登陆时间', minWidth:100, align:"center"},
            {field: 'create_id', title: '创建账户IP', minWidth:100, align:"center"},
            {field: 'create_time', title: '创建账户时间', minWidth:100, align:"center"},
            {field: 'update_time', title: '修改账户时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });
    
    // 添加用户
    function addUser(edit){
    	var qUrl = "";
    	if (edit == null){	// 添加
    		qUrl = "/member/userAdd";
    	} else {// 修改
    		qUrl = "/member/userAdd?uid=" + edit.id;
    	}
        var index = layui.layer.open({
            title : edit == null ? "添加用户" : "修改用户",
            type : 2,
            content : qUrl,
            success : function(layero, index){
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        // 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".btn_userAdd").click(function(){
        addUser(null);
    })
})
