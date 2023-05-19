package sg_1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game extends Thread {
// 전체적인 게임에 필요한 변수 ( 시간조절 )===================================

	private int delay = 20;
	private long pretime;
	private int cnt; // 시간의 흐름
	public static int cntB;
	private int blastX, blastY; // 폭탄이 터질 좌표
	private int score; // 점수
	private int kill; // 적을 얼마나 죽였는가?

	int pattern = 0; // 첫번째 패턴으로 시작
	int one = 1; // 한번만 작동하게끔 하는 변수
	int selectX = 0; // 총알 위치??들..
	int selectY = 0;
	int bsT = 0; // 보스몹 탄창수
	int count = 0; // 총알구분 위함

//플레이어 관련 이미지 불러오기====================================
	// 게임의 플레이어로 쓸 이미지 불러오기, 크기 조절
	private Image player_img = new ImageIcon("src/images/player.png").getImage();
	Image player = player_img.getScaledInstance(200, 80, Image.SCALE_SMOOTH);

	// 플레이어의 목숨을 표시할 수 있는 이미지 불러오기, 크기 조절
	private Image player_Heart = new ImageIcon("src/images/playerHeart.png").getImage();
	Image playerHeart_img = player_Heart.getScaledInstance(20, 40, Image.SCALE_SMOOTH);

	// 플레이어의 공격무기로 쓰일 총알과 폭탄의 이미지 불러오기, 크기 조절
	Image Bullet_img = new ImageIcon("src/images/bullet.png").getImage();
	Image Bullet = Bullet_img.getScaledInstance(50, 25, Image.SCALE_SMOOTH);

	// 3총알
	Image TripleBullet_img = new ImageIcon("src/images/tripleBullet.png").getImage();
	Image TripleBullet = TripleBullet_img.getScaledInstance(50, 60, Image.SCALE_SMOOTH);

	Image Bomb_img = new ImageIcon("src/images/bomb.png").getImage();
	Image Bomb = Bomb_img.getScaledInstance(70, 30, Image.SCALE_SMOOTH);

	// 적을 만나면 폭탄이 터지는 이미지, 크기 조절
	private Image Blast = new ImageIcon("src/images/BombBlast.png").getImage();
	Image BombBlast = Blast.getScaledInstance(500, 500, Image.SCALE_SMOOTH);

	private Image playerSkillBasic = new ImageIcon("src/images/skill_basic.png").getImage();
	private Image playerSkillUseful = new ImageIcon("src/images/skill_useful.png").getImage();
	private Image playerSkillImg = playerSkillBasic;

//적의 공격수단 이미지 불러오기==============================================
	private Image enemy_bullet_img = new ImageIcon("src/images/enemy_bullet.png").getImage();
	private Image enemyBullet = enemy_bullet_img.getScaledInstance(70, 40, Image.SCALE_SMOOTH);

	private Image Boss_bullet_img = new ImageIcon("src/images/circle.png").getImage(); // "src/images/BossBullet.png"
	private Image BossBullet = Boss_bullet_img.getScaledInstance(40, 20, Image.SCALE_SMOOTH);

//플레이어 관련 변수 선언=============================================
	// 플레이어에 관련한 변수들
	public static int playerX, playerY; // 플레이어의 위치를 담기위한 좌표변수
	private int playerWidth = 200; // 플레이어의 가로폭(크기)
	private int playerHeight = 80; // 플레이어의 세로폭(크기)
	private int playerSpeed = 10; // 플레이어의 이동 속도(10의 빠르기로 설정)
	private int playerHeart = 3;
	private int test = 1000;

	Random rand = new Random();

	private boolean up, down, left, right; // 위,아래,왼쪽,오른쪽 키가 각각 눌렸는지 아닌지 저장할 변수
	private boolean shooting, bomb;
	private boolean isBomb = true;
	private boolean isSpeedUp = false;
	private boolean isTriple = false;
	boolean isOver; // 게임오버 관련
	boolean isClear; // 게임 모두 클리어 했는지(보스몹까지 다 깨면 isClear가 true가 됨)
	private boolean isRestart; // r눌러서 restart했는지, true가 되면 아예 다 초기화하고 처음부터 게임 시작하도록 하기 위함
	private boolean isBoss = false; // 보스스테이지 검사
	private boolean bossStage = false;
	// private boolean bossAt = true;

//플레이어 관련 리스트와 객체(변수) 선언======================================
	ArrayList<playerAttack> playerShootingList = new ArrayList<playerAttack>(); // 플레이어의 공격을 담을 리스트
	private playerAttack playerShootingAttack; // playerAttack형 변수 생성

	ArrayList<playerAttack> playerBombList = new ArrayList<playerAttack>(); // 플레이어의 스킬(폭탄)을 담을 리스트
	private playerAttack playerBombAttack; // playerAttack형 스킬용 변수 생성

	// 적 관련 리스트와 객체(변수) 선언======================================
	ArrayList<Enemy> Enemy1List = new ArrayList<Enemy>(); // 적1(앞뒤로 움직임)의 생성을 담을 리스트
	private Enemy Enemy1; // 적1(앞뒤로 움직임), Enemy형 변수생성

	ArrayList<EnemyAttack> Enemy1AttackList = new ArrayList<EnemyAttack>(); // 적1의 공격을 담을 리스트
	private EnemyAttack Enemy1Attack; // 적1의 공격, EnemyAttack형 변수생성

	ArrayList<Enemy> Enemy2List = new ArrayList<Enemy>(); // 적2(좌우로 움직이면서 전진)의 생성을 담을 리스트
	private Enemy Enemy2; // 적2(좌우로 움직이면서 전진), Enemy형 변수생성

	ArrayList<EnemyAttack> Enemy2AttackList = new ArrayList<EnemyAttack>(); // 적2의 공격을 담을 리스트
	private EnemyAttack Enemy2Attack; // 적2의 공격, EnemyAttack형 변수생성

	// =====보스몹~~~~===============================================
	ArrayList<Boss> BossList = new ArrayList<Boss>(); // 적2(좌우로 움직이면서 전진)의 생성을 담을 리스트
	private Boss Boss; // 적2(좌우로 움직이면서 전진), Enemy형 변수생성

	ArrayList<BossAttack> BossAttackList = new ArrayList<BossAttack>(); // 적2의 공격을 담을 리스트
	private BossAttack BossAttack; // 적2의 공격, EnemyAttack형 변수생성

	// 적 때리면 나는 소리
	private Audio hitSound = new Audio("src/music/hitSound.mp3", false);
	// 피격음 관련
	// 폭탄 터질때 나는 소리
	private Audio bombSound = new Audio("src/music/bombSound.wav", false);

//아이템 관련 리스트와 객체 선언===============================================
	int now;
	int nowB;
	ArrayList<Item> tripleBulletList = new ArrayList<Item>(); // 총알 세개 동시 발사 스킬을 담을 리스트
	private Item tripleBullet; // 총알 세개 동시 발사 스킬 구현을 위한 객체

	ArrayList<Item> speedUpList = new ArrayList<Item>(); // 속도 증가 스킬을 담을 리스트
	private Item speedUp; // 속도 증가 스킬 구현을 위한 객체

	ArrayList<Item> hpUpList = new ArrayList<Item>(); // 생명 증가
	private Item hpUp;

	Music introMusic = new Music("backgroundmusic.mp3", true);

//게임 시작 ====================================================
	@Override
	public void run() {

		reset(); // 초기화 함수

		// bossStage=true;

		// 무한반복으로 키의 입력을 처리
		while (true) {

			if (isBoss) { // 보스전
				// ==========================================보스스테이지 가기 전 딜레이
				try { // 바로 넘어가면 좀 그러니 일단 딜레이..?
					Thread.sleep(3000); // 원래 3000
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				// 플레이어의 최초 위치 설정
				playerX = 10;
				playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;

				isTriple = false;
				isSpeedUp = false;

				playerShootingList.clear();
				Enemy1List.clear();
				Enemy2List.clear();

				Enemy1AttackList.clear();
				Enemy2AttackList.clear();

				// 적, 아이템 제거
				Enemy1AttackList.remove(Enemy1Attack);
				Enemy2AttackList.remove(Enemy2Attack);
				Enemy1List.remove(Enemy1);
				Enemy2List.remove(Enemy2);
				tripleBulletList.remove(tripleBullet);
				speedUpList.remove(speedUp);
				hpUpList.remove(hpUp);

				BossList.clear();
				BossAttackList.clear();
				playerShootingList.clear();

				bossStage = true;
				isBoss = false;
				isClear = false;

				Boss = new Boss(1000, 300);
				BossList.add(Boss);
			}

			else {

			}

			while (bossStage) { // ===============보스전
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						keyProcess(); // 입력된 키를 처리하는 함수
						playerAttackProcess(); // 플레이어의 공격을 처리하는 함수
						enemyAppearProcess(); // 적의 생성을 처리하는 함수
						enemyMoveProcess(); // 적의 움직임을 처리하는 함수

						enemyAttackProcess(); // 적의 공격을 처리하는 함수

						itemAppearProcess(); // 아이템이 화면에 보이게 하는 함수
						itemMoveProcess(); // 아이템이 화면에서 흐르는 함수
						itemUsingProcess(); // 아이템을 먹었을 때 모션을 구현하는 함수
						cnt++; // 시간의 흐름
						cntB++;
						if (kill >= 5) { // 스킬게이지가 일정 기준에 도달하면
							isBomb = true; // 폭탄 사용가능
							playerSkillImg = playerSkillUseful; // 스킬 사용가능 이미지로 바꿔주기
							kill = 0;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (isOver) {
					isBoss = false;
					break;
				}
			}

			while (!isOver) {
				BossList.clear();
				BossAttackList.clear();
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						keyProcess(); // 입력된 키를 처리하는 함수
						playerAttackProcess(); // 플레이어의 공격을 처리하는 함수
						enemyAppearProcess(); // 적의 생성을 처리하는 함수
						enemyMoveProcess(); // 적의 움직임을 처리하는 함수
						enemyAttackProcess(); // 적의 공격을 처리하는 함수
						itemAppearProcess(); // 아이템이 화면에 보이게 하는 함수
						itemMoveProcess(); // 아이템이 화면에서 흐르는 함수
						itemUsingProcess(); // 아이템을 먹었을 때 모션을 구현하는 함수
						cnt++; // 시간의 흐름
						cntB++;
						if (kill >= 5) { // 스킬게이지가 일정 기준에 도달하면
							isBomb = true; // 폭탄 사용가능
							playerSkillImg = playerSkillUseful; // 스킬 사용가능 이미지로 바꿔주기
							kill = 0;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (isBoss)
					break;
			}
			while (!isRestart && !isBoss && isOver) { // 보스몹 다 깨서 clear됐을때, 재시작(r버튼 누르기)전까지 스레드 멈춤
				try { // 게임오버 관련
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void reset() { // 초기화함수~~
		isOver = false;
		isClear = false;
		isBomb = false;
		playerSkillImg = playerSkillBasic;
		isBoss = false;
		bossStage = false;
		isRestart = false; //////////////

		// 플레이어의 최초 위치 설정
		kill = 0;
		playerSpeed = 10;
		now = 0;
		nowB = 0;
		cnt = 0;
		cntB = 0;
		score = 0;
		playerHeart = 3;
		playerX = 10;
		playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;

		isTriple = false;
		isSpeedUp = false;

		playerShootingList.clear();
		Enemy1List.clear();
		Enemy2List.clear();
		Enemy1AttackList.clear();
		Enemy2AttackList.clear();
		BossList.clear();
		BossAttackList.clear();

		// 보스랑 아이템도 초기화 시켜줬어요
		tripleBulletList.clear();
		speedUpList.clear();
		hpUpList.clear();

		// 배경음악 시작
		introMusic.start();

	}

//적에 관련한 함수==========================================================
	// 적의 생성을 처리하는 함수
	private void enemyAppearProcess() {
		if (!bossStage) {
			if (cnt % 80 == 0) { // 80의 빈도로
				Enemy1 = new Enemy(1120, (int) (Math.random() * 621) + 20); // y값을 랜덤생성하여, 랜덤된 위치에 Enemy1생성
				Enemy1List.add(Enemy1); // Enemy1 생성을 담는 리스트에 추가
			}
			if (cnt % 130 == 0) { // 130의 빈도로
				Enemy2 = new Enemy(1120, (int) (Math.random() * 621) + 20); // y값을 랜덤생성하여, 랜덤된 위치에 Enemy2생성
				Enemy2List.add(Enemy2); // Enemy2 생성을 담는 리스트에 추가
			}
		}
	}

	// 적의 움직임을 처리하는 함수
	private void enemyMoveProcess() {
		for (int i = 0; i < Enemy1List.size(); i++) {
			Enemy1 = Enemy1List.get(i); // Enemy1 리스트에 담겨있는 Enemy1을 하나씩 꺼냄
			Enemy1.move1(); // 움직임 적용
		}
		for (int i = 0; i < Enemy2List.size(); i++) {
			Enemy2 = Enemy2List.get(i); // Enemy2 리스트에 담겨있는 Enemy1을 하나씩 꺼냄
			Enemy2.move2(); // 움직임 적용
		}
		for (int i = 0; i < BossList.size(); i++) {
			Boss = BossList.get(i); // Enemy2 리스트에 담겨있는 Enemy1을 하나씩 꺼냄
			Boss.move(); // 움직임 적용
		}
	}

	// 플레이어/ 적 공격함수=========================================================
	// 공격 구현 함수, 리스트에 저장된 공격의 수 만큼 fire()함수 호출하여 구현
	private void playerAttackProcess() {
		for (int i = 0; i < playerShootingList.size(); i++) {
			playerShootingAttack = playerShootingList.get(i);
			if (isSpeedUp) // speedUp 아이템 먹으면 -> 탄창 속도 빨라짐
				playerShootingAttack.fireUp();
			else
				playerShootingAttack.fire();

			// 충돌판정 관련
			// 일반공격 맞으면 적의 hp 감소, 0되면 제거
			for (int j = 0; j < Enemy1List.size(); j++) {
				Enemy1 = Enemy1List.get(j);
				if (playerShootingAttack.x + 50 > Enemy1.x && playerShootingAttack.x < Enemy1.x + Enemy1.width
						&& playerShootingAttack.y > Enemy1.y && playerShootingAttack.y < Enemy1.y + Enemy1.height) {

					hitSound.start();
					playerShootingList.remove(playerShootingAttack); // ==========================================
					if (isTriple) { // 3샷이면 바로죽게해버리기
						Enemy2List.remove(Enemy2);
						kill++;
						score += 1000;
					}

					else {
						hitSound.start();
						Enemy1.hp -= playerShootingAttack.attack;
					}
				}
				if (Enemy1.hp <= 0) {
					hitSound.start();
					Enemy1List.remove(Enemy1);
					playerShootingList.remove(playerShootingAttack); // =================================
					kill++;
					score += 1000;
				}
			}
			for (int j = 0; j < Enemy2List.size(); j++) {
				Enemy2 = Enemy2List.get(j);

				if (playerShootingAttack.x + 50 > Enemy2.x && playerShootingAttack.x < Enemy2.x + Enemy2.width
						&& playerShootingAttack.y > Enemy2.y && playerShootingAttack.y < Enemy2.y + Enemy2.height) {
					hitSound.start();
					playerShootingList.remove(playerShootingAttack); // =================================

					if (isTriple) { // 3샷이면 바로죽게해버리기
						hitSound.start();
						Enemy2List.remove(Enemy2);
						kill++;
						score += 1000;

						if (score >= test)
							isBoss = true;
					}

					else {
						hitSound.start();
						Enemy2.hp -= playerShootingAttack.attack;
					}
						
				}

				if (Enemy2.hp <= 0) {
					hitSound.start();
					Enemy2List.remove(Enemy2);
					playerShootingList.remove(playerShootingAttack); // =================================

					kill++;
					score += 1000;

					if (score >= test)
						isBoss = true;
				}
			}
			// 보스 충돌 판정
			for (int j = 0; j < BossList.size(); j++) {
				Boss = BossList.get(j);
				if (playerShootingAttack.x + 50 > Boss.x && playerShootingAttack.x < Boss.x + Boss.width
						&& playerShootingAttack.y > Boss.y && playerShootingAttack.y < Boss.y + Boss.height) {
					playerShootingList.remove(playerShootingAttack); // =================================

					if (isTriple) // 3샷이면 평소 공격보다 2배 데미지 입게
					{
						Boss.hp -= (playerShootingAttack.attack) * 2;
						// playerShootingList.remove(playerShootingAttack);
						// //=================================
					} else
						Boss.hp -= playerShootingAttack.attack;
				}
				if (Boss.hp <= 0) {
					hitSound.start();
					BossList.remove(Boss);

					kill++;
					score += 1000;
					isOver = true;
					isBoss = false;
					isClear = true;
				}
			}
		}

		for (int i = 0; i < playerBombList.size(); i++) {
			playerBombAttack = playerBombList.get(i);
			playerBombAttack.bomb();

			if (playerBombAttack.x >= blastX && playerBombAttack.x <= Main.SCREEN_WIDTH) {
				bombSound.start();
				for (int j = 0; j < Enemy1List.size(); j++) {
					Enemy1 = Enemy1List.get(j);
					if (Enemy1.x <= blastX + 500 && Enemy1.x + 160 >= blastX && Enemy1.y <= blastY + 500
							&& Enemy1.y + 100 >= blastY) {
						Enemy1List.remove(Enemy1);
						score += 1000;

						if (score >= test)
							isBoss = true;
					}
				}
				for (int j = 0; j < Enemy2List.size(); j++) {
					Enemy2 = Enemy2List.get(j);
					if (Enemy2.x <= blastX + 500 && Enemy2.x + 120 >= blastX && Enemy2.y <= blastY + 500
							&& Enemy2.y + 180 >= blastY) {
						Enemy2List.remove(Enemy2);
						score += 1000;

						if (score >= test)
							isBoss = true;
					}
				}
			}
		}

	}

	private void enemyAttackProcess() {

		if (!bossStage) { // 일반공격들
			if (cnt % 50 == 0) {
				Enemy1Attack = new EnemyAttack(Enemy1.x - 79, Enemy1.y + 35);
				Enemy1AttackList.add(Enemy1Attack);
				Enemy2Attack = new EnemyAttack(Enemy2.x - 79, Enemy2.y + 35);
				Enemy2AttackList.add(Enemy2Attack);
			}

			for (int i = 0; i < Enemy1AttackList.size(); i++) {
				Enemy1Attack = Enemy1AttackList.get(i);
				Enemy1Attack.fire();

				// 충돌판정관련
				// 적에게 공격받을때
				if (Enemy1Attack.x > playerX && Enemy1Attack.x < playerX + playerWidth && Enemy1Attack.y > playerY
						&& Enemy1Attack.y < playerY + playerHeight) {
					hitSound.start();
					playerHeart--;
					Enemy1AttackList.remove(Enemy1Attack);
				}
				if (playerHeart < 0) {
					// 게임오버
					isOver = true;
				}
			}

			for (int i = 0; i < Enemy2AttackList.size(); i++) {
				Enemy2Attack = Enemy2AttackList.get(i);
				Enemy2Attack.fire();

				if (Enemy2Attack.x > playerX && Enemy2Attack.x < playerX + playerWidth && Enemy2Attack.y > playerY
						&& Enemy2Attack.y < playerY + playerHeight) {
					hitSound.start();
					playerHeart--;
					Enemy2AttackList.remove(Enemy2Attack);
				}
				if (playerHeart <= 0) {
					// 게임오버
					isOver = true;
				}
			}
		}

		else { // ================================ 보스 !!!
				// ===================================================
			// 보스공격
			if (one == 1) { // 처음에 한번만 실행하게 해줌
				nowB = cntB;
				one--;
			}
			// now=cnt; //초 재시작

			if (cntB - nowB < 300) { // 한 패턴당 일정시간동안 진행

				count = 0; // count ->총알들 구분하기위한 변수로 0부터 bsT-1 까지 공격array에 집어넣게됨

				if (pattern == 0) { // 111111111111 첫번째 패턴의 경우

					for (int k = 0; k < bsT; k++) { // 총알들 넣어줌

						if (count == 0) { // 총알들 위치 보정
							selectX = 60;
							selectY = 200;
						} else if (count == 1) {
							selectX = 60;
							selectY = 170;
						} else if (count == 2) {
							selectX = 60;
							selectY = 110;
						}

						if (cntB % 20 == 0 && cntB-nowB <=200) {
							BossAttack= new BossAttack(Boss.x - selectX, Boss.y + selectY);
							BossAttackList.add(BossAttack);
							count++; // count를 증가시켜서 0번째, 1번째, .. n번째 총알을 집어넣음
						}

					}

					// count=0; //다시 count 0 부터 시작해서 이번엔 fire함수에서 공격 이동보정 해줌

					for (int i = 0; i < BossAttackList.size(); i++) { // 집어넣은것들 와르르르
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);
						// count++; //다음 공 넣어주기 위해 count 증가

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // 이쪽은 기존이랑
																											// 똑같아용
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// 게임오버
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}
				}

				else if (pattern == 1) { // 222222 두번째 패턴의 경우

					for (int k = 0; k < bsT; k++) { // 총알들 넣어줌

						if (count == 0) { // 총알들 위치 보정
							selectX = -20;
							selectY = 130;
						} else if (count == 1) {
							selectX = 60;
							selectY = 170;
						} else if (count == 2) {
							selectX = -20;
							selectY = 210;
						}

						if (cntB % 30 == 0&& cntB-nowB <=200) {
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y + selectY);
							BossAttackList.add(BossAttack);
							count++; // count를 증가시켜서 0번째, 1번째, .. n번째 총알을 집어넣음
						}

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // 집어넣은것들 와르르르
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // 이쪽은 기존이랑
																											// 똑같아용
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// 게임오버
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				
				else if (pattern == 2) { // 3333333 세번째 패턴의 경우

					for (int k = 0; k < bsT; k++) { // 총알들 넣어줌

						if(count==0) { //총알들 위치 보정 
							selectX=60;
							selectY=110;
						}
						else if(count==1) {
							selectX=60;
							selectY=150;
						}
						else if(count==2) {
							selectX=60;
							selectY=190;
						}
						else {
							selectX=60;
							selectY=230;
						}
								
						if (cntB % 20 == 0&& cntB-nowB <=200) { 
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y +selectY );
							BossAttackList.add(BossAttack);
							count++; //count를 증가시켜서 0번째, 1번째, .. n번째 총알을 집어넣음
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // 집어넣은것들 와르르르
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // 이쪽은 기존이랑
																											// 똑같아용
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// 게임오버
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				
				else if (pattern == 3) { // 44444 네번째 패턴의 경우

					for (int k = 0; k < bsT; k++) { // 총알들 넣어줌

						if(count==0) { //총알들 위치 보정 
							selectX=60;
							selectY=110;
						}
						else if(count==1) {
							selectX=60;
							selectY=190;
						}
						
								
						if (cntB % 20 == 0&& cntB-nowB <=200) { 
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y +selectY );
							BossAttackList.add(BossAttack);
							count++; //count를 증가시켜서 0번째, 1번째, .. n번째 총알을 집어넣음
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // 집어넣은것들 와르르르
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // 이쪽은 기존이랑
																											// 똑같아용
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// 게임오버
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				else if (pattern == 4) { // 55555 다섯번째 패턴의 경우

					for (int k = 0; k < bsT; k++) { // 총알들 넣어줌

						selectX=60;
						
						selectY=(count+1)*20;
						
						if (cntB % 20 == 0&& cntB-nowB <=200) { 
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y +selectY );
							BossAttackList.add(BossAttack);
							count++; //count를 증가시켜서 0번째, 1번째, .. n번째 총알을 집어넣음
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // 집어넣은것들 와르르르
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // 이쪽은 기존이랑
																											// 똑같아용
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// 게임오버
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				else if (pattern == 5) { // 6666

					for (int k = 0; k < bsT; k++) { // 총알들 넣어줌

						selectX=60;
						selectY = -5;
							
						if (cntB % 20 == 0&& cntB-nowB <=200) { 
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y +selectY );
							BossAttackList.add(BossAttack);
							count++; //count를 증가시켜서 0번째, 1번째, .. n번째 총알을 집어넣음
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // 집어넣은것들 와르르르
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // 이쪽은 기존이랑
																											// 똑같아용
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// 게임오버
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}

			}

			else { // 시간 경과시
					// pattern = rand.nextInt(4); // 0~3 사이의 랜덤숫자.. 난수생성으로 패턴 뽑기 최소 4개?패턴은 있어야할거같아서
					// 일단 이렇게..
				//pattern = rand.nextInt(6);
				// pattern =1; //테스트할때 여기서 다음 패턴 강제적으로 조정할수있음
				pattern = 5;

				if (pattern == 0) // 패턴에 따른 총알 갯수들 : bsT
					bsT = 3;
				else if (pattern == 1)
					bsT = 3;
				else if (pattern == 2)
					bsT = 4;
				else if (pattern == 3)
					bsT = 2;
				else if (pattern == 4)
					bsT = 8;
				else if (pattern == 5)
					bsT = 1;

				// one=1;
				if(BossAttackList != null) BossAttackList.clear();
				nowB = cntB; // 초 재시작
			}

		}
	}

	// 아이템 관련 함수들================================================================
	// 아이템 생성
	private void itemAppearProcess() {
		if (cnt >= 300 && cnt % 300 == 0) { // 80의 빈도로 - 탄창증가
			tripleBullet = new Item(1120, (int) (Math.random() * 621)); // y값을 랜덤생성하여, 랜덤된 위치에 Enemy1생성
			tripleBulletList.add(tripleBullet);
		}
		if (cnt >= 300 && cnt % 370 == 0) { // 130의 빈도로 - 속도증가
			speedUp = new Item(1120, (int) (Math.random() * 620));
			speedUpList.add(speedUp);
		}

		if (cnt >= 300 && cnt % 390 == 0) { // hp증가
			hpUp = new Item(1120, (int) (Math.random() * 621));
			hpUpList.add(hpUp);
		}
	}

	// 아이템 움직임
	private void itemMoveProcess() {
		for (int i = 0; i < tripleBulletList.size(); i++) {
			tripleBullet = tripleBulletList.get(i);
			tripleBullet.move();
		}
		for (int i = 0; i < speedUpList.size(); i++) {
			speedUp = speedUpList.get(i);
			speedUp.move();
		}
		for (int i = 0; i < hpUpList.size(); i++) {
			hpUp = hpUpList.get(i);
			hpUp.move();
		}
	}

	// 아이템을 먹었을때 플레이어의 사용
	private void itemUsingProcess() {
		for (int i = 0; i < tripleBulletList.size(); i++) { // 탄창 증가
			now = 0;
			tripleBullet = tripleBulletList.get(i);

			if (tripleBullet.x < playerX + playerWidth && tripleBullet.y < playerY + playerHeight
					&& tripleBullet.y + 80 > playerY) {
				tripleBulletList.remove(tripleBullet);
				isTriple = true;
				now = cnt;
			}
			// 일정 시간동안 흐르면 스킬 발동 종료
			if (cnt - now >= 20) {
				isTriple = false;
			}

		}

		for (int i = 0; i < speedUpList.size(); i++) { // 속도 증가
			now = 0;
			speedUp = speedUpList.get(i);

			if (speedUp.x < playerX + playerWidth && speedUp.y < playerY + playerHeight && speedUp.y + 80 > playerY) {
				speedUpList.remove(speedUp);
				isSpeedUp = true;
				now = cnt;
			}
			// 일정 시간동안 흐르면 스킬 발동 종료 -> 속도가 다시 느려짐
			if (cnt - now >= 10)
				isSpeedUp = false;
		}

		for (int i = 0; i < hpUpList.size(); i++) { // hp 증가
			now = 0;
			hpUp = hpUpList.get(i);

			if (hpUp.x < playerX + playerWidth && hpUp.y < playerY + playerHeight && hpUp.y + 80 > playerY) {
				hpUpList.remove(hpUp);

				if (playerHeart < 3)
					playerHeart++;
				else
					continue;
			}
		}
	}

//그리기 함수================================================================
	// 게임의 배경에 컴포넌트들을 그려넣기
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		itemDraw(g);
		infoDraw(g);
	}

	// 점수 그려주는 함수
	public void infoDraw(Graphics g) {
		if (isRestart) {
			g.drawImage(ShootingGame.gamebackground, 0, 0, null);
			isRestart = false;
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);

		if (isOver) { // 게임 오버시 재시작 안내문구 출력
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("Press R to restart  ", 290, 380);
		}

		if (isBoss) { // 보스전
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("Stage Clear!", 450, 380);
		}

		if (isClear) {

			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("You Win!", 430, 280);

		}

	}

	// 플레이어 그리기 함수
	public void playerDraw(Graphics g) {

		g.drawImage(player, playerX, playerY, null);
		g.drawImage(playerSkillImg, 1065, 550, null);

		for (int i = 0; i < playerHeart; i++) {
			g.drawImage(playerHeart_img, 1150 + (40 * i), 10, null);
		}
		for (int i = 0; i < playerShootingList.size(); i++) { // 공격이 들어온만큼(리스트에 저장된 수만큼) 공격 구현(그리기)

			if (!isTriple) {// 일반공격
				playerShootingAttack = playerShootingList.get(i);
				g.drawImage(Bullet, playerShootingAttack.x, playerShootingAttack.y + 40, null);
			} else // 3샷
			{
				playerShootingAttack = playerShootingList.get(i);
				g.drawImage(TripleBullet, playerShootingAttack.x, playerShootingAttack.y + 40, null);
			}

		}

		for (int i = 0; i < playerBombList.size(); i++) {
			playerBombAttack = playerBombList.get(i);
			if (playerBombAttack.x >= blastX && playerBombAttack.x <= Main.SCREEN_WIDTH) {
				g.drawImage(BombBlast, blastX, blastY - 200, null);
				continue;
			}
			g.drawImage(Bomb, playerBombAttack.x, playerBombAttack.y, null);
		}

	}

	// 적 그리기 함수
	public void enemyDraw(Graphics g) {
		for (int i = 0; i < Enemy1List.size(); i++) {
			Enemy1 = Enemy1List.get(i);
			g.drawImage(Enemy1.Enemy1, Enemy1.x, Enemy1.y, null);

			// 체력바함수
			g.setColor(Color.RED);
			g.fillRect(Enemy1.x + 14, Enemy1.y - 40, Enemy1.hp * 50, 20);
		}

		for (int i = 0; i < Enemy2List.size(); i++) {
			Enemy2 = Enemy2List.get(i);
			g.drawImage(Enemy2.Enemy2, Enemy2.x, Enemy2.y, null);

			// 체력바함수
			g.setColor(Color.RED);
			g.fillRect(Enemy2.x + 14, Enemy2.y - 40, Enemy2.hp * 50, 20);
		}

		for (int i = 0; i < Enemy1AttackList.size(); i++) {
			Enemy1Attack = Enemy1AttackList.get(i);
			g.drawImage(enemyBullet, Enemy1Attack.x, Enemy1Attack.y, null);

		}

		for (int i = 0; i < Enemy2AttackList.size(); i++) {
			Enemy2Attack = Enemy2AttackList.get(i);
			g.drawImage(enemyBullet, Enemy2Attack.x, Enemy2Attack.y, null);
		}
		for (int i = 0; i < BossList.size(); i++) {
			Boss = BossList.get(i);
			g.drawImage(Boss.Boss, Boss.x, Boss.y, null);

			// 보스 체력바 함수
			g.setColor(Color.RED);
			g.fillRect(Boss.x + 14, Boss.y - 40, Boss.hp, 20);
		}

		for (int i = 0; i < BossAttackList.size(); i++) {
			BossAttack = BossAttackList.get(i);
			g.drawImage(BossBullet, BossAttack.x, BossAttack.y, null);
		}
	
	}

	// 아이템 그리기 함수
	public void itemDraw(Graphics g) {
		for (int i = 0; i < tripleBulletList.size(); i++) {
			tripleBullet = tripleBulletList.get(i);
			g.drawImage(tripleBullet.tripleAttack, tripleBullet.x, tripleBullet.y, null);
		}
		for (int i = 0; i < speedUpList.size(); i++) {
			speedUp = speedUpList.get(i);
			g.drawImage(speedUp.speedUp, speedUp.x, speedUp.y, null);
		}
		for (int i = 0; i < hpUpList.size(); i++) {
			hpUp = hpUpList.get(i);
			g.drawImage(hpUp.hpUp, hpUp.x, hpUp.y, null);
		}
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

//키 입력 처리=========================================================
	// 각 키가 눌렸을 때 플레이어의 이동 처리
	private void keyProcess() {
		if (!isClear) {
			if (up && playerY - playerSpeed > 0) {
				playerY -= playerSpeed;
			}
			if (down && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) {
				playerY += playerSpeed;
			}
			if (left && playerX - playerSpeed > 0) {
				playerX -= playerSpeed;
			}

			if (right && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) {
				playerX += playerSpeed;
			}
			// 공격키가 들어올때마다 리스트에 저장, 원하는 위치로 조정한 x,y값으로 생성자 호출, cnt%5로 약간의 딜레이
			if (shooting) { // 넘빠른거같아서 10으로 조정했어요!
				if (isSpeedUp && cnt % 7 == 0) {
					if (!isTriple) { // 일반공격
						playerShootingAttack = new playerAttack(playerX + 222, playerY + 25);
						playerShootingList.add(playerShootingAttack);
					} else {
						playerShootingAttack = new playerAttack(playerX + 222, playerY + 25);
						playerShootingList.add(playerShootingAttack);

					}
				} else if (!isSpeedUp && cnt % 10 == 0) {
					if (!isTriple) { // 일반공격
						playerShootingAttack = new playerAttack(playerX + 222, playerY + 25);
						playerShootingList.add(playerShootingAttack);
					} else {
						playerShootingAttack = new playerAttack(playerX + 222, playerY + 25);
						playerShootingList.add(playerShootingAttack);

					}

				}

			}
			if (bomb && cnt % 4 == 0) {
				if (isBomb == true) {
					playerBombAttack = new playerAttack(playerX + 230, playerY + 60);
					playerBombList.add(playerBombAttack);
					isBomb = false;
					blastX = playerX + 640;
					blastY = playerY;
					playerSkillImg = playerSkillBasic;
				}
			}
		}

	}

//================================세터 함수=======================================================
	// ShootingGame의 KeyListener로부터 up(...)의 참,거짓을 받아서 game의 up(...)변수에 저장
	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public void setStart(boolean isRestart) {
		this.isRestart = isRestart;

	}

}
