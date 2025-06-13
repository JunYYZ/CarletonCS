/*
 Problem 1.8
 Write an SQL query that would find if the line with phone number (613) 712-0024 is currently available to take a call because its channel is IDLE.
 */
SELECT l.portid,
    l.areacode || '-' || l.officecode || '-' || l.stationcode AS directory_number,
    c.state AS channel_state
FROM lines l
    JOIN channels c ON l.portid = c.portid
WHERE l.areacode = '613'
    AND l.officecode = '712'
    AND l.stationcode = '0024';
/*
 Test Output:
 portid  directory_number  channel_state
 ------  ----------------  -------------
 24      613-712-0024      BUSY
 */