package cn.tedu.shoot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;


import java.io.*;

import javax.swing.JApplet;

@SuppressWarnings("deprecation")
public class World extends JPanel implements Runnable,KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//����java.applet.AudioClip���͵���������������Ƶ
	java.applet.AudioClip all_bomb,enemy_bomb,hero_bomb,hero_bullet,wi,wh,bosswaring,equip,readygo,p,zh;
	java.applet.AudioClip bg;
	public static final int WIDTH = 400;    //���ڵĿ�
	public static final int HEIGHT = 700;   //���ڵĸ�
	
	private Sky sky = new Sky();            //������������ն���
	private Hero hero = new Hero();         //����������Ӣ�ۻ�����
	
	private FlyingObject[] enemies = {};    //������������enemies������������ΪFlyingObject���෽��ʹ��
	private Bullet[] heroBullets = {};      //����Ӣ�ۻ����ӵ�����heroBullets
	private Bullet[] enemiesBullets = {};   //�������˵��ӵ�����enemiesBullets
	
	
	//�����ĸ�״̬����һ����ǰ״̬��
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	public static final int WIN = 4;
	
	private int state = START;
	
	//����ͼƬ�ྲ̬��������ͼ������ͼ����Ϸ����ͼ����ֵ
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage win;
	
	static{
		start = Images.readImage("start.png");
		pause = Images.readImage("pause.png");
		gameover = Images.readImage("gameover.png");
		win = Images.readImage("win.png");
	}
	
	//�ڹ��췽����Ϊ����Ƶ��ֵ
	public World(){
		try{
			all_bomb = JApplet.newAudioClip(new File("music/all_bomb.wav").toURI().toURL());
			enemy_bomb = JApplet.newAudioClip(new File("music/enemy_bomb.wav").toURI().toURL());
			bg = JApplet.newAudioClip(new File("music/bg.wav").toURI().toURL());
			hero_bomb = JApplet.newAudioClip(new File("music/hero_bomb.wav").toURI().toURL());
			hero_bullet = JApplet.newAudioClip(new File("music/hero_bomb.wav").toURI().toURL());
			
			wi= JApplet.newAudioClip(new File("music/wi.wav").toURI().toURL()); //Ӯ������
			wh= JApplet.newAudioClip(new File("music/wh.wav").toURI().toURL()); // ����
			bosswaring= JApplet.newAudioClip(new File("music/bosswaring.wav").toURI().toURL()); //boss��ʾ����
			equip= JApplet.newAudioClip(new File("music/equip.wav").toURI().toURL());  //������Ӯ���л���
			readygo= JApplet.newAudioClip(new File("music/readygo.wav").toURI().toURL());   //�տ�ʼreadygo����
			p= JApplet.newAudioClip(new File("music/p.wav").toURI().toURL());         //��ͣ����
			zh= JApplet.newAudioClip(new File("music/zh.wav").toURI().toURL());       //��ͣ��������
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	///���ɵ���
	//������ɴ�л���С�л��������Ķ���
	public FlyingObject nextOne(){
			int n = (int)(Math.random()*100);
		if(n>70){
			return new Bee();
		}else if(n>40){
			return new BigAirplane();
		}else{
			return new Airplane();
		}
	  }
		
	/*
	*ÿ��һ��ʱ�����nextOne��������һ��������ӵ��������鵱��
	*����������100�������һ��ʱ������boss����ӵ���������
	*/
	private int enemiesIndex = 0;
	public void enterAction(){
		enemiesIndex++;
		if(enemiesIndex%30==0){
			enemies = Arrays.copyOf(enemies, enemies.length+1); 
			enemies[enemies.length-1] = nextOne(); 
		}
		if(enemiesIndex%1000==0 && score>100){     //100
			if(ckt==1) {
				bosswaring.play();     //boss��ʾ��
			}
				enemies = Arrays.copyOf(enemies, enemies.length+1);
			    enemies[enemies.length-1] = new BossAirplane();	
		}
	}
	 
	///���˷����ӵ�
	//ÿ��һ��ʱ��������˵���������ÿһ���������鷢���ӵ���ӵ������ӵ�����
	private int enemiesShootIndex = 0;
	public void enemiesShoot(){
		enemiesShootIndex++;
		if(enemiesShootIndex%100==0){
			for(int i=0;i<enemies.length;i++){   //������������
				FlyingObject f = enemies[i];
				//���˻������벻������ʱ�����ӵ�
				if(f.isLife() && !(f instanceof Bee)){
					//���˵���shoot���������ӵ�����
					Bullet[] b = f.shoot();
					//�����ɵ��ӵ�������ӵ������ӵ�����
					enemiesBullets = Arrays.copyOf(enemiesBullets, enemiesBullets.length+b.length);
					System.arraycopy(b, 0, enemiesBullets, enemiesBullets.length-b.length,b.length);
				}
			}
		}
	}
	
	///Ӣ�ۻ������ӵ�
	private int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		if(shootIndex%40==0){
			//Ӣ�ۻ�����shoot���������ӵ�����
			Bullet[] b = hero.shoot();
			//���ӵ�������ӵ�Ӣ�ۻ��ӵ�������
			heroBullets = Arrays.copyOf(heroBullets, heroBullets.length+b.length);
			System.arraycopy(b, 0, heroBullets, heroBullets.length-b.length,b.length);
		}
	}
	
	///���󶼶�����
	public void stepAction(){
		sky.step();      //��ն�����
		//���˶�����
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		//Ӣ�ۻ��ӵ�������
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].step();
		}
		//�����ӵ�������
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].step();
		}
	}
	
	///�ж�Ӣ�ۻ�������ӵ��Ƿ���е���
	static int score = 0;         //��ʼ������Ϊ0
	public void bangAction(){
		//������������
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			//����Ӣ�ۻ��ӵ�����
			for(int j=0;j<heroBullets.length;j++){
				Bullet b = heroBullets[j];
				//�жϵ��˺��ӵ���״̬�Ƿ�ΪLIFE���ӵ�����hit�����ж��Ƿ�����ײ
				if(b.isLife() && f.isLife() && b.hit(f)){
					if(f.life>1){
						//������˵���������1������һ���ӵ�״̬�ı�ΪDEAD
						f.subtractLife();
						b.goDead();
					}else{
						//���ű�����Ч
						enemy_bomb.play();
						//�����˵�����������һʱ�ı��ӵ��͵��˵�״̬ΪDEAD
						f.goDead();
						b.goDead();
						if(f instanceof Enemy){
							//�����ʵ���˼ӷֵĽӿ����score�ӷ�
							Enemy e = (Enemy) f;
							score += e.getScore();
						}
						if(f instanceof Award){
							//����Ǽӽ�������Ӷ�Ӧ�Ľ���
							Award a = (Award) f;
							int type = a.getType();
							switch(type){
							case Award.DOUBLE_FIRE:
								hero.addDoubleFire();
								break;
							case Award.LIFE:
								hero.addLife();
								break;
							}
						}
					}
				}
			}
		}
	}

	///���״̬ΪREMOVE�ͳ������ڵĵ��˺��ӵ�
	//��ֹ������෢���ڴ�й©
	public void outOfBoundsAction(){
		//����һ��FlyingObject������������ûREMOVE��û�������ڵĵ���
		int index = 0;
		FlyingObject[] fs = new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++){
			//���!f.isRemove() && !f.outBround()��洢��fs��
			FlyingObject f = enemies[i];
			if(!f.isRemove() && !f.outBround()){
				fs[index++] = f;
			}
		}
		//������������Ϊfs�е�Ԫ��
		enemies = Arrays.copyOf(fs, index);
		
		//���������ͬ��
		index = 0;
		Bullet[] bs = new Bullet[heroBullets.length];
		for(int i=0;i<heroBullets.length;i++){
			Bullet b = heroBullets[i];
			if(!b.isRemove() && !b.outBround()){
				bs[index++] = b;
			}
		}
		heroBullets = Arrays.copyOf(bs, index);
		
		index = 0;
		Bullet[] ebs = new Bullet[enemiesBullets.length];
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			if(!b.isRemove() && !b.outBround()){
				ebs[index++] = b;
			}
		}
		enemiesBullets = Arrays.copyOf(ebs, index);
	}
	
	///�жϵ��˵��ӵ��Ƿ����Ӣ�ۻ���Ӣ�ۻ��Ƿ��������ײ
	public void hitAction(){
		//������������
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			//���˺�Ӣ�ۻ��������ţ�Ӣ�ۻ�����hit���������ײ
			if(f.isLife() && hero.isLife() && hero.hit(f)){
				//��ײ��ı����״̬ΪDEAD
				f.goDead();
				//������ײ��Ч
				hero_bullet.play();
				//Ӣ�ۻ���������ջ���
				hero.clearDoubleFire();
				hero.subtractLife();
			}
		}
		
		///���������ӵ�����
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			//���Ӣ�ۻ���״̬ΪLIFE������hit�����ж��Ƿ�����ײ
			if(b.hit((FlyingObject)hero) && b.isLife()){
				//��ײ��ı��ӵ���״̬ΪDEAD
				b.goDead();
				//������ײ��Ч
				hero_bullet.play();
				//Ӣ�ۻ���������ջ���
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	//Ӣ�ۻ�Ӯ�˺�û������
	public void checkGameOverAction(){
		if(score>=400) {     //�������ڡ�400��Ӣ�ۻ�ʤ��
			wi.play();
			state = WIN;
		}
		if(hero.getLife()==0){     //Ӣ�ۻ�û������
			hero_bomb.play();
			wh.play();
			state = GAME_OVER;
		}
	}
	
	///��Ϸ���з���
	public void action(){
		//������������¼������ڲ���
		MouseAdapter l = new MouseAdapter(){
			//����ƶ��¼�
			public void mouseMoved(MouseEvent e){
				//״̬ΪRUNNINGʱ��ȡ������꣬Ӣ�ۻ�����moveTo������Ӣ�ۻ���������ƶ�
				if(state==RUNNING){
					int x=e.getX();
					int y=e.getY();
					hero.moveTo(x, y);
				}
			}
			//��굥���¼�
			public void mouseClicked(MouseEvent e){
				
				//״̬ΪSTARTʱ����״̬�ı�ΪRUNNING���Ҳ��ű�������
				if(state==START){
					readygo.play();
					//bg.loopΪѭ����������
					 bg.loop();
					state = RUNNING;
				}
				else if(state==GAME_OVER){
					//״̬ΪGAME_OVERʱ�ı�״̬ΪSTART
					//all_bomb.play();
					equip.play();
					state = START;
					//��������Ϊ0
					score = 0;
					rt = 5;          //���д�������
					ckt =1;          //�ؿ�����
					//�������ж���Ϊ��һ����Ϸ��׼��
					enemies = new FlyingObject[0];
					heroBullets = new Bullet[0];
					enemiesBullets = new Bullet[0];
					hero = new Hero();
				}
				
				else {       
					//״̬ΪWINʱ�ı�״̬ΪSTART
					equip.play();
					state = START;
					//��������Ϊ0
					score = 0;
					rt = 5;       //���д�������
					ckt =1;       //�ؿ�����
					//�������ж���Ϊ��һ����Ϸ��׼��
					enemies = new FlyingObject[0];
					heroBullets = new Bullet[0];
					enemiesBullets = new Bullet[0];
					hero = new Hero();
				}
				
			}
			//����Ƴ������¼�
			public void mouseExited(MouseEvent e){
				//��״̬ΪRUNNINGʱ�ı�״̬ΪPAUSE
				if(state==RUNNING){
					state = PAUSE;
					//ֹͣ�������ֲ���
				     bg.stop();
				     p.play();    //������ͣ��Ч
				}
			}
			//�����봰���¼�
			public void mouseEntered(MouseEvent e){
				//���״̬ΪPAUSE�ı�״̬ΪRUNNING�����ű�������
				if(state==PAUSE){
					bg.loop();
					zh.play();    //���ſ�ʼ��Ч
					state = RUNNING;
					
				}
			}
		};
		
		//Ϊ�������������Java�ļ���
			this.addMouseListener(l);
		    this.addMouseMotionListener(l);
		
		//������ʱ����ʱ��������ĸ��ַ����ó�������
		Timer timer = new Timer();
		//intervel Ϊ������ʱ���Ժ�����ʱ�������Ժ���Ϊ��λ
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run() {
				//���״̬ΪRUNNINGʱ��ʼ����
				if(state==RUNNING){
					enterAction();
					enemiesShoot();
					shootAction();
					stepAction();
					bangAction();
					outOfBoundsAction();
					hitAction();
					checkGameOverAction();
				}
				//ÿ�ζ��ػ���֤ˢ����
				repaint();
			}
		}, intervel,intervel);
	}
	
	///�����޵д�����
	//����ʵ����������̵Ľӿ�KeyListener����д����ʱ��
	//@Override	
	 int rt = 5;  //��ʼ�����д��� 
	public void keyPressed(KeyEvent e) {
		//��״̬ΪRUNNINGʱ
		if(state==RUNNING){
			
			if(rt!=0) {
			//�ж��Ƿ��¿ո��
			switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE:
				//���¿ո���󲥷ű�ը��Чͬʱ������еط�����
				all_bomb.play();
				enemies = new FlyingObject[0];
				enemiesBullets = new Bullet[0];
				break;
			}
			rt=rt-1;
		}
	}
		
}
	//�ؿ��ж�
	 int ckt =1;
	 public  int ChecKpoints(){
		if(score>=260) {              //������180����ڶ���
			ckt = 2;
		}
		return ckt;
	}
	 
	///�ڻ����ϻ������ж���
	public void paint(Graphics g){
		sky.paintObject(g);       //�����
		hero.paintObject(g);      //��Ӣ�ۻ�
		//������
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintObject(g);
		}
		//��Ӣ�ۻ��ӵ�
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].paintObject(g);
		}
		//�������ӵ�����
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].paintObject(g);
		}
		//�����÷ֺ�����
		g.setFont(new Font("����",Font.BOLD,20));      //�޸�����
		g.setColor(Color.WHITE);                         //�޸�������ɫ
		
		g.drawString("����ʣ�����:"+rt,10,25); // 10 660
		g.drawString("����ֵ:"+hero.getDoubleFire(),10,45);
		g.drawString("����ֵ:"+hero.getLife(), 10, 65);// 10 45
		g.drawString("�ؿ�:"+ChecKpoints(), 10, 85);
		g.drawString("����:"+score, 10, 105);  // 10 25
		
		//��Ϸ���ڲ�ͬ״̬������ͬ״̬ͼ
		switch(state){
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0,null);
			break;
	    case WIN:
			g.drawImage(win, 0, 0, null);
			break;
		}
	}
	
	///ʵ���߳�����
	/*��Ϊ������Ч���������򣬶�ʹ��java.applet.AudioClip����Ҫ���߳�����ܲ��ų�����
           ���������ʵ����Runnable�ӿڣ���дrun�������г���*/
	@Override
	public void run() {
		action();
	}
	
	public static void main(String[] args) {
		World world = new World();       //����World����World��̳��˻�����JPanel
		JFrame frame = new JFrame("����ս��");   //�������ڶ���
		
		frame.add(world);      //��������ӵ�������ȥ
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //���ô���Ĭ�Ϲرղ���
		frame.setSize(WIDTH, HEIGHT);     //���ô��ڴ�С
		frame.setLocationRelativeTo(null); 
		frame.setIconImage(Images.tb);  //���ô���ͼ��
		frame.setVisible(true);         //���ô��ڿɼ�
		frame.addKeyListener(world);    //��Ӽ��̼���
		Thread t=new Thread(world);     //�����̶߳���
		t.start();             	//�����߳�
		//world.action();
		System.out.println("����ս����ʼ��!!!");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
