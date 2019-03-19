#include <bits/stdc++.h>

using namespace std;

int num_test_cases = 300000;
float perc_add_bin  = 0.1;
int cap_lim = 1000000000;


void print(set <pair<int, int> > bins){
	for(auto itr = bins.begin(); itr!= bins.end(); ++itr){
		cout << itr->first << " " << itr->second << ", " ; 
	}
	cout << endl;
	cout << endl;
}

int main(int argc, char ** argv){
	srand (time(NULL));
	int num_add_bin = perc_add_bin * num_test_cases;
	ofstream myfile;
  	myfile.open(argv[1]);
	
	int bin_id_counter = 0;
	set <pair<int , int> > bin_set;
	vector <int> curr_cap;
	vector <set<pair<int,int> > > bin_obj_set_vector;
	int obj_id_counter = 0;
	vector <pair<int , int> > obj_set;  
	set <int> curr_cap_set;

	int last_query = 4;

	for(int i=0;i<num_add_bin;i++){
		cout << 1 << " ";
		int capacity = rand() % cap_lim + 1;
		while(curr_cap_set.find(capacity)!=curr_cap_set.end()){
			capacity = rand() % cap_lim + 1;
		}

		cout << bin_id_counter << " " << capacity << endl;
		bin_set.insert(make_pair(capacity, bin_id_counter));
		curr_cap.push_back(capacity);
		set<pair<int,int> > bin_obj_set;
		bin_obj_set_vector.push_back(bin_obj_set);
		bin_id_counter +=1;
		curr_cap_set.insert(capacity);

		// print(bin_set);
	}
	for(int i=num_add_bin; i<num_test_cases;i++){
		if(i%6==0){
			cout << 1 << " ";
			int capacity = rand() % cap_lim + 1;
			while(curr_cap_set.find(capacity)!=curr_cap_set.end()){
				capacity = rand() % cap_lim + 1;
			}
			cout << bin_id_counter << " " << capacity << endl;
			bin_set.insert(make_pair(capacity, bin_id_counter));
			curr_cap.push_back(capacity);
			set<pair<int,int> > bin_obj_set;
			bin_obj_set_vector.push_back(bin_obj_set);
			bin_id_counter +=1;	
			curr_cap_set.insert(capacity);
		}
		else if(i%6==1 || i%6==2 || i%6==3){
			auto itr = bin_set.rbegin();
			int highest_cap = itr->first;
			int highest_cap_ind = itr->second;
			if(highest_cap==0){
				continue;
			}
			cout  << 2 << " ";
			int obj_size = rand() % highest_cap + 1;
			int remainder = highest_cap - obj_size;
			while(curr_cap_set.find(remainder)!=curr_cap_set.end()){
				obj_size = rand() % highest_cap + 1;
				remainder = highest_cap - obj_size;
			}
			cout << obj_id_counter << " " << obj_size << endl;
			obj_set.push_back(make_pair(highest_cap_ind, obj_size));
			myfile << highest_cap_ind << endl;
			bin_set.erase(make_pair(curr_cap[highest_cap_ind], highest_cap_ind));
			bin_set.insert(make_pair(curr_cap[highest_cap_ind] - obj_size, highest_cap_ind));
			curr_cap_set.erase(curr_cap[highest_cap_ind]);
			curr_cap_set.insert(curr_cap[highest_cap_ind] - obj_size);
			curr_cap[highest_cap_ind] -= obj_size;
			bin_obj_set_vector[highest_cap_ind].insert(make_pair(obj_id_counter, obj_size));
			obj_id_counter +=1;
			last_query = 2;
		}
		else if(i%6==4){
			if(obj_id_counter<=1){
				continue;
			}
			cout << 3 << " ";
			int highest_obj = obj_id_counter - 1;
			int obj_id = rand() % highest_obj;
			int obj_bin =  obj_set[obj_id].first;
			int obj_size = obj_set[obj_id].second;
			int new_remaining_cap = -1;
			if(obj_bin!=-1){
				new_remaining_cap = curr_cap[obj_bin] + obj_size;
			}
			while(obj_bin==-1 || curr_cap_set.find(new_remaining_cap)!=curr_cap_set.end()){
				obj_id = rand() % highest_obj;
				obj_bin =  obj_set[obj_id].first;
				obj_size = obj_set[obj_id].second;
				new_remaining_cap = -1;
				if(obj_bin!=-1){
					new_remaining_cap = curr_cap[obj_bin] + obj_size;
				}	
			}
			cout << obj_id << endl;
			obj_set[obj_id] = make_pair(-1,-1);
			
			myfile << obj_bin <<  endl;
			bin_set.erase(make_pair(curr_cap[obj_bin], obj_bin));
			bin_set.insert(make_pair(curr_cap[obj_bin] + obj_size, obj_bin));
			curr_cap_set.erase(curr_cap[obj_bin]);
			curr_cap_set.insert(curr_cap[obj_bin] + obj_size);
			curr_cap[obj_bin] += obj_size;
			bin_obj_set_vector[obj_bin].erase(make_pair(obj_id, obj_size));
			last_query = 3;
		}	
		else{
			if(bin_id_counter<=1 || last_query==4){
				continue;
			}
			int bin_id = rand() % (bin_id_counter - 1);
			cout << 4 << " " << bin_id << endl;
			for(auto itr = bin_obj_set_vector[bin_id].begin(); itr!=bin_obj_set_vector[bin_id].end(); ++itr){
				myfile << itr->first << " " << itr->second << endl;
			}
			last_query = 4;
		}
		// print(bin_set);

		fflush(stdout);
	}


	return 0;
}