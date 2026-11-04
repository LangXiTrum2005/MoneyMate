from pathlib import Path
import json, html, shutil
root=Path(r'C:\Users\Chung\.codex\visualizations\2026\11\03\01a1a0b2-850e-7062-b4b1-759d086f73bb\.archify\workflow-moneymate-20261103-161801')
catalog=json.loads((root/'catalog.json').read_text(encoding='utf-8'))
for slug,_,_ in catalog:
    r=json.loads((root/(slug+'.finalize-summary.json')).read_text(encoding='utf-8'))
    assert r['ok'] and all(v=='pass' for v in r['gates'].values()), slug
cards=''.join(f'<a class="card" href="{slug}.html" target="_blank"><span class="number">{i:02}</span><div><strong>{html.escape(title.split(" · ",1)[1])}</strong><p>{html.escape(desc)}</p></div><span aria-hidden="true">↗</span></a>' for i,(slug,title,desc) in enumerate(catalog,1))
options=''.join(f'<option value="{slug}.html">{html.escape(title)}</option>' for slug,title,desc in catalog)
page='''<!doctype html><html lang="vi"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>MoneyMate · Sơ đồ luồng nghiệp vụ</title><style>
:root{color-scheme:dark;--bg:#0b1422;--panel:#111f31;--line:#263b53;--ink:#e9f2fc;--muted:#a9bbcf;--accent:#63ded0}*{box-sizing:border-box}body{margin:0;background:var(--bg);color:var(--ink);font:16px/1.65 system-ui,'Segoe UI',sans-serif}main{max-width:1200px;margin:auto;padding:48px 28px 64px}header{border-bottom:1px solid var(--line);padding-bottom:28px}.eyebrow{color:var(--accent);font-size:12px;font-weight:700;letter-spacing:.18em}h1{font-size:clamp(30px,4vw,48px);line-height:1.15;letter-spacing:-.035em;margin:14px 0 20px}h2{font-size:23px;margin:36px 0 16px}p{margin:0 0 12px}.lead{max-width:850px;color:var(--muted);font-size:18px}.pill{display:inline-block;margin:8px 8px 0 0;padding:5px 12px;background:#143735;border:1px solid #285752;border-radius:20px;color:#9ce6d7;font-size:13px}.notice{margin-top:24px;padding:18px 22px;border-left:3px solid #f1bd72;background:#2b261e;border-radius:0 8px 8px 0;color:#f0d9b8}.grid{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:14px}.card{display:flex;gap:16px;align-items:flex-start;padding:22px;background:var(--panel);border:1px solid var(--line);border-radius:12px;text-decoration:none;color:var(--ink)}.card:hover{border-color:var(--accent);background:#172d40}.card strong{font-size:17px}.card p{font-size:14px;color:var(--muted);margin:6px 0 0}.number{font:700 22px/1.4 ui-monospace,monospace;color:var(--accent)}.card>div{flex:1}.preview{border:1px solid var(--line);border-radius:12px;overflow:hidden}.toolbar{display:flex;align-items:center;gap:16px;padding:14px 18px;background:var(--panel)}select{flex:1;min-width:0;background:#0c1828;border:1px solid #456078;border-radius:6px;color:var(--ink);padding:10px;font:inherit}iframe{display:block;width:100%;height:800px;border:0;background:#0c1520}table{width:100%;border-collapse:collapse;font-size:15px}th,td{padding:13px 16px;border-bottom:1px solid var(--line);text-align:left;vertical-align:top}th{color:var(--accent);font-weight:600;background:var(--panel)}td:first-child{width:25%;font-weight:600}.notes{padding:22px;background:var(--panel);border:1px solid var(--line);border-radius:12px;color:var(--muted)}.notes strong{color:var(--ink)}ul{padding-left:22px;margin:10px 0}li{margin:7px 0}a{color:var(--accent)}footer{margin-top:32px;font-size:13px;color:var(--muted)}.flow{padding:20px 24px;border:1px solid #31595d;background:#102b32;border-radius:12px;font-size:18px;color:#b5f4e9}.small{font-size:13px;color:var(--muted)}@media(max-width:700px){main{padding:28px 16px}.grid{grid-template-columns:1fr}.toolbar{flex-direction:column;align-items:stretch}iframe{height:700px}table{font-size:13px}td,th{padding:10px 8px}}@media print{body{background:white;color:black}.preview{display:none}.card,.notes{background:white;color:black}.lead,.card p{color:#333}a{color:#145857}}
</style></head><body><main><header><div class="eyebrow">MONEYMATE / PHÂN TÍCH NGHIỆP VỤ / ARCHIFY</div><h1>Ghi chép thu chi tại máy.<br>Theo dõi tài chính qua từng giao dịch.</h1><p class="lead">Thiết kế luồng chính và các trường hợp rẽ nhánh cho ứng dụng Android quản lý tài chính cá nhân, dựa trên roadmap 24 tuần và đối chiếu mã nguồn hiện có.</p><span class="pill">MVP: tháng 1–3</span><span class="pill">Mở rộng: tháng 4–6</span><span class="pill">7 sơ đồ tương tác</span><div class="notice"><strong>Trạng thái thực tế:</strong> dự án hiện mới có khung Kotlin + Jetpack Compose và màn “Hello Android”. Các sơ đồ mô tả <strong>thiết kế đích theo roadmap</strong>, chưa phải các chức năng đã triển khai. Không có Git trong thư mục được cung cấp để gắn bằng chứng vào một commit.</div></header>
<h2>Luồng cốt lõi</h2><div class="flow">Mở ứng dụng → nhập thu/chi → kiểm tra dữ liệu → lưu Room → cập nhật số dư, thống kê và ngân sách.</div><p class="small" style="margin-top:10px">Người dùng tự nhập dữ liệu; không cần mạng cho nghiệp vụ chính. Khóa ứng dụng là tùy chọn mở rộng. Thay đổi hoặc xóa giao dịch đều phải tính lại các kết quả liên quan.</p>
<h2>Mở từng sơ đồ</h2><div class="grid">CARDS</div>
<h2>Xem ngay trong trang</h2><div class="preview"><div class="toolbar"><label for="diagram">Chọn luồng</label><select id="diagram">OPTIONS</select><a id="full" href="01-tong-quan.html" target="_blank">Mở toàn màn hình ↗</a></div><iframe id="viewer" title="Sơ đồ nghiệp vụ MoneyMate" src="01-tong-quan.html"></iframe></div><p class="small" style="margin-top:10px">Nội dung sơ đồ, điều kiện và chú giải được viết bằng tiếng Việt. Thanh công cụ cố định của trình xem Archify dùng tiếng Anh. Có thể phóng to và xuất hình từ trình xem.</p>
<h2>Các quy tắc chi phối luồng</h2><table><thead><tr><th>Phạm vi</th><th>Quy tắc / hệ quả nghiệp vụ</th></tr></thead><tbody><tr><td>Giao dịch</td><td>Số tiền là VND nguyên dương, lưu bằng Long. Chặn rỗng, sai kiểu, bằng 0, âm hoặc vượt giới hạn. Xóa phải được xác nhận.</td></tr><tr><td>Số dư và báo cáo</td><td>Số dư = tổng thu − tổng chi, suy ra từ giao dịch. Sửa/xóa phải cập nhật tổng hợp. Dữ liệu rỗng hiển thị số 0; biểu đồ không chia cho 0.</td></tr><tr><td>Ngân sách</td><td>Phạm vi theo tháng, hoặc theo tháng và danh mục. Còn lại = hạn mức − tổng chi đúng phạm vi; vượt mức khi chi lớn hơn hạn mức. Cảnh báo UI không chặn ghi nhận khoản chi.</td></tr><tr><td>Ngày và bộ lọc</td><td>Dùng cùng múi giờ khi xác định ngày/tháng. Có thể kết hợp lọc loại, danh mục, ngày, ghi chú và sắp xếp theo thời gian hoặc số tiền.</td></tr><tr><td>Dữ liệu tại máy</td><td>Room là nguồn dữ liệu nghiệp vụ. Migration phải giữ dữ liệu cũ. Không xóa tùy tiện danh mục còn giao dịch tham chiếu.</td></tr><tr><td>Mở rộng</td><td>Nhắc ghi chép: từ chối quyền thì app vẫn dùng được. Khóa app không đồng nghĩa mã hóa database. API demo không chứa dữ liệu cá nhân và không ghi vào Room chính.</td></tr></tbody></table>
<h2>Các quyết định còn cần chốt</h2><div class="notes"><p><strong>Đây là đề xuất thiết kế để làm rõ các nhánh, không phải yêu cầu đã được roadmap quyết định:</strong></p><ul><li><strong>Xóa danh mục đang dùng:</strong> tạm chặn và giải thích; phương án khác có thể là chuyển giao dịch sang danh mục khác hoặc ẩn danh mục.</li><li><strong>Nhập tệp:</strong> kiểm tra toàn bộ trước khi ghi; từ chối toàn lần nhập nếu tệp sai; bỏ qua ID trùng, không tự ghi đè; ghi nguyên khối và hoàn tác nếu lỗi.</li><li><strong>Ngân sách:</strong> hạn mức nguyên dương và một bản ghi cho mỗi tháng/phạm vi; cần chốt chính sách khi có ngân sách toàn tháng và danh mục cùng lúc.</li><li><strong>Kiểm tra thêm:</strong> giới hạn ghi chú, ngày tương lai, thay loại danh mục đã được dùng, chống lưu trùng và xử lý tràn khi cộng tổng.</li><li><strong>Thiết lập mở rộng:</strong> thời gian khóa lại, trường hợp không có sinh trắc hoặc khóa thiết bị, giờ nhắc và cấu trúc/phiên bản tệp nhập chưa được xác định.</li></ul></div>
<h2>Căn cứ phân tích</h2><div class="notes"><p><strong>Roadmap 6 tháng, 13 trang:</strong> trang 2–3 xác định phạm vi và quy tắc tiền; trang 4–6 mô tả MVP; trang 7–9 mô tả phần mở rộng; trang 10–11 cung cấp schema tối thiểu và các ca kiểm thử. Các chỉ dẫn học tập trong tài liệu được xem là nội dung nguồn, không phải lệnh thao tác dự án.</p><p><strong>Đối chiếu mã nguồn:</strong> <code>app/src/main/java/com/example/moneymate/MainActivity.kt</code> chỉ khởi tạo theme, Scaffold và Greeting; <code>app/build.gradle.kts</code> hiện chứa các phụ thuộc nền tảng Compose, chưa có Room và các lớp nghiệp vụ trong thư mục nguồn.</p><p><strong>Kiểm chứng:</strong> cả 7 sơ đồ đã qua các bước validate, xuất HTML, kiểm tra artifact và kiểm tra trình duyệt tự động của Archify. Chưa thực hiện đánh giá hình ảnh bằng mắt.</p></div>
<footer>Phân tích ngày 03/11/2026 · Chỉ tạo tài liệu sơ đồ, không thay đổi mã nguồn ứng dụng.</footer></main><script>const picker=document.getElementById('diagram');picker.addEventListener('change',()=>{document.getElementById('viewer').src=picker.value;document.getElementById('full').href=picker.value;});</script></body></html>'''.replace('CARDS',cards).replace('OPTIONS',options)
(root/'index.html').write_text(page,encoding='utf-8')
analysis='''# MoneyMate — phân tích luồng nghiệp vụ

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
'''
(root/'phan-tich-nghiep-vu.md').write_text(analysis,encoding='utf-8')
dest=Path(r'D:\MoneyMate\.archify\workflow-moneymate-20261103-161801')
for name in ['index.html','phan-tich-nghiep-vu.md','catalog.json']+[slug+ext for slug,_,_ in catalog for ext in ['.html','.json']]:
    shutil.copy2(root/name,dest/name)
print('All 7 workflows passed. Overview and analysis packaged at',dest)
