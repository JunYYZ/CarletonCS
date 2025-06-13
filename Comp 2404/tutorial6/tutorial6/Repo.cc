
#include "Repo.h"

Repo::Repo(const string &title, const string &owner) : title(title), owner(owner)
{
	fileList = new FileList();
}

Repo::Repo(const Repo &repo) : title(repo.title), owner(repo.owner), fileList(new FileList())
{
	for (int i = 0; i < repo.fileList->size(); ++i)
	{
		File *originalFile = repo.fileList->get(i);
		fileList->add(new File(*originalFile));
	}
}

Repo::~Repo()
{
	if (fileList)
	{
		while (fileList->size() > 0)
		{
			delete fileList->remove(0);
		}
		delete fileList;
		fileList = nullptr;
	}
}

// getter
const string &Repo::getTitle()
{
	return title;
}

const string &Repo::getOwner()
{
	return owner;
}

// other
int Repo::getNumFiles()
{
	return fileList->size();
}

bool Repo::addFile(const string &title, const string &content, Date &d)
{
	if (fileList->isFull())
		return false;
	fileList->add(new File(title, content, d));
	return true;
}

bool Repo::addFile(const string &title, const string &content, const Date &d)
{
	if (fileList->isFull())
		return false;
	fileList->add(new File(title, content, d));
	return true;
}

// File* Repo::get(int index) const{
// 	return fileList->get(index);
// }

void Repo::printContents(int file)
{
	cout << endl;
	File *f = fileList->get(file);
	if (f != nullptr)
		f->printContents();
	else
	{
		cout << "File not found" << endl;
	}
}

bool Repo::removeFile(int index)
{
	File *goner = fileList->remove(index);
	if (goner == nullptr)
		return false;
	delete goner;
	return true;
}

bool Repo::lessThan(Repo &p)
{
	return title < p.title;
}

void Repo::print()
{
	cout << "Title:   " << title << endl;
	cout << "owner:    " << owner << endl
		 << endl;
}

void Repo::printFiles()
{
	print();
	for (int i = 0; i < fileList->size(); ++i)
	{
		cout << i << ") ";
		fileList->get(i)->print();
		cout << endl;
	}
}

// void Repo::printFileContents(int index) const{
// 	if (index < 0 || index >= fileList->size()){
// 		cout << "File not found." << endl;
// 		return;
// 	};
// 	cout << endl;
// 	cout<<"Title:   "<<title<<endl;
// 	cout<<"owner:    "<<owner<<endl<<endl;
// 	fileList->get(index)->play();
// }