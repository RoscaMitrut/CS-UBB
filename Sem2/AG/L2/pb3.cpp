#include <iostream>
#include <fstream>
#include <cstring>

using namespace std;

ifstream f("labirint_1.txt");

int a[100][100];
int n, m;
int xstart, ystart, xfinish, yfinish;
char str[100];

void bordare() {
	for (int i = 0; i <= n + 1; i++)
		a[i][0] = a[i][m + 1] = -1;
	for (int i = 0; i <= m + 1; i++)
		a[0][i] = a[n + 1][i] = -1;
}

void fct_lee(int x, int y) {
	if (x == xfinish && y == yfinish) {
		return;
	}
	if (a[x + 1][y] == 0 || a[x][y] + 1 < a[x + 1][y]) {
		a[x + 1][y] = a[x][y] + 1;
		fct_lee(x + 1, y);
	}
	if (a[x - 1][y] == 0 || a[x][y] + 1 < a[x - 1][y]) {
		a[x - 1][y] = a[x][y] + 1;
		fct_lee(x - 1, y);
	}
	if (a[x][y + 1] == 0 || a[x][y] + 1 < a[x][y + 1]) {
		a[x][y + 1] = a[x][y] + 1;
		fct_lee(x, y + 1);
	}
	if (a[x][y - 1] == 0 || a[x][y] + 1 < a[x][y - 1]) {
		a[x][y - 1] = a[x][y] + 1;
		fct_lee(x, y - 1);
	}
}

int main() {
    while(f.getline(str,100)) {
        n++;
        m=strlen(str);
        for(int i=0;i<strlen(str);i++) {
            if(str[i]=='1')
                a[n][i+1]=-1;

            else if(str[i]==' ')
                a[n][i+1]=0;

            else if(str[i]=='S') {
                a[n][i+1]=1;
                xstart=n;
                ystart=i+1;
            }
            else {
                xfinish=n;
                yfinish=i+1;
            }
        }
    }
	bordare();
	fct_lee(xstart, ystart);

	int x = xfinish, y = yfinish;
		
	while (x != xstart || y != ystart) {
		if (a[x - 1][y] + 1 == a[x][y]) {
			a[x][y] = -2;
			x = x - 1;
		}
		else if (a[x + 1][y] + 1 == a[x][y]) {
			a[x][y] = -2;
			x++;
		}
		else if (a[x][y - 1] + 1 == a[x][y]) {
			a[x][y] = -2;
			y--;
		}
		else if (a[x][y + 1] + 1 == a[x][y]) {
			a[x][y] = -2;
			y++;
		}
	}
	a[xfinish][yfinish] = -3;
	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= m; j++) {
			if (a[i][j] == 1)
				cout << 'S';
			else if (a[i][j] == -2)
				cout << 'X';
			else if (a[i][j] == -3)
				cout << 'F';
			else cout << ' ';
		}
		cout << '\n';
	}
	return 0;
}