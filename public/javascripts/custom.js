$(function() {
	$(document).on('blur','input',function() {
		if ($(this).closest('div').hasClass('errorshow')
				&& $(this).val().trim().length > 0) {
			$(this).closest('div').removeClass('errorshow');
		}
	});
});
function login(){

	var username=$("#username").val();
	if(username.trim().length==0){
		$("#username").closest('div').addClass("errorshow");
		return;
	}else{
		$("#username").closest('div').removeClass("errorshow");
	}
	var password=$("#password").val();
	if(password.trim().length==0){
		$("#password").closest('div').addClass("errorshow");
		return;
	}else{
		$("#password").closest('div').removeClass("errorshow");
	}
	
	 $.ajax({  
              type: "POST",  
              url: "/login",  
              data: $('#loginform').serialize(),  
              success:function(data){  
              	if(data.result=="error"){
              		alert(data.msg);
              	}else{
              	console.log(data.info);
                    $.mobile.changePage("/store/index?id="+data.info.id);  
              	}
              },
              error:function(data){
              		console.log(data);
              }  
        });  
}
function register(){

	var username=$("#rname").val();
	if(username.trim().length==0){
		$("#rname").closest('div').addClass("errorshow");
		return;
	}else{
		$("#rname").closest('div').removeClass("errorshow");
	}
	var password=$("#rpass").val();
	if(password.trim().length==0){
		$("#rpass").closest('div').addClass("errorshow");
		return;
	}else{
		$("#rpass").closest('div').removeClass("errorshow");
	}
	var againpassword=$("#againpassword").val();
	if(againpassword.trim().length==0||password!=againpassword){
		$("#againpassword").closest('div').addClass("errorshow");
		return;
	}else{
		$("#againpassword").closest('div').removeClass("errorshow");
	}
	
	 $.ajax({  
              type: "POST",  
              url: "/regester",  
              data: $('#rform').serialize(),  
              success:function(data){  
              	console.log(data);
              	if(data.result=="error"){
              		alert(data.msg);
              	}else{
                    $.mobile.changePage("/store/index?id="+data.info);  
              	}
              },
              error:function(data){
              		console.log(data);
              }  
        });  
}
function addStore() {
	var s_name = $("#s_name").val();
	if (s_name.trim().length == 0) {
		$("#s_name").closest('div').addClass("errorshow");
		return;
	} else {
		$("#s_name").closest('div').removeClass("errorshow");
	}
	var dog_id = $("#dog_id").val();
	if (dog_id.trim().length == 0) {
		$("#dog_id").closest('div').addClass("errorshow");
		return;
	} else {
		$("#dog_id").closest('div').removeClass("errorshow");
	}

	var pswd = $("#pswd").val();
	if (pswd.trim().length == 0) {
		$("#pswd").closest('div').addClass("errorshow");
		return;
	} else {
		$("#pswd").closest('div').removeClass("errorshow");
	}
	$.ajax({
		type : "POST",
		url : "/store/add",
		data : $('#addform').serialize(),
		success : function(data) {
			if (data.result == "error") {
				alert(data.msg);
			} else {
				$.mobile.changePage("#pagestore", {
					'allowSamePageTransition' : true,
					'reloadPage' : true,
					'transition' : 'none'
				});
			}
		},
		error : function(data) {
			console.log(data);
		}
	});
}