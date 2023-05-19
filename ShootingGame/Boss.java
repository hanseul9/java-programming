package sg_1;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Boss {
	int x, y;
	int rand;
	int enemySpeed = 6;
	
	int width = 260;
	int height = 260;
	
	int hp=150;
	
//=================================���� �̹��� �ҷ�����========================================
	private Image Boss_img = new ImageIcon("src/images/boss.png").getImage();
	Image Boss =  Boss_img.getScaledInstance(260, 260, Image.SCALE_SMOOTH);

// =======================================================================================
	
	//������
	 Boss(int x, int y) {
		this.x = x;
		this.y = y;
		this.rand = (int) (Math.random() * (1 - 0 + 1) + 0); //��ü ���� �� �������� 0,1 �� �ϳ� ������ ����(0�̸� ��2�� ����, 1�̸� ��2�� �Ʒ��� ������)
	}
	
	//���� ������
	public void move() {
		//this.x -= 1; //3�� �ӵ��� �����ϸ鼭,,
		//rand �� 0�� ��� �⺻ ���� : 5�� �ӵ�, ���� ������
		if (rand == 0) { 
			//�׷��� ���� ���̻� �� �� ������, rand�� 1�� �ٲ㼭 ���� ��ȯ
			if (this.y - 1  < 0)  
				rand=1;
			else
				this.y -= 1;

		} 
		//rand�� 1�� ��� �⺻ ���� : 5�� �ӵ�, �Ʒ��� ������
		else if (rand == 1) {
			//�׷��� �Ʒ��� ���̻� �� �� ������, rand�� 0���� �ٲ㼭 ���� ��ȯ
			if (this.y + 250  > Main.SCREEN_HEIGHT)
				rand =0;
			else
				this.y += 1;
		}
	}

	
}