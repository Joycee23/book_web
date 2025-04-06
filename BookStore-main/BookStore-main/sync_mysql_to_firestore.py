import mysql.connector
import firebase_admin
from firebase_admin import credentials, firestore
from datetime import datetime, date
import json

# Cấu hình kết nối MySQL
mysql_config = {
    'host': 'localhost',  # Thay bằng host MySQL của anh
    'user': 'root',       # Thay bằng user MySQL
    'password': '23102004', # Thay bằng password
    'database': 'bookstore'  # Thay bằng tên database của anh
}

# Kết nối MySQL
db = mysql.connector.connect(**mysql_config)
cursor = db.cursor(dictionary=True)

# Khởi tạo Firestore
cred = credentials.Certificate("src/main/resources/bookstore-f41cb-firebase-adminsdk-fbsvc-6952ebfe23.json")
# File JSON key Firebase
firebase_admin.initialize_app(cred)
firestore_db = firestore.client()

# Lấy danh sách tất cả các bảng trong MySQL
cursor.execute("SHOW TABLES")
tables = [table["Tables_in_bookstore"] for table in cursor.fetchall()]

# Đồng bộ từng bảng lên Firestore
for table in tables:
    cursor.execute(f"SELECT * FROM {table}")
    records = cursor.fetchall()

    # Đẩy từng bản ghi lên Firestore
    for record in records:
        # Chuyển đổi các giá trị datetime.date thành datetime hoặc string
        for key, value in record.items():
            if isinstance(value, date):  # Sử dụng `date` thay vì `datetime.date`
                # Chuyển đổi thành datetime
                record[key] = datetime.combine(value, datetime.min.time())
                # Hoặc chuyển thành chuỗi ISO
                # record[key] = value.isoformat()

        doc_id = str(record.get("id", ""))  # Dùng ID làm document ID nếu có
        if not doc_id:
            doc_id = firestore_db.collection(table).document().id  # Tạo ID nếu không có
        firestore_db.collection(table).document(doc_id).set(record)

    print(f"Đã đồng bộ {len(records)} bản ghi từ bảng {table} lên Firestore")

# Đóng kết nối MySQL
cursor.close()
db.close()
