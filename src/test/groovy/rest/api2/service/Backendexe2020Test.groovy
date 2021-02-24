package rest.api2.service

import spock.lang.Specification

class Backendexe2020Test extends Specification {
    Backendexe2020 service
    void setup() {
        service = new Backendexe2020()
    }

    void cleanup() {
    }

    def "DigitComma"() {
        when:
        String result = service.digitComma(num)

        then:
        result == expected

        where:
        num | expected
        9527   | "9,527"
        3345678   | "3,345,678"
        -1234.45   | "-1,234.45"
        0   | "0"
        1   | "1"
        1000   | "1,000"
        -222   | "-222"
        -2222   | "-2,222"
        Math.pow(2,30).toInteger()   | "1,073,741,824"
    }

    def "pipe TestCaseA int muil 2,int to string ,and get string length"(){
        when:
        def result = service.pipe(start ,{ it*2},Integer.&toString,String.&length)
        then:
        result == expected
        where:
        start | expected
        666 | 4
        333 | 3
    }
    def "pipe2 TestCaseB LinkedList add e1,add e2,add e3,remove first "(){
        when:
        service.pipe2(start,{ArrayList it -> it.add(e1)},{ArrayList it -> it.add(e2)},{ArrayList it -> it.add(e3)}, { ArrayList it -> it.remove(0) })
        then:
        start == expected
        where:
        start|e1|e2|e3|expected
        [1,2,3]|4|5|6|[2,3,4,5,6]
        ["a","b","c"]|"d"|"e"|"f"|["b","c","d","e","f"]
    }
}