package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/*
 * �����࣬ʵ���������ƶ�
 */

public class Bee extends FlyingObject implements Award {

	private int xSpeed;
	private int ySpeed;
	private int awardType;
	
	Bee(){
		super(60,51,2);
		if(World.score>=260)      //�ڶ����ٶ�
		{
			xSpeed = 2;
			ySpeed = 4;
			awardType = (int)(Math.random()*2);
		}
		else {                   //��һ���ٶ�
			xSpeed = 1;
		    ySpeed = 2;
		    awardType = (int)(Math.random()*2);
		}
		
	}
	
	//���ؽ���������
	public int getType() {
		return awardType;
	}

	//�����������߽�ʱ�ı�x������ƶ�
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if(x<=0 || x>=World.WIDTH-this.width){
			xSpeed = -xSpeed;
		}
	}

	/*
	 * ��ȡ������ͼƬ��״̬ΪLIFEʱ��������ͼƬ
	 * ״̬ΪDEADʱ����4�ű���ͼƬ��ȫ�����غ�״̬��ΪREMOVE������null
	 */
	int index = 1;
	public BufferedImage getImage() {
		if(isLife()){
			return Images.bees[0];
		}else if(isDead()){
			if(index==5){
				state = REMOVE;
				return null;
			}
			return Images.bees[index++];
		}
		return null;
	}
	
	//������y������ڴ��ڵĸ߷���ture
	public boolean outBround() {
		return y>=World.HEIGHT;
	}

}
