function openTab(evt, tabName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}
function getUserNo() {
    // div 태그에서 data-user-no 속성을 읽어와 반환합니다.
    return $('#userData').data('userNo');
}


$(document).ready(function() {

    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 모든 AJAX 요청에 CSRF 토큰을 헤더로 보내도록 설정
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });

    const nicknameEditButton = $('#nicknameEditButton');
    const nicknameEditBox = $('#nicknameEditBox');
    const confirmButton = $('#confirmButton');
    const checkDuplicateButton = $('#checkDuplicateButton');
    const nicknameInput = $('#nicknameInput');

    nicknameEditButton.on('click', function() {
        // 닉네임 변경 버튼 숨기기
        nicknameEditButton.hide();

        // 중복 확인 박스와 확인 버튼 표시
        nicknameEditBox.css('display', 'flex');
        confirmButton.show();
    });




    checkDuplicateButton.on('click', function() {
        const newNickname = nicknameInput.val();

        if (newNickname === '') {
            alert('닉네임을 입력해주세요.');
            return;
        }

        // 여기서 $.ajax를 사용하여 닉네임 중복 확인 로직을 수행합니다.
        $.ajax({
            type: "GET",

            url: "/user/mypage/profile/check/checkNickNm", // 요청 URL
            data: { newNickname: newNickname },  // 전송할 데이터
            dataType: "json",  // 응답 데이터의 유형을 지정 (JSON 으로 받을 경우)
            success: function(response) {
                if (response.exists) {
                    alert("닉네임이 이미 존재합니다.");
                } else {
                    alert("사용 가능한 닉네임입니다.");
                }
            },
            error: function(error) {
                console.error(error);
                alert("오류가 발생했습니다. 다시 시도하세요.");
            }
        });
    });






   confirmButton.on('click', function() {
        // Reset everything to original state
        const newNickname = nicknameInput.val();
        nicknameEditBox.hide();
        confirmButton.hide();
        nicknameEditButton.show();

        const userEditInfo = {
            userNo: getUserNo(), // userNo를 얻는 함수를 호출
            userNickNm: newNickname // 입력한 새 닉네임
        };

        $.ajax({
            type: "POST",
            url: "/user/mypage/profile/profilePage/update/updateNickname",
            contentType: "application/json",
            data: JSON.stringify(userEditInfo),
            dataType: "json",
            success: function(response) {
                // 서버에서 응답으로 온 UserEditInfo 객체 사용
                alert('닉네임이 변경되었습니다. 다시 로그인 해주세요');
                console.log('Nickname updated to: ', response.userNickNm);
                // 페이지 내 닉네임을 표시하는 요소를 업데이트 할 수 있습니다.
                $('#someElementToShowNickname').text(response.userNickNm);
                nicknameEditBox.hide();
                confirmButton.hide();
                nicknameEditButton.show();
            },or: function(xhr, status, error) {
                // 오류 메시지를 표시
                alert("An error occurred: " + xhr.responseText);
            }
        });
    });


});
