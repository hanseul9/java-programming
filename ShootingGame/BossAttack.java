package sg_1;

public class BossAttack {
	int x, y;
	int width = 40; // 25..?
	int height = 20;
	int attack = 3;
	int pattern;
	int rand;

	// ������
	public BossAttack(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void fire(int pattern, int count, int bsT ) { //int pattern
		count%=bsT;
		
		if(pattern == 0) {	 //ù��° ���� - ������(��,�߰�,�Ʒ�)���� �� �����°�
			this.x -= 5;
			if(count == 0) 
				this.y+=2;
			//else if(select==1)  ->����
			else if(count==2)
				this.y-=2;
		}
		
		if(pattern == 1) {	 //�ι�° ���� - �� ���鼭 ������..!
			
			
			if(count==1) {
				if(Game.cntB %4 ==0 ) {
					this.x -= 40;
					this.y-=20;
				}
				else if(Game.cntB %4 ==1 ) {
					this.x -= 20;
					this.y+=20;
				}
				else if(Game.cntB %4 ==2 ) {
					this.x += 20;
					this.y+=20;
				}
				else {
					this.x += 20;
					this.y-=20;
				}
			}
			else if(count==0) {
				if(Game.cntB %4 ==0 ) {
					this.x -= 20;
					this.y+=20;
				}
				else if(Game.cntB %4 ==1 ) {
					this.x += 20;
					this.y+=20;
				}
				else if(Game.cntB %4 ==2 ) {
					this.x += 20;
					this.y-=20;
				}
				else {
					this.x -= 40;
					this.y-=20;
				}
			}
			
			else {
				if(Game.cntB %4 ==0 ) {
					this.x += 20;
					this.y-=20;
				}
				else if(Game.cntB %4 ==1 ) {
					this.x -= 40;
					this.y-=20;
				}
				else if(Game.cntB %4 ==2 ) {
					this.x -= 20;
					this.y+=20;
				}
				else {
					this.x += 20;
					this.y+=20;
				}
			}
				
			
		}
		
		if(pattern ==2) { //����° �д� - �ΰ��� ��, �Ʒ��� ���׸Ӵ� �����ϸ鼭..
			this.x -= 10;
			if(Game.cntB % 10==0) {
				if(count ==0 || count ==1) {
					this.y -=20;
				}
				else if(count ==2 || count==3) {
					this.y+=20;
				}
			}
		}
		
		if(pattern ==3 ) { //�׹�° ���� - �׳� �̳� ��°�..
			this.x-=5;
		}
		if(pattern == 4) {	 //ù��° ���� - ������(��,�߰�,�Ʒ�)���� �� �����°�
			this.x -= 5;
			
			if(count < 3) 
				this.y+=4;
			else if(count > 5)
				this.y-=4;
		}
		
		if(pattern == 5) {  //�������
			this.x -= 3;
			if (rand == 0) { 
				if (this.y - 80  < 0)  
					rand=1;
				else
					this.y -= 10;

			} 
			else if (rand == 1) {
				if (this.y + 80  > Main.SCREEN_HEIGHT)
					rand =0;
				else
					this.y += 10;
			}

		}
		
		
		
	}
	
	/*
	// ���� �Ѿ� �߻�
	public void fire_0(int count, int bsT) { // ù��° ���� ����
		count %= bsT;

		this.x -= 5;
		if (count == 0)
			this.y += 2;
		else if (count == 2)
			this.y -= 2;
	}

	public void fire_1(int count, int bsT) { // �ι�° ���� ����
		if(count%4 ==0 ) {
			this.x -= 10;
			this.y-=5;
		}
		else if(count%4 ==1) {
			this.x -= 10;
			this.y+=5;
		}
		else if(count%4 ==2) {
			this.x += 10;
			this.y+=5;
		}
		else
			this.x += 10;
			this.y-=5;
		
		
		
		
	}

	public void fire_2(int count, int bsT) { // ����° ���� ����
		count %= bsT;

		this.x -= 10;
		this.y -= 5;
	}

	public void fire_3(int count, int bsT) { // �׹�° ���� ����
		count %= bsT;

		this.x -= 10;
		this.y -= 5;
	}
	*/
}
