package miniProject;


import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	
	GamePanel gamePanel = null;
	StartFrame startFrame = null;
	//�� �̹���
	ImageIcon clothes1 = new ImageIcon("1clothes.png");
	//JLabel clothes1Label = new JLabel(clothes1);
	
	ImageIcon clothes2 = new ImageIcon("2clothes.png");
	//JLabel clothes2Label = new JLabel(clothes2);
	
	ImageIcon clothes3 = new ImageIcon("3clothes.png");
	//JLabel clothes3Label = new JLabel(clothes3);
	
	ImageIcon clothes4 = new ImageIcon("4clothes.png");
	//JLabel clothes4Label = new JLabel(clothes4);
	
	private JLabel[] clothesImg = new JLabel[4]; // �� �̹��� �迭
	
	private int score = 0;
	private JLabel textLabel = new JLabel("����");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));

	static int lifeTime = 5;
	
	int money = GameFrame.money;
	
	ImageIcon icon = new ImageIcon("ground.jpg"); //��� �̹���
	Image img = icon.getImage(); 
	
	ImageIcon gameOverIcon = new ImageIcon("gameOverImg.png"); //��� �̹���
	Image gameOverImg = gameOverIcon.getImage();
	
	ImageIcon heartLabel= new ImageIcon("heart.png"); //��Ʈ �̹���

	//private Vector<JLabel> heart = new Vector<JLabel>();
	private JLabel[] heart = new JLabel[5];
	//Image heart = heartIcon.getImage(); 
	
	
	public ScorePanel() {
		
		//System.out.println(money+"asdf");
		//this.setBackground(Color.YELLOW);
		setLayout(null);
		
		textLabel.setSize(50, 20);
		textLabel.setLocation(10, 10);
		add(textLabel);
		
		scoreLabel.setSize(100, 20);
		scoreLabel.setLocation(70, 10);
		add(scoreLabel);	
		
		//��Ʈ �̹���
		for(int i=0;i<lifeTime;i++) {
//			JLabel temp = new JLabel(heartLabel);//�̹��� ���̺� ����
//			temp.setSize(50,50);
//			temp.setLocation(45*i,50);
//			heart.add(temp); //���Ϳ� ����
//			add(heart.get(i));
			heart[i] = new JLabel(heartLabel);
			heart[i].setSize(50,50);
			heart[i].setLocation(45*i,50);
			add(heart[i]);
		}
		//�� �̹��� 
		clothesImg[0]= new JLabel(clothes1);
		clothesImg[1]= new JLabel(clothes2);
		clothesImg[2]= new JLabel(clothes3);
		clothesImg[3]= new JLabel(clothes4);
		
		for(int i=0;i<clothesImg.length;i++) {
			clothesImg[i].setSize(250,250);
			clothesImg[i].setLocation(-10,110);
		}
		
		if(money==1500) {
			add(clothesImg[0]);
		}
		else if(money==2000) {
			add(clothesImg[1]);
		}
		else if(money == 2500) {
			add(clothesImg[2]);
		}
		else if(money == 3000) {
			add(clothesImg[3]);
		}
		repaint();
	}
	
	public void increase(int won) { // ��������
		score += won;
		scoreLabel.setText(Integer.toString(score));
		
		if(score>=10) { //���� Ŭ����
			
			
			
		}
	}
	public void minusHeart(GamePanel gamePanel, StartFrame startFrame) {
		//Container parent = heart.get(1).getParent();
		lifeTime--;
//		heart.remove(lifeTime);
//		heart.get(0).getParent().repaint();
////		parent.repaint();
//		revalidate();
		if(lifeTime<0) {
			this.gamePanel = gamePanel;
			this.startFrame = startFrame;
			//System.out.println(gamePanel);
			gamePanel.stopGame(gamePanel);
			
			lifeTime = 5; //��Ʈ �ʱ����
			
			class AlarmFrame extends JFrame {
				public AlarmFrame() {
					setTitle("���� ����");
					setSize(800,450); 
					setLocation(600,300);
					this.addWindowListener(new MyWindow()); //���â ����� �̺�Ʈ �߻�
					
					AlarmPanel alarmPanel = new AlarmPanel();
					setContentPane(alarmPanel); //����Ʈ�� ����
				
					setVisible(true);
				}
				class AlarmPanel extends JPanel{
					
					private AlarmPanel() {
						
						
					}
					
					public void paintComponent(Graphics g) { 
						super.paintComponent(g); 
						//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
						g.drawImage(gameOverImg, 0, 0, this.getWidth(),this.getHeight(), null);			
					    
					}
				}
				
			}


			AlarmFrame alarmFrame = new AlarmFrame();
			
		}
		else {
			remove(heart[lifeTime]);
			repaint();
		}
		
		
		
		
	}
	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);			
	    
	}
	
	class MyWindow extends WindowAdapter{
		public void windowClosing(WindowEvent e) 
	    { 
//	          e.getWindow().setVisible(false); 
//	          e.getWindow().dispose(); 
			startFrame.dispose();
	    } 
	}
	
	
}
