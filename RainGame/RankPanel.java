//랭킹 패널
package miniProject;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RankPanel extends JPanel {
	
	ImageIcon icon = new ImageIcon("ground.jpg"); //배경 이미지
	Image img = icon.getImage(); 
	
	private int money; //사용자가 선택한 옷의 금액
	private int score; //점수
	
	private JTextField edit = new JTextField(20);
	private JButton addButton = new JButton("add");
	private JButton saveButton = new JButton("save");
	
	public RankPanel() {
		//this.setBackground(Color.YELLOW);
		this.setLayout(new FlowLayout());
		add(edit);
		add(addButton);
		add(saveButton);
	}
	
	public void giveMoney(int money) {
		this.money=money;
	}
	
	public void increase(int won) {
		score += won;
	}
	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		//이미지를 패널에 꽉 채움, 패널크기가 바뀔때마다 맞춰짐
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);			
	    
	}
}
