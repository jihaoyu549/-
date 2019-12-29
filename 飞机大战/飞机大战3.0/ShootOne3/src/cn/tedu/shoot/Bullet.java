package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/*
 * �ӵ��࣬ʵ���ӵ����ƶ�
 */
public class Bullet extends FlyingObject {

	//dirΪ��ͬ���ӵ�ͼƬ��ǣ�0Ϊ���ϣ�1Ϊ����
	private int speed;
	private int dir;
	
	Bullet(int x,int y,String direction){
		super(8,20,x,y);
		if(direction.equals("up")){  //Ӣ�ۻ��ӵ��ٶ�
				speed = -2;
			    dir = 0;
		}else if(direction.equals("down")){    //�л��ӵ��ٶ�
			if(World.score>=260)
			{
				speed=8;        //�ڶ��صл��ӵ��ٶ�
				dir = 1;
			}
			else {
				speed = 3;      //��һ�صл��ӵ��ٶ�
				dir = 1;
			}
		}
		
	}
	
	//�ӵ��ƶ�
	public void step() {
		y += speed;
	}
	
	/*
	 * �����ӵ�ͼƬ��Images.bullets[0]�����ϵģ�Images.bullets[1]�����µ�
	 * ��״̬ΪDEADʱ,�ı�״̬ΪREMOVE
	 */
	public BufferedImage getImage() {
		if(isLife()){
			
			if(World.score>=260)
			{
				return Images.bullets1[dir];  //���صڶ����ӵ�
			}
			else {
				return Images.bullets[dir];   //���ص�һ���ӵ�
			}
			
		}else if(isDead()){
			state = REMOVE;
			return null;
		}
		return null;
	}

	//�ж��Ƿ�Խ��
	public boolean outBround() {
		return y<=0 || y>=World.HEIGHT;
	}

}
