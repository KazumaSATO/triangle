""""""
from fastapi import FastAPI
import pymysql

app = FastAPI()


connection = pymysql.connect(
    host="127.0.0.1",
    user="initial",
    password="initial",
    db="initial",
    charset="utf8mb4",
    cursorclass=pymysql.cursors.DictCursor,
)


@app.get("/marketcaps/{company_id}")
def read_root(company_id: int):
    with connection.cursor() as cursor:
        sql = """
        select market_cap from disruptor
        where company_company_id = %s and market_cap is not null limit 1
        """
        cursor.execute(sql, (company_id))
        result = cursor.fetchone()
        if result is not None:
            result = result['market_cap']
        return {'market capitalization': result}
