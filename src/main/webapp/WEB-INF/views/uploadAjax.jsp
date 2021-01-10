<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<style>
	.uploadResult {
		width: 100%;
		background-color: gray;
	}
	.uploadResult ul {
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	.uploadResult ul li {
		list-style: none;
		padding: 10px;
	}
	.uploadResult ul li img {
		width: 20px;
	}
	
</style>
<body>
	
	<h1>Upload With Ajax</h1>
	
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple />
	</div>
	
	<div class="uploadResult" >
		<ul>
		
		</ul>
	</div>
	
	
	<button id="uploadBtn">Upload</button>
	
	<div class="uploadResult">
		<ul>
		
		</ul>
	</div>
	
</body>

<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; // 5MB
	
	// 파일 업로드 유효성 검사
	// 파일의 확장자나 크기의 사전 처리
	function checkExtension(fileName, fileSize) {
		
		if(fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if(regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	var cloneObj = $(".uploadDiv").clone();
	
	$("#uploadBtn").on("click", function(e) {
		
		var formData = new FormData();
		
		var inputFile = $("input[name='uploadFile']");
		
		var files = inputFile[0].files;
		
		console.log(files);
		
		// add fileData to formData
		for(var i = 0; i < files.length; i++) {
			
			if(!checkExtension(files[i].name, files[i].size) ) {
				return false;
			}
			
			formData.append("uploadFile", files[i]);
			
		}
		
		$.ajax({
			url : '/uploadAjaxAction',
			processData : false,
			contentType : false,
			data : formData,
			type : "POST",
			dataType : "json",
			success : function(result) {
// 				alert("Uploaded");
				console.log(result);
				
				showUploadedFile(result);
				
				$(".uploadDiv").html(cloneObj.html());
			}
		});
		
	});
	
	var uploadResult = $(".uploadResult ul");
	
	// 업로드된 파일의 목록을 보여주는 곳이다.
	function showUploadedFile(uploadResultArr) {
		
		var str = "";
		
		$(uploadResultArr).each(function(i, obj) {
			
			if(!obj.image) {
				var fileCallPath = encodeURIComponent( obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
				
				str += "<li><a href='/download?fileName=" + fileCallPath + "'>" 
					+  "<img src='/resources/img/attach.jpg'>" + obj.fileName + "</li>";
			} else {
// 	 			str += "<li>" + obj.fileName + "</li>";
				
				var fileCallPath = encodeURIComponent( obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName );
				
				str += "<li><img src='/display?fileName=" + fileCallPath + "'></li>";
				
			}
		});
		
		uploadResult.append(str);
	}
	
	
});
</script>




</html>