package miniProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GamePanel extends JPanel {
	
	GamePanel gamePanel=null; //다른 클래스에서 패널 조작
	StartFrame startFrame = null;
	
	private JTextField input = new JTextField(20); //아래 입력칸
	//private JLabel text = new JLabel("타이핑해보세요"); //떨어지는 텍스트
	private String selectText=null;
	
	private ScorePanel scorePanel = null;
	private RankPanel rankPanel = null;
	private TextSource textSource = new TextSource(); // 단어 벡터 생성
	
	//falling
	private JLabel label = new JLabel(); // 떨어지는 단어 
	private JLabel resLabel = new JLabel("성공/실패"); // 성공 실패를 나타내는 레이블
	private Words words = null;
	private String fallingWord = null;
	private FallingThread thread = null; 
	private boolean gameOn = false;
	
	private int count = 0;
	
	int money = 0 ; //옷의 금액
	
	//private JLabel label [] = new JLabel [10]; //떨어지는 단어
	
	// 새로 추가 수정. 10개의 스레드 레퍼런스 배열
	//FallingThread th [] = new FallingThread [10];
	//private JLabel test [] = new JLabel [10]; //떨어지는 단어
	
	//테스트중
	 WordsThread newWord = null;
	 FallingThread falling = null;
	 
	 private JLabel test [] = new JLabel [10]; //떨어지는 단어
	 //private Vector<JLabel> storeWords = new Vector<JLabel>(); //생성된 단어들을 저장
	 private Vector<Thread> storeThread = new Vector<Thread>(); //스레드 저장
	 
	 Vector storeWords = new Vector();
	 private Vector<JLabel> wordsLabel = new Vector<JLabel>();
	 
	 ThreadGroup virusGrp = new ThreadGroup("virus");
	 
	 
	
	ImageIcon icon = new ImageIcon("ground.jpg");
	Image img = icon.getImage(); 
	
	 class WordsManger
     { 
           String word; 
           int x = 0; 
           int y = 0; 
           int step = 5; 

           Color color = Color.BLACK;
           
           boolean isVirus = false; 
           
           WordsManger(String word) {this(word, 10, false);} 
           WordsManger(String word, boolean isVirus) { this(word, 10, isVirus); } 
           WordsManger(String word, int step, boolean isVirus) 
           { 
                 this.word = word; 
                 this.step = step; 
                 this.isVirus = isVirus; 

                 if(isVirus) color = Color.GREEN; 
     
                 //int strWidth = fm.stringWidth(word); 

                 x = (int)(Math.random() * 500); 

                // if(x + strWidth >= screenWidth) 
                      // x = screenWidth - strWidth; 
           } 
           public String toString() {return word;} 
     } 
	
	
	public GamePanel() {
		setLayout(new BorderLayout());
		add(new GameGroundPanel(), BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		//setLayout(null); // 배치관리자를 사용하지 않고 절대 위치에 컴포넌트를 배치한다.	
//		input.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JTextField t = (JTextField)(e.getSource());
//				String inWord = t.getText();
//				if(text.getText().equals(inWord)) { // 맞추기 성공
//					// 점수 올리기
//					scorePanel.increase();
//					startGame();
//					
//					//input 창 지우기
//					t.setText("");
//				}
//			}
//		});
		
		
		
	}

	
	public GamePanel(ScorePanel scorePanel, RankPanel rankPanel) {
		this.scorePanel = scorePanel;
		this.rankPanel = rankPanel;
		
		
		setLayout(new BorderLayout());
		add(new GameGroundPanel(), BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		words = new Words("words.txt");

		//setLayout(null);
		
		
		
		resLabel.setLocation(0,0); //성공 실패를 나타내는 공간
		resLabel.setSize(100, 30);
		add(resLabel);
		
		Container parent = resLabel.getParent();
		System.out.println(parent);
		
//		input.setLocation(500,500);
//		input.setLocation(100, 30);
//		add(input); //입력창
		//gameStart();
		
		
		
		
		setFocusable(true);
		requestFocus();
		
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //타이핑 엔터시
				
				
				JTextField t = (JTextField)(e.getSource()); // 사용자가 입력한 텍스트정보
				System.out.println(t);
				String inWord = t.getText();// 사용자가 입력한 텍스트
				//System.out.println(inWord); 
				//System.out.println(selectText); //선택된 단어
				
				
				
				int i = 0;
				
				for(i=0;i<storeWords.size();i++) {
					WordsManger temp = (WordsManger)storeWords.get(i); 
					if(temp.word.equals(inWord))
						break;
				}
				
				System.out.println(i);
				System.out.println(storeWords.size());
				
				if(i==storeWords.size()) { // 맞추기 실패
					t.setText("");
				}
				else { // 맞추기 성공
					storeWords.remove(i);
					scorePanel.increase(10); //점수 증가
					rankPanel.increase(10);
					//startGame();
					
					//input 창 지우기
					t.setText("");
					
					printResult("성공");
				}
				
//				if(selectText.equals(inWord)) { // 맞추기 성공
//					// 점수 올리기
//					scorePanel.increase();
//					//startGame();
//					
//					//input 창 지우기
//					t.setText("");
//					
//					printResult("성공");
//					//Game(); // 게임 중지
//					//stopGame();
//					//gameStart(); // 새 단어 시작
//				}
//				else {
//					//input 창 지우기
//					t.setText("");
//				}
			}
		});
		
		
		
		
	}
	
	public void gameStart(GamePanel gamePanel, StartFrame startFrame, int money) {
		
		this.gamePanel = gamePanel;
		this.startFrame = startFrame;
		this.money = money;
		
		System.out.println(money+"asdfasdfafdsfdas");
		
		rankPanel.giveMoney(money);
		
		//System.out.println(gamePanel);
		
		//this.gamePanel = gamePanel;
		
		//gamePanel.setLayout(null);//배치관리자 삭제
		
		
		newWord = new WordsThread(); // 단어 생성 스레드 시작
		newWord.start(); 
		

        falling = new FallingThread(); 
        falling.start(); 
        
		
//		
//		for(int i=0;i<test.length;i++) { //테스트
//			test[i]= new JLabel("11");
//			test[i].setSize(200, 30); // 레이블 크기
//			test[i].setLocation(i*10,i*10);
//			
//			th[i] = new FallingThread(this, test[i]); // 게임 스레드
//			th[i].start(); // 스레드 시작
//			
//			add(test[i]);
//		}
//		
//		fallingWord = words.getRandomWord(); //떨어지는 단어
//		
//		
//		System.out.println(fallingWord);
//		
//		int x = (int)(Math.random()*550); //랜덤으로 생성될 위치
//		
//		label.setText(fallingWord);
//		label.setSize(200, 30); // 레이블 크기
//		label.setLocation(x,10); // 레이블 위치
//		label.setForeground(Color.MAGENTA); //레이블의 글자 색을 설정한다.				
//		label.setFont(new Font("Tahoma", Font.ITALIC, 15)); // 레이블 글자의 폰트를 설정한다.	
//		
//
//		thread = new FallingThread(this, label); // 게임 스레드
//		thread.start(); // 스레드 시작
//		gameOn = true;
	}
	
//	 public void gameStart() {
//    	 newWord = new WordsThread(); // 단어 생성 스레드 시작
// 		newWord.start(); 
//
//         falling = new FallingThread(); 
//         falling.start(); 
//     }
	
//	public void startGame() { //기존내용
//		// 단어 한 개 선택
//		String newWord = textSource.get();
//		text.setText(newWord);
//		//text.setBackground(Color.GREEN);
//		text.setOpaque(true);
//	}
	
	public void stopGame(GamePanel gamePanel) {
		

		 //WordsThread newWord = null;
		// FallingThread falling = null;
		
		if(falling == null || newWord == null)
			return; // 스레드가 없음 
		newWord.interrupt(); // 스레드 강제 종료
		falling.interrupt();
		//thread = null;
		//gameOn = false;
	}
	
	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null);
//			text.setSize(100, 30);
//			text.setLocation(100,  10);
//			add(text);
		}
	}
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setLayout(new FlowLayout());
			this.setBackground(new Color(119,255,112));
			//this.setBackground(new Color(173,225,166));
			add(input);
		}
		
	}
	
	public void paintComponent(Graphics g) {    //=========================그래픽
		super.paintComponent(g); 
		//이미지를 패널에 꽉 채움, 패널크기가 바뀔때마다 맞춰짐
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);
		
		for(int i=0; i < storeWords.size(); i++) 
        { 
              WordsManger tmp = (WordsManger)storeWords.get(i); 
              g.setColor(tmp.color); 
              g.setFont(new Font("고딕체", Font.BOLD,15));
              g.drawString(tmp.word, tmp.x,tmp.y);                   
        } 
	    
	}
	
	class WordsThread extends Thread{ //일정 시간마다 단어를 생성하는 스레드
		public void run() {
			
			while(true) {

				fallingWord = words.getRandomWord(); // 랜덤 단어 가져옴
				//JLabel newJLabel = new JLabel(fallingWord);
				
				WordsManger word = new WordsManger(fallingWord); 
				storeWords.add(word); //벡터에 저장
				
				//Container parent = gamePanel;
				
				
				//System.out.println(storeWords.get(0));
				
				//wordsLabel.add(newJLabel);
				
				
				//System.out.println(wordsLabel);
				//System.out.println(newJLabel.getX());
				try {
					sleep(800); //1초에 한번씩 단어 생성하게됨
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					return;
					
				}
			}
			
			
			
			
			
		}
	}

	//==============================================================================
	
	class FallingThread extends Thread{ //생성된 단어를 움직이게하는 스레드
		public void run() {
			
			while(true) {
				try {
					sleep(100); //떨어지는 속도 조절
				} catch (InterruptedException e) {
					return;
				}
				
				
				for(int i=0;i<storeWords.size();i++) {
					
					 WordsManger temp = (WordsManger)storeWords.get(i); 

	                 temp.y += 10; 
					
					if(temp.y >= 658)  { //바닥에 부딪힐때
						storeWords.remove(i); //벡터에서 삭제
						scorePanel.minusHeart(gamePanel, startFrame); // 하트 감소
						int heart = scorePanel.lifeTime;
						//System.out.println(heart);
					}
					
					
				}
				repaint();
			}
			
			
		}
	}
	
	//=============================================
	
	private void setWords(JLabel newJLabel) {
		
		
		
	}
	
//	class FallingThread extends Thread {   ////스레드
//		private GamePanel panel;
//		private JLabel label; //게임 숫자를 출력하는 레이블
//		private long delay = 200; // 지연 시간의 초깃값 = 200
//		private boolean falling = false; // 떨이지고 있는지. 초깃값 = false
//		
//		public FallingThread(GamePanel panel, JLabel label) {
//			this.panel = panel;
//			this.label = label;
//		}
//		
//		public boolean isFalling() {
//			return falling; 
//		}	
//		
//		@Override
//		public void run() {
//			falling = true;
//			while(true) {
//				try {
//					sleep(delay);
//					int y = label.getY() + 5; //5픽셀 씩 아래로 이동
//					
//					//System.out.println(panel.getWidth());
//					
//					if(y >= panel.getHeight()-label.getHeight()-input.getHeight()) { //바닥에 닿으면
//						falling = false;
//						label.setText("");
//						panel.printResult("시간초과실패");
//						panel.stopSelfAndNewGame();
//						break; // 스레드 종료
//					}
//					
//					label.setLocation(label.getX(), y);
//					GamePanel.this.repaint();
//				} catch (InterruptedException e) {
//					falling = false;
//					return; // 스레드 종료
//				}
//			}
//		}	
//	}
//	
	public void printResult(String text) {
		resLabel.setText(text);
	}
	
//	public void stopSelfAndNewGame() { // 스레드가 바닥에 닿아서 실패할 때 호출
//		gameStart();			
//	}
	
	// words.txt 파일을 읽고 벡터에 저장하고 벡터로부터 랜덤하게 단어를 추출하는 클래스
		class Words {
			private Vector<String> wordVector = new Vector<String>();

			public Words(String fileName) {
				try {
					Scanner scanner = new Scanner(new FileReader(fileName));
					while(scanner.hasNext()) { // 파일 끝까지 읽음
						String word = scanner.nextLine(); // 한 라인을 읽고 '\n'을 버린 나머지 문자열만 리턴
						wordVector.add(word); // 문자열을 벡터에 저장
						
					}
					scanner.close();
				}
				catch(FileNotFoundException e) {
					System.out.println("file not found error");
					System.exit(0);
				}
			}
			
			public String getRandomWord() {
				final int WORDMAX = wordVector.size(); // 총 단어의 개수
				int index = (int)(Math.random()*WORDMAX);
				selectText = wordVector.get(index); //선택된 단어를 저장
				return wordVector.get(index);
				
			}	
		}
	
	

}