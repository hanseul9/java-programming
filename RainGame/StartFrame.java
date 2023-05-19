package miniProject;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class StartFrame extends JFrame {
	// 버튼을 위해 이미지 로딩 하여 아이콘 만들기
	private ImageIcon normalIcon = new ImageIcon("normal.gif");
	private ImageIcon pressedIcon = new ImageIcon("pressed.gif");
	private ImageIcon overIcon = new ImageIcon("over.gif");
	
	private JMenuItem startItem = new JMenuItem("start");
	private JMenuItem stopItem = new JMenuItem("stop");
	private JButton startBtn = new JButton(normalIcon);
	private JButton stopBtn = new JButton("stop");
	
	private ScorePanel scorePanel = new ScorePanel();
	private RankPanel rankPanel = new RankPanel();
	private GamePanel gamePanel = new GamePanel(scorePanel, rankPanel);
	
	StartFrame startFrame = null;
	
	private int money;
	
	private boolean start = false;
	
	
	
	public StartFrame(int money) {
		
		this.money=money;
		this.startFrame=startFrame; //게임 프레임을 스코어 패널까지 넘겨줌
		
		System.out.println(money);
		
		setTitle("죠르디 타자게임");
		
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(800,800); //프레임 크기
		setLocation(600,170);
		
		splitPane(); // JSplitPane을 생성하여 컨텐트팬의 CENTER에 부착
		//makeMenu();
		makeToolBar();
		setResizable(false);
		setVisible(true);
	}

	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane, BorderLayout.CENTER);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(550);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gamePanel);
		
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(400);
		pPane.setTopComponent(scorePanel);
		pPane.setBottomComponent(rankPanel);
		hPane.setRightComponent(pPane);
	}
	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		setJMenuBar(mBar);
		JMenu fileMenu = new JMenu("Game");
		fileMenu.add(startItem);
		fileMenu.add(stopItem);
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("exit"));
		mBar.add(fileMenu);
		
		startItem.addActionListener(new StartAction());
	}
	
	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		tBar.add(startBtn);
		tBar.add(stopBtn);
		getContentPane().add(tBar, BorderLayout.NORTH);
		
		startBtn.addActionListener(new StartAction());
		stopBtn.addActionListener(new StopAction());
		
		
		startBtn.setRolloverIcon(overIcon);
		startBtn.setPressedIcon(pressedIcon);
	}
	
	private class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent e) { //시작버튼 누르면 게임 시작
			if (start==false) {
				gamePanel.gameStart(gamePanel, startFrame, money);
				//System.out.println();
				start=true;
			}
			
		}
	}
	
	private class StopAction implements ActionListener {
		public void actionPerformed(ActionEvent e) { //중지버튼 누르면 게임 중지
			
			gamePanel.stopGame(gamePanel);
			start=false;
		}
	}
	
	void giveStartFrame(StartFrame startFrame) {
		this.startFrame = startFrame; // 스코어 패널까지 전달하기 위해 연결
		
	}
	
	
}
