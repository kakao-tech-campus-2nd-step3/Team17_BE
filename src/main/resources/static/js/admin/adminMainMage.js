// 로컬 스토리지에서 토큰 확인
const token = localStorage.getItem("access_token");

if (token) {
  // 토큰이 있는 경우: 로그인 버튼 비활성화하고 관리자 접근 버튼 표시
  document.getElementById("kakaoLogin").style.display = "none";
  document.getElementById("adminPageButton").style.display = "inline-block";
}