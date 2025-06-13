/*
 Problem 1.9
 Write an SQL query that will produce a list of all acceptable trunks that can be used to route a call to the 416 area code, office code 334,
 and have at least one idle channel. The query should list trunks in the order of preference.
 */
SELECT tr.portid,
    tr.area,
    tr.office,
    c.state
FROM trunk_routes tr
    JOIN channels c ON tr.portid = c.portid
WHERE (
        (
            tr.area = '416'
            AND tr.office = '334'
        )
        OR (
            tr.area = '416'
            AND tr.office = '000'
        )
        OR (
            tr.area = '000'
            AND tr.office = '000'
        )
    )
    AND c.state = 'IDLE'
ORDER BY CASE
        WHEN tr.area = '416'
        AND tr.office = '334' THEN 1
        WHEN tr.area = '416'
        AND tr.office = '000' THEN 2
        WHEN tr.area = '000'
        AND tr.office = '000' THEN 3
    END;
/*
 Test Output:
 portid  area  office  state
 ------  ----  ------  -----
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   334     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 102     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 106     416   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 107     000   000     IDLE
 */