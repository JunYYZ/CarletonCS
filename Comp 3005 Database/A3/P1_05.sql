/*
 Problem 1.5
 Find the names of all the subscribers who subscribe to at least three services.
 */
SELECT s.name
FROM subscribers s
    JOIN service_subscribers ss ON s.portid = ss.portid
GROUP BY s.name
HAVING COUNT(ss.service) >= 3;
/*
 Test Output:
 name
 --------------
 Chris Pronger
 Frank Thomas
 Homer Simpson
 Joe Carter
 Matt Stajan
 Michael Jordan
 Steve Sampras
 Vince Carter
 */