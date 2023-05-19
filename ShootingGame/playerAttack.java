package sg_1;

public class playerAttack {
	int x,y;
	int width = 50;
	int height = 25;
	int attack = 1;
	//int hp=3;


	//생성자
	public playerAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//총알 이동(발사)
	public void fire() {
		this.x += 15;
	}
	
	public void fireUp() {
		this.x +=50;
	}
	
	//폭탄 이동(발사)
	public void bomb() {
		this.x += 20;
	}


	
	

}
