#ifndef CATEGORY_H
#define CATEGORY_H

#include <iostream>
#include <string>


namespace cat{
    
    enum Category {rv_site, cabin, lodge, tent, trailer, misc};

    std::string categoryToString(Category c);

}
#endif