
SELECT DATE_FORMAT(b.update_time, '%Y') AS YEAR FROM t_blog b GROUP BY YEAR ORDER BY YEAR DESC
select * from t_blog b where date_format(b.update_time, '%Y') = '2023'