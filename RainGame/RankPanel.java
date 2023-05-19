//��ŷ �г�
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
	
	ImageIcon icon = new ImageIcon("ground.jpg"); //��� �̹���
	Image img = icon.getImage(); 
	
	private int money; //����ڰ� ������ ���� �ݾ�
	private int score; //����
	
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
		//�̹����� �гο� �� ä��, �г�ũ�Ⱑ �ٲ𶧸��� ������
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), null);			
	    
	}
}
