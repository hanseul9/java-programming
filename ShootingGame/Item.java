package sg_1;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Item {
	int x,y;
	// 아이템 관련 이미지 불러오기, 크기조정===============================================
	private Image item_triple = new ImageIcon("src/images/tripleAttack.png").getImage();
	Image tripleAttack = item_triple.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

	private Image item_speed = new ImageIcon("src/images/speedUp.png").getImage();
	Image speedUp = item_speed.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	
	private Image item_hpUp = new ImageIcon("src/images/hp.png").getImage();
	Image hpUp = item_hpUp.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	//================================================================================
	
	Item(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move () {
		this.x-=3;
	}
	

}
