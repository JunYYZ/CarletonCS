#include "Search.h"

bool H_Search::matches(const Episode *episode) const
{
    return episode->getHost() == host;
}

void H_Search::print(std::ostream &ost) const
{
    ost << "Matches episodes with host: " << host;
}

bool C_Search::matches(const Episode *episode) const
{
    return episode->getCategory() == category;
}

void C_Search::print(std::ostream &ost) const
{
    ost << "Matches episodes with category: " << category;
}

bool HorC_Search::matches(const Episode *episode) const
{
    return H_Search::matches(episode) || C_Search::matches(episode);
}

void HorC_Search::print(std::ostream &ost) const
{
    ost << "Matches episodes with host: " << host << " or category: " << category;
}
