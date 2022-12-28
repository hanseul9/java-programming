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
	
	GamePanel gamePanel=null; //�ٸ� Ŭ�������� �г� ����
	StartFrame startFrame = null;
	
	private JTextField input = new JTextField(20); //�Ʒ� �Է�ĭ
	//private JLabel text = new JLabel("Ÿ�����غ�����"); //�������� �ؽ�Ʈ
	private String selectText=null;
	
	private ScorePanel scorePanel = null;
	private RankPanel rankPanel = null;
	private TextSource textSource = new TextSource(); // �ܾ� ���� ����
	
	//falling
	private JLabel label = new JLabel(); // �������� �ܾ� 
	private JLabel resLabel = new JLabel("����/����"); // ���� ���и� ��Ÿ���� ���̺�
	private Words words = null;
	private String fallingWord = null;
	private FallingThread thread = null; 
	private boolean gameOn = false;
	
	private int count = 0;
	
	int money = 0 ; //���� �ݾ�
	
	//private JLabel label [] = new JLabel [10]; //�������� �ܾ�
	
	// ���� �߰� ����. 10���� ������ ���۷��� �迭
	//FallingThread th [] = new FallingThread [10];
	//private JLabel test [] = new JLabel [10]; //�������� �ܾ�
	
	//�׽�Ʈ��
	 WordsThread newWord = null;
	 FallingThread falling = null;
	 
	 private JLabel test [] = new JLabel [10]; //�������� �ܾ�
	 //private Vector<JLabel> storeWords = new Vector<JLabel>(); //������ �ܾ���� ����
	 private Vector<Thread> storeThread = new Vector<Thread>(); //������ ����
	 
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
		//setLayout(null); // ��ġ�����ڸ� ������� �ʰ� ���� ��ġ�� ������Ʈ�� ��ġ�Ѵ�.	
//		input.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JTextField t = (JTextField)(e.getSource());
//				String inWord = t.getText();
//				if(text.getText().equals(inWord)) { // ���߱� ����
//					// ���� �ø���
//					scorePanel.increase();
//					startGame();
//					
//					//input â �����
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
		
		
		
		resLabel.setLocation(0,0); //���� ���и� ��Ÿ���� ����
		resLabel.setSize(100, 30);
		add(resLabel);
		
		Container parent = resLabel.getParent();
		System.out.println(parent);
		
//		input.setLocation(500,500);
//		input.setLocation(100, 30);
//		add(input); //�Է�â
		//gameStart();
		
		
		
		
		setFocusable(true);
		requestFocus();
		
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //Ÿ���� ���ͽ�
				
				
				JTextField t = (JTextField)(e.getSource()); // ����ڰ� �Է��� �ؽ�Ʈ����
				System.out.println(t);
				String inWord = t.getText();// ����ڰ� �Է��� �ؽ�Ʈ
				//System.out.println(inWord); 
				//System.out.println(selectText); //���õ� �ܾ�
				
				
				
				int i = 0;
				
				for(i=0;i<storeWords.size();i++) {
					WordsManger temp = (WordsManger)storeWords.get(i); 
					if(temp.word.equals(inWord))
						break;
				}
				
				System.out.println(i);
				System.out.println(storeWords.size());
				
				if(i==storeWords.size()) { // ���߱� ����
					t.setText("");
				}
				else { // ���߱� ����
					storeWords.remove(i);
					scorePanel.increase(10); //���� ����
					rankPanel.increase(10);
					//startGame();
					
					//input â �����
					t.setText("");
					
					printResult("����");
				}
				
//				if(selectText.equals(inWord)) { // ���߱� ����
//					// ���� �ø���
//					scorePanel.increase();
//					//startGame();
//					
//					//input â �����
//					t.setText("");
//					
//					printResult("����");
//					//Game(); // ���� ����
//					//stopGame();
//					//gameStart(); // �� �ܾ� ����
//				}
//				else {
//					//input â �����
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
		
		//gamePanel.setLayout(null);//��ġ������ ����
		
		
		newWord = new WordsThread(); // �ܾ� ���� ������ ����
		newWord.start(); 
		

        falling = new FallingThread(); 
        falling.start(); 
        
		
//		
//		for(int i=0;i<test.length;i++) { //�׽�Ʈ
//			test[i]= new JLabel("11");
//			test[i].setSize(200, 30); // ���̺� ũ��
//			test[i].setLocation(i*10,i*10);
//			
//			th[i] = new FallingThread(this, test[i]); // ���� ������
//			th[i].start(); // ������ ����
//			
//			add(test[i]);
//		}
//		
//		fallingWord = words.getRandomWord(); //�������� �ܾ�
//		
//		
//		System.out.println(fallingWord);
//		
//		int x = (int)(Math.random()*550); //�������� ������ ��ġ
//		
//		label.setText(fallingWord);
//		label.setSize(200, 30); // ���̺� ũ��
//		label.setLocation(x,10); // ���̺� ��ġ
//		label.setForeground(Color.MAGENTA); //���̺��� ���� ���� �����Ѵ�.				
//		label.setFont(new Font("Tahoma", Font.ITALIC, 15)); // ���̺� ������ ��Ʈ�� �����Ѵ�.	
//		
//
//		thread = new FallingThread(this, label); // ���� ������
//		thread.start(); // ������ ����
//		gameOn = true;
	}
	
//	 public void gameStart() {
//    	 newWord = new WordsThread(); // �ܾ� ���� ������ ����
// 		newWord.start(); 
//
//         falling = new FallingThread(); 
//         falling.start(); 
//     }
	
//	public void startGame() { //��������
//		// �ܾ� �� �� ����
//		String newWord = textSource.get();
//		text.setText(newWord);
//		//text.setBackground(Color.GREEN);
//		text.setOpaque(true);
//	}
	
	public void stopGame(GamePanel gamePanel) {
		

		 //WordsThread newWord = null;
		// FallingThread falling = null;
		
		if(falling == null || newWord == null)
			return; // �����尡 ���� 
		newWord.interrupt(); // ������ ���� ����
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
	
	public void paintComponent(Graphics g) {    //=========================�׷���
		super.paintComponent(g); 
		//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);
		
		for(int i=0; i < storeWords.size(); i++) 
        { 
              WordsManger tmp = (WordsManger)storeWords.get(i); 
              g.setColor(tmp.color); 
              g.setFont(new Font("���ü", Font.BOLD,15));
              g.drawString(tmp.word, tmp.x,tmp.y);                   
        } 
	    
	}
	
	class WordsThread extends Thread{ //���� �ð����� �ܾ �����ϴ� ������
		public void run() {
			
			while(true) {

				fallingWord = words.getRandomWord(); // ���� �ܾ� ������
				//JLabel newJLabel = new JLabel(fallingWord);
				
				WordsManger word = new WordsManger(fallingWord); 
				storeWords.add(word); //���Ϳ� ����
				
				//Container parent = gamePanel;
				
				
				//System.out.println(storeWords.get(0));
				
				//wordsLabel.add(newJLabel);
				
				
				//System.out.println(wordsLabel);
				//System.out.println(newJLabel.getX());
				try {
					sleep(800); //1�ʿ� �ѹ��� �ܾ� �����ϰԵ�
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					return;
					
				}
			}
			
			
			
			
			
		}
	}

	//==============================================================================
	
	class FallingThread extends Thread{ //������ �ܾ �����̰��ϴ� ������
		public void run() {
			
			while(true) {
				try {
					sleep(100); //�������� �ӵ� ����
				} catch (InterruptedException e) {
					return;
				}
				
				
				for(int i=0;i<storeWords.size();i++) {
					
					 WordsManger temp = (WordsManger)storeWords.get(i); 

	                 temp.y += 10; 
					
					if(temp.y >= 658)  { //�ٴڿ� �ε�����
						storeWords.remove(i); //���Ϳ��� ����
						scorePanel.minusHeart(gamePanel, startFrame); // ��Ʈ ����
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
	
//	class FallingThread extends Thread {   ////������
//		private GamePanel panel;
//		private JLabel label; //���� ���ڸ� ����ϴ� ���̺�
//		private long delay = 200; // ���� �ð��� �ʱ갪 = 200
//		private boolean falling = false; // �������� �ִ���. �ʱ갪 = false
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
//					int y = label.getY() + 5; //5�ȼ� �� �Ʒ��� �̵�
//					
//					//System.out.println(panel.getWidth());
//					
//					if(y >= panel.getHeight()-label.getHeight()-input.getHeight()) { //�ٴڿ� ������
//						falling = false;
//						label.setText("");
//						panel.printResult("�ð��ʰ�����");
//						panel.stopSelfAndNewGame();
//						break; // ������ ����
//					}
//					
//					label.setLocation(label.getX(), y);
//					GamePanel.this.repaint();
//				} catch (InterruptedException e) {
//					falling = false;
//					return; // ������ ����
//				}
//			}
//		}	
//	}
//	
	public void printResult(String text) {
		resLabel.setText(text);
	}
	
//	public void stopSelfAndNewGame() { // �����尡 �ٴڿ� ��Ƽ� ������ �� ȣ��
//		gameStart();			
//	}
	
	// words.txt ������ �а� ���Ϳ� �����ϰ� ���ͷκ��� �����ϰ� �ܾ �����ϴ� Ŭ����
		class Words {
			private Vector<String> wordVector = new Vector<String>();

			public Words(String fileName) {
				try {
					Scanner scanner = new Scanner(new FileReader(fileName));
					while(scanner.hasNext()) { // ���� ������ ����
						String word = scanner.nextLine(); // �� ������ �а� '\n'�� ���� ������ ���ڿ��� ����
						wordVector.add(word); // ���ڿ��� ���Ϳ� ����
						
					}
					scanner.close();
				}
				catch(FileNotFoundException e) {
					System.out.println("file not found error");
					System.exit(0);
				}
			}
			
			public String getRandomWord() {
				final int WORDMAX = wordVector.size(); // �� �ܾ��� ����
				int index = (int)(Math.random()*WORDMAX);
				selectText = wordVector.get(index); //���õ� �ܾ ����
				return wordVector.get(index);
				
			}	
		}
	
	

}