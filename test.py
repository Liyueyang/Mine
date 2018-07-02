# -*- coding: utf-8 -*-
import json
import gzip
import msgpack
import urllib
import urllib2
import tarfile

def test():
    # import xmlrpclib
    # # info = xmlrpclib.ServerProxy('https://demo.odoo.com/start').start()
    # # info = xmlrpclib.ServerProxy('http://127.0.0.1:8069/en_US/web/login').start()
    # # url, db, username, password = info['host'], info['database'], info['user'], info['password']
    # # url = 'https://erp.aqara.com'
    # # db = 'erp'
    # url = 'http://127.0.0.1:8069'
    # db = 'erp_6_25'
    # username = 'yueyang.li@lumiunited.com'
    # password = '320443'
    # # common = xmlrpclib.ServerProxy('{}/xmlrpc/2/common'.format(url))
    # # {'host': 'https://demo2.odoo.com', 'password': 'admin', 'user': 'admin', 'database': 'demo_110_1530146175'}
    # # print common.version()
    # # uid = common.authenticate(db, username, password, {})
    # uid = 489
    # models = xmlrpclib.ServerProxy('{}/xmlrpc/2/object'.format(url))
    # models.execute_kw(db, uid, password,
    #                   'res.partner', 'search',
    #                   [[['is_company', '=', True], ['customer', '=', True]]])
    # import urllib.request
    # import urllib.parse

    url = "http://127.0.0.1:8069/web"
    postdata = urllib.parse.urlencode({
        'csrf_token':'c547ebb2e3e129a18248557044dcb32ed4e6acf3o1530503352',
        'login':'yueyang.li%40lumiunited.com',
        'password':'320443'
                   # & redirect =
    }).encode("utf-8")  # 将数据使用urlencode编码后，使用encode（）设置utf-8编码
    req = urllib.request.Request(url, postdata)
    req.add_header({
        'Host':'127.0.0.1:8069',
        'Cache-Control':'max-age=0',
        'Origin':'http://127.0.0.1:8069',
        'Upgrade-Insecure-Requests':'1',
        'Content-Type':'application/x-www-form-urlencoded',
        'User-Agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36',
        'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
        'Referer':'http://192.168.0.190:8069/en_US/web/login',
        'Accept-Encoding':'gzip, deflate',
        'Accept-Language':'en,zh-CN;q=0.9,zh;q=0.8',
        'Cookie':'website_lang=en_US; session_id=59cffb6285c0a515929b15d7f661b5c095c3bf83',
        'Content-Length':'122'
    })
    data = urllib.request.urlopen(req).read().decode("utf-8")
    print(data)

def request():
    try:
        url = "http://127.0.0.1:8069"
        values = {
            'csrf_token':'c547ebb2e3e129a18248557044dcb32ed4e6acf3o1530503352',
            'login':'yueyang.li%40lumiunited.com',
            'password':'320443'
        }
        data = json.JSONEncoder().encode(values)
        print data
        user_agent = 'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'
        headers = {'Host':'127.0.0.1:8069',
            'Cache-Control':'max-age=0',
            'Origin':'http://127.0.0.1:8069',
            'Upgrade-Insecure-Requests':1,
            'Content-Type':'application/x-www-form-urlencoded',
            'User-Agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36',
            'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
            'Referer':'http://192.168.0.190:8069/en_US/web/login',
            'Accept-Encoding':'gzip, deflate',
            'Accept-Language':'en,zh-CN;q=0.9,zh;q=0.8',
            'Cookie':'website_lang=en_US; session_id=59cffb6285c0a515929b15d7f661b5c095c3bf83',
            'Content-Length': 122}
        req = urllib2.Request(url, data=data, headers=headers)
        res_data = urllib2.urlopen(req)
        res = res_data.read()

        print "response:" + str(len(res))
        if res_data.getcode() == 200:
            return res
    except urllib2.HTTPError, err:
        print(err.code)
        print(err.read())
        raise

if __name__ == '__main__':
    # test()
    request()
#action=query&keyword=410603199006131036