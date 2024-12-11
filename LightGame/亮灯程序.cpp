#include <graphics.h>
#include<stdio.h>
#include<windows.h>
#include<conio.h>
#include<time.h>
#include<stdlib.h>
#include<conio.h>
#include<string.h>
#include<math.h>
#include<mmsystem.h>
#pragma comment(lib,"winmm.lib")//引用需要的头文件
int n, p,len;//n为灯的行列大小，p为最小点击数，len为代表灯的小格子边长
int a[1000][1000];//a表示灯是否被点击
int b[1000][1000];//b表示灯亮灭的状态
int c[1000][1000];//c用来存储答案

void Menu();
void introduce();
void Sett();
void se();
void solve();
void kik();
void ma(int x);
void Show();
void Draw();

//--------------------------------------------------------计算最小点击数-------------------------------------------------------------------//
void kik(int i, int j) {//点击变化b
	b[i][j] = fabs(b[i][j] - 1);
	if (0 <= i && i < n && 0 <= j + 1 && j + 1 < n) b[i][j + 1] = fabs(b[i][j + 1] - 1);
	if (0 <= i && i < n && 0 <= j - 1 && j - 1 < n) b[i][j - 1] = fabs(b[i][j - 1] - 1);
	if (0 <= i + 1 && i + 1 < n && 0 <= j && j < n) b[i + 1][j] = fabs(b[i + 1][j] - 1);
	if (0 <= i - 1 && i - 1 < n && 0 <= j && j < n) b[i - 1][j] = fabs(b[i - 1][j] - 1);
}
void solve() {
	int q = 0;
	for (int i = 0; i < n; i++) {//初始化b为全灭
		for (int j = 0; j < n; j++) {
			b[i][j] = 0;
		}
	}
	for (int i = 1; i < n; i++) {//把a除了第一行都初始化为不点击
		for (int j = 0; j < n; j++) {
			a[i][j] = 0;
		}
	}
	for (int i = 0; i < n; i++) {//进行第一行点击
		if (a[0][i] == 1) {
			q++;//点击数++
			kik(0, i);
		}
	}
	for (int i = 1; i < n; i++) {//看后面的行，如果一个格子上面是灭的，点击它
		for (int j = 0; j < n; j++) {
			if (b[i - 1][j] == 0) {
				a[i][j] = 1;
				q++;
				kik(i, j);
			}
		}
	}
	int num = 0;
	for (int i = 0; i < n; i++) {//看一下最后一行，按我们的做法只要最后一行全亮，就可以
		if (b[n - 1][i] == 1) num++;
	}

	if (num == n) {//如果最后一行全亮
		if (q < p) {//并且点击数小于当前答案，更新答案
			p = q;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					c[i][j] = a[i][j];
				}
			}
		}
	}
}
void se(int x) {
	if (x == n) {//如果x==n，判断是否需要更新答案
		solve();
		return;
	}
	a[0][x] = 0;//设置该位置为0
	se(x + 1);//设置下一位
	a[0][x] = 1;//设置该位置为一
	se(x + 1);//设置下一位置
}

void ma(int x){
    n = x;
    p = 100000000;//初始化最小点击数为一个很大的数
    for (int i = 0; i < n; i++) {//先初始化a为全部不被点击
        for (int j = 0; j < n; j++) {
            a[i][j] = 0;
        }
    }
    se(0);//从0进入递归设置第一行
}

//----------------------------------------------------------------显示步骤----------------------------------------------------------------------//
void Draw(int n){//绘制灯的矩阵
    len = 574 / n;//这里是每个小格子的边长
    for(int i = 0;i < n;i++){
        for(int j = 0;j < n;j++){
            if(b[i][j] == 0){//如果灯是的，就绘制一个棕色的格子代表灯是灭的
                //printf("BROWN\n");
                setfillcolor(BROWN);
            }
            else if(b[i][j] == 1){//如果灯是亮的，就绘制一个蓝色的格子代表灯是亮的
                setfillcolor(BLUE);
                //printf("BLUE\n");
            }
            int x1,y1,x2,y2;
            x1 = 235 + (len * j),y1 = 26 + (len * i),x2 = x1 + len,y2 = y1 + len;//这里用来绘制标记，在需要点击的格子中心画一个红色的圆
            fillrectangle(x1,y1,x2,y2);
            if(c[i][j] == 1){
                setfillcolor(RED);
                fillcircle(x1 + len / 2,y1 + len / 2,len / 10);
            }
        }
    }
}


void Show(){
    char a[4];
    itoa(p,a,10);
    for(int i = 0;i < n;i++){
        for(int j = 0;j < n;j++) b[i][j] = 0;
    }//先初始化所有的灯都是灭的
    BeginBatchDraw();//绘制一些背景及按钮
    cleardevice();
    SetWindowText(GetHWnd(), "点灯程序");
    IMAGE picture;
    loadimage(&picture, "4.png");
    putimage(0, 0, &picture);
    setbkmode(TRANSPARENT);
    settextcolor(BLACK);
    settextstyle(55, 0, "楷体");
    outtextxy(698, 617, "结束程序");
    outtextxy(69, 617, "再来一次");
    Draw(n);
    settextstyle(22, 0, "宋体");
    outtextxy(0,0,"最少点击数：");
    outtextxy(118,0,a);
    FlushBatchDraw();
    ExMessage m;
    while (1) {
        m = getmessage(EX_MOUSE);//不断获取鼠标位置
        if (m.message == WM_MOUSEMOVE) {//如果鼠标在移动
            if (m.x >= 73 && m.x <= 282 && m.y >= 626 && m.y <= 667) {//鼠标移动到了再来一次上，把再来一次放大形成点击效果
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "4.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(55, 0, "楷体");
                outtextxy(698, 617, "结束程序");
                settextstyle(60, 0, "楷体");
                outtextxy(60, 617, "再来一次");
                settextstyle(22, 0, "宋体");
                outtextxy(0,0,"最少点击数：");
                outtextxy(118,0,a);
                Draw(n);
                FlushBatchDraw();
            }
            else if (m.x >= 707 && m.x <= 907 && m.y >= 626 && m.y <= 661) {//鼠标移动到了结束程序上，把再来一次放大形成点击效果
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "4.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(66, 0, "楷体");
                outtextxy(689, 617, "结束程序");
                settextstyle(55, 0, "楷体");
                outtextxy(69, 617, "再来一次");
                settextstyle(22, 0, "宋体");
                outtextxy(0,0,"最少点击数：");
                outtextxy(118,0,a);
                Draw(n);
                FlushBatchDraw();
            }
            else {//否则打印正常大小
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "4.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(55, 0, "楷体");
                outtextxy(698, 617, "结束程序");
                outtextxy(69, 617, "再来一次");
                settextstyle(22, 0, "宋体");
                outtextxy(0,0,"最少点击数：");
                outtextxy(118,0,a);
                Draw(n);
                FlushBatchDraw();
            }
        }
        else if (m.message == WM_LBUTTONDOWN) {//如果点击了屏幕
            if (m.x >= 73 && m.x <= 282 && m.y >= 626 && m.y <= 667) {//如果点击再来一次
                Sett();//重新设置N
            }
            else if (m.x >= 707 && m.x <= 907 && m.y >= 626 && m.y <= 661) {//如果点击结束程序，结束
                closegraph();
            }
            else if(m.x >= 235 && m.x <= 809 && m.y >= 26 && m.y <= 600){//如果点击的是灯
                int j = (m.x - 235) / len,i = (m.y - 26) / len;//计算点击的行列
                c[i][j] = 0;//把标记擦掉
                b[i][j] = fabs(b[i][j] - 1);//灯的状态取反（注意判断存在不然会越界）
                if (0 <= i && i < n && 0 <= j + 1 && j + 1 < n) b[i][j + 1] = fabs(b[i][j + 1] - 1);
                if (0 <= i && i < n && 0 <= j - 1 && j - 1 < n) b[i][j - 1] = fabs(b[i][j - 1] - 1);
                if (0 <= i + 1 && i + 1 < n && 0 <= j && j < n) b[i + 1][j] = fabs(b[i + 1][j] - 1);
                if (0 <= i - 1 && i - 1 < n && 0 <= j && j < n) b[i - 1][j] = fabs(b[i - 1][j] - 1);
                BeginBatchDraw();//重新绘制改变后的界面
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "4.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(55, 0, "楷体");
                outtextxy(698, 617, "结束程序");
                outtextxy(69, 617, "再来一次");
                settextstyle(22, 0, "宋体");
                outtextxy(0,0,"最少点击数：");
                outtextxy(118,0,a);
                Draw(n);
                FlushBatchDraw();
            }
        }
    }
}

void Sett() {//设置n
    BeginBatchDraw();//绘制界面
    cleardevice();
    SetWindowText(GetHWnd(), "点灯程序");
    IMAGE picture;
    loadimage(&picture, "3.png");
    putimage(0, 0, &picture);
    setbkmode(TRANSPARENT);
    settextcolor(BLACK);
    settextstyle(22, 0, "宋体");
    outtextxy(250, 327, "请输入要计算的N：");
    outtextxy(426, 347, "--------------------");
    settextstyle(55, 0, "楷体");
    outtextxy(681, 522, "返回主菜单");
    FlushBatchDraw();
    ExMessage m;
    while (1) {
        m = getmessage(EX_MOUSE);//不断获取鼠标位置
        if (m.message == WM_MOUSEMOVE) {//如果鼠标在移动
            if (m.x >= 688 && m.x <= 944 && m.y >= 524 && m.y <= 572) {//鼠标移动到了返回主菜单上，把返回主菜单放大形成点击效果
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "3.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(22, 0, "宋体");
                outtextxy(250, 327, "请输入要计算的N：");
                outtextxy(426, 347, "--------------------");
                settextstyle(66, 0, "楷体");
                outtextxy(651, 522, "返回主菜单");
                FlushBatchDraw();
            }
            else {//否则按原大小打印
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "3.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(22, 0, "宋体");
                outtextxy(250, 327, "请输入要计算的N：");
                outtextxy(426, 347, "--------------------");
                settextstyle(55, 0, "楷体");
                outtextxy(681, 522, "返回主菜单");
                FlushBatchDraw();
            }
        }
        else if (m.message == WM_LBUTTONDOWN) {//如果点击屏幕
            if (m.x >= 688 && m.x <= 944 && m.y >= 524 && m.y <= 572) {//如果点击返回主菜单，返回主菜单
                Menu();
            }
            else if (m.x >= 427 && m.x <= 645 && m.y >= 329 && m.y <= 354) {//如果点击横线，弹出窗口获取N
                char c[3];
                InputBox(c, 3, "请输入N：");
                int x = atoi(c);
                ma(x);//计算答案
                Show();//显示出来

            }
        }
    }


}


void introduce() {//介绍
    BeginBatchDraw();
    cleardevice();
    SetWindowText(GetHWnd(), "点灯程序");
    IMAGE picture;
    loadimage(&picture, "2.png");
    putimage(0, 0, &picture);
    setbkmode(TRANSPARENT);
    settextcolor(BLACK);
    settextstyle(22, 0, "宋体");
    outtextxy(250, 227, "有一行N行N列的灯,开始时全部是灭的");
    outtextxy(250, 277, "当你点击其中一盏灯，他及他的上下左右(若存在的话)就");
    outtextxy(250, 327, "会全部变亮.本程序可计算出使全部灯变亮的最少步数！");
    settextstyle(55, 0, "楷体");
    outtextxy(681, 522, "返回主菜单");
    FlushBatchDraw();
    ExMessage m;
    while (1) {
        m = getmessage(EX_MOUSE);
        if (m.message == WM_MOUSEMOVE) {
            //printf("%d %d\n",m.x,m.y);

            if (m.x >= 688 && m.x <= 944 && m.y >= 524 && m.y <= 572) {
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "2.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(22, 0, "宋体");
                outtextxy(250, 227, "有一行N行N列的灯,开始时全部是灭的");
                outtextxy(250, 277, "当你点击其中一盏灯，他及他的上下左右(若存在的话)就");
                outtextxy(250, 327, "会全部变亮.本程序可计算出使全部灯变亮的最少步数！");
                settextstyle(66, 0, "楷体");
                outtextxy(651, 522, "返回主菜单");
                FlushBatchDraw();
            }
            else {
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "2.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(22, 0, "宋体");
                outtextxy(250, 227, "有一行N行N列的灯,开始时全部是灭的");
                outtextxy(250, 277, "当你点击其中一盏灯，他及他的上下左右(若存在的话)就");
                outtextxy(250, 327, "会全部变亮.本程序可计算出使全部灯变亮的最少步数！");
                settextstyle(55, 0, "楷体");
                outtextxy(681, 522, "返回主菜单");
                FlushBatchDraw();
            }
        }
        else if (m.message == WM_LBUTTONDOWN) {
            if (m.x >= 688 && m.x <= 944 && m.y >= 524 && m.y <= 572) {
                Menu();
            }
        }
    }

}

void Menu() {//主菜单

    initgraph(1024, 686);
    BeginBatchDraw();
    cleardevice();
    SetWindowText(GetHWnd(), "点灯程序");
    IMAGE picture;
    loadimage(&picture, "1.png");
    putimage(0, 0, &picture);
    setbkmode(TRANSPARENT);
    settextcolor(BLACK);
    settextstyle(66, 0, "楷体");
    outtextxy(439, 214, "开始");
    settextstyle(66, 0, "楷体");
    outtextxy(379, 314, "程序介绍");
    settextstyle(50, 0, "宋体");
    FlushBatchDraw();

    ExMessage m;
    while (1) {
        m = getmessage(EX_MOUSE);
        if (m.message == WM_MOUSEMOVE) {
            //printf("%d %d\n",m.x,m.y);
            if (m.x >= 456 && m.x <= 567 && m.y >= 230 && m.y <= 267) {
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "1.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(77, 0, "楷体");
                outtextxy(430, 214, "开始");
                settextstyle(66, 0, "楷体");
                outtextxy(379, 314, "程序介绍");
                settextstyle(50, 0, "宋体");
                FlushBatchDraw();
            }
            else if (m.x >= 392 && m.x <= 630 && m.y >= 322 && m.y <= 366) {
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "1.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(66, 0, "楷体");
                outtextxy(439, 214, "开始");
                settextstyle(77, 0, "楷体");
                outtextxy(360, 314, "程序介绍");
                settextstyle(50, 0, "宋体");
                FlushBatchDraw();
            }
            else {
                BeginBatchDraw();
                cleardevice();
                SetWindowText(GetHWnd(), "点灯程序");
                IMAGE picture;
                loadimage(&picture, "1.png");
                putimage(0, 0, &picture);
                setbkmode(TRANSPARENT);
                settextcolor(BLACK);
                settextstyle(66, 0, "楷体");
                outtextxy(439, 214, "开始");
                settextstyle(66, 0, "楷体");
                outtextxy(379, 314, "程序介绍");
                settextstyle(50, 0, "宋体");
                FlushBatchDraw();
            }
        }
        else if (m.message == WM_LBUTTONDOWN) {
            if (m.x >= 456 && m.x <= 567 && m.y >= 230 && m.y <= 267) {
                Sett();
            }
            else if (m.x >= 392 && m.x <= 630 && m.y >= 322 && m.y <= 366) {
                introduce();
            }
        }
    }
    closegraph();
}

int main() {


    //mciSendString("open 3333.mp3",0,0,0);//可以播放音乐
    //mciSendString("play 2222.mp3",0,0,0);
    //mciSendString("play 3333.mp3",0,0,0);

    Menu();

    return 0;
}
