def list = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000".split("")
.collect{Integer.parseInt(it)}

byte[] fieldList = new byte[list.size()]
for(int i = 0; i < list.size(); i++){
    fieldList[i] = list[i].byteValue()
}


def f = new File("field")
f << fieldList


def items = "1,10,1,45,0,0,0,0,0,0,0,0,0,0,0,0,0,0".split(",")
.collect{Integer.parseInt(it)}

byte[] itemList = new byte[items.size()]
for(int j = 0; j < items.size(); j++){
    itemList[j] = items[j].byteValue()
}


def itemFile = new File("items")
itemFile << itemList
