package sg_1;

public class BossAttack {
	int x, y;
	int width = 40; // 25..?
	int height = 20;
	int attack = 3;
	int pattern;
	int rand;

	// 생성자
	public BossAttack(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void fire(int pattern, int count, int bsT ) { //int pattern
		count%=bsT;
		
		if(pattern == 0) {	 //첫번째 패턴 - 세방향(위,중간,아래)으로 막 나가는거
			this.x -= 5;
			if(count == 0) 
				this.y+=2;
			//else if(select==1)  ->직진
			else if(count==2)
				this.y-=2;
		}
		
		if(pattern == 1) {	 //두번째 패턴 - 막 돌면서 나가요..!
			
			
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
		
		if(pattern ==2) { //세번째 패던 - 두개씩 위, 아래로 슬그머니 전진하면서..
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
		
		if(pattern ==3 ) { //네번째 패턴 - 그냥 겁나 쏘는거..
			this.x-=5;
		}
		if(pattern == 4) {	 //첫번째 패턴 - 세방향(위,중간,아래)으로 막 나가는거
			this.x -= 5;
			
			if(count < 3) 
				this.y+=4;
			else if(count > 5)
				this.y-=4;
		}
		
		if(pattern == 5) {  //지그재그
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
	// 적의 총알 발사
	public void fire_0(int count, int bsT) { // 첫번째 패턴 공격
		count %= bsT;

		this.x -= 5;
		if (count == 0)
			this.y += 2;
		else if (count == 2)
			this.y -= 2;
	}

	public void fire_1(int count, int bsT) { // 두번째 패턴 공격
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

	public void fire_2(int count, int bsT) { // 세번째 패턴 공격
		count %= bsT;

		this.x -= 10;
		this.y -= 5;
	}

	public void fire_3(int count, int bsT) { // 네번째 패턴 공격
		count %= bsT;

		this.x -= 10;
		this.y -= 5;
	}
	*/
}
