#ifndef SEARCH_H
#define SEARCH_H

#include <iostream>
#include <string>
#include "Episode.h"

using namespace std;

class Search
{
public:
    virtual bool matches(const Episode *episode) const = 0;
    virtual void print(std::ostream &ost) const = 0;
    virtual ~Search() = default;

    friend std::ostream &operator<<(std::ostream &ost, const Search &search)
    {
        search.print(ost);
        return ost;
    }
};

class H_Search : public virtual Search
{
protected:
    std::string host;

public:
    H_Search() = default;
    H_Search(const std::string &host_in) : host(host_in) {}
    bool matches(const Episode *episode) const override;
    void print(std::ostream &ost) const override;
};

class C_Search : public virtual Search
{
protected:
    std::string category;

public:
    C_Search() = default;
    C_Search(const std::string &category_in) : category(category_in) {}
    bool matches(const Episode *episode) const override;
    void print(std::ostream &ost) const override;
};

class HorC_Search : public H_Search, public C_Search
{
public:
    HorC_Search(const std::string &host_in, const std::string &category_in)
        : Search(), H_Search(host_in), C_Search(category_in) {}
    bool matches(const Episode *episode) const override;
    void print(std::ostream &ost) const override;
};

#endif // SEARCH_H
