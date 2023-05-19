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
	
//=================================보스 이미지 불러오기========================================
	private Image Boss_img = new ImageIcon("src/images/boss.png").getImage();
	Image Boss =  Boss_img.getScaledInstance(260, 260, Image.SCALE_SMOOTH);

// =======================================================================================
	
	//생성자
	 Boss(int x, int y) {
		this.x = x;
		this.y = y;
		this.rand = (int) (Math.random() * (1 - 0 + 1) + 0); //객체 생성 시 무작위로 0,1 중 하나 고르도록 설정(0이면 적2가 위로, 1이면 적2가 아래로 움직임)
	}
	
	//보스 움직임
	public void move() {
		//this.x -= 1; //3의 속도로 전진하면서,,
		//rand 가 0일 경우 기본 세팅 : 5의 속도, 위로 움직임
		if (rand == 0) { 
			//그러나 위로 더이상 갈 수 없으면, rand를 1로 바꿔서 방향 전환
			if (this.y - 1  < 0)  
				rand=1;
			else
				this.y -= 1;

		} 
		//rand가 1일 경우 기본 세팅 : 5의 속도, 아래로 움직임
		else if (rand == 1) {
			//그러나 아래로 더이상 갈 수 없으면, rand를 0으로 바꿔서 방향 전환
			if (this.y + 250  > Main.SCREEN_HEIGHT)
				rand =0;
			else
				this.y += 1;
		}
	}

	
}