s = 'insert\nhistor\n-1\n1\n1\n2\n3\nFIFTH\nadmin\nfoeinfeniofefioiofhnefhne\nBLUE\n'
file1 = open("script.txt", "w")
# file1.write(str(1))
for i in range(1000):
    z = s.replace("insert", "insert "+str(i))
    # print(z)
    file1.write(z)
    
