""""""
from fastapi import FastAPI
import pymysql

app = FastAPI()


def connect():
    return pymysql.connect(
        host="127.0.0.1",
        user="initial",
        password="initial",
        db="initial",
    )


@app.get("/marketcaps/{company_id}")
def read_root(company_id: int):
    connection = connect()
    with connection:
        with connection.cursor() as cursor:
            sql = """
            select market_cap from disruptor
            where company_company_id = %s and market_cap is not null limit 1
            """
            cursor.execute(sql, (company_id))
            result = cursor.fetchone()
            print(company_id, result)
            if result is not None:
                result = result[0]  # ["market_cap"]

            return {"marketCapitalization": result}
