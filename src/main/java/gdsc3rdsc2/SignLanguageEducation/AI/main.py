
import sys
#sys.path.append('<YOUR PATH TO KOMORAN IF YOU NEED>')
import json
import re, os
import io
from datetime import datetime
#from komoran.komoran import KomoranClass
from konlpy.tag import Mecab, Komoran
sys.path.append(os.path.abspath(__file__).split("/main.py")[0])
from ko_restoration import util, restoration

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding = 'utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding = 'utf-8')
def set_env():
    complex_verb_set = util.create_set(os.path.abspath(__file__).split('main.py')[0] + 'ko_restoration/complex_verb.txt')

    mecab = Mecab(dicpath=r"C:\mecab\mecab-ko-dic")
    return mecab, complex_verb_set

def start_restoration(tok, complex_verb_set, lines): #단어 원형 변환
    results = ""
    for row in lines:
        body = row
        body = restoration.rm_url(body)
        body = restoration.rm_specials(body)
        if body.strip() != "":
            result = tok.pos(body.strip())
            if len(result[0]) != 0:
                tokens = restoration.tok_processing(result, complex_verb_set)
                if tokens == "":
                    continue
                else:
                    results += tokens + " "
    return results

def filename_filter(filename): #파일 이름에서 정보 추출
    temp = filename.split("_")
    dir = temp[3]
    file = temp[2]
    return dir, file

def id_info(finalwordlist): #해당 영상 정보 반환
    copy = list(finalwordlist)
    temp = []
    final_ID = [] #단어 리스트에 대응하는 영상 정보
    adr = 'C:/Users/SCIENCE/Desktop/morpheme' #경로 변경 필요

    dir_list = os.listdir(adr)

    for dir in dir_list: #dir 01~17까지 검색
        adr2 = adr + '/' + dir #dir 포함 adr
        file_list = os.listdir(adr2)
        for file in file_list: #dir 내 파일 검색
            adr3 = adr2 + '/' + file

            with open(adr3, 'r', encoding='UTF-8') as f: #morpheme.json 열기
                json_data = json.load(f)


            for data in json_data['data']: #json name value
                for word in copy:
                    if data['attributes'][0]['name'] == word:
                        dirnum, filenum = filename_filter(file)

                        newlist = [] #id 정보, 영상 시작/끝 정보
                        if len(json_data['data']) > 1: #문장영상
                            newlist.append(word)
                            #newlist.append(file)
                            #newlist.append(dirnum)
                            newlist.append(filenum+"_"+dirnum)
                            #newlist.append(data['start'])
                            #newlist.append(data['end'])
                        else:
                            newlist.append(word)
                            #newlist.append(file)
                            #newlist.append(dirnum)
                            newlist.append(filenum+"_"+dirnum)
                        copy.remove(word)
                        temp.append(newlist)
                    if(len(copy) == 0):
                        for w in finalwordlist: #순서 정렬
                            for id in temp:
                                if id[0] == w:
                                    final_ID.append(id)
                        return final_ID
    return final_ID


tok, complex_verb_set = set_env()
lines = sys.argv[1:]
for line in lines:
    tok.pos(line)
finalword = start_restoration(tok, complex_verb_set, lines).split(" ")
finalword.remove("")
#print(finalword) #원형 변환 후 단어 리스트
print(id_info(finalword))#최종 결과

##################################################################################
