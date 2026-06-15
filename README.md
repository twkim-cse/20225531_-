# 🚀 수강신청 데이터베이스 시스템 (Hero)
> **20225531 김태우**의 Java Swing & MySQL 연동 수강신청 관리 프로그램입니다.

## ⚡ 시스템 효율성 개선 경험 (System Efficiency Optimization)
본 프로젝트는 대용량 데이터 환경과 다중 사용자 환경을 고려하여, 자바 Swing 클라이언트와 MySQL 데이터베이스 간의 통신 및 렌더링 효율성을 다음과 같이 개선하였습니다.

- **대용량 데이터 조회 효율성 개선 (DB 페이징 처리 도입):** `PPButtonListener` 클래스 내에 `LIMIT` 쿼리를 활용한 페이징 메커니즘(RECORDS_PER_PAGE = 10)을 구현했습니다. 한 번에 10개씩만 잘라서 가져오기 때문에 전체 조회 대비 네트워크 트래픽을 줄이고 GUI 렌더링 속도를 극대화했습니다.
- **중복 데이터 방지를 위한 정규화 및 데이터베이스 참조 최적화:** `SButtonListener`에서 `SELECT`로 기존 데이터의 존재 여부를 먼저 확인한 후, 데이터가 없을 때만 `INSERT`를 수행하고 생성된 Key값(`prof_id`, `subject_id`)만 참조하도록 설계하여 디스크 용량을 최적화했습니다.

### 🎬 DEMO
- 데이터베이스(MySQL)에 저장된 학생 및 수강 정보를 GUI 화면을 통해 실시간으로 확인하고 제어할 수 있습니다.
- (추후 실제 실행 화면 GUI 캡처 이미지 또는 GIF가 추가될 예정입니다.)

### ✨ Features (주요 기능)

#### 1. 학생 및 수강 정보 실시간 조회 (Read)
- 데이터베이스 구동 시 `class`, `subject_app`, `subject`, `profession` 4개 테이블을 `NATURAL JOIN`으로 결합하여 최신 정보를 실시간으로 불러옵니다.
- 등록된 학생들의 이름을 **가나다순(Alphabetical Sort)**으로 자동 정렬하여 `JList`에 표시합니다.
- 리스트에서 학생 이름을 클릭하면 해당 학생의 신청 과목, 이름, 직업, 성별 정보가 우측 텍스트 필드와 라디오 버튼에 자동으로 동기화됩니다.

#### 2. 조건 검색 기능 (Search)
- 상단 검색 창에 학생 이름을 입력하고 '검색' 버튼을 누르면, 리스트 내에서 일치하는 학생의 위치를 찾아 자동으로 포커스(Selection) 이동합니다.

#### 3. 수강 정보 신규 등록 및 수정 (Create & Update)
- **신규 등록:** '비우기(bNew)' 버튼으로 입력 창을 초기화한 뒤, 새로운 정보를 입력하고 '저장(bSave)' 버튼을 누르면 DB에 데이터가 새롭게 삽입(Insert)됩니다.
- **연쇄 등록 메커니즘:** 새로운 직업(Profession)이나 새로운 과목(Subject)이 입력될 경우, 데이터베이스의 기본키(`prof_id`, `subject_id`)를 자동으로 생성하여 참조 무결성을 유지하며 연쇄 등록합니다.
- **정보 수정:** 기존 학생의 이름이나 성별을 변경하고 저장하면 `UPDATE` 쿼리가 실행되어 기존 데이터가 수정됩니다.

#### 4. 데이터 삭제 및 동기화 (Delete)
- '삭제(bDelete)' 버튼 클릭 시, 선택된 학생의 고유 ID(`contact_id`)를 추적합니다.
- 데이터베이스 외래키 관계를 고려하여 `subject_app` 테이블의 신청 내역을 먼저 삭제한 후, `class` 테이블에서 학생 정보를 안전하게 삭제(Cascade Delete 로직 구현)합니다.

#### 5. 데이터 출력 및 미리보기 (Print & Preview)
- **인쇄 미리보기:** 데이터가 많을 경우 한 페이지당 **10개씩(RECORDS_PER_PAGE = 10)** 나누어 볼 수 있는 페이징 처리가 구현되어 있습니다. '다음 페이지' 버튼을 통해 전체 목록을 스크롤하며 확인할 수 있습니다.
- **실제 인쇄:** `PrintablePanel`과 자바 표준 `PrinterJob`을 연동하여 현재 화면에 보이는 수강신청 목록을 실제 프린터로 출력하는 기능을 지원합니다.

### 🏗️ Architecture (구조)
- **Language & UI:** Java (JDBC, Swing Component, RiverLayout)
- **Database:** MySQL (class, subject_app, subject, profession 테이블 간의 NATURAL JOIN 활용)
- **주요 구성 요소:**
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
