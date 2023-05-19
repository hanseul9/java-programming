package sg_1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ShootingGame extends JFrame {
	private Image screenImage; // 스크린 화면
	private Graphics screenGraphic; // 스크린 그래픽

//===========================================버튼 이미지 불러오기====================================================
	// 시작 버튼에 들어갈 이미지 불러오기, 크기 조절
	private Image startButtonBasic = new ImageIcon("src/images/startButtonBasic.png").getImage();
	Image StartButton = startButtonBasic.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon StartButtonBasic = new ImageIcon(StartButton);

	private Image startButtonEntered = new ImageIcon("src/images/startButtonEntered.png").getImage();
	Image StartButton_ent = startButtonEntered.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon StartButtonEntered = new ImageIcon(StartButton_ent);

	// 종료 버튼에 들어갈 이미지 불러오기, 크기 조절
	private Image quitButtonBasic = new ImageIcon("src/images/quitButtonBasic.png").getImage();
	Image QuitButton = quitButtonBasic.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon QuitButtonBasic = new ImageIcon(QuitButton);

	private Image quitButtonEntered = new ImageIcon("src/images/quitButtonEntered.png").getImage();
	Image QuitButton_ent = quitButtonEntered.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon QuitButtonEntered = new ImageIcon(QuitButton_ent);

//============================================화면 이미지==================================================
	// 메인화면, 로딩화면, 게임화면의 이미지 각각 불러오기
	private Image introbackground = new ImageIcon("src/images/introbackground(title).jpg").getImage();
	private Image loadingbackground = new ImageIcon("src/images/loadingbackground.jpg").getImage();
	public static Image gamebackground = new ImageIcon("src/images/gamebackground.jpg").getImage();
	public static Image gamebackground2 = new ImageIcon("src/images/gamebackground.png").getImage();

//==========================================================================================================
	// 시작 버튼과 종료 버튼에 해당 이미지를 삽입, 버튼 생성
	private JButton startButton = new JButton(StartButtonBasic);
	private JButton quitButton = new JButton(QuitButtonBasic);

	// 현재 화면을 알기위한 스크린 상태 변수들
	private boolean isMainScreen;
	private boolean isLoadingScreen;
	private boolean isGameScreen;

	// Game 객체 생성
	private Game game = new Game();

//=====================================================게임 화면 설정 시작==========================================
	public ShootingGame() {
		// shooting game 프레임 설정
		setUndecorated(true);
		setTitle("ShootingGame 슈팅게임");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);

		// 배경음악
		Music introMusic = new Music("backgroundmusic.mp3", true);
		introMusic.start();

//==============================================버튼 설정 및 이벤트 적용=======================================================
		// 게임시작 버튼 , 이벤트 적용
		startButton.setBounds(900, 330, 300, 200);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			// 마우스가 올라가면 효과음과 함께 그림 바뀜
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(StartButtonEntered);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("MP_Pressed.mp3", false);
				buttonEnteredMusic.start();
			}

			// 마우스가 내려가면 다시 원래그림으로 복귀
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(StartButtonBasic);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 마우스로 버튼 클릭하면 효과음과 함께 로딩화면(loadingbackground)로 background 설정
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("MP_Button.mp3", false);
				buttonEnteredMusic.start();
				introMusic.close(); // 로딩화면 중에는 잠시 음악 끊김
				startButton.setVisible(false);
				quitButton.setVisible(false);
				isMainScreen = false;
				isLoadingScreen = true;
			}
		});
		add(startButton); // startButton을 추가

		// 게임종료 버튼 , 이벤트 적용(창 닫기)
		quitButton.setBounds(900, 480, 300, 200);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			// 마우스가 올라가면 효과음과 함께 그림 바뀜
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(QuitButtonEntered);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("MP_Pressed.mp3", false);
				buttonEnteredMusic.start();
			}

			// 마우스가 내려가면 다시 원래그림으로 복귀
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(QuitButtonBasic);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 마우스로 버튼을 누르면 효과음과 함께 (잠시 대기 후) 창종료
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("MP_Button.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(700);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton); // quitButton 추가

		// 키 리스너 추가
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					// 엔터키가 들어왔을 때 게임화면이면 슈팅 구현하도록
					if (isGameScreen) {
						game.setShooting(true);
						break;
					}
					// 엔터키가 들어왔을 때 로딩화면이면 다음화면(게임화면)으로 넘어가도록(game시작)
					else if (isLoadingScreen) {
						isLoadingScreen = false;
						isGameScreen = true;
						game.start(); // game 스레드 시작
						break;// 엔터키가 들어오면 게임화면으로 전환
					}
				case KeyEvent.VK_SHIFT: // Shift 키가 들어오면 game의 setBomb 함수에 true 전달
					game.setBomb(true);
					break;
				case KeyEvent.VK_ESCAPE: // ESC가 들어오면 창 종료
					System.exit(0);
					break;
				case KeyEvent.VK_R:
					if (game.isOver()) {
						game.setStart(true);
						game.reset();
					}
					break;
				case KeyEvent.VK_W: // W키가 들어오면 game의 setUp 함수에 true 전달
					game.setUp(true);
					break;
				case KeyEvent.VK_S: // S키가 들어오면 game의 setDown 함수에 true 전달
					game.setDown(true);
					break;
				case KeyEvent.VK_A: // A키가 들어오면 game의 setLeft 함수에 true 전달
					game.setLeft(true);
					break;
				case KeyEvent.VK_D: // D키가 들어오면 game의 setRight 함수에 true 전달
					game.setRight(true);
					break;
				}

			}

			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER: // Enter키가 떨어지면 game의 setShooting 함수에 false 전달
					game.setShooting(false);
					break;
				case KeyEvent.VK_SHIFT: // shift키가 떨어지면 game의 setBomb 함수에 false 전달
					game.setBomb(false);
					break;
				case KeyEvent.VK_W: // W키가 떼어지면 game의 setUp 함수에 false 전달
					game.setUp(false);
					break;
				case KeyEvent.VK_S: // S키가 떼어지면 game의 setDown 함수에 false 전달
					game.setDown(false);
					break;
				case KeyEvent.VK_A: // A키가 떼어지면 game의 setLeft 함수에 false 전달
					game.setLeft(false);
					break;
				case KeyEvent.VK_D: // D키가 떼어지면 game의 setRight 함수에 false 전달
					game.setRight(false);
					break;
				}

			}

		});

		init(); // 게임시작 조건 초기화

	}

	// 게임을 처음 켰을 때 스크린의 상태 초기화, main화면부터 시작하므로 isMainScreen만 true로 초기화
	private void init() {
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;
	}

	// paint 함수(이미지와 그래픽 생성)
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	// 화면에 그림 그리는 함수
	public void screenDraw(Graphics g) {
		// 메인화면이라면 메인화면으로 쓸 introbackground 그리기
		if (isMainScreen) {
			g.drawImage(introbackground, 0, 0, null);
		}
		// 로딩화면이라면 로딩화면으로 쓸 loadingbackground 그리기
		if (isLoadingScreen) {
			g.drawImage(loadingbackground, 0, 0, null);
		}
		// 게임화면이라면 게임화면으로 쓸 gamebackground 그리기, game 클래스의 gameDraw()호출해서 게임 컴포넌트 그리기
		if (isGameScreen) {
			g.drawImage(gamebackground, 0, 0, null);

			try {
				game.gameDraw(g);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		paintComponents(g);
		this.repaint();
	}

}
