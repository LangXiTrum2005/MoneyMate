exec(open(r'D:\MoneyMate\.archify\workflow-moneymate-20261103-161801\repair.py',encoding='utf-8').read().split("d=load('01-tong-quan')")[0])
d=load('02-giao-dich');n(d,'cancel',col=0);e(d,'form-cancel',fromSide='top',toSide='bottom');save('02-giao-dich',d)
d=load('05-xuat-nhap');n(d,'commit',label='Nhập nguyên khối',sublabel='Ghi sau xác nhận');save('05-xuat-nhap',d)
