# All rights reserved MCBouncer.com
from BeautifulSoup import BeautifulSoup
import urllib2 as urllib
import time

SERVER = mc2.joe.to

f = open("mcbback.txt", "w").write("")
z = open("mcbback.txt", "a")
for page in range(1,3):
  if True:
       site = urllib.urlopen("http://mcbans.com/server/"+SERVER+"/"+str(page)).read()
       soup = BeautifulSoup(site)
       table = soup.find('table')
       rows = table.findAll('tr')
       for tr in rows:
           cols = tr.findAll('td')
           if len(cols) > 3:
               if not "temporary" in ''.join(cols[4].find(text=True)):
                    f = cols[1].renderContents()+", "
                    q = f.split("/>")[1]
                    player = q[q.find('">')+2:-6]
                    try:
                        z.write(player+"||"+''.join(cols[3].find(text=True))+"||"+''.join(cols[4].find(text=True))+"||"+''.join(cols[5].find(text=True))+"\n")
                    except:
                        open("mcbe.txt", "a").write("\n"+player)
       print page
       time.sleep(2)
time.sleep(100000000)
