package rest.api2.service

class Backendexe2020 {
    String digitComma(float num){
        String numS = num.intValue().toString()
        int isNegtive = 0
        if (num<0) isNegtive = 1
        int count = 0
        for (int i = numS.length() - 1 ; i > 0 + isNegtive ; i--) {
            count++
            if (count == 3){
                count = 0
                numS = numS.take(i)+","+numS.substring(i)
            }
        }
        if (num - (int)num !=0) numS = numS +  num.toString().substring(num.toString().indexOf('.'))
        return numS
    }

    def pipe (def start,def arg,def arg2 = null,def arg3 = null,arg4 = null,arg5 = null){
        if (arg) start = arg(start)
        if (arg2) start = arg2(start)
        if (arg3) start = arg3(start)
        if (arg4) start = arg4(start)
        if (arg5) start = arg5(start)
        return start
    }
    void pipe2 (def start,def arg,def arg2 = null,def arg3 = null,arg4 = null,arg5 = null){
        if (arg) arg(start)
        if (arg2) arg2(start)
        if (arg3) arg3(start)
        if (arg4) arg4(start)
        if (arg5) arg5(start)
        void
    }

//With the expansion of business, the number of website users is increasing.
//The original data table has grown to the point where it is very slow to read
//and write data. Please describe how you will handle this from the two aspects
//of reading and writing.
    //1檢查垃圾資料（R/W），2檢查索引是否在正確欄位上(R)，3檢查索引是否太多（W），4檢查每個欄位給的記憶體大小是否妥當（W）
}