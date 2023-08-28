	$(function(){ //html /css 준비가 끝나면 실행해라

        setTimeout(function() {
         window.scrollTo(0, 500);
              }, 500);

         // 파일 선택 버튼 클릭 시 파일 업로드 input 활성화
//          $("#fileUpload").click(function() {
//            $(".i_file").click();
//          });
//
//
//           선택한 파일명 표시
//          $(".i_file").change(function() {
//            var fileName = $(this).val().split("\\").pop();
//            $("#fileName").val(fileName);
//            $("#fileDelete").show();
//          });
//
//
//           파일 삭제 버튼 클릭 시 선택한 파일 제거
//          $("#fileDelete").click(function() {
//            var fileInput = $(".i_file");
//            fileInput.val("");
//            $("#fileName").val("");
//            $(this).hide();
//          });

        $("#fileUpload").click(function() {
            $("#asFormImgFile").click();
                });

        $("#asFormImgFile").change(function() {
                      var fileName = $(this).val().split("\\").pop();
                      $("#fileName").val(fileName);
                      $("#fileDelete").show();
                  });

         $("#fileDelete").click(function() {
            var fileInput = $("#asFormImgFile");
            fileInput.val("");
            $("#fileName").val("");
            $(this).hide();
        });







            //신청하기 클릭했을때 폼 전달
           $(".btnReq").click(function() {
             // 체크박스 상태 확인
             if ($("#check1").prop("checked")) {
               // 신청서 필수항목 체크
               if (
                 $("#user_nm").val() === "" ||
                 $("#tel1").val() === "" ||
                 $("#tel2").val() === "" ||
                 $("#tel3").val() === "" ||
                 $("#home_zip5").val() === "" ||
                 $("#road_addr").val() === "" ||
                 $("#home_addr").val() === "" ||
                 $("#email1").val() === "" ||
                 $("#email2").val() === "" ||
                 $("#title").val() === "" ||
                 $("#txt_content").val() === ""
               ) {
                 alert("필수 입력 항목을 모두 입력해주세요.");
               } else {
                 // 폼 전송
                 $("#asSubmitForm").submit();
               }
             } else {
               alert("개인정보취급방침에 동의해야 신청할 수 있습니다.");
             }
           });




               });