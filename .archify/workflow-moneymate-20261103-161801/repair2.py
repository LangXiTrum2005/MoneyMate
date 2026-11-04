exec(open(r'D:\MoneyMate\.archify\workflow-moneymate-20261103-161801\repair.py',encoding='utf-8').read().split("d=load('01-tong-quan')")[0])
d=load('01-tong-quan');e(d,'retry-home',route='up-channel',fromSide='top',toSide='top');save('01-tong-quan',d)
d=load('02-giao-dich');x=next(x for x in d['edges'] if x['id']=='delete-write');x.pop('route',None);x.pop('bias',None);save('02-giao-dich',d)
d=load('04-danh-muc')
d['lanes']=[{'id':'validation','label':'Erreur de saisie','variant':'exception'},{'id':'user','label':'Người dùng · thêm và sửa danh mục'},{'id':'deletion','label':'Nhánh xóa danh mục'},{'id':'err','label':'Hủy / còn tham chiếu','variant':'exception'}]
d['lanes'][0]['label']='Lỗi nhập thông tin'
for id in ['list','action','input','save','end']:n(d,id,lane='user',yOffset=0)
for id in ['used','confirm','remove']:n(d,id,lane='deletion',yOffset=0)
n(d,'invalid',lane='validation',col=2)
e(d,'used-confirm',fromSide='right',toSide='left')
e(d,'blocked-list',route='return-left',fromSide='left',toSide='left')
save('04-danh-muc',d)
d=load('05-xuat-nhap');e(d,'dest-cancel',route='return-left',fromSide='bottom',toSide='left');save('05-xuat-nhap',d)
