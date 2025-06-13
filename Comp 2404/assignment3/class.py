#!/usr/bin/python3

'''
This will generate some generic class files for you.

Usage:

python3 class.py <class_name>

<class_name> should start with a lowercase letter, otherwise capitize it how you like.
'''
import sys

if len(sys.argv)<2:
	print("no class name detected")
	exit()
	
cl = sys.argv[1]

caps = cl.upper();
title = cl[0].upper()+cl[1:]
print(title)

result = '''
#ifndef {caps}_H
#define {caps}_H

#include <iostream>
#include <string>
#include <iomanip>

using namespace std;

class {title} {{
		
	public:
		//constructor
		{title}();
		//copy constructor
		{title}({title}&);
		
		//destructor
		~{title}();
		
		void print();
	
	private:
	
}};
#endif'''.format(caps = caps,title = title)

f = open(title+".h",'w')
f.write(result)
f.close()

result = '''
#include "{title}.h"

{title}::{title}(){{


}}

{title}::{title}({title}& {cl}){{


}}


{title}::~{title}(){{


}}

void {title}::print(){{

}}'''.format(title = title, cl = cl)

f = open(title+".cc",'w')
f.write(result)
f.close()
