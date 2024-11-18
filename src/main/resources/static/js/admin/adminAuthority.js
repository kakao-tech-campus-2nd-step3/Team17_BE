// HTML 데이터 속성에서 role 값 가져오기
const role = document.getElementById('role').getAttribute('data-role');

// role 값에 따라 표시할 콘텐츠 결정
if (role === 'admin') {
  document.getElementById('adminPromote').style.display = 'none';
  document.getElementById('adminPage').style.display = 'flex';
} else if (role === 'user') {
  document.getElementById('adminPromote').style.display = 'flex';
  document.getElementById('adminPage').style.display = 'none';
}