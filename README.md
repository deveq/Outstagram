# Outstagram
#### 개인프로젝트, Kotlin, Android Studio
#### 개발기간 : 2020.11.07 ~ 2020.11.10

## 애플리케이션 설명
인스타그램의 기능을 모방한 프로젝트입니다.
#### 참고자료
1. https://www.fastcampus.co.kr/ 패스트캠퍼스의 안드로이드 앱 개발 과정
2. https://darkstart.tistory.com/66 : failed : EACCES (Permission denied) 문제
3. https://ju-hy.tistory.com/59 : Fragment에서 startActivityForResult 시 requestCode가 이상한 문제.

## 프로젝트 소개
- 회원가입 및 로그인
- token을 가진 회원만 글 확인 가능
- 전체 포스팅, 나의 포스팅 확인
- 사진과 내용 업로드

## 사용한 기능
- Retrofit으로 서버의 데이터 받아오기 (REST API)
- ViewModel로 Activity와 Fragment의 데이터 공유
- Stetho로 서버와의 네트워크 상태 확인

## 구동 이미지
1. 회원가입

![1  signup](https://user-images.githubusercontent.com/66777885/98694315-a4bf0680-23b4-11eb-8cb8-a909c5122f64.gif)

서버에 id, pw를 전송후 정상 가입 완료 되면 token을 받아서 SharedPreference에 저장해줌.

2. 하단 탭

![2  tab](https://user-images.githubusercontent.com/66777885/98694437-c9b37980-23b4-11eb-83bd-9287be0bb5a7.gif)

ViewPager2와 TabLayout을 사용해서 Activity내에 Fragment를 넣음

3. 사진 업로드

![3  upload1](https://user-images.githubusercontent.com/66777885/98694468-d20bb480-23b4-11eb-870a-6613490bcf5c.gif)

사진 1 업로드


![4  upload2](https://user-images.githubusercontent.com/66777885/98694488-da63ef80-23b4-11eb-87b8-8d0e5674ace0.gif)

사진 2 업로드 & 나의 포스트 확인


4. 로그아웃, 로그인

![5  login_logout](https://user-images.githubusercontent.com/66777885/98694539-eb146580-23b4-11eb-99ad-4e9593ded5c2.gif)

로그아웃 뒤 로그인 하여도 나의 포스트 목록에는 내 아이템만 조회됨.

