#include <iostream>
#include <fstream>
#include <map>
#include <string>

using namespace std;

ifstream fin("input_p4.txt");
ofstream fout("output_p4.txt");

void DecodareHuffman(){
	struct FreqPair{
		int freq = 0;
		char chr = 0;
		bool operator<(const FreqPair& oth) const{
			return freq < oth.freq || (freq == oth.freq && chr < oth.chr);
		}
	};

	multimap <FreqPair, string> Q;

	int letters, freq[256]{};
	string line;

	getline(fin, line);
	letters = stoi(line);

	for (int i = 0; i < letters; ++i){
		getline(fin, line);
		char chr = line[0];
		freq[int(chr)] = stoi(line.substr(2));
		Q.insert({ {freq[int(chr)], chr }, string(1, chr) });
	}

	string encoded;
	getline(fin, encoded);

	string text;
	map<string, char> codes;

	string code[256]{};
	while (--letters){
		auto x = *Q.begin();
		for (auto chr : x.second){
			code[int(chr)] = "0" + code[int(chr)];
		}
		Q.erase(Q.begin());

		auto y = *Q.begin();
		for (auto chr : y.second){
			code[int(chr)] = "1" + code[int(chr)];
		}
		Q.erase(Q.begin());

		Q.insert({ { x.first.freq + y.first.freq, min(x.first.chr, y.first.chr) }, x.second + y.second });
	}

	for (int i = 0; i < 256; ++i){
		if (code[i] != "\0"){
			codes.insert({ code[i], i });
		}
	}

	int last = 0, len = 0;
	string prev, chr;

	while (codes.size() > 1){
		string until = encoded.substr(last, len);
		len++;
		auto srch = codes.find(until);
		if (srch != codes.end()){
			prev = until;
			chr = srch->second;
		}
		else if (srch == codes.end() && prev != "\0"){
			text.append(chr);
			freq[int(chr[0])]--;

			if (freq[int(chr[0])] == 0){
				codes.erase(prev);
			}

			last += prev.size();
			len = 0;
			prev = "\0";
			chr = "\0";
		}
	}

	if (codes.size() == 1){
		text.append(string(1, codes.begin()->second));
	}

	fout << text;
	cout << text;
}


int main(){
	DecodareHuffman();
	return 0;
}