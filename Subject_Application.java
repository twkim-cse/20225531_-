import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

public class Subject_Application extends JFrame {
	JTextField subject;
	JTextField name;
	JTextField profession;

	JTextField search;

	JRadioButton male = new JRadioButton("남");
	JRadioButton female = new JRadioButton("여");

	JButton bSearch; // 색인 실행을 위한 버튼
	JButton bSave; // 저장 실행을 위한 버튼
	JButton bDelete; // 삭제 실행을 위한 버튼
	JButton bNew;
	JButton bPrint; // 출력을 위한 버튼
	JButton bPreview; // 미리보기를 위한 버튼

	JList names = new JList();
	JList subjects = new JList();
	JList professions = new JList();


	Connection conn;
	//JPanel backgroundPanel;
	MainPanel mainPanel;
	
	// DefaultListModel<String> nameListModel = new DefaultListModel<>();
	// JList<String> names = new JList<>(nameListModel);
	
	public class MainPanel extends JPanel {
        private ImageIcon backgroundIcon;

        public MainPanel(ImageIcon backgroundIcon) {
            this.backgroundIcon = backgroundIcon;

            // 메인 패널의 크기를 배경 이미지의 크기로 설정
            setPreferredSize(new Dimension(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight()));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("paintComponent 호출됨");
            if (backgroundIcon != null) {
                // 배경 이미지 그리기
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

	public Subject_Application() {
		
		ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\kty13\\OneDrive\\바탕 화면\\20225531java\\Winter_project_my\\src\\배경.jpg");
		if (backgroundIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
		    System.out.println("이미지가 성공적으로 로드되었습니다.");
		} else {
		    System.out.println("이미지 로드에 실패했습니다.");
		}
	    // 메인 패널 생성
		mainPanel = new MainPanel(backgroundIcon);
		mainPanel.setLayout(new BorderLayout());
		
	    // 왼쪽 상단 패널 생성
	    JPanel leftTopPanel = new JPanel(new RiverLayout());
	    JScrollPane name_Scroller = new JScrollPane(names);
	    name_Scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    name_Scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    names.setVisibleRowCount(7);
	    names.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    names.setFixedCellWidth(100);
	    leftTopPanel.add("br center", new JLabel("학생 별 수강 과목"));
	    leftTopPanel.add("p center", name_Scroller);

	    // 오른쪽 상단 패널 생성
	    JPanel rightTopPanel = new JPanel(new RiverLayout());
	    subject = new JTextField(20);
	    name = new JTextField(10);
	    profession = new JTextField(20);
	    ButtonGroup gender = new ButtonGroup();
	    gender.add(male);
	    gender.add(female);

	    rightTopPanel.add("br center", new JLabel("수강신청 과목"));
	    rightTopPanel.add("p left", new JLabel("과 목  명"));
	    rightTopPanel.add("tab", subject);
	    rightTopPanel.add("br", new JLabel());
	    rightTopPanel.add("br", new JLabel("이  름"));
	    rightTopPanel.add("tab", name);
	    rightTopPanel.add("br", new JLabel());
	    rightTopPanel.add("br", new JLabel("전  공"));
	    rightTopPanel.add("tab", profession);
	    rightTopPanel.add("br", new JLabel());
	    rightTopPanel.add("br", new JLabel("성  별"));
	    rightTopPanel.add("tab", male);
	    rightTopPanel.add("tab", female);

	    // 왼쪽 하단 패널 생성
	    JPanel leftBottomPanel = new JPanel(new RiverLayout());
	    JPanel tmpPanel = new JPanel(new RiverLayout());
	    JPanel tmpPanel1 = new JPanel(new RiverLayout());
	    JPanel tmpPanel2 = new JPanel(new RiverLayout());

	    search = new JTextField(20);
	    bSearch = new JButton("검색");
	    bPrint = new JButton("출력");
	    bPreview = new JButton("미리보기");
	    tmpPanel1.add(search);
	    tmpPanel2.add(bSearch);
	    tmpPanel2.add(bPrint);
	    tmpPanel2.add(bPreview);
	    tmpPanel.add("center", tmpPanel1);
	    tmpPanel.add("br center", tmpPanel2);
	    leftBottomPanel.add("center", tmpPanel);

	    // 오른쪽 하단 패널 생성
	    JPanel rightBottomPanel = new JPanel(new RiverLayout());
	    tmpPanel = new JPanel(new RiverLayout());
	    bSave = new JButton("저장");
	    bDelete = new JButton("삭제");
	    bNew = new JButton("등록");
	    tmpPanel.add(bSave);
	    tmpPanel.add("tab", bDelete);
	    tmpPanel.add("tab", bNew);
	    rightBottomPanel.add("center", tmpPanel);
	    rightBottomPanel.add("br", Box.createRigidArea(new Dimension(0, 5)));

	 // 상단 패널 구성
	    JPanel topPanel = new JPanel(new GridLayout(1, 2));
	    topPanel.add(leftTopPanel);
	    topPanel.add(rightTopPanel);

	    // 하단 패널 구성
	    JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
	    bottomPanel.add(leftBottomPanel);
	    bottomPanel.add(rightBottomPanel);
	    
	    // 상단 패널과 하단 패널을 포함하는 메인 패널 조립
	    mainPanel.add(topPanel, BorderLayout.CENTER);
	    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

	    SearchListener searchListener = new SearchListener();
		bSearch.addActionListener(searchListener);
		bDelete.addActionListener(new DButtonListener());
		bNew.addActionListener(new ButtonListener());
		bSave.addActionListener(new SButtonListener());
		sbjList listener = new sbjList();
		names.addListSelectionListener(listener);
		bPrint.addActionListener(new PPButtonListener());
		bPreview.addActionListener(new PPButtonListener());

	    // 배경 이미지 패널을 프레임에 추가
	    getContentPane().add(mainPanel, BorderLayout.CENTER);

	    setTitle("수강신청 데이터베이스 시스템");
	    setSize(700, 500);
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		Subject_Application sa = new Subject_Application();
		sa.dbConnection();
		sa.dbRecord();
		sa.setVisible(true);
	}

	private void dbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/김태우proj", "root", "mite");
			//			dbRecord();
		} catch (Exception ex) {
			System.out.println("DB 연결 에러: " + ex.getMessage());
		}
	}

	public void dbRecord() {
		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT s.subject, name, gender, p.profession FROM class "
					+ "NATURAL JOIN subject_app sa NATURAL JOIN subject s NATURAL JOIN profession p");
			Vector<String> list = new Vector<String>();
			while (rs.next()) {
				String studentName = rs.getString("name");
				System.out.println("Retrieved student: " + studentName);
				list.add(studentName);
			}
			stmt.close();
			Collections.sort(list);
			names.setListData(list);

			if (!list.isEmpty()) {
				names.setSelectedIndex(0);
			}
			// repaint();
			// names.revalidate();

		} catch (SQLException sqlex) {
			System.out.println("SQL 에러: " + sqlex.getMessage());
			sqlex.printStackTrace();
		}
	}

	public class sbjList implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting() && !names.isSelectionEmpty()) {
				try {
					Statement stmt = conn.createStatement();
					String selectedName = (String) names.getSelectedValue();
					ResultSet rs = stmt.executeQuery("SELECT s.subject, name, gender, p.profession FROM class "
							+ "NATURAL JOIN subject_app sa NATURAL JOIN subject s NATURAL JOIN profession p WHERE name = '"
							+ selectedName + "'");

					if (rs.next()) {
						// 나머지 처리 코드...
						subject.setText(rs.getString("subject"));
						name.setText(rs.getString("name"));
						profession.setText(rs.getString("profession"));
						if (rs.getString("gender").equals("M"))
							male.setSelected(true);
						else
							female.setSelected(true);
					}
					stmt.close();

				} catch (SQLException sqlex) {
					System.out.println("sql에러 : " + sqlex.getMessage());

				} catch (Exception ex) {
					System.out.println("DB Handling 에러 (리스트 리스너): " + ex.getMessage());
				}
			}
		}
	}

	public class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = names.getNextMatch(search.getText().trim(), 0, Position.Bias.Forward);
			if (index != -1)
				names.setSelectedIndex(index);
			search.setText("");
		}
	}

	public class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			subject.setText("");
			name.setText("");
			profession.setText("");
			male.setSelected(true);
			female.setSelected(false);
			names.clearSelection();
		}

	}

	public class DButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Statement stmt = conn.createStatement();
				String selectedName = (String) names.getSelectedValue();

				// 이름으로 contact_id를 찾기
				ResultSet rs = stmt.executeQuery("SELECT contact_id FROM class WHERE name = '" + selectedName + "'");
				if (rs.next()) {
					int contactId = rs.getInt("contact_id");

					// subject_app 테이블에서 subject_id에 해당하는 레코드 삭제
					stmt.executeUpdate("DELETE FROM subject_app WHERE contact_id = " + contactId);

					// class 테이블에서 contact_id에 해당하는 레코드 삭제
					stmt.executeUpdate("DELETE FROM class WHERE contact_id = " + contactId);
				}

				stmt.close();

				dbRecord(); // 변경된 내용을 다시 로드
			} catch (SQLException sqlex) {
				System.out.println("에러 발생: " + sqlex.getMessage());
			} catch (Exception ex) {
				System.out.println("DELETE 리스너 에러");
			}
		}
	}
	// 이름 바꿀 시 prof_id가 그대로여야 하는데 계속해서 하나씩 증가하는 현상 해결해야 할 차례.
	// 또한 profession도 바꿀 시 전공 이름 또한 바껴야 함
	// profession도 바꿀시 contact_id는 그대로여야함
	// -> 해결방법으로는 데베 연동시 중복제거 함수를 넣으면 되지 않을까 싶음.

	public class SButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Statement stmt = conn.createStatement();

				// 입력받은 학생 정보
				String oldStudentName = (String) names.getSelectedValue();
				String newStudentName = name.getText().trim();
				String gender = male.isSelected() ? "M" : "F";
				// 전공 정보는 profession 텍스트 필드에서 입력받아야 합니다.
				String professionName = profession.getText().trim();
				// 과목 정보는 subject 텍스트 필드에서 입력받아야 합니다.
				String subjectName = subject.getText().trim();

				// 이름이 변경되었을 때, 기존 레코드를 업데이트
				stmt.executeUpdate("UPDATE class SET name = '" + newStudentName + "', gender = '" + gender
						+ "' WHERE name = '" + oldStudentName + "'");

				// 기존 레코드가 없으면 새로 추가
				int updatedRows = stmt.getUpdateCount();
				if (updatedRows == 0) {
					// 전공이 이미 존재하는지 확인
					ResultSet profIdResult = stmt.executeQuery(
							"SELECT prof_id FROM profession WHERE profession = '" + professionName + "'");
					int profId = -1;
					if (profIdResult.next()) {
						// 이미 존재하는 경우 해당 전공의 prof_id를 가져옴
						profId = profIdResult.getInt("prof_id");
					} else {
						// 전공이 없는 경우 전공을 추가하고 prof_id를 가져옴
						stmt.executeUpdate("INSERT INTO profession (profession) VALUES ('" + professionName + "')",
								Statement.RETURN_GENERATED_KEYS);
						ResultSet generatedKeys = stmt.getGeneratedKeys();
						if (generatedKeys.next()) {
							profId = generatedKeys.getInt(1);
						}
					}

					// 학생을 class 테이블에 추가
					stmt.executeUpdate("INSERT INTO class (name, gender, prof_id) VALUES ('" + newStudentName + "', '"
							+ gender + "', " + profId + ")", Statement.RETURN_GENERATED_KEYS);
					ResultSet classGeneratedKeys = stmt.getGeneratedKeys();
					int contactId = -1;
					if (classGeneratedKeys.next()) {
						contactId = classGeneratedKeys.getInt(1);

						// 이미 존재하는지 확인
						ResultSet subjectIdResult = stmt.executeQuery(
								"SELECT subject_id FROM subject WHERE subject = '" + subjectName + "'");
						int subjectId = -1;
						if (subjectIdResult.next()) {
							// 이미 존재하는 경우 해당 과목의 subject_id를 가져옴
							subjectId = subjectIdResult.getInt("subject_id");
						} else {
							// 과목이 없는 경우 과목을 추가하고 subject_id를 가져옴
							stmt.executeUpdate("INSERT INTO subject (subject) VALUES ('" + subjectName + "')",
									Statement.RETURN_GENERATED_KEYS);
							ResultSet subjectGeneratedKeys = stmt.getGeneratedKeys();
							if (subjectGeneratedKeys.next()) {
								subjectId = subjectGeneratedKeys.getInt(1);
							}
						}

						// 학생이 수강하는 과목을 subject_app 테이블에 추가
						stmt.executeUpdate("INSERT INTO subject_app (contact_id, subject_id) VALUES (" + contactId
								+ ", " + subjectId + ")");
						// ...
					}
				}

				stmt.close();
				dbRecord(); // 변경된 내용을 다시 로드
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러: " + sqlex.getMessage());
			} catch (Exception ex) {
				System.out.println("DB 에러: " + ex.getMessage());
			}
		}
	}

	public class PPButtonListener implements ActionListener {
		private JFrame previewFrame = new JFrame("미리보기");
		private JPanel previewPanel = new JPanel();
		private JButton nextPageButton = new JButton("다음 페이지");
		private static final int RECORDS_PER_PAGE = 10; // 페이지당 레코드 수

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bPreview) {
				showPrintPreview(); // 미리보기 버튼을 눌렀을 때 미리보기 패널이 나타남
			} else if (e.getSource() == bPrint) {
				printPanel(); // 프린트 버튼을 눌렀을 때 프린트 작업이 수행됨
			}
		}

		// 미리보기 기능 수행
		private void showPrintPreview() {
			// 현재 페이지를 나타내는 변수
			int currentPage = 1;

			// 미리보기 패널 설정
			previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS));

			// 첫 페이지 표시
			displayPage(createBook(currentPage));

			// 다음 페이지 버튼에 대한 액션 리스너 추가
			nextPageButton.addActionListener(new ActionListener() {
				private AtomicInteger currentPage = new AtomicInteger(1); // AtomicInteger로 변경

				@Override
				public void actionPerformed(ActionEvent e) {
					currentPage.incrementAndGet(); // 현재 페이지를 증가시킴
					Book nextPageBook = createBook(currentPage.get());
					if (nextPageBook != null) {
						displayPage(nextPageBook);
						previewFrame.revalidate(); // 프레임을 다시 그리도록 요청하여 변경 사항을 반영
					} else {
						nextPageButton.setEnabled(false); // 더 이상 페이지가 없으면 버튼 비활성화
					}
				}
			});

			// 미리보기 프레임 설정
			previewFrame.setLayout(new BorderLayout());
			previewFrame.add(new JScrollPane(previewPanel), BorderLayout.CENTER);
			previewFrame.add(nextPageButton, BorderLayout.SOUTH);
			previewFrame.setSize(400, 600);
			previewFrame.setLocationRelativeTo(null);
			previewFrame.setVisible(true);      
		}

		// 프린트 패널을 인쇄
		private void printPanel() {
			// Book 객체를 생성하고, 출력할 내용을 설정합니다.
			Book book = createBook(1); // 첫 번째 페이지만 인쇄합니다.

			// PrintablePanel을 Book 객체로 초기화합니다.
			PrintablePanel printablePanel = new PrintablePanel(book);

			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(printablePanel);

			if (job.printDialog()) {
				try {
					job.print();
				} catch (PrinterException ex) {
					ex.printStackTrace();
				}
			}
		}

		// 페이지 내용을 미리보기 패널에 표시하는 메소드
		private void displayPage(Book book) {
			previewPanel.removeAll(); // 이전 페이지의 내용을 모두 지움
			for (String record : book.getContent().split("\n")) {
				JLabel label = new JLabel(record);
				previewPanel.add(label); // 미리보기 패널에 레코드 추가
			}
		}


		// Book 객체를 생성하고, 출력할 내용을 설정합니다.
		private Book createBook(int currentPage) {
			StringBuilder content = new StringBuilder();
			try {
				Statement stmt = conn.createStatement();
				int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
				ResultSet rs = stmt.executeQuery("SELECT s.subject, name, gender, p.profession FROM class "
						+ "NATURAL JOIN subject_app sa NATURAL JOIN subject s NATURAL JOIN profession p "
						+ "LIMIT " + startIndex + ", " + RECORDS_PER_PAGE);

				while (rs.next()) {
					// 학생 정보를 미리보기 패널에 추가
					String studentInfo = "이름: " + rs.getString("name") + ", "
							+ "과목: " + rs.getString("subject") + ", "
							+ "성별: " + rs.getString("gender") + ", "
							+ "전공: " + rs.getString("profession");
					content.append(studentInfo).append("\n");
				}
				stmt.close();

			} catch (SQLException sqlex) {
				System.out.println("미리보기 에러: " + sqlex.getMessage());
			} catch (Exception ex) {
				System.out.println("미리보기 에러: " + ex.getMessage());
			}
			return new Book(content.toString());
		}
	}

}



