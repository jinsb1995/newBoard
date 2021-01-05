<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    

<%@ include file="../includes/header.jsp" %>

            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
		
			<div class="panel-heading">Board Read Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="form-group">
					<label>Bno</label> <input class="form-control" name='bno' value='<c:out value="${board.bno }" />' readonly="readonly">
				</div>
				
				<div class="form-group">
					<label>Title</label> <input class="form-control" name='title' value='<c:out value="${board.title }" />' readonly="readonly">
				</div>
				
				<div class="form-group">
					<label>Text area</label> <textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content }" /></textarea>
				</div>
				
				<div class="form-group">
					<label>Writer</label> <input class="form-control" name='title' value='<c:out value="${board.writer }" />' readonly="readonly">
				</div>
				
				<button data-oper='modify' class="btn btn-default" >Modify</button>
				<button data-oper='list' class="btn btn-info" >List</button>
				
				
				<form id='operForm' action="/board/modify" method="get">
					<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno }" />' >
					<input type='hidden' id='pageNum' name='pageNum' value='<c:out value="${cri.pageNum}" />' >
					<input type='hidden' id='amount' name='amount' value='<c:out value="${cri.amount}" />' >
					<input type='hidden' id='keyword' name='keyword' value='<c:out value="${cri.keyword}" />' >
					<input type='hidden' id='type' name='type' value='<c:out value="${cri.type}" />' >
				</form>
				
				
				
			</div>
			<!-- /.table-responsive -->
		</div>
		<!-- /.panel-body -->


		<div class="panel panel-default">
<!-- 			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
			</div>
 -->			
 			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
				<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
			</div>
			
			<!-- /.panel-heading -->
			<div class="panel-body">
				
				<ul class="chat">
					<!-- start reply -->
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong>
								<small class="pull-right text-muted">2018-01-01 13:13</small>
							</div>
							<p>Good Job!</p>
						</div>
					</li>
					<!-- end reply -->
				</ul>
				<!-- ./ end ul -->
			</div>
			<!-- /. panel .chat-panel  추가 -->
			
			<div class="panel-footer">
			
			</div>
			
		</div>
		<!-- ./ end row-->

	</div>
	<!-- /.panel -->
</div>
<!-- /.row -->


<!-- 댓글의 추가는 이 모달창에서 진행될것임 -->
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                	<label>Reply</label>
                	<input class="form-control" name='reply' value='New Reply!!!'>
                </div>
                <div class="form-group">
                	<label>Replyer</label>
                	<input class="form-control" name='replyer' value='replyer'>
                </div>
                <div class="form-group">
                	<label>Reply Date</label>
                	<input class="form-control" name='replyDate' value=''>
                </div>
            </div>
            <div class="modal-footer">
                <button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
                <button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
                <button id="modalRegisterBtn" type="button" class="btn btn-default" data-dismiss="modal" >Register</button>
                <button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal" >Close </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<script type="text/javascript" src="/resources/js/reply.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	var result = '<c:out value = "${result}" />';
	
	var operForm = $("#operForm");
	$("button[data-oper='modify']").on("click", function(e) {
		console.log(e);
		operForm.attr("action", "/board/modify").submit();
	});
	
	$("button[data-oper='list']").on('click', function(e) {
		operForm.find("#bno").remove(); 		// 리스트로 이동할땐 어떤 데이터도 필요하지 않으므로 bno값을 없앤다
		operForm.attr("action", "/board/list");     
		operForm.submit();
	});	
	
	checkModal(result);
	
// 	console.log(replyService);
	
// 	console.log("====================");
// 	console.log("JS TEST");
	
	var bnoValue = '<c:out value="${board.bno}" />';
	var replyUL = $(".chat");
	
	showList(1);

	function showList(page) {
		
// 		console.log("show list  " + page);		
		
		replyService.getList({bno:bnoValue, page:page || 1}, function(replyCnt, list) {
			
// 			console.log("replyCnt:    " + replyCnt);
// 			console.log("list : " + list);
// 			console.log(list);
			
			if(page == -1) {
				pageNum = Math.ceil(replyCnt / 10.0);
				showList(pageNum);
				return;
			}

			var str = "";
			if(list == null || list.length == 0) {
				replyUL.html("");
				
				return;
			}
			for(var i = 0, len = list.length || 0; i < len; i++) {
				str += "<li class='left clearfix' data-rno='"+list[i].rno+"' >";
				str += "   <div><div class='header'><strong class='primary-font'>"+list[i].replyer+"</strong>";
				str += "        <small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
				str += "        <p>"+list[i].reply+"</p></div></li>";
// 				console.log('rno ============ ' , list[i].rno);
			}
			replyUL.html(str);
			
			showReplyPage(replyCnt);
			
		}); // end function
		
	} // end showList

	var pageNum = 1;
	var replyPageFooter = $(".panel-footer");
	
	function showReplyPage(replyCnt) {
		var endNum = Math.ceil(pageNum / 10.0) * 10;
		var startNum = endNum - 9;
		
		var prev = startNum != 1;
		var next = false;
		
		if(endNum * 10 >= replyCnt) {
			endNum = Math.ceil(replyCnt/10.0);
		}
		
		if(endNum * 10 < replyCnt) {
			next = true;
		}
		
		var str = "<ul class='pagination pull-right' >";
		
		if(prev) {
			str += "<li class='page-item'><a class='page-link' href='" + (startNum -1) + "'>Previous</a></li>";
		}
		
		for(var i = startNum ; i <= endNum; i++) {
			var active = pageNum == i? "active" : "";
			str += "<li class='page-item " + active + " '><a class='page-link' href='" + i + "'>" + i + "</a></li>";
		}
		
		if(next) {
			str += "<li class='page-item " + active + " '><a class='page-link' href='" +(endNum + i) + "'>Next</a></li>";
		}
		
		str += "</ul></div>";
		
		console.log("str > > > > > > > > > > " + str);
		
		replyPageFooter.html(str);
		
	}
	
	
	
	
	
	/*   새로운 댓글의 추가 버튼 처리 이벤트   */
	
	var modal = $(".modal");
	var modalInputReply = modal.find("input[name='reply']");
	var modalInputReplyer = modal.find("input[name='replyer']");
	var modalInputReplyDate = modal.find("input[name='replyDate']");
	
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	var modalRegisterBtn = $("#modalRegisterBtn");
	
	$("#addReplyBtn").on("click", function(e) {
		
		modal.find("input").val("");
		modalInputReplyDate.closest("div").hide();
		modal.find("button[id !='modalCloseBtn']").hide();
		
		modalRegisterBtn.show();
		
		$(".modal").modal("show");
		
	});
	
	
	// 댓글 등록 및 목록 갱신
	modalRegisterBtn.on("click", function(e) {
		
		var reply = {
				reply : modalInputReply.val(),
				replyer : modalInputReplyer.val(),
				bno : bnoValue
		};
		replyService.add(reply, function(result) {
			
			alert(result);
			
			modal.find("input").val("");
			modal.modal("hide");
			
// 			showList(1);   // 댓글이 추가된 후 목록 재조회

			// -1로 호출되면 마지막 페이지를 찾아서 다시 호출하게됨
			// 사용자가 새로운 댓글을 추가하면 showList(-1); 을 호출하여 우선 전체 댓글의 숫자를 파악하게 합니다.
			// 이후에 다시 마지막 페이지를 호출해서 이동시키는 방식으로 동작시킵니다.
			// 이러한 방식은 여러번 서버를 호출해야 하는 단점이 있기는 하지만, 댓글의 등록 행위가 댓글 조회나 페이징에 비해서 적기 때문에 심각한 문제는 아닙니다.
			showList(-1);   // 댓글이 추가된 후 목록 재조회
			
		});
	});
	
	
	// 특정 댓글의 클릭 이벤트 처리
	$(".chat").on("click", "li", function(e) {
		
		var rno = $(this).data("rno");
// 		console.log("rno ==== ", rno);
		
		replyService.get(rno, function(reply) {
			
			modalInputReply.val(reply.reply);
			modalInputReplyer.val(reply.replyer);
			modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readOnly","readOnly");
			modal.data("rno", reply.rno);
			
			modal.find("button[id !='modalCloseBtn']").hide();
			modalModBtn.show();
			modalRemoveBtn.show();
			
			$(".modal").modal("show"); 	
			
		});
	});
	
	// 댓글의 수정 이벤트 처리
	modalModBtn.on("click", function(e) {
		
		var reply = {rno : modal.data("rno"), reply : modalInputReply.val()};
		
		replyService.update(reply, function(result) {
			
			alert(result);
			modal.modal("hide");
			// 댓글이 페이지 처리되면 댓글의 수정과 삭제 시에도 현재 댓글이 포함된 페이지로 이동하도록 수정한다.
			showList(pageNum);
		});
	});

	// 댓글의 삭제 이벤트 처리
	modalRemoveBtn.on("click", function(e) {
		
		var rno = modal.data("rno");
		
		replyService.remove(rno, function(result) {
			
			alert(result);
			modal.modal("hide");
			showList(pageNum);
		});
	});
	
	
	// 페이지의 번호를 클릭했을 때 새로운 댓글을 가져오도록 하는 부분이다.
	// 댓글의 페이지 번호는 <a> 태그 내에 존재하므로 이벤트 처리에서는 <a> 태그의 기본 동작을 제한하고, 댓글 페이지 번호를 변경한 후 
	// 해당 페에지의 댓글을 가져오도록 한다.
	replyPageFooter.on("click", "li a", function(e) {
		e.preventDefault();
		console.log("page click");
		
		var targetPageNum = $(this).attr("href");
		
		console.log("targetPageNum ==== " + targetPageNum);
		
		pageNum = targetPageNum;
		
		showList(pageNum);
	});
	
	
	
	
	// for replyService add 함수 test
// 	replyService.add(
// 		{reply : "JS TEST", replyer : "tester", bno : bnoValue}
// 		,
// 		function(result) {
// 			alert("RESULT : " + result);
// 		}
// 	);
	
	// reply List test
// 	replyService.getList({bno:bnoValue, page:1}, function(list) {
		
// 		for(var i = 0, len = list.length || 0; i < len; i++) {
// 			console.log("list ============== ", list[i]);
// 		}
// 	});
	
	
	// 23번 댓글 삭제 테스트    성공하면 removed 실패하면 error
// 	replyService.remove(23, function(count) {
		
// 		console.log(count);
		
// 		if(count === "success") {
// 			alert("REMOVED");
// 		}
// 	}, function(err) {
// 		alert("ERROR.....");
// 	});
	
	// 22번 댓글 수정
// 	replyService.update({
// 		rno : 25,
// 		bno : bnoValue,
// 		reply : "수우우우우ㅜ정된 댓글"
// 	}, function(result) {
// 		alert("수정완료");
// 	});
	

	
// 	replyService.get(26, function(data) {
// 		console.log(data);
// 	});
	
	
	
	
});




function checkModal(result){
	if(result === '') {
		return;
	}
	if(parseInt(result) > 0 ) {
		$(".modal-body").html("게시글 " + parseInt(result) + "번이 등록되었습니다.");
	}
	
	$("#myModal").modal("show");
}

</script>



<%@ include file="../includes/footer.jsp" %>
            