#include <bits/stdc++.h>
#define CHAR_SET 100
using namespace std;


string T;
string P;
vector<int> global_vector;

typedef struct trie_{
	vector<int> indices;
	trie_ *children[CHAR_SET]; 
}trie;

trie* new_node(int index){
	trie* node = (trie*)malloc(sizeof(trie));
	node->indices.push_back(index);
	for(int i=0;i<CHAR_SET;i++){
		node->children[i] = NULL;
	}
	return node;
}

void add_string(int curr_index, trie* curr_node){
	if(curr_index==T.length()){
		return;
	}
	int curr_letter = (int)T[curr_index] - (int)' ';	
	if(curr_node->children[curr_letter]==NULL){
		trie* node = new_node(curr_index);
		curr_node->children[curr_letter] = node;
	}
	else{
		curr_node->children[curr_letter]->indices.push_back(curr_index);	
	}
	add_string(curr_index+1, curr_node->children[curr_letter]);
	return;
}

trie* construct_trie(string text){
	T = text;
	trie* root = new_node(-1);
	for(int i=0;i<T.length();i++){
		add_string(i,root);
	}
	return root;
}

void dfs_match(int curr_index, trie* curr_node){
	if(curr_index==P.length()){
		for(int i=0;i<curr_node->indices.size();i++){
			global_vector.push_back(curr_node->indices[i]);
		}
		return;
	}
	if(P[curr_index] == '?'){
		for(int i=0;i<CHAR_SET;i++){
			if(curr_node->children[i]!=NULL){
				dfs_match(curr_index+1, curr_node->children[i]);
			}
		}
	}
	else{
		int curr_letter = (int)P[curr_index] - (int)' ';
		if(curr_node->children[curr_letter]!=NULL){
			dfs_match(curr_index+1, curr_node->children[curr_letter]);
		}
	}
	return;
}

vector<pair<int, int> > pattern_match_normal(string pattern, trie* root){
	P = pattern;
	global_vector.clear();
	dfs_match(0, root);
	sort(global_vector.begin(),global_vector.end());
	vector <pair <int, int> > v;
	for(int i=0;i<global_vector.size();i++){
		v.push_back(make_pair(global_vector[i]-P.length()+1, global_vector[i]));
	}	
	return v;
}
vector<pair<int, int> > pattern_match_star(string pattern, trie* root){
	int n = T.length();
	int m = pattern.length();
	int star_pos = -1;
	for(int i=0;i<m;i++){
		if(pattern[i]=='*'){
			star_pos = i;
			break;
		}
	}
	if(star_pos==-1){
		return pattern_match_normal(pattern, root);
	}
	if(star_pos==0 && m==1){
		vector<pair<int, int> > v;
		for(int i=0;i<n;i++){
			for(int j=i;j<n;j++){
				v.push_back(make_pair(i,j));
			}
		}
		return v;
	}
	if(star_pos==0){
		vector<pair <int, int> > v;
		string part1 = pattern.substr(1);
		vector<pair <int, int> > v1 = pattern_match_normal(part1, root);
		for(int i=0;i<n;i++){
			vector<pair <int, int> > :: iterator itr = upper_bound(v1.begin(), v1.end(), make_pair(i, INT_MIN));
			for(vector<pair <int, int> > :: iterator j=itr;j!=v1.end();j++){
				v.push_back(make_pair(i, j->second));
			}
		}
		return v;
	}
	if(star_pos==(m-1)){
		vector<pair <int, int> > v;
		string part1 = pattern.substr(0,m-1);
		vector<pair <int, int> > v1 = pattern_match_normal(part1, root);
		for(int i=0;i<v1.size();i++){
			int start_pos = v1[i].first;
			int end_pos = v1[i].second;
			for(int j=end_pos;j<n;j++){
				v.push_back(make_pair(start_pos, j));
			}
		}
		return v;
	}
	string part1 = pattern.substr(0, star_pos);
	string part2 = pattern.substr((star_pos+1));
	vector<pair <int, int> > v1 = pattern_match_normal(part1, root);
	vector<pair <int, int> > v2 = pattern_match_normal(part2, root);
	vector<pair <int, int> > v;
	for(int i=0;i<v1.size();i++){
		int start_pos = v1[i].first;
		int end_pos = v1[i].second;
		vector<pair <int, int> > :: iterator itr = upper_bound(v2.begin(), v2.end(), make_pair(end_pos, INT_MAX));
		for(vector<pair <int, int> > :: iterator j=itr;j!=v2.end();j++){
			v.push_back(make_pair(start_pos, j->second));
		}
	}
	return v;
}

int main(int argc, char ** argv){
	string text;
	getline(cin,text);
	// cout << text << " " << text.length() << endl;
	// int len = text.length();
	// if(text[len-1]=='\r'){
	// 	text = text.substr(0,text.length()-1);
	// 	cout << "hulara";
	// }
		// text = text.substr(0,text.length()-1);


	// cout << text << " " << text.length() << endl;
	trie* root = construct_trie(text);
	int queries;
	cin >> queries;
	for(int i=0;i<queries;i++){
		string pattern;
		cin >> pattern;
		vector <pair <int, int> > v = pattern_match_star(pattern, root);
		for(int j=0;j<v.size();j++){
			cout << v[j].first << " " << v[j].second << endl;
		}
	}

}