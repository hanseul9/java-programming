package sg_1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game extends Thread {
// ��ü���� ���ӿ� �ʿ��� ���� ( �ð����� )===================================

	private int delay = 20;
	private long pretime;
	private int cnt; // �ð��� �帧
	public static int cntB;
	private int blastX, blastY; // ��ź�� ���� ��ǥ
	private int score; // ����
	private int kill; // ���� �󸶳� �׿��°�?

	int pattern = 0; // ù��° �������� ����
	int one = 1; // �ѹ��� �۵��ϰԲ� �ϴ� ����
	int selectX = 0; // �Ѿ� ��ġ??��..
	int selectY = 0;
	int bsT = 0; // ������ źâ��
	int count = 0; // �Ѿ˱��� ����

//�÷��̾� ���� �̹��� �ҷ�����====================================
	// ������ �÷��̾�� �� �̹��� �ҷ�����, ũ�� ����
	private Image player_img = new ImageIcon("src/images/player.png").getImage();
	Image player = player_img.getScaledInstance(200, 80, Image.SCALE_SMOOTH);

	// �÷��̾��� ����� ǥ���� �� �ִ� �̹��� �ҷ�����, ũ�� ����
	private Image player_Heart = new ImageIcon("src/images/playerHeart.png").getImage();
	Image playerHeart_img = player_Heart.getScaledInstance(20, 40, Image.SCALE_SMOOTH);

	// �÷��̾��� ���ݹ���� ���� �Ѿ˰� ��ź�� �̹��� �ҷ�����, ũ�� ����
	Image Bullet_img = new ImageIcon("src/images/bullet.png").getImage();
	Image Bullet = Bullet_img.getScaledInstance(50, 25, Image.SCALE_SMOOTH);

	// 3�Ѿ�
	Image TripleBullet_img = new ImageIcon("src/images/tripleBullet.png").getImage();
	Image TripleBullet = TripleBullet_img.getScaledInstance(50, 60, Image.SCALE_SMOOTH);

	Image Bomb_img = new ImageIcon("src/images/bomb.png").getImage();
	Image Bomb = Bomb_img.getScaledInstance(70, 30, Image.SCALE_SMOOTH);

	// ���� ������ ��ź�� ������ �̹���, ũ�� ����
	private Image Blast = new ImageIcon("src/images/BombBlast.png").getImage();
	Image BombBlast = Blast.getScaledInstance(500, 500, Image.SCALE_SMOOTH);

	private Image playerSkillBasic = new ImageIcon("src/images/skill_basic.png").getImage();
	private Image playerSkillUseful = new ImageIcon("src/images/skill_useful.png").getImage();
	private Image playerSkillImg = playerSkillBasic;

//���� ���ݼ��� �̹��� �ҷ�����==============================================
	private Image enemy_bullet_img = new ImageIcon("src/images/enemy_bullet.png").getImage();
	private Image enemyBullet = enemy_bullet_img.getScaledInstance(70, 40, Image.SCALE_SMOOTH);

	private Image Boss_bullet_img = new ImageIcon("src/images/circle.png").getImage(); // "src/images/BossBullet.png"
	private Image BossBullet = Boss_bullet_img.getScaledInstance(40, 20, Image.SCALE_SMOOTH);

//�÷��̾� ���� ���� ����=============================================
	// �÷��̾ ������ ������
	public static int playerX, playerY; // �÷��̾��� ��ġ�� ������� ��ǥ����
	private int playerWidth = 200; // �÷��̾��� ������(ũ��)
	private int playerHeight = 80; // �÷��̾��� ������(ũ��)
	private int playerSpeed = 10; // �÷��̾��� �̵� �ӵ�(10�� ������� ����)
	private int playerHeart = 3;
	private int test = 1000;

	Random rand = new Random();

	private boolean up, down, left, right; // ��,�Ʒ�,����,������ Ű�� ���� ���ȴ��� �ƴ��� ������ ����
	private boolean shooting, bomb;
	private boolean isBomb = true;
	private boolean isSpeedUp = false;
	private boolean isTriple = false;
	boolean isOver; // ���ӿ��� ����
	boolean isClear; // ���� ��� Ŭ���� �ߴ���(���������� �� ���� isClear�� true�� ��)
	private boolean isRestart; // r������ restart�ߴ���, true�� �Ǹ� �ƿ� �� �ʱ�ȭ�ϰ� ó������ ���� �����ϵ��� �ϱ� ����
	private boolean isBoss = false; // ������������ �˻�
	private boolean bossStage = false;
	// private boolean bossAt = true;

//�÷��̾� ���� ����Ʈ�� ��ü(����) ����======================================
	ArrayList<playerAttack> playerShootingList = new ArrayList<playerAttack>(); // �÷��̾��� ������ ���� ����Ʈ
	private playerAttack playerShootingAttack; // playerAttack�� ���� ����

	ArrayList<playerAttack> playerBombList = new ArrayList<playerAttack>(); // �÷��̾��� ��ų(��ź)�� ���� ����Ʈ
	private playerAttack playerBombAttack; // playerAttack�� ��ų�� ���� ����

	// �� ���� ����Ʈ�� ��ü(����) ����======================================
	ArrayList<Enemy> Enemy1List = new ArrayList<Enemy>(); // ��1(�յڷ� ������)�� ������ ���� ����Ʈ
	private Enemy Enemy1; // ��1(�յڷ� ������), Enemy�� ��������

	ArrayList<EnemyAttack> Enemy1AttackList = new ArrayList<EnemyAttack>(); // ��1�� ������ ���� ����Ʈ
	private EnemyAttack Enemy1Attack; // ��1�� ����, EnemyAttack�� ��������

	ArrayList<Enemy> Enemy2List = new ArrayList<Enemy>(); // ��2(�¿�� �����̸鼭 ����)�� ������ ���� ����Ʈ
	private Enemy Enemy2; // ��2(�¿�� �����̸鼭 ����), Enemy�� ��������

	ArrayList<EnemyAttack> Enemy2AttackList = new ArrayList<EnemyAttack>(); // ��2�� ������ ���� ����Ʈ
	private EnemyAttack Enemy2Attack; // ��2�� ����, EnemyAttack�� ��������

	// =====������~~~~===============================================
	ArrayList<Boss> BossList = new ArrayList<Boss>(); // ��2(�¿�� �����̸鼭 ����)�� ������ ���� ����Ʈ
	private Boss Boss; // ��2(�¿�� �����̸鼭 ����), Enemy�� ��������

	ArrayList<BossAttack> BossAttackList = new ArrayList<BossAttack>(); // ��2�� ������ ���� ����Ʈ
	private BossAttack BossAttack; // ��2�� ����, EnemyAttack�� ��������

	// �� ������ ���� �Ҹ�
	private Audio hitSound = new Audio("src/music/hitSound.mp3", false);
	// �ǰ��� ����
	// ��ź ������ ���� �Ҹ�
	private Audio bombSound = new Audio("src/music/bombSound.wav", false);

//������ ���� ����Ʈ�� ��ü ����===============================================
	int now;
	int nowB;
	ArrayList<Item> tripleBulletList = new ArrayList<Item>(); // �Ѿ� ���� ���� �߻� ��ų�� ���� ����Ʈ
	private Item tripleBullet; // �Ѿ� ���� ���� �߻� ��ų ������ ���� ��ü

	ArrayList<Item> speedUpList = new ArrayList<Item>(); // �ӵ� ���� ��ų�� ���� ����Ʈ
	private Item speedUp; // �ӵ� ���� ��ų ������ ���� ��ü

	ArrayList<Item> hpUpList = new ArrayList<Item>(); // ���� ����
	private Item hpUp;

	Music introMusic = new Music("backgroundmusic.mp3", true);

//���� ���� ====================================================
	@Override
	public void run() {

		reset(); // �ʱ�ȭ �Լ�

		// bossStage=true;

		// ���ѹݺ����� Ű�� �Է��� ó��
		while (true) {

			if (isBoss) { // ������
				// ==========================================������������ ���� �� ������
				try { // �ٷ� �Ѿ�� �� �׷��� �ϴ� ������..?
					Thread.sleep(3000); // ���� 3000
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				// �÷��̾��� ���� ��ġ ����
				playerX = 10;
				playerY = (Main.SCREEN_HEIGHT - playerHeight) / 2;

				isTriple = false;
				isSpeedUp = false;

				playerShootingList.clear();
				Enemy1List.clear();
				Enemy2List.clear();

				Enemy1AttackList.clear();
				Enemy2AttackList.clear();

				// ��, ������ ����
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

			while (bossStage) { // ===============������
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						keyProcess(); // �Էµ� Ű�� ó���ϴ� �Լ�
						playerAttackProcess(); // �÷��̾��� ������ ó���ϴ� �Լ�
						enemyAppearProcess(); // ���� ������ ó���ϴ� �Լ�
						enemyMoveProcess(); // ���� �������� ó���ϴ� �Լ�

						enemyAttackProcess(); // ���� ������ ó���ϴ� �Լ�

						itemAppearProcess(); // �������� ȭ�鿡 ���̰� �ϴ� �Լ�
						itemMoveProcess(); // �������� ȭ�鿡�� �帣�� �Լ�
						itemUsingProcess(); // �������� �Ծ��� �� ����� �����ϴ� �Լ�
						cnt++; // �ð��� �帧
						cntB++;
						if (kill >= 5) { // ��ų�������� ���� ���ؿ� �����ϸ�
							isBomb = true; // ��ź ��밡��
							playerSkillImg = playerSkillUseful; // ��ų ��밡�� �̹����� �ٲ��ֱ�
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
						keyProcess(); // �Էµ� Ű�� ó���ϴ� �Լ�
						playerAttackProcess(); // �÷��̾��� ������ ó���ϴ� �Լ�
						enemyAppearProcess(); // ���� ������ ó���ϴ� �Լ�
						enemyMoveProcess(); // ���� �������� ó���ϴ� �Լ�
						enemyAttackProcess(); // ���� ������ ó���ϴ� �Լ�
						itemAppearProcess(); // �������� ȭ�鿡 ���̰� �ϴ� �Լ�
						itemMoveProcess(); // �������� ȭ�鿡�� �帣�� �Լ�
						itemUsingProcess(); // �������� �Ծ��� �� ����� �����ϴ� �Լ�
						cnt++; // �ð��� �帧
						cntB++;
						if (kill >= 5) { // ��ų�������� ���� ���ؿ� �����ϸ�
							isBomb = true; // ��ź ��밡��
							playerSkillImg = playerSkillUseful; // ��ų ��밡�� �̹����� �ٲ��ֱ�
							kill = 0;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (isBoss)
					break;
			}
			while (!isRestart && !isBoss && isOver) { // ������ �� ���� clear������, �����(r��ư ������)������ ������ ����
				try { // ���ӿ��� ����
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void reset() { // �ʱ�ȭ�Լ�~~
		isOver = false;
		isClear = false;
		isBomb = false;
		playerSkillImg = playerSkillBasic;
		isBoss = false;
		bossStage = false;
		isRestart = false; //////////////

		// �÷��̾��� ���� ��ġ ����
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

		// ������ �����۵� �ʱ�ȭ ��������
		tripleBulletList.clear();
		speedUpList.clear();
		hpUpList.clear();

		// ������� ����
		introMusic.start();

	}

//���� ������ �Լ�==========================================================
	// ���� ������ ó���ϴ� �Լ�
	private void enemyAppearProcess() {
		if (!bossStage) {
			if (cnt % 80 == 0) { // 80�� �󵵷�
				Enemy1 = new Enemy(1120, (int) (Math.random() * 621) + 20); // y���� ���������Ͽ�, ������ ��ġ�� Enemy1����
				Enemy1List.add(Enemy1); // Enemy1 ������ ��� ����Ʈ�� �߰�
			}
			if (cnt % 130 == 0) { // 130�� �󵵷�
				Enemy2 = new Enemy(1120, (int) (Math.random() * 621) + 20); // y���� ���������Ͽ�, ������ ��ġ�� Enemy2����
				Enemy2List.add(Enemy2); // Enemy2 ������ ��� ����Ʈ�� �߰�
			}
		}
	}

	// ���� �������� ó���ϴ� �Լ�
	private void enemyMoveProcess() {
		for (int i = 0; i < Enemy1List.size(); i++) {
			Enemy1 = Enemy1List.get(i); // Enemy1 ����Ʈ�� ����ִ� Enemy1�� �ϳ��� ����
			Enemy1.move1(); // ������ ����
		}
		for (int i = 0; i < Enemy2List.size(); i++) {
			Enemy2 = Enemy2List.get(i); // Enemy2 ����Ʈ�� ����ִ� Enemy1�� �ϳ��� ����
			Enemy2.move2(); // ������ ����
		}
		for (int i = 0; i < BossList.size(); i++) {
			Boss = BossList.get(i); // Enemy2 ����Ʈ�� ����ִ� Enemy1�� �ϳ��� ����
			Boss.move(); // ������ ����
		}
	}

	// �÷��̾�/ �� �����Լ�=========================================================
	// ���� ���� �Լ�, ����Ʈ�� ����� ������ �� ��ŭ fire()�Լ� ȣ���Ͽ� ����
	private void playerAttackProcess() {
		for (int i = 0; i < playerShootingList.size(); i++) {
			playerShootingAttack = playerShootingList.get(i);
			if (isSpeedUp) // speedUp ������ ������ -> źâ �ӵ� ������
				playerShootingAttack.fireUp();
			else
				playerShootingAttack.fire();

			// �浹���� ����
			// �Ϲݰ��� ������ ���� hp ����, 0�Ǹ� ����
			for (int j = 0; j < Enemy1List.size(); j++) {
				Enemy1 = Enemy1List.get(j);
				if (playerShootingAttack.x + 50 > Enemy1.x && playerShootingAttack.x < Enemy1.x + Enemy1.width
						&& playerShootingAttack.y > Enemy1.y && playerShootingAttack.y < Enemy1.y + Enemy1.height) {

					hitSound.start();
					playerShootingList.remove(playerShootingAttack); // ==========================================
					if (isTriple) { // 3���̸� �ٷ��װ��ع�����
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

					if (isTriple) { // 3���̸� �ٷ��װ��ع�����
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
			// ���� �浹 ����
			for (int j = 0; j < BossList.size(); j++) {
				Boss = BossList.get(j);
				if (playerShootingAttack.x + 50 > Boss.x && playerShootingAttack.x < Boss.x + Boss.width
						&& playerShootingAttack.y > Boss.y && playerShootingAttack.y < Boss.y + Boss.height) {
					playerShootingList.remove(playerShootingAttack); // =================================

					if (isTriple) // 3���̸� ��� ���ݺ��� 2�� ������ �԰�
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

		if (!bossStage) { // �Ϲݰ��ݵ�
			if (cnt % 50 == 0) {
				Enemy1Attack = new EnemyAttack(Enemy1.x - 79, Enemy1.y + 35);
				Enemy1AttackList.add(Enemy1Attack);
				Enemy2Attack = new EnemyAttack(Enemy2.x - 79, Enemy2.y + 35);
				Enemy2AttackList.add(Enemy2Attack);
			}

			for (int i = 0; i < Enemy1AttackList.size(); i++) {
				Enemy1Attack = Enemy1AttackList.get(i);
				Enemy1Attack.fire();

				// �浹��������
				// ������ ���ݹ�����
				if (Enemy1Attack.x > playerX && Enemy1Attack.x < playerX + playerWidth && Enemy1Attack.y > playerY
						&& Enemy1Attack.y < playerY + playerHeight) {
					hitSound.start();
					playerHeart--;
					Enemy1AttackList.remove(Enemy1Attack);
				}
				if (playerHeart < 0) {
					// ���ӿ���
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
					// ���ӿ���
					isOver = true;
				}
			}
		}

		else { // ================================ ���� !!!
				// ===================================================
			// ��������
			if (one == 1) { // ó���� �ѹ��� �����ϰ� ����
				nowB = cntB;
				one--;
			}
			// now=cnt; //�� �����

			if (cntB - nowB < 300) { // �� ���ϴ� �����ð����� ����

				count = 0; // count ->�Ѿ˵� �����ϱ����� ������ 0���� bsT-1 ���� ����array�� ����ְԵ�

				if (pattern == 0) { // 111111111111 ù��° ������ ���

					for (int k = 0; k < bsT; k++) { // �Ѿ˵� �־���

						if (count == 0) { // �Ѿ˵� ��ġ ����
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
							count++; // count�� �������Ѽ� 0��°, 1��°, .. n��° �Ѿ��� �������
						}

					}

					// count=0; //�ٽ� count 0 ���� �����ؼ� �̹��� fire�Լ����� ���� �̵����� ����

					for (int i = 0; i < BossAttackList.size(); i++) { // ��������͵� �͸�����
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);
						// count++; //���� �� �־��ֱ� ���� count ����

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // ������ �����̶�
																											// �Ȱ��ƿ�
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// ���ӿ���
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}
				}

				else if (pattern == 1) { // 222222 �ι�° ������ ���

					for (int k = 0; k < bsT; k++) { // �Ѿ˵� �־���

						if (count == 0) { // �Ѿ˵� ��ġ ����
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
							count++; // count�� �������Ѽ� 0��°, 1��°, .. n��° �Ѿ��� �������
						}

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // ��������͵� �͸�����
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // ������ �����̶�
																											// �Ȱ��ƿ�
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// ���ӿ���
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				
				else if (pattern == 2) { // 3333333 ����° ������ ���

					for (int k = 0; k < bsT; k++) { // �Ѿ˵� �־���

						if(count==0) { //�Ѿ˵� ��ġ ���� 
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
							count++; //count�� �������Ѽ� 0��°, 1��°, .. n��° �Ѿ��� �������
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // ��������͵� �͸�����
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // ������ �����̶�
																											// �Ȱ��ƿ�
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// ���ӿ���
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				
				else if (pattern == 3) { // 44444 �׹�° ������ ���

					for (int k = 0; k < bsT; k++) { // �Ѿ˵� �־���

						if(count==0) { //�Ѿ˵� ��ġ ���� 
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
							count++; //count�� �������Ѽ� 0��°, 1��°, .. n��° �Ѿ��� �������
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // ��������͵� �͸�����
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // ������ �����̶�
																											// �Ȱ��ƿ�
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// ���ӿ���
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				else if (pattern == 4) { // 55555 �ټ���° ������ ���

					for (int k = 0; k < bsT; k++) { // �Ѿ˵� �־���

						selectX=60;
						
						selectY=(count+1)*20;
						
						if (cntB % 20 == 0&& cntB-nowB <=200) { 
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y +selectY );
							BossAttackList.add(BossAttack);
							count++; //count�� �������Ѽ� 0��°, 1��°, .. n��° �Ѿ��� �������
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // ��������͵� �͸�����
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // ������ �����̶�
																											// �Ȱ��ƿ�
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// ���ӿ���
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}
				else if (pattern == 5) { // 6666

					for (int k = 0; k < bsT; k++) { // �Ѿ˵� �־���

						selectX=60;
						selectY = -5;
							
						if (cntB % 20 == 0&& cntB-nowB <=200) { 
							BossAttack = new BossAttack(Boss.x - selectX, Boss.y +selectY );
							BossAttackList.add(BossAttack);
							count++; //count�� �������Ѽ� 0��°, 1��°, .. n��° �Ѿ��� �������
						}	

					}

					for (int i = 0; i < BossAttackList.size(); i++) { // ��������͵� �͸�����
						BossAttack = BossAttackList.get(i);
						BossAttack.fire(pattern, i, bsT);

						if (BossAttack.x > playerX && BossAttack.x < playerX + playerWidth
								&& BossAttack.y > playerY && BossAttack.y < playerY + playerHeight) { // ������ �����̶�
																											// �Ȱ��ƿ�
							hitSound.start();
							playerHeart -= 1;
							BossAttackList.remove(BossAttack);
						}
						if (playerHeart <= 0) {
							// ���ӿ���
							hitSound.start();
							isOver = true;
							isBoss = false;
						}

					}

				}

			}

			else { // �ð� �����
					// pattern = rand.nextInt(4); // 0~3 ������ ��������.. ������������ ���� �̱� �ּ� 4��?������ �־���ҰŰ��Ƽ�
					// �ϴ� �̷���..
				//pattern = rand.nextInt(6);
				// pattern =1; //�׽�Ʈ�Ҷ� ���⼭ ���� ���� ���������� �����Ҽ�����
				pattern = 5;

				if (pattern == 0) // ���Ͽ� ���� �Ѿ� ������ : bsT
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
				nowB = cntB; // �� �����
			}

		}
	}

	// ������ ���� �Լ���================================================================
	// ������ ����
	private void itemAppearProcess() {
		if (cnt >= 300 && cnt % 300 == 0) { // 80�� �󵵷� - źâ����
			tripleBullet = new Item(1120, (int) (Math.random() * 621)); // y���� ���������Ͽ�, ������ ��ġ�� Enemy1����
			tripleBulletList.add(tripleBullet);
		}
		if (cnt >= 300 && cnt % 370 == 0) { // 130�� �󵵷� - �ӵ�����
			speedUp = new Item(1120, (int) (Math.random() * 620));
			speedUpList.add(speedUp);
		}

		if (cnt >= 300 && cnt % 390 == 0) { // hp����
			hpUp = new Item(1120, (int) (Math.random() * 621));
			hpUpList.add(hpUp);
		}
	}

	// ������ ������
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

	// �������� �Ծ����� �÷��̾��� ���
	private void itemUsingProcess() {
		for (int i = 0; i < tripleBulletList.size(); i++) { // źâ ����
			now = 0;
			tripleBullet = tripleBulletList.get(i);

			if (tripleBullet.x < playerX + playerWidth && tripleBullet.y < playerY + playerHeight
					&& tripleBullet.y + 80 > playerY) {
				tripleBulletList.remove(tripleBullet);
				isTriple = true;
				now = cnt;
			}
			// ���� �ð����� �帣�� ��ų �ߵ� ����
			if (cnt - now >= 20) {
				isTriple = false;
			}

		}

		for (int i = 0; i < speedUpList.size(); i++) { // �ӵ� ����
			now = 0;
			speedUp = speedUpList.get(i);

			if (speedUp.x < playerX + playerWidth && speedUp.y < playerY + playerHeight && speedUp.y + 80 > playerY) {
				speedUpList.remove(speedUp);
				isSpeedUp = true;
				now = cnt;
			}
			// ���� �ð����� �帣�� ��ų �ߵ� ���� -> �ӵ��� �ٽ� ������
			if (cnt - now >= 10)
				isSpeedUp = false;
		}

		for (int i = 0; i < hpUpList.size(); i++) { // hp ����
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

//�׸��� �Լ�================================================================
	// ������ ��濡 ������Ʈ���� �׷��ֱ�
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		itemDraw(g);
		infoDraw(g);
	}

	// ���� �׷��ִ� �Լ�
	public void infoDraw(Graphics g) {
		if (isRestart) {
			g.drawImage(ShootingGame.gamebackground, 0, 0, null);
			isRestart = false;
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);

		if (isOver) { // ���� ������ ����� �ȳ����� ���
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("Press R to restart  ", 290, 380);
		}

		if (isBoss) { // ������
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

	// �÷��̾� �׸��� �Լ�
	public void playerDraw(Graphics g) {

		g.drawImage(player, playerX, playerY, null);
		g.drawImage(playerSkillImg, 1065, 550, null);

		for (int i = 0; i < playerHeart; i++) {
			g.drawImage(playerHeart_img, 1150 + (40 * i), 10, null);
		}
		for (int i = 0; i < playerShootingList.size(); i++) { // ������ ���¸�ŭ(����Ʈ�� ����� ����ŭ) ���� ����(�׸���)

			if (!isTriple) {// �Ϲݰ���
				playerShootingAttack = playerShootingList.get(i);
				g.drawImage(Bullet, playerShootingAttack.x, playerShootingAttack.y + 40, null);
			} else // 3��
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

	// �� �׸��� �Լ�
	public void enemyDraw(Graphics g) {
		for (int i = 0; i < Enemy1List.size(); i++) {
			Enemy1 = Enemy1List.get(i);
			g.drawImage(Enemy1.Enemy1, Enemy1.x, Enemy1.y, null);

			// ü�¹��Լ�
			g.setColor(Color.RED);
			g.fillRect(Enemy1.x + 14, Enemy1.y - 40, Enemy1.hp * 50, 20);
		}

		for (int i = 0; i < Enemy2List.size(); i++) {
			Enemy2 = Enemy2List.get(i);
			g.drawImage(Enemy2.Enemy2, Enemy2.x, Enemy2.y, null);

			// ü�¹��Լ�
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

			// ���� ü�¹� �Լ�
			g.setColor(Color.RED);
			g.fillRect(Boss.x + 14, Boss.y - 40, Boss.hp, 20);
		}

		for (int i = 0; i < BossAttackList.size(); i++) {
			BossAttack = BossAttackList.get(i);
			g.drawImage(BossBullet, BossAttack.x, BossAttack.y, null);
		}
	
	}

	// ������ �׸��� �Լ�
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

//Ű �Է� ó��=========================================================
	// �� Ű�� ������ �� �÷��̾��� �̵� ó��
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
			// ����Ű�� ���ö����� ����Ʈ�� ����, ���ϴ� ��ġ�� ������ x,y������ ������ ȣ��, cnt%5�� �ణ�� ������
			if (shooting) { // �Ѻ����Ű��Ƽ� 10���� �����߾��!
				if (isSpeedUp && cnt % 7 == 0) {
					if (!isTriple) { // �Ϲݰ���
						playerShootingAttack = new playerAttack(playerX + 222, playerY + 25);
						playerShootingList.add(playerShootingAttack);
					} else {
						playerShootingAttack = new playerAttack(playerX + 222, playerY + 25);
						playerShootingList.add(playerShootingAttack);

					}
				} else if (!isSpeedUp && cnt % 10 == 0) {
					if (!isTriple) { // �Ϲݰ���
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

//================================���� �Լ�=======================================================
	// ShootingGame�� KeyListener�κ��� up(...)�� ��,������ �޾Ƽ� game�� up(...)������ ����
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
