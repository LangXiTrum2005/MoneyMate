# MoneyMate — phân tích luồng nghiệp vụ

## Phạm vi và hiện trạng

Thiết kế đích theo MoneyMate_Roadmap_6_Thang_Chi_Tiet.pdf (13 trang), không phải xác nhận tính năng đã có. Mã nguồn hiện mới là khung Compose với Greeting("Android"). Thư mục không có Git, nên không thể gắn bằng chứng vào commit.

MVP tháng 1–3 chạy offline, dữ liệu do người dùng tự nhập, lưu Room. Không có đăng nhập, truy cập ngân hàng hoặc thanh toán tiền thật trong phạm vi. Tháng 4–6 bổ sung nhắc việc, cài đặt, khóa ứng dụng, xuất/nhập và API demo độc lập.

## Luồng chính

Mở ứng dụng → xác thực nếu bật khóa và hết hạn → trang chủ → thêm/sửa/xóa → kiểm tra dữ liệu và xác nhận nếu xóa → ghi Room → tính lại tổng thu, tổng chi, số dư, thống kê và ngân sách → UI hiển thị kết quả.

## Luồng và nhánh

| Sơ đồ | Luồng chính | Nhánh quan trọng | Nguồn roadmap |
|---|---|---|---|
| 01 | Mở app, ghi chép, xem kết quả | Khóa tắt/bật, xác thực sai/hủy, không hỗ trợ sinh trắc, dữ liệu rỗng, tải lỗi và thử lại | tr. 2, 5–6, 8, 10 |
| 02 | Thêm/sửa, kiểm tra và lưu | Xóa xác nhận/hủy, tiền sai, lưu lỗi, bản ghi không còn, hủy form | tr. 3–6, 10 |
| 03 | Lọc, thống kê, so ngân sách | Không có giao dịch, chưa có ngân sách, còn hạn mức/bằng mức/vượt mức | tr. 2, 5–6, 10 |
| 04 | Tạo/sửa danh mục | Xóa khi không dùng, chặn khi có tham chiếu, hủy xóa, dữ liệu sai | tr. 6, 10 |
| 05 | Nhập có xác nhận; xuất có nơi lưu | Tệp hỏng, ID trùng, hủy từng giai đoạn, lỗi ghi và hoàn tác | tr. 2, 8, 10 |
| 06 | Bật nhắc, cấp quyền, lập lịch và thông báo | Tắt nhắc, từ chối quyền, hệ thống trì hoãn | tr. 2, 7, 10 |
| 07 | Tải API demo, hiện thành công | Mất mạng, hết giờ, HTTP/dữ liệu lỗi, thử lại/rời màn | tr. 9, 11 |

## Quy tắc bắt buộc

- VND nguyên lưu Long; chặn tiền ≤ 0, rỗng, sai kiểu và vượt giới hạn.
- Số dư = thu − chi; không lưu một số dư độc lập chưa được đồng bộ.
- Sửa/xóa phải cập nhật thống kê và ngân sách.
- Ngân sách theo tháng/toàn bộ hoặc tháng/danh mục. Còn lại = hạn mức − chi. Cảnh báo vượt trong UI.
- Không chia cho 0 khi không có chi; có empty/loading/error state.
- Ngày/tháng nhất quán theo múi giờ được chọn; chú ý ranh giới tháng.
- Migration không xóa dữ liệu. Nhập lỗi không làm hỏng database.
- Nhắc việc và API demo không làm gián đoạn CRUD offline; API không đụng dữ liệu cá nhân thật.
- Khóa sinh trắc không phải bằng chứng database đã được mã hóa.

## Chính sách đề xuất, cần xác nhận khi triển khai

1. Tạm chặn xóa danh mục còn giao dịch, thay vì cascade delete.
2. Nhập: kiểm tra toàn tệp, tệp sai thì từ chối toàn lần nhập; ID trùng bỏ qua, không ghi đè; transaction hoàn tác nếu ghi lỗi. Schema/phiên bản tệp và ánh xạ danh mục còn mở.
3. Ngân sách: hạn mức nguyên dương, duy nhất theo tháng/phạm vi; tách trạng thái bằng hạn mức; cần chốt cách hiển thị ngân sách toàn tháng cùng ngân sách danh mục.
4. Giữ form khi lưu lỗi, cho thử lại; xử lý bản ghi đã mất và tránh lưu trùng.
5. Kiểm soát tràn khi cộng tổng; kiểm tra tham chiếu và loại danh mục; chốt giới hạn ghi chú, ngày tương lai.
6. Chốt timeout khóa, fallback khi không có thông tin xác thực thiết bị, giờ nhắc và nội dung thông báo.

## Đối chiếu ví dụ

- Thu 500.000đ + chi 35.000đ → số dư ròng 465.000đ.
- Sửa chi 35.000đ thành 50.000đ → tổng chi tăng 15.000đ, số dư còn 450.000đ.
- Hạn mức 1.500.000đ, chi 1.600.000đ → vượt 100.000đ.

## Bằng chứng và giới hạn

Đã đọc đủ 13 trang PDF. Nguồn mã: MainActivity.kt, AndroidManifest.xml, app/build.gradle.kts và danh sách toàn bộ file app. Sơ đồ diễn đạt nghiệp vụ thiết kế, không chứng minh các chức năng đã được triển khai. Các nguồn trang và đề xuất được ghi trong thẻ thông tin của từng sơ đồ.

Cả 7 sơ đồ qua finalize Archify: validate, deliver, check và browser-check, không có diagnostic. Chưa đánh giá hình ảnh bằng mắt. Nội dung, nhánh và chú giải bằng tiếng Việt; thanh công cụ cố định của Viewer bằng tiếng Anh.
