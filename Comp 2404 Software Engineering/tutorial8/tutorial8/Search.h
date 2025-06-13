#ifndef SEARCH_H
#define SEARCH_H

#include <iostream>
#include <string>
#include "Episode.h"

class Search
{
public:
    virtual ~Search() = default;
    virtual bool matches(const Episode *episode) const = 0;
    virtual void print(std::ostream &ost) const = 0;

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
    explicit H_Search(const std::string &host) : host(host) {}
    bool matches(const Episode *episode) const override;
    void print(std::ostream &ost) const override;
};

class C_Search : public virtual Search
{
protected:
    std::string category;

public:
    explicit C_Search(const std::string &category) : category(category) {}
    bool matches(const Episode *episode) const override;
    void print(std::ostream &ost) const override;
};

class HorC_Search : public H_Search, public C_Search
{
public:
    HorC_Search(const std::string &host, const std::string &category)
        : H_Search(host), C_Search(category) {}
    bool matches(const Episode *episode) const override;
    void print(std::ostream &ost) const override;
};

#endif
