/*
 Problem 1.6
 Produce a table that lists the most popular service (or services).
 */
WITH ServiceCounts AS (
    SELECT ss.service,
        COUNT(ss.portid) AS subscriber_count
    FROM service_subscribers ss
    GROUP BY ss.service
)
SELECT sc.service
FROM ServiceCounts sc
WHERE sc.subscriber_count = (
        SELECT MAX(subscriber_count)
        FROM ServiceCounts
    );
/*
 Test Output:
 service
 -------
 MSG
 */