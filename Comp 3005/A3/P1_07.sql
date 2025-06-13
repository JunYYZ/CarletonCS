/*
 Problem 1.7
 Write an SQL query that will produce a list of all acceptable trunks that can be used to route a call to the 416 area code, office code 334.
 This query should list the trunks in the order of preference.
 */
SELECT tr.portid,
    tr.area,
    tr.office
FROM trunk_routes tr
WHERE (
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
 portid  area  office
 ------  ----  ------
 102     416   334
 102     416   000
 106     416   000
 107     000   000
 */