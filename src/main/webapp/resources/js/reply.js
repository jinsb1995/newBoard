console.log("Reply Module......");

var replyService = (function() {

//	이 함수는 Ajax를 이용해서 POST방식으로 호출한다
//	사용방법 : replyService.add(객체, 콜백)
	function add(reply, callback, error) {
		console.log("add reply................");
		
		$.ajax({
			type : 'post',
			url  : '/replies/new',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if(callback) {         // Ajax 호출이 성공하고, callback 값으로 적절한 함수가 존재한다면 해당 함수를 호출해서 결과를 반영하는 방식이다.
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		})
	}
	
	// 댓글 목록 조회
	function getList(param, callback, error) {
		
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
				function(data) {
					if(callback) { 
//						callback(data);     // 댓글 목록만 가져오는 경우
						callback(data.replyCnt, data.list);     // 댓글 숫자와 목록을 가져오는 경우
					}
				}).fail(function(xhr, status, err) {
			if(error) {
				error();
			}
		});
	}
	
	// 댓글 삭제
	function remove(rno, callback, error) {
		$.ajax({
			type : "delete",
			url  : "/replies/"+rno,
			success : function(deleteResult, status, xhr) {
				if(callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		});
	}
	
	function update(reply, callback, error) {
		console.log("RNO :   " + reply.rno);
		
		$.ajax({
			type : 'put',
			url  : '/replies/' + reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		});
	}
	
	// 댓글 한개 조회
	function get(rno, callback, error) {
		$.get("/replies/"+ rno + ".json", function(result) {
			
			if(callback) {
				callback(result);
			}
		}).fail(function(xhr, status, err) {
			if(error) {
				error();
			}
		});
	}
	
	// 현재 시간을 기준으로 해서 화면에 내용이 달라지도록 하는 부분
	// 당일 등록된 데이터는 '시/분/초',   하루지난 데이터는 '년/월/일'
	function displayTime(timeValue) {
		var today = new Date();
		
		var gap = today.getTime() - timeValue;
		
		var dateObj = new Date(timeValue);     // 입력받은 시간을 date로 바꿔서 계산
		var str = "";
		
		if(gap < (1000 * 60 * 60 * 24)) {
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss ].join('');
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // getMonth 는 0부터 시작한다.
			var dd = dateObj.getDate();
			
			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd ].join('');
		}
	};
	
	
	
	
	return {
		add : add,
		get : get,
		getList : getList,
		remove : remove,
		update : update,
		displayTime : displayTime
	};
})();





