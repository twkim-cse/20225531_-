# 🚀 수강신청 데이터베이스 시스템 (Hero)
> **20225531 김태우**의 Java Swing & MySQL 연동 수강신청 관리 프로그램입니다.

### 🎬 DEMO
- 데이터베이스(MySQL)에 저장된 학생 및 수강 정보를 GUI 화면을 통해 실시간으로 확인하고 제어할 수 있습니다.
- (추후 실제 실행 화면 GUI 캡처 이미지 또는 GIF가 추가될 예정입니다.)

### ✨ Features (주요 기능)
- **학생 정보 조회 및 검색:** 등록된 학생 목록을 가나다순으로 정렬하여 보여주고, 검색 기능을 통해 특정 학생을 빠르게 찾을 수 있습니다.
- **수강 정보 관리:** 신청 과목, 학생 이름, 직업, 성별 정보를 신규 등록(Save), 수정, 삭제(Delete)할 수 있습니다.
- **인쇄 및 미리보기:** 수강신청 목록을 페이지별(10개씩)로 미리보고, 실제 프린터로 출력(Print)할 수 있는 기능을 지원합니다.

### 🏗️ Architecture (구조)
- **Language & UI:** Java (JDBC, Swing Component, RiverLayout)
- **Database:** MySQL (class, subject_app, subject, profession 테이블 간의 NATURAL JOIN 활용)
- **주요 클래스:**
  - `Subject_Application`: 메인 GUI 창 및 DB 연동 총괄
  - `MainPanel`: 배경 이미지를 그리기 위한 커스텀 패널 클래스
  - `SButtonListener` / `DButtonListener` / `SearchListener`: 저장, 삭제, 검색 등 각 버튼의 이벤트 처리 클래스

### 🏃 Runbook (실행 방법)
1. 로컬 환경에 **MySQL 서버**를 구동하고, `¿proj` 데이터베이스와 관련 테이블을 생성합니다.
2. `Subject_Application.java` 소스 코드 내 DB 연결 정보(ID: `root`, PW: `mite`)를 확인합니다.
3. 메인 메서드를 실행하여 GUI 시스템을 구동합니다.

### 🛠️ Troubleshooting (문제 해결)
- **DB 연결 실패 에러:** MySQL 드라이버(`com.mysql.cj.jdbc.Driver`)가 라이브러리(Build Path)에 정상적으로 추가되었는지 확인하세요.
- **한글 깨짐 현상:** 이클립스나 VS Code 등 사용 중인 IDE의 텍스트 인코딩 설정을 `UTF-8` 또는 `EUC-KR`로 일치시켜 주세요.
