import json
from pathlib import Path
root=Path(r'C:\Users\Chung\.codex\visualizations\2026\11\03\01a1a0b2-850e-7062-b4b1-759d086f73bb\.archify\workflow-moneymate-20261103-161801')
def load(slug):return json.loads((root/(slug+'.json')).read_text(encoding='utf-8'))
def save(slug,d):(root/(slug+'.json')).write_text(json.dumps(d,ensure_ascii=False,indent=2),encoding='utf-8')
def n(d,id,**kw):next(x for x in d['nodes'] if x['id']==id).update(kw)
def e(d,id,**kw):next(x for x in d['edges'] if x['id']==id).update(kw)
d=load('01-tong-quan')
n(d,'fallback',col=2)
e(d,'fallback-home',fromSide='top',toSide='bottom')
e(d,'fallback-denied',fromSide='bottom',toSide='right')
e(d,'retry-home',route='outside-right',fromSide='right',toSide='right')
save('01-tong-quan',d)
d=load('02-giao-dich');e(d,'delete-write',fromSide='bottom',toSide='left',route='drop',bias=0.2);save('02-giao-dich',d)
d=load('03-thong-ke-ngan-sach');n(d,'below',yOffset=90);n(d,'compare',yOffset=-30);save('03-thong-ke-ngan-sach',d)
d=load('04-danh-muc');n(d,'input',yOffset=-55);n(d,'used',yOffset=55);save('04-danh-muc',d)
d=load('05-xuat-nhap')
# Separate the export branch and its return paths from the import pipeline.
d['lanes'].insert(0,{'id':'exportlane','label':'Branche xuất dữ liệu · người dùng và tệp'})
d['lanes'][0]['label']='Nhánh xuất dữ liệu · người dùng và tệp'
for id in ['export','dest','write','exportdone']:n(d,id,lane='exportlane')
e(d,'choose-export',fromSide='top',toSide='left')
n(d,'cancel',col=0)
e(d,'preview-cancel',route='bottom-channel',fromSide='bottom',toSide='bottom')
e(d,'dest-cancel',route='return-left',fromSide='left',toSide='left')
save('05-xuat-nhap',d)
d=load('06-nhac-nho')
receipt=json.loads((root/'06-nhac-nho.finalize-summary.json').read_text(encoding='utf-8'))
for change in receipt['diagnostics'][0]['evidence']['verifiedRepair']['changes']:
    p=change['path'].split('/');d[p[1]][int(p[2])][p[3]]=change['value']
save('06-nhac-nho',d)
