package cn.tedu.shoot;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;

/*
 * ����ͼƬ���࣬ʵ���˽����������ͼƬ���ص�������������ÿ�ζ�Ҫȥ��ȡ
 */
public class Images {
	public static BufferedImage sky;    //��һ�����ͼƬ
	public static BufferedImage sky1;   //�ڶ�������ͼƬ
	
	public static BufferedImage[] bullets;  //��һ���ӵ�ͼ
	public static BufferedImage[] bullets1; //�ڶ��ӵ�ͼ
	
	public static BufferedImage[] bossairplanes;
	
	public static BufferedImage[] heros;      //��һ�ַɻ�
	public static BufferedImage[] heros1;     //�ڶ��ַɻ� 
	
	public static BufferedImage[] airplanes;
	public static BufferedImage[] bigairplanes;
	public static BufferedImage[] bees;
	
	public static BufferedImage tb;     //ͼ��
	
	static{
		
		//����ͼƬ�ļ���
		sky = readImage("background1.png");  //���
		sky1=readImage("bg2.png");           //����
		
		tb = readImage("tb.png");            //ͼ��
		
		//Ӣ�ۻ�ͼƬ�ļ���
		heros = new BufferedImage[2];        //��һ��ɻ�ͼƬ
		heros[0] = readImage("hero0.png");
		heros[1] = readImage("hero1.png");
		
		heros1 = new BufferedImage[2];       //�ڶ���ɻ�ͼƬ
		heros1[0]=readImage("hero2.png");
		heros1[1]=readImage("hero3.png");
		
		//�ӵ�ͼƬ�ļ���
		bullets = new BufferedImage[2];        //��һ���ӵ�
		bullets[0] = readImage("bullet0.png");
		bullets[1] = readImage("bullet1.png");
		
		bullets1 = new BufferedImage[2];       //�ڶ����ӵ�
		bullets1[0] = readImage("bullet2.png");
		bullets1[1] = readImage("bullet3.png");
		
		//boss��ͼƬ�ļ���
		bossairplanes = new BufferedImage[5];
		bossairplanes[0] = readImage("boss.png");
		
		//С�л�ͼƬ�ļ���
		airplanes = new BufferedImage[5];
		airplanes[0] = readImage("airplane0.png");
		
		//��л��ļ���
		bigairplanes = new BufferedImage[5];
		bigairplanes[0] = readImage("bigairplane0.png");
		
		//����ͼƬ�ļ���
		bees = new BufferedImage[5];
		bees[0] = readImage("bee0.png");
		
		//����ͼƬ�ļ���
		for(int i=1;i<5;i++){
			bees[i] = readImage("bom"+i+".png");
			airplanes[i] = readImage("bom"+i+".png");
			bigairplanes[i] = readImage("bom"+i+".png");
			bossairplanes[i] = readImage("bom"+i+".png");
		}
		
	}
	
	//��ȡͼƬ���ڴ�
	public static BufferedImage readImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
