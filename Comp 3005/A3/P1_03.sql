/*
 Problem 1.3
 List the names of all the subscribers who are originators of a call to someone who is also a subscriber on the same switch (i.e. a line to line call).
 */
SELECT DISTINCT s1.name AS originator
FROM calls c
    JOIN subscribers s1 ON c.orig = s1.portid
    JOIN subscribers s2 ON c.term = s2.portid
    /*
     Test Output:
     originator
     --------------
     Ed Belfour
     Homer Simpson
     Jason Allison
     Mats Sundin
     Michael Jordan
     */