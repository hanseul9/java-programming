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
	private Image screenImage; // ��ũ�� ȭ��
	private Graphics screenGraphic; // ��ũ�� �׷���

//===========================================��ư �̹��� �ҷ�����====================================================
	// ���� ��ư�� �� �̹��� �ҷ�����, ũ�� ����
	private Image startButtonBasic = new ImageIcon("src/images/startButtonBasic.png").getImage();
	Image StartButton = startButtonBasic.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon StartButtonBasic = new ImageIcon(StartButton);

	private Image startButtonEntered = new ImageIcon("src/images/startButtonEntered.png").getImage();
	Image StartButton_ent = startButtonEntered.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon StartButtonEntered = new ImageIcon(StartButton_ent);

	// ���� ��ư�� �� �̹��� �ҷ�����, ũ�� ����
	private Image quitButtonBasic = new ImageIcon("src/images/quitButtonBasic.png").getImage();
	Image QuitButton = quitButtonBasic.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon QuitButtonBasic = new ImageIcon(QuitButton);

	private Image quitButtonEntered = new ImageIcon("src/images/quitButtonEntered.png").getImage();
	Image QuitButton_ent = quitButtonEntered.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	ImageIcon QuitButtonEntered = new ImageIcon(QuitButton_ent);

//============================================ȭ�� �̹���==================================================
	// ����ȭ��, �ε�ȭ��, ����ȭ���� �̹��� ���� �ҷ�����
	private Image introbackground = new ImageIcon("src/images/introbackground(title).jpg").getImage();
	private Image loadingbackground = new ImageIcon("src/images/loadingbackground.jpg").getImage();
	public static Image gamebackground = new ImageIcon("src/images/gamebackground.jpg").getImage();
	public static Image gamebackground2 = new ImageIcon("src/images/gamebackground.png").getImage();

//==========================================================================================================
	// ���� ��ư�� ���� ��ư�� �ش� �̹����� ����, ��ư ����
	private JButton startButton = new JButton(StartButtonBasic);
	private JButton quitButton = new JButton(QuitButtonBasic);

	// ���� ȭ���� �˱����� ��ũ�� ���� ������
	private boolean isMainScreen;
	private boolean isLoadingScreen;
	private boolean isGameScreen;

	// Game ��ü ����
	private Game game = new Game();

//=====================================================���� ȭ�� ���� ����==========================================
	public ShootingGame() {
		// shooting game ������ ����
		setUndecorated(true);
		setTitle("ShootingGame ���ð���");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);

		// �������
		Music introMusic = new Music("backgroundmusic.mp3", true);
		introMusic.start();

//==============================================��ư ���� �� �̺�Ʈ ����=======================================================
		// ���ӽ��� ��ư , �̺�Ʈ ����
		startButton.setBounds(900, 330, 300, 200);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			// ���콺�� �ö󰡸� ȿ������ �Բ� �׸� �ٲ�
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(StartButtonEntered);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("MP_Pressed.mp3", false);
				buttonEnteredMusic.start();
			}

			// ���콺�� �������� �ٽ� �����׸����� ����
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(StartButtonBasic);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ���콺�� ��ư Ŭ���ϸ� ȿ������ �Բ� �ε�ȭ��(loadingbackground)�� background ����
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("MP_Button.mp3", false);
				buttonEnteredMusic.start();
				introMusic.close(); // �ε�ȭ�� �߿��� ��� ���� ����
				startButton.setVisible(false);
				quitButton.setVisible(false);
				isMainScreen = false;
				isLoadingScreen = true;
			}
		});
		add(startButton); // startButton�� �߰�

		// �������� ��ư , �̺�Ʈ ����(â �ݱ�)
		quitButton.setBounds(900, 480, 300, 200);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			// ���콺�� �ö󰡸� ȿ������ �Բ� �׸� �ٲ�
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(QuitButtonEntered);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("MP_Pressed.mp3", false);
				buttonEnteredMusic.start();
			}

			// ���콺�� �������� �ٽ� �����׸����� ����
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(QuitButtonBasic);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ���콺�� ��ư�� ������ ȿ������ �Բ� (��� ��� ��) â����
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
		add(quitButton); // quitButton �߰�

		// Ű ������ �߰�
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					// ����Ű�� ������ �� ����ȭ���̸� ���� �����ϵ���
					if (isGameScreen) {
						game.setShooting(true);
						break;
					}
					// ����Ű�� ������ �� �ε�ȭ���̸� ����ȭ��(����ȭ��)���� �Ѿ����(game����)
					else if (isLoadingScreen) {
						isLoadingScreen = false;
						isGameScreen = true;
						game.start(); // game ������ ����
						break;// ����Ű�� ������ ����ȭ������ ��ȯ
					}
				case KeyEvent.VK_SHIFT: // Shift Ű�� ������ game�� setBomb �Լ��� true ����
					game.setBomb(true);
					break;
				case KeyEvent.VK_ESCAPE: // ESC�� ������ â ����
					System.exit(0);
					break;
				case KeyEvent.VK_R:
					if (game.isOver()) {
						game.setStart(true);
						game.reset();
					}
					break;
				case KeyEvent.VK_W: // WŰ�� ������ game�� setUp �Լ��� true ����
					game.setUp(true);
					break;
				case KeyEvent.VK_S: // SŰ�� ������ game�� setDown �Լ��� true ����
					game.setDown(true);
					break;
				case KeyEvent.VK_A: // AŰ�� ������ game�� setLeft �Լ��� true ����
					game.setLeft(true);
					break;
				case KeyEvent.VK_D: // DŰ�� ������ game�� setRight �Լ��� true ����
					game.setRight(true);
					break;
				}

			}

			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER: // EnterŰ�� �������� game�� setShooting �Լ��� false ����
					game.setShooting(false);
					break;
				case KeyEvent.VK_SHIFT: // shiftŰ�� �������� game�� setBomb �Լ��� false ����
					game.setBomb(false);
					break;
				case KeyEvent.VK_W: // WŰ�� �������� game�� setUp �Լ��� false ����
					game.setUp(false);
					break;
				case KeyEvent.VK_S: // SŰ�� �������� game�� setDown �Լ��� false ����
					game.setDown(false);
					break;
				case KeyEvent.VK_A: // AŰ�� �������� game�� setLeft �Լ��� false ����
					game.setLeft(false);
					break;
				case KeyEvent.VK_D: // DŰ�� �������� game�� setRight �Լ��� false ����
					game.setRight(false);
					break;
				}

			}

		});

		init(); // ���ӽ��� ���� �ʱ�ȭ

	}

	// ������ ó�� ���� �� ��ũ���� ���� �ʱ�ȭ, mainȭ����� �����ϹǷ� isMainScreen�� true�� �ʱ�ȭ
	private void init() {
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;
	}

	// paint �Լ�(�̹����� �׷��� ����)
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	// ȭ�鿡 �׸� �׸��� �Լ�
	public void screenDraw(Graphics g) {
		// ����ȭ���̶�� ����ȭ������ �� introbackground �׸���
		if (isMainScreen) {
			g.drawImage(introbackground, 0, 0, null);
		}
		// �ε�ȭ���̶�� �ε�ȭ������ �� loadingbackground �׸���
		if (isLoadingScreen) {
			g.drawImage(loadingbackground, 0, 0, null);
		}
		// ����ȭ���̶�� ����ȭ������ �� gamebackground �׸���, game Ŭ������ gameDraw()ȣ���ؼ� ���� ������Ʈ �׸���
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
