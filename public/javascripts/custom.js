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
function hiderror(id){
	$("#"+id).closest('div').removeClass("errorshow");
}
function addStore() {
	var s_name = $("#s_name").val();
	if (s_name.trim().length == 0) {
		$("#s_name").closest('div').addClass("errorshow");
		setTimeout("hiderror('s_name')", 2000 ) ;
		return;
	} else {
		$("#s_name").closest('div').removeClass("errorshow");
	}
	var dog_id = $("#dog_id").val();
	if (dog_id.trim().length == 0) {
		$("#dog_id").closest('div').addClass("errorshow");
		setTimeout("hiderror('dog_id')", 2000 ) ;
		return;
	} else {
		$("#dog_id").closest('div').removeClass("errorshow");
	}

	var pswd = $("#pswd").val();
	if (pswd.trim().length == 0) {
		$("#pswd").closest('div').addClass("errorshow");
		setTimeout("hiderror('pswd')", 2000 ) ;
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
				console.log(data);
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
function showDelete(){
    var popupDialogId = 'popupDialog';
    $('<div data-role="popup" id="' + popupDialogId + '" data-confirmed="no" data-transition="pop" data-overlay-theme="b" data-theme="b" data-dismissible="false" style="min-width:216px;max-width:500px;"> \
                    \
                    <div role="main" class="ui-content">\
                        <h3 class="ui-title" style="color:#fff; text-align:center;margin-bottom:15px">确认删除门店吗？</h3>\
                        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b optionConfirm" data-rel="back" style="background: #1784fd;width: 33%;border-radius: 5px;height: 30px;line-height: 30px;padding: 0;font-size: .9em;margin: 0 0 0 12%;font-weight: 100;">确定</a>\
                        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-b optionCancel" data-rel="back" data-transition="flow" style="background: #DBDBDB;width: 33%;border-radius: 5px;height: 30px;line-height: 30px;padding: 0;font-size: .9em;margin: 0 0 0 5%;font-weight: 100;color: #333;text-shadow: none;">取消</a>\
                    </div>\
                </div>')
        .appendTo($.mobile.pageContainer);
    var popupDialogObj = $('#' + popupDialogId);
    popupDialogObj.trigger('create');
    popupDialogObj.popup({
        afterclose: function (event, ui) {
            popupDialogObj.find(".optionConfirm").first().off('click');
            var isConfirmed = popupDialogObj.attr('data-confirmed') === 'yes' ? true : false;
            $(event.target).remove();
            if (isConfirmed) {
               //这里执行确认需要执行的代码
            	var selectStore=$("input[name='store']:checked").val(); 
            	$.ajax({
            		type : "POST",
            		url : "/store/delete",
            		data :{sid:selectStore},
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
        }
    });
    popupDialogObj.popup('open');
    popupDialogObj.find(".optionConfirm").first().on('click', function () {
        popupDialogObj.attr('data-confirmed', 'yes');
    });
}
function showUpdate(){
	var selectStore=$("input[name='store']:checked").val(); 
	var sname=$("input[name='store']:checked").attr("attrname");
	var sdog=$("input[name='store']:checked").attr("attrdog");
	$("#storetitle").html("修改门店");
	$("#us_id").val(selectStore);
	$("#s_name").val(sname);
	$("#dog_id").val(sdog);
	$("#addStore").popup('open');
}
function showAdd(){
	$("#storetitle").html("添加门店");
	$("#us_id").val(-1);
	$("#s_name").val("");
	$("#dog_id").val("");
	$("#addStore").popup('open');
}