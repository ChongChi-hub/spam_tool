#!/bin/bash
echo "Đang biên dịch mã nguồn..."
javac -encoding UTF-8 AutoTyper.java
if [ $? -ne 0 ]; then
    echo ""
    echo "Biên dịch thất bại! Vui lòng kiểm tra lại."
    exit 1
fi
echo ""
echo "Khởi chạy ứng dụng..."
java AutoTyper
