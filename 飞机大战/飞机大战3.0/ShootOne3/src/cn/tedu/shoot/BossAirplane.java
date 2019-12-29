package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/*
 * boss���࣬ʵ��boss�����ƶ��������ӵ�
 */

public class BossAirplane extends FlyingObject implements Enemy,Award {

	private int speed;
	
	BossAirplane(){
		super(150,113,10);
		if(World.score>=260) {
			speed = 4;          //�ڶ����ٶ�
		}
		else {                   
			speed = 2;          //��һ���ٶ�
		}
	}
	
	//����boss���ķ���
	public int getScore() {
		return 100;
	}

	//boss�����ƶ�
	public void step() {
		y += speed;
	}

	/*
	 * ��ȡboss����ͼƬ��״̬ΪLIFEʱ����boss��ͼƬ
	 * ״̬ΪDEADʱ����4�ű���ͼƬ��ȫ�����غ�״̬��ΪREMOVE������null
	 */
	int index = 1;
	public BufferedImage getImage() {
		if(isLife()){
			return Images.bossairplanes[0];
		}else if(isDead()){
			if(index==5){
				state = REMOVE;
				return null;
			}
			return Images.bossairplanes[index++];
		}
		return null;
	}
	
	//���������ӵ�
	public Bullet[] shoot(){
		if(World.score>=260) {
			Bullet[] res = new Bullet[5];
				res[0] = new Bullet(x+3*this.width/6,y+this.height+60,"down");//�м�
				res[1] = new Bullet(x+2*this.width/3,y+this.height+30,"down");//��1
				res[2] = new Bullet(x+this.width/3,y+this.height+30,"down");//��1
				res[3] = new Bullet(x+this.width/9,y+this.height+10,"down");//��2
				res[4] = new Bullet(x+8*this.width/9,y+this.height+10,"down");//��2
			return res;
		}
		else {
			Bullet[] res = new Bullet[2];
		    res[0] = new Bullet(x+this.width/3,y+this.height+10,"down");
		    res[1] = new Bullet(x+2*this.width/3,y+this.height+10,"down");
		    return res;
		}
		
	}

	//�ж��Ƿ�Խ��
	public boolean outBround() {
		return y>=World.HEIGHT;
	}

	//���ؽ�������
	@Override
	public int getType() {
		return (int)(Math.random()*2);
	}
}
