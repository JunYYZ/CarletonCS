/*
 Problem 1.4
 Find the names and addresses of all subscribers who subscribe to all of the available services.
 */
SELECT s.name,
    s.address
FROM subscribers s
    JOIN service_subscribers ss ON s.portid = ss.portid
GROUP BY s.name,
    s.address
HAVING COUNT(ss.service) = (
        SELECT COUNT(*)
        FROM services
    );
/*
 Test Output:
 name | address
 ------------------
 (no output expected for the current dataset)
 */