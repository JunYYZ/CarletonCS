#include <istream>
#include <string>
#include <sstream>

#include "defs.h"
#include "Episode.h"
#include "Search.h"


using namespace std;

int testH_Search();
int testC_Search();
int testHorC_Search();
int testPolyPrint();

bool match(Search*, Episode*, bool);

int main(){

    int choice = 0;
    cin >> choice;
    switch(choice){
      case 0: return testH_Search();
      case 1: return testC_Search();
      case 2: return testHorC_Search(); 
      case 3: return testPolyPrint();
    }

    cout<<"Bad input: "<<choice<<endl;
    return 1;
  
}

bool match(Search* search, Episode* episode, bool matches){
    cout<<"Attempting to match:"<<endl<<endl;
    cout<<*search<<endl<<"with"<<endl<<endl<<*episode<<endl;
    cout <<"Should "<<(matches?"match":"not match")<<endl;
    bool out = search->matches(episode);
    cout << (out?"Matched":"Did not match")<<endl;
    if (out==matches){
        cout<<"Test passed"<<endl<<endl;
        return true;
    }else{
        cout<< " *****ERROR***** "<<endl;
        return false;
    }
}

int testH_Search(){
    int ret = 0;
    string host1 = "Bob";
    string host2 = "Alice";
    Search* search1 = new H_Search(host1);
    Search* search2 = new H_Search(host2);

    Episode episode("Bob Talk", "Bob","The one where bob talks", "talk", "I'm Bob and this is me talking", "no video");

    //if both tests pass, return 0 which means no errors occured
    if (match(search1, &episode, true)&&match(search2, &episode, false)) {
        cout<<"Testing host Search passed"<<endl<<endl;
    }else{
        cout<<"Testing host Search DID NOT pass"<<endl<<endl;
        ret = 1;
    }
    delete search1, search2;
    
    return ret;
    
}

int testC_Search(){
    int ret = 0;
    Search* search1 = new C_Search("Talk");
    Search* search2 = new C_Search("People");
    Episode episode("Bob Talk", "Bob","The one where bob talks", "Talk", "I'm Bob and this is me talking", "no video");

    //if both tests pass, return 0 which means no errors occured
    if (match(search1, &episode, true)&&match(search2, &episode , false))  {
        cout<<"Testing category Search passed"<<endl<<endl;
    }else{
        cout<<"Testing category Search DID NOT pass"<<endl<<endl;
        ret = 1;
    }
    delete search1, search2;
    return ret;
}

int testHorC_Search(){

    Search* search1 = new HorC_Search("Bob", "Animal");
    Search* search2 = new HorC_Search("Alice", "Animal");
    Search* search3 = new HorC_Search("Cindy", "Talk");
    Search* search4 = new HorC_Search("Alice", "People");
    Search* search5 = new HorC_Search("Bob", "Talk");

    //April fool's day
    Episode episode("Bob Talk", "Bob","The one where bob talks", "Talk", "I'm Bob and this is me talking", "no video");
    //check each test for error individually
    bool pass;
    pass = match(search1, &episode, true);
    pass = match(search2, &episode, false)&&pass;
    pass = match(search3, &episode, true)&&pass;
    pass = match(search4, &episode, false)&&pass;
    pass = match(search5, &episode, true)&&pass;

    delete search1, search2, search3, search4, search5;
    if (!pass){
        cout<<"Testing host or category Search DID NOT pass"<<endl<<endl;
        //returning 1 indicates an error stat
        return 1;
    }

    cout<<"Testing host or category Search passed"<<endl<<endl;
    return 0;

}

int testPolyPrint(){
    string p1 = "Bob";
    string p2 = "Alice";
    string cat1 = "Animal";
    string cat2 = "People";
    Search* search1 = new H_Search(p1); 
    Search* search2 = new C_Search(cat1);
    Search* search3 = new HorC_Search(p2, cat2);


    stringstream ss;
    
    ss<<*search1;
    cout<<*search1<<endl;
    if (ss.str().find(p1)==string::npos){
        cout<<"Could not find "<<p1<< " in H_Search output"<<endl;
        delete search1, search2, search3;
        return 1;
    }


    cout<<p1<<" found in output, test passed"<<endl<<endl;

    //reset the string stream
    ss.str(string());

    ss<<*search2;
    cout<<*search2<<endl;

    if (ss.str().find("Animal")==string::npos){
        cout<<"Could not find Animal in C_Search output"<<endl<<endl;
        delete search1, search2, search3;
        return 1;
    }

    cout<<cat1<<" found in output, test passed"<<endl<<endl;

    ss.str(string());
    ss<<*search3;
    cout<<"Do you see the 'or' on the previous line? Stringstream bug? Can't seem to shake it."<<endl<<endl;

    cout<<*search3<<endl<<endl;

    if (ss.str().find(p2)==string::npos){
        cout<<"Could not find "<<p2<<" in HorC_Search output"<<endl;
        delete search1, search2, search3;
        return 1;
    }

    if (ss.str().find(cat2)==string::npos){
        cout<<"Could not find "<<cat2<<" in HorC_Search output"<<endl;
        delete search1, search2, search3;
        return 1;
    }


    cout<<p2<<" and "<<cat2<<" found in output, test passed"<<endl<<endl;
    delete search1, search2, search3;
    return 0;
}

