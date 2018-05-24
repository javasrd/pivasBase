CREATE OR REPLACE TYPE ty_str_split IS TABLE OF VARCHAR2 (4000);
/
CREATE OR REPLACE FUNCTION fn_split (p_str IN VARCHAR2, p_delimiter IN VARCHAR2)
    RETURN ty_str_split result_cache
IS
    j INT := 0;
    i INT := 1;
    len INT := 0;
    len1 INT := 0;
    str VARCHAR2 (4000);
    str_split ty_str_split := ty_str_split ();
BEGIN
    len := LENGTH (p_str);
    len1 := LENGTH (p_delimiter);
    WHILE j < len
    LOOP
        j := INSTR (p_str, p_delimiter, i);
        IF j = 0
        THEN
            j := len;
             str := SUBSTR (p_str, i);
             str_split.EXTEND;
             str_split (str_split.COUNT) := str;
            IF i >= len
            THEN
                EXIT;
            END IF;
        ELSE
            str := SUBSTR (p_str, i, j - i);
            i := j + len1;
            str_split.EXTEND;
            str_split (str_split.COUNT) := str;
        END IF;
    END LOOP;
    RETURN str_split;
END fn_split;
/


--获取字符串split 个数
CREATE OR REPLACE FUNCTION getCount (v_source IN VARCHAR2, v_delimiter IN VARCHAR2)  
RETURN INT IS  
       j    INT := 0;  
       i    INT := 1;  
       len  INT := 0;  
       delim_len INT := 0;  
       cnt INT := 0;  
BEGIN  
       len  := length(v_source);  
       delim_len := length(v_delimiter);  
       WHILE j < len LOOP  
             j := instr(v_source, v_delimiter, i);  
             IF j = 0 THEN  
                j := len;  
                cnt := cnt+1;  
                IF i >= len THEN  
                EXIT;  
          END IF;  
        ELSE  
          i := j + delim_len;  
          cnt := cnt+1;  
        END IF;  
      END LOOP;  
     RETURN cnt;  
END getCount; 
/