package sg_1;

public class playerAttack {
	int x,y;
	int width = 50;
	int height = 25;
	int attack = 1;
	//int hp=3;


	//������
	public playerAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//�Ѿ� �̵�(�߻�)
	public void fire() {
		this.x += 15;
	}
	
	public void fireUp() {
		this.x +=50;
	}
	
	//��ź �̵�(�߻�)
	public void bomb() {
		this.x += 20;
	}


	
	

}
