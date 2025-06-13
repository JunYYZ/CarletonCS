#include "Search.h"

bool H_Search::matches(const Episode *episode) const
{
    return episode->getHost() == host;
}

void H_Search::print(std::ostream &ost) const
{
    ost << "H_Search: Matches episodes hosted by \"" << host << "\"";
}

bool C_Search::matches(const Episode *episode) const
{
    return episode->getCategory() == category;
}

void C_Search::print(std::ostream &ost) const
{
    ost << "C_Search: Matches episodes in the \"" << category << "\" category";
}

bool HorC_Search::matches(const Episode *episode) const
{
    return episode->getHost() == host || episode->getCategory() == category;
}

void HorC_Search::print(std::ostream &ost) const
{
    ost << "HorC_Search: Matches episodes hosted by \"" << host
        << "\" or in the \"" << category << "\" category";
}
