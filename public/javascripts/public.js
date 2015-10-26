Date.prototype.Format = function (fmt) { //author: meizz 
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "h+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
}

//console.log((new Date()).Format("yyyy-MM-dd hh:mm:ss"));

/*获取等长的文本*/
function cutstr(str, len) {
    var str_length = 0;
    var str_len = 0;
    var str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
        a = str.charAt(i);
        str_length++;
        if (escape(a).length > 4) {
            //中文字符的长度经编码之后大于4
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if (str_cut == str) {
            return str_cut;
        }
        if (str_length >= len) {
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
    //如果给定字符串小于指定长度，则返回源字符串；
    if (str_length < len) {
        return str;
    }
}

function isNullOrEmpty(strVal) {
    if (strVal == '' || strVal == null || strVal == undefined) {
        return true;
    } else {
        return false;
    }
}

function isNull(strVal) {
    if (strVal == null || strVal == undefined) {
        return true;
    } else {
        return false;
    }
}

// 点击弹出搜索框之外的区域隐藏
/*$(document).die().live("click", function(e) {
    if ($(e.target).parents("#searchCondition").length == 0 && e.target.id != 'conditionSearch' && e.target.id != 'searchCondition') {
        $('#searchCondition').hide();
    }
    
    if ($(e.target).parents("#reckey_div").length == 0 && $(e.target).attr("mark") != '1' && e.target.id != 'reckey_div') {
        $('#reckey_div').hide();
    }
    
    if ($(e.target).parents("#namegroup_div").length == 0 && $(e.target).attr("ft") != '1' && $(e.target).attr("ft") != '2' && e.target.id != 'namegroup_div') {
        $('#namegroup_div').hide();
    }
});*/

(function($, a) {
    $.fn.serializeObject = function() {
        var b = {};
        $.each(this.serializeArray(), function(d, e) {
            var f = e.name,
            c = e.value;
            b[f] = b[f] === a ? c: $.isArray(b[f]) ? b[f].concat(c) : [b[f], c]
        });
        return b
    }
    
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

function getEvent(event) {
    return event || window.event;
}
function stopBubble(e) {
    if (e.stopPropagation) {
        e.stopPropagation();
    } else {
        e.cancelBubble = true;
    }
}

/*loading*/
function loading($container) {
    $container.html("<div class='loading' style='text-align: center;margin-top: 10px'><img src='/public/img/loading.gif'></div>");
}

/**
 * json to string
 * @param obj
 * @return
 */
function jsonToString(obj) {
    var THIS = this;
    switch (typeof(obj)) {
    case 'string':
        return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
    case 'array':
        return '[' + obj.map(THIS.jsonToString).join(',') + ']';
    case 'object':
        if (obj instanceof Array) {
            var strArr = [];
            var len = obj.length;
            for (var i = 0; i < len; i++) {
                strArr.push(THIS.jsonToString(obj[i]));
            }
            return '[' + strArr.join(',') + ']';
        } else if (obj == null) {
            return 'null';

        } else {
            var string = [];
            for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));
            return '{' + string.join(',') + '}';
        }
    case 'number':
        return obj;
    case false:
        return obj;
    }
}

/**
 * string to json
 * @param obj
 * @return
 */
function stringToJSON(obj) {
    return eval('(' + obj + ')');
}

// 回到顶部
$(function() {
    var $body = $(document.body);
    $(window).scroll(function() {
        var scrollTop = $(window).scrollTop();
        scrollTop > 50 ? $("#scrollUp").fadeIn(200).css("display", "block") : $("#scrollUp").fadeOut(200);
    });
    $('#scrollUp').click(function(e) {
        e.preventDefault();
        $('html,body').animate({
            scrollTop : 0
        });
    });
    
    // collapse function for the widget
    $('.widget-buttons a.collapse').click(function() {
        if ($(this).attr('data-collapsed') == 'false') {
            $(this).closest('.widget').find('.widget-body').slideUp();
            $(this).attr('data-collapsed', 'true').addClass('widget-hidden');
        } else {
            $(this).closest('.widget').find('.widget-body').slideDown();
            $(this).attr('data-collapsed', 'false').removeClass('widget-hidden');
        }
    });
});

function toUtf8(str) {   
    var out, i, len, c;   
    out = "";   
    len = str.length;   
    for(i = 0; i < len; i++) {   
    	c = str.charCodeAt(i);   
    	if ((c >= 0x0001) && (c <= 0x007F)) {   
        	out += str.charAt(i);   
    	} else if (c > 0x07FF) {   
        	out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
        	out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	} else {   
        	out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	}   
    }   
    return out;   
}

function FloatMul(arg1,arg2){    
  	 var m=0,s1=arg1.toString(),s2=arg2.toString();    
  	 try{m+=s1.split(".")[1].length}catch(e){}    
  	 try{m+=s2.split(".")[1].length}catch(e){}    
  	 return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)    
}

function to2bits(flt,pos) {
  	 var rd=1;  
  	 for(var i=1;i<=parseInt(pos);i++){
  	     rd=rd*10;
  	 }
  	 if(parseFloat(flt) == flt)
  	    return Math.round(flt * rd) / rd;
  	 else
  	    return 0;
}

//获得某月的开始日期　　 
function getMonthStartDate(paraYear,paraMonth){　　 
  　 var monthStartDate = new Date(paraYear, paraMonth-1, 1);　　　 
  　 //return formatDate(monthStartDate);
  　 return monthStartDate.Format("yyyy-MM-dd");
}　　 
　　 
//获得某月的结束日期　　 
function getMonthEndDate(paraYear,paraMonth){ 
  　 var monthEndDate = new Date(paraYear, paraMonth-1, getMonthDays(paraYear, paraMonth-1));　　　 
  　 // return formatDate(monthEndDate);
  　 return monthEndDate.Format("yyyy-MM-dd");　　 
}　

//获得某月的天数　　 
function getMonthDays(paraYear,paraMonth){　　 
 　 var monthStartDate = new Date(paraYear, paraMonth, 1);　　　 
 　 var monthEndDate = new Date(paraYear, paraMonth + 1, 1);　　　 
 　 var days = (monthEndDate　 -　 monthStartDate)/(1000　 *　 60　 *　 60　 *　 24);　　　 
 　 return days;
}　　　 　 