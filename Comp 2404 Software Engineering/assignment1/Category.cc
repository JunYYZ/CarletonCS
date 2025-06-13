#include "Category.h"


std::string cat::categoryToString(cat::Category c){
    switch(c){
        case rv_site: return "rv_site";
        case cabin: return "cabin";
        case lodge: return "lodge";
        case tent: return "tent";
        case trailer: return "trailer";
        case misc: return "misc";
    }
    return "unknown category";
}