
#include "GetHub.h"

// constructor
GetHub::GetHub()
{
	repoList = new RepoList();
}

// destructor
GetHub::~GetHub()
{
	delete repoList;
}

void GetHub::addRepo(const string &repo, const string &host)
{
	if (repoList->isFull())
	{
		cout << "Repo list is full." << endl;
		return;
	}
	Repo *newRepo = new Repo(repo, host);
	repoList->add(newRepo);
}

void GetHub::deleteRepo(int repo)
{
	Repo *ch = repoList->remove(repo);
	cout << "Repo deleted:" << endl;
	ch->print();
	delete ch;
}

// void GetHub::addFile(const string& repo, const string& title, const string& content, Date& date){
// 	Repo* ch = repoList->get(repo);
// 	if (ch == nullptr){
// 		cout << "Repo not found." << endl;
// 		return;
// 	}
// 	if (!ch->addFile(title, content, date)){
// 		cout << "Repo is full - file not added." << endl;
// 		return;
// 	}
// }

void GetHub::addFile(const string &repo, const string &title, const string &content, const Date &date)
{
	Repo *ch = repoList->get(repo);
	if (ch == nullptr)
	{
		cout << "Repo not found." << endl;
		return;
	}
	if (!ch->addFile(title, content, date))
	{
		cout << "Repo is full - file not added." << endl;
		return;
	}
}

void GetHub::addFile(int repo, const string &title, const string &content, const Date &date)
{
	Repo *ch = repoList->get(repo);
	if (ch == nullptr)
	{
		cout << "Repo not found." << endl;
		return;
	}
	if (!ch->addFile(title, content, date))
	{
		cout << "Repo is full - file not added." << endl;
		return;
	}
}

void GetHub::deleteFile(int repo, int file){
	Repo* ch = repoList->get(repo);
	if (ch == nullptr){
		cout << "Repo not found." << endl;
		return;
	}
	if(!ch->removeFile(file)){
		cout << "Could not find file to remove" << endl;
		return;
	}
}

// Client services
bool GetHub::download(int repo, Repo **ch)
{
	*ch = repoList->get(repo);
	if (*ch == nullptr)
	{
		cout << "Repo not found." << endl;
		return false;
	}
	return true;
}

void GetHub::printFileContents(int repo, int file)
{
	Repo *rep = repoList->get(repo);
	if (rep == nullptr)
	{
		cout << "Repo not found." << endl;
		return;
	}
	rep->printContents(file);
}

// other
void GetHub::printRepos()
{
	cout << endl;
	cout << "GetHub" << endl;
	cout << "========" << endl;
	for (int i = 0; i < repoList->size(); ++i)
	{
		cout << i << ") ";
		repoList->get(i)->print();
	}
	cout << endl;
}
void GetHub::printRepo(int repo)
{
	Repo *ch = repoList->get(repo);
	if (ch == nullptr)
	{
		cout << "Repo not found." << endl;
		return;
	}
	ch->printFiles();
}
