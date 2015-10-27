$(function() {
    //-----  Users page or Tables page -----//
    if ($('#users_page, #tables_page').length > 0) {
    
        /* Set the defaults for DataTables initialisation */
        $.extend(true, $.fn.dataTable.defaults, {
            "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            }
        });
    
        /* Default class modification */
        $.extend($.fn.dataTableExt.oStdClasses, {
            "sWrapper": "dataTables_wrapper form-inline"
        });
    
        /* API method to get paging information */
        $.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
            return {
                "iStart": oSettings._iDisplayStart,
                "iEnd": oSettings.fnDisplayEnd(),
                "iLength": oSettings._iDisplayLength,
                "iTotal": oSettings.fnRecordsTotal(),
                "iFilteredTotal": oSettings.fnRecordsDisplay(),
                "iPage": Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
                "iTotalPages": Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
            };
        };
    
        /* Bootstrap style pagination control */
        $.extend($.fn.dataTableExt.oPagination, {
            "bootstrap": {
                "fnInit": function(oSettings, nPaging, fnDraw) {
                    var oLang = oSettings.oLanguage.oPaginate;
                    var fnClickHandler = function(e) {
                        e.preventDefault();
                        if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
                            fnDraw(oSettings);
                        }
                    };
    
                    $(nPaging).addClass('pagination').append('<ul>' + '<li class="prev disabled"><a href="#">&larr; ' + oLang.sPrevious + '</a></li>' + '<li class="next disabled"><a href="#">' + oLang.sNext + ' &rarr; </a></li>' + '</ul>');
                    var els = $('a', nPaging);
                    $(els[0]).bind('click.DT', {
                        action: "previous"
                    },
                    fnClickHandler);
                    $(els[1]).bind('click.DT', {
                        action: "next"
                    },
                    fnClickHandler);
                },
    
                "fnUpdate": function(oSettings, fnDraw) {
                    var iListLength = 5;
                    var oPaging = oSettings.oInstance.fnPagingInfo();
                    var an = oSettings.aanFeatures.p;
                    var i, j, sClass, iStart, iEnd, iHalf = Math.floor(iListLength / 2);
    
                    if (oPaging.iTotalPages < iListLength) {
                        iStart = 1;
                        iEnd = oPaging.iTotalPages;
                    } else if (oPaging.iPage <= iHalf) {
                        iStart = 1;
                        iEnd = iListLength;
                    } else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
                        iStart = oPaging.iTotalPages - iListLength + 1;
                        iEnd = oPaging.iTotalPages;
                    } else {
                        iStart = oPaging.iPage - iHalf + 1;
                        iEnd = iStart + iListLength - 1;
                    }
    
                    for (i = 0, iLen = an.length; i < iLen; i++) {
                        // Remove the middle elements
                        $('li:gt(0)', an[i]).filter(':not(:last)').remove();
    
                        // Add the new list items and their event handlers
                        for (j = iStart; j <= iEnd; j++) {
                            sClass = (j == oPaging.iPage + 1) ? 'class="active"': '';
                            $('<li ' + sClass + '><a href="#">' + j + '</a></li>').insertBefore($('li:last', an[i])[0]).bind('click',
                            function(e) {
                                e.preventDefault();
                                oSettings._iDisplayStart = (parseInt($('a', this).text(), 10) - 1) * oPaging.iLength;
                                fnDraw(oSettings);
                            });
                        }
    
                        // Add / remove disabled classes from the static elements
                        if (oPaging.iPage === 0) {
                            $('li:first', an[i]).addClass('disabled');
                        } else {
                            $('li:first', an[i]).removeClass('disabled');
                        }
    
                        if (oPaging.iPage === oPaging.iTotalPages - 1 || oPaging.iTotalPages === 0) {
                            $('li:last', an[i]).addClass('disabled');
                        } else {
                            $('li:last', an[i]).removeClass('disabled');
                        }
                    }
                }
            }
        });
    
        /*
        * TableTools Bootstrap compatibility
        * Required TableTools 2.1+
        */
        if ($.fn.DataTable.TableTools) {
            // Set the classes that TableTools uses to something suitable for Bootstrap
            $.extend(true, $.fn.DataTable.TableTools.classes, {
                "container": "DTTT btn-group",
                "buttons": {
                    "normal": "btn",
                    "disabled": "disabled"
                },
                "collection": {
                    "container": "DTTT_dropdown dropdown-menu",
                    "buttons": {
                        "normal": "",
                        "disabled": "disabled"
                    }
                },
                "print": {
                    "info": "DTTT_print_info modal"
                },
                "select": {
                    "row": "active"
                }
            });
    
            // Have the collection use a bootstrap compatible dropdown
            $.extend(true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
                "collection": {
                    "container": "ul",
                    "button": "li",
                    "liner": "a"
                }
            });
        }
    
        $('#users').dataTable({
            "aaSorting": [[ 0, "desc" ]],
            "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            //"sSearch": "搜索",
            //"oLanguage": {
            //    "sLengthMenu": "Entries: _MENU_ "
            //}
            "oLanguage": {//下面是一些汉语翻译
                "sSearch": "搜索",
                "sLengthMenu": "每页显示 _MENU_",
                "sZeroRecords": "没有检索到数据",
                "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
                "sInfoFiltered": "(筛选自 _MAX_ 条数据)",
                "sInfoEmtpy": "没有数据",
	            "sProcessing": "<img src='/public/images/loading.gif' /> 数据加载中...", 
                "oPaginate":
                            {
                                "sFirst": "首页",
                                "sPrevious": "前一页",
                                "sNext": "后一页",
                                "sLast": "末页"
                            }
            }
        });
    }
    
       $("#form_add_User").validate({
           rules: {
               "user.name": {
                   required: true,
                   remote:{
                      url: "/UserManage/nameIsExist",
                      type:'post',
                      data:{
                          name:function(){
                               return $("#name_add").val();
                          }
                      }
                   }
               },
               /*"user.user_name": {
             	   required: true,
             	   remote:{
        	       				url: "/UserManage/usernameIsExist",
        	       				type:'post',
        	       				data:{
        	       						username:function(){
           	       						    return $("#username_add").val();
           	       					}
        	       				}
        				     }
        	      },*/
               "user.password": "required"
           },
           messages: {
               "user.name": {
                   required: "请输入姓名",
                   remote: "姓名已经存在"
               },
               //"user.user_name": {
            	      //required: "请输入登录名",
            	  //    remote: "登录名已经存在"
               //},
               "user.password": "请输入密码"
           }
       });
    $("#saveBtn").die().live("click", function(){
        var $this = $(this);
        $this.attr('disabled','disabled');
        if($("#form_add_User").valid()){
            var params = $("#form_add_User").serializeObject();
            $.post("/add_user", params, function(result){
                $("#addUser").modal("hide");
                if(result){
                    location.reload();
                } else {
                    alert("添加失败!");
                }
            });
        }
        $this.attr('disabled', false);
    });
    
    $("#form_update_info").validate({
           rules: {
               "user.u_name": {
                   required: true,
                   remote:{
                      url: "/UserManage/nameIsExist",
                      type:'post',
                      data:{
                          name:function(){
                               return $("#name_update").val();
                          },
                          id:function(){
                              return $("#id_update").val();
                          }
                      }
                    }
                }
           },
           messages: {
               "user.u_name": {
                   required: "请输入姓名",
                   remote: "姓名已经存在"
               }
           }
       });
    $("#updateBtn").die().live("click", function(){
        if($("#form_update_info").valid()){
            var params = $("#form_update_info").serializeObject();
            $.post("/update_user", params, function(result){
                $("#editUser").modal("hide");
                if(result){
                    location.reload();
                } else {
                    alert("更新失败!");
                }
            });
        }
    });
    
}); // end document reday