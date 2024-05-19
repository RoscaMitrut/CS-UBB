#include <iostream>
#include <fstream>
#include <string>
#include <map>

using namespace std;

ifstream fin("input_p3.txt");
ofstream fout("input_p4.txt");

void CodareHuffman(){
	struct FreqPair{
		int freq = 0;
		char chr = 0;
		bool operator<(const FreqPair& oth) const{
			return freq < oth.freq || (freq == oth.freq && chr < oth.chr);
		}
	};

	string text;
	getline(fin, text);

	if (text == "") return;

	int freq[256]{};
	int letters = 0;

	for (char chr : text){
		freq[int(chr)]++;
		freq[int(chr)] == 1 ? letters++ : 0;
	}

	multimap<FreqPair, string> Q;

	fout << letters << "\n";
	cout << letters << "\n";
	for (int i = 0; i < 256; ++i){
		if (freq[i]){
			fout << char(i) << " " << freq[i] << "\n";
			cout << char(i) << " " << freq[i] << "\n";
			Q.insert({ { freq[i], char(i) }, string(1, i) });
		}
	}

	string code[256];
	while (--letters){
		auto x = *Q.begin();
		for (char chr : x.second){
			code[int(chr)] = "0" + code[int(chr)];
		}
		Q.erase(Q.begin());

		auto y = *Q.begin();
		for (char chr : y.second){
			code[int(chr)] = "1" + code[int(chr)];
		}
		Q.erase(Q.begin());

		Q.insert({ { x.first.freq + y.first.freq, min(x.first.chr, y.first.chr) }, x.second + y.second });
	}

	for (char chr : text){
		fout << code[int(chr)];
		cout << code[int(chr)];
	}
}

int main(){
	CodareHuffman();
	return 0;
}