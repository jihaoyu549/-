package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/*
 * ��л��࣬ʵ�ִ�л����ƶ��������ӵ������Լ����ط���
 */
public class BigAirplane extends FlyingObject implements Enemy {

	private int speed;
	BigAirplane(){
		super(66,89,3);
		
		if(World.score>=260) {
			speed = 4;   //�ڶ����ٶ�
		}
		else {
			speed=2;     //��һ���ٶ�
		}
		
	}
	
	public void step() {
		y += speed;
	}

	/*
	 * ��ȡ��л���ͼƬ��״̬ΪLIFEʱ���ش�л�ͼƬ
	 * ״̬ΪDEADʱ����4�ű���ͼƬ��ȫ�����غ�״̬��ΪREMOVE������null
	 */
	int index = 1;
	public BufferedImage getImage() {
		if(isLife()){
			return Images.bigairplanes[0];
		}else if(isDead()){
			if(index==5){
				state = REMOVE;
				return null;
			}
			return Images.bigairplanes[index++];
		}
		return null;
	}

	//����һ���ӵ����ӵ������ӵ��ĳ�ʼ����Ϊ��ǰС�л������棬�ӵ���������
	public Bullet[] shoot(){
		if(World.score>=260)
		{
			Bullet[] res = new Bullet[3];             //�ڶ����ӵ���
		    res[0] = new Bullet(x+this.width/2,y+this.height+30,"down");
		    res[1] = new Bullet(x+this.width/4,y+this.height+10,"down");
		    res[2] = new Bullet(x+3*this.width/4,y+this.height+10,"down");
		    return res;
		}
		else {
			Bullet[] res = new Bullet[1];            //��һ���ӵ���
		    res[0] = new Bullet(x+this.width/2,y+this.height+10,"down");
		    return res;
		}
			
		}
	
	//��л���y������ڴ��ڵĸ߷���ture
	public boolean outBround() {
		return y>=World.HEIGHT;
	}
	
	//ֱ�ӷ��ش�л��ķ���
	public int getScore() {
		return 3;
	}

}
