exec(open(r'D:\MoneyMate\.archify\workflow-moneymate-20261103-161801\repair.py',encoding='utf-8').read().split("d=load('01-tong-quan')")[0])
def unpin(d):
    for x in d['edges']:
        for k in ['route','fromSide','toSide','bias','labelAt','labelDx','labelDy','via']:x.pop(k,None)
d=load('01-tong-quan');unpin(d)
d['lanes']=[{'id':'read','label':'Dữ liệu rỗng / tải lại','variant':'exception'},{'id':'app','label':'Luồng chính · người dùng và ứng dụng'},{'id':'lock','label':'Nhánh xác thực · mở rộng tháng 5'},{'id':'err','label':'Xác thực chưa thành công','variant':'exception'}]
for id in ['open','gate','home','form','commit','result']:n(d,id,lane='app')
for id,col in [('empty',1),('readerror',2),('retry',3)]:n(d,id,lane='read',col=col)
save('01-tong-quan',d)
d=load('02-giao-dich');unpin(d)
d['lanes']=[{'id':'fault','label':'Lỗi nhập / lỗi lưu / bản ghi đã mất','variant':'exception'},{'id':'main','label':'Luồng chính · thêm và sửa giao dịch'},{'id':'deletion','label':'Nhánh xóa giao dịch'},{'id':'err','label':'Hủy thao tác','variant':'exception'}]
for id in ['choose','form','valid','write','aggregate','done']:n(d,id,lane='main')
n(d,'delete',lane='deletion',col=2)
for id in ['invalid','saveerror','reload']:n(d,id,lane='fault')
n(d,'cancel',col=1)
save('02-giao-dich',d)
d=load('05-xuat-nhap');unpin(d)
d['lanes']=[{'id':'errors','label':'Tệp lỗi / hủy thao tác','variant':'exception'},{'id':'import','label':'Nhánh nhập dữ liệu · người dùng và ứng dụng'},{'id':'duplicate','label':'Xử lý dữ liệu trùng · đề xuất'},{'id':'exportlane','label':'Nhánh xuất dữ liệu · người dùng và tệp'},{'id':'err','label':'Lỗi ghi dữ liệu','variant':'exception'}]
for id in ['choose','file','validate','preview','commit','refresh']:n(d,id,lane='import')
n(d,'bad',lane='errors',col=2)
n(d,'cancel',lane='errors',col=0)
n(d,'dup',lane='duplicate',col=3)
save('05-xuat-nhap',d)
