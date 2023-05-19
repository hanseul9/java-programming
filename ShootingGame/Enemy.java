package sg_1;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy {
	int x, y;
	int rand;
	int enemySpeed = 6;
	
	int width = 120;
	int height = 80;
	
	int hp=3;
	
//=================================�� �̹��� �ҷ�����========================================
	private Image Enemy1_img = new ImageIcon("src/images/enemy1.png").getImage();
	Image Enemy1 = Enemy1_img.getScaledInstance(160, 100, Image.SCALE_SMOOTH);

	private Image Enemy2_img = new ImageIcon("src/images/enemy2.png").getImage();
	Image Enemy2 = Enemy2_img.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
// =======================================================================================
	
	//������
	Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.rand = (int) (Math.random() * (1 - 0 + 1) + 0); //��ü ���� �� �������� 0,1 �� �ϳ� ������ ����(0�̸� ��2�� ����, 1�̸� ��2�� �Ʒ��� ������)
	}
	
	//��1�� ������ : 4�� �ӵ��� ����
	public void move1() {
		this.x -= 4;
	}
	//�� 2�� ������
	public void move2() { 
		this.x -= 3; //3�� �ӵ��� �����ϸ鼭,,
		//rand �� 0�� ��� �⺻ ���� : 5�� �ӵ�, ���� ������
		if (rand == 0) { 
			//�׷��� ���� ���̻� �� �� ������, rand�� 1�� �ٲ㼭 ���� ��ȯ
			if (this.y - 80  < 0)  
				rand=1;
			else
				this.y -= 5;

		} 
		//rand�� 1�� ��� �⺻ ���� : 5�� �ӵ�, �Ʒ��� ������
		else if (rand == 1) {
			//�׷��� �Ʒ��� ���̻� �� �� ������, rand�� 0���� �ٲ㼭 ���� ��ȯ
			if (this.y + 80  > Main.SCREEN_HEIGHT)
				rand =0;
			else
				this.y += 5;
		}

	}
	
	
	
}
