# Getting Started with STS

This project was coded with STS, JAVA JDK 11  
with using library spring web, devtools, security, lombok, jdbc, jpa, gson, jwt  
with Oracle DB  
rootProject.name = '1020_backend'  

## Available Scripts

구현 기능 (POSTMAN까지 확인 완료)  
-. 회원가입, 로그인  
-. 인증된 회원에 대한 회원정보 수정, 회원탈퇴  
-. 계좌 개설, 계좌목록 조회  

## List

1. 거래 적용
1. security config uri 권한 확인
1. login 유지 시간 확인
1. user detail impl - role 반환 수정
1. 모든 data 개인정보 암호화
1. 계좌 0원일 때만 해지하고 0원이 아닐 경우 다른 계좌에 이체하고 해지하거나 출금 후 해지 유도
1. 함수명, uri 정리, user, checkUser 변경
1. .get()s 처리, return type 정리해서 경우의 수 state diagram 정리 후 handling
1. paging 처리
1. 계좌 이체 (-) 금액 불가능하도록... DTO

## Memo

거래  
-. 화면에 사용자가 입력하는 건 무조건 받는 사람 계좌번호임  
-. 보내는 사람 계좌 번호도 저장은 해놔야 함  
-. A → B 의 직접 거래보다는 A → 은행, 은행 → B의 2 steps로 분리  
-. A, B는 entity에 저장하지 않도록 @Transient 처리  
-. 거래 type 지정으로 방향 지정하기로 함  
근데 모든 거래를 은행이 개설한 대표 계좌(?)를 경유해야 하는 게 되는 건가 흐음...  
뭐... log 남기는 걸 은행 계좌에 남기게 되는 셈인감...  

### Q

security test 순서