-- File: PLh10.sql 
-- Author: Kyle Flynn
-- ---------------------------------- 
SET SERVEROUTPUT ON  
SET VERIFY OFF 
-- ---------------------------------- 
ACCEPT traineeID NUMBER PROMPT 'Enter a trainee ID: ' 
ACCEPT increment NUMBER PROMPT 'Enter an increment for his trainers: ' 
DECLARE 
   sr     sailors%ROWTYPE; 
   sedit  sailors%ROWTYPE;
   CURSOR tCursor IS 
      SELECT S.sid, S.sname, S.rating, S.age, S.trainee
      FROM sailors S, sailors R
      WHERE S.trainee = R.sid AND
            R.sid = '&traineeID';

BEGIN 
   OPEN tCursor; 
   LOOP 
      -- Fetch the qualifying rows one by one 
      FETCH tCursor INTO sr;
      EXIT WHEN tCursor%NOTFOUND;
      -- Print the sailor' old record 
      DBMS_OUTPUT.PUT_LINE ('+++++ old row: '||sr.sid||'  '||sr.sname||sr.rating||'  '||sr.age||'   '||sr.trainee);
      -- Increment the trainers' rating 
      sr.rating := sr.rating + &increment;
      UPDATE sailors
        SET rating = sr.rating
        WHERE sailors.sid = sr.sid;
      -- Print the sailor' new record 
      SELECT s.*
        INTO sedit
        FROM sailors s
        WHERE s.sid = sr.sid;

      DBMS_OUTPUT.PUT_LINE('+++++ new row: ' ||sedit.sid|| '  ' ||sedit.sname||sedit.rating||'  '||sedit.age||'   '||sedit.trainee);   
   END LOOP; 
   -- test whether the sailor has no trainers (Hint: test tCursor%ROWCOUNT) 
   IF tCursor%ROWCOUNT = 0
        THEN DBMS_OUTPUT.PUT_LINE('+++++ ' || &traineeID || ' is 1) not a sailor, or 2) does not have a trainer');     
   ELSE   
        DBMS_OUTPUT.PUT_LINE('+++++ Sailors DB updated!');
   END IF; 
   CLOSE tCursor; 
EXCEPTION 
WHEN OTHERS THEN 
    DBMS_OUTPUT.PUT_LINE('+++++'||SQLCODE||'...'||SQLERRM); 
END; 

-- Let's see what happened to the database 
SELECT * 
FROM   sailors S 
WHERE  S.trainee = '&traineeID'; 
UNDEFINE traineeID 
UNDEFINE increment