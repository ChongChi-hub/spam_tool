import pyautogui
import time

# Khoảng thời gian chờ (giây) trước khi công cụ bắt đầu chạy
# Giúp bạn có đủ thời gian để nhấp chuột vào ô nhập liệu (chatbox)
print("Chuẩn bị chạy... Hãy nhấp chuột vào ô chat!")
time.sleep(5)

# Nội dung bạn muốn gửi tự động
noi_dung = "chan bo may de"

# Số lần bạn muốn lặp lại tin nhắn
so_lan = 100

for i in range(so_lan):
    # Gõ nội dung tin nhắn
    pyautogui.typewrite(noi_dung)
    
    # Nhấn phím Enter để gửi tin nhắn
    pyautogui.press("enter")
    
    # Khoảng nghỉ giữa các lần gửi (tránh bị hệ thống khóa do gửi quá nhanh)
    time.sleep(0.1)

print("Hoàn thành!")
