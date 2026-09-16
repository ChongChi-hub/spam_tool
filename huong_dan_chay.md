# Hướng dẫn sử dụng Auto Tool Pro

Công cụ này được viết bằng ngôn ngữ Java, do đó có thể hoạt động hoàn hảo trên cả **Windows** và **macOS**. 

## Yêu cầu cài đặt
Để chạy được tool, máy tính của bạn bắt buộc phải cài đặt sẵn **Java (JDK)**.
- Bạn có thể tải và cài đặt Java (khuyên dùng JDK 17 hoặc cao hơn) tại trang chủ: [Oracle Java](https://www.oracle.com/java/technologies/downloads/).

Để kiểm tra xem máy đã cài Java thành công chưa, hãy mở Terminal hoặc CMD lên và gõ:
```bash
java -version
```
Nếu màn hình hiện ra phiên bản Java thì bạn đã sẵn sàng sử dụng tool.

---

## Hướng dẫn khởi chạy

### Dành cho macOS (MacBook, iMac...)
1. Mở ứng dụng **Terminal**.
2. Dùng lệnh `cd` để trỏ tới thư mục chứa thư mục mã nguồn (ví dụ: `cd /Users/trongtri/Documents/spam`).
3. Nếu bạn mới tải code về hoặc vừa thay đổi code, hãy biên dịch (compile) bằng lệnh:
   ```bash
   javac AutoTyper.java
   ```
4. Khởi chạy ứng dụng:
   ```bash
   java AutoTyper
   ```

### Dành cho Windows
1. Mở **Command Prompt (cmd)** hoặc **PowerShell**.
   *(Mẹo nhanh: Bạn hãy mở thư mục chứa mã nguồn trong File Explorer, nhấp vào thanh địa chỉ đường dẫn ở phía trên cùng, gõ `cmd` rồi nhấn Enter).*
2. Nếu bạn chưa biên dịch code, hãy gõ:
   ```cmd
   javac AutoTyper.java
   ```
3. Khởi chạy ứng dụng:
   ```cmd
   java AutoTyper
   ```

---

## 🛠 Các tính năng chính
- **Tự động gõ (Auto Typer):** Hỗ trợ tự động dán và gửi văn bản. Hoạt động bằng cách gán chữ vào Clipboard rồi mô phỏng phím `Ctrl + V` (trên Windows) hoặc `Command + V` (trên macOS), sau đó tự động ấn `Enter`. Phương pháp này giúp gõ mượt mà **Tiếng Việt có dấu** mà không bị lỗi font.
- **Tự động Click (Auto Clicker):** Hỗ trợ tự động nhấp liên tục chuột Trái, Phải hoặc Giữa.
- Cửa sổ ứng dụng được thiết kế để luôn hiển thị **nổi lên trên cùng** (Always On Top). Nhờ vậy, ngay cả khi tool đang chạy spam/click với tốc độ cao, bạn vẫn có thể dễ dàng ấn vào nút **DỪNG LẠI** bất kỳ lúc nào!