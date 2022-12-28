//���� ������
package miniProject;

import java.awt.Container;
//import java.awt.C
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

//import GameFrame.StartAction;

//import GameFrame.StartAction;





public class GameFrame extends JFrame {
	
	public static int money=0;
	
	
	private MainPanel mainPanel = new MainPanel();
	private SelectPanel selectPanel = null;
	//private ManualPanel manualPanel = new ManualPanel();
	
	//private GamePanel gamePanel = new GamePanel(scorePanel, editPanel);
	//private HeartPanel editPanel = new EditPanel();
	//private ScorePanel scorePanel = new ScorePanel();
	
	
	
	public GameFrame() {
		setTitle("�Ҹ��� Ÿ�ڰ���");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(650,650); //������ ũ��
		setLocation(680,300); //������ ��ġ
		//splitPane(); //�г� ����
		///Container container = new Container();
		//container.add(mainPanel);
		
		setContentPane(new MainPanel());
		
		//new StartFrame();
		
		//new MainPanel();

		setResizable(false);
		setVisible(true);
		
		
	}
	
	  
	public GameFrame(int a) {
		setVisible(true);
	}
	
	//private void splitPane() {
		//JSplitPane mainPane = new JSplitPane();
		//getContentPane().add(mainPane, BorderLayout.CENTER);
	//}

		//new MainFrame(); //����ȭ�� ����
	
	//	public void showMain() {
	//		setContentPane(new MainPanel());
	//	}
	//	public void showManual() {
	//		setContentPane(new ManualPanel());
	//	}
//---------------------------------------------------------------------------------	
//����ȭ�� �г�	
		public class MainPanel extends JPanel {
			
			private JLabel gameStart = new JLabel("���� ����");
			private JLabel manual = new JLabel("���� ����");
			
			Font mainFont = new Font("���� ���", Font.BOLD,20);
			
			
			ImageIcon icon = new ImageIcon("background.jpg");
			Image img = icon.getImage(); 
			
			public MainPanel() {
				
				makeMenu();
				setLayout(null);
				
				//System.out.println("11");
			}
			
			//@Override
			public void paintComponent(Graphics g) { //������ ȣ���ϴ� �Լ��� �ƴ�. -> call back �Լ�(�������� �ҷ��ִ� �Լ�)
				//Graphic Ŭ�������� �׸��� ���� ���� �������� ����
					
				//super.paintComponent(g); //������ ����� �����. �������� ĥ�Ѵ�.
					
				//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
				g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);
				
				    
			}
			private void makeMenu() {
				//��Ʈ��Ÿ��, ������, ��ġ ����
				gameStart.setFont(mainFont);
				gameStart.setLocation(280,250);
				gameStart.setSize(100,50);
				gameStart.addMouseListener(new myMouse()); //�̺�Ʈ������ ���
				
				manual.setFont(mainFont);
				manual.setLocation(280,320);
				manual.setSize(100,50);
				manual.addMouseListener(new myMouse());  //�̺�Ʈ������ ��� 
				
				add(gameStart);
				add(manual);
				
				
				
			}
			
			
			class myMouse extends MouseAdapter{
				
				
				public void mouseEntered(MouseEvent e) { //������Ʈ�� ���콺�� �ö�ö�
					
					JLabel label = (JLabel)(e.getSource()); //�ٿ�ĳ����, �̺�Ʈ �ҽ���ü ����
					String text = label.getText(); //getText�� ���� ��ư ǥ�鿡 �ִ� ���ڿ��� �˾Ƴ� �� ����
					
					if(text.equals("���� ����")) {
						gameStart.setLocation(gameStart.getX()+5,gameStart.getY()-5);
					}
					else if(text.equals("���� ����")) {
						manual.setLocation(manual.getX()+5,manual.getY()-5);
					}
								
				}
				public void mouseExited(MouseEvent e) { //��������
					
					JLabel label = (JLabel)(e.getSource()); //�ٿ�ĳ����, �̺�Ʈ �ҽ���ü ����
					String text = label.getText(); //getText�� ���� ��ư ǥ�鿡 �ִ� ���ڿ��� �˾Ƴ� �� ����
					
					if(text.equals("���� ����")) {
						gameStart.setLocation(gameStart.getX()-5,gameStart.getY()+5);
					}
					else if(text.equals("���� ����")) {
						manual.setLocation(manual.getX()-5,manual.getY()+5);
					}
				}
				public void mousePressed(MouseEvent e) { //Ŭ����
					
					JLabel label = (JLabel)(e.getSource()); //�ٿ�ĳ����, �̺�Ʈ �ҽ���ü ����
					String text = label.getText(); //getText�� ���� ��ư ǥ�鿡 �ִ� ���ڿ��� �˾Ƴ� �� ����
					
					if(text.equals("���� ����")) {
						setVisible(false);
						setContentPane(new SelectPanel());
					}
					else if(text.equals("���� ����")) {
						setVisible(false);
						setContentPane(new ManualPanel());
					}
					
					
				}
			}
			
		}
		
		
//---------------------------------------------------------------------------------
//���� ���� �г�
		public class ManualPanel extends JPanel {
			
			private JLabel explain = new JLabel("<html>afdsafd<br>abasdf </html>");
			Font mainFont = new Font("���� ���", Font.BOLD,20);
			
			ImageIcon icon = new ImageIcon("background.jpg");
			Image img = icon.getImage(); 
			
			
			public ManualPanel(){
				System.out.println("dd");
				setLayout(null);
				explain.setFont(mainFont);
				explain.setLocation(30,30);
				explain.setSize(500,500);
				add(explain);
				explain.addMouseListener(new ExplainMouse());
			}
			
			class ExplainMouse extends MouseAdapter{
				public void mousePressed(MouseEvent e) { //Ŭ����
					//setContentPane(new ManualPanel());
					//removeAll();
					//repaint();
					setVisible(false);
					setContentPane(new MainPanel());
				}
			}
			
			public void paintComponent(Graphics g) { //������ ȣ���ϴ� �Լ��� �ƴ�. -> call back �Լ�(�������� �ҷ��ִ� �Լ�)
				//Graphic Ŭ�������� �׸��� ���� ���� �������� ����
				
				//this.getParent().repaint();
				super.paintComponent(g); //������ ����� �����. �������� ĥ�Ѵ�.
					
				//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
				g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);
				
				    
			}
		}
//---------------------------------------------------------------------------------
//�ʰ��� �г�
		public class SelectPanel extends JPanel{ //�ʰ��� �г�
			//int i=1;
			
			ImageIcon icon = new ImageIcon("ground.jpg");
			Image img = icon.getImage(); 
			
			//�� �̹���
			ImageIcon clothes1 = new ImageIcon("1clothes.png");
			//JLabel clothes1Label = new JLabel(clothes1);
			
			ImageIcon clothes2 = new ImageIcon("2clothes.png");
			//JLabel clothes2Label = new JLabel(clothes2);
			
			ImageIcon clothes3 = new ImageIcon("3clothes.png");
			//JLabel clothes3Label = new JLabel(clothes3);
			
			ImageIcon clothes4 = new ImageIcon("4clothes.png");
			//JLabel clothes4Label = new JLabel(clothes4);
			
			ImageIcon centerImg = new ImageIcon("centerImg.png");
			JLabel centerLabel = new JLabel(centerImg);
			
			JLabel [] clothesImg = new JLabel[5];
			JLabel [] clothesClone = new JLabel[4];
			
			
			private JLabel manual = new JLabel("���� ����");
			Font textFont = new Font("���� ���", Font.BOLD,15);
			JLabel [] text = new JLabel[5];
			
			//JLabel centerImg = null;
			
			//ImageIcon icon = new ImageIcon("ground.jpg");
			//JLabel img = new JLabel(clothes2);
			
			
			public SelectPanel(){
				Container container = getContentPane();
				//JFrame parent = this.getParent();
				//parent.setLayout(BorderLayout());
				setLayout(null);
			
				
				makeImg();	
				makeText();

				setFocusable(true);
				
			}
			
			private void makeText() {
				text[0] = new JLabel(" 1500\\ ");
				text[1] = new JLabel(" 2000\\ ");
				text[2] = new JLabel(" 2500\\ ");
				text[3] = new JLabel(" 3000\\ ");
				text[4] = new JLabel("���� ����");
			
				text[0].setLocation(80,100);
				text[1].setLocation(500,100);
				text[2].setLocation(70,475);
				text[3].setLocation(500,475);
				text[4].setLocation(275,300);
				//text[0]="asdf"; text.length
				for(int i=0;i<text.length;i++) {
					text[i].setFont(textFont);
					text[i].setSize(200,200);
					add(text[i]); 
				}
				text[4].setVisible(false);
				text[4].addMouseListener(new StartMouse());
			}
			private void makeImg() {
				
				//����ڰ� ������ 4���� �̹���
				clothesImg[0] = new JLabel(clothes1);
				clothesImg[1] = new JLabel(clothes2);
				clothesImg[2] = new JLabel(clothes3);
				clothesImg[3] = new JLabel(clothes4);
				//����� ������ �̹���
				clothesClone[0] = new JLabel(clothes1);
				clothesClone[1] = new JLabel(clothes2);
				clothesClone[2] = new JLabel(clothes3);
				clothesClone[3] = new JLabel(clothes4);
				
				clothesImg[0].setLocation(10,10);

				clothesImg[1].setLocation(400,10);
				//clothesImg[1].setSize(200,200);
				
				clothesImg[2].setLocation(10,370);
				//clothesImg[2].setSize(240,200);
				
				clothesImg[3].setLocation(400,370);
				//clothesImg[3].setSize(240,200);
				
				centerLabel.setSize(200,200);
				centerLabel.setLocation(220,190);
				
				add(centerLabel);
				
				//add(clothesImg[0]);
				
				for(int i=0;i<clothesImg.length-1;i++) {
					//4���� �� ������ ����, �̺�Ʈ ������ ���, ������Ʈ ����
					clothesImg[i].setSize(200,200);
					clothesImg[i].addMouseListener(new MyMouse());
					add(clothesImg[i]);
					//����� ����ڰ� �����ϸ� ������ �̹��� �߰�
					//clothesClone[i]=clothesImg[i];
					clothesClone[i].setSize(200,200);
					clothesClone[i].setLocation(220,190);
					add(clothesClone[i]);
					clothesClone[i].setVisible(false);
				}

			}
			
			class MyMouse extends MouseAdapter{
				public void mousePressed(MouseEvent e) { //Ŭ����
					//JLabel centerImg = new JLabel(clothes1);
					//centerLabel = clothes1Label;
					//add(centerLabel);
					JLabel test = (JLabel)(e.getSource()); 
					Icon name = test.getIcon();
					//
					System.out.println(money);
					text[4].setVisible(true);
					
					if(name==clothesImg[0].getIcon()) {
						money = 1500;
						for(int i=0;i<clothesClone.length;i++) {
							if(i==0)
								clothesClone[i].setVisible(true);
							else
								clothesClone[i].setVisible(false);
						}
						
					}
					else if(name==clothesImg[1].getIcon()) {
						money = 2000;
						for(int i=0;i<clothesClone.length;i++) {
							if(i==1)
								clothesClone[i].setVisible(true);
							else
								clothesClone[i].setVisible(false);
						}
					}
					else if(name==clothesImg[2].getIcon()) {
						money = 2500;
						for(int i=0;i<clothesClone.length;i++) {
							if(i==2)
								clothesClone[i].setVisible(true);
							else
								clothesClone[i].setVisible(false);
						}
					}
					else if(name==clothesImg[3].getIcon()) {
						money = 3000;
						for(int i=0;i<clothesClone.length;i++) {
							if(i==3)
								clothesClone[i].setVisible(true);
							else
								clothesClone[i].setVisible(false);
						}
					}
						
					//money=1000;
					
					centerLabel.setVisible(false);
					//clone1Label.setVisible(true);
					//repaint();
					//add(centerImg);
				}
			}
			
			class StartMouse extends MouseAdapter{ //���� ������ ������ 
				public void mousePressed(MouseEvent e) { //Ŭ����
					setVisible(false);
					setContentPane(new MainPanel());
					StartFrame startFrame = new StartFrame(money);
					startFrame.giveStartFrame(startFrame);
				}

				
			}
			
			public void paintComponent(Graphics g) { 
				super.paintComponent(g); 
				//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
				g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);
			    
			}
		}
		
		
	}

	


