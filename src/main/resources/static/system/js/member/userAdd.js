layui.use(['form','table','element','laytpl','layer','tree'], function(){
	var layer = layui.layer;
	var form = layui.form;
	var table = layui.table;
	var laytpl = layui.laytpl;
	var $ = layui.jquery;

	form.verify({
		verify_username : function(value, item) { // value：表单的值、item：表单的DOM对象
			if (value == ""){
				return "必填项不能为空";
			}
			if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
				return '用户名不能有特殊字符';
			}
			if (/(^\_)|(\__)|(\_+$)/.test(value)) {
				return '用户名首尾不能出现下划线\'_\'';
			}
			if (/^\d+\d+\d$/.test(value)) {
				return '用户名不能全为数字';
			}
		}
		// 我们既支持上述函数式的方式，也支持下述数组的形式
		// 数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		,
		verify_password : [ /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格' ],
		verify_repassword : function (value) {
            if (value != $('#ipt_password').val()) {
                return '两次输入的密码不一致！';
            }
        }
	});
	
    form.on("submit(addUser)",function(data){
    	var qUrl = "";
    	
    	if($("#ipt_id").val() != null && $("#ipt_id").val() != '' && $("#ipt_id").val() > 0){
    		qUrl = "/member/editUser"; //修改
    	}else{
    		qUrl = "/member/addUser"; //增加
    	}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 提交信息
        $.post(qUrl,{
        	 username:$("#ipt_username").val(),
        	 nickname : $("#ipt_nickname").val(),
        	 realname : $("#ipt_realname").val(),
        	 ipt_password : $("#ipt_password").val()
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