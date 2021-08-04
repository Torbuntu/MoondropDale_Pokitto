def list = "112200003300000000000000000000000000000000000000000000000000002211443311".split("")
.collect{Integer.parseInt(it)}

byte[] list2 = new byte[list.size()]
for(int i = 0; i < list.size(); i++){
    list2[i] = list[i].byteValue()
}

def f = new File("field")
f << list2
