package sg_1;

public class EnemyAttack {
	int x, y;
	int width = 70; //25..?
	int height =40;
	int attack = 3;
	

	//������
	public EnemyAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//���� �Ѿ� �߻�
	public void fire() {
		this.x -= 10;
	}
	


}
