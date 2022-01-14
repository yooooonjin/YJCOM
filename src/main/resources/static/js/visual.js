/**
 * 작성자 : 한윤진
 * 작성일 : 2022/01/14
 */


var myTimeout
var speed=300
var imgSize=900
var delay=3000
var imgs
$(function(){
	var lis_ea = $("#room-info .room-img .images li").length;
	imgs=$("#room-info .room-img .images");
	imgs.css("width",imgSize*lis_ea);
	myTimeout = setTimeout(move, delay);
});

function move(){
	
	var first_li =$("#room-info .room-img .images li:first-child");
	var last_li =$("#room-info .room-img .images li:last-child");
	imgs.animate({marginLeft:'-imgSize'}, speed, function(){
		//이미지가 이동한 다음 실행
		//첫번째 이미지를 맨 뒤로
		last_li.after(first_li);
		imgs.css("margin-left",0);
		myTimeout = setTimeout(move, 3000);
	});
};