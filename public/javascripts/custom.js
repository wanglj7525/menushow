$(function() {
	$(document).on('blur','input',function() {
		if ($(this).closest('div').hasClass('errorshow')
				&& $(this).val().trim().length > 0) {
			$(this).closest('div').removeClass('errorshow');
		}
	});

});	
var lineChartData = {
			labels : ["January","February","March","April","May","June","July"],
			datasets : [
				{
					fillColor : "rgba(220,220,220,0.5)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					data : [65,59,90,81,56,55,40]
				},
				{
					fillColor : "rgba(151,187,205,0.5)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff",
					data : [28,48,40,19,96,27,100]
				}
			]
		};
//		function lineChart() {
//	        var ctx = document.getElementById('sales-volume-chart').getContext("2d")
//	        var data = {
//	            labels: ["2014-10", "2014-11", "2014-12", "2015-1", "2015-2", "2015-3"],
//	            datasets: [{
//	                label: "",
//	                fillColor: "rgba(220,220,220,0.2)",
//	                strokeColor: "rgba(0,102,51,1)",
//	                pointColor: "rgba(220,220,220,1)",
//	                pointStrokeColor: "#339933",
//	                pointHighlightFill: "#339933",
//	                pointHighlightStroke: "rgba(220,220,220,1)",
//	                data: [1.27, 1.30, 1.30, 1.41, 1.04, 1.29]
//	            }]
//	        };
//	        // var salesVolumeChart = new Chart(ctx).Line(data);
//	        var salesVolumeChart = new Chart(ctx).Line(data, {
//	            // 小提示的圆角
//	            // tooltipCornerRadius: 0,
//	            // 折线的曲线过渡，0是直线，默认0.4是曲线
//	            bezierCurveTension: 0,
//	            // bezierCurveTension: 0.4,
//	            // 关闭曲线功能
//	            bezierCurve: false,
//	            // 背景表格显示
//	            // scaleShowGridLines : false,
//	            // 点击的小提示
//	            tooltipTemplate: "<%if (label){%><%=label%> 销量：<%}%><%= value %>万辆",
//	            //自定义背景小方格、y轴每个格子的单位、起始坐标
//	            scaleOverride: true,
//	            scaleSteps: 9.5,
//	            // scaleStepWidth: Math.ceil(Math.max.apply(null,data.datasets[0].data) / 0.1),
//	            scaleStepWidth: 0.05,
//	            scaleStartValue: 1
//	        });
//	    }
//	    function barChart() {
//	        var ctx = document.getElementById('sales-volume-bar-chart').getContext("2d")
//	        var data = {
//	            labels: ["2014-10", "2014-11", "2014-12", "2015-1", "2015-2", "2015-3"],
//	            datasets: [{
//	                label: "",
//	                fillColor: "rgba(153,204,153,0.5)",
//	                strokeColor: "rgba(0,102,51,1)",
//	                pointColor: "rgba(220,220,220,1)",
//	                pointStrokeColor: "#338033",
//	                pointHighlightFill: "#338033",
//	                pointHighlightStroke: "rgba(220,220,220,1)",
//	                data: [1.27, 1.30, 1.30, 1.41, 1.04, 1.29]
//	            }]
//	        };
//	        var salesVolumeChart = new Chart(ctx).Bar(data, {
//	            // 点击的小提示
//	            tooltipTemplate: "<%if (label){%><%=label%> 销量：<%}%><%= value %>万辆"
//	        });
//	    }
//		var graphInitDelay = 300;
		var globalGraphSettings = {animation : Modernizr.canvas};


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
              url: "/app/login",  
              data: $('#loginform').serialize(),  
              success:function(data){  
              	if(data.result=="error"){
              		alert(data.msg);
              	}else{
              		window.location = "/store/index?id="+data.info.id+"#pagestore";
//                    $.mobile.changePage("/store/index?id="+data.info.id);  
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
              		window.location = "/store/index?id="+data.info+"#pagestore";
//                    $.mobile.changePage("/store/index?id="+data.info);  
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
function showRadio(store){
	var html="";
	if (store.length==0){
		html+=" <legend>暂无门店列表</legend>";
	}else{
		html+="<legend>请选择门店：</legend>";
	}
	for ( var i = 0; i < store.length; i++) {
		var onestore = store[i];
		html+='<label for="store_'+onestore.id+'">'+onestore.sName+'</label>';
		if (i==0) {
			html+='<input type="radio" name="store" checked="checked" id="store_'+onestore.id+'" value="'+onestore.id+'" attrname="'+onestore.sName+'" attrdog="'+onestore.dogId+'">';
		}else{
			html+='<input type="radio" name="store" id="store_'+onestore.id+'" value="'+onestore.id+'" attrname="'+onestore.sName+'" attrdog="'+onestore.dogId+'">';
		}
		
	}
	$('#showlist').html(html);
	$("#showlist").trigger("create"); 
	$("#addStore").popup("close");
}
   
function addStore() {
	var url=window.location.href;
	var t_id=$("#us_id").val();
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
				showRadio(data);
//				$.mobile.changePage("#pagestore", {
//					'allowSamePageTransition' : true,
//					'reloadPage' : true,
//					'transition' : 'none',
//					'dataUrl':url
//				});
//				if(t_id!=-1){
//					$.mobile.changePage("#pagestore", {
//						'allowSamePageTransition' : true,
//						'reloadPage' : true,
//						'transition' : 'none'
//					});
//				}
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
            				 showRadio(data);
//            				$.mobile.changePage("#pagestore", {
//            					'allowSamePageTransition' : true,
//            					'reloadPage' : true,
//            					'transition' : 'none'
//            				});
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
function check(listid){
	var selectStore=$("input[name='store']:checked").val(); 
	if (listid==1) {
		$.mobile.changePage("/store/first?s_id="+selectStore);  
	}else{
		$.mobile.changePage("/store/second?s_id="+selectStore);  
	}
}
