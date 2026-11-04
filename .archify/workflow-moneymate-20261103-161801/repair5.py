exec(open(r'D:\MoneyMate\.archify\workflow-moneymate-20261103-161801\repair.py',encoding='utf-8').read().split("d=load('01-tong-quan')")[0])
d=load('01-tong-quan')
r=json.loads((root/'01-tong-quan.finalize-summary.json').read_text(encoding='utf-8'))
for change in r['diagnostics'][0]['evidence']['verifiedRepair']['changes']:
    p=change['path'].split('/');d[p[1]][int(p[2])][p[3]]=change['value']
save('01-tong-quan',d)
d=load('02-giao-dich');e(d,'choose-delete',fromSide='bottom',toSide='left');save('02-giao-dich',d)
d=load('05-xuat-nhap')
e(d,'choose-export',fromSide='bottom',toSide='left')
e(d,'file-cancel',fromSide='top',toSide='top')
e(d,'dup-cancel',toSide='right')
e(d,'dest-cancel',toSide='left')
save('05-xuat-nhap',d)
