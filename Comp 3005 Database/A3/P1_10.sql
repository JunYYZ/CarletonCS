/*
 Problem 1.10
 Produce a table that lists the names of all the service-subscribers that subscribe to at least all the same services as Jason Allison,
 but possibly others as well. Jason Allison rents the line with portID=2.
 */
WITH JasonServices AS (
    SELECT ss.service
    FROM service_subscribers ss
    WHERE ss.portid = 2
),
SubscribersWithSameServices AS (
    SELECT s.name,
        s.portid
    FROM subscribers s
    WHERE NOT EXISTS (
            SELECT js.service
            FROM JasonServices js
            WHERE NOT EXISTS (
                    SELECT ss.service
                    FROM service_subscribers ss
                    WHERE ss.portid = s.portid
                        AND ss.service = js.service
                )
        )
)
SELECT s.name
FROM SubscribersWithSameServices swss
    JOIN subscribers s ON swss.portid = s.portid;
/*
 Test Output:
 name
 --------------
 Jason Allison
 Michael Jordan
 Joe Carter
 Homer Simpson
 Matt Stajan
 */